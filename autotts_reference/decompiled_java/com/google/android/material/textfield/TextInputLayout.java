/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.StaticLayout$Builder
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewStructure
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.accessibility.AccessibilityEvent
 *  android.widget.AutoCompleteTextView
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageView$ScaleType
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.material.textfield;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.m0;
import androidx.core.widget.j;
import androidx.customview.view.AbsSavedState;
import androidx.transition.Fade;
import androidx.transition.Transition;
import c.a;
import c.i;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.b;
import com.google.android.material.internal.d;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.a0;
import com.google.android.material.textfield.b0;
import com.google.android.material.textfield.c0;
import com.google.android.material.textfield.h;
import com.google.android.material.textfield.q;
import com.google.android.material.textfield.r;
import com.google.android.material.textfield.u;
import com.google.android.material.textfield.y;
import com.google.android.material.textfield.z;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import o0.x0;
import p0.s;
import s2.c;
import v2.o;
import z1.k;
import z1.l;
import z1.m;

public class TextInputLayout
extends LinearLayout
implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final int F0 = z1.l.Widget_Design_TextInputLayout;
    public static final int[][] G0 = new int[][]{{16842919}, new int[0]};
    public Fade A;
    public boolean A0;
    public ColorStateList B;
    public ValueAnimator B0;
    public ColorStateList C;
    public boolean C0;
    public ColorStateList D;
    public boolean D0;
    public ColorStateList E;
    public boolean E0;
    public boolean F;
    public CharSequence G;
    public boolean H;
    public v2.i I;
    public v2.i J;
    public StateListDrawable K;
    public boolean L;
    public v2.i M;
    public v2.i N;
    public o O;
    public boolean P;
    public final int Q;
    public int R;
    public int S;
    public int T;
    public int U;
    public int V;
    public int W;
    public int a0;
    public final Rect b0;
    public final FrameLayout c;
    public final Rect c0;
    public final y d;
    public final RectF d0;
    public final r e;
    public Typeface e0;
    public final int f;
    public Drawable f0;
    public EditText g;
    public int g0;
    public CharSequence h;
    public final LinkedHashSet h0;
    public int i;
    public Drawable i0;
    public int j;
    public int j0;
    public int k;
    public Drawable k0;
    public int l;
    public ColorStateList l0;
    public final u m;
    public ColorStateList m0;
    public boolean n;
    public int n0;
    public int o;
    public int o0;
    public boolean p;
    public int p0;
    public f q;
    public ColorStateList q0;
    public TextView r;
    public int r0;
    public int s;
    public int s0;
    public int t;
    public int t0;
    public CharSequence u;
    public int u0;
    public boolean v;
    public int v0;
    public TextView w;
    public int w0;
    public ColorStateList x;
    public boolean x0;
    public int y;
    public final b y0;
    public Fade z;
    public boolean z0;

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.textInputStyle);
    }

    public TextInputLayout(Context context, AttributeSet object, int n3) {
        int n4 = F0;
        super(y2.a.d(context, (AttributeSet)object, n3, n4), (AttributeSet)object, n3);
        this.i = -1;
        this.j = -1;
        this.k = -1;
        this.l = -1;
        this.m = new u(this);
        this.q = new b0();
        this.b0 = new Rect();
        this.c0 = new Rect();
        this.d0 = new RectF();
        this.h0 = new LinkedHashSet();
        Object object2 = new b((View)this);
        this.y0 = object2;
        this.E0 = false;
        Object object3 = this.getContext();
        this.setOrientation(1);
        this.setWillNotDraw(false);
        this.setAddStatesFromChildren(true);
        context = new FrameLayout(object3);
        this.c = context;
        context.setAddStatesFromChildren(true);
        Object object4 = a2.a.a;
        ((b)object2).P0((TimeInterpolator)object4);
        ((b)object2).K0((TimeInterpolator)object4);
        ((b)object2).m0(0x800033);
        object2 = z1.m.TextInputLayout;
        int n5 = z1.m.TextInputLayout_counterTextAppearance;
        int n6 = z1.m.TextInputLayout_counterOverflowTextAppearance;
        int n7 = z1.m.TextInputLayout_errorTextAppearance;
        int n8 = z1.m.TextInputLayout_helperTextTextAppearance;
        int n9 = z1.m.TextInputLayout_hintTextAppearance;
        object2 = com.google.android.material.internal.z.j(object3, (AttributeSet)object, (int[])object2, n3, n4, new int[]{n5, n6, n7, n8, n9});
        object4 = new y(this, (m0)object2);
        this.d = object4;
        this.F = ((m0)object2).a(z1.m.TextInputLayout_hintEnabled, true);
        this.setHint(((m0)object2).p(z1.m.TextInputLayout_android_hint));
        this.A0 = ((m0)object2).a(z1.m.TextInputLayout_hintAnimationEnabled, true);
        this.z0 = ((m0)object2).a(z1.m.TextInputLayout_expandedHintEnabled, true);
        int n10 = z1.m.TextInputLayout_android_minEms;
        if (((m0)object2).s(n10)) {
            this.setMinEms(((m0)object2).k(n10, -1));
        } else {
            n10 = z1.m.TextInputLayout_android_minWidth;
            if (((m0)object2).s(n10)) {
                this.setMinWidth(((m0)object2).f(n10, -1));
            }
        }
        n10 = z1.m.TextInputLayout_android_maxEms;
        if (((m0)object2).s(n10)) {
            this.setMaxEms(((m0)object2).k(n10, -1));
        } else {
            n10 = z1.m.TextInputLayout_android_maxWidth;
            if (((m0)object2).s(n10)) {
                this.setMaxWidth(((m0)object2).f(n10, -1));
            }
        }
        this.O = v2.o.e(object3, (AttributeSet)object, n3, n4).m();
        this.Q = object3.getResources().getDimensionPixelOffset(z1.e.mtrl_textinput_box_label_cutout_padding);
        this.S = ((m0)object2).e(z1.m.TextInputLayout_boxCollapsedPaddingTop, 0);
        this.f = this.getResources().getDimensionPixelSize(z1.e.m3_multiline_hint_filled_text_extra_space);
        this.U = ((m0)object2).f(z1.m.TextInputLayout_boxStrokeWidth, object3.getResources().getDimensionPixelSize(z1.e.mtrl_textinput_box_stroke_width_default));
        this.V = ((m0)object2).f(z1.m.TextInputLayout_boxStrokeWidthFocused, object3.getResources().getDimensionPixelSize(z1.e.mtrl_textinput_box_stroke_width_focused));
        this.T = this.U;
        float f3 = ((m0)object2).d(z1.m.TextInputLayout_boxCornerRadiusTopStart, -1.0f);
        float f4 = ((m0)object2).d(z1.m.TextInputLayout_boxCornerRadiusTopEnd, -1.0f);
        float f5 = ((m0)object2).d(z1.m.TextInputLayout_boxCornerRadiusBottomEnd, -1.0f);
        float f6 = ((m0)object2).d(z1.m.TextInputLayout_boxCornerRadiusBottomStart, -1.0f);
        object = this.O.w();
        if (f3 >= 0.0f) {
            ((o.b)object).E(f3);
        }
        if (f4 >= 0.0f) {
            ((o.b)object).I(f4);
        }
        if (f5 >= 0.0f) {
            ((o.b)object).z(f5);
        }
        if (f6 >= 0.0f) {
            ((o.b)object).v(f6);
        }
        this.O = ((o.b)object).m();
        object = s2.c.b(object3, (m0)object2, z1.m.TextInputLayout_boxBackgroundColor);
        if (object != null) {
            this.r0 = n3 = object.getDefaultColor();
            this.a0 = n3;
            if (object.isStateful()) {
                this.s0 = object.getColorForState(new int[]{-16842910}, -1);
                this.t0 = object.getColorForState(new int[]{16842908, 16842910}, -1);
                this.u0 = object.getColorForState(new int[]{16843623, 16842910}, -1);
            } else {
                this.t0 = this.r0;
                object = d.a.a(object3, z1.d.mtrl_filled_background_color);
                this.s0 = object.getColorForState(new int[]{-16842910}, -1);
                this.u0 = object.getColorForState(new int[]{16843623}, -1);
            }
        } else {
            this.a0 = 0;
            this.r0 = 0;
            this.s0 = 0;
            this.t0 = 0;
            this.u0 = 0;
        }
        n3 = z1.m.TextInputLayout_android_textColorHint;
        if (((m0)object2).s(n3)) {
            object = ((m0)object2).c(n3);
            this.m0 = object;
            this.l0 = object;
        }
        n3 = z1.m.TextInputLayout_boxStrokeColor;
        object = s2.c.b(object3, (m0)object2, n3);
        this.p0 = ((m0)object2).b(n3, 0);
        this.n0 = e0.a.b(object3, z1.d.mtrl_textinput_default_box_stroke_color);
        this.v0 = e0.a.b(object3, z1.d.mtrl_textinput_disabled_color);
        this.o0 = e0.a.b(object3, z1.d.mtrl_textinput_hovered_box_stroke_color);
        if (object != null) {
            this.setBoxStrokeColorStateList((ColorStateList)object);
        }
        if (((m0)object2).s(n3 = z1.m.TextInputLayout_boxStrokeErrorColor)) {
            this.setBoxStrokeErrorColor(s2.c.b(object3, (m0)object2, n3));
        }
        if (((m0)object2).n(n9, -1) != -1) {
            this.setHintTextAppearance(((m0)object2).n(n9, 0));
        }
        this.D = ((m0)object2).c(z1.m.TextInputLayout_cursorColor);
        this.E = ((m0)object2).c(z1.m.TextInputLayout_cursorErrorColor);
        n7 = ((m0)object2).n(n7, 0);
        CharSequence charSequence = ((m0)object2).p(z1.m.TextInputLayout_errorContentDescription);
        n3 = ((m0)object2).k(z1.m.TextInputLayout_errorAccessibilityLiveRegion, 1);
        boolean bl = ((m0)object2).a(z1.m.TextInputLayout_errorEnabled, false);
        n8 = ((m0)object2).n(n8, 0);
        boolean bl2 = ((m0)object2).a(z1.m.TextInputLayout_helperTextEnabled, false);
        object = ((m0)object2).p(z1.m.TextInputLayout_helperText);
        n9 = ((m0)object2).n(z1.m.TextInputLayout_placeholderTextAppearance, 0);
        object3 = ((m0)object2).p(z1.m.TextInputLayout_placeholderText);
        boolean bl3 = ((m0)object2).a(z1.m.TextInputLayout_counterEnabled, false);
        this.setCounterMaxLength(((m0)object2).k(z1.m.TextInputLayout_counterMaxLength, -1));
        this.t = ((m0)object2).n(n5, 0);
        this.s = ((m0)object2).n(n6, 0);
        this.setBoxBackgroundMode(((m0)object2).k(z1.m.TextInputLayout_boxBackgroundMode, 0));
        this.setErrorContentDescription(charSequence);
        this.setErrorAccessibilityLiveRegion(n3);
        this.setCounterOverflowTextAppearance(this.s);
        this.setHelperTextTextAppearance(n8);
        this.setErrorTextAppearance(n7);
        this.setCounterTextAppearance(this.t);
        this.setPlaceholderText((CharSequence)object3);
        this.setPlaceholderTextAppearance(n9);
        n3 = z1.m.TextInputLayout_errorTextColor;
        if (((m0)object2).s(n3)) {
            this.setErrorTextColor(((m0)object2).c(n3));
        }
        if (((m0)object2).s(n3 = z1.m.TextInputLayout_helperTextTextColor)) {
            this.setHelperTextColor(((m0)object2).c(n3));
        }
        if (((m0)object2).s(n3 = z1.m.TextInputLayout_hintTextColor)) {
            this.setHintTextColor(((m0)object2).c(n3));
        }
        if (((m0)object2).s(n3 = z1.m.TextInputLayout_counterTextColor)) {
            this.setCounterTextColor(((m0)object2).c(n3));
        }
        if (((m0)object2).s(n3 = z1.m.TextInputLayout_counterOverflowTextColor)) {
            this.setCounterOverflowTextColor(((m0)object2).c(n3));
        }
        if (((m0)object2).s(n3 = z1.m.TextInputLayout_placeholderTextColor)) {
            this.setPlaceholderTextColor(((m0)object2).c(n3));
        }
        object3 = new r(this, (m0)object2);
        this.e = object3;
        boolean bl4 = ((m0)object2).a(z1.m.TextInputLayout_android_enabled, true);
        this.setHintMaxLines(((m0)object2).k(z1.m.TextInputLayout_hintMaxLines, 1));
        ((m0)object2).x();
        this.setImportantForAccessibility(2);
        this.setImportantForAutofill(1);
        context.addView((View)object4);
        context.addView((View)object3);
        this.addView((View)context);
        this.setEnabled(bl4);
        this.setHelperTextEnabled(bl2);
        this.setErrorEnabled(bl);
        this.setCounterEnabled(bl3);
        this.setHelperText((CharSequence)object);
    }

    public static Drawable I(v2.i i3, int n3, int n4, int[][] nArray) {
        return new RippleDrawable(new ColorStateList(nArray, new int[]{h2.a.j(n4, n3, 0.1f), n3}), (Drawable)i3, (Drawable)i3);
    }

    public static Drawable L(Context object, v2.i i3, int n3, int[][] object2) {
        int n4 = h2.a.c((Context)object, z1.c.colorSurface, "TextInputLayout");
        object = new v2.i(i3.K());
        n3 = h2.a.j(n3, n4, 0.1f);
        ((v2.i)object).i0(new ColorStateList((int[][])object2, new int[]{n3, 0}));
        ((v2.i)object).setTint(n4);
        ColorStateList colorStateList = new ColorStateList((int[][])object2, new int[]{n3, n4});
        object2 = new v2.i(i3.K());
        ((v2.i)object2).setTint(-1);
        return new LayerDrawable(new Drawable[]{new RippleDrawable(colorStateList, (Drawable)object, (Drawable)object2), i3});
    }

    public static void Y(ViewGroup viewGroup, boolean bl) {
        int n3 = viewGroup.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = viewGroup.getChildAt(i3);
            view.setEnabled(bl);
            if (!(view instanceof ViewGroup)) continue;
            TextInputLayout.Y((ViewGroup)view, bl);
        }
    }

    public static /* synthetic */ void a(TextInputLayout textInputLayout) {
        textInputLayout.g.requestLayout();
    }

    public static /* synthetic */ void b(TextInputLayout textInputLayout, StaticLayout.Builder builder) {
        builder.setBreakStrategy(textInputLayout.w.getBreakStrategy());
    }

    public static /* synthetic */ int c(Editable editable) {
        if (editable != null) {
            return editable.length();
        }
        return 0;
    }

    private Drawable getEditTextBoxBackground() {
        EditText editText = this.g;
        if (editText instanceof AutoCompleteTextView && !com.google.android.material.textfield.q.a(editText)) {
            int n3 = h2.a.d((View)this.g, a.colorControlHighlight);
            int n4 = this.R;
            if (n4 == 2) {
                return TextInputLayout.L(this.getContext(), this.I, n3, G0);
            }
            if (n4 == 1) {
                return TextInputLayout.I(this.I, this.a0, n3, G0);
            }
            return null;
        }
        return this.I;
    }

    private Drawable getOrCreateFilledDropDownMenuBackground() {
        if (this.K == null) {
            Object object;
            this.K = object = new StateListDrawable();
            Drawable drawable = this.getOrCreateOutlinedDropDownMenuBackground();
            object.addState(new int[]{0x10100AA}, drawable);
            drawable = this.K;
            object = this.H(false);
            drawable.addState(new int[0], (Drawable)object);
        }
        return this.K;
    }

    private Drawable getOrCreateOutlinedDropDownMenuBackground() {
        if (this.J == null) {
            this.J = this.H(true);
        }
        return this.J;
    }

    public static void m0(Context context, TextView textView, int n3, int n4, boolean bl) {
        int n5 = bl ? z1.k.character_counter_overflowed_content_description : z1.k.character_counter_content_description;
        textView.setContentDescription((CharSequence)context.getString(n5, new Object[]{n3, n4}));
    }

    private void setEditText(EditText editText) {
        if (this.g == null) {
            this.getEndIconMode();
            this.g = editText;
            int n3 = this.i;
            if (n3 != -1) {
                this.setMinEms(n3);
            } else {
                this.setMinWidth(this.k);
            }
            n3 = this.j;
            if (n3 != -1) {
                this.setMaxEms(n3);
            } else {
                this.setMaxWidth(this.l);
            }
            this.L = false;
            this.V();
            this.setTextInputAccessibilityDelegate(new e(this));
            this.y0.R0(this.g.getTypeface());
            this.y0.A0(this.g.getTextSize());
            this.y0.u0(this.g.getLetterSpacing());
            n3 = this.g.getGravity();
            this.y0.m0(n3 & 0xFFFFFF8F | 0x30);
            this.y0.z0(n3);
            this.w0 = editText.getMinimumHeight();
            this.g.addTextChangedListener(new TextWatcher(this, editText){
                public int c;
                public final EditText d;
                public final TextInputLayout e;
                {
                    this.e = textInputLayout;
                    this.d = editText;
                    this.c = editText.getLineCount();
                }

                public void afterTextChanged(Editable editable) {
                    int n3;
                    int n4;
                    TextInputLayout textInputLayout = this.e;
                    textInputLayout.w0(textInputLayout.D0 ^ true);
                    textInputLayout = this.e;
                    if (textInputLayout.n) {
                        textInputLayout.l0(editable);
                    }
                    if (this.e.v) {
                        this.e.A0(editable);
                    }
                    if ((n4 = this.d.getLineCount()) != (n3 = this.c)) {
                        int n5;
                        if (n4 < n3 && (n5 = this.d.getMinimumHeight()) != (n3 = this.e.w0)) {
                            this.d.setMinimumHeight(n3);
                        }
                        this.c = n4;
                    }
                }

                public void beforeTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
                }

                public void onTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
                }
            });
            if (this.l0 == null) {
                this.l0 = this.g.getHintTextColors();
            }
            if (this.F) {
                if (TextUtils.isEmpty((CharSequence)this.G)) {
                    CharSequence charSequence;
                    this.h = charSequence = this.g.getHint();
                    this.setHint(charSequence);
                    this.g.setHint(null);
                }
                this.H = true;
            }
            if (Build.VERSION.SDK_INT >= 29) {
                this.o0();
            }
            if (this.r != null) {
                this.l0(this.g.getText());
            }
            this.q0();
            this.m.f();
            this.d.bringToFront();
            this.e.bringToFront();
            this.D();
            this.e.x0();
            if (!this.isEnabled()) {
                editText.setEnabled(false);
            }
            this.x0(false, true);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void setHintInternal(CharSequence charSequence) {
        if (!TextUtils.equals((CharSequence)charSequence, (CharSequence)this.G)) {
            this.G = charSequence;
            this.y0.O0(charSequence);
            if (!this.x0) {
                this.W();
            }
        }
    }

    private void setPlaceholderTextEnabled(boolean bl) {
        if (this.v == bl) {
            return;
        }
        if (bl) {
            this.k();
        } else {
            this.a0();
            this.w = null;
        }
        this.v = bl;
    }

    public final void A(boolean bl) {
        ValueAnimator valueAnimator = this.B0;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.B0.cancel();
        }
        if (bl && this.A0) {
            this.m(1.0f);
        } else {
            this.y0.D0(1.0f);
        }
        this.x0 = false;
        if (this.C()) {
            this.W();
        }
        this.z0();
        this.d.l(false);
        this.e.H(false);
    }

    public final void A0(Editable editable) {
        if (this.q.a(editable) == 0 && !this.x0) {
            this.g0();
            return;
        }
        this.M();
    }

    public final Fade B() {
        Fade fade = new Fade();
        fade.f0(p2.k.f(this.getContext(), z1.c.motionDurationShort2, 87));
        fade.h0(p2.k.g(this.getContext(), z1.c.motionEasingLinearInterpolator, a2.a.a));
        return fade;
    }

    public final void B0(boolean bl, boolean bl2) {
        int n3 = this.q0.getDefaultColor();
        int n4 = this.q0.getColorForState(new int[]{16843623, 16842910}, n3);
        int n5 = this.q0.getColorForState(new int[]{16843518, 16842910}, n3);
        if (bl) {
            this.W = n5;
            return;
        }
        if (bl2) {
            this.W = n4;
            return;
        }
        this.W = n3;
    }

    public final boolean C() {
        return this.F && !TextUtils.isEmpty((CharSequence)this.G) && this.I instanceof h;
    }

    public void C0() {
        block17: {
            boolean bl;
            EditText editText;
            boolean bl2;
            block19: {
                block18: {
                    if (this.I == null || this.R == 0) break block17;
                    bl2 = this.isFocused();
                    boolean bl3 = false;
                    bl2 = bl2 || (editText = this.g) != null && editText.hasFocus();
                    if (this.isHovered()) break block18;
                    editText = this.g;
                    bl = bl3;
                    if (editText == null) break block19;
                    bl = bl3;
                    if (!editText.isHovered()) break block19;
                }
                bl = true;
            }
            if (!this.isEnabled()) {
                this.W = this.v0;
            } else if (this.d0()) {
                if (this.q0 != null) {
                    this.B0(bl2, bl);
                } else {
                    this.W = this.getErrorCurrentTextColors();
                }
            } else if (this.p && (editText = this.r) != null) {
                if (this.q0 != null) {
                    this.B0(bl2, bl);
                } else {
                    this.W = editText.getCurrentTextColor();
                }
            } else {
                this.W = bl2 ? this.p0 : (bl ? this.o0 : this.n0);
            }
            if (Build.VERSION.SDK_INT >= 29) {
                this.o0();
            }
            this.e.I();
            this.Z();
            if (this.R == 2) {
                int n3 = this.T;
                this.T = bl2 && this.isEnabled() ? this.V : this.U;
                if (this.T != n3) {
                    this.X();
                }
            }
            if (this.R == 1) {
                this.a0 = !this.isEnabled() ? this.s0 : (bl && !bl2 ? this.u0 : (bl2 ? this.t0 : this.r0));
            }
            this.n();
        }
    }

    public final void D() {
        Iterator iterator = ((AbstractCollection)this.h0).iterator();
        while (iterator.hasNext()) {
            ((g)iterator.next()).a(this);
        }
    }

    public final void E(Canvas canvas) {
        v2.i i3;
        if (this.N != null && (i3 = this.M) != null) {
            i3.draw(canvas);
            if (this.g.isFocused()) {
                i3 = this.N.getBounds();
                Rect rect = this.M.getBounds();
                float f3 = this.y0.H();
                int n3 = rect.centerX();
                ((Rect)i3).left = a2.a.c(n3, rect.left, f3);
                ((Rect)i3).right = a2.a.c(n3, rect.right, f3);
                this.N.draw(canvas);
            }
        }
    }

    public final void F(Canvas canvas) {
        if (this.F) {
            this.y0.k(canvas);
        }
    }

    public final void G(boolean bl) {
        ValueAnimator valueAnimator = this.B0;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.B0.cancel();
        }
        if (bl && this.A0) {
            this.m(0.0f);
        } else {
            this.y0.D0(0.0f);
        }
        if (this.C() && ((h)this.I).F0()) {
            this.z();
        }
        this.x0 = true;
        this.M();
        this.d.l(true);
        this.e.H(true);
    }

    public final v2.i H(boolean bl) {
        float f3 = this.getResources().getDimensionPixelOffset(z1.e.mtrl_shape_corner_size_small_component);
        float f4 = bl ? f3 : 0.0f;
        Object object = this.g;
        float f5 = object instanceof MaterialAutoCompleteTextView ? ((MaterialAutoCompleteTextView)((Object)object)).getPopupElevation() : (float)this.getResources().getDimensionPixelOffset(z1.e.m3_comp_outlined_autocomplete_menu_container_elevation);
        int n3 = this.getResources().getDimensionPixelOffset(z1.e.mtrl_exposed_dropdown_menu_popup_vertical_padding);
        o o3 = v2.o.a().E(f4).I(f4).v(f3).z(f3).m();
        object = this.g;
        object = object instanceof MaterialAutoCompleteTextView ? ((MaterialAutoCompleteTextView)((Object)object)).getDropDownBackgroundTintList() : null;
        object = v2.i.r(this.getContext(), f5, (ColorStateList)object);
        ((v2.i)object).setShapeAppearanceModel(o3);
        ((v2.i)object).l0(0, n3, 0, n3);
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int J(int n3, boolean bl) {
        int n4;
        if (!bl && this.getPrefixText() != null) {
            n4 = this.d.c();
            return n3 + n4;
        }
        if (bl && this.getSuffixText() != null) {
            n4 = this.e.y();
            return n3 + n4;
        }
        n4 = this.g.getCompoundPaddingLeft();
        return n3 + n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int K(int n3, boolean bl) {
        int n4;
        if (!bl && this.getSuffixText() != null) {
            n4 = this.e.y();
            return n3 - n4;
        }
        if (bl && this.getPrefixText() != null) {
            n4 = this.d.c();
            return n3 - n4;
        }
        n4 = this.g.getCompoundPaddingRight();
        return n3 - n4;
    }

    public final void M() {
        TextView textView = this.w;
        if (textView != null && this.v) {
            textView.setText(null);
            androidx.transition.c.a((ViewGroup)this.c, this.A);
            this.w.setVisibility(4);
        }
    }

    public boolean N() {
        return this.e.F();
    }

    public boolean O() {
        return this.m.A();
    }

    public boolean P() {
        return this.m.B();
    }

    public final boolean Q() {
        return this.x0;
    }

    public final boolean R() {
        return this.getHintMaxLines() == 1;
    }

    public final boolean S() {
        return this.d0() || this.r != null && this.p;
        {
        }
    }

    public boolean T() {
        return this.H;
    }

    public final boolean U() {
        return this.R == 1 && this.g.getMinLines() <= 1;
    }

    public final void V() {
        this.q();
        this.s0();
        this.C0();
        this.h0();
        this.l();
        if (this.R != 0) {
            this.v0();
        }
        this.b0();
    }

    public final void W() {
        if (this.C()) {
            RectF rectF = this.d0;
            this.y0.o(rectF, this.g.getWidth(), this.g.getGravity());
            if (!(rectF.width() <= 0.0f) && !(rectF.height() <= 0.0f)) {
                this.p(rectF);
                rectF.offset((float)(-this.getPaddingLeft()), (float)(-this.getPaddingTop()) - rectF.height() / 2.0f + (float)this.T);
                rectF.top = 0.0f;
                ((h)this.I).I0(rectF);
            }
        }
    }

    public final void X() {
        if (this.C() && !this.x0) {
            this.z();
            this.W();
        }
    }

    public void Z() {
        this.d.m();
    }

    public final void a0() {
        TextView textView = this.w;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = layoutParams2.gravity & 0xFFFFFF8F | 0x10;
            this.c.addView(view, (ViewGroup.LayoutParams)layoutParams2);
            this.c.setLayoutParams(layoutParams);
            this.v0();
            this.setEditText((EditText)view);
            return;
        }
        super.addView(view, n3, layoutParams);
    }

    public final void b0() {
        EditText editText = this.g;
        if (editText instanceof AutoCompleteTextView && (editText = (AutoCompleteTextView)editText).getDropDownBackground() == null) {
            int n3 = this.R;
            if (n3 == 2) {
                editText.setDropDownBackgroundDrawable(this.getOrCreateOutlinedDropDownMenuBackground());
                return;
            }
            if (n3 == 1) {
                editText.setDropDownBackgroundDrawable(this.getOrCreateFilledDropDownMenuBackground());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void c0(TextView textView, int n3) {
        try {
            androidx.core.widget.j.m(textView, n3);
            n3 = textView.getTextColors().getDefaultColor();
            if (n3 != -65281) {
                return;
            }
        }
        catch (Exception exception) {}
        androidx.core.widget.j.m(textView, c.i.TextAppearance_AppCompat_Caption);
        textView.setTextColor(e0.a.b(this.getContext(), z1.d.design_error));
    }

    public boolean d0() {
        return this.m.l();
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int n3) {
        EditText editText = this.g;
        if (editText == null) {
            super.dispatchProvideAutofillStructure(viewStructure, n3);
            return;
        }
        CharSequence charSequence = this.h;
        if (charSequence != null) {
            boolean bl = this.H;
            this.H = false;
            charSequence = editText.getHint();
            this.g.setHint(this.h);
            try {
                super.dispatchProvideAutofillStructure(viewStructure, n3);
                return;
            }
            finally {
                this.g.setHint(charSequence);
                this.H = bl;
            }
        }
        viewStructure.setAutofillId(this.getAutofillId());
        this.onProvideAutofillStructure(viewStructure, n3);
        this.onProvideAutofillVirtualStructure(viewStructure, n3);
        viewStructure.setChildCount(this.c.getChildCount());
        for (int i3 = 0; i3 < this.c.getChildCount(); ++i3) {
            editText = this.c.getChildAt(i3);
            charSequence = viewStructure.newChild(i3);
            editText.dispatchProvideAutofillStructure((ViewStructure)charSequence, n3);
            if (editText != this.g) continue;
            charSequence.setHint(this.getHint());
        }
    }

    public void dispatchRestoreInstanceState(SparseArray sparseArray) {
        this.D0 = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.D0 = false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.F(canvas);
        this.E(canvas);
    }

    public void drawableStateChanged() {
        if (this.C0) {
            return;
        }
        boolean bl = true;
        this.C0 = true;
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        b b3 = this.y0;
        boolean bl2 = b3 != null ? b3.M0(nArray) : false;
        if (this.g != null) {
            if (!this.isLaidOut() || !this.isEnabled()) {
                bl = false;
            }
            this.w0(bl);
        }
        this.q0();
        this.C0();
        if (bl2) {
            this.invalidate();
        }
        this.C0 = false;
    }

    public final boolean e0() {
        return (this.e.G() || this.e.A() && this.N() || this.e.w() != null) && this.e.getMeasuredWidth() > 0;
    }

    public final boolean f0() {
        return (this.getStartIconDrawable() != null || this.getPrefixText() != null && this.getPrefixTextView().getVisibility() == 0) && this.d.getMeasuredWidth() > 0;
    }

    public final void g0() {
        if (this.w != null && this.v && !TextUtils.isEmpty((CharSequence)this.u)) {
            this.w.setText(this.u);
            androidx.transition.c.a((ViewGroup)this.c, this.z);
            this.w.setVisibility(0);
            this.w.bringToFront();
        }
    }

    public int getBaseline() {
        EditText editText = this.g;
        if (editText != null) {
            return editText.getBaseline() + this.getPaddingTop() + this.w();
        }
        return super.getBaseline();
    }

    public v2.i getBoxBackground() {
        int n3 = this.R;
        if (n3 != 1 && n3 != 2) {
            throw new IllegalStateException();
        }
        return this.I;
    }

    public int getBoxBackgroundColor() {
        return this.a0;
    }

    public int getBoxBackgroundMode() {
        return this.R;
    }

    public int getBoxCollapsedPaddingTop() {
        return this.S;
    }

    public float getBoxCornerRadiusBottomEnd() {
        if (com.google.android.material.internal.c0.m((View)this)) {
            return this.O.j().a(this.d0);
        }
        return this.O.l().a(this.d0);
    }

    public float getBoxCornerRadiusBottomStart() {
        if (com.google.android.material.internal.c0.m((View)this)) {
            return this.O.l().a(this.d0);
        }
        return this.O.j().a(this.d0);
    }

    public float getBoxCornerRadiusTopEnd() {
        if (com.google.android.material.internal.c0.m((View)this)) {
            return this.O.r().a(this.d0);
        }
        return this.O.t().a(this.d0);
    }

    public float getBoxCornerRadiusTopStart() {
        if (com.google.android.material.internal.c0.m((View)this)) {
            return this.O.t().a(this.d0);
        }
        return this.O.r().a(this.d0);
    }

    public int getBoxStrokeColor() {
        return this.p0;
    }

    public ColorStateList getBoxStrokeErrorColor() {
        return this.q0;
    }

    public int getBoxStrokeWidth() {
        return this.U;
    }

    public int getBoxStrokeWidthFocused() {
        return this.V;
    }

    public int getCounterMaxLength() {
        return this.o;
    }

    public CharSequence getCounterOverflowDescription() {
        TextView textView;
        if (this.n && this.p && (textView = this.r) != null) {
            return textView.getContentDescription();
        }
        return null;
    }

    public ColorStateList getCounterOverflowTextColor() {
        return this.C;
    }

    public ColorStateList getCounterTextColor() {
        return this.B;
    }

    public ColorStateList getCursorColor() {
        return this.D;
    }

    public ColorStateList getCursorErrorColor() {
        return this.E;
    }

    public ColorStateList getDefaultHintTextColor() {
        return this.l0;
    }

    public EditText getEditText() {
        return this.g;
    }

    public CharSequence getEndIconContentDescription() {
        return this.e.l();
    }

    public Drawable getEndIconDrawable() {
        return this.e.n();
    }

    public int getEndIconMinSize() {
        return this.e.o();
    }

    public int getEndIconMode() {
        return this.e.p();
    }

    public ImageView.ScaleType getEndIconScaleType() {
        return this.e.q();
    }

    public CheckableImageButton getEndIconView() {
        return this.e.r();
    }

    public CharSequence getError() {
        if (this.m.A()) {
            return this.m.p();
        }
        return null;
    }

    public int getErrorAccessibilityLiveRegion() {
        return this.m.n();
    }

    public CharSequence getErrorContentDescription() {
        return this.m.o();
    }

    public int getErrorCurrentTextColors() {
        return this.m.q();
    }

    public Drawable getErrorIconDrawable() {
        return this.e.s();
    }

    public CharSequence getHelperText() {
        if (this.m.B()) {
            return this.m.s();
        }
        return null;
    }

    public int getHelperTextCurrentTextColor() {
        return this.m.u();
    }

    public CharSequence getHint() {
        if (this.F) {
            return this.G;
        }
        return null;
    }

    public final float getHintCollapsedTextHeight() {
        return this.y0.r();
    }

    public final int getHintCurrentCollapsedTextColor() {
        return this.y0.w();
    }

    public int getHintMaxLines() {
        return this.y0.A();
    }

    public ColorStateList getHintTextColor() {
        return this.m0;
    }

    public f getLengthCounter() {
        return this.q;
    }

    public int getMaxEms() {
        return this.j;
    }

    public int getMaxWidth() {
        return this.l;
    }

    public int getMinEms() {
        return this.i;
    }

    public int getMinWidth() {
        return this.k;
    }

    @Deprecated
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.e.u();
    }

    @Deprecated
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.e.v();
    }

    public CharSequence getPlaceholderText() {
        if (this.v) {
            return this.u;
        }
        return null;
    }

    public int getPlaceholderTextAppearance() {
        return this.y;
    }

    public ColorStateList getPlaceholderTextColor() {
        return this.x;
    }

    public CharSequence getPrefixText() {
        return this.d.a();
    }

    public ColorStateList getPrefixTextColor() {
        return this.d.b();
    }

    public TextView getPrefixTextView() {
        return this.d.d();
    }

    public o getShapeAppearanceModel() {
        return this.O;
    }

    public CharSequence getStartIconContentDescription() {
        return this.d.e();
    }

    public Drawable getStartIconDrawable() {
        return this.d.f();
    }

    public int getStartIconMinSize() {
        return this.d.g();
    }

    public ImageView.ScaleType getStartIconScaleType() {
        return this.d.h();
    }

    public CharSequence getSuffixText() {
        return this.e.w();
    }

    public ColorStateList getSuffixTextColor() {
        return this.e.x();
    }

    public TextView getSuffixTextView() {
        return this.e.z();
    }

    public Typeface getTypeface() {
        return this.e0;
    }

    public final void h0() {
        if (this.R == 1) {
            if (s2.c.l(this.getContext())) {
                this.S = this.getResources().getDimensionPixelSize(z1.e.material_font_2_0_box_collapsed_padding_top);
                return;
            }
            if (s2.c.k(this.getContext())) {
                this.S = this.getResources().getDimensionPixelSize(z1.e.material_font_1_3_box_collapsed_padding_top);
            }
        }
    }

    public final void i0(Rect rect) {
        int n3;
        int n4;
        v2.i i3 = this.M;
        if (i3 != null) {
            n4 = rect.bottom;
            n3 = this.U;
            i3.setBounds(rect.left, n4 - n3, rect.right, n4);
        }
        if ((i3 = this.N) != null) {
            n4 = rect.bottom;
            n3 = this.V;
            i3.setBounds(rect.left, n4 - n3, rect.right, n4);
        }
    }

    public void j(g g3) {
        ((AbstractCollection)this.h0).add(g3);
        if (this.g != null) {
            g3.a(this);
        }
    }

    public final void j0(int n3) {
        this.y0.U0(n3);
        Rect rect = this.b0;
        com.google.android.material.internal.d.a((ViewGroup)this, (View)this.g, rect);
        this.y0.g0(this.s(rect));
        this.v0();
        this.l();
        this.t0(n3);
    }

    public final void k() {
        TextView textView = this.w;
        if (textView != null) {
            this.c.addView((View)textView);
            this.w.setVisibility(0);
        }
    }

    public final void k0() {
        if (this.r != null) {
            Object object = this.g;
            object = object == null ? null : object.getText();
            this.l0((Editable)object);
        }
    }

    public final void l() {
        if (this.g != null && this.R == 1) {
            if (!this.R()) {
                EditText editText = this.g;
                editText.setPaddingRelative(editText.getPaddingStart(), (int)(this.y0.r() + (float)this.f), this.g.getPaddingEnd(), this.getResources().getDimensionPixelSize(z1.e.material_filled_edittext_font_1_3_padding_bottom));
                return;
            }
            if (s2.c.l(this.getContext())) {
                EditText editText = this.g;
                editText.setPaddingRelative(editText.getPaddingStart(), this.getResources().getDimensionPixelSize(z1.e.material_filled_edittext_font_2_0_padding_top), this.g.getPaddingEnd(), this.getResources().getDimensionPixelSize(z1.e.material_filled_edittext_font_2_0_padding_bottom));
                return;
            }
            if (s2.c.k(this.getContext())) {
                EditText editText = this.g;
                editText.setPaddingRelative(editText.getPaddingStart(), this.getResources().getDimensionPixelSize(z1.e.material_filled_edittext_font_1_3_padding_top), this.g.getPaddingEnd(), this.getResources().getDimensionPixelSize(z1.e.material_filled_edittext_font_1_3_padding_bottom));
            }
        }
    }

    public void l0(Editable object) {
        int n3 = this.q.a((Editable)object);
        boolean bl = this.p;
        int n4 = this.o;
        if (n4 == -1) {
            this.r.setText((CharSequence)String.valueOf(n3));
            this.r.setContentDescription(null);
            this.p = false;
        } else {
            boolean bl2 = n3 > n4;
            this.p = bl2;
            TextInputLayout.m0(this.getContext(), this.r, n3, this.o, this.p);
            if (bl != this.p) {
                this.n0();
            }
            object = m0.a.c();
            this.r.setText((CharSequence)((m0.a)object).j(this.getContext().getString(z1.k.character_counter_pattern, new Object[]{n3, this.o})));
        }
        if (this.g != null && bl != this.p) {
            this.w0(false);
            this.C0();
            this.q0();
        }
    }

    public void m(float f3) {
        if (this.y0.H() == f3) {
            return;
        }
        if (this.B0 == null) {
            ValueAnimator valueAnimator;
            this.B0 = valueAnimator = new ValueAnimator();
            valueAnimator.setInterpolator(p2.k.g(this.getContext(), z1.c.motionEasingEmphasizedInterpolator, a2.a.b));
            this.B0.setDuration((long)p2.k.f(this.getContext(), z1.c.motionDurationMedium4, 167));
            this.B0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this){
                public final TextInputLayout a;
                {
                    this.a = textInputLayout;
                }

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.a.y0.D0(((Float)valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }
        this.B0.setFloatValues(new float[]{this.y0.H(), f3});
        this.B0.start();
    }

    public final void n() {
        int n3;
        Object object = this.I;
        if (object == null) {
            return;
        }
        o o3 = ((v2.i)object).K();
        if (o3 != (object = this.O)) {
            this.I.setShapeAppearanceModel((o)object);
        }
        if (this.x()) {
            this.I.s0(this.T, this.W);
        }
        this.a0 = n3 = this.r();
        this.I.i0(ColorStateList.valueOf((int)n3));
        this.o();
        this.s0();
    }

    public final void n0() {
        TextView textView = this.r;
        if (textView != null) {
            int n3 = this.p ? this.s : this.t;
            this.c0(textView, n3);
            if (!this.p && (textView = this.B) != null) {
                this.r.setTextColor((ColorStateList)textView);
            }
            if (this.p && (textView = this.C) != null) {
                this.r.setTextColor((ColorStateList)textView);
            }
        }
    }

    public final void o() {
        if (this.M != null && this.N != null) {
            if (this.y()) {
                v2.i i3 = this.M;
                ColorStateList colorStateList = this.g.isFocused() ? ColorStateList.valueOf((int)this.n0) : ColorStateList.valueOf((int)this.W);
                i3.i0(colorStateList);
                this.N.i0(ColorStateList.valueOf((int)this.W));
            }
            this.invalidate();
        }
    }

    public final void o0() {
        ColorStateList colorStateList = this.D;
        if (colorStateList == null) {
            colorStateList = h2.a.g(this.getContext(), a.colorControlActivated);
        }
        EditText editText = this.g;
        if (editText != null && com.google.android.material.textfield.z.a(editText) != null) {
            Drawable drawable = h0.a.r(com.google.android.material.textfield.z.a(this.g)).mutate();
            editText = colorStateList;
            if (this.S()) {
                ColorStateList colorStateList2 = this.E;
                editText = colorStateList;
                if (colorStateList2 != null) {
                    editText = colorStateList2;
                }
            }
            drawable.setTintList((ColorStateList)editText);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.y0.Z(configuration);
    }

    public void onGlobalLayout() {
        this.e.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
        this.E0 = false;
        boolean bl = this.u0();
        boolean bl2 = this.p0();
        if (!bl && !bl2) {
            return;
        }
        this.g.post((Runnable)new a0(this));
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        EditText editText = this.g;
        if (editText != null) {
            Rect rect = this.b0;
            com.google.android.material.internal.d.a((ViewGroup)this, (View)editText, rect);
            this.i0(rect);
            if (this.F) {
                this.y0.A0(this.g.getTextSize());
                n3 = this.g.getGravity();
                this.y0.m0(n3 & 0xFFFFFF8F | 0x30);
                this.y0.z0(n3);
                this.y0.g0(this.s(rect));
                this.y0.t0(this.v(rect));
                this.y0.b0();
                if (this.C() && !this.x0) {
                    this.W();
                }
            }
        }
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        if (!this.E0) {
            this.e.getViewTreeObserver().addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
            this.E0 = true;
        }
        this.y0();
        this.e.x0();
        if (!this.R()) {
            this.j0(this.g.getMeasuredWidth() - this.g.getCompoundPaddingLeft() - this.g.getCompoundPaddingRight());
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.setError(parcelable.e);
        if (parcelable.f) {
            this.post(new Runnable(this){
                public final TextInputLayout c;
                {
                    this.c = textInputLayout;
                }

                @Override
                public void run() {
                    this.c.e.h();
                }
            });
        }
        this.requestLayout();
    }

    public void onRtlPropertiesChanged(int n3) {
        super.onRtlPropertiesChanged(n3);
        boolean bl = true;
        if (n3 != 1) {
            bl = false;
        }
        if (bl != this.P) {
            float f3 = this.O.r().a(this.d0);
            float f4 = this.O.t().a(this.d0);
            float f5 = this.O.j().a(this.d0);
            float f6 = this.O.l().a(this.d0);
            v2.e e3 = this.O.q();
            v2.e e4 = this.O.s();
            v2.e e5 = this.O.i();
            Object object = this.O.k();
            object = v2.o.a().D(e4).H(e3).u((v2.e)object).y(e5).E(f4).I(f3).v(f6).z(f5).m();
            this.P = bl;
            this.setShapeAppearanceModel((o)object);
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.d0()) {
            savedState.e = this.getError();
        }
        savedState.f = this.e.E();
        return savedState;
    }

    public final void p(RectF rectF) {
        float f3 = rectF.left;
        int n3 = this.Q;
        rectF.left = f3 - (float)n3;
        rectF.right += (float)n3;
    }

    /*
     * Unable to fully structure code
     */
    public boolean p0() {
        block12: {
            if (this.g == null) {
                return false;
            }
            var3_1 = this.f0();
            var4_2 = true;
            if (!var3_1) break block12;
            var1_3 = this.d.getMeasuredWidth() - this.g.getPaddingLeft();
            if (this.f0 == null || this.g0 != var1_3) {
                this.f0 = var5_4 = new ColorDrawable();
                this.g0 = var1_3;
                var5_4.setBounds(0, 0, var1_3, 1);
            }
            if ((var6_6 = (var7_5 = this.g.getCompoundDrawablesRelative())[0]) == (var5_4 = this.f0)) ** GOTO lbl-1000
            this.g.setCompoundDrawablesRelative((Drawable)var5_4, var7_5[1], var7_5[2], var7_5[3]);
            ** GOTO lbl19
        }
        if (this.f0 != null) {
            var5_4 = this.g.getCompoundDrawablesRelative();
            this.g.setCompoundDrawablesRelative(null, var5_4[1], var5_4[2], var5_4[3]);
            this.f0 = null;
lbl19:
            // 2 sources

            var3_1 = true;
        } else lbl-1000:
        // 2 sources

        {
            var3_1 = false;
        }
        if (this.e0()) {
            var2_7 = this.e.z().getMeasuredWidth() - this.g.getPaddingRight();
            var5_4 = this.e.k();
            var1_3 = var2_7;
            if (var5_4 != null) {
                var1_3 = var2_7 + var5_4.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams)var5_4.getLayoutParams()).getMarginStart();
            }
            var5_4 = this.g.getCompoundDrawablesRelative();
            var6_6 = this.i0;
            if (var6_6 != null && this.j0 != var1_3) {
                this.j0 = var1_3;
                var6_6.setBounds(0, 0, var1_3, 1);
                this.g.setCompoundDrawablesRelative(var5_4[0], var5_4[1], this.i0, var5_4[3]);
                return true;
            }
            if (var6_6 == null) {
                this.i0 = var6_6 = new ColorDrawable();
                this.j0 = var1_3;
                var6_6.setBounds(0, 0, var1_3, 1);
            }
            if ((var7_5 = var5_4[2]) != (var6_6 = this.i0)) {
                this.k0 = var7_5;
                this.g.setCompoundDrawablesRelative(var5_4[0], var5_4[1], var6_6, var5_4[3]);
                return true;
            }
        } else if (this.i0 != null) {
            var5_4 = this.g.getCompoundDrawablesRelative();
            if (var5_4[2] == this.i0) {
                this.g.setCompoundDrawablesRelative(var5_4[0], var5_4[1], this.k0, var5_4[3]);
                var3_1 = var4_2;
            }
            this.i0 = null;
            return var3_1;
        }
        return var3_1;
    }

    public final void q() {
        int n3 = this.R;
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 == 2) {
                    this.I = this.F && !(this.I instanceof h) ? com.google.android.material.textfield.h.E0(this.O) : new v2.i(this.O);
                    this.M = null;
                    this.N = null;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.R);
                stringBuilder.append(" is illegal; only @BoxBackgroundMode constants are supported.");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.I = new v2.i(this.O);
            this.M = new v2.i();
            this.N = new v2.i();
            return;
        }
        this.I = null;
        this.M = null;
        this.N = null;
    }

    public void q0() {
        Drawable drawable;
        EditText editText = this.g;
        if (editText != null && this.R == 0 && (drawable = editText.getBackground()) != null) {
            editText = drawable;
            if (androidx.appcompat.widget.z.a(drawable)) {
                editText = drawable.mutate();
            }
            if (this.d0()) {
                editText.setColorFilter((ColorFilter)androidx.appcompat.widget.g.e(this.getErrorCurrentTextColors(), PorterDuff.Mode.SRC_IN));
                return;
            }
            if (this.p && (drawable = this.r) != null) {
                editText.setColorFilter((ColorFilter)androidx.appcompat.widget.g.e(drawable.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
                return;
            }
            h0.a.c((Drawable)editText);
            this.g.refreshDrawableState();
        }
    }

    public final int r() {
        int n3 = this.a0;
        if (this.R == 1) {
            n3 = h2.a.i(h2.a.e((View)this, z1.c.colorSurface, 0), this.a0);
        }
        return n3;
    }

    public final void r0() {
        Drawable drawable = this.getEditTextBoxBackground();
        this.g.setBackground(drawable);
    }

    public final Rect s(Rect rect) {
        if (this.g != null) {
            Rect rect2 = this.c0;
            boolean bl = com.google.android.material.internal.c0.m((View)this);
            rect2.bottom = rect.bottom;
            int n3 = this.R;
            if (n3 != 1) {
                if (n3 != 2) {
                    rect2.left = this.J(rect.left, bl);
                    rect2.top = this.getPaddingTop();
                    rect2.right = this.K(rect.right, bl);
                    return rect2;
                }
                rect2.left = rect.left + this.g.getPaddingLeft();
                rect2.top = rect.top - this.w();
                rect2.right = rect.right - this.g.getPaddingRight();
                return rect2;
            }
            rect2.left = this.J(rect.left, bl);
            rect2.top = rect.top + this.S;
            rect2.right = this.K(rect.right, bl);
            return rect2;
        }
        throw new IllegalStateException();
    }

    public void s0() {
        EditText editText = this.g;
        if (editText != null && this.I != null && (this.L || editText.getBackground() == null) && this.R != 0) {
            this.r0();
            this.L = true;
        }
    }

    public void setBoxBackgroundColor(int n3) {
        if (this.a0 != n3) {
            this.a0 = n3;
            this.r0 = n3;
            this.t0 = n3;
            this.u0 = n3;
            this.n();
        }
    }

    public void setBoxBackgroundColorResource(int n3) {
        this.setBoxBackgroundColor(e0.a.b(this.getContext(), n3));
    }

    public void setBoxBackgroundColorStateList(ColorStateList colorStateList) {
        int n3;
        this.r0 = n3 = colorStateList.getDefaultColor();
        this.a0 = n3;
        this.s0 = colorStateList.getColorForState(new int[]{-16842910}, -1);
        this.t0 = colorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        this.u0 = colorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
        this.n();
    }

    public void setBoxBackgroundMode(int n3) {
        if (n3 != this.R) {
            this.R = n3;
            if (this.g != null) {
                this.V();
            }
        }
    }

    public void setBoxCollapsedPaddingTop(int n3) {
        this.S = n3;
    }

    public void setBoxCornerFamily(int n3) {
        this.O = this.O.w().C(n3, this.O.r()).G(n3, this.O.t()).t(n3, this.O.j()).x(n3, this.O.l()).m();
        this.n();
    }

    public void setBoxCornerRadii(float f3, float f4, float f5, float f6) {
        boolean bl;
        this.P = bl = com.google.android.material.internal.c0.m((View)this);
        float f7 = bl ? f4 : f3;
        if (!bl) {
            f3 = f4;
        }
        f4 = bl ? f6 : f5;
        if (!bl) {
            f5 = f6;
        }
        v2.i i3 = this.I;
        if (i3 != null && i3.P() == f7 && this.I.Q() == f3 && this.I.x() == f4 && this.I.y() == f5) {
            return;
        }
        this.O = this.O.w().E(f7).I(f3).v(f4).z(f5).m();
        this.n();
    }

    public void setBoxCornerRadiiResources(int n3, int n4, int n5, int n6) {
        this.setBoxCornerRadii(this.getContext().getResources().getDimension(n3), this.getContext().getResources().getDimension(n4), this.getContext().getResources().getDimension(n6), this.getContext().getResources().getDimension(n5));
    }

    public void setBoxStrokeColor(int n3) {
        if (this.p0 != n3) {
            this.p0 = n3;
            this.C0();
        }
    }

    public void setBoxStrokeColorStateList(ColorStateList colorStateList) {
        if (colorStateList.isStateful()) {
            this.n0 = colorStateList.getDefaultColor();
            this.v0 = colorStateList.getColorForState(new int[]{-16842910}, -1);
            this.o0 = colorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
            this.p0 = colorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        } else if (this.p0 != colorStateList.getDefaultColor()) {
            this.p0 = colorStateList.getDefaultColor();
        }
        this.C0();
    }

    public void setBoxStrokeErrorColor(ColorStateList colorStateList) {
        if (this.q0 != colorStateList) {
            this.q0 = colorStateList;
            this.C0();
        }
    }

    public void setBoxStrokeWidth(int n3) {
        this.U = n3;
        this.C0();
    }

    public void setBoxStrokeWidthFocused(int n3) {
        this.V = n3;
        this.C0();
    }

    public void setBoxStrokeWidthFocusedResource(int n3) {
        this.setBoxStrokeWidthFocused(this.getResources().getDimensionPixelSize(n3));
    }

    public void setBoxStrokeWidthResource(int n3) {
        this.setBoxStrokeWidth(this.getResources().getDimensionPixelSize(n3));
    }

    public void setCounterEnabled(boolean bl) {
        if (this.n != bl) {
            if (bl) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(this.getContext());
                this.r = appCompatTextView;
                appCompatTextView.setId(z1.g.textinput_counter);
                appCompatTextView = this.e0;
                if (appCompatTextView != null) {
                    this.r.setTypeface((Typeface)appCompatTextView);
                }
                this.r.setMaxLines(1);
                this.m.e(this.r, 2);
                ((ViewGroup.MarginLayoutParams)this.r.getLayoutParams()).setMarginStart(this.getResources().getDimensionPixelOffset(z1.e.mtrl_textinput_counter_margin_start));
                this.n0();
                this.k0();
            } else {
                this.m.C(this.r, 2);
                this.r = null;
            }
            this.n = bl;
        }
    }

    public void setCounterMaxLength(int n3) {
        if (this.o != n3) {
            this.o = n3 > 0 ? n3 : -1;
            if (this.n) {
                this.k0();
            }
        }
    }

    public void setCounterOverflowTextAppearance(int n3) {
        if (this.s != n3) {
            this.s = n3;
            this.n0();
        }
    }

    public void setCounterOverflowTextColor(ColorStateList colorStateList) {
        if (this.C != colorStateList) {
            this.C = colorStateList;
            this.n0();
        }
    }

    public void setCounterTextAppearance(int n3) {
        if (this.t != n3) {
            this.t = n3;
            this.n0();
        }
    }

    public void setCounterTextColor(ColorStateList colorStateList) {
        if (this.B != colorStateList) {
            this.B = colorStateList;
            this.n0();
        }
    }

    public void setCursorColor(ColorStateList colorStateList) {
        if (this.D != colorStateList) {
            this.D = colorStateList;
            this.o0();
        }
    }

    public void setCursorErrorColor(ColorStateList colorStateList) {
        if (this.E != colorStateList) {
            this.E = colorStateList;
            if (this.S()) {
                this.o0();
            }
        }
    }

    public void setDefaultHintTextColor(ColorStateList colorStateList) {
        this.l0 = colorStateList;
        this.m0 = colorStateList;
        if (this.g != null) {
            this.w0(false);
        }
    }

    public void setEnabled(boolean bl) {
        TextInputLayout.Y((ViewGroup)this, bl);
        super.setEnabled(bl);
    }

    public void setEndIconActivated(boolean bl) {
        this.e.N(bl);
    }

    public void setEndIconCheckable(boolean bl) {
        this.e.O(bl);
    }

    public void setEndIconContentDescription(int n3) {
        this.e.P(n3);
    }

    public void setEndIconContentDescription(CharSequence charSequence) {
        this.e.Q(charSequence);
    }

    public void setEndIconDrawable(int n3) {
        this.e.R(n3);
    }

    public void setEndIconDrawable(Drawable drawable) {
        this.e.S(drawable);
    }

    public void setEndIconMinSize(int n3) {
        this.e.T(n3);
    }

    public void setEndIconMode(int n3) {
        this.e.U(n3);
    }

    public void setEndIconOnClickListener(View.OnClickListener onClickListener) {
        this.e.V(onClickListener);
    }

    public void setEndIconOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.e.W(onLongClickListener);
    }

    public void setEndIconScaleType(ImageView.ScaleType scaleType) {
        this.e.X(scaleType);
    }

    public void setEndIconTintList(ColorStateList colorStateList) {
        this.e.Y(colorStateList);
    }

    public void setEndIconTintMode(PorterDuff.Mode mode) {
        this.e.Z(mode);
    }

    public void setEndIconVisible(boolean bl) {
        this.e.a0(bl);
    }

    public void setError(CharSequence charSequence) {
        if (!this.m.A()) {
            if (TextUtils.isEmpty((CharSequence)charSequence)) {
                return;
            }
            this.setErrorEnabled(true);
        }
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.m.Q(charSequence);
            return;
        }
        this.m.w();
    }

    public void setErrorAccessibilityLiveRegion(int n3) {
        this.m.E(n3);
    }

    public void setErrorContentDescription(CharSequence charSequence) {
        this.m.F(charSequence);
    }

    public void setErrorEnabled(boolean bl) {
        this.m.G(bl);
    }

    public void setErrorIconDrawable(int n3) {
        this.e.b0(n3);
    }

    public void setErrorIconDrawable(Drawable drawable) {
        this.e.c0(drawable);
    }

    public void setErrorIconOnClickListener(View.OnClickListener onClickListener) {
        this.e.d0(onClickListener);
    }

    public void setErrorIconOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.e.e0(onLongClickListener);
    }

    public void setErrorIconTintList(ColorStateList colorStateList) {
        this.e.f0(colorStateList);
    }

    public void setErrorIconTintMode(PorterDuff.Mode mode) {
        this.e.g0(mode);
    }

    public void setErrorTextAppearance(int n3) {
        this.m.H(n3);
    }

    public void setErrorTextColor(ColorStateList colorStateList) {
        this.m.I(colorStateList);
    }

    public void setExpandedHintEnabled(boolean bl) {
        if (this.z0 != bl) {
            this.z0 = bl;
            this.w0(false);
        }
    }

    public void setHelperText(CharSequence charSequence) {
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.P()) {
                this.setHelperTextEnabled(false);
            }
            return;
        }
        if (!this.P()) {
            this.setHelperTextEnabled(true);
        }
        this.m.R(charSequence);
    }

    public void setHelperTextColor(ColorStateList colorStateList) {
        this.m.L(colorStateList);
    }

    public void setHelperTextEnabled(boolean bl) {
        this.m.K(bl);
    }

    public void setHelperTextTextAppearance(int n3) {
        this.m.J(n3);
    }

    public void setHint(int n3) {
        CharSequence charSequence = n3 != 0 ? this.getResources().getText(n3) : null;
        this.setHint(charSequence);
    }

    public void setHint(CharSequence charSequence) {
        if (this.F) {
            this.setHintInternal(charSequence);
            this.sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean bl) {
        this.A0 = bl;
    }

    public void setHintEnabled(boolean bl) {
        if (bl != this.F) {
            this.F = bl;
            if (!bl) {
                this.H = false;
                if (!TextUtils.isEmpty((CharSequence)this.G) && TextUtils.isEmpty((CharSequence)this.g.getHint())) {
                    this.g.setHint(this.G);
                }
                this.setHintInternal(null);
            } else {
                CharSequence charSequence = this.g.getHint();
                if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                    if (TextUtils.isEmpty((CharSequence)this.G)) {
                        this.setHint(charSequence);
                    }
                    this.g.setHint(null);
                }
                this.H = true;
            }
            if (this.g != null) {
                this.v0();
            }
        }
    }

    public void setHintMaxLines(int n3) {
        this.y0.i0(n3);
        this.y0.v0(n3);
        this.requestLayout();
    }

    public void setHintTextAppearance(int n3) {
        this.y0.j0(n3);
        this.m0 = this.y0.p();
        if (this.g != null) {
            this.w0(false);
            this.v0();
        }
    }

    public void setHintTextColor(ColorStateList colorStateList) {
        if (this.m0 != colorStateList) {
            if (this.l0 == null) {
                this.y0.l0(colorStateList);
            }
            this.m0 = colorStateList;
            if (this.g != null) {
                this.w0(false);
            }
        }
    }

    public void setLengthCounter(f f3) {
        this.q = f3;
    }

    public void setMaxEms(int n3) {
        this.j = n3;
        EditText editText = this.g;
        if (editText != null && n3 != -1) {
            editText.setMaxEms(n3);
        }
    }

    public void setMaxWidth(int n3) {
        this.l = n3;
        EditText editText = this.g;
        if (editText != null && n3 != -1) {
            editText.setMaxWidth(n3);
        }
    }

    public void setMaxWidthResource(int n3) {
        this.setMaxWidth(this.getContext().getResources().getDimensionPixelSize(n3));
    }

    public void setMinEms(int n3) {
        this.i = n3;
        EditText editText = this.g;
        if (editText != null && n3 != -1) {
            editText.setMinEms(n3);
        }
    }

    public void setMinWidth(int n3) {
        this.k = n3;
        EditText editText = this.g;
        if (editText != null && n3 != -1) {
            editText.setMinWidth(n3);
        }
    }

    public void setMinWidthResource(int n3) {
        this.setMinWidth(this.getContext().getResources().getDimensionPixelSize(n3));
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(int n3) {
        this.e.i0(n3);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(CharSequence charSequence) {
        this.e.j0(charSequence);
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(int n3) {
        this.e.k0(n3);
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(Drawable drawable) {
        this.e.l0(drawable);
    }

    @Deprecated
    public void setPasswordVisibilityToggleEnabled(boolean bl) {
        this.e.m0(bl);
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintList(ColorStateList colorStateList) {
        this.e.n0(colorStateList);
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode mode) {
        this.e.o0(mode);
    }

    public void setPlaceholderText(CharSequence charSequence) {
        if (this.w == null) {
            Object object = new AppCompatTextView(this.getContext());
            this.w = object;
            object.setId(z1.g.textinput_placeholder);
            this.w.setImportantForAccessibility(1);
            this.w.setAccessibilityLiveRegion(1);
            object = this.B();
            this.z = object;
            ((Transition)object).l0(67L);
            this.A = this.B();
            this.setPlaceholderTextAppearance(this.y);
            this.setPlaceholderTextColor(this.x);
            o0.x0.h0((View)this.w, new o0.a(this){
                public final TextInputLayout d;
                {
                    this.d = textInputLayout;
                }

                @Override
                public void g(View view, s s3) {
                    super.g(view, s3);
                    s3.J0(false);
                }
            });
        }
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            this.setPlaceholderTextEnabled(false);
        } else {
            if (!this.v) {
                this.setPlaceholderTextEnabled(true);
            }
            this.u = charSequence;
        }
        this.z0();
    }

    public void setPlaceholderTextAppearance(int n3) {
        this.y = n3;
        TextView textView = this.w;
        if (textView != null) {
            androidx.core.widget.j.m(textView, n3);
        }
    }

    public void setPlaceholderTextColor(ColorStateList colorStateList) {
        if (this.x != colorStateList) {
            this.x = colorStateList;
            TextView textView = this.w;
            if (textView != null && colorStateList != null) {
                textView.setTextColor(colorStateList);
            }
        }
    }

    public void setPrefixText(CharSequence charSequence) {
        this.d.n(charSequence);
    }

    public void setPrefixTextAppearance(int n3) {
        this.d.o(n3);
    }

    public void setPrefixTextColor(ColorStateList colorStateList) {
        this.d.p(colorStateList);
    }

    public void setShapeAppearanceModel(o o3) {
        v2.i i3 = this.I;
        if (i3 != null && i3.K() != o3) {
            this.O = o3;
            this.n();
        }
    }

    public void setStartIconCheckable(boolean bl) {
        this.d.q(bl);
    }

    public void setStartIconContentDescription(int n3) {
        CharSequence charSequence = n3 != 0 ? this.getResources().getText(n3) : null;
        this.setStartIconContentDescription(charSequence);
    }

    public void setStartIconContentDescription(CharSequence charSequence) {
        this.d.r(charSequence);
    }

    public void setStartIconDrawable(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.setStartIconDrawable(drawable);
    }

    public void setStartIconDrawable(Drawable drawable) {
        this.d.s(drawable);
    }

    public void setStartIconMinSize(int n3) {
        this.d.t(n3);
    }

    public void setStartIconOnClickListener(View.OnClickListener onClickListener) {
        this.d.u(onClickListener);
    }

    public void setStartIconOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.d.v(onLongClickListener);
    }

    public void setStartIconScaleType(ImageView.ScaleType scaleType) {
        this.d.w(scaleType);
    }

    public void setStartIconTintList(ColorStateList colorStateList) {
        this.d.x(colorStateList);
    }

    public void setStartIconTintMode(PorterDuff.Mode mode) {
        this.d.y(mode);
    }

    public void setStartIconVisible(boolean bl) {
        this.d.z(bl);
    }

    public void setSuffixText(CharSequence charSequence) {
        this.e.p0(charSequence);
    }

    public void setSuffixTextAppearance(int n3) {
        this.e.q0(n3);
    }

    public void setSuffixTextColor(ColorStateList colorStateList) {
        this.e.r0(colorStateList);
    }

    public void setTextInputAccessibilityDelegate(e e3) {
        EditText editText = this.g;
        if (editText != null) {
            o0.x0.h0((View)editText, e3);
        }
    }

    public void setTypeface(Typeface typeface) {
        if (typeface != this.e0) {
            this.e0 = typeface;
            this.y0.R0(typeface);
            this.m.N(typeface);
            TextView textView = this.r;
            if (textView != null) {
                textView.setTypeface(typeface);
            }
        }
    }

    public final int t(Rect rect, Rect rect2, float f3) {
        if (this.U()) {
            return (int)((float)rect2.top + f3);
        }
        return rect.bottom - this.g.getCompoundPaddingBottom();
    }

    public final void t0(int n3) {
        if (this.g != null) {
            float f3 = this.y0.D();
            Object object = this.u;
            float f4 = 0.0f;
            float f5 = 0.0f;
            if (object != null) {
                object = new TextPaint(129);
                object.set(this.w.getPaint());
                object.setTextSize(this.w.getTextSize());
                object.setTypeface(this.w.getTypeface());
                object.setLetterSpacing(this.w.getLetterSpacing());
                object = com.google.android.material.internal.u.b(this.u, (TextPaint)object, n3);
                boolean bl = this.getLayoutDirection() == 1;
                object = ((com.google.android.material.internal.u)object).g(bl).f(true).h(this.w.getLineSpacingExtra(), this.w.getLineSpacingMultiplier()).j(new c0(this)).a();
                f4 = f5;
                if (this.R == 1) {
                    f5 = this.y0.r();
                    f4 = this.S;
                    f4 = (float)this.f + (f5 + f4);
                }
                f4 += (float)object.getHeight();
            }
            f4 = Math.max(f3, f4);
            if ((float)this.g.getMeasuredHeight() < f4) {
                this.g.setMinimumHeight(Math.round(f4));
            }
        }
    }

    public final int u(Rect rect, float f3) {
        if (this.U()) {
            return (int)((float)rect.centerY() - f3 / 2.0f);
        }
        int n3 = this.R == 0 && !this.R() ? (int)(this.y0.E() / 2.0f) : 0;
        return rect.top + this.g.getCompoundPaddingTop() - n3;
    }

    public final boolean u0() {
        if (this.g == null) {
            return false;
        }
        int n3 = Math.max(this.e.getMeasuredHeight(), this.d.getMeasuredHeight());
        if (this.g.getMeasuredHeight() < n3) {
            this.g.setMinimumHeight(n3);
            return true;
        }
        return false;
    }

    public final Rect v(Rect rect) {
        if (this.g != null) {
            Rect rect2 = this.c0;
            float f3 = this.R() ? this.y0.E() : this.y0.B() * (float)this.y0.z();
            rect2.left = rect.left + this.g.getCompoundPaddingLeft();
            rect2.top = this.u(rect, f3);
            rect2.right = rect.right - this.g.getCompoundPaddingRight();
            rect2.bottom = this.t(rect, rect2, f3);
            return rect2;
        }
        throw new IllegalStateException();
    }

    public final void v0() {
        if (this.R != 1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.c.getLayoutParams();
            int n3 = this.w();
            if (n3 != layoutParams.topMargin) {
                layoutParams.topMargin = n3;
                this.c.requestLayout();
            }
        }
    }

    public final int w() {
        if (!this.F) {
            return 0;
        }
        int n3 = this.R;
        if (n3 != 0) {
            if (n3 != 2) {
                return 0;
            }
            if (this.R()) {
                return (int)(this.y0.r() / 2.0f);
            }
            return Math.max(0, (int)(this.y0.r() - this.y0.n() / 2.0f));
        }
        return (int)this.y0.r();
    }

    public void w0(boolean bl) {
        this.x0(bl, false);
    }

    public final boolean x() {
        return this.R == 2 && this.y();
    }

    public final void x0(boolean bl, boolean bl2) {
        block19: {
            block18: {
                block17: {
                    boolean bl3 = this.isEnabled();
                    EditText editText = this.g;
                    int n3 = 0;
                    boolean bl4 = editText != null && !TextUtils.isEmpty((CharSequence)editText.getText());
                    editText = this.g;
                    int n4 = n3;
                    if (editText != null) {
                        n4 = n3;
                        if (editText.hasFocus()) {
                            n4 = 1;
                        }
                    }
                    if ((editText = this.l0) != null) {
                        this.y0.e0((ColorStateList)editText);
                    }
                    if (!bl3) {
                        editText = this.l0;
                        if (editText != null) {
                            n3 = this.v0;
                            n3 = editText.getColorForState(new int[]{-16842910}, n3);
                        } else {
                            n3 = this.v0;
                        }
                        this.y0.e0(ColorStateList.valueOf((int)n3));
                    } else if (this.d0()) {
                        this.y0.e0(this.m.r());
                    } else if (this.p && (editText = this.r) != null) {
                        this.y0.e0(editText.getTextColors());
                    } else if (n4 != 0 && (editText = this.m0) != null) {
                        this.y0.l0((ColorStateList)editText);
                    }
                    if (bl4 || !this.z0 || this.isEnabled() && n4 != 0) break block17;
                    if (bl2 || !this.x0) {
                        this.G(bl);
                        return;
                    }
                    break block18;
                }
                if (bl2 || this.x0) break block19;
            }
            return;
        }
        this.A(bl);
    }

    public final boolean y() {
        return this.T > -1 && this.W != 0;
    }

    public final void y0() {
        EditText editText;
        if (this.w != null && (editText = this.g) != null) {
            int n3 = editText.getGravity();
            this.w.setGravity(n3);
            this.w.setPadding(this.g.getCompoundPaddingLeft(), this.g.getCompoundPaddingTop(), this.g.getCompoundPaddingRight(), this.g.getCompoundPaddingBottom());
        }
    }

    public final void z() {
        if (this.C()) {
            ((h)this.I).G0();
        }
    }

    public final void z0() {
        Object object = this.g;
        object = object == null ? null : object.getText();
        this.A0((Editable)object);
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
        public CharSequence e;
        public boolean f;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            int n3 = parcel.readInt();
            boolean bl = true;
            if (n3 != 1) {
                bl = false;
            }
            this.f = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TextInputLayout.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" error=");
            stringBuilder.append((Object)this.e);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            TextUtils.writeToParcel((CharSequence)this.e, (Parcel)parcel, (int)n3);
            parcel.writeInt(this.f ? 1 : 0);
        }
    }

    public static class e
    extends o0.a {
        public final TextInputLayout d;

        public e(TextInputLayout textInputLayout) {
            this.d = textInputLayout;
        }

        @Override
        public void g(View view, s s3) {
            super.g(view, s3);
            Object object = this.d.getEditText();
            object = object != null ? object.getText() : null;
            CharSequence charSequence = this.d.getHint();
            CharSequence charSequence2 = this.d.getError();
            CharSequence charSequence3 = this.d.getPlaceholderText();
            int n3 = this.d.getCounterMaxLength();
            CharSequence charSequence4 = this.d.getCounterOverflowDescription();
            boolean bl = TextUtils.isEmpty((CharSequence)object);
            boolean bl2 = TextUtils.isEmpty((CharSequence)charSequence);
            boolean bl3 = this.d.Q();
            boolean bl4 = TextUtils.isEmpty((CharSequence)charSequence2);
            boolean bl5 = !bl4 || !TextUtils.isEmpty((CharSequence)charSequence4);
            charSequence = !bl2 ? charSequence.toString() : "";
            this.d.d.A(s3);
            if (!bl) {
                s3.H0((CharSequence)object);
            } else if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                s3.H0(charSequence);
                if (!bl3 && charSequence3 != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append(", ");
                    stringBuilder.append((Object)charSequence3);
                    s3.H0(stringBuilder.toString());
                }
            } else if (charSequence3 != null) {
                s3.H0(charSequence3);
            }
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                s3.r0(charSequence);
                s3.D0(bl);
            }
            if (object == null || object.length() != n3) {
                n3 = -1;
            }
            s3.t0(n3);
            if (bl5) {
                object = !bl4 ? charSequence2 : charSequence4;
                s3.n0((CharSequence)object);
            }
            if ((object = this.d.m.t()) != null) {
                s3.s0((View)object);
            }
            this.d.e.m().o(view, s3);
        }

        @Override
        public void h(View view, AccessibilityEvent accessibilityEvent) {
            super.h(view, accessibilityEvent);
            this.d.e.m().p(view, accessibilityEvent);
        }
    }

    public static interface f {
        public int a(Editable var1);
    }

    public static interface g {
        public void a(TextInputLayout var1);
    }
}

