/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable$Creator
 */
package s1;

import android.os.Parcelable;
import s1.c;

public final class b
implements Parcelable.Creator {
    public static final b b = new b(new c());
    public final Parcelable.Creator a;

    public b(Parcelable.Creator creator) {
        this.a = creator;
    }

    public static b a() {
        return b;
    }
}

