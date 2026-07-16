/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.FrameLayout
 */
package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import m.c;
import m.d;
import n.a;
import n.b;

public class CardView
extends FrameLayout {
    public static final int[] j = new int[]{0x1010031};
    public static final n.c k;
    public boolean c;
    public boolean d;
    public int e;
    public int f;
    public final Rect g;
    public final Rect h;
    public final b i;

    static {
        a a4 = new a();
        k = a4;
        a4.g();
    }

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, m.a.cardViewStyle);
    }

    public CardView(Context context, AttributeSet object, int n3) {
        super(context, object, n3);
        b b3;
        Rect rect;
        this.g = rect = new Rect();
        this.h = new Rect();
        this.i = b3 = new b(this){
            public Drawable a;
            public final CardView b;
            {
                this.b = cardView;
            }

            @Override
            public void a(int n3, int n4, int n5, int n6) {
                this.b.h.set(n3, n4, n5, n6);
                CardView cardView = this.b;
                Rect rect = cardView.g;
                CardView.super.setPadding(n3 + rect.left, n4 + rect.top, n5 + rect.right, n6 + rect.bottom);
            }

            @Override
            public View b() {
                return this.b;
            }

            @Override
            public void c(Drawable drawable) {
                this.a = drawable;
                this.b.setBackgroundDrawable(drawable);
            }

            @Override
            public boolean d() {
                return this.b.getPreventCornerOverlap();
            }

            @Override
            public boolean e() {
                return this.b.getUseCompatPadding();
            }

            @Override
            public Drawable f() {
                return this.a;
            }
        };
        TypedArray typedArray = context.obtainStyledAttributes(object, m.d.CardView, n3, m.c.CardView);
        n3 = m.d.CardView_cardBackgroundColor;
        if (typedArray.hasValue(n3)) {
            object = typedArray.getColorStateList(n3);
        } else {
            object = this.getContext().obtainStyledAttributes(j);
            n3 = object.getColor(0, 0);
            object.recycle();
            object = new float[3];
            Color.colorToHSV((int)n3, (float[])object);
            n3 = object[2] > 0.5f ? this.getResources().getColor(m.b.cardview_light_background) : this.getResources().getColor(m.b.cardview_dark_background);
            object = ColorStateList.valueOf((int)n3);
        }
        float f3 = typedArray.getDimension(m.d.CardView_cardCornerRadius, 0.0f);
        float f4 = typedArray.getDimension(m.d.CardView_cardElevation, 0.0f);
        float f5 = typedArray.getDimension(m.d.CardView_cardMaxElevation, 0.0f);
        this.c = typedArray.getBoolean(m.d.CardView_cardUseCompatPadding, false);
        this.d = typedArray.getBoolean(m.d.CardView_cardPreventCornerOverlap, true);
        n3 = typedArray.getDimensionPixelSize(m.d.CardView_contentPadding, 0);
        rect.left = typedArray.getDimensionPixelSize(m.d.CardView_contentPaddingLeft, n3);
        rect.top = typedArray.getDimensionPixelSize(m.d.CardView_contentPaddingTop, n3);
        rect.right = typedArray.getDimensionPixelSize(m.d.CardView_contentPaddingRight, n3);
        rect.bottom = typedArray.getDimensionPixelSize(m.d.CardView_contentPaddingBottom, n3);
        if (f4 > f5) {
            f5 = f4;
        }
        this.e = typedArray.getDimensionPixelSize(m.d.CardView_android_minWidth, 0);
        this.f = typedArray.getDimensionPixelSize(m.d.CardView_android_minHeight, 0);
        typedArray.recycle();
        k.h(b3, context, (ColorStateList)object, f3, f4, f5);
    }

    public ColorStateList getCardBackgroundColor() {
        return k.a(this.i);
    }

    public float getCardElevation() {
        return k.m(this.i);
    }

    public int getContentPaddingBottom() {
        return this.g.bottom;
    }

    public int getContentPaddingLeft() {
        return this.g.left;
    }

    public int getContentPaddingRight() {
        return this.g.right;
    }

    public int getContentPaddingTop() {
        return this.g.top;
    }

    public float getMaxCardElevation() {
        return k.f(this.i);
    }

    public boolean getPreventCornerOverlap() {
        return this.d;
    }

    public float getRadius() {
        return k.j(this.i);
    }

    public boolean getUseCompatPadding() {
        return this.c;
    }

    public void onMeasure(int n3, int n4) {
        n.c c3 = k;
        if (!(c3 instanceof a)) {
            int n5 = View.MeasureSpec.getMode((int)n3);
            if (n5 == Integer.MIN_VALUE || n5 == 0x40000000) {
                n3 = View.MeasureSpec.makeMeasureSpec((int)Math.max((int)Math.ceil(c3.k(this.i)), View.MeasureSpec.getSize((int)n3)), (int)n5);
            }
            n5 = View.MeasureSpec.getMode((int)n4);
            if (n5 == Integer.MIN_VALUE || n5 == 0x40000000) {
                n4 = View.MeasureSpec.makeMeasureSpec((int)Math.max((int)Math.ceil(c3.c(this.i)), View.MeasureSpec.getSize((int)n4)), (int)n5);
            }
            super.onMeasure(n3, n4);
            return;
        }
        super.onMeasure(n3, n4);
    }

    public void setCardBackgroundColor(int n3) {
        k.e(this.i, ColorStateList.valueOf((int)n3));
    }

    public void setCardBackgroundColor(ColorStateList colorStateList) {
        k.e(this.i, colorStateList);
    }

    public void setCardElevation(float f3) {
        k.n(this.i, f3);
    }

    public void setContentPadding(int n3, int n4, int n5, int n6) {
        this.g.set(n3, n4, n5, n6);
        k.o(this.i);
    }

    public void setMaxCardElevation(float f3) {
        k.d(this.i, f3);
    }

    public void setMinimumHeight(int n3) {
        this.f = n3;
        super.setMinimumHeight(n3);
    }

    public void setMinimumWidth(int n3) {
        this.e = n3;
        super.setMinimumWidth(n3);
    }

    public void setPadding(int n3, int n4, int n5, int n6) {
    }

    public void setPaddingRelative(int n3, int n4, int n5, int n6) {
    }

    public void setPreventCornerOverlap(boolean bl) {
        if (bl != this.d) {
            this.d = bl;
            k.b(this.i);
        }
    }

    public void setRadius(float f3) {
        k.i(this.i, f3);
    }

    public void setUseCompatPadding(boolean bl) {
        if (this.c != bl) {
            this.c = bl;
            k.l(this.i);
        }
    }
}

