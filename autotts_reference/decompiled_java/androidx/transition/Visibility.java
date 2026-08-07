/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.Animator$AnimatorPauseListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.d;
import f0.k;
import m1.a0;
import m1.b0;
import m1.n;
import m1.r;
import m1.y;
import org.xmlpull.v1.XmlPullParser;

public abstract class Visibility
extends Transition {
    public static final String[] Q = new String[]{"android:visibility:visibility", "android:visibility:parent"};
    public int P = 3;

    public Visibility() {
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, m1.r.e);
        int n3 = f0.k.g((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "transitionVisibilityMode", 0, 0);
        context.recycle();
        if (n3 != 0) {
            this.v0(n3);
        }
    }

    private void o0(y y3) {
        int n3 = y3.b.getVisibility();
        y3.a.put("android:visibility:visibility", n3);
        y3.a.put("android:visibility:parent", y3.b.getParent());
        int[] nArray = new int[2];
        y3.b.getLocationOnScreen(nArray);
        y3.a.put("android:visibility:screenLocation", nArray);
    }

    @Override
    public String[] K() {
        return Q;
    }

    @Override
    public boolean M(y object, y y3) {
        if (object == null && y3 == null) {
            return false;
        }
        if (object != null && y3 != null && y3.a.containsKey("android:visibility:visibility") != ((y)object).a.containsKey("android:visibility:visibility")) {
            return false;
        }
        object = this.q0((y)object, y3);
        return ((c)object).a && (((c)object).c == 0 || ((c)object).d == 0);
    }

    @Override
    public void h(y y3) {
        this.o0(y3);
    }

    @Override
    public void k(y y3) {
        this.o0(y3);
    }

    @Override
    public Animator o(ViewGroup viewGroup, y y3, y y4) {
        c c3 = this.q0(y3, y4);
        if (c3.a && (c3.e != null || c3.f != null)) {
            if (c3.b) {
                return this.s0(viewGroup, y3, c3.c, y4, c3.d);
            }
            return this.u0(viewGroup, y3, c3.c, y4, c3.d);
        }
        return null;
    }

    public int p0() {
        return this.P;
    }

    public final c q0(y y3, y y4) {
        c c3 = new c();
        c3.a = false;
        c3.b = false;
        if (y3 != null && y3.a.containsKey("android:visibility:visibility")) {
            c3.c = (Integer)y3.a.get("android:visibility:visibility");
            c3.e = (ViewGroup)y3.a.get("android:visibility:parent");
        } else {
            c3.c = -1;
            c3.e = null;
        }
        if (y4 != null && y4.a.containsKey("android:visibility:visibility")) {
            c3.d = (Integer)y4.a.get("android:visibility:visibility");
            c3.f = (ViewGroup)y4.a.get("android:visibility:parent");
        } else {
            c3.d = -1;
            c3.f = null;
        }
        if (y3 != null && y4 != null) {
            int n3 = c3.c;
            int n4 = c3.d;
            if (n3 != n4 || c3.e != c3.f) {
                if (n3 != n4) {
                    if (n3 == 0) {
                        c3.b = false;
                        c3.a = true;
                        return c3;
                    }
                    if (n4 == 0) {
                        c3.b = true;
                        c3.a = true;
                        return c3;
                    }
                } else {
                    if (c3.f == null) {
                        c3.b = false;
                        c3.a = true;
                        return c3;
                    }
                    if (c3.e == null) {
                        c3.b = true;
                        c3.a = true;
                        return c3;
                    }
                }
            }
        } else {
            if (y3 == null && c3.d == 0) {
                c3.b = true;
                c3.a = true;
                return c3;
            }
            if (y4 == null && c3.c == 0) {
                c3.b = false;
                c3.a = true;
            }
        }
        return c3;
    }

    public Animator r0(ViewGroup viewGroup, View view, y y3, y y4) {
        return null;
    }

    public Animator s0(ViewGroup viewGroup, y y3, int n3, y y4, int n4) {
        if ((this.P & 1) == 1 && y4 != null) {
            if (y3 == null) {
                View view = (View)y4.b.getParent();
                if (this.q0((y)this.z((View)view, (boolean)false), (y)this.L((View)view, (boolean)false)).a) {
                    return null;
                }
            }
            return this.r0(viewGroup, y4.b, y3, y4);
        }
        return null;
    }

    public Animator t0(ViewGroup viewGroup, View view, y y3, y y4) {
        return null;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public Animator u0(ViewGroup var1_1, y var2_2, int var3_3, y var4_4, int var5_5) {
        block15: {
            block18: {
                block19: {
                    block17: {
                        block14: {
                            block16: {
                                if ((this.P & 2) != 2) {
                                    return null;
                                }
                                if (var2_2 == null) {
                                    return null;
                                }
                                var11_6 = var2_2.b;
                                var9_7 /* !! */  = var4_4 != null ? var4_4.b : null;
                                var6_8 = m1.n.save_overlay_view;
                                var8_9 = (View)var11_6.getTag(var6_8);
                                if (var8_9 == null) break block16;
                                var9_7 /* !! */  = null;
                                var3_3 = 1;
                                break block15;
                            }
                            if (var9_7 /* !! */  == null || var9_7 /* !! */ .getParent() == null) ** GOTO lbl25
                            if (var5_5 /* !! */  == 4 || var11_6 == var9_7 /* !! */ ) {
                                var8_9 = var9_7 /* !! */ ;
                                var3_3 = 0;
                                var9_7 /* !! */  = null;
                            } else {
                                while (true) {
                                    var9_7 /* !! */  = null;
                                    var8_9 = null;
                                    var3_3 = 1;
                                    break block14;
                                    break;
                                }
lbl25:
                                // 1 sources

                                if (var9_7 /* !! */  == null) ** continue;
                                var8_9 = null;
                                var3_3 = 0;
                            }
                        }
                        var10_10 = var9_7 /* !! */ ;
                        if (var3_3 == 0) break block17;
                        if (var11_6.getParent() == null) break block18;
                        var10_10 = var9_7 /* !! */ ;
                        if (!(var11_6.getParent() instanceof View)) break block17;
                        var12_11 = (View)var11_6.getParent();
                        if (this.q0((y)this.L((View)var12_11, (boolean)true), (y)this.z((View)var12_11, (boolean)true)).a) break block19;
                        var10_10 = androidx.transition.d.a(var1_1 /* !! */ , var11_6, var12_11);
                    }
lbl38:
                    // 5 sources

                    while (true) {
                        var3_3 = 0;
                        var9_7 /* !! */  = var8_9;
                        var8_9 = var10_10;
                        break block15;
                        break;
                    }
                }
                var3_3 = var12_11.getId();
                var10_10 = var9_7 /* !! */ ;
                if (var12_11.getParent() != null) ** GOTO lbl38
                var10_10 = var9_7 /* !! */ ;
                if (var3_3 == -1) ** GOTO lbl38
                var10_10 = var9_7 /* !! */ ;
                if (var1_1 /* !! */ .findViewById(var3_3) == null) ** GOTO lbl38
                var10_10 = var9_7 /* !! */ ;
                if (this.y) ** break;
                ** while (true)
            }
            var9_7 /* !! */  = var8_9;
            var3_3 = 0;
            var8_9 = var11_6;
        }
        if (var8_9 != null) {
            if (var3_3 == 0) {
                var9_7 /* !! */  = (View)((int[])var2_2.a.get("android:visibility:screenLocation"));
                var5_5 /* !! */  = (int)var9_7 /* !! */ [0];
                var7_12 = var9_7 /* !! */ [1];
                var9_7 /* !! */  = (View)new int[2];
                var1_1 /* !! */ .getLocationOnScreen((int[])var9_7 /* !! */ );
                var8_9.offsetLeftAndRight(var5_5 /* !! */  - var9_7 /* !! */ [0] - var8_9.getLeft());
                var8_9.offsetTopAndBottom((int)(var7_12 - var9_7 /* !! */ [1] - var8_9.getTop()));
                var1_1 /* !! */ .getOverlay().add(var8_9);
            }
            var2_2 = this.t0(var1_1 /* !! */ , var8_9, (y)var2_2, var4_4);
            if (var3_3 == 0) {
                if (var2_2 == null) {
                    var1_1 /* !! */ .getOverlay().remove(var8_9);
                    return var2_2;
                }
                var11_6.setTag(var6_8, (Object)var8_9);
                var1_1 /* !! */  = new b(this, var1_1 /* !! */ , var8_9, var11_6);
                var2_2.addListener((Animator.AnimatorListener)var1_1 /* !! */ );
                var2_2.addPauseListener((Animator.AnimatorPauseListener)var1_1 /* !! */ );
                this.D().a((Transition.g)var1_1 /* !! */ );
            }
            return var2_2;
        }
        if (var9_7 /* !! */  != null) {
            var3_3 = var9_7 /* !! */ .getVisibility();
            b0.g(var9_7 /* !! */ , 0);
            var1_1 /* !! */  = this.t0(var1_1 /* !! */ , var9_7 /* !! */ , (y)var2_2, var4_4);
            if (var1_1 /* !! */  != null) {
                var2_2 = new a(var9_7 /* !! */ , var5_5 /* !! */ , true);
                var1_1 /* !! */ .addListener((Animator.AnimatorListener)var2_2);
                this.D().a((Transition.g)var2_2);
                return var1_1 /* !! */ ;
            }
            b0.g(var9_7 /* !! */ , var3_3);
            return var1_1 /* !! */ ;
        }
        return null;
    }

    public void v0(int n3) {
        if ((n3 & 0xFFFFFFFC) == 0) {
            this.P = n3;
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }

    public static class a
    extends AnimatorListenerAdapter
    implements Transition.g {
        public final View a;
        public final int b;
        public final ViewGroup c;
        public final boolean d;
        public boolean e;
        public boolean f = false;

        public a(View view, int n3, boolean bl) {
            this.a = view;
            this.b = n3;
            this.c = (ViewGroup)view.getParent();
            this.d = bl;
            this.i(true);
        }

        @Override
        public void a(Transition transition) {
        }

        @Override
        public void b(Transition transition) {
        }

        @Override
        public void d(Transition transition) {
            this.i(false);
            if (!this.f) {
                b0.g(this.a, this.b);
            }
        }

        @Override
        public void e(Transition transition) {
            this.i(true);
            if (!this.f) {
                b0.g(this.a, 0);
            }
        }

        @Override
        public void g(Transition transition) {
            transition.a0(this);
        }

        public final void h() {
            if (!this.f) {
                b0.g(this.a, this.b);
                ViewGroup viewGroup = this.c;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            this.i(false);
        }

        public final void i(boolean bl) {
            ViewGroup viewGroup;
            if (this.d && this.e != bl && (viewGroup = this.c) != null) {
                this.e = bl;
                a0.c(viewGroup, bl);
            }
        }

        public void onAnimationCancel(Animator animator) {
            this.f = true;
        }

        public void onAnimationEnd(Animator animator) {
            this.h();
        }

        public void onAnimationEnd(Animator animator, boolean bl) {
            if (!bl) {
                this.h();
            }
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        public void onAnimationStart(Animator animator, boolean bl) {
            if (bl) {
                b0.g(this.a, 0);
                animator = this.c;
                if (animator != null) {
                    animator.invalidate();
                }
            }
        }
    }

    public class b
    extends AnimatorListenerAdapter
    implements Transition.g {
        public final ViewGroup a;
        public final View b;
        public final View c;
        public boolean d;
        public final Visibility e;

        public b(Visibility visibility, ViewGroup viewGroup, View view, View view2) {
            this.e = visibility;
            this.d = true;
            this.a = viewGroup;
            this.b = view;
            this.c = view2;
        }

        @Override
        public void a(Transition transition) {
        }

        @Override
        public void b(Transition transition) {
            if (this.d) {
                this.h();
            }
        }

        @Override
        public void d(Transition transition) {
        }

        @Override
        public void e(Transition transition) {
        }

        @Override
        public void g(Transition transition) {
            transition.a0(this);
        }

        public final void h() {
            this.c.setTag(m1.n.save_overlay_view, null);
            this.a.getOverlay().remove(this.b);
            this.d = false;
        }

        public void onAnimationEnd(Animator animator) {
            this.h();
        }

        public void onAnimationEnd(Animator animator, boolean bl) {
            if (!bl) {
                this.h();
            }
        }

        public void onAnimationPause(Animator animator) {
            this.a.getOverlay().remove(this.b);
        }

        public void onAnimationResume(Animator animator) {
            if (this.b.getParent() == null) {
                this.a.getOverlay().add(this.b);
                return;
            }
            this.e.g();
        }

        public void onAnimationStart(Animator animator, boolean bl) {
            if (bl) {
                this.c.setTag(m1.n.save_overlay_view, (Object)this.b);
                this.a.getOverlay().add(this.b);
                this.d = true;
            }
        }
    }

    public static class c {
        public boolean a;
        public boolean b;
        public int c;
        public int d;
        public ViewGroup e;
        public ViewGroup f;
    }
}

