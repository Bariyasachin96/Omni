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
import c3.n;
import java.util.ArrayList;

public class CheckVoiceData
extends Activity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ArrayList arrayList = n.i(null, true);
        bundle = new Intent();
        bundle.putStringArrayListExtra("availableVoices", arrayList);
        this.setResult(1, (Intent)bundle);
        this.finish();
    }
}

