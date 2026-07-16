/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.FrameLayout
 *  android.widget.HeaderViewListAdapter
 *  android.widget.ListAdapter
 *  android.widget.PopupWindow$OnDismissListener
 */
package i;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import i.f;

public abstract class d
implements f,
i,
AdapterView.OnItemClickListener {
    public Rect c;

    public static androidx.appcompat.view.menu.d A(ListAdapter listAdapter) {
        if (listAdapter instanceof HeaderViewListAdapter) {
            return (androidx.appcompat.view.menu.d)((HeaderViewListAdapter)listAdapter).getWrappedAdapter();
        }
        return (androidx.appcompat.view.menu.d)listAdapter;
    }

    public static int q(ListAdapter listAdapter, ViewGroup viewGroup, Context context, int n3) {
        int n4 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n5 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n6 = listAdapter.getCount();
        int n7 = 0;
        int n8 = 0;
        ViewGroup viewGroup2 = null;
        ViewGroup viewGroup3 = viewGroup;
        viewGroup = viewGroup2;
        for (int i3 = 0; i3 < n6; ++i3) {
            int n9 = listAdapter.getItemViewType(i3);
            int n10 = n8;
            if (n9 != n8) {
                viewGroup = null;
                n10 = n9;
            }
            viewGroup2 = viewGroup3;
            if (viewGroup3 == null) {
                viewGroup2 = new FrameLayout(context);
            }
            viewGroup = listAdapter.getView(i3, (View)viewGroup, viewGroup2);
            viewGroup.measure(n4, n5);
            n9 = viewGroup.getMeasuredWidth();
            if (n9 >= n3) {
                return n3;
            }
            n8 = n7;
            if (n9 > n7) {
                n8 = n9;
            }
            n7 = n8;
            n8 = n10;
            viewGroup3 = viewGroup2;
        }
        return n7;
    }

    public static boolean z(e e3) {
        int n3 = e3.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            MenuItem menuItem = e3.getItem(i3);
            if (!menuItem.isVisible() || menuItem.getIcon() == null) continue;
            return true;
        }
        return false;
    }

    @Override
    public void b(Context context, e e3) {
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean k(e e3, g g3) {
        return false;
    }

    @Override
    public boolean l(e e3, g g3) {
        return false;
    }

    public abstract void n(e var1);

    public boolean o() {
        return true;
    }

    public void onItemClick(AdapterView object, View view, int n3, long l3) {
        view = (ListAdapter)object.getAdapter();
        object = d.A((ListAdapter)view).c;
        view = (MenuItem)view.getItem(n3);
        n3 = this.o() ? 0 : 4;
        ((e)object).P((MenuItem)view, this, n3);
    }

    public Rect p() {
        return this.c;
    }

    public abstract void r(View var1);

    public void s(Rect rect) {
        this.c = rect;
    }

    public abstract void t(boolean var1);

    public abstract void u(int var1);

    public abstract void v(int var1);

    public abstract void w(PopupWindow.OnDismissListener var1);

    public abstract void x(boolean var1);

    public abstract void y(int var1);
}

