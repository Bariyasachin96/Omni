/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Animatable2$AnimationCallback
 *  android.graphics.drawable.Drawable
 */
package n1;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;

public abstract class b {
    public Animatable2.AnimationCallback a;

    public Animatable2.AnimationCallback a() {
        if (this.a == null) {
            this.a = new Animatable2.AnimationCallback(this){
                public final b a;
                {
                    this.a = b3;
                }

                public void onAnimationEnd(Drawable drawable) {
                    this.a.b(drawable);
                }

                public void onAnimationStart(Drawable drawable) {
                    this.a.c(drawable);
                }
            };
        }
        return this.a;
    }

    public void b(Drawable drawable) {
    }

    public void c(Drawable drawable) {
    }
}

