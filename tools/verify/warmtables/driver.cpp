// See run.sh.
#include <dlfcn.h>
#include <link.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <unistd.h>
#include <chrono>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <thread>
#include <vector>

struct Seg { uintptr_t s, e; };
static std::vector<Seg> segs;
static const char* soPath;

static int collect(struct dl_phdr_info* info, size_t, void*) {
    if (!info->dlpi_name || !strstr(info->dlpi_name, "libeasyvoice.so")) return 0;
    long ps = sysconf(_SC_PAGESIZE); uintptr_t m = ps - 1;
    for (int i = 0; i < info->dlpi_phnum; i++) {
        const ElfW(Phdr)& ph = info->dlpi_phdr[i];
        if (ph.p_type != PT_LOAD || (ph.p_flags & PF_W) || ph.p_filesz == 0) continue;
        uintptr_t s = (info->dlpi_addr + ph.p_vaddr) & ~m;
        uintptr_t e = (info->dlpi_addr + ph.p_vaddr + ph.p_filesz + m) & ~m;
        segs.push_back({s, e});
    }
    return 1;
}
static long resident(long* total) {
    long ps = sysconf(_SC_PAGESIZE), r = 0; *total = 0;
    for (auto& g : segs) {
        size_t n = (g.e - g.s) / ps; std::vector<unsigned char> v(n);
        if (mincore((void*)g.s, g.e - g.s, v.data()) != 0) { perror("mincore"); exit(2); }
        for (auto c : v) r += (c & 1);
        *total += n;
    }
    return r;
}
static double touchAll() {
    long ps = sysconf(_SC_PAGESIZE); volatile unsigned long sum = 0;
    auto t0 = std::chrono::steady_clock::now();
    for (auto& g : segs) for (uintptr_t a = g.s; a < g.e; a += ps) sum += *(volatile const unsigned char*)a;
    return std::chrono::duration<double, std::milli>(std::chrono::steady_clock::now() - t0).count();
}
static void evict() {
    int fd = open(soPath, O_RDONLY); if (fd < 0) { perror("open"); exit(2); }
    fdatasync(fd); posix_fadvise(fd, 0, 0, POSIX_FADV_DONTNEED); close(fd);
}

// One round per PROCESS: a library that dlclose cannot unload (libstdc++'s
// unique symbols make it NODELETE) would stay mapped and resident otherwise.
//   driver <lib> cold|advised   prints: mode before resident total ms
int main(int argc, char** argv) {
    soPath = argv[1];
    bool advised = strcmp(argv[2], "advised") == 0;
    long total = 0;
    evict();
    void* h = dlopen(soPath, RTLD_NOW | RTLD_LOCAL);
    if (!h) { fprintf(stderr, "dlopen: %s\n", dlerror()); return 2; }
    dl_iterate_phdr(collect, nullptr);
    if (segs.empty()) { fprintf(stderr, "no read-only segments found\n"); return 2; }
    long before = resident(&total);
    if (advised) {
        typedef int (*OnLoad)(void*, void*);
        OnLoad onLoad = (OnLoad)dlsym(h, "JNI_OnLoad");
        if (!onLoad) { fprintf(stderr, "no JNI_OnLoad\n"); return 2; }
        onLoad(nullptr, nullptr);
        // the harness waits for the readahead to land (the app never does)
        long last = -1, now = before;
        for (int i = 0; i < 300 && now != last; i++) {
            last = now; std::this_thread::sleep_for(std::chrono::milliseconds(10)); now = resident(&total);
        }
    }
    long res = resident(&total);
    double ms = touchAll();
    printf("%s %ld %ld %ld %.3f\n", advised ? "advised" : "cold", before, res, total, ms);
    return 0;
}
