/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Layout$Alignment
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Button
 *  android.widget.Checkable
 *  android.widget.CompoundButton
 *  android.widget.LinearLayout$LayoutParams
 */
package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.button.MaterialButtonGroup;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.button.a;
import com.google.android.material.button.d;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import v2.i;
import v2.j;
import v2.o;
import v2.r;
import v2.w;
import v2.x;
import x0.k;
import x0.l;
import z1.m;

public class MaterialButton
extends AppCompatButton
implements Checkable,
r {
    public static final int[] H = new int[]{16842911};
    public static final int[] I = new int[]{0x10100A0};
    public static final int J = z1.l.Widget_MaterialComponents_Button;
    public static final int K = z1.c.materialSizeOverlay;
    public static final x0.i L = new x0.i("widthIncrease"){

        public float c(MaterialButton materialButton) {
            return materialButton.getDisplayedWidthIncrease();
        }

        public void d(MaterialButton materialButton, float f3) {
            materialButton.setDisplayedWidthIncrease(f3);
        }
    };
    public boolean A;
    public int B;
    public x C;
    public int D;
    public float E;
    public float F;
    public k G;
    public final d f;
    public final LinkedHashSet g;
    public c h;
    public PorterDuff.Mode i;
    public ColorStateList j;
    public Drawable k;
    public String l;
    public int m;
    public int n;
    public int o;
    public int p;
    public boolean q;
    public boolean r;
    public int s;
    public int t;
    public float u;
    public int v;
    public int w;
    public LinearLayout.LayoutParams x;
    public boolean y;
    public int z;

    public MaterialButton(Context context) {
        this(context, null);
    }

    public MaterialButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialButtonStyle);
    }

    public MaterialButton(Context object, AttributeSet attributeSet, int n3) {
        int n4 = J;
        super(y2.a.e((Context)object, attributeSet, n3, n4, new int[]{K}), attributeSet, n3);
        this.g = new LinkedHashSet();
        boolean bl = false;
        this.q = false;
        this.r = false;
        this.t = -1;
        this.u = -1.0f;
        this.v = -1;
        this.w = -1;
        this.B = -1;
        object = this.getContext();
        TypedArray typedArray = com.google.android.material.internal.z.i((Context)object, attributeSet, z1.m.MaterialButton, n3, n4, new int[0]);
        this.p = typedArray.getDimensionPixelSize(z1.m.MaterialButton_iconPadding, 0);
        this.i = c0.n(typedArray.getInt(z1.m.MaterialButton_iconTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.j = s2.c.a(this.getContext(), typedArray, z1.m.MaterialButton_iconTint);
        this.k = s2.c.e(this.getContext(), typedArray, z1.m.MaterialButton_icon);
        this.s = typedArray.getInteger(z1.m.MaterialButton_iconGravity, 1);
        this.m = typedArray.getDimensionPixelSize(z1.m.MaterialButton_iconSize, 0);
        w w3 = v2.w.b((Context)object, typedArray, z1.m.MaterialButton_shapeAppearance);
        object = w3 != null ? w3.c(true) : v2.o.e((Context)object, attributeSet, n3, n4).m();
        boolean bl2 = typedArray.getBoolean(z1.m.MaterialButton_opticalCenterEnabled, false);
        object = new d(this, (o)object);
        this.f = object;
        ((d)object).t(typedArray);
        this.setCheckedInternal(typedArray.getBoolean(z1.m.MaterialButton_android_checked, false));
        if (w3 != null) {
            ((d)object).z(this.f());
            ((d)object).F(w3);
        }
        this.setOpticalCenterEnabled(bl2);
        typedArray.recycle();
        this.setCompoundDrawablePadding(this.p);
        if (this.k != null) {
            bl = true;
        }
        this.r(bl);
    }

    public static /* synthetic */ void a(MaterialButton materialButton) {
        materialButton.z = materialButton.getOpticalCenterShift();
        materialButton.t();
        materialButton.invalidate();
    }

    public static /* synthetic */ void b(MaterialButton materialButton, float f3) {
        int n3 = (int)(f3 * 0.11f);
        if (materialButton.z != n3) {
            materialButton.z = n3;
            materialButton.t();
            materialButton.invalidate();
        }
    }

    private Layout.Alignment getActualTextAlignment() {
        int n3 = this.getTextAlignment();
        if (n3 != 1) {
            if (n3 != 6 && n3 != 3) {
                if (n3 != 4) {
                    return Layout.Alignment.ALIGN_NORMAL;
                }
                return Layout.Alignment.ALIGN_CENTER;
            }
            return Layout.Alignment.ALIGN_OPPOSITE;
        }
        return this.getGravityTextAlignment();
    }

    private float getDisplayedWidthIncrease() {
        return this.E;
    }

    private Layout.Alignment getGravityTextAlignment() {
        int n3 = this.getGravity() & 0x800007;
        if (n3 != 1) {
            if (n3 != 5 && n3 != 0x800005) {
                return Layout.Alignment.ALIGN_NORMAL;
            }
            return Layout.Alignment.ALIGN_OPPOSITE;
        }
        return Layout.Alignment.ALIGN_CENTER;
    }

    private int getOpticalCenterShift() {
        i i3;
        if (this.y && this.A && (i3 = this.f.g()) != null) {
            return (int)(i3.B() * 0.11f);
        }
        return 0;
    }

    private int getTextHeight() {
        String string;
        if (this.getLineCount() > 1) {
            return this.getLayout().getHeight();
        }
        TextPaint textPaint = this.getPaint();
        String string2 = string = this.getText().toString();
        if (this.getTransformationMethod() != null) {
            string2 = this.getTransformationMethod().getTransformation((CharSequence)string, (View)this).toString();
        }
        string = new Rect();
        textPaint.getTextBounds(string2, 0, string2.length(), (Rect)string);
        return Math.min(string.height(), this.getLayout().getHeight());
    }

    private int getTextLayoutWidth() {
        int n3 = this.getLineCount();
        float f3 = 0.0f;
        for (int i3 = 0; i3 < n3; ++i3) {
            f3 = Math.max(f3, this.getLayout().getLineWidth(i3));
        }
        return (int)Math.ceil(f3);
    }

    private void setCheckedInternal(boolean bl) {
        if (this.h() && this.q != bl) {
            this.q = bl;
            this.refreshDrawableState();
            if (this.getParent() instanceof MaterialButtonToggleGroup) {
                ((MaterialButtonToggleGroup)this.getParent()).w(this, this.q);
            }
            if (!this.r) {
                this.r = true;
                Iterator iterator = ((AbstractCollection)this.g).iterator();
                while (iterator.hasNext()) {
                    ((b)iterator.next()).a(this, this.q);
                }
                this.r = false;
            }
        }
    }

    private void setDisplayedWidthIncrease(float f3) {
        if (this.E != f3) {
            this.E = f3;
            this.t();
            this.invalidate();
            if (this.getParent() instanceof MaterialButtonGroup) {
                ((MaterialButtonGroup)this.getParent()).k(this, (int)this.E);
            }
        }
    }

    public void e(b b3) {
        ((AbstractCollection)this.g).add(b3);
    }

    public final l f() {
        return p2.k.h(this.getContext(), z1.c.motionSpringFastSpatial, z1.l.Motion_Material3_Spring_Standard_Fast_Spatial);
    }

    public final void g() {
        k k3;
        this.G = k3 = new k(this, L);
        k3.s(this.f());
    }

    public String getA11yClassName() {
        if (!TextUtils.isEmpty((CharSequence)this.l)) {
            return this.l;
        }
        Class clazz = this.h() ? CompoundButton.class : Button.class;
        return clazz.getName();
    }

    public int getAllowedWidthDecrease() {
        return this.B;
    }

    public ColorStateList getBackgroundTintList() {
        return this.getSupportBackgroundTintList();
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return this.getSupportBackgroundTintMode();
    }

    public int getCornerRadius() {
        if (this.n()) {
            return this.f.b();
        }
        return 0;
    }

    public l getCornerSpringForce() {
        return this.f.c();
    }

    public Drawable getIcon() {
        return this.k;
    }

    public int getIconGravity() {
        return this.s;
    }

    public int getIconPadding() {
        return this.p;
    }

    public int getIconSize() {
        return this.m;
    }

    public ColorStateList getIconTint() {
        return this.j;
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.i;
    }

    public int getInsetBottom() {
        return this.f.d();
    }

    public int getInsetTop() {
        return this.f.e();
    }

    public ColorStateList getRippleColor() {
        if (this.n()) {
            return this.f.i();
        }
        return null;
    }

    public o getShapeAppearanceModel() {
        if (this.n()) {
            return this.f.j();
        }
        throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
    }

    public w getStateListShapeAppearanceModel() {
        if (this.n()) {
            return this.f.k();
        }
        throw new IllegalStateException("Attempted to get StateListShapeAppearanceModel from a MaterialButton which has an overwritten background.");
    }

    public ColorStateList getStrokeColor() {
        if (this.n()) {
            return this.f.l();
        }
        return null;
    }

    public int getStrokeWidth() {
        if (this.n()) {
            return this.f.m();
        }
        return 0;
    }

    @Override
    public ColorStateList getSupportBackgroundTintList() {
        if (this.n()) {
            return this.f.n();
        }
        return super.getSupportBackgroundTintList();
    }

    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.n()) {
            return this.f.o();
        }
        return super.getSupportBackgroundTintMode();
    }

    public boolean h() {
        d d3 = this.f;
        return d3 != null && d3.r();
    }

    public final boolean i() {
        int n3 = this.s;
        return n3 == 3 || n3 == 4;
        {
        }
    }

    public boolean isChecked() {
        return this.q;
    }

    public final boolean j() {
        int n3 = this.s;
        return n3 == 1 || n3 == 2;
        {
        }
    }

    public final boolean k() {
        int n3 = this.s;
        return n3 == 16 || n3 == 32;
        {
        }
    }

    public final boolean l() {
        return this.getParent() instanceof MaterialButtonGroup && ((MaterialButtonGroup)this.getParent()).getOrientation() == 0;
    }

    public final boolean m() {
        return this.getLayoutDirection() == 1;
    }

    public final boolean n() {
        d d3 = this.f;
        return d3 != null && !d3.q();
    }

    public final void o(boolean bl) {
        if (this.C != null) {
            if (this.G == null) {
                this.g();
            }
            if (this.A) {
                int n3 = Math.min(this.D, this.C.e((int[])this.getDrawableState()).a.a(this.getWidth()));
                this.G.o(n3);
                if (bl) {
                    this.G.t();
                }
            }
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.n()) {
            v2.j.f((View)this, this.f.g());
        }
    }

    public int[] onCreateDrawableState(int n3) {
        int[] nArray = super.onCreateDrawableState(n3 + 2);
        if (this.h()) {
            View.mergeDrawableStates((int[])nArray, (int[])H);
        }
        if (this.isChecked()) {
            View.mergeDrawableStates((int[])nArray, (int[])I);
        }
        return nArray;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)this.getA11yClassName());
        accessibilityEvent.setChecked(this.isChecked());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)this.getA11yClassName());
        accessibilityNodeInfo.setCheckable(this.h());
        accessibilityNodeInfo.setChecked(this.isChecked());
        accessibilityNodeInfo.setClickable(this.isClickable());
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        this.s(this.getMeasuredWidth(), this.getMeasuredHeight());
        n3 = this.getResources().getConfiguration().orientation;
        if (this.t != n3) {
            this.t = n3;
            this.u = -1.0f;
        }
        if (this.u == -1.0f) {
            this.u = this.getMeasuredWidth();
            if (this.x == null && this.getParent() instanceof MaterialButtonGroup && ((MaterialButtonGroup)this.getParent()).getButtonSizeChange() != null) {
                this.x = (LinearLayout.LayoutParams)this.getLayoutParams();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.x);
                layoutParams.width = (int)this.u;
                this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            }
        }
        if (this.B == -1) {
            if (this.k == null) {
                n3 = 0;
            } else {
                n5 = this.getIconPadding();
                n3 = n4 = this.m;
                if (n4 == 0) {
                    n3 = this.k.getIntrinsicWidth();
                }
                n3 = n5 + n3;
            }
            this.B = this.getMeasuredWidth() - this.getTextLayoutWidth() - n3;
        }
        if (this.v == -1) {
            this.v = this.getPaddingStart();
        }
        if (this.w == -1) {
            this.w = this.getPaddingEnd();
        }
        this.A = this.l();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.setChecked(parcelable.e);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = this.q;
        return savedState;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
        super.onTextChanged(charSequence, n3, n4, n5);
        this.s(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    public void p() {
        LinearLayout.LayoutParams layoutParams = this.x;
        if (layoutParams != null) {
            this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.x = null;
            this.u = -1.0f;
        }
    }

    public boolean performClick() {
        if (this.isEnabled() && this.f.s()) {
            this.toggle();
        }
        return super.performClick();
    }

    public final void q() {
        if (this.j()) {
            this.setCompoundDrawablesRelative(this.k, null, null, null);
            return;
        }
        if (this.i()) {
            this.setCompoundDrawablesRelative(null, null, this.k, null);
            return;
        }
        if (this.k()) {
            this.setCompoundDrawablesRelative(null, this.k, null, null);
        }
    }

    public final void r(boolean bl) {
        Drawable drawable = this.k;
        if (drawable != null) {
            int n3;
            this.k = drawable = h0.a.r(drawable).mutate();
            drawable.setTintList(this.j);
            drawable = this.i;
            if (drawable != null) {
                this.k.setTintMode((PorterDuff.Mode)drawable);
            }
            if ((n3 = this.m) == 0) {
                n3 = this.k.getIntrinsicWidth();
            }
            int n4 = this.m;
            if (n4 == 0) {
                n4 = this.k.getIntrinsicHeight();
            }
            drawable = this.k;
            int n5 = this.n;
            int n6 = this.o;
            drawable.setBounds(n5, n6, n3 + n5, n4 + n6);
            this.k.setVisible(true, bl);
        }
        if (bl) {
            this.q();
            return;
        }
        Drawable drawable2 = this.getCompoundDrawablesRelative();
        drawable = drawable2[0];
        Drawable drawable3 = drawable2[1];
        drawable2 = drawable2[2];
        if (this.j() && drawable != this.k || this.i() && drawable2 != this.k || this.k() && drawable3 != this.k) {
            this.q();
        }
    }

    public void refreshDrawableState() {
        int[] nArray;
        super.refreshDrawableState();
        if (this.k != null && this.k.setState(nArray = this.getDrawableState())) {
            this.invalidate();
        }
    }

    public final void s(int n3, int n4) {
        if (this.k != null && this.getLayout() != null) {
            if (!this.j() && !this.i()) {
                if (this.k()) {
                    int n5;
                    this.n = 0;
                    if (this.s == 16) {
                        this.o = 0;
                        this.r(false);
                        return;
                    }
                    n3 = n5 = this.m;
                    if (n5 == 0) {
                        n3 = this.k.getIntrinsicHeight();
                    }
                    if (this.o != (n3 = Math.max(0, (n4 - this.getTextHeight() - this.getPaddingTop() - n3 - this.p - this.getPaddingBottom()) / 2))) {
                        this.o = n3;
                        this.r(false);
                        return;
                    }
                }
            } else {
                this.o = 0;
                Layout.Alignment alignment = this.getActualTextAlignment();
                n4 = this.s;
                boolean bl = true;
                if (!(n4 == 1 || n4 == 3 || n4 == 2 && alignment == Layout.Alignment.ALIGN_NORMAL || n4 == 4 && alignment == Layout.Alignment.ALIGN_OPPOSITE)) {
                    int n6;
                    n4 = n6 = this.m;
                    if (n6 == 0) {
                        n4 = this.k.getIntrinsicWidth();
                    }
                    n3 = n4 = n3 - this.getTextLayoutWidth() - this.getPaddingEnd() - n4 - this.p - this.getPaddingStart();
                    if (alignment == Layout.Alignment.ALIGN_CENTER) {
                        n3 = n4 / 2;
                    }
                    boolean bl2 = this.m();
                    if (this.s != 4) {
                        bl = false;
                    }
                    n4 = n3;
                    if (bl2 != bl) {
                        n4 = -n3;
                    }
                    if (this.n != n4) {
                        this.n = n4;
                        this.r(false);
                        return;
                    }
                } else {
                    this.n = 0;
                    this.r(false);
                }
            }
        }
    }

    public void setA11yClassName(String string) {
        this.l = string;
    }

    public void setBackground(Drawable drawable) {
        this.setBackgroundDrawable(drawable);
    }

    public void setBackgroundColor(int n3) {
        if (this.n()) {
            this.f.u(n3);
            return;
        }
        super.setBackgroundColor(n3);
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {
        if (this.n()) {
            if (drawable != this.getBackground()) {
                Log.w((String)"MaterialButton", (String)"MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
                this.f.v();
                super.setBackgroundDrawable(drawable);
                return;
            }
            this.getBackground().setState(drawable.getState());
            return;
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override
    public void setBackgroundResource(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.setBackgroundDrawable(drawable);
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        this.setSupportBackgroundTintList(colorStateList);
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        this.setSupportBackgroundTintMode(mode);
    }

    public void setCheckable(boolean bl) {
        if (this.n()) {
            this.f.w(bl);
        }
    }

    public void setChecked(boolean bl) {
        this.setCheckedInternal(bl);
    }

    public void setCornerRadius(int n3) {
        if (this.n()) {
            this.f.x(n3);
        }
    }

    public void setCornerRadiusResource(int n3) {
        if (this.n()) {
            this.setCornerRadius(this.getResources().getDimensionPixelSize(n3));
        }
    }

    public void setCornerSpringForce(l l3) {
        this.f.z(l3);
    }

    public void setDisplayedWidthDecrease(int n3) {
        this.F = Math.min(n3, this.B);
        this.t();
        this.invalidate();
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        if (this.n()) {
            this.f.g().h0(f3);
        }
    }

    public void setIcon(Drawable drawable) {
        if (this.k != drawable) {
            this.k = drawable;
            this.r(true);
            this.s(this.getMeasuredWidth(), this.getMeasuredHeight());
        }
    }

    public void setIconGravity(int n3) {
        if (this.s != n3) {
            this.s = n3;
            this.s(this.getMeasuredWidth(), this.getMeasuredHeight());
        }
    }

    public void setIconPadding(int n3) {
        if (this.p != n3) {
            this.p = n3;
            this.setCompoundDrawablePadding(n3);
        }
    }

    public void setIconResource(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.setIcon(drawable);
    }

    public void setIconSize(int n3) {
        if (n3 >= 0) {
            if (this.m != n3) {
                this.m = n3;
                this.r(true);
            }
            return;
        }
        throw new IllegalArgumentException("iconSize cannot be less than 0");
    }

    public void setIconTint(ColorStateList colorStateList) {
        if (this.j != colorStateList) {
            this.j = colorStateList;
            this.r(false);
        }
    }

    public void setIconTintMode(PorterDuff.Mode mode) {
        if (this.i != mode) {
            this.i = mode;
            this.r(false);
        }
    }

    public void setIconTintResource(int n3) {
        this.setIconTint(d.a.a(this.getContext(), n3));
    }

    public void setInsetBottom(int n3) {
        this.f.A(n3);
    }

    public void setInsetTop(int n3) {
        this.f.B(n3);
    }

    public void setInternalBackground(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    public void setOnPressedChangeListenerInternal(c c3) {
        this.h = c3;
    }

    public void setOpticalCenterEnabled(boolean bl) {
        if (this.y != bl) {
            this.y = bl;
            if (bl) {
                this.f.y(new a(this));
            } else {
                this.f.y(null);
            }
            this.post(new com.google.android.material.button.b(this));
        }
    }

    public void setPressed(boolean bl) {
        c c3 = this.h;
        if (c3 != null) {
            c3.a(this, bl);
        }
        super.setPressed(bl);
        this.o(false);
    }

    public void setRippleColor(ColorStateList colorStateList) {
        if (this.n()) {
            this.f.C(colorStateList);
        }
    }

    public void setRippleColorResource(int n3) {
        if (this.n()) {
            this.setRippleColor(d.a.a(this.getContext(), n3));
        }
    }

    @Override
    public void setShapeAppearanceModel(o o3) {
        if (this.n()) {
            this.f.D(o3);
            return;
        }
        throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    public void setShouldDrawSurfaceColorStroke(boolean bl) {
        if (this.n()) {
            this.f.E(bl);
        }
    }

    public void setSizeChange(x x3) {
        if (this.C != x3) {
            this.C = x3;
            this.o(true);
        }
    }

    public void setStateListShapeAppearanceModel(w w3) {
        if (this.n()) {
            if (this.f.c() == null && w3.f()) {
                this.f.z(this.f());
            }
            this.f.F(w3);
            return;
        }
        throw new IllegalStateException("Attempted to set StateListShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        if (this.n()) {
            this.f.G(colorStateList);
        }
    }

    public void setStrokeColorResource(int n3) {
        if (this.n()) {
            this.setStrokeColor(d.a.a(this.getContext(), n3));
        }
    }

    public void setStrokeWidth(int n3) {
        if (this.n()) {
            this.f.H(n3);
        }
    }

    public void setStrokeWidthResource(int n3) {
        if (this.n()) {
            this.setStrokeWidth(this.getResources().getDimensionPixelSize(n3));
        }
    }

    @Override
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.n()) {
            this.f.I(colorStateList);
            return;
        }
        super.setSupportBackgroundTintList(colorStateList);
    }

    @Override
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.n()) {
            this.f.J(mode);
            return;
        }
        super.setSupportBackgroundTintMode(mode);
    }

    public void setTextAlignment(int n3) {
        super.setTextAlignment(n3);
        this.s(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    public void setToggleCheckedStateOnClick(boolean bl) {
        this.f.K(bl);
    }

    public void setWidth(int n3) {
        this.u = -1.0f;
        super.setWidth(n3);
    }

    public void setWidthChangeMax(int n3) {
        if (this.D != n3) {
            this.D = n3;
            this.o(true);
        }
    }

    public final void t() {
        int n3 = (int)(this.E - this.F);
        int n4 = n3 / 2 + this.z;
        this.getLayoutParams().width = (int)(this.u + (float)n3);
        this.setPaddingRelative(this.v + n4, this.getPaddingTop(), this.w + n3 - n4, this.getPaddingBottom());
    }

    public void toggle() {
        this.setChecked(this.q ^ true);
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState b(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] c(int n3) {
                return new SavedState[n3];
            }
        };
        public boolean e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                this.getClass().getClassLoader();
            }
            this.p(parcel);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final void p(Parcel parcel) {
            int n3 = parcel.readInt();
            boolean bl = true;
            if (n3 != 1) {
                bl = false;
            }
            this.e = bl;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.e ? 1 : 0);
        }
    }

    public static interface b {
        public void a(MaterialButton var1, boolean var2);
    }

    public static interface c {
        public void a(MaterialButton var1, boolean var2);
    }
}

