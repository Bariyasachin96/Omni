/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapShader
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.AnimationDrawable
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.ClipDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.RoundRectShape
 *  android.graphics.drawable.shapes.Shape
 *  android.util.AttributeSet
 *  android.widget.ProgressBar
 */
package androidx.appcompat.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.appcompat.widget.m0;
import h0.b;

public class l {
    public static final int[] c = new int[]{16843067, 16843068};
    public final ProgressBar a;
    public Bitmap b;

    public l(ProgressBar progressBar) {
        this.a = progressBar;
    }

    public final Shape a() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    public Bitmap b() {
        return this.b;
    }

    public void c(AttributeSet object, int n3) {
        object = m0.v(this.a.getContext(), (AttributeSet)object, c, n3, 0);
        Drawable drawable = ((m0)object).h(0);
        if (drawable != null) {
            this.a.setIndeterminateDrawable(this.e(drawable));
        }
        if ((drawable = ((m0)object).h(1)) != null) {
            this.a.setProgressDrawable(this.d(drawable, false));
        }
        ((m0)object).x();
    }

    public Drawable d(Drawable drawable, boolean bl) {
        if (drawable instanceof b) {
            b b3 = (b)drawable;
            Drawable drawable2 = b3.b();
            if (drawable2 != null) {
                b3.a(this.d(drawable2, bl));
                return drawable;
            }
        } else {
            if (drawable instanceof LayerDrawable) {
                int n3;
                drawable = (LayerDrawable)drawable;
                int n4 = drawable.getNumberOfLayers();
                LayerDrawable layerDrawable = new Drawable[n4];
                int n5 = 0;
                for (n3 = 0; n3 < n4; ++n3) {
                    int n6 = drawable.getId(n3);
                    Drawable drawable3 = drawable.getDrawable(n3);
                    bl = n6 == 16908301 || n6 == 16908303;
                    layerDrawable[n3] = this.d(drawable3, bl);
                }
                layerDrawable = new LayerDrawable((Drawable[])layerDrawable);
                for (n3 = n5; n3 < n4; ++n3) {
                    layerDrawable.setId(n3, drawable.getId(n3));
                    androidx.appcompat.widget.l$a.a((LayerDrawable)drawable, layerDrawable, n3);
                }
                return layerDrawable;
            }
            if (drawable instanceof BitmapDrawable) {
                drawable = (BitmapDrawable)drawable;
                Bitmap bitmap = drawable.getBitmap();
                if (this.b == null) {
                    this.b = bitmap;
                }
                ShapeDrawable shapeDrawable = new ShapeDrawable(this.a());
                bitmap = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
                shapeDrawable.getPaint().setShader((Shader)bitmap);
                shapeDrawable.getPaint().setColorFilter(drawable.getPaint().getColorFilter());
                if (bl) {
                    return new ClipDrawable((Drawable)shapeDrawable, 3, 1);
                }
                return shapeDrawable;
            }
        }
        return drawable;
    }

    public final Drawable e(Drawable drawable) {
        if (drawable instanceof AnimationDrawable) {
            drawable = (AnimationDrawable)drawable;
            int n3 = drawable.getNumberOfFrames();
            AnimationDrawable animationDrawable = new AnimationDrawable();
            animationDrawable.setOneShot(drawable.isOneShot());
            for (int i3 = 0; i3 < n3; ++i3) {
                Drawable drawable2 = this.d(drawable.getFrame(i3), true);
                drawable2.setLevel(10000);
                animationDrawable.addFrame(drawable2, drawable.getDuration(i3));
            }
            animationDrawable.setLevel(10000);
            return animationDrawable;
        }
        return drawable;
    }

    public static abstract class a {
        public static void a(LayerDrawable layerDrawable, LayerDrawable layerDrawable2, int n3) {
            layerDrawable2.setLayerGravity(n3, layerDrawable.getLayerGravity(n3));
            layerDrawable2.setLayerWidth(n3, layerDrawable.getLayerWidth(n3));
            layerDrawable2.setLayerHeight(n3, layerDrawable.getLayerHeight(n3));
            layerDrawable2.setLayerInsetLeft(n3, layerDrawable.getLayerInsetLeft(n3));
            layerDrawable2.setLayerInsetRight(n3, layerDrawable.getLayerInsetRight(n3));
            layerDrawable2.setLayerInsetTop(n3, layerDrawable.getLayerInsetTop(n3));
            layerDrawable2.setLayerInsetBottom(n3, layerDrawable.getLayerInsetBottom(n3));
            layerDrawable2.setLayerInsetStart(n3, layerDrawable.getLayerInsetStart(n3));
            layerDrawable2.setLayerInsetEnd(n3, layerDrawable.getLayerInsetEnd(n3));
        }
    }
}

