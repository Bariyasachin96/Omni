package com.sachinbaria.easyvoice

import android.os.Build
import java.util.Locale

// EVERY LOCALE THE APP BUILDS GOES THROUGH HERE (2026-09-23).
//
// The Locale CONSTRUCTORS are deprecated from Java 19, and the API 37
// android.jar marks them so -- they were the bulk of the Kotlin compiler's
// warnings. Their replacement, Locale.of, is API 36 (the SDK's own
// api-versions.xml: since="36"), and minSdk is 24, so no single call can be
// used everywhere. This picks the new one where it exists and the old one
// below it, and nothing else in the app names either.
//
// It changes which method runs on Android 16 and later and NOTHING about the
// result: both go through BaseLocale.getInstance with the same old-ISO-code
// conversion, and Locale(lang) is itself Locale(lang, "", ""). Detection, the
// stored voice keys and the engine matching all compare the same objects they
// compared before.
fun localeOf(language: String, country: String = "", variant: String = ""): Locale =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) Locale.of(language, country, variant)
    else @Suppress("DEPRECATION") Locale(language, country, variant)
