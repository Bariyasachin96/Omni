/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.e0;
import androidx.appcompat.widget.f0;
import java.lang.ref.WeakReference;

public class s0
extends f0 {
    public static boolean c = false;
    public final WeakReference b;

    public s0(Context context, Resources resources) {
        super(resources);
        this.b = new WeakReference<Context>(context);
    }

    public static boolean b() {
        return c;
    }

    public static boolean c() {
        s0.b();
        return false;
    }

    public Drawable getDrawable(int n3) {
        Context context = (Context)this.b.get();
        if (context != null) {
            return e0.g().s(context, this, n3);
        }
        return this.a(n3);
    }
}

