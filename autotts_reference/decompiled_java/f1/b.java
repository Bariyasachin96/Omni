/*
 * Decompiled with CFR 0.152.
 */
package f1;

import androidx.lifecycle.y;
import androidx.lifecycle.z;
import f1.a;
import f1.f;
import o3.k;

public final class b
implements z.b {
    public final f[] b;

    public b(f ... fArray) {
        k.e(fArray, "initializers");
        this.b = fArray;
    }

    @Override
    public y b(Class clazz, a object) {
        k.e(clazz, "modelClass");
        k.e(object, "extras");
        f[] fArray = this.b;
        int n3 = fArray.length;
        Object object2 = null;
        for (int i3 = 0; i3 < n3; ++i3) {
            f f3 = fArray[i3];
            if (!k.a(f3.a(), clazz)) continue;
            object2 = f3.b().f(object);
            object2 = object2 instanceof y ? (y)object2 : null;
        }
        if (object2 != null) {
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No initializer set for given class ");
        ((StringBuilder)object).append(clazz.getName());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }
}

