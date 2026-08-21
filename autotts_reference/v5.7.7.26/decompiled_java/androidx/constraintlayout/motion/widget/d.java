/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 */
package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.c;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.b;
import androidx.constraintlayout.widget.c;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashSet;
import x.m;

public class d {
    public final MotionLayout a;
    public ArrayList b = new ArrayList();
    public HashSet c;
    public String d = "ViewTransitionController";
    public ArrayList e;
    public ArrayList f = new ArrayList();

    public d(MotionLayout motionLayout) {
        this.a = motionLayout;
    }

    public void a(c c3) {
        this.b.add(c3);
        this.c = null;
        if (c3.i() == 4) {
            this.f(c3, true);
            return;
        }
        if (c3.i() == 5) {
            this.f(c3, false);
        }
    }

    public void b(c.b b3) {
        if (this.e == null) {
            this.e = new ArrayList();
        }
        this.e.add(b3);
    }

    public void c() {
        ArrayList arrayList = this.e;
        if (arrayList != null) {
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                Object e3 = arrayList.get(i3);
                ((c.b)e3).a();
            }
            this.e.removeAll(this.f);
            this.f.clear();
            if (this.e.isEmpty()) {
                this.e = null;
            }
        }
    }

    public boolean d(int n3, m m3) {
        ArrayList arrayList = this.b;
        int n4 = arrayList.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            Object object = arrayList.get(i3);
            if (((c)(object = (c)object)).e() != n3) continue;
            ((c)object).f.a(m3);
            return true;
        }
        return false;
    }

    public void e() {
        this.a.invalidate();
    }

    public final void f(c c3, boolean bl) {
        int n3 = c3.h();
        int n4 = c3.g();
        ConstraintLayout.getSharedValues().a(c3.h(), new c.a(this, c3, n3, bl, n4){
            public final c c;
            public final int d;
            public final boolean e;
            public final int f;
            public final d g;
            {
                this.g = d3;
                this.c = c3;
                this.d = n3;
                this.e = bl;
                this.f = n4;
            }
        });
    }

    public void g(c.b b3) {
        this.f.add(b3);
    }

    public void h(MotionEvent object) {
        int n3 = this.a.getCurrentState();
        if (n3 != -1) {
            c c3;
            int n4;
            int n5;
            Object object22;
            int n6;
            AbstractCollection abstractCollection = this.c;
            int n7 = 0;
            if (abstractCollection == null) {
                this.c = new HashSet();
                abstractCollection = this.b;
                int n8 = ((ArrayList)abstractCollection).size();
                n6 = 0;
                block0: while (n6 < n8) {
                    object22 = ((ArrayList)abstractCollection).get(n6);
                    n5 = n6 + 1;
                    object22 = (c)object22;
                    int n9 = this.a.getChildCount();
                    n4 = 0;
                    while (true) {
                        n6 = n5;
                        if (n4 >= n9) continue block0;
                        c3 = this.a.getChildAt(n4);
                        if (((c)object22).j((View)c3)) {
                            c3.getId();
                            this.c.add(c3);
                        }
                        ++n4;
                    }
                }
            }
            float f3 = object.getX();
            float f4 = object.getY();
            abstractCollection = new Rect();
            n4 = object.getAction();
            object = this.e;
            if (object != null && !((ArrayList)object).isEmpty()) {
                object22 = this.e;
                n5 = ((ArrayList)object22).size();
                for (n6 = 0; n6 < n5; ++n6) {
                    object = ((ArrayList)object22).get(n6);
                    ((c.b)object).d(n4, f3, f4);
                }
            }
            if (n4 == 0 || n4 == 1) {
                b b3 = this.a.o0(n3);
                ArrayList arrayList = this.b;
                n5 = arrayList.size();
                for (n6 = n7; n6 < n5; ++n6) {
                    c3 = (c)arrayList.get(n6);
                    if (!c3.l(n4)) continue;
                    for (Object object22 : this.c) {
                        if (!c3.j((View)object22)) continue;
                        object22.getHitRect((Rect)abstractCollection);
                        if (!abstractCollection.contains((int)f3, (int)f4)) continue;
                        c3.c(this, this.a, n3, b3, new View[]{object22});
                    }
                }
            }
        }
    }

    public void i(int n3, View ... viewArray) {
        ArrayList<View> arrayList = new ArrayList<View>();
        ArrayList arrayList2 = this.b;
        int n4 = arrayList2.size();
        Object object3 = null;
        int n5 = 0;
        while (n5 < n4) {
            Object object2 = arrayList2.get(n5);
            int n6 = n5 + 1;
            object2 = (c)object2;
            n5 = n6;
            if (((c)object2).e() != n3) continue;
            for (Object object3 : viewArray) {
                if (!((c)object2).d((View)object3)) continue;
                arrayList.add((View)object3);
            }
            if (!arrayList.isEmpty()) {
                this.j((c)object2, arrayList.toArray(new View[0]));
                arrayList.clear();
            }
            object3 = object2;
            n5 = n6;
        }
        if (object3 == null) {
            Log.e((String)this.d, (String)" Could not find ViewTransition");
        }
    }

    public final void j(c object, View ... object2) {
        int n3 = this.a.getCurrentState();
        if (((c)object).e != 2) {
            if (n3 == -1) {
                object2 = this.d;
                object = new StringBuilder();
                ((StringBuilder)object).append("No support for ViewTransition within transition yet. Currently: ");
                ((StringBuilder)object).append(this.a.toString());
                Log.w((String)object2, (String)((StringBuilder)object).toString());
                return;
            }
            b b3 = this.a.o0(n3);
            if (b3 == null) {
                return;
            }
            ((c)object).c(this, this.a, n3, b3, (View)object2);
            return;
        }
        ((c)object).c(this, this.a, n3, null, (View)object2);
    }
}

