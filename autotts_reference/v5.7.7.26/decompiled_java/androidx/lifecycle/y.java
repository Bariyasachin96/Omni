/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class y {
    public final Map a = new HashMap();
    public final Set b = new LinkedHashSet();
    public volatile boolean c = false;

    public static void b(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable)object).close();
                return;
            }
            catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a() {
        Iterator<Object> iterator;
        this.c = true;
        Object object = this.a;
        if (object != null) {
            synchronized (object) {
                try {
                    iterator = this.a.values().iterator();
                    while (iterator.hasNext()) {
                        y.b(iterator.next());
                    }
                }
                catch (Throwable throwable) {
                }
                throw throwable;
            }
        }
        if ((object = this.b) != null) {
            synchronized (object) {
                try {
                    iterator = this.b.iterator();
                    while (iterator.hasNext()) {
                        y.b((Closeable)iterator.next());
                    }
                }
                catch (Throwable throwable) {
                }
                throw throwable;
            }
        }
        this.d();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object c(String string) {
        Map map = this.a;
        if (map == null) {
            return null;
        }
        synchronized (map) {
            return this.a.get(string);
        }
    }

    public void d() {
    }
}

