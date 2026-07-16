/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.os.CancellationSignal
 */
package g0;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import f0.e;
import g0.k;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import l0.g;

public abstract class j {
    public ConcurrentHashMap a = new ConcurrentHashMap();

    public static Object d(Object[] objectArray, int n3, b b3) {
        int n4 = (n3 & 1) == 0 ? 400 : 700;
        boolean bl = (n3 & 2) != 0;
        return j.e(objectArray, n4, bl, b3);
    }

    public static Object e(Object[] objectArray, int n3, boolean bl, b b3) {
        int n4 = objectArray.length;
        Object object = null;
        int n5 = Integer.MAX_VALUE;
        for (int i3 = 0; i3 < n4; ++i3) {
            int n6;
            block4: {
                int n7;
                Object object2;
                block3: {
                    object2 = objectArray[i3];
                    n7 = Math.abs(b3.a(object2) - n3);
                    n6 = b3.b(object2) == bl ? 0 : 1;
                    n7 = n7 * 2 + n6;
                    if (object == null) break block3;
                    n6 = n5;
                    if (n5 <= n7) break block4;
                }
                object = object2;
                n6 = n7;
            }
            n5 = n6;
        }
        return object;
    }

    public abstract Typeface a(Context var1, e.c var2, Resources var3, int var4);

    public abstract Typeface b(Context var1, CancellationSignal var2, g.b[] var3, int var4);

    public Typeface c(Context object, Resources resources, int n3, String string, int n4) {
        block6: {
            if ((object = k.d((Context)object)) == null) {
                return null;
            }
            boolean bl = k.b((File)object, resources, n3);
            if (bl) break block6;
            ((File)object).delete();
            return null;
        }
        try {
            resources = Typeface.createFromFile((String)((File)object).getPath());
            return resources;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        catch (RuntimeException runtimeException) {
            return null;
        }
        finally {
            ((File)object).delete();
        }
    }

    public g.b f(g.b[] bArray, int n3) {
        return (g.b)j.d(bArray, n3, new b(this){
            public final j a;
            {
                this.a = j3;
            }

            public int c(g.b b3) {
                return b3.e();
            }

            public boolean d(g.b b3) {
                return b3.f();
            }
        });
    }

    public static interface b {
        public int a(Object var1);

        public boolean b(Object var1);
    }
}

