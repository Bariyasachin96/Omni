/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 *  android.util.SparseArray
 */
package androidx.emoji2.text;

import android.graphics.Typeface;
import android.util.SparseArray;
import androidx.emoji2.text.m;
import androidx.emoji2.text.p;
import java.nio.ByteBuffer;
import k0.e;
import n0.h;
import y0.b;

public final class n {
    public final b a;
    public final char[] b;
    public final a c;
    public final Typeface d;

    public n(Typeface typeface, b b3) {
        this.d = typeface;
        this.a = b3;
        this.c = new a(1024);
        this.b = new char[b3.k() * 2];
        this.a(b3);
    }

    public static n b(Typeface object, ByteBuffer byteBuffer) {
        try {
            e.a("EmojiCompat.MetadataRepo.create");
            object = new n((Typeface)object, m.b(byteBuffer));
            return object;
        }
        finally {
            e.b();
        }
    }

    public final void a(b object) {
        int n3 = ((b)object).k();
        for (int i3 = 0; i3 < n3; ++i3) {
            object = new p(this, i3);
            Character.toChars(((p)object).f(), this.b, i3 * 2);
            this.h((p)object);
        }
    }

    public char[] c() {
        return this.b;
    }

    public b d() {
        return this.a;
    }

    public int e() {
        return this.a.l();
    }

    public a f() {
        return this.c;
    }

    public Typeface g() {
        return this.d;
    }

    public void h(p p3) {
        h.h(p3, "emoji metadata cannot be null");
        boolean bl = p3.c() > 0;
        h.b(bl, "invalid metadata codepoint length");
        this.c.c(p3, 0, p3.c() - 1);
    }

    public static class a {
        public final SparseArray a;
        public p b;

        public a() {
            this(1);
        }

        public a(int n3) {
            this.a = new SparseArray(n3);
        }

        public a a(int n3) {
            SparseArray sparseArray = this.a;
            if (sparseArray == null) {
                return null;
            }
            return (a)sparseArray.get(n3);
        }

        public final p b() {
            return this.b;
        }

        public void c(p p3, int n3, int n4) {
            a a4;
            a a5 = a4 = this.a(p3.b(n3));
            if (a4 == null) {
                a5 = new a();
                this.a.put(p3.b(n3), (Object)a5);
            }
            if (n4 > n3) {
                a5.c(p3, n3 + 1, n4);
                return;
            }
            a5.b = p3;
        }
    }
}

