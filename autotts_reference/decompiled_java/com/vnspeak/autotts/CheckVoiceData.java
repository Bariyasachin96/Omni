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
import c3.m;
import java.util.ArrayList;

public class CheckVoiceData
extends Activity {
    public void onCreate(Bundle object) {
        super.onCreate(object);
        object = m.j(null, true);
        Intent intent = new Intent();
        intent.putStringArrayListExtra("availableVoices", (ArrayList)object);
        this.setResult(1, intent);
        this.finish();
    }
}

