/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package androidx.appcompat.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.j;
import java.util.ArrayList;

public class d
extends BaseAdapter {
    public e c;
    public int d = -1;
    public boolean e;
    public final boolean f;
    public final LayoutInflater g;
    public final int h;

    public d(e e3, LayoutInflater layoutInflater, boolean bl, int n3) {
        this.f = bl;
        this.g = layoutInflater;
        this.c = e3;
        this.h = n3;
        this.a();
    }

    public void a() {
        g g3 = this.c.x();
        if (g3 != null) {
            ArrayList arrayList = this.c.B();
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                if ((g)arrayList.get(i3) != g3) continue;
                this.d = i3;
                return;
            }
        }
        this.d = -1;
    }

    public e b() {
        return this.c;
    }

    public g c(int n3) {
        ArrayList arrayList = this.f ? this.c.B() : this.c.G();
        int n4 = this.d;
        int n5 = n3;
        if (n4 >= 0) {
            n5 = n3;
            if (n3 >= n4) {
                n5 = n3 + 1;
            }
        }
        return (g)arrayList.get(n5);
    }

    public void d(boolean bl) {
        this.e = bl;
    }

    public int getCount() {
        ArrayList arrayList = this.f ? this.c.B() : this.c.G();
        if (this.d < 0) {
            return arrayList.size();
        }
        return arrayList.size() - 1;
    }

    public long getItemId(int n3) {
        return n3;
    }

    public View getView(int n3, View object, ViewGroup object2) {
        View view = object;
        if (object == null) {
            view = this.g.inflate(this.h, (ViewGroup)object2, false);
        }
        int n4 = this.c(n3).getGroupId();
        int n5 = n3 - 1;
        n5 = n5 >= 0 ? this.c(n5).getGroupId() : n4;
        object = (ListMenuItemView)view;
        boolean bl = this.c.I() && n4 != n5;
        ((ListMenuItemView)object).setGroupDividerEnabled(bl);
        object2 = (j.a)view;
        if (this.e) {
            ((ListMenuItemView)object).setForceShowIcon(true);
        }
        object2.d(this.c(n3), 0);
        return view;
    }

    public void notifyDataSetChanged() {
        this.a();
        super.notifyDataSetChanged();
    }
}

