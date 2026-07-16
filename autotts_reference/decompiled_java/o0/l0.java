/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.WindowInsetsController
 *  android.view.inputmethod.InputMethodManager
 */
package o0;

import android.os.Build;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import java.util.concurrent.atomic.AtomicBoolean;
import o0.k0;
import o0.m0;
import o0.n0;
import o0.o0;
import o0.p0;
import o0.q0;
import o0.r0;
import o0.s0;

public final class l0 {
    public final c a;

    public l0(View view) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new b(view);
            return;
        }
        this.a = new a(view);
    }

    public l0(WindowInsetsController windowInsetsController) {
        this.a = new b(windowInsetsController);
    }

    public void a() {
        this.a.a();
    }

    public void b() {
        this.a.b();
    }

    public static class a
    extends c {
        public final View a;

        public a(View view) {
            this.a = view;
        }

        public static /* synthetic */ void c(View view) {
            ((InputMethodManager)view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
        }

        @Override
        public void a() {
            View view = this.a;
            if (view != null) {
                ((InputMethodManager)view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.a.getWindowToken(), 0);
            }
        }

        @Override
        public void b() {
            View view = this.a;
            if (view != null) {
                if (!view.isInEditMode() && !view.onCheckIsTextEditor()) {
                    view = view.getRootView().findFocus();
                } else {
                    view.requestFocus();
                }
                View view2 = view;
                if (view == null) {
                    view2 = this.a.getRootView().findViewById(0x1020002);
                }
                if (view2 != null && view2.hasWindowFocus()) {
                    view2.post((Runnable)new k0(view2));
                }
            }
        }
    }

    public static class b
    extends a {
        public View b;
        public WindowInsetsController c;

        public b(View view) {
            super(view);
            this.b = view;
        }

        public b(WindowInsetsController windowInsetsController) {
            super(null);
            this.c = windowInsetsController;
        }

        public static /* synthetic */ void d(AtomicBoolean atomicBoolean, WindowInsetsController windowInsetsController, int n3) {
            boolean bl = (n3 & 8) != 0;
            atomicBoolean.set(bl);
        }

        @Override
        public void a() {
            Object object = this.c;
            if (object == null) {
                object = this.b;
                object = object != null ? m0.a((View)object) : null;
            }
            if (object != null) {
                AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                s0 s02 = new s0(atomicBoolean);
                p0.a(object, s02);
                if (!atomicBoolean.get() && (atomicBoolean = this.b) != null) {
                    ((InputMethodManager)atomicBoolean.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.b.getWindowToken(), 0);
                }
                q0.a(object, s02);
                r0.a(object, n0.a());
                return;
            }
            super.a();
        }

        @Override
        public void b() {
            Object object = this.b;
            if (object != null && Build.VERSION.SDK_INT < 33) {
                ((InputMethodManager)object.getContext().getSystemService("input_method")).isActive();
            }
            if ((object = this.c) == null) {
                object = this.b;
                object = object != null ? m0.a(object) : null;
            }
            if (object != null) {
                o0.a((WindowInsetsController)object, n0.a());
                return;
            }
            super.b();
        }
    }

    public static abstract class c {
        public abstract void a();

        public abstract void b();
    }
}

