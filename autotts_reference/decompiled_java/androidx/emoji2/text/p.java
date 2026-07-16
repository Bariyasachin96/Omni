/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Typeface
 */
package androidx.emoji2.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.emoji2.text.n;
import y0.a;

public class p {
    public static final ThreadLocal d = new ThreadLocal();
    public final int a;
    public final n b;
    public volatile int c = 0;

    public p(n n3, int n4) {
        this.b = n3;
        this.a = n4;
    }

    public void a(Canvas canvas, float f3, float f4, Paint paint) {
        Typeface typeface = this.b.g();
        Typeface typeface2 = paint.getTypeface();
        paint.setTypeface(typeface);
        int n3 = this.a;
        canvas.drawText(this.b.c(), n3 * 2, 2, f3, f4, paint);
        paint.setTypeface(typeface2);
    }

    public int b(int n3) {
        return this.g().h(n3);
    }

    public int c() {
        return this.g().i();
    }

    public int d() {
        return this.c & 3;
    }

    public int e() {
        return this.g().k();
    }

    public int f() {
        return this.g().l();
    }

    public final a g() {
        a a4;
        ThreadLocal threadLocal = d;
        a a5 = a4 = (a)threadLocal.get();
        if (a4 == null) {
            a5 = new a();
            threadLocal.set(a5);
        }
        this.b.d().j(a5, this.a);
        return a5;
    }

    public short h() {
        return this.g().m();
    }

    public int i() {
        return this.g().n();
    }

    public boolean j() {
        return this.g().j();
    }

    public boolean k() {
        return (this.c & 4) > 0;
    }

    public void l(boolean bl) {
        int n3 = this.d();
        if (bl) {
            this.c = n3 | 4;
            return;
        }
        this.c = n3;
    }

    public void m(boolean bl) {
        int n3 = this.c & 4;
        n3 = bl ? (n3 |= 2) : (n3 |= 1);
        this.c = n3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(", id:");
        stringBuilder.append(Integer.toHexString(this.f()));
        stringBuilder.append(", codepoints:");
        int n3 = this.c();
        for (int i3 = 0; i3 < n3; ++i3) {
            stringBuilder.append(Integer.toHexString(this.b(i3)));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}

