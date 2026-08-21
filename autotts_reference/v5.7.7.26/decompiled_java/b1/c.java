/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.util.Log
 *  android.view.ViewGroup
 */
package b1;

import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import b1.d;
import b1.e;
import b1.g;
import b1.h;
import e3.b0;
import e3.d0;
import e3.t;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import o3.k;

public final class c {
    public static final c a = new c();
    public static c b = c.d;

    public static /* synthetic */ void a(String string, g g3) {
        c.d(string, g3);
    }

    public static final void d(String string, g g3) {
        k.e(g3, "$violation");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Policy violation with PENALTY_DEATH in ");
        stringBuilder.append(string);
        Log.e((String)"FragmentStrictMode", (String)stringBuilder.toString(), (Throwable)g3);
        throw g3;
    }

    public static final void f(Fragment fragment, String object) {
        k.e(fragment, "fragment");
        k.e(object, "previousFragmentId");
        object = new b1.a(fragment, (String)object);
        c c3 = a;
        c3.e((g)object);
        c c4 = c3.b(fragment);
        if (c4.a().contains((Object)b1.c$a.e) && c3.k(c4, fragment.getClass(), object.getClass())) {
            c3.c(c4, (g)object);
        }
    }

    public static final void g(Fragment fragment, ViewGroup object) {
        k.e(fragment, "fragment");
        d d3 = new d(fragment, (ViewGroup)object);
        c c3 = a;
        c3.e(d3);
        object = c3.b(fragment);
        if (((c)object).a().contains((Object)b1.c$a.f) && c3.k((c)object, fragment.getClass(), d3.getClass())) {
            c3.c((c)object, d3);
        }
    }

    public static final void h(Fragment fragment) {
        k.e(fragment, "fragment");
        e e3 = new e(fragment);
        c c3 = a;
        c3.e(e3);
        c c4 = c3.b(fragment);
        if (c4.a().contains((Object)b1.c$a.i) && c3.k(c4, fragment.getClass(), e3.getClass())) {
            c3.c(c4, e3);
        }
    }

    public static final void i(Fragment fragment, ViewGroup object) {
        k.e(fragment, "fragment");
        k.e(object, "container");
        object = new h(fragment, (ViewGroup)object);
        c c3 = a;
        c3.e((g)object);
        c c4 = c3.b(fragment);
        if (c4.a().contains((Object)b1.c$a.j) && c3.k(c4, fragment.getClass(), object.getClass())) {
            c3.c(c4, (g)object);
        }
    }

    public final c b(Fragment object) {
        while (object != null) {
            if (((Fragment)object).X()) {
                FragmentManager fragmentManager = ((Fragment)object).E();
                k.d(fragmentManager, "declaringFragment.parentFragmentManager");
                if (fragmentManager.B0() != null) {
                    object = fragmentManager.B0();
                    k.b(object);
                    return object;
                }
            }
            object = ((Fragment)object).D();
        }
        return b;
    }

    public final void c(c c3, g g3) {
        Fragment fragment = g3.a();
        String string = fragment.getClass().getName();
        c3.a().contains((Object)b1.c$a.c);
        c3.b();
        if (c3.a().contains((Object)b1.c$a.d)) {
            this.j(fragment, new b1.b(string, g3));
        }
    }

    public final void e(g g3) {
        if (FragmentManager.I0(3)) {
            g3.a().getClass();
        }
    }

    public final void j(Fragment fragment, Runnable runnable) {
        if (fragment.X()) {
            fragment = fragment.E().v0().v();
            k.d(fragment, "fragment.parentFragmentManager.host.handler");
            if (k.a(fragment.getLooper(), Looper.myLooper())) {
                runnable.run();
                return;
            }
            fragment.post(runnable);
            return;
        }
        runnable.run();
    }

    public final boolean k(c object, Class object2, Class clazz) {
        object2 = ((Class)object2).getName();
        if ((object = (Set)((c)object).c().get(object2)) == null) {
            return true;
        }
        if (!k.a(clazz.getSuperclass(), g.class) && t.o((Iterable)object, clazz.getSuperclass())) {
            return false;
        }
        return object.contains(clazz) ^ true;
    }

    public static final class a
    extends Enum {
        public static final /* enum */ a c = new a("PENALTY_LOG", 0);
        public static final /* enum */ a d = new a("PENALTY_DEATH", 1);
        public static final /* enum */ a e = new a("DETECT_FRAGMENT_REUSE", 2);
        public static final /* enum */ a f = new a("DETECT_FRAGMENT_TAG_USAGE", 3);
        public static final /* enum */ a g = new a("DETECT_RETAIN_INSTANCE_USAGE", 4);
        public static final /* enum */ a h = new a("DETECT_SET_USER_VISIBLE_HINT", 5);
        public static final /* enum */ a i = new a("DETECT_TARGET_FRAGMENT_USAGE", 6);
        public static final /* enum */ a j = new a("DETECT_WRONG_FRAGMENT_CONTAINER", 7);
        public static final a[] k = b1.c$a.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public a() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static final /* synthetic */ a[] a() {
            return new a[]{c, d, e, f, g, h, i, j};
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        public static a[] values() {
            return (a[])k.clone();
        }
    }

    public static interface b {
    }

    public static final class c {
        public static final a c = new a(null);
        public static final c d = new c(d0.a(), null, b0.d());
        public final Set a;
        public final Map b;

        public c(Set object, b object2, Map object32) {
            k.e(object, "flags");
            k.e(object32, "allowedViolations");
            this.a = object;
            object = new LinkedHashMap<String, Set>();
            for (Map.Entry entry : object32.entrySet()) {
                object.put((String)entry.getKey(), (Set)entry.getValue());
            }
            this.b = object;
        }

        public final Set a() {
            return this.a;
        }

        public final b b() {
            return null;
        }

        public final Map c() {
            return this.b;
        }

        public static final class a {
            public a() {
            }

            public /* synthetic */ a(o3.g g3) {
                this();
            }
        }
    }
}

