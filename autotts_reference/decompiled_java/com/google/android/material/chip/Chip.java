/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Outline
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Bundle
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.PointerIcon
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewOutlineProvider
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.TextView$BufferType
 */
package com.google.android.material.chip;

import a2.h;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.a;
import com.google.android.material.internal.j;
import com.google.android.material.internal.z;
import java.util.List;
import o0.x0;
import p0.s;
import s2.b;
import s2.d;
import s2.f;
import v2.i;
import v2.o;
import v2.r;
import z1.k;
import z1.l;
import z1.m;

public class Chip
extends AppCompatCheckBox
implements a.a,
r,
j {
    public static final Rect A;
    public static final int[] B;
    public static final int[] C;
    public static final int z;
    public a g;
    public InsetDrawable h;
    public RippleDrawable i;
    public View.OnClickListener j;
    public CompoundButton.OnCheckedChangeListener k;
    public j.a l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public int r;
    public int s;
    public CharSequence t;
    public final c u;
    public boolean v;
    public final Rect w;
    public final RectF x;
    public final f y;

    static {
        z = z1.l.Widget_MaterialComponents_Chip_Action;
        A = new Rect();
        B = new int[]{0x10100A1};
        C = new int[]{16842911};
    }

    public Chip(Context context) {
        this(context, null);
    }

    public Chip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.chipStyle);
    }

    public Chip(Context object, AttributeSet attributeSet, int n3) {
        int n4 = z;
        super(y2.a.d((Context)object, attributeSet, n3, n4), attributeSet, n3);
        this.w = new Rect();
        this.x = new RectF();
        this.y = new f(this){
            public final Chip a;
            {
                this.a = chip;
            }

            @Override
            public void a(int n3) {
            }

            @Override
            public void b(Typeface object, boolean bl) {
                Chip chip = this.a;
                object = chip.g.f3() ? this.a.g.z1() : this.a.getText();
                chip.setText((CharSequence)object);
                this.a.requestLayout();
                this.a.invalidate();
            }
        };
        Context context = this.getContext();
        this.C(attributeSet);
        object = a.N0(context, attributeSet, n3, n4);
        this.o(context, attributeSet, n3);
        this.setChipDrawable((a)object);
        ((i)object).h0(this.getElevation());
        attributeSet = com.google.android.material.internal.z.i(context, attributeSet, z1.m.Chip, n3, n4, new int[0]);
        boolean bl = attributeSet.hasValue(z1.m.Chip_shapeAppearance);
        attributeSet.recycle();
        this.u = new c(this, this);
        this.x();
        if (!bl) {
            this.p();
        }
        this.setChecked(this.m);
        this.setText(((a)object).z1());
        this.setEllipsize(((a)object).t1());
        this.B();
        if (!this.g.f3()) {
            this.setLines(1);
            this.setHorizontallyScrolling(true);
        }
        this.setGravity(8388627);
        this.A();
        if (this.v()) {
            this.setMinHeight(this.s);
        }
        this.r = this.getLayoutDirection();
        super.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new g2.a(this));
    }

    private void C(AttributeSet attributeSet) {
        block6: {
            block7: {
                block8: {
                    block9: {
                        block10: {
                            block5: {
                                if (attributeSet == null) break block5;
                                if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background") != null) {
                                    Log.w((String)"Chip", (String)"Do not set the background; Chip manages its own background drawable.");
                                }
                                if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableLeft") != null) break block6;
                                if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableStart") != null) break block7;
                                if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableEnd") != null) break block8;
                                if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableRight") != null) break block9;
                                if (!attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", true) || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", 1) != 1 || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "minLines", 1) != 1 || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLines", 1) != 1) break block10;
                                if (attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", 8388627) != 8388627) {
                                    Log.w((String)"Chip", (String)"Chip text must be vertically center and start aligned");
                                }
                            }
                            return;
                        }
                        throw new UnsupportedOperationException("Chip does not support multi-line text");
                    }
                    throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
                }
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            }
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
    }

    public static /* synthetic */ void b(Chip chip, CompoundButton compoundButton, boolean bl) {
        j.a a4 = chip.l;
        if (a4 != null) {
            a4.a(chip, bl);
        }
        if ((chip = chip.k) != null) {
            chip.onCheckedChanged(compoundButton, bl);
        }
    }

    private RectF getCloseIconTouchBounds() {
        this.x.setEmpty();
        if (this.n() && this.j != null) {
            this.g.q1(this.x);
        }
        return this.x;
    }

    private Rect getCloseIconTouchBoundsInt() {
        RectF rectF = this.getCloseIconTouchBounds();
        this.w.set((int)rectF.left, (int)rectF.top, (int)rectF.right, (int)rectF.bottom);
        return this.w;
    }

    private d getTextAppearance() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.A1();
        }
        return null;
    }

    public static /* synthetic */ boolean h(Chip chip, boolean bl) {
        chip.p = bl;
        return bl;
    }

    private void setCloseIconHovered(boolean bl) {
        if (this.o != bl) {
            this.o = bl;
            this.refreshDrawableState();
        }
    }

    private void setCloseIconPressed(boolean bl) {
        if (this.n != bl) {
            this.n = bl;
            this.refreshDrawableState();
        }
    }

    public final void A() {
        a a4;
        if (!TextUtils.isEmpty((CharSequence)this.getText()) && (a4 = this.g) != null) {
            int n3 = (int)(a4.b1() + this.g.B1() + this.g.I0());
            int n4 = (int)(this.g.g1() + this.g.C1() + this.g.E0());
            int n5 = n3;
            int n6 = n4;
            if (this.h != null) {
                a4 = new Rect();
                this.h.getPadding((Rect)a4);
                n6 = n4 + ((Rect)a4).left;
                n5 = n3 + ((Rect)a4).right;
            }
            this.setPaddingRelative(n6, this.getPaddingTop(), n5, this.getPaddingBottom());
        }
    }

    public final void B() {
        TextPaint textPaint = this.getPaint();
        Object object = this.g;
        if (object != null) {
            textPaint.drawableState = object.getState();
        }
        if ((object = this.getTextAppearance()) != null) {
            ((d)object).p(this.getContext(), textPaint, this.y);
        }
    }

    @Override
    public void a() {
        this.m(this.s);
        this.requestLayout();
        this.invalidateOutline();
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (!this.v) {
            return super.dispatchHoverEvent(motionEvent);
        }
        return this.u.v(motionEvent) || super.dispatchHoverEvent(motionEvent);
        {
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!this.v) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (this.u.w(keyEvent) && this.u.A() != Integer.MIN_VALUE) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override
    public void drawableStateChanged() {
        super.drawableStateChanged();
        a a4 = this.g;
        boolean bl = a4 != null && a4.H1() ? this.g.E2(this.l()) : false;
        if (bl) {
            this.invalidate();
        }
    }

    public CharSequence getAccessibilityClassName() {
        if (!TextUtils.isEmpty((CharSequence)this.t)) {
            return this.t;
        }
        if (this.r()) {
            ViewParent viewParent = this.getParent();
            if (viewParent instanceof ChipGroup && ((ChipGroup)viewParent).i()) {
                return "android.widget.RadioButton";
            }
            return "android.widget.Button";
        }
        if (this.isClickable()) {
            return "android.widget.Button";
        }
        return "android.view.View";
    }

    public Drawable getBackgroundDrawable() {
        InsetDrawable insetDrawable;
        Object object = insetDrawable = this.h;
        if (insetDrawable == null) {
            object = this.g;
        }
        return object;
    }

    public Drawable getCheckedIcon() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.X0();
        }
        return null;
    }

    public ColorStateList getCheckedIconTint() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.Y0();
        }
        return null;
    }

    public ColorStateList getChipBackgroundColor() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.Z0();
        }
        return null;
    }

    public float getChipCornerRadius() {
        a a4 = this.g;
        if (a4 != null) {
            return Math.max(0.0f, a4.a1());
        }
        return 0.0f;
    }

    public Drawable getChipDrawable() {
        return this.g;
    }

    public float getChipEndPadding() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.b1();
        }
        return 0.0f;
    }

    public Drawable getChipIcon() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.c1();
        }
        return null;
    }

    public float getChipIconSize() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.d1();
        }
        return 0.0f;
    }

    public ColorStateList getChipIconTint() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.e1();
        }
        return null;
    }

    public float getChipMinHeight() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.f1();
        }
        return 0.0f;
    }

    public float getChipStartPadding() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.g1();
        }
        return 0.0f;
    }

    public ColorStateList getChipStrokeColor() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.h1();
        }
        return null;
    }

    public float getChipStrokeWidth() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.i1();
        }
        return 0.0f;
    }

    @Deprecated
    public CharSequence getChipText() {
        return this.getText();
    }

    public Drawable getCloseIcon() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.j1();
        }
        return null;
    }

    public CharSequence getCloseIconContentDescription() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.k1();
        }
        return null;
    }

    public float getCloseIconEndPadding() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.l1();
        }
        return 0.0f;
    }

    public float getCloseIconSize() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.m1();
        }
        return 0.0f;
    }

    public float getCloseIconStartPadding() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.n1();
        }
        return 0.0f;
    }

    public ColorStateList getCloseIconTint() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.p1();
        }
        return null;
    }

    public TextUtils.TruncateAt getEllipsize() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.t1();
        }
        return null;
    }

    public void getFocusedRect(Rect rect) {
        if (this.v && (this.u.A() == 1 || this.u.x() == 1)) {
            rect.set(this.getCloseIconTouchBoundsInt());
            return;
        }
        super.getFocusedRect(rect);
    }

    public h getHideMotionSpec() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.u1();
        }
        return null;
    }

    public float getIconEndPadding() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.v1();
        }
        return 0.0f;
    }

    public float getIconStartPadding() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.w1();
        }
        return 0.0f;
    }

    public ColorStateList getRippleColor() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.x1();
        }
        return null;
    }

    public o getShapeAppearanceModel() {
        return this.g.K();
    }

    public h getShowMotionSpec() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.y1();
        }
        return null;
    }

    public float getTextEndPadding() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.B1();
        }
        return 0.0f;
    }

    public float getTextStartPadding() {
        a a4 = this.g;
        if (a4 != null) {
            return a4.C1();
        }
        return 0.0f;
    }

    public final void k(a a4) {
        a4.I2(this);
    }

    public final int[] l() {
        int n3;
        int n4 = n3 = this.isEnabled();
        if (this.p) {
            n4 = n3 + 1;
        }
        n3 = n4;
        if (this.o) {
            n3 = n4 + 1;
        }
        n4 = n3;
        if (this.n) {
            n4 = n3 + 1;
        }
        n3 = n4;
        if (this.isChecked()) {
            n3 = n4 + 1;
        }
        int[] nArray = new int[n3];
        boolean bl = this.isEnabled();
        n4 = 0;
        if (bl) {
            nArray[0] = 16842910;
            n4 = 1;
        }
        n3 = n4;
        if (this.p) {
            nArray[n4] = 16842908;
            n3 = n4 + 1;
        }
        n4 = n3;
        if (this.o) {
            nArray[n3] = 16843623;
            n4 = n3 + 1;
        }
        n3 = n4;
        if (this.n) {
            nArray[n4] = 16842919;
            n3 = n4 + 1;
        }
        if (this.isChecked()) {
            nArray[n3] = 0x10100A1;
        }
        return nArray;
    }

    public boolean m(int n3) {
        this.s = n3;
        boolean bl = this.v();
        int n4 = 0;
        if (!bl) {
            if (this.h != null) {
                this.u();
            } else {
                this.y();
            }
            return false;
        }
        int n5 = Math.max(0, n3 - this.g.getIntrinsicHeight());
        int n6 = Math.max(0, n3 - this.g.getIntrinsicWidth());
        if (n6 <= 0 && n5 <= 0) {
            if (this.h != null) {
                this.u();
            } else {
                this.y();
            }
            return false;
        }
        n6 = n6 > 0 ? (n6 /= 2) : 0;
        if (n5 > 0) {
            n4 = n5 / 2;
        }
        if (this.h != null) {
            Rect rect = new Rect();
            this.h.getPadding(rect);
            if (rect.top == n4 && rect.bottom == n4 && rect.left == n6 && rect.right == n6) {
                this.y();
                return true;
            }
        }
        if (this.getMinHeight() != n3) {
            this.setMinHeight(n3);
        }
        if (this.getMinWidth() != n3) {
            this.setMinWidth(n3);
        }
        this.q(n6, n4, n6, n4);
        this.y();
        return true;
    }

    public final boolean n() {
        a a4 = this.g;
        return a4 != null && a4.j1() != null;
    }

    public final void o(Context context, AttributeSet attributeSet, int n3) {
        attributeSet = com.google.android.material.internal.z.i(context, attributeSet, z1.m.Chip, n3, z, new int[0]);
        this.q = attributeSet.getBoolean(z1.m.Chip_ensureMinTouchTargetSize, false);
        float f3 = b.e(context);
        this.s = (int)Math.ceil(attributeSet.getDimension(z1.m.Chip_chipMinTouchTargetSize, f3));
        attributeSet.recycle();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v2.j.f((View)this, this.g);
    }

    public int[] onCreateDrawableState(int n3) {
        int[] nArray = super.onCreateDrawableState(n3 + 2);
        if (this.isChecked()) {
            View.mergeDrawableStates((int[])nArray, (int[])B);
        }
        if (this.r()) {
            View.mergeDrawableStates((int[])nArray, (int[])C);
        }
        return nArray;
    }

    public void onFocusChanged(boolean bl, int n3, Rect rect) {
        super.onFocusChanged(bl, n3, rect);
        if (this.v) {
            this.u.K(bl, n3, rect);
        }
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionMasked();
        if (n3 != 7) {
            if (n3 == 10) {
                this.setCloseIconHovered(false);
            }
        } else {
            this.setCloseIconHovered(this.getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()));
        }
        return super.onHoverEvent(motionEvent);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        object.setClassName(this.getAccessibilityClassName());
        object.setCheckable(this.r());
        object.setClickable(this.isClickable());
        if (this.getParent() instanceof ChipGroup) {
            ChipGroup chipGroup = (ChipGroup)this.getParent();
            object = p0.s.L0((AccessibilityNodeInfo)object);
            int n3 = chipGroup.c() ? chipGroup.g((View)this) : -1;
            ((s)object).k0(s.f.a(chipGroup.b((View)this), 1, n3, 1, false, this.isChecked()));
        }
    }

    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n3) {
        if (this.getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()) && this.isEnabled()) {
            return PointerIcon.getSystemIcon((Context)this.getContext(), (int)1002);
        }
        return super.onResolvePointerIcon(motionEvent, n3);
    }

    public void onRtlPropertiesChanged(int n3) {
        super.onRtlPropertiesChanged(n3);
        if (this.r != n3) {
            this.r = n3;
            this.A();
        }
    }

    /*
     * Unable to fully structure code
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block7: {
            block11: {
                block8: {
                    block9: {
                        block10: {
                            var2_2 = var1_1.getActionMasked();
                            var3_3 = this.getCloseIconTouchBounds().contains(var1_1.getX(), var1_1.getY());
                            if (var2_2 == 0) break block8;
                            if (var2_2 == 1) break block9;
                            if (var2_2 == 2) break block10;
                            if (var2_2 == 3) ** GOTO lbl-1000
                            break block11;
                        }
                        if (this.n) {
                            if (!var3_3) {
                                this.setCloseIconPressed(false);
                            }
lbl12:
                            // 4 sources

                            while (true) {
                                var2_2 = 1;
                                break block7;
                                break;
                            }
                        }
                        break block11;
                    }
                    if (this.n) {
                        this.t();
                        var2_2 = 1;
                    } else lbl-1000:
                    // 2 sources

                    {
                        var2_2 = 0;
                    }
                    this.setCloseIconPressed(false);
                    break block7;
                }
                if (var3_3) {
                    this.setCloseIconPressed(true);
                    ** continue;
                }
            }
            var2_2 = 0;
        }
        return var2_2 != 0 || super.onTouchEvent(var1_1);
        {
        }
    }

    public final void p() {
        this.setOutlineProvider(new ViewOutlineProvider(this){
            public final Chip a;
            {
                this.a = chip;
            }

            public void getOutline(View view, Outline outline) {
                if (this.a.g != null) {
                    this.a.g.getOutline(outline);
                    return;
                }
                outline.setAlpha(0.0f);
            }
        });
    }

    public final void q(int n3, int n4, int n5, int n6) {
        this.h = new InsetDrawable((Drawable)this.g, n3, n4, n5, n6);
    }

    public boolean r() {
        a a4 = this.g;
        return a4 != null && a4.G1();
    }

    public boolean s() {
        a a4 = this.g;
        return a4 != null && a4.I1();
    }

    public void setAccessibilityClassName(CharSequence charSequence) {
        this.t = charSequence;
    }

    public void setBackground(Drawable drawable) {
        if (drawable != this.getBackgroundDrawable() && drawable != this.i) {
            Log.w((String)"Chip", (String)"Do not set the background; Chip manages its own background drawable.");
            return;
        }
        super.setBackground(drawable);
    }

    public void setBackgroundColor(int n3) {
        Log.w((String)"Chip", (String)"Do not set the background color; Chip manages its own background drawable.");
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable != this.getBackgroundDrawable() && drawable != this.i) {
            Log.w((String)"Chip", (String)"Do not set the background drawable; Chip manages its own background drawable.");
            return;
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override
    public void setBackgroundResource(int n3) {
        Log.w((String)"Chip", (String)"Do not set the background resource; Chip manages its own background drawable.");
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        Log.w((String)"Chip", (String)"Do not set the background tint list; Chip manages its own background drawable.");
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        Log.w((String)"Chip", (String)"Do not set the background tint mode; Chip manages its own background drawable.");
    }

    public void setCheckable(boolean bl) {
        a a4 = this.g;
        if (a4 != null) {
            a4.Q1(bl);
        }
    }

    public void setCheckableResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.R1(n3);
        }
    }

    public void setChecked(boolean bl) {
        a a4 = this.g;
        if (a4 == null) {
            this.m = bl;
            return;
        }
        if (a4.G1()) {
            super.setChecked(bl);
        }
    }

    public void setCheckedIcon(Drawable drawable) {
        a a4 = this.g;
        if (a4 != null) {
            a4.S1(drawable);
        }
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean bl) {
        this.setCheckedIconVisible(bl);
    }

    @Deprecated
    public void setCheckedIconEnabledResource(int n3) {
        this.setCheckedIconVisible(n3);
    }

    public void setCheckedIconResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.T1(n3);
        }
    }

    public void setCheckedIconTint(ColorStateList colorStateList) {
        a a4 = this.g;
        if (a4 != null) {
            a4.U1(colorStateList);
        }
    }

    public void setCheckedIconTintResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.V1(n3);
        }
    }

    public void setCheckedIconVisible(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.W1(n3);
        }
    }

    public void setCheckedIconVisible(boolean bl) {
        a a4 = this.g;
        if (a4 != null) {
            a4.X1(bl);
        }
    }

    public void setChipBackgroundColor(ColorStateList colorStateList) {
        a a4 = this.g;
        if (a4 != null) {
            a4.Y1(colorStateList);
        }
    }

    public void setChipBackgroundColorResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.Z1(n3);
        }
    }

    @Deprecated
    public void setChipCornerRadius(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.a2(f3);
        }
    }

    @Deprecated
    public void setChipCornerRadiusResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.b2(n3);
        }
    }

    public void setChipDrawable(a a4) {
        a a5 = this.g;
        if (a5 != a4) {
            this.w(a5);
            this.g = a4;
            a4.T2(false);
            this.k(this.g);
            this.m(this.s);
        }
    }

    public void setChipEndPadding(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.c2(f3);
        }
    }

    public void setChipEndPaddingResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.d2(n3);
        }
    }

    public void setChipIcon(Drawable drawable) {
        a a4 = this.g;
        if (a4 != null) {
            a4.e2(drawable);
        }
    }

    @Deprecated
    public void setChipIconEnabled(boolean bl) {
        this.setChipIconVisible(bl);
    }

    @Deprecated
    public void setChipIconEnabledResource(int n3) {
        this.setChipIconVisible(n3);
    }

    public void setChipIconResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.f2(n3);
        }
    }

    public void setChipIconSize(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.g2(f3);
        }
    }

    public void setChipIconSizeResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.h2(n3);
        }
    }

    public void setChipIconTint(ColorStateList colorStateList) {
        a a4 = this.g;
        if (a4 != null) {
            a4.i2(colorStateList);
        }
    }

    public void setChipIconTintResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.j2(n3);
        }
    }

    public void setChipIconVisible(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.k2(n3);
        }
    }

    public void setChipIconVisible(boolean bl) {
        a a4 = this.g;
        if (a4 != null) {
            a4.l2(bl);
        }
    }

    public void setChipMinHeight(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.m2(f3);
        }
    }

    public void setChipMinHeightResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.n2(n3);
        }
    }

    public void setChipStartPadding(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.o2(f3);
        }
    }

    public void setChipStartPaddingResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.p2(n3);
        }
    }

    public void setChipStrokeColor(ColorStateList colorStateList) {
        a a4 = this.g;
        if (a4 != null) {
            a4.q2(colorStateList);
        }
    }

    public void setChipStrokeColorResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.r2(n3);
        }
    }

    public void setChipStrokeWidth(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.s2(f3);
        }
    }

    public void setChipStrokeWidthResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.t2(n3);
        }
    }

    @Deprecated
    public void setChipText(CharSequence charSequence) {
        this.setText(charSequence);
    }

    @Deprecated
    public void setChipTextResource(int n3) {
        this.setText(this.getResources().getString(n3));
    }

    public void setCloseIcon(Drawable drawable) {
        a a4 = this.g;
        if (a4 != null) {
            a4.v2(drawable);
        }
        this.x();
    }

    public void setCloseIconContentDescription(CharSequence charSequence) {
        a a4 = this.g;
        if (a4 != null) {
            a4.w2(charSequence);
        }
    }

    @Deprecated
    public void setCloseIconEnabled(boolean bl) {
        this.setCloseIconVisible(bl);
    }

    @Deprecated
    public void setCloseIconEnabledResource(int n3) {
        this.setCloseIconVisible(n3);
    }

    public void setCloseIconEndPadding(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.x2(f3);
        }
    }

    public void setCloseIconEndPaddingResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.y2(n3);
        }
    }

    public void setCloseIconResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.z2(n3);
        }
        this.x();
    }

    public void setCloseIconSize(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.A2(f3);
        }
    }

    public void setCloseIconSizeResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.B2(n3);
        }
    }

    public void setCloseIconStartPadding(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.C2(f3);
        }
    }

    public void setCloseIconStartPaddingResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.D2(n3);
        }
    }

    public void setCloseIconTint(ColorStateList colorStateList) {
        a a4 = this.g;
        if (a4 != null) {
            a4.F2(colorStateList);
        }
    }

    public void setCloseIconTintResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.G2(n3);
        }
    }

    public void setCloseIconVisible(int n3) {
        this.setCloseIconVisible(this.getResources().getBoolean(n3));
    }

    public void setCloseIconVisible(boolean bl) {
        a a4 = this.g;
        if (a4 != null) {
            a4.H2(bl);
        }
        this.x();
    }

    @Override
    public void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable == null) {
            if (drawable3 == null) {
                super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
                return;
            }
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
    }

    @Override
    public void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable == null) {
            if (drawable3 == null) {
                super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
                return;
            }
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int n3, int n4, int n5, int n6) {
        if (n3 == 0) {
            if (n5 == 0) {
                super.setCompoundDrawablesRelativeWithIntrinsicBounds(n3, n4, n5, n6);
                return;
            }
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable == null) {
            if (drawable3 == null) {
                super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
                return;
            }
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int n3, int n4, int n5, int n6) {
        if (n3 == 0) {
            if (n5 == 0) {
                super.setCompoundDrawablesWithIntrinsicBounds(n3, n4, n5, n6);
                return;
            }
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable == null) {
            if (drawable3 == null) {
                super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
                return;
            }
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        }
        throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        a a4 = this.g;
        if (a4 != null) {
            a4.h0(f3);
        }
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        block5: {
            block4: {
                if (this.g == null) break block4;
                if (truncateAt == TextUtils.TruncateAt.MARQUEE) break block5;
                super.setEllipsize(truncateAt);
                a a4 = this.g;
                if (a4 != null) {
                    a4.J2(truncateAt);
                }
            }
            return;
        }
        throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
    }

    public void setEnsureMinTouchTargetSize(boolean bl) {
        this.q = bl;
        this.m(this.s);
    }

    public void setGravity(int n3) {
        if (n3 != 8388627) {
            Log.w((String)"Chip", (String)"Chip text must be vertically center and start aligned");
            return;
        }
        super.setGravity(n3);
    }

    public void setHideMotionSpec(h h3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.K2(h3);
        }
    }

    public void setHideMotionSpecResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.L2(n3);
        }
    }

    public void setIconEndPadding(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.M2(f3);
        }
    }

    public void setIconEndPaddingResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.N2(n3);
        }
    }

    public void setIconStartPadding(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.O2(f3);
        }
    }

    public void setIconStartPaddingResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.P2(n3);
        }
    }

    @Override
    public void setInternalOnCheckedChangeListener(j.a a4) {
        this.l = a4;
    }

    public void setLayoutDirection(int n3) {
        if (this.g == null) {
            return;
        }
        super.setLayoutDirection(n3);
    }

    public void setLines(int n3) {
        if (n3 <= 1) {
            super.setLines(n3);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setMaxLines(int n3) {
        if (n3 <= 1) {
            super.setMaxLines(n3);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setMaxWidth(int n3) {
        super.setMaxWidth(n3);
        a a4 = this.g;
        if (a4 != null) {
            a4.Q2(n3);
        }
    }

    public void setMinLines(int n3) {
        if (n3 <= 1) {
            super.setMinLines(n3);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.k = onCheckedChangeListener;
    }

    public void setOnCloseIconClickListener(View.OnClickListener onClickListener) {
        this.j = onClickListener;
        this.x();
    }

    public void setRippleColor(ColorStateList colorStateList) {
        a a4 = this.g;
        if (a4 != null) {
            a4.R2(colorStateList);
        }
        if (!this.g.E1()) {
            this.z();
        }
    }

    public void setRippleColorResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.S2(n3);
            if (!this.g.E1()) {
                this.z();
            }
        }
    }

    @Override
    public void setShapeAppearanceModel(o o3) {
        this.g.setShapeAppearanceModel(o3);
    }

    public void setShowMotionSpec(h h3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.U2(h3);
        }
    }

    public void setShowMotionSpecResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.V2(n3);
        }
    }

    public void setSingleLine(boolean bl) {
        if (bl) {
            super.setSingleLine(bl);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public void setText(CharSequence object, TextView.BufferType bufferType) {
        a a4 = this.g;
        if (a4 != null) {
            CharSequence charSequence = object;
            if (object == null) {
                charSequence = "";
            }
            object = a4.f3() ? null : charSequence;
            super.setText((CharSequence)object, bufferType);
            object = this.g;
            if (object != null) {
                ((a)object).W2(charSequence);
            }
        }
    }

    public void setTextAppearance(int n3) {
        super.setTextAppearance(n3);
        a a4 = this.g;
        if (a4 != null) {
            a4.Y2(n3);
        }
        this.B();
    }

    public void setTextAppearance(Context object, int n3) {
        super.setTextAppearance((Context)object, n3);
        object = this.g;
        if (object != null) {
            ((a)object).Y2(n3);
        }
        this.B();
    }

    public void setTextAppearance(d d3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.X2(d3);
        }
        this.B();
    }

    public void setTextAppearanceResource(int n3) {
        this.setTextAppearance(this.getContext(), n3);
    }

    public void setTextEndPadding(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.Z2(f3);
        }
    }

    public void setTextEndPaddingResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.a3(n3);
        }
    }

    public void setTextSize(int n3, float f3) {
        super.setTextSize(n3, f3);
        a a4 = this.g;
        if (a4 != null) {
            a4.b3(TypedValue.applyDimension((int)n3, (float)f3, (DisplayMetrics)this.getResources().getDisplayMetrics()));
        }
        this.B();
    }

    public void setTextStartPadding(float f3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.c3(f3);
        }
    }

    public void setTextStartPaddingResource(int n3) {
        a a4 = this.g;
        if (a4 != null) {
            a4.d3(n3);
        }
    }

    public boolean t() {
        boolean bl = false;
        this.playSoundEffect(0);
        View.OnClickListener onClickListener = this.j;
        if (onClickListener != null) {
            onClickListener.onClick((View)this);
            bl = true;
        }
        if (this.v) {
            this.u.W(1, 1);
        }
        return bl;
    }

    public final void u() {
        if (this.h != null) {
            this.h = null;
            this.setMinWidth(0);
            this.setMinHeight((int)this.getChipMinHeight());
            this.y();
        }
    }

    public boolean v() {
        return this.q;
    }

    public final void w(a a4) {
        if (a4 != null) {
            a4.I2(null);
        }
    }

    public final void x() {
        if (this.n() && this.s() && this.j != null) {
            x0.h0((View)this, this.u);
            this.v = true;
            return;
        }
        x0.h0((View)this, null);
        this.v = false;
    }

    public final void y() {
        this.z();
    }

    public final void z() {
        this.i = new RippleDrawable(t2.a.d(this.g.x1()), this.getBackgroundDrawable(), null);
        this.g.e3(false);
        this.setBackground((Drawable)this.i);
        this.A();
    }

    public class c
    extends v0.a {
        public final Chip q;

        public c(Chip chip, Chip chip2) {
            this.q = chip;
            super((View)chip2);
        }

        @Override
        public int B(float f3, float f4) {
            if (this.q.n() && this.q.getCloseIconTouchBounds().contains(f3, f4)) {
                return 1;
            }
            return 0;
        }

        @Override
        public void C(List list) {
            list.add(0);
            if (this.q.n() && this.q.s() && this.q.j != null) {
                list.add(1);
            }
        }

        @Override
        public boolean L(int n3, int n4, Bundle bundle) {
            if (n4 == 16) {
                if (n3 == 0) {
                    return this.q.performClick();
                }
                if (n3 == 1) {
                    return this.q.t();
                }
            }
            return false;
        }

        @Override
        public void O(s s3) {
            s3.f0(this.q.r());
            s3.i0(this.q.isClickable());
            s3.h0(this.q.getAccessibilityClassName());
            s3.H0(this.q.getText());
        }

        @Override
        public void P(int n3, s s3) {
            CharSequence charSequence = "";
            if (n3 == 1) {
                CharSequence charSequence2 = this.q.getCloseIconContentDescription();
                if (charSequence2 != null) {
                    s3.l0(charSequence2);
                } else {
                    charSequence2 = this.q.getText();
                    Context context = this.q.getContext();
                    n3 = z1.k.mtrl_chip_close_icon_content_description;
                    if (!TextUtils.isEmpty((CharSequence)charSequence2)) {
                        charSequence = charSequence2;
                    }
                    s3.l0(context.getString(n3, new Object[]{charSequence}).trim());
                }
                s3.c0(this.q.getCloseIconTouchBoundsInt());
                s3.b(s.a.i);
                s3.m0(this.q.isEnabled());
                s3.h0(Button.class.getName());
                return;
            }
            s3.l0("");
            s3.c0(A);
        }

        @Override
        public void Q(int n3, boolean bl) {
            if (n3 == 1) {
                Chip.h(this.q, bl);
            }
            if (this.q.g.P1(this.q.p)) {
                this.q.refreshDrawableState();
            }
        }
    }
}

