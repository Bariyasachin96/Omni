/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.g;
import d.a;
import f0.h;

public class m0 {
    public final Context a;
    public final TypedArray b;
    public TypedValue c;

    public m0(Context context, TypedArray typedArray) {
        this.a = context;
        this.b = typedArray;
    }

    public static m0 t(Context context, int n3, int[] nArray) {
        return new m0(context, context.obtainStyledAttributes(n3, nArray));
    }

    public static m0 u(Context context, AttributeSet attributeSet, int[] nArray) {
        return new m0(context, context.obtainStyledAttributes(attributeSet, nArray));
    }

    public static m0 v(Context context, AttributeSet attributeSet, int[] nArray, int n3, int n4) {
        return new m0(context, context.obtainStyledAttributes(attributeSet, nArray, n3, n4));
    }

    public boolean a(int n3, boolean bl) {
        return this.b.getBoolean(n3, bl);
    }

    public int b(int n3, int n4) {
        return this.b.getColor(n3, n4);
    }

    public ColorStateList c(int n3) {
        ColorStateList colorStateList;
        int n4;
        if (this.b.hasValue(n3) && (n4 = this.b.getResourceId(n3, 0)) != 0 && (colorStateList = d.a.a(this.a, n4)) != null) {
            return colorStateList;
        }
        return this.b.getColorStateList(n3);
    }

    public float d(int n3, float f3) {
        return this.b.getDimension(n3, f3);
    }

    public int e(int n3, int n4) {
        return this.b.getDimensionPixelOffset(n3, n4);
    }

    public int f(int n3, int n4) {
        return this.b.getDimensionPixelSize(n3, n4);
    }

    public Drawable g(int n3) {
        int n4;
        if (this.b.hasValue(n3) && (n4 = this.b.getResourceId(n3, 0)) != 0) {
            return d.a.b(this.a, n4);
        }
        return this.b.getDrawable(n3);
    }

    public Drawable h(int n3) {
        if (this.b.hasValue(n3) && (n3 = this.b.getResourceId(n3, 0)) != 0) {
            return g.b().d(this.a, n3, true);
        }
        return null;
    }

    public float i(int n3, float f3) {
        return this.b.getFloat(n3, f3);
    }

    public Typeface j(int n3, int n4, h.e e3) {
        if ((n3 = this.b.getResourceId(n3, 0)) == 0) {
            return null;
        }
        if (this.c == null) {
            this.c = new TypedValue();
        }
        return h.h(this.a, n3, this.c, n4, e3);
    }

    public int k(int n3, int n4) {
        return this.b.getInt(n3, n4);
    }

    public int l(int n3, int n4) {
        return this.b.getInteger(n3, n4);
    }

    public int m(int n3, int n4) {
        return this.b.getLayoutDimension(n3, n4);
    }

    public int n(int n3, int n4) {
        return this.b.getResourceId(n3, n4);
    }

    public String o(int n3) {
        return this.b.getString(n3);
    }

    public CharSequence p(int n3) {
        return this.b.getText(n3);
    }

    public CharSequence[] q(int n3) {
        return this.b.getTextArray(n3);
    }

    public TypedArray r() {
        return this.b;
    }

    public boolean s(int n3) {
        return this.b.hasValue(n3);
    }

    public TypedValue w(int n3) {
        return this.b.peekValue(n3);
    }

    public void x() {
        this.b.recycle();
    }
}

