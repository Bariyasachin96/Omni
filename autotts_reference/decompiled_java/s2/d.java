/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.Typeface
 *  android.text.TextPaint
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 */
package s2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Xml;
import f0.h;
import org.xmlpull.v1.XmlPullParser;
import s2.c;
import s2.e;
import s2.f;
import s2.j;
import z1.m;

public class d {
    public final ColorStateList a;
    public final ColorStateList b;
    public final ColorStateList c;
    public final String d;
    public String e;
    public final int f;
    public final int g;
    public final boolean h;
    public final float i;
    public final float j;
    public final float k;
    public final boolean l;
    public final float m;
    public ColorStateList n;
    public float o;
    public final int p;
    public boolean q = false;
    public boolean r = false;
    public Typeface s;

    public d(Context context, int n3) {
        TypedArray typedArray = context.obtainStyledAttributes(n3, c.j.TextAppearance);
        this.o(typedArray.getDimension(c.j.TextAppearance_android_textSize, 0.0f));
        this.n(s2.c.a(context, typedArray, c.j.TextAppearance_android_textColor));
        this.a = s2.c.a(context, typedArray, c.j.TextAppearance_android_textColorHint);
        this.b = s2.c.a(context, typedArray, c.j.TextAppearance_android_textColorLink);
        this.f = typedArray.getInt(c.j.TextAppearance_android_textStyle, 0);
        this.g = typedArray.getInt(c.j.TextAppearance_android_typeface, 1);
        int n4 = s2.c.g(typedArray, c.j.TextAppearance_fontFamily, c.j.TextAppearance_android_fontFamily);
        this.p = typedArray.getResourceId(n4, 0);
        this.d = typedArray.getString(n4);
        this.h = typedArray.getBoolean(c.j.TextAppearance_textAllCaps, false);
        this.c = s2.c.a(context, typedArray, c.j.TextAppearance_android_shadowColor);
        this.i = typedArray.getFloat(c.j.TextAppearance_android_shadowDx, 0.0f);
        this.j = typedArray.getFloat(c.j.TextAppearance_android_shadowDy, 0.0f);
        this.k = typedArray.getFloat(c.j.TextAppearance_android_shadowRadius, 0.0f);
        typedArray.recycle();
        context = context.obtainStyledAttributes(n3, z1.m.MaterialTextAppearance);
        n3 = z1.m.MaterialTextAppearance_android_letterSpacing;
        this.l = context.hasValue(n3);
        this.m = context.getFloat(n3, 0.0f);
        this.e = context.getString(s2.c.g((TypedArray)context, z1.m.MaterialTextAppearance_fontVariationSettings, z1.m.MaterialTextAppearance_android_fontVariationSettings));
        context.recycle();
    }

    public static /* synthetic */ Typeface b(d d3, Typeface typeface) {
        d3.s = typeface;
        return typeface;
    }

