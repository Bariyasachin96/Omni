/*
 * Decompiled with CFR 0.152.
 */
package androidx.core.widget;

import androidx.core.widget.ContentLoadingProgressBar;

public final class d
implements Runnable {
    public final ContentLoadingProgressBar c;

    public /* synthetic */ d(ContentLoadingProgressBar contentLoadingProgressBar) {
        this.c = contentLoadingProgressBar;
    }

    @Override
    public final void run() {
        ContentLoadingProgressBar.b(this.c);
    }
}

