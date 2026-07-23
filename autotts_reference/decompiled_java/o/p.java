/*
 * Decompiled with CFR 0.152.
 */
package o;

import o.q;
import o3.g;
import o3.k;
import p.a;

public abstract class p {
    public long[] a = q.a;
    public Object[] b;
    public Object[] c;
    public int d;
    public int e;

    public p() {
        Object[] objectArray = p.a.c;
        this.b = objectArray;
        this.c = objectArray;
    }

    public /* synthetic */ p(g g3) {
        this();
    }

    public final boolean a(Object object) {
        int n3 = object != null ? object.hashCode() : 0;
        int n4 = (n3 *= -862048943) ^ n3 << 16;
        int n5 = this.d;
        n3 = n4 >>> 7 & n5;
        int n6 = 0;
        while (true) {
            block5: {
                block4: {
                    long[] lArray = this.a;
                    int n7 = n3 >> 3;
                    int n8 = (n3 & 7) << 3;
                    long l3 = lArray[n7];
                    long l4 = lArray[n7 + 1] << 64 - n8 & -((long)n8) >> 63 | l3 >>> n8;
                    l3 = (long)(n4 & 0x7F) * 0x101010101010101L ^ l4;
                    for (l3 = (l3 ^ 0xFFFFFFFFFFFFFFFFL) & l3 - 0x101010101010101L & 0x8080808080808080L; l3 != 0L; l3 &= l3 - 1L) {
                        n8 = (Long.numberOfTrailingZeros(l3) >> 3) + n3 & n5;
                        if (!k.a(this.b[n8], object)) continue;
                        n3 = n8;
                        break block4;
                    }
                    if ((l4 & (l4 ^ 0xFFFFFFFFFFFFFFFFL) << 6 & 0x8080808080808080L) == 0L) break block5;
                    n3 = -1;
                }
                return n3 >= 0;
            }
            n3 = n3 + (n6 += 8) & n5;
        }
    }

    public final Object b(Object object) {
        int n3 = 0;
        int n4 = object != null ? object.hashCode() : 0;
        int n5 = (n4 *= -862048943) ^ n4 << 16;
        int n6 = this.d;
        int n7 = n5 >>> 7;
        n4 = n3;
        while (true) {
            block7: {
                block6: {
                    n3 = n7 & n6;
                    long[] lArray = this.a;
                    n7 = n3 >> 3;
                    int n8 = (n3 & 7) << 3;
                    long l3 = lArray[n7];
                    long l4 = lArray[n7 + 1] << 64 - n8 & -((long)n8) >> 63 | l3 >>> n8;
                    l3 = (long)(n5 & 0x7F) * 0x101010101010101L ^ l4;
                    for (l3 = (l3 ^ 0xFFFFFFFFFFFFFFFFL) & l3 - 0x101010101010101L & 0x8080808080808080L; l3 != 0L; l3 &= l3 - 1L) {
                        n7 = (Long.numberOfTrailingZeros(l3) >> 3) + n3 & n6;
                        if (!k.a(this.b[n7], object)) continue;
                        n4 = n7;
                        break block6;
                    }
                    if ((l4 & (l4 ^ 0xFFFFFFFFFFFFFFFFL) << 6 & 0x8080808080808080L) == 0L) break block7;
                    n4 = -1;
                }
                if (n4 >= 0) {
                    return this.c[n4];
                }
                return null;
            }
            n7 = n3 + (n4 += 8);
        }
    }

    public final int c() {
        return this.d;
    }

    public final int d() {
        return this.e;
    }

    public final boolean e() {
        return this.e == 0;
    }

