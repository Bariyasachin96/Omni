/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accessibilityservice.AccessibilityServiceInfo
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Filterable
 *  android.widget.ListAdapter
 *  android.widget.TextView
 */
package com.google.android.material.textfield;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.ListPopupWindow;
import c.a;
import c.i;
import com.google.android.material.internal.z;
import com.google.android.material.textfield.TextInputLayout;
import s2.c;
import z1.e;
import z1.m;

public class MaterialAutoCompleteTextView
extends AppCompatAutoCompleteTextView {
    public final ListPopupWindow g;
    public final AccessibilityManager h;
    public final Rect i = new Rect();
    public final int j;
    public final float k;
    public ColorStateList l;
    public int m;
    public ColorStateList n;

    public MaterialAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.autoCompleteTextViewStyle);
    }

    public MaterialAutoCompleteTextView(Context object, AttributeSet attributeSet, int n3) {
        super(y2.a.d((Context)object, attributeSet, n3, 0), attributeSet, n3);
        object = this.getContext();
        attributeSet = z.i((Context)object, attributeSet, z1.m.MaterialAutoCompleteTextView, n3, c.i.Widget_AppCompat_AutoCompleteTextView, new int[0]);
        n3 = z1.m.MaterialAutoCompleteTextView_android_inputType;
        if (attributeSet.hasValue(n3) && attributeSet.getInt(n3, 0) == 0) {
            this.setKeyListener(null);
        }
        this.j = attributeSet.getResourceId(z1.m.MaterialAutoCompleteTextView_simpleItemLayout, z1.i.mtrl_auto_complete_simple_item);
        this.k = attributeSet.getDimensionPixelOffset(z1.m.MaterialAutoCompleteTextView_android_popupElevation, z1.e.mtrl_exposed_dropdown_menu_popup_elevation);
        n3 = z1.m.MaterialAutoCompleteTextView_dropDownBackgroundTint;
        if (attributeSet.hasValue(n3)) {
            this.l = ColorStateList.valueOf((int)attributeSet.getColor(n3, 0));
        }
        this.m = attributeSet.getColor(z1.m.MaterialAutoCompleteTextView_simpleItemSelectedColor, 0);
        this.n = s2.c.a((Context)object, (TypedArray)attributeSet, z1.m.MaterialAutoCompleteTextView_simpleItemSelectedRippleColor);
        this.h = (AccessibilityManager)object.getSystemService("accessibility");
        object = new ListPopupWindow((Context)object);
        this.g = object;
        ((ListPopupWindow)object).J(true);
        ((ListPopupWindow)object).D((View)this);
        ((ListPopupWindow)object).I(2);
        ((ListPopupWindow)object).p(this.getAdapter());
        ((ListPopupWindow)object).L(new AdapterView.OnItemClickListener(this){
            public final MaterialAutoCompleteTextView c;
            {
                this.c = materialAutoCompleteTextView;
            }

            public void onItemClick(AdapterView object, View view, int n3, long l3) {
                block2: {
                    int n4;
                    block4: {
                        block3: {
                            object = this.c;
                            object = n3 < 0 ? ((MaterialAutoCompleteTextView)((Object)object)).g.v() : object.getAdapter().getItem(n3);
                            MaterialAutoCompleteTextView materialAutoCompleteTextView = this.c;
                            materialAutoCompleteTextView.setText(materialAutoCompleteTextView.convertSelectionToString(object), false);
                            object = this.c.getOnItemClickListener();
                            if (object == null) break block2;
                            if (view == null) break block3;
                            n4 = n3;
                            if (n3 >= 0) break block4;
                        }
                        view = this.c.g.y();
                        n4 = this.c.g.x();
                        l3 = this.c.g.w();
                    }
                    object.onItemClick((AdapterView)this.c.g.h(), view, n4, l3);
                }
                this.c.g.dismiss();
            }
        });
        n3 = z1.m.MaterialAutoCompleteTextView_simpleItems;
        if (attributeSet.hasValue(n3)) {
            this.setSimpleItems(attributeSet.getResourceId(n3, 0));
        }
        attributeSet.recycle();
    }

    public void dismissDropDown() {
        if (this.g()) {
            this.g.dismiss();
            return;
        }
        super.dismissDropDown();
    }

    public final TextInputLayout f() {
        for (ViewParent viewParent = this.getParent(); viewParent != null; viewParent = viewParent.getParent()) {
            if (!(viewParent instanceof TextInputLayout)) continue;
            return (TextInputLayout)viewParent;
        }
        return null;
    }

    public final boolean g() {
        return this.i() || this.h();
        {
        }
    }

    public ColorStateList getDropDownBackgroundTintList() {
        return this.l;
    }

    public CharSequence getHint() {
        TextInputLayout textInputLayout = this.f();
        if (textInputLayout != null && textInputLayout.T()) {
            return textInputLayout.getHint();
        }
        return super.getHint();
    }

    public float getPopupElevation() {
        return this.k;
    }

    public int getSimpleItemSelectedColor() {
        return this.m;
    }

    public ColorStateList getSimpleItemSelectedRippleColor() {
        return this.n;
    }

    public final boolean h() {
        Object object = this.h;
        if (object != null && object.isEnabled() && (object = this.h.getEnabledAccessibilityServiceList(16)) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo)object.next();
                if (accessibilityServiceInfo.getSettingsActivityName() == null || !accessibilityServiceInfo.getSettingsActivityName().contains("SwitchAccess")) continue;
                return true;
            }
        }
        return false;
    }

    public final boolean i() {
        AccessibilityManager accessibilityManager = this.h;
        return accessibilityManager != null && accessibilityManager.isTouchExplorationEnabled();
    }

    public final int j() {
        ListAdapter listAdapter = this.getAdapter();
        TextInputLayout textInputLayout = this.f();
        int n3 = 0;
        if (listAdapter != null && textInputLayout != null) {
            int n4;
            int n5 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0);
            int n6 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0);
            int n7 = Math.max(0, this.g.x());
            int n8 = Math.min(listAdapter.getCount(), n7 + 15);
            Drawable drawable = null;
            n7 = 0;
            for (n4 = Math.max(0, n8 - 15); n4 < n8; ++n4) {
                int n9 = listAdapter.getItemViewType(n4);
                int n10 = n3;
                if (n9 != n3) {
                    drawable = null;
                    n10 = n9;
                }
                if ((drawable = listAdapter.getView(n4, (View)drawable, (ViewGroup)textInputLayout)).getLayoutParams() == null) {
                    drawable.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                }
                drawable.measure(n5, n6);
                n7 = Math.max(n7, drawable.getMeasuredWidth());
                n3 = n10;
            }
            drawable = this.g.g();
            n4 = n7;
            if (drawable != null) {
                drawable.getPadding(this.i);
                drawable = this.i;
                n4 = n7 + (drawable.left + drawable.right);
            }
            return n4 + textInputLayout.getEndIconView().getMeasuredWidth();
        }
        return 0;
    }

    public final void k() {
        TextInputLayout textInputLayout = this.f();
        if (textInputLayout != null) {
            textInputLayout.s0();
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayout = this.f();
        if (textInputLayout != null && textInputLayout.T() && super.getHint() == null && com.google.android.material.internal.i.d()) {
            this.setHint("");
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.g.dismiss();
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        if (View.MeasureSpec.getMode((int)n3) == Integer.MIN_VALUE) {
            this.setMeasuredDimension(Math.min(Math.max(this.getMeasuredWidth(), this.j()), View.MeasureSpec.getSize((int)n3)), this.getMeasuredHeight());
        }
    }

    public void onWindowFocusChanged(boolean bl) {
        if (this.g()) {
            return;
        }
        super.onWindowFocusChanged(bl);
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T t3) {
        super.setAdapter(t3);
        this.g.p(this.getAdapter());
    }

    public void setDropDownBackgroundDrawable(Drawable drawable) {
        super.setDropDownBackgroundDrawable(drawable);
        ListPopupWindow listPopupWindow = this.g;
        if (listPopupWindow != null) {
            listPopupWindow.b(drawable);
        }
    }

    public void setDropDownBackgroundTint(int n3) {
        this.setDropDownBackgroundTintList(ColorStateList.valueOf((int)n3));
    }

    public void setDropDownBackgroundTintList(ColorStateList colorStateList) {
        this.l = colorStateList;
        colorStateList = this.getDropDownBackground();
        if (colorStateList instanceof v2.i) {
            ((v2.i)colorStateList).i0(this.l);
        }
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        super.setOnItemSelectedListener(onItemSelectedListener);
        this.g.M(this.getOnItemSelectedListener());
    }

    public void setRawInputType(int n3) {
        super.setRawInputType(n3);
        this.k();
    }

    public void setSimpleItemSelectedColor(int n3) {
        this.m = n3;
        if (this.getAdapter() instanceof b) {
            ((b)this.getAdapter()).f();
        }
    }

    public void setSimpleItemSelectedRippleColor(ColorStateList colorStateList) {
        this.n = colorStateList;
        if (this.getAdapter() instanceof b) {
            ((b)this.getAdapter()).f();
        }
    }

    public void setSimpleItems(int n3) {
        this.setSimpleItems(this.getResources().getStringArray(n3));
    }

    public void setSimpleItems(String[] stringArray) {
        this.setAdapter(new b(this, this.getContext(), this.j, stringArray));
    }

    public void showDropDown() {
        if (this.g()) {
            this.g.e();
            return;
        }
        super.showDropDown();
    }

    public class b
    extends ArrayAdapter {
        public ColorStateList c;
        public ColorStateList d;
        public final MaterialAutoCompleteTextView e;

        public b(MaterialAutoCompleteTextView materialAutoCompleteTextView, Context context, int n3, String[] stringArray) {
            this.e = materialAutoCompleteTextView;
            super(context, n3, (Object[])stringArray);
            this.f();
        }

        public final ColorStateList a() {
            if (this.c() && this.d()) {
                int[] nArray = new int[]{16843623, -16842919};
                int[] nArray2 = new int[]{0x10100A1, -16842919};
                int n3 = this.e.n.getColorForState(nArray2, 0);
                int n4 = this.e.n.getColorForState(nArray, 0);
                n3 = h2.a.i(this.e.m, n3);
                n4 = h2.a.i(this.e.m, n4);
                int n5 = this.e.m;
                return new ColorStateList((int[][])new int[][]{nArray2, nArray, new int[0]}, new int[]{n3, n4, n5});
            }
            return null;
        }

        public final Drawable b() {
            if (!this.c()) {
                return null;
            }
            ColorDrawable colorDrawable = new ColorDrawable(this.e.m);
            if (this.d != null) {
                colorDrawable.setTintList(this.c);
                return new RippleDrawable(this.d, (Drawable)colorDrawable, null);
            }
            return colorDrawable;
        }

        public final boolean c() {
            return this.e.m != 0;
        }

        public final boolean d() {
            return this.e.n != null;
        }

        public final ColorStateList e() {
            if (!this.d()) {
                return null;
            }
            int[] nArray = new int[]{16842919};
            int n3 = this.e.n.getColorForState(nArray, 0);
            return new ColorStateList((int[][])new int[][]{nArray, new int[0]}, new int[]{n3, 0});
        }

        public void f() {
            this.d = this.e();
            this.c = this.a();
        }

        public View getView(int n3, View object, ViewGroup viewGroup) {
            View view = super.getView(n3, object, viewGroup);
            if (view instanceof TextView) {
                viewGroup = (TextView)view;
                object = this.e.getText().toString().contentEquals(viewGroup.getText()) ? this.b() : null;
                viewGroup.setBackground((Drawable)object);
            }
            return view;
        }
    }
}

