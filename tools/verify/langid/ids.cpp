// langid.h's preprocessing against MediaPipe's own C++, bit for bit.
//
// Everything the model's ids depend on is compared with the upstream code in
// tools/langid/mediapipe/, compiled next to it (run.sh supplies four one-line
// headers upstream includes but does not ship with the op):
//   - utf_isalpharune / utf_tolowerrune / utf_runetochar at every code point
//   - utf_charntorune on every byte sequence of 1 to 3 bytes, every 4-byte one
//     that starts F0..FF with bytes 7F..C0 after it, and random longer ones
//   - MurmurHash64WithSeed on random buffers and seeds
//   - LowercaseUnicodeStr + Tokenize + GetNGramHashIndices' ids on every line
//     of the files given and on random text (letters of many scripts, digits,
//     punctuation, emoji, NUL, ill-formed bytes, the letters whose lowercase is
//     longer or shorter), skipping only what MediaPipe cannot take at all: an
//     incomplete sequence at the very end (its lowercasing loops forever).
//     Where the lowercase is SHORTER than the input MediaPipe reads past its
//     string's end; it is given the zeros langid.h assumes there, in bounds.
#include <cstdio>
#include <cstring>
#include <random>
#include <string>
#include <vector>
#include "langid.h"
#include "mediapipe/tasks/cc/text/language_detector/custom_ops/utils/hash/murmur.h"
#include "mediapipe/tasks/cc/text/language_detector/custom_ops/utils/ngram_hash_ops_utils.h"
#include "mediapipe/tasks/cc/text/language_detector/custom_ops/utils/utf/utf.h"

namespace mp = mediapipe::tasks::text::language_detector::custom_ops;

static long fails = 0;
#define CHECK(cond, ...) do { if (!(cond)) { if (fails++ < 20) { fprintf(stderr, "  MISMATCH: " __VA_ARGS__); fputc('\n', stderr); } } } while (0)

static void decodeBoth(const unsigned char* s, int n) {
    int32_t a; Rune b;
    int na = lidDecode(s, (size_t)n, &a);
    int nb = utf_charntorune(&b, (const char*)s, n);
    CHECK(na == nb && a == b, "decode %d bytes %02X %02X %02X %02X: ours %d/%X theirs %d/%X",
          n, s[0], n > 1 ? s[1] : 0, n > 2 ? s[2] : 0, n > 3 ? s[3] : 0, na, a, nb, b);
}

static long texts = 0, skippedCut = 0, shrunk = 0;

static void tokensBoth(const std::string& s) {
    for (size_t i = 0; i < s.size();) {   // MediaPipe would loop forever
        int32_t r;
        int used = lidDecode((const unsigned char*)s.data() + i, s.size() - i, &r);
        if (used == 0) { skippedCut++; return; }
        i += used;
    }
    std::string lower;
    mp::LowercaseUnicodeStr(s.data(), (int)s.size(), &lower);
    if (lower.size() < s.size()) { shrunk++; lower.resize(s.size(), '\0'); }
    mp::TokenizedOutput up = mp::Tokenize(lower.c_str(), (int)s.size(), kLidMaxSplits, true);
    LidTokens t;
    lidTokenize(s.data(), s.size(), &t);
    texts++;
    bool same = (int)up.tokens.size() == t.count && up.str == std::string(t.str, (size_t)t.start[t.count]);
    for (int k = 0; same && k < t.count; k++)
        same = up.tokens[k].first == (size_t)t.start[k] && up.tokens[k].second == (size_t)(t.start[k + 1] - t.start[k]);
    CHECK(same, "tokens of a %zu-byte text: ours %d theirs %zu", s.size(), t.count, up.tokens.size());
    if (!same) return;
    for (int n = 0; n < 4; n++) {
        for (int st = 0; st < t.count; st++) {
            size_t bytes = 0;
            for (int i = st; i < t.count && i < st + kLidNgramLength[n]; i++) bytes += up.tokens[i].second;
            int theirs = (int)(mp::hash::MurmurHash64WithSeed(up.str.c_str() + up.tokens[st].first, bytes, kLidSeed) % kLidVocab[n]) + 1;
            int end = st + kLidNgramLength[n] < t.count ? st + kLidNgramLength[n] : t.count;
            int ours = (int)(lidMurmur(t.str + t.start[st], (size_t)(t.start[end] - t.start[st]), kLidSeed) % (uint64_t)kLidVocab[n]) + 1;
            CHECK(ours == theirs, "id n=%d at %d: ours %d theirs %d", n + 1, st, ours, theirs);
        }
    }
}

static void put(std::string& s, int32_t c) { char b[4]; s.append(b, (size_t)lidEncode(c, b)); }

