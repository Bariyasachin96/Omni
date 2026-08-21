/*
 * Decompiled with CFR 0.152.
 */
package h1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public abstract class c {
    public static int a(int n3) {
        return (n3 + 7 & 0xFFFFFFF8) / 8;
    }

    /*
     * Exception decompiling
     */
    public static byte[] b(byte[] var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 4[TRYBLOCK] [4 : 58->65)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static RuntimeException c(String string) {
        return new IllegalStateException(string);
    }

    public static byte[] d(InputStream object, int n3) {
        int n4;
        byte[] byArray = new byte[n3];
        for (int i3 = 0; i3 < n3; i3 += n4) {
            n4 = ((InputStream)object).read(byArray, i3, n3 - i3);
            if (n4 >= 0) {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Not enough bytes to read: ");
            ((StringBuilder)object).append(n3);
            throw c.c(((StringBuilder)object).toString());
        }
        return byArray;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static byte[] e(InputStream object, int n3, int n4) {
        Throwable throwable2;
        Inflater inflater;
        block9: {
            int n5;
            inflater = new Inflater();
            try {
                int n6;
                int n7;
                byte[] byArray = new byte[n4];
                byte[] byArray2 = new byte[2048];
                int n8 = 0;
                for (n5 = 0; !inflater.finished() && !inflater.needsDictionary() && n5 < n3; n8 += n7, n5 += n6) {
                    n6 = ((InputStream)object).read(byArray2);
                    if (n6 < 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid zip data. Stream ended after $totalBytesRead bytes. Expected ");
                        ((StringBuilder)object).append(n3);
                        ((StringBuilder)object).append(" bytes");
                        throw c.c(((StringBuilder)object).toString());
                    }
                    inflater.setInput(byArray2, 0, n6);
                    try {
                        n7 = inflater.inflate(byArray, n8, n4 - n8);
                        continue;
                    }
                    catch (DataFormatException dataFormatException) {
                        throw c.c(dataFormatException.getMessage());
                    }
                }
                if (n5 == n3) {
                    boolean bl = inflater.finished();
                    if (bl) {
                        inflater.end();
                        return byArray;
                    }
                    throw c.c("Inflater did not finish");
                }
            }
            catch (Throwable throwable2) {
                break block9;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Didn't read enough bytes during decompression. expected=");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(" actual=");
            ((StringBuilder)object).append(n5);
            throw c.c(((StringBuilder)object).toString());
        }
        inflater.end();
        throw throwable2;
    }

    public static String f(InputStream inputStream, int n3) {
        return new String(c.d(inputStream, n3), StandardCharsets.UTF_8);
    }

    public static long g(InputStream object, int n3) {
        object = c.d((InputStream)object, n3);
        long l3 = 0L;
        for (int i3 = 0; i3 < n3; ++i3) {
            l3 += (long)(object[i3] & 0xFF) << i3 * 8;
        }
        return l3;
    }

    public static int h(InputStream inputStream) {
        return (int)c.g(inputStream, 2);
    }

    public static long i(InputStream inputStream) {
        return c.g(inputStream, 4);
    }

    public static int j(InputStream inputStream) {
        return (int)c.g(inputStream, 1);
    }

    public static int k(String string) {
        return string.getBytes(StandardCharsets.UTF_8).length;
    }

    public static void l(InputStream inputStream, OutputStream outputStream, FileLock object) {
        if (object != null && ((FileLock)object).isValid()) {
            int n3;
            object = new byte[512];
            while ((n3 = inputStream.read((byte[])object)) > 0) {
                outputStream.write((byte[])object, 0, n3);
            }
            return;
        }
        throw new IOException("Unable to acquire a lock on the underlying file channel.");
    }

    public static void m(OutputStream outputStream, byte[] byArray) {
        c.q(outputStream, byArray.length);
        byArray = c.b(byArray);
        c.q(outputStream, byArray.length);
        outputStream.write(byArray);
    }

    public static void n(OutputStream outputStream, String string) {
        outputStream.write(string.getBytes(StandardCharsets.UTF_8));
    }

    public static void o(OutputStream outputStream, long l3, int n3) {
        byte[] byArray = new byte[n3];
        for (int i3 = 0; i3 < n3; ++i3) {
            byArray[i3] = (byte)(l3 >> i3 * 8 & 0xFFL);
        }
        outputStream.write(byArray);
    }

    public static void p(OutputStream outputStream, int n3) {
        c.o(outputStream, n3, 2);
    }

    public static void q(OutputStream outputStream, long l3) {
        c.o(outputStream, l3, 4);
    }

    public static void r(OutputStream outputStream, int n3) {
        c.o(outputStream, n3, 1);
    }
}

