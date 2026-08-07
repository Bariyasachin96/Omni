/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.Region$Op
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.customview.view.AbsSavedState;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import o0.c0;
import o0.d0;
import o0.e0;
import o0.f0;
import o0.s;
import o0.x0;
import o0.z1;
import z.a;

public class CoordinatorLayout
extends ViewGroup
implements c0,
d0 {
    public static final n0.e A;
    public static final String w;
    public static final Class[] x;
    public static final ThreadLocal y;
    public static final Comparator z;
    public final List c = new ArrayList();
    public final a0.b d = new a0.b();
    public final List e = new ArrayList();
    public final List f = new ArrayList();
    public Paint g;
    public final int[] h = new int[2];
    public final int[] i = new int[2];
    public boolean j;
    public boolean k;
    public int[] l;
    public View m;
    public View n;
    public f o;
    public boolean p;
    public z1 q;
    public boolean r;
    public Drawable s;
    public ViewGroup.OnHierarchyChangeListener t;
    public f0 u;
    public final e0 v = new e0(this);

    static {
        Object object = CoordinatorLayout.class.getPackage();
        object = object != null ? ((Package)object).getName() : null;
        w = object;
        z = new g();
        x = new Class[]{Context.class, AttributeSet.class};
        y = new ThreadLocal();
        A = new n0.g(12);
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.coordinatorLayoutStyle);
    }

    public CoordinatorLayout(Context object, AttributeSet attributeSet, int n3) {
        super(object, attributeSet, n3);
        int n4 = 0;
        TypedArray typedArray = n3 == 0 ? object.obtainStyledAttributes(attributeSet, z.c.CoordinatorLayout, 0, z.b.Widget_Support_CoordinatorLayout) : object.obtainStyledAttributes(attributeSet, z.c.CoordinatorLayout, n3, 0);
        if (Build.VERSION.SDK_INT >= 29) {
            if (n3 == 0) {
                a0.a.a(this, object, z.c.CoordinatorLayout, attributeSet, typedArray, 0, z.b.Widget_Support_CoordinatorLayout);
            } else {
                a0.a.a(this, object, z.c.CoordinatorLayout, attributeSet, typedArray, n3, 0);
            }
        }
        if ((n3 = typedArray.getResourceId(z.c.CoordinatorLayout_keylines, 0)) != 0) {
            object = object.getResources();
            this.l = object.getIntArray(n3);
            float f3 = object.getDisplayMetrics().density;
            int n5 = this.l.length;
            for (n3 = n4; n3 < n5; ++n3) {
                object = this.l;
                object[n3] = (Context)((int)((float)object[n3] * f3));
            }
        }
        this.s = typedArray.getDrawable(z.c.CoordinatorLayout_statusBarBackground);
        typedArray.recycle();
        this.b0();
        super.setOnHierarchyChangeListener((ViewGroup.OnHierarchyChangeListener)new d(this));
        if (x0.w((View)this) == 0) {
            x0.o0((View)this, 1);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Behavior O(Context object, AttributeSet attributeSet, String hashMap) {
        Exception exception2;
        CharSequence charSequence;
        block10: {
            Object object2;
            Constructor<?> constructor;
            block9: {
                if (TextUtils.isEmpty((CharSequence)((Object)hashMap))) {
                    return null;
                }
                if (((String)((Object)hashMap)).startsWith(".")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(object.getPackageName());
                    ((StringBuilder)charSequence).append((String)((Object)hashMap));
                    charSequence = ((StringBuilder)charSequence).toString();
                } else if (((String)((Object)hashMap)).indexOf(46) >= 0) {
                    charSequence = hashMap;
                } else {
                    constructor = w;
                    charSequence = hashMap;
                    if (!TextUtils.isEmpty((CharSequence)((Object)constructor))) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append((String)((Object)constructor));
                        ((StringBuilder)charSequence).append('.');
                        ((StringBuilder)charSequence).append((String)((Object)hashMap));
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                }
                try {
                    object2 = y;
                    constructor = (Map)((ThreadLocal)object2).get();
                    hashMap = constructor;
                    if (constructor != null) break block9;
                    hashMap = new HashMap();
                    ((ThreadLocal)object2).set(hashMap);
                }
                catch (Exception exception2) {
                    break block10;
                }
            }
            object2 = (Constructor)hashMap.get(charSequence);
            constructor = object2;
            if (object2 == null) {
                constructor = Class.forName((String)charSequence, false, object.getClassLoader()).getConstructor(x);
                ((AccessibleObject)constructor).setAccessible(true);
                hashMap.put(charSequence, constructor);
            }
            return (Behavior)constructor.newInstance(object, attributeSet);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not inflate Behavior subclass ");
        ((StringBuilder)object).append((String)charSequence);
        throw new RuntimeException(((StringBuilder)object).toString(), exception2);
    }

    public static void S(Rect rect) {
        rect.setEmpty();
        A.a(rect);
    }

    public static int V(int n3) {
        int n4 = n3;
        if (n3 == 0) {
            n4 = 17;
        }
        return n4;
    }

    public static int W(int n3) {
        int n4 = n3;
        if ((n3 & 7) == 0) {
            n4 = n3 | 0x800003;
        }
        n3 = n4;
        if ((n4 & 0x70) == 0) {
            n3 = n4 | 0x30;
        }
        return n3;
    }

    public static int X(int n3) {
        int n4 = n3;
        if (n3 == 0) {
            n4 = 8388661;
        }
        return n4;
    }

    public static Rect f() {
        Rect rect;
        Rect rect2 = rect = (Rect)A.b();
        if (rect == null) {
            rect2 = new Rect();
        }
        return rect2;
    }

    private static int j(int n3, int n4, int n5) {
        if (n3 < n4) {
            return n4;
        }
        if (n3 > n5) {
            return n5;
        }
        return n3;
    }

    public final int A(int n3) {
        Object object = this.l;
        if (object == null) {
            object = new StringBuilder();
            object.append("No keylines defined for ");
            object.append(this);
            object.append(" - attempted index lookup ");
            object.append(n3);
            Log.e((String)"CoordinatorLayout", (String)object.toString());
            return 0;
        }
        if (n3 >= 0 && n3 < ((int[])object).length) {
            return object[n3];
        }
        object = new StringBuilder();
        object.append("Keyline index ");
        object.append(n3);
        object.append(" out of range for ");
        object.append(this);
        Log.e((String)"CoordinatorLayout", (String)object.toString());
        return 0;
    }

    public void B(View view, Rect rect) {
        rect.set(((e)view.getLayoutParams()).h());
    }

    public e C(View object) {
        e e3 = (e)object.getLayoutParams();
        if (!e3.b) {
            if (object instanceof b) {
                if ((object = ((b)object).getBehavior()) == null) {
                    Log.e((String)"CoordinatorLayout", (String)"Attached behavior class is null");
                }
                e3.o((Behavior)object);
                e3.b = true;
                return e3;
            }
            Serializable serializable = object.getClass();
            object = null;
            while (serializable != null) {
                c c3 = ((Class)serializable).getAnnotation(c.class);
                object = c3;
                if (c3 != null) break;
                serializable = ((Class)serializable).getSuperclass();
                object = c3;
            }
            if (object != null) {
                try {
                    e3.o((Behavior)object.value().getDeclaredConstructor(null).newInstance(null));
                }
                catch (Exception exception) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Default behavior class ");
                    ((StringBuilder)serializable).append(object.value().getName());
                    ((StringBuilder)serializable).append(" could not be instantiated. Did you forget a default constructor?");
                    Log.e((String)"CoordinatorLayout", (String)((StringBuilder)serializable).toString(), (Throwable)exception);
                }
            }
            e3.b = true;
        }
        return e3;
    }

    public final void D(List list) {
        list.clear();
        boolean bl = this.isChildrenDrawingOrderEnabled();
        int n3 = this.getChildCount();
        for (int i3 = n3 - 1; i3 >= 0; --i3) {
            int n4 = bl ? this.getChildDrawingOrder(n3, i3) : i3;
            list.add(this.getChildAt(n4));
        }
        Comparator comparator = z;
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
    }

    public final boolean E(View view) {
        return this.d.j(view);
    }

    public boolean F(View view, int n3, int n4) {
        Rect rect = CoordinatorLayout.f();
        this.x(view, rect);
        try {
            boolean bl = rect.contains(n3, n4);
            return bl;
        }
        finally {
            CoordinatorLayout.S(rect);
        }
    }

    public final void G(View view, int n3) {
        e e3 = (e)view.getLayoutParams();
        Rect rect = CoordinatorLayout.f();
        rect.set(this.getPaddingLeft() + e3.leftMargin, this.getPaddingTop() + e3.topMargin, this.getWidth() - this.getPaddingRight() - e3.rightMargin, this.getHeight() - this.getPaddingBottom() - e3.bottomMargin);
        if (this.q != null && x0.v((View)this) && !x0.v(view)) {
            rect.left += this.q.j();
            rect.top += this.q.l();
            rect.right -= this.q.k();
            rect.bottom -= this.q.i();
        }
        Rect rect2 = CoordinatorLayout.f();
        o0.s.a(CoordinatorLayout.W(e3.c), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, n3);
        view.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
        CoordinatorLayout.S(rect);
        CoordinatorLayout.S(rect2);
    }

    public final void H(View view, View view2, int n3) {
        Rect rect = CoordinatorLayout.f();
        Rect rect2 = CoordinatorLayout.f();
        try {
            this.x(view2, rect);
            this.y(view, n3, rect, rect2);
            view.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
            return;
        }
        finally {
            CoordinatorLayout.S(rect);
            CoordinatorLayout.S(rect2);
        }
    }

    public final void I(View view, int n3, int n4) {
        e e3 = (e)view.getLayoutParams();
        int n5 = o0.s.b(CoordinatorLayout.X(e3.c), n4);
        int n6 = n5 & 7;
        int n7 = n5 & 0x70;
        int n8 = this.getWidth();
        int n9 = this.getHeight();
        int n10 = view.getMeasuredWidth();
        int n11 = view.getMeasuredHeight();
        n5 = n3;
        if (n4 == 1) {
            n5 = n8 - n3;
        }
        n3 = this.A(n5) - n10;
        if (n6 != 1) {
            if (n6 == 5) {
                n3 += n10;
            }
        } else {
            n3 += n10 / 2;
        }
        n4 = n7 != 16 ? (n7 != 80 ? 0 : n11) : n11 / 2;
        n3 = Math.max(this.getPaddingLeft() + e3.leftMargin, Math.min(n3, n8 - this.getPaddingRight() - n10 - e3.rightMargin));
        n4 = Math.max(this.getPaddingTop() + e3.topMargin, Math.min(n4, n9 - this.getPaddingBottom() - n11 - e3.bottomMargin));
        view.layout(n3, n4, n10 + n3, n11 + n4);
    }

    public final void J(View object, Rect rect, int n3) {
        if (x0.O((View)object) && object.getWidth() > 0 && object.getHeight() > 0) {
            int n4;
            e e3 = (e)object.getLayoutParams();
            Behavior behavior = e3.f();
            Rect rect2 = CoordinatorLayout.f();
            Rect rect3 = CoordinatorLayout.f();
            rect3.set(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
            if (behavior != null && behavior.f(this, (View)object, rect2)) {
                if (!rect3.contains(rect2)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Rect should be within the child's bounds. Rect:");
                    ((StringBuilder)object).append(rect2.toShortString());
                    ((StringBuilder)object).append(" | Bounds:");
                    ((StringBuilder)object).append(rect3.toShortString());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            } else {
                rect2.set(rect3);
            }
            CoordinatorLayout.S(rect3);
            if (rect2.isEmpty()) {
                CoordinatorLayout.S(rect2);
                return;
            }
            int n5 = o0.s.b(e3.h, n3);
            int n6 = 1;
            if ((n5 & 0x30) == 48 && (n4 = rect2.top - e3.topMargin - e3.j) < (n3 = rect.top)) {
                this.Z((View)object, n3 - n4);
                n3 = 1;
            } else {
                n3 = 0;
            }
            n4 = n3;
            if ((n5 & 0x50) == 80) {
                int n7 = this.getHeight() - rect2.bottom - e3.bottomMargin + e3.j;
                int n8 = rect.bottom;
                n4 = n3;
                if (n7 < n8) {
                    this.Z((View)object, n7 - n8);
                    n4 = 1;
                }
            }
            if (n4 == 0) {
                this.Z((View)object, 0);
            }
            if ((n5 & 3) == 3 && (n3 = rect2.left - e3.leftMargin - e3.i) < (n4 = rect.left)) {
                this.Y((View)object, n4 - n3);
                n3 = 1;
            } else {
                n3 = 0;
            }
            if ((n5 & 5) == 5 && (n4 = this.getWidth() - rect2.right - e3.rightMargin + e3.i) < (n5 = rect.right)) {
                this.Y((View)object, n4 - n5);
                n3 = n6;
            }
            if (n3 == 0) {
                this.Y((View)object, 0);
            }
            CoordinatorLayout.S(rect2);
        }
    }

    public void K(View view, int n3) {
        block6: {
            Behavior behavior;
            int n4;
            int n5;
            int n6;
            Rect rect;
            Rect rect2;
            Rect rect3;
            e e3;
            block8: {
                block7: {
                    e3 = (e)view.getLayoutParams();
                    if (e3.k == null) break block6;
                    rect3 = CoordinatorLayout.f();
                    rect2 = CoordinatorLayout.f();
                    rect = CoordinatorLayout.f();
                    this.x(e3.k, rect3);
                    n6 = 0;
                    this.u(view, false, rect2);
                    n5 = view.getMeasuredWidth();
                    n4 = view.getMeasuredHeight();
                    this.z(view, n3, rect3, rect, e3, n5, n4);
                    if (rect.left != rect2.left) break block7;
                    n3 = n6;
                    if (rect.top == rect2.top) break block8;
                }
                n3 = 1;
            }
            this.n(e3, rect, n5, n4);
            n4 = rect.left - rect2.left;
            n6 = rect.top - rect2.top;
            if (n4 != 0) {
                x0.R(view, n4);
            }
            if (n6 != 0) {
                x0.S(view, n6);
            }
            if (n3 != 0 && (behavior = e3.f()) != null) {
                behavior.l(this, view, e3.k);
            }
            CoordinatorLayout.S(rect3);
            CoordinatorLayout.S(rect2);
            CoordinatorLayout.S(rect);
            return;
        }
    }

    public final void L(int n3) {
        int n4 = x0.y((View)this);
        int n5 = this.c.size();
        Rect rect = CoordinatorLayout.f();
        Rect rect2 = CoordinatorLayout.f();
        Rect rect3 = CoordinatorLayout.f();
        for (int i3 = 0; i3 < n5; ++i3) {
            Object object;
            int n6;
            View view = (View)this.c.get(i3);
            Object object2 = (e)view.getLayoutParams();
            if (n3 == 0 && view.getVisibility() == 8) continue;
            for (n6 = 0; n6 < i3; ++n6) {
                object = (View)this.c.get(n6);
                if (((e)((Object)object2)).l != object) continue;
                this.K(view, n4);
            }
            this.u(view, true, rect2);
            if (((e)((Object)object2)).g != 0 && !rect2.isEmpty()) {
                n6 = o0.s.b(((e)((Object)object2)).g, n4);
                int n7 = n6 & 0x70;
                if (n7 != 48) {
                    if (n7 == 80) {
                        rect.bottom = Math.max(rect.bottom, this.getHeight() - rect2.top);
                    }
                } else {
                    rect.top = Math.max(rect.top, rect2.bottom);
                }
                if ((n6 &= 7) != 3) {
                    if (n6 == 5) {
                        rect.right = Math.max(rect.right, this.getWidth() - rect2.left);
                    }
                } else {
                    rect.left = Math.max(rect.left, rect2.right);
                }
            }
            if (((e)((Object)object2)).h != 0 && view.getVisibility() == 0) {
                this.J(view, rect, n4);
            }
            if (n3 != 2) {
                this.B(view, rect3);
                if (rect3.equals((Object)rect2)) continue;
                this.R(view, rect2);
            }
            for (n6 = i3 + 1; n6 < n5; ++n6) {
                boolean bl;
                View view2 = (View)this.c.get(n6);
                object = (e)view2.getLayoutParams();
                object2 = object.f();
                if (object2 == null || !((Behavior)object2).i(this, view2, view)) continue;
                if (n3 == 0 && object.g()) {
                    object.k();
                    continue;
                }
                if (n3 != 2) {
                    bl = ((Behavior)object2).l(this, view2, view);
                } else {
                    ((Behavior)object2).m(this, view2, view);
                    bl = true;
                }
                if (n3 != 1) continue;
                object.p(bl);
            }
        }
        CoordinatorLayout.S(rect);
        CoordinatorLayout.S(rect2);
        CoordinatorLayout.S(rect3);
    }

    public void M(View view, int n3) {
        e e3 = (e)view.getLayoutParams();
        if (!e3.a()) {
            View view2 = e3.k;
            if (view2 != null) {
                this.H(view, view2, n3);
                return;
            }
            int n4 = e3.e;
            if (n4 >= 0) {
                this.I(view, n4, n3);
                return;
            }
            this.G(view, n3);
            return;
        }
        throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
    }

    public void N(View view, int n3, int n4, int n5, int n6) {
        this.measureChildWithMargins(view, n3, n4, n5, n6);
    }

    public final boolean P(MotionEvent motionEvent, int n3) {
        boolean bl;
        boolean bl2;
        int n4 = motionEvent.getActionMasked();
        List list = this.e;
        this.D(list);
        int n5 = list.size();
        e e3 = null;
        int n6 = 0;
        boolean bl3 = bl2 = false;
        while (true) {
            boolean bl4;
            bl = bl2;
            if (n6 >= n5) break;
            View view = (View)list.get(n6);
            e e4 = (e)view.getLayoutParams();
            Behavior behavior = e4.f();
            if ((bl2 || bl3) && n4 != 0) {
                e4 = e3;
                bl4 = bl2;
                bl = bl3;
                if (behavior != null) {
                    e4 = e3;
                    if (e3 == null) {
                        long l3 = SystemClock.uptimeMillis();
                        e4 = MotionEvent.obtain((long)l3, (long)l3, (int)3, (float)0.0f, (float)0.0f, (int)0);
                    }
                    if (n3 != 0) {
                        if (n3 != 1) {
                            bl4 = bl2;
                            bl = bl3;
                        } else {
                            behavior.H(this, view, (MotionEvent)e4);
                            bl4 = bl2;
                            bl = bl3;
                        }
                    } else {
                        behavior.o(this, view, (MotionEvent)e4);
                        bl4 = bl2;
                        bl = bl3;
                    }
                }
            } else {
                bl3 = bl2;
                if (!bl2) {
                    bl3 = bl2;
                    if (behavior != null) {
                        if (n3 != 0) {
                            if (n3 == 1) {
                                bl2 = behavior.H(this, view, motionEvent);
                            }
                        } else {
                            bl2 = behavior.o(this, view, motionEvent);
                        }
                        bl3 = bl2;
                        if (bl2) {
                            this.m = view;
                            bl3 = bl2;
                        }
                    }
                }
                bl2 = e4.c();
                boolean bl5 = e4.i(this, view);
                bl2 = bl5 && !bl2;
                e4 = e3;
                bl4 = bl3;
                bl = bl2;
                if (bl5) {
                    e4 = e3;
                    bl4 = bl3;
                    bl = bl2;
                    if (!bl2) {
                        bl = bl3;
                        break;
                    }
                }
            }
            ++n6;
            e3 = e4;
            bl2 = bl4;
            bl3 = bl;
        }
        list.clear();
        return bl;
    }

    public final void Q() {
        this.c.clear();
        this.d.c();
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            e e3 = this.C(view);
            e3.d(this, view);
            this.d.b(view);
            for (int i4 = 0; i4 < n3; ++i4) {
                View view2;
                if (i4 == i3 || !e3.b(this, view, view2 = this.getChildAt(i4))) continue;
                if (!this.d.d(view2)) {
                    this.d.b(view2);
                }
                this.d.a(view2, view);
            }
        }
        this.c.addAll(this.d.i());
        Collections.reverse(this.c);
    }

    public void R(View view, Rect rect) {
        ((e)view.getLayoutParams()).q(rect);
    }

    public void T() {
        if (this.k && this.o != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.o);
        }
        this.p = false;
    }

    public final void U(boolean bl) {
        int n3;
        int n4 = this.getChildCount();
        for (n3 = 0; n3 < n4; ++n3) {
            View view = this.getChildAt(n3);
            Behavior behavior = ((e)view.getLayoutParams()).f();
            if (behavior == null) continue;
            long l3 = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain((long)l3, (long)l3, (int)3, (float)0.0f, (float)0.0f, (int)0);
            if (bl) {
                behavior.o(this, view, motionEvent);
            } else {
                behavior.H(this, view, motionEvent);
            }
            motionEvent.recycle();
        }
        for (n3 = 0; n3 < n4; ++n3) {
            ((e)this.getChildAt(n3).getLayoutParams()).m();
        }
        this.m = null;
        this.j = false;
    }

    public final void Y(View view, int n3) {
        e e3 = (e)view.getLayoutParams();
        int n4 = e3.i;
        if (n4 != n3) {
            x0.R(view, n3 - n4);
            e3.i = n3;
        }
    }

    public final void Z(View view, int n3) {
        e e3 = (e)view.getLayoutParams();
        int n4 = e3.j;
        if (n4 != n3) {
            x0.S(view, n3 - n4);
            e3.j = n3;
        }
    }

    public final z1 a0(z1 z12) {
        z1 z13 = z12;
        if (!n0.c.a(this.q, z12)) {
            this.q = z12;
            boolean bl = false;
            boolean bl2 = z12 != null && z12.l() > 0;
            this.r = bl2;
            boolean bl3 = bl;
            if (!bl2) {
                bl3 = bl;
                if (this.getBackground() == null) {
                    bl3 = true;
                }
            }
            this.setWillNotDraw(bl3);
            z13 = this.o(z12);
            this.requestLayout();
        }
        return z13;
    }

    @Override
    public void b(View view, View view2, int n3, int n4) {
        this.v.c(view, view2, n3, n4);
        this.n = view2;
        int n5 = this.getChildCount();
        for (int i3 = 0; i3 < n5; ++i3) {
            View view3 = this.getChildAt(i3);
            Object object = (e)view3.getLayoutParams();
            if (!object.j(n4) || (object = object.f()) == null) continue;
            ((Behavior)object).z(this, view3, view, view2, n3, n4);
        }
    }

    public final void b0() {
        if (x0.v((View)this)) {
            if (this.u == null) {
                this.u = new f0(this){
                    public final CoordinatorLayout a;
                    {
                        this.a = coordinatorLayout;
                    }

                    @Override
                    public z1 a(View view, z1 z12) {
                        return this.a.a0(z12);
                    }
                };
            }
            x0.r0((View)this, this.u);
            this.setSystemUiVisibility(1280);
            return;
        }
        x0.r0((View)this, null);
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof e && super.checkLayoutParams(layoutParams);
    }

    public boolean drawChild(Canvas canvas, View view, long l3) {
        block2: {
            float f3;
            e e3 = (e)view.getLayoutParams();
            Behavior behavior = e3.a;
            if (behavior == null || !((f3 = behavior.h(this, view)) > 0.0f)) break block2;
            if (this.g == null) {
                this.g = new Paint();
            }
            this.g.setColor(e3.a.g(this, view));
            this.g.setAlpha(CoordinatorLayout.j(Math.round(f3 * 255.0f), 0, 255));
            int n3 = canvas.save();
            if (view.isOpaque()) {
                canvas.clipRect((float)view.getLeft(), (float)view.getTop(), (float)view.getRight(), (float)view.getBottom(), Region.Op.DIFFERENCE);
            }
            canvas.drawRect((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()), this.g);
            canvas.restoreToCount(n3);
        }
        return super.drawChild(canvas, view, l3);
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        Drawable drawable = this.s;
        boolean bl = drawable != null && drawable.isStateful() ? drawable.setState(nArray) : false;
        if (bl) {
            this.invalidate();
        }
    }

    @Override
    public void g(View view, int n3) {
        this.v.d(view, n3);
        int n4 = this.getChildCount();
        for (int i3 = 0; i3 < n4; ++i3) {
            View view2 = this.getChildAt(i3);
            e e3 = (e)view2.getLayoutParams();
            if (!e3.j(n3)) continue;
            Behavior behavior = e3.f();
            if (behavior != null) {
                behavior.G(this, view2, view, n3);
            }
            e3.l(n3);
            e3.k();
        }
        this.n = null;
    }

    public final List<View> getDependencySortedChildren() {
        this.Q();
        return Collections.unmodifiableList(this.c);
    }

    public final z1 getLastWindowInsets() {
        return this.q;
    }

    public int getNestedScrollAxes() {
        return this.v.a();
    }

    public Drawable getStatusBarBackground() {
        return this.s;
    }

    public int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), this.getPaddingTop() + this.getPaddingBottom());
    }

    public int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), this.getPaddingLeft() + this.getPaddingRight());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void h(View view, int n3, int n4, int[] nArray, int n5) {
        int n6;
        int n7;
        int n8 = this.getChildCount();
        boolean bl = false;
        int n9 = n7 = (n6 = 0);
        int n10 = n7;
        while (n6 < n8) {
            int n11;
            View view2 = this.getChildAt(n6);
            if (view2.getVisibility() == 8) {
                n11 = n10;
                n7 = n9;
            } else {
                e e3 = (e)view2.getLayoutParams();
                if (!e3.j(n5)) {
                    n11 = n10;
                    n7 = n9;
                } else {
                    Behavior behavior = e3.f();
                    n11 = n10;
                    n7 = n9;
                    if (behavior != null) {
                        int[] nArray2 = this.h;
                        nArray2[0] = 0;
                        nArray2[1] = 0;
                        behavior.u(this, view2, view, n3, n4, nArray2, n5);
                        int[] nArray3 = this.h;
                        n7 = n3 > 0 ? Math.max(n10, nArray3[0]) : Math.min(n10, nArray3[0]);
                        n11 = n7;
                        int[] nArray4 = this.h;
                        n7 = n4 > 0 ? Math.max(n9, nArray4[1]) : Math.min(n9, nArray4[1]);
                        bl = true;
                    }
                }
            }
            ++n6;
            n10 = n11;
            n9 = n7;
        }
        nArray[0] = n10;
        nArray[1] = n9;
        if (bl) {
            this.L(1);
        }
    }

    public void i() {
        if (this.k) {
            if (this.o == null) {
                this.o = new f(this);
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.o);
        }
        this.p = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void k(View view, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
        int n8;
        int n9;
        int n10 = this.getChildCount();
        boolean bl = false;
        int n11 = n9 = (n8 = 0);
        int n12 = n9;
        while (n8 < n10) {
            int n13;
            View view2 = this.getChildAt(n8);
            if (view2.getVisibility() == 8) {
                n13 = n12;
                n9 = n11;
            } else {
                e e3 = (e)view2.getLayoutParams();
                if (!e3.j(n7)) {
                    n13 = n12;
                    n9 = n11;
                } else {
                    Behavior behavior = e3.f();
                    n13 = n12;
                    n9 = n11;
                    if (behavior != null) {
                        int[] nArray2 = this.h;
                        nArray2[0] = 0;
                        nArray2[1] = 0;
                        behavior.x(this, view2, view, n3, n4, n5, n6, n7, nArray2);
                        int[] nArray3 = this.h;
                        n9 = n5 > 0 ? Math.max(n12, nArray3[0]) : Math.min(n12, nArray3[0]);
                        n13 = n9;
                        int[] nArray4 = this.h;
                        n9 = n6 > 0 ? Math.max(n11, nArray4[1]) : Math.min(n11, nArray4[1]);
                        bl = true;
                    }
                }
            }
            ++n8;
            n12 = n13;
            n11 = n9;
        }
        nArray[0] = nArray[0] + n12;
        nArray[1] = nArray[1] + n11;
        if (bl) {
            this.L(1);
        }
    }

    @Override
    public void l(View view, int n3, int n4, int n5, int n6, int n7) {
        this.k(view, n3, n4, n5, n6, 0, this.i);
    }

    @Override
    public boolean m(View view, View view2, int n3, int n4) {
        int n5 = this.getChildCount();
        boolean bl = false;
        for (int i3 = 0; i3 < n5; ++i3) {
            View view3 = this.getChildAt(i3);
            if (view3.getVisibility() == 8) continue;
            e e3 = (e)view3.getLayoutParams();
            Behavior behavior = e3.f();
            if (behavior != null) {
                boolean bl2 = behavior.E(this, view3, view, view2, n3, n4);
                bl |= bl2;
                e3.r(n4, bl2);
                continue;
            }
            e3.r(n4, false);
        }
        return bl;
    }

    public final void n(e e3, Rect rect, int n3, int n4) {
        int n5 = this.getWidth();
        int n6 = this.getHeight();
        n5 = Math.max(this.getPaddingLeft() + e3.leftMargin, Math.min(rect.left, n5 - this.getPaddingRight() - n3 - e3.rightMargin));
        n6 = Math.max(this.getPaddingTop() + e3.topMargin, Math.min(rect.top, n6 - this.getPaddingBottom() - n4 - e3.bottomMargin));
        rect.set(n5, n6, n3 + n5, n4 + n6);
    }

    public final z1 o(z1 z12) {
        if (z12.p()) {
            return z12;
        }
        int n3 = this.getChildCount();
        z1 z13 = z12;
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            z12 = z13;
            if (x0.v(view)) {
                Behavior behavior = ((e)view.getLayoutParams()).f();
                z12 = z13;
                if (behavior != null) {
                    z12 = z13 = behavior.j(this, view, z13);
                    if (z13.p()) {
                        return z13;
                    }
                }
            }
            z13 = z12;
        }
        return z13;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.U(false);
        if (this.p) {
            if (this.o == null) {
                this.o = new f(this);
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.o);
        }
        if (this.q == null && x0.v((View)this)) {
            x0.e0((View)this);
        }
        this.k = true;
    }

    public void onDetachedFromWindow() {
        View view;
        super.onDetachedFromWindow();
        this.U(false);
        if (this.p && this.o != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.o);
        }
        if ((view = this.n) != null) {
            this.onStopNestedScroll(view);
        }
        this.k = false;
    }

    public void onDraw(Canvas canvas) {
        z1 z12;
        int n3;
        super.onDraw(canvas);
        if (this.r && this.s != null && (n3 = (z12 = this.q) != null ? z12.l() : 0) > 0) {
            this.s.setBounds(0, 0, this.getWidth(), n3);
            this.s.draw(canvas);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionMasked();
        if (n3 == 0) {
            this.U(true);
        }
        boolean bl = this.P(motionEvent, 0);
        if (n3 != 1 && n3 != 3) {
            return bl;
        }
        this.U(true);
        return bl;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        n5 = x0.y((View)this);
        n4 = this.c.size();
        for (n3 = 0; n3 < n4; ++n3) {
            Behavior behavior;
            View view = (View)this.c.get(n3);
            if (view.getVisibility() == 8 || (behavior = ((e)view.getLayoutParams()).f()) != null && behavior.p(this, view, n5)) continue;
            this.M(view, n5);
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void onMeasure(int var1_1, int var2_2) {
        var27_3 = this;
        var27_3.Q();
        var27_3.q();
        var16_4 = var27_3.getPaddingLeft();
        var20_5 = var27_3.getPaddingTop();
        var17_6 = var27_3.getPaddingRight();
        var19_7 = var27_3.getPaddingBottom();
        var9_8 = x0.y((View)var27_3);
        var7_9 = var9_8 == 1;
        var23_10 = View.MeasureSpec.getMode((int)var1_1);
        var24_11 = View.MeasureSpec.getSize((int)var1_1);
        var22_12 = View.MeasureSpec.getMode((int)var2_2);
        var21_13 = View.MeasureSpec.getSize((int)var2_2);
        var4_14 = var27_3.getSuggestedMinimumWidth();
        var12_15 = var27_3.getSuggestedMinimumHeight();
        var8_16 = var27_3.q != null && x0.v((View)var27_3) != false;
        var10_17 = var27_3.c.size();
        var3_18 = 0;
        var11_19 = 0;
        var6_20 = var17_6;
        var5_21 = var16_4;
        while (true) {
            block9: {
                block8: {
                    var13_22 = var5_21;
                    if (var3_18 >= var10_17) break;
                    var26_27 /* !! */  = (View)var27_3.c.get(var3_18);
                    if (var26_27 /* !! */ .getVisibility() != 8) break block8;
                    var5_21 = var3_18;
                    var26_27 /* !! */  = var27_3;
                    break block9;
                }
                var28_28 = (e)var26_27 /* !! */ .getLayoutParams();
                var5_21 = var28_28.e;
                if (var5_21 < 0 || var23_10 == 0) ** GOTO lbl-1000
                var5_21 = var27_3.A(var5_21);
                var14_23 = o0.s.b(CoordinatorLayout.X(var28_28.c), var9_8) & 7;
                if (var14_23 == 3 && !var7_9 || var14_23 == 5 && var7_9) {
                    var5_21 = Math.max(0, var24_11 - var6_20 - var5_21);
                } else if (var14_23 == 5 && !var7_9 || var14_23 == 3 && var7_9) {
                    var5_21 = Math.max(0, var5_21 - var13_22);
                } else lbl-1000:
                // 2 sources

                {
                    var5_21 = 0;
                }
                if (var8_16 && !x0.v(var26_27 /* !! */ )) {
                    var25_26 = var27_3.q.j();
                    var14_23 = var27_3.q.k();
                    var15_24 = var27_3.q.l();
                    var18_25 = var27_3.q.i();
                    var14_23 = View.MeasureSpec.makeMeasureSpec((int)(var24_11 - (var25_26 + var14_23)), (int)var23_10);
                    var15_24 = View.MeasureSpec.makeMeasureSpec((int)(var21_13 - (var15_24 + var18_25)), (int)var22_12);
                } else {
                    var14_23 = var1_1;
                    var15_24 = var2_2;
                }
                var18_25 = var3_18;
                var29_29 = var28_28.f();
                if (var29_29 == null || !var29_29.q(this, (View)(var27_3 = var26_27 /* !! */ ), var14_23, var5_21, var15_24, 0)) {
                    this.N(var26_27 /* !! */ , var14_23, var5_21, var15_24, 0);
                    var27_3 = var26_27 /* !! */ ;
                }
                var27_3 = var26_27 /* !! */ ;
                var26_27 /* !! */  = this;
                var4_14 = Math.max(var4_14, var16_4 + var17_6 + var27_3.getMeasuredWidth() + var28_28.leftMargin + var28_28.rightMargin);
                var12_15 = Math.max(var12_15, var20_5 + var19_7 + var27_3.getMeasuredHeight() + var28_28.topMargin + var28_28.bottomMargin);
                var11_19 = View.combineMeasuredStates((int)var11_19, (int)var27_3.getMeasuredState());
            }
            ++var3_18;
            var5_21 = var13_22;
            var27_3 = var26_27 /* !! */ ;
        }
        var27_3.setMeasuredDimension(View.resolveSizeAndState((int)var4_14, (int)var1_1, (int)(-16777216 & var11_19)), View.resolveSizeAndState((int)var12_15, (int)var2_2, (int)(var11_19 << 16)));
    }

    public boolean onNestedFling(View view, float f3, float f4, boolean bl) {
        int n3 = this.getChildCount();
        boolean bl2 = false;
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object;
            View view2 = this.getChildAt(i3);
            if (view2.getVisibility() == 8 || !((e)((Object)(object = (e)view2.getLayoutParams()))).j(0) || (object = ((e)((Object)object)).f()) == null) continue;
            bl2 |= ((Behavior)object).r(this, view2, view, f3, f4, bl);
        }
        if (bl2) {
            this.L(1);
        }
        return bl2;
    }

    public boolean onNestedPreFling(View view, float f3, float f4) {
        int n3 = this.getChildCount();
        boolean bl = false;
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object;
            View view2 = this.getChildAt(i3);
            if (view2.getVisibility() == 8 || !((e)((Object)(object = (e)view2.getLayoutParams()))).j(0) || (object = ((e)((Object)object)).f()) == null) continue;
            bl |= ((Behavior)object).s(this, view2, view, f3, f4);
        }
        return bl;
    }

    public void onNestedPreScroll(View view, int n3, int n4, int[] nArray) {
        this.h(view, n3, n4, nArray, 0);
    }

    public void onNestedScroll(View view, int n3, int n4, int n5, int n6) {
        this.l(view, n3, n4, n5, n6, 0);
    }

    public void onNestedScrollAccepted(View view, View view2, int n3) {
        this.b(view, view2, n3, 0);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        SparseArray sparseArray = parcelable.e;
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            Parcelable parcelable2;
            parcelable = this.getChildAt(i3);
            int n4 = parcelable.getId();
            Behavior behavior = this.C((View)parcelable).f();
            if (n4 == -1 || behavior == null || (parcelable2 = (Parcelable)sparseArray.get(n4)) == null) continue;
            behavior.B(this, (View)parcelable, parcelable2);
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            int n4 = view.getId();
            Behavior behavior = ((e)view.getLayoutParams()).f();
            if (n4 == -1 || behavior == null || (view = behavior.C(this, view)) == null) continue;
            sparseArray.append(n4, (Object)view);
        }
        savedState.e = sparseArray;
        return savedState;
    }

    public boolean onStartNestedScroll(View view, View view2, int n3) {
        return this.m(view, view2, n3, 0);
    }

    public void onStopNestedScroll(View view) {
        this.g(view, 0);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block9: {
            var2_2 = var1_1 /* !! */ .getActionMasked();
            if (this.m == null) {
                var6_4 = var5_3 = this.P(var1_1 /* !! */ , 1);
                if (!var5_3) lbl-1000:
                // 2 sources

                {
                    while (true) {
                        var7_6 = false;
                        var5_3 = var6_4;
                        var6_4 = var7_6;
                        break block9;
                        break;
                    }
                }
            } else {
                var5_3 = false;
            }
            var8_5 = ((e)this.m.getLayoutParams()).f();
            var6_4 = var5_3;
            ** while (var8_5 == null)
lbl15:
            // 1 sources

            var6_4 = var8_5.H(this, this.m, var1_1 /* !! */ );
        }
        var9_7 = this.m;
        var8_5 = null;
        if (var9_7 == null) {
            var7_6 = var6_4 | super.onTouchEvent(var1_1 /* !! */ );
            var1_1 /* !! */  = var8_5;
        } else {
            var7_6 = var6_4;
            var1_1 /* !! */  = var8_5;
            if (var5_3) {
                var3_8 = SystemClock.uptimeMillis();
                var1_1 /* !! */  = MotionEvent.obtain((long)var3_8, (long)var3_8, (int)3, (float)0.0f, (float)0.0f, (int)0);
                super.onTouchEvent(var1_1 /* !! */ );
                var7_6 = var6_4;
            }
        }
        if (var1_1 /* !! */  != null) {
            var1_1 /* !! */ .recycle();
        }
        if (var2_2 != 1 && var2_2 != 3) {
            return var7_6;
        }
        this.U(false);
        return var7_6;
    }

    public void p(View view) {
        List list = this.d.g(view);
        if (list != null && !list.isEmpty()) {
            for (int i3 = 0; i3 < list.size(); ++i3) {
                View view2 = (View)list.get(i3);
                Behavior behavior = ((e)view2.getLayoutParams()).f();
                if (behavior == null) continue;
                behavior.l(this, view2, view);
            }
        }
    }

    public void q() {
        boolean bl;
        int n3 = this.getChildCount();
        boolean bl2 = false;
        int n4 = 0;
        while (true) {
            bl = bl2;
            if (n4 >= n3) break;
            if (this.E(this.getChildAt(n4))) {
                bl = true;
                break;
            }
            ++n4;
        }
        if (bl != this.p) {
            if (bl) {
                this.i();
                return;
            }
            this.T();
        }
    }

    public e r() {
        return new e(-2, -2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        Behavior behavior = ((e)view.getLayoutParams()).f();
        if (behavior != null && behavior.A(this, view, rect, bl)) {
            return true;
        }
        return super.requestChildRectangleOnScreen(view, rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        super.requestDisallowInterceptTouchEvent(bl);
        if (bl && !this.j) {
            this.U(false);
            this.j = true;
        }
    }

    public e s(AttributeSet attributeSet) {
        return new e(this.getContext(), attributeSet);
    }

    public void setFitsSystemWindows(boolean bl) {
        super.setFitsSystemWindows(bl);
        this.b0();
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.t = onHierarchyChangeListener;
    }

    public void setStatusBarBackground(Drawable drawable) {
        Drawable drawable2 = this.s;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.s = drawable3;
            if (drawable3 != null) {
                if (drawable3.isStateful()) {
                    this.s.setState(this.getDrawableState());
                }
                h0.a.m(this.s, x0.y((View)this));
                drawable = this.s;
                boolean bl = this.getVisibility() == 0;
                drawable.setVisible(bl, false);
                this.s.setCallback((Drawable.Callback)this);
            }
            x0.Y((View)this);
        }
    }

    public void setStatusBarBackgroundColor(int n3) {
        this.setStatusBarBackground((Drawable)new ColorDrawable(n3));
    }

    public void setStatusBarBackgroundResource(int n3) {
        Drawable drawable = n3 != 0 ? e0.a.d(this.getContext(), n3) : null;
        this.setStatusBarBackground(drawable);
    }

    public void setVisibility(int n3) {
        super.setVisibility(n3);
        boolean bl = n3 == 0;
        Drawable drawable = this.s;
        if (drawable != null && drawable.isVisible() != bl) {
            this.s.setVisible(bl, false);
        }
    }

    public e t(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof e) {
            return new e((e)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new e((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new e(layoutParams);
    }

    public void u(View view, boolean bl, Rect rect) {
        if (!view.isLayoutRequested() && view.getVisibility() != 8) {
            if (bl) {
                this.x(view, rect);
                return;
            }
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            return;
        }
        rect.setEmpty();
    }

    public List v(View object) {
        object = this.d.h(object);
        this.f.clear();
        if (object != null) {
            this.f.addAll(object);
        }
        return this.f;
    }

    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.s;
        {
        }
    }

    public List w(View object) {
        object = this.d.g(object);
        this.f.clear();
        if (object != null) {
            this.f.addAll(object);
        }
        return this.f;
    }

    public void x(View view, Rect rect) {
        a0.c.a(this, view, rect);
    }

    public void y(View view, int n3, Rect rect, Rect rect2) {
        e e3 = (e)view.getLayoutParams();
        int n4 = view.getMeasuredWidth();
        int n5 = view.getMeasuredHeight();
        this.z(view, n3, rect, rect2, e3, n4, n5);
        this.n(e3, rect2, n4, n5);
    }

    public final void z(View view, int n3, Rect rect, Rect rect2, e e3, int n4, int n5) {
        int n6 = o0.s.b(CoordinatorLayout.V(e3.c), n3);
        n3 = o0.s.b(CoordinatorLayout.W(e3.d), n3);
        int n7 = n6 & 7;
        int n8 = n6 & 0x70;
        int n9 = n3 & 7;
        n6 = n3 & 0x70;
        n3 = n9 != 1 ? (n9 != 5 ? rect.left : rect.right) : rect.left + rect.width() / 2;
        n6 = n6 != 16 ? (n6 != 80 ? rect.top : rect.bottom) : rect.top + rect.height() / 2;
        if (n7 != 1) {
            n9 = n3;
            if (n7 != 5) {
                n9 = n3 - n4;
            }
        } else {
            n9 = n3 - n4 / 2;
        }
        if (n8 != 16) {
            n3 = n6;
            if (n8 != 80) {
                n3 = n6 - n5;
            }
        } else {
            n3 = n6 - n5 / 2;
        }
        rect2.set(n9, n3, n4 + n9, n5 + n3);
    }

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public boolean A(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean bl) {
            return false;
        }

        public void B(CoordinatorLayout coordinatorLayout, View view, Parcelable parcelable) {
        }

        public Parcelable C(CoordinatorLayout coordinatorLayout, View view) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        public boolean D(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int n3) {
            return false;
        }

        public boolean E(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int n3, int n4) {
            if (n4 == 0) {
                return this.D(coordinatorLayout, view, view2, view3, n3);
            }
            return false;
        }

        public void F(CoordinatorLayout coordinatorLayout, View view, View view2) {
        }

        public void G(CoordinatorLayout coordinatorLayout, View view, View view2, int n3) {
            if (n3 == 0) {
                this.F(coordinatorLayout, view, view2);
            }
        }

        public boolean H(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return false;
        }

        public boolean e(CoordinatorLayout coordinatorLayout, View view) {
            return this.h(coordinatorLayout, view) > 0.0f;
        }

        public boolean f(CoordinatorLayout coordinatorLayout, View view, Rect rect) {
            return false;
        }

        public int g(CoordinatorLayout coordinatorLayout, View view) {
            return -16777216;
        }

        public float h(CoordinatorLayout coordinatorLayout, View view) {
            return 0.0f;
        }

        public boolean i(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return false;
        }

        public z1 j(CoordinatorLayout coordinatorLayout, View view, z1 z12) {
            return z12;
        }

        public void k(e e3) {
        }

        public boolean l(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return false;
        }

        public void m(CoordinatorLayout coordinatorLayout, View view, View view2) {
        }

        public void n() {
        }

        public boolean o(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return false;
        }

        public boolean p(CoordinatorLayout coordinatorLayout, View view, int n3) {
            return false;
        }

        public boolean q(CoordinatorLayout coordinatorLayout, View view, int n3, int n4, int n5, int n6) {
            return false;
        }

        public boolean r(CoordinatorLayout coordinatorLayout, View view, View view2, float f3, float f4, boolean bl) {
            return false;
        }

        public boolean s(CoordinatorLayout coordinatorLayout, View view, View view2, float f3, float f4) {
            return false;
        }

        public void t(CoordinatorLayout coordinatorLayout, View view, View view2, int n3, int n4, int[] nArray) {
        }

        public void u(CoordinatorLayout coordinatorLayout, View view, View view2, int n3, int n4, int[] nArray, int n5) {
            if (n5 == 0) {
                this.t(coordinatorLayout, view, view2, n3, n4, nArray);
            }
        }

        public void v(CoordinatorLayout coordinatorLayout, View view, View view2, int n3, int n4, int n5, int n6) {
        }

        public void w(CoordinatorLayout coordinatorLayout, View view, View view2, int n3, int n4, int n5, int n6, int n7) {
            if (n7 == 0) {
                this.v(coordinatorLayout, view, view2, n3, n4, n5, n6);
            }
        }

        public void x(CoordinatorLayout coordinatorLayout, View view, View view2, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
            nArray[0] = nArray[0] + n5;
            nArray[1] = nArray[1] + n6;
            this.w(coordinatorLayout, view, view2, n3, n4, n5, n6, n7);
        }

        public void y(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int n3) {
        }

        public void z(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int n3, int n4) {
            if (n4 == 0) {
                this.y(coordinatorLayout, view, view2, view3, n3);
            }
        }
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
        public SparseArray e;

        public SavedState(Parcel parcelableArray, ClassLoader classLoader) {
            super((Parcel)parcelableArray, classLoader);
            int n3 = parcelableArray.readInt();
            int[] nArray = new int[n3];
            parcelableArray.readIntArray(nArray);
            parcelableArray = parcelableArray.readParcelableArray(classLoader);
            this.e = new SparseArray(n3);
            for (int i3 = 0; i3 < n3; ++i3) {
                this.e.append(nArray[i3], (Object)parcelableArray[i3]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            Object object = this.e;
            int n4 = object != null ? object.size() : 0;
            parcel.writeInt(n4);
            object = new int[n4];
            Parcelable[] parcelableArray = new Parcelable[n4];
            for (int i3 = 0; i3 < n4; ++i3) {
                object[i3] = (SparseArray)this.e.keyAt(i3);
                parcelableArray[i3] = (Parcelable)this.e.valueAt(i3);
            }
            parcel.writeIntArray((int[])object);
            parcel.writeParcelableArray(parcelableArray, n3);
        }
    }

    public static interface b {
        public Behavior getBehavior();
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface c {
        public Class value();
    }

    public class d
    implements ViewGroup.OnHierarchyChangeListener {
        public final CoordinatorLayout c;

        public d(CoordinatorLayout coordinatorLayout) {
            this.c = coordinatorLayout;
        }

        public void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.c.t;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            this.c.L(2);
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.c.t;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public static class e
    extends ViewGroup.MarginLayoutParams {
        public Behavior a;
        public boolean b = false;
        public int c = 0;
        public int d = 0;
        public int e = -1;
        public int f = -1;
        public int g = 0;
        public int h = 0;
        public int i;
        public int j;
        public View k;
        public View l;
        public boolean m;
        public boolean n;
        public boolean o;
        public boolean p;
        public final Rect q = new Rect();
        public Object r;

        public e(int n3, int n4) {
            super(n3, n4);
        }

        public e(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            boolean bl;
            TypedArray typedArray = object.obtainStyledAttributes(attributeSet, z.c.CoordinatorLayout_Layout);
            this.c = typedArray.getInteger(z.c.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.f = typedArray.getResourceId(z.c.CoordinatorLayout_Layout_layout_anchor, -1);
            this.d = typedArray.getInteger(z.c.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.e = typedArray.getInteger(z.c.CoordinatorLayout_Layout_layout_keyline, -1);
            this.g = typedArray.getInt(z.c.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.h = typedArray.getInt(z.c.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            int n3 = z.c.CoordinatorLayout_Layout_layout_behavior;
            this.b = bl = typedArray.hasValue(n3);
            if (bl) {
                this.a = CoordinatorLayout.O((Context)object, attributeSet, typedArray.getString(n3));
            }
            typedArray.recycle();
            object = this.a;
            if (object != null) {
                ((Behavior)object).k(this);
            }
        }

        public e(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public e(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public e(e e3) {
            super((ViewGroup.MarginLayoutParams)e3);
        }

        public boolean a() {
            return this.k == null && this.f != -1;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, View view, View view2) {
            Behavior behavior;
            return view2 == this.l || this.s(view2, x0.y((View)coordinatorLayout)) || (behavior = this.a) != null && behavior.i(coordinatorLayout, view, view2);
            {
            }
        }

        public boolean c() {
            if (this.a == null) {
                this.m = false;
            }
            return this.m;
        }

        public View d(CoordinatorLayout coordinatorLayout, View view) {
            if (this.f == -1) {
                this.l = null;
                this.k = null;
                return null;
            }
            if (this.k == null || !this.t(view, coordinatorLayout)) {
                this.n(view, coordinatorLayout);
            }
            return this.k;
        }

        public int e() {
            return this.f;
        }

        public Behavior f() {
            return this.a;
        }

        public boolean g() {
            return this.p;
        }

        public Rect h() {
            return this.q;
        }

        public boolean i(CoordinatorLayout coordinatorLayout, View view) {
            boolean bl = this.m;
            if (bl) {
                return true;
            }
            Behavior behavior = this.a;
            boolean bl2 = behavior != null ? behavior.e(coordinatorLayout, view) : false;
            this.m = bl2 |= bl;
            return bl2;
        }

        public boolean j(int n3) {
            if (n3 != 0) {
                if (n3 != 1) {
                    return false;
                }
                return this.o;
            }
            return this.n;
        }

        public void k() {
            this.p = false;
        }

        public void l(int n3) {
            this.r(n3, false);
        }

        public void m() {
            this.m = false;
        }

        public final void n(View view, CoordinatorLayout coordinatorLayout) {
            View view2;
            this.k = view2 = coordinatorLayout.findViewById(this.f);
            if (view2 != null) {
                if (view2 == coordinatorLayout) {
                    if (coordinatorLayout.isInEditMode()) {
                        this.l = null;
                        this.k = null;
                        return;
                    }
                    throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                }
                for (ViewParent viewParent = view2.getParent(); viewParent != coordinatorLayout && viewParent != null; viewParent = viewParent.getParent()) {
                    if (viewParent == view) {
                        if (coordinatorLayout.isInEditMode()) {
                            this.l = null;
                            this.k = null;
                            return;
                        }
                        throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                    }
                    if (!(viewParent instanceof View)) continue;
                    view2 = (View)viewParent;
                }
                this.l = view2;
                return;
            }
            if (coordinatorLayout.isInEditMode()) {
                this.l = null;
                this.k = null;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find CoordinatorLayout descendant view with id ");
            stringBuilder.append(coordinatorLayout.getResources().getResourceName(this.f));
            stringBuilder.append(" to anchor view ");
            stringBuilder.append(view);
            throw new IllegalStateException(stringBuilder.toString());
        }

        public void o(Behavior behavior) {
            Behavior behavior2 = this.a;
            if (behavior2 != behavior) {
                if (behavior2 != null) {
                    behavior2.n();
                }
                this.a = behavior;
                this.r = null;
                this.b = true;
                if (behavior != null) {
                    behavior.k(this);
                }
            }
        }

        public void p(boolean bl) {
            this.p = bl;
        }

        public void q(Rect rect) {
            this.q.set(rect);
        }

        public void r(int n3, boolean bl) {
            if (n3 != 0) {
                if (n3 != 1) {
                    return;
                }
                this.o = bl;
                return;
            }
            this.n = bl;
        }

        public final boolean s(View view, int n3) {
            int n4 = o0.s.b(((e)view.getLayoutParams()).g, n3);
            return n4 != 0 && (o0.s.b(this.h, n3) & n4) == n4;
        }

        public final boolean t(View view, CoordinatorLayout coordinatorLayout) {
            if (this.k.getId() != this.f) {
                return false;
            }
            View view2 = this.k;
            for (ViewParent viewParent = view2.getParent(); viewParent != coordinatorLayout; viewParent = viewParent.getParent()) {
                if (viewParent != null && viewParent != view) {
                    if (!(viewParent instanceof View)) continue;
                    view2 = (View)viewParent;
                    continue;
                }
                this.l = null;
                this.k = null;
                return false;
            }
            this.l = view2;
            return true;
        }
    }

    public class f
    implements ViewTreeObserver.OnPreDrawListener {
        public final CoordinatorLayout c;

        public f(CoordinatorLayout coordinatorLayout) {
            this.c = coordinatorLayout;
        }

        public boolean onPreDraw() {
            this.c.L(0);
            return true;
        }
    }

    public static class g
    implements Comparator {
        public int a(View view, View view2) {
            float f3;
            float f4 = x0.J(view);
            if (f4 > (f3 = x0.J(view2))) {
                return -1;
            }
            if (f4 < f3) {
                return 1;
            }
            return 0;
        }
    }
}

