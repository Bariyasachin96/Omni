/*
 * Decompiled with CFR 0.152.
 */
package r3;

import e3.y;
import java.util.NoSuchElementException;

public final class b
extends y {
    public final int c;
    public final int d;
    public boolean e;
    public int f;

    /*
     * Enabled aggressive block sorting
     */
    public b(int n3, int n4, int n5) {
        this.c = n5;
        this.d = n4;
        boolean bl = false;
        if (n5 > 0 ? n3 <= n4 : n3 >= n4) {
            bl = true;
        }
        this.e = bl;
        if (!bl) {
            n3 = n4;
        }
        this.f = n3;
    }

    @Override
    public boolean hasNext() {
        return this.e;
    }

    @Override
    public int nextInt() {
        int n3 = this.f;
        if (n3 == this.d) {
            if (this.e) {
                this.e = false;
                return n3;
            }
            throw new NoSuchElementException();
        }
        this.f = this.c + n3;
        return n3;
    }
}

