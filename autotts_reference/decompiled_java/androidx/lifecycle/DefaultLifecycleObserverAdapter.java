/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.b;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;

public final class DefaultLifecycleObserverAdapter
implements i {
    public final b a;
    public final i b;

    public DefaultLifecycleObserverAdapter(b b3, i i3) {
        o3.k.e(b3, "defaultLifecycleObserver");
        this.a = b3;
        this.b = i3;
    }

    @Override
    public void d(k k3, f.a a4) {
        o3.k.e(k3, "source");
        o3.k.e((Object)a4, "event");
        switch (androidx.lifecycle.DefaultLifecycleObserverAdapter$a.a[a4.ordinal()]) {
            default: {
                break;
            }
            case 7: {
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
            }
            case 6: {
                this.a.b(k3);
                break;
            }
            case 5: {
                this.a.g(k3);
                break;
            }
            case 4: {
                this.a.e(k3);
                break;
            }
            case 3: {
                this.a.a(k3);
                break;
            }
            case 2: {
                this.a.f(k3);
                break;
            }
            case 1: {
                this.a.c(k3);
            }
        }
        i i3 = this.b;
        if (i3 != null) {
            i3.d(k3, a4);
        }
    }

    public abstract class a {
        public static final int[] a;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static {
            int[] nArray = new int[f.a.values().length];
            try {
                nArray[f.a.ON_CREATE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                nArray[f.a.ON_START.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                nArray[f.a.ON_RESUME.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                nArray[f.a.ON_PAUSE.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                nArray[f.a.ON_STOP.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                nArray[f.a.ON_DESTROY.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                nArray[f.a.ON_ANY.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            a = nArray;
        }
    }
}

