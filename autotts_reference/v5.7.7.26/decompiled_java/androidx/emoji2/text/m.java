/*
 * Decompiled with CFR 0.152.
 */
package androidx.emoji2.text;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class m {
    public static b a(c c3) {
        c3.a(4);
        int n3 = c3.readUnsignedShort();
        if (n3 <= 100) {
            long l3;
            int n4;
            int n5;
            block6: {
                c3.a(6);
                n5 = 0;
                for (n4 = 0; n4 < n3; ++n4) {
                    int n6 = c3.c();
                    c3.a(4);
                    l3 = c3.b();
                    c3.a(4);
                    if (1835365473 != n6) {
                        continue;
                    }
                    break block6;
                }
                l3 = -1L;
            }
            if (l3 != -1L) {
                c3.a((int)(l3 - c3.getPosition()));
                c3.a(12);
                long l4 = c3.b();
                n4 = n5;
                while ((long)n4 < l4) {
                    n5 = c3.c();
                    long l5 = c3.b();
                    long l6 = c3.b();
                    if (1164798569 != n5 && 1701669481 != n5) {
                        ++n4;
                        continue;
                    }
                    return new b(l5 + l3, l6);
                }
            }
            throw new IOException("Cannot read metadata.");
        }
        throw new IOException("Cannot read metadata.");
    }

    public static y0.b b(ByteBuffer byteBuffer) {
        byteBuffer = byteBuffer.duplicate();
        byteBuffer.position((int)m.a(new a(byteBuffer)).a());
        return y0.b.h(byteBuffer);
    }

    public static long c(int n3) {
        return (long)n3 & 0xFFFFFFFFL;
    }

    public static int d(short s3) {
        return s3 & 0xFFFF;
    }

    public static class a
    implements c {
        public final ByteBuffer a;

        public a(ByteBuffer byteBuffer) {
            this.a = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        @Override
        public void a(int n3) {
            ByteBuffer byteBuffer = this.a;
            byteBuffer.position(byteBuffer.position() + n3);
        }

        @Override
        public long b() {
            return m.c(this.a.getInt());
        }

        @Override
        public int c() {
            return this.a.getInt();
        }

        @Override
        public long getPosition() {
            return this.a.position();
        }

        @Override
        public int readUnsignedShort() {
            return m.d(this.a.getShort());
        }
    }

    public static class b {
        public final long a;
        public final long b;

        public b(long l3, long l4) {
            this.a = l3;
            this.b = l4;
        }

        public long a() {
            return this.a;
        }
    }

    public static interface c {
        public void a(int var1);

        public long b();

        public int c();

        public long getPosition();

        public int readUnsignedShort();
    }
}

