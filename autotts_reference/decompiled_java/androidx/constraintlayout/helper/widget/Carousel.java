/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.a;
import java.util.ArrayList;
import y.d;

public class Carousel
extends MotionHelper {
    public int A = 0;
    public int B = 4;
    public int C = 1;
    public float D = 2.0f;
    public int E = -1;
    public int F = 200;
    public int G = -1;
    public Runnable H;
    public final ArrayList p = new ArrayList();
    public int q = 0;
    public int r = 0;
    public MotionLayout s;
    public int t = -1;
    public boolean u = false;
    public int v = -1;
    public int w = -1;
    public int x = -1;
    public int y = -1;
    public float z = 0.9f;

    public Carousel(Context context) {
        super(context);
        this.H = new Runnable(this){
            public final Carousel c;
            {
                this.c = carousel;
            }

            @Override
            public void run() {
                this.c.s.setProgress(0.0f);
                this.c.J();
                Carousel.H(this.c);
                this.c.r;
                throw null;
            }
        };
    }

    public Carousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.H = new /* invalid duplicate definition of identical inner class */;
        this.I(context, attributeSet);
    }

    public Carousel(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.H = new /* invalid duplicate definition of identical inner class */;
        this.I(context, attributeSet);
    }

    public static /* synthetic */ b H(Carousel carousel) {
        carousel.getClass();
        return null;
    }

    public final void I(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            context = context.obtainStyledAttributes(attributeSet, y.d.Carousel);
            int n3 = context.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = context.getIndex(i3);
                if (n4 == y.d.Carousel_carousel_firstView) {
                    this.t = context.getResourceId(n4, this.t);
                    continue;
                }
                if (n4 == y.d.Carousel_carousel_backwardTransition) {
                    this.v = context.getResourceId(n4, this.v);
                    continue;
                }
                if (n4 == y.d.Carousel_carousel_forwardTransition) {
                    this.w = context.getResourceId(n4, this.w);
                    continue;
                }
                if (n4 == y.d.Carousel_carousel_emptyViewsBehavior) {
                    this.B = context.getInt(n4, this.B);
                    continue;
                }
                if (n4 == y.d.Carousel_carousel_previousState) {
                    this.x = context.getResourceId(n4, this.x);
                    continue;
                }
                if (n4 == y.d.Carousel_carousel_nextState) {
                    this.y = context.getResourceId(n4, this.y);
                    continue;
                }
                if (n4 == y.d.Carousel_carousel_touchUp_dampeningFactor) {
                    this.z = context.getFloat(n4, this.z);
                    continue;
                }
                if (n4 == y.d.Carousel_carousel_touchUpMode) {
                    this.C = context.getInt(n4, this.C);
                    continue;
                }
                if (n4 == y.d.Carousel_carousel_touchUp_velocityThreshold) {
                    this.D = context.getFloat(n4, this.D);
                    continue;
                }
                if (n4 != y.d.Carousel_carousel_infinite) continue;
                this.u = context.getBoolean(n4, this.u);
            }
            context.recycle();
        }
    }

    public final void J() {
    }

    @Override
    public void a(MotionLayout motionLayout, int n3, int n4, float f3) {
        this.G = n3;
    }

    @Override
    public void d(MotionLayout motionLayout, int n3) {
        int n4;
        this.q = n4 = this.r;
        if (n3 == this.y) {
            this.r = n4 + 1;
        } else if (n3 == this.x) {
            this.r = n4 - 1;
        }
        if (this.u) {
            throw null;
        }
        throw null;
    }

    public int getCount() {
        return 0;
    }

    public int getCurrentIndex() {
        return this.r;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.getParent() instanceof MotionLayout) {
            Object object;
            MotionLayout motionLayout = (MotionLayout)this.getParent();
            this.p.clear();
            for (int i3 = 0; i3 < this.d; ++i3) {
                int n3 = this.c[i3];
                object = motionLayout.q(n3);
                if (this.t == n3) {
                    this.A = i3;
                }
                this.p.add(object);
            }
            this.s = motionLayout;
            if (this.C == 2) {
                object = motionLayout.q0(this.w);
                if (object != null) {
                    ((a.b)object).G(5);
                }
                if ((object = this.s.q0(this.v)) != null) {
                    ((a.b)object).G(5);
                }
            }
            this.J();
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.p.clear();
    }

    public void setAdapter(b b3) {
    }

    public void setInfinite(boolean bl) {
        this.u = bl;
    }

    public static interface b {
    }
}

