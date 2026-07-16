/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package x1;

import android.os.IBinder;
import android.os.IInterface;
import x1.c;
import y1.b;

public interface a
extends IInterface {

    public static abstract class a
    extends b
    implements a {
        public a() {
            super("com.google.android.gms.dynamic.IObjectWrapper");
        }

        public static a i(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
            if (iInterface instanceof a) {
                return (a)iInterface;
            }
            return new c(iBinder);
        }
    }
}

