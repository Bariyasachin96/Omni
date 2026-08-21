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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static p f(Context object) {
        synchronized (p.class) {
            if (e == null) {
                p p3;
                e = p3 = new p(object.getApplicationContext());
            }
            object = e;
            return object;
        }
    }

    public void a() {
        synchronized (this) {
            boolean bl = this.a.exists();
            if (bl) {
                FileWriter fileWriter = new FileWriter(this.a, false);
                ((Writer)fileWriter).close();
            }
            return;
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

    public void h(a object, String object2, String string) {
        synchronized (this) {
            boolean bl;
            int n3 = ((Enum)object).ordinal();
            if (n3 != 0 && n3 != 1) {
                if (n3 != 2) {
                    if (n3 == 3) {
                        Log.e((String)object2, (String)string);
                    }
                } else {
                    Log.w((String)object2, (String)string);
                }
            }
            if (!(bl = this.d)) {
                return;
            }
            this.i();
            SimpleDateFormat simpleDateFormat = this.b;
            Date date = new Date();
            string = String.format("%s [%s] %s: %s", simpleDateFormat.format(date), ((a)((Object)object)).c, object2, string);
            object2 = new FileWriter(this.a, true);
            object = new BufferedWriter((Writer)object2);
            ((Writer)object).write(string);
            ((BufferedWriter)object).newLine();
            ((BufferedWriter)object).close();
            return;
        }
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

