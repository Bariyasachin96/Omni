/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.ViewConfiguration
 */
package o0;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import o0.b1;
import o0.m;
import o0.n;
import o0.p;
import o0.u0;

public class o {
    public final Context a;
    public final p b;
    public final b c;
    public final a d;
    public VelocityTracker e;
    public float f;
    public int g = -1;
    public int h = -1;
    public int i = -1;
    public final int[] j = new int[]{Integer.MAX_VALUE, 0};

    public o(Context context, p p3) {
        this(context, p3, new m(), new n());
    }

    public o(Context context, p p3, b b3, a a4) {
        this.a = context;
        this.b = p3;
        this.c = b3;
        this.d = a4;
    }

    public static /* synthetic */ void a(Context context, int[] nArray, MotionEvent motionEvent, int n3) {
        o.c(context, nArray, motionEvent, n3);
    }

    public static /* synthetic */ float b(VelocityTracker velocityTracker, MotionEvent motionEvent, int n3) {
        return o.f(velocityTracker, motionEvent, n3);
    }

    public static void c(Context context, int[] nArray, MotionEvent motionEvent, int n3) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        nArray[0] = b1.g(context, viewConfiguration, motionEvent.getDeviceId(), n3, motionEvent.getSource());
        nArray[1] = b1.f(context, viewConfiguration, motionEvent.getDeviceId(), n3, motionEvent.getSource());
    }

    public static float f(VelocityTracker velocityTracker, MotionEvent motionEvent, int n3) {
        u0.a(velocityTracker, motionEvent);
        u0.b(velocityTracker, 1000);
        return u0.d(velocityTracker, n3);
    }

    public final boolean d(MotionEvent motionEvent, int n3) {
        int n4 = motionEvent.getSource();
        int n5 = motionEvent.getDeviceId();
        if (this.h == n4 && this.i == n5 && this.g == n3) {
            return false;
        }
        this.c.a(this.a, this.j, motionEvent, n3);
        this.h = n4;
        this.i = n5;
        this.g = n3;
        return true;
    }

    public final float e(MotionEvent motionEvent, int n3) {
        if (this.e == null) {
            this.e = VelocityTracker.obtain();
        }
        return this.d.a(this.e, motionEvent, n3);
    }

    public void g(MotionEvent object, int object2) {
        float f3;
        float f4;
        block9: {
            block8: {
                boolean bl;
                block7: {
                    bl = this.d((MotionEvent)object, (int)object2);
                    if (this.j[0] != Integer.MAX_VALUE) break block7;
                    object = this.e;
                    if (object != null) {
                        object.recycle();
                        this.e = null;
                        return;
                    }
                    break block8;
                }
                f4 = this.e((MotionEvent)object, (int)object2) * this.b.b();
                float f5 = Math.signum(f4);
                f3 = 0.0f;
                if (bl || f5 != Math.signum(this.f) && f5 != 0.0f) {
                    this.b.c();
                }
                if (!((f5 = Math.abs(f4)) < (float)(object = (Object)this.j)[0])) break block9;
            }
            return;
        }
        object2 = object[1];
        if (this.b.a(f4 = Math.max((float)(-object2), Math.min(f4, (float)object2)))) {
            f3 = f4;
        }
        this.f = f3;
    }

    public static interface a {
        public float a(VelocityTracker var1, MotionEvent var2, int var3);
    }

    public static interface b {
        public void a(Context var1, int[] var2, MotionEvent var3, int var4);
    }
}

