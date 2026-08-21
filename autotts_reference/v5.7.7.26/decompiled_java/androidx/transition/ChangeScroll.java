/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.d;
import m1.y;

public class ChangeScroll
extends Transition {
    public static final String[] P = new String[]{"android:changeScroll:x", "android:changeScroll:y"};

    public ChangeScroll(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void o0(y y3) {
        y3.a.put("android:changeScroll:x", y3.b.getScrollX());
        y3.a.put("android:changeScroll:y", y3.b.getScrollY());
    }

    @Override
    public String[] K() {
        return P;
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
        View view = null;
        Object var8_5 = null;
        viewGroup = view;
        if (y3 != null) {
            if (y4 == null) {
                viewGroup = view;
            } else {
                view = y4.b;
                int n3 = (Integer)y3.a.get("android:changeScroll:x");
                int n4 = (Integer)y4.a.get("android:changeScroll:x");
                int n5 = (Integer)y3.a.get("android:changeScroll:y");
                int n6 = (Integer)y4.a.get("android:changeScroll:y");
                if (n3 != n4) {
                    view.setScrollX(n3);
                    viewGroup = ObjectAnimator.ofInt((Object)view, (String)"scrollX", (int[])new int[]{n3, n4});
                } else {
                    viewGroup = null;
                }
                y3 = var8_5;
                if (n5 != n6) {
                    view.setScrollY(n5);
                    y3 = ObjectAnimator.ofInt((Object)view, (String)"scrollY", (int[])new int[]{n5, n6});
                }
                viewGroup = androidx.transition.d.c((Animator)viewGroup, (Animator)y3);
            }
        }
        return viewGroup;
    }
}

