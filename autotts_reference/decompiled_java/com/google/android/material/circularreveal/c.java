/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 *  android.graphics.drawable.Drawable
 *  android.util.Property
 */
package com.google.android.material.circularreveal;

import android.animation.TypeEvaluator;
import android.graphics.drawable.Drawable;
import android.util.Property;
import com.google.android.material.circularreveal.b;

public interface c
extends b.a {
    public void a();

    public void d();

    public int getCircularRevealScrimColor();

    public e getRevealInfo();

    public void setCircularRevealOverlayDrawable(Drawable var1);

    public void setCircularRevealScrimColor(int var1);

    public void setRevealInfo(e var1);

    public static class b
    implements TypeEvaluator {
        public static final TypeEvaluator b = new b();
        public final e a = new e(null);

        public e a(float f3, e e3, e e4) {
            this.a.b(o2.a.f(e3.a, e4.a, f3), o2.a.f(e3.b, e4.b, f3), o2.a.f(e3.c, e4.c, f3));
            return this.a;
        }
    }

    public static class c
    extends Property {
        public static final Property a = new c("circularReveal");

        public c(String string) {
            super(e.class, string);
        }

        public e a(c c3) {
            return c3.getRevealInfo();
        }

        public void b(c c3, e e3) {
            c3.setRevealInfo(e3);
        }
    }

    public static class d
    extends Property {
        public static final Property a = new d("circularRevealScrimColor");

        public d(String string) {
            super(Integer.class, string);
        }

        public Integer a(c c3) {
            return c3.getCircularRevealScrimColor();
        }

        public void b(c c3, Integer n3) {
            c3.setCircularRevealScrimColor(n3);
        }
    }

    public static class e {
        public float a;
        public float b;
        public float c;

        public e() {
        }

        public e(float f3, float f4, float f5) {
            this.a = f3;
            this.b = f4;
            this.c = f5;
        }

        public /* synthetic */ e(a a4) {
            this();
        }

        public e(e e3) {
            this(e3.a, e3.b, e3.c);
        }

        public boolean a() {
            return this.c == Float.MAX_VALUE;
        }

        public void b(float f3, float f4, float f5) {
            this.a = f3;
            this.b = f4;
            this.c = f5;
        }

        public void c(e e3) {
            this.b(e3.a, e3.b, e3.c);
        }
    }
}

