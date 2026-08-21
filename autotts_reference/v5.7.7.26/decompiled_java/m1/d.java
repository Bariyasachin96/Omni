/*
 * Decompiled with CFR 0.152.
 */
package m1;

import androidx.transition.Transition;
import androidx.transition.a;
import k0.a;

public final class d
implements a.a {
    public final Runnable a;
    public final Transition b;
    public final Runnable c;

    public /* synthetic */ d(Runnable runnable, Transition transition, Runnable runnable2) {
        this.a = runnable;
        this.b = transition;
        this.c = runnable2;
    }

    @Override
    public final void onCancel() {
        androidx.transition.a.v(this.a, this.b, this.c);
    }
}

