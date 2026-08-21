/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.AnimatedStateListDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.autofill.AutofillManager
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package com.google.android.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.widget.CompoundButton;
import androidx.appcompat.app.s;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.m0;
import androidx.core.widget.c;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import j2.d;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import n1.b;
import z1.f;
import z1.g;
import z1.k;
import z1.l;
import z1.m;

public class MaterialCheckBox
extends AppCompatCheckBox {
    public static final int A = z1.l.Widget_MaterialComponents_CompoundButton_CheckBox;
    public static final int[] B = new int[]{z1.c.state_indeterminate};
    public static final int[] C;
    public static final int[][] D;
    public static final int E;
    public final LinkedHashSet g;
    public final LinkedHashSet h;
    public ColorStateList i;
    public boolean j;
    public boolean k;
    public boolean l;
    public CharSequence m;
    public Drawable n;
    public Drawable o;
    public boolean p;
    public ColorStateList q;
    public ColorStateList r;
    public PorterDuff.Mode s;
    public int t;
    public int[] u;
    public boolean v;
    public CharSequence w;
    public CompoundButton.OnCheckedChangeListener x;
    public final n1.c y;
    public final b z;

    static {
        int n3 = z1.c.state_error;
        C = new int[]{n3};
        D = new int[][]{{16842910, n3}, {16842910, 0x10100A0}, {16842910, -16842912}, {-16842910, 0x10100A0}, {-16842910, -16842912}};
        E = Resources.getSystem().getIdentifier("btn_check_material_anim", "drawable", "android");
    }

    public MaterialCheckBox(Context context) {
        this(context, null);
    }

    public MaterialCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, c.a.checkboxStyle);
    }

    public MaterialCheckBox(Context context, AttributeSet object, int n3) {
        int n4 = A;
        super(y2.a.d(context, (AttributeSet)object, n3, n4), (AttributeSet)object, n3);
        this.g = new LinkedHashSet();
        this.h = new LinkedHashSet();
        this.y = n1.c.a(this.getContext(), z1.f.mtrl_checkbox_button_checked_unchecked);
        this.z = new b(this){
            public final MaterialCheckBox b;
            {
                this.b = materialCheckBox;
            }

            @Override
            public void b(Drawable drawable) {
                super.b(drawable);
                ColorStateList colorStateList = this.b.q;
                if (colorStateList != null) {
                    drawable.setTintList(colorStateList);
                }
            }

            @Override
            public void c(Drawable drawable) {
                super.c(drawable);
                MaterialCheckBox materialCheckBox = this.b;
                ColorStateList colorStateList = materialCheckBox.q;
                if (colorStateList != null) {
                    drawable.setTint(colorStateList.getColorForState(materialCheckBox.u, this.b.q.getDefaultColor()));
                }
            }
        };
        context = this.getContext();
        this.n = androidx.core.widget.c.a((CompoundButton)this);
        this.q = this.getSuperButtonTintList();
        this.setSupportButtonTintList(null);
        object = com.google.android.material.internal.z.j(context, (AttributeSet)object, z1.m.MaterialCheckBox, n3, n4, new int[0]);
        this.o = ((m0)object).g(z1.m.MaterialCheckBox_buttonIcon);
        if (this.n != null && com.google.android.material.internal.z.g(context) && this.c((m0)object)) {
            super.setButtonDrawable(null);
            this.n = d.a.b(context, z1.f.mtrl_checkbox_button);
            this.p = true;
            if (this.o == null) {
                this.o = d.a.b(context, z1.f.mtrl_checkbox_button_icon);
            }
        }
        this.r = s2.c.b(context, (m0)object, z1.m.MaterialCheckBox_buttonIconTint);
        this.s = c0.n(((m0)object).k(z1.m.MaterialCheckBox_buttonIconTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.j = ((m0)object).a(z1.m.MaterialCheckBox_useMaterialThemeColors, false);
        this.k = ((m0)object).a(z1.m.MaterialCheckBox_centerIfNoTextEnabled, true);
        this.l = ((m0)object).a(z1.m.MaterialCheckBox_errorShown, false);
        this.m = ((m0)object).p(z1.m.MaterialCheckBox_errorAccessibilityLabel);
        n3 = z1.m.MaterialCheckBox_checkedState;
        if (((m0)object).s(n3)) {
            this.setCheckedState(((m0)object).k(n3, 0));
        }
        ((m0)object).x();
        this.e();
    }

    private String getButtonStateDescription() {
        int n3 = this.t;
        if (n3 == 1) {
            return this.getResources().getString(z1.k.mtrl_checkbox_state_description_checked);
        }
        if (n3 == 0) {
            return this.getResources().getString(z1.k.mtrl_checkbox_state_description_unchecked);
        }
        return this.getResources().getString(z1.k.mtrl_checkbox_state_description_indeterminate);
    }

    private ColorStateList getMaterialThemeColorsTintList() {
        if (this.i == null) {
            int[][] nArray = D;
            int[] nArray2 = new int[nArray.length];
            int n3 = h2.a.d((View)this, c.a.colorControlActivated);
            int n4 = h2.a.d((View)this, c.a.colorError);
            int n5 = h2.a.d((View)this, z1.c.colorSurface);
            int n6 = h2.a.d((View)this, z1.c.colorOnSurface);
            nArray2[0] = h2.a.j(n5, n4, 1.0f);
            nArray2[1] = h2.a.j(n5, n3, 1.0f);
            nArray2[2] = h2.a.j(n5, n6, 0.54f);
            nArray2[3] = h2.a.j(n5, n6, 0.38f);
            nArray2[4] = h2.a.j(n5, n6, 0.38f);
            this.i = new ColorStateList(nArray, nArray2);
        }
        return this.i;
    }

    private ColorStateList getSuperButtonTintList() {
        ColorStateList colorStateList = this.q;
        if (colorStateList != null) {
            return colorStateList;
        }
        if (super.getButtonTintList() != null) {
            return super.getButtonTintList();
        }
        return this.getSupportButtonTintList();
    }

    public final boolean c(m0 m02) {
        int n3 = m02.n(z1.m.MaterialCheckBox_android_button, 0);
        int n4 = m02.n(z1.m.MaterialCheckBox_buttonCompat, 0);
        return n3 == E && n4 == 0;
    }

    public boolean d() {
        return this.l;
    }

    public final void e() {
        this.n = j2.d.d(this.n, this.q, androidx.core.widget.c.c((CompoundButton)this));
        this.o = j2.d.d(this.o, this.r, this.s);
        this.g();
        this.h();
        super.setButtonDrawable(j2.d.a(this.n, this.o));
        this.refreshDrawableState();
    }

    public final void f() {
        if (Build.VERSION.SDK_INT >= 30 && this.w == null) {
            super.setStateDescription((CharSequence)this.getButtonStateDescription());
        }
    }

    public final void g() {
        if (this.p) {
            Drawable drawable;
            n1.c c3 = this.y;
            if (c3 != null) {
                c3.f(this.z);
                this.y.b(this.z);
            }
            if ((drawable = this.n) instanceof AnimatedStateListDrawable && (c3 = this.y) != null) {
                drawable = (AnimatedStateListDrawable)drawable;
                int n3 = z1.g.checked;
                int n4 = z1.g.unchecked;
                drawable.addTransition(n3, n4, (Drawable)c3, false);
                ((AnimatedStateListDrawable)this.n).addTransition(z1.g.indeterminate, n4, (Drawable)this.y, false);
            }
        }
    }

    public Drawable getButtonDrawable() {
        return this.n;
    }

    public Drawable getButtonIconDrawable() {
        return this.o;
    }

    public ColorStateList getButtonIconTintList() {
        return this.r;
    }

    public PorterDuff.Mode getButtonIconTintMode() {
        return this.s;
    }

    public ColorStateList getButtonTintList() {
        return this.q;
    }

    public int getCheckedState() {
        return this.t;
    }

    public CharSequence getErrorAccessibilityLabel() {
        return this.m;
    }

    public final void h() {
        ColorStateList colorStateList;
        Drawable drawable = this.n;
        if (drawable != null && (colorStateList = this.q) != null) {
            drawable.setTintList(colorStateList);
        }
        if ((drawable = this.o) != null && (colorStateList = this.r) != null) {
            drawable.setTintList(colorStateList);
        }
    }

    public boolean isChecked() {
        return this.t == 1;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.j && this.q == null && this.r == null) {
            this.setUseMaterialThemeColors(true);
        }
    }

    public int[] onCreateDrawableState(int n3) {
        int[] nArray = super.onCreateDrawableState(n3 + 2);
        if (this.getCheckedState() == 2) {
            View.mergeDrawableStates((int[])nArray, (int[])B);
        }
        if (this.d()) {
            View.mergeDrawableStates((int[])nArray, (int[])C);
        }
        this.u = j2.d.f(nArray);
        return nArray;
    }

    public void onDraw(Canvas canvas) {
        Drawable drawable;
        if (this.k && TextUtils.isEmpty((CharSequence)this.getText()) && (drawable = androidx.core.widget.c.a((CompoundButton)this)) != null) {
            int n3 = c0.m((View)this) ? -1 : 1;
            int n4 = (this.getWidth() - drawable.getIntrinsicWidth()) / 2 * n3;
            n3 = canvas.save();
            canvas.translate((float)n4, 0.0f);
            super.onDraw(canvas);
            canvas.restoreToCount(n3);
            if (this.getBackground() != null) {
                canvas = drawable.getBounds();
                this.getBackground().setHotspotBounds(canvas.left + n4, canvas.top, canvas.right + n4, canvas.bottom);
            }
            return;
        }
        super.onDraw(canvas);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (accessibilityNodeInfo != null && this.d()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((Object)accessibilityNodeInfo.getText());
            stringBuilder.append(", ");
            stringBuilder.append((Object)this.m);
            accessibilityNodeInfo.setText((CharSequence)stringBuilder.toString());
        }
    }

    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setCheckedState(object.c);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.c = this.getCheckedState();
        return savedState;
    }

    @Override
    public void setButtonDrawable(int n3) {
        this.setButtonDrawable(d.a.b(this.getContext(), n3));
    }

    @Override
    public void setButtonDrawable(Drawable drawable) {
        this.n = drawable;
        this.p = false;
        this.e();
    }

    public void setButtonIconDrawable(Drawable drawable) {
        this.o = drawable;
        this.e();
    }

    public void setButtonIconDrawableResource(int n3) {
        this.setButtonIconDrawable(d.a.b(this.getContext(), n3));
    }

    public void setButtonIconTintList(ColorStateList colorStateList) {
        if (this.r == colorStateList) {
            return;
        }
        this.r = colorStateList;
        this.e();
    }

    public void setButtonIconTintMode(PorterDuff.Mode mode) {
        if (this.s == mode) {
            return;
        }
        this.s = mode;
        this.e();
    }

    public void setButtonTintList(ColorStateList colorStateList) {
        if (this.q == colorStateList) {
            return;
        }
        this.q = colorStateList;
        this.e();
    }

    public void setButtonTintMode(PorterDuff.Mode mode) {
        this.setSupportButtonTintMode(mode);
        this.e();
    }

    public void setCenterIfNoTextEnabled(boolean bl) {
        this.k = bl;
    }

    public void setChecked(boolean bl) {
        this.setCheckedState(bl ? 1 : 0);
    }

    public void setCheckedState(int n3) {
        if (this.t != n3) {
            this.t = n3;
            boolean bl = n3 == 1;
            super.setChecked(bl);
            this.refreshDrawableState();
            this.f();
            if (!this.v) {
                this.v = true;
                Object object = this.h;
                if (object != null && (object = ((AbstractCollection)object).iterator()).hasNext()) {
                    androidx.appcompat.app.s.a(object.next());
                    throw null;
                }
                if (this.t != 2 && (object = this.x) != null) {
                    object.onCheckedChanged((CompoundButton)this, this.isChecked());
                }
                if ((object = (AutofillManager)this.getContext().getSystemService(AutofillManager.class)) != null) {
                    object.notifyValueChanged((View)this);
                }
                this.v = false;
            }
        }
    }

    public void setErrorAccessibilityLabel(CharSequence charSequence) {
        this.m = charSequence;
    }

    public void setErrorAccessibilityLabelResource(int n3) {
        CharSequence charSequence = n3 != 0 ? this.getResources().getText(n3) : null;
        this.setErrorAccessibilityLabel(charSequence);
    }

    public void setErrorShown(boolean bl) {
        Iterator iterator;
        block3: {
            block2: {
                if (this.l == bl) break block2;
                this.l = bl;
                this.refreshDrawableState();
                iterator = ((AbstractCollection)this.g).iterator();
                if (iterator.hasNext()) break block3;
            }
            return;
        }
        androidx.appcompat.app.s.a(iterator.next());
        throw null;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.x = onCheckedChangeListener;
    }

    public void setStateDescription(CharSequence charSequence) {
        this.w = charSequence;
        if (charSequence == null) {
            this.f();
            return;
        }
        super.setStateDescription(charSequence);
    }

    public void setUseMaterialThemeColors(boolean bl) {
        this.j = bl;
        if (bl) {
            androidx.core.widget.c.d((CompoundButton)this, this.getMaterialThemeColorsTintList());
            return;
        }
        androidx.core.widget.c.d((CompoundButton)this, null);
    }

    public void toggle() {
        this.setChecked(this.isChecked() ^ true);
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState[] b(int n3) {
                return new SavedState[n3];
            }
        };
        public int c;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.c = (Integer)parcel.readValue(((Object)((Object)this)).getClass().getClassLoader());
        }

        public /* synthetic */ SavedState(Parcel parcel, a a4) {
            this(parcel);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final String o() {
            int n3 = this.c;
            if (n3 != 1) {
                if (n3 != 2) {
                    return "unchecked";
                }
                return "indeterminate";
            }
            return "checked";
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MaterialCheckBox.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode((Object)this)));
            stringBuilder.append(" CheckedState=");
            stringBuilder.append(this.o());
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeValue((Object)this.c);
        }
    }
}

