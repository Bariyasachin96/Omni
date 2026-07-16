/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 */
package v2;

import android.graphics.RectF;
import java.util.Arrays;
import v2.d;

public final class b
implements d {
    public final d a;
    public final float b;

    public b(float f3, d d3) {
        while (d3 instanceof b) {
            d3 = ((b)d3).a;
            f3 += ((b)d3).b;
        }
        this.a = d3;
        this.b = f3;
    }

    @Override
    public float a(RectF rectF) {
        return Math.max(0.0f, this.a.a(rectF) + this.b);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof b)) {
            return false;
        }
        object = (b)object;
        return this.a.equals(((b)object).a) && this.b == ((b)object).b;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, Float.valueOf(this.b)});
    }
}

