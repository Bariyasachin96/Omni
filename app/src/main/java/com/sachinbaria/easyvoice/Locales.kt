package com.sachinbaria.easyvoice

import java.util.Locale

// EVERY LOCALE THE APP BUILDS GOES THROUGH HERE.
//
// One call for every Android version (owner, 2026-09-24: no version branches
// where one call does the job). Locale.of is API 36 only, while the
// three-argument constructor exists on every Android this app runs on and
// builds the identical object -- both go through BaseLocale.getInstance with
// the same old-ISO-code conversion. The constructor is only deprecated by Java
// 19, so the warning is silenced here, in the one place that calls it.
@Suppress("DEPRECATION")
fun localeOf(language: String, country: String = "", variant: String = ""): Locale =
    Locale(language, country, variant)
