/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.CancellationSignal
 */
package k0;

import android.os.CancellationSignal;

public final class a {
    public boolean a;
    public a b;
    public Object c;
    public boolean d;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void a() {
        block15: {
            Throwable throwable222;
            block14: {
                // MONITORENTER : this
                if (this.a) {
                    // MONITOREXIT : this
                    return;
                }
                this.a = true;
                this.d = true;
                a a4 = this.b;
                Object object = this.c;
                // MONITOREXIT : this
                if (a4 != null) {
                    try {
                        a4.onCancel();
                    }
                    catch (Throwable throwable222) {
                        break block14;
                    }
                }
                if (object != null) {
                    ((CancellationSignal)object).cancel();
                }
                break block15;
            }
            // MONITORENTER : this
            this.d = false;
            this.notifyAll();
            // MONITOREXIT : this
            throw throwable222;
        }
        // MONITORENTER : this
        this.d = false;
        this.notifyAll();
        // MONITOREXIT : this
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(a a4) {
        synchronized (this) {
            try {
                this.c();
                if (this.b == a4) {
                    return;
                }
                this.b = a4;
                if (this.a && a4 != null) {
                    // MONITOREXIT @DISABLED, blocks:[0, 2, 4] lbl9 : MonitorExitStatement: MONITOREXIT : this
                    a4.onCancel();
                    return;
                }
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void c() {
        while (this.d) {
            try {
                this.wait();
            }
            catch (InterruptedException interruptedException) {
            }
        }
    }

    public static interface a {
        public void onCancel();
    }
}

