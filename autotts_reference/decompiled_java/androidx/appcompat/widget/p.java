/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.LocaleList
 *  android.text.method.PasswordTransformationMethod
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.appcompat.widget.g;
import androidx.appcompat.widget.k0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.q;
import androidx.appcompat.widget.t0;
import androidx.appcompat.widget.z;
import androidx.core.widget.j;
import f0.h;
import java.lang.ref.WeakReference;
import o0.x0;
import s0.a;

public class p {
    public final TextView a;
    public k0 b;
    public k0 c;
    public k0 d;
    public k0 e;
    public k0 f;
    public k0 g;
    public k0 h;
    public final q i;
    public int j = 0;
    public int k = -1;
    public Typeface l;
    public boolean m;

    public p(TextView textView) {
        this.a = textView;
        this.i = new q(textView);
    }

    public static k0 d(Context context, g object, int n3) {
        if ((context = ((g)object).f(context, n3)) != null) {
            object = new k0();
            ((k0)object).d = true;
            ((k0)object).a = context;
            return object;
        }
        return null;
    }

    public void A(int n3, float f3) {
        if (!t0.c && !this.l()) {
            this.B(n3, f3);
        }
    }

    public final void B(int n3, float f3) {
        this.i.t(n3, f3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void C(Context object, m0 m02) {
        int n3;
        void var2_10;
        this.j = var2_10.k(c.j.TextAppearance_android_textStyle, this.j);
        int n4 = Build.VERSION.SDK_INT;
        if (n4 >= 28) {
            this.k = n3 = var2_10.k(c.j.TextAppearance_android_textFontWeight, -1);
            if (n3 != -1) {
                this.j &= 2;
            }
        }
        n3 = c.j.TextAppearance_android_fontFamily;
        boolean bl = var2_10.s(n3);
        boolean bl2 = true;
        if (!bl && !var2_10.s(c.j.TextAppearance_fontFamily)) {
            n3 = c.j.TextAppearance_android_typeface;
            if (!var2_10.s(n3)) return;
            this.m = false;
            if ((n3 = var2_10.k(n3, 1)) == 1) {
                this.l = Typeface.SANS_SERIF;
                return;
            }
            if (n3 == 2) {
                this.l = Typeface.SERIF;
                return;
            }
            if (n3 != 3) {
                return;
            }
            this.l = Typeface.MONOSPACE;
            return;
        }
        this.l = null;
        int n5 = c.j.TextAppearance_fontFamily;
        if (var2_10.s(n5)) {
            n3 = n5;
        }
        n5 = this.k;
        int n6 = this.j;
        if (!object.isRestricted()) {
            h.e e3 = new h.e(this, n5, n6, new WeakReference<TextView>(this.a)){
                public final int a;
                public final int b;
                public final WeakReference c;
                public final p d;
                {
                    this.d = p3;
                    this.a = n3;
                    this.b = n4;
                    this.c = weakReference;
                }

                @Override
                public void f(int n3) {
                }

                @Override
                public void g(Typeface typeface) {
                    Typeface typeface2 = typeface;
                    if (Build.VERSION.SDK_INT >= 28) {
                        int n3 = this.a;
                        typeface2 = typeface;
                        if (n3 != -1) {
                            boolean bl = (this.b & 2) != 0;
                            typeface2 = androidx.appcompat.widget.p$e.a(typeface, n3, bl);
                        }
                    }
                    this.d.n(this.c, typeface2);
                }
            };
            try {
                Typeface typeface = var2_10.j(n3, this.j, e3);
                if (typeface != null) {
                    if (n4 >= 28 && this.k != -1) {
                        Typeface typeface2 = Typeface.create((Typeface)typeface, (int)0);
                        n5 = this.k;
                        bl = (this.j & 2) != 0;
                        this.l = androidx.appcompat.widget.p$e.a(typeface2, n5, bl);
                    } else {
                        this.l = typeface;
                    }
                }
                bl = this.l == null;
                this.m = bl;
            }
            catch (Resources.NotFoundException | UnsupportedOperationException throwable) {}
        }
        if (this.l != null) return;
        String string = var2_10.o(n3);
        if (string == null) return;
        if (Build.VERSION.SDK_INT >= 28 && this.k != -1) {
            Typeface typeface = Typeface.create((String)string, (int)0);
            n3 = this.k;
            bl = (this.j & 2) != 0 ? bl2 : false;
            this.l = androidx.appcompat.widget.p$e.a(typeface, n3, bl);
            return;
        }
        this.l = Typeface.create((String)string, (int)this.j);
    }

    public final void a(Drawable drawable, k0 k02) {
        if (drawable != null && k02 != null) {
            androidx.appcompat.widget.g.i(drawable, k02, this.a.getDrawableState());
        }
    }

    public void b() {
        Drawable[] drawableArray;
        if (this.b != null || this.c != null || this.d != null || this.e != null) {
            drawableArray = this.a.getCompoundDrawables();
            this.a(drawableArray[0], this.b);
            this.a(drawableArray[1], this.c);
            this.a(drawableArray[2], this.d);
            this.a(drawableArray[3], this.e);
        }
        if (this.f == null && this.g == null) {
            return;
        }
        drawableArray = this.a.getCompoundDrawablesRelative();
        this.a(drawableArray[0], this.f);
        this.a(drawableArray[2], this.g);
    }

    public void c() {
        this.i.a();
    }

    public int e() {
        return this.i.f();
    }

    public int f() {
        return this.i.g();
    }

    public int g() {
        return this.i.h();
    }

    public int[] h() {
        return this.i.i();
    }

    public int i() {
        return this.i.j();
    }

    public ColorStateList j() {
        k0 k02 = this.h;
        if (k02 != null) {
            return k02.a;
        }
        return null;
    }

    public PorterDuff.Mode k() {
        k0 k02 = this.h;
        if (k02 != null) {
            return k02.b;
        }
        return null;
    }

    public boolean l() {
        return this.i.n();
    }

    public void m(AttributeSet object, int n3) {
        float f3;
        m0 m02;
        int n4;
        boolean bl;
        Context context = this.a.getContext();
        g g3 = androidx.appcompat.widget.g.b();
        Object object2 = c.j.AppCompatTextHelper;
        Object object3 = m0.v(context, object, (int[])object2, n3, 0);
        Object object4 = this.a;
        x0.f0((View)object4, object4.getContext(), (int[])object2, object, ((m0)object3).r(), n3, 0);
        int n5 = ((m0)object3).n(c.j.AppCompatTextHelper_android_textAppearance, -1);
        int n6 = c.j.AppCompatTextHelper_android_drawableLeft;
        if (((m0)object3).s(n6)) {
            this.b = p.d(context, g3, ((m0)object3).n(n6, 0));
        }
        if (((m0)object3).s(n6 = c.j.AppCompatTextHelper_android_drawableTop)) {
            this.c = p.d(context, g3, ((m0)object3).n(n6, 0));
        }
        if (((m0)object3).s(n6 = c.j.AppCompatTextHelper_android_drawableRight)) {
            this.d = p.d(context, g3, ((m0)object3).n(n6, 0));
        }
        if (((m0)object3).s(n6 = c.j.AppCompatTextHelper_android_drawableBottom)) {
            this.e = p.d(context, g3, ((m0)object3).n(n6, 0));
        }
        if (((m0)object3).s(n6 = c.j.AppCompatTextHelper_android_drawableStart)) {
            this.f = p.d(context, g3, ((m0)object3).n(n6, 0));
        }
        if (((m0)object3).s(n6 = c.j.AppCompatTextHelper_android_drawableEnd)) {
            this.g = p.d(context, g3, ((m0)object3).n(n6, 0));
        }
        ((m0)object3).x();
        boolean bl2 = this.a.getTransformationMethod() instanceof PasswordTransformationMethod;
        n6 = 1;
        if (n5 != -1) {
            object4 = m0.t(context, n5, c.j.TextAppearance);
            if (!bl2 && ((m0)object4).s(n5 = c.j.TextAppearance_textAllCaps)) {
                bl = ((m0)object4).a(n5, false);
                n5 = 1;
            } else {
                bl = false;
                n5 = 0;
            }
            this.C(context, (m0)object4);
            n4 = c.j.TextAppearance_textLocale;
            object3 = ((m0)object4).s(n4) ? ((m0)object4).o(n4) : null;
            n4 = c.j.TextAppearance_fontVariationSettings;
            object2 = ((m0)object4).s(n4) ? ((m0)object4).o(n4) : null;
            ((m0)object4).x();
            object4 = object3;
            object3 = object2;
        } else {
            bl = false;
            n5 = 0;
            object4 = null;
            object3 = null;
        }
        object2 = m0.v(context, object, c.j.TextAppearance, n3, 0);
        if (!bl2 && ((m0)object2).s(n4 = c.j.TextAppearance_textAllCaps)) {
            bl = ((m0)object2).a(n4, false);
            n5 = n6;
        }
        n6 = Build.VERSION.SDK_INT;
        n4 = c.j.TextAppearance_textLocale;
        if (((m0)object2).s(n4)) {
            object4 = ((m0)object2).o(n4);
        }
        if (((m0)object2).s(n4 = c.j.TextAppearance_fontVariationSettings)) {
            object3 = ((m0)object2).o(n4);
        }
        if (n6 >= 28 && ((m0)object2).s(n6 = c.j.TextAppearance_android_textSize) && ((m0)object2).f(n6, -1) == 0) {
            this.a.setTextSize(0, 0.0f);
        }
        this.C(context, (m0)object2);
        ((m0)object2).x();
        if (!bl2 && n5 != 0) {
            this.s(bl);
        }
        if ((object2 = this.l) != null) {
            if (this.k == -1) {
                this.a.setTypeface((Typeface)object2, this.j);
            } else {
                this.a.setTypeface((Typeface)object2);
            }
        }
        if (object3 != null) {
            androidx.appcompat.widget.p$d.d(this.a, (String)object3);
        }
        if (object4 != null) {
            androidx.appcompat.widget.p$c.b(this.a, androidx.appcompat.widget.p$c.a((String)object4));
        }
        this.i.o((AttributeSet)object, n3);
        if (t0.c && this.i.j() != 0 && ((Object)(object3 = (Object)this.i.i())).length > 0) {
            if ((float)androidx.appcompat.widget.p$d.a(this.a) != -1.0f) {
                androidx.appcompat.widget.p$d.b(this.a, this.i.g(), this.i.f(), this.i.h(), 0);
            } else {
                androidx.appcompat.widget.p$d.c(this.a, (int[])object3, 0);
            }
        }
        object = (n3 = (m02 = m0.u(context, object, c.j.AppCompatTextView)).n(c.j.AppCompatTextView_drawableLeftCompat, -1)) != -1 ? g3.c(context, n3) : null;
        n3 = m02.n(c.j.AppCompatTextView_drawableTopCompat, -1);
        object3 = n3 != -1 ? g3.c(context, n3) : null;
        n3 = m02.n(c.j.AppCompatTextView_drawableRightCompat, -1);
        object4 = n3 != -1 ? g3.c(context, n3) : null;
        n3 = m02.n(c.j.AppCompatTextView_drawableBottomCompat, -1);
        object2 = n3 != -1 ? g3.c(context, n3) : null;
        n3 = m02.n(c.j.AppCompatTextView_drawableStartCompat, -1);
        Drawable drawable = n3 != -1 ? g3.c(context, n3) : null;
        n3 = m02.n(c.j.AppCompatTextView_drawableEndCompat, -1);
        g3 = n3 != -1 ? g3.c(context, n3) : null;
        this.y((Drawable)object, (Drawable)object3, (Drawable)object4, (Drawable)object2, drawable, (Drawable)g3);
        n3 = c.j.AppCompatTextView_drawableTint;
        if (m02.s(n3)) {
            object = m02.c(n3);
            androidx.core.widget.j.f(this.a, (ColorStateList)object);
        }
        if (m02.s(n3 = c.j.AppCompatTextView_drawableTintMode)) {
            object = z.e(m02.k(n3, -1), null);
            androidx.core.widget.j.g(this.a, (PorterDuff.Mode)object);
        }
        n6 = m02.f(c.j.AppCompatTextView_firstBaselineToTopHeight, -1);
        n5 = m02.f(c.j.AppCompatTextView_lastBaselineToBottomHeight, -1);
        n3 = c.j.AppCompatTextView_lineHeight;
        if (m02.s(n3)) {
            object = m02.w(n3);
            if (object != null && object.type == 5) {
                n3 = n0.j.a(object.data);
                f3 = TypedValue.complexToFloat((int)object.data);
            } else {
                f3 = m02.f(n3, -1);
                n3 = -1;
            }
        } else {
            n3 = -1;
            f3 = -1.0f;
        }
        m02.x();
        if (n6 != -1) {
            androidx.core.widget.j.h(this.a, n6);
        }
        if (n5 != -1) {
            androidx.core.widget.j.i(this.a, n5);
        }
        if (f3 != -1.0f) {
            if (n3 == -1) {
                androidx.core.widget.j.j(this.a, (int)f3);
                return;
            }
            androidx.core.widget.j.k(this.a, n3, f3);
        }
    }

    public void n(WeakReference weakReference, Typeface typeface) {
        if (this.m) {
            this.l = typeface;
            if ((weakReference = (TextView)weakReference.get()) != null) {
                if (weakReference.isAttachedToWindow()) {
                    weakReference.post(new Runnable(this, (TextView)weakReference, typeface, this.j){
                        public final TextView c;
                        public final Typeface d;
                        public final int e;
                        public final p f;
                        {
                            this.f = p3;
                            this.c = textView;
                            this.d = typeface;
                            this.e = n3;
                        }

                        @Override
                        public void run() {
                            this.c.setTypeface(this.d, this.e);
                        }
                    });
                    return;
                }
                weakReference.setTypeface(typeface, this.j);
            }
        }
    }

    public void o(boolean bl, int n3, int n4, int n5, int n6) {
        if (!t0.c) {
            this.c();
        }
    }

    public void p() {
        this.b();
    }

    public void q(Context object, int n3) {
        m0 m02 = m0.t(object, n3, c.j.TextAppearance);
        if (m02.s(n3 = c.j.TextAppearance_textAllCaps)) {
            this.s(m02.a(n3, false));
        }
        if (m02.s(n3 = c.j.TextAppearance_android_textSize) && m02.f(n3, -1) == 0) {
            this.a.setTextSize(0, 0.0f);
        }
        this.C((Context)object, m02);
        n3 = c.j.TextAppearance_fontVariationSettings;
        if (m02.s(n3) && (object = m02.o(n3)) != null) {
            androidx.appcompat.widget.p$d.d(this.a, (String)object);
        }
        m02.x();
        object = this.l;
        if (object != null) {
            this.a.setTypeface((Typeface)object, this.j);
        }
    }

    public void r(TextView textView, InputConnection inputConnection, EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT < 30 && inputConnection != null) {
            s0.a.e(editorInfo, textView.getText());
        }
    }

    public void s(boolean bl) {
        this.a.setAllCaps(bl);
    }

    public void t(int n3, int n4, int n5, int n6) {
        this.i.p(n3, n4, n5, n6);
    }

    public void u(int[] nArray, int n3) {
        this.i.q(nArray, n3);
    }

    public void v(int n3) {
        this.i.r(n3);
    }

    public void w(ColorStateList colorStateList) {
        if (this.h == null) {
            this.h = new k0();
        }
        k0 k02 = this.h;
        k02.a = colorStateList;
        boolean bl = colorStateList != null;
        k02.d = bl;
        this.z();
    }

    public void x(PorterDuff.Mode mode) {
        if (this.h == null) {
            this.h = new k0();
        }
        k0 k02 = this.h;
        k02.b = mode;
        boolean bl = mode != null;
        k02.c = bl;
        this.z();
    }

    public final void y(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawableArray) {
        if (drawable5 == null && drawableArray == null) {
            if (drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) {
                return;
            }
            drawableArray = this.a.getCompoundDrawablesRelative();
            drawable5 = drawableArray[0];
            if (drawable5 == null && drawableArray[2] == null) {
                drawableArray = this.a.getCompoundDrawables();
                drawable5 = this.a;
                if (drawable == null) {
                    drawable = drawableArray[0];
                }
                if (drawable2 == null) {
                    drawable2 = drawableArray[1];
                }
                if (drawable3 == null) {
                    drawable3 = drawableArray[2];
                }
                if (drawable4 == null) {
                    drawable4 = drawableArray[3];
                }
                drawable5.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
                return;
            }
            if (drawable2 == null) {
                drawable2 = drawableArray[1];
            }
            if (drawable4 == null) {
                drawable4 = drawableArray[3];
            }
            this.a.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawableArray[2], drawable4);
            return;
        }
        drawable = this.a.getCompoundDrawablesRelative();
        if (drawable5 == null) {
            drawable5 = drawable[0];
        }
        if (drawable2 == null) {
            drawable2 = drawable[1];
        }
        if (drawableArray == null) {
            drawableArray = drawable[2];
        }
        drawable3 = this.a;
        if (drawable4 == null) {
            drawable4 = drawable[3];
        }
        drawable3.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, (Drawable)drawableArray, drawable4);
    }

    public final void z() {
        k0 k02;
        this.b = k02 = this.h;
        this.c = k02;
        this.d = k02;
        this.e = k02;
        this.f = k02;
        this.g = k02;
    }

    public static abstract class c {
        public static LocaleList a(String string) {
            return LocaleList.forLanguageTags((String)string);
        }

        public static void b(TextView textView, LocaleList localeList) {
            textView.setTextLocales(localeList);
        }
    }

    public static abstract class d {
        public static int a(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        public static void b(TextView textView, int n3, int n4, int n5, int n6) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(n3, n4, n5, n6);
        }

        public static void c(TextView textView, int[] nArray, int n3) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(nArray, n3);
        }

        public static boolean d(TextView textView, String string) {
            return textView.setFontVariationSettings(string);
        }
    }

    public static abstract class e {
        public static Typeface a(Typeface typeface, int n3, boolean bl) {
            return Typeface.create((Typeface)typeface, (int)n3, (boolean)bl);
        }
    }
}

