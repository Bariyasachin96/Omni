/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$PackageInfoFlags
 *  android.os.Build$VERSION
 */
package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;

public abstract class d {
    public static final q.c a = q.c.o();
    public static final Object b = new Object();
    public static c c = null;

    public static long a(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (Build.VERSION.SDK_INT >= 33) {
            return androidx.profileinstaller.d$a.a((PackageManager)packageManager, (Context)context).lastUpdateTime;
        }
        return packageManager.getPackageInfo((String)context.getPackageName(), (int)0).lastUpdateTime;
    }

    public static c b(int n3, boolean bl, boolean bl2, boolean bl3) {
        c c3;
        c = c3 = new c(n3, bl, bl2, bl3);
        a.m(c3);
        return c;
    }

    /*
     * Exception decompiling
     */
    public static c c(Context var0, boolean var1_7) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 2[TRYBLOCK] [3, 2 : 51->62)] java.lang.Throwable
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

    public static abstract class a {
        public static PackageInfo a(PackageManager packageManager, Context context) {
            return packageManager.getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of((long)0L));
        }
    }

    public static class b {
        public final int a;
        public final int b;
        public final long c;
        public final long d;

        public b(int n3, int n4, long l3, long l4) {
            this.a = n3;
            this.b = n4;
            this.c = l3;
            this.d = l4;
        }

        public static b a(File object) {
            object = new DataInputStream(new FileInputStream((File)object));
            try {
                b b3 = new b(((DataInputStream)object).readInt(), ((DataInputStream)object).readInt(), ((DataInputStream)object).readLong(), ((DataInputStream)object).readLong());
                return b3;
            }
            finally {
                ((InputStream)object).close();
            }
        }

        public void b(File file) {
            file.delete();
            try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));){
                dataOutputStream.writeInt(this.a);
                dataOutputStream.writeInt(this.b);
                dataOutputStream.writeLong(this.c);
                dataOutputStream.writeLong(this.d);
                return;
            }
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && object instanceof b) {
                object = (b)object;
                if (this.b == ((b)object).b && this.c == ((b)object).c && this.a == ((b)object).a && this.d == ((b)object).d) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.b, this.c, this.a, this.d);
        }
    }

    public static class c {
        public final int a;
        public final boolean b;
        public final boolean c;
        public final boolean d;

        public c(int n3, boolean bl, boolean bl2, boolean bl3) {
            this.a = n3;
            this.c = bl2;
            this.b = bl;
            this.d = bl3;
        }
    }
}

