/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageButton
 *  android.widget.TextView
 */
package com.google.android.material.search;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.j;
import androidx.customview.view.AbsSavedState;
import c.a;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.a0;
import com.google.android.material.internal.z;
import com.google.android.material.search.b;
import s2.c;
import v2.i;
import v2.o;
import z1.e;
import z1.f;
import z1.g;
import z1.l;
import z1.m;

public class SearchBar
extends Toolbar {
    public static final int v0 = z1.l.Widget_Material3_SearchBar;
    public final TextView V;
    public final TextView W;
    public final FrameLayout a0;
    public final int b0;
    public boolean c0;
    public final ColorStateList d0;
    public final boolean e0;
    public final boolean f0;
    public final b g0;
    public final Drawable h0;
    public final boolean i0;
    public final boolean j0;
    public View k0;
    public Integer l0;
    public Drawable m0;
    public int n0;
    public boolean o0;
    public i p0;
    public boolean q0;
    public int r0;
    public ActionMenuView s0;
    public ImageButton t0;
    public final AppBarLayout.e u0;

    public SearchBar(Context context) {
        this(context, null);
    }

    public SearchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialSearchBarStyle);
    }

    public SearchBar(Context context, AttributeSet object, int n3) {
        int n4 = v0;
        super(y2.a.d(context, object, n3, n4), (AttributeSet)object, n3);
        this.n0 = -1;
        this.u0 = new AppBarLayout.e(this){
            public final SearchBar a;
            {
                this.a = searchBar;
            }

            @Override
            public void a(float f3, int n3, float f4) {
                if (this.a.d0 != null) {
                    n3 = h2.a.j(this.a.b0, this.a.d0.getDefaultColor(), f4);
                    this.a.p0.i0(ColorStateList.valueOf((int)n3));
                }
            }
        };
        Context context2 = this.getContext();
        this.j0((AttributeSet)object);
        this.h0 = d.a.b(context2, this.getDefaultNavigationIconResource());
        this.g0 = new b();
        context = com.google.android.material.internal.z.i(context2, object, z1.m.SearchBar, n3, n4, new int[0]);
        o o3 = v2.o.e(context2, object, n3, n4).m();
        this.b0 = n3 = context.getColor(z1.m.SearchBar_backgroundTint, 0);
        this.d0 = s2.c.a(context2, (TypedArray)context, z1.m.SearchBar_liftOnScrollColor);
        float f3 = context.getDimension(z1.m.SearchBar_elevation, 0.0f);
        this.f0 = context.getBoolean(z1.m.SearchBar_defaultMarginsEnabled, true);
        this.o0 = context.getBoolean(z1.m.SearchBar_defaultScrollFlagsEnabled, true);
        boolean bl = context.getBoolean(z1.m.SearchBar_hideNavigationIcon, false);
        this.j0 = context.getBoolean(z1.m.SearchBar_forceDefaultNavigationOnClickListener, false);
        this.i0 = context.getBoolean(z1.m.SearchBar_tintNavigationIcon, true);
        n4 = z1.m.SearchBar_navigationIconTint;
        if (context.hasValue(n4)) {
            this.l0 = context.getColor(n4, -1);
        }
        int n5 = context.getResourceId(z1.m.SearchBar_android_textAppearance, -1);
        object = context.getString(z1.m.SearchBar_android_text);
        String string = context.getString(z1.m.SearchBar_android_hint);
        float f4 = context.getDimension(z1.m.SearchBar_strokeWidth, -1.0f);
        n4 = context.getColor(z1.m.SearchBar_strokeColor, 0);
        this.q0 = context.getBoolean(z1.m.SearchBar_textCentered, false);
        this.c0 = context.getBoolean(z1.m.SearchBar_liftOnScroll, false);
        this.r0 = context.getDimensionPixelSize(z1.m.SearchBar_android_maxWidth, -1);
        context.recycle();
        if (!bl) {
            this.X();
        }
        this.setClickable(true);
        this.setFocusable(true);
        LayoutInflater.from((Context)context2).inflate(z1.i.mtrl_search_bar, (ViewGroup)this);
        this.e0 = true;
        this.V = (TextView)this.findViewById(z1.g.open_search_bar_text_view);
        this.W = (TextView)this.findViewById(z1.g.open_search_bar_placeholder_text_view);
        this.a0 = (FrameLayout)this.findViewById(z1.g.open_search_bar_text_view_container);
        this.setElevation(f3);
        this.Y(n5, (String)object, string);
        this.W(o3, n3, f3, f4, n4);
    }

    private Drawable c0(Drawable drawable) {
        Object object = drawable;
        if (this.i0) {
            if (drawable == null) {
                object = drawable;
            } else {
                int n3;
                object = this.l0;
                if (object != null) {
                    n3 = (Integer)object;
                } else {
                    n3 = drawable == this.h0 ? z1.c.colorOnSurfaceVariant : z1.c.colorOnSurface;
                    n3 = h2.a.d((View)this, n3);
                }
                object = h0.a.r(drawable.mutate());
                object.setTint(n3);
            }
        }
        return object;
    }

    private AppBarLayout getAppBarLayoutParentIfExists() {
        for (ViewParent viewParent = this.getParent(); viewParent != null; viewParent = viewParent.getParent()) {
            if (!(viewParent instanceof AppBarLayout)) continue;
            return (AppBarLayout)viewParent;
        }
        return null;
    }

    private void setNavigationIconDecorative(boolean bl) {
        ImageButton imageButton = com.google.android.material.internal.a0.d(this);
        if (imageButton == null) {
            return;
        }
        imageButton.setClickable(bl ^ true);
        imageButton.setFocusable(bl ^ true);
        Object object = imageButton.getBackground();
        if (object != null) {
            this.m0 = object;
        }
        object = bl ? null : this.m0;
        imageButton.setBackgroundDrawable(object);
        this.g0();
    }

    public final void S() {
        AppBarLayout appBarLayout = this.getAppBarLayoutParentIfExists();
        if (appBarLayout != null && this.d0 != null) {
            appBarLayout.c(this.u0);
        }
    }

    public final int T(int n3, int n4) {
        if (n3 == 0) {
            return n4;
        }
        return n3;
    }

    public final ActionMenuView U() {
        if (this.s0 == null) {
            this.s0 = com.google.android.material.internal.a0.a(this);
        }
        return this.s0;
    }

    public final ImageButton V() {
        if (this.t0 == null) {
            this.t0 = com.google.android.material.internal.a0.d(this);
        }
        return this.t0;
    }

    public final void W(o object, int n3, float f3, float f4, int n4) {
        this.p0 = object = new i((o)object);
        ((i)object).W(this.getContext());
        this.p0.h0(f3);
        if (f4 >= 0.0f) {
            this.p0.s0(f4, n4);
        }
        n4 = h2.a.d((View)this, a.colorControlHighlight);
        this.p0.i0(ColorStateList.valueOf((int)n3));
        ColorStateList colorStateList = ColorStateList.valueOf((int)n4);
        object = this.p0;
        this.setBackground((Drawable)new RippleDrawable(colorStateList, (Drawable)object, (Drawable)object));
    }

    public final void X() {
        Drawable drawable = this.getNavigationIcon() == null ? this.h0 : this.getNavigationIcon();
        this.setNavigationIcon(drawable);
        this.setNavigationIconDecorative(true);
    }

    public final void Y(int n3, String string, String string2) {
        if (n3 != -1) {
            androidx.core.widget.j.m(this.V, n3);
            androidx.core.widget.j.m(this.W, n3);
        }
        this.setText(string);
        this.setHint(string2);
        this.setTextCentered(this.q0);
    }

    public final void Z(View view, int n3, int n4, int n5, int n6) {
        if (this.getLayoutDirection() == 1) {
            view.layout(this.getMeasuredWidth() - n5, n4, this.getMeasuredWidth() - n3, n6);
            return;
        }
        view.layout(n3, n4, n5, n6);
    }

    public final void a0() {
        int n3 = this.getMeasuredWidth() / 2 - this.a0.getMeasuredWidth() / 2;
        int n4 = this.a0.getMeasuredWidth();
        int n5 = this.getMeasuredHeight() / 2 - this.a0.getMeasuredHeight() / 2;
        int n6 = this.a0.getMeasuredHeight();
        int n7 = this.getLayoutDirection();
        int n8 = 1;
        if (n7 != 1) {
            n8 = 0;
        }
        ActionMenuView actionMenuView = this.U();
        ImageButton imageButton = this.V();
        int n9 = this.a0.getMeasuredWidth() / 2 - this.V.getMeasuredWidth() / 2;
        n7 = this.V.getMeasuredWidth();
        int n10 = n9 + n3;
        ActionMenuView actionMenuView2 = n8 != 0 ? actionMenuView : imageButton;
        if (n8 != 0) {
            actionMenuView = imageButton;
        }
        n8 = actionMenuView2 != null ? Math.max(actionMenuView2.getRight() - n10, 0) : 0;
        n9 = n7 + n9 + n3 + n8;
        n7 = actionMenuView != null ? Math.max(n9 - actionMenuView.getLeft(), 0) : 0;
        n10 = n10 + n8 - n7;
        n9 -= n7;
        n10 = Math.max(this.getPaddingLeft() - n10, this.getContentInsetLeft() - n10);
        n9 = Math.max(n9 - (this.getMeasuredWidth() - this.getPaddingRight()), n9 - (this.getMeasuredWidth() - this.getContentInsetRight()));
        n8 = n8 - n7 + Math.max(n10, 0) - Math.max(n9, 0);
        this.a0.layout(n3 + n8, n5, n4 + n3 + n8, n6 + n5);
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        if (this.e0 && this.k0 == null && !(view instanceof ActionMenuView)) {
            this.k0 = view;
            view.setAlpha(0.0f);
        }
        super.addView(view, n3, layoutParams);
    }

    public final void b0(View view) {
        if (view == null) {
            return;
        }
        int n3 = view.getMeasuredWidth();
        int n4 = this.getMeasuredWidth() / 2 - n3 / 2;
        int n5 = view.getMeasuredHeight();
        int n6 = this.getMeasuredHeight() / 2 - n5 / 2;
        this.Z(view, n4, n6, n4 + n3, n6 + n5);
    }

    public final void d0(int n3, int n4) {
        View view = this.k0;
        if (view != null) {
            view.measure(n3, n4);
        }
    }

    public final void e0() {
        AppBarLayout appBarLayout = this.getAppBarLayoutParentIfExists();
        if (appBarLayout != null) {
            appBarLayout.y(this.u0);
        }
    }

    public final void f0() {
        if (this.f0 && this.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            Resources resources = this.getResources();
            int n3 = resources.getDimensionPixelSize(z1.e.m3_searchbar_margin_horizontal);
            int n4 = resources.getDimensionPixelSize(this.getDefaultMarginVerticalResource());
            resources = (ViewGroup.MarginLayoutParams)this.getLayoutParams();
            resources.leftMargin = this.T(resources.leftMargin, n3);
            resources.topMargin = this.T(resources.topMargin, n4);
            resources.rightMargin = this.T(resources.rightMargin, n3);
            resources.bottomMargin = this.T(resources.bottomMargin, n4);
        }
    }

    public final void g0() {
        if (Build.VERSION.SDK_INT < 34) {
            return;
        }
        int n3 = this.getLayoutDirection();
        boolean bl = true;
        int n4 = 0;
        if (n3 != 1) {
            bl = false;
        }
        Object object = com.google.android.material.internal.a0.d(this);
        n3 = object != null && object.isClickable() ? (bl ? this.getWidth() - object.getLeft() : object.getRight()) : 0;
        object = com.google.android.material.internal.a0.a(this);
        if (object != null) {
            n4 = bl ? object.getRight() : this.getWidth() - object.getLeft();
        }
        int n5 = bl ? n4 : n3;
        float f3 = -n5;
        if (!bl) {
            n3 = n4;
        }
        com.google.android.material.search.a.a(this, f3, 0.0f, -n3, 0.0f);
    }

    public View getCenterView() {
        return this.k0;
    }

    public float getCompatElevation() {
        i i3 = this.p0;
        if (i3 != null) {
            return i3.C();
        }
        return this.getElevation();
    }

    public float getCornerSize() {
        return this.p0.P();
    }

    public int getDefaultMarginVerticalResource() {
        return z1.e.m3_searchbar_margin_vertical;
    }

    public int getDefaultNavigationIconResource() {
        return z1.f.ic_search_black_24;
    }

    public CharSequence getHint() {
        return this.V.getHint();
    }

    public int getMaxWidth() {
        return this.r0;
    }

    public int getMenuResId() {
        return this.n0;
    }

    public TextView getPlaceholderTextView() {
        return this.W;
    }

    public int getStrokeColor() {
        return this.p0.L().getDefaultColor();
    }

    public float getStrokeWidth() {
        return this.p0.N();
    }

    public CharSequence getText() {
        return this.V.getText();
    }

    public boolean getTextCentered() {
        return this.q0;
    }

    public TextView getTextView() {
        return this.V;
    }

    public final void h0() {
        if (this.getLayoutParams() instanceof AppBarLayout.LayoutParams) {
            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams)this.getLayoutParams();
            if (this.o0) {
                if (layoutParams.c() == 0) {
                    layoutParams.g(53);
                    return;
                }
            } else if (layoutParams.c() == 53) {
                layoutParams.g(0);
            }
        }
    }

    public void i0() {
        this.g0.b(this);
    }

    public final void j0(AttributeSet attributeSet) {
        block3: {
            block4: {
                block2: {
                    if (attributeSet == null) break block2;
                    if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "title") != null) break block3;
                    if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "subtitle") != null) break block4;
                }
                return;
            }
            throw new UnsupportedOperationException("SearchBar does not support subtitle. Use hint or text instead.");
        }
        throw new UnsupportedOperationException("SearchBar does not support title. Use hint or text instead.");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v2.j.f((View)this, this.p0);
        this.f0();
        this.h0();
        if (this.c0) {
            this.S();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.e0();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)EditText.class.getCanonicalName());
        accessibilityNodeInfo.setEditable(this.isEnabled());
        CharSequence charSequence = this.getText();
        boolean bl = TextUtils.isEmpty((CharSequence)charSequence);
        accessibilityNodeInfo.setHintText(this.getHint());
        accessibilityNodeInfo.setShowingHintText(bl);
        if (bl) {
            charSequence = this.getHint();
        }
        accessibilityNodeInfo.setText(charSequence);
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        View view = this.k0;
        if (view != null) {
            this.b0(view);
        }
        this.g0();
        if (this.V != null && this.q0) {
            this.a0();
        }
    }

    @Override
    public void onMeasure(int n3, int n4) {
        int n5 = this.r0;
        int n6 = n3;
        if (n5 >= 0) {
            n6 = n3;
            if (n5 < View.MeasureSpec.getSize((int)n3)) {
                n3 = View.MeasureSpec.getMode((int)n3);
                n6 = View.MeasureSpec.makeMeasureSpec((int)this.r0, (int)n3);
            }
        }
        super.onMeasure(n6, n4);
        this.d0(n6, n4);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.setText(parcelable.e);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        CharSequence charSequence = this.getText();
        charSequence = charSequence == null ? null : charSequence.toString();
        savedState.e = charSequence;
        return savedState;
    }

    public void setCenterView(View view) {
        View view2 = this.k0;
        if (view2 != null) {
            this.removeView(view2);
            this.k0 = null;
        }
        if (view != null) {
            this.addView(view);
        }
    }

    public void setDefaultScrollFlagsEnabled(boolean bl) {
        this.o0 = bl;
        this.h0();
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        i i3 = this.p0;
        if (i3 != null) {
            i3.h0(f3);
        }
    }

    public void setHint(int n3) {
        this.V.setHint(n3);
    }

    public void setHint(CharSequence charSequence) {
        this.V.setHint(charSequence);
    }

    public void setLiftOnScroll(boolean bl) {
        this.c0 = bl;
        if (bl) {
            this.S();
            return;
        }
        this.e0();
    }

    public void setMaxWidth(int n3) {
        if (this.r0 != n3) {
            this.r0 = n3;
            this.requestLayout();
        }
    }

    @Override
    public void setNavigationIcon(Drawable drawable) {
        super.setNavigationIcon(this.c0(drawable));
    }

    @Override
    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        if (this.j0) {
            return;
        }
        super.setNavigationOnClickListener(onClickListener);
        boolean bl = onClickListener == null;
        this.setNavigationIconDecorative(bl);
    }

    public void setOnLoadAnimationFadeInEnabled(boolean bl) {
        this.g0.a(bl);
    }

    public void setPlaceholderText(String string) {
        this.W.setText((CharSequence)string);
    }

    public void setStrokeColor(int n3) {
        if (this.getStrokeColor() != n3) {
            this.p0.u0(ColorStateList.valueOf((int)n3));
        }
    }

    public void setStrokeWidth(float f3) {
        if (this.getStrokeWidth() != f3) {
            this.p0.v0(f3);
        }
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
    }

    public void setText(int n3) {
        this.V.setText(n3);
        this.W.setText(n3);
    }

    public void setText(CharSequence charSequence) {
        this.V.setText(charSequence);
        this.W.setText(charSequence);
    }

    public void setTextCentered(boolean bl) {
        this.q0 = bl;
        TextView textView = this.V;
        if (textView == null) {
            return;
        }
        textView = (FrameLayout.LayoutParams)textView.getLayoutParams();
        if (bl) {
            textView.gravity = 1;
            this.V.setGravity(1);
        } else {
            textView.gravity = 0;
            this.V.setGravity(0);
        }
        this.V.setLayoutParams((ViewGroup.LayoutParams)textView);
        this.W.setLayoutParams((ViewGroup.LayoutParams)textView);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
    }

    @Override
    public void z(int n3) {
        super.z(n3);
        this.n0 = n3;
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState b(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] c(int n3) {
                return new SavedState[n3];
            }
        };
        public String e;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = parcel.readString();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeString(this.e);
        }
    }

    public static class ScrollingViewBehavior
    extends AppBarLayout.ScrollingViewBehavior {
        public boolean j = false;

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override
        public boolean T() {
            return true;
        }

        public final void Y(AppBarLayout appBarLayout) {
            appBarLayout.setBackgroundColor(0);
            appBarLayout.setTargetElevation(0.0f);
        }

        @Override
        public boolean l(CoordinatorLayout coordinatorLayout, View view, View view2) {
            boolean bl = super.l(coordinatorLayout, view, view2);
            if (!this.j && view2 instanceof AppBarLayout) {
                this.j = true;
                this.Y((AppBarLayout)view2);
            }
            return bl;
        }
    }
}

