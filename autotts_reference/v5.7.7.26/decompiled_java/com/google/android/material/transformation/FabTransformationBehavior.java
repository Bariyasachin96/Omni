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
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Pair
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewAnimationUtils
 *  android.view.ViewGroup
 *  android.widget.ImageView
 */
package com.google.android.material.transformation;

import a2.b;
import a2.d;
import a2.h;
import a2.i;
import a2.j;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.circularreveal.c;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transformation.ExpandableTransformationBehavior;
import com.google.android.material.transformation.TransformationChildCard;
import com.google.android.material.transformation.TransformationChildLayout;
import java.util.ArrayList;
import java.util.List;
import o2.a;
import z1.g;

@Deprecated
public abstract class FabTransformationBehavior
extends ExpandableTransformationBehavior {
    public final Rect e = new Rect();
    public final RectF f = new RectF();
    public final RectF g = new RectF();
    public final int[] h = new int[2];
    public float i;
    public float j;

    public FabTransformationBehavior() {
    }

    public FabTransformationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public AnimatorSet N(View view, View view2, boolean bl, boolean bl2) {
        e e3 = this.i0(view2.getContext(), bl);
        if (bl) {
            this.i = view.getTranslationX();
            this.j = view.getTranslationY();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        this.b0(view, view2, bl, bl2, e3, arrayList, arrayList2);
        RectF rectF = this.f;
        this.g0(view, view2, bl, bl2, e3, arrayList, arrayList2, rectF);
        float f3 = rectF.width();
        float f4 = rectF.height();
        this.a0(view, view2, bl, e3, arrayList);
        this.d0(view, view2, bl, bl2, e3, arrayList, arrayList2);
        this.c0(view, view2, bl, bl2, e3, f3, f4, arrayList, arrayList2);
        this.Z(view, view2, bl, bl2, e3, arrayList, arrayList2);
        this.Y(view, view2, bl, bl2, e3, arrayList, arrayList2);
        e3 = new AnimatorSet();
        b.a((AnimatorSet)e3, arrayList);
        e3.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, bl, view2, view){
            public final boolean a;
            public final View b;
            public final View c;
            public final FabTransformationBehavior d;
            {
                this.d = fabTransformationBehavior;
                this.a = bl;
                this.b = view;
                this.c = view2;
            }

            public void onAnimationEnd(Animator animator) {
                if (!this.a) {
                    this.b.setVisibility(4);
                    this.c.setAlpha(1.0f);
                    this.c.setVisibility(0);
                }
            }

            public void onAnimationStart(Animator animator) {
                if (this.a) {
                    this.b.setVisibility(0);
                    this.c.setAlpha(0.0f);
                    this.c.setVisibility(4);
                }
            }
        });
        int n3 = arrayList2.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            e3.addListener((Animator.AnimatorListener)arrayList2.get(i3));
        }
        return e3;
    }

    public final ViewGroup O(View view) {
        View view2 = view.findViewById(z1.g.mtrl_child_content_container);
        if (view2 != null) {
            return this.j0(view2);
        }
        if (!(view instanceof TransformationChildLayout) && !(view instanceof TransformationChildCard)) {
            return this.j0(view);
        }
        return this.j0(((ViewGroup)view).getChildAt(0));
    }

    public final void P(View view, e e3, i i3, i i4, float f3, float f4, float f5, float f6, RectF rectF) {
        f3 = this.W(e3, i3, f3, f5);
        f4 = this.W(e3, i4, f4, f6);
        i3 = this.e;
        view.getWindowVisibleDisplayFrame((Rect)i3);
        e3 = this.f;
        e3.set((Rect)i3);
        i3 = this.g;
        this.X(view, (RectF)i3);
        i3.offset(f3, f4);
        i3.intersect((RectF)e3);
        rectF.set((RectF)i3);
    }

    public final void Q(View view, RectF rectF) {
        this.X(view, rectF);
        rectF.offset(this.i, this.j);
    }

    public final Pair R(float f3, float f4, boolean bl, e object) {
        i i3;
        float f5;
        float f6;
        if (f3 != 0.0f && (f6 = (f5 = f4 - 0.0f) == 0.0f ? 0 : (f5 > 0.0f ? 1 : -1)) != false) {
            if (bl && f4 < 0.0f || !bl && f6 > 0) {
                i3 = ((e)object).a.h("translationXCurveUpwards");
                i i4 = ((e)object).a.h("translationYCurveUpwards");
                object = i3;
                i3 = i4;
            } else {
                i3 = ((e)object).a.h("translationXCurveDownwards");
                i i5 = ((e)object).a.h("translationYCurveDownwards");
                object = i3;
                i3 = i5;
            }
        } else {
            i i6 = ((e)object).a.h("translationXLinear");
            i3 = ((e)object).a.h("translationYLinear");
            object = i6;
        }
        return new Pair(object, (Object)i3);
    }

    public final float S(View view, View view2, j j3) {
        RectF rectF = this.f;
        RectF rectF2 = this.g;
        this.Q(view, rectF);
        this.X(view2, rectF2);
        rectF2.offset(-this.U(view, view2, j3), 0.0f);
        return rectF.centerX() - rectF2.left;
    }

    public final float T(View view, View view2, j j3) {
        RectF rectF = this.f;
        RectF rectF2 = this.g;
        this.Q(view, rectF);
        this.X(view2, rectF2);
        rectF2.offset(0.0f, -this.V(view, view2, j3));
        return rectF.centerY() - rectF2.top;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final float U(View view, View view2, j j3) {
        float f3;
        float f4;
        RectF rectF = this.f;
        RectF rectF2 = this.g;
        this.Q(view, rectF);
        this.X(view2, rectF2);
        int n3 = j3.a & 7;
        if (n3 != 1) {
            if (n3 != 3) {
                if (n3 != 5) {
                    f4 = 0.0f;
                    return f4 + j3.b;
                }
                f4 = rectF2.right;
                f3 = rectF.right;
                return (f4 -= f3) + j3.b;
            } else {
                f4 = rectF2.left;
                f3 = rectF.left;
            }
            return (f4 -= f3) + j3.b;
        }
        f4 = rectF2.centerX();
        f3 = rectF.centerX();
        return (f4 -= f3) + j3.b;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final float V(View view, View view2, j j3) {
        float f3;
        float f4;
        RectF rectF = this.f;
        RectF rectF2 = this.g;
        this.Q(view, rectF);
        this.X(view2, rectF2);
        int n3 = j3.a & 0x70;
        if (n3 != 16) {
            if (n3 != 48) {
                if (n3 != 80) {
                    f4 = 0.0f;
                    return f4 + j3.c;
                }
                f4 = rectF2.bottom;
                f3 = rectF.bottom;
                return (f4 -= f3) + j3.c;
            } else {
                f4 = rectF2.top;
                f3 = rectF.top;
            }
            return (f4 -= f3) + j3.c;
        }
        f4 = rectF2.centerY();
        f3 = rectF.centerY();
        return (f4 -= f3) + j3.c;
    }

    public final float W(e object, i i3, float f3, float f4) {
        long l3 = i3.c();
        long l4 = i3.d();
        object = ((e)object).a.h("expansion");
        float f5 = (float)(((i)object).c() + ((i)object).d() + 17L - l3) / (float)l4;
        return a2.a.a(f3, f4, i3.e().getInterpolation(f5));
    }

    public final void X(View view, RectF rectF) {
        rectF.set(0.0f, 0.0f, (float)view.getWidth(), (float)view.getHeight());
        int[] nArray = this.h;
        view.getLocationInWindow(nArray);
        rectF.offsetTo((float)nArray[0], (float)nArray[1]);
        rectF.offset((float)((int)(-view.getTranslationX())), (float)((int)(-view.getTranslationY())));
    }

    public final void Y(View view, View view2, boolean bl, boolean bl2, e e3, List list, List list2) {
        if (!(view2 instanceof ViewGroup) || (view = this.O(view2)) == null) {
            return;
        }
        if (bl) {
            if (!bl2) {
                a2.d.a.set((Object)view, (Object)Float.valueOf(0.0f));
            }
            view = ObjectAnimator.ofFloat((Object)view, (Property)a2.d.a, (float[])new float[]{1.0f});
        } else {
            view = ObjectAnimator.ofFloat((Object)view, (Property)a2.d.a, (float[])new float[]{0.0f});
        }
        e3.a.h("contentFade").a((Animator)view);
        list.add(view);
    }

    public final void Z(View view, View object, boolean bl, boolean bl2, e e3, List list, List list2) {
        if (!(object instanceof c)) {
            return;
        }
        object = (c)object;
        int n3 = this.h0(view);
        if (bl) {
            if (!bl2) {
                object.setCircularRevealScrimColor(n3);
            }
            view = ObjectAnimator.ofInt((Object)object, (Property)c.d.a, (int[])new int[]{0xFFFFFF & n3});
        } else {
            view = ObjectAnimator.ofInt((Object)object, (Property)c.d.a, (int[])new int[]{n3});
        }
        view.setEvaluator((TypeEvaluator)a2.c.b());
        e3.a.h("color").a((Animator)view);
        list.add(view);
    }

    public final void a0(View view, View object, boolean bl, e object2, List list) {
        float f3 = this.U(view, (View)object, ((e)object2).b);
        float f4 = this.V(view, (View)object, ((e)object2).b);
        object2 = this.R(f3, f4, bl, (e)object2);
        object = (i)((Pair)object2).first;
        object2 = (i)((Pair)object2).second;
        Property property = View.TRANSLATION_X;
        if (!bl) {
            f3 = this.i;
        }
        property = ObjectAnimator.ofFloat((Object)view, (Property)property, (float[])new float[]{f3});
        Property property2 = View.TRANSLATION_Y;
        f3 = bl ? f4 : this.j;
        view = ObjectAnimator.ofFloat((Object)view, (Property)property2, (float[])new float[]{f3});
        ((i)object).a((Animator)property);
        ((i)object2).a((Animator)view);
        list.add(property);
        list.add(view);
    }

    public final void b0(View view, View view2, boolean bl, boolean bl2, e e3, List list, List list2) {
        float f3 = view2.getElevation() - view.getElevation();
        if (bl) {
            if (!bl2) {
                view2.setTranslationZ(-f3);
            }
            view = ObjectAnimator.ofFloat((Object)view2, (Property)View.TRANSLATION_Z, (float[])new float[]{0.0f});
        } else {
            view = ObjectAnimator.ofFloat((Object)view2, (Property)View.TRANSLATION_Z, (float[])new float[]{-f3});
        }
        e3.a.h("elevation").a((Animator)view);
        list.add(view);
    }

    public final void c0(View view, View view2, boolean bl, boolean bl2, e e3, float f3, float f4, List list, List list2) {
        if (!(view2 instanceof c)) {
            return;
        }
        c c3 = (c)view2;
        float f5 = this.S(view, view2, e3.b);
        float f6 = this.T(view, view2, e3.b);
        ((FloatingActionButton)view).h(this.e);
        float f7 = (float)this.e.width() / 2.0f;
        i i3 = e3.a.h("expansion");
        if (bl) {
            if (!bl2) {
                c3.setRevealInfo(new c.e(f5, f6, f7));
            }
            if (bl2) {
                f7 = c3.getRevealInfo().c;
            }
            view = com.google.android.material.circularreveal.a.a(c3, f5, f6, a.c(f5, f6, 0.0f, 0.0f, f3, f4));
            view.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, c3){
                public final c a;
                public final FabTransformationBehavior b;
                {
                    this.b = fabTransformationBehavior;
                    this.a = c3;
                }

                public void onAnimationEnd(Animator object) {
                    object = this.a.getRevealInfo();
                    object.c = Float.MAX_VALUE;
                    this.a.setRevealInfo((c.e)object);
                }
            });
            this.f0(view2, i3.c(), (int)f5, (int)f6, f7, list);
        } else {
            f3 = c3.getRevealInfo().c;
            view = com.google.android.material.circularreveal.a.a(c3, f5, f6, f7);
            long l3 = i3.c();
            int n3 = (int)f5;
            int n4 = (int)f6;
            this.f0(view2, l3, n3, n4, f3, list);
            this.e0(view2, i3.c(), i3.d(), e3.a.i(), n3, n4, f7, list);
        }
        i3.a((Animator)view);
        list.add(view);
        list2.add(com.google.android.material.circularreveal.a.b(c3));
    }

    public final void d0(View view, View view2, boolean bl, boolean bl2, e e3, List list, List list2) {
        if (view2 instanceof c && view instanceof ImageView) {
            c c3 = (c)view2;
            Drawable drawable = ((ImageView)view).getDrawable();
            if (drawable != null) {
                drawable.mutate();
                if (bl) {
                    if (!bl2) {
                        drawable.setAlpha(255);
                    }
                    view = ObjectAnimator.ofInt((Object)drawable, (Property)a2.e.a, (int[])new int[]{0});
                } else {
                    view = ObjectAnimator.ofInt((Object)drawable, (Property)a2.e.a, (int[])new int[]{255});
                }
                view.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this, view2){
                    public final View a;
                    public final FabTransformationBehavior b;
                    {
                        this.b = fabTransformationBehavior;
                        this.a = view;
                    }

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.a.invalidate();
                    }
                });
                e3.a.h("iconFade").a((Animator)view);
                list.add(view);
                list2.add(new AnimatorListenerAdapter(this, c3, drawable){
                    public final c a;
                    public final Drawable b;
                    public final FabTransformationBehavior c;
                    {
                        this.c = fabTransformationBehavior;
                        this.a = c3;
                        this.b = drawable;
                    }

                    public void onAnimationEnd(Animator animator) {
                        this.a.setCircularRevealOverlayDrawable(null);
                    }

                    public void onAnimationStart(Animator animator) {
                        this.a.setCircularRevealOverlayDrawable(this.b);
                    }
                });
            }
        }
    }

    public final void e0(View view, long l3, long l4, long l5, int n3, int n4, float f3, List list) {
        if ((l3 += l4) < l5) {
            view = ViewAnimationUtils.createCircularReveal((View)view, (int)n3, (int)n4, (float)f3, (float)f3);
            view.setStartDelay(l3);
            view.setDuration(l5 - l3);
            list.add(view);
        }
    }

    public final void f0(View view, long l3, int n3, int n4, float f3, List list) {
        if (l3 > 0L) {
            view = ViewAnimationUtils.createCircularReveal((View)view, (int)n3, (int)n4, (float)f3, (float)f3);
            view.setStartDelay(0L);
            view.setDuration(l3);
            list.add(view);
        }
    }

    public final void g0(View view, View object, boolean bl, boolean bl2, e e3, List list, List list2, RectF rectF) {
        float f3 = this.U(view, (View)object, e3.b);
        float f4 = this.V(view, (View)object, e3.b);
        view = this.R(f3, f4, bl, e3);
        i i3 = (i)view.first;
        i i4 = (i)view.second;
        if (bl) {
            if (!bl2) {
                object.setTranslationX(-f3);
                object.setTranslationY(-f4);
            }
            view = ObjectAnimator.ofFloat((Object)object, (Property)View.TRANSLATION_X, (float[])new float[]{0.0f});
            list2 = ObjectAnimator.ofFloat((Object)object, (Property)View.TRANSLATION_Y, (float[])new float[]{0.0f});
            this.P((View)object, e3, i3, i4, -f3, -f4, 0.0f, 0.0f, rectF);
            object = list2;
        } else {
            view = ObjectAnimator.ofFloat((Object)object, (Property)View.TRANSLATION_X, (float[])new float[]{-f3});
            object = ObjectAnimator.ofFloat((Object)object, (Property)View.TRANSLATION_Y, (float[])new float[]{-f4});
        }
        i3.a((Animator)view);
        i4.a((Animator)object);
        list.add(view);
        list.add(object);
    }

    public final int h0(View view) {
        ColorStateList colorStateList = view.getBackgroundTintList();
        if (colorStateList != null) {
            return colorStateList.getColorForState(view.getDrawableState(), colorStateList.getDefaultColor());
        }
        return 0;
    }

    @Override
    public boolean i(CoordinatorLayout coordinatorLayout, View view, View view2) {
        if (view.getVisibility() != 8) {
            if (view2 instanceof FloatingActionButton) {
                int n3 = ((FloatingActionButton)view2).getExpandedComponentIdHint();
                return n3 == 0 || n3 == view.getId();
                {
                }
            }
            return false;
        }
        throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
    }

    public abstract e i0(Context var1, boolean var2);

    public final ViewGroup j0(View view) {
        if (view instanceof ViewGroup) {
            return (ViewGroup)view;
        }
        return null;
    }

    @Override
    public void k(CoordinatorLayout.e e3) {
        if (e3.h == 0) {
            e3.h = 80;
        }
    }

    public static class e {
        public h a;
        public j b;
    }
}

