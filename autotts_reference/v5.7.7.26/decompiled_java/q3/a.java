/*
 * Decompiled with CFR 0.152.
 */
package q3;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import o3.k;

public final class a
extends p3.a {
    @Override
    public Random c() {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        k.d(threadLocalRandom, "current()");
        return threadLocalRandom;
    }
}

