/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Checkable
 */
package com.google.android.material.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import androidx.cardview.widget.CardView;
import com.google.android.material.internal.z;
import e2.b;
import v2.j;
import v2.o;
import v2.r;
import z1.c;
import z1.l;
import z1.m;

public class MaterialCardView
extends CardView
implements Checkable,
r {
    public static final int[] p = new int[]{16842911};
    public static final int[] q = new int[]{0x10100A0};
    public static final int[] r = new int[]{z1.c.state_dragged};
    public static final int s = z1.l.Widget_MaterialComponents_CardView;
    public final b l;
    public boolean m;
    public boolean n;
    public boolean o;

    public MaterialCardView(Context context) {
        this(context, null);
    }

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialCardViewStyle);
    }

    public MaterialCardView(Context context, AttributeSet object, int n3) {
        int n4 = s;
        super(y2.a.d(context, (AttributeSet)object, n3, n4), (AttributeSet)object, n3);
        this.n = false;
        this.o = false;
        this.m = true;
        context = z.i(this.getContext(), (AttributeSet)object, z1.m.MaterialCardView, n3, n4, new int[0]);
        object = new b(this, (AttributeSet)object, n3, n4);
        this.l = object;
        ((b)object).J(super.getCardBackgroundColor());
        ((b)object).Z(super.getContentPaddingLeft(), super.getContentPaddingTop(), super.getContentPaddingRight(), super.getContentPaddingBottom());
        ((b)object).G((TypedArray)context);
        context.recycle();
    }

    private RectF getBoundsAsRectF() {
        RectF rectF = new RectF();
        rectF.set(this.l.j().getBounds());
        return rectF;
    }

    public final void g() {
        if (Build.VERSION.SDK_INT > 26) {
            this.l.i();
        }
    }

    @Override
    public ColorStateList getCardBackgroundColor() {
        return this.l.k();
    }

    public ColorStateList getCardForegroundColor() {
        return this.l.l();
    }

    public float getCardViewRadius() {
        return MaterialCardView.super.getRadius();
    }

    public Drawable getCheckedIcon() {
        return this.l.m();
    }

    public int getCheckedIconGravity() {
        return this.l.n();
    }

    public int getCheckedIconMargin() {
        return this.l.o();
    }

    public int getCheckedIconSize() {
        return this.l.p();
    }

    public ColorStateList getCheckedIconTint() {
        return this.l.q();
    }

    @Override
    public int getContentPaddingBottom() {
        return this.l.A().bottom;
    }

    @Override
    public int getContentPaddingLeft() {
        return this.l.A().left;
    }

    @Override
    public int getContentPaddingRight() {
        return this.l.A().right;
    }

    @Override
    public int getContentPaddingTop() {
        return this.l.A().top;
    }

    public float getProgress() {
        return this.l.u();
    }

    @Override
    public float getRadius() {
        return this.l.s();
    }

    public ColorStateList getRippleColor() {
        return this.l.v();
    }

    public o getShapeAppearanceModel() {
        return this.l.w();
    }

    @Deprecated
    public int getStrokeColor() {
        return this.l.x();
    }

    public ColorStateList getStrokeColorStateList() {
        return this.l.y();
    }

    public int getStrokeWidth() {
        return this.l.z();
    }

    public boolean h() {
        b b3 = this.l;
        return b3 != null && b3.D();
    }

    public boolean i() {
        return this.o;
    }

    public boolean isChecked() {
        return this.n;
    }

    public void j(int n3, int n4, int n5, int n6) {
        super.setContentPadding(n3, n4, n5, n6);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.l.d0();
        v2.j.f((View)this, this.l.j());
    }

    public int[] onCreateDrawableState(int n3) {
        int[] nArray = super.onCreateDrawableState(n3 + 3);
        if (this.h()) {
            View.mergeDrawableStates((int[])nArray, (int[])p);
        }
        if (this.isChecked()) {
            View.mergeDrawableStates((int[])nArray, (int[])q);
        }
        if (this.i()) {
            View.mergeDrawableStates((int[])nArray, (int[])r);
        }
        return nArray;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)"androidx.cardview.widget.CardView");
        accessibilityEvent.setChecked(this.isChecked());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)"androidx.cardview.widget.CardView");
        accessibilityNodeInfo.setCheckable(this.h());
        accessibilityNodeInfo.setClickable(this.isClickable());
        accessibilityNodeInfo.setChecked(this.isChecked());
    }

    @Override
    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        this.l.H(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    public void setBackground(Drawable drawable) {
        this.setBackgroundDrawable(drawable);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (this.m) {
            if (!this.l.C()) {
                this.l.I(true);
            }
            super.setBackgroundDrawable(drawable);
        }
    }

    public void setBackgroundInternal(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    @Override
    public void setCardBackgroundColor(int n3) {
        this.l.J(ColorStateList.valueOf((int)n3));
    }

    @Override
    public void setCardBackgroundColor(ColorStateList colorStateList) {
        this.l.J(colorStateList);
    }

    @Override
    public void setCardElevation(float f3) {
        super.setCardElevation(f3);
        this.l.f0();
    }

    public void setCardForegroundColor(ColorStateList colorStateList) {
        this.l.K(colorStateList);
    }

    public void setCheckable(boolean bl) {
        this.l.L(bl);
    }

    public void setChecked(boolean bl) {
        if (this.n != bl) {
            this.toggle();
        }
    }

    public void setCheckedIcon(Drawable drawable) {
        this.l.O(drawable);
    }

    public void setCheckedIconGravity(int n3) {
        if (this.l.n() != n3) {
            this.l.P(n3);
        }
    }

    public void setCheckedIconMargin(int n3) {
        this.l.Q(n3);
    }

    public void setCheckedIconMarginResource(int n3) {
        if (n3 != -1) {
            this.l.Q(this.getResources().getDimensionPixelSize(n3));
        }
    }

    public void setCheckedIconResource(int n3) {
        this.l.O(d.a.b(this.getContext(), n3));
    }

    public void setCheckedIconSize(int n3) {
        this.l.R(n3);
    }

    public void setCheckedIconSizeResource(int n3) {
        if (n3 != 0) {
            this.l.R(this.getResources().getDimensionPixelSize(n3));
        }
    }

    public void setCheckedIconTint(ColorStateList colorStateList) {
        this.l.S(colorStateList);
    }

    public void setClickable(boolean bl) {
        super.setClickable(bl);
        b b3 = this.l;
        if (b3 != null) {
            b3.d0();
        }
    }

    @Override
    public void setContentPadding(int n3, int n4, int n5, int n6) {
        this.l.Z(n3, n4, n5, n6);
    }

    public void setDragged(boolean bl) {
        if (this.o != bl) {
            this.o = bl;
            this.refreshDrawableState();
            this.g();
            this.invalidate();
        }
    }

    @Override
    public void setMaxCardElevation(float f3) {
        super.setMaxCardElevation(f3);
        this.l.h0();
    }

    public void setOnCheckedChangeListener(a a4) {
    }

    @Override
    public void setPreventCornerOverlap(boolean bl) {
        super.setPreventCornerOverlap(bl);
        this.l.h0();
        this.l.e0();
    }

    public void setProgress(float f3) {
        this.l.U(f3);
    }

    @Override
    public void setRadius(float f3) {
        super.setRadius(f3);
        this.l.T(f3);
    }

    public void setRippleColor(ColorStateList colorStateList) {
        this.l.V(colorStateList);
    }

    public void setRippleColorResource(int n3) {
        this.l.V(d.a.a(this.getContext(), n3));
    }

    @Override
    public void setShapeAppearanceModel(o o3) {
        this.setClipToOutline(o3.v(this.getBoundsAsRectF()));
        this.l.W(o3);
    }

    public void setStrokeColor(int n3) {
        this.setStrokeColor(ColorStateList.valueOf((int)n3));
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        this.l.X(colorStateList);
        this.invalidate();
    }

    public void setStrokeWidth(int n3) {
        this.l.Y(n3);
        this.invalidate();
    }

    @Override
    public void setUseCompatPadding(boolean bl) {
        super.setUseCompatPadding(bl);
        this.l.h0();
        this.l.e0();
    }

    public void toggle() {
        if (this.h() && this.isEnabled()) {
            this.n ^= true;
            this.refreshDrawableState();
            this.g();
            this.l.N(this.n, true);
        }
    }

    public static interface a {
    }
}

