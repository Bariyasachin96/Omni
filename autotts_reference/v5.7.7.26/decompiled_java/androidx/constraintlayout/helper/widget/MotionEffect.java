/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.HashMap;
import x.a;
import x.d;
import x.e;
import x.h;
import x.m;

public class MotionEffect
extends MotionHelper {
    public float p = 0.1f;
    public int q = 49;
    public int r = 50;
    public int s = 0;
    public int t = 0;
    public boolean u = true;
    public int v = -1;
    public int w = -1;

    public MotionEffect(Context context) {
        super(context);
    }

    public MotionEffect(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.E(context, attributeSet);
    }

    public MotionEffect(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.E(context, attributeSet);
    }

    private void E(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            int n3;
            context = context.obtainStyledAttributes(attributeSet, y.d.MotionEffect);
            int n4 = context.getIndexCount();
            for (n3 = 0; n3 < n4; ++n3) {
                int n5 = context.getIndex(n3);
                if (n5 == y.d.MotionEffect_motionEffect_start) {
                    this.q = n5 = context.getInt(n5, this.q);
                    this.q = Math.max(Math.min(n5, 99), 0);
                    continue;
                }
                if (n5 == y.d.MotionEffect_motionEffect_end) {
                    this.r = n5 = context.getInt(n5, this.r);
                    this.r = Math.max(Math.min(n5, 99), 0);
                    continue;
                }
                if (n5 == y.d.MotionEffect_motionEffect_translationX) {
                    this.s = context.getDimensionPixelOffset(n5, this.s);
                    continue;
                }
                if (n5 == y.d.MotionEffect_motionEffect_translationY) {
                    this.t = context.getDimensionPixelOffset(n5, this.t);
                    continue;
                }
                if (n5 == y.d.MotionEffect_motionEffect_alpha) {
                    this.p = context.getFloat(n5, this.p);
                    continue;
                }
                if (n5 == y.d.MotionEffect_motionEffect_move) {
                    this.w = context.getInt(n5, this.w);
                    continue;
                }
                if (n5 == y.d.MotionEffect_motionEffect_strict) {
                    this.u = context.getBoolean(n5, this.u);
                    continue;
                }
                if (n5 != y.d.MotionEffect_motionEffect_viewTransition) continue;
                this.v = context.getResourceId(n5, this.v);
            }
            n4 = this.q;
            n3 = this.r;
            if (n4 == n3) {
                if (n4 > 0) {
                    this.q = n4 - 1;
                } else {
                    this.r = n3 + 1;
                }
            }
            context.recycle();
        }
    }

    @Override
    public void D(MotionLayout motionLayout, HashMap hashMap) {
        int n3;
        float f3;
        float f4;
        e e3;
        e e4;
        Object object = 0;
        Object object2 = 1;
        Object object3 = 1;
        View[] viewArray = this.n((ConstraintLayout)this.getParent());
        if (viewArray == null) {
            a.a();
            return;
        }
        e e5 = new e();
        e e6 = new e();
        e5.R("alpha", Float.valueOf(this.p));
        e6.R("alpha", Float.valueOf(this.p));
        e5.g(this.q);
        e6.g(this.r);
        h h3 = new h();
        h3.g(this.q);
        h3.m(0);
        h3.n("percentX", object);
        h3.n("percentY", object);
        h h4 = new h();
        h4.g(this.r);
        h4.m(0);
        h4.n("percentX", object3);
        h4.n("percentY", object3);
        int n4 = this.s;
        e e7 = null;
        if (n4 > 0) {
            e4 = new e();
            object3 = new e();
            e4.R("translationX", this.s);
            e4.g(this.r);
            ((e)object3).R("translationX", object);
            ((d)object3).g(this.r - 1);
        } else {
            e4 = null;
            object3 = null;
        }
        if (this.t > 0) {
            e7 = new e();
            e3 = new e();
            e7.R("translationY", this.t);
            e7.g(this.r);
            e3.R("translationY", object);
            e3.g(this.r - 1);
        } else {
            e3 = null;
        }
        n4 = this.w;
        if (n4 == -1) {
            object = new int[4];
            for (n4 = 0; n4 < viewArray.length; ++n4) {
                m m3 = (m)hashMap.get(viewArray[n4]);
                if (m3 == null) continue;
                f4 = m3.n() - m3.t();
                f3 = m3.o() - m3.u();
                if (f3 < 0.0f) {
                    object[1] = object[1] + true;
                }
                if (f3 > 0.0f) {
                    object[0] = object[0] + true;
                }
                if (f4 > 0.0f) {
                    object[3] = object[3] + true;
                }
                if (!(f4 < 0.0f)) continue;
                object[2] = object[2] + true;
            }
            Object object4 = object[0];
            n3 = 0;
            for (n4 = object2; n4 < 4; ++n4) {
                Object object5 = object[n4];
                object2 = object4;
                if (object4 < object5) {
                    object2 = object5;
                    n3 = n4;
                }
                object4 = object2;
            }
            n4 = n3;
        }
        for (n3 = 0; n3 < viewArray.length; ++n3) {
            object = (m)hashMap.get(viewArray[n3]);
            if (object == null) continue;
            f4 = ((m)object).n() - ((m)object).t();
            f3 = ((m)object).o() - ((m)object).u();
            if (n4 == 0) {
                if (f3 > 0.0f && (!this.u || f4 == 0.0f)) {
                    continue;
                }
            } else if (n4 == 1) {
                if (f3 < 0.0f && (!this.u || f4 == 0.0f)) {
                    continue;
                }
            } else if (n4 != 2 ? n4 == 3 && f4 > 0.0f && (!this.u || f3 == 0.0f) : f4 < 0.0f && (!this.u || f3 == 0.0f)) continue;
            if ((object2 = this.v) == -1) {
                ((m)object).a(e5);
                ((m)object).a(e6);
                ((m)object).a(h3);
                ((m)object).a(h4);
                if (this.s > 0) {
                    ((m)object).a(e4);
                    ((m)object).a((d)object3);
                }
                if (this.t <= 0) continue;
                ((m)object).a(e7);
                ((m)object).a(e3);
                continue;
            }
            motionLayout.a0((int)object2, (m)object);
        }
    }

    @Override
    public boolean x() {
        return true;
    }
}

