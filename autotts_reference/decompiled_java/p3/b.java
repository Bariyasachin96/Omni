/*
 * Decompiled with CFR 0.152.
 */
package p3;

import java.util.Random;
import o3.k;

public final class b
extends p3.a {
    public final a e = new ThreadLocal(){

        public Random a() {
            return new Random();
        }
    };

    @Override
    public Random c() {
        Object t3 = this.e.get();
        k.d(t3, "implStorage.get()");
        return (Random)t3;
    }
}

