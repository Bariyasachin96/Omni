/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 */
package o0;

import android.os.Build;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import o0.v0;

public abstract class u0 {
    public static Map a = Collections.synchronizedMap(new WeakHashMap());

    public static void a(VelocityTracker velocityTracker, MotionEvent motionEvent) {
        velocityTracker.addMovement(motionEvent);
        if (Build.VERSION.SDK_INT < 34 && motionEvent.getSource() == 0x400000) {
            if (!a.containsKey(velocityTracker)) {
                a.put(velocityTracker, new v0());
            }
            ((v0)a.get(velocityTracker)).a(motionEvent);
        }
    }

    public static void b(VelocityTracker velocityTracker, int n3) {
        u0.c(velocityTracker, n3, Float.MAX_VALUE);
    }

    public static void c(VelocityTracker object, int n3, float f3) {
        object.computeCurrentVelocity(n3, f3);
        object = u0.e((VelocityTracker)object);
        if (object != null) {
            ((v0)object).c(n3, f3);
        }
    }

    public static float d(VelocityTracker object, int n3) {
        if (Build.VERSION.SDK_INT >= 34) {
            return o0.u0$a.a((VelocityTracker)object, n3);
        }
        if (n3 == 0) {
            return object.getXVelocity();
        }
        if (n3 == 1) {
            return object.getYVelocity();
        }
        if ((object = u0.e((VelocityTracker)object)) != null) {
            return ((v0)object).d(n3);
        }
        return 0.0f;
    }

    public static v0 e(VelocityTracker velocityTracker) {
        return (v0)a.get(velocityTracker);
    }

    public static abstract class a {
        public static float a(VelocityTracker velocityTracker, int n3) {
            return velocityTracker.getAxisVelocity(n3);
        }

        public static float b(VelocityTracker velocityTracker, int n3, int n4) {
            return velocityTracker.getAxisVelocity(n3, n4);
        }

        public static boolean c(VelocityTracker velocityTracker, int n3) {
            return velocityTracker.isAxisSupported(n3);
        }
    }
}

