/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.Animator$AnimatorPauseListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Matrix
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.b;
import f0.k;
import m1.b0;
import m1.i;
import m1.m;
import m1.n;
import m1.r;
import m1.y;
import o0.x0;
import org.xmlpull.v1.XmlPullParser;

public class ChangeTransform
extends Transition {
    public static final String[] S = new String[]{"android:changeTransform:matrix", "android:changeTransform:transforms", "android:changeTransform:parentMatrix"};
    public static final Property T = new Property(float[].class, "nonTranslations"){

        public float[] a(e e3) {
            return null;
        }

        public void b(e e3, float[] fArray) {
            e3.d(fArray);
        }
    };
    public static final Property U = new Property(PointF.class, "translations"){

        public PointF a(e e3) {
            return null;
        }

        public void b(e e3, PointF pointF) {
            e3.c(pointF);
        }
    };
    public static final boolean V = true;
    public boolean P = true;
    public boolean Q = true;
    public Matrix R = new Matrix();

    public ChangeTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, m1.r.g);
        attributeSet = (XmlPullParser)attributeSet;
        this.P = f0.k.a((TypedArray)context, (XmlPullParser)attributeSet, "reparentWithOverlay", 1, true);
        this.Q = f0.k.a((TypedArray)context, (XmlPullParser)attributeSet, "reparent", 0, true);
        context.recycle();
    }

    private void o0(y y3) {
        View view = y3.b;
        if (view.getVisibility() != 8) {
            y3.a.put("android:changeTransform:parent", view.getParent());
            f f3 = new f(view);
            y3.a.put("android:changeTransform:transforms", f3);
            f3 = view.getMatrix();
            f3 = f3 != null && !f3.isIdentity() ? new Matrix((Matrix)f3) : null;
            y3.a.put("android:changeTransform:matrix", f3);
            if (this.Q) {
                Matrix matrix = new Matrix();
                f3 = (ViewGroup)view.getParent();
                b0.h((View)f3, matrix);
                matrix.preTranslate((float)(-f3.getScrollX()), (float)(-f3.getScrollY()));
                y3.a.put("android:changeTransform:parentMatrix", matrix);
                y3.a.put("android:changeTransform:intermediateMatrix", view.getTag(m1.n.transition_transform));
                y3.a.put("android:changeTransform:intermediateParentMatrix", view.getTag(m1.n.parent_matrix));
            }
        }
    }

    public static void s0(View view) {
        ChangeTransform.u0(view, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
    }

    public static void u0(View view, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        view.setTranslationX(f3);
        view.setTranslationY(f4);
        x0.x0(view, f5);
        view.setScaleX(f6);
        view.setScaleY(f7);
        view.setRotationX(f8);
        view.setRotationY(f9);
        view.setRotation(f10);
    }

    @Override
    public String[] K() {
        return S;
    }

    @Override
    public void h(y y3) {
        this.o0(y3);
    }

    @Override
    public void k(y y3) {
        this.o0(y3);
        if (!V) {
            ((ViewGroup)y3.b.getParent()).startViewTransition(y3.b);
        }
    }

    @Override
    public Animator o(ViewGroup viewGroup, y y3, y y4) {
        if (y3 != null && y4 != null && y3.a.containsKey("android:changeTransform:parent") && y4.a.containsKey("android:changeTransform:parent")) {
            ViewGroup viewGroup2 = (ViewGroup)y3.a.get("android:changeTransform:parent");
            ViewGroup viewGroup3 = (ViewGroup)y4.a.get("android:changeTransform:parent");
            boolean bl = this.Q && !this.r0(viewGroup2, viewGroup3);
            viewGroup3 = (Matrix)y3.a.get("android:changeTransform:intermediateMatrix");
            if (viewGroup3 != null) {
                y3.a.put("android:changeTransform:matrix", viewGroup3);
            }
            if ((viewGroup3 = (Matrix)y3.a.get("android:changeTransform:intermediateParentMatrix")) != null) {
                y3.a.put("android:changeTransform:parentMatrix", viewGroup3);
            }
            if (bl) {
                this.t0(y3, y4);
            }
            viewGroup3 = this.q0(y3, y4, bl);
            if (bl && viewGroup3 != null && this.P) {
                this.p0(viewGroup, y3, y4);
                return viewGroup3;
            }
            if (!V) {
                viewGroup2.endViewTransition(y3.b);
            }
            return viewGroup3;
        }
        return null;
    }

    public final void p0(ViewGroup object, y y3, y y4) {
        View view = y4.b;
        Object object2 = new Matrix((Matrix)y4.a.get("android:changeTransform:parentMatrix"));
        b0.i((View)object, object2);
        m1.e e3 = m1.i.a(view, (ViewGroup)object, object2);
        if (e3 != null) {
            e3.a((ViewGroup)y3.a.get("android:changeTransform:parent"), y3.b);
            object = this;
            while ((object2 = ((Transition)object).t) != null) {
                object = object2;
            }
            ((Transition)object).a(new c(view, e3));
            if (V) {
                object = y3.b;
                if (object != y4.b) {
                    b0.f((View)object, 0.0f);
                }
                b0.f(view, 1.0f);
            }
        }
    }

    public final ObjectAnimator q0(y object, y y3, boolean bl) {
        object = (Matrix)object.a.get("android:changeTransform:matrix");
        Object object2 = (Matrix)y3.a.get("android:changeTransform:matrix");
        Object object3 = object;
        if (object == null) {
            object3 = m1.k.a;
        }
        object = object2;
        if (object2 == null) {
            object = m1.k.a;
        }
        if (object3.equals(object)) {
            return null;
        }
        object2 = (f)y3.a.get("android:changeTransform:transforms");
        y3 = y3.b;
        ChangeTransform.s0((View)y3);
        Object object4 = new float[9];
        object3.getValues(object4);
        float[] fArray = new float[9];
        object.getValues(fArray);
        object3 = new e((View)y3, (float[])object4);
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofObject((Property)T, (TypeEvaluator)new m1.c(new float[9]), (Object[])new float[][]{object4, fArray});
        object4 = this.B().a(object4[2], object4[5], fArray[2], fArray[5]);
        propertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder((Object)object3, (PropertyValuesHolder[])new PropertyValuesHolder[]{propertyValuesHolder, m1.m.a(U, (Path)object4)});
        object = new d((View)y3, (f)object2, (e)object3, (Matrix)object, bl, this.P);
        propertyValuesHolder.addListener((Animator.AnimatorListener)object);
        propertyValuesHolder.addPauseListener((Animator.AnimatorPauseListener)object);
        return propertyValuesHolder;
    }

    public final boolean r0(ViewGroup object, ViewGroup viewGroup) {
        if (this.O((View)object) && this.O((View)viewGroup)) {
            return (object = this.z((View)object, true)) != null && viewGroup == object.b;
        }
        return object == viewGroup;
    }

    public final void t0(y y3, y y4) {
        Matrix matrix = (Matrix)y4.a.get("android:changeTransform:parentMatrix");
        y4.b.setTag(m1.n.parent_matrix, (Object)matrix);
        Matrix matrix2 = this.R;
        matrix2.reset();
        matrix.invert(matrix2);
        matrix = (Matrix)y3.a.get("android:changeTransform:matrix");
        y4 = matrix;
        if (matrix == null) {
            y4 = new Matrix();
            y3.a.put("android:changeTransform:matrix", y4);
        }
        y4.postConcat((Matrix)y3.a.get("android:changeTransform:parentMatrix"));
        y4.postConcat(matrix2);
    }

    public static class c
    extends b {
        public View a;
        public m1.e b;

        public c(View view, m1.e e3) {
            this.a = view;
            this.b = e3;
        }

        @Override
        public void d(Transition transition) {
            this.b.setVisibility(4);
        }

        @Override
        public void e(Transition transition) {
            this.b.setVisibility(0);
        }

        @Override
        public void g(Transition transition) {
            transition.a0(this);
            m1.i.b(this.a);
            this.a.setTag(m1.n.transition_transform, null);
            this.a.setTag(m1.n.parent_matrix, null);
        }
    }

    public static class d
    extends AnimatorListenerAdapter {
        public boolean a;
        public final Matrix b = new Matrix();
        public final boolean c;
        public final boolean d;
        public final View e;
        public final f f;
        public final e g;
        public final Matrix h;

        public d(View view, f f3, e e3, Matrix matrix, boolean bl, boolean bl2) {
            this.c = bl;
            this.d = bl2;
            this.e = view;
            this.f = f3;
            this.g = e3;
            this.h = matrix;
        }

        public final void a(Matrix matrix) {
            this.b.set(matrix);
            this.e.setTag(m1.n.transition_transform, (Object)this.b);
            this.f.a(this.e);
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.a) {
                if (this.c && this.d) {
                    this.a(this.h);
                } else {
                    this.e.setTag(m1.n.transition_transform, null);
                    this.e.setTag(m1.n.parent_matrix, null);
                }
            }
            b0.d(this.e, null);
            this.f.a(this.e);
        }

        public void onAnimationPause(Animator animator) {
            this.a(this.g.a());
        }

        public void onAnimationResume(Animator animator) {
            ChangeTransform.s0(this.e);
        }
    }

    public static class e {
        public final Matrix a = new Matrix();
        public final View b;
        public final float[] c;
        public float d;
        public float e;

        public e(View object, float[] fArray) {
            this.b = object;
            object = (float[])fArray.clone();
            this.c = (float[])object;
            this.d = (float)object[2];
            this.e = (float)object[5];
            this.b();
        }

        public Matrix a() {
            return this.a;
        }

        public final void b() {
            float[] fArray = this.c;
            fArray[2] = this.d;
            fArray[5] = this.e;
            this.a.setValues(fArray);
            b0.d(this.b, this.a);
        }

        public void c(PointF pointF) {
            this.d = pointF.x;
            this.e = pointF.y;
            this.b();
        }

        public void d(float[] fArray) {
            System.arraycopy(fArray, 0, this.c, 0, fArray.length);
            this.b();
        }
    }

    public static class f {
        public final float a;
        public final float b;
        public final float c;
        public final float d;
        public final float e;
        public final float f;
        public final float g;
        public final float h;

        public f(View view) {
            this.a = view.getTranslationX();
            this.b = view.getTranslationY();
            this.c = x0.G(view);
            this.d = view.getScaleX();
            this.e = view.getScaleY();
            this.f = view.getRotationX();
            this.g = view.getRotationY();
            this.h = view.getRotation();
        }

        public void a(View view) {
            ChangeTransform.u0(view, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
        }

        public boolean equals(Object object) {
            if (!(object instanceof f)) {
                return false;
            }
            object = (f)object;
            return ((f)object).a == this.a && ((f)object).b == this.b && ((f)object).c == this.c && ((f)object).d == this.d && ((f)object).e == this.e && ((f)object).f == this.f && ((f)object).g == this.g && ((f)object).h == this.h;
        }

        public int hashCode() {
            float f3 = this.a;
            int n3 = 0;
            int n4 = f3 != 0.0f ? Float.floatToIntBits(f3) : 0;
            f3 = this.b;
            int n5 = f3 != 0.0f ? Float.floatToIntBits(f3) : 0;
            f3 = this.c;
            int n6 = f3 != 0.0f ? Float.floatToIntBits(f3) : 0;
            f3 = this.d;
            int n7 = f3 != 0.0f ? Float.floatToIntBits(f3) : 0;
            f3 = this.e;
            int n8 = f3 != 0.0f ? Float.floatToIntBits(f3) : 0;
            f3 = this.f;
            int n9 = f3 != 0.0f ? Float.floatToIntBits(f3) : 0;
            f3 = this.g;
            int n10 = f3 != 0.0f ? Float.floatToIntBits(f3) : 0;
            f3 = this.h;
            if (f3 != 0.0f) {
                n3 = Float.floatToIntBits(f3);
            }
            return ((((((n4 * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n3;
        }
    }
}

