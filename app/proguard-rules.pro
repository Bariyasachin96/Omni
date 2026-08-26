# ---------------------------------------------------------- shrinking
# R8 full mode is on (gradle.properties). Let it widen access so it can
# inline and merge across class boundaries.
-allowaccessmodification
# Move every surviving class into the root package: shorter descriptors.
-repackageclasses ''

# --------------------------------------------------- crash reports
# Keep the line table so a stack trace still carries line numbers, but
# rename the source file to a constant so real paths never ship.
# mapping.txt is kept as a private CI artifact - not in the APK, not in
# the release - so only we can retrace a trace.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ------------------------------------------------------------ keeps
# JNI resolves by symbol name (Java_com_tts_easyvoice_NativeEngine_*),
# so this class and its native methods must keep theirs. Nothing else
# in the app is reached by name: the only two reflection sites read
# framework names (TextToSpeech.mCurrentEngine, android resource ids).
-keep,includedescriptorclasses class com.tts.easyvoice.NativeEngine {
    native <methods>;
}
# kotlinx-coroutines ships this as a consumer rule; repeated so the
# build does not depend on the AAR carrying it.
-keepclassmembers class kotlinx.coroutines.** { volatile <fields>; }
-dontwarn kotlinx.coroutines.**
-dontwarn javax.annotation.**
-dontwarn javax.naming.**
-dontwarn org.checkerframework.**
-dontwarn com.google.auto.value.**
-dontwarn com.google.errorprone.annotations.**
