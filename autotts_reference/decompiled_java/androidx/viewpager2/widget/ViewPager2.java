/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.Gravity
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package androidx.viewpager2.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.b;
import androidx.viewpager2.widget.c;
import androidx.viewpager2.widget.d;
import o0.x0;
import p0.s;
import p0.v;

public final class ViewPager2
extends ViewGroup {
    public static boolean w = true;
    public final Rect c = new Rect();
    public final Rect d = new Rect();
    public b e = new b(3);
    public int f;
    public boolean g = false;
    public RecyclerView.j h = new g(this){
        public final ViewPager2 a;
        {
            this.a = viewPager2;
            super(null);
        }

        @Override
        public void a() {
            ViewPager2 viewPager2 = this.a;
            viewPager2.g = true;
            viewPager2.n.l();
        }
    };
    public LinearLayoutManager i;
    public int j = -1;
    public Parcelable k;
    public RecyclerView l;
    public androidx.recyclerview.widget.j m;
    public androidx.viewpager2.widget.e n;
    public b o;
    public c p;
    public d q;
    public RecyclerView.m r = null;
    public boolean s = false;
    public boolean t = true;
    public int u = -1;
    public e v;

    public ViewPager2(Context context) {
        super(context);
        this.b(context, null);
    }

    public ViewPager2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.b(context, attributeSet);
    }

    public ViewPager2(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.b(context, attributeSet);
    }

    public final RecyclerView.q a() {
        return new RecyclerView.q(this){
            public final ViewPager2 a;
            {
                this.a = viewPager2;
            }

            @Override
            public void a(View object) {
                object = (RecyclerView.LayoutParams)object.getLayoutParams();
                if (object.width == -1 && object.height == -1) {
                    return;
                }
                throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
            }

            @Override
            public void b(View view) {
            }
        };
    }

    public final void b(Context object, AttributeSet object2) {
        Object object3 = w ? new j(this) : new f(this);
        this.v = object3;
        this.l = object3 = new m(this, (Context)object);
        object3.setId(View.generateViewId());
        this.l.setDescendantFocusability(131072);
        this.i = object3 = new h(this, (Context)object);
        this.l.setLayoutManager((RecyclerView.p)object3);
        this.l.setScrollingTouchSlop(1);
        this.k((Context)object, (AttributeSet)object2);
        this.l.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.l.l(this.a());
        object = new androidx.viewpager2.widget.e(this);
        this.n = object;
        this.p = new c(this, (androidx.viewpager2.widget.e)object, this.l);
        object = new l(this);
        this.m = object;
        ((androidx.recyclerview.widget.n)object).b(this.l);
        this.l.n(this.n);
        this.o = object = new b(3);
        this.n.o((i)object);
        object = new i(this){
            public final ViewPager2 a;
            {
                this.a = viewPager2;
            }

            @Override
            public void a(int n3) {
                if (n3 == 0) {
                    this.a.n();
                }
            }

            @Override
            public void c(int n3) {
                ViewPager2 viewPager2 = this.a;
                if (viewPager2.f != n3) {
                    viewPager2.f = n3;
                    viewPager2.v.r();
                }
            }
        };
        object2 = new i(this){
            public final ViewPager2 a;
            {
                this.a = viewPager2;
            }

            @Override
            public void c(int n3) {
                this.a.clearFocus();
                if (this.a.hasFocus()) {
                    this.a.l.requestFocus(2);
                }
            }
        };
        this.o.d((i)object);
        this.o.d((i)object2);
        this.v.h(this.o, this.l);
        this.o.d(this.e);
        this.q = object = new d(this.i);
        this.o.d((i)object);
        object = this.l;
        this.attachViewToParent((View)object, 0, object.getLayoutParams());
    }

    public boolean c() {
        return this.p.a();
    }

    public boolean canScrollHorizontally(int n3) {
        return this.l.canScrollHorizontally(n3);
    }

    public boolean canScrollVertically(int n3) {
        return this.l.canScrollVertically(n3);
    }

    public boolean d() {
        return this.i.d0() == 1;
    }

    public void dispatchRestoreInstanceState(SparseArray sparseArray) {
        Parcelable parcelable = (Parcelable)sparseArray.get(this.getId());
        if (parcelable instanceof SavedState) {
            int n3 = ((SavedState)parcelable).c;
            sparseArray.put(this.l.getId(), (Object)((Parcelable)sparseArray.get(n3)));
            sparseArray.remove(n3);
        }
        super.dispatchRestoreInstanceState(sparseArray);
        this.i();
    }

    public boolean e() {
        return this.t;
    }

    public final void f(RecyclerView.h h3) {
        if (h3 != null) {
            h3.w(this.h);
        }
    }

    public void g(i i3) {
        this.e.d(i3);
    }

    public CharSequence getAccessibilityClassName() {
        if (this.v.a()) {
            return this.v.g();
        }
        return super.getAccessibilityClassName();
    }

    public RecyclerView.h getAdapter() {
        return this.l.getAdapter();
    }

    public int getCurrentItem() {
        return this.f;
    }

    public int getItemDecorationCount() {
        return this.l.getItemDecorationCount();
    }

    public int getOffscreenPageLimit() {
        return this.u;
    }

    public int getOrientation() {
        if (this.i.p2() == 1) {
            return 1;
        }
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getPageSize() {
        int n3;
        int n4;
        RecyclerView recyclerView = this.l;
        if (this.getOrientation() == 0) {
            n4 = recyclerView.getWidth() - recyclerView.getPaddingLeft();
            n3 = recyclerView.getPaddingRight();
            return n4 - n3;
        }
        n4 = recyclerView.getHeight() - recyclerView.getPaddingTop();
        n3 = recyclerView.getPaddingBottom();
        return n4 - n3;
    }

    public int getScrollState() {
        return this.n.h();
    }

    public void h() {
        this.q.d();
    }

    public final void i() {
        int n3;
        RecyclerView.h h3;
        if (this.j == -1 || (h3 = this.getAdapter()) == null) {
            return;
        }
        Parcelable parcelable = this.k;
        if (parcelable != null) {
            if (h3 instanceof androidx.viewpager2.adapter.b) {
                ((androidx.viewpager2.adapter.b)((Object)h3)).b(parcelable);
            }
            this.k = null;
        }
        this.f = n3 = Math.max(0, Math.min(this.j, h3.f() - 1));
        this.j = -1;
        this.l.r1(n3);
        this.v.n();
    }

    public void j(int n3, boolean bl) {
        int n4;
        Object object;
        block10: {
            block9: {
                block8: {
                    object = this.getAdapter();
                    if (object != null) break block8;
                    if (this.j != -1) {
                        this.j = Math.max(n3, 0);
                        return;
                    }
                    break block9;
                }
                if (((RecyclerView.h)object).f() > 0 && ((n4 = Math.min(Math.max(n3, 0), ((RecyclerView.h)object).f() - 1)) != this.f || !this.n.j()) && (n4 != (n3 = this.f) || !bl)) break block10;
            }
            return;
        }
        double d3 = n3;
        this.f = n4;
        this.v.r();
        if (!this.n.j()) {
            d3 = this.n.g();
        }
        this.n.m(n4, bl);
        if (!bl) {
            this.l.r1(n4);
            return;
        }
        double d4 = n4;
        if (Math.abs(d4 - d3) > 3.0) {
            object = this.l;
            n3 = d4 > d3 ? n4 - 3 : n4 + 3;
            ((RecyclerView)object).r1(n3);
            object = this.l;
            object.post((Runnable)new n(n4, (RecyclerView)object));
            return;
        }
        this.l.A1(n4);
    }

    public final void k(Context context, AttributeSet attributeSet) {
        int[] nArray = q1.a.ViewPager2;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, nArray);
        x0.f0((View)this, context, nArray, attributeSet, typedArray, 0, 0);
        try {
            this.setOrientation(typedArray.getInt(q1.a.ViewPager2_android_orientation, 0));
            return;
        }
        finally {
            typedArray.recycle();
        }
    }

    public final void l(RecyclerView.h h3) {
        if (h3 != null) {
            h3.y(this.h);
        }
    }

    public void m(i i3) {
        this.e.e(i3);
    }

    public void n() {
        androidx.recyclerview.widget.j j3 = this.m;
        if (j3 != null) {
            if ((j3 = j3.f(this.i)) == null) {
                return;
            }
            int n3 = this.i.l0((View)j3);
            if (n3 != this.f && this.getScrollState() == 0) {
                this.o.c(n3);
            }
            this.g = false;
            return;
        }
        throw new IllegalStateException("Design assumption violated.");
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        this.v.i(accessibilityNodeInfo);
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        int n7 = this.l.getMeasuredWidth();
        int n8 = this.l.getMeasuredHeight();
        this.c.left = this.getPaddingLeft();
        this.c.right = n5 - n3 - this.getPaddingRight();
        this.c.top = this.getPaddingTop();
        this.c.bottom = n6 - n4 - this.getPaddingBottom();
        Gravity.apply((int)0x800033, (int)n7, (int)n8, (Rect)this.c, (Rect)this.d);
        RecyclerView recyclerView = this.l;
        Rect rect = this.d;
        recyclerView.layout(rect.left, rect.top, rect.right, rect.bottom);
        if (this.g) {
            this.n();
        }
    }

    public void onMeasure(int n3, int n4) {
        this.measureChild((View)this.l, n3, n4);
        int n5 = this.l.getMeasuredWidth();
        int n6 = this.l.getMeasuredHeight();
        int n7 = this.l.getMeasuredState();
        int n8 = this.getPaddingLeft();
        int n9 = this.getPaddingRight();
        int n10 = this.getPaddingTop();
        int n11 = this.getPaddingBottom();
        n5 = Math.max(n5 + (n8 + n9), this.getSuggestedMinimumWidth());
        n11 = Math.max(n6 + (n10 + n11), this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSizeAndState((int)n5, (int)n3, (int)n7), View.resolveSizeAndState((int)n11, (int)n4, (int)(n7 << 16)));
    }

    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.j = object.d;
        this.k = object.e;
    }

    public Parcelable onSaveInstanceState() {
        int n3;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.c = this.l.getId();
        int n4 = n3 = this.j;
        if (n3 == -1) {
            n4 = this.f;
        }
        savedState.d = n4;
        Object object = this.k;
        if (object != null) {
            savedState.e = object;
            return savedState;
        }
        object = this.l.getAdapter();
        if (object instanceof androidx.viewpager2.adapter.b) {
            savedState.e = ((androidx.viewpager2.adapter.b)object).a();
        }
        return savedState;
    }

    public void onViewAdded(View object) {
        object = new StringBuilder();
        ((StringBuilder)object).append(ViewPager2.class.getSimpleName());
        ((StringBuilder)object).append(" does not support direct child views");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public boolean performAccessibilityAction(int n3, Bundle bundle) {
        if (this.v.c(n3, bundle)) {
            return this.v.m(n3, bundle);
        }
        return super.performAccessibilityAction(n3, bundle);
    }

    public void setAdapter(RecyclerView.h h3) {
        RecyclerView.h h4 = this.l.getAdapter();
        this.v.f(h4);
        this.l(h4);
        this.l.setAdapter(h3);
        this.f = 0;
        this.i();
        this.v.e(h3);
        this.f(h3);
    }

    public void setCurrentItem(int n3) {
        this.setCurrentItem(n3, true);
    }

    public void setCurrentItem(int n3, boolean bl) {
        if (!this.c()) {
            this.j(n3, bl);
            return;
        }
        throw new IllegalStateException("Cannot change current item when ViewPager2 is fake dragging");
    }

    public void setLayoutDirection(int n3) {
        super.setLayoutDirection(n3);
        this.v.q();
    }

    public void setOffscreenPageLimit(int n3) {
        if (n3 < 1 && n3 != -1) {
            throw new IllegalArgumentException("Offscreen page limit must be OFFSCREEN_PAGE_LIMIT_DEFAULT or a number > 0");
        }
        this.u = n3;
        this.l.requestLayout();
    }

    public void setOrientation(int n3) {
        this.i.C2(n3);
        this.v.s();
    }

    public void setPageTransformer(k k3) {
        if (k3 != null) {
            if (!this.s) {
                this.r = this.l.getItemAnimator();
                this.s = true;
            }
            this.l.setItemAnimator(null);
        } else if (this.s) {
            this.l.setItemAnimator(this.r);
            this.r = null;
            this.s = false;
        }
        this.q.d();
        if (k3 == null) {
            return;
        }
        this.q.e(k3);
        this.h();
    }

    public void setUserInputEnabled(boolean bl) {
        this.t = bl;
        this.v.t();
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

            public SavedState a(Parcel parcel) {
                return this.b(parcel, null);
            }

            public SavedState b(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] c(int n3) {
                return new SavedState[n3];
            }
        };
        public int c;
        public int d;
        public Parcelable e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.o(parcel, classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final void o(Parcel parcel, ClassLoader classLoader) {
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readParcelable(classLoader);
        }

        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeParcelable(this.e, n3);
        }
    }

    public abstract class e {
        public final ViewPager2 a;

        public e(ViewPager2 viewPager2) {
            this.a = viewPager2;
        }

        public /* synthetic */ e(ViewPager2 viewPager2, a a4) {
            this(viewPager2);
        }

        public boolean a() {
            return false;
        }

        public boolean b(int n3) {
            return false;
        }

        public boolean c(int n3, Bundle bundle) {
            return false;
        }

        public boolean d() {
            return false;
        }

        public void e(RecyclerView.h h3) {
        }

        public void f(RecyclerView.h h3) {
        }

        public String g() {
            throw new IllegalStateException("Not implemented.");
        }

        public void h(b b3, RecyclerView recyclerView) {
        }

        public void i(AccessibilityNodeInfo accessibilityNodeInfo) {
        }

        public void j(s s3) {
        }

        public void k(View view, s s3) {
        }

        public boolean l(int n3) {
            throw new IllegalStateException("Not implemented.");
        }

        public boolean m(int n3, Bundle bundle) {
            throw new IllegalStateException("Not implemented.");
        }

        public void n() {
        }

        public CharSequence o() {
            throw new IllegalStateException("Not implemented.");
        }

        public void p(AccessibilityEvent accessibilityEvent) {
        }

        public void q() {
        }

        public void r() {
        }

        public void s() {
        }

        public void t() {
        }
    }

    public class f
    extends e {
        public final ViewPager2 b;

        public f(ViewPager2 viewPager2) {
            this.b = viewPager2;
            super(viewPager2, null);
        }

        @Override
        public boolean b(int n3) {
            return (n3 == 8192 || n3 == 4096) && !this.b.e();
        }

        @Override
        public boolean d() {
            return true;
        }

        @Override
        public void j(s s3) {
            if (!this.b.e()) {
                s3.Z(s.a.r);
                s3.Z(s.a.q);
                s3.B0(false);
            }
        }

        @Override
        public boolean l(int n3) {
            if (this.b(n3)) {
                return false;
            }
            throw new IllegalStateException();
        }

        @Override
        public CharSequence o() {
            if (this.d()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }
    }

    public static abstract class g
    extends RecyclerView.j {
        public g() {
        }

        public /* synthetic */ g(a a4) {
            this();
        }

        @Override
        public abstract void a();

        @Override
        public final void b(int n3, int n4, Object object) {
            this.a();
        }
    }

    public class h
    extends LinearLayoutManager {
        public final ViewPager2 I;

        public h(ViewPager2 viewPager2, Context context) {
            this.I = viewPager2;
            super(context);
        }

        @Override
        public void Q0(RecyclerView.v v3, RecyclerView.z z3, s s3) {
            super.Q0(v3, z3, s3);
            this.I.v.j(s3);
        }

        @Override
        public void Q1(RecyclerView.z z3, int[] nArray) {
            int n3 = this.I.getOffscreenPageLimit();
            if (n3 == -1) {
                super.Q1(z3, nArray);
                return;
            }
            nArray[0] = n3 = this.I.getPageSize() * n3;
            nArray[1] = n3;
        }

        @Override
        public void T0(RecyclerView.v v3, RecyclerView.z z3, View view, s s3) {
            this.I.v.k(view, s3);
        }

        @Override
        public boolean l1(RecyclerView.v v3, RecyclerView.z z3, int n3, Bundle bundle) {
            if (this.I.v.b(n3)) {
                return this.I.v.l(n3);
            }
            return super.l1(v3, z3, n3, bundle);
        }

        @Override
        public boolean w1(RecyclerView recyclerView, View view, Rect rect, boolean bl, boolean bl2) {
            return false;
        }
    }

    public static abstract class i {
        public void a(int n3) {
        }

        public void b(int n3, float f3, int n4) {
        }

        public void c(int n3) {
        }
    }

    public class j
    extends e {
        public final v b;
        public final v c;
        public RecyclerView.j d;
        public final ViewPager2 e;

        public j(ViewPager2 viewPager2) {
            this.e = viewPager2;
            super(viewPager2, null);
            this.b = new v(this){
                public final j a;
                {
                    this.a = j3;
                }

                @Override
                public boolean a(View object, v.a a4) {
                    object = (ViewPager2)((Object)object);
                    this.a.x(((ViewPager2)((Object)object)).getCurrentItem() + 1);
                    return true;
                }
            };
            this.c = new v(this){
                public final j a;
                {
                    this.a = j3;
                }

                @Override
                public boolean a(View object, v.a a4) {
                    object = (ViewPager2)((Object)object);
                    this.a.x(((ViewPager2)((Object)object)).getCurrentItem() - 1);
                    return true;
                }
            };
        }

        @Override
        public boolean a() {
            return true;
        }

        @Override
        public boolean c(int n3, Bundle bundle) {
            return n3 == 8192 || n3 == 4096;
            {
            }
        }

        @Override
        public void e(RecyclerView.h h3) {
            this.y();
            if (h3 != null) {
                h3.w(this.d);
            }
        }

        @Override
        public void f(RecyclerView.h h3) {
            if (h3 != null) {
                h3.y(this.d);
            }
        }

        @Override
        public String g() {
            if (this.a()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }

        @Override
        public void h(b b3, RecyclerView recyclerView) {
            recyclerView.setImportantForAccessibility(2);
            this.d = new g(this){
                public final j a;
                {
                    this.a = j3;
                    super(null);
                }

                @Override
                public void a() {
                    this.a.y();
                }
            };
            if (this.e.getImportantForAccessibility() == 0) {
                this.e.setImportantForAccessibility(1);
            }
        }

        @Override
        public void i(AccessibilityNodeInfo object) {
            object = p0.s.L0(object);
            this.u((s)object);
            this.w((s)object);
        }

        @Override
        public void k(View view, s s3) {
            this.v(view, s3);
        }

        @Override
        public boolean m(int n3, Bundle bundle) {
            if (this.c(n3, bundle)) {
                n3 = n3 == 8192 ? this.e.getCurrentItem() - 1 : this.e.getCurrentItem() + 1;
                this.x(n3);
                return true;
            }
            throw new IllegalStateException();
        }

        @Override
        public void n() {
            this.y();
        }

        @Override
        public void p(AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setSource((View)this.e);
            accessibilityEvent.setClassName((CharSequence)this.g());
        }

        @Override
        public void q() {
            this.y();
        }

        @Override
        public void r() {
            this.y();
        }

        @Override
        public void s() {
            this.y();
        }

        @Override
        public void t() {
            this.y();
        }

        public final void u(s s3) {
            int n3;
            int n4;
            if (this.e.getAdapter() != null) {
                n4 = this.e.getOrientation();
                n3 = 1;
                if (n4 == 1) {
                    n3 = this.e.getAdapter().f();
                    n4 = 1;
                } else {
                    n4 = this.e.getAdapter().f();
                }
            } else {
                n4 = 0;
                n3 = 0;
            }
            s3.j0(s.e.b(n3, n4, false, 0));
        }

        public final void v(View view, s s3) {
            int n3 = this.e.getOrientation();
            int n4 = 0;
            n3 = n3 == 1 ? this.e.i.l0(view) : 0;
            if (this.e.getOrientation() == 0) {
                n4 = this.e.i.l0(view);
            }
            s3.k0(s.f.a(n3, 1, n4, 1, false, false));
        }

        public final void w(s s3) {
            int n3;
            RecyclerView.h h3 = this.e.getAdapter();
            if (h3 != null && (n3 = h3.f()) != 0 && this.e.e()) {
                if (this.e.f > 0) {
                    s3.a(8192);
                }
                if (this.e.f < n3 - 1) {
                    s3.a(4096);
                }
                s3.B0(true);
            }
        }

        public void x(int n3) {
            if (this.e.e()) {
                this.e.j(n3, true);
            }
        }

        public void y() {
            int n3;
            ViewPager2 viewPager2 = this.e;
            int n4 = 16908360;
            x0.b0((View)viewPager2, 16908360);
            x0.b0((View)viewPager2, 16908361);
            x0.b0((View)viewPager2, 16908358);
            x0.b0((View)viewPager2, 16908359);
            if (this.e.getAdapter() != null && (n3 = this.e.getAdapter().f()) != 0 && this.e.e()) {
                if (this.e.getOrientation() == 0) {
                    boolean bl = this.e.d();
                    int n5 = bl ? 16908360 : 16908361;
                    if (bl) {
                        n4 = 16908361;
                    }
                    if (this.e.f < n3 - 1) {
                        x0.d0((View)viewPager2, new s.a(n5, null), null, this.b);
                    }
                    if (this.e.f > 0) {
                        x0.d0((View)viewPager2, new s.a(n4, null), null, this.c);
                        return;
                    }
                } else {
                    if (this.e.f < n3 - 1) {
                        x0.d0((View)viewPager2, new s.a(16908359, null), null, this.b);
                    }
                    if (this.e.f > 0) {
                        x0.d0((View)viewPager2, new s.a(16908358, null), null, this.c);
                    }
                }
            }
        }
    }

    public static interface k {
    }

    public class l
    extends androidx.recyclerview.widget.j {
        public final ViewPager2 f;

        public l(ViewPager2 viewPager2) {
            this.f = viewPager2;
        }

        @Override
        public View f(RecyclerView.p p3) {
            if (this.f.c()) {
                return null;
            }
            return super.f(p3);
        }
    }

    public class m
    extends RecyclerView {
        public final ViewPager2 Q0;

        public m(ViewPager2 viewPager2, Context context) {
            this.Q0 = viewPager2;
            super(context);
        }

        @Override
        public CharSequence getAccessibilityClassName() {
            if (this.Q0.v.d()) {
                return this.Q0.v.o();
            }
            return super.getAccessibilityClassName();
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setFromIndex(this.Q0.f);
            accessibilityEvent.setToIndex(this.Q0.f);
            this.Q0.v.p(accessibilityEvent);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return this.Q0.e() && super.onInterceptTouchEvent(motionEvent);
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.Q0.e() && super.onTouchEvent(motionEvent);
        }
    }

    public static class n
    implements Runnable {
        public final int c;
        public final RecyclerView d;

        public n(int n3, RecyclerView recyclerView) {
            this.c = n3;
            this.d = recyclerView;
        }

        @Override
        public void run() {
            this.d.A1(this.c);
        }
    }
}

