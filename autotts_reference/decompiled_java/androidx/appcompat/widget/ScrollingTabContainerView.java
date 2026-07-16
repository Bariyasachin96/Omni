/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.AbsListView$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.BaseAdapter
 *  android.widget.HorizontalScrollView
 *  android.widget.LinearLayout
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 */
package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.s;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.m0;
import c.a;

public class ScrollingTabContainerView
extends HorizontalScrollView
implements AdapterView.OnItemSelectedListener {
    public static final Interpolator n = new DecelerateInterpolator();
    public Runnable c;
    public c d;
    public LinearLayoutCompat e;
    public Spinner f;
    public boolean g;
    public int h;
    public int i;
    public int j;
    public int k;
    public ViewPropertyAnimator l;
    public final e m = new e(this);

    public ScrollingTabContainerView(Context object) {
        super(object);
        this.setHorizontalScrollBarEnabled(false);
        object = h.a.b(object);
        this.setContentHeight(object.f());
        this.i = object.e();
        object = this.c();
        this.e = object;
        this.addView((View)object, new ViewGroup.LayoutParams(-2, -1));
    }

    public void a(int n3) {
        View view = this.e.getChildAt(n3);
        Runnable runnable = this.c;
        if (runnable != null) {
            this.removeCallbacks(runnable);
        }
        this.c = runnable = new Runnable(this, view){
            public final View c;
            public final ScrollingTabContainerView d;
            {
                this.d = scrollingTabContainerView;
                this.c = view;
            }

            @Override
            public void run() {
                int n3 = this.c.getLeft();
                int n4 = (this.d.getWidth() - this.c.getWidth()) / 2;
                this.d.smoothScrollTo(n3 - n4, 0);
                this.d.c = null;
            }
        };
        this.post(runnable);
    }

    public final Spinner b() {
        AppCompatSpinner appCompatSpinner = new AppCompatSpinner(this.getContext(), null, a.actionDropDownStyle);
        appCompatSpinner.setLayoutParams((ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(-2, -1));
        appCompatSpinner.setOnItemSelectedListener(this);
        return appCompatSpinner;
    }

    public final LinearLayoutCompat c() {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this.getContext(), null, a.actionBarTabBarStyle);
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true);
        linearLayoutCompat.setGravity(17);
        linearLayoutCompat.setLayoutParams((ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(-2, -1));
        return linearLayoutCompat;
    }

    public d d(ActionBar.a object, boolean bl) {
        object = new d(this, this.getContext(), (ActionBar.a)object, bl);
        if (bl) {
            object.setBackgroundDrawable(null);
            object.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, this.j));
            return object;
        }
        object.setFocusable(true);
        if (this.d == null) {
            this.d = new c(this);
        }
        object.setOnClickListener(this.d);
        return object;
    }

    public final boolean e() {
        Spinner spinner = this.f;
        return spinner != null && spinner.getParent() == this;
    }

    public final void f() {
        Runnable runnable;
        if (this.e()) {
            return;
        }
        if (this.f == null) {
            this.f = this.b();
        }
        this.removeView((View)this.e);
        this.addView((View)this.f, new ViewGroup.LayoutParams(-2, -1));
        if (this.f.getAdapter() == null) {
            this.f.setAdapter((SpinnerAdapter)new b(this));
        }
        if ((runnable = this.c) != null) {
            this.removeCallbacks(runnable);
            this.c = null;
        }
        this.f.setSelection(this.k);
    }

    public final boolean g() {
        if (!this.e()) {
            return false;
        }
        this.removeView((View)this.f);
        this.addView((View)this.e, new ViewGroup.LayoutParams(-2, -1));
        this.setTabSelected(this.f.getSelectedItemPosition());
        return false;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable = this.c;
        if (runnable != null) {
            this.post(runnable);
        }
    }

    public void onConfigurationChanged(Configuration object) {
        super.onConfigurationChanged((Configuration)object);
        object = h.a.b(this.getContext());
        this.setContentHeight(((h.a)object).f());
        this.i = ((h.a)object).e();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.c;
        if (runnable != null) {
            this.removeCallbacks(runnable);
        }
    }

    public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
        ((d)view).b();
        throw null;
    }

    public void onMeasure(int n3, int n4) {
        int n5 = View.MeasureSpec.getMode((int)n3);
        boolean bl = n5 == 0x40000000;
        this.setFillViewport(bl);
        n4 = this.e.getChildCount();
        if (n4 > 1 && (n5 == 0x40000000 || n5 == Integer.MIN_VALUE)) {
            this.h = n4 > 2 ? (int)((float)View.MeasureSpec.getSize((int)n3) * 0.4f) : View.MeasureSpec.getSize((int)n3) / 2;
            this.h = Math.min(this.h, this.i);
        } else {
            this.h = -1;
        }
        n5 = View.MeasureSpec.makeMeasureSpec((int)this.j, (int)0x40000000);
        if (!bl && this.g) {
            this.e.measure(0, n5);
            if (this.e.getMeasuredWidth() > View.MeasureSpec.getSize((int)n3)) {
                this.f();
            } else {
                this.g();
            }
        } else {
            this.g();
        }
        n4 = this.getMeasuredWidth();
        super.onMeasure(n3, n5);
        n3 = this.getMeasuredWidth();
        if (bl && n4 != n3) {
            this.setTabSelected(this.k);
        }
    }

    public void onNothingSelected(AdapterView adapterView) {
    }

    public void setAllowCollapse(boolean bl) {
        this.g = bl;
    }

    public void setContentHeight(int n3) {
        this.j = n3;
        this.requestLayout();
    }

    public void setTabSelected(int n3) {
        Spinner spinner;
        this.k = n3;
        int n4 = this.e.getChildCount();
        for (int i3 = 0; i3 < n4; ++i3) {
            spinner = this.e.getChildAt(i3);
            boolean bl = i3 == n3;
            spinner.setSelected(bl);
            if (!bl) continue;
            this.a(n3);
        }
        spinner = this.f;
        if (spinner != null && n3 >= 0) {
            spinner.setSelection(n3);
        }
    }

    public class b
    extends BaseAdapter {
        public final ScrollingTabContainerView c;

        public b(ScrollingTabContainerView scrollingTabContainerView) {
            this.c = scrollingTabContainerView;
        }

        public int getCount() {
            return this.c.e.getChildCount();
        }

        public Object getItem(int n3) {
            ((d)this.c.e.getChildAt(n3)).b();
            return null;
        }

        public long getItemId(int n3) {
            return n3;
        }

        public View getView(int n3, View object, ViewGroup object2) {
            if (object == null) {
                object = this.c;
                s.a(this.getItem(n3));
                return ((ScrollingTabContainerView)((Object)object)).d(null, true);
            }
            object2 = (d)((Object)object);
            s.a(this.getItem(n3));
            ((d)((Object)object2)).a(null);
            return object;
        }
    }

    public class c
    implements View.OnClickListener {
        public final ScrollingTabContainerView c;

        public c(ScrollingTabContainerView scrollingTabContainerView) {
            this.c = scrollingTabContainerView;
        }

        public void onClick(View view) {
            ((d)view).b();
            throw null;
        }
    }

    public class d
    extends LinearLayout {
        public final int[] c;
        public final ScrollingTabContainerView d;

        public d(ScrollingTabContainerView object, Context context, ActionBar.a a4, boolean bl) {
            this.d = object;
            int n3 = a.actionBarTabStyle;
            super(context, null, n3);
            object = new int[1];
            object[0] = (ScrollingTabContainerView)16842964;
            this.c = (int[])object;
            object = m0.v(context, null, (int[])object, n3, 0);
            if (((m0)object).s(0)) {
                this.setBackgroundDrawable(((m0)object).g(0));
            }
            ((m0)object).x();
            if (bl) {
                this.setGravity(8388627);
            }
            this.c();
        }

        public void a(ActionBar.a a4) {
            this.c();
        }

        public ActionBar.a b() {
            return null;
        }

        public void c() {
            throw null;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)"androidx.appcompat.app.ActionBar$Tab");
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName((CharSequence)"androidx.appcompat.app.ActionBar$Tab");
        }

        public void onMeasure(int n3, int n4) {
            int n5;
            super.onMeasure(n3, n4);
            if (this.d.h > 0 && (n3 = this.getMeasuredWidth()) > (n5 = this.d.h)) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n5, (int)0x40000000), n4);
            }
        }

        public void setSelected(boolean bl) {
            boolean bl2 = this.isSelected() != bl;
            super.setSelected(bl);
            if (bl2 && bl) {
                this.sendAccessibilityEvent(4);
            }
        }
    }

    public class e
    extends AnimatorListenerAdapter {
        public boolean a;
        public int b;
        public final ScrollingTabContainerView c;

        public e(ScrollingTabContainerView scrollingTabContainerView) {
            this.c = scrollingTabContainerView;
            this.a = false;
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator object) {
            if (this.a) {
                return;
            }
            object = this.c;
            object.l = null;
            object.setVisibility(this.b);
        }

        public void onAnimationStart(Animator animator) {
            this.c.setVisibility(0);
            this.a = false;
        }
    }
}

