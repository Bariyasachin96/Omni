/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package s2;

import android.graphics.Typeface;
import s2.f;

public final class a
extends f {
    public final Typeface a;
    public final a b;
    public boolean c;

    public a(a a4, Typeface typeface) {
        this.a = typeface;
        this.b = a4;
    }

    @Override
    public void a(int n3) {
        this.d(this.a);
    }

    @Override
    public void b(Typeface typeface, boolean bl) {
        this.d(typeface);
    }

    public void c() {
        this.c = true;
    }

    public final void d(Typeface typeface) {
        if (!this.c) {
            this.b.a(typeface);
        }
    }

    public static interface a {
        public void a(Typeface var1);
    }
}

