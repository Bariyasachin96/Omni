/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.Animator$AnimatorPauseListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.ViewGroup
 *  android.widget.ImageView
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.transition.Transition;
import androidx.transition.d;
import java.util.Map;
import m1.j;
import m1.k;
import m1.n;
import m1.y;

public class ChangeImageTransform
extends Transition {
    public static final String[] P = new String[]{"android:changeImageTransform:matrix", "android:changeImageTransform:bounds"};
    public static final TypeEvaluator Q = new TypeEvaluator(){

        public Matrix a(float f3, Matrix matrix, Matrix matrix2) {
            return null;
        }
    };
    public static final Property R = new Property(Matrix.class, "animatedTransform"){

        public Matrix a(ImageView imageView) {
            return null;
        }

        public void b(ImageView imageView, Matrix matrix) {
            m1.j.a(imageView, matrix);
        }
    };

    public ChangeImageTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void o0(y y3, boolean bl) {
        ImageView imageView;
        Object object = y3.b;
        if (object instanceof ImageView && object.getVisibility() == 0 && (imageView = (ImageView)object).getDrawable() != null) {
            Map map = y3.a;
            map.put("android:changeImageTransform:bounds", new Rect(object.getLeft(), object.getTop(), object.getRight(), object.getBottom()));
            y3 = bl ? (Matrix)imageView.getTag(m1.n.transition_image_transform) : null;
            object = y3;
            if (y3 == null) {
                object = ChangeImageTransform.q0(imageView);
            }
            map.put("android:changeImageTransform:matrix", object);
        }
    }

    public static Matrix p0(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        int n3 = drawable.getIntrinsicWidth();
        float f3 = imageView.getWidth();
        float f4 = n3;
        float f5 = f3 / f4;
        n3 = drawable.getIntrinsicHeight();
        float f6 = imageView.getHeight();
        float f7 = n3;
        f5 = Math.max(f5, f6 / f7);
        int n4 = Math.round((f3 - f4 * f5) / 2.0f);
        n3 = Math.round((f6 - f7 * f5) / 2.0f);
        imageView = new Matrix();
        imageView.postScale(f5, f5);
        imageView.postTranslate((float)n4, (float)n3);
        return imageView;
    }

    public static Matrix q0(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0) {
            int n3 = androidx.transition.ChangeImageTransform$c.a[imageView.getScaleType().ordinal()];
            if (n3 != 1) {
                if (n3 != 2) {
                    return new Matrix(imageView.getImageMatrix());
                }
                return ChangeImageTransform.p0(imageView);
            }
            return ChangeImageTransform.t0(imageView);
        }
        return new Matrix(imageView.getImageMatrix());
    }

    public static Matrix t0(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Matrix matrix = new Matrix();
        matrix.postScale((float)imageView.getWidth() / (float)drawable.getIntrinsicWidth(), (float)imageView.getHeight() / (float)drawable.getIntrinsicHeight());
        return matrix;
    }

    @Override
    public String[] K() {
        return P;
    }

    @Override
    public void h(y y3) {
        this.o0(y3, false);
    }

    @Override
    public void k(y y3) {
        this.o0(y3, true);
    }

    @Override
    public Animator o(ViewGroup object, y y3, y y4) {
        Matrix matrix = null;
        object = matrix;
        if (y3 != null) {
            if (y4 == null) {
                object = matrix;
            } else {
                Rect rect = (Rect)y3.a.get("android:changeImageTransform:bounds");
                Rect rect2 = (Rect)y4.a.get("android:changeImageTransform:bounds");
                object = matrix;
                if (rect != null) {
                    if (rect2 == null) {
                        object = matrix;
                    } else {
                        y3 = (Matrix)y3.a.get("android:changeImageTransform:matrix");
                        matrix = (Matrix)y4.a.get("android:changeImageTransform:matrix");
                        int n3 = y3 == null && matrix == null || y3 != null && y3.equals(matrix) ? 1 : 0;
                        if (rect.equals((Object)rect2) && n3 != 0) {
                            return null;
                        }
                        y4 = (ImageView)y4.b;
                        object = y4.getDrawable();
                        int n4 = object.getIntrinsicWidth();
                        n3 = object.getIntrinsicHeight();
                        if (n4 > 0 && n3 > 0) {
                            object = y3;
                            if (y3 == null) {
                                object = m1.k.a;
                            }
                            y3 = matrix;
                            if (matrix == null) {
                                y3 = m1.k.a;
                            }
                            R.set((Object)y4, object);
                            matrix = this.r0((ImageView)y4, (Matrix)object, (Matrix)y3);
                            object = new d((ImageView)y4, (Matrix)object, (Matrix)y3);
                            matrix.addListener((Animator.AnimatorListener)object);
                            matrix.addPauseListener((Animator.AnimatorPauseListener)object);
                            this.a((Transition.g)object);
                            return matrix;
                        }
                        object = this.s0((ImageView)y4);
                    }
                }
            }
        }
        return object;
    }

    public final ObjectAnimator r0(ImageView imageView, Matrix matrix, Matrix matrix2) {
        return ObjectAnimator.ofObject((Object)imageView, (Property)R, (TypeEvaluator)new d.b(), (Object[])new Matrix[]{matrix, matrix2});
    }

    public final ObjectAnimator s0(ImageView imageView) {
        Property property = R;
        TypeEvaluator typeEvaluator = Q;
        Matrix matrix = m1.k.a;
        return ObjectAnimator.ofObject((Object)imageView, (Property)property, (TypeEvaluator)typeEvaluator, (Object[])new Matrix[]{matrix, matrix});
    }

    public static class d
    extends AnimatorListenerAdapter
    implements Transition.g {
        public final ImageView a;
        public final Matrix b;
        public final Matrix c;
        public boolean d = true;

        public d(ImageView imageView, Matrix matrix, Matrix matrix2) {
            this.a = imageView;
            this.b = matrix;
            this.c = matrix2;
        }

        @Override
        public void a(Transition transition) {
        }

        @Override
        public void b(Transition transition) {
        }

        @Override
        public void d(Transition transition) {
            if (this.d) {
                this.i(this.b);
            }
        }

        @Override
        public void e(Transition transition) {
            this.h();
        }

        @Override
        public void g(Transition transition) {
        }

        public final void h() {
            ImageView imageView = this.a;
            int n3 = m1.n.transition_image_transform;
            if ((imageView = (Matrix)imageView.getTag(n3)) != null) {
                m1.j.a(this.a, (Matrix)imageView);
                this.a.setTag(n3, null);
            }
        }

        public final void i(Matrix matrix) {
            this.a.setTag(m1.n.transition_image_transform, (Object)matrix);
            m1.j.a(this.a, this.c);
        }

        public void onAnimationEnd(Animator animator) {
            this.d = false;
        }

        public void onAnimationEnd(Animator animator, boolean bl) {
            this.d = bl;
        }

        public void onAnimationPause(Animator animator) {
            this.i((Matrix)((ObjectAnimator)animator).getAnimatedValue());
        }

        public void onAnimationResume(Animator animator) {
            this.h();
        }

        public void onAnimationStart(Animator animator) {
            this.d = false;
        }

        public void onAnimationStart(Animator animator, boolean bl) {
            this.d = false;
        }
    }
}

