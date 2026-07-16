/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.text.InputFilter
 *  android.util.AttributeSet
 *  android.view.ActionMode$Callback
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Button
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.d;
import androidx.appcompat.widget.i;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.j0;
import androidx.appcompat.widget.p;
import androidx.appcompat.widget.t0;
import androidx.core.widget.j;
import c.a;

public class AppCompatButton
extends Button {
    public final d c;
    public final p d;
    public i e;

    public AppCompatButton(Context context) {
        this(context, null);
    }

    public AppCompatButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.buttonStyle);
    }

    public AppCompatButton(Context object, AttributeSet attributeSet, int n3) {
        super(j0.b((Context)object), attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new d((View)this);
        this.c = object;
        ((d)object).e(attributeSet, n3);
        this.d = object = new p((TextView)this);
        ((p)object).m(attributeSet, n3);
        ((p)object).b();
        this.getEmojiTextViewHelper().c(attributeSet, n3);
    }

    private i getEmojiTextViewHelper() {
        if (this.e == null) {
            this.e = new i((TextView)this);
        }
        return this.e;
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        Object object = this.c;
        if (object != null) {
            ((d)object).b();
        }
        if ((object = this.d) != null) {
            ((p)object).b();
        }
    }

    public int getAutoSizeMaxTextSize() {
        if (t0.c) {
            return super.getAutoSizeMaxTextSize();
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.e();
        }
        return -1;
    }

    public int getAutoSizeMinTextSize() {
        if (t0.c) {
            return super.getAutoSizeMinTextSize();
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.f();
        }
        return -1;
    }

    public int getAutoSizeStepGranularity() {
        if (t0.c) {
            return super.getAutoSizeStepGranularity();
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.g();
        }
        return -1;
    }

    public int[] getAutoSizeTextAvailableSizes() {
        if (t0.c) {
            return super.getAutoSizeTextAvailableSizes();
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.h();
        }
        return new int[0];
    }

    public int getAutoSizeTextType() {
        if (t0.c) {
            if (super.getAutoSizeTextType() == 1) {
                return 1;
            }
            return 0;
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.i();
        }
        return 0;
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        return j.o(super.getCustomSelectionActionModeCallback());
    }

    public ColorStateList getSupportBackgroundTintList() {
        d d3 = this.c;
        if (d3 != null) {
            return d3.c();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        d d3 = this.c;
        if (d3 != null) {
            return d3.d();
        }
        return null;
    }

    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.d.j();
    }

    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.d.k();
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)Button.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)Button.class.getName());
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        p p3 = this.d;
        if (p3 != null) {
            p3.o(bl, n3, n4, n5, n6);
        }
    }

    public void onTextChanged(CharSequence object, int n3, int n4, int n5) {
        super.onTextChanged((CharSequence)object, n3, n4, n5);
        object = this.d;
        if (object != null && !t0.c && ((p)object).l()) {
            this.d.c();
        }
    }

    public void setAllCaps(boolean bl) {
        super.setAllCaps(bl);
        this.getEmojiTextViewHelper().d(bl);
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int n3, int n4, int n5, int n6) {
        if (t0.c) {
            super.setAutoSizeTextTypeUniformWithConfiguration(n3, n4, n5, n6);
            return;
        }
        p p3 = this.d;
        if (p3 != null) {
            p3.t(n3, n4, n5, n6);
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] nArray, int n3) {
        if (t0.c) {
            super.setAutoSizeTextTypeUniformWithPresetSizes(nArray, n3);
            return;
        }
        p p3 = this.d;
        if (p3 != null) {
            p3.u(nArray, n3);
        }
    }

    public void setAutoSizeTextTypeWithDefaults(int n3) {
        if (t0.c) {
            super.setAutoSizeTextTypeWithDefaults(n3);
            return;
        }
        p p3 = this.d;
        if (p3 != null) {
            p3.v(n3);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        d d3 = this.c;
        if (d3 != null) {
            d3.f(drawable);
        }
    }

    public void setBackgroundResource(int n3) {
        super.setBackgroundResource(n3);
        d d3 = this.c;
        if (d3 != null) {
            d3.g(n3);
        }
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(j.p((TextView)this, callback));
    }

    public void setEmojiCompatEnabled(boolean bl) {
        this.getEmojiTextViewHelper().e(bl);
    }

    public void setFilters(InputFilter[] inputFilterArray) {
        super.setFilters(this.getEmojiTextViewHelper().a(inputFilterArray));
    }

    public void setSupportAllCaps(boolean bl) {
        p p3 = this.d;
        if (p3 != null) {
            p3.s(bl);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        d d3 = this.c;
        if (d3 != null) {
            d3.i(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        d d3 = this.c;
        if (d3 != null) {
            d3.j(mode);
        }
    }

    public void setSupportCompoundDrawablesTintList(ColorStateList colorStateList) {
        this.d.w(colorStateList);
        this.d.b();
    }

    public void setSupportCompoundDrawablesTintMode(PorterDuff.Mode mode) {
        this.d.x(mode);
        this.d.b();
    }

    public void setTextAppearance(Context context, int n3) {
        super.setTextAppearance(context, n3);
        p p3 = this.d;
        if (p3 != null) {
            p3.q(context, n3);
        }
    }

    public void setTextSize(int n3, float f3) {
        if (t0.c) {
            super.setTextSize(n3, f3);
            return;
        }
        p p3 = this.d;
        if (p3 != null) {
            p3.A(n3, f3);
        }
    }
}

