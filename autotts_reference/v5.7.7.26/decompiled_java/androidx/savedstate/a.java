/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.savedstate;

import android.os.Bundle;
import androidx.lifecycle.f;
import androidx.savedstate.Recreator;
import j1.d;
import java.util.Map;
import k.b;
import o3.g;
import o3.k;

public final class a {
    public static final b g = new b(null);
    public final k.b a = new k.b();
    public boolean b;
    public Bundle c;
    public boolean d;
    public Recreator.b e;
    public boolean f = true;

    public static /* synthetic */ void a(a a4, androidx.lifecycle.k k3, f.a a5) {
        androidx.savedstate.a.d(a4, k3, a5);
    }

    public static final void d(a a4, androidx.lifecycle.k k3, f.a a5) {
        k.e(a4, "this$0");
        k.e(k3, "<anonymous parameter 0>");
        k.e((Object)a5, "event");
        if (a5 == f.a.ON_START) {
            a4.f = true;
            return;
        }
        if (a5 == f.a.ON_STOP) {
            a4.f = false;
        }
    }

    public final Bundle b(String string) {
        k.e(string, "key");
        if (this.d) {
            Object object = this.c;
            if (object != null) {
                object = object != null ? object.getBundle(string) : null;
                Bundle bundle = this.c;
                if (bundle != null) {
                    bundle.remove(string);
                }
                if ((string = this.c) != null && !string.isEmpty()) {
                    return object;
                }
                this.c = null;
                return object;
            }
            return null;
        }
        throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component");
    }

    public final c c(String string) {
        k.e(string, "key");
        for (Object object : this.a) {
            k.d(object, "components");
            String string2 = (String)object.getKey();
            object = (c)object.getValue();
            if (!k.a(string2, string)) continue;
            return object;
        }
        return null;
    }

    public final void e(f f3) {
        k.e(f3, "lifecycle");
        if (!this.b) {
            f3.a(new j1.b(this));
            this.b = true;
            return;
        }
        throw new IllegalStateException("SavedStateRegistry was already attached.");
    }

    public final void f(Bundle object) {
        if (this.b) {
            if (!this.d) {
                object = object != null ? object.getBundle("androidx.lifecycle.BundlableSavedStateRegistry.key") : null;
                this.c = object;
                this.d = true;
                return;
            }
            throw new IllegalStateException("SavedStateRegistry was already restored.");
        }
        throw new IllegalStateException("You must call performAttach() before calling performRestore(Bundle).");
    }

    public final void g(Bundle bundle) {
        k.e(bundle, "outBundle");
        Bundle bundle2 = new Bundle();
        Object object = this.c;
        if (object != null) {
            bundle2.putAll((Bundle)object);
        }
        b.d d3 = this.a.c();
        k.d(d3, "this.components.iteratorWithAdditions()");
        while (d3.hasNext()) {
            object = (Map.Entry)d3.next();
            bundle2.putBundle((String)object.getKey(), ((c)object.getValue()).a());
        }
        if (!bundle2.isEmpty()) {
            bundle.putBundle("androidx.lifecycle.BundlableSavedStateRegistry.key", bundle2);
        }
    }

    public final void h(String string, c c3) {
        k.e(string, "key");
        k.e(c3, "provider");
        if ((c)this.a.f(string, c3) == null) {
            return;
        }
        throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
    }

    public final void i(Class object) {
        k.e(object, "clazz");
        if (this.f) {
            block4: {
                Object object2 = this.e;
                Recreator.b b3 = object2;
                if (object2 == null) {
                    b3 = new Recreator.b(this);
                }
                this.e = b3;
                try {
                    ((Class)object).getDeclaredConstructor(null);
                    b3 = this.e;
                    if (b3 == null) break block4;
                    object = ((Class)object).getName();
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Class ");
                    ((StringBuilder)object2).append(((Class)object).getSimpleName());
                    ((StringBuilder)object2).append(" must have default constructor in order to be automatically recreated");
                    throw new IllegalArgumentException(((StringBuilder)object2).toString(), noSuchMethodException);
                }
                k.d(object, "clazz.name");
                b3.b((String)object);
            }
            return;
        }
        throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
    }

    public static interface a {
        public void a(d var1);
    }

    public static final class b {
        public b() {
        }

        public /* synthetic */ b(g g3) {
            this();
        }
    }

    public static interface c {
        public Bundle a();
    }
}

