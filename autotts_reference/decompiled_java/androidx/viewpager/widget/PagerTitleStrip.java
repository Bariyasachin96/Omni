/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.DataSetObserver
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils$TruncateAt
 *  android.text.method.SingleLineTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package androidx.viewpager.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.s;
import androidx.core.widget.j;
import androidx.viewpager.widget.ViewPager;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Locale;

@ViewPager.e
public class PagerTitleStrip
extends ViewGroup {
    public static final int[] q = new int[]{16842804, 16842901, 16842904, 16842927};
    public static final int[] r = new int[]{16843660};
    public ViewPager c;
    public TextView d;
    public TextView e;
    public TextView f;
    public int g = -1;
    public float h = -1.0f;
    public int i;
    public int j;
    public boolean k;
    public boolean l;
    public final a m = new a(this);
    public WeakReference n;
    public int o;
    public int p;

    public PagerTitleStrip(Context context) {
        this(context, null);
    }

    public PagerTitleStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int n3;
        TextView textView;
        this.d = textView = new TextView(context);
        this.addView((View)textView);
        this.e = textView = new TextView(context);
        this.addView((View)textView);
        this.f = textView = new TextView(context);
        this.addView((View)textView);
        attributeSet = context.obtainStyledAttributes(attributeSet, q);
        boolean bl = false;
        int n4 = attributeSet.getResourceId(0, 0);
        if (n4 != 0) {
            androidx.core.widget.j.m(this.d, n4);
            androidx.core.widget.j.m(this.e, n4);
            androidx.core.widget.j.m(this.f, n4);
        }
        if ((n3 = attributeSet.getDimensionPixelSize(1, 0)) != 0) {
            this.setTextSize(0, n3);
        }
        if (attributeSet.hasValue(2)) {
            n3 = attributeSet.getColor(2, 0);
            this.d.setTextColor(n3);
            this.e.setTextColor(n3);
            this.f.setTextColor(n3);
        }
        this.j = attributeSet.getInteger(3, 80);
        attributeSet.recycle();
        this.p = this.e.getTextColors().getDefaultColor();
        this.setNonPrimaryAlpha(0.6f);
        textView = this.d;
        attributeSet = TextUtils.TruncateAt.END;
        textView.setEllipsize((TextUtils.TruncateAt)attributeSet);
        this.e.setEllipsize((TextUtils.TruncateAt)attributeSet);
        this.f.setEllipsize((TextUtils.TruncateAt)attributeSet);
        if (n4 != 0) {
            attributeSet = context.obtainStyledAttributes(n4, r);
            bl = attributeSet.getBoolean(0, false);
            attributeSet.recycle();
        }
        if (bl) {
            PagerTitleStrip.setSingleLineAllCaps(this.d);
            PagerTitleStrip.setSingleLineAllCaps(this.e);
            PagerTitleStrip.setSingleLineAllCaps(this.f);
        } else {
            this.d.setSingleLine();
            this.e.setSingleLine();
            this.f.setSingleLine();
        }
        this.i = (int)(context.getResources().getDisplayMetrics().density * 16.0f);
    }

    private static void setSingleLineAllCaps(TextView textView) {
        textView.setTransformationMethod((TransformationMethod)new b(textView.getContext()));
    }

    public void a(p1.a object, p1.a a4) {
        object = this.c;
        if (object != null) {
            this.g = -1;
            this.h = -1.0f;
            this.b(((ViewPager)((Object)object)).getCurrentItem(), a4);
            this.requestLayout();
        }
    }

    public void b(int n3, p1.a a4) {
        this.k = true;
        this.d.setText(null);
        this.e.setText(null);
        this.f.setText(null);
        int n4 = View.MeasureSpec.makeMeasureSpec((int)Math.max(0, (int)((float)(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()) * 0.8f)), (int)Integer.MIN_VALUE);
        int n5 = View.MeasureSpec.makeMeasureSpec((int)Math.max(0, this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()), (int)Integer.MIN_VALUE);
        this.d.measure(n4, n5);
        this.e.measure(n4, n5);
        this.f.measure(n4, n5);
        this.g = n3;
        if (!this.l) {
            this.c(n3, this.h, false);
        }
        this.k = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void c(int n3, float f3, boolean bl) {
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        block6: {
            int n13;
            int n14;
            block7: {
                int n15;
                int n16;
                block4: {
                    block5: {
                        float f4;
                        if (n3 != this.g) {
                            this.c.getAdapter();
                            this.b(n3, null);
                        } else if (!bl && f3 == this.h) {
                            return;
                        }
                        this.l = true;
                        n12 = this.d.getMeasuredWidth();
                        n11 = this.e.getMeasuredWidth();
                        n10 = this.f.getMeasuredWidth();
                        n9 = n11 / 2;
                        n8 = this.getWidth();
                        n3 = this.getHeight();
                        n7 = this.getPaddingLeft();
                        n6 = this.getPaddingRight();
                        n5 = this.getPaddingTop();
                        n16 = this.getPaddingBottom();
                        n14 = n6 + n9;
                        float f5 = f4 = 0.5f + f3;
                        if (f4 > 1.0f) {
                            f5 = f4 - 1.0f;
                        }
                        n4 = n8 - n14 - (int)((float)(n8 - (n7 + n9) - n14) * f5) - n9;
                        n9 = n11 + n4;
                        n13 = this.d.getBaseline();
                        n11 = this.e.getBaseline();
                        n14 = this.f.getBaseline();
                        int n17 = Math.max(Math.max(n13, n11), n14);
                        n13 = n17 - n13;
                        n11 = n17 - n11;
                        n14 = n17 - n14;
                        int n18 = this.d.getMeasuredHeight();
                        n17 = this.e.getMeasuredHeight();
                        n15 = this.f.getMeasuredHeight();
                        n15 = Math.max(Math.max(n18 + n13, n17 + n11), n15 + n14);
                        n17 = this.j & 0x70;
                        if (n17 == 16) break block4;
                        if (n17 == 80) break block5;
                        n3 = n13 + n5;
                        n11 += n5;
                        n5 += n14;
                        break block6;
                    }
                    n3 = n3 - n16 - n15;
                    break block7;
                }
                n3 = (n3 - n5 - n16 - n15) / 2;
            }
            n5 = n13 + n3;
            n11 += n3;
            n14 = n3 + n14;
            n3 = n5;
            n5 = n14;
        }
        TextView textView = this.e;
        textView.layout(n4, n11, n9, textView.getMeasuredHeight() + n11);
        n11 = Math.min(n7, n4 - this.i - n12);
        textView = this.d;
        textView.layout(n11, n3, n11 + n12, textView.getMeasuredHeight() + n3);
        n3 = Math.max(n8 - n6 - n10, n9 + this.i);
        textView = this.f;
        textView.layout(n3, n5, n3 + n10, textView.getMeasuredHeight() + n5);
        this.h = f3;
        this.l = false;
    }

    public int getMinHeight() {
        Drawable drawable = this.getBackground();
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return 0;
    }

    public int getTextSpacing() {
        return this.i;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object object = this.getParent();
        if (object instanceof ViewPager) {
            object = (ViewPager)((Object)object);
            ((ViewPager)((Object)object)).getAdapter();
            ((ViewPager)((Object)object)).I(this.m);
            ((ViewPager)((Object)object)).a(this.m);
            this.c = object;
            object = this.n;
            if (object != null) {
                s.a(((Reference)object).get());
            }
            this.a(null, null);
            return;
        }
        throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPager viewPager = this.c;
        if (viewPager != null) {
            viewPager.getAdapter();
            this.a(null, null);
            this.c.I(null);
            this.c.B(this.m);
            this.c = null;
        }
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        if (this.c != null) {
            float f3 = this.h;
            if (!(f3 >= 0.0f)) {
                f3 = 0.0f;
            }
            this.c(this.g, f3, true);
        }
    }

    public void onMeasure(int n3, int n4) {
        if (View.MeasureSpec.getMode((int)n3) == 0x40000000) {
            int n5 = this.getPaddingTop() + this.getPaddingBottom();
            int n6 = ViewGroup.getChildMeasureSpec((int)n4, (int)n5, (int)-2);
            int n7 = View.MeasureSpec.getSize((int)n3);
            n3 = ViewGroup.getChildMeasureSpec((int)n3, (int)((int)((float)n7 * 0.2f)), (int)-2);
            this.d.measure(n3, n6);
            this.e.measure(n3, n6);
            this.f.measure(n3, n6);
            if (View.MeasureSpec.getMode((int)n4) == 0x40000000) {
                n3 = View.MeasureSpec.getSize((int)n4);
            } else {
                n3 = this.e.getMeasuredHeight();
                n3 = Math.max(this.getMinHeight(), n3 + n5);
            }
            this.setMeasuredDimension(n7, View.resolveSizeAndState((int)n3, (int)n4, (int)(this.e.getMeasuredState() << 16)));
            return;
        }
        throw new IllegalStateException("Must measure with an exact width");
    }

    public void requestLayout() {
        if (!this.k) {
            super.requestLayout();
        }
    }

    public void setGravity(int n3) {
        this.j = n3;
        this.requestLayout();
    }

    public void setNonPrimaryAlpha(float f3) {
        int n3;
        this.o = n3 = (int)(f3 * 255.0f) & 0xFF;
        n3 = n3 << 24 | this.p & 0xFFFFFF;
        this.d.setTextColor(n3);
        this.f.setTextColor(n3);
    }

    public void setTextColor(int n3) {
        this.p = n3;
        this.e.setTextColor(n3);
        n3 = this.o << 24 | this.p & 0xFFFFFF;
        this.d.setTextColor(n3);
        this.f.setTextColor(n3);
    }

    public void setTextSize(int n3, float f3) {
        this.d.setTextSize(n3, f3);
        this.e.setTextSize(n3, f3);
        this.f.setTextSize(n3, f3);
    }

    public void setTextSpacing(int n3) {
        this.i = n3;
        this.requestLayout();
    }

    public class a
    extends DataSetObserver
    implements ViewPager.i,
    ViewPager.h {
        public int a;
        public final PagerTitleStrip b;

        public a(PagerTitleStrip pagerTitleStrip) {
            this.b = pagerTitleStrip;
        }

        @Override
        public void a(int n3, float f3, int n4) {
            n4 = n3;
            if (f3 > 0.5f) {
                n4 = n3 + 1;
            }
            this.b.c(n4, f3, false);
        }

        @Override
        public void b(int n3) {
            this.a = n3;
        }

        @Override
        public void c(int n3) {
            if (this.a == 0) {
                PagerTitleStrip pagerTitleStrip = this.b;
                n3 = pagerTitleStrip.c.getCurrentItem();
                this.b.c.getAdapter();
                pagerTitleStrip.b(n3, null);
                pagerTitleStrip = this.b;
                float f3 = pagerTitleStrip.h;
                if (!(f3 >= 0.0f)) {
                    f3 = 0.0f;
                }
                pagerTitleStrip.c(pagerTitleStrip.c.getCurrentItem(), f3, true);
            }
        }

        @Override
        public void d(ViewPager viewPager, p1.a a4, p1.a a5) {
            this.b.a(a4, a5);
        }

        public void onChanged() {
            PagerTitleStrip pagerTitleStrip = this.b;
            int n3 = pagerTitleStrip.c.getCurrentItem();
            this.b.c.getAdapter();
            pagerTitleStrip.b(n3, null);
            pagerTitleStrip = this.b;
            float f3 = pagerTitleStrip.h;
            if (!(f3 >= 0.0f)) {
                f3 = 0.0f;
            }
            pagerTitleStrip.c(pagerTitleStrip.c.getCurrentItem(), f3, true);
        }
    }

    public static class b
    extends SingleLineTransformationMethod {
        public Locale c;

        public b(Context context) {
            this.c = context.getResources().getConfiguration().locale;
        }

        public CharSequence getTransformation(CharSequence charSequence, View view) {
            if ((charSequence = super.getTransformation(charSequence, view)) != null) {
                return charSequence.toString().toUpperCase(this.c);
            }
            return null;
        }
    }
}

