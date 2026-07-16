/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.PropertyValuesHolder
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package com.google.android.material.floatingactionbutton;

import a2.h;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.b;
import com.google.android.material.internal.d;
import com.google.android.material.internal.z;
import java.util.List;
import m2.a;
import v2.o;
import z1.c;

public class ExtendedFloatingActionButton
extends MaterialButton
implements CoordinatorLayout.b {
    public static final int h0 = z1.l.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon;
    public static final Property i0 = new Property(Float.class, "width"){

        public Float a(View view) {
            return Float.valueOf(view.getLayoutParams().width);
        }

        public void b(View view, Float f3) {
            view.getLayoutParams().width = f3.intValue();
            view.requestLayout();
        }
    };
    public static final Property j0 = new Property(Float.class, "height"){

        public Float a(View view) {
            return Float.valueOf(view.getLayoutParams().height);
        }

        public void b(View view, Float f3) {
            view.getLayoutParams().height = f3.intValue();
            view.requestLayout();
        }
    };
    public static final Property k0 = new Property(Float.class, "paddingStart"){

        public Float a(View view) {
            return Float.valueOf(view.getPaddingStart());
        }

        public void b(View view, Float f3) {
            view.setPaddingRelative(f3.intValue(), view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
        }
    };
    public static final Property l0 = new Property(Float.class, "paddingEnd"){

        public Float a(View view) {
            return Float.valueOf(view.getPaddingEnd());
        }

        public void b(View view, Float f3) {
            view.setPaddingRelative(view.getPaddingStart(), view.getPaddingTop(), f3.intValue(), view.getPaddingBottom());
        }
    };
    public int M;
    public boolean N;
    public final a O;
    public final b P;
    public final b Q;
    public final b R;
    public final b S;
    public final int T;
    public int U;
    public int V;
    public final CoordinatorLayout.Behavior W;
    public boolean a0;
    public boolean b0;
    public boolean c0;
    public ColorStateList d0;
    public int e0;
    public int f0;
    public final int g0;

    public ExtendedFloatingActionButton(Context context) {
        this(context, null);
    }

    public ExtendedFloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.extendedFloatingActionButtonStyle);
    }

    public ExtendedFloatingActionButton(Context object, AttributeSet attributeSet, int n3) {
        int n4;
        int n5 = h0;
        super(y2.a.d((Context)object, attributeSet, n3, n5), attributeSet, n3);
        this.M = 0;
        this.N = true;
        Object object2 = new a();
        this.O = object2;
        object = new m(this, (a)object2);
        this.R = object;
        k k3 = new k(this, (a)object2);
        this.S = k3;
        this.a0 = true;
        this.b0 = false;
        this.c0 = false;
        Context context = this.getContext();
        this.W = new ExtendedFloatingActionButtonBehavior(context, attributeSet);
        TypedArray typedArray = com.google.android.material.internal.z.i(context, attributeSet, z1.m.ExtendedFloatingActionButton, n3, n5, new int[0]);
        object2 = a2.h.c(context, typedArray, z1.m.ExtendedFloatingActionButton_showMotionSpec);
        h h3 = a2.h.c(context, typedArray, z1.m.ExtendedFloatingActionButton_hideMotionSpec);
        h h4 = a2.h.c(context, typedArray, z1.m.ExtendedFloatingActionButton_extendMotionSpec);
        h h5 = a2.h.c(context, typedArray, z1.m.ExtendedFloatingActionButton_shrinkMotionSpec);
        this.T = typedArray.getDimensionPixelSize(z1.m.ExtendedFloatingActionButton_collapsedSize, -1);
        this.g0 = n4 = typedArray.getInt(z1.m.ExtendedFloatingActionButton_extendStrategy, 1);
        this.U = this.getPaddingStart();
        this.V = this.getPaddingEnd();
        Object object3 = new a();
        j j3 = new j(this, (a)object3, this.H(n4), true);
        this.Q = j3;
        this.P = object3 = new j(this, (a)object3, new n(this){
            public final ExtendedFloatingActionButton a;
            {
                this.a = extendedFloatingActionButton;
            }

            @Override
            public int a() {
                return this.a.getCollapsedSize();
            }

            @Override
            public int b() {
                return this.a.getCollapsedPadding();
            }

            @Override
            public int c() {
                return this.a.getCollapsedPadding();
            }

            @Override
            public int d() {
                return this.a.getCollapsedSize();
            }

            @Override
            public ViewGroup.LayoutParams e() {
                return new ViewGroup.LayoutParams(this.d(), this.a());
            }
        }, false);
        object.h((h)object2);
        k3.h(h3);
        j3.h(h4);
        object3.h(h5);
        typedArray.recycle();
        this.setShapeAppearanceModel(v2.o.g(context, attributeSet, n3, n5, v2.o.m).m());
        this.L();
    }

    public static /* synthetic */ boolean C(ExtendedFloatingActionButton extendedFloatingActionButton, boolean bl) {
        extendedFloatingActionButton.a0 = bl;
        return bl;
    }

    public static /* synthetic */ boolean D(ExtendedFloatingActionButton extendedFloatingActionButton, boolean bl) {
        extendedFloatingActionButton.b0 = bl;
        return bl;
    }

    public static /* synthetic */ int E(ExtendedFloatingActionButton extendedFloatingActionButton, int n3) {
        extendedFloatingActionButton.M = n3;
        return n3;
    }

    private boolean I() {
        if (this.getVisibility() == 0) {
            return this.M == 1;
        }
        return this.M != 2;
    }

    private boolean J() {
        if (this.getVisibility() != 0) {
            return this.M == 2;
        }
        return this.M != 1;
    }

    public static /* synthetic */ int x(ExtendedFloatingActionButton extendedFloatingActionButton, int n3) {
        extendedFloatingActionButton.f0 = n3;
        return n3;
    }

    public static /* synthetic */ int z(ExtendedFloatingActionButton extendedFloatingActionButton, int n3) {
        extendedFloatingActionButton.e0 = n3;
        return n3;
    }

    public final n H(int n3) {
        n n4 = new n(this){
            public final ExtendedFloatingActionButton a;
            {
                this.a = extendedFloatingActionButton;
            }

            @Override
            public int a() {
                return this.a.getMeasuredHeight();
            }

            @Override
            public int b() {
                return this.a.V;
            }

            @Override
            public int c() {
                return this.a.U;
            }

            @Override
            public int d() {
                return this.a.getMeasuredWidth() - this.a.getPaddingStart() - this.a.getPaddingEnd() + this.a.U + this.a.V;
            }

            @Override
            public ViewGroup.LayoutParams e() {
                return new ViewGroup.LayoutParams(-2, -2);
            }
        };
        n n5 = new n(this, n4){
            public final n a;
            public final ExtendedFloatingActionButton b;
            {
                this.b = extendedFloatingActionButton;
                this.a = n3;
            }

            @Override
            public int a() {
                if (this.b.f0 == -1) {
                    if (!(this.b.getParent() instanceof View)) {
                        return this.a.a();
                    }
                    View view = (View)this.b.getParent();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    if (layoutParams != null && layoutParams.height == -2) {
                        return this.a.a();
                    }
                    int n3 = view.getPaddingTop();
                    int n4 = view.getPaddingBottom();
                    int n5 = this.b.getLayoutParams() instanceof ViewGroup.MarginLayoutParams && (layoutParams = (ViewGroup.MarginLayoutParams)this.b.getLayoutParams()) != null ? layoutParams.topMargin + layoutParams.bottomMargin : 0;
                    return view.getHeight() - n5 - (n3 + n4);
                }
                if (this.b.f0 != 0 && this.b.f0 != -2) {
                    return this.b.f0;
                }
                return this.a.a();
            }

            @Override
            public int b() {
                return this.b.V;
            }

            @Override
            public int c() {
                return this.b.U;
            }

            @Override
            public int d() {
                if (!(this.b.getParent() instanceof View)) {
                    return this.a.d();
                }
                View view = (View)this.b.getParent();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams != null && layoutParams.width == -2) {
                    return this.a.d();
                }
                int n3 = view.getPaddingLeft();
                int n4 = view.getPaddingRight();
                int n5 = this.b.getLayoutParams() instanceof ViewGroup.MarginLayoutParams && (layoutParams = (ViewGroup.MarginLayoutParams)this.b.getLayoutParams()) != null ? layoutParams.leftMargin + layoutParams.rightMargin : 0;
                return view.getWidth() - n5 - (n3 + n4);
            }

            @Override
            public ViewGroup.LayoutParams e() {
                int n3 = this.b.f0 == 0 ? -2 : this.b.f0;
                return new ViewGroup.LayoutParams(-1, n3);
            }
        };
        n n6 = new n(this, n5, n4){
            public final n a;
            public final n b;
            public final ExtendedFloatingActionButton c;
            {
                this.c = extendedFloatingActionButton;
                this.a = n3;
                this.b = n4;
            }

            @Override
            public int a() {
                if (this.c.f0 == -1) {
                    return this.a.a();
                }
                if (this.c.f0 != 0 && this.c.f0 != -2) {
                    return this.c.f0;
                }
                return this.b.a();
            }

            @Override
            public int b() {
                return this.c.V;
            }

            @Override
            public int c() {
                return this.c.U;
            }

            @Override
            public int d() {
                if (this.c.e0 == -1) {
                    return this.a.d();
                }
                if (this.c.e0 != 0 && this.c.e0 != -2) {
                    return this.c.e0;
                }
                return this.b.d();
            }

            @Override
            public ViewGroup.LayoutParams e() {
                int n3 = this.c.e0;
                int n4 = -2;
                n3 = n3 == 0 ? -2 : this.c.e0;
                if (this.c.f0 != 0) {
                    n4 = this.c.f0;
                }
                return new ViewGroup.LayoutParams(n3, n4);
            }
        };
        if (n3 != 1) {
            if (n3 != 2) {
                return n6;
            }
            return n5;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void K(int n3, l iterator) {
        ViewGroup.LayoutParams layoutParams;
        b b3;
        block12: {
            block13: {
                if (n3 == 0) break block13;
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 3) {
                            iterator = new StringBuilder();
                            ((StringBuilder)((Object)iterator)).append("Unknown strategy type: ");
                            ((StringBuilder)((Object)iterator)).append(n3);
                            throw new IllegalStateException(((StringBuilder)((Object)iterator)).toString());
                        }
                        b3 = this.Q;
                        break block12;
                    } else {
                        b3 = this.P;
                    }
                    break block12;
                } else {
                    b3 = this.S;
                }
                break block12;
            }
            b3 = this.R;
        }
        if (b3.j()) {
            return;
        }
        if (!this.M()) {
            b3.d();
            b3.i((l)((Object)iterator));
            return;
        }
        if (n3 == 2) {
            layoutParams = this.getLayoutParams();
            if (layoutParams != null) {
                this.e0 = layoutParams.width;
                this.f0 = layoutParams.height;
            } else {
                this.e0 = this.getWidth();
                this.f0 = this.getHeight();
            }
        }
        this.measure(0, 0);
        layoutParams = b3.f();
        layoutParams.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, b3, (l)((Object)iterator)){
            public boolean a;
            public final b b;
            public final ExtendedFloatingActionButton c;
            {
                this.c = extendedFloatingActionButton;
                this.b = b3;
            }

            public void onAnimationCancel(Animator animator) {
                this.a = true;
                this.b.b();
            }

            public void onAnimationEnd(Animator animator) {
                this.b.a();
                if (!this.a) {
                    this.b.i(null);
                }
            }

            public void onAnimationStart(Animator animator) {
                this.b.onAnimationStart(animator);
                this.a = false;
            }
        });
        iterator = b3.g().iterator();
        while (true) {
            if (!iterator.hasNext()) {
                layoutParams.start();
                return;
            }
            layoutParams.addListener((Animator.AnimatorListener)iterator.next());
        }
    }

    public final void L() {
        this.d0 = this.getTextColors();
    }

    public final boolean M() {
        return this.N && (this.isLaidOut() || !this.J() && this.c0) && !this.isInEditMode();
    }

    public void N(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
    }

    public CharSequence getAccessibilityClassName() {
        return "com.google.android.material.floatingactionbutton.FloatingActionButton";
    }

    @Override
    public CoordinatorLayout.Behavior<ExtendedFloatingActionButton> getBehavior() {
        return this.W;
    }

    public int getCollapsedPadding() {
        return (this.getCollapsedSize() - this.getIconSize()) / 2;
    }

    public int getCollapsedSize() {
        int n3;
        int n4 = n3 = this.T;
        if (n3 < 0) {
            n4 = Math.min(this.getPaddingStart(), this.getPaddingEnd()) * 2 + this.getIconSize();
        }
        return n4;
    }

    public h getExtendMotionSpec() {
        return this.Q.e();
    }

    public h getHideMotionSpec() {
        return this.S.e();
    }

    public h getShowMotionSpec() {
        return this.R.e();
    }

    public h getShrinkMotionSpec() {
        return this.P.e();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.a0 && TextUtils.isEmpty((CharSequence)this.getText()) && this.getIcon() != null) {
            this.a0 = false;
            this.P.d();
        }
    }

    public void setAnimateShowBeforeLayout(boolean bl) {
        this.c0 = bl;
    }

    public void setAnimationEnabled(boolean bl) {
        this.N = bl;
    }

    public void setExtendMotionSpec(h h3) {
        this.Q.h(h3);
    }

    public void setExtendMotionSpecResource(int n3) {
        this.setExtendMotionSpec(a2.h.d(this.getContext(), n3));
    }

    public void setExtended(boolean bl) {
        b b3;
        if (this.a0 == bl || (b3 = bl ? this.Q : this.P).j()) {
            return;
        }
        b3.d();
    }

    public void setHideMotionSpec(h h3) {
        this.S.h(h3);
    }

    public void setHideMotionSpecResource(int n3) {
        this.setHideMotionSpec(a2.h.d(this.getContext(), n3));
    }

    public void setPadding(int n3, int n4, int n5, int n6) {
        super.setPadding(n3, n4, n5, n6);
        if (this.a0 && !this.b0) {
            this.U = this.getPaddingStart();
            this.V = this.getPaddingEnd();
        }
    }

    public void setPaddingRelative(int n3, int n4, int n5, int n6) {
        super.setPaddingRelative(n3, n4, n5, n6);
        if (this.a0 && !this.b0) {
            this.U = n3;
            this.V = n5;
        }
    }

    public void setShowMotionSpec(h h3) {
        this.R.h(h3);
    }

    public void setShowMotionSpecResource(int n3) {
        this.setShowMotionSpec(a2.h.d(this.getContext(), n3));
    }

    public void setShrinkMotionSpec(h h3) {
        this.P.h(h3);
    }

    public void setShrinkMotionSpecResource(int n3) {
        this.setShrinkMotionSpec(a2.h.d(this.getContext(), n3));
    }

    public void setTextColor(int n3) {
        super.setTextColor(n3);
        this.L();
    }

    public void setTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
        this.L();
    }

    public static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton>
    extends CoordinatorLayout.Behavior<T> {
        public Rect c;
        public boolean d;
        public boolean e;

        public ExtendedFloatingActionButtonBehavior() {
            this.d = false;
            this.e = true;
        }

        public ExtendedFloatingActionButtonBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, z1.m.ExtendedFloatingActionButton_Behavior_Layout);
            this.d = context.getBoolean(z1.m.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoHide, false);
            this.e = context.getBoolean(z1.m.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoShrink, true);
            context.recycle();
        }

        public static boolean K(View view) {
            if ((view = view.getLayoutParams()) instanceof CoordinatorLayout.e) {
                return ((CoordinatorLayout.e)view).f() instanceof BottomSheetBehavior;
            }
            return false;
        }

        public void I(ExtendedFloatingActionButton extendedFloatingActionButton) {
            int n3 = this.e ? 3 : 0;
            extendedFloatingActionButton.K(n3, null);
        }

        public boolean J(CoordinatorLayout coordinatorLayout, ExtendedFloatingActionButton extendedFloatingActionButton, Rect rect) {
            return super.f(coordinatorLayout, (View)extendedFloatingActionButton, rect);
        }

        public boolean L(CoordinatorLayout coordinatorLayout, ExtendedFloatingActionButton extendedFloatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                this.P(coordinatorLayout, (AppBarLayout)view, extendedFloatingActionButton);
            } else if (ExtendedFloatingActionButtonBehavior.K(view)) {
                this.Q(view, extendedFloatingActionButton);
            }
            return false;
        }

        public boolean M(CoordinatorLayout coordinatorLayout, ExtendedFloatingActionButton extendedFloatingActionButton, int n3) {
            View view;
            List list = coordinatorLayout.v((View)extendedFloatingActionButton);
            int n4 = list.size();
            for (int i3 = 0; i3 < n4 && !((view = (View)list.get(i3)) instanceof AppBarLayout ? this.P(coordinatorLayout, (AppBarLayout)view, extendedFloatingActionButton) : ExtendedFloatingActionButtonBehavior.K(view) && this.Q(view, extendedFloatingActionButton)); ++i3) {
            }
            coordinatorLayout.M((View)extendedFloatingActionButton, n3);
            return true;
        }

        public final boolean N(View view, ExtendedFloatingActionButton object) {
            object = (CoordinatorLayout.e)object.getLayoutParams();
            if (!this.d && !this.e) {
                return false;
            }
            return ((CoordinatorLayout.e)((Object)object)).e() == view.getId();
        }

        public void O(ExtendedFloatingActionButton extendedFloatingActionButton) {
            int n3 = this.e ? 2 : 1;
            extendedFloatingActionButton.K(n3, null);
        }

        public final boolean P(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, ExtendedFloatingActionButton extendedFloatingActionButton) {
            if (!this.N((View)appBarLayout, extendedFloatingActionButton)) {
                return false;
            }
            if (this.c == null) {
                this.c = new Rect();
            }
            Rect rect = this.c;
            com.google.android.material.internal.d.a(coordinatorLayout, (View)appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                this.O(extendedFloatingActionButton);
            } else {
                this.I(extendedFloatingActionButton);
            }
            return true;
        }

        public final boolean Q(View view, ExtendedFloatingActionButton extendedFloatingActionButton) {
            if (!this.N(view, extendedFloatingActionButton)) {
                return false;
            }
            CoordinatorLayout.e e3 = (CoordinatorLayout.e)extendedFloatingActionButton.getLayoutParams();
            if (view.getTop() < extendedFloatingActionButton.getHeight() / 2 + e3.topMargin) {
                this.O(extendedFloatingActionButton);
            } else {
                this.I(extendedFloatingActionButton);
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

    public class j
    extends m2.b {
        public final n g;
        public final boolean h;
        public final ExtendedFloatingActionButton i;

        public j(ExtendedFloatingActionButton extendedFloatingActionButton, a a4, n n3, boolean bl) {
            this.i = extendedFloatingActionButton;
            super(extendedFloatingActionButton, a4);
            this.g = n3;
            this.h = bl;
        }

        @Override
        public void a() {
            super.a();
            ExtendedFloatingActionButton.D(this.i, false);
            this.i.setHorizontallyScrolling(false);
            ViewGroup.LayoutParams layoutParams = this.i.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            layoutParams.width = this.g.e().width;
            layoutParams.height = this.g.e().height;
        }

        @Override
        public int c() {
            if (this.h) {
                return z1.b.mtrl_extended_fab_change_size_expand_motion_spec;
            }
            return z1.b.mtrl_extended_fab_change_size_collapse_motion_spec;
        }

        @Override
        public void d() {
            ExtendedFloatingActionButton.C(this.i, this.h);
            Object object = this.i.getLayoutParams();
            if (object == null) {
                return;
            }
            if (!this.h) {
                ExtendedFloatingActionButton.z(this.i, ((ViewGroup.LayoutParams)object).width);
                ExtendedFloatingActionButton.x(this.i, ((ViewGroup.LayoutParams)object).height);
            }
            ((ViewGroup.LayoutParams)object).width = this.g.e().width;
            ((ViewGroup.LayoutParams)object).height = this.g.e().height;
            if (this.h) {
                object = this.i;
                ((ExtendedFloatingActionButton)object).N(((ExtendedFloatingActionButton)object).d0);
            } else if (this.i.getText() != null && this.i.getText() != "") {
                this.i.N(ColorStateList.valueOf((int)0));
            }
            this.i.setPaddingRelative(this.g.c(), this.i.getPaddingTop(), this.g.b(), this.i.getPaddingBottom());
            this.i.requestLayout();
        }

        @Override
        public AnimatorSet f() {
            PropertyValuesHolder[] propertyValuesHolderArray;
            h h3 = this.m();
            if (h3.j("width")) {
                propertyValuesHolderArray = h3.g("width");
                propertyValuesHolderArray[0].setFloatValues(new float[]{this.i.getWidth(), this.g.d()});
                h3.l("width", propertyValuesHolderArray);
            }
            if (h3.j("height")) {
                propertyValuesHolderArray = h3.g("height");
                propertyValuesHolderArray[0].setFloatValues(new float[]{this.i.getHeight(), this.g.a()});
                h3.l("height", propertyValuesHolderArray);
            }
            if (h3.j("paddingStart")) {
                propertyValuesHolderArray = h3.g("paddingStart");
                propertyValuesHolderArray[0].setFloatValues(new float[]{this.i.getPaddingStart(), this.g.c()});
                h3.l("paddingStart", propertyValuesHolderArray);
            }
            if (h3.j("paddingEnd")) {
                propertyValuesHolderArray = h3.g("paddingEnd");
                propertyValuesHolderArray[0].setFloatValues(new float[]{this.i.getPaddingEnd(), this.g.b()});
                h3.l("paddingEnd", propertyValuesHolderArray);
            }
            if (h3.j("labelOpacity")) {
                propertyValuesHolderArray = h3.g("labelOpacity");
                boolean bl = this.h;
                float f3 = 1.0f;
                float f4 = bl ? 0.0f : 1.0f;
                if (!bl) {
                    f3 = 0.0f;
                }
                propertyValuesHolderArray[0].setFloatValues(new float[]{f4, f3});
                h3.l("labelOpacity", propertyValuesHolderArray);
            }
            return super.l(h3);
        }

        @Override
        public void i(l l3) {
        }

        @Override
        public boolean j() {
            return this.h == this.i.a0 || this.i.getIcon() == null || TextUtils.isEmpty((CharSequence)this.i.getText());
            {
            }
        }

        @Override
        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            ExtendedFloatingActionButton.C(this.i, this.h);
            ExtendedFloatingActionButton.D(this.i, true);
            this.i.setHorizontallyScrolling(true);
        }
    }

    public class k
    extends m2.b {
        public boolean g;
        public final ExtendedFloatingActionButton h;

        public k(ExtendedFloatingActionButton extendedFloatingActionButton, a a4) {
            this.h = extendedFloatingActionButton;
            super(extendedFloatingActionButton, a4);
        }

        @Override
        public void a() {
            super.a();
            ExtendedFloatingActionButton.E(this.h, 0);
            if (!this.g) {
                this.h.setVisibility(8);
            }
        }

        @Override
        public void b() {
            super.b();
            this.g = true;
        }

        @Override
        public int c() {
            return z1.b.mtrl_extended_fab_hide_motion_spec;
        }

        @Override
        public void d() {
            this.h.setVisibility(8);
        }

        @Override
        public void i(l l3) {
        }

        @Override
        public boolean j() {
            return this.h.I();
        }

        @Override
        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            this.g = false;
            this.h.setVisibility(0);
            ExtendedFloatingActionButton.E(this.h, 1);
        }
    }

    public static abstract class l {
    }

    public class m
    extends m2.b {
        public final ExtendedFloatingActionButton g;

        public m(ExtendedFloatingActionButton extendedFloatingActionButton, a a4) {
            this.g = extendedFloatingActionButton;
            super(extendedFloatingActionButton, a4);
        }

        @Override
        public void a() {
            super.a();
            ExtendedFloatingActionButton.E(this.g, 0);
        }

        @Override
        public int c() {
            return z1.b.mtrl_extended_fab_show_motion_spec;
        }

        @Override
        public void d() {
            this.g.setVisibility(0);
            this.g.setAlpha(1.0f);
            this.g.setScaleY(1.0f);
            this.g.setScaleX(1.0f);
        }

        @Override
        public void i(l l3) {
        }

        @Override
        public boolean j() {
            return this.g.J();
        }

        @Override
        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            this.g.setVisibility(0);
            ExtendedFloatingActionButton.E(this.g, 2);
        }
    }

    public static interface n {
        public int a();

        public int b();

        public int c();

        public int d();

        public ViewGroup.LayoutParams e();
    }
}

