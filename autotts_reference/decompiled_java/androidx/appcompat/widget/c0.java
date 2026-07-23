/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.SystemClock
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewConfiguration
 *  android.view.ViewParent
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import androidx.appcompat.widget.a0;
import i.f;

public abstract class c0
implements View.OnTouchListener,
View.OnAttachStateChangeListener {
    public final float c;
    public final int d;
    public final int e;
    public final View f;
    public Runnable g;
    public Runnable h;
    public boolean i;
    public int j;
    public final int[] k = new int[2];

    public c0(View view) {
        int n3;
        this.f = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        this.c = ViewConfiguration.get((Context)view.getContext()).getScaledTouchSlop();
        this.d = n3 = ViewConfiguration.getTapTimeout();
        this.e = (n3 + ViewConfiguration.getLongPressTimeout()) / 2;
    }

    public static boolean h(View view, float f3, float f4, float f5) {
        float f6 = -f5;
        return f3 >= f6 && f4 >= f6 && f3 < (float)(view.getRight() - view.getLeft()) + f5 && f4 < (float)(view.getBottom() - view.getTop()) + f5;
    }

    public final void a() {
        Runnable runnable = this.h;
        if (runnable != null) {
            this.f.removeCallbacks(runnable);
        }
        if ((runnable = this.g) != null) {
            this.f.removeCallbacks(runnable);
        }
    }

    public abstract f b();

    public abstract boolean c();

    public boolean d() {
        f f3 = this.b();
        if (f3 != null && f3.c()) {
            f3.dismiss();
        }
        return true;
    }

    public void e() {
        this.a();
        View view = this.f;
        if (view.isEnabled() && !view.isLongClickable() && this.c()) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            long l3 = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain((long)l3, (long)l3, (int)3, (float)0.0f, (float)0.0f, (int)0);
            view.onTouchEvent(motionEvent);
            motionEvent.recycle();
            this.i = true;
        }
    }

    public final boolean f(MotionEvent motionEvent) {
        a0 a02;
        View view = this.f;
        f f3 = this.b();
        if (f3 != null && f3.c() && (a02 = (a0)f3.h()) != null && a02.isShown()) {
            f3 = MotionEvent.obtainNoHistory((MotionEvent)motionEvent);
            this.i(view, (MotionEvent)f3);
            this.j((View)a02, (MotionEvent)f3);
            boolean bl = a02.e((MotionEvent)f3, this.j);
            f3.recycle();
            int n3 = motionEvent.getActionMasked();
            n3 = n3 != 1 && n3 != 3 ? 1 : 0;
            if (bl && n3 != 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean g(MotionEvent motionEvent) {
        block7: {
            View view;
            block4: {
                block5: {
                    int n3;
                    block6: {
                        view = this.f;
                        if (!view.isEnabled()) {
                            return false;
                        }
                        n3 = motionEvent.getActionMasked();
                        if (n3 == 0) break block4;
                        if (n3 == 1) break block5;
                        if (n3 == 2) break block6;
                        if (n3 == 3) break block5;
                        break block7;
                    }
                    n3 = motionEvent.findPointerIndex(this.j);
                    if (n3 >= 0 && !c0.h(view, motionEvent.getX(n3), motionEvent.getY(n3), this.c)) {
                        this.a();
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                    break block7;
                }
                this.a();
                break block7;
            }
            this.j = motionEvent.getPointerId(0);
            if (this.g == null) {
                this.g = new a(this);
            }
            view.postDelayed(this.g, (long)this.d);
            if (this.h == null) {
                this.h = new b(this);
            }
            view.postDelayed(this.h, (long)this.e);
        }
        return false;
    }

    public final boolean i(View view, MotionEvent motionEvent) {
        int[] nArray = this.k;
        view.getLocationOnScreen(nArray);
        motionEvent.offsetLocation((float)nArray[0], (float)nArray[1]);
        return true;
    }

    public final boolean j(View view, MotionEvent motionEvent) {
        int[] nArray = this.k;
        view.getLocationOnScreen(nArray);
        motionEvent.offsetLocation((float)(-nArray[0]), (float)(-nArray[1]));
        return true;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean bl;
        boolean bl2 = this.i;
        if (bl2) {
            bl = this.f(motionEvent) || !this.d();
        } else {
            boolean bl3 = this.g(motionEvent) && this.c();
            bl = bl3;
            if (bl3) {
                long l3 = SystemClock.uptimeMillis();
                view = MotionEvent.obtain((long)l3, (long)l3, (int)3, (float)0.0f, (float)0.0f, (int)0);
                this.f.onTouchEvent((MotionEvent)view);
                view.recycle();
                bl = bl3;
            }
        }
        this.i = bl;
        return bl || bl2;
        {
        }
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View object) {
        this.i = false;
        this.j = -1;
        object = this.g;
        if (object != null) {
            this.f.removeCallbacks((Runnable)object);
        }
    }

    public class a
    implements Runnable {
        public final c0 c;

        public a(c0 c02) {
            this.c = c02;
        }

        @Override
        public void run() {
            ViewParent viewParent = this.c.f.getParent();
            if (viewParent != null) {
                viewParent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    public class b
    implements Runnable {
        public final c0 c;

        public b(c0 c02) {
            this.c = c02;
        }

        @Override
        public void run() {
            this.c.e();
        }
    }
}

