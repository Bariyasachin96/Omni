/*
 * Decompiled with CFR 0.152.
 */
package t3;

import java.util.Iterator;
import n3.p;
import o3.k;
import t3.a;
import t3.b;

public abstract class d {
    public static final Iterator a(p p3) {
        k.e(p3, "block");
        b b3 = new b();
        b3.h(h3.b.a(p3, b3, b3));
        return b3;
    }

    public static a b(p p3) {
        k.e(p3, "block");
        return new a(p3){
            public final p a;
            {
                this.a = p3;
            }

            @Override
            public Iterator iterator() {
                return d.a(this.a);
            }
        };
    }
}

