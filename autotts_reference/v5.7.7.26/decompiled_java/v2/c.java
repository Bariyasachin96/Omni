/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 */
package v2;

import android.graphics.RectF;
import java.util.Arrays;
import v2.a;
import v2.d;

public final class c
implements d {
    public final float a;

    public c(float f3) {
        this.a = f3;
    }

    public static c b(a a4) {
        return new c(a4.b());
    }

    public static float c(RectF rectF) {
        return Math.min(rectF.width() / 2.0f, rectF.height() / 2.0f);
    }

    @Override
    public float a(RectF rectF) {
        return j0.a.a(this.a, 0.0f, c.c(rectF));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof c)) {
            return false;
        }
        object = (c)object;
        return this.a == ((c)object).a;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.a)});
    }
}

