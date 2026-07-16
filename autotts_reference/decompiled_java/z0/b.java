/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.text.Editable$Factory
 */
package z0;

import android.text.Editable;
import androidx.emoji2.text.o;

public final class b
extends Editable.Factory {
    public static final Object a = new Object();
    public static volatile Editable.Factory b;
    public static Class c;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public b() {
        try {
            c = Class.forName("android.text.DynamicLayout$ChangeWatcher", false, b.class.getClassLoader());
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Editable.Factory getInstance() {
        if (b != null) return b;
        Object object = a;
        synchronized (object) {
            try {
                if (b != null) return b;
                b b3 = new b();
                b = b3;
                return b;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public Editable newEditable(CharSequence charSequence) {
        Class clazz = c;
        if (clazz != null) {
            return o.c(clazz, charSequence);
        }
        return super.newEditable(charSequence);
    }
}

