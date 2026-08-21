/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.SparseArray
 *  android.view.ContextThemeWrapper
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.BaseAdapter
 *  android.widget.ListAdapter
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import androidx.appcompat.view.menu.ExpandedMenuView;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.f;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.view.menu.l;
import java.util.ArrayList;

public class c
implements i,
AdapterView.OnItemClickListener {
    public Context c;
    public LayoutInflater d;
    public e e;
    public ExpandedMenuView f;
    public int g;
    public int h;
    public int i;
    public i.a j;
    public a k;
    public int l;

    public c(int n3, int n4) {
        this.i = n3;
        this.h = n4;
    }

    public c(Context context, int n3) {
        this(n3, 0);
        this.c = context;
        this.d = LayoutInflater.from((Context)context);
    }

    @Override
    public void a(e e3, boolean bl) {
        i.a a4 = this.j;
        if (a4 != null) {
            a4.a(e3, bl);
        }
    }

    @Override
    public void b(Context object, e e3) {
        if (this.h != 0) {
            object = new ContextThemeWrapper((Context)object, this.h);
            this.c = object;
            this.d = LayoutInflater.from((Context)object);
        } else if (this.c != null) {
            this.c = object;
            if (this.d == null) {
                this.d = LayoutInflater.from((Context)object);
            }
        }
        this.e = e3;
        object = this.k;
        if (object != null) {
            ((a)((Object)object)).notifyDataSetChanged();
        }
    }

    public ListAdapter c() {
        if (this.k == null) {
            this.k = new a(this);
        }
        return this.k;
    }

    @Override
    public void d(Parcelable parcelable) {
        this.h((Bundle)parcelable);
    }

    public j e(ViewGroup viewGroup) {
        if (this.f == null) {
            this.f = (ExpandedMenuView)this.d.inflate(c.g.abc_expanded_menu_layout, viewGroup, false);
            if (this.k == null) {
                this.k = new a(this);
            }
            this.f.setAdapter((ListAdapter)this.k);
            this.f.setOnItemClickListener(this);
        }
        return this.f;
    }

    @Override
    public boolean f(l l3) {
        if (!l3.hasVisibleItems()) {
            return false;
        }
        new f(l3).d(null);
        i.a a4 = this.j;
        if (a4 != null) {
            a4.b(l3);
        }
        return true;
    }

    @Override
    public void g(boolean bl) {
        a a4 = this.k;
        if (a4 != null) {
            a4.notifyDataSetChanged();
        }
    }

    @Override
    public int getId() {
        return this.l;
    }

    public void h(Bundle bundle) {
        if ((bundle = bundle.getSparseParcelableArray("android:menu:list")) != null) {
            this.f.restoreHierarchyState((SparseArray)bundle);
        }
    }

    @Override
    public boolean i() {
        return false;
    }

    @Override
    public Parcelable j() {
        if (this.f == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        this.n(bundle);
        return bundle;
    }

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
        this.j = a4;
    }

    public void n(Bundle bundle) {
        SparseArray sparseArray = new SparseArray();
        ExpandedMenuView expandedMenuView = this.f;
        if (expandedMenuView != null) {
            expandedMenuView.saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray("android:menu:list", sparseArray);
    }

    public void onItemClick(AdapterView adapterView, View view, int n3, long l3) {
        this.e.P(this.k.b(n3), this, 0);
    }

    public class a
    extends BaseAdapter {
        public int c;
        public final c d;

        public a(c c3) {
            this.d = c3;
            this.c = -1;
            this.a();
        }

        public void a() {
            g g3 = this.d.e.x();
            if (g3 != null) {
                ArrayList arrayList = this.d.e.B();
                int n3 = arrayList.size();
                for (int i3 = 0; i3 < n3; ++i3) {
                    if ((g)arrayList.get(i3) != g3) continue;
                    this.c = i3;
                    return;
                }
            }
            this.c = -1;
        }

        public g b(int n3) {
            ArrayList arrayList = this.d.e.B();
            int n4 = n3 + this.d.g;
            int n5 = this.c;
            n3 = n4;
            if (n5 >= 0) {
                n3 = n4;
                if (n4 >= n5) {
                    n3 = n4 + 1;
                }
            }
            return (g)arrayList.get(n3);
        }

        public int getCount() {
            int n3 = this.d.e.B().size() - this.d.g;
            if (this.c < 0) {
                return n3;
            }
            return n3 - 1;
        }

        public long getItemId(int n3) {
            return n3;
        }

        public View getView(int n3, View object, ViewGroup viewGroup) {
            View view = object;
            if (object == null) {
                object = this.d;
                view = object.d.inflate(object.i, viewGroup, false);
            }
            ((j.a)view).d(this.b(n3), 0);
            return view;
        }

        public void notifyDataSetChanged() {
            this.a();
            super.notifyDataSetChanged();
        }
    }
}

