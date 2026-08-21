/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.Typeface
 *  android.os.Build$VERSION
 */
package s2;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import j0.a;
import s2.g;
import s2.h;
import s2.i;

public abstract class j {
    public static Typeface a(Context context, Typeface typeface) {
        return j.b(context.getResources().getConfiguration(), typeface);
    }

    public static Typeface b(Configuration configuration, Typeface typeface) {
        if (Build.VERSION.SDK_INT >= 31 && g.a(configuration) != Integer.MAX_VALUE && g.a(configuration) != 0 && typeface != null) {
            return i.a(typeface, a.b(h.a(typeface) + g.a(configuration), 1, 1000), typeface.isItalic());
        }
        return null;
    }
}

