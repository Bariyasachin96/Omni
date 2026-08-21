/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.content.Context
 *  android.content.res.Resources$NotFoundException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.AnimationSet
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Transformation
 */
package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import androidx.fragment.app.Fragment;
import o0.i0;

public abstract class h {
    public static int a(Fragment fragment, boolean bl, boolean bl2) {
        if (bl2) {
            if (bl) {
                return fragment.G();
            }
            return fragment.H();
        }
        if (bl) {
            return fragment.q();
        }
        return fragment.v();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static a b(Context context, Fragment object, boolean bl, boolean bl2) {
        int n3;
        int n4 = ((Fragment)object).C();
        int n5 = h.a((Fragment)object, bl, bl2);
        ((Fragment)object).s1(0, 0, 0, 0);
        ViewGroup viewGroup = ((Fragment)object).J;
        if (viewGroup != null && viewGroup.getTag(n3 = a1.b.visible_removing_fragment_view_tag) != null) {
            ((Fragment)object).J.setTag(n3, null);
        }
        if ((viewGroup = ((Fragment)object).J) != null && viewGroup.getLayoutTransition() != null) {
            return null;
        }
        viewGroup = ((Fragment)object).l0(n4, bl, n5);
        if (viewGroup != null) {
            return new a((Animation)viewGroup);
        }
        if ((object = ((Fragment)object).m0(n4, bl, n5)) != null) {
            return new a((Animator)object);
        }
        n3 = n5;
        if (n5 == 0) {
            n3 = n5;
            if (n4 != 0) {
                n3 = h.d(context, n4, bl);
            }
        }
        if (n3 == 0) return null;
        bl = "anim".equals(context.getResources().getResourceTypeName(n3));
        if (bl) {
            try {
                object = AnimationUtils.loadAnimation((Context)context, (int)n3);
                if (object == null) return null;
                return new a((Animation)object);
            }
            catch (Resources.NotFoundException notFoundException) {
                throw notFoundException;
            }
            catch (RuntimeException runtimeException) {}
        }
        try {
            object = AnimatorInflater.loadAnimator((Context)context, (int)n3);
            if (object == null) return null;
            return new a((Animator)object);
        }
        catch (RuntimeException runtimeException) {
            if (bl) {
                throw runtimeException;
            }
            if ((context = AnimationUtils.loadAnimation((Context)context, (int)n3)) == null) return null;
            return new a((Animation)context);
        }
    }

    public static int c(Context context, int n3) {
        context = context.obtainStyledAttributes(0x1030001, new int[]{n3});
        n3 = context.getResourceId(0, -1);
        context.recycle();
        return n3;
    }

    public static int d(Context context, int n3, boolean bl) {
        if (n3 != 4097) {
            if (n3 != 8194) {
                if (n3 != 8197) {
                    if (n3 != 4099) {
                        if (n3 != 4100) {
                            return -1;
                        }
                        if (bl) {
                            return h.c(context, 16842936);
                        }
                        return h.c(context, 16842937);
                    }
                    if (bl) {
                        return a1.a.fragment_fade_enter;
                    }
                    return a1.a.fragment_fade_exit;
                }
                if (bl) {
                    return h.c(context, 16842938);
                }
                return h.c(context, 0x10100BB);
            }
            if (bl) {
                return a1.a.fragment_close_enter;
            }
            return a1.a.fragment_close_exit;
        }
        if (bl) {
            return a1.a.fragment_open_enter;
        }
        return a1.a.fragment_open_exit;
    }

    public static class a {
        public final Animation a;
        public final Animator b;

        public a(Animator animator) {
            this.a = null;
            this.b = animator;
            if (animator != null) {
                return;
            }
            throw new IllegalStateException("Animator cannot be null");
        }

        public a(Animation animation) {
            this.a = animation;
            this.b = null;
            if (animation != null) {
                return;
            }
            throw new IllegalStateException("Animation cannot be null");
        }
    }

    public static class b
    extends AnimationSet
    implements Runnable {
        public final ViewGroup c;
        public final View d;
        public boolean e;
        public boolean f;
        public boolean g = true;

        public b(Animation animation, ViewGroup viewGroup, View view) {
            super(false);
            this.c = viewGroup;
            this.d = view;
            this.addAnimation(animation);
            viewGroup.post((Runnable)this);
        }

        public boolean getTransformation(long l3, Transformation transformation) {
            this.g = true;
            if (this.e) {
                return this.f ^ true;
            }
            if (!super.getTransformation(l3, transformation)) {
                this.e = true;
                i0.a((View)this.c, this);
            }
            return true;
        }

        public boolean getTransformation(long l3, Transformation transformation, float f3) {
            this.g = true;
            if (this.e) {
                return this.f ^ true;
            }
            if (!super.getTransformation(l3, transformation, f3)) {
                this.e = true;
                i0.a((View)this.c, this);
            }
            return true;
        }

        @Override
        public void run() {
            if (!this.e && this.g) {
                this.g = false;
                this.c.post((Runnable)this);
                return;
            }
            this.c.endViewTransition(this.d);
            this.f = true;
        }
    }
}

