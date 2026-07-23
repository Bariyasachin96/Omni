/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.ColorMatrix
 *  android.graphics.ColorMatrixColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Outline
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewOutlineProvider
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import d.a;
import y.d;

public class ImageFilterView
extends AppCompatImageView {
    public c f = new c();
    public boolean g = true;
    public Drawable h = null;
    public Drawable i = null;
    public float j = 0.0f;
    public float k = 0.0f;
    public float l = Float.NaN;
    public Path m;
    public ViewOutlineProvider n;
    public RectF o;
    public Drawable[] p = new Drawable[2];
    public LayerDrawable q;
    public float r = Float.NaN;
    public float s = Float.NaN;
    public float t = Float.NaN;
    public float u = Float.NaN;

    public ImageFilterView(Context context) {
        super(context);
        this.e(context, null);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e(context, attributeSet);
    }

    public ImageFilterView(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.e(context, attributeSet);
    }

    private void e(Context layerDrawable, AttributeSet attributeSet) {
        if (attributeSet != null) {
            layerDrawable = layerDrawable.obtainStyledAttributes(attributeSet, y.d.ImageFilterView);
            int n3 = layerDrawable.getIndexCount();
            this.h = layerDrawable.getDrawable(y.d.ImageFilterView_altSrc);
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = layerDrawable.getIndex(i3);
                if (n4 == y.d.ImageFilterView_crossfade) {
                    this.j = layerDrawable.getFloat(n4, 0.0f);
                    continue;
                }
                if (n4 == y.d.ImageFilterView_warmth) {
                    this.setWarmth(layerDrawable.getFloat(n4, 0.0f));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_saturation) {
                    this.setSaturation(layerDrawable.getFloat(n4, 0.0f));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_contrast) {
                    this.setContrast(layerDrawable.getFloat(n4, 0.0f));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_brightness) {
                    this.setBrightness(layerDrawable.getFloat(n4, 0.0f));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_round) {
                    this.setRound(layerDrawable.getDimension(n4, 0.0f));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_roundPercent) {
                    this.setRoundPercent(layerDrawable.getFloat(n4, 0.0f));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_overlay) {
                    this.setOverlay(layerDrawable.getBoolean(n4, this.g));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_imagePanX) {
                    this.setImagePanX(layerDrawable.getFloat(n4, this.r));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_imagePanY) {
                    this.setImagePanY(layerDrawable.getFloat(n4, this.s));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_imageRotate) {
                    this.setImageRotate(layerDrawable.getFloat(n4, this.u));
                    continue;
                }
                if (n4 != y.d.ImageFilterView_imageZoom) continue;
                this.setImageZoom(layerDrawable.getFloat(n4, this.t));
            }
            layerDrawable.recycle();
            layerDrawable = this.getDrawable();
            this.i = layerDrawable;
            if (this.h != null && layerDrawable != null) {
                layerDrawable = this.p;
                attributeSet = this.getDrawable().mutate();
                this.i = attributeSet;
                layerDrawable[0] = attributeSet;
                this.p[1] = this.h.mutate();
                this.q = layerDrawable = new LayerDrawable(this.p);
                layerDrawable.getDrawable(1).setAlpha((int)(this.j * 255.0f));
                if (!this.g) {
                    this.q.getDrawable(0).setAlpha((int)((1.0f - this.j) * 255.0f));
                }
                super.setImageDrawable((Drawable)this.q);
                return;
            }
            attributeSet = this.getDrawable();
            this.i = attributeSet;
            if (attributeSet != null) {
                layerDrawable = this.p;
                attributeSet = attributeSet.mutate();
                this.i = attributeSet;
                layerDrawable[0] = attributeSet;
            }
        }
    }

    private void f() {
        if (Float.isNaN(this.r) && Float.isNaN(this.s) && Float.isNaN(this.t) && Float.isNaN(this.u)) {
            return;
        }
        boolean bl = Float.isNaN(this.r);
        float f3 = 0.0f;
        float f4 = bl ? 0.0f : this.r;
        float f5 = Float.isNaN(this.s) ? 0.0f : this.s;
        float f6 = Float.isNaN(this.t) ? 1.0f : this.t;
        if (!Float.isNaN(this.u)) {
            f3 = this.u;
        }
        Matrix matrix = new Matrix();
        matrix.reset();
        float f7 = this.getDrawable().getIntrinsicWidth();
        float f8 = this.getDrawable().getIntrinsicHeight();
        float f9 = this.getWidth();
        float f10 = this.getHeight();
        float f11 = f7 * f10 < f8 * f9 ? f9 / f7 : f10 / f8;
        f11 = f6 * f11;
        matrix.postScale(f11, f11);
        f6 = f7 * f11;
        matrix.postTranslate((f4 * (f9 - f6) + f9 - f6) * 0.5f, (f5 * (f10 - (f11 *= f8)) + f10 - f11) * 0.5f);
        matrix.postRotate(f3, f9 / 2.0f, f10 / 2.0f);
        this.setImageMatrix(matrix);
        this.setScaleType(ImageView.ScaleType.MATRIX);
    }

    private void g() {
        if (Float.isNaN(this.r) && Float.isNaN(this.s) && Float.isNaN(this.t) && Float.isNaN(this.u)) {
            this.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return;
        }
        this.f();
    }

    private void setOverlay(boolean bl) {
        this.g = bl;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public float getBrightness() {
        return this.f.d;
    }

    public float getContrast() {
        return this.f.f;
    }

    public float getCrossfade() {
        return this.j;
    }

    public float getImagePanX() {
        return this.r;
    }

    public float getImagePanY() {
        return this.s;
    }

    public float getImageRotate() {
        return this.u;
    }

    public float getImageZoom() {
        return this.t;
    }

    public float getRound() {
        return this.l;
    }

    public float getRoundPercent() {
        return this.k;
    }

    public float getSaturation() {
        return this.f.e;
    }

    public float getWarmth() {
        return this.f.g;
    }

    public void layout(int n3, int n4, int n5, int n6) {
        super.layout(n3, n4, n5, n6);
        this.f();
    }

    public void setAltImageDrawable(Drawable layerDrawable) {
        Drawable drawable;
        this.h = drawable = layerDrawable.mutate();
        layerDrawable = this.p;
        layerDrawable[0] = this.i;
        layerDrawable[1] = drawable;
        this.q = layerDrawable = new LayerDrawable(this.p);
        super.setImageDrawable((Drawable)layerDrawable);
        this.setCrossfade(this.j);
    }

    public void setAltImageResource(int n3) {
        Drawable drawable;
        this.h = drawable = a.b(this.getContext(), n3);
        this.setAltImageDrawable(drawable);
    }

    public void setBrightness(float f3) {
        c c3 = this.f;
        c3.d = f3;
        c3.c(this);
    }

    public void setContrast(float f3) {
        c c3 = this.f;
        c3.f = f3;
        c3.c(this);
    }

    public void setCrossfade(float f3) {
        this.j = f3;
        if (this.p != null) {
            if (!this.g) {
                this.q.getDrawable(0).setAlpha((int)((1.0f - this.j) * 255.0f));
            }
            this.q.getDrawable(1).setAlpha((int)(this.j * 255.0f));
            super.setImageDrawable((Drawable)this.q);
        }
    }

    @Override
    public void setImageDrawable(Drawable layerDrawable) {
        if (this.h != null && layerDrawable != null) {
            Drawable drawable;
            this.i = drawable = layerDrawable.mutate();
            layerDrawable = this.p;
            layerDrawable[0] = drawable;
            layerDrawable[1] = this.h;
            this.q = layerDrawable = new LayerDrawable(this.p);
            super.setImageDrawable((Drawable)layerDrawable);
            this.setCrossfade(this.j);
            return;
        }
        super.setImageDrawable((Drawable)layerDrawable);
    }

    public void setImagePanX(float f3) {
        this.r = f3;
        this.g();
    }

    public void setImagePanY(float f3) {
        this.s = f3;
        this.g();
    }

    @Override
    public void setImageResource(int n3) {
        if (this.h != null) {
            Drawable drawable;
            this.i = drawable = a.b(this.getContext(), n3).mutate();
            Drawable[] drawableArray = this.p;
            drawableArray[0] = drawable;
            drawableArray[1] = this.h;
            drawable = new LayerDrawable(this.p);
            this.q = drawable;
            super.setImageDrawable(drawable);
            this.setCrossfade(this.j);
            return;
        }
        super.setImageResource(n3);
    }

    public void setImageRotate(float f3) {
        this.u = f3;
        this.g();
    }

    public void setImageZoom(float f3) {
        this.t = f3;
        this.g();
    }

    public void setRound(float f3) {
        if (Float.isNaN(f3)) {
            this.l = f3;
            f3 = this.k;
            this.k = -1.0f;
            this.setRoundPercent(f3);
            return;
        }
        boolean bl = this.l != f3;
        this.l = f3;
        if (f3 != 0.0f) {
            ViewOutlineProvider viewOutlineProvider;
            if (this.m == null) {
                this.m = new Path();
            }
            if (this.o == null) {
                this.o = new RectF();
            }
            if (this.n == null) {
                this.n = viewOutlineProvider = new ViewOutlineProvider(this){
                    public final ImageFilterView a;
                    {
                        this.a = imageFilterView;
                    }

                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, this.a.getWidth(), this.a.getHeight(), this.a.l);
                    }
                };
                this.setOutlineProvider(viewOutlineProvider);
            }
            this.setClipToOutline(true);
            int n3 = this.getWidth();
            int n4 = this.getHeight();
            this.o.set(0.0f, 0.0f, (float)n3, (float)n4);
            this.m.reset();
            Path path = this.m;
            viewOutlineProvider = this.o;
            f3 = this.l;
            path.addRoundRect((RectF)viewOutlineProvider, f3, f3, Path.Direction.CW);
        } else {
            this.setClipToOutline(false);
        }
        if (bl) {
            this.invalidateOutline();
        }
    }

    public void setRoundPercent(float f3) {
        boolean bl = this.k != f3;
        this.k = f3;
        if (f3 != 0.0f) {
            if (this.m == null) {
                this.m = new Path();
            }
            if (this.o == null) {
                this.o = new RectF();
            }
            if (this.n == null) {
                ViewOutlineProvider viewOutlineProvider;
                this.n = viewOutlineProvider = new ViewOutlineProvider(this){
                    public final ImageFilterView a;
                    {
                        this.a = imageFilterView;
                    }

                    public void getOutline(View view, Outline outline) {
                        int n3 = this.a.getWidth();
                        int n4 = this.a.getHeight();
                        outline.setRoundRect(0, 0, n3, n4, (float)Math.min(n3, n4) * this.a.k / 2.0f);
                    }
                };
                this.setOutlineProvider(viewOutlineProvider);
            }
            this.setClipToOutline(true);
            int n3 = this.getWidth();
            int n4 = this.getHeight();
            f3 = (float)Math.min(n3, n4) * this.k / 2.0f;
            this.o.set(0.0f, 0.0f, (float)n3, (float)n4);
            this.m.reset();
            this.m.addRoundRect(this.o, f3, f3, Path.Direction.CW);
        } else {
            this.setClipToOutline(false);
        }
        if (bl) {
            this.invalidateOutline();
        }
    }

    public void setSaturation(float f3) {
        c c3 = this.f;
        c3.e = f3;
        c3.c(this);
    }

    public void setWarmth(float f3) {
        c c3 = this.f;
        c3.g = f3;
        c3.c(this);
    }

    public static class c {
        public float[] a = new float[20];
        public ColorMatrix b = new ColorMatrix();
        public ColorMatrix c = new ColorMatrix();
        public float d = 1.0f;
        public float e = 1.0f;
        public float f = 1.0f;
        public float g = 1.0f;

        public final void a(float f3) {
            float[] fArray = this.a;
            fArray[0] = f3;
            fArray[1] = 0.0f;
            fArray[2] = 0.0f;
            fArray[3] = 0.0f;
            fArray[4] = 0.0f;
            fArray[5] = 0.0f;
            fArray[6] = f3;
            fArray[7] = 0.0f;
            fArray[8] = 0.0f;
            fArray[9] = 0.0f;
            fArray[10] = 0.0f;
            fArray[11] = 0.0f;
            fArray[12] = f3;
            fArray[13] = 0.0f;
            fArray[14] = 0.0f;
            fArray[15] = 0.0f;
            fArray[16] = 0.0f;
            fArray[17] = 0.0f;
            fArray[18] = 1.0f;
            fArray[19] = 0.0f;
        }

        public final void b(float f3) {
            float f4 = 1.0f - f3;
            float f5 = 0.2999f * f4;
            float f6 = 0.587f * f4;
            float[] fArray = this.a;
            fArray[0] = f5 + f3;
            fArray[1] = f6;
            fArray[2] = f4 *= 0.114f;
            fArray[3] = 0.0f;
            fArray[4] = 0.0f;
            fArray[5] = f5;
            fArray[6] = f6 + f3;
            fArray[7] = f4;
            fArray[8] = 0.0f;
            fArray[9] = 0.0f;
            fArray[10] = f5;
            fArray[11] = f6;
            fArray[12] = f4 + f3;
            fArray[13] = 0.0f;
            fArray[14] = 0.0f;
            fArray[15] = 0.0f;
            fArray[16] = 0.0f;
            fArray[17] = 0.0f;
            fArray[18] = 1.0f;
            fArray[19] = 0.0f;
        }

        public void c(ImageView imageView) {
            boolean bl;
            this.b.reset();
            float f3 = this.e;
            boolean bl2 = true;
            if (f3 != 1.0f) {
                this.b(f3);
                this.b.set(this.a);
                bl = true;
            } else {
                bl = false;
            }
            f3 = this.f;
            if (f3 != 1.0f) {
                this.c.setScale(f3, f3, f3, 1.0f);
                this.b.postConcat(this.c);
                bl = true;
            }
            if ((f3 = this.g) != 1.0f) {
                this.d(f3);
                this.c.set(this.a);
                this.b.postConcat(this.c);
                bl = true;
            }
            if ((f3 = this.d) != 1.0f) {
                this.a(f3);
                this.c.set(this.a);
                this.b.postConcat(this.c);
                bl = bl2;
            }
            if (bl) {
                imageView.setColorFilter((ColorFilter)new ColorMatrixColorFilter(this.b));
                return;
            }
            imageView.clearColorFilter();
        }

        public final void d(float f3) {
            float f4;
            float f5 = f3;
            if (f3 <= 0.0f) {
                f5 = 0.01f;
            }
            if ((f3 = 5000.0f / f5 / 100.0f) > 66.0f) {
                double d3 = f3 - 60.0f;
                f4 = (float)Math.pow(d3, -0.13320475816726685) * 329.69873f;
                f5 = (float)Math.pow(d3, 0.07551485300064087) * 288.12216f;
            } else {
                f5 = (float)Math.log(f3) * 99.4708f - 161.11957f;
                f4 = 255.0f;
            }
            f3 = f3 < 66.0f ? (f3 > 19.0f ? (float)Math.log(f3 - 10.0f) * 138.51773f - 305.0448f : 0.0f) : 255.0f;
            f4 = Math.min(255.0f, Math.max(f4, 0.0f));
            f5 = Math.min(255.0f, Math.max(f5, 0.0f));
            f3 = Math.min(255.0f, Math.max(f3, 0.0f));
            float f6 = (float)Math.log(50.0f);
            float f7 = (float)Math.log(40.0f);
            float f8 = Math.min(255.0f, Math.max(255.0f, 0.0f));
            f6 = Math.min(255.0f, Math.max(f6 * 99.4708f - 161.11957f, 0.0f));
            f7 = Math.min(255.0f, Math.max(f7 * 138.51773f - 305.0448f, 0.0f));
            f5 /= f6;
            f3 /= f7;
            float[] fArray = this.a;
            fArray[0] = f4 /= f8;
            fArray[1] = 0.0f;
            fArray[2] = 0.0f;
            fArray[3] = 0.0f;
            fArray[4] = 0.0f;
            fArray[5] = 0.0f;
            fArray[6] = f5;
            fArray[7] = 0.0f;
            fArray[8] = 0.0f;
            fArray[9] = 0.0f;
            fArray[10] = 0.0f;
            fArray[11] = 0.0f;
            fArray[12] = f3;
            fArray[13] = 0.0f;
            fArray[14] = 0.0f;
            fArray[15] = 0.0f;
            fArray[16] = 0.0f;
            fArray[17] = 0.0f;
            fArray[18] = 1.0f;
            fArray[19] = 0.0f;
        }
    }
}

