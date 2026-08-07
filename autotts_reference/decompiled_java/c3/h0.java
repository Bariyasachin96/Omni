/*
 * Decompiled with CFR 0.152.
 */
package c3;

import c3.k0;
import java.util.concurrent.ThreadFactory;

public final class h0
implements ThreadFactory {
    @Override
    public final Thread newThread(Runnable runnable) {
        return k0.a(runnable);
    }
}

