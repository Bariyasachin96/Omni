/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import d.a;
import y.d;

public class ImageFilterButton
extends AppCompatImageButton {
    public ImageFilterView.c f = new ImageFilterView.c();
    public float g = 0.0f;
    public float h = 0.0f;
    public float i = Float.NaN;
    public Path j;
    public ViewOutlineProvider k;
    public RectF l;
    public Drawable[] m = new Drawable[2];
    public LayerDrawable n;
    public boolean o = true;
    public Drawable p = null;
    public Drawable q = null;
    public float r = Float.NaN;
    public float s = Float.NaN;
    public float t = Float.NaN;
    public float u = Float.NaN;

    public ImageFilterButton(Context context) {
        super(context);
        this.c(context, null);
    }

    public ImageFilterButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c(context, attributeSet);
    }

    public ImageFilterButton(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.c(context, attributeSet);
    }

    private void setOverlay(boolean bl) {
        this.o = bl;
    }

    public final void c(Context layerDrawable, AttributeSet drawable) {
        this.setPadding(0, 0, 0, 0);
        if (drawable != null) {
            layerDrawable = layerDrawable.obtainStyledAttributes((AttributeSet)drawable, y.d.ImageFilterView);
            int n3 = layerDrawable.getIndexCount();
            this.p = layerDrawable.getDrawable(y.d.ImageFilterView_altSrc);
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = layerDrawable.getIndex(i3);
                if (n4 == y.d.ImageFilterView_crossfade) {
                    this.g = layerDrawable.getFloat(n4, 0.0f);
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
                if (n4 == y.d.ImageFilterView_round) {
                    this.setRound(layerDrawable.getDimension(n4, 0.0f));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_roundPercent) {
                    this.setRoundPercent(layerDrawable.getFloat(n4, 0.0f));
                    continue;
                }
                if (n4 == y.d.ImageFilterView_overlay) {
                    this.setOverlay(layerDrawable.getBoolean(n4, this.o));
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
            this.q = layerDrawable;
            if (this.p != null && layerDrawable != null) {
                drawable = this.m;
                this.q = layerDrawable = this.getDrawable().mutate();
                drawable[0] = layerDrawable;
                this.m[1] = this.p.mutate();
                this.n = layerDrawable = new LayerDrawable(this.m);
                layerDrawable.getDrawable(1).setAlpha((int)(this.g * 255.0f));
                if (!this.o) {
                    this.n.getDrawable(0).setAlpha((int)((1.0f - this.g) * 255.0f));
                }
                super.setImageDrawable((Drawable)this.n);
                return;
            }
            this.q = drawable = this.getDrawable();
            if (drawable != null) {
                layerDrawable = this.m;
                this.q = drawable = drawable.mutate();
                layerDrawable[0] = drawable;
            }
        }
    }

    public final void d() {
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

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public final void e() {
        if (Float.isNaN(this.r) && Float.isNaN(this.s) && Float.isNaN(this.t) && Float.isNaN(this.u)) {
            this.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return;
        }
        this.d();
    }

    public float getContrast() {
        return this.f.f;
    }

    public float getCrossfade() {
        return this.g;
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
        return this.i;
    }

    public float getRoundPercent() {
        return this.h;
    }

    public float getSaturation() {
        return this.f.e;
    }

    public float getWarmth() {
        return this.f.g;
    }

    public void layout(int n3, int n4, int n5, int n6) {
        super.layout(n3, n4, n5, n6);
        this.d();
    }

    public void setAltImageResource(int n3) {
        Drawable drawable;
        this.p = drawable = a.b(this.getContext(), n3).mutate();
        Drawable[] drawableArray = this.m;
        drawableArray[0] = this.q;
        drawableArray[1] = drawable;
        drawable = new LayerDrawable(this.m);
        this.n = drawable;
        super.setImageDrawable(drawable);
        this.setCrossfade(this.g);
    }

    public void setBrightness(float f3) {
        ImageFilterView.c c3 = this.f;
        c3.d = f3;
        c3.c((ImageView)this);
    }

    public void setContrast(float f3) {
        ImageFilterView.c c3 = this.f;
        c3.f = f3;
        c3.c((ImageView)this);
    }

    public void setCrossfade(float f3) {
        this.g = f3;
        if (this.m != null) {
            if (!this.o) {
                this.n.getDrawable(0).setAlpha((int)((1.0f - this.g) * 255.0f));
            }
            this.n.getDrawable(1).setAlpha((int)(this.g * 255.0f));
            super.setImageDrawable((Drawable)this.n);
        }
    }

    @Override
    public void setImageDrawable(Drawable layerDrawable) {
        if (this.p != null && layerDrawable != null) {
            Drawable drawable;
            this.q = drawable = layerDrawable.mutate();
            layerDrawable = this.m;
            layerDrawable[0] = drawable;
            layerDrawable[1] = this.p;
            this.n = layerDrawable = new LayerDrawable(this.m);
            super.setImageDrawable((Drawable)layerDrawable);
            this.setCrossfade(this.g);
            return;
        }
        super.setImageDrawable((Drawable)layerDrawable);
    }

    public void setImagePanX(float f3) {
        this.r = f3;
        this.e();
    }

    public void setImagePanY(float f3) {
        this.s = f3;
        this.e();
    }

    @Override
    public void setImageResource(int n3) {
        if (this.p != null) {
            Drawable drawable;
            this.q = drawable = a.b(this.getContext(), n3).mutate();
            Drawable[] drawableArray = this.m;
            drawableArray[0] = drawable;
            drawableArray[1] = this.p;
            drawable = new LayerDrawable(this.m);
            this.n = drawable;
            super.setImageDrawable(drawable);
            this.setCrossfade(this.g);
            return;
        }
        super.setImageResource(n3);
    }

    public void setImageRotate(float f3) {
        this.u = f3;
        this.e();
    }

    public void setImageZoom(float f3) {
        this.t = f3;
        this.e();
    }

    public void setRound(float f3) {
        if (Float.isNaN(f3)) {
            this.i = f3;
            f3 = this.h;
            this.h = -1.0f;
            this.setRoundPercent(f3);
            return;
        }
        boolean bl = this.i != f3;
        this.i = f3;
        if (f3 != 0.0f) {
            ViewOutlineProvider viewOutlineProvider;
            if (this.j == null) {
                this.j = new Path();
            }
            if (this.l == null) {
                this.l = new RectF();
            }
            if (this.k == null) {
                this.k = viewOutlineProvider = new ViewOutlineProvider(this){
                    public final ImageFilterButton a;
                    {
                        this.a = imageFilterButton;
                    }

                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, this.a.getWidth(), this.a.getHeight(), this.a.i);
                    }
                };
                this.setOutlineProvider(viewOutlineProvider);
            }
            this.setClipToOutline(true);
            int n3 = this.getWidth();
            int n4 = this.getHeight();
            this.l.set(0.0f, 0.0f, (float)n3, (float)n4);
            this.j.reset();
            viewOutlineProvider = this.j;
            RectF rectF = this.l;
            f3 = this.i;
            viewOutlineProvider.addRoundRect(rectF, f3, f3, Path.Direction.CW);
        } else {
            this.setClipToOutline(false);
        }
        if (bl) {
            this.invalidateOutline();
        }
    }

    public void setRoundPercent(float f3) {
        boolean bl = this.h != f3;
        this.h = f3;
        if (f3 != 0.0f) {
            if (this.j == null) {
                this.j = new Path();
            }
            if (this.l == null) {
                this.l = new RectF();
            }
            if (this.k == null) {
                ViewOutlineProvider viewOutlineProvider;
                this.k = viewOutlineProvider = new ViewOutlineProvider(this){
                    public final ImageFilterButton a;
                    {
                        this.a = imageFilterButton;
                    }

                    public void getOutline(View view, Outline outline) {
                        int n3 = this.a.getWidth();
                        int n4 = this.a.getHeight();
                        outline.setRoundRect(0, 0, n3, n4, (float)Math.min(n3, n4) * this.a.h / 2.0f);
                    }
                };
                this.setOutlineProvider(viewOutlineProvider);
            }
            this.setClipToOutline(true);
            int n3 = this.getWidth();
            int n4 = this.getHeight();
            f3 = (float)Math.min(n3, n4) * this.h / 2.0f;
            this.l.set(0.0f, 0.0f, (float)n3, (float)n4);
            this.j.reset();
            this.j.addRoundRect(this.l, f3, f3, Path.Direction.CW);
        } else {
            this.setClipToOutline(false);
        }
        if (bl) {
            this.invalidateOutline();
        }
    }

    public void setSaturation(float f3) {
        ImageFilterView.c c3 = this.f;
        c3.e = f3;
        c3.c((ImageView)this);
    }

    public void setWarmth(float f3) {
        ImageFilterView.c c3 = this.f;
        c3.g = f3;
        c3.c((ImageView)this);
    }
}

