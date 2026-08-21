/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.window.BackEvent
 *  android.window.OnBackInvokedDispatcher
 */
package androidx.activity;

import android.os.Build;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.activity.b;
import androidx.activity.c;
import androidx.activity.o;
import androidx.activity.p;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import e3.e;
import java.util.ListIterator;
import n3.a;
import n3.l;
import o3.k;

public final class OnBackPressedDispatcher {
    public final Runnable a;
    public final n0.a b;
    public final e c;
    public o d;
    public OnBackInvokedCallback e;
    public OnBackInvokedDispatcher f;
    public boolean g;
    public boolean h;

    public OnBackPressedDispatcher(Runnable runnable) {
        this(runnable, null);
    }

    public OnBackPressedDispatcher(Runnable object, n0.a a4) {
        this.a = object;
        this.b = a4;
        this.c = new e();
        int n3 = Build.VERSION.SDK_INT;
        if (n3 >= 33) {
            object = n3 >= 34 ? androidx.activity.OnBackPressedDispatcher$g.a.a(new l(this){
                public final OnBackPressedDispatcher d;
                {
                    this.d = onBackPressedDispatcher;
                    super(1);
                }

                public final void b(b b3) {
                    k.e(b3, "backEvent");
                    this.d.m(b3);
                }
            }, new l(this){
                public final OnBackPressedDispatcher d;
                {
                    this.d = onBackPressedDispatcher;
                    super(1);
                }

                public final void b(b b3) {
                    k.e(b3, "backEvent");
                    this.d.l(b3);
                }
            }, new a(this){
                public final OnBackPressedDispatcher d;
                {
                    this.d = onBackPressedDispatcher;
                    super(0);
                }

                public final void b() {
                    this.d.k();
                }
            }, new a(this){
                public final OnBackPressedDispatcher d;
                {
                    this.d = onBackPressedDispatcher;
                    super(0);
                }

                public final void b() {
                    this.d.j();
                }
            }) : androidx.activity.OnBackPressedDispatcher$f.a.b(new a(this){
                public final OnBackPressedDispatcher d;
                {
                    this.d = onBackPressedDispatcher;
                    super(0);
                }

                public final void b() {
                    this.d.k();
                }
            });
            this.e = object;
        }
    }

    public final void h(androidx.lifecycle.k object, o o3) {
        k.e(object, "owner");
        k.e(o3, "onBackPressedCallback");
        object = object.t();
        if (((androidx.lifecycle.f)object).b() == f.b.c) {
            return;
        }
        o3.a(new LifecycleOnBackPressedCancellable(this, (androidx.lifecycle.f)object, o3));
        this.p();
        o3.k(new a(this){

            public final void k() {
                ((OnBackPressedDispatcher)this.d).p();
            }
        });
    }

    public final c i(o o3) {
        k.e(o3, "onBackPressedCallback");
        this.c.add(o3);
        h h3 = new h(this, o3);
        o3.a(h3);
        this.p();
        o3.k(new a(this){

            public final void k() {
                ((OnBackPressedDispatcher)this.d).p();
            }
        });
        return h3;
    }

    public final void j() {
        Object object;
        block2: {
            object = this.c;
            ListIterator listIterator = object.listIterator(object.size());
            while (listIterator.hasPrevious()) {
                object = listIterator.previous();
                if (!((o)object).g()) continue;
                break block2;
            }
            object = null;
        }
        object = (o)object;
        this.d = null;
        if (object != null) {
            ((o)object).c();
        }
    }

    public final void k() {
        Object object;
        block3: {
            object = this.c;
            ListIterator listIterator = object.listIterator(object.size());
            while (listIterator.hasPrevious()) {
                object = listIterator.previous();
                if (!((o)object).g()) continue;
                break block3;
            }
            object = null;
        }
        object = (o)object;
        this.d = null;
        if (object != null) {
            ((o)object).d();
            return;
        }
        object = this.a;
        if (object != null) {
            object.run();
        }
    }

    public final void l(b b3) {
        Object object;
        block2: {
            object = this.c;
            ListIterator listIterator = object.listIterator(object.size());
            while (listIterator.hasPrevious()) {
                object = listIterator.previous();
                if (!((o)object).g()) continue;
                break block2;
            }
            object = null;
        }
        object = (o)object;
        if (object != null) {
            ((o)object).e(b3);
        }
    }

    public final void m(b b3) {
        Object object;
        block2: {
            object = this.c;
            ListIterator listIterator = object.listIterator(object.size());
            while (listIterator.hasPrevious()) {
                object = listIterator.previous();
                if (!((o)object).g()) continue;
                break block2;
            }
            object = null;
        }
        this.d = object = (o)object;
        if (object != null) {
            ((o)object).f(b3);
        }
    }

    public final void n(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        k.e(onBackInvokedDispatcher, "invoker");
        this.f = onBackInvokedDispatcher;
        this.o(this.h);
    }

