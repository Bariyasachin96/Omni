/*
 * Decompiled with CFR 0.152.
 */
package r3;

import e3.y;
import j3.c;
import o3.g;
import r3.b;

public class a
implements Iterable {
    public static final a f = new a(null);
    public final int c;
    public final int d;
    public final int e;

    public a(int n3, int n4, int n5) {
        if (n5 != 0) {
            if (n5 != Integer.MIN_VALUE) {
                this.c = n3;
                this.d = j3.c.b(n3, n4, n5);
                this.e = n5;
                return;
            }
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        }
        throw new IllegalArgumentException("Step must be non-zero.");
    }

    public final int a() {
        return this.c;
    }

    public final int b() {
        return this.d;
    }

    public final int c() {
        return this.e;
    }

    public y d() {
        return new b(this.c, this.d, this.e);
    }

    public boolean equals(Object object) {
        block2: {
            block3: {
                if (!(object instanceof a)) break block2;
                if (this.isEmpty() && ((a)object).isEmpty()) break block3;
                int n3 = this.c;
                object = (a)object;
                if (n3 != ((a)object).c || this.d != ((a)object).d || this.e != ((a)object).e) break block2;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.isEmpty()) {
            return -1;
        }
        return (this.c * 31 + this.d) * 31 + this.e;
    }

    public boolean isEmpty() {
        if (this.e > 0) {
            return this.c > this.d;
        }
        return this.c < this.d;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        int n3;
        StringBuilder stringBuilder;
        if (this.e > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.c);
            stringBuilder.append("..");
            stringBuilder.append(this.d);
            stringBuilder.append(" step ");
            n3 = this.e;
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.c);
            stringBuilder.append(" downTo ");
            stringBuilder.append(this.d);
            stringBuilder.append(" step ");
            n3 = -this.e;
        }
        stringBuilder.append(n3);
        return stringBuilder.toString();
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final a a(int n3, int n4, int n5) {
            return new a(n3, n4, n5);
        }
    }
}

