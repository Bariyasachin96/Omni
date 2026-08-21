/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Trace
 */
package l1;

import android.os.Trace;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import l1.a;
import l1.c;

public abstract class b {
    public static long a;
    public static Method b;

    public static void a(String string) {
        c.a(string);
    }

    public static void b() {
        c.b();
    }

    public static void c(String object, Exception exception) {
        if (exception instanceof InvocationTargetException) {
            object = exception.getCause();
            if (object instanceof RuntimeException) {
                throw (RuntimeException)object;
            }
            throw new RuntimeException((Throwable)object);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean d() {
        try {
            if (b != null) return l1.b.e();
            return l1.a.a();
        }
        catch (NoClassDefFoundError | NoSuchMethodError linkageError) {
            return l1.b.e();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean e() {
        try {
            if (b != null) return (Boolean)b.invoke(null, a);
            a = Trace.class.getField("TRACE_TAG_APP").getLong(null);
            b = Trace.class.getMethod("isTagEnabled", Long.TYPE);
            return (Boolean)b.invoke(null, a);
        }
        catch (Exception exception) {}
        l1.b.c("isTagEnabled", exception);
        return false;
    }
}

