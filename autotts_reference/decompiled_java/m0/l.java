/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.text.PrecomputedText$Params
 *  android.text.Spannable
 *  android.text.TextDirectionHeuristic
 *  android.text.TextDirectionHeuristics
 *  android.text.TextPaint
 *  android.text.TextUtils
 */
package m0;

import android.os.Build;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import m0.c;
import m0.d;
import m0.e;
import m0.f;
import m0.g;
import m0.h;
import m0.i;
import m0.j;
import m0.k;

public abstract class l
implements Spannable {

    public static final class m0.l$a {
        public final TextPaint a;
        public final TextDirectionHeuristic b;
        public final int c;
        public final int d;
        public final PrecomputedText.Params e;

        public cfr_renamed_8(PrecomputedText.Params params) {
            this.a = m0.c.a(params);
            this.b = m0.d.a(params);
            this.c = m0.e.a(params);
            this.d = f.a(params);
            if (Build.VERSION.SDK_INT < 29) {
                params = null;
            }
            this.e = params;
        }

        public cfr_renamed_8(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int n3, int n4) {
            this.e = Build.VERSION.SDK_INT >= 29 ? j.a(i.a(h.a(g.a(k.a(textPaint), n3), n4), textDirectionHeuristic)) : null;
            this.a = textPaint;
            this.b = textDirectionHeuristic;
            this.c = n3;
            this.d = n4;
        }

        public boolean a(m0.l$a a4) {
            if (this.c != a4.b()) {
                return false;
            }
            if (this.d != a4.c()) {
                return false;
            }
            if (this.a.getTextSize() != a4.e().getTextSize()) {
                return false;
            }
            if (this.a.getTextScaleX() != a4.e().getTextScaleX()) {
                return false;
            }
            if (this.a.getTextSkewX() != a4.e().getTextSkewX()) {
                return false;
            }
            if (this.a.getLetterSpacing() != a4.e().getLetterSpacing()) {
                return false;
            }
            if (!TextUtils.equals((CharSequence)this.a.getFontFeatureSettings(), (CharSequence)a4.e().getFontFeatureSettings())) {
                return false;
            }
            if (this.a.getFlags() != a4.e().getFlags()) {
                return false;
            }
            if (!this.a.getTextLocales().equals((Object)a4.e().getTextLocales())) {
                return false;
            }
            return !(this.a.getTypeface() == null ? a4.e().getTypeface() != null : !this.a.getTypeface().equals((Object)a4.e().getTypeface()));
        }

        public int b() {
            return this.c;
        }

        public int c() {
            return this.d;
        }

        public TextDirectionHeuristic d() {
            return this.b;
        }

        public TextPaint e() {
            return this.a;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof m0.l$a)) {
                return false;
            }
            if (!this.a((m0.l$a)(object = (m0.l$a)object))) {
                return false;
            }
            return this.b == ((m0.l$a)object).d();
        }

        public int hashCode() {
            return n0.c.b(Float.valueOf(this.a.getTextSize()), Float.valueOf(this.a.getTextScaleX()), Float.valueOf(this.a.getTextSkewX()), Float.valueOf(this.a.getLetterSpacing()), this.a.getFlags(), this.a.getTextLocales(), this.a.getTypeface(), this.a.isElegantTextHeight(), this.b, this.c, this.d);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("{");
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("textSize=");
            stringBuilder2.append(this.a.getTextSize());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", textScaleX=");
            stringBuilder2.append(this.a.getTextScaleX());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", textSkewX=");
            stringBuilder2.append(this.a.getTextSkewX());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", letterSpacing=");
            stringBuilder2.append(this.a.getLetterSpacing());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", elegantTextHeight=");
            stringBuilder2.append(this.a.isElegantTextHeight());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", textLocale=");
            stringBuilder2.append(this.a.getTextLocales());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", typeface=");
            stringBuilder2.append(this.a.getTypeface());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", variationSettings=");
            stringBuilder2.append(this.a.getFontVariationSettings());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", textDir=");
            stringBuilder2.append(this.b);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", breakStrategy=");
            stringBuilder2.append(this.c);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", hyphenationFrequency=");
            stringBuilder2.append(this.d);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public static class a {
            public final TextPaint a;
            public TextDirectionHeuristic b;
            public int c;
            public int d;

            public a(TextPaint textPaint) {
                this.a = textPaint;
                this.c = 1;
                this.d = 1;
                this.b = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }

            public m0.l$a a() {
                return new m0.l$a(this.a, this.b, this.c, this.d);
            }

            public a b(int n3) {
                this.c = n3;
                return this;
            }

            public a c(int n3) {
                this.d = n3;
                return this;
            }

            public a d(TextDirectionHeuristic textDirectionHeuristic) {
                this.b = textDirectionHeuristic;
                return this;
            }
        }
    }
}

