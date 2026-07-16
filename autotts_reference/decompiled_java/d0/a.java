/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 */
package d0;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public interface a
extends IInterface {
    public static final String b = "androidx$core$app$unusedapprestrictions$IUnusedAppRestrictionsBackportCallback".replace('$', '.');

    public static abstract class d0.a$a
    extends Binder
    implements d0.a {
        public static d0.a h(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(b);
            if (iInterface != null && iInterface instanceof d0.a) {
                return (d0.a)iInterface;
            }
            return new a(iBinder);
        }

        public static class a
        implements d0.a {
            public IBinder d;

            public a(IBinder iBinder) {
                this.d = iBinder;
            }

            public IBinder asBinder() {
                return this.d;
            }
        }
    }
}

