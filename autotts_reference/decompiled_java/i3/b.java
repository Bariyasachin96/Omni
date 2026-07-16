/*
 * Decompiled with CFR 0.152.
 */
package i3;

import g3.a;
import g3.c;

public final class b
implements a {
    public static final b c = new b();

    @Override
    public c b() {
        throw new IllegalStateException("This continuation is already complete");
    }

    @Override
    public void d(Object object) {
        throw new IllegalStateException("This continuation is already complete");
    }

    public String toString() {
        return "This continuation is already complete";
    }
}

