/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.os.Build$VERSION
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewConfiguration
 */
package i2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class a
implements View.OnTouchListener {
    public final Dialog c;
    public final int d;
    public final int e;
    public final int f;

    public a(Dialog dialog, Rect rect) {
        this.c = dialog;
        this.d = rect.left;
        this.e = rect.top;
        this.f = ViewConfiguration.get((Context)dialog.getContext()).getScaledWindowTouchSlop();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int n3;
        View view2 = view.findViewById(0x1020002);
        int n4 = this.d + view2.getLeft();
        int n5 = view2.getWidth();
        int n6 = this.e + view2.getTop();
        if (new RectF((float)n4, (float)n6, (float)(n5 + n4), (float)((n3 = view2.getHeight()) + n6)).contains(motionEvent.getX(), motionEvent.getY())) {
            return false;
        }
        view2 = MotionEvent.obtain((MotionEvent)motionEvent);
        if (motionEvent.getAction() == 1) {
            view2.setAction(4);
        }
        if (Build.VERSION.SDK_INT < 28) {
            view2.setAction(0);
            n5 = this.f;
            view2.setLocation((float)(-n5 - 1), (float)(-n5 - 1));
        }
        view.performClick();
        return this.c.onTouchEvent((MotionEvent)view2);
    }
}

