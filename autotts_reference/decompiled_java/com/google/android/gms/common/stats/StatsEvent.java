/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@Deprecated
public abstract class StatsEvent
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public abstract long o();

    public abstract int p();

    public abstract String q();

    public final String toString() {
        long l3 = this.o();
        int n3 = this.p();
        String string = this.q();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(l3).length() + 1 + String.valueOf(n3).length() + 3 + string.length());
        stringBuilder.append(l3);
        stringBuilder.append("\t");
        stringBuilder.append(n3);
        stringBuilder.append("\t-1");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }
}

