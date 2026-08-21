/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.text.TextPaint
 */
package androidx.emoji2.text;

import android.graphics.Paint;
import android.text.TextPaint;
import androidx.emoji2.text.f;
import g0.c;

public class e
implements f.e {
    public static final ThreadLocal b = new ThreadLocal();
    public final TextPaint a;

    public e() {
        TextPaint textPaint;
        this.a = textPaint = new TextPaint();
        textPaint.setTextSize(10.0f);
    }

    public static StringBuilder b() {
        ThreadLocal threadLocal = b;
        if (threadLocal.get() == null) {
            threadLocal.set(new StringBuilder());
        }
        return (StringBuilder)threadLocal.get();
    }

    @Override
    public boolean a(CharSequence charSequence, int n3, int n4, int n5) {
        StringBuilder stringBuilder = e.b();
        stringBuilder.setLength(0);
        while (n3 < n4) {
            stringBuilder.append(charSequence.charAt(n3));
            ++n3;
        }
        return c.a((Paint)this.a, stringBuilder.toString());
    }
}

