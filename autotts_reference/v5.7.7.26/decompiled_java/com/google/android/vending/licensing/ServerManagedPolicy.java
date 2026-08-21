/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.google.android.vending.licensing;

import android.content.Context;
import android.util.Log;
import b3.c;
import com.google.android.vending.licensing.Obfuscator;
import com.google.android.vending.licensing.Policy;
import com.google.android.vending.licensing.PreferenceObfuscator;
import com.google.android.vending.licensing.ResponseData;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ServerManagedPolicy
implements Policy {
    public long a;
    public long b;
    public long c;
    public long d;
    public long e = 0L;
    public int f;
    public String g;
    public PreferenceObfuscator h;

    public ServerManagedPolicy(Context object, Obfuscator obfuscator) {
        object = new PreferenceObfuscator(object.getSharedPreferences("com.google.android.vending.licensing.ServerManagedPolicy", 0), obfuscator);
        this.h = object;
        this.f = Integer.parseInt(((PreferenceObfuscator)object).b("lastResponse", Integer.toString(291)));
        this.a = Long.parseLong(this.h.b("validityTimestamp", "0"));
        this.b = Long.parseLong(this.h.b("retryUntil", "0"));
        this.c = Long.parseLong(this.h.b("maxRetries", "0"));
        this.d = Long.parseLong(this.h.b("retryCount", "0"));
        this.g = this.h.b("licensingUrl", null);
    }

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
            b3.c.a(uRI, hashMap);
            return hashMap;
        }
        catch (URISyntaxException uRISyntaxException) {
            Log.w((String)"ServerManagedPolicy", (String)"Invalid syntax error while decoding extras data from server.");
            return hashMap;
        }
    }

    private void d(int n3) {
        this.e = System.currentTimeMillis();
        this.f = n3;
        this.h.c("lastResponse", Integer.toString(n3));
    }

    private void e(String string) {
        this.g = string;
        this.h.c("licensingUrl", string);
    }

    private void f(String string) {
        Long l3;
        try {
            l3 = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            Log.w((String)"ServerManagedPolicy", (String)"Licence retry count (GR) missing, grace period disabled");
            l3 = 0L;
            string = "0";
        }
        this.c = l3;
        this.h.c("maxRetries", string);
    }

    private void g(long l3) {
        this.d = l3;
        this.h.c("retryCount", Long.toString(l3));
    }

    private void h(String string) {
        Long l3;
        try {
            l3 = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            Log.w((String)"ServerManagedPolicy", (String)"License retry timestamp (GT) missing, grace period disabled");
            l3 = 0L;
            string = "0";
        }
        this.b = l3;
        this.h.c("retryUntil", string);
    }

    private void i(String string) {
        Long l3;
        try {
            l3 = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            Log.w((String)"ServerManagedPolicy", (String)"License validity timestamp (VT) missing, caching for a minute");
            long l4 = System.currentTimeMillis() + 60000L;
            string = Long.toString(l4);
            l3 = l4;
        }
        this.a = l3;
        this.h.c("validityTimestamp", string);
    }

    @Override
    public boolean a() {
        long l3 = System.currentTimeMillis();
        int n3 = this.f;
        if (n3 == 256) {
            if (l3 <= this.a) {
                return true;
            }
        } else if (n3 == 291 && l3 < this.e + 60000L) {
            return l3 <= this.b || this.d <= this.c;
            {
            }
        }
        return false;
    }

    @Override
    public void b(int n3, ResponseData object) {
        if (n3 != 291) {
            this.g(0L);
        } else {
            this.g(this.d + 1L);
        }
        object = this.c((ResponseData)object);
        if (n3 == 256) {
            this.f = n3;
            this.e(null);
            this.i((String)object.get("VT"));
            this.h((String)object.get("GT"));
            this.f((String)object.get("GR"));
        } else if (n3 == 561) {
            this.i("0");
            this.h("0");
            this.f("0");
            this.e((String)object.get("LU"));
        }
        this.d(n3);
        this.h.a();
    }
}