    public static /* synthetic */ boolean c(d d3, boolean bl) {
        d3.q = bl;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String m(Context context, int n3) {
        Object object = context.getResources();
        if (n3 == 0) return null;
        if (!object.getResourceTypeName(n3).equals("font")) {
            return null;
        }
        try {
            context = object.getXml(n3);
            while (context.getEventType() != 1) {
                if (context.getEventType() == 2 && context.getName().equals("font-family")) {
                    context = object.obtainAttributes(Xml.asAttributeSet((XmlPullParser)context), b0.c.FontFamily);
                    object = context.getString(b0.c.FontFamily_fontProviderSystemFontFamily);
                    context.recycle();
                    return object;
                }
                context.next();
            }
            return null;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public final void d() {
        String string;
        if (this.s == null && (string = this.d) != null) {
            this.s = Typeface.create((String)string, (int)this.f);
        }
        if (this.s == null) {
            int n3 = this.g;
            this.s = n3 != 1 ? (n3 != 2 ? (n3 != 3 ? Typeface.DEFAULT : Typeface.MONOSPACE) : Typeface.SERIF) : Typeface.SANS_SERIF;
            this.s = Typeface.create((Typeface)this.s, (int)this.f);
        }
    }

    public Typeface e() {
        this.d();
        return this.s;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Typeface f(Context context) {
        if (this.q) {
            return this.s;
        }
        if (!context.isRestricted()) {
            try {
                context = f0.h.g(context, this.p);
                this.s = context;
                if (context != null) {
                    this.s = Typeface.create((Typeface)context, (int)this.f);
                }
            }
            catch (Resources.NotFoundException | Exception | UnsupportedOperationException throwable) {}
        }
        this.d();
        this.q = true;
        return this.s;
    }

    public void g(Context context, TextPaint textPaint, f f3) {
        this.r(context, textPaint, this.e());
        this.h(context, new f(this, context, textPaint, f3){
            public final Context a;
            public final TextPaint b;
            public final f c;
            public final d d;
            {
                this.d = d3;
                this.a = context;
                this.b = textPaint;
                this.c = f3;
            }

            @Override
            public void a(int n3) {
                this.c.a(n3);
            }

            @Override
            public void b(Typeface typeface, boolean bl) {
                this.d.r(this.a, this.b, typeface);
                this.c.b(typeface, bl);
            }
        });
    }

    public void h(Context context, f f3) {
        int n3;
        if (!this.l(context)) {
            this.d();
        }
        if ((n3 = this.p) == 0) {
            this.q = true;
        }
        if (this.q) {
            f3.b(this.s, true);
            return;
        }
        try {
            h.e e3 = new h.e(this, f3){
                public final f a;
                public final d b;
                {
                    this.b = d3;
                    this.a = f3;
                }

                @Override
                public void f(int n3) {
                    s2.d.c(this.b, true);
                    this.a.a(n3);
                }

                @Override
                public void g(Typeface typeface) {
                    d d3 = this.b;
                    s2.d.b(d3, Typeface.create((Typeface)typeface, (int)d3.f));
                    s2.d.c(this.b, true);
                    this.a.b(this.b.s, false);
                }
            };
            f0.h.i(context, n3, e3, null);
            return;
        }
        catch (Exception exception) {
            this.q = true;
            f3.a(-3);
        }
        catch (Resources.NotFoundException notFoundException) {
            this.q = true;
            f3.a(1);
        }
    }

    public final Typeface i(Context object) {
        if (this.r) {
            return null;
        }
        this.r = true;
        if ((object = s2.d.m(object, this.p)) == null) {
            return null;
        }
        if ((object = Typeface.create((String)object, (int)0)) == Typeface.DEFAULT) {
            return null;
        }
        return Typeface.create((Typeface)object, (int)this.f);
    }

    public ColorStateList j() {
        return this.n;
    }

    public float k() {
        return this.o;
    }

    public final boolean l(Context context) {
        if (s2.e.a()) {
            this.f(context);
            return true;
        }
        if (this.q) {
            return true;
        }
        int n3 = this.p;
        if (n3 == 0) {
            return false;
        }
        Typeface typeface = f0.h.c(context, n3);
        if (typeface != null) {
            this.s = typeface;
            this.q = true;
            return true;
        }
        if ((context = this.i(context)) != null) {
            this.s = context;
            this.q = true;
            return true;
        }
        return false;
    }

    public void n(ColorStateList colorStateList) {
        this.n = colorStateList;
    }

    public void o(float f3) {
        this.o = f3;
    }

    public void p(Context context, TextPaint textPaint, f f3) {
        this.q(context, textPaint, f3);
        context = this.n;
        int n3 = context != null ? context.getColorForState(textPaint.drawableState, context.getDefaultColor()) : -16777216;
        textPaint.setColor(n3);
        float f4 = this.k;
        float f5 = this.i;
        float f6 = this.j;
        context = this.c;
        n3 = context != null ? context.getColorForState(textPaint.drawableState, context.getDefaultColor()) : 0;
        textPaint.setShadowLayer(f4, f5, f6, n3);
    }

    public void q(Context context, TextPaint textPaint, f f3) {
        Typeface typeface;
        if (this.l(context) && this.q && (typeface = this.s) != null) {
            this.r(context, textPaint, typeface);
            return;
        }
        this.g(context, textPaint, f3);
    }

    public void r(Context context, TextPaint textPaint, Typeface typeface) {
        if ((context = s2.j.a(context, typeface)) != null) {
            typeface = context;
        }
        textPaint.setTypeface(typeface);
        int n3 = this.f & ~typeface.getStyle();
        boolean bl = (n3 & 1) != 0;
        textPaint.setFakeBoldText(bl);
        float f3 = (n3 & 2) != 0 ? -0.25f : 0.0f;
        textPaint.setTextSkewX(f3);
        textPaint.setTextSize(this.o);
        textPaint.setFontVariationSettings(this.e);
        if (this.l) {
            textPaint.setLetterSpacing(this.m);
        }
    }
}

