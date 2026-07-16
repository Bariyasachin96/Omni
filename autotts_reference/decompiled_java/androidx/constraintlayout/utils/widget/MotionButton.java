/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Outline
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewOutlineProvider
 */
package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.widget.AppCompatButton;
import y.d;

public class MotionButton
extends AppCompatButton {
    public float f = 0.0f;
    public float g = Float.NaN;
    public Path h;
    public ViewOutlineProvider i;
    public RectF j;

    public MotionButton(Context context) {
        super(context);
        this.c(context, null);
    }

    public MotionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c(context, attributeSet);
    }

    public MotionButton(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.c(context, attributeSet);
    }

    private void c(Context context, AttributeSet attributeSet) {
        this.setPadding(0, 0, 0, 0);
        if (attributeSet != null) {
            context = context.obtainStyledAttributes(attributeSet, y.d.ImageFilterView);
            int n3 = context.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = context.getIndex(i3);
                if (n4 == y.d.ImageFilterView_round) {
                    this.setRound(context.getDimension(n4, 0.0f));
                    continue;
                }
                if (n4 != y.d.ImageFilterView_roundPercent) continue;
                this.setRoundPercent(context.getFloat(n4, 0.0f));
            }
            context.recycle();
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public float getRound() {
        return this.g;
    }

    public float getRoundPercent() {
        return this.f;
    }

    public void setRound(float f3) {
        if (Float.isNaN(f3)) {
            this.g = f3;
            f3 = this.f;
            this.f = -1.0f;
            this.setRoundPercent(f3);
            return;
        }
        boolean bl = this.g != f3;
        this.g = f3;
        if (f3 != 0.0f) {
            ViewOutlineProvider viewOutlineProvider;
            if (this.h == null) {
                this.h = new Path();
            }
            if (this.j == null) {
                this.j = new RectF();
            }
            if (this.i == null) {
                this.i = viewOutlineProvider = new ViewOutlineProvider(this){
                    public final MotionButton a;
                    {
                        this.a = motionButton;
                    }

                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, this.a.getWidth(), this.a.getHeight(), this.a.g);
                    }
                };
                this.setOutlineProvider(viewOutlineProvider);
            }
            this.setClipToOutline(true);
            int n3 = this.getWidth();
            int n4 = this.getHeight();
            this.j.set(0.0f, 0.0f, (float)n3, (float)n4);
            this.h.reset();
            Path path = this.h;
            viewOutlineProvider = this.j;
            f3 = this.g;
            path.addRoundRect((RectF)viewOutlineProvider, f3, f3, Path.Direction.CW);
        } else {
            this.setClipToOutline(false);
        }
        if (bl) {
            this.invalidateOutline();
        }
    }

    public void setRoundPercent(float f3) {
        boolean bl = this.f != f3;
        this.f = f3;
        if (f3 != 0.0f) {
            if (this.h == null) {
                this.h = new Path();
            }
            if (this.j == null) {
                this.j = new RectF();
            }
            if (this.i == null) {
                ViewOutlineProvider viewOutlineProvider;
                this.i = viewOutlineProvider = new ViewOutlineProvider(this){
                    public final MotionButton a;
                    {
                        this.a = motionButton;
                    }

                    public void getOutline(View view, Outline outline) {
                        int n3 = this.a.getWidth();
                        int n4 = this.a.getHeight();
                        outline.setRoundRect(0, 0, n3, n4, (float)Math.min(n3, n4) * this.a.f / 2.0f);
                    }
                };
                this.setOutlineProvider(viewOutlineProvider);
            }
            this.setClipToOutline(true);
            int n3 = this.getWidth();
            int n4 = this.getHeight();
            f3 = (float)Math.min(n3, n4) * this.f / 2.0f;
            this.j.set(0.0f, 0.0f, (float)n3, (float)n4);
            this.h.reset();
            this.h.addRoundRect(this.j, f3, f3, Path.Direction.CW);
        } else {
            this.setClipToOutline(false);
        }
        if (bl) {
            this.invalidateOutline();
        }
    }
}

