/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ListView
 */
package androidx.core.widget;

import android.view.View;
import android.widget.ListView;
import androidx.core.widget.a;

public class h
extends a {
    public final ListView u;

    public h(ListView listView) {
        super((View)listView);
        this.u = listView;
    }

    @Override
    public boolean a(int n3) {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean b(int n3) {
        ListView listView = this.u;
        int n4 = listView.getCount();
        if (n4 == 0) {
            return false;
        }
        int n5 = listView.getChildCount();
        int n6 = listView.getFirstVisiblePosition();
        if (n3 > 0) {
            if (n6 + n5 < n4 || listView.getChildAt(n5 - 1).getBottom() > listView.getHeight()) return true;
            return false;
        }
        if (n3 >= 0) return false;
        if (n6 > 0 || listView.getChildAt(0).getTop() < 0) return true;
        return false;
    }

    @Override
    public void j(int n3, int n4) {
        this.u.scrollListBy(n4);
    }
}

