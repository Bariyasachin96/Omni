/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.FrameLayout
 */
package com.google.android.material.snackbar;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.app.s;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.internal.c0;
import com.google.android.material.snackbar.a;
import s2.c;
import v2.i;
import v2.o;
import z1.e;
import z1.m;

public abstract class BaseTransientBottomBar {
    public static final TimeInterpolator a = a2.a.b;
    public static final TimeInterpolator b = a2.a.a;
    public static final TimeInterpolator c = a2.a.d;
    public static final Handler d;
    public static final int[] e;
    public static final String f;

    static {
        e = new int[]{z1.c.snackbarStyle};
        f = BaseTransientBottomBar.class.getSimpleName();
        d = new Handler(Looper.getMainLooper(), new Handler.Callback(){

            public boolean handleMessage(Message message) {
                int n3 = message.what;
                if (n3 != 0) {
                    if (n3 != 1) {
                        return false;
                    }
                    s.a(message.obj);
                    throw null;
                }
                s.a(message.obj);
                throw null;
            }
        });
    }

    public static GradientDrawable c(int n3, Resources resources) {
        float f3 = resources.getDimension(z1.e.mtrl_snackbar_background_corner_radius);
        resources = new GradientDrawable();
        resources.setShape(0);
        resources.setCornerRadius(f3);
        resources.setColor(n3);
        return resources;
    }

    public static i d(int n3, o object) {
        object = new i((o)object);
        ((i)object).i0(ColorStateList.valueOf((int)n3));
        return object;
    }

    public static class Behavior
    extends SwipeDismissBehavior<View> {
        public final b m = new b(this);

        @Override
        public boolean J(View view) {
            return this.m.a(view);
        }

        @Override
        public boolean o(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            this.m.b(coordinatorLayout, view, motionEvent);
            return super.o(coordinatorLayout, view, motionEvent);
        }
    }

    public static class SnackbarBaseLayout
    extends FrameLayout {
        public static final View.OnTouchListener m = new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        };
        public o c;
        public int d;
        public final float e;
        public final float f;
        public final int g;
        public final int h;
        public ColorStateList i;
        public PorterDuff.Mode j;
        public Rect k;
        public boolean l;

        public SnackbarBaseLayout(Context context) {
            this(context, null);
        }

        public SnackbarBaseLayout(Context context, AttributeSet attributeSet) {
            super(y2.a.d(context, attributeSet, 0, 0), attributeSet);
            Context context2 = this.getContext();
            context = context2.obtainStyledAttributes(attributeSet, z1.m.SnackbarLayout);
            int n3 = z1.m.SnackbarLayout_elevation;
            if (context.hasValue(n3)) {
                this.setElevation(context.getDimensionPixelSize(n3, 0));
            }
            this.d = context.getInt(z1.m.SnackbarLayout_animationMode, 0);
            if (context.hasValue(z1.m.SnackbarLayout_shapeAppearance) || context.hasValue(z1.m.SnackbarLayout_shapeAppearanceOverlay)) {
                this.c = o.e(context2, attributeSet, 0, 0).m();
            }
            this.e = context.getFloat(z1.m.SnackbarLayout_backgroundOverlayColorAlpha, 1.0f);
            this.setBackgroundTintList(s2.c.a(context2, (TypedArray)context, z1.m.SnackbarLayout_backgroundTint));
            this.setBackgroundTintMode(c0.n(context.getInt(z1.m.SnackbarLayout_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN));
            this.f = context.getFloat(z1.m.SnackbarLayout_actionTextColorAlpha, 1.0f);
            this.g = context.getDimensionPixelSize(z1.m.SnackbarLayout_android_maxWidth, -1);
            this.h = context.getDimensionPixelSize(z1.m.SnackbarLayout_maxActionInlineWidth, -1);
            context.recycle();
            this.setOnTouchListener(m);
            this.setFocusable(true);
            if (this.getBackground() == null) {
                this.setBackground(this.a());
            }
        }

        private void setBaseTransientBottomBar(BaseTransientBottomBar baseTransientBottomBar) {
        }

        public final Drawable a() {
            int n3 = h2.a.k((View)this, z1.c.colorSurface, z1.c.colorOnSurface, this.getBackgroundOverlayColorAlpha());
            Object object = this.c;
            object = object != null ? BaseTransientBottomBar.d(n3, (o)object) : BaseTransientBottomBar.c(n3, this.getResources());
            if (this.i != null) {
                object = h0.a.r((Drawable)object);
                object.setTintList(this.i);
                return object;
            }
            return h0.a.r((Drawable)object);
        }

        public final void b(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.k = new Rect(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        }

        public float getActionTextColorAlpha() {
            return this.f;
        }

        public int getAnimationMode() {
            return this.d;
        }

        public float getBackgroundOverlayColorAlpha() {
            return this.e;
        }

        public int getMaxInlineActionWidth() {
            return this.h;
        }

        public int getMaxWidth() {
            return this.g;
        }

        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.requestApplyInsets();
        }

        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
        }

