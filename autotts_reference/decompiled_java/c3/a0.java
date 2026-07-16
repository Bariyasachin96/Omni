/*
 * Decompiled with CFR 0.152.
 */
package c3;

import c3.d0;
import java.util.concurrent.ThreadFactory;

public final class a0
implements ThreadFactory {
    @Override
    public final Thread newThread(Runnable runnable) {
        return d0.a(runnable);
    }
}

