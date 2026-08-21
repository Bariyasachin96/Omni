/*
 * Decompiled with CFR 0.152.
 */
package androidx.fragment.app;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.b0;
import androidx.lifecycle.y;
import androidx.lifecycle.z;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public final class u
extends y {
    public static final z.b k = new z.b(){

        @Override
        public y a(Class clazz) {
            return new u(true);
        }
    };
    public final HashMap d = new HashMap();
    public final HashMap e = new HashMap();
    public final HashMap f = new HashMap();
    public final boolean g;
    public boolean h = false;
    public boolean i = false;
    public boolean j = false;

    public u(boolean bl) {
        this.g = bl;
    }

    public static u k(b0 b02) {
        return (u)new z(b02, k).a(u.class);
    }

    @Override
    public void d() {
        if (FragmentManager.I0(3)) {
            ((Object)this).toString();
        }
        this.h = true;
    }

    public void e(Fragment fragment) {
        if (this.j) {
            FragmentManager.I0(2);
            return;
        }
        if (!this.d.containsKey(fragment.h)) {
            this.d.put(fragment.h, fragment);
            if (FragmentManager.I0(2)) {
                ((Object)fragment).toString();
            }
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && u.class == object.getClass()) {
            object = (u)object;
            if (((Object)this.d).equals(((u)object).d) && ((Object)this.e).equals(((u)object).e) && ((Object)this.f).equals(((u)object).f)) {
                return true;
            }
        }
        return false;
    }

    public void f(Fragment fragment) {
        if (FragmentManager.I0(3)) {
            Objects.toString(fragment);
        }
        this.h(fragment.h);
    }

    public void g(String string) {
        FragmentManager.I0(3);
        this.h(string);
    }

    public final void h(String string) {
        Object object = (u)this.e.get(string);
        if (object != null) {
            ((u)object).d();
            this.e.remove(string);
        }
        if ((object = (b0)this.f.get(string)) != null) {
            ((b0)object).a();
            this.f.remove(string);
        }
    }

    public int hashCode() {
        return (((Object)this.d).hashCode() * 31 + ((Object)this.e).hashCode()) * 31 + ((Object)this.f).hashCode();
    }

    public Fragment i(String string) {
        return (Fragment)this.d.get(string);
    }

    public u j(Fragment fragment) {
        u u3;
        u u4 = u3 = (u)this.e.get(fragment.h);
        if (u3 == null) {
            u4 = new u(this.g);
            this.e.put(fragment.h, u4);
        }
        return u4;
    }

    public Collection l() {
        return new ArrayList(this.d.values());
    }

    public b0 m(Fragment fragment) {
        b0 b02;
        b0 b03 = b02 = (b0)this.f.get(fragment.h);
        if (b02 == null) {
            b03 = new b0();
            this.f.put(fragment.h, b03);
        }
        return b03;
    }

    public boolean n() {
        return this.h;
    }

    public void o(Fragment fragment) {
        if (this.j) {
            FragmentManager.I0(2);
            return;
        }
        if (this.d.remove(fragment.h) != null && FragmentManager.I0(2)) {
            ((Object)fragment).toString();
        }
    }

    public void p(boolean bl) {
        this.j = bl;
    }

    public boolean q(Fragment fragment) {
        if (!this.d.containsKey(fragment.h)) {
            return true;
        }
        if (this.g) {
            return this.h;
        }
        return this.i ^ true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("FragmentManagerViewModel{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append("} Fragments (");
        Iterator<Object> iterator = this.d.values().iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
            if (!iterator.hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(") Child Non Config (");
        iterator = this.e.keySet().iterator();
        while (iterator.hasNext()) {
            stringBuilder.append((String)iterator.next());
            if (!iterator.hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(") ViewModelStores (");
        iterator = this.f.keySet().iterator();
        while (iterator.hasNext()) {
            stringBuilder.append((String)iterator.next());
            if (!iterator.hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

