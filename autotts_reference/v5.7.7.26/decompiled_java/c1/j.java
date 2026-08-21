/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.y;
import e3.l;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import o.f;
import o3.k;
import r3.e;

public abstract class j {
    public static final float a(f object, f f3, float f4) {
        k.e(object, "xValues");
        k.e(f3, "yValues");
        if (0.0f <= f4 && f4 <= 1.0f) {
            Iterator iterator = e.e(0, ((f)object).b).iterator();
            while (iterator.hasNext()) {
                int n3;
                int n4 = ((e3.y)iterator).nextInt();
                float f5 = ((f)object).b(n4);
                if (!j.b(f4, f5, ((f)object).b((n3 = n4 + 1) % ((f)object).c()))) continue;
                float f6 = y.j(((f)object).b(n3 %= ((f)object).c()) - ((f)object).b(n4), 1.0f);
                f5 = y.j(f3.b(n3) - f3.b(n4), 1.0f);
                f4 = f6 < 0.001f ? 0.5f : y.j(f4 - ((f)object).b(n4), 1.0f) / f6;
                return y.j(f3.b(n4) + f5 * f4, 1.0f);
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid progress: ");
        ((StringBuilder)object).append(f4);
        throw new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    public static final boolean b(float f3, float f4, float f5) {
        if (f5 >= f4) {
            return f4 <= f3 && f3 <= f5;
        }
        return f3 >= f4 || f3 <= f5;
        {
        }
    }

    public static final void c(f f3) {
        k.e(f3, "p");
        Object object = Boolean.TRUE;
        float[] fArray = f3.a;
        int n3 = f3.b;
        int n4 = 0;
        int n5 = 0;
        while (true) {
            boolean bl = true;
            if (n5 >= n3) break;
            float f4 = fArray[n5];
            if (!(((Boolean)object).booleanValue() && 0.0f <= f4 && f4 <= 1.0f)) {
                bl = false;
            }
            object = bl;
            ++n5;
        }
        if (((Boolean)object).booleanValue()) {
            object = e.e(1, f3.c());
            if (object instanceof Collection && ((Collection)object).isEmpty()) {
                n3 = 0;
            } else {
                object = object.iterator();
                n5 = 0;
                while (true) {
                    n3 = n5;
                    if (!object.hasNext()) break;
                    n3 = ((e3.y)object).nextInt();
                    if (!(f3.b(n3) < f3.b(n3 - 1))) continue;
                    n5 = n3 = n5 + 1;
                    if (n3 >= 0) continue;
                    l.j();
                    n5 = n3;
                }
            }
            n5 = n4;
            if (n3 <= 1) {
                n5 = 1;
            }
            if (n5 != 0) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("FloatMapping - Progress wraps more than once: ");
            ((StringBuilder)object).append(f.f(f3, null, null, null, 0, null, 31, null));
            throw new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("FloatMapping - Progress outside of range: ");
        ((StringBuilder)object).append(f.f(f3, null, null, null, 0, null, 31, null));
        throw new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }
}

