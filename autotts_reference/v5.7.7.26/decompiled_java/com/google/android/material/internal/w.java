/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Typeface
 *  android.text.TextPaint
 */
package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import java.lang.ref.WeakReference;
import s2.d;
import s2.f;

public class w {
    public final TextPaint a = new TextPaint(1);
    public final f b = new f(this){
        public final w a;
        {
            this.a = w3;
        }

        @Override
        public void a(int n3) {
            w.a(this.a, true);
            b b3 = (b)this.a.f.get();
            if (b3 != null) {
                b3.a();
            }
        }

        @Override
        public void b(Typeface object, boolean bl) {
            if (!bl) {
                w.a(this.a, true);
                object = (b)this.a.f.get();
                if (object != null) {
                    object.a();
                }
            }
        }
    };
    public float c;
    public float d;
    public boolean e = true;
    public WeakReference f = new WeakReference<Object>(null);
    public d g;

    public w(b b3) {
        this.j(b3);
    }

    public static /* synthetic */ boolean a(w w3, boolean bl) {
        w3.e = bl;
        return bl;
    }

    public final float c(String string) {
        if (string == null) {
            return 0.0f;
        }
        return Math.abs(this.a.getFontMetrics().ascent);
    }

    public final float d(CharSequence charSequence) {
        if (charSequence == null) {
            return 0.0f;
        }
        return this.a.measureText(charSequence, 0, charSequence.length());
    }

    public d e() {
        return this.g;
    }

    public float f(String string) {
        if (!this.e) {
            return this.d;
        }
        this.i(string);
        return this.d;
    }

    public TextPaint g() {
        return this.a;
    }

    public float h(String string) {
        if (!this.e) {
            return this.c;
        }
        this.i(string);
        return this.c;
    }

    public final void i(String string) {
        this.c = this.d(string);
        this.d = this.c(string);
        this.e = false;
    }

    public void j(b b3) {
        this.f = new WeakReference<b>(b3);
    }

    public void k(d object, Context context) {
        if (this.g != object) {
            this.g = object;
            if (object != null) {
                ((d)object).q(context, this.a, this.b);
                b b3 = (b)this.f.get();
                if (b3 != null) {
                    this.a.drawableState = b3.getState();
                }
                ((d)object).p(context, this.a, this.b);
                this.e = true;
            }
            if ((object = (b)this.f.get()) != null) {
                object.a();
                object.onStateChange(object.getState());
            }
        }
    }

    public void l(boolean bl) {
        this.e = bl;
    }

    public void m(boolean bl) {
        this.e = bl;
    }

    public void n(Context context) {
        this.g.p(context, this.a, this.b);
    }

    public static interface b {
        public void a();

        public int[] getState();

        public boolean onStateChange(int[] var1);
    }
}

