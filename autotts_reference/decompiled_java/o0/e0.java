/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package o0;

import android.view.View;
import android.view.ViewGroup;

public class e0 {
    public int a;
    public int b;

    public e0(ViewGroup viewGroup) {
    }

    public int a() {
        return this.a | this.b;
    }

    public void b(View view, View view2, int n3) {
        this.c(view, view2, n3, 0);
    }

    public void c(View view, View view2, int n3, int n4) {
        if (n4 == 1) {
            this.b = n3;
            return;
        }
        this.a = n3;
    }

    public void d(View view, int n3) {
        if (n3 == 1) {
            this.b = 0;
            return;
        }
        this.a = 0;
    }
}

