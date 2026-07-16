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
import com.google.android.vending.licensing.PreferenceObfuscator;
import com.google.android.vending.licensing.ResponseData;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class APKExpansionPolicy
implements Policy {
    public long a;
    public long b;
    public long c;
    public long d;
    public long e;
    public int f;
    public String g;
    public PreferenceObfuscator h;
    public Vector i;
    public Vector j;
    public Vector k;

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
            this.j(0L);
        } else {
            this.j(this.d + 1L);
        }
        Map map = this.c((ResponseData)object);
        if (n3 == 256) {
            this.f = n3;
            this.h(null);
            this.l(Long.toString(System.currentTimeMillis() + 60000L));
            for (String string : map.keySet()) {
                if (string.equals("VT")) {
                    this.l((String)map.get(string));
                    continue;
                }
                if (string.equals("GT")) {
                    this.k((String)map.get(string));
                    continue;
                }
                if (string.equals("GR")) {
                    this.i((String)map.get(string));
                    continue;
                }
                if (string.startsWith("FILE_URL")) {
                    this.f(Integer.parseInt(string.substring(8)) - 1, (String)map.get(string));
                    continue;
                }
                if (string.startsWith("FILE_NAME")) {
                    this.d(Integer.parseInt(string.substring(9)) - 1, (String)map.get(string));
                    continue;
                }
                if (!string.startsWith("FILE_SIZE")) continue;
                this.e(Integer.parseInt(string.substring(9)) - 1, Long.parseLong((String)map.get(string)));
            }
        } else if (n3 == 561) {
            this.l("0");
            this.k("0");
            this.i("0");
            this.h((String)map.get("LU"));
        }
        this.g(n3);
        this.h.a();
    }

    public final Map c(ResponseData responseData) {
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
            Log.w((String)"APKExpansionPolicy", (String)"Invalid syntax error while decoding extras data from server.");
            return hashMap;
        }
    }

    public void d(int n3, String string) {
        if (n3 >= this.j.size()) {
            this.j.setSize(n3 + 1);
        }
        this.j.set(n3, string);
    }

    public void e(int n3, long l3) {
        if (n3 >= this.k.size()) {
            this.k.setSize(n3 + 1);
        }
        this.k.set(n3, l3);
    }

    public void f(int n3, String string) {
        if (n3 >= this.i.size()) {
            this.i.setSize(n3 + 1);
        }
        this.i.set(n3, string);
    }

    public final void g(int n3) {
        this.e = System.currentTimeMillis();
        this.f = n3;
        this.h.c("lastResponse", Integer.toString(n3));
    }

    public final void h(String string) {
        this.g = string;
        this.h.c("licensingUrl", string);
    }

    public final void i(String string) {
        Long l3;
        try {
            l3 = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            Log.w((String)"APKExpansionPolicy", (String)"Licence retry count (GR) missing, grace period disabled");
            l3 = 0L;
            string = "0";
        }
        this.c = l3;
        this.h.c("maxRetries", string);
    }

    public final void j(long l3) {
        this.d = l3;
        this.h.c("retryCount", Long.toString(l3));
    }

    public final void k(String string) {
        Long l3;
        try {
            l3 = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            Log.w((String)"APKExpansionPolicy", (String)"License retry timestamp (GT) missing, grace period disabled");
            l3 = 0L;
            string = "0";
        }
        this.b = l3;
        this.h.c("retryUntil", string);
    }

    public final void l(String string) {
        Long l3;
        try {
            l3 = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            Log.w((String)"APKExpansionPolicy", (String)"License validity timestamp (VT) missing, caching for a minute");
            long l4 = System.currentTimeMillis() + 60000L;
            string = Long.toString(l4);
            l3 = l4;
        }
        this.a = l3;
        this.h.c("validityTimestamp", string);
    }
}

