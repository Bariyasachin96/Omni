/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.internal;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.internal.j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class a {
    public final Map a = new HashMap();
    public final Set b = new HashSet();
    public b c;
    public boolean d;
    public boolean e;

    public void e(j j3) {
        this.a.put(j3.getId(), j3);
        if (j3.isChecked()) {
            this.g(j3);
        }
        j3.setInternalOnCheckedChangeListener(new j.a(this){
            public final a a;
            {
                this.a = a4;
            }

            public void b(j j3, boolean bl) {
                a a4;
                if (bl ? this.a.g(j3) : (a4 = this.a).r(j3, a4.e)) {
                    this.a.m();
                }
            }
        });
    }

    public void f(int n3) {
        j j3 = (j)this.a.get(n3);
        if (j3 != null && this.g(j3)) {
            this.m();
        }
    }

    public final boolean g(j j3) {
        int n3 = j3.getId();
        if (this.b.contains(n3)) {
            return false;
        }
        j j4 = (j)this.a.get(this.k());
        if (j4 != null) {
            this.r(j4, false);
        }
        boolean bl = this.b.add(n3);
        if (!j3.isChecked()) {
            j3.setChecked(true);
        }
        return bl;
    }

    public void h() {
        boolean bl = this.b.isEmpty();
        Iterator iterator = this.a.values().iterator();
        while (iterator.hasNext()) {
            this.r((j)iterator.next(), false);
        }
        if (!bl) {
            this.m();
        }
    }

    public Set i() {
        return new HashSet(this.b);
    }

    public List j(ViewGroup viewGroup) {
        Set set = this.i();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i3 = 0; i3 < viewGroup.getChildCount(); ++i3) {
            View view = viewGroup.getChildAt(i3);
            if (!(view instanceof j) || !set.contains(view.getId())) continue;
            arrayList.add(view.getId());
        }
        return arrayList;
    }

    public int k() {
        if (this.d && !this.b.isEmpty()) {
            return (Integer)this.b.iterator().next();
        }
        return -1;
    }

    public boolean l() {
        return this.d;
    }

    public final void m() {
        b b3 = this.c;
        if (b3 != null) {
            b3.a(this.i());
        }
    }

    public void n(j j3) {
        j3.setInternalOnCheckedChangeListener(null);
        this.a.remove(j3.getId());
        this.b.remove(j3.getId());
    }

    public void o(b b3) {
        this.c = b3;
    }

    public void p(boolean bl) {
        this.e = bl;
    }

    public void q(boolean bl) {
        if (this.d != bl) {
            this.d = bl;
            this.h();
        }
    }

    public final boolean r(j j3, boolean bl) {
        int n3 = j3.getId();
        if (!this.b.contains(n3)) {
            return false;
        }
        if (bl && this.b.size() == 1 && this.b.contains(n3)) {
            j3.setChecked(true);
            return false;
        }
        bl = this.b.remove(n3);
        if (j3.isChecked()) {
            j3.setChecked(false);
        }
        return bl;
    }

    public static interface b {
        public void a(Set var1);
    }
}

