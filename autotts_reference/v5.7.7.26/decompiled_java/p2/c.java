/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.window.BackEvent
 *  android.window.OnBackInvokedDispatcher
 */
package p2;

import android.os.Build;
import android.view.View;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.app.h;
import androidx.appcompat.app.j;
import java.util.Objects;
import p2.e;

public final class c {
    public final d a = p2.c.a();
    public final p2.b b;
    public final View c;

    public c(View view) {
        this((p2.b)view, view);
    }

    public c(p2.b b3, View view) {
        this.b = b3;
        this.c = view;
    }

    public static d a() {
        int n3 = Build.VERSION.SDK_INT;
        if (n3 >= 34) {
            return new c(null);
        }
        if (n3 >= 33) {
            return new b(null);
        }
        return null;
    }

    public boolean b() {
        return this.a != null;
    }

    public void c() {
        this.d(false);
    }

    public final void d(boolean bl) {
        d d3 = this.a;
        if (d3 != null) {
            d3.b(this.b, this.c, bl);
        }
    }

    public void e() {
        this.d(true);
    }

    public void f() {
        d d3 = this.a;
        if (d3 != null) {
            d3.a(this.c);
        }
    }

    public static class b
    implements d {
        public OnBackInvokedCallback a;

        public b() {
        }

        public /* synthetic */ b(a a4) {
            this();
        }

        @Override
        public void a(View view) {
            if (this.a == null || (view = p2.d.a(view)) == null) {
                return;
            }
            h.a((OnBackInvokedDispatcher)view, this.a);
            this.a = null;
        }

        @Override
        public void b(p2.b object, View view, boolean bl) {
            if (this.a != null || (view = p2.d.a(view)) == null) {
                return;
            }
            this.a = object = this.c((p2.b)object);
            int n3 = bl ? 1000000 : 0;
            j.a((OnBackInvokedDispatcher)view, n3, (OnBackInvokedCallback)object);
        }

        public OnBackInvokedCallback c(p2.b b3) {
            Objects.requireNonNull(b3);
            return new e(b3);
        }

        public boolean d() {
            return this.a != null;
        }
    }

    public static class c
    extends b {
        public c() {
            super(null);
        }

        public /* synthetic */ c(a a4) {
            this();
        }

        @Override
        public OnBackInvokedCallback c(p2.b b3) {
            return new OnBackAnimationCallback(this, b3){
                public final p2.b a;
                public final c b;
                {
                    this.b = c3;
                    this.a = b3;
                }

                public void onBackCancelled() {
                    if (!this.b.d()) {
                        return;
                    }
                    this.a.d();
                }

                public void onBackInvoked() {
                    this.a.a();
                }

                public void onBackProgressed(BackEvent backEvent) {
                    if (!this.b.d()) {
                        return;
                    }
                    this.a.c(new androidx.activity.b(backEvent));
                }

                public void onBackStarted(BackEvent backEvent) {
                    if (!this.b.d()) {
                        return;
                    }
                    this.a.b(new androidx.activity.b(backEvent));
                }
            };
        }
    }

    public static interface d {
        public void a(View var1);

        public void b(p2.b var1, View var2, boolean var3);
    }
}

