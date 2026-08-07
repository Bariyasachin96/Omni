/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.ViewGroup
 */
package x;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import x.d;

public class k
extends d {
    public boolean A;
    public float g = 0.1f;
    public int h;
    public int i;
    public int j;
    public RectF k;
    public RectF l;
    public HashMap m;
    public int n;
    public String o;
    public int p;
    public String q;
    public String r;
    public int s;
    public int t;
    public View u;
    public boolean v;
    public boolean w;
    public boolean x;
    public float y;
    public float z;

    public k() {
        int n3;
        this.h = n3 = x.d.f;
        this.i = n3;
        this.j = n3;
        this.k = new RectF();
        this.l = new RectF();
        this.m = new HashMap();
        this.n = -1;
        this.o = null;
        this.p = n3 = x.d.f;
        this.q = null;
        this.r = null;
        this.s = n3;
        this.t = n3;
        this.u = null;
        this.v = true;
        this.w = true;
        this.x = true;
        this.y = Float.NaN;
        this.A = false;
        this.d = 5;
        this.e = new HashMap();
    }

    public static /* synthetic */ float m(k k3, float f3) {
        k3.y = f3;
        return f3;
    }

    public static /* synthetic */ String n(k k3, String string) {
        k3.q = string;
        return string;
    }

    public static /* synthetic */ String o(k k3, String string) {
        k3.r = string;
        return string;
    }

    public static /* synthetic */ String p(k k3, String string) {
        k3.o = string;
        return string;
    }

    public static /* synthetic */ int r(k k3, int n3) {
        k3.s = n3;
        return n3;
    }

    public static /* synthetic */ int t(k k3, int n3) {
        k3.t = n3;
        return n3;
    }

    public static /* synthetic */ boolean v(k k3, boolean bl) {
        k3.A = bl;
        return bl;
    }

    public static /* synthetic */ int x(k k3, int n3) {
        k3.p = n3;
        return n3;
    }

    public final void A(String object, View view) {
        boolean bl = ((String)object).length() == 1;
        String string = object;
        if (!bl) {
            string = ((String)object).substring(1).toLowerCase(Locale.ROOT);
        }
        for (Object object2 : this.e.keySet()) {
            String string2 = ((String)object2).toLowerCase(Locale.ROOT);
            if (!bl && !string2.matches(string) || (object2 = (androidx.constraintlayout.widget.a)this.e.get(object2)) == null) continue;
            ((androidx.constraintlayout.widget.a)object2).a(view);
        }
    }

    public final void B(RectF rectF, View view, boolean bl) {
        rectF.top = view.getTop();
        rectF.bottom = view.getBottom();
        rectF.left = view.getLeft();
        rectF.right = view.getRight();
        if (bl) {
            view.getMatrix().mapRect(rectF);
        }
    }

    @Override
    public void a(HashMap hashMap) {
    }

    @Override
    public d b() {
        return new k().c(this);
    }

    @Override
    public d c(d d3) {
        super.c(d3);
        d3 = (k)d3;
        this.n = ((k)d3).n;
        this.o = ((k)d3).o;
        this.p = ((k)d3).p;
        this.q = ((k)d3).q;
        this.r = ((k)d3).r;
        this.s = ((k)d3).s;
        this.t = ((k)d3).t;
        this.u = ((k)d3).u;
        this.g = ((k)d3).g;
        this.v = ((k)d3).v;
        this.w = ((k)d3).w;
        this.x = ((k)d3).x;
        this.y = ((k)d3).y;
        this.z = ((k)d3).z;
        this.A = ((k)d3).A;
        this.k = ((k)d3).k;
        this.l = ((k)d3).l;
        this.m = ((k)d3).m;
        return this;
    }

    @Override
    public void d(HashSet hashSet) {
    }

    @Override
    public void e(Context context, AttributeSet attributeSet) {
        x.k$a.a(this, context.obtainStyledAttributes(attributeSet, y.d.KeyTrigger), context);
    }

    public void y(float f3, View view) {
        String string;
        boolean bl;
        boolean bl2;
        int n3;
        block29: {
            float f4;
            float f5;
            block35: {
                block34: {
                    block33: {
                        block32: {
                            block31: {
                                block30: {
                                    block28: {
                                        int n4 = this.t;
                                        n3 = x.d.f;
                                        bl2 = true;
                                        bl = false;
                                        if (n4 == n3) break block28;
                                        if (this.u == null) {
                                            this.u = ((ViewGroup)view.getParent()).findViewById(this.t);
                                        }
                                        this.B(this.k, this.u, this.A);
                                        this.B(this.l, view, this.A);
                                        if (this.k.intersect(this.l)) {
                                            if (this.v) {
                                                this.v = false;
                                                n3 = 1;
                                            } else {
                                                n3 = 0;
                                            }
                                            if (this.x) {
                                                this.x = false;
                                                bl2 = true;
                                            } else {
                                                bl2 = false;
                                            }
                                            this.w = true;
                                        } else {
                                            if (!this.v) {
                                                this.v = true;
                                                n3 = 1;
                                            } else {
                                                n3 = 0;
                                            }
                                            if (this.w) {
                                                this.w = false;
                                                bl = true;
                                            } else {
                                                bl = false;
                                            }
                                            this.x = true;
                                            bl2 = false;
                                        }
                                        break block29;
                                    }
                                    if (!this.v) break block30;
                                    f5 = this.y;
                                    if (!((f3 - f5) * (this.z - f5) < 0.0f)) break block31;
                                    this.v = false;
                                    n3 = 1;
                                    break block32;
                                }
                                if (Math.abs(f3 - this.y) > this.g) {
                                    this.v = true;
                                }
                            }
                            n3 = 0;
                        }
                        if (!this.w) break block33;
                        f4 = this.y;
                        f5 = f3 - f4;
                        if (!((this.z - f4) * f5 < 0.0f) || !(f5 < 0.0f)) break block34;
                        this.w = false;
                        bl = true;
                        break block35;
                    }
                    if (Math.abs(f3 - this.y) > this.g) {
                        this.w = true;
                    }
                }
                bl = false;
            }
            if (this.x) {
                f4 = this.y;
                f5 = f3 - f4;
                if ((this.z - f4) * f5 < 0.0f && f5 > 0.0f) {
                    this.x = false;
                } else {
                    bl2 = false;
                }
            } else {
                if (Math.abs(f3 - this.y) > this.g) {
                    this.x = true;
                }
                bl2 = false;
            }
        }
        this.z = f3;
        if (bl || n3 != 0 || bl2) {
            ((MotionLayout)view.getParent()).m0(this.s, bl2, f3);
        }
        View view2 = this.p == x.d.f ? view : ((MotionLayout)view.getParent()).findViewById(this.p);
        if (bl) {
            string = this.q;
            if (string != null) {
                this.z(string, view2);
            }
            if (this.h != x.d.f) {
                ((MotionLayout)view.getParent()).K0(this.h, view2);
            }
        }
        if (bl2) {
            string = this.r;
            if (string != null) {
                this.z(string, view2);
            }
            if (this.i != x.d.f) {
                ((MotionLayout)view.getParent()).K0(this.i, view2);
            }
        }
        if (n3 != 0) {
            string = this.o;
            if (string != null) {
                this.z(string, view2);
            }
            if (this.j != x.d.f) {
                ((MotionLayout)view.getParent()).K0(this.j, view2);
            }
        }
    }

    public final void z(String string, View view) {
        Method method;
        Method method2;
        block10: {
            block9: {
                block8: {
                    if (string == null) break block8;
                    if (string.startsWith(".")) {
                        this.A(string, view);
                        return;
                    }
                    if (!this.m.containsKey(string)) break block9;
                    method = method2 = (Method)this.m.get(string);
                    if (method2 != null) break block10;
                }
                return;
            }
            method = null;
        }
        method2 = method;
        if (method == null) {
            try {
                method2 = view.getClass().getMethod(string, null);
                this.m.put(string, method2);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                this.m.put(string, null);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not find method \"");
                stringBuilder.append(string);
                stringBuilder.append("\"on class ");
                stringBuilder.append(view.getClass().getSimpleName());
                stringBuilder.append(" ");
                stringBuilder.append(x.a.d(view));
                Log.e((String)"KeyTrigger", (String)stringBuilder.toString());
                return;
            }
        }
        try {
            method2.invoke((Object)view, null);
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception in call \"");
            stringBuilder.append(this.o);
            stringBuilder.append("\"on class ");
            stringBuilder.append(view.getClass().getSimpleName());
            stringBuilder.append(" ");
            stringBuilder.append(x.a.d(view));
            Log.e((String)"KeyTrigger", (String)stringBuilder.toString());
            return;
        }
    }

    public static abstract class a {
        public static SparseIntArray a;

        static {
            SparseIntArray sparseIntArray;
            a = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(y.d.KeyTrigger_framePosition, 8);
            a.append(y.d.KeyTrigger_onCross, 4);
            a.append(y.d.KeyTrigger_onNegativeCross, 1);
            a.append(y.d.KeyTrigger_onPositiveCross, 2);
            a.append(y.d.KeyTrigger_motionTarget, 7);
            a.append(y.d.KeyTrigger_triggerId, 6);
            a.append(y.d.KeyTrigger_triggerSlack, 5);
            a.append(y.d.KeyTrigger_motion_triggerOnCollision, 9);
            a.append(y.d.KeyTrigger_motion_postLayoutCollision, 10);
            a.append(y.d.KeyTrigger_triggerReceiver, 11);
            a.append(y.d.KeyTrigger_viewTransitionOnCross, 12);
            a.append(y.d.KeyTrigger_viewTransitionOnNegativeCross, 13);
            a.append(y.d.KeyTrigger_viewTransitionOnPositiveCross, 14);
        }

        public static void a(k k3, TypedArray typedArray, Context object) {
            int n3 = typedArray.getIndexCount();
            block15: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = typedArray.getIndex(i3);
                switch (a.get(n4)) {
                    default: {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("unused attribute 0x");
                        ((StringBuilder)object).append(Integer.toHexString(n4));
                        ((StringBuilder)object).append("   ");
                        ((StringBuilder)object).append(a.get(n4));
                        Log.e((String)"KeyTrigger", (String)((StringBuilder)object).toString());
                        continue block15;
                    }
                    case 14: {
                        k3.i = typedArray.getResourceId(n4, k3.i);
                        continue block15;
                    }
                    case 13: {
                        k3.h = typedArray.getResourceId(n4, k3.h);
                        continue block15;
                    }
                    case 12: {
                        k3.j = typedArray.getResourceId(n4, k3.j);
                        continue block15;
                    }
                    case 11: {
                        x.k.x(k3, typedArray.getResourceId(n4, k3.p));
                        continue block15;
                    }
                    case 10: {
                        x.k.v(k3, typedArray.getBoolean(n4, k3.A));
                        continue block15;
                    }
                    case 9: {
                        x.k.t(k3, typedArray.getResourceId(n4, k3.t));
                        continue block15;
                    }
                    case 8: {
                        int n5;
                        k3.a = n5 = typedArray.getInteger(n4, k3.a);
                        x.k.m(k3, ((float)n5 + 0.5f) / 100.0f);
                        continue block15;
                    }
                    case 7: {
                        int n5;
                        if (MotionLayout.f1) {
                            k3.b = n5 = typedArray.getResourceId(n4, k3.b);
                            if (n5 != -1) continue block15;
                            k3.c = typedArray.getString(n4);
                            continue block15;
                        }
                        if (typedArray.peekValue((int)n4).type == 3) {
                            k3.c = typedArray.getString(n4);
                            continue block15;
                        }
                        k3.b = typedArray.getResourceId(n4, k3.b);
                        continue block15;
                    }
                    case 6: {
                        x.k.r(k3, typedArray.getResourceId(n4, k3.s));
                        continue block15;
                    }
                    case 5: {
                        k3.g = typedArray.getFloat(n4, k3.g);
                        continue block15;
                    }
                    case 4: {
                        x.k.p(k3, typedArray.getString(n4));
                        continue block15;
                    }
                    case 2: {
                        x.k.o(k3, typedArray.getString(n4));
                        continue block15;
                    }
                    case 1: {
                        x.k.n(k3, typedArray.getString(n4));
                    }
                }
            }
        }
    }
}

