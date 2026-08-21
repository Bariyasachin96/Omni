/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewStub
 *  android.widget.CheckedTextView
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.r0;
import androidx.core.widget.j;
import c.a;
import com.google.android.material.internal.ForegroundLinearLayout;
import f0.h;
import o0.x0;
import p0.s;
import z1.e;
import z1.f;
import z1.i;

public class NavigationMenuItemView
extends ForegroundLinearLayout
implements j.a {
    public static final int[] I = new int[]{0x10100A0};
    public boolean A = true;
    public final CheckedTextView B;
    public FrameLayout C;
    public g D;
    public ColorStateList E;
    public boolean F;
    public Drawable G;
    public final o0.a H;
    public int x;
    public boolean y;
    public boolean z;

    public NavigationMenuItemView(Context context) {
        this(context, null);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationMenuItemView(Context context, AttributeSet object, int n3) {
        super(context, (AttributeSet)object, n3);
        object = new o0.a(this){
            public final NavigationMenuItemView d;
            {
                this.d = navigationMenuItemView;
            }

            @Override
            public void g(View view, s s3) {
                super.g(view, s3);
                s3.f0(this.d.z);
            }
        };
        this.H = object;
        this.setOrientation(0);
        LayoutInflater.from((Context)context).inflate(z1.i.design_navigation_menu_item, (ViewGroup)this, true);
        this.setIconSize(context.getResources().getDimensionPixelSize(z1.e.design_navigation_icon_size));
        context = (CheckedTextView)this.findViewById(z1.g.design_menu_item_text);
        this.B = context;
        x0.h0((View)context, (o0.a)object);
    }

    private void setActionView(View view) {
        if (view != null) {
            if (this.C == null) {
                this.C = (FrameLayout)((ViewStub)this.findViewById(z1.g.design_menu_item_action_area_stub)).inflate();
            }
            if (view.getParent() != null) {
                ((ViewGroup)view.getParent()).removeView(view);
            }
            this.C.removeAllViews();
            this.C.addView(view);
        }
    }

    public final StateListDrawable A() {
        TypedValue typedValue = new TypedValue();
        if (this.getContext().getTheme().resolveAttribute(a.colorControlHighlight, typedValue, true)) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(I, (Drawable)new ColorDrawable(typedValue.data));
            typedValue = new ColorDrawable(0);
            stateListDrawable.addState(ViewGroup.EMPTY_STATE_SET, (Drawable)typedValue);
            return stateListDrawable;
        }
        return null;
    }

    public void B(g g3, boolean bl) {
        this.A = bl;
        this.d(g3, 0);
    }

    public void C() {
        FrameLayout frameLayout = this.C;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        this.B.setCompoundDrawables(null, null, null, null);
    }

    public final boolean D() {
        return this.D.getTitle() == null && this.D.getIcon() == null && this.D.getActionView() != null;
    }

    @Override
    public boolean c() {
        return false;
    }

    @Override
    public void d(g g3, int n3) {
        this.D = g3;
        if (g3.getItemId() > 0) {
            this.setId(g3.getItemId());
        }
        n3 = g3.isVisible() ? 0 : 8;
        this.setVisibility(n3);
        if (this.getBackground() == null) {
            this.setBackground((Drawable)this.A());
        }
        this.setCheckable(g3.isCheckable());
        this.setChecked(g3.isChecked());
        this.setEnabled(g3.isEnabled());
        this.setTitle(g3.getTitle());
        this.setIcon(g3.getIcon());
        this.setActionView(g3.getActionView());
        this.setContentDescription(g3.getContentDescription());
        r0.a((View)this, g3.getTooltipText());
        this.z();
    }

    @Override
    public g getItemData() {
        return this.D;
    }

    public int[] onCreateDrawableState(int n3) {
        int[] nArray = super.onCreateDrawableState(n3 + 1);
        g g3 = this.D;
        if (g3 != null && g3.isCheckable() && this.D.isChecked()) {
            View.mergeDrawableStates((int[])nArray, (int[])I);
        }
        return nArray;
    }

    public void setCheckable(boolean bl) {
        this.refreshDrawableState();
        if (this.z != bl) {
            this.z = bl;
            this.H.l((View)this.B, 2048);
        }
    }

    public void setChecked(boolean bl) {
        this.refreshDrawableState();
        this.B.setChecked(bl);
        CheckedTextView checkedTextView = this.B;
        Typeface typeface = checkedTextView.getTypeface();
        int n3 = bl && this.A ? 1 : 0;
        checkedTextView.setTypeface(typeface, n3);
    }

    public void setHorizontalPadding(int n3) {
        this.setPadding(n3, this.getPaddingTop(), n3, this.getPaddingBottom());
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            Drawable drawable2 = drawable;
            if (this.F) {
                drawable2 = drawable.getConstantState();
                if (drawable2 != null) {
                    drawable = drawable2.newDrawable();
                }
                drawable2 = h0.a.r(drawable).mutate();
                drawable2.setTintList(this.E);
            }
            int n3 = this.x;
            drawable2.setBounds(0, 0, n3, n3);
            drawable = drawable2;
        } else if (this.y) {
            if (this.G == null) {
                this.G = drawable = f0.h.e(this.getResources(), z1.f.navigation_empty_icon, this.getContext().getTheme());
                if (drawable != null) {
                    int n4 = this.x;
                    drawable.setBounds(0, 0, n4, n4);
                }
            }
            drawable = this.G;
        }
        this.B.setCompoundDrawablesRelative(drawable, null, null, null);
    }

    public void setIconPadding(int n3) {
        this.B.setCompoundDrawablePadding(n3);
    }

    public void setIconSize(int n3) {
        this.x = n3;
    }

    public void setIconTintList(ColorStateList object) {
        this.E = object;
        boolean bl = object != null;
        this.F = bl;
        object = this.D;
        if (object != null) {
            this.setIcon(((g)object).getIcon());
        }
    }

    public void setMaxLines(int n3) {
        this.B.setMaxLines(n3);
    }

    public void setNeedsEmptyIcon(boolean bl) {
        this.y = bl;
    }

    public void setShortcut(boolean bl, char c3) {
    }

    public void setTextAppearance(int n3) {
        androidx.core.widget.j.m((TextView)this.B, n3);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.B.setTextColor(colorStateList);
    }

    public void setTitle(CharSequence charSequence) {
        this.B.setText(charSequence);
    }

    public final void z() {
        if (this.D()) {
            this.B.setVisibility(8);
            Object object = this.C;
            if (object != null) {
                object = (LinearLayoutCompat.LayoutParams)object.getLayoutParams();
                object.width = -1;
                this.C.setLayoutParams((ViewGroup.LayoutParams)object);
                return;
            }
        } else {
            this.B.setVisibility(0);
            Object object = this.C;
            if (object != null) {
                object = (LinearLayoutCompat.LayoutParams)object.getLayoutParams();
                object.width = -2;
                this.C.setLayoutParams((ViewGroup.LayoutParams)object);
            }
        }
    }
}

