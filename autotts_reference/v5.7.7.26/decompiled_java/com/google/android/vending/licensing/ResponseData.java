/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.vending.licensing;

import android.text.TextUtils;
import java.util.regex.Pattern;

public class ResponseData {
    public int a;
    public int b;
    public String c;
    public String d;
    public String e;
    public long f;
    public String g;

    public static ResponseData a(String object) {
        Object object2;
        int n3 = object.indexOf(58);
        Object object3 = "";
        if (-1 == n3) {
            object2 = object;
        } else {
            object2 = object.substring(0, n3);
            object = n3 >= object.length() ? object3 : object.substring(n3 + 1);
            object3 = object;
        }
        object = TextUtils.split((String)object2, (String)Pattern.quote("|"));
        if (((String[])object).length >= 6) {
            object2 = new ResponseData();
            object2.g = object3;
            object2.a = Integer.parseInt(object[0]);
            object2.b = Integer.parseInt(object[1]);
            object2.c = object[2];
            object2.d = object[3];
            object2.e = object[4];
            object2.f = Long.parseLong(object[5]);
            return object2;
        }
        throw new IllegalArgumentException("Wrong number of fields.");
    }

    public String toString() {
        return TextUtils.join((CharSequence)"|", (Object[])new Object[]{this.a, this.b, this.c, this.d, this.e, this.f});
    }
}

