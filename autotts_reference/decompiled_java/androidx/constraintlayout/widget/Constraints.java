/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.b;
import y.d;

public class Constraints
extends ViewGroup {
    public b c;

    public Constraints(Context context) {
        super(context);
        super.setVisibility(8);
    }

    public Constraints(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c();
        super.setVisibility(8);
    }

    public Constraints(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.c();
        super.setVisibility(8);
    }

    public LayoutParams a() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams b(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public final void c() {
    }

    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new ConstraintLayout.LayoutParams(layoutParams);
    }

    public b getConstraintSet() {
        if (this.c == null) {
            this.c = new b();
        }
        this.c.q(this);
        return this.c;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
    }

    public static class LayoutParams
    extends ConstraintLayout.LayoutParams {
        public float A0 = 0.0f;
        public float B0 = 0.0f;
        public float C0 = 0.0f;
        public float D0 = 1.0f;
        public float E0 = 1.0f;
        public float F0 = 0.0f;
        public float G0 = 0.0f;
        public float H0 = 0.0f;
        public float I0 = 0.0f;
        public float J0 = 0.0f;
        public float x0 = 1.0f;
        public boolean y0 = false;
        public float z0 = 0.0f;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, d.ConstraintSet);
            int n3 = context.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = context.getIndex(i3);
                if (n4 == d.ConstraintSet_android_alpha) {
                    this.x0 = context.getFloat(n4, this.x0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_elevation) {
                    this.z0 = context.getFloat(n4, this.z0);
                    this.y0 = true;
                    continue;
                }
                if (n4 == d.ConstraintSet_android_rotationX) {
                    this.B0 = context.getFloat(n4, this.B0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_rotationY) {
                    this.C0 = context.getFloat(n4, this.C0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_rotation) {
                    this.A0 = context.getFloat(n4, this.A0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_scaleX) {
                    this.D0 = context.getFloat(n4, this.D0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_scaleY) {
                    this.E0 = context.getFloat(n4, this.E0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_transformPivotX) {
                    this.F0 = context.getFloat(n4, this.F0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_transformPivotY) {
                    this.G0 = context.getFloat(n4, this.G0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_translationX) {
                    this.H0 = context.getFloat(n4, this.H0);
                    continue;
                }
                if (n4 == d.ConstraintSet_android_translationY) {
                    this.I0 = context.getFloat(n4, this.I0);
                    continue;
                }
                if (n4 != d.ConstraintSet_android_translationZ) continue;
                this.J0 = context.getFloat(n4, this.J0);
            }
            context.recycle();
        }
    }
}

