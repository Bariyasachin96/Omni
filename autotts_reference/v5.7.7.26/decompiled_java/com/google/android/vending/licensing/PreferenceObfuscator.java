/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.util.Log
 */
package com.google.android.vending.licensing;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.vending.licensing.Obfuscator;
import com.google.android.vending.licensing.ValidationException;

public class PreferenceObfuscator {
    public final SharedPreferences a;
    public final Obfuscator b;
    public SharedPreferences.Editor c;

    public PreferenceObfuscator(SharedPreferences sharedPreferences, Obfuscator obfuscator) {
        this.a = sharedPreferences;
        this.b = obfuscator;
        this.c = null;
    }

    public void a() {
        SharedPreferences.Editor editor = this.c;
        if (editor != null) {
            editor.commit();
            this.c = null;
        }
    }

    public String b(String string, String string2) {
        String string3 = this.a.getString(string, null);
        if (string3 != null) {
            try {
                string3 = this.b.b(string3, string);
                return string3;
            }
            catch (ValidationException validationException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Validation error while reading preference: ");
                stringBuilder.append(string);
                Log.w((String)"PreferenceObfuscator", (String)stringBuilder.toString());
            }
        }
        return string2;
    }

    public void c(String string, String string2) {
        if (this.c == null) {
            this.c = this.a.edit();
        }
        string2 = this.b.a(string2, string);
        this.c.putString(string, string2);
    }
}

