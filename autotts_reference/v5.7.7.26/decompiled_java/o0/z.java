/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package o0;

import android.view.MotionEvent;

public abstract class z {
    public static boolean a(MotionEvent motionEvent, int n3) {
        return (motionEvent.getSource() & n3) == n3;
    }
}