    public boolean equals(Object objectArray) {
        if (objectArray == this) {
            return true;
        }
        if (!(objectArray instanceof p)) {
            return false;
        }
        p p3 = (p)objectArray;
        if (p3.d() != this.d()) {
            return false;
        }
        Object[] objectArray2 = this.b;
        objectArray = this.c;
        long[] lArray = this.a;
        int n3 = lArray.length - 2;
        if (n3 >= 0) {
            int n4 = 0;
            while (true) {
                long l3;
                if ((((l3 = lArray[n4]) ^ 0xFFFFFFFFFFFFFFFFL) << 7 & l3 & 0x8080808080808080L) != -9187201950435737472L) {
                    int n5 = 8 - (~(n4 - n3) >>> 31);
                    for (int i3 = 0; i3 < n5; ++i3) {
                        if ((0xFFL & l3) < 128L) {
                            int n6 = (n4 << 3) + i3;
                            Object object = objectArray2[n6];
                            Object object2 = objectArray[n6];
                            if (object2 == null ? p3.b(object) != null || !p3.a(object) : !k.a(object2, p3.b(object))) {
                                return false;
                            }
                        }
                        l3 >>= 8;
                    }
                    if (n5 != 8) break;
                }
                if (n4 == n3) break;
                ++n4;
            }
        }
        return true;
    }

    public int hashCode() {
        Object[] objectArray = this.b;
        Object[] objectArray2 = this.c;
        long[] lArray = this.a;
        int n3 = lArray.length - 2;
        if (n3 >= 0) {
            int n4;
            int n5 = 0;
            int n6 = 0;
            while (true) {
                long l3 = lArray[n5];
                n4 = n6;
                if (((l3 ^ 0xFFFFFFFFFFFFFFFFL) << 7 & l3 & 0x8080808080808080L) != -9187201950435737472L) {
                    int n7 = 8 - (~(n5 - n3) >>> 31);
                    for (n4 = 0; n4 < n7; ++n4) {
                        int n8 = n6;
                        if ((0xFFL & l3) < 128L) {
                            n8 = (n5 << 3) + n4;
                            Object object = objectArray[n8];
                            Object object2 = objectArray2[n8];
                            n8 = object != null ? object.hashCode() : 0;
                            int n9 = object2 != null ? object2.hashCode() : 0;
                            n8 = n6 + (n9 ^ n8);
                        }
                        l3 >>= 8;
                        n6 = n8;
                    }
                    if (n7 == 8) {
                        n4 = n6;
                    } else {
                        return n6;
                    }
                }
                if (n5 == n3) break;
                ++n5;
                n6 = n4;
            }
            return n4;
        }
        return 0;
    }

    public String toString() {
        Object object;
        if (this.e()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        Object[] objectArray = this.b;
        Object[] objectArray2 = this.c;
        long[] lArray = this.a;
        int n3 = lArray.length - 2;
        if (n3 >= 0) {
            int n4 = 0;
            int n5 = 0;
            while (true) {
                long l3 = lArray[n4];
                int n6 = n5;
                if (((l3 ^ 0xFFFFFFFFFFFFFFFFL) << 7 & l3 & 0x8080808080808080L) != -9187201950435737472L) {
                    int n7 = 8 - (~(n4 - n3) >>> 31);
                    for (n6 = 0; n6 < n7; ++n6) {
                        int n8 = n5;
                        if ((0xFFL & l3) < 128L) {
                            n8 = (n4 << 3) + n6;
                            Object object2 = objectArray[n8];
                            Object object3 = objectArray2[n8];
                            object = object2;
                            if (object2 == this) {
                                object = "(this)";
                            }
                            stringBuilder.append(object);
                            stringBuilder.append("=");
                            object = object3;
                            if (object3 == this) {
                                object = "(this)";
                            }
                            stringBuilder.append(object);
                            n8 = ++n5;
                            if (n5 < this.e) {
                                stringBuilder.append(',');
                                stringBuilder.append(' ');
                                n8 = n5;
                            }
                        }
                        l3 >>= 8;
                        n5 = n8;
                    }
                    if (n7 != 8) break;
                    n6 = n5;
                }
                if (n4 == n3) break;
                ++n4;
                n5 = n6;
            }
        }
        stringBuilder.append('}');
        object = stringBuilder.toString();
        k.d(object, "s.append('}').toString()");
        return object;
    }
}

