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

public final class a
implements d {
    public final float a;

    public a(float f3) {
        this.a = f3;
    }

    @Override
    public float a(RectF rectF) {
        return this.a;
    }

    public float b() {
        return this.a;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof a)) {
            return false;
        }
        object = (a)object;
        return this.a == ((a)object).a;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.a)});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.b());
        stringBuilder.append("px");
        return stringBuilder.toString();
    }
}

