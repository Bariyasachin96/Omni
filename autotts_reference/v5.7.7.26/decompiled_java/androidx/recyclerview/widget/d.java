/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.view.MotionEvent
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import o0.x0;

public class d
extends RecyclerView.o
implements RecyclerView.s {
    public static final int[] D = new int[]{16842919};
    public static final int[] E = new int[0];
    public int A;
    public final Runnable B;
    public final RecyclerView.t C;
    public final int a;
    public final int b;
    public final StateListDrawable c;
    public final Drawable d;
    public final int e;
    public final int f;
    public final StateListDrawable g;
    public final Drawable h;
    public final int i;
    public final int j;
    public int k;
    public int l;
    public float m;
    public int n;
    public int o;
    public float p;
    public int q = 0;
    public int r = 0;
    public RecyclerView s;
    public boolean t = false;
    public boolean u = false;
    public int v = 0;
    public int w = 0;
    public final int[] x = new int[2];
    public final int[] y = new int[2];
    public final ValueAnimator z;

    public d(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int n3, int n4, int n5) {
        ValueAnimator valueAnimator;
        this.z = valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        this.A = 0;
        this.B = new Runnable(this){
            public final d c;
            {
                this.c = d3;
            }

            @Override
            public void run() {
                this.c.q(500);
            }
        };
        this.C = new RecyclerView.t(this){
            public final d a;
            {
                this.a = d3;
            }

            @Override
            public void b(RecyclerView recyclerView, int n3, int n4) {
                this.a.B(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
            }
        };
        this.c = stateListDrawable;
        this.d = drawable;
        this.g = stateListDrawable2;
        this.h = drawable2;
        this.e = Math.max(n3, stateListDrawable.getIntrinsicWidth());
        this.f = Math.max(n3, drawable.getIntrinsicWidth());
        this.i = Math.max(n3, stateListDrawable2.getIntrinsicWidth());
        this.j = Math.max(n3, drawable2.getIntrinsicWidth());
        this.a = n4;
        this.b = n5;
        stateListDrawable.setAlpha(255);
        drawable.setAlpha(255);
        valueAnimator.addListener((Animator.AnimatorListener)new c(this));
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new d(this));
        this.j(recyclerView);
    }

    public void A() {
        int n3 = this.A;
        if (n3 != 0) {
            if (n3 != 3) {
                return;
            }
            this.z.cancel();
        }
        this.A = 1;
        ValueAnimator valueAnimator = this.z;
        valueAnimator.setFloatValues(new float[]{((Float)valueAnimator.getAnimatedValue()).floatValue(), 1.0f});
        this.z.setDuration(500L);
        this.z.setStartDelay(0L);
        this.z.start();
    }

    public void B(int n3, int n4) {
        block9: {
            block8: {
                float f3;
                float f4;
                boolean bl;
                int n5;
                int n6;
                int n7;
                int n8;
                block7: {
                    n8 = this.s.computeVerticalScrollRange();
                    boolean bl2 = n8 - (n7 = this.r) > 0 && n7 >= this.a;
                    this.t = bl2;
                    n6 = this.s.computeHorizontalScrollRange();
                    n5 = this.q;
                    bl2 = n6 - n5 > 0 && n5 >= this.a;
                    this.u = bl2;
                    bl = this.t;
                    if (bl || bl2) break block7;
                    if (this.v != 0) {
                        this.y(0);
                        return;
                    }
                    break block8;
                }
                if (bl) {
                    f4 = n4;
                    f3 = n7;
                    this.l = (int)(f3 * (f4 + f3 / 2.0f) / (float)n8);
                    this.k = Math.min(n7, n7 * n7 / n8);
                }
                if (this.u) {
                    f4 = n3;
                    f3 = n5;
                    this.o = (int)(f3 * (f4 + f3 / 2.0f) / (float)n6);
                    this.n = Math.min(n5, n5 * n5 / n6);
                }
                if ((n3 = this.v) == 0 || n3 == 1) break block9;
            }
            return;
        }
        this.y(1);
    }

    public final void C(float f3) {
        int[] nArray = this.p();
        f3 = Math.max((float)nArray[0], Math.min((float)nArray[1], f3));
        if (Math.abs((float)this.l - f3) < 2.0f) {
            return;
        }
        int n3 = this.x(this.m, f3, nArray, this.s.computeVerticalScrollRange(), this.s.computeVerticalScrollOffset(), this.r);
        if (n3 != 0) {
            this.s.scrollBy(0, n3);
        }
        this.m = f3;
    }

    @Override
    public boolean a(RecyclerView recyclerView, MotionEvent motionEvent) {
        int n3 = this.v;
        if (n3 == 1) {
            boolean bl = this.u(motionEvent.getX(), motionEvent.getY());
            boolean bl2 = this.t(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() == 0 && (bl || bl2)) {
                if (bl2) {
                    this.w = 1;
                    this.p = (int)motionEvent.getX();
                } else if (bl) {
                    this.w = 2;
                    this.m = (int)motionEvent.getY();
                }
                this.y(2);
                return true;
            }
            return false;
        }
        return n3 == 2;
    }

    @Override
    public void b(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.v != 0) {
            if (motionEvent.getAction() == 0) {
                boolean bl = this.u(motionEvent.getX(), motionEvent.getY());
                boolean bl2 = this.t(motionEvent.getX(), motionEvent.getY());
                if (bl || bl2) {
                    if (bl2) {
                        this.w = 1;
                        this.p = (int)motionEvent.getX();
                    } else if (bl) {
                        this.w = 2;
                        this.m = (int)motionEvent.getY();
                    }
                    this.y(2);
                    return;
                }
            } else {
                if (motionEvent.getAction() == 1 && this.v == 2) {
                    this.m = 0.0f;
                    this.p = 0.0f;
                    this.y(1);
                    this.w = 0;
                    return;
                }
                if (motionEvent.getAction() == 2 && this.v == 2) {
                    this.A();
                    if (this.w == 1) {
                        this.r(motionEvent.getX());
                    }
                    if (this.w == 2) {
                        this.C(motionEvent.getY());
                    }
                }
            }
        }
    }

    @Override
    public void c(boolean bl) {
    }

    @Override
    public void i(Canvas canvas, RecyclerView recyclerView, RecyclerView.z z3) {
        if (this.q == this.s.getWidth() && this.r == this.s.getHeight()) {
            if (this.A != 0) {
                if (this.t) {
                    this.n(canvas);
                }
                if (this.u) {
                    this.m(canvas);
                }
            }
            return;
        }
        this.q = this.s.getWidth();
        this.r = this.s.getHeight();
        this.y(0);
    }

    public void j(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.s;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                this.l();
            }
            this.s = recyclerView;
            if (recyclerView != null) {
                this.z();
            }
        }
    }

    public final void k() {
        this.s.removeCallbacks(this.B);
    }

    public final void l() {
        this.s.g1(this);
        this.s.h1(this);
        this.s.i1(this.C);
        this.k();
    }

    public final void m(Canvas canvas) {
        int n3 = this.r;
        int n4 = this.i;
        int n5 = n3 - n4;
        int n6 = this.o;
        n3 = this.n;
        this.g.setBounds(0, 0, n3, n4);
        this.h.setBounds(0, 0, this.q, this.j);
        canvas.translate(0.0f, (float)n5);
        this.h.draw(canvas);
        canvas.translate((float)(n6 -= n3 / 2), 0.0f);
        this.g.draw(canvas);
        canvas.translate((float)(-n6), (float)(-n5));
    }

    public final void n(Canvas canvas) {
        int n3 = this.q;
        int n4 = this.e;
        int n5 = n3 - n4;
        int n6 = this.l;
        n3 = this.k;
        n6 -= n3 / 2;
        this.c.setBounds(0, 0, n4, n3);
        this.d.setBounds(0, 0, this.f, this.r);
        if (this.s()) {
            this.d.draw(canvas);
            canvas.translate((float)this.e, (float)n6);
            canvas.scale(-1.0f, 1.0f);
            this.c.draw(canvas);
            canvas.scale(-1.0f, 1.0f);
            canvas.translate((float)(-this.e), (float)(-n6));
            return;
        }
        canvas.translate((float)n5, 0.0f);
        this.d.draw(canvas);
        canvas.translate(0.0f, (float)n6);
        this.c.draw(canvas);
        canvas.translate((float)(-n5), (float)(-n6));
    }

    public final int[] o() {
        int n3;
        int[] nArray = this.y;
        nArray[0] = n3 = this.b;
        nArray[1] = this.q - n3;
        return nArray;
    }

    public final int[] p() {
        int n3;
        int[] nArray = this.x;
        nArray[0] = n3 = this.b;
        nArray[1] = this.r - n3;
        return nArray;
    }

    public void q(int n3) {
        int n4 = this.A;
        if (n4 != 1) {
            if (n4 != 2) {
                return;
            }
        } else {
            this.z.cancel();
        }
        this.A = 3;
        ValueAnimator valueAnimator = this.z;
        valueAnimator.setFloatValues(new float[]{((Float)valueAnimator.getAnimatedValue()).floatValue(), 0.0f});
        this.z.setDuration((long)n3);
        this.z.start();
    }

    public final void r(float f3) {
        int[] nArray = this.o();
        f3 = Math.max((float)nArray[0], Math.min((float)nArray[1], f3));
        if (Math.abs((float)this.o - f3) < 2.0f) {
            return;
        }
        int n3 = this.x(this.p, f3, nArray, this.s.computeHorizontalScrollRange(), this.s.computeHorizontalScrollOffset(), this.q);
        if (n3 != 0) {
            this.s.scrollBy(n3, 0);
        }
        this.p = f3;
    }

    public final boolean s() {
        return x0.y((View)this.s) == 1;
    }

    public boolean t(float f3, float f4) {
        int n3;
        int n4;
        return f4 >= (float)(this.r - this.i) && f3 >= (float)((n4 = this.o) - (n3 = this.n) / 2) && f3 <= (float)(n4 + n3 / 2);
    }

    public boolean u(float f3, float f4) {
        int n3;
        int n4;
        return (this.s() ? f3 <= (float)this.e : f3 >= (float)(this.q - this.e)) && f4 >= (float)((n4 = this.l) - (n3 = this.k) / 2) && f4 <= (float)(n4 + n3 / 2);
    }

    public void v() {
        this.s.invalidate();
    }

    public final void w(int n3) {
        this.k();
        this.s.postDelayed(this.B, n3);
    }

    public final int x(float f3, float f4, int[] nArray, int n3, int n4, int n5) {
        int n6 = nArray[1] - nArray[0];
        if (n6 == 0) {
            return 0;
        }
        if ((n4 += (n5 = (int)((f3 = (f4 - f3) / (float)n6) * (float)(n3 -= n5)))) < n3 && n4 >= 0) {
            return n5;
        }
        return 0;
    }

    public void y(int n3) {
        if (n3 == 2 && this.v != 2) {
            this.c.setState(D);
            this.k();
        }
        if (n3 == 0) {
            this.v();
        } else {
            this.A();
        }
        if (this.v == 2 && n3 != 2) {
            this.c.setState(E);
            this.w(1200);
        } else if (n3 == 1) {
            this.w(1500);
        }
        this.v = n3;
    }

    public final void z() {
        this.s.j(this);
        this.s.m(this);
        this.s.n(this.C);
    }

    public class c
    extends AnimatorListenerAdapter {
        public boolean a;
        public final d b;

        public c(d d3) {
            this.b = d3;
            this.a = false;
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator object) {
            if (this.a) {
                this.a = false;
                return;
            }
            if (((Float)this.b.z.getAnimatedValue()).floatValue() == 0.0f) {
                object = this.b;
                ((d)object).A = 0;
                ((d)object).y(0);
                return;
            }
            object = this.b;
            ((d)object).A = 2;
            ((d)object).v();
        }
    }

    public class d
    implements ValueAnimator.AnimatorUpdateListener {
        public final d a;

        public d(d d3) {
            this.a = d3;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int n3 = (int)(((Float)valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            this.a.c.setAlpha(n3);
            this.a.d.setAlpha(n3);
            this.a.v();
        }
    }
}

