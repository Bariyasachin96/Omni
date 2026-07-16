/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.k;
import androidx.lifecycle.f;
import b1.c;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public abstract class y {
    public final k a;
    public final ClassLoader b;
    public ArrayList c = new ArrayList();
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public boolean i;
    public boolean j = true;
    public String k;
    public int l;
    public CharSequence m;
    public int n;
    public CharSequence o;
    public ArrayList p;
    public ArrayList q;
    public boolean r = false;
    public ArrayList s;

    public y(k k3, ClassLoader classLoader) {
        this.a = k3;
        this.b = classLoader;
    }

    public y b(int n3, Fragment fragment, String string) {
        this.k(n3, fragment, string, 1);
        return this;
    }

    public y c(ViewGroup viewGroup, Fragment fragment, String string) {
        fragment.J = viewGroup;
        return this.b(viewGroup.getId(), fragment, string);
    }

    public y d(Fragment fragment, String string) {
        this.k(0, fragment, string, 1);
        return this;
    }

    public void e(a a4) {
        this.c.add(a4);
        a4.d = this.d;
        a4.e = this.e;
        a4.f = this.f;
        a4.g = this.g;
    }

    public abstract int f();

    public abstract int g();

    public abstract void h();

    public abstract void i();

    public y j() {
        if (!this.i) {
            this.j = false;
            return this;
        }
        throw new IllegalStateException("This transaction is already being added to the back stack");
    }

    public void k(int n3, Fragment object, String charSequence, int n4) {
        Object object2 = ((Fragment)object).S;
        if (object2 != null) {
            b1.c.f((Fragment)object, (String)object2);
        }
        object2 = object.getClass();
        int n5 = ((Class)object2).getModifiers();
        if (!((Class)object2).isAnonymousClass() && Modifier.isPublic(n5) && (!((Class)object2).isMemberClass() || Modifier.isStatic(n5))) {
            if (charSequence != null) {
                object2 = ((Fragment)object).B;
                if (object2 != null && !((String)charSequence).equals(object2)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Can't change tag of fragment ");
                    ((StringBuilder)object2).append(object);
                    ((StringBuilder)object2).append(": was ");
                    ((StringBuilder)object2).append(((Fragment)object).B);
                    ((StringBuilder)object2).append(" now ");
                    ((StringBuilder)object2).append((String)charSequence);
                    throw new IllegalStateException(((StringBuilder)object2).toString());
                }
                ((Fragment)object).B = charSequence;
            }
            if (n3 != 0) {
                if (n3 != -1) {
                    n5 = ((Fragment)object).z;
                    if (n5 != 0 && n5 != n3) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Can't change container ID of fragment ");
                        ((StringBuilder)charSequence).append(object);
                        ((StringBuilder)charSequence).append(": was ");
                        ((StringBuilder)charSequence).append(((Fragment)object).z);
                        ((StringBuilder)charSequence).append(" now ");
                        ((StringBuilder)charSequence).append(n3);
                        throw new IllegalStateException(((StringBuilder)charSequence).toString());
                    }
                    ((Fragment)object).z = n3;
                    ((Fragment)object).A = n3;
                } else {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Can't add fragment ");
                    ((StringBuilder)object2).append(object);
                    ((StringBuilder)object2).append(" with tag ");
                    ((StringBuilder)object2).append((String)charSequence);
                    ((StringBuilder)object2).append(" to container view with no id");
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
            }
            this.e(new a(n4, (Fragment)object));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(((Class)object2).getCanonicalName());
        ((StringBuilder)object).append(" must be a public static class to be  properly recreated from instance state.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public abstract boolean l();

    public y m(Fragment fragment) {
        this.e(new a(3, fragment));
        return this;
    }

    public y n(int n3, Fragment fragment) {
        return this.o(n3, fragment, null);
    }

    public y o(int n3, Fragment fragment, String string) {
        if (n3 != 0) {
            this.k(n3, fragment, string, 2);
            return this;
        }
        throw new IllegalArgumentException("Must use non-zero containerViewId");
    }

    public y p(Fragment fragment, f.b b3) {
        this.e(new a(10, fragment, b3));
        return this;
    }

    public y q(boolean bl) {
        this.r = bl;
        return this;
    }

    public static final class a {
        public int a;
        public Fragment b;
        public boolean c;
        public int d;
        public int e;
        public int f;
        public int g;
        public f.b h;
        public f.b i;

        public a() {
        }

        public a(int n3, Fragment object) {
            this.a = n3;
            this.b = object;
            this.c = false;
            object = f.b.g;
            this.h = object;
            this.i = object;
        }

        public a(int n3, Fragment fragment, f.b b3) {
            this.a = n3;
            this.b = fragment;
            this.c = false;
            this.h = fragment.T;
            this.i = b3;
        }

        public a(int n3, Fragment object, boolean bl) {
            this.a = n3;
            this.b = object;
            this.c = bl;
            object = f.b.g;
            this.h = object;
            this.i = object;
        }
    }
}

