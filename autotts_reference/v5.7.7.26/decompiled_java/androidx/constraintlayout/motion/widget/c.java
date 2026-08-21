/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.Log
 *  android.util.Xml
 *  android.view.View
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AnimationUtils
 *  android.view.animation.AnticipateInterpolator
 *  android.view.animation.BounceInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.OvershootInterpolator
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.a;
import androidx.constraintlayout.motion.widget.d;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.b;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import x.a;
import x.g;
import x.m;
import x.p;

public class c {
    public int a;
    public int b;
    public boolean c;
    public int d;
    public int e;
    public g f;
    public b.a g;
    public int h;
    public int i;
    public int j;
    public String k;
    public int l;
    public String m;
    public int n;
    public Context o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public c(Context var1_1, XmlPullParser var2_4) {
        block14: {
            block15: {
                super();
                this.b = -1;
                this.c = false;
                this.d = 0;
                this.h = -1;
                this.i = -1;
                this.l = 0;
                this.m = null;
                this.n = -1;
                this.p = -1;
                this.q = -1;
                this.r = -1;
                this.s = -1;
                this.t = -1;
                this.u = -1;
                this.v = -1;
                this.o = var1_1;
                try {
                    var3_5 = var2_4.getEventType();
                    break block14;
                }
                catch (IOException var1_2) {
                }
                catch (XmlPullParserException var1_3) {
                    break block15;
                }
                Log.e((String)"ViewTransition", (String)"Error parsing XML resource", (Throwable)var1_2);
                return;
            }
            Log.e((String)"ViewTransition", (String)"Error parsing XML resource", (Throwable)var1_3);
            return;
        }
        while (var3_5 != 1) {
            block17: {
                block16: {
                    if (var3_5 == 2) break block16;
                    if (var3_5 == 3 && "ViewTransition".equals(var2_4.getName())) {
                        return;
                    }
                    break block17;
                }
                var5_7 = var2_4.getName();
                switch (var5_7.hashCode()) {
                    default: {
                        ** GOTO lbl-1000
                    }
                    case 1791837707: {
                        if (!var5_7.equals("CustomAttribute")) ** GOTO lbl-1000
                        ** GOTO lbl48
                    }
                    case 366511058: {
                        if (!var5_7.equals("CustomMethod")) ** GOTO lbl-1000
lbl48:
                        // 2 sources

                        androidx.constraintlayout.widget.a.i(var1_1, var2_4, this.g.g);
                        break block17;
                    }
                    case 61998586: {
                        if (!var5_7.equals("ViewTransition")) ** GOTO lbl-1000
                        this.k(var1_1, var2_4);
                        break block17;
                    }
                    case -1239391468: {
                        if (!var5_7.equals("KeyFrameSet")) ** GOTO lbl-1000
                        this.f = var4_6 = new g(var1_1, var2_4);
                        break block17;
                    }
                    case -1962203927: 
                }
                if (var5_7.equals("ConstraintOverride")) {
                    this.g = androidx.constraintlayout.widget.b.m(var1_1, var2_4);
                } else lbl-1000:
                // 6 sources

                {
                    var4_6 = new StringBuilder();
                    var4_6.append(x.a.a());
                    var4_6.append(" unknown tag ");
                    var4_6.append(var5_7);
                    Log.e((String)"ViewTransition", (String)var4_6.toString());
                    var4_6 = new StringBuilder();
                    var4_6.append(".xml:");
                    var4_6.append(var2_4.getLineNumber());
                    Log.e((String)"ViewTransition", (String)var4_6.toString());
                }
            }
            var3_5 = var2_4.next();
        }
    }

    public static /* synthetic */ void a(c c3, View[] viewArray) {
        int n3;
        int n4 = c3.p;
        int n5 = 0;
        if (n4 != -1) {
            n3 = viewArray.length;
            for (n4 = 0; n4 < n3; ++n4) {
                viewArray[n4].setTag(c3.p, (Object)System.nanoTime());
            }
        }
        if (c3.q != -1) {
            n3 = viewArray.length;
            for (n4 = n5; n4 < n3; ++n4) {
                viewArray[n4].setTag(c3.q, null);
            }
        }
    }

