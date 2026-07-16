/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.view.PointerIcon
 */
package o0;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.PointerIcon;

public final class j0 {
    public final PointerIcon a;

    public j0(PointerIcon pointerIcon) {
        this.a = pointerIcon;
    }

    public static j0 b(Context context, int n3) {
        return new j0(o0.j0$a.b(context, n3));
    }

    public Object a() {
        return this.a;
    }

    public static abstract class a {
        public static PointerIcon a(Bitmap bitmap, float f3, float f4) {
            return PointerIcon.create((Bitmap)bitmap, (float)f3, (float)f4);
        }

        public static PointerIcon b(Context context, int n3) {
            return PointerIcon.getSystemIcon((Context)context, (int)n3);
        }

        public static PointerIcon c(Resources resources, int n3) {
            return PointerIcon.load((Resources)resources, (int)n3);
        }
    }
}

