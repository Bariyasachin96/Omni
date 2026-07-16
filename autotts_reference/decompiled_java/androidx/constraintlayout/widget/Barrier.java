/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.SparseArray
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.b;
import u.a;
import u.e;
import u.f;
import u.j;
import y.d;

public class Barrier
extends ConstraintHelper {
    public int l;
    public int m;
    public a n;

    public Barrier(Context context) {
        super(context);
        super.setVisibility(8);
    }

    public Barrier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        super.setVisibility(8);
    }

    public Barrier(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        super.setVisibility(8);
    }

    public boolean getAllowsGoneWidget() {
        return this.n.z1();
    }

    public int getMargin() {
        return this.n.B1();
    }

    public int getType() {
        return this.l;
    }

    @Override
    public void o(AttributeSet attributeSet) {
        super.o(attributeSet);
        this.n = new a();
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_Layout);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.ConstraintLayout_Layout_barrierDirection) {
                    this.setType(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_barrierAllowsGoneWidgets) {
                    this.n.E1(attributeSet.getBoolean(n4, true));
                    continue;
                }
                if (n4 != y.d.ConstraintLayout_Layout_barrierMargin) continue;
                n4 = attributeSet.getDimensionPixelSize(n4, 0);
                this.n.G1(n4);
            }
            attributeSet.recycle();
        }
        this.f = this.n;
        this.w();
    }

    @Override
    public void p(b.a a4, j j3, ConstraintLayout.LayoutParams object, SparseArray sparseArray) {
        super.p(a4, j3, (ConstraintLayout.LayoutParams)((Object)object), sparseArray);
        if (j3 instanceof a) {
            object = (a)j3;
            boolean bl = ((f)j3.M()).V1();
            this.x((e)object, a4.e.h0, bl);
            ((a)object).E1(a4.e.p0);
            ((a)object).G1(a4.e.i0);
        }
    }

    @Override
    public void q(e e3, boolean bl) {
        this.x(e3, this.l, bl);
    }

    public void setAllowsGoneWidget(boolean bl) {
        this.n.E1(bl);
    }

    public void setDpMargin(int n3) {
        float f3 = this.getResources().getDisplayMetrics().density;
        n3 = (int)((float)n3 * f3 + 0.5f);
        this.n.G1(n3);
    }

    public void setMargin(int n3) {
        this.n.G1(n3);
    }

    public void setType(int n3) {
        this.l = n3;
    }

    public final void x(e e3, int n3, boolean bl) {
        this.m = n3;
        if (bl) {
            n3 = this.l;
            if (n3 == 5) {
                this.m = 1;
            } else if (n3 == 6) {
                this.m = 0;
            }
        } else {
            n3 = this.l;
            if (n3 == 5) {
                this.m = 0;
            } else if (n3 == 6) {
                this.m = 1;
            }
        }
        if (e3 instanceof a) {
            ((a)e3).F1(this.m);
        }
    }
}