    public void b(d d3, MotionLayout motionLayout, View view) {
        m m3 = new m(view);
        m3.B(view);
        this.f.a(m3);
        m3.I(motionLayout.getWidth(), motionLayout.getHeight(), this.h, System.nanoTime());
        new b(d3, m3, this.h, this.i, this.b, this.f(motionLayout.getContext()), this.p, this.q);
    }

    public void c(d object, MotionLayout motionLayout, int n3, androidx.constraintlayout.widget.b b3, View ... viewArray) {
        b.a a4;
        Object object2;
        Object object3;
        if (this.c) {
            return;
        }
        int n4 = this.e;
        int n5 = 0;
        if (n4 == 2) {
            this.b((d)object, motionLayout, viewArray[0]);
            return;
        }
        if (n4 == 1) {
            object3 = motionLayout.getConstraintSetIds();
            for (n4 = 0; n4 < ((Object)object3).length; ++n4) {
                object2 = object3[n4];
                if (object2 == n3) continue;
                androidx.constraintlayout.widget.b b4 = motionLayout.o0((int)object2);
                int n6 = viewArray.length;
                for (object2 = 0; object2 < n6; ++object2) {
                    object = b4.v(viewArray[object2].getId());
                    a4 = this.g;
                    if (a4 == null) continue;
                    a4.d((b.a)object);
                    ((b.a)object).g.putAll(this.g.g);
                }
            }
        }
        object3 = new androidx.constraintlayout.widget.b();
        ((androidx.constraintlayout.widget.b)object3).p(b3);
        object2 = viewArray.length;
        for (n4 = 0; n4 < object2; ++n4) {
            a4 = ((androidx.constraintlayout.widget.b)object3).v(viewArray[n4].getId());
            object = this.g;
            if (object == null) continue;
            ((b.a)object).d(a4);
            a4.g.putAll(this.g.g);
        }
        motionLayout.J0(n3, (androidx.constraintlayout.widget.b)object3);
        n4 = y.c.view_transition;
        motionLayout.J0(n4, b3);
        motionLayout.setState(n4, -1, -1);
        object = new a.b(-1, motionLayout.B, n4, n3);
        n4 = viewArray.length;
        for (n3 = n5; n3 < n4; ++n3) {
            this.m((a.b)object, viewArray[n3]);
        }
        motionLayout.setTransition((a.b)object);
        motionLayout.D0(new p(this, viewArray));
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean d(View view) {
        int n3 = this.r;
        n3 = n3 == -1 || view.getTag(n3) != null ? 1 : 0;
        int n4 = this.s;
        n4 = n4 == -1 || view.getTag(n4) == null ? 1 : 0;
        return n3 != 0 && n4 != 0;
    }

    public int e() {
        return this.a;
    }

    public Interpolator f(Context context) {
        int n3 = this.l;
        if (n3 != -2) {
            if (n3 != -1) {
                if (n3 != 0) {
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 4) {
                                if (n3 != 5) {
                                    if (n3 != 6) {
                                        return null;
                                    }
                                    return new AnticipateInterpolator();
                                }
                                return new OvershootInterpolator();
                            }
                            return new BounceInterpolator();
                        }
                        return new DecelerateInterpolator();
                    }
                    return new AccelerateInterpolator();
                }
                return new AccelerateDecelerateInterpolator();
            }
            return new Interpolator(this, s.c.c(this.m)){
                public final s.c a;
                public final c b;
                {
                    this.b = c3;
                    this.a = c4;
                }

                public float getInterpolation(float f3) {
                    return (float)this.a.a(f3);
                }
            };
        }
        return AnimationUtils.loadInterpolator((Context)context, (int)this.n);
    }

    public int g() {
        return this.t;
    }

    public int h() {
        return this.u;
    }

    public int i() {
        return this.b;
    }

    public boolean j(View object) {
        if (object == null) {
            return false;
        }
        if (this.j == -1 && this.k == null) {
            return false;
        }
        if (!this.d((View)object)) {
            return false;
        }
        if (object.getId() == this.j) {
            return true;
        }
        if (this.k == null) {
            return false;
        }
        return object.getLayoutParams() instanceof ConstraintLayout.LayoutParams && (object = ((ConstraintLayout.LayoutParams)object.getLayoutParams()).c0) != null && ((String)object).matches(this.k);
    }

    public final void k(Context context, XmlPullParser object) {
        context = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)object), y.d.ViewTransition);
        int n3 = context.getIndexCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            int n4;
            int n5 = context.getIndex(i3);
            if (n5 == y.d.ViewTransition_android_id) {
                this.a = context.getResourceId(n5, this.a);
                continue;
            }
            if (n5 == y.d.ViewTransition_motionTarget) {
                if (MotionLayout.f1) {
                    this.j = n4 = context.getResourceId(n5, this.j);
                    if (n4 != -1) continue;
                    this.k = context.getString(n5);
                    continue;
                }
                if (context.peekValue((int)n5).type == 3) {
                    this.k = context.getString(n5);
                    continue;
                }
                this.j = context.getResourceId(n5, this.j);
                continue;
            }
            if (n5 == y.d.ViewTransition_onStateTransition) {
                this.b = context.getInt(n5, this.b);
                continue;
            }
            if (n5 == y.d.ViewTransition_transitionDisable) {
                this.c = context.getBoolean(n5, this.c);
                continue;
            }
            if (n5 == y.d.ViewTransition_pathMotionArc) {
                this.d = context.getInt(n5, this.d);
                continue;
            }
            if (n5 == y.d.ViewTransition_duration) {
                this.h = context.getInt(n5, this.h);
                continue;
            }
            if (n5 == y.d.ViewTransition_upDuration) {
                this.i = context.getInt(n5, this.i);
                continue;
            }
            if (n5 == y.d.ViewTransition_viewTransitionMode) {
                this.e = context.getInt(n5, this.e);
                continue;
            }
            if (n5 == y.d.ViewTransition_motionInterpolator) {
                n4 = context.peekValue((int)n5).type;
                if (n4 == 1) {
                    this.n = n5 = context.getResourceId(n5, -1);
                    if (n5 == -1) continue;
                    this.l = -2;
                    continue;
                }
                if (n4 == 3) {
                    object = context.getString(n5);
                    this.m = object;
                    if (object != null && ((String)object).indexOf("/") > 0) {
                        this.n = context.getResourceId(n5, -1);
                        this.l = -2;
                        continue;
                    }
                    this.l = -1;
                    continue;
                }
                this.l = context.getInteger(n5, this.l);
                continue;
            }
            if (n5 == y.d.ViewTransition_setsTag) {
                this.p = context.getResourceId(n5, this.p);
                continue;
            }
            if (n5 == y.d.ViewTransition_clearsTag) {
                this.q = context.getResourceId(n5, this.q);
                continue;
            }
            if (n5 == y.d.ViewTransition_ifTagSet) {
                this.r = context.getResourceId(n5, this.r);
                continue;
            }
            if (n5 == y.d.ViewTransition_ifTagNotSet) {
                this.s = context.getResourceId(n5, this.s);
                continue;
            }
            if (n5 == y.d.ViewTransition_SharedValueId) {
                this.u = context.getResourceId(n5, this.u);
                continue;
            }
            if (n5 != y.d.ViewTransition_SharedValue) continue;
            this.t = context.getInteger(n5, this.t);
        }
        context.recycle();
    }

    public boolean l(int n3) {
        int n4 = this.b;
        if (n4 == 1) {
            return n3 == 0;
        }
        if (n4 == 2) {
            return n3 == 1;
        }
        return n4 == 3 && n3 == 0;
    }

    public final void m(a.b b3, View object) {
        int n3 = this.h;
        if (n3 != -1) {
            b3.E(n3);
        }
        b3.H(this.d);
        b3.F(this.l, this.m, this.n);
        int n4 = object.getId();
        object = this.f;
        if (object != null) {
            ArrayList arrayList = object.d(-1);
            g g3 = new g();
            int n5 = arrayList.size();
            for (n3 = 0; n3 < n5; ++n3) {
                object = arrayList.get(n3);
                g3.c(((x.d)object).b().i(n4));
            }
            b3.t(g3);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewTransition(");
        stringBuilder.append(x.a.c(this.o, this.a));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static class b {
        public final int a;
        public final int b;
        public long c;
        public m d;
        public int e;
        public int f;
        public s.d g = new s.d();
        public d h;
        public Interpolator i;
        public boolean j = false;
        public float k;
        public float l;
        public long m;
        public Rect n = new Rect();
        public boolean o = false;

        public b(d d3, m m3, int n3, int n4, int n5, Interpolator interpolator, int n6, int n7) {
            long l3;
            this.h = d3;
            this.d = m3;
            this.e = n3;
            this.f = n4;
            this.c = l3 = System.nanoTime();
            this.m = l3;
            this.h.b(this);
            this.i = interpolator;
            this.a = n6;
            this.b = n7;
            if (n5 == 3) {
                this.o = true;
            }
            float f3 = n3 == 0 ? Float.MAX_VALUE : 1.0f / (float)n3;
            this.l = f3;
            this.a();
        }

        public void a() {
            if (this.j) {
                this.c();
                return;
            }
            this.b();
        }

        public void b() {
            Object object;
            float f3;
            long l3 = System.nanoTime();
            long l4 = this.m;
            this.m = l3;
            this.k = f3 = this.k + (float)((double)(l3 - l4) * 1.0E-6) * this.l;
            if (f3 >= 1.0f) {
                this.k = 1.0f;
            }
            f3 = (object = this.i) == null ? this.k : object.getInterpolation(this.k);
            object = this.d;
            boolean bl = ((m)object).x(((m)object).b, f3, l3, this.g);
            if (this.k >= 1.0f) {
                if (this.a != -1) {
                    this.d.v().setTag(this.a, (Object)System.nanoTime());
                }
                if (this.b != -1) {
                    this.d.v().setTag(this.b, null);
                }
                if (!this.o) {
                    this.h.g(this);
                }
            }
            if (!(this.k < 1.0f) && !bl) {
                return;
            }
            this.h.e();
        }

        public void c() {
            Object object;
            float f3;
            long l3 = System.nanoTime();
            long l4 = this.m;
            this.m = l3;
            this.k = f3 = this.k - (float)((double)(l3 - l4) * 1.0E-6) * this.l;
            if (f3 < 0.0f) {
                this.k = 0.0f;
            }
            f3 = (object = this.i) == null ? this.k : object.getInterpolation(this.k);
            object = this.d;
            boolean bl = ((m)object).x(((m)object).b, f3, l3, this.g);
            if (this.k <= 0.0f) {
                if (this.a != -1) {
                    this.d.v().setTag(this.a, (Object)System.nanoTime());
                }
                if (this.b != -1) {
                    this.d.v().setTag(this.b, null);
                }
                this.h.g(this);
            }
            if (!(this.k > 0.0f) && !bl) {
                return;
            }
            this.h.e();
        }

        public void d(int n3, float f3, float f4) {
            if (n3 != 1) {
                if (n3 == 2) {
                    this.d.v().getHitRect(this.n);
                    if (!this.n.contains((int)f3, (int)f4) && !this.j) {
                        this.e(true);
                        return;
                    }
                }
            } else if (!this.j) {
                this.e(true);
            }
        }

        public void e(boolean bl) {
            int n3;
            this.j = bl;
            if (bl && (n3 = this.f) != -1) {
                float f3 = n3 == 0 ? Float.MAX_VALUE : 1.0f / (float)n3;
                this.l = f3;
            }
            this.h.e();
            this.m = System.nanoTime();
        }
    }
}

