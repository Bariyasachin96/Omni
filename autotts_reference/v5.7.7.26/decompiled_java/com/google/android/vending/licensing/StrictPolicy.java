/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.vending.licensing;

import android.util.Log;
import b3.c;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.ResponseData;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class StrictPolicy
implements Policy {
    public int a = 291;
    public String b = null;

    private Map c(ResponseData responseData) {
        HashMap hashMap = new HashMap();
        if (responseData == null) {
            return hashMap;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("?");
            stringBuilder.append(responseData.g);
            URI uRI = new URI(stringBuilder.toString());
            c.a(uRI, hashMap);
            return hashMap;
        }
        catch (URISyntaxException uRISyntaxException) {
            Log.w((String)"StrictPolicy", (String)"Invalid syntax error while decoding extras data from server.");
            return hashMap;
        }
    }

    @Override
    public boolean a() {
        return this.a == 256;
    }

    @Override
    public void b(int n3, ResponseData responseData) {
        this.a = n3;
        if (n3 == 561) {
            this.b = (String)this.c(responseData).get("LU");
        }
    }
}

