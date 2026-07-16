/*
 * Decompiled with CFR 0.152.
 */
package o;

import o.h;
import o.q;
import o3.k;

public abstract class g {
    public long[] a = q.a;
    public float[] b = h.a();
    public int c;
    public int d;

    public g() {
    }

    public /* synthetic */ g(o3.g g3) {
        this();
    }

    public static /* synthetic */ String d(g g3, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int n3, CharSequence charSequence4, int n4, Object object) {
        if (object == null) {
            if ((n4 & 1) != 0) {
                charSequence = ", ";
            }
            if ((n4 & 2) != 0) {
                charSequence2 = "";
            }
            if ((n4 & 4) != 0) {
                charSequence3 = "";
            }
            if ((n4 & 8) != 0) {
                n3 = -1;
            }
            if ((n4 & 0x10) != 0) {
                charSequence4 = "...";
            }
            return g3.c(charSequence, charSequence2, charSequence3, n3, charSequence4);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: joinToString");
    }

    public final boolean a(float f3) {
        int n3 = Float.hashCode(f3) * -862048943;
        int n4 = n3 ^ n3 << 16;
        int n5 = this.c;
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
                        n7 = (Long.numberOfTrailingZeros(l3) >> 3) + n3 & n5;
                        if (this.b[n7] != f3) continue;
                        n3 = n7;
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

    public final int b() {
        return this.c;
    }

    public final String c(CharSequence charSequence, CharSequence object, CharSequence charSequence2, int n3, CharSequence charSequence3) {
        StringBuilder stringBuilder;
        block7: {
            k.e(charSequence, "separator");
            k.e(object, "prefix");
            k.e(charSequence2, "postfix");
            k.e(charSequence3, "truncated");
            stringBuilder = new StringBuilder();
            stringBuilder.append((CharSequence)object);
            object = this.b;
            long[] lArray = this.a;
            int n4 = lArray.length - 2;
            if (n4 >= 0) {
                int n5 = 0;
                int n6 = 0;
                while (true) {
                    long l3;
                    if ((((l3 = lArray[n5]) ^ 0xFFFFFFFFFFFFFFFFL) << 7 & l3 & 0x8080808080808080L) != -9187201950435737472L) {
                        int n7 = 8;
                        int n8 = 8 - (~(n5 - n4) >>> 31);
                        for (int i3 = 0; i3 < n8; ++i3) {
                            if ((l3 & 0xFFL) < 128L) {
                                Object object2 = object[(n5 << 3) + i3];
                                if (n6 == n3) {
                                    stringBuilder.append(charSequence3);
                                    break block7;
                                }
                                if (n6 != 0) {
                                    stringBuilder.append(charSequence);
                                }
                                stringBuilder.append((float)object2);
                                ++n6;
                            }
                            l3 >>= n7;
                        }
                        if (n8 != n7) break;
                    }
                    if (n5 == n4) break;
                    ++n5;
                }
            }
            stringBuilder.append(charSequence2);
        }
        charSequence = stringBuilder.toString();
        k.d(charSequence, "StringBuilder().apply(builderAction).toString()");
        return charSequence;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof g)) {
            return false;
        }
        object = (g)object;
        if (((g)object).d != this.d) {
            return false;
        }
        float[] fArray = this.b;
        long[] lArray = this.a;
        int n3 = lArray.length - 2;
        if (n3 >= 0) {
            int n4 = 0;
            while (true) {
                long l3;
                if ((((l3 = lArray[n4]) ^ 0xFFFFFFFFFFFFFFFFL) << 7 & l3 & 0x8080808080808080L) != -9187201950435737472L) {
                    int n5 = 8 - (~(n4 - n3) >>> 31);
                    for (int i3 = 0; i3 < n5; ++i3) {
                        if ((0xFFL & l3) < 128L && !((g)object).a(fArray[(n4 << 3) + i3])) {
                            return false;
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
        float[] fArray = this.b;
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
                            n8 = n6 + Float.hashCode(fArray[(n5 << 3) + n4]);
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
        return g.d(this, null, "[", "]", 0, null, 25, null);
    }
}