        public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
            super.onLayout(bl, n3, n4, n5, n6);
        }

        public void onMeasure(int n3, int n4) {
            int n5;
            super.onMeasure(n3, n4);
            if (this.g > 0 && (n3 = this.getMeasuredWidth()) > (n5 = this.g)) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n5, (int)0x40000000), n4);
            }
        }

        public void setAnimationMode(int n3) {
            this.d = n3;
        }

        public void setBackground(Drawable drawable) {
            this.setBackgroundDrawable(drawable);
        }

        public void setBackgroundDrawable(Drawable drawable) {
            Drawable drawable2 = drawable;
            if (drawable != null) {
                drawable2 = drawable;
                if (this.i != null) {
                    drawable2 = h0.a.r(drawable.mutate());
                    drawable2.setTintList(this.i);
                    drawable2.setTintMode(this.j);
                }
            }
            super.setBackgroundDrawable(drawable2);
        }

        public void setBackgroundTintList(ColorStateList colorStateList) {
            this.i = colorStateList;
            if (this.getBackground() != null) {
                Drawable drawable = h0.a.r(this.getBackground().mutate());
                drawable.setTintList(colorStateList);
                drawable.setTintMode(this.j);
                if (drawable != this.getBackground()) {
                    super.setBackgroundDrawable(drawable);
                }
            }
        }

        public void setBackgroundTintMode(PorterDuff.Mode mode) {
            this.j = mode;
            if (this.getBackground() != null) {
                Drawable drawable = h0.a.r(this.getBackground().mutate());
                drawable.setTintMode(mode);
                if (drawable != this.getBackground()) {
                    super.setBackgroundDrawable(drawable);
                }
            }
        }

        public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            super.setLayoutParams(layoutParams);
            if (!this.l && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                this.b((ViewGroup.MarginLayoutParams)layoutParams);
            }
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            View.OnTouchListener onTouchListener = onClickListener != null ? null : m;
            this.setOnTouchListener(onTouchListener);
            super.setOnClickListener(onClickListener);
        }
    }

    public static class b {
        public b(SwipeDismissBehavior swipeDismissBehavior) {
            swipeDismissBehavior.P(0.1f);
            swipeDismissBehavior.O(0.6f);
            swipeDismissBehavior.Q(0);
        }

        public boolean a(View view) {
            return view instanceof SnackbarBaseLayout;
        }

        public void b(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            int n3 = motionEvent.getActionMasked();
            if (n3 != 0) {
                if (n3 == 1 || n3 == 3) {
                    com.google.android.material.snackbar.a.b().f(null);
                    return;
                }
            } else if (coordinatorLayout.F(view, (int)motionEvent.getX(), (int)motionEvent.getY())) {
                com.google.android.material.snackbar.a.b().e(null);
            }
        }
    }
}

