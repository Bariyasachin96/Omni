/*
 * Decompiled with CFR 0.152.
 */
package androidx.emoji2.text;

import androidx.emoji2.text.EmojiCompatInitializer;
import androidx.emoji2.text.f;
import java.util.concurrent.ThreadPoolExecutor;

public final class g
implements Runnable {
    public final EmojiCompatInitializer.b c;
    public final f.i d;
    public final ThreadPoolExecutor e;

    public /* synthetic */ g(EmojiCompatInitializer.b b3, f.i i3, ThreadPoolExecutor threadPoolExecutor) {
        this.c = b3;
        this.d = i3;
        this.e = threadPoolExecutor;
    }

    @Override
    public final void run() {
        EmojiCompatInitializer.b.b(this.c, this.d, this.e);
    }
}

