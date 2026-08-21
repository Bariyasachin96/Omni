/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.recyclerview.widget;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Map;
import java.util.WeakHashMap;
import o0.x0;
import p0.s;
import p0.t;

public class k
extends o0.a {
    public final RecyclerView d;
    public final a e;

    public k(RecyclerView object) {
        this.d = object;
        object = this.n();
        if (object != null && object instanceof a) {
            this.e = (a)object;
            return;
        }
        this.e = new a(this);
    }

    @Override
    public void f(View object, AccessibilityEvent accessibilityEvent) {
        super.f((View)object, accessibilityEvent);
        if (object instanceof RecyclerView && !this.o() && ((RecyclerView)(object = (RecyclerView)object)).getLayoutManager() != null) {
            ((RecyclerView)object).getLayoutManager().O0(accessibilityEvent);
        }
    }

    @Override
    public void g(View view, s s3) {
        super.g(view, s3);
        if (!this.o() && this.d.getLayoutManager() != null) {
            this.d.getLayoutManager().R0(s3);
        }
    }

    @Override
    public boolean j(View view, int n3, Bundle bundle) {
        if (super.j(view, n3, bundle)) {
            return true;
        }
        if (!this.o() && this.d.getLayoutManager() != null) {
            return this.d.getLayoutManager().k1(n3, bundle);
        }
        return false;
    }

    public o0.a n() {
        return this.e;
    }

    public boolean o() {
        return this.d.t0();
    }

    public static class a
    extends o0.a {
        public final k d;
        public Map e = new WeakHashMap();

        public a(k k3) {
            this.d = k3;
        }

        @Override
        public boolean a(View view, AccessibilityEvent accessibilityEvent) {
            o0.a a4 = (o0.a)this.e.get(view);
            if (a4 != null) {
                return a4.a(view, accessibilityEvent);
            }
            return super.a(view, accessibilityEvent);
        }

        @Override
        public t b(View view) {
            o0.a a4 = (o0.a)this.e.get(view);
            if (a4 != null) {
                return a4.b(view);
            }
            return super.b(view);
        }

        @Override
        public void f(View view, AccessibilityEvent accessibilityEvent) {
            o0.a a4 = (o0.a)this.e.get(view);
            if (a4 != null) {
                a4.f(view, accessibilityEvent);
                return;
            }
            super.f(view, accessibilityEvent);
        }

        @Override
        public void g(View view, s s3) {
            if (!this.d.o() && this.d.d.getLayoutManager() != null) {
                this.d.d.getLayoutManager().S0(view, s3);
                o0.a a4 = (o0.a)this.e.get(view);
                if (a4 != null) {
                    a4.g(view, s3);
                    return;
                }
                super.g(view, s3);
                return;
            }
            super.g(view, s3);
        }

        @Override
        public void h(View view, AccessibilityEvent accessibilityEvent) {
            o0.a a4 = (o0.a)this.e.get(view);
            if (a4 != null) {
                a4.h(view, accessibilityEvent);
                return;
            }
            super.h(view, accessibilityEvent);
        }

        @Override
        public boolean i(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            o0.a a4 = (o0.a)this.e.get(viewGroup);
            if (a4 != null) {
                return a4.i(viewGroup, view, accessibilityEvent);
            }
            return super.i(viewGroup, view, accessibilityEvent);
        }

        @Override
        public boolean j(View view, int n3, Bundle bundle) {
            if (!this.d.o() && this.d.d.getLayoutManager() != null) {
                o0.a a4 = (o0.a)this.e.get(view);
                if (a4 != null ? a4.j(view, n3, bundle) : super.j(view, n3, bundle)) {
                    return true;
                }
                return this.d.d.getLayoutManager().m1(view, n3, bundle);
            }
            return super.j(view, n3, bundle);
        }

        @Override
        public void l(View view, int n3) {
            o0.a a4 = (o0.a)this.e.get(view);
            if (a4 != null) {
                a4.l(view, n3);
                return;
            }
            super.l(view, n3);
        }

        @Override
        public void m(View view, AccessibilityEvent accessibilityEvent) {
            o0.a a4 = (o0.a)this.e.get(view);
            if (a4 != null) {
                a4.m(view, accessibilityEvent);
                return;
            }
            super.m(view, accessibilityEvent);
        }

        public o0.a n(View view) {
            return (o0.a)this.e.remove(view);
        }

        public void o(View view) {
            o0.a a4 = x0.k(view);
            if (a4 != null && a4 != this) {
                this.e.put(view, a4);
            }
        }
    }
}

