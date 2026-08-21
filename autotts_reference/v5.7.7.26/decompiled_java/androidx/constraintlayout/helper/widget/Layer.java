/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import u.e;
import y.d;

public class Layer
extends ConstraintHelper {
    public float A = 0.0f;
    public boolean B;
    public boolean C;
    public float l = Float.NaN;
    public float m = Float.NaN;
    public float n = Float.NaN;
    public ConstraintLayout o;
    public float p = 1.0f;
    public float q = 1.0f;
    public float r = Float.NaN;
    public float s = Float.NaN;
    public float t = Float.NaN;
    public float u = Float.NaN;
    public float v = Float.NaN;
    public float w = Float.NaN;
    public boolean x = true;
    public View[] y = null;
    public float z = 0.0f;

    public Layer(Context context) {
        super(context);
    }

    public Layer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Layer(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    @Override
    public void j(ConstraintLayout constraintLayout) {
        this.i(constraintLayout);
    }

    @Override
    public void o(AttributeSet attributeSet) {
        super.o(attributeSet);
        this.g = false;
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_Layout);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.ConstraintLayout_Layout_android_visibility) {
                    this.B = true;
                    continue;
                }
                if (n4 != y.d.ConstraintLayout_Layout_android_elevation) continue;
                this.C = true;
            }
            attributeSet.recycle();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.o = (ConstraintLayout)this.getParent();
        if (this.B || this.C) {
            int n3 = this.getVisibility();
            float f3 = this.getElevation();
            for (int i3 = 0; i3 < this.d; ++i3) {
                int n4 = this.c[i3];
                View view = this.o.q(n4);
                if (view == null) continue;
                if (this.B) {
                    view.setVisibility(n3);
                }
                if (!this.C || !(f3 > 0.0f)) continue;
                view.setTranslationZ(view.getTranslationZ() + f3);
            }
        }
    }

    @Override
    public void r(ConstraintLayout object) {
        this.y();
        this.r = Float.NaN;
        this.s = Float.NaN;
        object = ((ConstraintLayout.LayoutParams)this.getLayoutParams()).b();
        ((e)object).p1(0);
        ((e)object).Q0(0);
        this.x();
        this.layout((int)this.v - this.getPaddingLeft(), (int)this.w - this.getPaddingTop(), (int)this.t + this.getPaddingRight(), (int)this.u + this.getPaddingBottom());
        this.z();
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        this.h();
    }

    public void setPivotX(float f3) {
        this.l = f3;
        this.z();
    }

    public void setPivotY(float f3) {
        this.m = f3;
        this.z();
    }

    public void setRotation(float f3) {
        this.n = f3;
        this.z();
    }

    public void setScaleX(float f3) {
        this.p = f3;
        this.z();
    }

    public void setScaleY(float f3) {
        this.q = f3;
        this.z();
    }

    public void setTranslationX(float f3) {
        this.z = f3;
        this.z();
    }

    public void setTranslationY(float f3) {
        this.A = f3;
        this.z();
    }

    public void setVisibility(int n3) {
        super.setVisibility(n3);
        this.h();
    }

    @Override
    public void t(ConstraintLayout constraintLayout) {
        this.o = constraintLayout;
        float f3 = this.getRotation();
        if (f3 == 0.0f) {
            if (!Float.isNaN(this.n)) {
                this.n = f3;
            }
            return;
        }
        this.n = f3;
    }

    public void x() {
        if (this.o == null || !this.x && !Float.isNaN(this.r) && !Float.isNaN(this.s)) {
            return;
        }
        if (!Float.isNaN(this.l) && !Float.isNaN(this.m)) {
            this.s = this.m;
            this.r = this.l;
            return;
        }
        View[] viewArray = this.n(this.o);
        int n3 = viewArray[0].getLeft();
        int n4 = viewArray[0].getTop();
        int n5 = viewArray[0].getRight();
        int n6 = viewArray[0].getBottom();
        for (int i3 = 0; i3 < this.d; ++i3) {
            View view = viewArray[i3];
            n3 = Math.min(n3, view.getLeft());
            n4 = Math.min(n4, view.getTop());
            n5 = Math.max(n5, view.getRight());
            n6 = Math.max(n6, view.getBottom());
        }
        this.t = n5;
        this.u = n6;
        this.v = n3;
        this.w = n4;
        this.r = Float.isNaN(this.l) ? (float)((n3 + n5) / 2) : this.l;
        if (Float.isNaN(this.m)) {
            this.s = (n4 + n6) / 2;
            return;
        }
        this.s = this.m;
    }

    public final void y() {
        int n3;
        if (this.o != null && (n3 = this.d) != 0) {
            View[] viewArray = this.y;
            if (viewArray == null || viewArray.length != n3) {
                this.y = new View[n3];
            }
            for (n3 = 0; n3 < this.d; ++n3) {
                int n4 = this.c[n3];
                this.y[n3] = this.o.q(n4);
            }
        }
    }

    public final void z() {
        if (this.o != null) {
            if (this.y == null) {
                this.y();
            }
            this.x();
            double d3 = Float.isNaN(this.n) ? 0.0 : Math.toRadians(this.n);
            float f3 = (float)Math.sin(d3);
            float f4 = (float)Math.cos(d3);
            float f5 = this.p;
            float f6 = this.q;
            float f7 = -f6;
            for (int i3 = 0; i3 < this.d; ++i3) {
                View view = this.y[i3];
                int n3 = (view.getLeft() + view.getRight()) / 2;
                int n4 = (view.getTop() + view.getBottom()) / 2;
                float f8 = (float)n3 - this.r;
                float f9 = (float)n4 - this.s;
                float f10 = this.z;
                float f11 = this.A;
                view.setTranslationX(f5 * f4 * f8 + f7 * f3 * f9 - f8 + f10);
                view.setTranslationY(f8 * (f5 * f3) + f6 * f4 * f9 - f9 + f11);
                view.setScaleY(this.q);
                view.setScaleX(this.p);
                if (Float.isNaN(this.n)) continue;
                view.setRotation(this.n);
            }
        }
    }
}

