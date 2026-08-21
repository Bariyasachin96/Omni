/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Region$Op
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build$VERSION
 *  android.text.InputFilter
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.ActionMode$Callback
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.CompoundButton
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.i;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.p;
import androidx.appcompat.widget.t0;
import androidx.appcompat.widget.z;
import androidx.core.widget.j;
import androidx.emoji2.text.f;
import c.a;
import c.h;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import o0.x0;

public class SwitchCompat
extends CompoundButton {
    public static final Property U = new Property(Float.class, "thumbPos"){

        public Float a(SwitchCompat switchCompat) {
            return Float.valueOf(switchCompat.B);
        }

        public void b(SwitchCompat switchCompat, Float f3) {
            switchCompat.setThumbPosition(f3.floatValue());
        }
    };
    public static final int[] V = new int[]{0x10100A0};
    public int A;
    public float B;
    public int C;
    public int D;
    public int E;
    public int F;
    public int G;
    public int H;
    public int I;
    public boolean J = true;
    public final TextPaint K;
    public ColorStateList L;
    public Layout M;
    public Layout N;
    public TransformationMethod O;
    public ObjectAnimator P;
    public final p Q;
    public i R;
    public b S;
    public final Rect T;
    public Drawable c;
    public ColorStateList d = null;
    public PorterDuff.Mode e = null;
    public boolean f = false;
    public boolean g = false;
    public Drawable h;
    public ColorStateList i = null;
    public PorterDuff.Mode j = null;
    public boolean k = false;
    public boolean l = false;
    public int m;
    public int n;
    public int o;
    public boolean p;
    public CharSequence q;
    public CharSequence r;
    public CharSequence s;
    public CharSequence t;
    public boolean u;
    public int v;
    public int w;
    public float x;
    public float y;
    public VelocityTracker z = VelocityTracker.obtain();

    public SwitchCompat(Context context) {
        this(context, null);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.switchStyle);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        int n4;
        this.T = new Rect();
        i0.a((View)this, this.getContext());
        Object object = new TextPaint(1);
        this.K = object;
        ((TextPaint)object).density = this.getResources().getDisplayMetrics().density;
        Object object2 = c.j.SwitchCompat;
        object = m0.v(context, attributeSet, (int[])object2, n3, 0);
        x0.f0((View)this, context, (int[])object2, attributeSet, ((m0)object).r(), n3, 0);
        object2 = ((m0)object).g(c.j.SwitchCompat_android_thumb);
        this.c = (Drawable)object2;
        if (object2 != null) {
            object2.setCallback((Drawable.Callback)this);
        }
        object2 = ((m0)object).g(c.j.SwitchCompat_track);
        this.h = (Drawable)object2;
        if (object2 != null) {
            object2.setCallback((Drawable.Callback)this);
        }
        this.setTextOnInternal(((m0)object).p(c.j.SwitchCompat_android_textOn));
        this.setTextOffInternal(((m0)object).p(c.j.SwitchCompat_android_textOff));
        this.u = ((m0)object).a(c.j.SwitchCompat_showText, true);
        this.m = ((m0)object).f(c.j.SwitchCompat_thumbTextPadding, 0);
        this.n = ((m0)object).f(c.j.SwitchCompat_switchMinWidth, 0);
        this.o = ((m0)object).f(c.j.SwitchCompat_switchPadding, 0);
        this.p = ((m0)object).a(c.j.SwitchCompat_splitTrack, false);
        object2 = ((m0)object).c(c.j.SwitchCompat_thumbTint);
        if (object2 != null) {
            this.d = (ColorStateList)object2;
            this.f = true;
        }
        if (this.e != (object2 = (Object)androidx.appcompat.widget.z.e(((m0)object).k(c.j.SwitchCompat_thumbTintMode, -1), null))) {
            this.e = (PorterDuff.Mode)object2;
            this.g = true;
        }
        if (this.f || this.g) {
            this.b();
        }
        if ((object2 = (Object)((m0)object).c(c.j.SwitchCompat_trackTint)) != null) {
            this.i = (ColorStateList)object2;
            this.k = true;
        }
        if (this.j != (object2 = (Object)androidx.appcompat.widget.z.e(((m0)object).k(c.j.SwitchCompat_trackTintMode, -1), null))) {
            this.j = (PorterDuff.Mode)object2;
            this.l = true;
        }
        if (this.k || this.l) {
            this.c();
        }
        if ((n4 = ((m0)object).n(c.j.SwitchCompat_switchTextAppearance, 0)) != 0) {
            this.setSwitchTextAppearance(context, n4);
        }
        object2 = new p((TextView)this);
        this.Q = object2;
        ((p)object2).m(attributeSet, n3);
        ((m0)object).x();
        context = ViewConfiguration.get((Context)context);
        this.w = context.getScaledTouchSlop();
        this.A = context.getScaledMinimumFlingVelocity();
        this.getEmojiTextViewHelper().c(attributeSet, n3);
        this.refreshDrawableState();
        this.setChecked(this.isChecked());
    }

    public static float f(float f3, float f4, float f5) {
        if (f3 < f4) {
            return f4;
        }
        if (f3 > f5) {
            return f5;
        }
        return f3;
    }

    private i getEmojiTextViewHelper() {
        if (this.R == null) {
            this.R = new i((TextView)this);
        }
        return this.R;
    }

    private boolean getTargetCheckedState() {
        return this.B > 0.5f;
    }

    private int getThumbOffset() {
        float f3 = t0.b((View)this) ? 1.0f - this.B : this.B;
        return (int)(f3 * (float)this.getThumbScrollRange() + 0.5f);
    }

    private int getThumbScrollRange() {
        Drawable drawable = this.h;
        if (drawable != null) {
            Rect rect = this.T;
            drawable.getPadding(rect);
            drawable = this.c;
            drawable = drawable != null ? androidx.appcompat.widget.z.d(drawable) : androidx.appcompat.widget.z.c;
            return this.C - this.E - rect.left - rect.right - drawable.left - drawable.right;
        }
        return 0;
    }

    private void setTextOffInternal(CharSequence charSequence) {
        this.s = charSequence;
        this.t = this.g(charSequence);
        this.N = null;
        if (this.u) {
            this.n();
        }
    }

    private void setTextOnInternal(CharSequence charSequence) {
        this.q = charSequence;
        this.r = this.g(charSequence);
        this.M = null;
        if (this.u) {
            this.n();
        }
    }

    public final void a(boolean bl) {
        ObjectAnimator objectAnimator;
        float f3 = bl ? 1.0f : 0.0f;
        this.P = objectAnimator = ObjectAnimator.ofFloat((Object)((Object)this), (Property)U, (float[])new float[]{f3});
        objectAnimator.setDuration(250L);
        this.P.setAutoCancel(true);
        this.P.start();
    }

    public final void b() {
        Drawable drawable = this.c;
        if (drawable != null && (this.f || this.g)) {
            this.c = drawable = h0.a.r(drawable).mutate();
            if (this.f) {
                h0.a.o(drawable, this.d);
            }
            if (this.g) {
                h0.a.p(this.c, this.e);
            }
            if (this.c.isStateful()) {
                this.c.setState(this.getDrawableState());
            }
        }
    }

    public final void c() {
        Drawable drawable = this.h;
        if (drawable != null && (this.k || this.l)) {
            this.h = drawable = h0.a.r(drawable).mutate();
            if (this.k) {
                h0.a.o(drawable, this.i);
            }
            if (this.l) {
                h0.a.p(this.h, this.j);
            }
            if (this.h.isStateful()) {
                this.h.setState(this.getDrawableState());
            }
        }
    }

    public final void d() {
        ObjectAnimator objectAnimator = this.P;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n3;
        Drawable drawable;
        int n4;
        int n5;
        int n6;
        Rect rect;
        block4: {
            int n7;
            int n8;
            int n9;
            block7: {
                int n10;
                int n11;
                block6: {
                    block5: {
                        rect = this.T;
                        n9 = this.F;
                        n6 = this.G;
                        n11 = this.H;
                        n5 = this.I;
                        n4 = this.getThumbOffset() + n9;
                        drawable = this.c;
                        drawable = drawable != null ? androidx.appcompat.widget.z.d(drawable) : androidx.appcompat.widget.z.c;
                        Drawable drawable2 = this.h;
                        n3 = n4;
                        if (drawable2 == null) break block4;
                        drawable2.getPadding(rect);
                        n8 = rect.left;
                        n7 = n4 + n8;
                        if (drawable == null) break block5;
                        n4 = drawable.left;
                        n3 = n9;
                        if (n4 > n8) {
                            n3 = n9 + (n4 - n8);
                        }
                        n4 = (n8 = drawable.top) > (n4 = rect.top) ? n8 - n4 + n6 : n6;
                        n10 = drawable.right;
                        n9 = rect.right;
                        n8 = n11;
                        if (n10 > n9) {
                            n8 = n11 - (n10 - n9);
                        }
                        int n12 = drawable.bottom;
                        int n13 = rect.bottom;
                        n9 = n3;
                        n11 = n8;
                        n10 = n4;
                        if (n12 <= n13) break block6;
                        n9 = n5 - (n12 - n13);
                        break block7;
                    }
                    n10 = n6;
                }
                n4 = n5;
                n3 = n9;
                n8 = n11;
                n9 = n4;
                n4 = n10;
            }
            this.h.setBounds(n3, n4, n8, n9);
            n3 = n7;
        }
        if ((drawable = this.c) != null) {
            drawable.getPadding(rect);
            n4 = n3 - rect.left;
            n3 = n3 + this.E + rect.right;
            this.c.setBounds(n4, n6, n3, n5);
            drawable = this.getBackground();
            if (drawable != null) {
                h0.a.l(drawable, n4, n6, n3, n5);
            }
        }
        super.draw(canvas);
    }

    public void drawableHotspotChanged(float f3, float f4) {
        super.drawableHotspotChanged(f3, f4);
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.k(drawable, f3, f4);
        }
        if ((drawable = this.h) != null) {
            h0.a.k(drawable, f3, f4);
        }
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        Drawable drawable = this.c;
        boolean bl = drawable != null && drawable.isStateful() ? drawable.setState(nArray) : false;
        drawable = this.h;
        boolean bl2 = bl;
        if (drawable != null) {
            bl2 = bl;
            if (drawable.isStateful()) {
                bl2 = bl | drawable.setState(nArray);
            }
        }
        if (bl2) {
            this.invalidate();
        }
    }

    public final void e(MotionEvent motionEvent) {
        motionEvent = MotionEvent.obtain((MotionEvent)motionEvent);
        motionEvent.setAction(3);
        super.onTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    public final CharSequence g(CharSequence charSequence) {
        TransformationMethod transformationMethod = this.getEmojiTextViewHelper().f(this.O);
        CharSequence charSequence2 = charSequence;
        if (transformationMethod != null) {
            charSequence2 = transformationMethod.getTransformation(charSequence, (View)this);
        }
        return charSequence2;
    }

    public int getCompoundPaddingLeft() {
        int n3;
        if (!t0.b((View)this)) {
            return super.getCompoundPaddingLeft();
        }
        int n4 = n3 = super.getCompoundPaddingLeft() + this.C;
        if (!TextUtils.isEmpty((CharSequence)this.getText())) {
            n4 = n3 + this.o;
        }
        return n4;
    }

    public int getCompoundPaddingRight() {
        int n3;
        if (t0.b((View)this)) {
            return super.getCompoundPaddingRight();
        }
        int n4 = n3 = super.getCompoundPaddingRight() + this.C;
        if (!TextUtils.isEmpty((CharSequence)this.getText())) {
            n4 = n3 + this.o;
        }
        return n4;
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        return androidx.core.widget.j.o(super.getCustomSelectionActionModeCallback());
    }

    public boolean getShowText() {
        return this.u;
    }

    public boolean getSplitTrack() {
        return this.p;
    }

    public int getSwitchMinWidth() {
        return this.n;
    }

    public int getSwitchPadding() {
        return this.o;
    }

    public CharSequence getTextOff() {
        return this.s;
    }

    public CharSequence getTextOn() {
        return this.q;
    }

    public Drawable getThumbDrawable() {
        return this.c;
    }

    public final float getThumbPosition() {
        return this.B;
    }

    public int getThumbTextPadding() {
        return this.m;
    }

    public ColorStateList getThumbTintList() {
        return this.d;
    }

    public PorterDuff.Mode getThumbTintMode() {
        return this.e;
    }

    public Drawable getTrackDrawable() {
        return this.h;
    }

    public ColorStateList getTrackTintList() {
        return this.i;
    }

    public PorterDuff.Mode getTrackTintMode() {
        return this.j;
    }

    public final boolean h(float f3, float f4) {
        if (this.c == null) {
            return false;
        }
        int n3 = this.getThumbOffset();
        this.c.getPadding(this.T);
        int n4 = this.G;
        int n5 = this.w;
        int n6 = this.F + n3 - n5;
        int n7 = this.E;
        Rect rect = this.T;
        int n8 = rect.left;
        n3 = rect.right;
        int n9 = this.I;
        return f3 > (float)n6 && f3 < (float)(n7 + n6 + n8 + n3 + n5) && f4 > (float)(n4 - n5) && f4 < (float)(n9 + n5);
    }

    public final Layout i(CharSequence charSequence) {
        TextPaint textPaint = this.K;
        int n3 = charSequence != null ? (int)Math.ceil(Layout.getDesiredWidth((CharSequence)charSequence, (TextPaint)textPaint)) : 0;
        return new StaticLayout(charSequence, textPaint, n3, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    public void j() {
        this.setTextOnInternal(this.q);
        this.setTextOffInternal(this.s);
        this.requestLayout();
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        if ((drawable = this.h) != null) {
            drawable.jumpToCurrentState();
        }
        if ((drawable = this.P) != null && drawable.isStarted()) {
            this.P.end();
            this.P = null;
        }
    }

    public final void k() {
        if (Build.VERSION.SDK_INT >= 30) {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = this.s;
            if (charSequence == null) {
                charSequence2 = this.getResources().getString(c.h.abc_capital_off);
            }
            x0.v0((View)this, charSequence2);
        }
    }

    public final void l() {
        if (Build.VERSION.SDK_INT >= 30) {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = this.q;
            if (charSequence == null) {
                charSequence2 = this.getResources().getString(c.h.abc_capital_on);
            }
            x0.v0((View)this, charSequence2);
        }
    }

    public final void m(int n3, int n4) {
        Object object = n3 != 1 ? (n3 != 2 ? (n3 != 3 ? null : Typeface.MONOSPACE) : Typeface.SERIF) : Typeface.SANS_SERIF;
        this.setSwitchTypeface((Typeface)object, n4);
    }

    public final void n() {
        f f3;
        int n3;
        if (this.S == null && this.R.b() && androidx.emoji2.text.f.i() && ((n3 = (f3 = androidx.emoji2.text.f.c()).e()) == 3 || n3 == 0)) {
            b b3;
            this.S = b3 = new b(this);
            f3.t(b3);
        }
    }

    public final void o(MotionEvent motionEvent) {
        this.v = 0;
        int n3 = motionEvent.getAction();
        boolean bl = true;
        n3 = n3 == 1 && this.isEnabled() ? 1 : 0;
        boolean bl2 = this.isChecked();
        if (n3 != 0) {
            this.z.computeCurrentVelocity(1000);
            float f3 = this.z.getXVelocity();
            if (Math.abs(f3) > (float)this.A) {
                if (!(t0.b((View)this) ? f3 < 0.0f : f3 > 0.0f)) {
                    bl = false;
                }
            } else {
                bl = this.getTargetCheckedState();
            }
        } else {
            bl = bl2;
        }
        if (bl != bl2) {
            this.playSoundEffect(0);
        }
        this.setChecked(bl);
        this.e(motionEvent);
    }

    public int[] onCreateDrawableState(int n3) {
        int[] nArray = super.onCreateDrawableState(n3 + 1);
        if (this.isChecked()) {
            View.mergeDrawableStates((int[])nArray, (int[])V);
        }
        return nArray;
    }

    public void onDraw(Canvas canvas) {
        int n3;
        Rect rect;
        super.onDraw(canvas);
        Object object = this.T;
        Drawable drawable = this.h;
        if (drawable != null) {
            drawable.getPadding(object);
        } else {
            object.setEmpty();
        }
        int n4 = this.G;
        int n5 = this.I;
        int n6 = object.top;
        int n7 = object.bottom;
        Drawable drawable2 = this.c;
        if (drawable != null) {
            if (this.p && drawable2 != null) {
                rect = androidx.appcompat.widget.z.d(drawable2);
                drawable2.copyBounds(object);
                object.left += rect.left;
                object.right -= rect.right;
                n3 = canvas.save();
                canvas.clipRect(object, Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(n3);
            } else {
                drawable.draw(canvas);
            }
        }
        int n8 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        if ((drawable = this.getTargetCheckedState() ? this.M : this.N) != null) {
            object = this.getDrawableState();
            rect = this.L;
            if (rect != null) {
                this.K.setColor(rect.getColorForState((int[])object, 0));
            }
            this.K.drawableState = (int[])object;
            if (drawable2 != null) {
                drawable2 = drawable2.getBounds();
                n3 = drawable2.left + drawable2.right;
            } else {
                n3 = this.getWidth();
            }
            int n9 = n3 / 2;
            n3 = drawable.getWidth() / 2;
            n6 = (n4 + n6 + (n5 - n7)) / 2;
            n4 = drawable.getHeight() / 2;
            canvas.translate((float)(n9 - n3), (float)(n6 - n4));
            drawable.draw(canvas);
        }
        canvas.restoreToCount(n8);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)"android.widget.Switch");
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        CharSequence charSequence;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)"android.widget.Switch");
        if (Build.VERSION.SDK_INT < 30 && !TextUtils.isEmpty((CharSequence)(charSequence = this.isChecked() ? this.q : this.s))) {
            CharSequence charSequence2 = accessibilityNodeInfo.getText();
            if (TextUtils.isEmpty((CharSequence)charSequence2)) {
                accessibilityNodeInfo.setText(charSequence);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(charSequence2);
            stringBuilder.append(' ');
            stringBuilder.append(charSequence);
            accessibilityNodeInfo.setText((CharSequence)stringBuilder);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        block9: {
            block8: {
                block6: {
                    block7: {
                        super.onLayout(bl, n3, n4, n5, n6);
                        Drawable drawable = this.c;
                        n4 = 0;
                        if (drawable != null) {
                            drawable = this.T;
                            Drawable drawable2 = this.h;
                            if (drawable2 != null) {
                                drawable2.getPadding((Rect)drawable);
                            } else {
                                drawable.setEmpty();
                            }
                            drawable2 = androidx.appcompat.widget.z.d(this.c);
                            n3 = Math.max(0, drawable2.left - drawable.left);
                            n4 = Math.max(0, drawable2.right - drawable.right);
                        } else {
                            n3 = 0;
                        }
                        if (t0.b((View)this)) {
                            n5 = this.getPaddingLeft() + n3;
                            n3 = this.C + n5 - n3 - n4;
                            n4 = n5;
                            n5 = n3;
                        } else {
                            n5 = this.getWidth() - this.getPaddingRight() - n4;
                            n4 = n5 - this.C + n3 + n4;
                        }
                        n3 = this.getGravity() & 0x70;
                        if (n3 == 16) break block6;
                        if (n3 == 80) break block7;
                        n3 = this.getPaddingTop();
                        n6 = this.D;
                        break block8;
                    }
                    n6 = this.getHeight() - this.getPaddingBottom();
                    n3 = n6 - this.D;
                    break block9;
                }
                n3 = (this.getPaddingTop() + this.getHeight() - this.getPaddingBottom()) / 2;
                n6 = this.D;
                n3 -= n6 / 2;
            }
            n6 += n3;
        }
        this.F = n4;
        this.G = n3;
        this.I = n6;
        this.H = n5;
    }

    public void onMeasure(int n3, int n4) {
        int n5;
        int n6;
        if (this.u) {
            if (this.M == null) {
                this.M = this.i(this.r);
            }
            if (this.N == null) {
                this.N = this.i(this.t);
            }
        }
        Rect rect = this.T;
        Drawable drawable = this.c;
        int n7 = 0;
        if (drawable != null) {
            drawable.getPadding(rect);
            n6 = this.c.getIntrinsicWidth() - rect.left - rect.right;
            n5 = this.c.getIntrinsicHeight();
        } else {
            n6 = 0;
            n5 = 0;
        }
        int n8 = this.u ? Math.max(this.M.getWidth(), this.N.getWidth()) + this.m * 2 : 0;
        this.E = Math.max(n8, n6);
        drawable = this.h;
        if (drawable != null) {
            drawable.getPadding(rect);
            n6 = this.h.getIntrinsicHeight();
        } else {
            rect.setEmpty();
            n6 = n7;
        }
        int n9 = rect.left;
        int n10 = rect.right;
        rect = this.c;
        n7 = n10;
        n8 = n9;
        if (rect != null) {
            rect = androidx.appcompat.widget.z.d((Drawable)rect);
            n8 = Math.max(n9, rect.left);
            n7 = Math.max(n10, rect.right);
        }
        n8 = this.J ? Math.max(this.n, this.E * 2 + n8 + n7) : this.n;
        n5 = Math.max(n6, n5);
        this.C = n8;
        this.D = n5;
        super.onMeasure(n3, n4);
        if (this.getMeasuredHeight() < n5) {
            this.setMeasuredDimension(this.getMeasuredWidthAndState(), n5);
        }
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = this.isChecked() ? this.q : this.s;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        block11: {
            block8: {
                block9: {
                    int n3;
                    block10: {
                        this.z.addMovement(motionEvent);
                        n3 = motionEvent.getActionMasked();
                        if (n3 == 0) break block8;
                        if (n3 == 1) break block9;
                        if (n3 == 2) break block10;
                        if (n3 == 3) break block9;
                        break block11;
                    }
                    n3 = this.v;
                    if (n3 != 1) {
                        if (n3 == 2) {
                            float f3 = motionEvent.getX();
                            n3 = this.getThumbScrollRange();
                            float f4 = f3 - this.x;
                            f4 = n3 != 0 ? (f4 /= (float)n3) : (f4 > 0.0f ? 1.0f : -1.0f);
                            float f5 = f4;
                            if (t0.b((View)this)) {
                                f5 = -f4;
                            }
                            if ((f4 = SwitchCompat.f(this.B + f5, 0.0f, 1.0f)) != this.B) {
                                this.x = f3;
                                this.setThumbPosition(f4);
                            }
                            return true;
                        }
                    } else {
                        float f6 = motionEvent.getX();
                        float f7 = motionEvent.getY();
                        if (Math.abs(f6 - this.x) > (float)this.w || Math.abs(f7 - this.y) > (float)this.w) {
                            this.v = 2;
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                            this.x = f6;
                            this.y = f7;
                            return true;
                        }
                    }
                    break block11;
                }
                if (this.v == 2) {
                    this.o(motionEvent);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
                this.v = 0;
                this.z.clear();
                break block11;
            }
            float f8 = motionEvent.getX();
            float f9 = motionEvent.getY();
            if (this.isEnabled() && this.h(f8, f9)) {
                this.v = 1;
                this.x = f8;
                this.y = f9;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setAllCaps(boolean bl) {
        super.setAllCaps(bl);
        this.getEmojiTextViewHelper().d(bl);
    }

    public void setChecked(boolean bl) {
        super.setChecked(bl);
        bl = this.isChecked();
        if (bl) {
            this.l();
        } else {
            this.k();
        }
        if (this.getWindowToken() != null && this.isLaidOut()) {
            this.a(bl);
            return;
        }
        this.d();
        float f3 = bl ? 1.0f : 0.0f;
        this.setThumbPosition(f3);
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(androidx.core.widget.j.p((TextView)this, callback));
    }

    public void setEmojiCompatEnabled(boolean bl) {
        this.getEmojiTextViewHelper().e(bl);
        this.setTextOnInternal(this.q);
        this.setTextOffInternal(this.s);
        this.requestLayout();
    }

    public final void setEnforceSwitchWidth(boolean bl) {
        this.J = bl;
        this.invalidate();
    }

    public void setFilters(InputFilter[] inputFilterArray) {
        super.setFilters(this.getEmojiTextViewHelper().a(inputFilterArray));
    }

    public void setShowText(boolean bl) {
        if (this.u != bl) {
            this.u = bl;
            this.requestLayout();
            if (bl) {
                this.n();
            }
        }
    }

    public void setSplitTrack(boolean bl) {
        this.p = bl;
        this.invalidate();
    }

    public void setSwitchMinWidth(int n3) {
        this.n = n3;
        this.requestLayout();
    }

    public void setSwitchPadding(int n3) {
        this.o = n3;
        this.requestLayout();
    }

    public void setSwitchTextAppearance(Context context, int n3) {
        float f3;
        m0 m02 = m0.t(context, n3, c.j.TextAppearance);
        this.L = (context = m02.c(c.j.TextAppearance_android_textColor)) != null ? context : this.getTextColors();
        n3 = m02.f(c.j.TextAppearance_android_textSize, 0);
        if (n3 != 0 && (f3 = (float)n3) != this.K.getTextSize()) {
            this.K.setTextSize(f3);
            this.requestLayout();
        }
        this.m(m02.k(c.j.TextAppearance_android_typeface, -1), m02.k(c.j.TextAppearance_android_textStyle, -1));
        this.O = m02.a(c.j.TextAppearance_textAllCaps, false) ? new g.a(this.getContext()) : null;
        this.setTextOnInternal(this.q);
        this.setTextOffInternal(this.s);
        m02.x();
    }

    public void setSwitchTypeface(Typeface typeface) {
        if (this.K.getTypeface() != null && !this.K.getTypeface().equals((Object)typeface) || this.K.getTypeface() == null && typeface != null) {
            this.K.setTypeface(typeface);
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setSwitchTypeface(Typeface typeface, int n3) {
        float f3 = 0.0f;
        boolean bl = false;
        if (n3 > 0) {
            typeface = typeface == null ? Typeface.defaultFromStyle((int)n3) : Typeface.create((Typeface)typeface, (int)n3);
            this.setSwitchTypeface(typeface);
            int n4 = typeface != null ? typeface.getStyle() : 0;
            n3 = ~n4 & n3;
            typeface = this.K;
            if ((n3 & 1) != 0) {
                bl = true;
            }
            typeface.setFakeBoldText(bl);
            typeface = this.K;
            if ((n3 & 2) != 0) {
                f3 = -0.25f;
            }
            typeface.setTextSkewX(f3);
            return;
        }
        this.K.setFakeBoldText(false);
        this.K.setTextSkewX(0.0f);
        this.setSwitchTypeface(typeface);
    }

    public void setTextOff(CharSequence charSequence) {
        this.setTextOffInternal(charSequence);
        this.requestLayout();
        if (!this.isChecked()) {
            this.k();
        }
    }

    public void setTextOn(CharSequence charSequence) {
        this.setTextOnInternal(charSequence);
        this.requestLayout();
        if (this.isChecked()) {
            this.l();
        }
    }

    public void setThumbDrawable(Drawable drawable) {
        Drawable drawable2 = this.c;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.c = drawable;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this);
        }
        this.requestLayout();
    }

    public void setThumbPosition(float f3) {
        this.B = f3;
        this.invalidate();
    }

    public void setThumbResource(int n3) {
        this.setThumbDrawable(d.a.b(this.getContext(), n3));
    }

    public void setThumbTextPadding(int n3) {
        this.m = n3;
        this.requestLayout();
    }

    public void setThumbTintList(ColorStateList colorStateList) {
        this.d = colorStateList;
        this.f = true;
        this.b();
    }

    public void setThumbTintMode(PorterDuff.Mode mode) {
        this.e = mode;
        this.g = true;
        this.b();
    }

    public void setTrackDrawable(Drawable drawable) {
        Drawable drawable2 = this.h;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.h = drawable;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this);
        }
        this.requestLayout();
    }

    public void setTrackResource(int n3) {
        this.setTrackDrawable(d.a.b(this.getContext(), n3));
    }

    public void setTrackTintList(ColorStateList colorStateList) {
        this.i = colorStateList;
        this.k = true;
        this.c();
    }

    public void setTrackTintMode(PorterDuff.Mode mode) {
        this.j = mode;
        this.l = true;
        this.c();
    }

    public void toggle() {
        this.setChecked(this.isChecked() ^ true);
    }

    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.c || drawable == this.h;
        {
        }
    }

    public static class b
    extends f.f {
        public final Reference a;

        public b(SwitchCompat switchCompat) {
            this.a = new WeakReference<SwitchCompat>(switchCompat);
        }

        @Override
        public void a(Throwable object) {
            object = (SwitchCompat)((Object)this.a.get());
            if (object != null) {
                ((SwitchCompat)((Object)object)).j();
            }
        }

        @Override
        public void b() {
            SwitchCompat switchCompat = (SwitchCompat)((Object)this.a.get());
            if (switchCompat != null) {
                switchCompat.j();
            }
        }
    }
}

