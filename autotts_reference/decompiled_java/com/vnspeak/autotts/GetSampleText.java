/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.vnspeak.autotts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import c3.y;
import java.util.Locale;

public class GetSampleText
extends Activity {
    public static Locale a(Intent object) {
        if (object != null && (object = object.getStringExtra("language")) != null) {
            return new Locale((String)object);
        }
        return Locale.getDefault();
    }

    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        Locale locale = GetSampleText.a(this.getIntent());
        String string = y.a(locale.getISO3Language());
        object = string;
        if (string.isEmpty()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Sorry. Sample text for language ");
            ((StringBuilder)object).append(locale.getDisplayName(new Locale("eng")));
            ((StringBuilder)object).append(" is missing.");
            object = ((StringBuilder)object).toString();
        }
        string = new Intent();
        string.putExtra("sampleText", (String)object);
        this.setResult(0, (Intent)string);
        this.finish();
    }
}

