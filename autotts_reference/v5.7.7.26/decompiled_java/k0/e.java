/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.os.Trace
 */
package k0;

import android.os.Build;
import android.os.Trace;
import java.lang.reflect.Method;

public abstract class e {
    public static long a;
    public static Method b;
    public static Method c;
    public static Method d;
    public static Method e;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        if (Build.VERSION.SDK_INT >= 29) return;
        try {
            a = Trace.class.getField("TRACE_TAG_APP").getLong(null);
            Class<Long> clazz = Long.TYPE;
            b = Trace.class.getMethod("isTagEnabled", clazz);
            Class<Integer> clazz2 = Integer.TYPE;
            c = Trace.class.getMethod("asyncTraceBegin", clazz, String.class, clazz2);
            d = Trace.class.getMethod("asyncTraceEnd", clazz, String.class, clazz2);
            e = Trace.class.getMethod("traceCounter", clazz, String.class, clazz2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static void a(String string) {
        Trace.beginSection((String)string);
    }

    public static void b() {
        Trace.endSection();
    }
}