    public final void o(boolean bl) {
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.f;
        OnBackInvokedCallback onBackInvokedCallback = this.e;
        if (onBackInvokedDispatcher != null && onBackInvokedCallback != null) {
            if (bl && !this.g) {
                androidx.activity.OnBackPressedDispatcher$f.a.d(onBackInvokedDispatcher, 0, onBackInvokedCallback);
                this.g = true;
                return;
            }
            if (!bl && this.g) {
                androidx.activity.OnBackPressedDispatcher$f.a.e(onBackInvokedDispatcher, onBackInvokedCallback);
                this.g = false;
            }
        }
    }

    public final void p() {
        boolean bl;
        Object object;
        boolean bl2;
        block6: {
            bl2 = this.h;
            object = this.c;
            boolean bl3 = false;
            if (object != null && object.isEmpty()) {
                bl = bl3;
            } else {
                object = object.iterator();
                do {
                    bl = bl3;
                    if (!object.hasNext()) break block6;
                } while (!((o)object.next()).g());
                bl = true;
            }
        }
        this.h = bl;
        if (bl != bl2) {
            object = this.b;
            if (object != null) {
                object.accept(bl);
            }
            if (Build.VERSION.SDK_INT >= 33) {
                this.o(bl);
            }
        }
    }

    public final class LifecycleOnBackPressedCancellable
    implements i,
    c {
        public final androidx.lifecycle.f a;
        public final o b;
        public c c;
        public final OnBackPressedDispatcher d;

        public LifecycleOnBackPressedCancellable(OnBackPressedDispatcher onBackPressedDispatcher, androidx.lifecycle.f f3, o o3) {
            k.e(f3, "lifecycle");
            k.e(o3, "onBackPressedCallback");
            this.d = onBackPressedDispatcher;
            this.a = f3;
            this.b = o3;
            f3.a(this);
        }

        @Override
        public void cancel() {
            this.a.c(this);
            this.b.i(this);
            c c3 = this.c;
            if (c3 != null) {
                c3.cancel();
            }
            this.c = null;
        }

        @Override
        public void d(androidx.lifecycle.k object, f.a a4) {
            k.e(object, "source");
            k.e((Object)a4, "event");
            if (a4 == f.a.ON_START) {
                this.c = this.d.i(this.b);
                return;
            }
            if (a4 == f.a.ON_STOP) {
                object = this.c;
                if (object != null) {
                    object.cancel();
                    return;
                }
            } else if (a4 == f.a.ON_DESTROY) {
                this.cancel();
            }
        }
    }

    public static final class f {
        public static final f a = new f();

        public static /* synthetic */ void a(a a4) {
            androidx.activity.OnBackPressedDispatcher$f.c(a4);
        }

        public static final void c(a a4) {
            k.e(a4, "$onBackInvoked");
            a4.a();
        }

        public final OnBackInvokedCallback b(a a4) {
            k.e(a4, "onBackInvoked");
            return new p(a4);
        }

        public final void d(Object object, int n3, Object object2) {
            k.e(object, "dispatcher");
            k.e(object2, "callback");
            ((OnBackInvokedDispatcher)object).registerOnBackInvokedCallback(n3, (OnBackInvokedCallback)object2);
        }

        public final void e(Object object, Object object2) {
            k.e(object, "dispatcher");
            k.e(object2, "callback");
            ((OnBackInvokedDispatcher)object).unregisterOnBackInvokedCallback((OnBackInvokedCallback)object2);
        }
    }

    public static final class g {
        public static final g a = new g();

        public final OnBackInvokedCallback a(l l3, l l4, a a4, a a5) {
            k.e(l3, "onBackStarted");
            k.e(l4, "onBackProgressed");
            k.e(a4, "onBackInvoked");
            k.e(a5, "onBackCancelled");
            return new OnBackAnimationCallback(l3, l4, a4, a5){
                public final l a;
                public final l b;
                public final a c;
                public final a d;
                {
                    this.a = l3;
                    this.b = l4;
                    this.c = a4;
                    this.d = a5;
                }

                public void onBackCancelled() {
                    this.d.a();
                }

                public void onBackInvoked() {
                    this.c.a();
                }

                public void onBackProgressed(BackEvent backEvent) {
                    k.e(backEvent, "backEvent");
                    this.b.f(new b(backEvent));
                }

                public void onBackStarted(BackEvent backEvent) {
                    k.e(backEvent, "backEvent");
                    this.a.f(new b(backEvent));
                }
            };
        }
    }

    public final class h
    implements c {
        public final o a;
        public final OnBackPressedDispatcher b;

        public h(OnBackPressedDispatcher onBackPressedDispatcher, o o3) {
            k.e(o3, "onBackPressedCallback");
            this.b = onBackPressedDispatcher;
            this.a = o3;
        }

        @Override
        public void cancel() {
            this.b.c.remove(this.a);
            if (k.a(this.b.d, this.a)) {
                this.a.c();
                this.b.d = null;
            }
            this.a.i(this);
            a a4 = this.a.b();
            if (a4 != null) {
                a4.a();
            }
            this.a.k(null);
        }
    }
}

