/*
 * Decompiled with CFR 0.152.
 */
package i3;

import g3.c;
import g3.d;
import i3.a;

public abstract class h
extends a {
    public h(g3.a a4) {
        super(a4);
        if (a4 != null) {
            if (a4.b() == d.c) {
                return;
            }
            throw new IllegalArgumentException("Coroutines with restricted suspension must have EmptyCoroutineContext");
        }
    }

    @Override
    public c b() {
        return d.c;
    }
}

