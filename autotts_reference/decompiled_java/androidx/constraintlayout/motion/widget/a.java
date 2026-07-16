/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.util.Xml
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
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
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.MotionEvent;
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
import androidx.constraintlayout.motion.widget.c;
import androidx.constraintlayout.motion.widget.d;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import x.g;
import x.m;
import y.e;

public class a {
    public final MotionLayout a;
    public e b = null;
    public b c = null;
    public boolean d = false;
    public ArrayList e = new ArrayList();
    public b f = null;
    public ArrayList g = new ArrayList();
    public SparseArray h = new SparseArray();
    public HashMap i = new HashMap();
    public SparseIntArray j = new SparseIntArray();
    public int k = 400;
    public int l = 0;
    public MotionEvent m;
    public boolean n = false;
    public boolean o = false;
    public MotionLayout.f p;
    public boolean q;
    public final d r;
    public float s;
    public float t;

    public a(Context context, MotionLayout motionLayout, int n3) {
        this.a = motionLayout;
        this.r = new d(motionLayout);
        this.K(context, n3);
        context = this.h;
        n3 = y.c.motion_base;
        context.put(n3, (Object)new androidx.constraintlayout.widget.b());
        this.i.put("motion_base", n3);
    }

    public static String a0(String string) {
        if (string == null) {
            return "";
        }
        int n3 = string.indexOf(47);
        if (n3 < 0) {
            return string;
        }
        return string.substring(n3 + 1);
    }

    public static /* synthetic */ MotionLayout d(a a4) {
        return a4.a;
    }

