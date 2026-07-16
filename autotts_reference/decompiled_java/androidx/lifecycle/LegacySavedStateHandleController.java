/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.SavedStateHandleController;
import androidx.lifecycle.b0;
import androidx.lifecycle.c0;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import androidx.lifecycle.y;
import androidx.savedstate.a;
import j1.d;
import java.util.Iterator;

public final class LegacySavedStateHandleController {
    public static final LegacySavedStateHandleController a = new LegacySavedStateHandleController();

    public static final void a(y object, androidx.savedstate.a a4, f f3) {
        o3.k.e(object, "viewModel");
        o3.k.e(a4, "registry");
        o3.k.e(f3, "lifecycle");
        object = (SavedStateHandleController)((y)object).c("androidx.lifecycle.savedstate.vm.tag");
        if (object != null && !((SavedStateHandleController)object).i()) {
            ((SavedStateHandleController)object).h(a4, f3);
            a.b(a4, f3);
        }
    }

    public final void b(androidx.savedstate.a a4, f f3) {
        f.b b3 = f3.b();
        if (b3 != f.b.d && !b3.b(f.b.f)) {
            f3.a(new i(f3, a4){
                public final f a;
                public final androidx.savedstate.a b;
                {
                    this.a = f3;
                    this.b = a4;
                }

                public void d(k k3, f.a a4) {
                    o3.k.e(k3, "source");
                    o3.k.e((Object)((Object)a4), "event");
                    if (a4 == f.a.ON_START) {
                        this.a.c(this);
                        this.b.i(a.class);
                    }
                }
            });
            return;
        }
        a4.i(a.class);
    }

    public static final class a
    implements a.a {
        @Override
        public void a(d d3) {
            o3.k.e(d3, "owner");
            if (d3 instanceof c0) {
                b0 b02 = ((c0)((Object)d3)).r();
                androidx.savedstate.a a4 = d3.c();
                Iterator iterator = b02.c().iterator();
                while (iterator.hasNext()) {
                    y y3 = b02.b((String)iterator.next());
                    o3.k.b(y3);
                    LegacySavedStateHandleController.a(y3, a4, d3.t());
                }
                if (!b02.c().isEmpty()) {
                    a4.i(a.class);
                }
                return;
            }
            throw new IllegalStateException("Internal error: OnRecreation should be registered only on components that implement ViewModelStoreOwner");
        }
    }
}

