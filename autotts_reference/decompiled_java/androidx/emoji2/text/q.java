/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.text.Spanned
 *  android.text.TextPaint
 *  android.text.style.CharacterStyle
 */
package androidx.emoji2.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import androidx.emoji2.text.f;
import androidx.emoji2.text.j;
import androidx.emoji2.text.p;

public final class q
extends j {
    public static Paint i;
    public TextPaint h;

    public q(p p3) {
        super(p3);
    }

    public static Paint e() {
        if (i == null) {
            TextPaint textPaint = new TextPaint();
            i = textPaint;
            textPaint.setColor(androidx.emoji2.text.f.c().d());
            i.setStyle(Paint.Style.FILL);
        }
        return i;
    }

    public final TextPaint c(CharSequence charSequence, int n3, int n4, Paint paint) {
        if (charSequence instanceof Spanned) {
            CharacterStyle[] characterStyleArray = (CharacterStyle[])((Spanned)charSequence).getSpans(n3, n4, CharacterStyle.class);
            if (characterStyleArray.length != 0) {
                n4 = characterStyleArray.length;
                n3 = 0;
                if (n4 != 1 || characterStyleArray[0] != this) {
                    TextPaint textPaint = this.h;
                    charSequence = textPaint;
                    if (textPaint == null) {
                        charSequence = new TextPaint();
                        this.h = charSequence;
                    }
                    charSequence.set(paint);
                    while (n3 < characterStyleArray.length) {
                        characterStyleArray[n3].updateDrawState((TextPaint)charSequence);
                        ++n3;
                    }
                    return charSequence;
                }
            }
            if (paint instanceof TextPaint) {
                return (TextPaint)paint;
            }
            return null;
        }
        if (paint instanceof TextPaint) {
            return (TextPaint)paint;
        }
        return null;
    }

    public void d(Canvas canvas, TextPaint textPaint, float f3, float f4, float f5, float f6) {
        int n3 = textPaint.getColor();
        Paint.Style style = textPaint.getStyle();
        textPaint.setColor(textPaint.bgColor);
        textPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(f3, f5, f4, f6, (Paint)textPaint);
        textPaint.setStyle(style);
        textPaint.setColor(n3);
    }

    public void draw(Canvas canvas, CharSequence charSequence, int n3, int n4, float f3, int n5, int n6, int n7, Paint paint) {
        if ((charSequence = this.c(charSequence, n3, n4, paint)) != null && ((TextPaint)charSequence).bgColor != 0) {
            this.d(canvas, (TextPaint)charSequence, f3, f3 + (float)this.b(), n5, n7);
        }
        if (androidx.emoji2.text.f.c().j()) {
            canvas.drawRect(f3, (float)n5, f3 + (float)this.b(), (float)n7, q.e());
        }
        p p3 = this.a();
        float f4 = n6;
        if (charSequence == null) {
            charSequence = paint;
        }
        p3.a(canvas, f3, f4, (Paint)charSequence);
    }
}

