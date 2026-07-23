/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.DecelerateInterpolator
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.transition.Visibility;
import androidx.transition.e;
import m1.b;
import m1.n;
import m1.y;

public class Explode
extends Visibility {
    public static final TimeInterpolator S = new DecelerateInterpolator();
    public static final TimeInterpolator T = new AccelerateInterpolator();
    public int[] R = new int[2];

    public Explode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.k0(new b());
    }

    private void o0(y y3) {
        View view = y3.b;
        view.getLocationOnScreen(this.R);
        int[] nArray = this.R;
        int n3 = nArray[0];
        int n4 = nArray[1];
        int n5 = view.getWidth();
        int n6 = view.getHeight();
        y3.a.put("android:explode:screenBounds", new Rect(n3, n4, n5 + n3, n6 + n4));
    }

    public static float w0(float f3, float f4) {
        return (float)Math.sqrt(f3 * f3 + f4 * f4);
    }

    public static float x0(View view, int n3, int n4) {
        n3 = Math.max(n3, view.getWidth() - n3);
        n4 = Math.max(n4, view.getHeight() - n4);
        return Explode.w0(n3, n4);
    }

    @Override
    public void h(y y3) {
        super.h(y3);
        this.o0(y3);
    }

    @Override
    public void k(y y3) {
        super.k(y3);
        this.o0(y3);
    }

    @Override
    public Animator r0(ViewGroup object, View view, y y3, y y4) {
        if (y4 == null) {
            return null;
        }
        y3 = (Rect)y4.a.get("android:explode:screenBounds");
        float f3 = view.getTranslationX();
        float f4 = view.getTranslationY();
        this.y0((View)object, (Rect)y3, this.R);
        object = this.R;
        float f5 = (float)object[0];
        float f6 = (float)object[1];
        return androidx.transition.e.a(view, y4, ((Rect)y3).left, ((Rect)y3).top, f3 + f5, f4 + f6, f3, f4, S, this);
    }

    @Override
    public Animator t0(ViewGroup object, View view, y y3, y y4) {
        float f3;
        float f4;
        if (y3 == null) {
            return null;
        }
        y4 = (Rect)y3.a.get("android:explode:screenBounds");
        int n3 = ((Rect)y4).left;
        int n4 = ((Rect)y4).top;
        float f5 = view.getTranslationX();
        float f6 = view.getTranslationY();
        int[] nArray = (int[])y3.b.getTag(m1.n.transition_position);
        if (nArray != null) {
            int n5 = nArray[0];
            f4 = (float)(n5 - ((Rect)y4).left) + f5;
            int n6 = nArray[1];
            f3 = (float)(n6 - ((Rect)y4).top) + f6;
            y4.offsetTo(n5, n6);
        } else {
            f4 = f5;
            f3 = f6;
        }
        this.y0((View)object, (Rect)y4, this.R);
        object = this.R;
        return androidx.transition.e.a(view, y3, n3, n4, f5, f6, f4 + (float)object[0], f3 + (float)object[1], T, this);
    }

    public final void y0(View view, Rect rect, int[] nArray) {
        int n3;
        int n4;
        view.getLocationOnScreen(this.R);
        Object object = this.R;
        int n5 = object[0];
        int n6 = object[1];
        object = this.w();
        if (object == null) {
            n4 = view.getWidth() / 2 + n5 + Math.round(view.getTranslationX());
            n3 = view.getHeight() / 2 + n6 + Math.round(view.getTranslationY());
        } else {
            n4 = object.centerX();
            n3 = object.centerY();
        }
        int n7 = rect.centerX();
        int n8 = rect.centerY();
        float f3 = n7 - n4;
        float f4 = n8 - n3;
        float f5 = f3;
        float f6 = f4;
        if (f3 == 0.0f) {
            f5 = f3;
            f6 = f4;
            if (f4 == 0.0f) {
                f5 = (float)(Math.random() * 2.0) - 1.0f;
                f6 = (float)(Math.random() * 2.0) - 1.0f;
            }
        }
        f3 = Explode.w0(f5, f6);
        f5 /= f3;
        f6 /= f3;
        f3 = Explode.x0(view, n4 - n5, n3 - n6);
        nArray[0] = Math.round(f5 * f3);
        nArray[1] = Math.round(f3 * f6);
    }
}

