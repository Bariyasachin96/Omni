/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.b;
import androidx.transition.d;
import f0.k;
import java.util.Map;
import m1.a0;
import m1.b0;
import m1.l;
import m1.n;
import m1.o;
import m1.r;
import m1.y;
import org.xmlpull.v1.XmlPullParser;

public class ChangeBounds
extends Transition {
    public static final String[] Q = new String[]{"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    public static final Property R = new Property(PointF.class, "topLeft"){

        public PointF a(i i3) {
            return null;
        }

        public void b(i i3, PointF pointF) {
            i3.c(pointF);
        }
    };
    public static final Property S = new Property(PointF.class, "bottomRight"){

        public PointF a(i i3) {
            return null;
        }

        public void b(i i3, PointF pointF) {
            i3.a(pointF);
        }
    };
    public static final Property T = new Property(PointF.class, "bottomRight"){

        public PointF a(View view) {
            return null;
        }

        public void b(View view, PointF pointF) {
            b0.e(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
        }
    };
    public static final Property U = new Property(PointF.class, "topLeft"){

        public PointF a(View view) {
            return null;
        }

        public void b(View view, PointF pointF) {
            b0.e(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
        }
    };
    public static final Property V = new Property(PointF.class, "position"){

        public PointF a(View view) {
            return null;
        }

        public void b(View view, PointF pointF) {
            int n3 = Math.round(pointF.x);
            int n4 = Math.round(pointF.y);
            b0.e(view, n3, n4, view.getWidth() + n3, view.getHeight() + n4);
        }
    };
    public static final o W = new o();
    public boolean P = false;

    public ChangeBounds() {
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, m1.r.d);
        boolean bl = f0.k.a((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "resizeClip", 0, false);
        context.recycle();
        this.p0(bl);
    }

    @Override
    public String[] K() {
        return Q;
    }

    @Override
    public void h(y y3) {
        this.o0(y3);
    }

    @Override
    public void k(y y3) {
        Rect rect;
        this.o0(y3);
        if (this.P && (rect = (Rect)y3.b.getTag(m1.n.transition_clip)) != null) {
            y3.a.put("android:changeBounds:clip", rect);
        }
    }

    @Override
    public Animator o(ViewGroup object, y object2, y y3) {
        block22: {
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            int n11;
            int n12;
            int n13;
            int n14;
            int n15;
            View view;
            Map map;
            block27: {
                int n16;
                block26: {
                    block25: {
                        block23: {
                            block24: {
                                if (object2 == null || y3 == null) break block22;
                                object = ((y)object2).a;
                                map = y3.a;
                                object = (ViewGroup)object.get("android:changeBounds:parent");
                                map = (ViewGroup)map.get("android:changeBounds:parent");
                                if (object == null || map == null) break block22;
                                view = y3.b;
                                object = (Rect)((y)object2).a.get("android:changeBounds:bounds");
                                map = (Rect)y3.a.get("android:changeBounds:bounds");
                                n15 = ((Rect)object).left;
                                n14 = ((Rect)map).left;
                                n13 = ((Rect)object).top;
                                n12 = ((Rect)map).top;
                                n11 = ((Rect)object).right;
                                n10 = ((Rect)map).right;
                                n9 = ((Rect)object).bottom;
                                n8 = ((Rect)map).bottom;
                                n7 = n11 - n15;
                                n6 = n9 - n13;
                                n5 = n10 - n14;
                                n4 = n8 - n12;
                                object2 = (Rect)((y)object2).a.get("android:changeBounds:clip");
                                y3 = (Rect)y3.a.get("android:changeBounds:clip");
                                if ((n7 == 0 || n6 == 0) && (n5 == 0 || n4 == 0)) break block23;
                                n3 = n15 == n14 && n13 == n12 ? 0 : 1;
                                if (n11 != n10) break block24;
                                n16 = n3;
                                if (n9 == n8) break block25;
                            }
                            n16 = n3 + 1;
                            break block25;
                        }
                        n16 = 0;
                    }
                    if (object2 != null && !object2.equals((Object)y3)) break block26;
                    n3 = n16;
                    if (object2 != null) break block27;
                    n3 = n16;
                    if (y3 == null) break block27;
                }
                n3 = n16 + 1;
            }
            if (n3 > 0) {
                if (!this.P) {
                    b0.e(view, n15, n13, n11, n9);
                    if (n3 == 2) {
                        if (n7 == n5 && n6 == n4) {
                            object = this.B().a(n15, n13, n14, n12);
                            object = m1.l.a(view, V, (Path)object);
                        } else {
                            object2 = new i(view);
                            object = this.B().a(n15, n13, n14, n12);
                            y3 = m1.l.a(object2, R, (Path)object);
                            object = this.B().a(n11, n9, n10, n8);
                            map = m1.l.a(object2, S, (Path)object);
                            object = new AnimatorSet();
                            object.playTogether(new Animator[]{y3, map});
                            object.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, (i)object2){
                                public final i a;
                                public final ChangeBounds b;
                                private final i mViewBounds;
                                {
                                    this.b = changeBounds;
                                    this.a = i3;
                                    this.mViewBounds = i3;
                                }
                            });
                        }
                    } else if (n15 == n14 && n13 == n12) {
                        object = this.B().a(n11, n9, n10, n8);
                        object = m1.l.a(view, T, (Path)object);
                    } else {
                        object = this.B().a(n15, n13, n14, n12);
                        object = m1.l.a(view, U, (Path)object);
                    }
                } else {
                    boolean bl;
                    b0.e(view, n15, n13, Math.max(n7, n5) + n15, n13 + Math.max(n6, n4));
                    if (n15 == n14 && n13 == n12) {
                        object = null;
                    } else {
                        object = this.B().a(n15, n13, n14, n12);
                        object = m1.l.a(view, V, (Path)object);
                    }
                    boolean bl2 = object2 == null;
                    if (bl2) {
                        object2 = new Rect(0, 0, n7, n6);
                    }
                    if (bl = y3 == null) {
                        y3 = new Rect(0, 0, n5, n4);
                    }
                    if (!object2.equals((Object)y3)) {
                        view.setClipBounds((Rect)object2);
                        map = ObjectAnimator.ofObject((Object)view, (String)"clipBounds", (TypeEvaluator)W, (Object[])new Object[]{object2, y3});
                        object2 = new g(view, (Rect)object2, bl2, (Rect)y3, bl, n15, n13, n11, n9, n14, n12, n10, n8);
                        map.addListener((Animator.AnimatorListener)object2);
                        this.a((Transition.g)object2);
                        object2 = map;
                    } else {
                        object2 = null;
                    }
                    object = androidx.transition.d.c((Animator)object, (Animator)object2);
                }
                if (view.getParent() instanceof ViewGroup) {
                    object2 = (ViewGroup)view.getParent();
                    a0.c((ViewGroup)object2, true);
                    this.D().a(new h((ViewGroup)object2));
                }
                return object;
            }
        }
        return null;
    }

    public final void o0(y y3) {
        View view = y3.b;
        if (view.isLaidOut() || view.getWidth() != 0 || view.getHeight() != 0) {
            y3.a.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            y3.a.put("android:changeBounds:parent", y3.b.getParent());
            if (this.P) {
                y3.a.put("android:changeBounds:clip", view.getClipBounds());
            }
        }
    }

    public void p0(boolean bl) {
        this.P = bl;
    }

    public static class g
    extends AnimatorListenerAdapter
    implements Transition.g {
        public final View a;
        public final Rect b;
        public final boolean c;
        public final Rect d;
        public final boolean e;
        public final int f;
        public final int g;
        public final int h;
        public final int i;
        public final int j;
        public final int k;
        public final int l;
        public final int m;
        public boolean n;

        public g(View view, Rect rect, boolean bl, Rect rect2, boolean bl2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
            this.a = view;
            this.b = rect;
            this.c = bl;
            this.d = rect2;
            this.e = bl2;
            this.f = n3;
            this.g = n4;
            this.h = n5;
            this.i = n6;
            this.j = n7;
            this.k = n8;
            this.l = n9;
            this.m = n10;
        }

        @Override
        public void a(Transition transition) {
        }

        @Override
        public void b(Transition transition) {
            this.n = true;
        }

        @Override
        public void d(Transition transition) {
            transition = this.a.getClipBounds();
            this.a.setTag(m1.n.transition_clip, (Object)transition);
            transition = this.e ? null : this.d;
            this.a.setClipBounds((Rect)transition);
        }

        @Override
        public void e(Transition transition) {
            transition = this.a;
            int n3 = m1.n.transition_clip;
            transition = (Rect)transition.getTag(n3);
            this.a.setTag(n3, null);
            this.a.setClipBounds((Rect)transition);
        }

        @Override
        public void g(Transition transition) {
        }

        public void onAnimationEnd(Animator animator) {
            this.onAnimationEnd(animator, false);
        }

        public void onAnimationEnd(Animator animator, boolean bl) {
            if (this.n) {
                return;
            }
            animator = null;
            if (bl) {
                if (!this.c) {
                    animator = this.b;
                }
            } else if (!this.e) {
                animator = this.d;
            }
            this.a.setClipBounds((Rect)animator);
            if (bl) {
                b0.e(this.a, this.f, this.g, this.h, this.i);
                return;
            }
            b0.e(this.a, this.j, this.k, this.l, this.m);
        }

        public void onAnimationStart(Animator animator) {
            this.onAnimationStart(animator, false);
        }

        public void onAnimationStart(Animator animator, boolean bl) {
            int n3 = Math.max(this.h - this.f, this.l - this.j);
            int n4 = Math.max(this.i - this.g, this.m - this.k);
            int n5 = bl ? this.j : this.f;
            int n6 = bl ? this.k : this.g;
            b0.e(this.a, n5, n6, n3 + n5, n4 + n6);
            animator = bl ? this.d : this.b;
            this.a.setClipBounds((Rect)animator);
        }
    }

    public static class h
    extends b {
        public boolean a = false;
        public final ViewGroup b;

        public h(ViewGroup viewGroup) {
            this.b = viewGroup;
        }

        @Override
        public void b(Transition transition) {
            a0.c(this.b, false);
            this.a = true;
        }

        @Override
        public void d(Transition transition) {
            a0.c(this.b, false);
        }

        @Override
        public void e(Transition transition) {
            a0.c(this.b, true);
        }

        @Override
        public void g(Transition transition) {
            if (!this.a) {
                a0.c(this.b, false);
            }
            transition.a0(this);
        }
    }

    public static class i {
        public int a;
        public int b;
        public int c;
        public int d;
        public final View e;
        public int f;
        public int g;

        public i(View view) {
            this.e = view;
        }

        public void a(PointF pointF) {
            int n3;
            this.c = Math.round(pointF.x);
            this.d = Math.round(pointF.y);
            this.g = n3 = this.g + 1;
            if (this.f == n3) {
                this.b();
            }
        }

        public final void b() {
            b0.e(this.e, this.a, this.b, this.c, this.d);
            this.f = 0;
            this.g = 0;
        }

        public void c(PointF pointF) {
            int n3;
            this.a = Math.round(pointF.x);
            this.b = Math.round(pointF.y);
            this.f = n3 = this.f + 1;
            if (n3 == this.g) {
                this.b();
            }
        }
    }
}