int main(int argc, char** argv) {
    long points = 0;
    for (int32_t c = 0; c <= 0x10FFFF + 64; c++, points++) {
        CHECK(lidIsAlpha(c) == (utf_isalpharune(c) != 0), "isalpha %X", c);
        CHECK(lidToLower(c) == utf_tolowerrune(c), "tolower %X", c);
        char a[4], b[4];
        Rune r = c;
        int na = lidEncode(c, a), nb = utf_runetochar(b, &r);
        CHECK(na == nb && !memcmp(a, b, (size_t)na), "encode %X", c);
    }
    printf("rune tables + encoder: %ld code points\n", points);

    unsigned char s[8] = {0};
    long seqs = 0;
    for (int a = 0; a < 256; a++) {
        s[0] = (unsigned char)a; decodeBoth(s, 1); seqs++;
        for (int b = 0; b < 256; b++) {
            s[1] = (unsigned char)b; decodeBoth(s, 2); seqs++;
            for (int c = 0; c < 256; c++) { s[2] = (unsigned char)c; decodeBoth(s, 3); seqs++; }
        }
    }
    for (int a = 0xF0; a < 256; a++)
        for (int b = 0x7F; b <= 0xC0; b++)
            for (int c = 0x7F; c <= 0xC0; c++)
                for (int d = 0x7F; d <= 0xC0; d++) {
                    s[0] = (unsigned char)a; s[1] = (unsigned char)b; s[2] = (unsigned char)c; s[3] = (unsigned char)d;
                    decodeBoth(s, 4); seqs++;
                }
    std::mt19937_64 rng(20261020);
    for (int i = 0; i < 4000000; i++) {
        int n = 4 + (int)(rng() % 5);
        for (int k = 0; k < n; k++) s[k] = (unsigned char)rng();
        decodeBoth(s, n); seqs++;
    }
    printf("decoder: %ld byte sequences\n", seqs);

    long hashes = 0;
    std::vector<char> buf(80);
    for (int i = 0; i < 1000000; i++, hashes++) {
        size_t n = rng() % 72;
        for (size_t k = 0; k < n; k++) buf[k] = (char)rng();
        uint64_t seed = i % 2 ? kLidSeed : rng();
        CHECK(lidMurmur(buf.data(), n, seed) == mp::hash::MurmurHash64WithSeed(buf.data(), n, seed), "murmur %zu bytes", n);
    }
    printf("murmur: %ld buffers\n", hashes);

    long lines = 0;
    for (int f = 1; f < argc; f++) {
        FILE* in = fopen(argv[f], "rb");
        if (!in) { fprintf(stderr, "cannot read %s\n", argv[f]); return 2; }
        std::string all;
        char chunk[65536];
        size_t got;
        while ((got = fread(chunk, 1, sizeof chunk, in)) > 0) all.append(chunk, got);
        fclose(in);
        size_t at = 0;
        while (at < all.size()) {
            size_t nl = all.find('\n', at);
            if (nl == std::string::npos) nl = all.size();
            if (nl > at) { tokensBoth(all.substr(at, nl - at)); lines++; }
            at = nl + 1;
        }
    }

    // Random text. Weighted pools of code points, and raw bytes MediaPipe's
    // decoder must survive. U+023A / U+023E lowercase to 3 bytes from 2.
    static const struct { int32_t lo, hi; int weight; } kPools[] = {
        {'a', 'z', 30}, {'A', 'Z', 8}, {'0', '9', 4}, {' ', ' ', 12}, {'!', '/', 4}, {':', '@', 2},
        {0x00, 0x1F, 1}, {0xA0, 0x24F, 6}, {0x250, 0x36F, 2}, {0x370, 0x3FF, 3}, {0x400, 0x52F, 5},
        {0x530, 0x58F, 1}, {0x590, 0x5FF, 2}, {0x600, 0x6FF, 6}, {0x700, 0x8FF, 1}, {0x900, 0x97F, 8},
        {0x980, 0xDFF, 8}, {0xE00, 0xEFF, 2}, {0xF00, 0x10FF, 2}, {0x1100, 0x1FFF, 3}, {0x2000, 0x2BFF, 3},
        {0x2C00, 0x2C7F, 1}, {0x3000, 0x30FF, 3}, {0x3400, 0x9FFF, 4}, {0xA000, 0xA7FF, 1}, {0xAC00, 0xD7A3, 3},
        {0xF900, 0xFFFF, 2}, {0x10000, 0x1F2FF, 1}, {0x1F300, 0x1FAFF, 2}, {0x20000, 0x10FFFF, 1},
        {0x23A, 0x23A, 1}, {0x23E, 0x23E, 1},
    };
    int total = 0;
    for (auto& p : kPools) total += p.weight;
    long randoms = 0;
    for (int i = 0; i < 300000; i++, randoms++) {
        std::string t;
        int n = 1 + (int)(rng() % (i % 4 ? 40 : 300));
        int focus = (int)(rng() % (sizeof kPools / sizeof *kPools));   // most texts lean on one pool
        for (int k = 0; k < n; k++) {
            if (rng() % 200 == 0) {   // an ill-formed byte or a surrogate, as bytes
                static const char* const kBad[] = {"\x80", "\xBF", "\xC0\x80", "\xC1", "\xE0\x80\x80", "\xED\xA0\x80",
                                                   "\xF4\x90\x80\x80", "\xF8", "\xFF", "\xE2\x28\xA1"};
                t += kBad[rng() % (sizeof kBad / sizeof *kBad)];
                continue;
            }
            int pick = rng() % 3 ? focus : -1;
            if (pick < 0) {
                int w = (int)(rng() % (uint64_t)total);
                for (pick = 0; w >= kPools[pick].weight; pick++) w -= kPools[pick].weight;
            }
            put(t, kPools[pick].lo + (int32_t)(rng() % (uint64_t)(kPools[pick].hi - kPools[pick].lo + 1)));
        }
        if (i % 500 == 0) t += "\xE0\xA4";   // cut off at the end: counted as skipped
        tokensBoth(t);
    }
    printf("tokens + ids: %ld texts compared (%ld lines, %ld random; %ld with a shorter lowercase); skipped %ld cut off at the end\n",
           texts, lines, randoms, shrunk, skippedCut);
    if (fails) { printf("FAILED: %ld mismatches\n", fails); return 1; }
    printf("ids: all identical to MediaPipe's C++\n");
    return 0;
}
