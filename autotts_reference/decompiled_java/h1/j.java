/*
 * Decompiled with CFR 0.152.
 */
package h1;

import h1.b;
import h1.c;
import h1.d;
import h1.k;
import h1.l;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public abstract class j {
    public static final byte[] a = new byte[]{112, 114, 111, 0};
    public static final byte[] b = new byte[]{112, 114, 109, 0};

    public static void A(byte[] byArray, int n3, int n4, b b3) {
        n3 = j.m(n3, n4, b3.g);
        n4 = n3 / 8;
        byArray[n4] = (byte)(1 << n3 % 8 | byArray[n4]);
    }

    public static void B(InputStream inputStream) {
        c.h(inputStream);
        int n3 = c.j(inputStream);
        if (n3 != 6) {
            if (n3 != 7) {
                for (int i3 = n3; i3 > 0; --i3) {
                    c.j(inputStream);
                    for (n3 = c.j(inputStream); n3 > 0; --n3) {
                        c.h(inputStream);
                    }
                }
            }
        }
    }

    public static boolean C(OutputStream outputStream, byte[] byArray, b[] bArray) {
        if (Arrays.equals(byArray, k.a)) {
            j.P(outputStream, bArray);
            return true;
        }
        if (Arrays.equals(byArray, k.b)) {
            j.O(outputStream, bArray);
            return true;
        }
        if (Arrays.equals(byArray, k.d)) {
            j.M(outputStream, bArray);
            return true;
        }
        if (Arrays.equals(byArray, k.c)) {
            j.N(outputStream, bArray);
            return true;
        }
        if (Arrays.equals(byArray, k.e)) {
            j.L(outputStream, bArray);
            return true;
        }
        return false;
    }

    public static void D(OutputStream outputStream, b object) {
        object = ((b)object).h;
        int n3 = ((Object)object).length;
        Object object2 = false;
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object3 = object[i3];
            c.p(outputStream, (int)(object3 - object2));
            object2 = object3;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static l E(b[] object) {
        Throwable throwable2;
        Object object2;
        int n3;
        ByteArrayOutputStream byteArrayOutputStream;
        block7: {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                c.p(byteArrayOutputStream, ((Object)object).length);
                n3 = 2;
                for (int i3 = 0; i3 < ((Object)object).length; ++i3) {
                    object2 = object[i3];
                    c.q(byteArrayOutputStream, ((b)object2).c);
                    c.q(byteArrayOutputStream, ((b)object2).d);
                    c.q(byteArrayOutputStream, ((b)object2).g);
                    object2 = j.j(((b)object2).a, ((b)object2).b, k.a);
                    int n4 = c.k((String)object2);
                    c.p(byteArrayOutputStream, n4);
                    n3 = n3 + 14 + n4;
                    c.n(byteArrayOutputStream, (String)object2);
                }
                object2 = byteArrayOutputStream.toByteArray();
                if (n3 != ((Object)object2).length) break block7;
                object = new l(d.d, n3, (byte[])object2, false);
            }
            catch (Throwable throwable2) {}
            byteArrayOutputStream.close();
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Expected size ");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(", does not match actual size ");
        ((StringBuilder)object).append(((Object)object2).length);
        throw c.c(((StringBuilder)object).toString());
        try {
            byteArrayOutputStream.close();
            throw throwable2;
        }
        catch (Throwable throwable3) {
            throwable2.addSuppressed(throwable3);
        }
        throw throwable2;
    }

    public static void F(OutputStream outputStream, byte[] byArray) {
        outputStream.write(a);
        outputStream.write(byArray);
    }

    public static void G(OutputStream outputStream, b b3) {
        j.K(outputStream, b3);
        j.D(outputStream, b3);
        j.I(outputStream, b3);
    }

    public static void H(OutputStream outputStream, b b3, String string) {
        c.p(outputStream, c.k(string));
        c.p(outputStream, b3.e);
        c.q(outputStream, b3.f);
        c.q(outputStream, b3.c);
        c.q(outputStream, b3.g);
        c.n(outputStream, string);
    }

    public static void I(OutputStream outputStream, b b3) {
        byte[] byArray = new byte[j.k(b3.g)];
        for (Map.Entry entry : b3.i.entrySet()) {
            int n3 = (Integer)entry.getKey();
            int n4 = (Integer)entry.getValue();
            if ((n4 & 2) != 0) {
                j.A(byArray, 2, n3, b3);
            }
            if ((n4 & 4) == 0) continue;
            j.A(byArray, 4, n3, b3);
        }
        outputStream.write(byArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void J(OutputStream outputStream, int n3, b b3) {
        byte[] byArray = new byte[j.l(n3, b3.g)];
        Iterator iterator = b3.i.entrySet().iterator();
        block0: while (true) {
            if (!iterator.hasNext()) {
                outputStream.write(byArray);
                return;
            }
            Map.Entry entry = iterator.next();
            int n4 = (Integer)entry.getKey();
            int n5 = (Integer)entry.getValue();
            int n6 = 0;
            int n7 = 1;
            while (true) {
                if (n7 > 4) continue block0;
                if (n7 != 1 && (n7 & n3) != 0) {
                    if ((n7 & n5) == n7) {
                        int n8 = b3.g * n6 + n4;
                        int n9 = n8 / 8;
                        byArray[n9] = (byte)(1 << n8 % 8 | byArray[n9]);
                    }
                    ++n6;
                }
                n7 <<= 1;
            }
            break;
        }
    }

    public static void K(OutputStream outputStream, b object) {
        Iterator iterator = ((b)object).i.entrySet().iterator();
        int n3 = 0;
        while (iterator.hasNext()) {
            object = iterator.next();
            int n4 = (Integer)object.getKey();
            if (((Integer)object.getValue() & 1) == 0) continue;
            c.p(outputStream, n4 - n3);
            c.p(outputStream, 0);
            n3 = n4;
        }
    }

    public static void L(OutputStream outputStream, b[] bArray) {
        c.p(outputStream, bArray.length);
        for (b b3 : bArray) {
            Object object = j.j(b3.a, b3.b, k.e);
            c.p(outputStream, c.k((String)object));
            c.p(outputStream, b3.i.size());
            c.p(outputStream, b3.h.length);
            c.q(outputStream, b3.c);
            c.n(outputStream, (String)object);
            object = b3.i.keySet().iterator();
            while (object.hasNext()) {
                c.p(outputStream, (Integer)object.next());
            }
            int[] object2 = b3.h;
            int n3 = object2.length;
            for (int i3 = 0; i3 < n3; ++i3) {
                c.p(outputStream, object2[i3]);
            }
        }
    }

    public static void M(OutputStream outputStream, b[] bArray) {
        c.r(outputStream, bArray.length);
        for (b b3 : bArray) {
            int n3 = b3.i.size();
            Object object = j.j(b3.a, b3.b, k.d);
            c.p(outputStream, c.k((String)object));
            c.p(outputStream, b3.h.length);
            c.q(outputStream, n3 * 4);
            c.q(outputStream, b3.c);
            c.n(outputStream, (String)object);
            object = b3.i.keySet().iterator();
            while (object.hasNext()) {
                c.p(outputStream, (Integer)object.next());
                c.p(outputStream, 0);
            }
            int[] object2 = b3.h;
            int n4 = object2.length;
            for (n3 = 0; n3 < n4; ++n3) {
                c.p(outputStream, object2[n3]);
            }
        }
    }

    public static void N(OutputStream outputStream, b[] bArray) {
        byte[] byArray = j.b(bArray, k.c);
        c.r(outputStream, bArray.length);
        c.m(outputStream, byArray);
    }

    public static void O(OutputStream outputStream, b[] bArray) {
        byte[] byArray = j.b(bArray, k.b);
        c.r(outputStream, bArray.length);
        c.m(outputStream, byArray);
    }

    public static void P(OutputStream outputStream, b[] bArray) {
        j.Q(outputStream, bArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void Q(OutputStream outputStream, b[] bArray) {
        int n3;
        ArrayList<l> arrayList = new ArrayList<l>(3);
        ArrayList<byte[]> arrayList2 = new ArrayList<byte[]>(3);
        arrayList.add(j.E(bArray));
        arrayList.add(j.c(bArray));
        arrayList.add(j.d(bArray));
        long l3 = (long)k.a.length + (long)a.length + 4L + (long)(arrayList.size() * 16);
        c.q(outputStream, arrayList.size());
        int n4 = 0;
        int n5 = 0;
        while (true) {
            n3 = n4;
            if (n5 >= arrayList.size()) break;
            l l4 = (l)arrayList.get(n5);
            c.q(outputStream, l4.a.b());
            c.q(outputStream, l3);
            if (l4.d) {
                byte[] byArray = l4.c;
                long l5 = byArray.length;
                byte[] byArray2 = c.b(byArray);
                arrayList2.add(byArray2);
                c.q(outputStream, byArray2.length);
                c.q(outputStream, l5);
                n3 = byArray2.length;
            } else {
                arrayList2.add(l4.c);
                c.q(outputStream, l4.c.length);
                c.q(outputStream, 0L);
                n3 = l4.c.length;
            }
            l3 += (long)n3;
            ++n5;
        }
        while (n3 < arrayList2.size()) {
            outputStream.write((byte[])arrayList2.get(n3));
            ++n3;
        }
        return;
    }

    public static int a(b object) {
        object = ((b)object).i.entrySet().iterator();
        int n3 = 0;
        while (object.hasNext()) {
            n3 |= ((Integer)((Map.Entry)object.next()).getValue()).intValue();
        }
        return n3;
    }

    public static byte[] b(b[] object, byte[] byArray) {
        Object object2;
        int n3;
        int n4 = ((b[])object).length;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            object2 = object[n3];
            n7 += c.k(j.j(((b)object2).a, ((b)object2).b, byArray)) + 16 + ((b)object2).e * 2 + ((b)object2).f + j.k(((b)object2).g);
        }
        object2 = new ByteArrayOutputStream(n7);
        if (Arrays.equals(byArray, k.c)) {
            n5 = ((b[])object).length;
            for (n3 = n6; n3 < n5; ++n3) {
                b b3 = object[n3];
                j.H((OutputStream)object2, b3, j.j(b3.a, b3.b, byArray));
                j.G((OutputStream)object2, b3);
            }
        } else {
            for (b b4 : object) {
                j.H((OutputStream)object2, b4, j.j(b4.a, b4.b, byArray));
            }
            n6 = ((b[])object).length;
            for (n3 = n5; n3 < n6; ++n3) {
                j.G((OutputStream)object2, (b)object[n3]);
            }
        }
        if (((ByteArrayOutputStream)object2).size() == n7) {
            return ((ByteArrayOutputStream)object2).toByteArray();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("The bytes saved do not match expectation. actual=");
        ((StringBuilder)object).append(((ByteArrayOutputStream)object2).size());
        ((StringBuilder)object).append(" expected=");
        ((StringBuilder)object).append(n7);
        throw c.c(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static l c(b[] object) {
        Throwable throwable2;
        Object object2;
        int n3;
        ByteArrayOutputStream byteArrayOutputStream;
        block8: {
            byteArrayOutputStream = new ByteArrayOutputStream();
            int n4 = 0;
            n3 = 0;
            while (true) {
                if (n4 >= ((b[])object).length) break;
                object2 = object[n4];
                c.p(byteArrayOutputStream, n4);
                c.p(byteArrayOutputStream, ((b)object2).e);
                n3 = n3 + 4 + ((b)object2).e * 2;
                j.D(byteArrayOutputStream, (b)object2);
                ++n4;
                continue;
                break;
            }
            try {
                object = byteArrayOutputStream.toByteArray();
                if (n3 != ((b[])object).length) break block8;
                object = new l(d.f, n3, (byte[])object, true);
            }
            catch (Throwable throwable2) {}
            byteArrayOutputStream.close();
            return object;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Expected size ");
        ((StringBuilder)object2).append(n3);
        ((StringBuilder)object2).append(", does not match actual size ");
        ((StringBuilder)object2).append(((b[])object).length);
        throw c.c(((StringBuilder)object2).toString());
        try {
            byteArrayOutputStream.close();
            throw throwable2;
        }
        catch (Throwable throwable3) {
            throwable2.addSuppressed(throwable3);
        }
        throw throwable2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static l d(b[] object) {
        Throwable throwable2;
        Object object2;
        int n3;
        ByteArrayOutputStream byteArrayOutputStream;
        block8: {
            byteArrayOutputStream = new ByteArrayOutputStream();
            int n4 = 0;
            n3 = 0;
            while (true) {
                if (n4 >= ((b[])object).length) break;
                Object object3 = object[n4];
                int n5 = j.a((b)object3);
                object2 = j.e(n5, (b)object3);
                object3 = j.f((b)object3);
                c.p(byteArrayOutputStream, n4);
                int n6 = ((Object)object2).length + 2 + ((Object)object3).length;
                c.q(byteArrayOutputStream, n6);
                c.p(byteArrayOutputStream, n5);
                byteArrayOutputStream.write((byte[])object2);
                byteArrayOutputStream.write((byte[])object3);
                n3 = n3 + 6 + n6;
                ++n4;
                continue;
                break;
            }
            try {
                object = byteArrayOutputStream.toByteArray();
                if (n3 != ((b[])object).length) break block8;
                object = new l(d.g, n3, (byte[])object, true);
            }
            catch (Throwable throwable2) {}
            byteArrayOutputStream.close();
            return object;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Expected size ");
        ((StringBuilder)object2).append(n3);
        ((StringBuilder)object2).append(", does not match actual size ");
        ((StringBuilder)object2).append(((b[])object).length);
        throw c.c(((StringBuilder)object2).toString());
        try {
            byteArrayOutputStream.close();
            throw throwable2;
        }
        catch (Throwable throwable3) {
            throwable2.addSuppressed(throwable3);
        }
        throw throwable2;
    }

    public static byte[] e(int n3, b object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            j.J(byteArrayOutputStream, n3, (b)object);
            object = byteArrayOutputStream.toByteArray();
            return object;
        }
    }

    public static byte[] f(b object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            j.K(byteArrayOutputStream, (b)object);
            object = byteArrayOutputStream.toByteArray();
            return object;
        }
    }

    public static String g(String string, String string2) {
        if ("!".equals(string2)) {
            return string.replace(":", "!");
        }
        String string3 = string;
        if (":".equals(string2)) {
            string3 = string.replace("!", ":");
        }
        return string3;
    }

    public static String h(String string) {
        int n3;
        int n4 = n3 = string.indexOf("!");
        if (n3 < 0) {
            n4 = string.indexOf(":");
        }
        String string2 = string;
        if (n4 > 0) {
            string2 = string.substring(n4 + 1);
        }
        return string2;
    }

    public static b i(b[] bArray, String string) {
        if (bArray.length <= 0) {
            return null;
        }
        string = j.h(string);
        for (int i3 = 0; i3 < bArray.length; ++i3) {
            if (!bArray[i3].b.equals(string)) continue;
            return bArray[i3];
        }
        return null;
    }

    public static String j(String string, String string2, byte[] byArray) {
        CharSequence charSequence = k.a(byArray);
        if (string.length() <= 0) {
            return j.g(string2, (String)charSequence);
        }
        if (string2.equals("classes.dex")) {
            return string;
        }
        if (!string2.contains("!") && !string2.contains(":")) {
            if (string2.endsWith(".apk")) {
                return string2;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(k.a(byArray));
            ((StringBuilder)charSequence).append(string2);
            return ((StringBuilder)charSequence).toString();
        }
        return j.g(string2, (String)charSequence);
    }

    public static int k(int n3) {
        return j.z(n3 * 2) / 8;
    }

    public static int l(int n3, int n4) {
        return j.z(Integer.bitCount(n3 & 0xFFFFFFFE) * n4) / 8;
    }

    public static int m(int n3, int n4, int n5) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 == 4) {
                    return n4 + n5;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected flag: ");
                stringBuilder.append(n3);
                throw c.c(stringBuilder.toString());
            }
            return n4;
        }
        throw c.c("HOT methods are not stored in the bitmap");
    }

    public static int[] n(InputStream inputStream, int n3) {
        int[] nArray = new int[n3];
        int n4 = 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            nArray[i3] = n4 += c.h(inputStream);
        }
        return nArray;
    }

    public static int o(BitSet bitSet, int n3, int n4) {
        int n5 = 2;
        if (!bitSet.get(j.m(2, n3, n4))) {
            n5 = 0;
        }
        if (bitSet.get(j.m(4, n3, n4))) {
            return n5 | 4;
        }
        return n5;
    }

    public static byte[] p(InputStream inputStream, byte[] byArray) {
        if (Arrays.equals(byArray, c.d(inputStream, byArray.length))) {
            return c.d(inputStream, k.b.length);
        }
        throw c.c("Invalid magic");
    }

    public static void q(InputStream inputStream, b b3) {
        int n3 = inputStream.available() - b3.f;
        int n4 = 0;
        block0: while (inputStream.available() > n3) {
            int n5 = n4 + c.h(inputStream);
            b3.i.put(n5, 1);
            int n6 = c.h(inputStream);
            while (true) {
                n4 = n5;
                if (n6 <= 0) continue block0;
                j.B(inputStream);
                --n6;
            }
        }
        if (inputStream.available() == n3) {
            return;
        }
        throw c.c("Read too much data during profile line parse");
    }

    public static b[] r(InputStream inputStream, byte[] byArray, byte[] byArray2, b[] bArray) {
        if (Arrays.equals(byArray, k.f)) {
            if (!Arrays.equals(k.a, byArray2)) {
                return j.s(inputStream, byArray, bArray);
            }
            throw c.c("Requires new Baseline Profile Metadata. Please rebuild the APK with Android Gradle Plugin 7.2 Canary 7 or higher");
        }
        if (Arrays.equals(byArray, k.g)) {
            return j.u(inputStream, byArray2, bArray);
        }
        throw c.c("Unsupported meta version");
    }

    public static b[] s(InputStream inputStream, byte[] objectArray, b[] bArray) {
        if (Arrays.equals(objectArray, k.f)) {
            int n3 = c.j(inputStream);
            long l3 = c.i(inputStream);
            objectArray = c.e(inputStream, (int)c.i(inputStream), (int)l3);
            if (inputStream.read() <= 0) {
                inputStream = new ByteArrayInputStream((byte[])objectArray);
                try {
                    objectArray = j.t(inputStream, n3, bArray);
                    return objectArray;
                }
                finally {
                    inputStream.close();
                }
            }
            throw c.c("Content found after the end of file");
        }
        throw c.c("Unsupported meta version");
    }

    public static b[] t(InputStream inputStream, int n3, b[] bArray) {
        int n4 = inputStream.available();
        int n5 = 0;
        if (n4 == 0) {
            return new b[0];
        }
        if (n3 == bArray.length) {
            String[] stringArray = new String[n3];
            int[] nArray = new int[n3];
            int n6 = 0;
            while (true) {
                if (n6 >= n3) break;
                n4 = c.h(inputStream);
                nArray[n6] = c.h(inputStream);
                stringArray[n6] = c.f(inputStream, n4);
                ++n6;
            }
            for (n4 = n5; n4 < n3; ++n4) {
                b b3 = bArray[n4];
                if (b3.b.equals(stringArray[n4])) {
                    b3.e = n6 = nArray[n4];
                    b3.h = j.n(inputStream, n6);
                    continue;
                }
                throw c.c("Order of dexfiles in metadata did not match baseline");
            }
            return bArray;
        }
        throw c.c("Mismatched number of dex files found in metadata");
    }

    public static b[] u(InputStream inputStream, byte[] objectArray, b[] bArray) {
        int n3 = c.h(inputStream);
        long l3 = c.i(inputStream);
        byte[] byArray = c.e(inputStream, (int)c.i(inputStream), (int)l3);
        if (inputStream.read() <= 0) {
            inputStream = new ByteArrayInputStream(byArray);
            try {
                objectArray = j.v(inputStream, objectArray, n3, bArray);
                return objectArray;
            }
            finally {
                inputStream.close();
            }
        }
        throw c.c("Content found after the end of file");
    }

    public static b[] v(InputStream object, byte[] byArray, int n3, b[] bArray) {
        int n4 = ((InputStream)object).available();
        if (n4 == 0) {
            return new b[0];
        }
        if (n3 == bArray.length) {
            for (int i3 = 0; i3 < n3; ++i3) {
                c.h((InputStream)object);
                Object object2 = c.f((InputStream)object, c.h((InputStream)object));
                long l3 = c.i((InputStream)object);
                n4 = c.h((InputStream)object);
                b b3 = j.i(bArray, (String)object2);
                if (b3 != null) {
                    b3.d = l3;
                    object2 = j.n((InputStream)object, n4);
                    if (!Arrays.equals(byArray, k.e)) continue;
                    b3.e = n4;
                    b3.h = (int[])object2;
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Missing profile key: ");
                ((StringBuilder)object).append((String)object2);
                throw c.c(((StringBuilder)object).toString());
            }
            return bArray;
        }
        throw c.c("Mismatched number of dex files found in metadata");
    }

    public static void w(InputStream object, b b3) {
        int n3;
        BitSet bitSet = BitSet.valueOf(c.d((InputStream)object, c.a(b3.g * 2)));
        for (int i3 = 0; i3 < (n3 = b3.g); ++i3) {
            if ((n3 = j.o(bitSet, i3, n3)) == 0) continue;
            Integer n4 = (Integer)b3.i.get(i3);
            object = n4;
            if (n4 == null) {
                object = 0;
            }
            b3.i.put(i3, n3 | (Integer)object);
        }
    }

    public static b[] x(InputStream inputStream, byte[] objectArray, String string) {
        if (Arrays.equals(objectArray, k.b)) {
            int n3 = c.j(inputStream);
            long l3 = c.i(inputStream);
            objectArray = c.e(inputStream, (int)c.i(inputStream), (int)l3);
            if (inputStream.read() <= 0) {
                inputStream = new ByteArrayInputStream((byte[])objectArray);
                try {
                    objectArray = j.y(inputStream, string, n3);
                    return objectArray;
                }
                finally {
                    inputStream.close();
                }
            }
            throw c.c("Content found after the end of file");
        }
        throw c.c("Unsupported version");
    }

    public static b[] y(InputStream inputStream, String object, int n3) {
        int n4;
        int n5 = inputStream.available();
        int n6 = 0;
        if (n5 == 0) {
            return new b[0];
        }
        b[] bArray = new b[n3];
        n5 = 0;
        while (true) {
            if (n5 >= n3) break;
            int n7 = c.h(inputStream);
            n4 = c.h(inputStream);
            long l3 = c.i(inputStream);
            long l4 = c.i(inputStream);
            long l5 = c.i(inputStream);
            bArray[n5] = new b((String)object, c.f(inputStream, n7), l4, 0L, n4, (int)l3, (int)l5, new int[n4], new TreeMap());
            ++n5;
        }
        for (n4 = n6; n4 < n3; ++n4) {
            object = bArray[n4];
            j.q(inputStream, (b)object);
            ((b)object).h = j.n(inputStream, ((b)object).e);
            j.w(inputStream, (b)object);
        }
        return bArray;
    }

    public static int z(int n3) {
        return n3 + 7 & 0xFFFFFFF8;
    }
}

