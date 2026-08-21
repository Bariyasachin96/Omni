/*
 * Decompiled with CFR 0.152.
 */
package o3;

import o3.c;
import o3.h;
import o3.k;
import o3.n;
import s3.a;
import s3.d;

public abstract class i
extends c
implements h,
d {
    public final int j;
    public final int k;

    public i(int n3, Object object, Class clazz, String string, String string2, int n4) {
        boolean bl = (n4 & 1) == 1;
        super(object, clazz, string, string2, bl);
        this.j = n3;
        this.k = n4 >> 1;
    }

    @Override
    public int c() {
        return this.j;
    }

    @Override
    public a d() {
        return n.a(this);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof i) {
            object = (i)object;
            return this.h().equals(((c)object).h()) && this.j().equals(((c)object).j()) && this.k == ((i)object).k && this.j == ((i)object).j && o3.k.a(this.g(), ((c)object).g()) && o3.k.a(this.i(), ((c)object).i());
        }
        if (object instanceof d) {
            return object.equals(this.b());
        }
        return false;
    }

    public int hashCode() {
        int n3 = this.i() == null ? 0 : this.i().hashCode() * 31;
        return (n3 + this.h().hashCode()) * 31 + this.j().hashCode();
    }

    public String toString() {
        Object object = this.b();
        if (object != this) {
            return object.toString();
        }
        if ("<init>".equals(this.h())) {
            return "constructor (Kotlin reflection is not available)";
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("function ");
        ((StringBuilder)object).append(this.h());
        ((StringBuilder)object).append(" (Kotlin reflection is not available)");
        return ((StringBuilder)object).toString();
    }
}

