/*
 * Decompiled with CFR 0.152.
 */
package androidx.emoji2.text;

import androidx.emoji2.text.c;
import java.util.concurrent.ThreadFactory;

public final class a
implements ThreadFactory {
    public final String a;

    public /* synthetic */ a(String string) {
        this.a = string;
    }

    @Override
    public final Thread newThread(Runnable runnable) {
        return c.a(this.a, runnable);
    }
}

