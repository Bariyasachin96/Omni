/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListView
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.widget.m0;

public final class ExpandedMenuView
extends ListView
implements e.b,
j,
AdapterView.OnItemClickListener {
    public static final int[] e = new int[]{16842964, 16843049};
    public e c;
    public int d;

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public ExpandedMenuView(Context object, AttributeSet attributeSet, int n3) {
        super((Context)object, attributeSet);
        this.setOnItemClickListener(this);
        object = m0.v((Context)object, attributeSet, e, n3, 0);
        if (((m0)object).s(0)) {
            this.setBackgroundDrawable(((m0)object).g(0));
        }
        if (((m0)object).s(1)) {
            this.setDivider(((m0)object).g(1));
        }
        ((m0)object).x();
    }

    @Override
    public boolean a(g g3) {
        return this.c.O(g3, 0);
    }

    @Override
    public void b(e e3) {
        this.c = e3;
    }

    public int getWindowAnimations() {
        return this.d;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.setChildrenDrawingCacheEnabled(false);
    }

    public void onItemClick(AdapterView adapterView, View view, int n3, long l3) {
        this.a((g)this.getAdapter().getItem(n3));
    }
}

