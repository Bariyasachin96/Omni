/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.text.InputFilter
 *  android.util.AttributeSet
 *  android.view.ActionMode$Callback
 *  android.view.View
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputMethodManager
 *  android.view.textclassifier.TextClassifier
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.appcompat.app.s;
import androidx.appcompat.widget.i;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.j;
import androidx.appcompat.widget.j0;
import androidx.appcompat.widget.o;
import androidx.appcompat.widget.p;
import androidx.appcompat.widget.t0;
import g0.e;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import m0.l;

public class AppCompatTextView
extends TextView {
    public final androidx.appcompat.widget.d c;
    public final p d;
    public final o e;
    public i f;
    public boolean g = false;
    public a h = null;
    public Future i;

    public AppCompatTextView(Context context) {
        this(context, null);
    }

    public AppCompatTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public AppCompatTextView(Context object, AttributeSet attributeSet, int n3) {
        super(j0.b((Context)object), attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new androidx.appcompat.widget.d((View)this);
        this.c = object;
        ((androidx.appcompat.widget.d)object).e(attributeSet, n3);
        this.d = object = new p(this);
        ((p)object).m(attributeSet, n3);
        ((p)object).b();
        this.e = new o(this);
        this.getEmojiTextViewHelper().c(attributeSet, n3);
    }

    private i getEmojiTextViewHelper() {
        if (this.f == null) {
            this.f = new i(this);
        }
        return this.f;
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        Object object = this.c;
        if (object != null) {
            ((androidx.appcompat.widget.d)object).b();
        }
        if ((object = this.d) != null) {
            ((p)object).b();
        }
    }

    public int getAutoSizeMaxTextSize() {
        if (t0.c) {
            return this.getSuperCaller().f();
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.e();
        }
        return -1;
    }

    public int getAutoSizeMinTextSize() {
        if (t0.c) {
            return this.getSuperCaller().i();
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.f();
        }
        return -1;
    }

    public int getAutoSizeStepGranularity() {
        if (t0.c) {
            return this.getSuperCaller().k();
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.g();
        }
        return -1;
    }

    public int[] getAutoSizeTextAvailableSizes() {
        if (t0.c) {
            return this.getSuperCaller().c();
        }
        p p3 = this.d;
        if (p3 != null) {
            return p3.h();
        }
        return new int[0];
    }

    public int getAutoSizeTextType() {
        if (t0.c) {
            if (this.getSuperCaller().h() == 1) {
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
        return androidx.core.widget.j.o(super.getCustomSelectionActionModeCallback());
    }

    public int getFirstBaselineToTopHeight() {
        return androidx.core.widget.j.a(this);
    }

    public int getLastBaselineToBottomHeight() {
        return androidx.core.widget.j.b(this);
    }

    public a getSuperCaller() {
        if (this.h == null) {
            int n3 = Build.VERSION.SDK_INT;
            this.h = n3 >= 34 ? new d(this) : (n3 >= 28 ? new c(this) : new b(this));
        }
        return this.h;
    }

    public ColorStateList getSupportBackgroundTintList() {
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            return d3.c();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        androidx.appcompat.widget.d d3 = this.c;
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

    public CharSequence getText() {
        this.r();
        return super.getText();
    }

    public TextClassifier getTextClassifier() {
        o o3;
        if (Build.VERSION.SDK_INT < 28 && (o3 = this.e) != null) {
            return o3.a();
        }
        return this.getSuperCaller().e();
    }

    public l.a getTextMetricsParamsCompat() {
        return androidx.core.widget.j.e(this);
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnection = super.onCreateInputConnection(editorInfo);
        this.d.r(this, inputConnection, editorInfo);
        return j.a(inputConnection, editorInfo, (View)this);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int n3 = Build.VERSION.SDK_INT;
        if (n3 >= 30 && n3 < 33 && this.onCheckIsTextEditor()) {
            ((InputMethodManager)this.getContext().getSystemService("input_method")).isActive((View)this);
        }
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        p p3 = this.d;
        if (p3 != null) {
            p3.o(bl, n3, n4, n5, n6);
        }
    }

    public void onMeasure(int n3, int n4) {
        this.r();
        super.onMeasure(n3, n4);
    }

    public void onTextChanged(CharSequence object, int n3, int n4, int n5) {
        super.onTextChanged((CharSequence)object, n3, n4, n5);
        object = this.d;
        if (object != null && !t0.c && ((p)object).l()) {
            this.d.c();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void r() {
        Future future = this.i;
        if (future == null) return;
        try {
            this.i = null;
            s.a(future.get());
            androidx.core.widget.j.l(this, null);
            return;
        }
        catch (InterruptedException | ExecutionException exception) {
            return;
        }
    }

    public void setAllCaps(boolean bl) {
        super.setAllCaps(bl);
        this.getEmojiTextViewHelper().d(bl);
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int n3, int n4, int n5, int n6) {
        if (t0.c) {
            this.getSuperCaller().g(n3, n4, n5, n6);
            return;
        }
        p p3 = this.d;
        if (p3 != null) {
            p3.t(n3, n4, n5, n6);
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] nArray, int n3) {
        if (t0.c) {
            this.getSuperCaller().a(nArray, n3);
            return;
        }
        p p3 = this.d;
        if (p3 != null) {
            p3.u(nArray, n3);
        }
    }

    public void setAutoSizeTextTypeWithDefaults(int n3) {
        if (t0.c) {
            this.getSuperCaller().l(n3);
            return;
        }
        p p3 = this.d;
        if (p3 != null) {
            p3.v(n3);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            d3.f(drawable);
        }
    }

    public void setBackgroundResource(int n3) {
        super.setBackgroundResource(n3);
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            d3.g(n3);
        }
    }

    public void setCompoundDrawables(Drawable object, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        super.setCompoundDrawables((Drawable)object, drawable, drawable2, drawable3);
        object = this.d;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setCompoundDrawablesRelative(Drawable object, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        super.setCompoundDrawablesRelative((Drawable)object, drawable, drawable2, drawable3);
        object = this.d;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int n3, int n4, int n5, int n6) {
        Context context = this.getContext();
        Drawable drawable = null;
        Object object = n3 != 0 ? d.a.b(context, n3) : null;
        Drawable drawable2 = n4 != 0 ? d.a.b(context, n4) : null;
        Drawable drawable3 = n5 != 0 ? d.a.b(context, n5) : null;
        if (n6 != 0) {
            drawable = d.a.b(context, n6);
        }
        this.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable)object, drawable2, drawable3, drawable);
        object = this.d;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable object, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable)object, drawable, drawable2, drawable3);
        object = this.d;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int n3, int n4, int n5, int n6) {
        Context context = this.getContext();
        Drawable drawable = null;
        Object object = n3 != 0 ? d.a.b(context, n3) : null;
        Drawable drawable2 = n4 != 0 ? d.a.b(context, n4) : null;
        Drawable drawable3 = n5 != 0 ? d.a.b(context, n5) : null;
        if (n6 != 0) {
            drawable = d.a.b(context, n6);
        }
        this.setCompoundDrawablesWithIntrinsicBounds((Drawable)object, drawable2, drawable3, drawable);
        object = this.d;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable object, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        super.setCompoundDrawablesWithIntrinsicBounds((Drawable)object, drawable, drawable2, drawable3);
        object = this.d;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(androidx.core.widget.j.p(this, callback));
    }

    public void setEmojiCompatEnabled(boolean bl) {
        this.getEmojiTextViewHelper().e(bl);
    }

    public void setFilters(InputFilter[] inputFilterArray) {
        super.setFilters(this.getEmojiTextViewHelper().a(inputFilterArray));
    }

    public void setFirstBaselineToTopHeight(int n3) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.getSuperCaller().j(n3);
            return;
        }
        androidx.core.widget.j.h(this, n3);
    }

    public void setLastBaselineToBottomHeight(int n3) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.getSuperCaller().d(n3);
            return;
        }
        androidx.core.widget.j.i(this, n3);
    }

    public void setLineHeight(int n3) {
        androidx.core.widget.j.j(this, n3);
    }

    public void setLineHeight(int n3, float f3) {
        if (Build.VERSION.SDK_INT >= 34) {
            this.getSuperCaller().m(n3, f3);
            return;
        }
        androidx.core.widget.j.k(this, n3, f3);
    }

    public void setPrecomputedText(l l3) {
        androidx.core.widget.j.l(this, l3);
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            d3.i(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        androidx.appcompat.widget.d d3 = this.c;
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

    public void setTextClassifier(TextClassifier textClassifier) {
        o o3;
        if (Build.VERSION.SDK_INT < 28 && (o3 = this.e) != null) {
            o3.b(textClassifier);
            return;
        }
        this.getSuperCaller().b(textClassifier);
    }

    public void setTextFuture(Future<l> future) {
        this.i = future;
        if (future != null) {
            this.requestLayout();
        }
    }

    public void setTextMetricsParamsCompat(l.a a4) {
        androidx.core.widget.j.n(this, a4);
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

    public void setTypeface(Typeface typeface, int n3) {
        if (this.g) {
            return;
        }
        Typeface typeface2 = typeface != null && n3 > 0 ? g0.e.a(this.getContext(), typeface, n3) : null;
        this.g = true;
        if (typeface2 != null) {
            typeface = typeface2;
        }
        try {
            super.setTypeface(typeface, n3);
            return;
        }
        finally {
            this.g = false;
        }
    }

    public static interface a {
        public void a(int[] var1, int var2);

        public void b(TextClassifier var1);

        public int[] c();

        public void d(int var1);

        public TextClassifier e();

        public int f();

        public void g(int var1, int var2, int var3, int var4);

        public int h();

        public int i();

        public void j(int var1);

        public int k();

        public void l(int var1);

        public void m(int var1, float var2);
    }

    public class b
    implements a {
        public final AppCompatTextView a;

        public b(AppCompatTextView appCompatTextView) {
            this.a = appCompatTextView;
        }

        @Override
        public void a(int[] nArray, int n3) {
            AppCompatTextView.super.setAutoSizeTextTypeUniformWithPresetSizes(nArray, n3);
        }

        @Override
        public void b(TextClassifier textClassifier) {
            AppCompatTextView.super.setTextClassifier(textClassifier);
        }

        @Override
        public int[] c() {
            return AppCompatTextView.super.getAutoSizeTextAvailableSizes();
        }

        @Override
        public void d(int n3) {
        }

        @Override
        public TextClassifier e() {
            return AppCompatTextView.super.getTextClassifier();
        }

        @Override
        public int f() {
            return AppCompatTextView.super.getAutoSizeMaxTextSize();
        }

        @Override
        public void g(int n3, int n4, int n5, int n6) {
            AppCompatTextView.super.setAutoSizeTextTypeUniformWithConfiguration(n3, n4, n5, n6);
        }

        @Override
        public int h() {
            return AppCompatTextView.super.getAutoSizeTextType();
        }

        @Override
        public int i() {
            return AppCompatTextView.super.getAutoSizeMinTextSize();
        }

        @Override
        public void j(int n3) {
        }

        @Override
        public int k() {
            return AppCompatTextView.super.getAutoSizeStepGranularity();
        }

        @Override
        public void l(int n3) {
            AppCompatTextView.super.setAutoSizeTextTypeWithDefaults(n3);
        }

        @Override
        public void m(int n3, float f3) {
        }
    }

    public class c
    extends b {
        public final AppCompatTextView b;

        public c(AppCompatTextView appCompatTextView) {
            this.b = appCompatTextView;
            super(appCompatTextView);
        }

        @Override
        public void d(int n3) {
            AppCompatTextView.super.setLastBaselineToBottomHeight(n3);
        }

        @Override
        public void j(int n3) {
            AppCompatTextView.super.setFirstBaselineToTopHeight(n3);
        }
    }

    public class d
    extends c {
        public final AppCompatTextView c;

        public d(AppCompatTextView appCompatTextView) {
            this.c = appCompatTextView;
            super(appCompatTextView);
        }

        @Override
        public void m(int n3, float f3) {
            AppCompatTextView.super.setLineHeight(n3, f3);
        }
    }
}

