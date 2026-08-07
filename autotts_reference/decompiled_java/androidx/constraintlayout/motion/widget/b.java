/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.a;
import androidx.core.widget.NestedScrollView;
import org.xmlpull.v1.XmlPullParser;
import x.m;
import y.d;

public class b {
    public static final float[][] G;
    public static final float[][] H;
    public float A = 10.0f;
    public float B = 1.0f;
    public float C;
    public float D;
    public int E = 0;
    public int F = 0;
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public int d = -1;
    public int e = -1;
    public int f = -1;
    public float g = 0.5f;
    public float h = 0.5f;
    public float i = 0.5f;
    public float j = 0.5f;
    public int k = -1;
    public boolean l = false;
    public float m = 0.0f;
    public float n = 1.0f;
    public boolean o = false;
    public float[] p = new float[2];
    public int[] q = new int[2];
    public float r;
    public float s;
    public final MotionLayout t;
    public float u = 4.0f;
    public float v = 1.2f;
    public boolean w = true;
    public float x = 1.0f;
    public int y = 0;
    public float z = 10.0f;

    static {
        float[] fArray = new float[]{1.0f, 0.5f};
        G = new float[][]{{0.5f, 0.0f}, {0.0f, 0.5f}, fArray, {0.5f, 1.0f}, {0.5f, 0.5f}, {0.0f, 0.5f}, {1.0f, 0.5f}};
        H = new float[][]{{0.0f, -1.0f}, {0.0f, 1.0f}, {-1.0f, 0.0f}, {1.0f, 0.0f}, {-1.0f, 0.0f}, {1.0f, 0.0f}};
    }

    public b(Context context, MotionLayout motionLayout, XmlPullParser xmlPullParser) {
        this.C = Float.NaN;
        this.D = Float.NaN;
        this.t = motionLayout;
        this.c(context, Xml.asAttributeSet((XmlPullParser)xmlPullParser));
    }

    public void A() {
        Object object;
        int n3 = this.d;
        if (n3 != -1) {
            View view = this.t.findViewById(n3);
            object = view;
            if (view == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("cannot find TouchAnchorId @id/");
                ((StringBuilder)object).append(x.a.c(this.t.getContext(), this.d));
                Log.e((String)"TouchResponse", (String)((StringBuilder)object).toString());
                object = view;
            }
        } else {
            object = null;
        }
        if (object instanceof NestedScrollView) {
            object = (NestedScrollView)object;
            object.setOnTouchListener(new View.OnTouchListener(this){
                public final b c;
                {
                    this.c = b3;
                }

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
            ((NestedScrollView)object).setOnScrollChangeListener(new NestedScrollView.d(this){
                public final b a;
                {
                    this.a = b3;
                }

                @Override
                public void a(NestedScrollView nestedScrollView, int n3, int n4, int n5, int n6) {
                }
            });
        }
    }

    public float a(float f3, float f4) {
        return f3 * this.m + f4 * this.n;
    }

    public final void b(TypedArray typedArray) {
        int n3 = typedArray.getIndexCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object;
            int n4 = typedArray.getIndex(i3);
            if (n4 == y.d.OnSwipe_touchAnchorId) {
                this.d = typedArray.getResourceId(n4, this.d);
                continue;
            }
            if (n4 == y.d.OnSwipe_touchAnchorSide) {
                this.a = n4 = typedArray.getInt(n4, this.a);
                object = G[n4];
                this.h = object[0];
                this.g = object[1];
                continue;
            }
            if (n4 == y.d.OnSwipe_dragDirection) {
                this.b = n4 = typedArray.getInt(n4, this.b);
                object = H;
                if (n4 < ((float[])object).length) {
                    object = object[n4];
                    this.m = object[0];
                    this.n = object[1];
                    continue;
                }
                this.n = Float.NaN;
                this.m = Float.NaN;
                this.l = true;
                continue;
            }
            if (n4 == y.d.OnSwipe_maxVelocity) {
                this.u = typedArray.getFloat(n4, this.u);
                continue;
            }
            if (n4 == y.d.OnSwipe_maxAcceleration) {
                this.v = typedArray.getFloat(n4, this.v);
                continue;
            }
            if (n4 == y.d.OnSwipe_moveWhenScrollAtTop) {
                this.w = typedArray.getBoolean(n4, this.w);
                continue;
            }
            if (n4 == y.d.OnSwipe_dragScale) {
                this.x = typedArray.getFloat(n4, this.x);
                continue;
            }
            if (n4 == y.d.OnSwipe_dragThreshold) {
                this.z = typedArray.getFloat(n4, this.z);
                continue;
            }
            if (n4 == y.d.OnSwipe_touchRegionId) {
                this.e = typedArray.getResourceId(n4, this.e);
                continue;
            }
            if (n4 == y.d.OnSwipe_onTouchUp) {
                this.c = typedArray.getInt(n4, this.c);
                continue;
            }
            if (n4 == y.d.OnSwipe_nestedScrollFlags) {
                this.y = typedArray.getInteger(n4, 0);
                continue;
            }
            if (n4 == y.d.OnSwipe_limitBoundsTo) {
                this.f = typedArray.getResourceId(n4, 0);
                continue;
            }
            if (n4 == y.d.OnSwipe_rotationCenterId) {
                this.k = typedArray.getResourceId(n4, this.k);
                continue;
            }
            if (n4 == y.d.OnSwipe_springDamping) {
                this.A = typedArray.getFloat(n4, this.A);
                continue;
            }
            if (n4 == y.d.OnSwipe_springMass) {
                this.B = typedArray.getFloat(n4, this.B);
                continue;
            }
            if (n4 == y.d.OnSwipe_springStiffness) {
                this.C = typedArray.getFloat(n4, this.C);
                continue;
            }
            if (n4 == y.d.OnSwipe_springStopThreshold) {
                this.D = typedArray.getFloat(n4, this.D);
                continue;
            }
            if (n4 == y.d.OnSwipe_springBoundary) {
                this.E = typedArray.getInt(n4, this.E);
                continue;
            }
            if (n4 != y.d.OnSwipe_autoCompleteMode) continue;
            this.F = typedArray.getInt(n4, this.F);
        }
    }

    public final void c(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, y.d.OnSwipe);
        this.b((TypedArray)context);
        context.recycle();
    }

