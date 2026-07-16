/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Color
 *  android.util.Property
 *  android.view.View
 */
package m2;

import a2.h;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Property;
import android.view.View;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import m2.a;

public abstract class b
implements com.google.android.material.floatingactionbutton.b {
    public final Context a;
    public final ExtendedFloatingActionButton b;
    public final ArrayList c = new ArrayList();
    public final a d;
    public h e;
    public h f;

    public b(ExtendedFloatingActionButton extendedFloatingActionButton, a a4) {
        this.b = extendedFloatingActionButton;
        this.a = extendedFloatingActionButton.getContext();
        this.d = a4;
    }

    @Override
    public void a() {
        this.d.b();
    }

    @Override
    public void b() {
        this.d.b();
    }

    @Override
    public h e() {
        return this.f;
    }

    @Override
    public AnimatorSet f() {
        return this.l(this.m());
    }

    @Override
    public final List g() {
        return this.c;
    }

    @Override
    public final void h(h h3) {
        this.f = h3;
    }

    public AnimatorSet l(h h3) {
        ArrayList<ObjectAnimator> arrayList = new ArrayList<ObjectAnimator>();
        if (h3.j("opacity")) {
            arrayList.add(h3.f("opacity", this.b, View.ALPHA));
        }
        if (h3.j("scale")) {
            arrayList.add(h3.f("scale", this.b, View.SCALE_Y));
            arrayList.add(h3.f("scale", this.b, View.SCALE_X));
        }
        if (h3.j("width")) {
            arrayList.add(h3.f("width", this.b, ExtendedFloatingActionButton.i0));
        }
        if (h3.j("height")) {
            arrayList.add(h3.f("height", this.b, ExtendedFloatingActionButton.j0));
        }
        if (h3.j("paddingStart")) {
            arrayList.add(h3.f("paddingStart", this.b, ExtendedFloatingActionButton.k0));
        }
        if (h3.j("paddingEnd")) {
            arrayList.add(h3.f("paddingEnd", this.b, ExtendedFloatingActionButton.l0));
        }
        if (h3.j("labelOpacity")) {
            arrayList.add(h3.f("labelOpacity", this.b, new Property(this, Float.class, "LABEL_OPACITY_PROPERTY"){
                public final b a;
                {
                    this.a = b3;
                    super(clazz, string);
                }

                public Float a(ExtendedFloatingActionButton extendedFloatingActionButton) {
                    int n3 = Color.alpha((int)extendedFloatingActionButton.d0.getColorForState(extendedFloatingActionButton.getDrawableState(), ((b)this.a).b.d0.getDefaultColor()));
                    return Float.valueOf(a2.a.a(0.0f, 1.0f, (float)Color.alpha((int)extendedFloatingActionButton.getCurrentTextColor()) / 255.0f / (float)n3));
                }

                public void b(ExtendedFloatingActionButton extendedFloatingActionButton, Float f3) {
                    int n3 = extendedFloatingActionButton.d0.getColorForState(extendedFloatingActionButton.getDrawableState(), ((b)this.a).b.d0.getDefaultColor());
                    ColorStateList colorStateList = ColorStateList.valueOf((int)Color.argb((int)((int)(a2.a.a(0.0f, (float)Color.alpha((int)n3) / 255.0f, f3.floatValue()) * 255.0f)), (int)Color.red((int)n3), (int)Color.green((int)n3), (int)Color.blue((int)n3)));
                    if (f3.floatValue() == 1.0f) {
                        extendedFloatingActionButton.N(extendedFloatingActionButton.d0);
                        return;
                    }
                    extendedFloatingActionButton.N(colorStateList);
                }
            }));
        }
        h3 = new AnimatorSet();
        a2.b.a((AnimatorSet)h3, arrayList);
        return h3;
    }

    public final h m() {
        h h3 = this.f;
        if (h3 != null) {
            return h3;
        }
        if (this.e == null) {
            this.e = h.d(this.a, this.c());
        }
        return (h)n0.h.g(this.e);
    }

    @Override
    public void onAnimationStart(Animator animator) {
        this.d.c(animator);
    }
}

