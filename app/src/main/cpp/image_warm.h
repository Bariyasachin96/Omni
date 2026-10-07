// THE DETECTOR'S TABLES ARE READ IN WHILE NOTHING IS WAITING FOR THEM
// (owner, 2026-10-07: "logically ... performance bahut hi fast").
//
// CLD2's tables are about 6 MB of read-only data inside this library, mapped
// from the file on demand. After a cold start of the process -- a reboot, an
// update, memory pressure that dropped the file from the page cache -- the
// first detection page-faults them in from storage one page at a time, on the
// synthesis thread, while the screen reader waits for the first word. Measured
// on the latency harness the first detection is about four times a warm one.
//
// The library is loaded when the service class initialises, i.e. when the
// system binds the engine -- before any text exists. So at JNI_OnLoad we tell
// the kernel what we will read: madvise(MADV_WILLNEED) over this object's own
// read-only segments. For a file mapping that is the kernel's readahead
// (vfs_fadvise -> force_page_cache_readahead): it queues the reads and returns,
// and the data arrives in the page cache by itself. No detection is run for
// it, no data is copied, nothing is pinned (page cache stays reclaimable), and
// no clock is involved -- the load of the library IS the event. It runs on its
// own short-lived thread so not even the read submission is on the thread that
// loaded us.
//
// Only an advice: if any step is refused the library behaves exactly as before.
// Proof: tools/verify/warmtables/run.sh (residency with and without, and the
// first read of every page timed).
#pragma once
#include <link.h>
#include <dlfcn.h>
#include <sys/mman.h>
#include <unistd.h>
#include <pthread.h>
#include <stdint.h>
#include <stddef.h>

namespace evwarm {

struct Find { uintptr_t addr; size_t advised; };

static int adviseIfOwner(struct dl_phdr_info* info, size_t, void* data) {
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
        // In pieces: the kernel reads at most one readahead window per request
        // (force_page_cache_ra caps it at max(io_pages, ra_pages)), so one call
        // over the whole segment reads only its first few hundred KB. 128 KB is
        // the smallest window a device configures; on one with a larger window
        // this is merely more requests than needed.
        const uintptr_t piece = 128 * 1024;
        for (uintptr_t a = s; a < e; a += piece) {
            uintptr_t n = (e - a < piece) ? e - a : piece;
            if (madvise(reinterpret_cast<void*>(a), n, MADV_WILLNEED) == 0) f->advised += n;
        }
    }
    return 1;   // found: stop the walk
}

// Advises every read-only segment of the object that contains `anyAddress`.
// Answers how many bytes were advised (0 if nothing was).
static size_t adviseOwnImage(const void* anyAddress) {
    Find f{ reinterpret_cast<uintptr_t>(anyAddress), 0 };
    dl_iterate_phdr(adviseIfOwner, &f);
    return f.advised;
}

static void* adviseThread(void* anyAddress) {
    adviseOwnImage(anyAddress);
    return nullptr;
}

// Fire and forget, on a detached thread.
static void startAdvisingOwnImage(const void* anyAddress) {
    pthread_attr_t attr;
    if (pthread_attr_init(&attr) != 0) return;
    pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
    pthread_t t;
    pthread_create(&t, &attr, adviseThread, const_cast<void*>(anyAddress));
    pthread_attr_destroy(&attr);
}

}  // namespace evwarm
