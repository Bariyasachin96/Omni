/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.text.Spannable
 *  android.text.SpannableString
 */
package androidx.emoji2.text;

import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import androidx.emoji2.text.s;
import java.util.stream.IntStream;

public class r
implements Spannable {
    public boolean c = false;
    public Spannable d;

    public r(Spannable spannable) {
        this.d = spannable;
    }

    public r(CharSequence charSequence) {
        this.d = new SpannableString(charSequence);
    }

    public static b c() {
        if (Build.VERSION.SDK_INT < 28) {
            return new b();
        }
        return new c();
    }

    public final void a() {
        Spannable spannable = this.d;
        if (!this.c && r.c().a((CharSequence)spannable)) {
            this.d = new SpannableString((CharSequence)spannable);
        }
        this.c = true;
    }

    public Spannable b() {
        return this.d;
    }

    public char charAt(int n3) {
        return this.d.charAt(n3);
    }

    public IntStream chars() {
        return a.a((CharSequence)this.d);
    }

    public IntStream codePoints() {
        return a.b((CharSequence)this.d);
    }

    public int getSpanEnd(Object object) {
        return this.d.getSpanEnd(object);
    }

    public int getSpanFlags(Object object) {
        return this.d.getSpanFlags(object);
    }

    public int getSpanStart(Object object) {
        return this.d.getSpanStart(object);
    }

    public Object[] getSpans(int n3, int n4, Class clazz) {
        return this.d.getSpans(n3, n4, clazz);
    }

    public int length() {
        return this.d.length();
    }

    public int nextSpanTransition(int n3, int n4, Class clazz) {
        return this.d.nextSpanTransition(n3, n4, clazz);
    }

    public void removeSpan(Object object) {
        this.a();
        this.d.removeSpan(object);
    }

    public void setSpan(Object object, int n3, int n4, int n5) {
        this.a();
        this.d.setSpan(object, n3, n4, n5);
    }

    public CharSequence subSequence(int n3, int n4) {
        return this.d.subSequence(n3, n4);
    }

    public String toString() {
        return this.d.toString();
    }

    public static abstract class a {
        public static IntStream a(CharSequence charSequence) {
            return charSequence.chars();
        }

        public static IntStream b(CharSequence charSequence) {
            return charSequence.codePoints();
        }
    }

    public static class b {
        public boolean a(CharSequence charSequence) {
            return false;
        }
    }

    public static class c
    extends b {
        @Override
        public boolean a(CharSequence charSequence) {
            return s.a(charSequence);
        }
    }
}

