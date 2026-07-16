/*
 * Decompiled with CFR 0.152.
 */
package o;

public final class e {
    public final long a;

    public /* synthetic */ e(long l3) {
        this.a = l3;
    }

    public static final /* synthetic */ e a(long l3) {
        return new e(l3);
    }

    public static long b(float f3, float f4) {
        long l3 = Float.floatToRawIntBits(f3);
        return e.c((long)Float.floatToRawIntBits(f4) & 0xFFFFFFFFL | l3 << 32);
    }

    public static long c(long l3) {
        return l3;
    }

    public static boolean d(long l3, Object object) {
        if (!(object instanceof e)) {
            return false;
        }
        return l3 == ((e)object).g();
    }

    public static int e(long l3) {
        return Long.hashCode(l3);
    }

    public static String f(long l3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        stringBuilder.append(Float.intBitsToFloat((int)(l3 >> 32)));
        stringBuilder.append(", ");
        stringBuilder.append(Float.intBitsToFloat((int)(l3 & 0xFFFFFFFFL)));
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public boolean equals(Object object) {
        return e.d(this.a, object);
    }

    public final /* synthetic */ long g() {
        return this.a;
    }

    public int hashCode() {
        return e.e(this.a);
    }

    public String toString() {
        return e.f(this.a);
    }
}

