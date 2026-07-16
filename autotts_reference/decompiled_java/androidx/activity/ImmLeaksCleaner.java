/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.inputmethod.InputMethodManager
 */
package androidx.activity;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

final class ImmLeaksCleaner
implements i {
    public static int b;
    public static Field c;
    public static Field d;
    public static Field e;
    public Activity a;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void h() {
        try {
            Field field;
            b = 2;
            d = field = InputMethodManager.class.getDeclaredField("mServedView");
            ((AccessibleObject)field).setAccessible(true);
            e = field = InputMethodManager.class.getDeclaredField("mNextServedView");
            ((AccessibleObject)field).setAccessible(true);
            c = field = InputMethodManager.class.getDeclaredField("mH");
            ((AccessibleObject)field).setAccessible(true);
            b = 1;
            return;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void d(k var1_1, f.a var2_3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 3[TRYBLOCK] [5 : 78->98)] java.lang.Throwable
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
}

