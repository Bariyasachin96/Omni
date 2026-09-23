# ---------------------------------------------------------- shrinking
# R8 full mode is on, and gradle.properties now SAYS so rather than
# relying on the AGP default. Let R8 widen access so it can inline and
# merge across class boundaries.
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
# JNI resolves by symbol name (Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_*),
# so these native methods must keep theirs. They are @JvmStatic members of the
# service's companion, which puts them on EasyVoiceTtsService itself -- there is
# deliberately no NativeEngine class any more (owner, 2026-09-09), because a
# class holding native methods cannot be renamed or merged away and so always
# showed up in a decompile. Nothing else in the app is reached by name: the only
# two reflection sites read framework names (TextToSpeech.mCurrentEngine,
# android resource ids).
#
# The service itself is already kept by the manifest, so this rule is only about
# the METHOD names.
-keepclassmembers class com.sachinbaria.easyvoice.EasyVoiceTtsService {
    native <methods>;
}

# --------------------------------------------------- Kotlin null checks
# Every public Kotlin function with a non-null parameter compiles to a
# leading Intrinsics.checkNotNullParameter(...) -- a call plus the
# parameter-name string constant, in EVERY such method in the app. They
# exist to turn "Java handed Kotlin a null" into a clear exception at the
# boundary, and R8 does not drop them on its own even in full mode.
#
# They are removable HERE because nothing can hand this app a null through
# a non-null parameter:
#   * the framework entry points all declare their parameters NULLABLE
#     (onSynthesizeText(request: SynthesisRequest?, callback: SynthesisCallback?),
#     onLoadLanguage(langCode: String?, ...)), so no check is generated for
#     them in the first place;
#   * the JNI boundary is the other place a null could arrive, and every
#     entry point in tts_engine_core.cpp returns NewStringUTF/NewObjectArray
#     and can never return null -- and every Kotlin call into it is inside a
#     try/catch anyway.
# What it costs: a null arriving from somewhere unforeseen would NPE further
# in rather than at the parameter. That is the whole trade.
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkNotNullParameter(java.lang.Object, java.lang.String);
    static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    static void checkNotNull(java.lang.Object, java.lang.String);
}

# ------------------------------------- android.util.Log is NOT stripped
# The obvious next line would be -assumenosideeffects on android.util.Log,
# and it is WRONG here. Measured before deciding: the app calls it at five
# sites, but EasyVoiceLogger calls it too -- Log.e/Log.w for logcat AND
# Log.getStackTraceString, which is what puts a stack trace INTO
# easy_voice.log, the file the owner shares when reporting a bug. Stripping
# it would delete that trace from the log file, and would save nothing worth
# having: the message strings are built by our own code and handed to
# EasyVoiceLogger, so they stay in the APK either way. Do not add it.
