/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.os.Build$VERSION
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.StaticLayout$Builder
 *  android.text.TextDirectionHeuristic
 *  android.text.TextDirectionHeuristics
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.View
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.r;
import androidx.appcompat.widget.s;
import c.j;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import o0.x0;

public class q {
    public static final RectF l = new RectF();
    public static ConcurrentHashMap m = new ConcurrentHashMap();
    public int a = 0;
    public boolean b = false;
    public float c = -1.0f;
    public float d = -1.0f;
    public float e = -1.0f;
    public int[] f = new int[0];
    public boolean g = false;
    public TextPaint h;
    public final TextView i;
    public final Context j;
    public final d k;

    public q(TextView textView) {
        this.i = textView;
        this.j = textView.getContext();
        if (Build.VERSION.SDK_INT >= 29) {
            this.k = new c();
            return;
        }
        this.k = new b();
    }

    public static Method k(String string) {
        Exception exception2;
        Object object;
        block5: {
            Object object2;
            block4: {
                try {
                    object2 = object = (Method)m.get(string);
                    if (object != null) break block4;
                }
                catch (Exception exception2) {}
                object2 = object = TextView.class.getDeclaredMethod(string, null);
                if (object == null) break block4;
                ((AccessibleObject)object).setAccessible(true);
                m.put(string, object);
                return object;
                break block5;
            }
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to retrieve TextView#");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("() method");
        Log.w((String)"ACTVAutoSizeHelper", (String)((StringBuilder)object).toString(), (Throwable)exception2);
        return null;
    }

    public static Object m(Object object, String string, Object object2) {
        try {
            object = q.k(string).invoke(object, null);
            return object;
        }
        catch (Exception exception) {
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to invoke TextView#");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("() method");
        Log.w((String)"ACTVAutoSizeHelper", (String)((StringBuilder)object).toString(), (Throwable)exception);
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a() {
        block9: {
            if (!this.n()) {
                return;
            }
            if (this.b) {
                if (this.i.getMeasuredHeight() <= 0) return;
                if (this.i.getMeasuredWidth() <= 0) {
                    return;
                }
                int n3 = this.k.b(this.i) ? 0x100000 : this.i.getMeasuredWidth() - this.i.getTotalPaddingLeft() - this.i.getTotalPaddingRight();
                int n4 = this.i.getHeight() - this.i.getCompoundPaddingBottom() - this.i.getCompoundPaddingTop();
                if (n3 <= 0) return;
                if (n4 <= 0) {
                    return;
                }
                RectF rectF = l;
                synchronized (rectF) {
                    Throwable throwable2;
                    block8: {
                        block7: {
                            try {
                                rectF.setEmpty();
                                rectF.right = n3;
                                rectF.bottom = n4;
                                float f3 = this.e(rectF);
                                if (f3 == this.i.getTextSize()) break block7;
                                this.t(0, f3);
                            }
                            catch (Throwable throwable2) {
                                break block8;
                            }
                        }
                        break block9;
                    }
                    throw throwable2;
                }
            }
        }
        this.b = true;
    }

    public final int[] b(int[] nArray) {
        int n3;
        ArrayList<Integer> arrayList;
        int n4;
        block5: {
            block4: {
                n4 = nArray.length;
                if (n4 == 0) break block4;
                Arrays.sort(nArray);
                arrayList = new ArrayList<Integer>();
                int n5 = 0;
                for (n3 = 0; n3 < n4; ++n3) {
                    int n6 = nArray[n3];
                    if (n6 <= 0 || Collections.binarySearch(arrayList, n6) >= 0) continue;
                    arrayList.add(n6);
                }
                if (n4 != arrayList.size()) break block5;
            }
            return nArray;
        }
        n4 = arrayList.size();
        nArray = new int[n4];
        for (n3 = n5; n3 < n4; ++n3) {
            nArray[n3] = (Integer)arrayList.get(n3);
        }
        return nArray;
    }

    public final void c() {
        this.a = 0;
        this.d = -1.0f;
        this.e = -1.0f;
        this.c = -1.0f;
        this.f = new int[0];
        this.b = false;
    }

    public StaticLayout d(CharSequence charSequence, Layout.Alignment alignment, int n3, int n4) {
        return androidx.appcompat.widget.q$a.a(charSequence, alignment, n3, n4, this.i, this.h, this.k);
    }

    public final int e(RectF rectF) {
        int n3 = this.f.length;
        if (n3 != 0) {
            int n4 = 1;
            int n5 = n3 - 1;
            n3 = 0;
            while (n4 <= n5) {
                int n6 = (n4 + n5) / 2;
                if (this.x(this.f[n6], rectF)) {
                    n3 = n4;
                    n4 = n6 + 1;
                    continue;
                }
                n5 = n3 = n6 - 1;
            }
            return this.f[n3];
        }
        throw new IllegalStateException("No available text sizes to choose from.");
    }

    public int f() {
        return Math.round(this.e);
    }

    public int g() {
        return Math.round(this.d);
    }

    public int h() {
        return Math.round(this.c);
    }

    public int[] i() {
        return this.f;
    }

    public int j() {
        return this.a;
    }

    public void l(int n3) {
        TextPaint textPaint = this.h;
        if (textPaint == null) {
            this.h = new TextPaint();
        } else {
            textPaint.reset();
        }
        this.h.set(this.i.getPaint());
        this.h.setTextSize((float)n3);
    }

    public boolean n() {
        return this.y() && this.a != 0;
    }

    public void o(AttributeSet attributeSet, int n3) {
        Context context = this.j;
        int[] nArray = c.j.AppCompatTextView;
        context = context.obtainStyledAttributes(attributeSet, nArray, n3, 0);
        TextView textView = this.i;
        x0.f0((View)textView, textView.getContext(), nArray, attributeSet, (TypedArray)context, n3, 0);
        n3 = c.j.AppCompatTextView_autoSizeTextType;
        if (context.hasValue(n3)) {
            this.a = context.getInt(n3, 0);
        }
        float f3 = context.hasValue(n3 = c.j.AppCompatTextView_autoSizeStepGranularity) ? context.getDimension(n3, -1.0f) : -1.0f;
        n3 = c.j.AppCompatTextView_autoSizeMinTextSize;
        float f4 = context.hasValue(n3) ? context.getDimension(n3, -1.0f) : -1.0f;
        n3 = c.j.AppCompatTextView_autoSizeMaxTextSize;
        float f5 = context.hasValue(n3) ? context.getDimension(n3, -1.0f) : -1.0f;
        n3 = c.j.AppCompatTextView_autoSizePresetSizes;
        if (context.hasValue(n3) && (n3 = context.getResourceId(n3, 0)) > 0) {
            attributeSet = context.getResources().obtainTypedArray(n3);
            this.v((TypedArray)attributeSet);
            attributeSet.recycle();
        }
        context.recycle();
        if (this.y()) {
            if (this.a == 1) {
                if (!this.g) {
                    attributeSet = this.j.getResources().getDisplayMetrics();
                    float f6 = f4;
                    if (f4 == -1.0f) {
                        f6 = TypedValue.applyDimension((int)2, (float)12.0f, (DisplayMetrics)attributeSet);
                    }
                    f4 = f5;
                    if (f5 == -1.0f) {
                        f4 = TypedValue.applyDimension((int)2, (float)112.0f, (DisplayMetrics)attributeSet);
                    }
                    f5 = f3;
                    if (f3 == -1.0f) {
                        f5 = 1.0f;
                    }
                    this.z(f6, f4, f5);
                }
                this.u();
            }
            return;
        }
        this.a = 0;
    }

    public void p(int n3, int n4, int n5, int n6) {
        if (this.y()) {
            DisplayMetrics displayMetrics = this.j.getResources().getDisplayMetrics();
            this.z(TypedValue.applyDimension((int)n6, (float)n3, (DisplayMetrics)displayMetrics), TypedValue.applyDimension((int)n6, (float)n4, (DisplayMetrics)displayMetrics), TypedValue.applyDimension((int)n6, (float)n5, (DisplayMetrics)displayMetrics));
            if (this.u()) {
                this.a();
            }
        }
    }

    public void q(int[] nArray, int n3) {
        if (this.y()) {
            int n4 = nArray.length;
            int n5 = 0;
            if (n4 > 0) {
                Object object;
                int[] nArray2 = new int[n4];
                if (n3 == 0) {
                    object = Arrays.copyOf(nArray, n4);
                } else {
                    DisplayMetrics displayMetrics = this.j.getResources().getDisplayMetrics();
                    while (true) {
                        object = nArray2;
                        if (n5 >= n4) break;
                        nArray2[n5] = Math.round(TypedValue.applyDimension((int)n3, (float)nArray[n5], (DisplayMetrics)displayMetrics));
                        ++n5;
                    }
                }
                this.f = this.b((int[])object);
                if (!this.w()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("None of the preset sizes is valid: ");
                    ((StringBuilder)object).append(Arrays.toString(nArray));
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            } else {
                this.g = false;
            }
            if (this.u()) {
                this.a();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void r(int n3) {
        if (!this.y()) return;
        if (n3 != 0) {
            if (n3 == 1) {
                DisplayMetrics displayMetrics = this.j.getResources().getDisplayMetrics();
                this.z(TypedValue.applyDimension((int)2, (float)12.0f, (DisplayMetrics)displayMetrics), TypedValue.applyDimension((int)2, (float)112.0f, (DisplayMetrics)displayMetrics), 1.0f);
                if (!this.u()) return;
                this.a();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown auto-size text type: ");
            stringBuilder.append(n3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.c();
    }

    public final void s(float f3) {
        if (f3 != this.i.getPaint().getTextSize()) {
            this.i.getPaint().setTextSize(f3);
            boolean bl = this.i.isInLayout();
            if (this.i.getLayout() != null) {
                block7: {
                    this.b = false;
                    Method method = q.k("nullLayouts");
                    if (method == null) break block7;
                    try {
                        method.invoke((Object)this.i, null);
                    }
                    catch (Exception exception) {
                        Log.w((String)"ACTVAutoSizeHelper", (String)"Failed to invoke TextView#nullLayouts() method", (Throwable)exception);
                    }
                }
                if (!bl) {
                    this.i.requestLayout();
                } else {
                    this.i.forceLayout();
                }
                this.i.invalidate();
            }
        }
    }

    public void t(int n3, float f3) {
        Context context = this.j;
        context = context == null ? Resources.getSystem() : context.getResources();
        this.s(TypedValue.applyDimension((int)n3, (float)f3, (DisplayMetrics)context.getDisplayMetrics()));
    }

    public final boolean u() {
        boolean bl = this.y();
        if (bl && this.a == 1) {
            if (!this.g || this.f.length == 0) {
                int n3 = (int)Math.floor((this.e - this.d) / this.c) + 1;
                int[] nArray = new int[n3];
                for (int i3 = 0; i3 < n3; ++i3) {
                    nArray[i3] = Math.round(this.d + (float)i3 * this.c);
                }
                this.f = this.b(nArray);
            }
            this.b = true;
        } else {
            this.b = false;
        }
        return this.b;
    }

    public final void v(TypedArray typedArray) {
        int n3 = typedArray.length();
        int[] nArray = new int[n3];
        if (n3 > 0) {
            for (int i3 = 0; i3 < n3; ++i3) {
                nArray[i3] = typedArray.getDimensionPixelSize(i3, -1);
            }
            this.f = this.b(nArray);
            this.w();
        }
    }

    public final boolean w() {
        int[] nArray = this.f;
        int n3 = nArray.length;
        boolean bl = n3 > 0;
        this.g = bl;
        if (bl) {
            this.a = 1;
            this.d = nArray[0];
            this.e = nArray[n3 - 1];
            this.c = -1.0f;
        }
        return bl;
    }

    public final boolean x(int n3, RectF rectF) {
        CharSequence charSequence = this.i.getText();
        Object object = this.i.getTransformationMethod();
        CharSequence charSequence2 = charSequence;
        if (object != null) {
            object = object.getTransformation(charSequence, (View)this.i);
            charSequence2 = charSequence;
            if (object != null) {
                charSequence2 = object;
            }
        }
        int n4 = this.i.getMaxLines();
        this.l(n3);
        charSequence = this.d(charSequence2, (Layout.Alignment)q.m(this.i, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL), Math.round(rectF.right), n4);
        if (n4 != -1 && (charSequence.getLineCount() > n4 || charSequence.getLineEnd(charSequence.getLineCount() - 1) != charSequence2.length())) {
            return false;
        }
        return !((float)charSequence.getHeight() > rectF.bottom);
    }

    public final boolean y() {
        return this.i instanceof AppCompatEditText ^ true;
    }

    public final void z(float f3, float f4, float f5) {
        if (!(f3 <= 0.0f)) {
            if (!(f4 <= f3)) {
                if (!(f5 <= 0.0f)) {
                    this.a = 1;
                    this.d = f3;
                    this.e = f4;
                    this.c = f5;
                    this.g = false;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The auto-size step granularity (");
                stringBuilder.append(f5);
                stringBuilder.append("px) is less or equal to (0px)");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Maximum auto-size text size (");
            stringBuilder.append(f4);
            stringBuilder.append("px) is less or equal to minimum auto-size text size (");
            stringBuilder.append(f3);
            stringBuilder.append("px)");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Minimum auto-size text size (");
        stringBuilder.append(f3);
        stringBuilder.append("px) is less or equal to (0px)");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static final abstract class a {
        public static StaticLayout a(CharSequence charSequence, Layout.Alignment alignment, int n3, int n4, TextView textView, TextPaint textPaint, d d3) {
            charSequence = StaticLayout.Builder.obtain((CharSequence)charSequence, (int)0, (int)charSequence.length(), (TextPaint)textPaint, (int)n3);
            alignment = charSequence.setAlignment(alignment).setLineSpacing(textView.getLineSpacingExtra(), textView.getLineSpacingMultiplier()).setIncludePad(textView.getIncludeFontPadding()).setBreakStrategy(textView.getBreakStrategy()).setHyphenationFrequency(textView.getHyphenationFrequency());
            n3 = n4;
            if (n4 == -1) {
                n3 = Integer.MAX_VALUE;
            }
            alignment.setMaxLines(n3);
            try {
                d3.a((StaticLayout.Builder)charSequence, textView);
            }
            catch (ClassCastException classCastException) {
                Log.w((String)"ACTVAutoSizeHelper", (String)"Failed to obtain TextDirectionHeuristic, auto size may be incorrect");
            }
            return charSequence.build();
        }
    }

    public static class b
    extends d {
        @Override
        public void a(StaticLayout.Builder builder, TextView textView) {
            builder.setTextDirection((TextDirectionHeuristic)q.m(textView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR));
        }
    }

    public static class c
    extends b {
        @Override
        public void a(StaticLayout.Builder builder, TextView textView) {
            builder.setTextDirection(r.a(textView));
        }

        @Override
        public boolean b(TextView textView) {
            return s.a(textView);
        }
    }

    public static abstract class d {
        public abstract void a(StaticLayout.Builder var1, TextView var2);

        public boolean b(TextView textView) {
            return (Boolean)q.m(textView, "getHorizontallyScrolling", Boolean.FALSE);
        }
    }
}

