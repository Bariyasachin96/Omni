/*
 * Decompiled with CFR 0.152.
 */
package r3;

import o3.g;

public final class c
extends r3.a {
    public static final a g = new a(null);
    public static final c h = new c(1, 0);

    public c(int n3, int n4) {
        super(n3, n4, 1);
    }

    @Override
    public boolean equals(Object object) {
        int n3;
        return object instanceof c && (this.isEmpty() && ((c)object).isEmpty() || (n3 = this.a()) == ((r3.a)(object = (c)object)).a() && this.b() == ((r3.a)object).b());
    }

    @Override
    public int hashCode() {
        if (this.isEmpty()) {
            return -1;
        }
        return this.a() * 31 + this.b();
    }

    @Override
    public boolean isEmpty() {
        return this.a() > this.b();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.a());
        stringBuilder.append("..");
        stringBuilder.append(this.b());
        return stringBuilder.toString();
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final c a() {
            return h;
        }
    }
}

