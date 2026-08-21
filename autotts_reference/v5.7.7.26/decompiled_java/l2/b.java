/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewParent
 */
package l2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import l2.a;

public final class b {
    public final View a;
    public boolean b = false;
    public int c = 0;

    public b(a a4) {
        this.a = (View)a4;
    }

    public final void a() {
        ViewParent viewParent = this.a.getParent();
        if (viewParent instanceof CoordinatorLayout) {
            ((CoordinatorLayout)viewParent).p(this.a);
        }
    }

    public int b() {
        return this.c;
    }

    public boolean c() {
        return this.b;
    }

    public void d(Bundle bundle) {
        this.b = bundle.getBoolean("expanded", false);
        this.c = bundle.getInt("expandedComponentIdHint", 0);
        if (this.b) {
            this.a();
        }
    }

    public Bundle e() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("expanded", this.b);
        bundle.putInt("expandedComponentIdHint", this.c);
        return bundle;
    }

    public void f(int n3) {
        this.c = n3;
    }
}

