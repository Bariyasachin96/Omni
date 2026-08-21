/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.window.BackEvent
 */
package androidx.activity;

import android.window.BackEvent;
import o3.g;
import o3.k;

public final class b {
    public static final a e = new a(null);
    public final float a;
    public final float b;
    public final float c;
    public final int d;

    public b(float f3, float f4, float f5, int n3) {
        this.a = f3;
        this.b = f4;
        this.c = f5;
        this.d = n3;
    }

    public b(BackEvent backEvent) {
        k.e(backEvent, "backEvent");
        androidx.activity.a a4 = androidx.activity.a.a;
        this(a4.d(backEvent), a4.e(backEvent), a4.b(backEvent), a4.c(backEvent));
    }

    public final float a() {
        return this.c;
    }

    public final int b() {
        return this.d;
    }

    public final float c() {
        return this.b;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BackEventCompat{touchX=");
        stringBuilder.append(this.a);
        stringBuilder.append(", touchY=");
        stringBuilder.append(this.b);
        stringBuilder.append(", progress=");
        stringBuilder.append(this.c);
        stringBuilder.append(", swipeEdge=");
        stringBuilder.append(this.d);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }
    }
}

