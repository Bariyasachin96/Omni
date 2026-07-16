/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.transition.Transition;
import m1.y;

public class x
extends Transition {
    private void o0(y y3) {
        View view = y3.b;
        if (view instanceof TextView) {
            view = (TextView)view;
            y3.a.put("android:textscale:scale", Float.valueOf(view.getScaleX()));
        }
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
    public Animator o(ViewGroup object, y object2, y y3) {
        TextView textView = null;
        object = textView;
        if (object2 != null) {
            object = textView;
            if (y3 != null) {
                object = textView;
                if (((y)object2).b instanceof TextView) {
                    object = y3.b;
                    if (!(object instanceof TextView)) {
                        object = textView;
                    } else {
                        textView = (TextView)object;
                        object = ((y)object2).a;
                        object2 = y3.a;
                        y3 = object.get("android:textscale:scale");
                        float f3 = 1.0f;
                        float f4 = y3 != null ? ((Float)object.get("android:textscale:scale")).floatValue() : 1.0f;
                        if (object2.get("android:textscale:scale") != null) {
                            f3 = ((Float)object2.get("android:textscale:scale")).floatValue();
                        }
                        if (f4 == f3) {
                            return null;
                        }
                        object = ValueAnimator.ofFloat((float[])new float[]{f4, f3});
                        object.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this, textView){
                            public final TextView a;
                            public final x b;
                            {
                                this.b = x3;
                                this.a = textView;
                            }

                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float f3 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                                this.a.setScaleX(f3);
                                this.a.setScaleY(f3);
                            }
                        });
                    }
                }
            }
        }
        return object;
    }
}

