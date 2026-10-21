// THE DETECTOR'S TABLES ARE READ IN WHILE NOTHING IS WAITING FOR THEM
// (owner, 2026-10-07: "logically ... performance bahut hi fast", and the same
// day: "Karnal level per ... sari chijen").
//
// The detector's tables -- the model ML Kit runs, the word-and-ending model and
// the Unicode tables, about 1.4 MB since CLD2's 6 MB left on 2026-10-21 -- are
// read-only data inside this library, mapped from the file on demand. After a cold start of the process -- a reboot, an
// update, memory pressure that dropped the file from the page cache -- the
// first detection page-faults them in from storage one page at a time, on the
// synthesis thread, while the screen reader waits for the first word.
//
// THREE KERNEL HINTS, ALL ON ONE WORKER THREAD OF OUR OWN, NONE ON THE SPEAKING
// PATH, NO CLOCK:
//
//  1. At library load (JNI_OnLoad -- the system binding the engine, before any
//     text exists): madvise(MADV_WILLNEED) over this object's read-only
//     segments, in 128 KB pieces. For a file mapping that is the kernel's
//     readahead: it queues the reads and returns. The kernel reads at most one
//     readahead window per request (force_page_cache_ra caps it at
//     max(io_pages, ra_pages)), so one call over a whole segment read only its
//     first part; 128 KB is the smallest window a device configures.
//
//  2. Then MADV_POPULATE_READ (Linux 5.14+, value 22) over the same pieces: the
//     page TABLES are filled too, so the first detection does not even take a
//     minor fault per page. An older kernel answers EINVAL and step 1 stands.
//
//  3. At every utterance start (nativeRewarmTables, one mutex + notify on the
//     synthesis thread): the worker asks mincore() whether the tables are
//     still in the page cache, and reads back only the pieces the kernel has
//     dropped since (memory pressure while the phone sat idle). A resident
//     check is one syscall per segment.
//
// Nothing is detected, copied or pinned (the page cache stays reclaimable), and
// if any step is refused the library behaves exactly as before.
// Proof: tools/verify/warmtables/run.sh.
#pragma once
#include <link.h>
#include <dlfcn.h>
#include <sys/mman.h>
#include <unistd.h>
#include <pthread.h>
#include <stdint.h>
#include <stddef.h>
#include <vector>

#ifndef MADV_POPULATE_READ
#define MADV_POPULATE_READ 22
#endif

