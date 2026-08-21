/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.AssetManager
 *  android.util.Log
 */
package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;
import androidx.profileinstaller.b;
import androidx.profileinstaller.d;
import h1.e;
import h1.f;
import java.io.File;
import java.util.concurrent.Executor;

public abstract class c {
    public static final c a = new c(){

        @Override
        public void a(int n3, Object object) {
        }

        @Override
        public void b(int n3, Object object) {
        }
    };
    public static final c b = new c(){

        @Override
        public void a(int n3, Object object) {
        }

        @Override
        public void b(int n3, Object object) {
            String string;
            switch (n3) {
                default: {
                    string = "";
                    break;
                }
                case 11: {
                    string = "RESULT_DELETE_SKIP_FILE_SUCCESS";
                    break;
                }
                case 10: {
                    string = "RESULT_INSTALL_SKIP_FILE_SUCCESS";
                    break;
                }
                case 8: {
                    string = "RESULT_PARSE_EXCEPTION";
                    break;
                }
                case 7: {
                    string = "RESULT_IO_EXCEPTION";
                    break;
                }
                case 6: {
                    string = "RESULT_BASELINE_PROFILE_NOT_FOUND";
                    break;
                }
                case 5: {
                    string = "RESULT_DESIRED_FORMAT_UNSUPPORTED";
                    break;
                }
                case 4: {
                    string = "RESULT_NOT_WRITABLE";
                    break;
                }
                case 3: {
                    string = "RESULT_UNSUPPORTED_ART_VERSION";
                    break;
                }
                case 2: {
                    string = "RESULT_ALREADY_INSTALLED";
                    break;
                }
                case 1: {
                    string = "RESULT_INSTALL_SUCCESS";
                }
            }
            if (n3 != 6 && n3 != 7 && n3 != 8) {
                return;
            }
            Log.e((String)"ProfileInstaller", (String)string, (Throwable)((Throwable)object));
        }
    };

    public static /* synthetic */ void a(c c3, int n3, Object object) {
        c3.b(n3, object);
    }

    public static boolean b(File file) {
        return new File(file, "profileinstaller_profileWrittenFor_lastUpdateTime.dat").delete();
    }

    public static void c(Context context, Executor executor, c c3) {
        c.b(context.getFilesDir());
        c.f(executor, c3, 11, null);
    }

    /*
     * Exception decompiling
     */
    public static boolean d(PackageInfo var0, File var1_3, c var2_5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
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

    /*
     * Exception decompiling
     */
    public static void e(PackageInfo var0, File var1_3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
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

    public static void f(Executor executor, c c3, int n3, Object object) {
        executor.execute(new f(c3, n3, object));
    }

    public static boolean g(AssetManager object, String string, PackageInfo packageInfo, File file, String string2, Executor executor, c c3) {
        if (!((b)(object = new b((AssetManager)object, executor, c3, string2, "dexopt/baseline.prof", "dexopt/baseline.profm", new File(new File("/data/misc/profiles/cur/0", string), "primary.prof")))).e()) {
            return false;
        }
        boolean bl = ((b)object).h().l().m();
        if (bl) {
            c.e(packageInfo, file);
        }
        return bl;
    }

    public static void h(Context context) {
        c.i(context, new e(), a);
    }

    public static void i(Context context, Executor executor, c c3) {
        c.j(context, executor, c3, false);
    }

    public static void j(Context context, Executor executor, c c3, boolean bl) {
        Context context2 = context.getApplicationContext();
        String string = context2.getPackageName();
        Object object = context2.getApplicationInfo();
        context2 = context2.getAssets();
        object = new File(object.sourceDir).getName();
        PackageManager packageManager = context.getPackageManager();
        boolean bl2 = false;
        try {
            packageManager = packageManager.getPackageInfo(string, 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            c3.b(7, (Object)nameNotFoundException);
            d.c(context, false);
            return;
        }
        File file = context.getFilesDir();
        if (!bl && c.d((PackageInfo)packageManager, file, c3)) {
            context.getPackageName();
            d.c(context, false);
            return;
        }
        context.getPackageName();
        boolean bl3 = bl2;
        if (c.g((AssetManager)context2, string, (PackageInfo)packageManager, file, (String)object, executor, c3)) {
            bl3 = bl2;
            if (bl) {
                bl3 = true;
            }
        }
        d.c(context, bl3);
    }

    public static void k(Context context, Executor executor, c c3) {
        String string = context.getApplicationContext().getPackageName();
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager = packageManager.getPackageInfo(string, 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            c.f(executor, c3, 7, (Object)nameNotFoundException);
            return;
        }
        c.e((PackageInfo)packageManager, context.getFilesDir());
        c.f(executor, c3, 10, null);
    }

    public static interface c {
        public void a(int var1, Object var2);

        public void b(int var1, Object var2);
    }
}

