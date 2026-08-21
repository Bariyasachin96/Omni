/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ProgressBar
 */
package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.core.widget.d;
import androidx.core.widget.e;

public class ContentLoadingProgressBar
extends ProgressBar {
    public long c = -1L;
    public boolean d = false;
    public boolean e = false;
    public boolean f = false;
    public final Runnable g = new d(this);
    public final Runnable h = new e(this);

    public ContentLoadingProgressBar(Context context) {
        this(context, null);
    }

    public ContentLoadingProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public static /* synthetic */ void a(ContentLoadingProgressBar contentLoadingProgressBar) {
        contentLoadingProgressBar.e = false;
        if (!contentLoadingProgressBar.f) {
            contentLoadingProgressBar.c = System.currentTimeMillis();
            contentLoadingProgressBar.setVisibility(0);
        }
    }

    public static /* synthetic */ void b(ContentLoadingProgressBar contentLoadingProgressBar) {
        contentLoadingProgressBar.d = false;
        contentLoadingProgressBar.c = -1L;
        contentLoadingProgressBar.setVisibility(8);
    }

    public final void c() {
        this.removeCallbacks(this.g);
        this.removeCallbacks(this.h);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.c();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.c();
    }
}

