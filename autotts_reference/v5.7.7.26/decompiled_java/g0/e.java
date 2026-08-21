/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.os.Build$VERSION
 *  android.os.CancellationSignal
 *  android.os.Handler
 */
package g0;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import f0.e;
import f0.h;
import g0.g;
import g0.h;
import g0.i;
import g0.j;
import l0.g;
import o.l;

public abstract class e {
    public static final j a;
    public static final l b;

    static {
        int n3 = Build.VERSION.SDK_INT;
        a = n3 >= 29 ? new i() : (n3 >= 28 ? new h() : new g());
        b = new l(16);
    }

    public static Typeface a(Context context, Typeface typeface, int n3) {
        if (context != null) {
            return Typeface.create((Typeface)typeface, (int)n3);
        }
        throw new IllegalArgumentException("Context cannot be null");
    }

    public static Typeface b(Context context, CancellationSignal cancellationSignal, g.b[] bArray, int n3) {
        return a.b(context, cancellationSignal, bArray, n3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static Typeface c(Context object, e.b b3, Resources resources, int n3, String string, int n4, int n5, h.e object2, Handler handler, boolean bl) {
        void var0_5;
        void var2_7;
        void var6_11;
        Handler handler2;
        a a4;
        e.e e3;
        if (e3 instanceof e.e) {
            void var9_14;
            Typeface typeface = e.g((e3 = (e.e)e3).c());
            if (typeface != null) {
                if (a4 != null) {
                    ((h.e)((Object)a4)).d(typeface, handler2);
                }
                return typeface;
            }
            boolean bl2 = var9_14 != false ? e3.a() == 0 : a4 == null;
            int n6 = var9_14 != false ? e3.d() : -1;
            handler2 = h.e.e(handler2);
            a4 = new a((h.e)((Object)a4));
            Typeface typeface2 = l0.g.c(object, e3.b(), (int)var6_11, bl2, n6, handler2, a4);
        } else {
            e.e e4 = e3 = a.a((Context)object, (e.c)((Object)e3), (Resources)var2_7, (int)var6_11);
            if (a4 != null) {
                if (e3 != null) {
                    ((h.e)((Object)a4)).d((Typeface)e3, handler2);
                    e.e e5 = e3;
                } else {
                    ((h.e)((Object)a4)).c(-3, handler2);
                    e.e e6 = e3;
                }
            }
        }
        if (var0_5 != null) {
            void var5_10;
            void var4_9;
            void var3_8;
            b.d(e.e((Resources)var2_7, (int)var3_8, (String)var4_9, (int)var5_10, (int)var6_11), var0_5);
        }
        return var0_5;
    }

    public static Typeface d(Context context, Resources object, int n3, String string, int n4, int n5) {
        if ((context = a.c(context, (Resources)object, n3, string, n5)) != null) {
            object = e.e(object, n3, string, n4, n5);
            b.d(object, context);
        }
        return context;
    }

    public static String e(Resources resources, int n3, String string, int n4, int n5) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(resources.getResourcePackageName(n3));
        stringBuilder.append('-');
        stringBuilder.append(string);
        stringBuilder.append('-');
        stringBuilder.append(n4);
        stringBuilder.append('-');
        stringBuilder.append(n3);
        stringBuilder.append('-');
        stringBuilder.append(n5);
        return stringBuilder.toString();
    }

    public static Typeface f(Resources resources, int n3, String string, int n4, int n5) {
        return (Typeface)b.c(e.e(resources, n3, string, n4, n5));
    }

    public static Typeface g(String string) {
        if (string != null && !string.isEmpty()) {
            string = Typeface.create((String)string, (int)0);
            Typeface typeface = Typeface.create((Typeface)Typeface.DEFAULT, (int)0);
            if (string != null && !string.equals((Object)typeface)) {
                return string;
            }
        }
        return null;
    }

    public static class a
    extends g.c {
        public h.e a;

        public a(h.e e3) {
            this.a = e3;
        }

        @Override
        public void a(int n3) {
            h.e e3 = this.a;
            if (e3 != null) {
                e3.f(n3);
            }
        }

        @Override
        public void b(Typeface typeface) {
            h.e e3 = this.a;
            if (e3 != null) {
                e3.g(typeface);
            }
        }
    }
}

