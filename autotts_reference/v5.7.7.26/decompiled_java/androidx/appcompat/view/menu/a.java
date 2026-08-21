/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.view.menu.l;
import java.util.ArrayList;

public abstract class a
implements i {
    public Context c;
    public Context d;
    public e e;
    public LayoutInflater f;
    public LayoutInflater g;
    public i.a h;
    public int i;
    public int j;
    public j k;
    public int l;

    public a(Context context, int n3, int n4) {
        this.c = context;
        this.f = LayoutInflater.from((Context)context);
        this.i = n3;
        this.j = n4;
    }

    @Override
    public void a(e e3, boolean bl) {
        i.a a4 = this.h;
        if (a4 != null) {
            a4.a(e3, bl);
        }
    }

    @Override
    public void b(Context context, e e3) {
        this.d = context;
        this.g = LayoutInflater.from((Context)context);
        this.e = e3;
    }

    public void e(View view, int n3) {
        ViewGroup viewGroup = (ViewGroup)view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        ((ViewGroup)this.k).addView(view, n3);
    }

    @Override
    public boolean f(l e3) {
        i.a a4 = this.h;
        if (a4 != null) {
            if (e3 == null) {
                e3 = this.e;
            }
            return a4.b(e3);
        }
        return false;
    }

    @Override
    public void g(boolean bl) {
        ViewGroup viewGroup = (ViewGroup)this.k;
        if (viewGroup != null) {
            Object object = this.e;
            int n3 = 0;
            if (object != null) {
                ((e)object).t();
                ArrayList arrayList = this.e.G();
                int n4 = arrayList.size();
                n3 = 0;
                for (int i3 = 0; i3 < n4; ++i3) {
                    g g3 = (g)arrayList.get(i3);
                    int n5 = n3;
                    if (this.t(n3, g3)) {
                        View view = viewGroup.getChildAt(n3);
                        object = view instanceof j.a ? ((j.a)view).getItemData() : null;
                        View view2 = this.q(g3, view, viewGroup);
                        if (g3 != object) {
                            view2.setPressed(false);
                            view2.jumpDrawablesToCurrentState();
                        }
                        if (view2 != view) {
                            this.e(view2, n3);
                        }
                        n5 = n3 + 1;
                    }
                    n3 = n5;
                }
            }
            while (n3 < viewGroup.getChildCount()) {
                if (this.o(viewGroup, n3)) continue;
                ++n3;
            }
        }
    }

    @Override
    public int getId() {
        return this.l;
    }

    public abstract void h(g var1, j.a var2);

    @Override
    public boolean k(e e3, g g3) {
        return false;
    }

    @Override
    public boolean l(e e3, g g3) {
        return false;
    }

    @Override
    public void m(i.a a4) {
        this.h = a4;
    }

    public j.a n(ViewGroup viewGroup) {
        return (j.a)this.f.inflate(this.j, viewGroup, false);
    }

    public boolean o(ViewGroup viewGroup, int n3) {
        viewGroup.removeViewAt(n3);
        return true;
    }

    public i.a p() {
        return this.h;
    }

    public View q(g g3, View object, ViewGroup viewGroup) {
        object = object instanceof j.a ? (j.a)object : this.n(viewGroup);
        this.h(g3, (j.a)object);
        return object;
    }

    public j r(ViewGroup object) {
        if (this.k == null) {
            object = (j)this.f.inflate(this.i, (ViewGroup)object, false);
            this.k = object;
            object.b(this.e);
            this.g(true);
        }
        return this.k;
    }

    public void s(int n3) {
        this.l = n3;
    }

    public abstract boolean t(int var1, g var2);
}

