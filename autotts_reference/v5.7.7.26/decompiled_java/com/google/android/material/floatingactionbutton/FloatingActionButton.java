/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator$AnimatorListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.g;
import androidx.appcompat.widget.k;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.a;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import com.google.android.material.stateful.ExtendableSavedState;
import java.util.List;
import n0.h;
import o0.x0;
import v2.o;
import v2.r;
import z1.e;
import z1.l;
import z1.m;

public class FloatingActionButton
extends VisibilityAwareImageButton
implements l2.a,
r,
CoordinatorLayout.b {
    public static final int t = z1.l.Widget_Design_FloatingActionButton;
    public ColorStateList d;
    public PorterDuff.Mode e;
    public ColorStateList f;
    public PorterDuff.Mode g;
    public ColorStateList h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public boolean n;
    public final Rect o;
    public final Rect p;
    public final k q;
    public final l2.b r;
    public a s;

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.floatingActionButtonStyle);
    }

    public FloatingActionButton(Context object, AttributeSet attributeSet, int n3) {
        int n4 = t;
        super(y2.a.d(object, attributeSet, n3, n4), attributeSet, n3);
        this.o = new Rect();
        this.p = new Rect();
        Object object2 = this.getContext();
        Object object3 = z.i(object2, attributeSet, z1.m.FloatingActionButton, n3, n4, new int[0]);
        this.d = s2.c.a(object2, (TypedArray)object3, z1.m.FloatingActionButton_backgroundTint);
        this.e = c0.n(object3.getInt(z1.m.FloatingActionButton_backgroundTintMode, -1), null);
        this.h = s2.c.a(object2, (TypedArray)object3, z1.m.FloatingActionButton_rippleColor);
        this.j = object3.getInt(z1.m.FloatingActionButton_fabSize, -1);
        this.k = object3.getDimensionPixelSize(z1.m.FloatingActionButton_fabCustomSize, 0);
        this.i = object3.getDimensionPixelSize(z1.m.FloatingActionButton_borderWidth, 0);
        float f3 = object3.getDimension(z1.m.FloatingActionButton_elevation, 0.0f);
        float f4 = object3.getDimension(z1.m.FloatingActionButton_hoveredFocusedTranslationZ, 0.0f);
        float f5 = object3.getDimension(z1.m.FloatingActionButton_pressedTranslationZ, 0.0f);
        this.n = object3.getBoolean(z1.m.FloatingActionButton_useCompatPadding, false);
        int n5 = this.getResources().getDimensionPixelSize(z1.e.mtrl_fab_min_touch_target);
        this.setMaxImageSize(object3.getDimensionPixelSize(z1.m.FloatingActionButton_maxImageSize, 0));
        a2.h h3 = a2.h.c(object2, (TypedArray)object3, z1.m.FloatingActionButton_showMotionSpec);
        object = a2.h.c(object2, (TypedArray)object3, z1.m.FloatingActionButton_hideMotionSpec);
        object2 = v2.o.g(object2, attributeSet, n3, n4, v2.o.m).m();
        boolean bl = object3.getBoolean(z1.m.FloatingActionButton_ensureMinTouchTargetSize, false);
        this.setEnabled(object3.getBoolean(z1.m.FloatingActionButton_android_enabled, true));
        object3.recycle();
        object3 = new k((ImageView)this);
        this.q = object3;
        ((k)object3).g(attributeSet, n3);
        this.r = new l2.b(this);
        this.getImpl().W((o)object2);
        this.getImpl().A(this.d, this.e, this.h, this.i);
        this.getImpl().S(n5);
        this.getImpl().M(f3);
        this.getImpl().P(f4);
        this.getImpl().T(f5);
        this.getImpl().X(h3);
        this.getImpl().O((a2.h)object);
        this.getImpl().N(bl);
        this.setScaleType(ImageView.ScaleType.MATRIX);
    }

    private a getImpl() {
        if (this.s == null) {
            this.s = new a(this, new c(this));
        }
        return this.s;
    }

    @Override
    public boolean a() {
        return this.r.c();
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
    }

    public void e(Animator.AnimatorListener animatorListener) {
        this.getImpl().e(animatorListener);
    }

    public void f(Animator.AnimatorListener animatorListener) {
        this.getImpl().f(animatorListener);
    }

    public void g(a2.k k3) {
        this.getImpl().g(new d(this, k3));
    }

    public CharSequence getAccessibilityClassName() {
        return "com.google.android.material.floatingactionbutton.FloatingActionButton";
    }

    public ColorStateList getBackgroundTintList() {
        return this.d;
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return this.e;
    }

    @Override
    public CoordinatorLayout.Behavior<FloatingActionButton> getBehavior() {
        return new Behavior();
    }

    public float getCompatElevation() {
        return this.getImpl().p();
    }

    public float getCompatHoveredFocusedTranslationZ() {
        return this.getImpl().s();
    }

    public float getCompatPressedTranslationZ() {
        return this.getImpl().u();
    }

    public Drawable getContentBackground() {
        return this.getImpl().o();
    }

    public int getCustomSize() {
        return this.k;
    }

    public int getExpandedComponentIdHint() {
        return this.r.b();
    }

    public a2.h getHideMotionSpec() {
        return this.getImpl().r();
    }

    @Deprecated
    public int getRippleColor() {
        ColorStateList colorStateList = this.h;
        if (colorStateList != null) {
            return colorStateList.getDefaultColor();
        }
        return 0;
    }

    public ColorStateList getRippleColorStateList() {
        return this.h;
    }

    public o getShapeAppearanceModel() {
        return (o)n0.h.g(this.getImpl().v());
    }

    public a2.h getShowMotionSpec() {
        return this.getImpl().w();
    }

    public int getSize() {
        return this.j;
    }

    public int getSizeDimension() {
        return this.i(this.j);
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.getBackgroundTintList();
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return this.getBackgroundTintMode();
    }

    public ColorStateList getSupportImageTintList() {
        return this.f;
    }

    public PorterDuff.Mode getSupportImageTintMode() {
        return this.g;
    }

    public boolean getUseCompatPadding() {
        return this.n;
    }

    public void h(Rect rect) {
        rect.set(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
        this.o(rect);
    }

    public final int i(int n3) {
        int n4 = this.k;
        if (n4 != 0) {
            return n4;
        }
        Resources resources = this.getResources();
        if (n3 != -1) {
            if (n3 != 1) {
                return resources.getDimensionPixelSize(z1.e.design_fab_size_normal);
            }
            return resources.getDimensionPixelSize(z1.e.design_fab_size_mini);
        }
        if (Math.max(resources.getConfiguration().screenWidthDp, resources.getConfiguration().screenHeightDp) < 470) {
            return this.i(1);
        }
        return this.i(0);
    }

    public final void j(Rect rect) {
        this.h(rect);
        int n3 = -this.s.x();
        rect.inset(n3, n3);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
    }

    public void k(b b3) {
        this.l(b3, true);
    }

    public void l(b b3, boolean bl) {
        this.getImpl().y(this.s(b3), bl);
    }

    public boolean m() {
        return this.getImpl().B();
    }

    public boolean n() {
        return this.getImpl().C();
    }

    public final void o(Rect rect) {
        int n3 = rect.left;
        Rect rect2 = this.o;
        rect.left = n3 + rect2.left;
        rect.top += rect2.top;
        rect.right -= rect2.right;
        rect.bottom -= rect2.bottom;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getImpl().D();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getImpl().F();
    }

    public void onMeasure(int n3, int n4) {
        int n5 = this.getSizeDimension();
        this.l = (n5 - this.m) / 2;
        this.getImpl().c0();
        n3 = Math.min(View.resolveSize((int)n5, (int)n3), View.resolveSize((int)n5, (int)n4));
        Rect rect = this.o;
        this.setMeasuredDimension(rect.left + n3 + rect.right, n3 + rect.top + rect.bottom);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof ExtendableSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (ExtendableSavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.r.d((Bundle)n0.h.g((Bundle)parcelable.e.get("expandableWidgetHelper")));
    }

    public Parcelable onSaveInstanceState() {
        Parcelable parcelable;
        Parcelable parcelable2 = parcelable = super.onSaveInstanceState();
        if (parcelable == null) {
            parcelable2 = new Bundle();
        }
        parcelable2 = new ExtendableSavedState(parcelable2);
        parcelable2.e.put("expandableWidgetHelper", this.r.e());
        return parcelable2;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.j(this.p);
            if (!this.p.contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
                return false;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void p() {
        Drawable drawable = this.getDrawable();
        if (drawable == null) {
            return;
        }
        ColorStateList colorStateList = this.f;
        if (colorStateList == null) {
            h0.a.c(drawable);
            return;
        }
        int n3 = colorStateList.getColorForState(this.getDrawableState(), 0);
        PorterDuff.Mode mode = this.g;
        colorStateList = mode;
        if (mode == null) {
            colorStateList = PorterDuff.Mode.SRC_IN;
        }
        drawable.mutate().setColorFilter((ColorFilter)androidx.appcompat.widget.g.e(n3, (PorterDuff.Mode)colorStateList));
    }

    public void q(b b3) {
        this.r(b3, true);
    }

    public void r(b b3, boolean bl) {
        this.getImpl().a0(this.s(b3), bl);
    }

    public final a.g s(b b3) {
        if (b3 == null) {
            return null;
        }
        return new a.g(this, b3){
            public final b a;
            public final FloatingActionButton b;
            {
                this.b = floatingActionButton;
                this.a = b3;
            }

            @Override
            public void a() {
                this.a.b(this.b);
            }

            @Override
            public void b() {
                this.a.a(this.b);
            }
        };
    }

    public void setBackgroundColor(int n3) {
    }

    public void setBackgroundDrawable(Drawable drawable) {
    }

    public void setBackgroundResource(int n3) {
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.d != colorStateList) {
            this.d = colorStateList;
            this.getImpl().K(colorStateList);
        }
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.e != mode) {
            this.e = mode;
            this.getImpl().L(mode);
        }
    }

    public void setCompatElevation(float f3) {
        this.getImpl().M(f3);
    }

    public void setCompatElevationResource(int n3) {
        this.setCompatElevation(this.getResources().getDimension(n3));
    }

    public void setCompatHoveredFocusedTranslationZ(float f3) {
        this.getImpl().P(f3);
    }

    public void setCompatHoveredFocusedTranslationZResource(int n3) {
        this.setCompatHoveredFocusedTranslationZ(this.getResources().getDimension(n3));
    }

    public void setCompatPressedTranslationZ(float f3) {
        this.getImpl().T(f3);
    }

    public void setCompatPressedTranslationZResource(int n3) {
        this.setCompatPressedTranslationZ(this.getResources().getDimension(n3));
    }

    public void setCustomSize(int n3) {
        if (n3 >= 0) {
            if (n3 != this.k) {
                this.k = n3;
                this.requestLayout();
            }
            return;
        }
        throw new IllegalArgumentException("Custom size must be non-negative");
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        this.getImpl().d0(f3);
    }

    public void setEnsureMinTouchTargetSize(boolean bl) {
        if (bl != this.getImpl().q()) {
            this.getImpl().N(bl);
            this.requestLayout();
        }
    }

    public void setExpandedComponentIdHint(int n3) {
        this.r.f(n3);
    }

    public void setHideMotionSpec(a2.h h3) {
        this.getImpl().O(h3);
    }

    public void setHideMotionSpecResource(int n3) {
        this.setHideMotionSpec(a2.h.d(this.getContext(), n3));
    }

    public void setImageDrawable(Drawable drawable) {
        if (this.getDrawable() != drawable) {
            super.setImageDrawable(drawable);
            this.getImpl().b0();
            if (this.f != null) {
                this.p();
            }
        }
    }

    public void setImageResource(int n3) {
        this.q.i(n3);
        this.p();
    }

    public void setMaxImageSize(int n3) {
        this.m = n3;
        this.getImpl().R(n3);
    }

    public void setRippleColor(int n3) {
        this.setRippleColor(ColorStateList.valueOf((int)n3));
    }

    public void setRippleColor(ColorStateList colorStateList) {
        if (this.h != colorStateList) {
            this.h = colorStateList;
            this.getImpl().U(this.h);
        }
    }

    public void setScaleX(float f3) {
        super.setScaleX(f3);
        this.getImpl().I();
    }

    public void setScaleY(float f3) {
        super.setScaleY(f3);
        this.getImpl().I();
    }

    public void setShadowPaddingEnabled(boolean bl) {
        this.getImpl().V(bl);
    }

    @Override
    public void setShapeAppearanceModel(o o3) {
        this.getImpl().W(o3);
    }

    public void setShowMotionSpec(a2.h h3) {
        this.getImpl().X(h3);
    }

    public void setShowMotionSpecResource(int n3) {
        this.setShowMotionSpec(a2.h.d(this.getContext(), n3));
    }

    public void setSize(int n3) {
        this.k = 0;
        if (n3 != this.j) {
            this.j = n3;
            this.requestLayout();
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        this.setBackgroundTintList(colorStateList);
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        this.setBackgroundTintMode(mode);
    }

    public void setSupportImageTintList(ColorStateList colorStateList) {
        if (this.f != colorStateList) {
            this.f = colorStateList;
            this.p();
        }
    }

    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        if (this.g != mode) {
            this.g = mode;
            this.p();
        }
    }

    public void setTranslationX(float f3) {
        super.setTranslationX(f3);
        this.getImpl().J();
    }

    public void setTranslationY(float f3) {
        super.setTranslationY(f3);
        this.getImpl().J();
    }

    public void setTranslationZ(float f3) {
        super.setTranslationZ(f3);
        this.getImpl().J();
    }

    public void setUseCompatPadding(boolean bl) {
        if (this.n != bl) {
            this.n = bl;
            this.getImpl().E();
        }
    }

    @Override
    public void setVisibility(int n3) {
        super.setVisibility(n3);
    }

    public static class BaseBehavior<T extends FloatingActionButton>
    extends CoordinatorLayout.Behavior<T> {
        public Rect c;
        public b d;
        public boolean e;

        public BaseBehavior() {
            this.e = true;
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, z1.m.FloatingActionButton_Behavior_Layout);
            this.e = context.getBoolean(z1.m.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            context.recycle();
        }

        private static boolean K(View view) {
            if ((view = view.getLayoutParams()) instanceof CoordinatorLayout.e) {
                return ((CoordinatorLayout.e)view).f() instanceof BottomSheetBehavior;
            }
            return false;
        }

        public boolean I(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, Rect rect) {
            coordinatorLayout = floatingActionButton.o;
            rect.set(floatingActionButton.getLeft() + ((Rect)coordinatorLayout).left, floatingActionButton.getTop() + ((Rect)coordinatorLayout).top, floatingActionButton.getRight() - ((Rect)coordinatorLayout).right, floatingActionButton.getBottom() - ((Rect)coordinatorLayout).bottom);
            return true;
        }

        public final boolean J(View view, FloatingActionButton floatingActionButton) {
            CoordinatorLayout.e e3 = (CoordinatorLayout.e)floatingActionButton.getLayoutParams();
            if (!this.e) {
                return true;
            }
            if (e3.e() != view.getId()) {
                return true;
            }
            return floatingActionButton.getUserSetVisibility() != 0;
        }

        public final void L(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            Rect rect = floatingActionButton.o;
            if (rect.centerX() > 0 && rect.centerY() > 0) {
                CoordinatorLayout.e e3 = (CoordinatorLayout.e)floatingActionButton.getLayoutParams();
                int n3 = floatingActionButton.getRight();
                int n4 = coordinatorLayout.getWidth();
                int n5 = e3.rightMargin;
                int n6 = 0;
                n3 = n3 >= n4 - n5 ? rect.right : (floatingActionButton.getLeft() <= e3.leftMargin ? -rect.left : 0);
                if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - e3.bottomMargin) {
                    n6 = rect.bottom;
                } else if (floatingActionButton.getTop() <= e3.topMargin) {
                    n6 = -rect.top;
                }
                if (n6 != 0) {
                    x0.S((View)floatingActionButton, n6);
                }
                if (n3 != 0) {
                    x0.R((View)floatingActionButton, n3);
                }
            }
        }

        public boolean M(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                this.O(coordinatorLayout, (AppBarLayout)view, floatingActionButton);
            } else if (BaseBehavior.K(view)) {
                this.P(view, floatingActionButton);
            }
            return false;
        }

        public boolean N(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int n3) {
            View view;
            List list = coordinatorLayout.v((View)floatingActionButton);
            int n4 = list.size();
            for (int i3 = 0; i3 < n4 && !((view = (View)list.get(i3)) instanceof AppBarLayout ? this.O(coordinatorLayout, (AppBarLayout)view, floatingActionButton) : BaseBehavior.K(view) && this.P(view, floatingActionButton)); ++i3) {
            }
            coordinatorLayout.M((View)floatingActionButton, n3);
            this.L(coordinatorLayout, floatingActionButton);
            return true;
        }

        public final boolean O(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (this.J((View)appBarLayout, floatingActionButton)) {
                return false;
            }
            if (this.c == null) {
                this.c = new Rect();
            }
            Rect rect = this.c;
            com.google.android.material.internal.d.a(coordinatorLayout, (View)appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.l(this.d, false);
            } else {
                floatingActionButton.r(this.d, false);
            }
            return true;
        }

        public final boolean P(View view, FloatingActionButton floatingActionButton) {
            if (this.J(view, floatingActionButton)) {
                return false;
            }
            CoordinatorLayout.e e3 = (CoordinatorLayout.e)floatingActionButton.getLayoutParams();
            if (view.getTop() < floatingActionButton.getHeight() / 2 + e3.topMargin) {
                floatingActionButton.l(this.d, false);
            } else {
                floatingActionButton.r(this.d, false);
            }
            return true;
        }

        @Override
        public void k(CoordinatorLayout.e e3) {
            if (e3.h == 0) {
                e3.h = 80;
            }
        }
    }

    public static class Behavior
    extends BaseBehavior<FloatingActionButton> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public static abstract class b {
        public void a(FloatingActionButton floatingActionButton) {
        }

        public void b(FloatingActionButton floatingActionButton) {
        }
    }

    public class c
    implements u2.b {
        public final FloatingActionButton a;

        public c(FloatingActionButton floatingActionButton) {
            this.a = floatingActionButton;
        }

        @Override
        public void a(int n3, int n4, int n5, int n6) {
            this.a.o.set(n3, n4, n5, n6);
            FloatingActionButton floatingActionButton = this.a;
            floatingActionButton.setPadding(n3 + floatingActionButton.l, n4 + this.a.l, n5 + this.a.l, n6 + this.a.l);
        }

        @Override
        public void b(Drawable drawable) {
            if (drawable != null) {
                FloatingActionButton.super.setBackgroundDrawable(drawable);
            }
        }

        @Override
        public boolean c() {
            return this.a.n;
        }
    }

    public class d
    implements a.f {
        public final a2.k a;
        public final FloatingActionButton b;

        public d(FloatingActionButton floatingActionButton, a2.k k3) {
            this.b = floatingActionButton;
            this.a = k3;
        }

        @Override
        public void a() {
            this.a.a((View)this.b);
        }

        @Override
        public void b() {
            this.a.b((View)this.b);
        }

        public boolean equals(Object object) {
            return object instanceof d && ((d)object).a.equals(this.a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }
}