    public int d() {
        return this.F;
    }

    public int e() {
        return this.y;
    }

    public RectF f(ViewGroup viewGroup, RectF rectF) {
        int n3 = this.f;
        if (n3 == -1) {
            return null;
        }
        if ((viewGroup = viewGroup.findViewById(n3)) == null) {
            return null;
        }
        rectF.set((float)viewGroup.getLeft(), (float)viewGroup.getTop(), (float)viewGroup.getRight(), (float)viewGroup.getBottom());
        return rectF;
    }

    public float g() {
        return this.v;
    }

    public float h() {
        return this.u;
    }

    public boolean i() {
        return this.w;
    }

    public float j(float f3, float f4) {
        float f5 = this.t.getProgress();
        this.t.n0(this.d, f5, this.h, this.g, this.p);
        f5 = this.m;
        if (f5 != 0.0f) {
            float[] fArray = this.p;
            if (fArray[0] == 0.0f) {
                fArray[0] = 1.0E-7f;
            }
            return f3 * f5 / fArray[0];
        }
        float[] fArray = this.p;
        if (fArray[1] == 0.0f) {
            fArray[1] = 1.0E-7f;
        }
        return f4 * this.n / fArray[1];
    }

    public int k() {
        return this.E;
    }

    public float l() {
        return this.A;
    }

    public float m() {
        return this.B;
    }

    public float n() {
        return this.C;
    }

    public float o() {
        return this.D;
    }

    public RectF p(ViewGroup viewGroup, RectF rectF) {
        int n3 = this.e;
        if (n3 == -1) {
            return null;
        }
        if ((viewGroup = viewGroup.findViewById(n3)) == null) {
            return null;
        }
        rectF.set((float)viewGroup.getLeft(), (float)viewGroup.getTop(), (float)viewGroup.getRight(), (float)viewGroup.getBottom());
        return rectF;
    }

    public int q() {
        return this.e;
    }

    public boolean r() {
        return this.o;
    }

