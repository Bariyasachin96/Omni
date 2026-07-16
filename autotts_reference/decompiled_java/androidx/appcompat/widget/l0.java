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

public class l0
extends f0 {
    public final WeakReference b;

    public l0(Context context, Resources resources) {
        super(resources);
        this.b = new WeakReference<Context>(context);
    }

    public Drawable getDrawable(int n3) {
        Drawable drawable = this.a(n3);
        Context context = (Context)this.b.get();
        if (drawable != null && context != null) {
            e0.g().w(context, n3, drawable);
        }
        return drawable;
    }
}

