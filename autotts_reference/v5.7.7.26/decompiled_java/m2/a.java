/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 */
package m2;

import android.animation.Animator;

public class a {
    public Animator a;

    public void a() {
        Animator animator = this.a;
        if (animator != null) {
            animator.cancel();
        }
    }

    public void b() {
        this.a = null;
    }

    public void c(Animator animator) {
        this.a();
        this.a = animator;
    }
}

