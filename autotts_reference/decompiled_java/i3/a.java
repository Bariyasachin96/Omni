/*
 * Decompiled with CFR 0.152.
 */
package i3;

import h3.c;
import i3.e;
import i3.f;
import java.io.Serializable;
import o3.k;

public abstract class a
implements g3.a,
Serializable {
    public final g3.a c;

    public a(g3.a a4) {
        this.c = a4;
    }

    @Override
    public final void d(Object object) {
        g3.a a4 = this;
        do {
            f.b(a4);
            a a5 = a4;
            a4 = a5.c;
            k.b(a4);
            try {
                object = a5.j(object);
                if (object == h3.c.b()) {
                    return;
                }
                object = d3.e.a(object);
            }
            catch (Throwable throwable) {
                object = d3.e.c;
                object = d3.e.a(d3.f.a(throwable));
            }
            a5.k();
        } while (a4 instanceof a);
        a4.d(object);
    }

    public g3.a g(Object object, g3.a a4) {
        k.e(a4, "completion");
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }

    public final g3.a h() {
        return this.c;
    }

    public StackTraceElement i() {
        return e.d(this);
    }

    public abstract Object j(Object var1);

    public void k() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Continuation at ");
        Object object = this.i();
        if (object == null) {
            object = this.getClass().getName();
        }
        stringBuilder.append(object);
        return stringBuilder.toString();
    }
}

