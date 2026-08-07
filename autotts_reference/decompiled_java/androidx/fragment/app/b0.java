/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import k0.a;
import o0.i0;
import o0.x0;

public abstract class b0 {
    public static void d(List list, View view) {
        int n3 = list.size();
        if (!b0.g(list, view, n3)) {
            if (x0.F(view) != null) {
                list.add(view);
            }
            for (int i3 = n3; i3 < list.size(); ++i3) {
                view = (View)list.get(i3);
                if (!(view instanceof ViewGroup)) continue;
                ViewGroup viewGroup = (ViewGroup)view;
                int n4 = viewGroup.getChildCount();
                for (int i4 = 0; i4 < n4; ++i4) {
                    view = viewGroup.getChildAt(i4);
                    if (b0.g(list, view, n3) || x0.F(view) == null) continue;
                    list.add(view);
                }
            }
        }
    }

    public static boolean g(List list, View view, int n3) {
        for (int i3 = 0; i3 < n3; ++i3) {
            if (list.get(i3) != view) continue;
            return true;
        }
        return false;
    }

    public static boolean i(List list) {
        return list == null || list.isEmpty();
        {
        }
    }

    public abstract void a(Object var1, View var2);

    public abstract void b(Object var1, ArrayList var2);

    public abstract void c(ViewGroup var1, Object var2);

    public abstract boolean e(Object var1);

    public abstract Object f(Object var1);

    public void h(View view, Rect rect) {
        if (!x0.N(view)) {
            return;
        }
        RectF rectF = new RectF();
        rectF.set(0.0f, 0.0f, (float)view.getWidth(), (float)view.getHeight());
        view.getMatrix().mapRect(rectF);
        rectF.offset((float)view.getLeft(), (float)view.getTop());
        Object object = view.getParent();
        while (object instanceof View) {
            object = (View)object;
            rectF.offset((float)(-object.getScrollX()), (float)(-object.getScrollY()));
            object.getMatrix().mapRect(rectF);
            rectF.offset((float)object.getLeft(), (float)object.getTop());
            object = object.getParent();
        }
        object = new int[2];
        view.getRootView().getLocationOnScreen((int[])object);
        rectF.offset((float)object[0], (float)object[1]);
        rect.set(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    public abstract Object j(Object var1, Object var2, Object var3);

    public abstract Object k(Object var1, Object var2, Object var3);

    public ArrayList l(ArrayList arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<String>();
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = (View)arrayList.get(i3);
            arrayList2.add(x0.F(view));
            x0.w0(view, null);
        }
        return arrayList2;
    }

    public abstract void m(Object var1, View var2, ArrayList var3);

    public abstract void n(Object var1, Object var2, ArrayList var3, Object var4, ArrayList var5, Object var6, ArrayList var7);

    public abstract void o(Object var1, Rect var2);

    public abstract void p(Object var1, View var2);

    public abstract void q(Fragment var1, Object var2, a var3, Runnable var4);

    public void r(View view, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Map map) {
        int n3 = arrayList2.size();
        ArrayList<String> arrayList4 = new ArrayList<String>();
        block0: for (int i3 = 0; i3 < n3; ++i3) {
            Object object = (View)arrayList.get(i3);
            String string = x0.F((View)object);
            arrayList4.add(string);
            if (string == null) continue;
            x0.w0((View)object, null);
            object = (String)map.get(string);
            for (int i4 = 0; i4 < n3; ++i4) {
                if (!((String)object).equals(arrayList3.get(i4))) continue;
                x0.w0((View)arrayList2.get(i4), string);
                continue block0;
            }
        }
        i0.a(view, new Runnable(this, n3, arrayList2, arrayList3, arrayList, arrayList4){
            public final int c;
            public final ArrayList d;
            public final ArrayList e;
            public final ArrayList f;
            public final ArrayList g;
            public final b0 h;
            {
                this.h = b02;
                this.c = n3;
                this.d = arrayList;
                this.e = arrayList2;
                this.f = arrayList3;
                this.g = arrayList4;
            }

            @Override
            public void run() {
                for (int i3 = 0; i3 < this.c; ++i3) {
                    x0.w0((View)this.d.get(i3), (String)this.e.get(i3));
                    x0.w0((View)this.f.get(i3), (String)this.g.get(i3));
                }
            }
        });
    }

    public abstract void s(Object var1, View var2, ArrayList var3);

    public abstract void t(Object var1, ArrayList var2, ArrayList var3);

    public abstract Object u(Object var1);
}

