/*
 * Decompiled with CFR 0.152.
 */
package y0;

import java.nio.ByteBuffer;
import y0.c;

public final class a
extends c {
    public a f(int n3, ByteBuffer byteBuffer) {
        this.g(n3, byteBuffer);
        return this;
    }

    public void g(int n3, ByteBuffer byteBuffer) {
        this.c(n3, byteBuffer);
    }

    public int h(int n3) {
        int n4 = this.b(16);
        if (n4 != 0) {
            return this.b.getInt(this.d(n4) + n3 * 4);
        }
        return 0;
    }

    public int i() {
        int n3 = this.b(16);
        if (n3 != 0) {
            return this.e(n3);
        }
        return 0;
    }

    public boolean j() {
        int n3 = this.b(6);
        return n3 != 0 && this.b.get(n3 + this.a) != 0;
    }

    public short k() {
        int n3 = this.b(14);
        if (n3 != 0) {
            return this.b.getShort(n3 + this.a);
        }
        return 0;
    }

    public int l() {
        int n3 = this.b(4);
        if (n3 != 0) {
            return this.b.getInt(n3 + this.a);
        }
        return 0;
    }

    public short m() {
        int n3 = this.b(8);
        if (n3 != 0) {
            return this.b.getShort(n3 + this.a);
        }
        return 0;
    }

    public short n() {
        int n3 = this.b(12);
        if (n3 != 0) {
            return this.b.getShort(n3 + this.a);
        }
        return 0;
    }
}

