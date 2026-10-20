// langid.h's probabilities for every line of a file: float32 [lines][111] to
// argv[2], the labels one per line to argv[3], and to argv[4] one byte per
// line, 1 where the lowercase is shorter than the text (MediaPipe then reads
// past its string's end). oracle.py compares them with MediaPipe's
// LanguageDetector. Also times lidClassify over the whole file.
#include <chrono>
#include <cstdio>
#include <string>
#include <vector>
#include "langid.h"

int main(int argc, char** argv) {
    if (argc != 5) { fprintf(stderr, "usage: probs corpus.txt probs.f32 labels.txt shrunk.u8\n"); return 2; }
    FILE* in = fopen(argv[1], "rb");
    if (!in) { fprintf(stderr, "cannot read %s\n", argv[1]); return 2; }
    std::string all;
    char chunk[65536];
    size_t got;
    while ((got = fread(chunk, 1, sizeof chunk, in)) > 0) all.append(chunk, got);
    fclose(in);
    std::vector<std::string> lines;
    for (size_t at = 0; at < all.size();) {
        size_t nl = all.find('\n', at);
        if (nl == std::string::npos) nl = all.size();
        lines.push_back(all.substr(at, nl - at));
        at = nl + 1;
    }
    std::vector<float> out(lines.size() * EV_LID_CLASSES, -1.0f);
    auto t0 = std::chrono::steady_clock::now();
    for (size_t i = 0; i < lines.size(); i++)
        lidClassify(lines[i].data(), lines[i].size(), &out[i * EV_LID_CLASSES]);
    double us = std::chrono::duration<double, std::micro>(std::chrono::steady_clock::now() - t0).count();
    FILE* f = fopen(argv[2], "wb");
    fwrite(out.data(), sizeof(float), out.size(), f);
    fclose(f);
    f = fopen(argv[3], "w");
    for (int k = 0; k < EV_LID_CLASSES; k++) fprintf(f, "%s\n", kLidLabels[k]);
    fclose(f);
    f = fopen(argv[4], "wb");
    for (const std::string& line : lines) {
        size_t lowered = 0;
        for (size_t i = 0; i < line.size();) {
            int32_t r;
            int used = lidDecode((const unsigned char*)line.data() + i, line.size() - i, &r);
            if (used == 0) break;
            i += used;
            char enc[4];
            lowered += (size_t)lidEncode(lidIsAlpha(r) ? lidToLower(r) : r, enc);
        }
        fputc(lowered < line.size(), f);
    }
    fclose(f);
    printf("ours: %zu texts, %.1f us each on this machine\n", lines.size(), us / (lines.empty() ? 1 : lines.size()));
    return 0;
}
