/*
 * Decompiled with CFR 0.152.
 */
package y0;

import java.nio.ByteBuffer;
import y0.d;

public abstract class c {
    public int a;
    public ByteBuffer b;
    public int c;
    public int d;
    public d e = y0.d.a();

    public int a(int n3) {
        return n3 + this.b.getInt(n3);
    }

    public int b(int n3) {
        if (n3 < this.d) {
            return this.b.getShort(this.c + n3);
        }
        return 0;
    }

    public void c(int n3, ByteBuffer byteBuffer) {
        this.b = byteBuffer;
        if (byteBuffer != null) {
            this.a = n3;
            n3 -= byteBuffer.getInt(n3);
            this.c = n3;
            this.d = this.b.getShort(n3);
            return;
        }
        this.a = 0;
        this.c = 0;
        this.d = 0;
    }

    public int d(int n3) {
        return (n3 += this.a) + this.b.getInt(n3) + 4;
    }

    public int e(int n3) {
        int n4 = n3 + this.a;
        n3 = this.b.getInt(n4);
        return this.b.getInt(n4 + n3);
    }
}

