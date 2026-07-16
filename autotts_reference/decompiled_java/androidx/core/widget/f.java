/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.widget.EdgeEffect
 */
package androidx.core.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EdgeEffect;

public abstract class f {
    public static EdgeEffect a(Context context, AttributeSet attributeSet) {
        if (Build.VERSION.SDK_INT >= 31) {
            return b.a(context, attributeSet);
        }
        return new EdgeEffect(context);
    }

    public static float b(EdgeEffect edgeEffect) {
        if (Build.VERSION.SDK_INT >= 31) {
            return b.b(edgeEffect);
        }
        return 0.0f;
    }

    public static void c(EdgeEffect edgeEffect, float f3, float f4) {
        a.a(edgeEffect, f3, f4);
    }

    public static float d(EdgeEffect edgeEffect, float f3, float f4) {
        if (Build.VERSION.SDK_INT >= 31) {
            return b.c(edgeEffect, f3, f4);
        }
        f.c(edgeEffect, f3, f4);
        return f3;
    }

    public static abstract class a {
        public static void a(EdgeEffect edgeEffect, float f3, float f4) {
            edgeEffect.onPull(f3, f4);
        }
    }

    public static abstract class b {
        public static EdgeEffect a(Context context, AttributeSet attributeSet) {
            try {
                attributeSet = new EdgeEffect(context, attributeSet);
                return attributeSet;
            }
            catch (Throwable throwable) {
                return new EdgeEffect(context);
            }
        }

        public static float b(EdgeEffect edgeEffect) {
            try {
                float f3 = edgeEffect.getDistance();
                return f3;
            }
            catch (Throwable throwable) {
                return 0.0f;
            }
        }

        public static float c(EdgeEffect edgeEffect, float f3, float f4) {
            try {
                float f5 = edgeEffect.onPullDistance(f3, f4);
                return f5;
            }
            catch (Throwable throwable) {
                edgeEffect.onPull(f3, f4);
                return 0.0f;
            }
        }
    }
}