    public void s(MotionEvent object, MotionLayout.f f3, int n3, a object2) {
        block23: {
            block27: {
                block25: {
                    float f4;
                    block26: {
                        float f5;
                        block24: {
                            if (this.l) {
                                this.t((MotionEvent)object, f3, n3, (a)object2);
                                return;
                            }
                            f3.a((MotionEvent)object);
                            n3 = object.getAction();
                            if (n3 == 0) break block23;
                            if (n3 == 1) break block24;
                            if (n3 == 2) {
                                float f6 = object.getRawY() - this.s;
                                float f7 = object.getRawX() - this.r;
                                if (Math.abs(this.m * f7 + this.n * f6) > this.z || this.o) {
                                    float f8;
                                    float f9 = this.t.getProgress();
                                    if (!this.o) {
                                        this.o = true;
                                        this.t.setProgress(f9);
                                    }
                                    if ((n3 = this.d) != -1) {
                                        this.t.n0(n3, f9, this.h, this.g, this.p);
                                    } else {
                                        f8 = Math.min(this.t.getWidth(), this.t.getHeight());
                                        object2 = this.p;
                                        object2[1] = this.n * f8;
                                        object2[0] = f8 * this.m;
                                    }
                                    f8 = this.m;
                                    object2 = this.p;
                                    if ((double)Math.abs((f8 * object2[0] + this.n * object2[1]) * this.x) < 0.01) {
                                        object2 = this.p;
                                        object2[0] = 0.01f;
                                        object2[1] = 0.01f;
                                    }
                                    f7 = this.m != 0.0f ? (f7 /= this.p[0]) : f6 / this.p[1];
                                    f7 = f9 = Math.max(Math.min(f9 + f7, 1.0f), 0.0f);
                                    if (this.c == 6) {
                                        f7 = Math.max(f9, 0.01f);
                                    }
                                    f9 = f7;
                                    if (this.c == 7) {
                                        f9 = Math.min(f7, 0.99f);
                                    }
                                    if (f9 != (f7 = this.t.getProgress())) {
                                        float f10 = f7 - 0.0f;
                                        n3 = f10 == 0.0f ? 0 : (f10 > 0.0f ? 1 : -1);
                                        if (n3 == 0 || f7 == 1.0f) {
                                            object2 = this.t;
                                            boolean bl = n3 == 0;
                                            ((MotionLayout)object2).g0(bl);
                                        }
                                        this.t.setProgress(f9);
                                        f3.e(1000);
                                        f9 = f3.c();
                                        f7 = f3.b();
                                        f7 = this.m != 0.0f ? f9 / this.p[0] : (f7 /= this.p[1]);
                                        this.t.E = f7;
                                    } else {
                                        this.t.E = 0.0f;
                                    }
                                    this.r = object.getRawX();
                                    this.s = object.getRawY();
                                    return;
                                }
                            }
                            break block25;
                        }
                        this.o = false;
                        f3.e(1000);
                        f4 = f3.c();
                        float f11 = f3.b();
                        float f12 = this.t.getProgress();
                        n3 = this.d;
                        if (n3 != -1) {
                            this.t.n0(n3, f12, this.h, this.g, this.p);
                        } else {
                            f5 = Math.min(this.t.getWidth(), this.t.getHeight());
                            object = this.p;
                            object[1] = (MotionEvent)(this.n * f5);
                            object[0] = (MotionEvent)(f5 * this.m);
                        }
                        float f13 = this.m;
                        object = this.p;
                        MotionEvent motionEvent = object[0];
                        f5 = (float)object[1];
                        f11 = f13 != 0.0f ? f4 / motionEvent : (f11 /= f5);
                        f4 = !Float.isNaN(f11) ? f11 / 3.0f + f12 : f12;
                        if (f4 == 0.0f || f4 == 1.0f || (n3 = this.c) == 3) break block26;
                        f5 = (double)f4 < 0.5 ? 0.0f : 1.0f;
                        f4 = f11;
                        if (n3 == 6) {
                            f4 = f11;
                            if (f12 + f11 < 0.0f) {
                                f4 = Math.abs(f11);
                            }
                            f5 = 1.0f;
                        }
                        f11 = f4;
                        if (this.c == 7) {
                            f11 = f4;
                            if (f12 + f4 > 1.0f) {
                                f11 = -Math.abs(f4);
                            }
                            f5 = 0.0f;
                        }
                        this.t.B0(this.c, f5, f11);
                        if (0.0f >= f12 || 1.0f <= f12) {
                            this.t.setState(MotionLayout.j.f);
                            return;
                        }
                        break block25;
                    }
                    if (0.0f >= f4 || 1.0f <= f4) break block27;
                }
                return;
            }
            this.t.setState(MotionLayout.j.f);
            return;
        }
        this.r = object.getRawX();
        this.s = object.getRawY();
        this.o = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void t(MotionEvent object, MotionLayout.f object2, int n3, a object3) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        int n4;
        block33: {
            int n5;
            block32: {
                Object object4;
                block31: {
                    object4.a((MotionEvent)object);
                    n4 = object.getAction();
                    if (n4 == 0) {
                        this.r = object.getRawX();
                        this.s = object.getRawY();
                        this.o = false;
                        return;
                    }
                    boolean bl = true;
                    if (n4 != 1) {
                        float f8;
                        float f9;
                        Object object5;
                        if (n4 != 2) {
                            return;
                        }
                        object.getRawY();
                        object.getRawX();
                        float f10 = (float)this.t.getWidth() / 2.0f;
                        float f11 = (float)this.t.getHeight() / 2.0f;
                        n4 = this.k;
                        if (n4 != -1) {
                            object5 = this.t.findViewById(n4);
                            this.t.getLocationOnScreen(this.q);
                            f9 = this.q[0];
                            f11 = (float)(object5.getLeft() + object5.getRight()) / 2.0f;
                            f8 = this.q[1];
                            f8 = (float)(object5.getTop() + object5.getBottom()) / 2.0f + f8;
                            f9 += f11;
                        } else {
                            n4 = this.d;
                            f9 = f10;
                            f8 = f11;
                            if (n4 != -1) {
                                object5 = this.t.p0(n4);
                                if ((object5 = this.t.findViewById(((m)object5).h())) == null) {
                                    Log.e((String)"TouchResponse", (String)"could not find view to animate to");
                                    f9 = f10;
                                    f8 = f11;
                                } else {
                                    this.t.getLocationOnScreen(this.q);
                                    f9 = (float)this.q[0] + (float)(object5.getLeft() + object5.getRight()) / 2.0f;
                                    f8 = (float)this.q[1] + (float)(object5.getTop() + object5.getBottom()) / 2.0f;
                                }
                            }
                        }
                        float f12 = object.getRawX();
                        float f13 = object.getRawY();
                        double d3 = Math.atan2(object.getRawY() - f8, object.getRawX() - f9);
                        f10 = (float)((d3 - Math.atan2(this.s - f8, this.r - f9)) * 180.0 / Math.PI);
                        if (f10 > 330.0f) {
                            f11 = f10 - 360.0f;
                        } else {
                            f11 = f10;
                            if (f10 < -330.0f) {
                                f11 = f10 + 360.0f;
                            }
                        }
                        if (!((double)Math.abs(f11) > 0.01)) {
                            if (!this.o) return;
                        }
                        f10 = this.t.getProgress();
                        if (!this.o) {
                            this.o = true;
                            this.t.setProgress(f10);
                        }
                        if ((n4 = this.d) != -1) {
                            this.t.n0(n4, f10, this.h, this.g, this.p);
                            object5 = this.p;
                            object5[1] = (View)((float)Math.toDegrees((double)object5[1]));
                        } else {
                            this.p[1] = 360.0f;
                        }
                        f10 = Math.max(Math.min(f10 + f11 * this.x / this.p[1], 1.0f), 0.0f);
                        f11 = this.t.getProgress();
                        if (f10 != f11) {
                            float f14 = f11 - 0.0f;
                            n4 = f14 == 0.0f ? 0 : (f14 > 0.0f ? 1 : -1);
                            if (n4 == 0 || f11 == 1.0f) {
                                object5 = this.t;
                                if (n4 != 0) {
                                    bl = false;
                                }
                                ((MotionLayout)object5).g0(bl);
                            }
                            this.t.setProgress(f10);
                            object4.e(1000);
                            f11 = object4.c();
                            double d4 = object4.b();
                            double d5 = f11;
                            f9 = (float)(Math.hypot(d4, d5) * Math.sin(Math.atan2(d4, d5) - d3) / Math.hypot(f12 - f9, f13 - f8));
                            this.t.E = (float)Math.toDegrees(f9);
                        } else {
                            this.t.E = 0.0f;
                        }
                        this.r = object.getRawX();
                        this.s = object.getRawY();
                        return;
                    }
                    this.o = false;
                    object4.e(16);
                    f7 = object4.c();
                    f6 = object4.b();
                    f5 = this.t.getProgress();
                    f4 = (float)this.t.getWidth() / 2.0f;
                    f3 = (float)this.t.getHeight() / 2.0f;
                    n4 = this.k;
                    if (n4 == -1) break block31;
                    object4 = this.t.findViewById(n4);
                    this.t.getLocationOnScreen(this.q);
                    f4 = (float)this.q[0] + (float)(object4.getLeft() + object4.getRight()) / 2.0f;
                    f3 = this.q[1];
                    n5 = object4.getTop();
                    n4 = object4.getBottom();
                    break block32;
                }
                n4 = this.d;
                if (n4 == -1) break block33;
                object4 = this.t.p0(n4);
                object4 = this.t.findViewById(((m)object4).h());
                this.t.getLocationOnScreen(this.q);
                f4 = (float)this.q[0] + (float)(object4.getLeft() + object4.getRight()) / 2.0f;
                f3 = this.q[1];
                n5 = object4.getTop();
                n4 = object4.getBottom();
            }
            f3 = (float)(n5 + n4) / 2.0f + f3;
        }
        f4 = object.getRawX() - f4;
        f3 = object.getRawY() - f3;
        double d6 = Math.toDegrees(Math.atan2(f3, f4));
        n4 = this.d;
        if (n4 != -1) {
            this.t.n0(n4, f5, this.h, this.g, this.p);
            float[] fArray = this.p;
            fArray[1] = (float)Math.toDegrees(fArray[1]);
        } else {
            this.p[1] = 360.0f;
        }
        f3 = (float)(Math.toDegrees(Math.atan2(f6 + f3, f7 + f4)) - d6) * 62.5f;
        f4 = !Float.isNaN(f3) ? f3 * 3.0f * this.x / this.p[1] + f5 : f5;
        if (f4 != 0.0f && f4 != 1.0f && (n4 = this.c) != 3) {
            f6 = f3 * this.x / this.p[1];
            f3 = (double)f4 < 0.5 ? 0.0f : 1.0f;
            f4 = f6;
            if (n4 == 6) {
                f4 = f6;
                if (f5 + f6 < 0.0f) {
                    f4 = Math.abs(f6);
                }
                f3 = 1.0f;
            }
            f7 = f4;
            f6 = f3;
            if (this.c == 7) {
                f3 = f4;
                if (f5 + f4 > 1.0f) {
                    f3 = -Math.abs(f4);
                }
                f6 = 0.0f;
                f7 = f3;
            }
            this.t.B0(this.c, f6, f7 * 3.0f);
            if (!(0.0f >= f5)) {
                if (!(1.0f <= f5)) return;
            }
            this.t.setState(MotionLayout.j.f);
            return;
        }
        if (!(0.0f >= f4)) {
            if (!(1.0f <= f4)) return;
        }
        this.t.setState(MotionLayout.j.f);
    }