namespace evwarm {

struct Segment { uintptr_t start, end; };
static const uintptr_t kPiece = 128 * 1024;
static std::vector<Segment> g_segments;      // written once by the worker before it serves requests
static pthread_mutex_t g_lock = PTHREAD_MUTEX_INITIALIZER;
static pthread_cond_t g_wake = PTHREAD_COND_INITIALIZER;
static bool g_rewarmWanted = false;
static bool g_started = false;

struct Find { uintptr_t addr; std::vector<Segment>* out; };

static int collectIfOwner(struct dl_phdr_info* info, size_t, void* data) {
    Find* f = static_cast<Find*>(data);
    bool owner = false;
    for (int i = 0; i < info->dlpi_phnum; i++) {
        const ElfW(Phdr)& ph = info->dlpi_phdr[i];
        if (ph.p_type != PT_LOAD) continue;
        uintptr_t s = info->dlpi_addr + ph.p_vaddr;
        if (f->addr >= s && f->addr < s + ph.p_memsz) { owner = true; break; }
    }
    if (!owner) return 0;
    long pageSize = sysconf(_SC_PAGESIZE);
    if (pageSize <= 0) return 1;
    uintptr_t mask = static_cast<uintptr_t>(pageSize) - 1;
    for (int i = 0; i < info->dlpi_phnum; i++) {
        const ElfW(Phdr)& ph = info->dlpi_phdr[i];
        // The read-only segments: the code and the tables. A writable one is
        // copied on write and holds nothing the detector reads cold.
        if (ph.p_type != PT_LOAD || (ph.p_flags & PF_W) || ph.p_filesz == 0) continue;
        uintptr_t s = (info->dlpi_addr + ph.p_vaddr) & ~mask;
        uintptr_t e = (info->dlpi_addr + ph.p_vaddr + ph.p_filesz + mask) & ~mask;
        f->out->push_back({s, e});
    }
    return 1;   // found: stop the walk
}

// The read-only segments of the object containing `anyAddress`.
static std::vector<Segment> segmentsOf(const void* anyAddress) {
    std::vector<Segment> out;
    Find f{ reinterpret_cast<uintptr_t>(anyAddress), &out };
    dl_iterate_phdr(collectIfOwner, &f);
    return out;
}

// Reads [s, e) in: readahead for every piece first (the reads queue up in
// parallel), then the page tables filled where the kernel can.
static size_t readIn(uintptr_t s, uintptr_t e) {
    size_t advised = 0;
    for (uintptr_t a = s; a < e; a += kPiece) {
        uintptr_t n = (e - a < kPiece) ? e - a : kPiece;
        if (madvise(reinterpret_cast<void*>(a), n, MADV_WILLNEED) == 0) advised += n;
    }
    for (uintptr_t a = s; a < e; a += kPiece) {
        uintptr_t n = (e - a < kPiece) ? e - a : kPiece;
        if (madvise(reinterpret_cast<void*>(a), n, MADV_POPULATE_READ) != 0) break;   // EINVAL: an older kernel
    }
    return advised;
}

// Advises every read-only segment of the object that contains `anyAddress`.
// Answers how many bytes were advised (0 if nothing was).
static size_t adviseOwnImage(const void* anyAddress) {
    size_t advised = 0;
    for (const Segment& g : segmentsOf(anyAddress)) advised += readIn(g.start, g.end);
    return advised;
}

// Reads back the pieces the kernel dropped. Answers how many pieces it read.
static size_t rewarmEvicted() {
    long pageSize = sysconf(_SC_PAGESIZE);
    if (pageSize <= 0) return 0;
    size_t pieces = 0;
    std::vector<unsigned char> resident;
    for (const Segment& g : g_segments) {
        size_t pages = (g.end - g.start) / static_cast<size_t>(pageSize);
        resident.assign(pages, 0);
        if (mincore(reinterpret_cast<void*>(g.start), g.end - g.start, resident.data()) != 0) continue;
        size_t perPiece = kPiece / static_cast<size_t>(pageSize);
        if (perPiece == 0) perPiece = 1;
        for (size_t p = 0; p < pages; p += perPiece) {
            size_t last = (p + perPiece < pages) ? p + perPiece : pages;
            bool missing = false;
            for (size_t q = p; q < last; q++) if (!(resident[q] & 1)) { missing = true; break; }
            if (!missing) continue;
            uintptr_t a = g.start + p * static_cast<size_t>(pageSize);
            uintptr_t b = g.start + last * static_cast<size_t>(pageSize);
            readIn(a, b);
            pieces++;
        }
    }
    return pieces;
}

static void* worker(void* anyAddress) {
    std::vector<Segment> segs = segmentsOf(anyAddress);
    for (const Segment& g : segs) readIn(g.start, g.end);
    pthread_mutex_lock(&g_lock);
    g_segments = segs;
    for (;;) {
        while (!g_rewarmWanted) pthread_cond_wait(&g_wake, &g_lock);
        g_rewarmWanted = false;
        pthread_mutex_unlock(&g_lock);
        rewarmEvicted();
        pthread_mutex_lock(&g_lock);
    }
    return nullptr;
}

// Fire and forget, on a detached thread that then waits for rewarm requests.
static void startAdvisingOwnImage(const void* anyAddress) {
    pthread_mutex_lock(&g_lock);
    bool already = g_started;
    g_started = true;
    pthread_mutex_unlock(&g_lock);
    if (already) return;
    pthread_attr_t attr;
    if (pthread_attr_init(&attr) != 0) return;
    pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
    pthread_t t;
    pthread_create(&t, &attr, worker, const_cast<void*>(anyAddress));
    pthread_attr_destroy(&attr);
}

// An utterance is starting: have the worker check the tables are still in.
static void requestRewarm() {
    pthread_mutex_lock(&g_lock);
    g_rewarmWanted = true;
    pthread_cond_signal(&g_wake);
    pthread_mutex_unlock(&g_lock);
}

}  // namespace evwarm
