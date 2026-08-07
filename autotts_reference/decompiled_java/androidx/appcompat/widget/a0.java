/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView
 *  android.widget.AdapterView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.core.widget.h;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import o0.h1;

public class a0
extends ListView {
    public final Rect c = new Rect();
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 0;
    public int h;
    public d i;
    public boolean j;
    public boolean k;
    public boolean l;
    public h1 m;
    public h n;
    public f o;

    public a0(Context context, boolean bl) {
        super(context, null, c.a.dropDownListViewStyle);
        this.k = bl;
        this.setCacheColorHint(0);
    }

    public final void a() {
        this.l = false;
        this.setPressed(false);
        this.drawableStateChanged();
        Object object = this.getChildAt(this.h - this.getFirstVisiblePosition());
        if (object != null) {
            object.setPressed(false);
        }
        if ((object = this.m) != null) {
            ((h1)object).c();
            this.m = null;
        }
    }

    public final void b(View view, int n3) {
        this.performItemClick(view, n3, this.getItemIdAtPosition(n3));
    }

    public final void c(Canvas canvas) {
        Drawable drawable;
        if (!this.c.isEmpty() && (drawable = this.getSelector()) != null) {
            drawable.setBounds(this.c);
            drawable.draw(canvas);
        }
    }

    public int d(int n3, int n4, int n5, int n6, int n7) {
        int n8;
        int n9 = this.getListPaddingTop();
        n4 = this.getListPaddingBottom();
        n5 = this.getDividerHeight();
        Drawable drawable = this.getDivider();
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter == null) {
            return n9 + n4;
        }
        n4 = n9 + n4;
        if (n5 <= 0 || drawable == null) {
            n5 = 0;
        }
        int n10 = listAdapter.getCount();
        n9 = n8 = 0;
        drawable = null;
        for (int i3 = 0; i3 < n10; ++i3) {
            int n11 = listAdapter.getItemViewType(i3);
            int n12 = n8;
            if (n11 != n8) {
                drawable = null;
                n12 = n11;
            }
            View view = listAdapter.getView(i3, (View)drawable, (ViewGroup)this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            drawable = layoutParams;
            if (layoutParams == null) {
                drawable = this.generateDefaultLayoutParams();
                view.setLayoutParams((ViewGroup.LayoutParams)drawable);
            }
            n8 = (n8 = drawable.height) > 0 ? View.MeasureSpec.makeMeasureSpec((int)n8, (int)0x40000000) : View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
            view.measure(n3, n8);
            view.forceLayout();
            n8 = n4;
            if (i3 > 0) {
                n8 = n4 + n5;
            }
            if ((n4 = n8 + view.getMeasuredHeight()) >= n6) {
                if (n7 >= 0 && i3 > n7 && n9 > 0 && n4 != n6) {
                    return n9;
                }
                return n6;
            }
            n11 = n9;
            if (n7 >= 0) {
                n11 = n9;
                if (i3 >= n7) {
                    n11 = n4;
                }
            }
            n8 = n12;
            drawable = view;
            n9 = n11;
        }
        return n4;
    }

    public void dispatchDraw(Canvas canvas) {
        this.c(canvas);
        super.dispatchDraw(canvas);
    }

    public void drawableStateChanged() {
        if (this.o != null) {
            return;
        }
        super.drawableStateChanged();
        this.j(true);
        this.n();
    }

    /*
     * Unable to fully structure code
     */
    public boolean e(MotionEvent var1_1, int var2_2) {
        block10: {
            block11: {
                var3_3 = var1_1.getActionMasked();
                if (var3_3 != 1) {
                    if (var3_3 != 2) {
                        if (var3_3 != 3) lbl-1000:
                        // 3 sources

                        {
                            while (true) {
                                var6_4 = true;
                                var2_2 = 0;
                                break block10;
                                break;
                            }
                        }
lbl9:
                        // 3 sources

                        while (true) {
                            var2_2 = 0;
                            var6_4 = false;
                            break block10;
                            break;
                        }
                    }
                    var6_4 = true;
                } else {
                    var6_4 = false;
                }
                ** while ((var4_5 = var1_1.findPointerIndex((int)var2_2)) < 0)
lbl17:
                // 1 sources

                var2_2 = (int)var1_1.getX(var4_5);
                var5_6 = this.pointToPosition(var2_2, var4_5 = (int)var1_1.getY(var4_5));
                if (var5_6 != -1) break block11;
                var2_2 = 1;
                break block10;
            }
            var7_7 = this.getChildAt(var5_6 - this.getFirstVisiblePosition());
            this.i(var7_7, var5_6, var2_2, var4_5);
            if (var3_3 != 1) ** GOTO lbl-1000
            this.b(var7_7, var5_6);
            ** while (true)
        }
        if (!var6_4 || var2_2 != 0) {
            this.a();
        }
        if (var6_4) {
            if (this.n == null) {
                this.n = new h(this);
            }
            this.n.m(true);
            this.n.onTouch((View)this, (MotionEvent)var1_1);
            return var6_4;
        }
        var1_1 = this.n;
        if (var1_1 != null) {
            var1_1.m(false);
        }
        return var6_4;
    }

    public final void f(int n3, View view) {
        Rect rect = this.c;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        rect.left -= this.d;
        rect.top -= this.e;
        rect.right += this.f;
        rect.bottom += this.g;
        boolean bl = this.k();
        if (view.isEnabled() != bl) {
            this.l(bl ^ true);
            if (n3 != -1) {
                this.refreshDrawableState();
            }
        }
    }

    public final void g(int n3, View view) {
        Drawable drawable = this.getSelector();
        boolean bl = true;
        boolean bl2 = drawable != null && n3 != -1;
        if (bl2) {
            drawable.setVisible(false, false);
        }
        this.f(n3, view);
        if (bl2) {
            view = this.c;
            float f3 = view.exactCenterX();
            float f4 = view.exactCenterY();
            if (this.getVisibility() != 0) {
                bl = false;
            }
            drawable.setVisible(bl, false);
            h0.a.k(drawable, f3, f4);
        }
    }

    public final void h(int n3, View view, float f3, float f4) {
        this.g(n3, view);
        view = this.getSelector();
        if (view != null && n3 != -1) {
            h0.a.k((Drawable)view, f3, f4);
        }
    }

    public boolean hasFocus() {
        return this.k || super.hasFocus();
        {
        }
    }

    public boolean hasWindowFocus() {
        return this.k || super.hasWindowFocus();
        {
        }
    }

    public final void i(View view, int n3, float f3, float f4) {
        View view2;
        this.l = true;
        a.a((View)this, f3, f4);
        if (!this.isPressed()) {
            this.setPressed(true);
        }
        this.layoutChildren();
        int n4 = this.h;
        if (n4 != -1 && (view2 = this.getChildAt(n4 - this.getFirstVisiblePosition())) != null && view2 != view && view2.isPressed()) {
            view2.setPressed(false);
        }
        this.h = n3;
        a.a(view, f3 - (float)view.getLeft(), f4 - (float)view.getTop());
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        this.h(n3, view, f3, f4);
        this.j(false);
        this.refreshDrawableState();
    }

    public boolean isFocused() {
        return this.k || super.isFocused();
        {
        }
    }

    public boolean isInTouchMode() {
        return this.k && this.j || super.isInTouchMode();
    }

    public final void j(boolean bl) {
        d d3 = this.i;
        if (d3 != null) {
            d3.b(bl);
        }
    }

    public final boolean k() {
        if (Build.VERSION.SDK_INT >= 33) {
            return androidx.appcompat.widget.a0$c.a((AbsListView)this);
        }
        return androidx.appcompat.widget.a0$e.a((AbsListView)this);
    }

    public final void l(boolean bl) {
        if (Build.VERSION.SDK_INT >= 33) {
            androidx.appcompat.widget.a0$c.b((AbsListView)this, bl);
            return;
        }
        androidx.appcompat.widget.a0$e.b((AbsListView)this, bl);
    }

    public final boolean m() {
        return this.l;
    }

    public final void n() {
        Drawable drawable = this.getSelector();
        if (drawable != null && this.m() && this.isPressed()) {
            drawable.setState(this.getDrawableState());
        }
    }

    public void onDetachedFromWindow() {
        this.o = null;
        super.onDetachedFromWindow();
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n3 = Build.VERSION.SDK_INT;
        int n4 = motionEvent.getActionMasked();
        if (n4 == 10 && this.o == null) {
            f f3;
            this.o = f3 = new f(this);
            f3.b();
        }
        boolean bl = super.onHoverEvent(motionEvent);
        if (n4 != 9 && n4 != 7) {
            this.setSelection(-1);
            return bl;
        }
        n4 = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
        if (n4 != -1 && n4 != this.getSelectedItemPosition()) {
            motionEvent = this.getChildAt(n4 - this.getFirstVisiblePosition());
            if (motionEvent.isEnabled()) {
                this.requestFocus();
                if (n3 >= 30 && b.a()) {
                    b.b(this, n4, (View)motionEvent);
                } else {
                    this.setSelectionFromTop(n4, motionEvent.getTop() - this.getTop());
                }
            }
            this.n();
        }
        return bl;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.h = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
        }
        f f3 = this.o;
        if (f3 != null) {
            f3.a();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setListSelectionHidden(boolean bl) {
        this.j = bl;
    }

    public void setSelector(Drawable drawable) {
        d d3 = drawable != null ? new d(drawable) : null;
        this.i = d3;
        super.setSelector((Drawable)d3);
        d3 = new Rect();
        if (drawable != null) {
            drawable.getPadding((Rect)d3);
        }
        this.d = ((Rect)d3).left;
        this.e = ((Rect)d3).top;
        this.f = ((Rect)d3).right;
        this.g = ((Rect)d3).bottom;
    }

    public static abstract class a {
        public static void a(View view, float f3, float f4) {
            view.drawableHotspotChanged(f3, f4);
        }
    }

    public static abstract class b {
        public static Method a;
        public static Method b;
        public static Method c;
        public static boolean d;

        static {
            try {
                GenericDeclaration genericDeclaration = Integer.TYPE;
                Class<Boolean> clazz = Boolean.TYPE;
                GenericDeclaration genericDeclaration2 = Float.TYPE;
                genericDeclaration2 = AbsListView.class.getDeclaredMethod("positionSelector", new Class[]{genericDeclaration, View.class, clazz, genericDeclaration2, genericDeclaration2});
                a = genericDeclaration2;
                ((AccessibleObject)((Object)genericDeclaration2)).setAccessible(true);
                genericDeclaration2 = AdapterView.class.getDeclaredMethod("setSelectedPositionInt", new Class[]{genericDeclaration});
                b = genericDeclaration2;
                ((AccessibleObject)((Object)genericDeclaration2)).setAccessible(true);
                genericDeclaration = AdapterView.class.getDeclaredMethod("setNextSelectedPositionInt", new Class[]{genericDeclaration});
                c = genericDeclaration;
                ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
                d = true;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            }
        }

        public static boolean a() {
            return d;
        }

        public static void b(a0 a02, int n3, View view) {
            Integer n4 = -1;
            try {
                a.invoke((Object)a02, n3, view, Boolean.FALSE, n4, n4);
                b.invoke((Object)a02, n3);
                c.invoke((Object)a02, n3);
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
            }
            catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }

    public static abstract class c {
        public static boolean a(AbsListView absListView) {
            return absListView.isSelectedChildViewEnabled();
        }

        public static void b(AbsListView absListView, boolean bl) {
            absListView.setSelectedChildViewEnabled(bl);
        }
    }

    public static class d
    extends e.a {
        public boolean d = true;

        public d(Drawable drawable) {
            super(drawable);
        }

        public void b(boolean bl) {
            this.d = bl;
        }

        @Override
        public void draw(Canvas canvas) {
            if (this.d) {
                super.draw(canvas);
            }
        }

        @Override
        public void setHotspot(float f3, float f4) {
            if (this.d) {
                super.setHotspot(f3, f4);
            }
        }

        @Override
        public void setHotspotBounds(int n3, int n4, int n5, int n6) {
            if (this.d) {
                super.setHotspotBounds(n3, n4, n5, n6);
            }
        }

        @Override
        public boolean setState(int[] nArray) {
            if (this.d) {
                return super.setState(nArray);
            }
            return false;
        }

        @Override
        public boolean setVisible(boolean bl, boolean bl2) {
            if (this.d) {
                return super.setVisible(bl, bl2);
            }
            return false;
        }
    }

    public static abstract class e {
        public static final Field a;

        static {
            Field field;
            Field field2 = null;
            field2 = field = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            try {
                ((AccessibleObject)field).setAccessible(true);
                field2 = field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                noSuchFieldException.printStackTrace();
            }
            a = field2;
        }

        public static boolean a(AbsListView absListView) {
            Field field = a;
            if (field != null) {
                try {
                    boolean bl = field.getBoolean(absListView);
                    return bl;
                }
                catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
            return false;
        }

        public static void b(AbsListView absListView, boolean bl) {
            Field field = a;
            if (field != null) {
                try {
                    field.set(absListView, bl);
                    return;
                }
                catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        }
    }

    public class f
    implements Runnable {
        public final a0 c;

        public f(a0 a02) {
            this.c = a02;
        }

        public void a() {
            a0 a02 = this.c;
            a02.o = null;
            a02.removeCallbacks(this);
        }

        public void b() {
            this.c.post(this);
        }

        @Override
        public void run() {
            a0 a02 = this.c;
            a02.o = null;
            a02.drawableStateChanged();
        }
    }
}

