/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import a1.b;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.f0;
import androidx.fragment.app.w;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import k0.a;
import o0.x0;

public abstract class e0 {
    public final ViewGroup a;
    public final ArrayList b = new ArrayList();
    public final ArrayList c = new ArrayList();
    public boolean d = false;
    public boolean e = false;

    public e0(ViewGroup viewGroup) {
        this.a = viewGroup;
    }

    public static e0 n(ViewGroup viewGroup, FragmentManager fragmentManager) {
        return e0.o(viewGroup, fragmentManager.A0());
    }

    public static e0 o(ViewGroup viewGroup, f0 object) {
        int n3 = a1.b.special_effects_controller_view_tag;
        Object object2 = viewGroup.getTag(n3);
        if (object2 instanceof e0) {
            return (e0)object2;
        }
        object = object.a(viewGroup);
        viewGroup.setTag(n3, object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(e.c object, e.b b3, w w3) {
        ArrayList arrayList = this.b;
        synchronized (arrayList) {
            Throwable throwable2;
            block4: {
                e e3;
                a a4;
                try {
                    a4 = new a();
                    e3 = this.h(w3.k());
                    if (e3 != null) {
                        e3.k((e.c)((Object)object), b3);
                        return;
                    }
                }
                catch (Throwable throwable2) {
                    break block4;
                }
                e3 = new d((e.c)((Object)object), b3, w3, a4);
                this.b.add(e3);
                object = new Runnable(this, (d)e3){
                    public final d c;
                    public final e0 d;
                    {
                        this.d = e02;
                        this.c = d3;
                    }

                    @Override
                    public void run() {
                        if (this.d.b.contains(this.c)) {
                            this.c.e().a(this.c.f().K);
                        }
                    }
                };
                e3.a((Runnable)object);
                object = new Runnable(this, (d)e3){
                    public final d c;
                    public final e0 d;
                    {
                        this.d = e02;
                        this.c = d3;
                    }

                    @Override
                    public void run() {
                        this.d.b.remove(this.c);
                        this.d.c.remove(this.c);
                    }
                };
                e3.a((Runnable)object);
                return;
            }
            throw throwable2;
        }
    }

    public void b(e.c c3, w w3) {
        if (FragmentManager.I0(2)) {
            Objects.toString(w3.k());
        }
        this.a(c3, e.b.d, w3);
    }

    public void c(w w3) {
        if (FragmentManager.I0(2)) {
            Objects.toString(w3.k());
        }
        this.a(e.c.e, e.b.c, w3);
    }

    public void d(w w3) {
        if (FragmentManager.I0(2)) {
            Objects.toString(w3.k());
        }
        this.a(e.c.c, e.b.e, w3);
    }

    public void e(w w3) {
        if (FragmentManager.I0(2)) {
            Objects.toString(w3.k());
        }
        this.a(e.c.d, e.b.c, w3);
    }

    public abstract void f(List var1, boolean var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void g() {
        if (this.e) {
            return;
        }
        if (!x0.N((View)this.a)) {
            this.j();
            this.d = false;
            return;
        }
        ArrayList arrayList = this.b;
        synchronized (arrayList) {
            block9: {
                int n3;
                Object object;
                int n4;
                ArrayList arrayList2;
                try {
                    if (this.b.isEmpty()) break block9;
                    arrayList2 = new ArrayList(this.c);
                    this.c.clear();
                    int n5 = arrayList2.size();
                    n4 = 0;
                    while (n4 < n5) {
                        object = arrayList2.get(n4);
                        n3 = n4 + 1;
                        object = (e)object;
                        if (FragmentManager.I0(2)) {
                            Objects.toString(object);
                        }
                        ((e)object).b();
                        n4 = n3;
                        if (((e)object).i()) continue;
                        this.c.add(object);
                        n4 = n3;
                    }
                    this.q();
                    object = new ArrayList(this.b);
                    this.b.clear();
                    this.c.addAll(object);
                    FragmentManager.I0(2);
                    n3 = ((ArrayList)object).size();
                }
                catch (Throwable throwable) {}
                throw throwable;
                for (n4 = 0; n4 < n3; ++n4) {
                    arrayList2 = ((ArrayList)object).get(n4);
                    ((e)((Object)arrayList2)).l();
                }
                this.f((List)object, this.d);
                this.d = false;
                FragmentManager.I0(2);
            }
            return;
        }
    }

    public final e h(Fragment fragment) {
        ArrayList arrayList = this.b;
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            Object object = arrayList.get(n4);
            int n5 = n4 + 1;
            object = (e)object;
            n4 = n5;
            if (!((e)object).f().equals(fragment)) continue;
            n4 = n5;
            if (((e)object).h()) continue;
            return object;
        }
        return null;
    }

    public final e i(Fragment fragment) {
        ArrayList arrayList = this.c;
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            Object object = arrayList.get(n4);
            int n5 = n4 + 1;
            object = (e)object;
            n4 = n5;
            if (!((e)object).f().equals(fragment)) continue;
            n4 = n5;
            if (((e)object).h()) continue;
            return object;
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    public void j() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 3[TRYBLOCK] [3 : 75->95)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void k() {
        if (this.e) {
            FragmentManager.I0(2);
            this.e = false;
            this.g();
        }
    }

    public e.b l(w object) {
        Object object2 = this.h(((w)object).k());
        object2 = object2 != null ? object2.g() : null;
        if ((object = this.i(((w)object).k())) != null && (object2 == null || object2 == e.b.c)) {
            return ((e)object).g();
        }
        return object2;
    }

    public ViewGroup m() {
        return this.a;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void p() {
        ArrayList arrayList = this.b;
        synchronized (arrayList) {
            try {
                this.q();
                this.e = false;
                for (int i3 = this.b.size() - 1; i3 >= 0; --i3) {
                    e.c c3;
                    e e3 = (e)this.b.get(i3);
                    e.c c4 = e.c.c(e3.f().K);
                    e.c c5 = e3.e();
                    if (c5 != (c3 = e.c.d) || c4 == c3) continue;
                    this.e = e3.f().b0();
                    break;
                }
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final void q() {
        ArrayList arrayList = this.b;
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            Object object = arrayList.get(n4);
            int n5 = n4 + 1;
            object = (e)object;
            n4 = n5;
            if (((e)object).g() != e.b.d) continue;
            ((e)object).k(e.c.b(((e)object).f().o1().getVisibility()), e.b.c);
            n4 = n5;
        }
    }

    public void r(boolean bl) {
        this.d = bl;
    }

    public static class d
    extends e {
        public final w h;

        public d(e.c c3, e.b b3, w w3, a a4) {
            super(c3, b3, w3.k(), a4);
            this.h = w3;
        }

        @Override
        public void c() {
            super.c();
            this.h.m();
        }

        @Override
        public void l() {
            if (this.g() == e.b.d) {
                Fragment fragment = this.h.k();
                View view = fragment.K.findFocus();
                if (view != null) {
                    fragment.u1(view);
                    if (FragmentManager.I0(2)) {
                        view.toString();
                        ((Object)fragment).toString();
                    }
                }
                if ((view = this.f().o1()).getParent() == null) {
                    this.h.b();
                    view.setAlpha(0.0f);
                }
                if (view.getAlpha() == 0.0f && view.getVisibility() == 0) {
                    view.setVisibility(4);
                }
                view.setAlpha(fragment.I());
                return;
            }
            if (this.g() == e.b.e) {
                Fragment fragment = this.h.k();
                View view = fragment.o1();
                if (FragmentManager.I0(2)) {
                    Objects.toString(view.findFocus());
                    view.toString();
                    ((Object)fragment).toString();
                }
                view.clearFocus();
            }
        }
    }

    public static abstract class e {
        public c a;
        public b b;
        public final Fragment c;
        public final List d = new ArrayList();
        public final HashSet e = new HashSet();
        public boolean f = false;
        public boolean g = false;

        public e(c c3, b b3, Fragment fragment, a a4) {
            this.a = c3;
            this.b = b3;
            this.c = fragment;
            a4.b(new a.a(this){
                public final e a;
                {
                    this.a = e3;
                }

                @Override
                public void onCancel() {
                    this.a.b();
                }
            });
        }

        public final void a(Runnable runnable) {
            this.d.add(runnable);
        }

        public final void b() {
            if (!this.h()) {
                this.f = true;
                if (this.e.isEmpty()) {
                    this.c();
                    return;
                }
                ArrayList arrayList = new ArrayList(this.e);
                int n3 = arrayList.size();
                for (int i3 = 0; i3 < n3; ++i3) {
                    Object e3 = arrayList.get(i3);
                    ((a)e3).a();
                }
            }
        }

        public void c() {
            if (!this.g) {
                if (FragmentManager.I0(2)) {
                    ((Object)this).toString();
                }
                this.g = true;
                Iterator iterator = this.d.iterator();
                while (iterator.hasNext()) {
                    ((Runnable)iterator.next()).run();
                }
            }
        }

        public final void d(a a4) {
            if (this.e.remove(a4) && this.e.isEmpty()) {
                this.c();
            }
        }

        public c e() {
            return this.a;
        }

        public final Fragment f() {
            return this.c;
        }

        public b g() {
            return this.b;
        }

        public final boolean h() {
            return this.f;
        }

        public final boolean i() {
            return this.g;
        }

        public final void j(a a4) {
            this.l();
            this.e.add(a4);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public final void k(c c3, b b3) {
            int n3 = androidx.fragment.app.e0$c.b[b3.ordinal()];
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3 || this.a == c.c) return;
                    if (FragmentManager.I0(2)) {
                        Objects.toString(this.c);
                        Objects.toString((Object)this.a);
                        Objects.toString((Object)c3);
                    }
                    this.a = c3;
                    return;
                }
                if (FragmentManager.I0(2)) {
                    Objects.toString(this.c);
                    Objects.toString((Object)this.a);
                    Objects.toString((Object)this.b);
                }
                this.a = c.c;
                this.b = b.e;
                return;
            }
            if (this.a != c.c) return;
            if (FragmentManager.I0(2)) {
                Objects.toString(this.c);
                Objects.toString((Object)this.b);
            }
            this.a = c.d;
            this.b = b.d;
        }

        public abstract void l();

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Operation ");
            stringBuilder.append("{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append("} ");
            stringBuilder.append("{");
            stringBuilder.append("mFinalState = ");
            stringBuilder.append((Object)this.a);
            stringBuilder.append("} ");
            stringBuilder.append("{");
            stringBuilder.append("mLifecycleImpact = ");
            stringBuilder.append((Object)this.b);
            stringBuilder.append("} ");
            stringBuilder.append("{");
            stringBuilder.append("mFragment = ");
            stringBuilder.append(this.c);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public static final class b
        extends Enum {
            public static final /* enum */ b c;
            public static final /* enum */ b d;
            public static final /* enum */ b e;
            public static final b[] f;

            static {
                b b3;
                b b4;
                b b5;
                c = b5 = new b("NONE", 0);
                d = b4 = new b("ADDING", 1);
                e = b3 = new b("REMOVING", 2);
                f = new b[]{b5, b4, b3};
            }

            /*
             * WARNING - Possible parameter corruption
             * WARNING - void declaration
             */
            public b() {
                void cfr_renamed_1;
                void cfr_renamed_2;
            }

            public static b valueOf(String string) {
                return Enum.valueOf(b.class, string);
            }

            public static b[] values() {
                return (b[])f.clone();
            }
        }

        public static final class c
        extends Enum {
            public static final /* enum */ c c;
            public static final /* enum */ c d;
            public static final /* enum */ c e;
            public static final /* enum */ c f;
            public static final c[] g;

            static {
                c c3;
                c c4;
                c c5;
                c c6;
                c = c6 = new c("REMOVED", 0);
                d = c5 = new c("VISIBLE", 1);
                e = c4 = new c("GONE", 2);
                f = c3 = new c("INVISIBLE", 3);
                g = new c[]{c6, c5, c4, c3};
            }

            /*
             * WARNING - Possible parameter corruption
             * WARNING - void declaration
             */
            public c() {
                void cfr_renamed_1;
                void cfr_renamed_2;
            }

            public static c b(int n3) {
                if (n3 != 0) {
                    if (n3 != 4) {
                        if (n3 == 8) {
                            return e;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown visibility ");
                        stringBuilder.append(n3);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    return f;
                }
                return d;
            }

            public static c c(View view) {
                if (view.getAlpha() == 0.0f && view.getVisibility() == 0) {
                    return f;
                }
                return androidx.fragment.app.e0$e$c.b(view.getVisibility());
            }

            public static c valueOf(String string) {
                return Enum.valueOf(c.class, string);
            }

            public static c[] values() {
                return (c[])g.clone();
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public void a(View view) {
                int n3 = androidx.fragment.app.e0$c.a[this.ordinal()];
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 3) {
                            if (n3 != 4) return;
                            if (FragmentManager.I0(2)) {
                                Objects.toString(view);
                            }
                            view.setVisibility(4);
                            return;
                        }
                        if (FragmentManager.I0(2)) {
                            Objects.toString(view);
                        }
                        view.setVisibility(8);
                        return;
                    }
                    if (FragmentManager.I0(2)) {
                        Objects.toString(view);
                    }
                    view.setVisibility(0);
                    return;
                }
                ViewGroup viewGroup = (ViewGroup)view.getParent();
                if (viewGroup == null) return;
                if (FragmentManager.I0(2)) {
                    view.toString();
                    viewGroup.toString();
                }
                viewGroup.removeView(view);
            }
        }
    }
}

