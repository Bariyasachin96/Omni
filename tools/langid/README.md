# tools/langid

The language identifier the native core runs (`app/src/main/cpp/langid.h`) is
the model of ML Kit's language identification, computed in C++ with no
TensorFlow Lite and no network. What is here, and where it came from:

| file | what | from |
|---|---|---|
| `language_detector.tflite` | the model, 315,294 bytes, sha256 `7db4f23d...e5417` | MediaPipe Language Detector, `https://storage.googleapis.com/mediapipe-models/language_detector/language_detector/float32/latest/language_detector.tflite`, Apache License 2.0 (model card: Learn2Compress team, Google Research) |
| `mediapipe/*.cc, *.c, *.h` | the reference: NGramHash, KmeansEmbeddingLookup, Murmur hash, Plan 9 rune tables | `google-ai-edge/mediapipe` at `4970c6a4926b`, `mediapipe/tasks/cc/text/language_detector/`, Apache License 2.0 (`mediapipe/LICENSE`) |
| `gen_model.py` | writes `app/src/main/cpp/langid_model.h` from the two above; `--check` fails if it is stale | ours |
| `han_sets.txt`, `gen_han.py` | which of GB 2312 (S), CNS 11643 planes 1-2 (T) and JIS X 0208 (J) hold each Han character -> `langhan.h`, the Chinese/Japanese rules | Unihan 18.0.0 IRG sources (`gen_han.py --extract Unihan_IRGSources.txt`), Unicode License v3 |
| `nb_model.tsv`, `letters.tsv`, `letters_rare.tsv`, `train_nb.py` | the word-and-ending model: per script, the 20,000 most informative words and word endings with how many of each language's learning texts held them, the letters one language writes and another practically never does (1 in 50,000: they decide), and the looser ones (1 in 2,000: they only keep the context away, `train_nb.py letters`) -> `langnb.h` | learned (`train_nb.py learn`) from Wikipedia (CC BY-SA 4.0), Tatoeba (CC BY 2.0 FR), Common Voice (CC0), AOSP UI strings (Apache 2.0) and the UDHR, fetched by `fetch_train.sh`; FLORES-200 (CC BY-SA 4.0) is read only as test text |

**It is ML Kit's model.** `com.google.mlkit:language-id` 17.0.6 bundles the same
graph with the same 29 tensors, byte for byte, and the same 111 labels; its own
native library carries the same NGramHash / KmeansEmbeddingLookup code
(`learning/expander/pod/deep_pod/tflite_handlers/`, the same error strings).

**The proof is `tools/verify/langid/run.sh`:** the token ids bit for bit
against the C++ above, and all 111 probabilities of 58,770 texts in 388
languages against **ML Kit's own library** (its x86_64 build, run on glibc
through a shim): within 1e-5 everywhere, the same top label and the same
decision everywhere. The few things ML Kit's arithmetic does that are not the
obvious reading of TensorFlow Lite's source are written down in `langid.h`.
