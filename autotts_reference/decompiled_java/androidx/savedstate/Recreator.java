/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.savedstate;

import android.os.Bundle;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.savedstate.a;
import j1.d;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import o3.g;
import o3.k;

public final class Recreator
implements i {
    public static final a b = new a(null);
    public final d a;

    public Recreator(d d3) {
        k.e(d3, "owner");
        this.a = d3;
    }

    @Override
    public void d(androidx.lifecycle.k k3, f.a object) {
        k.e(k3, "source");
        k.e(object, "event");
        if (object == f.a.ON_CREATE) {
            k3.t().c(this);
            k3 = this.a.c().b("androidx.savedstate.Restarter");
            if (k3 != null) {
                object = k3.getStringArrayList("classes_to_restore");
                if (object != null) {
                    int n3 = ((ArrayList)object).size();
                    for (int i3 = 0; i3 < n3; ++i3) {
                        k3 = ((ArrayList)object).get(i3);
                        this.h((String)((Object)k3));
                    }
                }
            } else {
                return;
            }
            throw new IllegalStateException("Bundle with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
        }
        throw new AssertionError((Object)"Next event must be ON_CREATE");
    }

    public final void h(String charSequence) {
        Constructor<a.a> constructor;
        Object object;
        try {
            object = Class.forName((String)charSequence, false, Recreator.class.getClassLoader()).asSubclass(a.a.class);
            k.d(object, "{\n                Class.…class.java)\n            }");
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" wasn't found");
            throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
        }
        try {
            constructor = ((Class)object).getDeclaredConstructor(null);
            ((AccessibleObject)constructor).setAccessible(true);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Class ");
            ((StringBuilder)charSequence).append(((Class)object).getSimpleName());
            ((StringBuilder)charSequence).append(" must have default constructor in order to be automatically recreated");
            throw new IllegalStateException(((StringBuilder)charSequence).toString(), noSuchMethodException);
        }
        try {
            object = constructor.newInstance(null);
            k.d(object, "{\n                constr…wInstance()\n            }");
            object = (a.a)object;
            object.a(this.a);
            return;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to instantiate ");
            ((StringBuilder)object).append((String)charSequence);
            throw new RuntimeException(((StringBuilder)object).toString(), exception);
        }
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }
    }

    public static final class b
    implements a.c {
        public final Set a;

        public b(androidx.savedstate.a a4) {
            k.e(a4, "registry");
            this.a = new LinkedHashSet();
            a4.h("androidx.savedstate.Restarter", this);
        }

        @Override
        public Bundle a() {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("classes_to_restore", new ArrayList(this.a));
            return bundle;
        }

        public final void b(String string) {
            k.e(string, "className");
            this.a.add(string);
        }
    }
}

