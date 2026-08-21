/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorSet
 *  android.animation.TypeEvaluator
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Picture
 *  android.graphics.RectF
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import m1.b0;

public abstract class d {
    public static final boolean a;

    static {
        boolean bl = Build.VERSION.SDK_INT >= 28;
        a = bl;
    }

    public static View a(ViewGroup viewGroup, View view, View view2) {
        Matrix matrix = new Matrix();
        matrix.setTranslate((float)(-view2.getScrollX()), (float)(-view2.getScrollY()));
        b0.h(view, matrix);
        b0.i((View)viewGroup, matrix);
        RectF rectF = new RectF(0.0f, 0.0f, (float)view.getWidth(), (float)view.getHeight());
        matrix.mapRect(rectF);
        int n3 = Math.round(rectF.left);
        int n4 = Math.round(rectF.top);
        int n5 = Math.round(rectF.right);
        int n6 = Math.round(rectF.bottom);
        view2 = new ImageView(view.getContext());
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewGroup = d.b(view, matrix, rectF, viewGroup);
        if (viewGroup != null) {
            view2.setImageBitmap((Bitmap)viewGroup);
        }
        view2.measure(View.MeasureSpec.makeMeasureSpec((int)(n5 - n3), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)(n6 - n4), (int)0x40000000));
        view2.layout(n3, n4, n5, n6);
        return view2;
    }

    public static Bitmap b(View view, Matrix matrix, RectF rectF, ViewGroup viewGroup) {
        ViewGroup viewGroup2;
        boolean bl = view.isAttachedToWindow();
        int n3 = viewGroup != null && viewGroup.isAttachedToWindow() ? 1 : 0;
        Bitmap bitmap = null;
        if (!bl) {
            if (n3 == 0) {
                return null;
            }
            viewGroup2 = (ViewGroup)view.getParent();
            n3 = viewGroup2.indexOfChild(view);
            viewGroup.getOverlay().add(view);
        } else {
            n3 = 0;
            viewGroup2 = null;
        }
        int n4 = Math.round(rectF.width());
        int n5 = Math.round(rectF.height());
        Bitmap bitmap2 = bitmap;
        if (n4 > 0) {
            bitmap2 = bitmap;
            if (n5 > 0) {
                float f3 = Math.min(1.0f, 1048576.0f / (float)(n4 * n5));
                n4 = Math.round((float)n4 * f3);
                n5 = Math.round((float)n5 * f3);
                matrix.postTranslate(-rectF.left, -rectF.top);
                matrix.postScale(f3, f3);
                if (a) {
                    rectF = new Picture();
                    bitmap2 = rectF.beginRecording(n4, n5);
                    bitmap2.concat(matrix);
                    view.draw((Canvas)bitmap2);
                    rectF.endRecording();
                    bitmap2 = androidx.transition.d$a.a((Picture)rectF);
                } else {
                    bitmap2 = Bitmap.createBitmap((int)n4, (int)n5, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                    rectF = new Canvas(bitmap2);
                    rectF.concat(matrix);
                    view.draw((Canvas)rectF);
                }
            }
        }
        if (!bl) {
            viewGroup.getOverlay().remove(view);
            viewGroup2.addView(view, n3);
        }
        return bitmap2;
    }

    public static Animator c(Animator animator, Animator animator2) {
        if (animator == null) {
            return animator2;
        }
        if (animator2 == null) {
            return animator;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{animator, animator2});
        return animatorSet;
    }

    public static abstract class a {
        public static Bitmap a(Picture picture) {
            return Bitmap.createBitmap((Picture)picture);
        }
    }

    public static class b
    implements TypeEvaluator {
        public final float[] a = new float[9];
        public final float[] b = new float[9];
        public final Matrix c = new Matrix();

        public Matrix a(float f3, Matrix object, Matrix matrix) {
            object.getValues(this.a);
            matrix.getValues(this.b);
            for (int i3 = 0; i3 < 9; ++i3) {
                object = this.b;
                Matrix matrix2 = object[i3];
                float f4 = this.a[i3];
                object[i3] = (Matrix)(f4 + (matrix2 - f4) * f3);
            }
            this.c.setValues(this.b);
            return this.c;
        }
    }
}