    public float A() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.l();
        }
        return 0.0f;
    }

    public float B() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.m();
        }
        return 0.0f;
    }

    public float C() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.n();
        }
        return 0.0f;
    }

    public float D() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.o();
        }
        return 0.0f;
    }

    public float E() {
        b b3 = this.c;
        if (b3 != null) {
            return b3.i;
        }
        return 0.0f;
    }

    public int F() {
        b b3 = this.c;
        if (b3 == null) {
            return -1;
        }
        return b3.d;
    }

    public b G(int n3) {
        ArrayList arrayList = this.e;
        int n4 = arrayList.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            Object object = arrayList.get(i3);
            if (((b)(object = (b)object)).a != n3) continue;
            return object;
        }
        return null;
    }

    public List H(int n3) {
        int n4 = this.y(n3);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = this.e;
        int n5 = arrayList2.size();
        n3 = 0;
        while (n3 < n5) {
            Object object = arrayList2.get(n3);
            int n6 = n3 + 1;
            if (((b)(object = (b)object)).d != n4) {
                n3 = n6;
                if (((b)object).c != n4) continue;
            }
            arrayList.add(object);
            n3 = n6;
        }
        return arrayList;
    }

    public final boolean I(int n3) {
        int n4 = this.j.get(n3);
        int n5 = this.j.size();
        while (n4 > 0) {
            if (n4 == n3) {
                return true;
            }
            if (n5 < 0) {
                return true;
            }
            n4 = this.j.get(n4);
            --n5;
        }
        return false;
    }

    public final boolean J() {
        return this.p != null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void K(Context var1_1, int var2_4) {
        block35: {
            block36: {
                var6_5 = var1_1.getResources().getXml(var2_4);
                try {
                    var3_6 = var6_5.getEventType();
                    var4_7 = null;
                    break block35;
                }
                catch (IOException var1_2) {
                }
                catch (XmlPullParserException var1_3) {
                    break block36;
                }
                var4_8 = new StringBuilder();
                var4_8.append("Error parsing resource: ");
                var4_8.append(var2_4);
                Log.e((String)"MotionScene", (String)var4_8.toString(), (Throwable)var1_2);
                return;
            }
            var4_9 = new StringBuilder();
            var4_9.append("Error parsing resource: ");
            var4_9.append(var2_4);
            Log.e((String)"MotionScene", (String)var4_9.toString(), (Throwable)var1_3);
            return;
        }
        while (var3_6 != 1) {
            block38: {
                block37: {
                    if (var3_6 == 2) break block37;
                    var5_10 = var4_7;
                    break block38;
                }
                var7_11 = var6_5.getName();
                switch (var7_11.hashCode()) {
                    default: {
                        var5_10 = var4_7;
                        break block38;
                    }
                    case 1942574248: {
                        var5_10 = var4_7;
                        if (!var7_11.equals("include")) break block38;
                        ** GOTO lbl114
                    }
                    case 1382829617: {
                        var5_10 = var4_7;
                        if (var7_11.equals("StateSet")) {
                            this.b = var5_10 = new e(var1_1, (XmlPullParser)var6_5);
                            var5_10 = var4_7;
                        }
                        break block38;
                    }
                    case 793277014: {
                        var5_10 = var4_7;
                        if (var7_11.equals("MotionScene")) {
                            this.O(var1_1, (XmlPullParser)var6_5);
                            var5_10 = var4_7;
                        }
                        break block38;
                    }
                    case 327855227: {
                        var5_10 = var4_7;
                        if (var7_11.equals("OnSwipe")) {
                            if (var4_7 == null) {
                                var1_1.getResources().getResourceEntryName(var2_4);
                                var6_5.getLineNumber();
                            }
                            var5_10 = var4_7;
                            if (var4_7 != null) {
                                var5_10 = new androidx.constraintlayout.motion.widget.b(var1_1, this.a, (XmlPullParser)var6_5);
                                androidx.constraintlayout.motion.widget.a$b.n((b)var4_7, (androidx.constraintlayout.motion.widget.b)var5_10);
                                var5_10 = var4_7;
                            }
                        }
                        break block38;
                    }
                    case 312750793: {
                        var5_10 = var4_7;
                        if (!var7_11.equals("OnClick")) break block38;
                        var5_10 = var4_7;
                        if (var4_7 != null) {
                            var5_10 = var4_7;
                            if (!this.a.isInEditMode()) {
                                var4_7.u(var1_1, (XmlPullParser)var6_5);
                                var5_10 = var4_7;
                            }
                        }
                        break block38;
                    }
                    case 269306229: {
                        var5_10 = var4_7;
                        if (var7_11.equals("Transition")) {
                            var5_10 = this.e;
                            var4_7 = new b(this, var1_1, (XmlPullParser)var6_5);
                            var5_10.add(var4_7);
                            if (this.c == null && !androidx.constraintlayout.motion.widget.a$b.e((b)var4_7)) {
                                this.c = var4_7;
                                if (androidx.constraintlayout.motion.widget.a$b.l((b)var4_7) != null) {
                                    androidx.constraintlayout.motion.widget.a$b.l(this.c).x(this.q);
                                }
                            }
                            var5_10 = var4_7;
                            if (androidx.constraintlayout.motion.widget.a$b.e((b)var4_7)) {
                                if (androidx.constraintlayout.motion.widget.a$b.a((b)var4_7) == -1) {
                                    this.f = var4_7;
                                } else {
                                    this.g.add(var4_7);
                                }
                                this.e.remove(var4_7);
                                var5_10 = var4_7;
                            }
                        }
                        break block38;
                    }
                    case 61998586: {
                        var5_10 = var4_7;
                        if (var7_11.equals("ViewTransition")) {
                            var5_10 = new c(var1_1, (XmlPullParser)var6_5);
                            this.r.a((c)var5_10);
                            var5_10 = var4_7;
                        }
                        break block38;
                    }
                    case -687739768: {
                        var5_10 = var4_7;
                        if (!var7_11.equals("Include")) break block38;
lbl114:
                        // 2 sources

                        this.N(var1_1, (XmlPullParser)var6_5);
                        var5_10 = var4_7;
                        break block38;
                    }
                    case -1239391468: {
                        var5_10 = var4_7;
                        if (var7_11.equals("KeyFrameSet")) {
                            var7_11 = new g(var1_1, (XmlPullParser)var6_5);
                            var5_10 = var4_7;
                            if (var4_7 != null) {
                                androidx.constraintlayout.motion.widget.a$b.f((b)var4_7).add(var7_11);
                                var5_10 = var4_7;
                            }
                        }
                        break block38;
                    }
                    case -1349929691: 
                }
                var5_10 = var4_7;
                if (var7_11.equals("ConstraintSet")) {
                    this.L(var1_1, (XmlPullParser)var6_5);
                    var5_10 = var4_7;
                }
            }
            var3_6 = var6_5.next();
            var4_7 = var5_10;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int L(Context context, XmlPullParser xmlPullParser) {
        androidx.constraintlayout.widget.b b3 = new androidx.constraintlayout.widget.b();
        b3.Q(false);
        int n3 = xmlPullParser.getAttributeCount();
        int n4 = -1;
        int n5 = -1;
        block41: for (int i3 = 0; i3 < n3; ++i3) {
            int n6;
            String string = xmlPullParser.getAttributeName(i3);
            String string2 = xmlPullParser.getAttributeValue(i3);
            string.getClass();
            int n7 = -1;
            switch (string.hashCode()) {
                case 973381616: {
                    if (!string.equals("stateLabels")) break;
                    n7 = 1;
                    break;
                }
                case 3355: {
                    if (!string.equals("id")) break;
                    n7 = 2;
                    break;
                }
                case -1153153640: {
                    if (!string.equals("constraintRotate")) break;
                    n7 = 3;
                    break;
                }
                case -1496482599: {
                    if (!string.equals("deriveConstraintsFrom")) break;
                    n7 = 4;
                    break;
                }
            }
            switch (n7) {
                default: {
                    n6 = -1;
                    break;
                }
                case 1: {
                    n6 = 3;
                    break;
                }
                case 2: {
                    n6 = 2;
                    break;
                }
                case 3: {
                    n6 = 1;
                    break;
                }
                case 4: {
                    n6 = 0;
                }
            }
            switch (n6) {
                default: {
                    continue block41;
                }
                case 3: {
                    b3.R(string2);
                    continue block41;
                }
                case 2: {
                    n4 = this.r(context, string2);
                    this.i.put(androidx.constraintlayout.motion.widget.a.a0(string2), n4);
                    b3.b = x.a.c(context, n4);
                    continue block41;
                }
                case 1: {
                    try {
                        b3.e = Integer.parseInt(string2);
                    }
                    catch (NumberFormatException numberFormatException) {
                        string2.getClass();
                        tmp = -1;
                        switch (string2.hashCode()) {
                            case 1954540437: {
                                if (!string2.equals("x_right")) break;
                                tmp = 1;
                                break;
                            }
                            case 108511772: {
                                if (!string2.equals("right")) break;
                                tmp = 2;
                                break;
                            }
                            case 3387192: {
                                if (!string2.equals("none")) break;
                                tmp = 3;
                                break;
                            }
                            case 3317767: {
                                if (!string2.equals("left")) break;
                                tmp = 4;
                                break;
                            }
                            case -768416914: {
                                if (!string2.equals("x_left")) break;
                                tmp = 5;
                                break;
                            }
                        }
                        switch (tmp) {
                            default: {
                                n6 = -1;
                                break;
                            }
                            case 1: {
                                n6 = 4;
                                break;
                            }
                            case 2: {
                                n6 = 3;
                                break;
                            }
                            case 3: {
                                n6 = 2;
                                break;
                            }
                            case 4: {
                                n6 = 1;
                                break;
                            }
                            case 5: {
                                n6 = 0;
                            }
                        }
                        switch (n6) {
                            default: {
                                continue block41;
                            }
                            case 4: {
                                b3.e = 3;
                                continue block41;
                            }
                            case 3: {
                                b3.e = 1;
                                continue block41;
                            }
                            case 2: {
                                b3.e = 0;
                                continue block41;
                            }
                            case 1: {
                                b3.e = 2;
                                continue block41;
                            }
                            case 0: 
                        }
                        b3.e = 4;
                    }
                    continue block41;
                }
                case 0: {
                    n5 = this.r(context, string2);
                }
            }
        }
        if (n4 != -1) {
            if (this.a.b0 != 0) {
                b3.S(true);
            }
            b3.D(context, xmlPullParser);
            if (n5 != -1) {
                this.j.put(n4, n5);
            }
            this.h.put(n4, (Object)b3);
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int M(Context object, int n3) {
        int n4;
        XmlResourceParser xmlResourceParser;
        block5: {
            XmlPullParserException xmlPullParserException2;
            block6: {
                xmlResourceParser = object.getResources().getXml(n3);
                try {
                    n4 = xmlResourceParser.getEventType();
                    break block5;
                }
                catch (IOException iOException) {
                }
                catch (XmlPullParserException xmlPullParserException2) {
                    break block6;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Error parsing resource: ");
                ((StringBuilder)object).append(n3);
                Log.e((String)"MotionScene", (String)((StringBuilder)object).toString(), (Throwable)iOException);
                return -1;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Error parsing resource: ");
            ((StringBuilder)object).append(n3);
            Log.e((String)"MotionScene", (String)((StringBuilder)object).toString(), (Throwable)xmlPullParserException2);
            return -1;
        }
        while (n4 != 1) {
            String string = xmlResourceParser.getName();
            if (2 == n4 && "ConstraintSet".equals(string)) {
                return this.L((Context)object, (XmlPullParser)xmlResourceParser);
            }
            n4 = xmlResourceParser.next();
        }
        return -1;
    }

    public final void N(Context context, XmlPullParser xmlPullParser) {
        xmlPullParser = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), y.d.include);
        int n3 = xmlPullParser.getIndexCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            int n4 = xmlPullParser.getIndex(i3);
            if (n4 != y.d.include_constraintSet) continue;
            this.M(context, xmlPullParser.getResourceId(n4, -1));
        }
        xmlPullParser.recycle();
    }

    public final void O(Context context, XmlPullParser xmlPullParser) {
        context = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), y.d.MotionScene);
        int n3 = context.getIndexCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            int n4 = context.getIndex(i3);
            if (n4 == y.d.MotionScene_defaultDuration) {
                this.k = n4 = context.getInt(n4, this.k);
                if (n4 >= 8) continue;
                this.k = 8;
                continue;
            }
            if (n4 != y.d.MotionScene_layoutDuringTransition) continue;
            this.l = context.getInteger(n4, 0);
        }
        context.recycle();
    }

    public void P(float f3, float f4) {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            this.c.l.u(f3, f4);
        }
    }

    public void Q(float f3, float f4) {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            this.c.l.v(f3, f4);
        }
    }

    public void R(MotionEvent object, int n3, MotionLayout motionLayout) {
        block11: {
            Object object2;
            block9: {
                block10: {
                    Object object3;
                    object2 = new RectF();
                    if (this.p == null) {
                        this.p = this.a.v0();
                    }
                    this.p.a((MotionEvent)object);
                    if (n3 == -1) break block9;
                    int n4 = object.getAction();
                    boolean bl = false;
                    if (n4 == 0) break block10;
                    if (n4 != 2 || this.n) break block9;
                    float f3 = object.getRawY() - this.t;
                    float f4 = object.getRawX() - this.s;
                    if ((double)f4 == 0.0 && (double)f3 == 0.0 || (object3 = this.m) == null) break block11;
                    if ((object3 = this.i(n3, f4, f3, (MotionEvent)object3)) == null) break block9;
                    motionLayout.setTransition((b)object3);
                    object2 = this.c.l.p(this.a, (RectF)object2);
                    boolean bl2 = bl;
                    if (object2 != null) {
                        bl2 = bl;
                        if (!object2.contains(this.m.getX(), this.m.getY())) {
                            bl2 = true;
                        }
                    }
                    this.o = bl2;
                    this.c.l.z(this.s, this.t);
                    break block9;
                }
                this.s = object.getRawX();
                this.t = object.getRawY();
                this.m = object;
                this.n = false;
                if (this.c.l != null) {
                    object = this.c.l.f(this.a, (RectF)object2);
                    if (object != null && !object.contains(this.m.getX(), this.m.getY())) {
                        this.m = null;
                        this.n = true;
                        return;
                    }
                    object = this.c.l.p(this.a, (RectF)object2);
                    this.o = object != null && !object.contains(this.m.getX(), this.m.getY());
                    this.c.l.w(this.s, this.t);
                    return;
                }
                break block11;
            }
            if (!this.n) {
                object2 = this.c;
                if (object2 != null && ((b)object2).l != null && !this.o) {
                    this.c.l.s((MotionEvent)object, this.p, n3, this);
                }
                this.s = object.getRawX();
                this.t = object.getRawY();
                if (object.getAction() == 1 && (object = this.p) != null) {
                    object.d();
                    this.p = null;
                    n3 = motionLayout.G;
                    if (n3 != -1) {
                        this.h(motionLayout, n3);
                    }
                }
            }
        }
    }

    public final void S(int n3, MotionLayout object) {
        androidx.constraintlayout.widget.b b3 = (androidx.constraintlayout.widget.b)this.h.get(n3);
        b3.c = b3.b;
        if ((n3 = this.j.get(n3)) > 0) {
            this.S(n3, (MotionLayout)object);
            androidx.constraintlayout.widget.b b4 = (androidx.constraintlayout.widget.b)this.h.get(n3);
            if (b4 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ERROR! invalid deriveConstraintsFrom: @id/");
                ((StringBuilder)object).append(x.a.c(this.a.getContext(), n3));
                Log.e((String)"MotionScene", (String)((StringBuilder)object).toString());
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(b3.c);
            ((StringBuilder)object).append("/");
            ((StringBuilder)object).append(b4.c);
            b3.c = ((StringBuilder)object).toString();
            b3.L(b4);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(b3.c);
            stringBuilder.append("  layout");
            b3.c = stringBuilder.toString();
            b3.K((ConstraintLayout)((Object)object));
        }
        b3.h(b3);
    }

    public void T(MotionLayout motionLayout) {
        for (int i3 = 0; i3 < this.h.size(); ++i3) {
            int n3 = this.h.keyAt(i3);
            if (this.I(n3)) {
                Log.e((String)"MotionScene", (String)"Cannot be derived from yourself");
                return;
            }
            this.S(n3, motionLayout);
        }
    }

    public void U(int n3, androidx.constraintlayout.widget.b b3) {
        this.h.put(n3, (Object)b3);
    }

    public void V(int n3) {
        b b3 = this.c;
        if (b3 != null) {
            b3.E(n3);
            return;
        }
        this.k = n3;
    }

    public void W(boolean bl) {
        this.q = bl;
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            this.c.l.x(this.q);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void X(int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        Object object;
        block8: {
            block7: {
                block6: {
                    object = this.b;
                    if (object == null) break block6;
                    n8 = ((e)object).c(n3, -1, -1);
                    if (n8 == -1) {
                        n8 = n3;
                    }
                    n7 = this.b.c(n4, -1, -1);
                    n6 = n8;
                    if (n7 == -1) break block7;
                    n6 = n7;
                    break block8;
                }
                n6 = n3;
            }
            n7 = n4;
            n8 = n6;
            n6 = n7;
        }
        object = this.c;
        if (object != null && ((b)object).c == n4 && this.c.d == n3) {
            return;
        }
        object = this.e;
        int n9 = ((ArrayList)object).size();
        int n10 = 0;
        n7 = 0;
        while (n7 < n9) {
            Object e3 = ((ArrayList)object).get(n7);
            n5 = n7 + 1;
            b b3 = (b)e3;
            if (b3.c != n6 || b3.d != n8) {
                n7 = n5;
                if (b3.c != n4) continue;
                n7 = n5;
                if (b3.d != n3) continue;
            }
            this.c = b3;
            if (b3 == null) return;
            if (b3.l == null) return;
            this.c.l.x(this.q);
            return;
        }
        object = this.f;
        ArrayList arrayList = this.g;
        n5 = arrayList.size();
        n3 = n10;
        while (n3 < n5) {
            Object e4 = arrayList.get(n3);
            n7 = n3 + 1;
            b b4 = (b)e4;
            n3 = n7;
            if (b4.c != n4) continue;
            object = b4;
            n3 = n7;
        }
        object = new b(this, (b)object);
        androidx.constraintlayout.motion.widget.a$b.d((b)object, n8);
        androidx.constraintlayout.motion.widget.a$b.b((b)object, n6);
        if (n8 != -1) {
            this.e.add(object);
        }
        this.c = object;
    }

    public void Y(b b3) {
        this.c = b3;
        if (b3 != null && b3.l != null) {
            this.c.l.x(this.q);
        }
    }

    public void Z() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            this.c.l.A();
        }
    }

    public boolean b0() {
        Object object = this.e;
        int n3 = ((ArrayList)object).size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object e3 = ((ArrayList)object).get(i3);
            if (((b)e3).l == null) continue;
            return true;
        }
        object = this.c;
        return object != null && ((b)object).l != null;
    }

    public void c0(int n3, View ... viewArray) {
        this.r.i(n3, viewArray);
    }

    public void f(MotionLayout motionLayout, int n3) {
        Object object;
        int n4;
        int n5;
        ArrayList arrayList;
        int n6;
        Object object2;
        ArrayList arrayList2 = this.e;
        int n7 = arrayList2.size();
        int n8 = 0;
        block0: while (n8 < n7) {
            object2 = arrayList2.get(n8);
            n6 = n8 + 1;
            object2 = (b)object2;
            n8 = n6;
            if (((b)object2).m.size() <= 0) continue;
            arrayList = ((b)object2).m;
            n5 = arrayList.size();
            n4 = 0;
            while (true) {
                n8 = n6;
                if (n4 >= n5) continue block0;
                object2 = arrayList.get(n4);
                ++n4;
                ((b.a)object2).c(motionLayout);
            }
        }
        arrayList2 = this.g;
        n7 = arrayList2.size();
        n4 = 0;
        block2: while (n4 < n7) {
            object2 = arrayList2.get(n4);
            n6 = n4 + 1;
            object2 = (b)object2;
            n4 = n6;
            if (((b)object2).m.size() <= 0) continue;
            object2 = ((b)object2).m;
            n5 = ((ArrayList)object2).size();
            n8 = 0;
            while (true) {
                n4 = n6;
                if (n8 >= n5) continue block2;
                arrayList = ((ArrayList)object2).get(n8);
                ++n8;
                ((b.a)((Object)arrayList)).c(motionLayout);
            }
        }
        arrayList2 = this.e;
        n7 = arrayList2.size();
        n4 = 0;
        block4: while (n4 < n7) {
            object2 = arrayList2.get(n4);
            n6 = n4 + 1;
            object2 = (b)object2;
            n4 = n6;
            if (((b)object2).m.size() <= 0) continue;
            object = ((b)object2).m;
            n5 = ((ArrayList)object).size();
            n8 = 0;
            while (true) {
                n4 = n6;
                if (n8 >= n5) continue block4;
                arrayList = ((ArrayList)object).get(n8);
                ++n8;
                ((b.a)((Object)arrayList)).a(motionLayout, n3, (b)object2);
            }
        }
        arrayList2 = this.g;
        n7 = arrayList2.size();
        n4 = 0;
        block6: while (n4 < n7) {
            object2 = arrayList2.get(n4);
            n6 = n4 + 1;
            object = (b)object2;
            n4 = n6;
            if (((b)object).m.size() <= 0) continue;
            object2 = ((b)object).m;
            n5 = ((ArrayList)object2).size();
            n8 = 0;
            while (true) {
                n4 = n6;
                if (n8 >= n5) continue block6;
                arrayList = ((ArrayList)object2).get(n8);
                ++n8;
                ((b.a)((Object)arrayList)).a(motionLayout, n3, (b)object);
            }
        }
    }

    public boolean g(int n3, m m3) {
        return this.r.d(n3, m3);
    }

    public boolean h(MotionLayout motionLayout, int n3) {
        if (this.J()) {
            return false;
        }
        if (this.d) {
            return false;
        }
        Object object = this.e;
        int n4 = object.size();
        int n5 = 0;
        while (n5 < n4) {
            Object object2 = object.get(n5);
            int n6 = n5 + 1;
            if (((b)(object2 = (b)object2)).n == 0) {
                n5 = n6;
                continue;
            }
            b b3 = this.c;
            if (b3 == object2 && b3.D(2)) {
                n5 = n6;
                continue;
            }
            if (n3 == ((b)object2).d && (((b)object2).n == 4 || ((b)object2).n == 2)) {
                object = MotionLayout.j.f;
                motionLayout.setState((MotionLayout.j)((Object)object));
                motionLayout.setTransition((b)object2);
                if (((b)object2).n == 4) {
                    motionLayout.C0();
                    motionLayout.setState(MotionLayout.j.d);
                    motionLayout.setState(MotionLayout.j.e);
                } else {
                    motionLayout.setProgress(1.0f);
                    motionLayout.h0(true);
                    motionLayout.setState(MotionLayout.j.d);
                    motionLayout.setState(MotionLayout.j.e);
                    motionLayout.setState((MotionLayout.j)((Object)object));
                    motionLayout.w0();
                }
                return true;
            }
            n5 = n6;
            if (n3 != ((b)object2).c) continue;
            if (((b)object2).n != 3) {
                n5 = n6;
                if (((b)object2).n != 1) continue;
            }
            object = MotionLayout.j.f;
            motionLayout.setState((MotionLayout.j)((Object)object));
            motionLayout.setTransition((b)object2);
            if (((b)object2).n == 3) {
                motionLayout.E0();
                motionLayout.setState(MotionLayout.j.d);
                motionLayout.setState(MotionLayout.j.e);
            } else {
                motionLayout.setProgress(0.0f);
                motionLayout.h0(true);
                motionLayout.setState(MotionLayout.j.d);
                motionLayout.setState(MotionLayout.j.e);
                motionLayout.setState((MotionLayout.j)((Object)object));
                motionLayout.w0();
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public b i(int n3, float f3, float f4, MotionEvent motionEvent) {
        if (n3 == -1) {
            return this.c;
        }
        Object object = this.H(n3);
        RectF rectF = new RectF();
        Iterator iterator = object.iterator();
        float f5 = 0.0f;
        object = null;
        while (iterator.hasNext()) {
            float f6;
            b b3 = (b)iterator.next();
            if (b3.o || b3.l == null) continue;
            b3.l.x(this.q);
            RectF rectF2 = b3.l.p(this.a, rectF);
            if (rectF2 != null && motionEvent != null && !rectF2.contains(motionEvent.getX(), motionEvent.getY()) || (rectF2 = b3.l.f(this.a, rectF)) != null && motionEvent != null && !rectF2.contains(motionEvent.getX(), motionEvent.getY())) continue;
            float f7 = f6 = b3.l.a(f3, f4);
            if (((b)b3).l.l) {
                f7 = f6;
                if (motionEvent != null) {
                    f7 = motionEvent.getX() - ((b)b3).l.i;
                    f6 = motionEvent.getY() - ((b)b3).l.j;
                    f7 = (float)(Math.atan2(f4 + f6, f3 + f7) - Math.atan2(f7, f6)) * 10.0f;
                }
            }
            if (!((f7 *= (f6 = b3.c == n3 ? -1.0f : 1.1f)) > f5)) continue;
            object = b3;
            f5 = f7;
        }
        return object;
    }

    public int j() {
        b b3 = this.c;
        if (b3 != null) {
            return b3.p;
        }
        return -1;
    }

    public int k() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.d();
        }
        return 0;
    }

    public androidx.constraintlayout.widget.b l(int n3) {
        return this.m(n3, -1, -1);
    }

    public androidx.constraintlayout.widget.b m(int n3, int n4, int n5) {
        Object object = this.b;
        int n6 = n3;
        if (object != null) {
            n4 = ((e)object).c(n3, n4, n5);
            n6 = n3;
            if (n4 != -1) {
                n6 = n4;
            }
        }
        if (this.h.get(n6) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Warning could not find ConstraintSet id/");
            ((StringBuilder)object).append(x.a.c(this.a.getContext(), n6));
            ((StringBuilder)object).append(" In MotionScene");
            Log.e((String)"MotionScene", (String)((StringBuilder)object).toString());
            object = this.h;
            return (androidx.constraintlayout.widget.b)object.get(object.keyAt(0));
        }
        return (androidx.constraintlayout.widget.b)this.h.get(n6);
    }

    public int[] n() {
        int n3 = this.h.size();
        int[] nArray = new int[n3];
        for (int i3 = 0; i3 < n3; ++i3) {
            nArray[i3] = this.h.keyAt(i3);
        }
        return nArray;
    }

    public ArrayList o() {
        return this.e;
    }

    public int p() {
        b b3 = this.c;
        if (b3 != null) {
            return b3.h;
        }
        return this.k;
    }

    public int q() {
        b b3 = this.c;
        if (b3 == null) {
            return -1;
        }
        return b3.c;
    }

    public final int r(Context context, String string) {
        int n3;
        if (string.contains("/")) {
            String string2 = string.substring(string.indexOf(47) + 1);
            n3 = context.getResources().getIdentifier(string2, "id", context.getPackageName());
        } else {
            n3 = -1;
        }
        if (n3 == -1) {
            if (string.length() > 1) {
                return Integer.parseInt(string.substring(1));
            }
            Log.e((String)"MotionScene", (String)"error in parsing id");
        }
        return n3;
    }

    public Interpolator s() {
        int n3 = this.c.e;
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
            return new Interpolator(this, s.c.c(this.c.f)){
                public final s.c a;
                public final a b;
                {
                    this.b = a4;
                    this.a = c3;
                }

                public float getInterpolation(float f3) {
                    return (float)this.a.a(f3);
                }
            };
        }
        return AnimationUtils.loadInterpolator((Context)this.a.getContext(), (int)this.c.g);
    }

    public void t(m m3) {
        block3: {
            int n3;
            b b3;
            block2: {
                b3 = this.c;
                int n4 = 0;
                if (b3 != null) break block2;
                b3 = this.f;
                if (b3 == null) break block3;
                ArrayList arrayList = b3.k;
                n4 = arrayList.size();
                for (n3 = 0; n3 < n4; ++n3) {
                    b3 = arrayList.get(n3);
                    ((g)((Object)b3)).b(m3);
                }
                break block3;
            }
            ArrayList arrayList = b3.k;
            int n5 = arrayList.size();
            for (n3 = n4; n3 < n5; ++n3) {
                b3 = arrayList.get(n3);
                ((g)((Object)b3)).b(m3);
            }
        }
    }

    public float u() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.g();
        }
        return 0.0f;
    }

    public float v() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.h();
        }
        return 0.0f;
    }

    public boolean w() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.i();
        }
        return false;
    }

    public float x(float f3, float f4) {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.j(f3, f4);
        }
        return 0.0f;
    }

    public final int y(int n3) {
        int n4;
        e e3 = this.b;
        if (e3 != null && (n4 = e3.c(n3, -1, -1)) != -1) {
            return n4;
        }
        return n3;
    }

    public int z() {
        b b3 = this.c;
        if (b3 != null && b3.l != null) {
            return this.c.l.k();
        }
        return 0;
    }

    public static class b {
        public int a = -1;
        public boolean b = false;
        public int c = -1;
        public int d = -1;
        public int e = 0;
        public String f = null;
        public int g = -1;
        public int h = 400;
        public float i = 0.0f;
        public final androidx.constraintlayout.motion.widget.a j;
        public ArrayList k = new ArrayList();
        public androidx.constraintlayout.motion.widget.b l = null;
        public ArrayList m = new ArrayList();
        public int n = 0;
        public boolean o = false;
        public int p = -1;
        public int q = 0;
        public int r = 0;

        public b(int n3, androidx.constraintlayout.motion.widget.a a4, int n4, int n5) {
            this.a = n3;
            this.j = a4;
            this.d = n4;
            this.c = n5;
            this.h = a4.k;
            this.q = a4.l;
        }

        public b(androidx.constraintlayout.motion.widget.a a4, Context context, XmlPullParser xmlPullParser) {
            this.h = a4.k;
            this.q = a4.l;
            this.j = a4;
            this.w(a4, context, Xml.asAttributeSet((XmlPullParser)xmlPullParser));
        }

        public b(androidx.constraintlayout.motion.widget.a a4, b b3) {
            this.j = a4;
            this.h = a4.k;
            if (b3 != null) {
                this.p = b3.p;
                this.e = b3.e;
                this.f = b3.f;
                this.g = b3.g;
                this.h = b3.h;
                this.k = b3.k;
                this.i = b3.i;
                this.q = b3.q;
            }
        }

        public static /* synthetic */ int b(b b3, int n3) {
            b3.c = n3;
            return n3;
        }

        public static /* synthetic */ int d(b b3, int n3) {
            b3.d = n3;
            return n3;
        }

        public static /* synthetic */ boolean e(b b3) {
            return b3.b;
        }

        public static /* synthetic */ androidx.constraintlayout.motion.widget.b n(b b3, androidx.constraintlayout.motion.widget.b b4) {
            b3.l = b4;
            return b4;
        }

        public static /* synthetic */ androidx.constraintlayout.motion.widget.a s(b b3) {
            return b3.j;
        }

        public int A() {
            return this.d;
        }

        public androidx.constraintlayout.motion.widget.b B() {
            return this.l;
        }

        public boolean C() {
            return this.o ^ true;
        }

        public boolean D(int n3) {
            return (n3 & this.r) != 0;
        }

        public void E(int n3) {
            this.h = Math.max(n3, 8);
        }

        public void F(int n3, String string, int n4) {
            this.e = n3;
            this.f = string;
            this.g = n4;
        }

        public void G(int n3) {
            androidx.constraintlayout.motion.widget.b b3 = this.B();
            if (b3 != null) {
                b3.y(n3);
            }
        }

        public void H(int n3) {
            this.p = n3;
        }

        public void t(g g3) {
            this.k.add(g3);
        }

        public void u(Context context, XmlPullParser xmlPullParser) {
            this.m.add(new a(context, this, xmlPullParser));
        }

        public final void v(androidx.constraintlayout.motion.widget.a a4, Context context, TypedArray typedArray) {
            int n3 = typedArray.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                Object object;
                int n4 = typedArray.getIndex(i3);
                if (n4 == y.d.Transition_constraintSetEnd) {
                    this.c = typedArray.getResourceId(n4, -1);
                    object = context.getResources().getResourceTypeName(this.c);
                    if ("layout".equals(object)) {
                        object = new androidx.constraintlayout.widget.b();
                        ((androidx.constraintlayout.widget.b)object).C(context, this.c);
                        a4.h.append(this.c, object);
                        continue;
                    }
                    if (!"xml".equals(object)) continue;
                    this.c = a4.M(context, this.c);
                    continue;
                }
                if (n4 == y.d.Transition_constraintSetStart) {
                    this.d = typedArray.getResourceId(n4, this.d);
                    object = context.getResources().getResourceTypeName(this.d);
                    if ("layout".equals(object)) {
                        object = new androidx.constraintlayout.widget.b();
                        ((androidx.constraintlayout.widget.b)object).C(context, this.d);
                        a4.h.append(this.d, object);
                        continue;
                    }
                    if (!"xml".equals(object)) continue;
                    this.d = a4.M(context, this.d);
                    continue;
                }
                if (n4 == y.d.Transition_motionInterpolator) {
                    int n5 = typedArray.peekValue((int)n4).type;
                    if (n5 == 1) {
                        this.g = n4 = typedArray.getResourceId(n4, -1);
                        if (n4 == -1) continue;
                        this.e = -2;
                        continue;
                    }
                    if (n5 == 3) {
                        this.f = object = typedArray.getString(n4);
                        if (object == null) continue;
                        if (((String)object).indexOf("/") > 0) {
                            this.g = typedArray.getResourceId(n4, -1);
                            this.e = -2;
                            continue;
                        }
                        this.e = -1;
                        continue;
                    }
                    this.e = typedArray.getInteger(n4, this.e);
                    continue;
                }
                if (n4 == y.d.Transition_duration) {
                    this.h = n4 = typedArray.getInt(n4, this.h);
                    if (n4 >= 8) continue;
                    this.h = 8;
                    continue;
                }
                if (n4 == y.d.Transition_staggered) {
                    this.i = typedArray.getFloat(n4, this.i);
                    continue;
                }
                if (n4 == y.d.Transition_autoTransition) {
                    this.n = typedArray.getInteger(n4, this.n);
                    continue;
                }
                if (n4 == y.d.Transition_android_id) {
                    this.a = typedArray.getResourceId(n4, this.a);
                    continue;
                }
                if (n4 == y.d.Transition_transitionDisable) {
                    this.o = typedArray.getBoolean(n4, this.o);
                    continue;
                }
                if (n4 == y.d.Transition_pathMotionArc) {
                    this.p = typedArray.getInteger(n4, -1);
                    continue;
                }
                if (n4 == y.d.Transition_layoutDuringTransition) {
                    this.q = typedArray.getInteger(n4, 0);
                    continue;
                }
                if (n4 != y.d.Transition_transitionFlags) continue;
                this.r = typedArray.getInteger(n4, 0);
            }
            if (this.d == -1) {
                this.b = true;
            }
        }

        public final void w(androidx.constraintlayout.motion.widget.a a4, Context context, AttributeSet attributeSet) {
            attributeSet = context.obtainStyledAttributes(attributeSet, y.d.Transition);
            this.v(a4, context, (TypedArray)attributeSet);
            attributeSet.recycle();
        }

        public int x() {
            return this.n;
        }

        public int y() {
            return this.c;
        }

        public int z() {
            return this.q;
        }

        public static class a
        implements View.OnClickListener {
            public final b c;
            public int d = -1;
            public int e = 17;

            public a(Context context, b b3, XmlPullParser xmlPullParser) {
                this.c = b3;
                context = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), y.d.OnClick);
                int n3 = context.getIndexCount();
                for (int i3 = 0; i3 < n3; ++i3) {
                    int n4 = context.getIndex(i3);
                    if (n4 == y.d.OnClick_targetId) {
                        this.d = context.getResourceId(n4, this.d);
                        continue;
                    }
                    if (n4 != y.d.OnClick_clickAction) continue;
                    this.e = context.getInt(n4, this.e);
                }
                context.recycle();
            }

            public void a(MotionLayout object, int n3, b b3) {
                int n4 = this.d;
                if (n4 != -1) {
                    object = object.findViewById(n4);
                }
                if (object == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("OnClick could not find id ");
                    ((StringBuilder)object).append(this.d);
                    Log.e((String)"MotionScene", (String)((StringBuilder)object).toString());
                    return;
                }
                int n5 = b3.d;
                int n6 = b3.c;
                if (n5 == -1) {
                    object.setOnClickListener((View.OnClickListener)this);
                    return;
                }
                int n7 = this.e;
                int n8 = 0;
                n4 = (n7 & 1) != 0 && n3 == n5 ? 1 : 0;
                int n9 = (n7 & 0x100) != 0 && n3 == n5 ? 1 : 0;
                n5 = (n7 & 1) != 0 && n3 == n5 ? 1 : 0;
                int n10 = (n7 & 0x10) != 0 && n3 == n6 ? 1 : 0;
                int n11 = n8;
                if ((n7 & 0x1000) != 0) {
                    n11 = n8;
                    if (n3 == n6) {
                        n11 = 1;
                    }
                }
                if ((n5 | (n4 | n9) | n10 | n11) != 0) {
                    object.setOnClickListener((View.OnClickListener)this);
                }
            }

            public boolean b(b b3, MotionLayout motionLayout) {
                b b4 = this.c;
                if (b4 == b3) {
                    return true;
                }
                int n3 = b4.c;
                int n4 = this.c.d;
                if (n4 == -1) {
                    return motionLayout.G != n3;
                }
                int n5 = motionLayout.G;
                return n5 == n4 || n5 == n3;
                {
                }
            }

            public void c(MotionLayout object) {
                int n3 = this.d;
                if (n3 == -1) {
                    return;
                }
                if ((object = object.findViewById(n3)) == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" (*)  could not find id ");
                    ((StringBuilder)object).append(this.d);
                    Log.e((String)"MotionScene", (String)((StringBuilder)object).toString());
                    return;
                }
                object.setOnClickListener(null);
            }

            /*
             * Unable to fully structure code
             */
            public void onClick(View var1_1) {
                block13: {
                    block14: {
                        var1_1 = androidx.constraintlayout.motion.widget.a.d(androidx.constraintlayout.motion.widget.a$b.s(this.c));
                        if (!var1_1.u0()) break block13;
                        if (androidx.constraintlayout.motion.widget.a$b.c(this.c) == -1) {
                            var2_2 = var1_1.getCurrentState();
                            if (var2_2 == -1) {
                                var1_1.F0(androidx.constraintlayout.motion.widget.a$b.a(this.c));
                                return;
                            }
                            var7_4 = new b(androidx.constraintlayout.motion.widget.a$b.s(this.c), this.c);
                            androidx.constraintlayout.motion.widget.a$b.d(var7_4, var2_2);
                            androidx.constraintlayout.motion.widget.a$b.b(var7_4, androidx.constraintlayout.motion.widget.a$b.a(this.c));
                            var1_1.setTransition(var7_4);
                            var1_1.C0();
                            return;
                        }
                        var8_6 = androidx.constraintlayout.motion.widget.a$b.s((b)this.c).c;
                        var2_3 = this.e;
                        var6_7 = false;
                        var3_8 = (var2_3 & 1) != 0 || (var2_3 & 256) != 0;
                        var2_3 = (var2_3 & 16) == 0 && (var2_3 & 4096) == 0 ? 0 : 1;
                        var4_9 = var2_3;
                        if (!var3_8) ** GOTO lbl37
                        var4_9 = var2_3;
                        if (var2_3 == 0) ** GOTO lbl37
                        var7_5 = androidx.constraintlayout.motion.widget.a$b.s((b)this.c).c;
                        var9_10 = this.c;
                        if (var7_5 != var9_10) {
                            var1_1.setTransition(var9_10);
                        }
                        var4_9 = var2_3;
                        var5_11 = var6_7;
                        if (var1_1.getCurrentState() == var1_1.getEndState()) break block14;
                        if (var1_1.getProgress() > 0.5f) {
                            var4_9 = var2_3;
                            var5_11 = var6_7;
                        } else {
                            var4_9 = 0;
lbl37:
                            // 3 sources

                            var5_11 = var3_8;
                        }
                    }
                    if (this.b(var8_6, (MotionLayout)var1_1)) {
                        if (var5_11 && (this.e & 1) != 0) {
                            var1_1.setTransition(this.c);
                            var1_1.C0();
                            return;
                        }
                        if (var4_9 != 0 && (this.e & 16) != 0) {
                            var1_1.setTransition(this.c);
                            var1_1.E0();
                            return;
                        }
                        if (var5_11 && (this.e & 256) != 0) {
                            var1_1.setTransition(this.c);
                            var1_1.setProgress(1.0f);
                            return;
                        }
                        if (var4_9 != 0 && (this.e & 4096) != 0) {
                            var1_1.setTransition(this.c);
                            var1_1.setProgress(0.0f);
                        }
                    }
                }
            }
        }
    }
}

