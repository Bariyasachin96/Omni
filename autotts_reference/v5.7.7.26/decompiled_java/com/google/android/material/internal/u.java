/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.StaticLayout$Builder
 *  android.text.TextDirectionHeuristic
 *  android.text.TextDirectionHeuristics
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 */
package com.google.android.material.internal;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import com.google.android.material.internal.v;

public final class u {
    public static final int o = 1;
    public CharSequence a;
    public final TextPaint b;
    public final int c;
    public int d;
    public int e;
    public Layout.Alignment f;
    public int g;
    public float h;
    public float i;
    public int j;
    public boolean k;
    public boolean l;
    public TextUtils.TruncateAt m;
    public v n;

    public u(CharSequence charSequence, TextPaint textPaint, int n3) {
        this.a = charSequence;
        this.b = textPaint;
        this.c = n3;
        this.d = 0;
        this.e = charSequence.length();
        this.f = Layout.Alignment.ALIGN_NORMAL;
        this.g = Integer.MAX_VALUE;
        this.h = 0.0f;
        this.i = 1.0f;
        this.j = o;
        this.k = true;
        this.m = null;
    }

    public static u b(CharSequence charSequence, TextPaint textPaint, int n3) {
        return new u(charSequence, textPaint, n3);
    }

    public StaticLayout a() {
        int n3;
        if (this.a == null) {
            this.a = "";
        }
        int n4 = Math.max(0, this.c);
        CharSequence charSequence = this.a;
        Object object = charSequence;
        if (this.g == 1) {
            object = TextUtils.ellipsize((CharSequence)charSequence, (TextPaint)this.b, (float)n4, (TextUtils.TruncateAt)this.m);
        }
        this.e = n3 = Math.min(object.length(), this.e);
        if (this.l && this.g == 1) {
            this.f = Layout.Alignment.ALIGN_OPPOSITE;
        }
        charSequence = StaticLayout.Builder.obtain((CharSequence)object, (int)this.d, (int)n3, (TextPaint)this.b, (int)n4);
        charSequence.setAlignment(this.f);
        charSequence.setIncludePad(this.k);
        object = this.l ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
        charSequence.setTextDirection((TextDirectionHeuristic)object);
        object = this.m;
        if (object != null) {
            charSequence.setEllipsize((TextUtils.TruncateAt)object);
        }
        charSequence.setMaxLines(this.g);
        float f3 = this.h;
        if (f3 != 0.0f || this.i != 1.0f) {
            charSequence.setLineSpacing(f3, this.i);
        }
        if (this.g > 1) {
            charSequence.setHyphenationFrequency(this.j);
        }
        if ((object = this.n) != null) {
            object.a((StaticLayout.Builder)charSequence);
        }
        return charSequence.build();
    }

    public u c(Layout.Alignment alignment) {
        this.f = alignment;
        return this;
    }

    public u d(TextUtils.TruncateAt truncateAt) {
        this.m = truncateAt;
        return this;
    }

    public u e(int n3) {
        this.j = n3;
        return this;
    }

    public u f(boolean bl) {
        this.k = bl;
        return this;
    }

    public u g(boolean bl) {
        this.l = bl;
        return this;
    }

    public u h(float f3, float f4) {
        this.h = f3;
        this.i = f4;
        return this;
    }

    public u i(int n3) {
        this.g = n3;
        return this;
    }

    public u j(v v3) {
        this.n = v3;
        return this;
    }

    public static abstract class a
    extends Exception {
    }
}

