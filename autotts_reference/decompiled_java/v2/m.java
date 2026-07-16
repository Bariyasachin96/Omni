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

public final class m
implements d {
    public final float a;

    public m(float f3) {
        this.a = f3;
    }

    private static float b(RectF rectF) {
        return Math.min(rectF.width(), rectF.height());
    }

    @Override
    public float a(RectF rectF) {
        return this.a * m.b(rectF);
    }

    public float c() {
        return this.a;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof m)) {
            return false;
        }
        object = (m)object;
        return this.a == ((m)object).a;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.a)});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((int)(this.c() * 100.0f));
        stringBuilder.append("%");
        return stringBuilder.toString();
    }
}

