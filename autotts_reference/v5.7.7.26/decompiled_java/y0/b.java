/*
 * Decompiled with CFR 0.152.
 */
package y0;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import y0.a;
import y0.c;

public final class b
extends c {
    public static b h(ByteBuffer byteBuffer) {
        return y0.b.i(byteBuffer, new b());
    }

    public static b i(ByteBuffer byteBuffer, b b3) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return b3.f(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public b f(int n3, ByteBuffer byteBuffer) {
        this.g(n3, byteBuffer);
        return this;
    }

    public void g(int n3, ByteBuffer byteBuffer) {
        this.c(n3, byteBuffer);
    }

    public a j(a a4, int n3) {
        int n4 = this.b(6);
        if (n4 != 0) {
            return a4.f(this.a(this.d(n4) + n3 * 4), this.b);
        }
        return null;
    }

    public int k() {
        int n3 = this.b(6);
        if (n3 != 0) {
            return this.e(n3);
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
}

