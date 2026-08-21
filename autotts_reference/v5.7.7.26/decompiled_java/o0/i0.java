/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package o0;

import android.view.View;
import android.view.ViewTreeObserver;

public final class i0
implements ViewTreeObserver.OnPreDrawListener,
View.OnAttachStateChangeListener {
    public final View c;
    public ViewTreeObserver d;
    public final Runnable e;

    public i0(View view, Runnable runnable) {
        this.c = view;
        this.d = view.getViewTreeObserver();
        this.e = runnable;
    }

    public static i0 a(View view, Runnable object) {
        if (view != null) {
            if (object != null) {
                object = new i0(view, (Runnable)object);
                view.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)object);
                view.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)object);
                return object;
            }
            throw new NullPointerException("runnable == null");
        }
        throw new NullPointerException("view == null");
    }

    public void b() {
        if (this.d.isAlive()) {
            this.d.removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
        } else {
            this.c.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
        }
        this.c.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
    }

    public boolean onPreDraw() {
        this.b();
        this.e.run();
        return true;
    }

    public void onViewAttachedToWindow(View view) {
        this.d = view.getViewTreeObserver();
    }

    public void onViewDetachedFromWindow(View view) {
        this.b();
    }
}

