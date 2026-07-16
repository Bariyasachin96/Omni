/*
 * Decompiled with CFR 0.152.
 */
package o;

import d3.j;
import e3.t;
import java.util.Map;
import o3.k;
import p.b;
import p.c;

public class l {
    public int a;
    public final c b;
    public final b c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;

    public l(int n3) {
        this.a = n3;
        if (n3 > 0) {
            this.b = new c(0, 0.75f);
            this.c = new b();
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public Object a(Object object) {
        k.e(object, "key");
        return null;
    }

    public void b(boolean bl, Object object, Object object2, Object object3) {
        k.e(object, "key");
        k.e(object2, "oldValue");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final Object c(Object object) {
        Object object2;
        Object object3;
        block10: {
            block9: {
                k.e(object, "key");
                b b3 = this.c;
                // MONITORENTER : b3
                object3 = this.b.a(object);
                if (object3 != null) {
                    ++this.h;
                    // MONITOREXIT : b3
                    return object3;
                }
                ++this.i;
                // MONITOREXIT : b3
                object3 = this.a(object);
                if (object3 == null) {
                    return null;
                }
                b3 = this.c;
                // MONITORENTER : b3
                ++this.f;
                object2 = this.b.d(object, object3);
                if (object2 == null) break block9;
                this.b.d(object, object2);
                break block10;
            }
            this.d += this.e(object, object3);
            j j3 = j.a;
        }
        // MONITOREXIT : b3
        if (object2 != null) {
            this.b(false, object, object3, object2);
            return object2;
        }
        this.g(this.a);
        return object3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Object d(Object object, Object object2) {
        k.e(object, "key");
        k.e(object2, "value");
        b b3 = this.c;
        synchronized (b3) {
            try {
                ++this.e;
                this.d += this.e(object, object2);
                Object object3 = this.b.d(object, object2);
                if (object3 != null) {
                    this.d -= this.e(object, object3);
                }
                j j3 = j.a;
                // MONITOREXIT @DISABLED, blocks:[0, 1] lbl12 : MonitorExitStatement: MONITOREXIT : var3_4
                if (object3 != null) {
                    this.b(false, object, object3, object2);
                }
                this.g(this.a);
                return object3;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final int e(Object object, Object object2) {
        int n3 = this.f(object, object2);
        if (n3 >= 0) {
            return n3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative size: ");
        stringBuilder.append(object);
        stringBuilder.append('=');
        stringBuilder.append(object2);
        throw new IllegalStateException(stringBuilder.toString().toString());
    }

    public int f(Object object, Object object2) {
        k.e(object, "key");
        k.e(object2, "value");
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void g(int n3) {
        IllegalStateException illegalStateException;
        block7: {
            while (true) {
                Map.Entry entry;
                block8: {
                    b b3 = this.c;
                    // MONITORENTER : b3
                    if (this.d < 0 || this.b.c() && this.d != 0) break block7;
                    if (this.d <= n3) break;
                    if (this.b.c()) {
                        return;
                    }
                    entry = (Map.Entry)t.q(this.b.b());
                    if (entry != null) break block8;
                    // MONITOREXIT : b3
                    return;
                }
                illegalStateException = entry.getKey();
                entry = entry.getValue();
                this.b.e(illegalStateException);
                this.d -= this.e(illegalStateException, entry);
                ++this.g;
                // MONITOREXIT : b3
                this.b(true, illegalStateException, entry, null);
                continue;
                break;
            }
            // MONITOREXIT : b3
            return;
        }
        illegalStateException = new IllegalStateException("LruCache.sizeOf() is reporting inconsistent results!");
        throw illegalStateException;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        b b3 = this.c;
        synchronized (b3) {
            Throwable throwable2;
            block3: {
                int n3;
                try {
                    n3 = this.h;
                    int n4 = this.i + n3;
                    n3 = n4 != 0 ? n3 * 100 / n4 : 0;
                }
                catch (Throwable throwable2) {
                    break block3;
                }
                CharSequence charSequence = new StringBuilder();
                charSequence.append("LruCache[maxSize=");
                charSequence.append(this.a);
                charSequence.append(",hits=");
                charSequence.append(this.h);
                charSequence.append(",misses=");
                charSequence.append(this.i);
                charSequence.append(",hitRate=");
                charSequence.append(n3);
                charSequence.append("%]");
                return charSequence.toString();
            }
            throw throwable2;
        }
    }
}

