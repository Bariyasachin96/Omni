/*
 * Decompiled with CFR 0.152.
 */
package f1;

import androidx.lifecycle.z;
import f1.b;
import f1.f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import m3.a;
import n3.l;
import o3.k;

public final class c {
    public final List a = new ArrayList();

    public final void a(s3.b b3, l l3) {
        k.e(b3, "clazz");
        k.e(l3, "initializer");
        this.a.add(new f(m3.a.a(b3), l3));
    }

    public final z.b b() {
        f[] fArray = this.a.toArray(new f[0]);
        return new b(Arrays.copyOf(fArray, fArray.length));
    }
}

