/*
 * Decompiled with CFR 0.152.
 */
package c3;

import c3.f0;
import java.util.concurrent.ThreadFactory;

public final class c0
implements ThreadFactory {
    @Override
    public final Thread newThread(Runnable runnable) {
        return f0.a(runnable);
    }
}