    public String toString() {
        if (Float.isNaN(this.m)) {
            return "rotation";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.m);
        stringBuilder.append(" , ");
        stringBuilder.append(this.n);
        return stringBuilder.toString();
    }

    public void u(float f3, float f4) {
        float f5 = this.t.getProgress();
        if (!this.o) {
            this.o = true;
            this.t.setProgress(f5);
        }
        this.t.n0(this.d, f5, this.h, this.g, this.p);
        float f6 = this.m;
        float[] fArray = this.p;
        if ((double)Math.abs(f6 * fArray[0] + this.n * fArray[1]) < 0.01) {
            fArray = this.p;
            fArray[0] = 0.01f;
            fArray[1] = 0.01f;
        }
        f3 = (f6 = this.m) != 0.0f ? f3 * f6 / this.p[0] : f4 * this.n / this.p[1];
        if ((f3 = Math.max(Math.min(f5 + f3, 1.0f), 0.0f)) != this.t.getProgress()) {
            this.t.setProgress(f3);
        }
    }

    public void v(float f3, float f4) {
        int n3;
        this.o = false;
        float f5 = this.t.getProgress();
        this.t.n0(this.d, f5, this.h, this.g, this.p);
        float f6 = this.m;
        Object object = this.p;
        float f7 = object[0];
        float f8 = this.n;
        float f9 = object[1];
        float f10 = 0.0f;
        f3 = f6 != 0.0f ? f3 * f6 / f7 : f4 * f8 / f9;
        f4 = f5;
        if (!Float.isNaN(f3)) {
            f4 = f5 + f3 / 3.0f;
        }
        if (f4 != 0.0f && f4 != 1.0f && (n3 = this.c) != 3) {
            object = this.t;
            f4 = (double)f4 < 0.5 ? f10 : 1.0f;
            ((MotionLayout)object).B0(n3, f4, f3);
        }
    }

    public void w(float f3, float f4) {
        this.r = f3;
        this.s = f4;
    }

    public void x(boolean bl) {
        Object object;
        if (bl) {
            object = H;
            object[4] = object[3];
            object[5] = object[2];
            object = G;
            object[5] = object[2];
            object[6] = object[1];
        } else {
            object = H;
            object[4] = object[2];
            object[5] = object[3];
            object = G;
            object[5] = object[1];
            object[6] = object[2];
        }
        object = G[this.a];
        this.h = (float)object[0];
        this.g = (float)object[1];
        int n3 = this.b;
        object = H;
        if (n3 >= ((float[][])object).length) {
            return;
        }
        object = object[n3];
        this.m = (float)object[0];
        this.n = (float)object[1];
    }

    public void y(int n3) {
        this.c = n3;
    }

    public void z(float f3, float f4) {
        this.r = f3;
        this.s = f4;
        this.o = false;
    }
}

