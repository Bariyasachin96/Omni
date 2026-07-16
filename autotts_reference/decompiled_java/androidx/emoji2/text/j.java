/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.graphics.Paint$FontMetricsInt
 *  android.text.style.ReplacementSpan
 */
package androidx.emoji2.text;

import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import androidx.emoji2.text.p;
import n0.h;

public abstract class j
extends ReplacementSpan {
    public final Paint.FontMetricsInt c = new Paint.FontMetricsInt();
    public final p d;
    public short e = (short)-1;
    public short f = (short)-1;
    public float g = 1.0f;

    public j(p p3) {
        h.h(p3, "rasterizer cannot be null");
        this.d = p3;
    }

    public final p a() {
        return this.d;
    }

    public final int b() {
        return this.e;
    }

    public int getSize(Paint paint, CharSequence charSequence, int n3, int n4, Paint.FontMetricsInt fontMetricsInt) {
        short s3;
        paint.getFontMetricsInt(this.c);
        paint = this.c;
        this.g = (float)Math.abs(paint.descent - paint.ascent) * 1.0f / (float)this.d.e();
        this.f = (short)((float)this.d.e() * this.g);
        this.e = s3 = (short)((float)this.d.i() * this.g);
        if (fontMetricsInt != null) {
            paint = this.c;
            fontMetricsInt.ascent = paint.ascent;
            fontMetricsInt.descent = paint.descent;
            fontMetricsInt.top = paint.top;
            fontMetricsInt.bottom = paint.bottom;
        }
        return s3;
    }
}

