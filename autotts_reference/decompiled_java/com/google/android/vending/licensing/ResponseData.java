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
        String[] stringArray;
        int n3 = object.indexOf(58);
        Object object2 = "";
        if (-1 == n3) {
            stringArray = object;
        } else {
            stringArray = object.substring(0, n3);
            object = n3 >= object.length() ? object2 : object.substring(n3 + 1);
            object2 = object;
        }
        stringArray = TextUtils.split((String)stringArray, (String)Pattern.quote("|"));
        if (stringArray.length >= 6) {
            object = new ResponseData();
            object.g = object2;
            object.a = Integer.parseInt(stringArray[0]);
            object.b = Integer.parseInt(stringArray[1]);
            object.c = stringArray[2];
            object.d = stringArray[3];
            object.e = stringArray[4];
            object.f = Long.parseLong(stringArray[5]);
            return object;
        }
        throw new IllegalArgumentException("Wrong number of fields.");
    }

    public String toString() {
        return TextUtils.join((CharSequence)"|", (Object[])new Object[]{this.a, this.b, this.c, this.d, this.e, this.f});
    }
}

