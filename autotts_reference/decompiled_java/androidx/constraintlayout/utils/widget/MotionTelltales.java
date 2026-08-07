/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.util.AttributeSet
 */
package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.utils.widget.MockView;
import y.d;

public class MotionTelltales
extends MockView {
    public Paint n = new Paint();
    public MotionLayout o;
    public float[] p = new float[2];
    public Matrix q = new Matrix();
    public int r = 0;
    public int s = -65281;
    public float t = 0.25f;

    public MotionTelltales(Context context) {
        super(context);
        this.a(context, null);
    }

    public MotionTelltales(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a(context, attributeSet);
    }

    public MotionTelltales(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.a(context, attributeSet);
    }

    public final void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            context = context.obtainStyledAttributes(attributeSet, y.d.MotionTelltales);
            int n3 = context.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = context.getIndex(i3);
                if (n4 == y.d.MotionTelltales_telltales_tailColor) {
                    this.s = context.getColor(n4, this.s);
                    continue;
                }
                if (n4 == y.d.MotionTelltales_telltales_velocityMode) {
                    this.r = context.getInt(n4, this.r);
                    continue;
                }
                if (n4 != y.d.MotionTelltales_telltales_tailScale) continue;
                this.t = context.getFloat(n4, this.t);
            }
            context.recycle();
        }
        this.n.setColor(this.s);
        this.n.setStrokeWidth(5.0f);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.getMatrix().invert(this.q);
        if (this.o == null) {
            canvas = this.getParent();
            if (canvas instanceof MotionLayout) {
                this.o = (MotionLayout)canvas;
                return;
            }
        } else {
            float[] fArray;
            int n3 = this.getWidth();
            int n4 = this.getHeight();
            float[] fArray2 = fArray = new float[5];
            fArray[0] = 0.1f;
            fArray2[1] = 0.25f;
            fArray2[2] = 0.5f;
            fArray2[3] = 0.75f;
            fArray2[4] = 0.9f;
            for (int i3 = 0; i3 < 5; ++i3) {
                float f3 = fArray[i3];
                for (int i4 = 0; i4 < 5; ++i4) {
                    float f4 = fArray[i4];
                    this.o.r0(this, f4, f3, this.p, this.r);
                    this.q.mapVectors(this.p);
                    float f5 = (float)n3 * f4;
                    f4 = (float)n4 * f3;
                    float[] fArray3 = this.p;
                    float f6 = fArray3[0];
                    float f7 = this.t;
                    float f8 = fArray3[1];
                    this.q.mapVectors(fArray3);
                    canvas.drawLine(f5, f4, f5 - f6 * f7, f4 - f8 * f7, this.n);
                }
            }
        }
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        this.postInvalidate();
    }

    public void setText(CharSequence charSequence) {
        this.h = charSequence.toString();
        this.requestLayout();
    }
}

