/*
 * Decompiled with CFR 0.152.
 */
package h1;

import java.util.concurrent.Executor;

public final class e
implements Executor {
    @Override
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}

