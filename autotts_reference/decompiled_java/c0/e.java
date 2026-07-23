/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParserException
 */
package c0;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

public abstract class e {
    public static final Object a = new Object();

    /*
     * Exception decompiling
     */
    public static void a(Context var0, String var1_5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 1[TRYBLOCK] [2 : 29->41)] java.lang.Throwable
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

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String b(Context var0) {
        var6_4 = e.a;
        synchronized (var6_4) {
            block16: {
                var4_5 = "";
                var7_7 = var0.openFileInput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                var5_8 = Xml.newPullParser();
                var5_8.setInput((InputStream)var7_7, "UTF-8");
                var1_9 = var5_8.getDepth();
                do {
                    var2_10 = var5_8.next();
                    var3_11 = var4_5;
                    if (var2_10 == 1) break block16;
                    if (var2_10 != 3) continue;
                    var3_11 = var4_5;
                    if (var5_8.getDepth() <= var1_9) break block16;
                } while (var2_10 == 3 || var2_10 == 4 || !var5_8.getName().equals("locales"));
                var3_11 = var5_8.getAttributeValue(null, "application_locales");
            }
            var5_8 = var3_11;
            if (var7_7 != null) {
            }
            ** GOTO lbl50
            {
                catch (Throwable var0_1) {
                    ** GOTO lbl-1000
                }
                catch (IOException | XmlPullParserException var3_12) {}
                {
                    Log.w((String)"AppLocalesStorageHelper", (String)"Reading app Locales : Unable to parse through file :androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                    var5_8 = var4_5;
                    if (var7_7 == null) ** GOTO lbl50
                    var3_11 = var4_5;
                }
            }
            var7_7.close();
            var5_8 = var3_11;
lbl-1000:
            // 1 sources

            {
                block17: {
                    if (var7_7 == null) throw var0_1;
                    try {
                        var7_7.close();
                    }
                    catch (IOException var3_13) {
                        throw var0_1;
                    }
                    throw var0_1;
                    catch (Throwable var0_2) {
                        break block17;
                    }
                    catch (FileNotFoundException var0_3) {
                        return "";
                    }
                }
                throw var0_2;
                catch (IOException var4_6) {
                    var5_8 = var3_11;
                }
lbl50:
                // 4 sources

                if (var5_8.isEmpty()) {
                    var0.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                }
                return var5_8;
            }
        }
    }
}

