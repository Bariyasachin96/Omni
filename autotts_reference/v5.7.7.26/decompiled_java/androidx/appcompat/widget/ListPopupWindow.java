/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.appcompat.widget.AppCompatPopupWindow;
import androidx.appcompat.widget.a0;
import c.a;
import c.j;
import java.lang.reflect.Method;

public class ListPopupWindow
implements i.f {
    public static Method I;
    public static Method J;
    public final g A;
    public final e B;
    public Runnable C;
    public final Handler D;
    public final Rect E;
    public Rect F;
    public boolean G;
    public PopupWindow H;
    public Context c;
    public ListAdapter d;
    public a0 e;
    public int f = -2;
    public int g = -2;
    public int h;
    public int i;
    public int j = 1002;
    public boolean k;
    public boolean l;
    public boolean m;
    public int n = 0;
    public boolean o = false;
    public boolean p = false;
    public int q = Integer.MAX_VALUE;
    public View r;
    public int s = 0;
    public DataSetObserver t;
    public View u;
    public Drawable v;
    public AdapterView.OnItemClickListener w;
    public AdapterView.OnItemSelectedListener x;
    public final i y = new i(this);
    public final h z = new h(this);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        if (Build.VERSION.SDK_INT > 28) return;
        try {
            I = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
        }
        catch (NoSuchMethodException noSuchMethodException) {}
        try {
            J = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return;
        }
    }

    public ListPopupWindow(Context context) {
        this(context, null, a.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, 0);
    }

    public ListPopupWindow(Context object, AttributeSet attributeSet, int n3, int n4) {
        int n5;
        this.A = new g(this);
        this.B = new e(this);
        this.E = new Rect();
        this.c = object;
        this.D = new Handler(object.getMainLooper());
        TypedArray typedArray = object.obtainStyledAttributes(attributeSet, c.j.ListPopupWindow, n3, n4);
        this.h = typedArray.getDimensionPixelOffset(c.j.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.i = n5 = typedArray.getDimensionPixelOffset(c.j.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (n5 != 0) {
            this.k = true;
        }
        typedArray.recycle();
        object = new AppCompatPopupWindow((Context)object, attributeSet, n3, n4);
        this.H = object;
        object.setInputMethodMode(1);
    }

    public boolean A() {
        return this.H.getInputMethodMode() == 2;
    }

    public boolean B() {
        return this.G;
    }

    public final void C() {
        View view = this.r;
        if (view != null && (view = view.getParent()) instanceof ViewGroup) {
            ((ViewGroup)view).removeView(this.r);
        }
    }

    public void D(View view) {
        this.u = view;
    }

    public void E(int n3) {
        this.H.setAnimationStyle(n3);
    }

    public void F(int n3) {
        Drawable drawable = this.H.getBackground();
        if (drawable != null) {
            drawable.getPadding(this.E);
            drawable = this.E;
            this.g = drawable.left + drawable.right + n3;
            return;
        }
        this.R(n3);
    }

    public void G(int n3) {
        this.n = n3;
    }

    public void H(Rect object) {
        object = object != null ? new Rect(object) : null;
        this.F = object;
    }

    public void I(int n3) {
        this.H.setInputMethodMode(n3);
    }

    public void J(boolean bl) {
        this.G = bl;
        this.H.setFocusable(bl);
    }

    public void K(PopupWindow.OnDismissListener onDismissListener) {
        this.H.setOnDismissListener(onDismissListener);
    }

    public void L(AdapterView.OnItemClickListener onItemClickListener) {
        this.w = onItemClickListener;
    }

    public void M(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.x = onItemSelectedListener;
    }

    public void N(boolean bl) {
        this.m = true;
        this.l = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void O(boolean bl) {
        if (Build.VERSION.SDK_INT > 28) {
            androidx.appcompat.widget.ListPopupWindow$d.b(this.H, bl);
            return;
        }
        Method method = I;
        if (method == null) return;
        try {
            method.invoke((Object)this.H, bl);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void P(int n3) {
        this.s = n3;
    }

    public void Q(int n3) {
        a0 a02 = this.e;
        if (this.c() && a02 != null) {
            a02.setListSelectionHidden(false);
            a02.setSelection(n3);
            if (a02.getChoiceMode() != 0) {
                a02.setItemChecked(n3, true);
            }
        }
    }

    public void R(int n3) {
        this.g = n3;
    }

    public void b(Drawable drawable) {
        this.H.setBackgroundDrawable(drawable);
    }

    @Override
    public boolean c() {
        return this.H.isShowing();
    }

    public int d() {
        return this.h;
    }

    @Override
    public void dismiss() {
        this.H.dismiss();
        this.C();
        this.H.setContentView(null);
        this.e = null;
        this.D.removeCallbacks((Runnable)this.y);
    }

    @Override
    public void e() {
        int n3 = this.q();
        boolean bl = this.A();
        androidx.core.widget.i.b(this.H, this.j);
        boolean bl2 = this.H.isShowing();
        boolean bl3 = true;
        boolean bl4 = true;
        if (bl2) {
            if (this.t().isAttachedToWindow()) {
                PopupWindow popupWindow;
                int n4;
                int n5 = this.g;
                if (n5 == -1) {
                    n4 = -1;
                } else {
                    n4 = n5;
                    if (n5 == -2) {
                        n4 = this.t().getWidth();
                    }
                }
                n5 = this.f;
                if (n5 == -1) {
                    if (!bl) {
                        n3 = -1;
                    }
                    if (bl) {
                        popupWindow = this.H;
                        n5 = this.g == -1 ? -1 : 0;
                        popupWindow.setWidth(n5);
                        this.H.setHeight(0);
                    } else {
                        popupWindow = this.H;
                        n5 = this.g == -1 ? -1 : 0;
                        popupWindow.setWidth(n5);
                        this.H.setHeight(-1);
                    }
                } else if (n5 != -2) {
                    n3 = n5;
                }
                popupWindow = this.H;
                if (this.p || this.o) {
                    bl4 = false;
                }
                popupWindow.setOutsideTouchable(bl4);
                PopupWindow popupWindow2 = this.H;
                popupWindow = this.t();
                n5 = this.h;
                int n6 = this.i;
                if (n4 < 0) {
                    n4 = -1;
                }
                if (n3 < 0) {
                    n3 = -1;
                }
                popupWindow2.update((View)popupWindow, n5, n6, n4, n3);
                return;
            }
        } else {
            int n7;
            int n8 = this.g;
            if (n8 == -1) {
                n7 = -1;
            } else {
                n7 = n8;
                if (n8 == -2) {
                    n7 = this.t().getWidth();
                }
            }
            n8 = this.f;
            if (n8 == -1) {
                n3 = -1;
            } else if (n8 != -2) {
                n3 = n8;
            }
            this.H.setWidth(n7);
            this.H.setHeight(n3);
            this.O(true);
            Object object = this.H;
            bl4 = !this.p && !this.o ? bl3 : false;
            object.setOutsideTouchable(bl4);
            this.H.setTouchInterceptor((View.OnTouchListener)this.z);
            if (this.m) {
                androidx.core.widget.i.a(this.H, this.l);
            }
            if (Build.VERSION.SDK_INT <= 28) {
                object = J;
                if (object != null) {
                    try {
                        ((Method)object).invoke((Object)this.H, this.F);
                    }
                    catch (Exception exception) {
                        Log.e((String)"ListPopupWindow", (String)"Could not invoke setEpicenterBounds on PopupWindow", (Throwable)exception);
                    }
                }
            } else {
                androidx.appcompat.widget.ListPopupWindow$d.a(this.H, this.F);
            }
            androidx.core.widget.i.c(this.H, this.t(), this.h, this.i, this.n);
            this.e.setSelection(-1);
            if (!this.G || this.e.isInTouchMode()) {
                this.r();
            }
            if (!this.G) {
                this.D.post((Runnable)this.B);
            }
        }
    }

    public Drawable g() {
        return this.H.getBackground();
    }

    @Override
    public ListView h() {
        return this.e;
    }

    public void j(int n3) {
        this.i = n3;
        this.k = true;
    }

    public void l(int n3) {
        this.h = n3;
    }

    public int n() {
        if (!this.k) {
            return 0;
        }
        return this.i;
    }

    public void p(ListAdapter object) {
        DataSetObserver dataSetObserver = this.t;
        if (dataSetObserver == null) {
            this.t = new f(this);
        } else {
            ListAdapter listAdapter = this.d;
            if (listAdapter != null) {
                listAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.d = object;
        if (object != null) {
            object.registerDataSetObserver(this.t);
        }
        if ((object = this.e) != null) {
            object.setAdapter(this.d);
        }
    }

    public final int q() {
        int n3;
        int n4;
        int n5;
        int n6;
        a0 a02 = this.e;
        boolean bl = true;
        if (a02 == null) {
            a0 a03;
            a02 = this.c;
            this.C = new Runnable(this){
                public final ListPopupWindow c;
                {
                    this.c = listPopupWindow;
                }

                @Override
                public void run() {
                    View view = this.c.t();
                    if (view != null && view.getWindowToken() != null) {
                        this.c.e();
                    }
                }
            };
            this.e = a03 = this.s((Context)a02, this.G ^ true);
            Object object = this.v;
            if (object != null) {
                a03.setSelector((Drawable)object);
            }
            this.e.setAdapter(this.d);
            this.e.setOnItemClickListener(this.w);
            this.e.setFocusable(true);
            this.e.setFocusableInTouchMode(true);
            this.e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                public final ListPopupWindow c;
                {
                    this.c = listPopupWindow;
                }

                public void onItemSelected(AdapterView object, View view, int n3, long l3) {
                    if (n3 != -1 && (object = this.c.e) != null) {
                        ((a0)((Object)object)).setListSelectionHidden(false);
                    }
                }

                public void onNothingSelected(AdapterView adapterView) {
                }
            });
            this.e.setOnScrollListener(this.A);
            object = this.x;
            if (object != null) {
                this.e.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)object);
            }
            object = this.e;
            a03 = this.r;
            if (a03 != null) {
                a02 = new LinearLayout((Context)a02);
                a02.setOrientation(1);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                n6 = this.s;
                if (n6 != 0) {
                    if (n6 != 1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid hint position ");
                        ((StringBuilder)object).append(this.s);
                        Log.e((String)"ListPopupWindow", (String)((StringBuilder)object).toString());
                    } else {
                        a02.addView((View)object, (ViewGroup.LayoutParams)layoutParams);
                        a02.addView((View)a03);
                    }
                } else {
                    a02.addView((View)a03);
                    a02.addView((View)object, (ViewGroup.LayoutParams)layoutParams);
                }
                n6 = this.g;
                if (n6 >= 0) {
                    n5 = Integer.MIN_VALUE;
                } else {
                    n6 = 0;
                    n5 = 0;
                }
                a03.measure(View.MeasureSpec.makeMeasureSpec((int)n6, (int)n5), 0);
                object = (LinearLayout.LayoutParams)a03.getLayoutParams();
                n6 = a03.getMeasuredHeight() + ((LinearLayout.LayoutParams)object).topMargin + ((LinearLayout.LayoutParams)object).bottomMargin;
            } else {
                n6 = 0;
                a02 = object;
            }
            this.H.setContentView((View)a02);
        } else {
            a02 = (ViewGroup)this.H.getContentView();
            View view = this.r;
            if (view != null) {
                a02 = (LinearLayout.LayoutParams)view.getLayoutParams();
                n6 = view.getMeasuredHeight() + ((LinearLayout.LayoutParams)a02).topMargin + ((LinearLayout.LayoutParams)a02).bottomMargin;
            } else {
                n6 = 0;
            }
        }
        a02 = this.H.getBackground();
        if (a02 != null) {
            a02.getPadding(this.E);
            a02 = this.E;
            n4 = ((Rect)a02).top;
            n3 = n5 = ((Rect)a02).bottom + n4;
            if (!this.k) {
                this.i = -n4;
                n3 = n5;
            }
        } else {
            this.E.setEmpty();
            n3 = 0;
        }
        if (this.H.getInputMethodMode() != 2) {
            bl = false;
        }
        n4 = this.u(this.t(), this.i, bl);
        if (!this.o && this.f != -1) {
            n5 = this.g;
            if (n5 != -2) {
                if (n5 != -1) {
                    n5 = View.MeasureSpec.makeMeasureSpec((int)n5, (int)0x40000000);
                } else {
                    n5 = this.c.getResources().getDisplayMetrics().widthPixels;
                    a02 = this.E;
                    n5 = View.MeasureSpec.makeMeasureSpec((int)(n5 - (((Rect)a02).left + ((Rect)a02).right)), (int)0x40000000);
                }
            } else {
                n5 = this.c.getResources().getDisplayMetrics().widthPixels;
                a02 = this.E;
                n5 = View.MeasureSpec.makeMeasureSpec((int)(n5 - (((Rect)a02).left + ((Rect)a02).right)), (int)Integer.MIN_VALUE);
            }
            n4 = this.e.d(n5, 0, -1, n4 - n6, -1);
            n5 = n6;
            if (n4 > 0) {
                n5 = n6 + (n3 + (this.e.getPaddingTop() + this.e.getPaddingBottom()));
            }
            return n4 + n5;
        }
        return n4 + n3;
    }

    public void r() {
        a0 a02 = this.e;
        if (a02 != null) {
            a02.setListSelectionHidden(true);
            a02.requestLayout();
        }
    }

    public a0 s(Context context, boolean bl) {
        return new a0(context, bl);
    }

    public View t() {
        return this.u;
    }

    public final int u(View view, int n3, boolean bl) {
        return androidx.appcompat.widget.ListPopupWindow$c.a(this.H, view, n3, bl);
    }

    public Object v() {
        if (!this.c()) {
            return null;
        }
        return this.e.getSelectedItem();
    }

    public long w() {
        if (!this.c()) {
            return Long.MIN_VALUE;
        }
        return this.e.getSelectedItemId();
    }

    public int x() {
        if (!this.c()) {
            return -1;
        }
        return this.e.getSelectedItemPosition();
    }

    public View y() {
        if (!this.c()) {
            return null;
        }
        return this.e.getSelectedView();
    }

    public int z() {
        return this.g;
    }

    public static abstract class c {
        public static int a(PopupWindow popupWindow, View view, int n3, boolean bl) {
            return popupWindow.getMaxAvailableHeight(view, n3, bl);
        }
    }

    public static abstract class d {
        public static void a(PopupWindow popupWindow, Rect rect) {
            popupWindow.setEpicenterBounds(rect);
        }

        public static void b(PopupWindow popupWindow, boolean bl) {
            popupWindow.setIsClippedToScreen(bl);
        }
    }

    public class e
    implements Runnable {
        public final ListPopupWindow c;

        public e(ListPopupWindow listPopupWindow) {
            this.c = listPopupWindow;
        }

        @Override
        public void run() {
            this.c.r();
        }
    }

    public class f
    extends DataSetObserver {
        public final ListPopupWindow a;

        public f(ListPopupWindow listPopupWindow) {
            this.a = listPopupWindow;
        }

        public void onChanged() {
            if (this.a.c()) {
                this.a.e();
            }
        }

        public void onInvalidated() {
            this.a.dismiss();
        }
    }

    public class g
    implements AbsListView.OnScrollListener {
        public final ListPopupWindow a;

        public g(ListPopupWindow listPopupWindow) {
            this.a = listPopupWindow;
        }

        public void onScroll(AbsListView absListView, int n3, int n4, int n5) {
        }

        public void onScrollStateChanged(AbsListView object, int n3) {
            if (n3 == 1 && !this.a.A() && this.a.H.getContentView() != null) {
                object = this.a;
                object.D.removeCallbacks((Runnable)object.y);
                this.a.y.run();
            }
        }
    }

    public class h
    implements View.OnTouchListener {
        public final ListPopupWindow c;

        public h(ListPopupWindow listPopupWindow) {
            this.c = listPopupWindow;
        }

        public boolean onTouch(View object, MotionEvent motionEvent) {
            int n3 = motionEvent.getAction();
            int n4 = (int)motionEvent.getX();
            int n5 = (int)motionEvent.getY();
            if (n3 == 0 && (object = this.c.H) != null && object.isShowing() && n4 >= 0 && n4 < this.c.H.getWidth() && n5 >= 0 && n5 < this.c.H.getHeight()) {
                object = this.c;
                object.D.postDelayed((Runnable)object.y, 250L);
            } else if (n3 == 1) {
                object = this.c;
                object.D.removeCallbacks((Runnable)object.y);
            }
            return false;
        }
    }

    public class i
    implements Runnable {
        public final ListPopupWindow c;

        public i(ListPopupWindow listPopupWindow) {
            this.c = listPopupWindow;
        }

        @Override
        public void run() {
            Object object = this.c.e;
            if (object != null && object.isAttachedToWindow() && this.c.e.getCount() > this.c.e.getChildCount()) {
                int n3 = this.c.e.getChildCount();
                object = this.c;
                if (n3 <= ((ListPopupWindow)object).q) {
                    ((ListPopupWindow)object).H.setInputMethodMode(2);
                    this.c.e();
                }
            }
        }
    }
}

