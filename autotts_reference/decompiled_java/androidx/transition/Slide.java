/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.transition.Visibility;
import androidx.transition.e;
import f0.k;
import m1.q;
import m1.r;
import m1.y;
import org.xmlpull.v1.XmlPullParser;

public class Slide
extends Visibility {
    public static final TimeInterpolator T = new DecelerateInterpolator();
    public static final TimeInterpolator U = new AccelerateInterpolator();
    public static final g V = new h(){

        @Override
        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationX() - (float)viewGroup.getWidth();
        }
    };
    public static final g W = new h(){

        @Override
        public float b(ViewGroup viewGroup, View view) {
            if (viewGroup.getLayoutDirection() == 1) {
                return view.getTranslationX() + (float)viewGroup.getWidth();
            }
            return view.getTranslationX() - (float)viewGroup.getWidth();
        }
    };
    public static final g X = new i(){

        @Override
        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationY() - (float)viewGroup.getHeight();
        }
    };
    public static final g Y = new h(){

        @Override
        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationX() + (float)viewGroup.getWidth();
        }
    };
    public static final g Z = new h(){

        @Override
        public float b(ViewGroup viewGroup, View view) {
            if (viewGroup.getLayoutDirection() == 1) {
                return view.getTranslationX() - (float)viewGroup.getWidth();
            }
            return view.getTranslationX() + (float)viewGroup.getWidth();
        }
    };
    public static final g a0 = new i(){

        @Override
        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationY() + (float)viewGroup.getHeight();
        }
    };
    public g R = a0;
    public int S = 80;

    public Slide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, m1.r.h);
        int n3 = f0.k.g((TypedArray)context, (XmlPullParser)attributeSet, "slideEdge", 0, 80);
        context.recycle();
        this.w0(n3);
    }

    private void o0(y y3) {
        View view = y3.b;
        int[] nArray = new int[2];
        view.getLocationOnScreen(nArray);
        y3.a.put("android:slide:screenPosition", nArray);
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
    public Animator r0(ViewGroup viewGroup, View view, y object, y y3) {
        if (y3 == null) {
            return null;
        }
        object = (int[])y3.a.get("android:slide:screenPosition");
        float f3 = view.getTranslationX();
        float f4 = view.getTranslationY();
        float f5 = this.R.b(viewGroup, view);
        float f6 = this.R.a(viewGroup, view);
        return androidx.transition.e.a(view, y3, (int)object[0], (int)object[1], f5, f6, f3, f4, T, this);
    }

    @Override
    public Animator t0(ViewGroup viewGroup, View view, y y3, y object) {
        if (y3 == null) {
            return null;
        }
        object = (int[])y3.a.get("android:slide:screenPosition");
        float f3 = view.getTranslationX();
        float f4 = view.getTranslationY();
        float f5 = this.R.b(viewGroup, view);
        float f6 = this.R.a(viewGroup, view);
        return androidx.transition.e.a(view, y3, (int)object[0], (int)object[1], f3, f4, f5, f6, U, this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void w0(int n3) {
        if (n3 != 3) {
            if (n3 != 5) {
                if (n3 != 48) {
                    if (n3 != 80) {
                        if (n3 != 0x800003) {
                            if (n3 != 0x800005) throw new IllegalArgumentException("Invalid slide direction");
                            this.R = Z;
                        } else {
                            this.R = W;
                        }
                    } else {
                        this.R = a0;
                    }
                } else {
                    this.R = X;
                }
            } else {
                this.R = Y;
            }
        } else {
            this.R = V;
        }
        this.S = n3;
        q q3 = new q();
        q3.j(n3);
        this.k0(q3);
    }

    public static interface g {
        public float a(ViewGroup var1, View var2);

        public float b(ViewGroup var1, View var2);
    }

    public static abstract class h
    implements g {
        public h() {
        }

        public /* synthetic */ h(a a4) {
            this();
        }

        @Override
        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationY();
        }
    }

    public static abstract class i
    implements g {
        public i() {
        }

        public /* synthetic */ i(a a4) {
            this();
        }

        @Override
        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationX();
        }
    }
}

