/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.s;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import androidx.constraintlayout.widget.Placeholder;
import androidx.constraintlayout.widget.VirtualLayout;
import androidx.constraintlayout.widget.c;
import java.util.ArrayList;
import java.util.HashMap;
import r.e;
import u.d;
import u.e;
import u.f;
import u.h;
import u.k;
import u.m;
import v.b;
import y.d;

public class ConstraintLayout
extends ViewGroup {
    public static c A;
    public SparseArray c = new SparseArray();
    public ArrayList d = new ArrayList(4);
    public f e = new f();
    public int f = 0;
    public int g = 0;
    public int h = Integer.MAX_VALUE;
    public int i = Integer.MAX_VALUE;
    public boolean j = true;
    public int k = 257;
    public androidx.constraintlayout.widget.b l = null;
    public y.a m = null;
    public int n = -1;
    public HashMap o = new HashMap();
    public int p = -1;
    public int q = -1;
    public int r = -1;
    public int s = -1;
    public int t = 0;
    public int u = 0;
    public SparseArray v = new SparseArray();
    public b w = new b(this, this);
    public int x = 0;
    public int y = 0;
    public ArrayList z;

    public ConstraintLayout(Context context) {
        super(context);
        this.s(null, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.s(attributeSet, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.s(attributeSet, n3, 0);
    }

    public static /* synthetic */ e c(ConstraintLayout constraintLayout) {
        ((Object)((Object)constraintLayout)).getClass();
        return null;
    }

    private int getPaddingWidth() {
        int n3 = Math.max(0, this.getPaddingLeft());
        int n4 = Math.max(0, this.getPaddingRight());
        int n5 = Math.max(0, this.getPaddingStart()) + Math.max(0, this.getPaddingEnd());
        if (n5 > 0) {
            return n5;
        }
        return n3 + n4;
    }

    public static c getSharedValues() {
        if (A == null) {
            A = new c();
        }
        return A;
    }

    public final void A(u.e e3, LayoutParams layoutParams, SparseArray object, int n3, d.a a4) {
        Object object2 = (View)this.c.get(n3);
        if ((object = (u.e)object.get(n3)) != null && object2 != null && object2.getLayoutParams() instanceof LayoutParams) {
            layoutParams.g0 = true;
            d.a a5 = d.a.h;
            if (a4 == a5) {
                object2 = (LayoutParams)object2.getLayoutParams();
                object2.g0 = true;
                object2.v0.P0(true);
            }
            e3.q(a5).b(((u.e)object).q(a4), layoutParams.D, layoutParams.C, true);
            e3.P0(true);
            e3.q(d.a.e).q();
            e3.q(d.a.g).q();
        }
    }

    public final boolean B() {
        boolean bl;
        int n3 = this.getChildCount();
        boolean bl2 = false;
        int n4 = 0;
        while (true) {
            bl = bl2;
            if (n4 >= n3) break;
            if (this.getChildAt(n4).isLayoutRequested()) {
                bl = true;
                break;
            }
            ++n4;
        }
        if (bl) {
            this.y();
        }
        return bl;
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void dispatchDraw(Canvas canvas) {
        int n3;
        int n4;
        Object object = this.d;
        if (object != null && (n4 = object.size()) > 0) {
            for (n3 = 0; n3 < n4; ++n3) {
                ((ConstraintHelper)((Object)this.d.get(n3))).t(this);
            }
        }
        super.dispatchDraw(canvas);
        if (this.isInEditMode()) {
            float f3 = this.getWidth();
            float f4 = this.getHeight();
            n4 = this.getChildCount();
            for (n3 = 0; n3 < n4; ++n3) {
                object = this.getChildAt(n3);
                if (object.getVisibility() == 8 || (object = object.getTag()) == null || !(object instanceof String) || ((String[])(object = ((String)object).split(","))).length != 4) continue;
                int n5 = Integer.parseInt(object[0]);
                int n6 = Integer.parseInt(object[1]);
                int n7 = Integer.parseInt(object[2]);
                int n8 = Integer.parseInt(object[3]);
                n5 = (int)((float)n5 / 1080.0f * f3);
                n6 = (int)((float)n6 / 1920.0f * f4);
                n7 = (int)((float)n7 / 1080.0f * f3);
                n8 = (int)((float)n8 / 1920.0f * f4);
                object = new Paint();
                object.setColor(-65536);
                float f5 = n5;
                float f6 = n6;
                float f7 = n5 + n7;
                canvas.drawLine(f5, f6, f7, f6, object);
                float f8 = n6 + n8;
                canvas.drawLine(f7, f6, f7, f8, object);
                canvas.drawLine(f7, f8, f5, f8, object);
                canvas.drawLine(f5, f8, f5, f6, object);
                object.setColor(-16711936);
                canvas.drawLine(f5, f6, f7, f8, object);
                canvas.drawLine(f5, f8, f7, f6, object);
            }
        }
    }

    public void f(boolean bl, View object, u.e e3, LayoutParams layoutParams, SparseArray sparseArray) {
        layoutParams.c();
        layoutParams.w0 = false;
        e3.o1(object.getVisibility());
        if (layoutParams.j0) {
            e3.Y0(true);
            e3.o1(8);
        }
        e3.G0(object);
        if (object instanceof ConstraintHelper) {
            ((ConstraintHelper)((Object)object)).q(e3, this.e.V1());
        }
        if (layoutParams.h0) {
            object = (h)e3;
            int n3 = layoutParams.s0;
            int n4 = layoutParams.t0;
            float f3 = layoutParams.u0;
            if (f3 != -1.0f) {
                object.E1(f3);
                return;
            }
            if (n3 != -1) {
                object.C1(n3);
                return;
            }
            if (n4 != -1) {
                object.D1(n4);
            }
            return;
        }
        int n5 = layoutParams.l0;
        int n6 = layoutParams.m0;
        int n7 = layoutParams.n0;
        int n8 = layoutParams.o0;
        int n9 = layoutParams.p0;
        int n10 = layoutParams.q0;
        float f4 = layoutParams.r0;
        int n11 = layoutParams.p;
        if (n11 != -1) {
            object = (u.e)sparseArray.get(n11);
            if (object != null) {
                e3.m((u.e)object, layoutParams.r, layoutParams.q);
            }
        } else {
            Object object2;
            if (n5 != -1) {
                object2 = (u.e)sparseArray.get(n5);
                if (object2 != null) {
                    object = d.a.d;
                    e3.g0((d.a)((Object)object), (u.e)object2, (d.a)((Object)object), layoutParams.leftMargin, n9);
                }
            } else if (n6 != -1 && (object = (u.e)sparseArray.get(n6)) != null) {
                e3.g0(d.a.d, (u.e)object, d.a.f, layoutParams.leftMargin, n9);
            }
            if (n7 != -1) {
                object = (u.e)sparseArray.get(n7);
                if (object != null) {
                    e3.g0(d.a.f, (u.e)object, d.a.d, layoutParams.rightMargin, n10);
                }
            } else if (n8 != -1 && (object2 = (u.e)sparseArray.get(n8)) != null) {
                object = d.a.f;
                e3.g0((d.a)((Object)object), (u.e)object2, (d.a)((Object)object), layoutParams.rightMargin, n10);
            }
            if ((n6 = layoutParams.i) != -1) {
                object = (u.e)sparseArray.get(n6);
                if (object != null) {
                    object2 = d.a.e;
                    e3.g0((d.a)((Object)object2), (u.e)object, (d.a)((Object)object2), layoutParams.topMargin, layoutParams.x);
                }
            } else {
                n6 = layoutParams.j;
                if (n6 != -1 && (object = (u.e)sparseArray.get(n6)) != null) {
                    e3.g0(d.a.e, (u.e)object, d.a.g, layoutParams.topMargin, layoutParams.x);
                }
            }
            if ((n6 = layoutParams.k) != -1) {
                object = (u.e)sparseArray.get(n6);
                if (object != null) {
                    e3.g0(d.a.g, (u.e)object, d.a.e, layoutParams.bottomMargin, layoutParams.z);
                }
            } else {
                n6 = layoutParams.l;
                if (n6 != -1 && (object2 = (u.e)sparseArray.get(n6)) != null) {
                    object = d.a.g;
                    e3.g0((d.a)((Object)object), (u.e)object2, (d.a)((Object)object), layoutParams.bottomMargin, layoutParams.z);
                }
            }
            if ((n6 = layoutParams.m) != -1) {
                this.A(e3, layoutParams, sparseArray, n6, d.a.h);
            } else {
                n6 = layoutParams.n;
                if (n6 != -1) {
                    this.A(e3, layoutParams, sparseArray, n6, d.a.e);
                } else {
                    n6 = layoutParams.o;
                    if (n6 != -1) {
                        this.A(e3, layoutParams, sparseArray, n6, d.a.g);
                    }
                }
            }
            if (f4 >= 0.0f) {
                e3.R0(f4);
            }
            if ((f4 = layoutParams.H) >= 0.0f) {
                e3.i1(f4);
            }
        }
        if (bl && ((n6 = layoutParams.X) != -1 || layoutParams.Y != -1)) {
            e3.g1(n6, layoutParams.Y);
        }
        if (!layoutParams.e0) {
            if (layoutParams.width == -1) {
                if (layoutParams.a0) {
                    e3.U0(e.b.e);
                } else {
                    e3.U0(e.b.f);
                }
                e3.q((d.a)d.a.d).g = layoutParams.leftMargin;
                e3.q((d.a)d.a.f).g = layoutParams.rightMargin;
            } else {
                e3.U0(e.b.e);
                e3.p1(0);
            }
        } else {
            e3.U0(e.b.c);
            e3.p1(layoutParams.width);
            if (layoutParams.width == -2) {
                e3.U0(e.b.d);
            }
        }
        if (!layoutParams.f0) {
            if (layoutParams.height == -1) {
                if (layoutParams.b0) {
                    e3.l1(e.b.e);
                } else {
                    e3.l1(e.b.f);
                }
                e3.q((d.a)d.a.e).g = layoutParams.topMargin;
                e3.q((d.a)d.a.g).g = layoutParams.bottomMargin;
            } else {
                e3.l1(e.b.e);
                e3.Q0(0);
            }
        } else {
            e3.l1(e.b.c);
            e3.Q0(layoutParams.height);
            if (layoutParams.height == -2) {
                e3.l1(e.b.d);
            }
        }
        e3.I0(layoutParams.I);
        e3.W0(layoutParams.L);
        e3.n1(layoutParams.M);
        e3.S0(layoutParams.N);
        e3.j1(layoutParams.O);
        e3.q1(layoutParams.d0);
        e3.V0(layoutParams.P, layoutParams.R, layoutParams.T, layoutParams.V);
        e3.m1(layoutParams.Q, layoutParams.S, layoutParams.U, layoutParams.W);
    }

    public void forceLayout() {
        this.u();
        super.forceLayout();
    }

    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getMaxHeight() {
        return this.i;
    }

    public int getMaxWidth() {
        return this.h;
    }

    public int getMinHeight() {
        return this.g;
    }

    public int getMinWidth() {
        return this.f;
    }

    public int getOptimizationLevel() {
        return this.e.P1();
    }

    public String getSceneString() {
        Object object;
        int n3;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.e.o == null) {
            n3 = this.getId();
            this.e.o = n3 != -1 ? (object = this.getContext().getResources().getResourceEntryName(n3)) : "parent";
        }
        if (this.e.v() == null) {
            object = this.e;
            ((u.e)object).H0(((u.e)object).o);
            this.e.v();
        }
        object = this.e.w1();
        int n4 = ((ArrayList)object).size();
        n3 = 0;
        while (n3 < n4) {
            Object object2 = ((ArrayList)object).get(n3);
            int n5 = n3 + 1;
            object2 = (u.e)object2;
            View view = (View)((u.e)object2).u();
            n3 = n5;
            if (view == null) continue;
            if (((u.e)object2).o == null && (n3 = view.getId()) != -1) {
                ((u.e)object2).o = this.getContext().getResources().getResourceEntryName(n3);
            }
            n3 = n5;
            if (((u.e)object2).v() != null) continue;
            ((u.e)object2).H0(((u.e)object2).o);
            ((u.e)object2).v();
            n3 = n5;
        }
        this.e.Q(stringBuilder);
        return stringBuilder.toString();
    }

    public boolean i(int n3, int n4) {
        if (this.z == null) {
            return false;
        }
        View.MeasureSpec.getSize((int)n3);
        View.MeasureSpec.getSize((int)n4);
        Object object = this.z;
        n4 = object.size();
        for (n3 = 0; n3 < n4; ++n3) {
            Object object2 = object.get(n3);
            androidx.appcompat.app.s.a(object2);
            object2 = this.e.w1().iterator();
            if (!object2.hasNext()) continue;
            object = (View)((u.e)object2.next()).u();
            object.getId();
            object = (LayoutParams)object.getLayoutParams();
            throw null;
        }
        return false;
    }

    public LayoutParams j() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams n(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public Object o(int n3, Object object) {
        if (n3 == 0 && object instanceof String) {
            String string = (String)object;
            object = this.o;
            if (object != null && ((HashMap)object).containsKey(string)) {
                return this.o.get(string);
            }
        }
        return null;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        n5 = this.getChildCount();
        bl = this.isInEditMode();
        n4 = 0;
        for (n3 = 0; n3 < n5; ++n3) {
            View view = this.getChildAt(n3);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            u.e e3 = layoutParams.v0;
            if (view.getVisibility() == 8 && !layoutParams.h0 && !layoutParams.i0 && !layoutParams.k0 && !bl || layoutParams.j0) continue;
            n6 = e3.Z();
            int n7 = e3.a0();
            int n8 = e3.Y() + n6;
            int n9 = e3.z() + n7;
            view.layout(n6, n7, n8, n9);
            if (!(view instanceof Placeholder) || (layoutParams = ((Placeholder)view).getContent()) == null) continue;
            layoutParams.setVisibility(0);
            layoutParams.layout(n6, n7, n8, n9);
        }
        n5 = this.d.size();
        if (n5 > 0) {
            for (n3 = n4; n3 < n5; ++n3) {
                ((ConstraintHelper)((Object)this.d.get(n3))).r(this);
            }
        }
    }

    public void onMeasure(int n3, int n4) {
        boolean bl;
        this.j = bl = this.j | this.i(n3, n4);
        if (!bl) {
            int n5 = this.getChildCount();
            for (int i3 = 0; i3 < n5; ++i3) {
                if (!this.getChildAt(i3).isLayoutRequested()) continue;
                this.j = true;
                break;
            }
        }
        this.x = n3;
        this.y = n4;
        this.e.e2(this.t());
        if (this.j) {
            this.j = false;
            if (this.B()) {
                this.e.g2();
            }
        }
        this.e.N1(null);
        this.x(this.e, this.k, n3, n4);
        this.w(n3, n4, this.e.Y(), this.e.z(), this.e.W1(), this.e.U1());
    }

    public void onViewAdded(View view) {
        super.onViewAdded(view);
        Object object = this.r(view);
        if (view instanceof Guideline && !(object instanceof h)) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            layoutParams.v0 = object = new h();
            layoutParams.h0 = true;
            ((h)object).F1(layoutParams.Z);
        }
        if (view instanceof ConstraintHelper) {
            object = (ConstraintHelper)view;
            ((ConstraintHelper)((Object)object)).w();
            ((LayoutParams)view.getLayoutParams()).i0 = true;
            if (!this.d.contains(object)) {
                this.d.add(object);
            }
        }
        this.c.put(view.getId(), (Object)view);
        this.j = true;
    }

    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.c.remove(view.getId());
        u.e e3 = this.r(view);
        this.e.y1(e3);
        this.d.remove(view);
        this.j = true;
    }

    public final u.e p(int n3) {
        View view;
        if (n3 == 0) {
            return this.e;
        }
        View view2 = view = (View)this.c.get(n3);
        if (view == null) {
            view2 = view = this.findViewById(n3);
            if (view != null) {
                view2 = view;
                if (view != this) {
                    view2 = view;
                    if (view.getParent() == this) {
                        this.onViewAdded(view);
                        view2 = view;
                    }
                }
            }
        }
        if (view2 == this) {
            return this.e;
        }
        if (view2 == null) {
            return null;
        }
        return ((LayoutParams)view2.getLayoutParams()).v0;
    }

    public View q(int n3) {
        return (View)this.c.get(n3);
    }

    public final u.e r(View view) {
        if (view == this) {
            return this.e;
        }
        if (view != null) {
            if (view.getLayoutParams() instanceof LayoutParams) {
                return ((LayoutParams)view.getLayoutParams()).v0;
            }
            view.setLayoutParams(this.generateLayoutParams(view.getLayoutParams()));
            if (view.getLayoutParams() instanceof LayoutParams) {
                return ((LayoutParams)view.getLayoutParams()).v0;
            }
        }
        return null;
    }

    public void requestLayout() {
        this.u();
        super.requestLayout();
    }

    public final void s(AttributeSet attributeSet, int n3, int n4) {
        this.e.G0((Object)this);
        this.e.b2(this.w);
        this.c.put(this.getId(), (Object)this);
        this.l = null;
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_Layout, n3, n4);
            n4 = attributeSet.getIndexCount();
            for (n3 = 0; n3 < n4; ++n3) {
                int n5 = attributeSet.getIndex(n3);
                if (n5 == y.d.ConstraintLayout_Layout_android_minWidth) {
                    this.f = attributeSet.getDimensionPixelOffset(n5, this.f);
                    continue;
                }
                if (n5 == y.d.ConstraintLayout_Layout_android_minHeight) {
                    this.g = attributeSet.getDimensionPixelOffset(n5, this.g);
                    continue;
                }
                if (n5 == y.d.ConstraintLayout_Layout_android_maxWidth) {
                    this.h = attributeSet.getDimensionPixelOffset(n5, this.h);
                    continue;
                }
                if (n5 == y.d.ConstraintLayout_Layout_android_maxHeight) {
                    this.i = attributeSet.getDimensionPixelOffset(n5, this.i);
                    continue;
                }
                if (n5 == y.d.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.k = attributeSet.getInt(n5, this.k);
                    continue;
                }
                if (n5 == y.d.ConstraintLayout_Layout_layoutDescription) {
                    if ((n5 = attributeSet.getResourceId(n5, 0)) == 0) continue;
                    try {
                        this.v(n5);
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        this.m = null;
                    }
                    continue;
                }
                if (n5 != y.d.ConstraintLayout_Layout_constraintSet) continue;
                n5 = attributeSet.getResourceId(n5, 0);
                try {
                    androidx.constraintlayout.widget.b b3;
                    this.l = b3 = new androidx.constraintlayout.widget.b();
                    b3.C(this.getContext(), n5);
                }
                catch (Resources.NotFoundException notFoundException) {
                    this.l = null;
                }
                this.n = n5;
            }
            attributeSet.recycle();
        }
        this.e.c2(this.k);
    }

    public void setConstraintSet(androidx.constraintlayout.widget.b b3) {
        this.l = b3;
    }

    public void setDesignInformation(int n3, Object object, Object object2) {
        if (n3 == 0 && object instanceof String && object2 instanceof Integer) {
            if (this.o == null) {
                this.o = new HashMap();
            }
            String string = (String)object;
            n3 = string.indexOf("/");
            object = string;
            if (n3 != -1) {
                object = string.substring(n3 + 1);
            }
            object2 = (Integer)object2;
            this.o.put(object, object2);
        }
    }

    public void setId(int n3) {
        this.c.remove(this.getId());
        super.setId(n3);
        this.c.put(this.getId(), (Object)this);
    }

    public void setMaxHeight(int n3) {
        if (n3 == this.i) {
            return;
        }
        this.i = n3;
        this.requestLayout();
    }

    public void setMaxWidth(int n3) {
        if (n3 == this.h) {
            return;
        }
        this.h = n3;
        this.requestLayout();
    }

    public void setMinHeight(int n3) {
        if (n3 == this.g) {
            return;
        }
        this.g = n3;
        this.requestLayout();
    }

    public void setMinWidth(int n3) {
        if (n3 == this.f) {
            return;
        }
        this.f = n3;
        this.requestLayout();
    }

    public void setOnConstraintsChanged(y.b b3) {
        y.a a4 = this.m;
        if (a4 != null) {
            a4.c(b3);
        }
    }

    public void setOptimizationLevel(int n3) {
        this.k = n3;
        this.e.c2(n3);
    }

    public void setState(int n3, int n4, int n5) {
        y.a a4 = this.m;
        if (a4 != null) {
            a4.d(n3, n4, n5);
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public boolean t() {
        return (this.getContext().getApplicationInfo().flags & 0x400000) != 0 && 1 == this.getLayoutDirection();
    }

    public final void u() {
        this.j = true;
        this.p = -1;
        this.q = -1;
        this.r = -1;
        this.s = -1;
        this.t = 0;
        this.u = 0;
    }

    public void v(int n3) {
        this.m = new y.a(this.getContext(), this, n3);
    }

    public void w(int n3, int n4, int n5, int n6, boolean bl, boolean bl2) {
        b b3 = this.w;
        int n7 = b3.e;
        n3 = View.resolveSizeAndState((int)(n5 + b3.d), (int)n3, (int)0);
        n5 = View.resolveSizeAndState((int)(n6 + n7), (int)n4, (int)0);
        n4 = Math.min(this.h, n3 & 0xFFFFFF);
        n5 = Math.min(this.i, n5 & 0xFFFFFF);
        n3 = n4;
        if (bl) {
            n3 = n4 | 0x1000000;
        }
        n4 = n5;
        if (bl2) {
            n4 = n5 | 0x1000000;
        }
        this.setMeasuredDimension(n3, n4);
        this.p = n3;
        this.q = n4;
    }

    public void x(f f3, int n3, int n4, int n5) {
        int n6 = View.MeasureSpec.getMode((int)n4);
        int n7 = View.MeasureSpec.getSize((int)n4);
        int n8 = View.MeasureSpec.getMode((int)n5);
        int n9 = View.MeasureSpec.getSize((int)n5);
        int n10 = Math.max(0, this.getPaddingTop());
        int n11 = Math.max(0, this.getPaddingBottom());
        int n12 = n10 + n11;
        int n13 = this.getPaddingWidth();
        this.w.c(n4, n5, n10, n11, n13, n12);
        n4 = Math.max(0, this.getPaddingStart());
        n5 = Math.max(0, this.getPaddingEnd());
        if (n4 <= 0 && n5 <= 0) {
            n4 = Math.max(0, this.getPaddingLeft());
        } else if (this.t()) {
            n4 = n5;
        }
        n5 = n7 - n13;
        this.z(f3, n6, n5, n8, n9 -= n12);
        f3.X1(n3, n6, n5, n8, n9, this.p, this.q, n4, n10);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void y() {
        int n3;
        Object object;
        Object object2;
        int n4;
        boolean bl = this.isInEditMode();
        int n5 = this.getChildCount();
        for (n4 = 0; n4 < n5; ++n4) {
            object2 = this.r(this.getChildAt(n4));
            if (object2 == null) continue;
            ((u.e)object2).v0();
        }
        if (bl) {
            for (n4 = 0; n4 < n5; ++n4) {
                View view = this.getChildAt(n4);
                try {
                    object = this.getResources().getResourceName(view.getId());
                    this.setDesignInformation(0, object, view.getId());
                    n3 = ((String)object).indexOf(47);
                    object2 = object;
                    if (n3 != -1) {
                        object2 = ((String)object).substring(n3 + 1);
                    }
                    this.p(view.getId()).H0((String)object2);
                    continue;
                }
                catch (Resources.NotFoundException notFoundException) {}
            }
        }
        if (this.n != -1) {
            for (n4 = 0; n4 < n5; ++n4) {
                object2 = this.getChildAt(n4);
                if (object2.getId() != this.n || !(object2 instanceof Constraints)) continue;
                this.l = ((Constraints)((Object)object2)).getConstraintSet();
            }
        }
        if ((object2 = this.l) != null) {
            ((androidx.constraintlayout.widget.b)object2).k(this, true);
        }
        this.e.z1();
        n3 = this.d.size();
        if (n3 > 0) {
            for (n4 = 0; n4 < n3; ++n4) {
                ((ConstraintHelper)((Object)this.d.get(n4))).u(this);
            }
        }
        for (n4 = 0; n4 < n5; ++n4) {
            object2 = this.getChildAt(n4);
            if (!(object2 instanceof Placeholder)) continue;
            ((Placeholder)((Object)object2)).c(this);
        }
        this.v.clear();
        this.v.put(0, (Object)this.e);
        this.v.put(this.getId(), (Object)this.e);
        for (n4 = 0; n4 < n5; ++n4) {
            object = this.getChildAt(n4);
            object2 = this.r((View)object);
            this.v.put(object.getId(), object2);
        }
        n4 = 0;
        while (n4 < n5) {
            object2 = this.getChildAt(n4);
            object = this.r((View)object2);
            if (object != null) {
                LayoutParams layoutParams = (LayoutParams)object2.getLayoutParams();
                this.e.a((u.e)object);
                this.f(bl, (View)object2, (u.e)object, layoutParams, this.v);
            }
            ++n4;
        }
        return;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void z(f var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        var9_6 /* !! */  = this.w;
        var6_7 = var9_6 /* !! */ .e;
        var7_8 = var9_6 /* !! */ .d;
        var10_9 = e.b.c;
        var8_10 = this.getChildCount();
        if (var2_2 != -2147483648) {
            if (var2_2 != 0) {
                if (var2_2 != 0x40000000) {
                    var9_6 /* !! */  = var10_9;
                    while (true) {
                        var3_3 = 0;
                        break;
                    }
                } else {
                    var3_3 = Math.min(this.h - var7_8, var3_3);
                    var9_6 /* !! */  = var10_9;
                }
            } else {
                var11_11 = e.b.d;
                var9_6 /* !! */  = var11_11;
                if (var8_10 != 0) ** continue;
                var3_3 = Math.max(0, this.f);
                var9_6 /* !! */  = var11_11;
            }
        } else {
            var11_11 = e.b.d;
            var9_6 /* !! */  = var11_11;
            if (var8_10 == 0) {
                var3_3 = Math.max(0, this.f);
                var9_6 /* !! */  = var11_11;
            }
        }
        if (var4_4 != -2147483648) {
            if (var4_4 != 0) {
                if (var4_4 != 0x40000000) {
                    while (true) {
                        var5_5 = 0;
                        break;
                    }
                } else {
                    var5_5 = Math.min(this.i - var6_7, var5_5);
                }
            } else {
                var10_9 = var11_11 = e.b.d;
                if (var8_10 != 0) ** continue;
                var5_5 = Math.max(0, this.g);
                var10_9 = var11_11;
            }
        } else {
            var10_9 = var11_11 = e.b.d;
            if (var8_10 == 0) {
                var5_5 = Math.max(0, this.g);
                var10_9 = var11_11;
            }
        }
        if (var3_3 != var1_1.Y() || var5_5 != var1_1.z()) {
            var1_1.T1();
        }
        var1_1.r1(0);
        var1_1.s1(0);
        var1_1.c1(this.h - var7_8);
        var1_1.b1(this.i - var6_7);
        var1_1.f1(0);
        var1_1.e1(0);
        var1_1.U0((e.b)var9_6 /* !! */ );
        var1_1.p1(var3_3);
        var1_1.l1(var10_9);
        var1_1.Q0(var5_5);
        var1_1.f1(this.f - var7_8);
        var1_1.e1(this.g - var6_7);
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int A;
        public int B;
        public int C;
        public int D = 0;
        public boolean E = true;
        public boolean F = true;
        public float G = 0.5f;
        public float H = 0.5f;
        public String I = null;
        public float J = 0.0f;
        public int K = 1;
        public float L = -1.0f;
        public float M = -1.0f;
        public int N = 0;
        public int O = 0;
        public int P = 0;
        public int Q = 0;
        public int R = 0;
        public int S = 0;
        public int T = 0;
        public int U = 0;
        public float V = 1.0f;
        public float W = 1.0f;
        public int X = -1;
        public int Y = -1;
        public int Z = -1;
        public int a = -1;
        public boolean a0 = false;
        public int b = -1;
        public boolean b0 = false;
        public float c = -1.0f;
        public String c0 = null;
        public boolean d = true;
        public int d0 = 0;
        public int e = -1;
        public boolean e0 = true;
        public int f = -1;
        public boolean f0 = true;
        public int g = -1;
        public boolean g0 = false;
        public int h = -1;
        public boolean h0 = false;
        public int i = -1;
        public boolean i0 = false;
        public int j = -1;
        public boolean j0 = false;
        public int k = -1;
        public boolean k0 = false;
        public int l = -1;
        public int l0 = -1;
        public int m = -1;
        public int m0 = -1;
        public int n = -1;
        public int n0 = -1;
        public int o = -1;
        public int o0 = -1;
        public int p = -1;
        public int p0;
        public int q = 0;
        public int q0;
        public float r = 0.0f;
        public float r0 = 0.5f;
        public int s = -1;
        public int s0;
        public int t = -1;
        public int t0;
        public int u = -1;
        public float u0;
        public int v = -1;
        public u.e v0;
        public int w = Integer.MIN_VALUE;
        public boolean w0 = false;
        public int x = Integer.MIN_VALUE;
        public int y = Integer.MIN_VALUE;
        public int z = Integer.MIN_VALUE;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
            this.A = Integer.MIN_VALUE;
            this.B = Integer.MIN_VALUE;
            this.C = Integer.MIN_VALUE;
            this.p0 = Integer.MIN_VALUE;
            this.q0 = Integer.MIN_VALUE;
            this.v0 = new u.e();
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.A = Integer.MIN_VALUE;
            this.B = Integer.MIN_VALUE;
            this.C = Integer.MIN_VALUE;
            this.p0 = Integer.MIN_VALUE;
            this.q0 = Integer.MIN_VALUE;
            this.v0 = new u.e();
            context = context.obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_Layout);
            int n3 = context.getIndexCount();
            block68: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = context.getIndex(i3);
                int n5 = a.a.get(n4);
                switch (n5) {
                    default: {
                        block44 : switch (n5) {
                            default: {
                                switch (n5) {
                                    default: {
                                        break block44;
                                    }
                                    case 67: {
                                        this.d = context.getBoolean(n4, this.d);
                                        break block44;
                                    }
                                    case 66: {
                                        this.d0 = context.getInt(n4, this.d0);
                                        break block44;
                                    }
                                    case 65: {
                                        androidx.constraintlayout.widget.b.F((Object)this, (TypedArray)context, n4, 1);
                                        this.F = true;
                                        break block44;
                                    }
                                    case 64: 
                                }
                                androidx.constraintlayout.widget.b.F((Object)this, (TypedArray)context, n4, 0);
                                this.E = true;
                                break;
                            }
                            case 55: {
                                this.C = context.getDimensionPixelSize(n4, this.C);
                                break;
                            }
                            case 54: {
                                this.D = context.getDimensionPixelSize(n4, this.D);
                                break;
                            }
                            case 53: {
                                this.o = n5 = context.getResourceId(n4, this.o);
                                if (n5 != -1) continue block68;
                                this.o = context.getInt(n4, -1);
                                break;
                            }
                            case 52: {
                                this.n = n5 = context.getResourceId(n4, this.n);
                                if (n5 != -1) continue block68;
                                this.n = context.getInt(n4, -1);
                                break;
                            }
                            case 51: {
                                this.c0 = context.getString(n4);
                                break;
                            }
                            case 50: {
                                this.Y = context.getDimensionPixelOffset(n4, this.Y);
                                break;
                            }
                            case 49: {
                                this.X = context.getDimensionPixelOffset(n4, this.X);
                                break;
                            }
                            case 48: {
                                this.O = context.getInt(n4, 0);
                                break;
                            }
                            case 47: {
                                this.N = context.getInt(n4, 0);
                                break;
                            }
                            case 46: {
                                this.M = context.getFloat(n4, this.M);
                                break;
                            }
                            case 45: {
                                this.L = context.getFloat(n4, this.L);
                                break;
                            }
                            case 44: {
                                androidx.constraintlayout.widget.b.H(this, context.getString(n4));
                                break;
                            }
                        }
                        continue block68;
                    }
                    case 38: {
                        this.W = Math.max(0.0f, context.getFloat(n4, this.W));
                        this.Q = 2;
                        continue block68;
                    }
                    case 37: {
                        try {
                            this.U = context.getDimensionPixelSize(n4, this.U);
                        }
                        catch (Exception exception) {
                            if (context.getInt(n4, this.U) != -2) continue block68;
                            this.U = -2;
                        }
                        continue block68;
                    }
                    case 36: {
                        try {
                            this.S = context.getDimensionPixelSize(n4, this.S);
                        }
                        catch (Exception exception) {
                            if (context.getInt(n4, this.S) != -2) continue block68;
                            this.S = -2;
                        }
                        continue block68;
                    }
                    case 35: {
                        this.V = Math.max(0.0f, context.getFloat(n4, this.V));
                        this.P = 2;
                        continue block68;
                    }
                    case 34: {
                        try {
                            this.T = context.getDimensionPixelSize(n4, this.T);
                        }
                        catch (Exception exception) {
                            if (context.getInt(n4, this.T) != -2) continue block68;
                            this.T = -2;
                        }
                        continue block68;
                    }
                    case 33: {
                        try {
                            this.R = context.getDimensionPixelSize(n4, this.R);
                        }
                        catch (Exception exception) {
                            if (context.getInt(n4, this.R) != -2) continue block68;
                            this.R = -2;
                        }
                        continue block68;
                    }
                    case 32: {
                        this.Q = n4 = context.getInt(n4, 0);
                        if (n4 != 1) continue block68;
                        Log.e((String)"ConstraintLayout", (String)"layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                        continue block68;
                    }
                    case 31: {
                        this.P = n4 = context.getInt(n4, 0);
                        if (n4 != 1) continue block68;
                        Log.e((String)"ConstraintLayout", (String)"layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                        continue block68;
                    }
                    case 30: {
                        this.H = context.getFloat(n4, this.H);
                        continue block68;
                    }
                    case 29: {
                        this.G = context.getFloat(n4, this.G);
                        continue block68;
                    }
                    case 28: {
                        this.b0 = context.getBoolean(n4, this.b0);
                        continue block68;
                    }
                    case 27: {
                        this.a0 = context.getBoolean(n4, this.a0);
                        continue block68;
                    }
                    case 26: {
                        this.B = context.getDimensionPixelSize(n4, this.B);
                        continue block68;
                    }
                    case 25: {
                        this.A = context.getDimensionPixelSize(n4, this.A);
                        continue block68;
                    }
                    case 24: {
                        this.z = context.getDimensionPixelSize(n4, this.z);
                        continue block68;
                    }
                    case 23: {
                        this.y = context.getDimensionPixelSize(n4, this.y);
                        continue block68;
                    }
                    case 22: {
                        this.x = context.getDimensionPixelSize(n4, this.x);
                        continue block68;
                    }
                    case 21: {
                        this.w = context.getDimensionPixelSize(n4, this.w);
                        continue block68;
                    }
                    case 20: {
                        this.v = n5 = context.getResourceId(n4, this.v);
                        if (n5 != -1) continue block68;
                        this.v = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 19: {
                        this.u = n5 = context.getResourceId(n4, this.u);
                        if (n5 != -1) continue block68;
                        this.u = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 18: {
                        this.t = n5 = context.getResourceId(n4, this.t);
                        if (n5 != -1) continue block68;
                        this.t = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 17: {
                        this.s = n5 = context.getResourceId(n4, this.s);
                        if (n5 != -1) continue block68;
                        this.s = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 16: {
                        this.m = n5 = context.getResourceId(n4, this.m);
                        if (n5 != -1) continue block68;
                        this.m = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 15: {
                        this.l = n5 = context.getResourceId(n4, this.l);
                        if (n5 != -1) continue block68;
                        this.l = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 14: {
                        this.k = n5 = context.getResourceId(n4, this.k);
                        if (n5 != -1) continue block68;
                        this.k = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 13: {
                        this.j = n5 = context.getResourceId(n4, this.j);
                        if (n5 != -1) continue block68;
                        this.j = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 12: {
                        this.i = n5 = context.getResourceId(n4, this.i);
                        if (n5 != -1) continue block68;
                        this.i = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 11: {
                        this.h = n5 = context.getResourceId(n4, this.h);
                        if (n5 != -1) continue block68;
                        this.h = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 10: {
                        this.g = n5 = context.getResourceId(n4, this.g);
                        if (n5 != -1) continue block68;
                        this.g = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 9: {
                        this.f = n5 = context.getResourceId(n4, this.f);
                        if (n5 != -1) continue block68;
                        this.f = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 8: {
                        this.e = n5 = context.getResourceId(n4, this.e);
                        if (n5 != -1) continue block68;
                        this.e = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 7: {
                        this.c = context.getFloat(n4, this.c);
                        continue block68;
                    }
                    case 6: {
                        this.b = context.getDimensionPixelOffset(n4, this.b);
                        continue block68;
                    }
                    case 5: {
                        this.a = context.getDimensionPixelOffset(n4, this.a);
                        continue block68;
                    }
                    case 4: {
                        float f3;
                        this.r = f3 = context.getFloat(n4, this.r) % 360.0f;
                        if (!(f3 < 0.0f)) continue block68;
                        this.r = (360.0f - f3) % 360.0f;
                        continue block68;
                    }
                    case 3: {
                        this.q = context.getDimensionPixelSize(n4, this.q);
                        continue block68;
                    }
                    case 2: {
                        this.p = n5 = context.getResourceId(n4, this.p);
                        if (n5 != -1) continue block68;
                        this.p = context.getInt(n4, -1);
                        continue block68;
                    }
                    case 1: {
                        this.Z = context.getInt(n4, this.Z);
                    }
                }
            }
            context.recycle();
            this.c();
        }

        public LayoutParams(ViewGroup.LayoutParams object) {
            super(object);
            this.A = Integer.MIN_VALUE;
            this.B = Integer.MIN_VALUE;
            this.C = Integer.MIN_VALUE;
            this.p0 = Integer.MIN_VALUE;
            this.q0 = Integer.MIN_VALUE;
            this.v0 = new u.e();
            if (object instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)object;
                this.leftMargin = marginLayoutParams.leftMargin;
                this.rightMargin = marginLayoutParams.rightMargin;
                this.topMargin = marginLayoutParams.topMargin;
                this.bottomMargin = marginLayoutParams.bottomMargin;
                this.setMarginStart(marginLayoutParams.getMarginStart());
                this.setMarginEnd(marginLayoutParams.getMarginEnd());
            }
            if (!(object instanceof LayoutParams)) {
                return;
            }
            object = (LayoutParams)((Object)object);
            this.a = object.a;
            this.b = object.b;
            this.c = object.c;
            this.d = object.d;
            this.e = object.e;
            this.f = object.f;
            this.g = object.g;
            this.h = object.h;
            this.i = object.i;
            this.j = object.j;
            this.k = object.k;
            this.l = object.l;
            this.m = object.m;
            this.n = object.n;
            this.o = object.o;
            this.p = object.p;
            this.q = object.q;
            this.r = object.r;
            this.s = object.s;
            this.t = object.t;
            this.u = object.u;
            this.v = object.v;
            this.w = object.w;
            this.x = object.x;
            this.y = object.y;
            this.z = object.z;
            this.A = object.A;
            this.B = object.B;
            this.C = object.C;
            this.D = object.D;
            this.G = object.G;
            this.H = object.H;
            this.I = object.I;
            this.J = object.J;
            this.K = object.K;
            this.L = object.L;
            this.M = object.M;
            this.N = object.N;
            this.O = object.O;
            this.a0 = object.a0;
            this.b0 = object.b0;
            this.P = object.P;
            this.Q = object.Q;
            this.R = object.R;
            this.T = object.T;
            this.S = object.S;
            this.U = object.U;
            this.V = object.V;
            this.W = object.W;
            this.X = object.X;
            this.Y = object.Y;
            this.Z = object.Z;
            this.e0 = object.e0;
            this.f0 = object.f0;
            this.g0 = object.g0;
            this.h0 = object.h0;
            this.l0 = object.l0;
            this.m0 = object.m0;
            this.n0 = object.n0;
            this.o0 = object.o0;
            this.p0 = object.p0;
            this.q0 = object.q0;
            this.r0 = object.r0;
            this.c0 = object.c0;
            this.d0 = object.d0;
            this.v0 = object.v0;
            this.E = object.E;
            this.F = object.F;
        }

        public String a() {
            return this.c0;
        }

        public u.e b() {
            return this.v0;
        }

        public void c() {
            int n3;
            this.h0 = false;
            this.e0 = true;
            this.f0 = true;
            int n4 = this.width;
            if (n4 == -2 && this.a0) {
                this.e0 = false;
                if (this.P == 0) {
                    this.P = 1;
                }
            }
            if ((n3 = this.height) == -2 && this.b0) {
                this.f0 = false;
                if (this.Q == 0) {
                    this.Q = 1;
                }
            }
            if (n4 == 0 || n4 == -1) {
                this.e0 = false;
                if (n4 == 0 && this.P == 1) {
                    this.width = -2;
                    this.a0 = true;
                }
            }
            if (n3 == 0 || n3 == -1) {
                this.f0 = false;
                if (n3 == 0 && this.Q == 1) {
                    this.height = -2;
                    this.b0 = true;
                }
            }
            if (this.c == -1.0f && this.a == -1 && this.b == -1) {
                return;
            }
            this.h0 = true;
            this.e0 = true;
            this.f0 = true;
            if (!(this.v0 instanceof h)) {
                this.v0 = new h();
            }
            ((h)this.v0).F1(this.Z);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void resolveLayoutDirection(int n3) {
            int n4;
            int n5;
            block33: {
                block29: {
                    int n6;
                    int n7;
                    block34: {
                        float f3;
                        float f4;
                        int n8;
                        block32: {
                            block31: {
                                block30: {
                                    n5 = this.leftMargin;
                                    n4 = this.rightMargin;
                                    super.resolveLayoutDirection(n3);
                                    n3 = this.getLayoutDirection();
                                    n8 = 0;
                                    n3 = 1 == n3 ? 1 : 0;
                                    this.n0 = -1;
                                    this.o0 = -1;
                                    this.l0 = -1;
                                    this.m0 = -1;
                                    this.p0 = this.w;
                                    this.q0 = this.y;
                                    this.r0 = f4 = this.G;
                                    this.s0 = n7 = this.a;
                                    this.t0 = n6 = this.b;
                                    this.u0 = f3 = this.c;
                                    if (n3 == 0) break block29;
                                    n3 = this.s;
                                    if (n3 == -1) break block30;
                                    this.n0 = n3;
                                    break block31;
                                }
                                int n9 = this.t;
                                n3 = n8;
                                if (n9 == -1) break block32;
                                this.o0 = n9;
                            }
                            n3 = 1;
                        }
                        if ((n8 = this.u) != -1) {
                            this.m0 = n8;
                            n3 = 1;
                        }
                        if ((n8 = this.v) != -1) {
                            this.l0 = n8;
                            n3 = 1;
                        }
                        if ((n8 = this.A) != Integer.MIN_VALUE) {
                            this.q0 = n8;
                        }
                        if ((n8 = this.B) != Integer.MIN_VALUE) {
                            this.p0 = n8;
                        }
                        if (n3 != 0) {
                            this.r0 = 1.0f - f4;
                        }
                        if (!this.h0 || this.Z != 1 || !this.d) break block33;
                        if (f3 == -1.0f) break block34;
                        this.u0 = 1.0f - f3;
                        this.s0 = -1;
                        this.t0 = -1;
                        break block33;
                    }
                    if (n7 != -1) {
                        this.t0 = n7;
                        this.s0 = -1;
                        this.u0 = -1.0f;
                        break block33;
                    } else if (n6 != -1) {
                        this.s0 = n6;
                        this.t0 = -1;
                        this.u0 = -1.0f;
                    }
                    break block33;
                }
                n3 = this.s;
                if (n3 != -1) {
                    this.m0 = n3;
                }
                if ((n3 = this.t) != -1) {
                    this.l0 = n3;
                }
                if ((n3 = this.u) != -1) {
                    this.n0 = n3;
                }
                if ((n3 = this.v) != -1) {
                    this.o0 = n3;
                }
                if ((n3 = this.A) != Integer.MIN_VALUE) {
                    this.p0 = n3;
                }
                if ((n3 = this.B) != Integer.MIN_VALUE) {
                    this.q0 = n3;
                }
            }
            if (this.u != -1 || this.v != -1 || this.t != -1 || this.s != -1) return;
            n3 = this.g;
            if (n3 != -1) {
                this.n0 = n3;
                if (this.rightMargin <= 0 && n4 > 0) {
                    this.rightMargin = n4;
                }
            } else {
                n3 = this.h;
                if (n3 != -1) {
                    this.o0 = n3;
                    if (this.rightMargin <= 0 && n4 > 0) {
                        this.rightMargin = n4;
                    }
                }
            }
            if ((n3 = this.e) != -1) {
                this.l0 = n3;
                if (this.leftMargin > 0 || n5 <= 0) return;
                this.leftMargin = n5;
                return;
            } else {
                n3 = this.f;
                if (n3 == -1) return;
                this.m0 = n3;
                if (this.leftMargin > 0 || n5 <= 0) return;
                this.leftMargin = n5;
            }
        }

        public static abstract class a {
            public static final SparseIntArray a;

            static {
                SparseIntArray sparseIntArray;
                a = sparseIntArray = new SparseIntArray();
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintWidth, 64);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintHeight, 65);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintBaseline_toTopOf, 52);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintBaseline_toBottomOf, 53);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintCircle, 2);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_guidelineUseRtl, 67);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_android_orientation, 1);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_goneMarginBaseline, 55);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_marginBaseline, 54);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_constraintTag, 51);
                sparseIntArray.append(y.d.ConstraintLayout_Layout_layout_wrapBehaviorInParent, 66);
            }
        }
    }

    public class b
    implements b.b {
        public ConstraintLayout a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public final ConstraintLayout h;

        public b(ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2) {
            this.h = constraintLayout;
            this.a = constraintLayout2;
        }

        @Override
        public final void a() {
            int n3;
            int n4 = this.a.getChildCount();
            int n5 = 0;
            for (n3 = 0; n3 < n4; ++n3) {
                View view = this.a.getChildAt(n3);
                if (!(view instanceof Placeholder)) continue;
                ((Placeholder)view).b(this.a);
            }
            n4 = this.a.d.size();
            if (n4 > 0) {
                for (n3 = n5; n3 < n4; ++n3) {
                    ((ConstraintHelper)((Object)this.a.d.get(n3))).s(this.a);
                }
            }
        }

        @Override
        public final void b(u.e e3, b.a a4) {
            int n3;
            int n4;
            int n5;
            Object object;
            block44: {
                int n6;
                int n7;
                View view;
                int n8;
                int n9;
                int n10;
                int n11;
                block45: {
                    boolean bl;
                    boolean bl2;
                    boolean bl3;
                    Object object2;
                    block43: {
                        block42: {
                            block27: {
                                Object object3;
                                block39: {
                                    block35: {
                                        block36: {
                                            block37: {
                                                block41: {
                                                    block40: {
                                                        block38: {
                                                            block32: {
                                                                block28: {
                                                                    block29: {
                                                                        block30: {
                                                                            block34: {
                                                                                block33: {
                                                                                    block31: {
                                                                                        if (e3 == null) break block27;
                                                                                        if (e3.X() == 8 && !e3.l0()) {
                                                                                            a4.e = 0;
                                                                                            a4.f = 0;
                                                                                            a4.g = 0;
                                                                                            return;
                                                                                        }
                                                                                        if (e3.M() == null) break block27;
                                                                                        ConstraintLayout.c(this.h);
                                                                                        object2 = a4.a;
                                                                                        object = a4.b;
                                                                                        n11 = a4.c;
                                                                                        n10 = a4.d;
                                                                                        n9 = this.b + this.c;
                                                                                        n8 = this.d;
                                                                                        view = (View)e3.u();
                                                                                        object3 = androidx.constraintlayout.widget.ConstraintLayout$a.a;
                                                                                        n5 = object3[object2.ordinal()];
                                                                                        if (n5 == 1) break block28;
                                                                                        if (n5 == 2) break block29;
                                                                                        if (n5 == 3) break block30;
                                                                                        if (n5 == 4) break block31;
                                                                                        n8 = 0;
                                                                                        break block32;
                                                                                    }
                                                                                    n5 = ViewGroup.getChildMeasureSpec((int)this.f, (int)n8, (int)-2);
                                                                                    n11 = e3.w == 1 ? 1 : 0;
                                                                                    n4 = a4.j;
                                                                                    if (n4 == b.a.l) break block33;
                                                                                    n8 = n5;
                                                                                    if (n4 != b.a.m) break block32;
                                                                                }
                                                                                n8 = view.getMeasuredHeight() == e3.z() ? 1 : 0;
                                                                                if (a4.j == b.a.m || n11 == 0 || n11 != 0 && n8 != 0 || view instanceof Placeholder) break block34;
                                                                                n8 = n5;
                                                                                if (!e3.p0()) break block32;
                                                                            }
                                                                            n8 = View.MeasureSpec.makeMeasureSpec((int)e3.Y(), (int)0x40000000);
                                                                            break block32;
                                                                        }
                                                                        n8 = ViewGroup.getChildMeasureSpec((int)this.f, (int)(n8 + e3.D()), (int)-1);
                                                                        break block32;
                                                                    }
                                                                    n8 = ViewGroup.getChildMeasureSpec((int)this.f, (int)n8, (int)-2);
                                                                    break block32;
                                                                }
                                                                n8 = View.MeasureSpec.makeMeasureSpec((int)n11, (int)0x40000000);
                                                            }
                                                            n11 = object3[object.ordinal()];
                                                            if (n11 == 1) break block35;
                                                            if (n11 == 2) break block36;
                                                            if (n11 == 3) break block37;
                                                            if (n11 == 4) break block38;
                                                            n11 = 0;
                                                            break block39;
                                                        }
                                                        n10 = ViewGroup.getChildMeasureSpec((int)this.g, (int)n9, (int)-2);
                                                        n5 = e3.x == 1 ? 1 : 0;
                                                        n9 = a4.j;
                                                        if (n9 == b.a.l) break block40;
                                                        n11 = n10;
                                                        if (n9 != b.a.m) break block39;
                                                    }
                                                    n11 = view.getMeasuredWidth() == e3.Y() ? 1 : 0;
                                                    if (a4.j == b.a.m || n5 == 0 || n5 != 0 && n11 != 0 || view instanceof Placeholder) break block41;
                                                    n11 = n10;
                                                    if (!e3.q0()) break block39;
                                                }
                                                n11 = View.MeasureSpec.makeMeasureSpec((int)e3.z(), (int)0x40000000);
                                                break block39;
                                            }
                                            n11 = ViewGroup.getChildMeasureSpec((int)this.g, (int)(n9 + e3.W()), (int)-1);
                                            break block39;
                                        }
                                        n11 = ViewGroup.getChildMeasureSpec((int)this.g, (int)n9, (int)-2);
                                        break block39;
                                    }
                                    n11 = View.MeasureSpec.makeMeasureSpec((int)n10, (int)0x40000000);
                                }
                                object3 = (f)e3.M();
                                if (object3 != null && u.k.b(this.h.k, 256) && view.getMeasuredWidth() == e3.Y() && view.getMeasuredWidth() < ((u.e)object3).Y() && view.getMeasuredHeight() == e3.z() && view.getMeasuredHeight() < ((u.e)object3).z() && view.getBaseline() == e3.r() && !e3.o0() && this.d(e3.E(), n8, e3.Y()) && this.d(e3.F(), n11, e3.z())) {
                                    a4.e = e3.Y();
                                    a4.f = e3.z();
                                    a4.g = e3.r();
                                    return;
                                }
                                object3 = (Object)e.b.e;
                                n5 = object2 == object3 ? 1 : 0;
                                n10 = object == object3 ? 1 : 0;
                                object3 = (Object)e.b.f;
                                n3 = object != object3 && object != e.b.c ? 0 : 1;
                                bl3 = object2 == object3 || object2 == e.b.c;
                                bl2 = n5 != 0 && e3.f0 > 0.0f;
                                bl = n10 != 0 && e3.f0 > 0.0f;
                                if (view != null) break block42;
                            }
                            return;
                        }
                        object = (LayoutParams)view.getLayoutParams();
                        n9 = a4.j;
                        if (n9 == b.a.l || n9 == b.a.m || n5 == 0 || e3.w != 0 || n10 == 0 || e3.x != 0) break block43;
                        n5 = 0;
                        n3 = 0;
                        n4 = 0;
                        break block44;
                    }
                    if (view instanceof VirtualLayout && e3 instanceof m) {
                        object2 = (m)e3;
                        ((VirtualLayout)view).x((m)object2, n8, n11);
                    } else {
                        view.measure(n8, n11);
                    }
                    e3.a1(n8, n11);
                    n7 = view.getMeasuredWidth();
                    n6 = view.getMeasuredHeight();
                    int n12 = view.getBaseline();
                    n5 = e3.z;
                    n10 = n5 > 0 ? Math.max(n5, n7) : n7;
                    n9 = e3.A;
                    n5 = n10;
                    if (n9 > 0) {
                        n5 = Math.min(n9, n10);
                    }
                    n10 = (n10 = e3.C) > 0 ? Math.max(n10, n6) : n6;
                    n9 = e3.D;
                    n4 = n10;
                    if (n9 > 0) {
                        n4 = Math.min(n9, n10);
                    }
                    n10 = n4;
                    n9 = n5;
                    if (!u.k.b(this.h.k, 1)) {
                        if (bl2 && n3 != 0) {
                            float f3 = e3.f0;
                            n9 = (int)((float)n4 * f3 + 0.5f);
                            n10 = n4;
                        } else {
                            n10 = n4;
                            n9 = n5;
                            if (bl) {
                                n10 = n4;
                                n9 = n5;
                                if (bl3) {
                                    float f4 = e3.f0;
                                    n10 = (int)((float)n5 / f4 + 0.5f);
                                    n9 = n5;
                                }
                            }
                        }
                    }
                    if (n7 != n9) break block45;
                    n5 = n10;
                    n3 = n12;
                    n4 = n9;
                    if (n6 == n10) break block44;
                }
                if (n7 != n9) {
                    n8 = View.MeasureSpec.makeMeasureSpec((int)n9, (int)0x40000000);
                }
                if (n6 != n10) {
                    n11 = View.MeasureSpec.makeMeasureSpec((int)n10, (int)0x40000000);
                }
                view.measure(n8, n11);
                e3.a1(n8, n11);
                n4 = view.getMeasuredWidth();
                n5 = view.getMeasuredHeight();
                n3 = view.getBaseline();
            }
            boolean bl = n3 != -1;
            boolean bl4 = n4 != a4.c || n5 != a4.d;
            a4.i = bl4;
            if (((LayoutParams)((Object)object)).g0) {
                bl = true;
            }
            if (bl && n3 != -1 && e3.r() != n3) {
                a4.i = true;
            }
            a4.e = n4;
            a4.f = n5;
            a4.h = bl;
            a4.g = n3;
            ConstraintLayout.c(this.h);
        }

        public void c(int n3, int n4, int n5, int n6, int n7, int n8) {
            this.b = n5;
            this.c = n6;
            this.d = n7;
            this.e = n8;
            this.f = n3;
            this.g = n4;
        }

        public final boolean d(int n3, int n4, int n5) {
            if (n3 == n4) {
                return true;
            }
            n3 = View.MeasureSpec.getMode((int)n3);
            int n6 = View.MeasureSpec.getMode((int)n4);
            n4 = View.MeasureSpec.getSize((int)n4);
            return n6 == 0x40000000 && (n3 == Integer.MIN_VALUE || n3 == 0) && n5 == n4;
        }
    }
}

