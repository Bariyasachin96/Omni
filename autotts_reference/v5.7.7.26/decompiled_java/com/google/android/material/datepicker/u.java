/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.datepicker;

import java.util.Calendar;
import java.util.TimeZone;

public class u {
    public static final u c = new u(null, null);
    public final Long a;
    public final TimeZone b;

    public u(Long l3, TimeZone timeZone) {
        this.a = l3;
        this.b = timeZone;
    }

    public static u c() {
        return c;
    }

    public Calendar a() {
        return this.b(this.b);
    }

    public Calendar b(TimeZone cloneable) {
        cloneable = cloneable == null ? Calendar.getInstance() : Calendar.getInstance((TimeZone)cloneable);
        Long l3 = this.a;
        if (l3 != null) {
            ((Calendar)cloneable).setTimeInMillis(l3);
        }
        return cloneable;
    }
}

