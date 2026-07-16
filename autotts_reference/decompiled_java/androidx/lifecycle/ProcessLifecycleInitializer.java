/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package androidx.lifecycle;

import android.content.Context;
import androidx.lifecycle.h;
import androidx.lifecycle.k;
import androidx.lifecycle.s;
import e3.l;
import java.util.List;
import k1.a;
import k1.b;

public final class ProcessLifecycleInitializer
implements b {
    @Override
    public List a() {
        return l.e();
    }

    public k c(Context context) {
        o3.k.e(context, "context");
        Object object = a.e(context);
        o3.k.d(object, "getInstance(context)");
        if (((a)object).g(ProcessLifecycleInitializer.class)) {
            h.a(context);
            object = s.k;
            ((s.b)object).b(context);
            return ((s.b)object).a();
        }
        throw new IllegalStateException("ProcessLifecycleInitializer cannot be initialized lazily.\n               Please ensure that you have:\n               <meta-data\n                   android:name='androidx.lifecycle.ProcessLifecycleInitializer'\n                   android:value='androidx.startup' />\n               under InitializationProvider in your AndroidManifest.xml");
    }
}

