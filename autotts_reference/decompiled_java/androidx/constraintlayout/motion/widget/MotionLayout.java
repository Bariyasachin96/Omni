/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseBooleanArray
 *  android.util.SparseIntArray
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Interpolator
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.appcompat.app.s;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.a;
import androidx.constraintlayout.motion.widget.b;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import o0.d0;
import u.e;
import u.l;
import x.m;
import x.n;

public class MotionLayout
extends ConstraintLayout
implements d0 {
    public static boolean f1;
    public float A0 = 0.0f;
    public a B;
    public boolean B0 = false;
    public Interpolator C;
    public boolean C0 = false;
    public Interpolator D = null;
    public int D0;
    public float E = 0.0f;
    public int E0;
    public int F = -1;
    public int F0;
    public int G = -1;
    public int G0;
    public int H = -1;
    public int H0;
    public int I = 0;
    public int I0;
    public int J = 0;
    public float J0;
    public boolean K = true;
    public s.d K0;
    public HashMap L = new HashMap();
    public boolean L0 = false;
    public long M = 0L;
    public h M0;
    public float N = 1.0f;
    public Runnable N0 = null;
    public float O = 0.0f;
    public int[] O0 = null;
    public float P = 0.0f;
    public int P0 = 0;
    public long Q;
    public boolean Q0 = false;
    public float R = 0.0f;
    public int R0 = 0;
    public boolean S;
    public HashMap S0;
    public boolean T = false;
    public int T0;
    public boolean U = false;
    public int U0;
    public i V;
    public int V0;
    public float W;
    public Rect W0;
    public boolean X0 = false;
    public j Y0;
    public e Z0;
    public float a0;
    public boolean a1 = false;
    public int b0 = 0;
    public RectF b1;
    public d c0;
    public View c1 = null;
    public boolean d0 = false;
    public Matrix d1 = null;
    public w.b e0 = new w.b();
    public ArrayList e1;
    public c f0 = new c(this);
    public x.b g0;
    public boolean h0 = true;
    public int i0;
    public int j0;
    public int k0;
    public int l0;
    public boolean m0 = false;
    public float n0;
    public float o0;
    public long p0;
    public float q0;
    public boolean r0 = false;
    public ArrayList s0 = null;
    public ArrayList t0 = null;
    public ArrayList u0 = null;
    public CopyOnWriteArrayList v0 = null;
    public int w0 = 0;
    public long x0 = -1L;
    public float y0 = 0.0f;
    public int z0 = 0;

    public MotionLayout(Context context) {
        super(context);
        this.K0 = new s.d();
        this.S0 = new HashMap();
        this.W0 = new Rect();
        this.Y0 = androidx.constraintlayout.motion.widget.MotionLayout$j.c;
        this.Z0 = new e(this);
        this.b1 = new RectF();
        this.e1 = new ArrayList();
        this.t0(null);
    }

    public MotionLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.K0 = new s.d();
        this.S0 = new HashMap();
        this.W0 = new Rect();
        this.Y0 = androidx.constraintlayout.motion.widget.MotionLayout$j.c;
        this.Z0 = new e(this);
        this.b1 = new RectF();
        this.e1 = new ArrayList();
        this.t0(attributeSet);
    }

    public MotionLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.K0 = new s.d();
        this.S0 = new HashMap();
        this.W0 = new Rect();
        this.Y0 = androidx.constraintlayout.motion.widget.MotionLayout$j.c;
        this.Z0 = new e(this);
        this.b1 = new RectF();
        this.e1 = new ArrayList();
        this.t0(attributeSet);
    }

    public static boolean L0(float f3, float f4, float f5) {
        if (f3 > 0.0f) {
            float f6 = f3 / f5;
            return f4 + (f3 * f6 - f5 * f6 * f6 / 2.0f) > 1.0f;
        }
        float f7 = -f3 / f5;
        return f4 + (f3 * f7 + f5 * f7 * f7 / 2.0f) < 0.0f;
    }

    public final Rect A0(u.e e3) {
        this.W0.top = e3.a0();
        this.W0.left = e3.Z();
        Rect rect = this.W0;
        int n3 = e3.Y();
        Rect rect2 = this.W0;
        rect.right = n3 + rect2.left;
        n3 = e3.z();
        e3 = this.W0;
        rect2.bottom = n3 + ((Rect)e3).top;
        return e3;
    }

    public void B0(int n3, float f3, float f4) {
        block11: {
            block8: {
                block9: {
                    block10: {
                        if (this.B == null || this.P == f3) {
                            return;
                        }
                        this.d0 = true;
                        this.M = this.getNanoTime();
                        this.N = (float)this.B.p() / 1000.0f;
                        this.R = f3;
                        this.T = true;
                        if (n3 == 0 || n3 == 1 || n3 == 2) break block8;
                        if (n3 == 4) break block9;
                        if (n3 == 5) break block10;
                        if (n3 == 6 || n3 == 7) break block8;
                        break block11;
                    }
                    if (MotionLayout.L0(f4, this.P, this.B.u())) {
                        this.f0.b(f4, this.P, this.B.u());
                        this.C = this.f0;
                    } else {
                        this.e0.b(this.P, f3, f4, this.N, this.B.u(), this.B.v());
                        this.E = 0.0f;
                        n3 = this.G;
                        this.R = f3;
                        this.G = n3;
                        this.C = this.e0;
                    }
                    break block11;
                }
                this.f0.b(f4, this.P, this.B.u());
                this.C = this.f0;
                break block11;
            }
            if (n3 != 1 && n3 != 7) {
                if (n3 == 2 || n3 == 6) {
                    f3 = 1.0f;
                }
            } else {
                f3 = 0.0f;
            }
            if (this.B.k() == 0) {
                this.e0.b(this.P, f3, f4, this.N, this.B.u(), this.B.v());
            } else {
                this.e0.d(this.P, f3, f4, this.B.B(), this.B.C(), this.B.A(), this.B.D(), this.B.z());
            }
            n3 = this.G;
            this.R = f3;
            this.G = n3;
            this.C = this.e0;
        }
        this.S = false;
        this.M = this.getNanoTime();
        this.invalidate();
    }

    public void C0() {
        this.Z(1.0f);
        this.N0 = null;
    }

    public void D0(Runnable runnable) {
        this.Z(1.0f);
        this.N0 = runnable;
    }

    public void E0() {
        this.Z(0.0f);
    }

    public void F0(int n3) {
        if (!this.isAttachedToWindow()) {
            if (this.M0 == null) {
                this.M0 = new h(this);
            }
            this.M0.d(n3);
            return;
        }
        this.G0(n3, -1, -1);
    }

    public void G0(int n3, int n4, int n5) {
        this.H0(n3, n4, n5, -1);
    }

    public void H0(int n3, int n4, int n5, int n6) {
        float f3;
        Object object;
        int n7;
        Object object2;
        block27: {
            block24: {
                block26: {
                    block25: {
                        object2 = this.B;
                        n7 = n3;
                        if (object2 != null) {
                            object2 = ((a)object2).b;
                            n7 = n3;
                            if (object2 != null) {
                                n4 = ((y.e)object2).a(this.G, n3, n4, n5);
                                n7 = n3;
                                if (n4 != -1) {
                                    n7 = n4;
                                }
                            }
                        }
                        if ((n3 = this.G) == n7) break block24;
                        if (this.F != n7) break block25;
                        this.Z(0.0f);
                        if (n6 > 0) {
                            this.N = (float)n6 / 1000.0f;
                            return;
                        }
                        break block24;
                    }
                    if (this.H != n7) break block26;
                    this.Z(1.0f);
                    if (n6 > 0) {
                        this.N = (float)n6 / 1000.0f;
                        return;
                    }
                    break block24;
                }
                this.H = n7;
                if (n3 == -1) break block27;
                this.setTransition(n3, n7);
                this.Z(1.0f);
                this.P = 0.0f;
                this.C0();
                if (n6 > 0) {
                    this.N = (float)n6 / 1000.0f;
                }
            }
            return;
        }
        n5 = 0;
        this.d0 = false;
        this.R = 1.0f;
        this.O = 0.0f;
        this.P = 0.0f;
        this.Q = this.getNanoTime();
        this.M = this.getNanoTime();
        this.S = false;
        this.C = null;
        if (n6 == -1) {
            this.N = (float)this.B.p() / 1000.0f;
        }
        this.F = -1;
        this.B.X(-1, this.H);
        object2 = new SparseArray();
        if (n6 == 0) {
            this.N = (float)this.B.p() / 1000.0f;
        } else if (n6 > 0) {
            this.N = (float)n6 / 1000.0f;
        }
        n6 = this.getChildCount();
        this.L.clear();
        for (n3 = 0; n3 < n6; ++n3) {
            object = this.getChildAt(n3);
            m m3 = new m((View)object);
            this.L.put(object, m3);
            object2.put(object.getId(), (Object)((m)this.L.get(object)));
        }
        this.T = true;
        this.Z0.e(this.e, null, this.B.l(n7));
        this.y0();
        this.Z0.a();
        this.f0();
        n4 = this.getWidth();
        n7 = this.getHeight();
        if (this.u0 != null) {
            for (n3 = 0; n3 < n6; ++n3) {
                object2 = (m)this.L.get(this.getChildAt(n3));
                if (object2 == null) continue;
                this.B.t((m)object2);
            }
            object2 = this.u0;
            int n8 = ((ArrayList)object2).size();
            for (n3 = 0; n3 < n8; ++n3) {
                object = ((ArrayList)object2).get(n3);
                ((MotionHelper)object).D(this, this.L);
            }
            for (n3 = 0; n3 < n6; ++n3) {
                object2 = (m)this.L.get(this.getChildAt(n3));
                if (object2 == null) continue;
                ((m)object2).I(n4, n7, this.N, this.getNanoTime());
            }
        } else {
            for (n3 = 0; n3 < n6; ++n3) {
                object2 = (m)this.L.get(this.getChildAt(n3));
                if (object2 == null) continue;
                this.B.t((m)object2);
                ((m)object2).I(n4, n7, this.N, this.getNanoTime());
            }
        }
        if ((f3 = this.B.E()) != 0.0f) {
            float f4;
            float f5 = Float.MAX_VALUE;
            float f6 = -3.4028235E38f;
            n4 = 0;
            while (true) {
                if (n4 >= n6) break;
                object2 = (m)this.L.get(this.getChildAt(n4));
                f4 = ((m)object2).n();
                f4 = ((m)object2).o() + f4;
                f5 = Math.min(f5, f4);
                f6 = Math.max(f6, f4);
                ++n4;
            }
            for (n3 = n5; n3 < n6; ++n3) {
                object2 = (m)this.L.get(this.getChildAt(n3));
                f4 = ((m)object2).n();
                float f7 = ((m)object2).o();
                ((m)object2).o = 1.0f / (1.0f - f3);
                ((m)object2).n = f3 - (f4 + f7 - f5) * f3 / (f6 - f5);
            }
        }
        this.O = 0.0f;
        this.P = 0.0f;
        this.T = true;
        this.invalidate();
    }

    public void I0() {
        this.Z0.e(this.e, this.B.l(this.F), this.B.l(this.H));
        this.y0();
    }

    public void J0(int n3, androidx.constraintlayout.widget.b b3) {
        a a4 = this.B;
        if (a4 != null) {
            a4.U(n3, b3);
        }
        this.I0();
        if (this.G == n3) {
            b3.i(this);
        }
    }

    public void K0(int n3, View ... viewArray) {
        a a4 = this.B;
        if (a4 != null) {
            a4.c0(n3, viewArray);
            return;
        }
        Log.e((String)"MotionLayout", (String)" no motionScene");
    }

    public void Z(float f3) {
        float f4;
        a a4;
        block5: {
            block4: {
                a4 = this.B;
                if (a4 == null) break block4;
                f4 = this.P;
                float f5 = this.O;
                if (f4 != f5 && this.S) {
                    this.P = f5;
                }
                if ((f4 = this.P) != f3) break block5;
            }
            return;
        }
        this.d0 = false;
        this.R = f3;
        this.N = (float)a4.p() / 1000.0f;
        this.setProgress(this.R);
        this.C = null;
        this.D = this.B.s();
        this.S = false;
        this.M = this.getNanoTime();
        this.T = true;
        this.O = f4;
        this.P = f4;
        this.invalidate();
    }

    public boolean a0(int n3, m m3) {
        a a4 = this.B;
        if (a4 != null) {
            return a4.g(n3, m3);
        }
        return false;
    }

    @Override
    public void b(View view, View view2, int n3, int n4) {
        this.p0 = this.getNanoTime();
        this.q0 = 0.0f;
        this.n0 = 0.0f;
        this.o0 = 0.0f;
    }

    public final boolean b0(View view, MotionEvent motionEvent, float f3, float f4) {
        Matrix matrix = view.getMatrix();
        if (matrix.isIdentity()) {
            motionEvent.offsetLocation(f3, f4);
            boolean bl = view.onTouchEvent(motionEvent);
            motionEvent.offsetLocation(-f3, -f4);
            return bl;
        }
        motionEvent = MotionEvent.obtain((MotionEvent)motionEvent);
        motionEvent.offsetLocation(f3, f4);
        if (this.d1 == null) {
            this.d1 = new Matrix();
        }
        matrix.invert(this.d1);
        motionEvent.transform(this.d1);
        boolean bl = view.onTouchEvent(motionEvent);
        motionEvent.recycle();
        return bl;
    }

    public final void c0() {
        a a4 = this.B;
        if (a4 == null) {
            Log.e((String)"MotionLayout", (String)"CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
            return;
        }
        int n3 = a4.F();
        a4 = this.B;
        this.d0(n3, a4.l(a4.F()));
        a4 = new SparseIntArray();
        SparseIntArray sparseIntArray = new SparseIntArray();
        ArrayList arrayList = this.B.o();
        int n4 = arrayList.size();
        n3 = 0;
        while (n3 < n4) {
            StringBuilder stringBuilder;
            Object object = arrayList.get(n3);
            int n5 = n3 + 1;
            Object object2 = (a.b)object;
            object = this.B.c;
            this.e0((a.b)object2);
            n3 = ((a.b)object2).A();
            int n6 = ((a.b)object2).y();
            object = x.a.c(this.getContext(), n3);
            object2 = x.a.c(this.getContext(), n6);
            if (a4.get(n3) == n6) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: two transitions with the same start and end ");
                stringBuilder.append((String)object);
                stringBuilder.append("->");
                stringBuilder.append((String)object2);
                Log.e((String)"MotionLayout", (String)stringBuilder.toString());
            }
            if (sparseIntArray.get(n6) == n3) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: you can't have reverse transitions");
                stringBuilder.append((String)object);
                stringBuilder.append("->");
                stringBuilder.append((String)object2);
                Log.e((String)"MotionLayout", (String)stringBuilder.toString());
            }
            a4.put(n3, n6);
            sparseIntArray.put(n6, n3);
            if (this.B.l(n3) == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(" no such constraintSetStart ");
                ((StringBuilder)object2).append((String)object);
                Log.e((String)"MotionLayout", (String)((StringBuilder)object2).toString());
            }
            n3 = n5;
            if (this.B.l(n6) != null) continue;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" no such constraintSetEnd ");
            ((StringBuilder)object2).append((String)object);
            Log.e((String)"MotionLayout", (String)((StringBuilder)object2).toString());
            n3 = n5;
        }
    }

    public final void d0(int n3, androidx.constraintlayout.widget.b b3) {
        CharSequence charSequence;
        Object object;
        String string = x.a.c(this.getContext(), n3);
        int n4 = this.getChildCount();
        int n5 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            object = this.getChildAt(n3);
            int n6 = object.getId();
            if (n6 == -1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("CHECK: ");
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" ALL VIEWS SHOULD HAVE ID's ");
                ((StringBuilder)charSequence).append(object.getClass().getName());
                ((StringBuilder)charSequence).append(" does not!");
                Log.w((String)"MotionLayout", (String)((StringBuilder)charSequence).toString());
            }
            if (b3.v(n6) != null) continue;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("CHECK: ");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" NO CONSTRAINTS for ");
            ((StringBuilder)charSequence).append(x.a.d((View)object));
            Log.w((String)"MotionLayout", (String)((StringBuilder)charSequence).toString());
        }
        object = b3.x();
        for (n3 = n5; n3 < ((int[])object).length; ++n3) {
            StringBuilder stringBuilder;
            n5 = object[n3];
            charSequence = x.a.c(this.getContext(), n5);
            if (this.findViewById(object[n3]) == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: ");
                stringBuilder.append(string);
                stringBuilder.append(" NO View matches id ");
                stringBuilder.append((String)charSequence);
                Log.w((String)"MotionLayout", (String)stringBuilder.toString());
            }
            if (b3.w(n5) == -1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: ");
                stringBuilder.append(string);
                stringBuilder.append("(");
                stringBuilder.append((String)charSequence);
                stringBuilder.append(") no LAYOUT_HEIGHT");
                Log.w((String)"MotionLayout", (String)stringBuilder.toString());
            }
            if (b3.B(n5) != -1) continue;
            stringBuilder = new StringBuilder();
            stringBuilder.append("CHECK: ");
            stringBuilder.append(string);
            stringBuilder.append("(");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(") no LAYOUT_HEIGHT");
            Log.w((String)"MotionLayout", (String)stringBuilder.toString());
        }
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        Object object;
        int n3;
        int n4;
        Object object2 = this.u0;
        int n5 = 0;
        if (object2 != null) {
            n4 = ((ArrayList)object2).size();
            for (n3 = 0; n3 < n4; ++n3) {
                object = ((ArrayList)object2).get(n3);
                ((MotionHelper)object).C(canvas);
            }
        }
        this.h0(false);
        object2 = this.B;
        if (object2 != null && (object2 = ((a)object2).r) != null) {
            ((androidx.constraintlayout.motion.widget.d)object2).c();
        }
        super.dispatchDraw(canvas);
        if (this.B != null) {
            if ((this.b0 & 1) == 1 && !this.isInEditMode()) {
                ++this.w0;
                long l3 = this.getNanoTime();
                long l4 = this.x0;
                if (l4 != -1L) {
                    if ((l4 = l3 - l4) > 200000000L) {
                        this.y0 = (float)((int)((float)this.w0 / ((float)l4 * 1.0E-9f) * 100.0f)) / 100.0f;
                        this.w0 = 0;
                        this.x0 = l3;
                    }
                } else {
                    this.x0 = l3;
                }
                object = new Paint();
                object.setTextSize(42.0f);
                float f3 = (float)((int)(this.getProgress() * 1000.0f)) / 10.0f;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(this.y0);
                ((StringBuilder)object2).append(" fps ");
                ((StringBuilder)object2).append(x.a.e(this, this.F));
                ((StringBuilder)object2).append(" -> ");
                object2 = ((StringBuilder)object2).toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object2);
                stringBuilder.append(x.a.e(this, this.H));
                stringBuilder.append(" (progress: ");
                stringBuilder.append(f3);
                stringBuilder.append(" ) state=");
                n3 = this.G;
                object2 = n3 == -1 ? "undefined" : x.a.e(this, n3);
                stringBuilder.append((String)object2);
                object2 = stringBuilder.toString();
                object.setColor(-16777216);
                canvas.drawText((String)object2, 11.0f, (float)(this.getHeight() - 29), (Paint)object);
                object.setColor(-7864184);
                canvas.drawText((String)object2, 10.0f, (float)(this.getHeight() - 30), (Paint)object);
            }
            if (this.b0 > 1) {
                if (this.c0 == null) {
                    this.c0 = new d(this);
                }
                this.c0.a(canvas, this.L, this.B.p(), this.b0);
            }
            if ((object = this.u0) != null) {
                n4 = ((ArrayList)object).size();
                for (n3 = n5; n3 < n4; ++n3) {
                    object2 = ((ArrayList)object).get(n3);
                    ((MotionHelper)object2).B(canvas);
                }
            }
        }
    }

    public final void e0(a.b b3) {
        if (b3.A() == b3.y()) {
            Log.e((String)"MotionLayout", (String)"CHECK: start and end constraint set should not be the same!");
        }
    }

    public final void f0() {
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            m m3 = (m)this.L.get(view);
            if (m3 == null) continue;
            m3.E(view);
        }
    }

    @Override
    public void g(View object, int n3) {
        float f3;
        object = this.B;
        if (object != null && (f3 = this.q0) != 0.0f) {
            ((a)object).Q(this.n0 / f3, this.o0 / f3);
        }
    }

    public void g0(boolean bl) {
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = this.getChildAt(i3);
            if ((object = (m)this.L.get(object)) == null) continue;
            ((m)object).f(bl);
        }
    }

    public int[] getConstraintSetIds() {
        a a4 = this.B;
        if (a4 == null) {
            return null;
        }
        return a4.n();
    }

    public int getCurrentState() {
        return this.G;
    }

    public ArrayList<a.b> getDefinedTransitions() {
        a a4 = this.B;
        if (a4 == null) {
            return null;
        }
        return a4.o();
    }

    public x.b getDesignTool() {
        if (this.g0 == null) {
            this.g0 = new x.b(this);
        }
        return this.g0;
    }

    public int getEndState() {
        return this.H;
    }

    public long getNanoTime() {
        return System.nanoTime();
    }

    public float getProgress() {
        return this.P;
    }

    public a getScene() {
        return this.B;
    }

    public int getStartState() {
        return this.F;
    }

    public float getTargetPosition() {
        return this.R;
    }

    public Bundle getTransitionState() {
        if (this.M0 == null) {
            this.M0 = new h(this);
        }
        this.M0.c();
        return this.M0.b();
    }

    public long getTransitionTimeMs() {
        a a4 = this.B;
        if (a4 != null) {
            this.N = (float)a4.p() / 1000.0f;
        }
        return (long)(this.N * 1000.0f);
    }

    public float getVelocity() {
        return this.E;
    }

    @Override
    public void h(View view, int n3, int n4, int[] nArray, int n5) {
        block9: {
            float f3;
            float f4;
            float f5;
            a.b b3;
            a a4;
            block10: {
                b b4;
                a4 = this.B;
                if (a4 == null || (b3 = a4.c) == null || !b3.C()) break block9;
                boolean bl = b3.C();
                int n6 = -1;
                if (bl && (b4 = b3.B()) != null && (n5 = b4.q()) != -1 && view.getId() != n5) break block9;
                if (!a4.w()) break block10;
                b4 = b3.B();
                n5 = n6;
                if (b4 != null) {
                    n5 = n6;
                    if ((b4.e() & 4) != 0) {
                        n5 = n4;
                    }
                }
                if (((f5 = this.O) == 1.0f || f5 == 0.0f) && view.canScrollVertically(n5)) break block9;
            }
            if (b3.B() != null && (b3.B().e() & 1) != 0) {
                f4 = a4.x(n3, n4);
                f5 = this.P;
                if (f5 <= 0.0f && f4 < 0.0f || f5 >= 1.0f && f4 > 0.0f) {
                    view.setNestedScrollingEnabled(false);
                    view.post(new Runnable(this, view){
                        public final View c;
                        public final MotionLayout d;
                        {
                            this.d = motionLayout;
                            this.c = view;
                        }

                        @Override
                        public void run() {
                            this.c.setNestedScrollingEnabled(true);
                        }
                    });
                    return;
                }
            }
            f5 = this.O;
            long l3 = this.getNanoTime();
            this.n0 = f4 = (float)n3;
            this.o0 = f3 = (float)n4;
            this.q0 = (float)((double)(l3 - this.p0) * 1.0E-9);
            this.p0 = l3;
            a4.P(f4, f3);
            if (f5 != this.O) {
                nArray[0] = n3;
                nArray[1] = n4;
            }
            this.h0(false);
            if (nArray[0] != 0 || nArray[1] != 0) {
                this.m0 = true;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void h0(boolean bl) {
        int n3;
        block63: {
            int n4;
            block62: {
                int n5;
                int n6;
                float f3;
                block61: {
                    block51: {
                        block60: {
                            Object object;
                            float f4;
                            float f5;
                            Interpolator interpolator;
                            long l3;
                            float f6;
                            int n7;
                            block56: {
                                block59: {
                                    block58: {
                                        block57: {
                                            block54: {
                                                block55: {
                                                    block52: {
                                                        block53: {
                                                            float f7;
                                                            float f8;
                                                            block50: {
                                                                if (this.Q == -1L) {
                                                                    this.Q = this.getNanoTime();
                                                                }
                                                                if ((f3 = this.P) > 0.0f && f3 < 1.0f) {
                                                                    this.G = -1;
                                                                }
                                                                boolean bl2 = this.r0;
                                                                n6 = 1;
                                                                n5 = 1;
                                                                n7 = 0;
                                                                n3 = 0;
                                                                if (bl2) break block50;
                                                                n4 = n7;
                                                                if (!this.T) break block51;
                                                                if (bl) break block50;
                                                                n4 = n7;
                                                                if (this.R == f3) break block51;
                                                            }
                                                            f6 = Math.signum(this.R - f3);
                                                            l3 = this.getNanoTime();
                                                            interpolator = this.C;
                                                            f5 = !(interpolator instanceof n) ? (float)(l3 - this.Q) * f6 * 1.0E-9f / this.N : 0.0f;
                                                            f3 = this.P + f5;
                                                            if (this.S) {
                                                                f3 = this.R;
                                                            }
                                                            if ((n7 = (int)((f8 = f6 - 0.0f) == 0.0f ? 0 : (f8 > 0.0f ? 1 : -1))) > 0 && f3 >= this.R || f6 <= 0.0f && f3 <= this.R) {
                                                                f3 = this.R;
                                                                this.T = false;
                                                                n4 = 1;
                                                            } else {
                                                                n4 = 0;
                                                            }
                                                            this.P = f3;
                                                            this.O = f3;
                                                            this.Q = l3;
                                                            if (interpolator == null || n4 != 0) break block52;
                                                            if (!this.d0) break block53;
                                                            f4 = interpolator.getInterpolation((float)(l3 - this.M) * 1.0E-9f);
                                                            object = this.C;
                                                            interpolator = this.e0;
                                                            n4 = object == interpolator ? (interpolator.c() ? 2 : 1) : 0;
                                                            this.P = f4;
                                                            this.Q = l3;
                                                            interpolator = this.C;
                                                            f3 = f4;
                                                            if (!(interpolator instanceof n)) break block54;
                                                            this.E = f7 = ((n)interpolator).a();
                                                            if (Math.abs(f7) * this.N <= 1.0E-5f && n4 == 2) {
                                                                this.T = false;
                                                            }
                                                            f5 = f4;
                                                            if (f7 > 0.0f) {
                                                                f5 = f4;
                                                                if (f4 >= 1.0f) {
                                                                    this.P = 1.0f;
                                                                    this.T = false;
                                                                    f5 = 1.0f;
                                                                }
                                                            }
                                                            f3 = f5;
                                                            if (f7 < 0.0f) {
                                                                f3 = f5;
                                                                if (f5 <= 0.0f) {
                                                                    this.P = 0.0f;
                                                                    this.T = false;
                                                                    f3 = 0.0f;
                                                                }
                                                            }
                                                            break block54;
                                                        }
                                                        f4 = interpolator.getInterpolation(f3);
                                                        interpolator = this.C;
                                                        this.E = interpolator instanceof n ? ((n)interpolator).a() : (interpolator.getInterpolation(f3 + f5) - f4) * f6 / f5;
                                                        f3 = f4;
                                                        break block55;
                                                    }
                                                    this.E = f5;
                                                }
                                                n4 = 0;
                                            }
                                            if (Math.abs(this.E) > 1.0E-5f) {
                                                this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.e);
                                            }
                                            f5 = f3;
                                            if (n4 == 1) break block56;
                                            if (n7 > 0 && f3 >= this.R) break block57;
                                            f4 = f3;
                                            if (!(f6 <= 0.0f)) break block58;
                                            f4 = f3;
                                            if (!(f3 <= this.R)) break block58;
                                        }
                                        f4 = this.R;
                                        this.T = false;
                                    }
                                    if (f4 >= 1.0f) break block59;
                                    f5 = f4;
                                    if (!(f4 <= 0.0f)) break block56;
                                }
                                this.T = false;
                                this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.f);
                                f5 = f4;
                            }
                            int n8 = this.getChildCount();
                            this.r0 = false;
                            l3 = this.getNanoTime();
                            this.J0 = f5;
                            interpolator = this.D;
                            f3 = interpolator == null ? f5 : interpolator.getInterpolation(f5);
                            interpolator = this.D;
                            if (interpolator != null) {
                                this.E = f4 = interpolator.getInterpolation(f6 / this.N + f5);
                                this.E = f4 - this.D.getInterpolation(f5);
                            }
                            for (n4 = 0; n4 < n8; ++n4) {
                                interpolator = this.getChildAt(n4);
                                object = (m)this.L.get(interpolator);
                                if (object == null) continue;
                                this.r0 |= ((m)object).x((View)interpolator, f3, l3, this.K0);
                            }
                            n4 = n7 > 0 && f5 >= this.R || f6 <= 0.0f && f5 <= this.R ? 1 : 0;
                            if (!this.r0 && !this.T && n4 != 0) {
                                this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.f);
                            }
                            if (this.C0) {
                                this.requestLayout();
                            }
                            this.r0 = n4 ^ 1 | this.r0;
                            n4 = n3;
                            if (f5 <= 0.0f) {
                                n8 = this.F;
                                n4 = n3;
                                if (n8 != -1) {
                                    n4 = n3;
                                    if (this.G != n8) {
                                        this.G = n8;
                                        this.B.l(n8).g(this);
                                        this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.f);
                                        n4 = 1;
                                    }
                                }
                            }
                            n3 = n4;
                            if ((double)f5 >= 1.0) {
                                n8 = this.G;
                                int n9 = this.H;
                                n3 = n4;
                                if (n8 != n9) {
                                    this.G = n9;
                                    this.B.l(n9).g(this);
                                    this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.f);
                                    n3 = 1;
                                }
                            }
                            if (!this.r0 && !this.T) {
                                if (n7 > 0 && f5 == 1.0f || f6 < 0.0f && f5 == 0.0f) {
                                    this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.f);
                                }
                            } else {
                                this.invalidate();
                            }
                            n4 = n3;
                            if (this.r0) break block51;
                            n4 = n3;
                            if (this.T) break block51;
                            if (n7 > 0 && f5 == 1.0f) break block60;
                            n4 = n3;
                            if (!(f6 < 0.0f)) break block51;
                            n4 = n3;
                            if (f5 != 0.0f) break block51;
                        }
                        this.w0();
                        n4 = n3;
                    }
                    if (!((f3 = this.P) >= 1.0f)) break block61;
                    n6 = this.G;
                    n3 = this.H;
                    if (n6 != n3) {
                        n4 = n5;
                    }
                    this.G = n3;
                    break block62;
                }
                n3 = n4;
                if (!(f3 <= 0.0f)) break block63;
                n5 = this.G;
                n3 = this.F;
                if (n5 != n3) {
                    n4 = n6;
                }
                this.G = n3;
            }
            n3 = n4;
        }
        this.a1 |= n3;
        if (n3 != 0 && !this.L0) {
            this.requestLayout();
        }
        this.O = this.P;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void i0() {
        int n3;
        float f3;
        float f4;
        Object object;
        long l3;
        block12: {
            block11: {
                float f5;
                float f6 = Math.signum(this.R - this.P);
                l3 = this.getNanoTime();
                object = this.C;
                float f7 = !(object instanceof w.b) ? (float)(l3 - this.Q) * f6 * 1.0E-9f / this.N : 0.0f;
                f4 = this.P + f7;
                if (this.S) {
                    f4 = this.R;
                }
                f3 = (f5 = f6 - 0.0f) == 0.0f ? 0 : (f5 > 0.0f ? 1 : -1);
                int n4 = 0;
                if (f3 > 0 && f4 >= this.R || f6 <= 0.0f && f4 <= this.R) {
                    f4 = this.R;
                    n3 = 1;
                } else {
                    n3 = 0;
                }
                f7 = f4;
                if (object != null) {
                    f7 = f4;
                    if (n3 == 0) {
                        f7 = this.d0 ? object.getInterpolation((float)(l3 - this.M) * 1.0E-9f) : object.getInterpolation(f4);
                    }
                }
                if (f3 > 0 && f7 >= this.R) break block11;
                f4 = f7;
                if (!(f6 <= 0.0f)) break block12;
                f4 = f7;
                if (!(f7 <= this.R)) break block12;
            }
            f4 = this.R;
        }
        this.J0 = f4;
        f3 = this.getChildCount();
        l3 = this.getNanoTime();
        object = this.D;
        if (object != null) {
            f4 = object.getInterpolation(f4);
        }
        for (n3 = n4; n3 < f3; ++n3) {
            View view = this.getChildAt(n3);
            object = (m)this.L.get(view);
            if (object == null) continue;
            ((m)object).x(view, f4, l3, this.K0);
        }
        if (this.C0) {
            this.requestLayout();
        }
    }

    public final void j0() {
        Object object;
        if ((this.V != null || (object = this.v0) != null && !((CopyOnWriteArrayList)object).isEmpty()) && this.A0 != this.O) {
            float f3;
            if (this.z0 != -1) {
                this.l0();
                this.B0 = true;
            }
            this.z0 = -1;
            this.A0 = f3 = this.O;
            object = this.V;
            if (object != null) {
                object.a(this, this.F, this.H, f3);
            }
            if ((object = this.v0) != null) {
                object = ((CopyOnWriteArrayList)object).iterator();
                while (object.hasNext()) {
                    ((i)object.next()).a(this, this.F, this.H, this.O);
                }
            }
            this.B0 = true;
        }
    }

    @Override
    public void k(View view, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
        if (this.m0 || n3 != 0 || n4 != 0) {
            nArray[0] = nArray[0] + n5;
            nArray[1] = nArray[1] + n6;
        }
        this.m0 = false;
    }

    public void k0() {
        Object object;
        if ((this.V != null || (object = this.v0) != null && !((CopyOnWriteArrayList)object).isEmpty()) && this.z0 == -1) {
            int n3;
            this.z0 = this.G;
            if (!this.e1.isEmpty()) {
                object = this.e1;
                n3 = (Integer)((ArrayList)object).get(((ArrayList)object).size() - 1);
            } else {
                n3 = -1;
            }
            int n4 = this.G;
            if (n3 != n4 && n4 != -1) {
                this.e1.add(n4);
            }
        }
        this.x0();
        object = this.N0;
        if (object != null) {
            object.run();
            this.N0 = null;
        }
        if ((object = (Object)this.O0) != null && this.P0 > 0) {
            this.F0((int)object[0]);
            object = this.O0;
            System.arraycopy(object, 1, object, 0, ((Object)object).length - 1);
            --this.P0;
        }
    }

    @Override
    public void l(View view, int n3, int n4, int n5, int n6, int n7) {
    }

    public final void l0() {
        Object object = this.V;
        if (object != null) {
            object.b(this, this.F, this.H);
        }
        if ((object = this.v0) != null) {
            object = ((CopyOnWriteArrayList)object).iterator();
            while (object.hasNext()) {
                ((i)object.next()).b(this, this.F, this.H);
            }
        }
    }

    @Override
    public boolean m(View object, View view, int n3, int n4) {
        object = this.B;
        return object != null && (object = ((a)object).c) != null && ((a.b)object).B() != null && (this.B.c.B().e() & 2) == 0;
        {
        }
    }

    public void m0(int n3, boolean bl, float f3) {
        Object object = this.V;
        if (object != null) {
            object.c(this, n3, bl, f3);
        }
        if ((object = this.v0) != null) {
            object = ((CopyOnWriteArrayList)object).iterator();
            while (object.hasNext()) {
                ((i)object.next()).c(this, n3, bl, f3);
            }
        }
    }

    public void n0(int n3, float f3, float f4, float f5, float[] object) {
        Object object2 = this.L;
        Object object3 = this.q(n3);
        if ((object2 = (m)((HashMap)object2).get(object3)) != null) {
            ((m)object2).l(f3, f4, f5, (float[])object);
            f4 = object3.getY();
            this.W = f3;
            this.a0 = f4;
            return;
        }
        if (object3 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("");
            ((StringBuilder)object).append(n3);
            object = ((StringBuilder)object).toString();
        } else {
            object = object3.getContext().getResources().getResourceName(n3);
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("WARNING could not find view id ");
        ((StringBuilder)object3).append((String)object);
        Log.w((String)"MotionLayout", (String)((StringBuilder)object3).toString());
    }

    public androidx.constraintlayout.widget.b o0(int n3) {
        a a4 = this.B;
        if (a4 == null) {
            return null;
        }
        return a4.l(n3);
    }

    public void onAttachedToWindow() {
        int n3;
        super.onAttachedToWindow();
        Object object = this.getDisplay();
        if (object != null) {
            this.V0 = object.getRotation();
        }
        if ((object = this.B) != null && (n3 = this.G) != -1) {
            androidx.constraintlayout.widget.b b3 = ((a)object).l(n3);
            this.B.T(this);
            object = this.u0;
            if (object != null) {
                int n4 = ((ArrayList)object).size();
                for (n3 = 0; n3 < n4; ++n3) {
                    Object e3 = ((ArrayList)object).get(n3);
                    ((MotionHelper)e3).A(this);
                }
            }
            if (b3 != null) {
                b3.i(this);
            }
            this.F = this.G;
        }
        this.w0();
        object = this.M0;
        if (object != null) {
            if (this.X0) {
                this.post(new Runnable(this){
                    public final MotionLayout c;
                    {
                        this.c = motionLayout;
                    }

                    @Override
                    public void run() {
                        this.c.M0.a();
                    }
                });
                return;
            }
            ((h)object).a();
            return;
        }
        object = this.B;
        if (object != null && (object = ((a)object).c) != null && ((a.b)object).x() == 4) {
            this.C0();
            this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.d);
            this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.e);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Object object = this.B;
        if (object != null && this.K) {
            object = ((a)object).r;
            if (object != null) {
                ((androidx.constraintlayout.motion.widget.d)object).h(motionEvent);
            }
            if ((object = this.B.c) != null && ((a.b)object).C() && (object = ((a.b)object).B()) != null) {
                RectF rectF;
                if (motionEvent.getAction() == 0 && (rectF = ((b)object).p(this, new RectF())) != null && !rectF.contains(motionEvent.getX(), motionEvent.getY())) {
                    return false;
                }
                int n3 = ((b)object).q();
                if (n3 != -1) {
                    object = this.c1;
                    if (object == null || object.getId() != n3) {
                        this.c1 = this.findViewById(n3);
                    }
                    if ((object = this.c1) != null) {
                        this.b1.set((float)object.getLeft(), (float)this.c1.getTop(), (float)this.c1.getRight(), (float)this.c1.getBottom());
                        if (this.b1.contains(motionEvent.getX(), motionEvent.getY()) && !this.s0(this.c1.getLeft(), this.c1.getTop(), this.c1, motionEvent)) {
                            return this.onTouchEvent(motionEvent);
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        void var6_7;
        block5: {
            block6: {
                this.L0 = true;
                try {
                    if (this.B == null) {
                        super.onLayout(bl, n3, n4, n5, n6);
                        this.L0 = false;
                        return;
                    }
                }
                catch (Throwable throwable) {
                    break block5;
                }
                n3 = n5 - n3;
                n4 = n6 - n4;
                try {
                    if (this.k0 == n3 && this.l0 == n4) break block6;
                }
                catch (Throwable throwable) {
                    break block5;
                }
                this.y0();
                this.h0(true);
            }
            this.k0 = n3;
            this.l0 = n4;
            this.i0 = n3;
            this.j0 = n4;
            this.L0 = false;
            return;
        }
        this.L0 = false;
        throw var6_7;
    }

    @Override
    public void onMeasure(int n3, int n4) {
        if (this.B == null) {
            super.onMeasure(n3, n4);
            return;
        }
        int n5 = this.I;
        int n6 = 0;
        n5 = n5 == n3 && this.J == n4 ? 0 : 1;
        if (this.a1) {
            this.a1 = false;
            this.w0();
            this.x0();
            n5 = 1;
        }
        if (this.j) {
            n5 = 1;
        }
        this.I = n3;
        this.J = n4;
        int n7 = this.B.F();
        int n8 = this.B.q();
        if ((n5 != 0 || this.Z0.f(n7, n8)) && this.F != -1) {
            super.onMeasure(n3, n4);
            this.Z0.e(this.e, this.B.l(n7), this.B.l(n8));
            this.Z0.h();
            this.Z0.i(n7, n8);
            n3 = n6;
        } else {
            if (n5 != 0) {
                super.onMeasure(n3, n4);
            }
            n3 = 1;
        }
        if (this.C0 || n3 != 0) {
            n4 = this.getPaddingTop();
            n5 = this.getPaddingBottom();
            n6 = this.getPaddingLeft();
            n3 = this.getPaddingRight();
            n3 = this.e.Y() + (n6 + n3);
            n4 = this.e.z() + (n4 + n5);
            n5 = this.H0;
            if (n5 == Integer.MIN_VALUE || n5 == 0) {
                n3 = this.D0;
                n3 = (int)((float)n3 + this.J0 * (float)(this.F0 - n3));
                this.requestLayout();
            }
            if ((n5 = this.I0) == Integer.MIN_VALUE || n5 == 0) {
                n4 = this.E0;
                n4 = (int)((float)n4 + this.J0 * (float)(this.G0 - n4));
                this.requestLayout();
            }
            this.setMeasuredDimension(n3, n4);
        }
        this.i0();
    }

    public boolean onNestedFling(View view, float f3, float f4, boolean bl) {
        return false;
    }

    public boolean onNestedPreFling(View view, float f3, float f4) {
        return false;
    }

    public void onRtlPropertiesChanged(int n3) {
        a a4 = this.B;
        if (a4 != null) {
            a4.W(this.t());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Object object = this.B;
        if (object != null && this.K && ((a)object).b0()) {
            object = this.B.c;
            if (object != null && !((a.b)object).C()) {
                return super.onTouchEvent(motionEvent);
            }
            this.B.R(motionEvent, this.getCurrentState(), this);
            if (this.B.c.D(4)) {
                return this.B.c.B().r();
            }
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof MotionHelper) {
            view = (MotionHelper)view;
            if (this.v0 == null) {
                this.v0 = new CopyOnWriteArrayList();
            }
            this.v0.add(view);
            if (view.z()) {
                if (this.s0 == null) {
                    this.s0 = new ArrayList();
                }
                this.s0.add(view);
            }
            if (view.y()) {
                if (this.t0 == null) {
                    this.t0 = new ArrayList();
                }
                this.t0.add(view);
            }
            if (view.x()) {
                if (this.u0 == null) {
                    this.u0 = new ArrayList();
                }
                this.u0.add(view);
            }
        }
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ArrayList arrayList = this.s0;
        if (arrayList != null) {
            arrayList.remove(view);
        }
        if ((arrayList = this.t0) != null) {
            arrayList.remove(view);
        }
    }

    public m p0(int n3) {
        return (m)this.L.get(this.findViewById(n3));
    }

    public a.b q0(int n3) {
        return this.B.G(n3);
    }

    public void r0(View view, float f3, float f4, float[] fArray, int n3) {
        Object object;
        float f5 = this.E;
        float f6 = this.P;
        if (this.C != null) {
            float f7 = Math.signum(this.R - f6);
            f5 = this.C.getInterpolation(this.P + 1.0E-5f);
            f6 = this.C.getInterpolation(this.P);
            f5 = f7 * ((f5 - f6) / 1.0E-5f) / this.N;
        }
        if ((object = this.C) instanceof n) {
            f5 = ((n)object).a();
        }
        object = (m)this.L.get(view);
        if ((n3 & 1) == 0) {
            ((m)object).r(f6, view.getWidth(), view.getHeight(), f3, f4, fArray);
        } else {
            ((m)object).l(f6, f3, f4, fArray);
        }
        if (n3 < 2) {
            fArray[0] = fArray[0] * f5;
            fArray[1] = fArray[1] * f5;
        }
    }

    @Override
    public void requestLayout() {
        Object object;
        if (!this.C0 && this.G == -1 && (object = this.B) != null && (object = ((a)object).c) != null) {
            int n3 = ((a.b)object).z();
            if (n3 != 0) {
                if (n3 == 2) {
                    int n4 = this.getChildCount();
                    for (n3 = 0; n3 < n4; ++n3) {
                        object = this.getChildAt(n3);
                        ((m)this.L.get(object)).z();
                    }
                }
            } else {
                return;
            }
        }
        super.requestLayout();
    }

    public final boolean s0(float f3, float f4, View view, MotionEvent motionEvent) {
        boolean bl;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            for (int i3 = viewGroup.getChildCount() - 1; i3 >= 0; --i3) {
                View view2 = viewGroup.getChildAt(i3);
                if (!this.s0((float)view2.getLeft() + f3 - (float)view.getScrollX(), (float)view2.getTop() + f4 - (float)view.getScrollY(), view2, motionEvent)) continue;
                bl = true;
                break;
            }
        } else {
            bl = false;
        }
        if (!bl) {
            this.b1.set(f3, f4, (float)view.getRight() + f3 - (float)view.getLeft(), (float)view.getBottom() + f4 - (float)view.getTop());
            if ((motionEvent.getAction() != 0 || this.b1.contains(motionEvent.getX(), motionEvent.getY())) && this.b0(view, motionEvent, -f3, -f4)) {
                return true;
            }
        }
        return bl;
    }

    public void setDebugMode(int n3) {
        this.b0 = n3;
        this.invalidate();
    }

    public void setDelayedApplicationOfInitialState(boolean bl) {
        this.X0 = bl;
    }

    public void setInteractionEnabled(boolean bl) {
        this.K = bl;
    }

    public void setInterpolatedProgress(float f3) {
        if (this.B != null) {
            this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.e);
            Interpolator interpolator = this.B.s();
            if (interpolator != null) {
                this.setProgress(interpolator.getInterpolation(f3));
                return;
            }
        }
        this.setProgress(f3);
    }

    public void setOnHide(float f3) {
        ArrayList arrayList = this.t0;
        if (arrayList != null) {
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                ((MotionHelper)this.t0.get(i3)).setProgress(f3);
            }
        }
    }

    public void setOnShow(float f3) {
        ArrayList arrayList = this.s0;
        if (arrayList != null) {
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                ((MotionHelper)this.s0.get(i3)).setProgress(f3);
            }
        }
    }

    public void setProgress(float f3) {
        float f4 = f3 - 0.0f;
        float f5 = f4 == 0.0f ? 0 : (f4 < 0.0f ? -1 : 1);
        if (f5 < 0 || f3 > 1.0f) {
            Log.w((String)"MotionLayout", (String)"Warning! Progress is defined for values between 0.0 and 1.0 inclusive");
        }
        if (!this.isAttachedToWindow()) {
            if (this.M0 == null) {
                this.M0 = new h(this);
            }
            this.M0.e(f3);
            return;
        }
        if (f5 <= 0) {
            if (this.P == 1.0f && this.G == this.H) {
                this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.e);
            }
            this.G = this.F;
            if (this.P == 0.0f) {
                this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.f);
            }
        } else if (f3 >= 1.0f) {
            if (this.P == 0.0f && this.G == this.F) {
                this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.e);
            }
            this.G = this.H;
            if (this.P == 1.0f) {
                this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.f);
            }
        } else {
            this.G = -1;
            this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.e);
        }
        if (this.B == null) {
            return;
        }
        this.S = true;
        this.R = f3;
        this.O = f3;
        this.Q = -1L;
        this.M = -1L;
        this.C = null;
        this.T = true;
        this.invalidate();
    }

    public void setProgress(float f3, float f4) {
        if (!this.isAttachedToWindow()) {
            if (this.M0 == null) {
                this.M0 = new h(this);
            }
            this.M0.e(f3);
            this.M0.h(f4);
            return;
        }
        this.setProgress(f3);
        this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.e);
        this.E = f4;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = f4 - 0.0f;
        float f8 = f7 == 0.0f ? 0 : (f7 > 0.0f ? 1 : -1);
        if (f8 != false) {
            f3 = f6;
            if (f8 > 0) {
                f3 = 1.0f;
            }
            this.Z(f3);
            return;
        }
        if (f3 != 0.0f && f3 != 1.0f) {
            f4 = f5;
            if (f3 > 0.5f) {
                f4 = 1.0f;
            }
            this.Z(f4);
        }
    }

    public void setScene(a a4) {
        this.B = a4;
        a4.W(this.t());
        this.y0();
    }

    public void setStartState(int n3) {
        if (!this.isAttachedToWindow()) {
            if (this.M0 == null) {
                this.M0 = new h(this);
            }
            this.M0.f(n3);
            this.M0.d(n3);
            return;
        }
        this.G = n3;
    }

    @Override
    public void setState(int n3, int n4, int n5) {
        this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.d);
        this.G = n3;
        this.F = -1;
        this.H = -1;
        Object object = this.m;
        if (object != null) {
            ((y.a)object).d(n3, n4, n5);
            return;
        }
        object = this.B;
        if (object != null) {
            ((a)object).l(n3).i(this);
        }
    }

    public void setState(j j3) {
        j j4 = androidx.constraintlayout.motion.widget.MotionLayout$j.f;
        if (j3 != j4 || this.G != -1) {
            int n3;
            j j5 = this.Y0;
            this.Y0 = j3;
            j j6 = androidx.constraintlayout.motion.widget.MotionLayout$j.e;
            if (j5 == j6 && j3 == j6) {
                this.j0();
            }
            if ((n3 = j5.ordinal()) != 0 && n3 != 1) {
                if (n3 == 2 && j3 == j4) {
                    this.k0();
                    return;
                }
            } else {
                if (j3 == j6) {
                    this.j0();
                }
                if (j3 == j4) {
                    this.k0();
                }
            }
        }
    }

    public void setTransition(int n3) {
        if (this.B != null) {
            a.b b3 = this.q0(n3);
            this.F = b3.A();
            this.H = b3.y();
            if (!this.isAttachedToWindow()) {
                if (this.M0 == null) {
                    this.M0 = new h(this);
                }
                this.M0.f(this.F);
                this.M0.d(this.H);
                return;
            }
            n3 = this.G;
            int n4 = this.F;
            float f3 = 0.0f;
            float f4 = n3 == n4 ? 0.0f : (n3 == this.H ? 1.0f : Float.NaN);
            this.B.Y(b3);
            this.Z0.e(this.e, this.B.l(this.F), this.B.l(this.H));
            this.y0();
            if (this.P != f4) {
                if (f4 == 0.0f) {
                    this.g0(true);
                    this.B.l(this.F).i(this);
                } else if (f4 == 1.0f) {
                    this.g0(false);
                    this.B.l(this.H).i(this);
                }
            }
            if (!Float.isNaN(f4)) {
                f3 = f4;
            }
            this.P = f3;
            if (Float.isNaN(f4)) {
                x.a.b();
                this.E0();
                return;
            }
            this.setProgress(f4);
        }
    }

    public void setTransition(int n3, int n4) {
        if (!this.isAttachedToWindow()) {
            if (this.M0 == null) {
                this.M0 = new h(this);
            }
            this.M0.f(n3);
            this.M0.d(n4);
            return;
        }
        a a4 = this.B;
        if (a4 != null) {
            this.F = n3;
            this.H = n4;
            a4.X(n3, n4);
            this.Z0.e(this.e, this.B.l(n3), this.B.l(n4));
            this.y0();
            this.P = 0.0f;
            this.E0();
        }
    }

    public void setTransition(a.b b3) {
        this.B.Y(b3);
        this.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.d);
        if (this.G == this.B.q()) {
            this.P = 1.0f;
            this.O = 1.0f;
            this.R = 1.0f;
        } else {
            this.P = 0.0f;
            this.O = 0.0f;
            this.R = 0.0f;
        }
        long l3 = b3.D(1) ? -1L : this.getNanoTime();
        this.Q = l3;
        int n3 = this.B.F();
        int n4 = this.B.q();
        if (n3 == this.F && n4 == this.H) {
            return;
        }
        this.F = n3;
        this.H = n4;
        this.B.X(n3, n4);
        this.Z0.e(this.e, this.B.l(this.F), this.B.l(this.H));
        this.Z0.i(this.F, this.H);
        this.Z0.h();
        this.y0();
    }

    public void setTransitionDuration(int n3) {
        a a4 = this.B;
        if (a4 == null) {
            Log.e((String)"MotionLayout", (String)"MotionScene not defined");
            return;
        }
        a4.V(n3);
    }

    public void setTransitionListener(i i3) {
        this.V = i3;
    }

    public void setTransitionState(Bundle bundle) {
        if (this.M0 == null) {
            this.M0 = new h(this);
        }
        this.M0.g(bundle);
        if (this.isAttachedToWindow()) {
            this.M0.a();
        }
    }

    public final void t0(AttributeSet object) {
        f1 = this.isInEditMode();
        if (object != null) {
            object = this.getContext().obtainStyledAttributes((AttributeSet)object, y.d.MotionLayout);
            int n3 = object.getIndexCount();
            boolean bl = true;
            for (int i3 = 0; i3 < n3; ++i3) {
                boolean bl2;
                int n4 = object.getIndex(i3);
                if (n4 == y.d.MotionLayout_layoutDescription) {
                    n4 = object.getResourceId(n4, -1);
                    this.B = new a(this.getContext(), this, n4);
                    bl2 = bl;
                } else if (n4 == y.d.MotionLayout_currentState) {
                    this.G = object.getResourceId(n4, -1);
                    bl2 = bl;
                } else if (n4 == y.d.MotionLayout_motionProgress) {
                    this.R = object.getFloat(n4, 0.0f);
                    this.T = true;
                    bl2 = bl;
                } else if (n4 == y.d.MotionLayout_applyMotionScene) {
                    bl2 = object.getBoolean(n4, bl);
                } else if (n4 == y.d.MotionLayout_showPaths) {
                    bl2 = bl;
                    if (this.b0 == 0) {
                        n4 = object.getBoolean(n4, false) ? 2 : 0;
                        this.b0 = n4;
                        bl2 = bl;
                    }
                } else {
                    bl2 = bl;
                    if (n4 == y.d.MotionLayout_motionDebug) {
                        this.b0 = object.getInt(n4, 0);
                        bl2 = bl;
                    }
                }
                bl = bl2;
            }
            object.recycle();
            if (this.B == null) {
                Log.e((String)"MotionLayout", (String)"WARNING NO app:layoutDescription tag");
            }
            if (!bl) {
                this.B = null;
            }
        }
        if (this.b0 != 0) {
            this.c0();
        }
        if (this.G == -1 && (object = this.B) != null) {
            this.G = ((a)object).F();
            this.F = this.B.F();
            this.H = this.B.q();
        }
    }

    public String toString() {
        Context context = this.getContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(x.a.c(context, this.F));
        stringBuilder.append("->");
        stringBuilder.append(x.a.c(context, this.H));
        stringBuilder.append(" (pos:");
        stringBuilder.append(this.P);
        stringBuilder.append(" Dpos/Dt:");
        stringBuilder.append(this.E);
        return stringBuilder.toString();
    }

    public boolean u0() {
        return this.K;
    }

    @Override
    public void v(int n3) {
        this.m = null;
    }

    public f v0() {
        return androidx.constraintlayout.motion.widget.MotionLayout$g.f();
    }

    public void w0() {
        a a4 = this.B;
        if (a4 != null) {
            if (a4.h(this, this.G)) {
                this.requestLayout();
                return;
            }
            int n3 = this.G;
            if (n3 != -1) {
                this.B.f(this, n3);
            }
            if (this.B.b0()) {
                this.B.Z();
            }
        }
    }

    public final void x0() {
        List list;
        if (this.V == null && ((list = this.v0) == null || ((CopyOnWriteArrayList)list).isEmpty())) {
            return;
        }
        int n3 = 0;
        this.B0 = false;
        list = this.e1;
        int n4 = ((ArrayList)list).size();
        block0: while (n3 < n4) {
            Object object = ((ArrayList)list).get(n3);
            int n5 = n3 + 1;
            object = (Integer)object;
            Object object2 = this.V;
            if (object2 != null) {
                object2.d(this, (Integer)object);
            }
            object2 = this.v0;
            n3 = n5;
            if (object2 == null) continue;
            object2 = ((CopyOnWriteArrayList)object2).iterator();
            while (true) {
                n3 = n5;
                if (!object2.hasNext()) continue block0;
                ((i)object2.next()).d(this, (Integer)object);
            }
        }
        this.e1.clear();
    }

    public void y0() {
        this.Z0.h();
        this.invalidate();
    }

    public final void z0() {
        block19: {
            int n3;
            Object object;
            Object object2;
            int n4;
            int n5 = this.getChildCount();
            this.Z0.a();
            int n6 = 1;
            this.T = true;
            Object object3 = new SparseArray();
            int n7 = 0;
            int n8 = 0;
            for (n4 = 0; n4 < n5; ++n4) {
                object2 = this.getChildAt(n4);
                object3.put(object2.getId(), (Object)((m)this.L.get(object2)));
            }
            int n9 = this.getWidth();
            int n10 = this.getHeight();
            int n11 = this.B.j();
            if (n11 != -1) {
                for (n4 = 0; n4 < n5; ++n4) {
                    object2 = (m)this.L.get(this.getChildAt(n4));
                    if (object2 == null) continue;
                    ((m)object2).D(n11);
                }
            }
            object2 = new SparseBooleanArray();
            object3 = new int[this.L.size()];
            n4 = 0;
            for (n11 = 0; n11 < n5; ++n11) {
                object = this.getChildAt(n11);
                object = (m)this.L.get(object);
                n3 = n4;
                if (((m)object).h() != -1) {
                    object2.put(((m)object).h(), true);
                    object3[n4] = (SparseArray)((m)object).h();
                    n3 = n4 + 1;
                }
                n4 = n3;
            }
            if (this.u0 != null) {
                for (n11 = 0; n11 < n4; ++n11) {
                    object = (m)this.L.get(this.findViewById((int)object3[n11]));
                    if (object == null) continue;
                    this.B.t((m)object);
                }
                object = this.u0;
                n3 = ((ArrayList)object).size();
                for (n11 = 0; n11 < n3; ++n11) {
                    Object e3 = ((ArrayList)object).get(n11);
                    ((MotionHelper)e3).D(this, this.L);
                }
                for (n11 = 0; n11 < n4; ++n11) {
                    object = (m)this.L.get(this.findViewById((int)object3[n11]));
                    if (object == null) continue;
                    ((m)object).I(n9, n10, this.N, this.getNanoTime());
                }
            } else {
                for (n11 = 0; n11 < n4; ++n11) {
                    object = (m)this.L.get(this.findViewById((int)object3[n11]));
                    if (object == null) continue;
                    this.B.t((m)object);
                    ((m)object).I(n9, n10, this.N, this.getNanoTime());
                }
            }
            for (n4 = 0; n4 < n5; ++n4) {
                object3 = this.getChildAt(n4);
                object = (m)this.L.get(object3);
                if (object2.get(object3.getId()) || object == null) continue;
                this.B.t((m)object);
                ((m)object).I(n9, n10, this.N, this.getNanoTime());
            }
            float f3 = this.B.E();
            if (f3 != 0.0f) {
                float f4;
                n4 = (double)f3 < 0.0 ? n6 : 0;
                float f5 = Math.abs(f3);
                float f6 = -3.4028235E38f;
                float f7 = Float.MAX_VALUE;
                n11 = 0;
                f3 = -3.4028235E38f;
                float f8 = Float.MAX_VALUE;
                while (true) {
                    if (n11 >= n5) break;
                    object2 = (m)this.L.get(this.getChildAt(n11));
                    if (!Float.isNaN(((m)object2).m)) {
                        n11 = 0;
                        f3 = f7;
                        f4 = f6;
                        while (true) {
                            if (n11 >= n5) break;
                            object2 = (m)this.L.get(this.getChildAt(n11));
                            f6 = f4;
                            f8 = f3;
                            if (!Float.isNaN(((m)object2).m)) {
                                f8 = Math.min(f3, ((m)object2).m);
                                f6 = Math.max(f4, ((m)object2).m);
                            }
                            ++n11;
                            f4 = f6;
                            f3 = f8;
                        }
                        for (n3 = n8; n3 < n5; ++n3) {
                            object2 = (m)this.L.get(this.getChildAt(n3));
                            if (Float.isNaN(((m)object2).m)) continue;
                            ((m)object2).o = 1.0f / (1.0f - f5);
                            ((m)object2).n = n4 != 0 ? f5 - (f4 - ((m)object2).m) / (f4 - f3) * f5 : f5 - (((m)object2).m - f3) * f5 / (f4 - f3);
                        }
                        break block19;
                    }
                    f4 = ((m)object2).n();
                    float f9 = ((m)object2).o();
                    f4 = n4 != 0 ? f9 - f4 : f9 + f4;
                    f8 = Math.min(f8, f4);
                    f3 = Math.max(f3, f4);
                    ++n11;
                }
                for (n3 = n7; n3 < n5; ++n3) {
                    object2 = (m)this.L.get(this.getChildAt(n3));
                    f4 = ((m)object2).n();
                    f6 = ((m)object2).o();
                    f4 = n4 != 0 ? f6 - f4 : f6 + f4;
                    ((m)object2).o = 1.0f / (1.0f - f5);
                    ((m)object2).n = f5 - (f4 - f8) * f5 / (f3 - f8);
                }
            }
        }
    }

    public class c
    extends n {
        public float a;
        public float b;
        public float c;
        public final MotionLayout d;

        public c(MotionLayout motionLayout) {
            this.d = motionLayout;
            this.a = 0.0f;
            this.b = 0.0f;
        }

        @Override
        public float a() {
            return this.d.E;
        }

        public void b(float f3, float f4, float f5) {
            this.a = f3;
            this.b = f4;
            this.c = f5;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public float getInterpolation(float f3) {
            float f4;
            float f5 = this.a;
            if (f5 > 0.0f) {
                float f6 = this.c;
                f4 = f3;
                if (f5 / f6 < f3) {
                    f4 = f5 / f6;
                }
                this.d.E = f5 - f6 * f4;
                f4 = f5 * f4 - f6 * f4 * f4 / 2.0f;
                f3 = this.b;
                return f4 + f3;
            }
            float f7 = -f5;
            float f8 = this.c;
            f4 = f3;
            if (f7 / f8 < f3) {
                f4 = -f5 / f8;
            }
            this.d.E = f8 * f4 + f5;
            f4 = f5 * f4 + f8 * f4 * f4 / 2.0f;
            f3 = this.b;
            return f4 + f3;
        }
    }

    public class d {
        public float[] a;
        public int[] b;
        public float[] c;
        public Path d;
        public Paint e;
        public Paint f;
        public Paint g;
        public Paint h;
        public Paint i;
        public float[] j;
        public final int k;
        public final int l;
        public final int m;
        public final int n;
        public final int o;
        public DashPathEffect p;
        public int q;
        public Rect r;
        public boolean s;
        public int t;
        public final MotionLayout u;

        public d(MotionLayout motionLayout) {
            Paint paint;
            this.u = motionLayout;
            this.k = -21965;
            this.l = -2067046;
            this.m = -13391360;
            this.n = 0x77000000;
            this.o = 10;
            this.r = new Rect();
            this.s = false;
            this.t = 1;
            this.e = paint = new Paint();
            paint.setAntiAlias(true);
            this.e.setColor(-21965);
            this.e.setStrokeWidth(2.0f);
            Paint paint2 = this.e;
            paint = Paint.Style.STROKE;
            paint2.setStyle((Paint.Style)paint);
            this.f = paint2 = new Paint();
            paint2.setAntiAlias(true);
            this.f.setColor(-2067046);
            this.f.setStrokeWidth(2.0f);
            this.f.setStyle((Paint.Style)paint);
            this.g = paint2 = new Paint();
            paint2.setAntiAlias(true);
            this.g.setColor(-13391360);
            this.g.setStrokeWidth(2.0f);
            this.g.setStyle((Paint.Style)paint);
            this.h = paint = new Paint();
            paint.setAntiAlias(true);
            this.h.setColor(-13391360);
            this.h.setTextSize(motionLayout.getContext().getResources().getDisplayMetrics().density * 12.0f);
            this.j = new float[8];
            motionLayout = new Paint();
            this.i = motionLayout;
            motionLayout.setAntiAlias(true);
            motionLayout = new DashPathEffect(new float[]{4.0f, 8.0f}, 0.0f);
            this.p = motionLayout;
            this.g.setPathEffect((PathEffect)motionLayout);
            this.c = new float[100];
            this.b = new int[50];
            if (this.s) {
                this.e.setStrokeWidth(8.0f);
                this.i.setStrokeWidth(8.0f);
                this.f.setStrokeWidth(8.0f);
                this.t = 4;
            }
        }

        public void a(Canvas canvas, HashMap object, int n3, int n4) {
            if (object != null && ((HashMap)object).size() != 0) {
                canvas.save();
                if (!this.u.isInEditMode() && (n4 & 1) == 2) {
                    Object object2 = new StringBuilder();
                    ((StringBuilder)object2).append(this.u.getContext().getResources().getResourceName(this.u.H));
                    ((StringBuilder)object2).append(":");
                    ((StringBuilder)object2).append(this.u.getProgress());
                    object2 = ((StringBuilder)object2).toString();
                    canvas.drawText((String)object2, 10.0f, (float)(this.u.getHeight() - 30), this.h);
                    canvas.drawText((String)object2, 11.0f, (float)(this.u.getHeight() - 29), this.e);
                }
                for (Object object2 : ((HashMap)object).values()) {
                    int n5;
                    int n6 = n5 = ((m)object2).m();
                    if (n4 > 0) {
                        n6 = n5;
                        if (n5 == 0) {
                            n6 = 1;
                        }
                    }
                    if (n6 == 0) continue;
                    this.q = ((m)object2).c(this.c, this.b);
                    if (n6 < 1) continue;
                    n5 = n3 / 16;
                    object = this.a;
                    if (object == null || ((Object)object).length != n5 * 2) {
                        this.a = new float[n5 * 2];
                        this.d = new Path();
                    }
                    int n7 = this.t;
                    canvas.translate((float)n7, (float)n7);
                    this.e.setColor(0x77000000);
                    this.i.setColor(0x77000000);
                    this.f.setColor(0x77000000);
                    this.g.setColor(0x77000000);
                    ((m)object2).d(this.a, n5);
                    this.b(canvas, n6, this.q, (m)object2);
                    this.e.setColor(-21965);
                    this.f.setColor(-2067046);
                    this.i.setColor(-2067046);
                    this.g.setColor(-13391360);
                    n5 = this.t;
                    canvas.translate((float)(-n5), (float)(-n5));
                    this.b(canvas, n6, this.q, (m)object2);
                    if (n6 != 5) continue;
                    this.j(canvas, (m)object2);
                }
                canvas.restore();
            }
        }

        public void b(Canvas canvas, int n3, int n4, m m3) {
            if (n3 == 4) {
                this.d(canvas);
            }
            if (n3 == 2) {
                this.g(canvas);
            }
            if (n3 == 3) {
                this.e(canvas);
            }
            this.c(canvas);
            this.k(canvas, n3, n4, m3);
        }

        public final void c(Canvas canvas) {
            canvas.drawLines(this.a, this.e);
        }

        public final void d(Canvas canvas) {
            boolean bl = false;
            boolean bl2 = false;
            for (int i3 = 0; i3 < this.q; ++i3) {
                int n3 = this.b[i3];
                if (n3 == 1) {
                    bl = true;
                }
                if (n3 != 0) continue;
                bl2 = true;
            }
            if (bl) {
                this.g(canvas);
            }
            if (bl2) {
                this.e(canvas);
            }
        }

        public final void e(Canvas canvas) {
            float[] fArray = this.a;
            float f3 = fArray[0];
            float f4 = fArray[1];
            float f5 = fArray[fArray.length - 2];
            float f6 = fArray[fArray.length - 1];
            canvas.drawLine(Math.min(f3, f5), Math.max(f4, f6), Math.max(f3, f5), Math.max(f4, f6), this.g);
            canvas.drawLine(Math.min(f3, f5), Math.min(f4, f6), Math.min(f3, f5), Math.max(f4, f6), this.g);
        }

        public final void f(Canvas canvas, float f3, float f4) {
            Object object = this.a;
            float f5 = object[0];
            float f6 = object[1];
            float f7 = object[((float[])object).length - 2];
            float f8 = object[((float[])object).length - 1];
            float f9 = Math.min(f5, f7);
            float f10 = Math.max(f6, f8);
            float f11 = f3 - Math.min(f5, f7);
            float f12 = Math.max(f6, f8) - f4;
            object = new StringBuilder();
            ((StringBuilder)object).append("");
            ((StringBuilder)object).append((float)((int)((double)(f11 * 100.0f / Math.abs(f7 - f5)) + 0.5)) / 100.0f);
            object = ((StringBuilder)object).toString();
            this.l((String)object, this.h);
            canvas.drawText((String)object, f11 / 2.0f - (float)(this.r.width() / 2) + f9, f4 - 20.0f, this.h);
            canvas.drawLine(f3, f4, Math.min(f5, f7), f4, this.g);
            object = new StringBuilder();
            ((StringBuilder)object).append("");
            ((StringBuilder)object).append((float)((int)((double)(f12 * 100.0f / Math.abs(f8 - f6)) + 0.5)) / 100.0f);
            object = ((StringBuilder)object).toString();
            this.l((String)object, this.h);
            canvas.drawText((String)object, f3 + 5.0f, f10 - (f12 / 2.0f - (float)(this.r.height() / 2)), this.h);
            canvas.drawLine(f3, f4, f3, Math.max(f6, f8), this.g);
        }

        public final void g(Canvas canvas) {
            float[] fArray = this.a;
            canvas.drawLine(fArray[0], fArray[1], fArray[fArray.length - 2], fArray[fArray.length - 1], this.g);
        }

        public final void h(Canvas canvas, float f3, float f4) {
            Object object = this.a;
            float f5 = object[0];
            float f6 = object[1];
            float f7 = object[((float[])object).length - 2];
            float f8 = object[((float[])object).length - 1];
            float f9 = (float)Math.hypot(f5 - f7, f6 - f8);
            float f10 = f8 - f6;
            f8 = ((f3 - f5) * (f7 -= f5) + (f4 - f6) * f10) / (f9 * f9);
            object = new Path();
            object.moveTo(f3, f4);
            object.lineTo(f5 += f7 * f8, f6 += f8 * f10);
            f7 = (float)Math.hypot(f5 - f3, f6 - f4);
            CharSequence charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append((float)((int)(f7 * 100.0f / f9)) / 100.0f);
            charSequence = charSequence.toString();
            this.l((String)charSequence, this.h);
            canvas.drawTextOnPath((String)charSequence, (Path)object, f7 / 2.0f - (float)(this.r.width() / 2), -20.0f, this.h);
            canvas.drawLine(f3, f4, f5, f6, this.g);
        }

        public final void i(Canvas canvas, float f3, float f4, int n3, int n4) {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("");
            ((StringBuilder)charSequence).append((float)((int)((double)((f3 - (float)(n3 / 2)) * 100.0f / (float)(this.u.getWidth() - n3)) + 0.5)) / 100.0f);
            charSequence = ((StringBuilder)charSequence).toString();
            this.l((String)charSequence, this.h);
            canvas.drawText((String)charSequence, f3 / 2.0f - (float)(this.r.width() / 2) + 0.0f, f4 - 20.0f, this.h);
            canvas.drawLine(f3, f4, Math.min(0.0f, 1.0f), f4, this.g);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("");
            ((StringBuilder)charSequence).append((float)((int)((double)((f4 - (float)(n4 / 2)) * 100.0f / (float)(this.u.getHeight() - n4)) + 0.5)) / 100.0f);
            charSequence = ((StringBuilder)charSequence).toString();
            this.l((String)charSequence, this.h);
            canvas.drawText((String)charSequence, 5.0f + f3, 0.0f - (f4 / 2.0f - (float)(this.r.height() / 2)), this.h);
            canvas.drawLine(f3, f4, f3, Math.max(0.0f, 1.0f), this.g);
        }

        public final void j(Canvas canvas, m m3) {
            this.d.reset();
            for (int i3 = 0; i3 <= 50; ++i3) {
                m3.e((float)i3 / (float)50, this.j, 0);
                Path path = this.d;
                float[] fArray = this.j;
                path.moveTo(fArray[0], fArray[1]);
                path = this.d;
                fArray = this.j;
                path.lineTo(fArray[2], fArray[3]);
                path = this.d;
                fArray = this.j;
                path.lineTo(fArray[4], fArray[5]);
                path = this.d;
                fArray = this.j;
                path.lineTo(fArray[6], fArray[7]);
                this.d.close();
            }
            this.e.setColor(0x44000000);
            canvas.translate(2.0f, 2.0f);
            canvas.drawPath(this.d, this.e);
            canvas.translate(-2.0f, -2.0f);
            this.e.setColor(-65536);
            canvas.drawPath(this.d, this.e);
        }

        public final void k(Canvas canvas, int n3, int n4, m object) {
            int n5;
            int n6;
            Object object2 = ((m)object).b;
            if (object2 != null) {
                n6 = object2.getWidth();
                n5 = ((m)object).b.getHeight();
            } else {
                n6 = 0;
                n5 = 0;
            }
            for (int i3 = 1; i3 < n4 - 1; ++i3) {
                if (n3 == 4 && this.b[i3 - 1] == 0) continue;
                object2 = this.c;
                int n7 = i3 * 2;
                View view = object2[n7];
                View view2 = object2[n7 + 1];
                this.d.reset();
                this.d.moveTo((float)view, (float)(view2 + 10.0f));
                this.d.lineTo((float)(view + 10.0f), (float)view2);
                this.d.lineTo((float)view, (float)(view2 - 10.0f));
                this.d.lineTo((float)(view - 10.0f), (float)view2);
                this.d.close();
                n7 = i3 - 1;
                ((m)object).q(n7);
                if (n3 == 4) {
                    if ((n7 = this.b[n7]) == 1) {
                        this.h(canvas, (float)(view - 0.0f), (float)(view2 - 0.0f));
                    } else if (n7 == 0) {
                        this.f(canvas, (float)(view - 0.0f), (float)(view2 - 0.0f));
                    } else if (n7 == 2) {
                        this.i(canvas, (float)(view - 0.0f), (float)(view2 - 0.0f), n6, n5);
                    }
                    canvas.drawPath(this.d, this.i);
                }
                if (n3 == 2) {
                    this.h(canvas, (float)(view - 0.0f), (float)(view2 - 0.0f));
                }
                if (n3 == 3) {
                    this.f(canvas, (float)(view - 0.0f), (float)(view2 - 0.0f));
                }
                if (n3 == 6) {
                    this.i(canvas, (float)(view - 0.0f), (float)(view2 - 0.0f), n6, n5);
                }
                canvas.drawPath(this.d, this.i);
            }
            object = this.a;
            if (((Object)object).length > 1) {
                canvas.drawCircle((float)object[0], (float)object[1], 8.0f, this.f);
                object = this.a;
                canvas.drawCircle((float)object[((Object)object).length - 2], (float)object[((Object)object).length - 1], 8.0f, this.f);
            }
        }

        public void l(String string, Paint paint) {
            paint.getTextBounds(string, 0, string.length(), this.r);
        }
    }

    public class e {
        public u.f a;
        public u.f b;
        public androidx.constraintlayout.widget.b c;
        public androidx.constraintlayout.widget.b d;
        public int e;
        public int f;
        public final MotionLayout g;

        public e(MotionLayout motionLayout) {
            this.g = motionLayout;
            this.a = new u.f();
            this.b = new u.f();
            this.c = null;
            this.d = null;
        }

        public void a() {
            int n3;
            Object object;
            Object object2;
            int n4;
            int n5 = this.g.getChildCount();
            this.g.L.clear();
            SparseArray sparseArray = new SparseArray();
            int[] nArray = new int[n5];
            int n6 = 0;
            for (n4 = 0; n4 < n5; ++n4) {
                object2 = this.g.getChildAt(n4);
                object = new m((View)object2);
                nArray[n4] = n3 = object2.getId();
                sparseArray.put(n3, object);
                this.g.L.put(object2, object);
            }
            n3 = 0;
            while (true) {
                if (n3 >= n5) break;
                object2 = this.g.getChildAt(n3);
                object = (m)this.g.L.get(object2);
                if (object != null) {
                    Object object3;
                    if (this.c != null) {
                        object3 = this.d(this.a, (View)object2);
                        if (object3 != null) {
                            ((m)object).F(this.g.A0((u.e)object3), this.c, this.g.getWidth(), this.g.getHeight());
                        } else if (this.g.b0 != 0) {
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append(x.a.b());
                            ((StringBuilder)object3).append("no widget for  ");
                            ((StringBuilder)object3).append(x.a.d((View)object2));
                            ((StringBuilder)object3).append(" (");
                            ((StringBuilder)object3).append(object2.getClass().getName());
                            ((StringBuilder)object3).append(")");
                            Log.e((String)"MotionLayout", (String)((StringBuilder)object3).toString());
                        }
                    } else if (this.g.Q0) {
                        androidx.appcompat.app.s.a(this.g.S0.get(object2));
                        object3 = this.g;
                        ((m)object).G(null, (View)object2, ((MotionLayout)object3).R0, ((MotionLayout)object3).T0, this.g.U0);
                    }
                    if (this.d != null) {
                        object3 = this.d(this.b, (View)object2);
                        if (object3 != null) {
                            ((m)object).C(this.g.A0((u.e)object3), this.d, this.g.getWidth(), this.g.getHeight());
                        } else if (this.g.b0 != 0) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append(x.a.b());
                            ((StringBuilder)object).append("no widget for  ");
                            ((StringBuilder)object).append(x.a.d((View)object2));
                            ((StringBuilder)object).append(" (");
                            ((StringBuilder)object).append(object2.getClass().getName());
                            ((StringBuilder)object).append(")");
                            Log.e((String)"MotionLayout", (String)((StringBuilder)object).toString());
                        }
                    }
                }
                ++n3;
            }
            for (n4 = n6; n4 < n5; ++n4) {
                object2 = (m)sparseArray.get(nArray[n4]);
                n3 = ((m)object2).h();
                if (n3 == -1) continue;
                ((m)object2).J((m)sparseArray.get(n3));
            }
        }

        public final void b(int n3, int n4) {
            int n5;
            Object object;
            Object object2;
            int n6 = this.g.getOptimizationLevel();
            Object object3 = this.g;
            if (((MotionLayout)object3).G == ((MotionLayout)object3).getStartState()) {
                MotionLayout motionLayout = this.g;
                object3 = this.b;
                androidx.constraintlayout.widget.b b3 = this.d;
                int n7 = b3 != null && b3.e != 0 ? n4 : n3;
                int n8 = b3 != null && b3.e != 0 ? n3 : n4;
                motionLayout.x((u.f)object3, n6, n7, n8);
                b3 = this.c;
                if (b3 != null) {
                    motionLayout = this.g;
                    object3 = this.a;
                    n8 = b3.e;
                    n7 = n8 == 0 ? n3 : n4;
                    if (n8 == 0) {
                        n3 = n4;
                    }
                    motionLayout.x((u.f)object3, n6, n7, n3);
                }
                return;
            }
            object3 = this.c;
            if (object3 != null) {
                object2 = this.g;
                object = this.a;
                int n9 = ((androidx.constraintlayout.widget.b)object3).e;
                n5 = n9 == 0 ? n3 : n4;
                n9 = n9 == 0 ? n4 : n3;
                ((MotionLayout)object2).x((u.f)object, n6, n5, n9);
            }
            object3 = this.g;
            object2 = this.b;
            object = this.d;
            n5 = object != null && ((androidx.constraintlayout.widget.b)object).e != 0 ? n4 : n3;
            if (object == null || ((androidx.constraintlayout.widget.b)object).e == 0) {
                n3 = n4;
            }
            ((MotionLayout)object3).x((u.f)object2, n6, n5, n3);
        }

        public void c(u.f e3, u.f f3) {
            int n3;
            ArrayList arrayList = e3.w1();
            HashMap<u.e, u.f> hashMap = new HashMap<u.e, u.f>();
            hashMap.put(e3, f3);
            f3.w1().clear();
            f3.n(e3, hashMap);
            int n4 = arrayList.size();
            int n5 = 0;
            for (n3 = 0; n3 < n4; ++n3) {
                e3 = arrayList.get(n3);
                u.e e4 = e3;
                e3 = e4 instanceof u.a ? new u.a() : (e4 instanceof u.h ? new u.h() : (e4 instanceof u.g ? new u.g() : (e4 instanceof l ? new l() : (e4 instanceof u.i ? new u.j() : new u.e()))));
                f3.a(e3);
                hashMap.put(e4, (u.f)e3);
            }
            n4 = arrayList.size();
            for (n3 = n5; n3 < n4; ++n3) {
                e3 = arrayList.get(n3);
                e3 = e3;
                ((u.e)hashMap.get(e3)).n(e3, hashMap);
            }
        }

        public u.e d(u.f object, View view) {
            if (((u.e)object).u() == view) {
                return object;
            }
            object = ((u.n)object).w1();
            int n3 = ((ArrayList)object).size();
            for (int i3 = 0; i3 < n3; ++i3) {
                u.e e3 = (u.e)((ArrayList)object).get(i3);
                if (e3.u() != view) continue;
                return e3;
            }
            return null;
        }

        public void e(u.f f3, androidx.constraintlayout.widget.b object, androidx.constraintlayout.widget.b object2) {
            this.c = object;
            this.d = object2;
            this.a = new u.f();
            this.b = new u.f();
            this.a.b2(this.g.e.O1());
            this.b.b2(this.g.e.O1());
            this.a.z1();
            this.b.z1();
            this.c(this.g.e, this.a);
            this.c(this.g.e, this.b);
            if ((double)this.g.P > 0.5) {
                if (object != null) {
                    this.j(this.a, (androidx.constraintlayout.widget.b)object);
                }
                this.j(this.b, (androidx.constraintlayout.widget.b)object2);
            } else {
                this.j(this.b, (androidx.constraintlayout.widget.b)object2);
                if (object != null) {
                    this.j(this.a, (androidx.constraintlayout.widget.b)object);
                }
            }
            this.a.e2(this.g.t());
            this.a.g2();
            this.b.e2(this.g.t());
            this.b.g2();
            f3 = this.g.getLayoutParams();
            if (f3 != null) {
                if (((ViewGroup.LayoutParams)f3).width == -2) {
                    object2 = this.a;
                    object = e.b.d;
                    ((u.e)object2).U0((e.b)((Object)object));
                    this.b.U0((e.b)((Object)object));
                }
                if (((ViewGroup.LayoutParams)f3).height == -2) {
                    f3 = this.a;
                    object = e.b.d;
                    f3.l1((e.b)((Object)object));
                    this.b.l1((e.b)((Object)object));
                }
            }
        }

        public boolean f(int n3, int n4) {
            return n3 != this.e || n4 != this.f;
            {
            }
        }

        public void g(int n3, int n4) {
            boolean bl;
            int n5;
            int n6;
            block10: {
                int n7;
                MotionLayout motionLayout;
                block9: {
                    int n8;
                    block8: {
                        block7: {
                            n6 = View.MeasureSpec.getMode((int)n3);
                            n5 = View.MeasureSpec.getMode((int)n4);
                            motionLayout = this.g;
                            motionLayout.H0 = n6;
                            motionLayout.I0 = n5;
                            this.b(n3, n4);
                            if (!(this.g.getParent() instanceof MotionLayout) || n6 != 0x40000000 || n5 != 0x40000000) {
                                this.b(n3, n4);
                                this.g.D0 = this.a.Y();
                                this.g.E0 = this.a.z();
                                this.g.F0 = this.b.Y();
                                this.g.G0 = this.b.z();
                                motionLayout = this.g;
                                bl = motionLayout.D0 != motionLayout.F0 || motionLayout.E0 != motionLayout.G0;
                                motionLayout.C0 = bl;
                            }
                            motionLayout = this.g;
                            n6 = motionLayout.D0;
                            n7 = motionLayout.E0;
                            n8 = motionLayout.H0;
                            if (n8 == Integer.MIN_VALUE) break block7;
                            n5 = n6;
                            if (n8 != 0) break block8;
                        }
                        n5 = (int)((float)n6 + motionLayout.J0 * (float)(motionLayout.F0 - n6));
                    }
                    n8 = motionLayout.I0;
                    if (n8 == Integer.MIN_VALUE) break block9;
                    n6 = n7;
                    if (n8 != 0) break block10;
                }
                n6 = (int)((float)n7 + motionLayout.J0 * (float)(motionLayout.G0 - n7));
            }
            bl = this.a.W1() || this.b.W1();
            boolean bl2 = this.a.U1() || this.b.U1();
            this.g.w(n3, n4, n5, n6, bl, bl2);
        }

        public void h() {
            this.g(this.g.I, this.g.J);
            this.g.z0();
        }

        public void i(int n3, int n4) {
            this.e = n3;
            this.f = n4;
        }

        public final void j(u.f f3, androidx.constraintlayout.widget.b object) {
            Object object2;
            int n3;
            Object object3;
            SparseArray sparseArray = new SparseArray();
            Object object4 = new Constraints.LayoutParams(-2, -2);
            sparseArray.clear();
            int n4 = 0;
            sparseArray.put(0, (Object)f3);
            sparseArray.put(this.g.getId(), (Object)f3);
            if (object != null && ((androidx.constraintlayout.widget.b)object).e != 0) {
                object3 = this.g;
                ((MotionLayout)object3).x(this.b, ((ConstraintLayout)((Object)object3)).getOptimizationLevel(), View.MeasureSpec.makeMeasureSpec((int)this.g.getHeight(), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)this.g.getWidth(), (int)0x40000000));
            }
            object3 = f3.w1();
            int n5 = ((ArrayList)object3).size();
            for (n3 = 0; n3 < n5; ++n3) {
                object2 = ((ArrayList)object3).get(n3);
                object2 = (u.e)object2;
                ((u.e)object2).E0(true);
                sparseArray.put(((View)((u.e)object2).u()).getId(), object2);
            }
            object3 = f3.w1();
            n5 = ((ArrayList)object3).size();
            for (n3 = 0; n3 < n5; ++n3) {
                object2 = (u.e)((ArrayList)object3).get(n3);
                View view = (View)((u.e)object2).u();
                ((androidx.constraintlayout.widget.b)object).l(view.getId(), (ConstraintLayout.LayoutParams)((Object)object4));
                ((u.e)object2).p1(((androidx.constraintlayout.widget.b)object).B(view.getId()));
                ((u.e)object2).Q0(((androidx.constraintlayout.widget.b)object).w(view.getId()));
                if (view instanceof ConstraintHelper) {
                    ((androidx.constraintlayout.widget.b)object).j((ConstraintHelper)view, (u.e)object2, (ConstraintLayout.LayoutParams)((Object)object4), sparseArray);
                    if (view instanceof Barrier) {
                        ((Barrier)view).w();
                    }
                }
                ((ConstraintLayout.LayoutParams)((Object)object4)).resolveLayoutDirection(this.g.getLayoutDirection());
                this.g.f(false, view, (u.e)object2, (ConstraintLayout.LayoutParams)((Object)object4), sparseArray);
                if (((androidx.constraintlayout.widget.b)object).A(view.getId()) == 1) {
                    ((u.e)object2).o1(view.getVisibility());
                    continue;
                }
                ((u.e)object2).o1(((androidx.constraintlayout.widget.b)object).z(view.getId()));
            }
            object = f3.w1();
            n5 = ((ArrayList)object).size();
            n3 = n4;
            while (n3 < n5) {
                object4 = ((ArrayList)object).get(n3);
                n4 = n3 + 1;
                object3 = (u.e)object4;
                n3 = n4;
                if (!(object3 instanceof u.m)) continue;
                object4 = (ConstraintHelper)((Object)((u.e)object3).u());
                object3 = (u.i)object3;
                ((ConstraintHelper)((Object)object4)).v(f3, (u.i)object3, sparseArray);
                ((u.m)object3).z1();
                n3 = n4;
            }
        }
    }

    public static interface f {
        public void a(MotionEvent var1);

        public float b();

        public float c();

        public void d();

        public void e(int var1);
    }

    public static class g
    implements f {
        public static g b = new g();
        public VelocityTracker a;

        public static g f() {
            androidx.constraintlayout.motion.widget.MotionLayout$g.b.a = VelocityTracker.obtain();
            return b;
        }

        @Override
        public void a(MotionEvent motionEvent) {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
        }

        @Override
        public float b() {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                return velocityTracker.getYVelocity();
            }
            return 0.0f;
        }

        @Override
        public float c() {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                return velocityTracker.getXVelocity();
            }
            return 0.0f;
        }

        @Override
        public void d() {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.a = null;
            }
        }

        @Override
        public void e(int n3) {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(n3);
            }
        }
    }

    public class h {
        public float a;
        public float b;
        public int c;
        public int d;
        public final String e;
        public final String f;
        public final String g;
        public final String h;
        public final MotionLayout i;

        public h(MotionLayout motionLayout) {
            this.i = motionLayout;
            this.a = Float.NaN;
            this.b = Float.NaN;
            this.c = -1;
            this.d = -1;
            this.e = "motion.progress";
            this.f = "motion.velocity";
            this.g = "motion.StartState";
            this.h = "motion.EndState";
        }

        public void a() {
            int n3 = this.c;
            if (n3 != -1 || this.d != -1) {
                if (n3 == -1) {
                    this.i.F0(this.d);
                } else {
                    int n4 = this.d;
                    if (n4 == -1) {
                        this.i.setState(n3, -1, -1);
                    } else {
                        this.i.setTransition(n3, n4);
                    }
                }
                this.i.setState(androidx.constraintlayout.motion.widget.MotionLayout$j.d);
            }
            if (Float.isNaN(this.b)) {
                if (Float.isNaN(this.a)) {
                    return;
                }
                this.i.setProgress(this.a);
                return;
            }
            this.i.setProgress(this.a, this.b);
            this.a = Float.NaN;
            this.b = Float.NaN;
            this.c = -1;
            this.d = -1;
        }

        public Bundle b() {
            Bundle bundle = new Bundle();
            bundle.putFloat("motion.progress", this.a);
            bundle.putFloat("motion.velocity", this.b);
            bundle.putInt("motion.StartState", this.c);
            bundle.putInt("motion.EndState", this.d);
            return bundle;
        }

        public void c() {
            this.d = this.i.H;
            this.c = this.i.F;
            this.b = this.i.getVelocity();
            this.a = this.i.getProgress();
        }

        public void d(int n3) {
            this.d = n3;
        }

        public void e(float f3) {
            this.a = f3;
        }

        public void f(int n3) {
            this.c = n3;
        }

        public void g(Bundle bundle) {
            this.a = bundle.getFloat("motion.progress");
            this.b = bundle.getFloat("motion.velocity");
            this.c = bundle.getInt("motion.StartState");
            this.d = bundle.getInt("motion.EndState");
        }

        public void h(float f3) {
            this.b = f3;
        }
    }

    public static interface i {
        public void a(MotionLayout var1, int var2, int var3, float var4);

        public void b(MotionLayout var1, int var2, int var3);

        public void c(MotionLayout var1, int var2, boolean var3, float var4);

        public void d(MotionLayout var1, int var2);
    }

    public static final class j
    extends Enum {
        public static final /* enum */ j c = new j("UNDEFINED", 0);
        public static final /* enum */ j d = new j("SETUP", 1);
        public static final /* enum */ j e = new j("MOVING", 2);
        public static final /* enum */ j f = new j("FINISHED", 3);
        public static final j[] g = androidx.constraintlayout.motion.widget.MotionLayout$j.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public j() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ j[] a() {
            return new j[]{c, d, e, f};
        }

        public static j valueOf(String string) {
            return Enum.valueOf(j.class, string);
        }

        public static j[] values() {
            return (j[])g.clone();
        }
    }
}

