/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Path
 *  android.util.Log
 */
package g0;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

public abstract class d {
    public static void a(ArrayList arrayList, char c3, float[] fArray) {
        arrayList.add(new b(c3, fArray));
    }

    public static boolean b(b[] bArray, b[] bArray2) {
        if (bArray != null && bArray2 != null) {
            if (bArray.length != bArray2.length) {
                return false;
            }
            for (int i3 = 0; i3 < bArray.length; ++i3) {
                if (bArray[i3].a == bArray2[i3].a && bArray[i3].b.length == bArray2[i3].b.length) {
                    continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    public static float[] c(float[] fArray, int n3, int n4) {
        if (n3 <= n4) {
            int n5 = fArray.length;
            if (n3 >= 0 && n3 <= n5) {
                n5 = Math.min(n4 -= n3, n5 - n3);
                float[] fArray2 = new float[n4];
                System.arraycopy(fArray, n3, fArray2, 0, n5);
                return fArray2;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static b[] d(String string) {
        ArrayList arrayList = new ArrayList();
        int n3 = 0;
        int n4 = 1;
        while (n4 < string.length()) {
            String string2 = string.substring(n3, n4 = d.i(string, n4)).trim();
            if (!string2.isEmpty()) {
                float[] fArray = d.h(string2);
                d.a(arrayList, string2.charAt(0), fArray);
            }
            n3 = n4++;
        }
        if (n4 - n3 == 1 && n3 < string.length()) {
            d.a(arrayList, string.charAt(n3), new float[0]);
        }
        return arrayList.toArray(new b[0]);
    }

    public static Path e(String string) {
        Path path = new Path();
        Object object = d.d(string);
        try {
            b.h((b[])object, path);
            return path;
        }
        catch (RuntimeException runtimeException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error in parsing ");
            ((StringBuilder)object).append(string);
            throw new RuntimeException(((StringBuilder)object).toString(), runtimeException);
        }
    }

    public static b[] f(b[] bArray) {
        b[] bArray2 = new b[bArray.length];
        for (int i3 = 0; i3 < bArray.length; ++i3) {
            bArray2[i3] = new b(bArray[i3]);
        }
        return bArray2;
    }

    /*
     * Unable to fully structure code
     */
    public static void g(String var0, int var1_1, a var2_2) {
        var2_2.b = false;
        var3_4 = false;
        var4_6 = var6_5 = false;
        for (var5_3 = var1_1; var5_3 < var0.length(); ++var5_3) {
            block8: {
                block7: {
                    var7_7 = var0.charAt(var5_3);
                    if (var7_7 == ' ') ** GOTO lbl-1000
                    if (var7_7 == 'E' || var7_7 == 'e') break block7;
                    switch (var7_7) {
                        default: {
                            break;
                        }
                        case '.': {
                            if (var6_5) ** GOTO lbl16
                            var3_4 = false;
                            var6_5 = true;
                            break block8;
lbl16:
                            // 1 sources

                            var2_2.b = true;
                        }
lbl17:
                        // 3 sources

                        case ',': lbl-1000:
                        // 2 sources

                        {
                            var3_4 = false;
                            var4_6 = true;
                            break block8;
                        }
                        case '-': {
                            if (var5_3 == var1_1 || var3_4) break;
                            var2_2.b = true;
                            ** GOTO lbl17
                        }
                    }
                    var3_4 = false;
                    break block8;
                }
                var3_4 = true;
            }
            if (var4_6) break;
        }
        var2_2.a = var5_3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static float[] h(String string) {
        int n3;
        int n4;
        int n5;
        Object object;
        float[] fArray;
        block7: {
            if (string.charAt(0) == 'z') return new float[0];
            if (string.charAt(0) == 'Z') {
                return new float[0];
            }
            try {
                fArray = new float[string.length()];
                object = new a();
                n5 = string.length();
                n4 = 1;
                n3 = 0;
                break block7;
            }
            catch (NumberFormatException numberFormatException) {}
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error in parsing \"");
            stringBuilder.append(string);
            stringBuilder.append("\"");
            throw new RuntimeException(stringBuilder.toString(), numberFormatException);
        }
        while (n4 < n5) {
            d.g(string, n4, (a)object);
            int n6 = ((a)object).a;
            int n7 = n3;
            if (n4 < n6) {
                fArray[n3] = Float.parseFloat(string.substring(n4, n6));
                n7 = n3 + 1;
            }
            if (((a)object).b) {
                n4 = n6;
                n3 = n7;
                continue;
            }
            n4 = n6 + 1;
            n3 = n7;
        }
        return d.c(fArray, 0, n3);
    }

    public static int i(String string, int n3) {
        char c3;
        while (n3 < string.length() && (((c3 = string.charAt(n3)) - 65) * (c3 - 90) > 0 && (c3 - 97) * (c3 - 122) > 0 || c3 == 'e' || c3 == 'E')) {
            ++n3;
        }
        return n3;
    }

    public static void j(b[] bArray, Path path) {
        float[] fArray = new float[6];
        int n3 = bArray.length;
        char c3 = 'm';
        for (int i3 = 0; i3 < n3; ++i3) {
            b b3 = bArray[i3];
            b.e(path, fArray, c3, b3.a, b3.b);
            c3 = b3.a;
        }
    }

    public static void k(b[] bArray, b[] bArray2) {
        for (int i3 = 0; i3 < bArray2.length; ++i3) {
            b.b(bArray[i3], bArray2[i3].a);
            for (int i4 = 0; i4 < bArray2[i3].b.length; ++i4) {
                ((b)bArray[i3]).b[i4] = bArray2[i3].b[i4];
            }
        }
    }

    public static class a {
        public int a;
        public boolean b;
    }

    public static class b {
        public char a;
        public final float[] b;

        public b(char c3, float[] fArray) {
            this.a = c3;
            this.b = fArray;
        }

        public b(b object) {
            this.a = ((b)object).a;
            object = ((b)object).b;
            this.b = d.c((float[])object, 0, ((Object)object).length);
        }

        public static /* synthetic */ char b(b b3, char c3) {
            b3.a = c3;
            return c3;
        }

        /*
         * Handled duff style switch with additional control
         * Enabled aggressive block sorting
         */
        public static void e(Path path, float[] fArray, char n3, char c3, float[] fArray2) {
            int n4;
            int n5;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            float f11;
            int n6;
            int n7;
            block24: {
                n7 = 0;
                float f12 = fArray[0];
                n6 = 1;
                f11 = fArray[1];
                int n8 = 2;
                f10 = fArray[2];
                int n9 = 3;
                f9 = fArray[3];
                f8 = fArray[4];
                f7 = fArray[5];
                f6 = f12;
                f5 = f11;
                f4 = f10;
                f3 = f9;
                char c4 = '\u0000';
                block8: do {
                    switch (c4 == '\u0000' ? c3 : c4) {
                        default: {
                            f3 = f9;
                            f4 = f10;
                            f5 = f11;
                            f6 = f12;
                            c4 = 'L';
                            continue block8;
                        }
                        case 'Z': 
                        case 'z': {
                            path.close();
                            path.moveTo(f8, f7);
                            f4 = f6 = f8;
                            f3 = f5 = f7;
                        }
                        case 'L': 
                        case 'M': 
                        case 'T': 
                        case 'l': 
                        case 'm': 
                        case 't': {
                            n5 = 2;
                            break block24;
                        }
                        case 'Q': 
                        case 'S': 
                        case 'q': 
                        case 's': {
                            n5 = 4;
                            f6 = f12;
                            f5 = f11;
                            f4 = f10;
                            f3 = f9;
                            break block24;
                        }
                        case 'H': 
                        case 'V': 
                        case 'h': 
                        case 'v': {
                            n5 = 1;
                            f6 = f12;
                            f5 = f11;
                            f4 = f10;
                            f3 = f9;
                            break block24;
                        }
                        case 'C': 
                        case 'c': {
                            n4 = 6;
                            break;
                        }
                        case 'A': 
                        case 'a': {
                            n4 = 7;
                        }
                    }
                    break;
                } while (true);
                f6 = f12;
                f5 = f11;
                f4 = f10;
                f3 = f9;
                n5 = n4;
            }
            f9 = f6;
            int n10 = 0;
            int n11 = n3;
            n3 = n6;
            n4 = n7;
            n7 = n10;
            f6 = f5;
            f5 = f9;
            while (true) {
                block42: {
                    int n12;
                    block25: {
                        block26: {
                            block27: {
                                block51: {
                                    block28: {
                                        block29: {
                                            block30: {
                                                block31: {
                                                    block32: {
                                                        block43: {
                                                            block33: {
                                                                block46: {
                                                                    block34: {
                                                                        block53: {
                                                                            block35: {
                                                                                block45: {
                                                                                    block52: {
                                                                                        block36: {
                                                                                            block50: {
                                                                                                block49: {
                                                                                                    block37: {
                                                                                                        block48: {
                                                                                                            block47: {
                                                                                                                block38: {
                                                                                                                    block39: {
                                                                                                                        block44: {
                                                                                                                            block40: {
                                                                                                                                block41: {
                                                                                                                                    if (n7 >= fArray2.length) {
                                                                                                                                        fArray[n4] = f5;
                                                                                                                                        fArray[n3] = f6;
                                                                                                                                        fArray[n8] = f4;
                                                                                                                                        fArray[n9] = f3;
                                                                                                                                        fArray[4] = f8;
                                                                                                                                        fArray[5] = f7;
                                                                                                                                        return;
                                                                                                                                    }
                                                                                                                                    if (c3 == 'A') break block25;
                                                                                                                                    if (c3 == 'C') break block26;
                                                                                                                                    if (c3 == 'H') break block27;
                                                                                                                                    if (c3 == 'Q') break block28;
                                                                                                                                    if (c3 == 'V') break block29;
                                                                                                                                    if (c3 == 'a') break block30;
                                                                                                                                    if (c3 == 'c') break block31;
                                                                                                                                    if (c3 == 'h') break block32;
                                                                                                                                    if (c3 == 'q') break block33;
                                                                                                                                    if (c3 == 'v') break block34;
                                                                                                                                    if (c3 == 'L') break block35;
                                                                                                                                    if (c3 == 'M') break block36;
                                                                                                                                    if (c3 == 'S') break block37;
                                                                                                                                    if (c3 == 'T') break block38;
                                                                                                                                    if (c3 == 'l') break block39;
                                                                                                                                    if (c3 == 'm') break block40;
                                                                                                                                    if (c3 == 's') break block41;
                                                                                                                                    if (c3 == 't') {
                                                                                                                                        if (n11 != 113 && n11 != 116 && n11 != 81 && n11 != 84) {
                                                                                                                                            f3 = 0.0f;
                                                                                                                                            f4 = 0.0f;
                                                                                                                                        } else {
                                                                                                                                            f4 = f5 - f4;
                                                                                                                                            f3 = f6 - f3;
                                                                                                                                        }
                                                                                                                                        f9 = fArray2[n7];
                                                                                                                                        n11 = n7 + 1;
                                                                                                                                        path.rQuadTo(f4, f3, f9, fArray2[n11]);
                                                                                                                                        f10 = f5 + fArray2[n7];
                                                                                                                                        f9 = f6 + fArray2[n11];
                                                                                                                                        f3 += f6;
                                                                                                                                        f4 += f5;
                                                                                                                                        f5 = f10;
                                                                                                                                        f6 = f9;
                                                                                                                                    }
                                                                                                                                    break block42;
                                                                                                                                }
                                                                                                                                if (n11 != 99 && n11 != 115 && n11 != 67 && n11 != 83) {
                                                                                                                                    f4 = 0.0f;
                                                                                                                                    f3 = 0.0f;
                                                                                                                                } else {
                                                                                                                                    f3 = f6 - f3;
                                                                                                                                    f4 = f5 - f4;
                                                                                                                                }
                                                                                                                                f10 = fArray2[n7];
                                                                                                                                n10 = n7 + 1;
                                                                                                                                f9 = fArray2[n10];
                                                                                                                                n6 = n7 + 2;
                                                                                                                                f11 = fArray2[n6];
                                                                                                                                n11 = n7 + 3;
                                                                                                                                path.rCubicTo(f4, f3, f10, f9, f11, fArray2[n11]);
                                                                                                                                f3 = fArray2[n7] + f5;
                                                                                                                                f4 = fArray2[n10] + f6;
                                                                                                                                f5 += fArray2[n6];
                                                                                                                                f9 = fArray2[n11];
                                                                                                                                break block43;
                                                                                                                            }
                                                                                                                            f9 = fArray2[n7];
                                                                                                                            f5 += f9;
                                                                                                                            f10 = fArray2[n7 + 1];
                                                                                                                            f6 += f10;
                                                                                                                            if (n7 <= 0) break block44;
                                                                                                                            path.rLineTo(f9, f10);
                                                                                                                            break block42;
                                                                                                                        }
                                                                                                                        path.rMoveTo(f9, f10);
                                                                                                                        f8 = f5;
                                                                                                                        break block45;
                                                                                                                    }
                                                                                                                    f9 = fArray2[n7];
                                                                                                                    n11 = n7 + 1;
                                                                                                                    path.rLineTo(f9, fArray2[n11]);
                                                                                                                    f5 += fArray2[n7];
                                                                                                                    f9 = fArray2[n11];
                                                                                                                    break block46;
                                                                                                                }
                                                                                                                if (n11 == 113 || n11 == 116 || n11 == 81) break block47;
                                                                                                                f10 = f5;
                                                                                                                f9 = f6;
                                                                                                                if (n11 != 84) break block48;
                                                                                                            }
                                                                                                            f10 = f5 * 2.0f - f4;
                                                                                                            f9 = f6 * 2.0f - f3;
                                                                                                        }
                                                                                                        f5 = fArray2[n7];
                                                                                                        n11 = n7 + 1;
                                                                                                        path.quadTo(f10, f9, f5, fArray2[n11]);
                                                                                                        f5 = fArray2[n7];
                                                                                                        f6 = fArray2[n11];
                                                                                                        f4 = f10;
                                                                                                        f3 = f9;
                                                                                                        break block42;
                                                                                                    }
                                                                                                    if (n11 == 99 || n11 == 115 || n11 == 67) break block49;
                                                                                                    f9 = f5;
                                                                                                    f10 = f6;
                                                                                                    if (n11 != 83) break block50;
                                                                                                }
                                                                                                f9 = f5 * 2.0f - f4;
                                                                                                f10 = f6 * 2.0f - f3;
                                                                                            }
                                                                                            f5 = fArray2[n7];
                                                                                            n6 = n7 + 1;
                                                                                            f6 = fArray2[n6];
                                                                                            n11 = n7 + 2;
                                                                                            f4 = fArray2[n11];
                                                                                            n10 = n7 + 3;
                                                                                            path.cubicTo(f9, f10, f5, f6, f4, fArray2[n10]);
                                                                                            f3 = fArray2[n7];
                                                                                            f4 = fArray2[n6];
                                                                                            f5 = fArray2[n11];
                                                                                            f6 = fArray2[n10];
                                                                                            break block51;
                                                                                        }
                                                                                        f9 = fArray2[n7];
                                                                                        f6 = fArray2[n7 + 1];
                                                                                        if (n7 <= 0) break block52;
                                                                                        path.lineTo(f9, f6);
                                                                                        f5 = f6;
                                                                                        f6 = f9;
                                                                                        break block53;
                                                                                    }
                                                                                    path.moveTo(f9, f6);
                                                                                    f8 = f5 = f9;
                                                                                }
                                                                                f7 = f6;
                                                                                break block42;
                                                                            }
                                                                            f5 = fArray2[n7];
                                                                            n11 = n7 + 1;
                                                                            path.lineTo(f5, fArray2[n11]);
                                                                            f6 = fArray2[n7];
                                                                            f5 = fArray2[n11];
                                                                        }
                                                                        f9 = f6;
                                                                        f6 = f5;
                                                                        f5 = f9;
                                                                        break block42;
                                                                    }
                                                                    path.rLineTo(0.0f, fArray2[n7]);
                                                                    f9 = fArray2[n7];
                                                                }
                                                                f6 += f9;
                                                                break block42;
                                                            }
                                                            f4 = fArray2[n7];
                                                            n6 = n7 + 1;
                                                            f3 = fArray2[n6];
                                                            n10 = n7 + 2;
                                                            f9 = fArray2[n10];
                                                            n11 = n7 + 3;
                                                            path.rQuadTo(f4, f3, f9, fArray2[n11]);
                                                            f3 = fArray2[n7] + f5;
                                                            f4 = fArray2[n6] + f6;
                                                            f5 += fArray2[n10];
                                                            f9 = fArray2[n11];
                                                        }
                                                        f9 = f6 + f9;
                                                        f6 = f3;
                                                        f3 = f4;
                                                        f4 = f6;
                                                        f6 = f9;
                                                        break block42;
                                                    }
                                                    path.rLineTo(fArray2[n7], 0.0f);
                                                    f5 += fArray2[n7];
                                                    break block42;
                                                }
                                                f4 = fArray2[n7];
                                                f11 = fArray2[n7 + 1];
                                                n11 = n7 + 2;
                                                f9 = fArray2[n11];
                                                n10 = n7 + 3;
                                                f3 = fArray2[n10];
                                                n6 = n7 + 4;
                                                f10 = fArray2[n6];
                                                n12 = n7 + 5;
                                                path.rCubicTo(f4, f11, f9, f3, f10, fArray2[n12]);
                                                f4 = fArray2[n11];
                                                f3 = fArray2[n10];
                                                f10 = f5 + fArray2[n6];
                                                f9 = f6 + fArray2[n12];
                                                f4 += f5;
                                                f3 += f6;
                                                f5 = f10;
                                                f6 = f9;
                                                break block42;
                                            }
                                            n10 = n7 + 5;
                                            f10 = fArray2[n10];
                                            n12 = n7 + 6;
                                            f3 = fArray2[n12];
                                            f9 = fArray2[n7];
                                            f11 = fArray2[n7 + 1];
                                            f4 = fArray2[n7 + 2];
                                            n11 = fArray2[n7 + 3] != 0.0f ? n3 : n4;
                                            n6 = fArray2[n7 + 4] != 0.0f ? n3 : n4;
                                            g0.d$b.g(path, f5, f6, f10 + f5, f3 + f6, f9, f11, f4, n11 != 0, n6 != 0);
                                            f4 = f5 += fArray2[n10];
                                            f3 = f6 += fArray2[n12];
                                            break block42;
                                        }
                                        path.lineTo(f5, fArray2[n7]);
                                        f6 = fArray2[n7];
                                        break block42;
                                    }
                                    f4 = fArray2[n7];
                                    n11 = n7 + 1;
                                    f6 = fArray2[n11];
                                    n10 = n7 + 2;
                                    f5 = fArray2[n10];
                                    n6 = n7 + 3;
                                    path.quadTo(f4, f6, f5, fArray2[n6]);
                                    f3 = fArray2[n7];
                                    f4 = fArray2[n11];
                                    f5 = fArray2[n10];
                                    f6 = fArray2[n6];
                                }
                                f9 = f3;
                                f3 = f4;
                                f4 = f9;
                                break block42;
                            }
                            path.lineTo(fArray2[n7], f6);
                            f5 = fArray2[n7];
                            break block42;
                        }
                        f6 = fArray2[n7];
                        f4 = fArray2[n7 + 1];
                        n12 = n7 + 2;
                        f9 = fArray2[n12];
                        n10 = n7 + 3;
                        f5 = fArray2[n10];
                        n6 = n7 + 4;
                        f3 = fArray2[n6];
                        n11 = n7 + 5;
                        path.cubicTo(f6, f4, f9, f5, f3, fArray2[n11]);
                        f5 = fArray2[n6];
                        f6 = fArray2[n11];
                        f4 = fArray2[n12];
                        f3 = fArray2[n10];
                        break block42;
                    }
                    n10 = n7 + 5;
                    f9 = fArray2[n10];
                    n12 = n7 + 6;
                    f4 = fArray2[n12];
                    f10 = fArray2[n7];
                    f3 = fArray2[n7 + 1];
                    f11 = fArray2[n7 + 2];
                    n11 = fArray2[n7 + 3] != 0.0f ? n3 : n4;
                    n6 = fArray2[n7 + 4] != 0.0f ? n3 : n4;
                    g0.d$b.g(path, f5, f6, f9, f4, f10, f3, f11, n11 != 0, n6 != 0);
                    f4 = fArray2[n10];
                    f3 = fArray2[n12];
                    f5 = f4;
                    f6 = f3;
                }
                n7 += n5;
                n11 = c3;
            }
        }

        public static void f(Path path, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11) {
            int n3 = (int)Math.ceil(Math.abs(d11 * 4.0 / Math.PI));
            double d12 = Math.cos(d9);
            double d13 = Math.sin(d9);
            double d14 = Math.cos(d10);
            double d15 = Math.sin(d10);
            d9 = -d5;
            double d16 = d9 * d12;
            double d17 = d6 * d13;
            double d18 = d9 * d13;
            double d19 = d6 * d12;
            d11 /= (double)n3;
            d9 = d15 * d18 + d14 * d19;
            d14 = d16 * d15 - d17 * d14;
            d15 = d10;
            d10 = d8;
            d6 = d18;
            d18 = d7;
            d7 = d11;
            d11 = d13;
            d8 = d12;
            for (int i3 = 0; i3 < n3; ++i3) {
                double d20 = d15 + d7;
                double d21 = Math.sin(d20);
                double d22 = Math.cos(d20);
                double d23 = d3 + d5 * d8 * d22 - d17 * d21;
                d13 = d4 + d5 * d11 * d22 + d19 * d21;
                d12 = d16 * d21 - d17 * d22;
                d21 = d21 * d6 + d22 * d19;
                d22 = d20 - d15;
                d15 = Math.tan(d22 / 2.0);
                d15 = Math.sin(d22) * (Math.sqrt(d15 * 3.0 * d15 + 4.0) - 1.0) / 3.0;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float)(d18 + d14 * d15), (float)(d10 + d9 * d15), (float)(d23 - d15 * d12), (float)(d13 - d15 * d21), (float)d23, (float)d13);
                d18 = d23;
                d15 = d20;
                d9 = d21;
                d10 = d13;
                d14 = d12;
            }
        }

        public static void g(Path path, float f3, float f4, float f5, float f6, float f7, float f8, float f9, boolean bl, boolean bl2) {
            double d3 = Math.toRadians(f9);
            double d4 = Math.cos(d3);
            double d5 = Math.sin(d3);
            double d6 = f3;
            double d7 = f4;
            double d8 = f7;
            double d9 = (d6 * d4 + d7 * d5) / d8;
            double d10 = -f3;
            double d11 = f8;
            double d12 = (d10 * d5 + d7 * d4) / d11;
            double d13 = f5;
            d10 = f6;
            double d14 = (d13 * d4 + d10 * d5) / d8;
            double d15 = ((double)(-f5) * d5 + d10 * d4) / d11;
            double d16 = d9 - d14;
            double d17 = d12 - d15;
            d13 = (d9 + d14) / 2.0;
            d10 = (d12 + d15) / 2.0;
            double d18 = d16 * d16 + d17 * d17;
            if (d18 == 0.0) {
                Log.w((String)"PathParser", (String)" Points are coincident");
                return;
            }
            double d19 = 1.0 / d18 - 0.25;
            if (d19 < 0.0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Points are too far apart ");
                stringBuilder.append(d18);
                Log.w((String)"PathParser", (String)stringBuilder.toString());
                float f10 = (float)(Math.sqrt(d18) / 1.99999);
                g0.d$b.g(path, f3, f4, f5, f6, f7 * f10, f10 * f8, f9, bl, bl2);
                return;
            }
            d18 = Math.sqrt(d19);
            d16 *= d18;
            d17 = d18 * d17;
            if (bl == bl2) {
                d13 -= d17;
                d10 += d16;
            } else {
                d13 += d17;
                d10 -= d16;
            }
            d16 = Math.atan2(d12 - d10, d9 - d13);
            d12 = Math.atan2(d15 - d10, d14 - d13) - d16;
            double d20 = d12 - 0.0;
            double d21 = d20 == 0.0 ? 0 : (d20 > 0.0 ? 1 : -1);
            bl = d21 >= 0;
            d9 = d12;
            if (bl2 != bl) {
                d9 = d21 > 0 ? d12 - Math.PI * 2 : d12 + Math.PI * 2;
            }
            g0.d$b.f(path, (d13 *= d8) * d4 - (d10 *= d11) * d5, d13 * d5 + d10 * d4, d8, d11, d6, d7, d3, d16, d9);
        }

        public static void h(b[] bArray, Path path) {
            d.j(bArray, path);
        }
    }
}

