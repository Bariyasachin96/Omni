/*
 * Decompiled with CFR 0.152.
 */
package d3;

import d3.b;
import d3.i;
import java.io.Serializable;
import n3.a;
import o3.k;

public final class g
implements b,
Serializable {
    public a c;
    public volatile Object d;
    public final Object e;

    public g(a object, Object object2) {
        k.e(object, "initializer");
        this.c = object;
        this.d = i.a;
        object = object2;
        if (object2 == null) {
            object = this;
        }
        this.e = object;
    }

    public /* synthetic */ g(a a4, Object object, int n3, o3.g g3) {
        if ((n3 & 2) != 0) {
            object = null;
        }
        this(a4, object);
    }

    public boolean a() {
        return this.d != i.a;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object getValue() {
        Object object = this.d;
        i i3 = i.a;
        if (object != i3) {
            return object;
        }
        Object object2 = this.e;
        synchronized (object2) {
            object = this.d;
            if (object == i3) {
                object = this.c;
                k.b(object);
                this.d = object = object.a();
                this.c = null;
            }
            return object;
        }
    }

    public String toString() {
        if (this.a()) {
            return String.valueOf(this.getValue());
        }
        return "Lazy value not initialized yet.";
    }
}

