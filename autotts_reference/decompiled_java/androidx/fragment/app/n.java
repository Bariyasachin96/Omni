/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 */
package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.concurrent.CopyOnWriteArrayList;

public class n {
    public final CopyOnWriteArrayList a = new CopyOnWriteArrayList();
    public final FragmentManager b;

    public n(FragmentManager fragmentManager) {
        this.b = fragmentManager;
    }

    public void a(Fragment fragment, Bundle bundle, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().a(fragment, bundle, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.a(this.b, fragment, bundle);
        }
    }

    public void b(Fragment fragment, boolean bl) {
        Context context = this.b.v0().q();
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().b(fragment, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.b(this.b, fragment, context);
        }
    }

    public void c(Fragment fragment, Bundle bundle, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().c(fragment, bundle, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.c(this.b, fragment, bundle);
        }
    }

    public void d(Fragment fragment, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().d(fragment, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.d(this.b, fragment);
        }
    }

    public void e(Fragment fragment, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().e(fragment, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.e(this.b, fragment);
        }
    }

    public void f(Fragment fragment, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().f(fragment, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.f(this.b, fragment);
        }
    }

    public void g(Fragment fragment, boolean bl) {
        Context context = this.b.v0().q();
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().g(fragment, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.g(this.b, fragment, context);
        }
    }

    public void h(Fragment fragment, Bundle bundle, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().h(fragment, bundle, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.h(this.b, fragment, bundle);
        }
    }

    public void i(Fragment fragment, boolean bl) {
        Object object2 = this.b.y0();
        if (object2 != null) {
            ((Fragment)object2).E().x0().i(fragment, true);
        }
        for (Object object2 : this.a) {
            if (bl && !((a)object2).b) continue;
            ((a)object2).a.i(this.b, fragment);
        }
    }

    public void j(Fragment fragment, Bundle bundle, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().j(fragment, bundle, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.j(this.b, fragment, bundle);
        }
    }

    public void k(Fragment fragment, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().k(fragment, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.k(this.b, fragment);
        }
    }

    public void l(Fragment fragment, boolean bl) {
        Object object2 = this.b.y0();
        if (object2 != null) {
            ((Fragment)object2).E().x0().l(fragment, true);
        }
        for (Object object2 : this.a) {
            if (bl && !((a)object2).b) continue;
            ((a)object2).a.l(this.b, fragment);
        }
    }

    public void m(Fragment fragment, View view, Bundle bundle, boolean bl) {
        Fragment fragment2 = this.b.y0();
        if (fragment2 != null) {
            fragment2.E().x0().m(fragment, view, bundle, true);
        }
        for (a a4 : this.a) {
            if (bl && !a4.b) continue;
            a4.a.m(this.b, fragment, view, bundle);
        }
    }

    public void n(Fragment fragment, boolean bl) {
        Object object2 = this.b.y0();
        if (object2 != null) {
            ((Fragment)object2).E().x0().n(fragment, true);
        }
        for (Object object2 : this.a) {
            if (bl && !((a)object2).b) continue;
            ((a)object2).a.n(this.b, fragment);
        }
    }

    public void o(FragmentManager.k k3, boolean bl) {
        this.a.add(new a(k3, bl));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void p(FragmentManager.k k3) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.a;
        synchronized (copyOnWriteArrayList) {
            try {
                int n3 = this.a.size();
                for (int i3 = 0; i3 < n3; ++i3) {
                    if (((a)this.a.get((int)i3)).a != k3) continue;
                    this.a.remove(i3);
                    break;
                }
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public static final class a {
        public final FragmentManager.k a;
        public final boolean b;

        public a(FragmentManager.k k3, boolean bl) {
            this.a = k3;
            this.b = bl;
        }
    }
}

