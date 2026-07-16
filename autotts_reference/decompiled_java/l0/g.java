/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Typeface
 *  android.net.Uri
 *  android.os.CancellationSignal
 *  android.os.Handler
 */
package l0;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import l0.d;
import l0.e;
import l0.f;
import n0.h;

public abstract class g {
    public static Typeface a(Context context, CancellationSignal cancellationSignal, b[] bArray) {
        return g0.e.b(context, cancellationSignal, bArray, 0);
    }

    public static a b(Context context, CancellationSignal cancellationSignal, e e3) {
        return d.e(context, e3, cancellationSignal);
    }

    public static Typeface c(Context context, e e3, int n3, boolean bl, int n4, Handler object, c c3) {
        object = new l0.a(c3, (Handler)object);
        if (bl) {
            return f.e(context, e3, (l0.a)object, n3, n4);
        }
        return f.d(context, e3, n3, null, (l0.a)object);
    }

    public static class a {
        public final int a;
        public final b[] b;

        public a(int n3, b[] bArray) {
            this.a = n3;
            this.b = bArray;
        }

        public static a a(int n3, b[] bArray) {
            return new a(n3, bArray);
        }

        public b[] b() {
            return this.b;
        }

        public int c() {
            return this.a;
        }
    }

    public static class b {
        public final Uri a;
        public final int b;
        public final int c;
        public final boolean d;
        public final int e;

        public b(Uri uri, int n3, int n4, boolean bl, int n5) {
            this.a = (Uri)h.g(uri);
            this.b = n3;
            this.c = n4;
            this.d = bl;
            this.e = n5;
        }

        public static b a(Uri uri, int n3, int n4, boolean bl, int n5) {
            return new b(uri, n3, n4, bl, n5);
        }

        public int b() {
            return this.e;
        }

        public int c() {
            return this.b;
        }

        public Uri d() {
            return this.a;
        }

        public int e() {
            return this.c;
        }

        public boolean f() {
            return this.d;
        }
    }

    public static abstract class c {
        public abstract void a(int var1);

        public abstract void b(Typeface var1);
    }
}

