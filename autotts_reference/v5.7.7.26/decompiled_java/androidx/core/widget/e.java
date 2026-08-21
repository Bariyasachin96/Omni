/*
 * Decompiled with CFR 0.152.
 */
package androidx.core.widget;

import androidx.core.widget.ContentLoadingProgressBar;

public final class e
implements Runnable {
    public final ContentLoadingProgressBar c;

    public /* synthetic */ e(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.c = contentLoadingProgressBar;
    }

    @Override
    public final void run() {
        ContentLoadingProgressBar.a(this.c);
    }
}

