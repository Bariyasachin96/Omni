/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Parcelable
 *  android.util.Log
 *  android.widget.Toast
 */
package c3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class p {
    public static p e;
    public final File a;
    public final SimpleDateFormat b;
    public final SharedPreferences c;
    public boolean d;

    public p(Context context) {
        File file = new File(context.getFilesDir(), "logs");
        if (!file.exists()) {
            file.mkdirs();
        }
        this.a = new File(file, "auto_tts.log");
        this.b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        context = context.getSharedPreferences("auto_tts_settings", 0);
        this.c = context;
        this.d = context.getBoolean("logging_enabled", false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static p f(Context object) {
        synchronized (p.class) {
            try {
                p p3;
                if (e != null) return e;
                e = p3 = new p(object.getApplicationContext());
                return e;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a() {
        synchronized (this) {
            try {
                boolean bl = this.a.exists();
                if (bl) {
                    try {
                        FileWriter fileWriter = new FileWriter(this.a, false);
                        ((Writer)fileWriter).close();
                    }
                    catch (IOException iOException) {
                        Log.e((String)"TtsLogger", (String)"Failed to clear log", (Throwable)iOException);
                    }
                }
                return;
            }
            catch (Throwable throwable22) {}
            throw throwable22;
        }
    }

    public Intent b(Context context) {
        if (this.a.exists() && this.a.length() != 0L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(context.getPackageName());
            stringBuilder.append(".fileprovider");
            context = FileProvider.h(context, stringBuilder.toString(), this.a);
            stringBuilder = new Intent("android.intent.action.SEND");
            stringBuilder.setType("text/plain");
            stringBuilder.putExtra("android.intent.extra.STREAM", (Parcelable)context);
            stringBuilder.putExtra("android.intent.extra.SUBJECT", "Auto TTS Log");
            stringBuilder.addFlags(1);
            return stringBuilder;
        }
        return null;
    }

    public void c(String string, String string2) {
        this.h(c3.p$a.d, string, string2);
    }

    public void d(String string, String string2) {
        this.h(c3.p$a.g, string, string2);
    }

    public void e(String string, String string2, Throwable throwable) {
        a a4 = c3.p$a.g;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("\n");
        stringBuilder.append(Log.getStackTraceString((Throwable)throwable));
        this.h(a4, string, stringBuilder.toString());
    }

    public boolean g() {
        return this.d;
    }

    /*
     * Exception decompiling
     */
    public void h(a var1_1, String var2_5, String var3_7) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 2[TRYBLOCK] [2 : 47->59)] java.lang.Throwable
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

    public final void i() {
        if (this.a.exists() && this.a.length() >= 0x200000L) {
            Comparable<File> comparable = new File(this.a.getParent(), "auto_tts.log.3");
            if (((File)comparable).exists()) {
                ((File)comparable).delete();
            }
            for (int i3 = 2; i3 >= 1; --i3) {
                Object object = this.a.getParent();
                comparable = new StringBuilder();
                ((StringBuilder)comparable).append("auto_tts.log.");
                ((StringBuilder)comparable).append(i3);
                comparable = new File((String)object, ((StringBuilder)comparable).toString());
                String string = this.a.getParent();
                object = new StringBuilder();
                ((StringBuilder)object).append("auto_tts.log.");
                ((StringBuilder)object).append(i3 + 1);
                object = new File(string, ((StringBuilder)object).toString());
                if (!((File)comparable).exists()) continue;
                ((File)comparable).renameTo((File)object);
            }
            this.a.renameTo(new File(this.a.getParent(), "auto_tts.log.1"));
        }
    }

    public void j(boolean bl) {
        this.d = bl;
        this.c.edit().putBoolean("logging_enabled", bl).apply();
    }

    public void k(Context context) {
        Intent intent = this.b(context);
        if (intent == null) {
            Toast.makeText((Context)context, (CharSequence)"No log file to share", (int)0).show();
            return;
        }
        intent = Intent.createChooser((Intent)intent, (CharSequence)"Share Auto TTS Log");
        intent.addFlags(0x10000000);
        context.startActivity(intent);
    }

    public static final class a
    extends Enum {
        public static final /* enum */ a d = new a("DEBUG", 0, "D");
        public static final /* enum */ a e = new a("INFO", 1, "I");
        public static final /* enum */ a f = new a("WARN", 2, "W");
        public static final /* enum */ a g = new a("ERROR", 3, "E");
        public static final a[] h = c3.p$a.a();
        public final String c;

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public a() {
            void var3_2;
            void cfr_renamed_1;
            void cfr_renamed_2;
            this.c = var3_2;
        }

        public static /* synthetic */ a[] a() {
            return new a[]{d, e, f, g};
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        public static a[] values() {
            return (a[])h.clone();
        }
    }
}

