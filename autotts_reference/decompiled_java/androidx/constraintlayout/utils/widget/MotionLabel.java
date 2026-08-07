/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.text.Layout
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewOutlineProvider
 */
package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import java.util.Objects;
import x.a;
import x.c;
import y.d;

public class MotionLabel
extends View
implements c {
    public int A = 0;
    public boolean B = false;
    public float C;
    public float D;
    public float E;
    public Drawable F;
    public Matrix G;
    public Bitmap H;
    public BitmapShader I;
    public Matrix J;
    public float K;
    public float L;
    public float M = 0.0f;
    public float N = 0.0f;
    public Paint O;
    public int P = 0;
    public Rect Q;
    public Paint R;
    public float S;
    public float T;
    public float U;
    public float V;
    public float W;
    public TextPaint c = new TextPaint();
    public Path d = new Path();
    public int e = 65535;
    public int f = 65535;
    public boolean g = false;
    public float h = 0.0f;
    public float i = Float.NaN;
    public ViewOutlineProvider j;
    public RectF k;
    public float l = 48.0f;
    public float m = Float.NaN;
    public int n;
    public int o;
    public float p = 0.0f;
    public String q = "Hello World";
    public boolean r = true;
    public Rect s = new Rect();
    public int t = 1;
    public int u = 1;
    public int v = 1;
    public int w = 1;
    public String x;
    public Layout y;
    public int z = 0x800033;

    public MotionLabel(Context context) {
        super(context);
        this.K = Float.NaN;
        this.L = Float.NaN;
        this.O = new Paint();
        this.T = Float.NaN;
        this.U = Float.NaN;
        this.V = Float.NaN;
        this.W = Float.NaN;
        this.g(context, null);
    }

    public MotionLabel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.K = Float.NaN;
        this.L = Float.NaN;
        this.O = new Paint();
        this.T = Float.NaN;
        this.U = Float.NaN;
        this.V = Float.NaN;
        this.W = Float.NaN;
        this.g(context, attributeSet);
    }

    public MotionLabel(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.K = Float.NaN;
        this.L = Float.NaN;
        this.O = new Paint();
        this.T = Float.NaN;
        this.U = Float.NaN;
        this.V = Float.NaN;
        this.W = Float.NaN;
        this.g(context, attributeSet);
    }

    private float getHorizontalOffset() {
        float f3 = Float.isNaN(this.m) ? 1.0f : this.l / this.m;
        TextPaint textPaint = this.c;
        String string = this.q;
        float f4 = textPaint.measureText(string, 0, string.length());
        float f5 = Float.isNaN(this.D) ? (float)this.getMeasuredWidth() : this.D;
        return (f5 - (float)this.getPaddingLeft() - (float)this.getPaddingRight() - f3 * f4) * (this.M + 1.0f) / 2.0f;
    }

    private float getVerticalOffset() {
        float f3 = Float.isNaN(this.m) ? 1.0f : this.l / this.m;
        Paint.FontMetrics fontMetrics = this.c.getFontMetrics();
        float f4 = Float.isNaN(this.E) ? (float)this.getMeasuredHeight() : this.E;
        float f5 = this.getPaddingTop();
        float f6 = this.getPaddingBottom();
        float f7 = fontMetrics.descent;
        float f8 = fontMetrics.ascent;
        return (f4 - f5 - f6 - (f7 - f8) * f3) * (1.0f - this.N) / 2.0f - f3 * f8;
    }

    private void setUpTheme(Context context) {
        int n3;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(c.a.colorPrimary, typedValue, true);
        context = this.c;
        this.e = n3 = typedValue.data;
        context.setColor(n3);
    }

    @Override
    public void a(float f3, float f4, float f5, float f6) {
        float f7;
        float f8;
        int n3 = (int)(f3 + 0.5f);
        this.C = f3 - (float)n3;
        int n4 = (int)(f5 + 0.5f);
        int n5 = n4 - n3;
        int n6 = (int)(f6 + 0.5f);
        int n7 = (int)(0.5f + f4);
        int n8 = n6 - n7;
        this.D = f8 = f5 - f3;
        this.E = f7 = f6 - f4;
        this.d(f3, f4, f5, f6);
        if (this.getMeasuredHeight() == n8 && this.getMeasuredWidth() == n5) {
            super.layout(n3, n7, n4, n6);
        } else {
            this.measure(View.MeasureSpec.makeMeasureSpec((int)n5, (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)n8, (int)0x40000000));
            super.layout(n3, n7, n4, n6);
        }
        if (this.B) {
            if (this.Q == null) {
                this.R = new Paint();
                this.Q = new Rect();
                this.R.set((Paint)this.c);
                this.S = this.R.getTextSize();
            }
            this.D = f8;
            this.E = f7;
            Paint paint = this.R;
            String string = this.q;
            paint.getTextBounds(string, 0, string.length(), this.Q);
            n5 = this.Q.width();
            f3 = (float)this.Q.height() * 1.3f;
            f4 = f8 - (float)this.u - (float)this.t;
            f5 = f7 - (float)this.w - (float)this.v;
            f6 = n5;
            if (f6 * f5 > f3 * f4) {
                this.c.setTextSize(this.S * f4 / f6);
            } else {
                this.c.setTextSize(this.S * f5 / f3);
            }
            if (this.g || !Float.isNaN(this.m)) {
                f3 = Float.isNaN(this.m) ? 1.0f : this.l / this.m;
                this.f(f3);
            }
        }
    }

    public final void d(float f3, float f4, float f5, float f6) {
        if (this.J == null) {
            return;
        }
        this.D = f5 - f3;
        this.E = f6 - f4;
        this.k();
    }

    public Bitmap e(Bitmap bitmap, int n3) {
        int n4 = bitmap.getWidth();
        int n5 = bitmap.getHeight();
        int n6 = n4 / 2;
        n4 = n5 / 2;
        bitmap = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)n6, (int)n4, (boolean)true);
        for (n5 = 0; n5 < n3 && n6 >= 32 && n4 >= 32; ++n5) {
            bitmap = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)(n6 /= 2), (int)(n4 /= 2), (boolean)true);
        }
        return bitmap;
    }

    public void f(float f3) {
        if (!this.g && f3 == 1.0f) {
            return;
        }
        this.d.reset();
        String string = this.q;
        int n3 = string.length();
        this.c.getTextBounds(string, 0, n3, this.s);
        this.c.getTextPath(string, 0, n3, 0.0f, 0.0f, this.d);
        if (f3 != 1.0f) {
            a.a();
            string = new Matrix();
            string.postScale(f3, f3);
            this.d.transform((Matrix)string);
        }
        string = this.s;
        --((Rect)string).right;
        ++((Rect)string).left;
        ++((Rect)string).bottom;
        --((Rect)string).top;
        string = new RectF();
        ((RectF)string).bottom = this.getHeight();
        ((RectF)string).right = this.getWidth();
        this.r = false;
    }

    public final void g(Context context, AttributeSet attributeSet) {
        this.setUpTheme(context);
        if (attributeSet != null) {
            context = this.getContext().obtainStyledAttributes(attributeSet, y.d.MotionLabel);
            int n3 = context.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                float f3;
                int n4 = context.getIndex(i3);
                if (n4 == y.d.MotionLabel_android_text) {
                    this.setText(context.getText(n4));
                    continue;
                }
                if (n4 == y.d.MotionLabel_android_fontFamily) {
                    this.x = context.getString(n4);
                    continue;
                }
                if (n4 == y.d.MotionLabel_scaleFromTextSize) {
                    this.m = context.getDimensionPixelSize(n4, (int)this.m);
                    continue;
                }
                if (n4 == y.d.MotionLabel_android_textSize) {
                    this.l = context.getDimensionPixelSize(n4, (int)this.l);
                    continue;
                }
                if (n4 == y.d.MotionLabel_android_textStyle) {
                    this.n = context.getInt(n4, this.n);
                    continue;
                }
                if (n4 == y.d.MotionLabel_android_typeface) {
                    this.o = context.getInt(n4, this.o);
                    continue;
                }
                if (n4 == y.d.MotionLabel_android_textColor) {
                    this.e = context.getColor(n4, this.e);
                    continue;
                }
                if (n4 == y.d.MotionLabel_borderRound) {
                    this.i = f3 = context.getDimension(n4, this.i);
                    this.setRound(f3);
                    continue;
                }
                if (n4 == y.d.MotionLabel_borderRoundPercent) {
                    this.h = f3 = context.getFloat(n4, this.h);
                    this.setRoundPercent(f3);
                    continue;
                }
                if (n4 == y.d.MotionLabel_android_gravity) {
                    this.setGravity(context.getInt(n4, -1));
                    continue;
                }
                if (n4 == y.d.MotionLabel_android_autoSizeTextType) {
                    this.A = context.getInt(n4, 0);
                    continue;
                }
                if (n4 == y.d.MotionLabel_textOutlineColor) {
                    this.f = context.getInt(n4, this.f);
                    this.g = true;
                    continue;
                }
                if (n4 == y.d.MotionLabel_textOutlineThickness) {
                    this.p = context.getDimension(n4, this.p);
                    this.g = true;
                    continue;
                }
                if (n4 == y.d.MotionLabel_textBackground) {
                    this.F = context.getDrawable(n4);
                    this.g = true;
                    continue;
                }
                if (n4 == y.d.MotionLabel_textBackgroundPanX) {
                    this.T = context.getFloat(n4, this.T);
                    continue;
                }
                if (n4 == y.d.MotionLabel_textBackgroundPanY) {
                    this.U = context.getFloat(n4, this.U);
                    continue;
                }
                if (n4 == y.d.MotionLabel_textPanX) {
                    this.M = context.getFloat(n4, this.M);
                    continue;
                }
                if (n4 == y.d.MotionLabel_textPanY) {
                    this.N = context.getFloat(n4, this.N);
                    continue;
                }
                if (n4 == y.d.MotionLabel_textBackgroundRotate) {
                    this.W = context.getFloat(n4, this.W);
                    continue;
                }
                if (n4 == y.d.MotionLabel_textBackgroundZoom) {
                    this.V = context.getFloat(n4, this.V);
                    continue;
                }
                if (n4 == y.d.MotionLabel_textureHeight) {
                    this.K = context.getDimension(n4, this.K);
                    continue;
                }
                if (n4 == y.d.MotionLabel_textureWidth) {
                    this.L = context.getDimension(n4, this.L);
                    continue;
                }
                if (n4 != y.d.MotionLabel_textureEffect) continue;
                this.P = context.getInt(n4, this.P);
            }
            context.recycle();
        }
        this.j();
        this.i();
    }

    public float getRound() {
        return this.i;
    }

    public float getRoundPercent() {
        return this.h;
    }

    public float getScaleFromTextSize() {
        return this.m;
    }

    public float getTextBackgroundPanX() {
        return this.T;
    }

    public float getTextBackgroundPanY() {
        return this.U;
    }

    public float getTextBackgroundRotate() {
        return this.W;
    }

    public float getTextBackgroundZoom() {
        return this.V;
    }

    public int getTextOutlineColor() {
        return this.f;
    }

    public float getTextPanX() {
        return this.M;
    }

    public float getTextPanY() {
        return this.N;
    }

    public float getTextureHeight() {
        return this.K;
    }

    public float getTextureWidth() {
        return this.L;
    }

    public Typeface getTypeface() {
        return this.c.getTypeface();
    }

    public final void h(String string, int n3, int n4) {
        if (string != null) {
            Typeface typeface = Typeface.create((String)string, (int)n4);
            string = typeface;
            if (typeface != null) {
                this.setTypeface(typeface);
                return;
            }
        } else {
            string = null;
        }
        boolean bl = true;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 == 3) {
                    string = Typeface.MONOSPACE;
                }
            } else {
                string = Typeface.SERIF;
            }
        } else {
            string = Typeface.SANS_SERIF;
        }
        float f3 = 0.0f;
        if (n4 > 0) {
            string = string == null ? Typeface.defaultFromStyle((int)n4) : Typeface.create((Typeface)string, (int)n4);
            this.setTypeface((Typeface)string);
            n3 = string != null ? string.getStyle() : 0;
            n3 = ~n3 & n4;
            string = this.c;
            if ((n3 & 1) == 0) {
                bl = false;
            }
            string.setFakeBoldText(bl);
            string = this.c;
            if ((n3 & 2) != 0) {
                f3 = -0.25f;
            }
            string.setTextSkewX(f3);
            return;
        }
        this.c.setFakeBoldText(false);
        this.c.setTextSkewX(0.0f);
        this.setTypeface((Typeface)string);
    }

    public void i() {
        this.t = this.getPaddingLeft();
        this.u = this.getPaddingRight();
        this.v = this.getPaddingTop();
        this.w = this.getPaddingBottom();
        this.h(this.x, this.o, this.n);
        this.c.setColor(this.e);
        this.c.setStrokeWidth(this.p);
        this.c.setStyle(Paint.Style.FILL_AND_STROKE);
        this.c.setFlags(128);
        this.setTextSize(this.l);
        this.c.setAntiAlias(true);
    }

    public final void j() {
        if (this.F != null) {
            this.J = new Matrix();
            int n3 = this.F.getIntrinsicWidth();
            int n4 = this.F.getIntrinsicHeight();
            int n5 = 128;
            int n6 = n3;
            if (n3 <= 0) {
                n6 = n3 = this.getWidth();
                if (n3 == 0) {
                    n6 = Float.isNaN(this.L) ? 128 : (int)this.L;
                }
            }
            n3 = n4;
            if (n4 <= 0) {
                n3 = n4 = this.getHeight();
                if (n4 == 0) {
                    n3 = Float.isNaN(this.K) ? n5 : (int)this.K;
                }
            }
            n4 = n6;
            n5 = n3;
            if (this.P != 0) {
                n4 = n6 / 2;
                n5 = n3 / 2;
            }
            this.H = Bitmap.createBitmap((int)n4, (int)n5, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(this.H);
            this.F.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            this.F.setFilterBitmap(true);
            this.F.draw(canvas);
            if (this.P != 0) {
                this.H = this.e(this.H, 4);
            }
            Bitmap bitmap = this.H;
            canvas = Shader.TileMode.REPEAT;
            this.I = new BitmapShader(bitmap, (Shader.TileMode)canvas, (Shader.TileMode)canvas);
        }
    }

    public final void k() {
        boolean bl = Float.isNaN(this.T);
        float f3 = 0.0f;
        float f4 = bl ? 0.0f : this.T;
        float f5 = Float.isNaN(this.U) ? 0.0f : this.U;
        float f6 = Float.isNaN(this.V) ? 1.0f : this.V;
        if (!Float.isNaN(this.W)) {
            f3 = this.W;
        }
        this.J.reset();
        float f7 = this.H.getWidth();
        float f8 = this.H.getHeight();
        float f9 = Float.isNaN(this.L) ? this.D : this.L;
        float f10 = Float.isNaN(this.K) ? this.E : this.K;
        float f11 = f7 * f10 < f8 * f9 ? f9 / f7 : f10 / f8;
        this.J.postScale(f6 *= f11, f6);
        f11 = f9 - (f7 *= f6);
        f8 = f6 * f8;
        f6 = f10 - f8;
        if (!Float.isNaN(this.K)) {
            f6 = this.K / 2.0f;
        }
        if (!Float.isNaN(this.L)) {
            f11 = this.L / 2.0f;
        }
        this.J.postTranslate((f4 * f11 + f9 - f7) * 0.5f, (f5 * f6 + f10 - f8) * 0.5f);
        this.J.postRotate(f3, f9 / 2.0f, f10 / 2.0f);
        this.I.setLocalMatrix(this.J);
    }

    public void layout(int n3, int n4, int n5, int n6) {
        super.layout(n3, n4, n5, n6);
        boolean bl = Float.isNaN(this.m);
        float f3 = bl ? 1.0f : this.l / this.m;
        this.D = n5 - n3;
        this.E = n6 - n4;
        float f4 = f3;
        if (this.B) {
            if (this.Q == null) {
                this.R = new Paint();
                this.Q = new Rect();
                this.R.set((Paint)this.c);
                this.S = this.R.getTextSize();
            }
            Paint paint = this.R;
            String string = this.q;
            paint.getTextBounds(string, 0, string.length(), this.Q);
            int n7 = this.Q.width();
            int n8 = (int)((float)this.Q.height() * 1.3f);
            f4 = this.D - (float)this.u - (float)this.t;
            float f5 = this.E - (float)this.w - (float)this.v;
            if (bl) {
                float f6 = n7;
                float f7 = n8;
                if (f6 * f5 > f7 * f4) {
                    this.c.setTextSize(this.S * f4 / f6);
                    f4 = f3;
                } else {
                    this.c.setTextSize(this.S * f5 / f7);
                    f4 = f3;
                }
            } else {
                float f8 = n7;
                f3 = n8;
                f4 = f8 * f5 > f3 * f4 ? (f4 /= f8) : f5 / f3;
            }
        }
        if (!this.g && bl) {
            return;
        }
        this.d(n3, n4, n5, n6);
        this.f(f4);
    }

    public void onDraw(Canvas canvas) {
        float f3 = Float.isNaN(this.m) ? 1.0f : this.l / this.m;
        super.onDraw(canvas);
        if (!this.g && f3 == 1.0f) {
            float f4 = this.t;
            f3 = this.getHorizontalOffset();
            float f5 = this.v;
            float f6 = this.getVerticalOffset();
            canvas.drawText(this.q, this.C + (f4 + f3), f5 + f6, (Paint)this.c);
            return;
        }
        if (this.r) {
            this.f(f3);
        }
        if (this.G == null) {
            this.G = new Matrix();
        }
        if (this.g) {
            this.O.set((Paint)this.c);
            this.G.reset();
            float f7 = (float)this.t + this.getHorizontalOffset();
            float f8 = (float)this.v + this.getVerticalOffset();
            this.G.postTranslate(f7, f8);
            this.G.preScale(f3, f3);
            this.d.transform(this.G);
            if (this.I != null) {
                this.c.setFilterBitmap(true);
                this.c.setShader((Shader)this.I);
            } else {
                this.c.setColor(this.e);
            }
            this.c.setStyle(Paint.Style.FILL);
            this.c.setStrokeWidth(this.p);
            canvas.drawPath(this.d, (Paint)this.c);
            if (this.I != null) {
                this.c.setShader(null);
            }
            this.c.setColor(this.f);
            this.c.setStyle(Paint.Style.STROKE);
            this.c.setStrokeWidth(this.p);
            canvas.drawPath(this.d, (Paint)this.c);
            this.G.reset();
            this.G.postTranslate(-f7, -f8);
            this.d.transform(this.G);
            this.c.set(this.O);
            return;
        }
        f3 = (float)this.t + this.getHorizontalOffset();
        float f9 = (float)this.v + this.getVerticalOffset();
        this.G.reset();
        this.G.preTranslate(f3, f9);
        this.d.transform(this.G);
        this.c.setColor(this.e);
        this.c.setStyle(Paint.Style.FILL_AND_STROKE);
        this.c.setStrokeWidth(this.p);
        canvas.drawPath(this.d, (Paint)this.c);
        this.G.reset();
        this.G.preTranslate(-f3, -f9);
        this.d.transform(this.G);
    }

    public void onMeasure(int n3, int n4) {
        int n5 = View.MeasureSpec.getMode((int)n3);
        int n6 = View.MeasureSpec.getMode((int)n4);
        n3 = View.MeasureSpec.getSize((int)n3);
        int n7 = View.MeasureSpec.getSize((int)n4);
        this.B = false;
        this.t = this.getPaddingLeft();
        this.u = this.getPaddingRight();
        this.v = this.getPaddingTop();
        this.w = this.getPaddingBottom();
        if (n5 == 0x40000000 && n6 == 0x40000000) {
            n5 = n3;
            n4 = n7;
            if (this.A != 0) {
                this.B = true;
                n5 = n3;
                n4 = n7;
            }
        } else {
            int n8;
            TextPaint textPaint = this.c;
            String string = this.q;
            textPaint.getTextBounds(string, 0, string.length(), this.s);
            if (n5 != 0x40000000) {
                n3 = (int)((float)this.s.width() + 0.99999f);
            }
            n5 = n8 = n3 + (this.t + this.u);
            n4 = n7;
            if (n6 != 0x40000000) {
                n3 = n4 = (int)((float)this.c.getFontMetricsInt(null) + 0.99999f);
                if (n6 == Integer.MIN_VALUE) {
                    n3 = Math.min(n7, n4);
                }
                n4 = this.v + this.w + n3;
                n5 = n8;
            }
        }
        this.setMeasuredDimension(n5, n4);
    }

    /*
     * Unable to fully structure code
     */
    public void setGravity(int var1_1) {
        var2_2 = var1_1;
        if ((var1_1 & 0x800007) == 0) {
            var2_2 = var1_1 | 0x800003;
        }
        var1_1 = var2_2;
        if ((var2_2 & 112) == 0) {
            var1_1 = var2_2 | 48;
        }
        if (var1_1 != this.z) {
            this.invalidate();
        }
        this.z = var1_1;
        var2_2 = var1_1 & 112;
        this.N = var2_2 != 48 ? (var2_2 != 80 ? 0.0f : 1.0f) : -1.0f;
        if ((var1_1 &= 0x800007) != 3) {
            if (var1_1 != 5) {
                if (var1_1 != 0x800003) {
                    if (var1_1 != 0x800005) {
                        this.M = 0.0f;
                        return;
                    } else {
                        ** GOTO lbl-1000
                    }
                }
            } else lbl-1000:
            // 3 sources

            {
                this.M = 1.0f;
                return;
            }
        }
        this.M = -1.0f;
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
            if (this.d == null) {
                this.d = new Path();
            }
            if (this.k == null) {
                this.k = new RectF();
            }
            if (this.j == null) {
                this.j = viewOutlineProvider = new ViewOutlineProvider(this){
                    public final MotionLabel a;
                    {
                        this.a = motionLabel;
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
            this.k.set(0.0f, 0.0f, (float)n3, (float)n4);
            this.d.reset();
            Path path = this.d;
            viewOutlineProvider = this.k;
            f3 = this.i;
            path.addRoundRect((RectF)viewOutlineProvider, f3, f3, Path.Direction.CW);
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
            if (this.d == null) {
                this.d = new Path();
            }
            if (this.k == null) {
                this.k = new RectF();
            }
            if (this.j == null) {
                ViewOutlineProvider viewOutlineProvider;
                this.j = viewOutlineProvider = new ViewOutlineProvider(this){
                    public final MotionLabel a;
                    {
                        this.a = motionLabel;
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
            this.k.set(0.0f, 0.0f, (float)n3, (float)n4);
            this.d.reset();
            this.d.addRoundRect(this.k, f3, f3, Path.Direction.CW);
        } else {
            this.setClipToOutline(false);
        }
        if (bl) {
            this.invalidateOutline();
        }
    }

    public void setScaleFromTextSize(float f3) {
        this.m = f3;
    }

    public void setText(CharSequence charSequence) {
        this.q = charSequence.toString();
        this.invalidate();
    }

    public void setTextBackgroundPanX(float f3) {
        this.T = f3;
        this.k();
        this.invalidate();
    }

    public void setTextBackgroundPanY(float f3) {
        this.U = f3;
        this.k();
        this.invalidate();
    }

    public void setTextBackgroundRotate(float f3) {
        this.W = f3;
        this.k();
        this.invalidate();
    }

    public void setTextBackgroundZoom(float f3) {
        this.V = f3;
        this.k();
        this.invalidate();
    }

    public void setTextFillColor(int n3) {
        this.e = n3;
        this.invalidate();
    }

    public void setTextOutlineColor(int n3) {
        this.f = n3;
        this.g = true;
        this.invalidate();
    }

    public void setTextOutlineThickness(float f3) {
        this.p = f3;
        this.g = true;
        if (Float.isNaN(f3)) {
            this.p = 1.0f;
            this.g = false;
        }
        this.invalidate();
    }

    public void setTextPanX(float f3) {
        this.M = f3;
        this.invalidate();
    }

    public void setTextPanY(float f3) {
        this.N = f3;
        this.invalidate();
    }

    public void setTextSize(float f3) {
        this.l = f3;
        TextPaint textPaint = this.c;
        if (!Float.isNaN(this.m)) {
            f3 = this.m;
        }
        textPaint.setTextSize(f3);
        f3 = Float.isNaN(this.m) ? 1.0f : this.l / this.m;
        this.f(f3);
        this.requestLayout();
        this.invalidate();
    }

    public void setTextureHeight(float f3) {
        this.K = f3;
        this.k();
        this.invalidate();
    }

    public void setTextureWidth(float f3) {
        this.L = f3;
        this.k();
        this.invalidate();
    }

    public void setTypeface(Typeface typeface) {
        if (!Objects.equals(this.c.getTypeface(), typeface)) {
            this.c.setTypeface(typeface);
            if (this.y != null) {
                this.y = null;
                this.requestLayout();
                this.invalidate();
            }
        }
    }
}

