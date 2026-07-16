/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.View$MeasureSpec
 */
package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.VirtualLayout;
import androidx.constraintlayout.widget.b;
import u.e;
import u.g;
import u.j;
import u.m;
import y.d;

public class Flow
extends VirtualLayout {
    public g n;

    public Flow(Context context) {
        super(context);
    }

    public Flow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Flow(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    @Override
    public void o(AttributeSet attributeSet) {
        super.o(attributeSet);
        this.n = new g();
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_Layout);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.ConstraintLayout_Layout_android_orientation) {
                    this.n.I2(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_android_padding) {
                    this.n.N1(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_android_paddingStart) {
                    this.n.S1(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_android_paddingEnd) {
                    this.n.P1(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_android_paddingLeft) {
                    this.n.Q1(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_android_paddingTop) {
                    this.n.T1(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_android_paddingRight) {
                    this.n.R1(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_android_paddingBottom) {
                    this.n.O1(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_wrapMode) {
                    this.n.N2(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_horizontalStyle) {
                    this.n.C2(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_verticalStyle) {
                    this.n.M2(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_firstHorizontalStyle) {
                    this.n.w2(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_lastHorizontalStyle) {
                    this.n.E2(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_firstVerticalStyle) {
                    this.n.y2(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_lastVerticalStyle) {
                    this.n.G2(attributeSet.getInt(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_horizontalBias) {
                    this.n.A2(attributeSet.getFloat(n4, 0.5f));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_firstHorizontalBias) {
                    this.n.v2(attributeSet.getFloat(n4, 0.5f));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_lastHorizontalBias) {
                    this.n.D2(attributeSet.getFloat(n4, 0.5f));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_firstVerticalBias) {
                    this.n.x2(attributeSet.getFloat(n4, 0.5f));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_lastVerticalBias) {
                    this.n.F2(attributeSet.getFloat(n4, 0.5f));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_verticalBias) {
                    this.n.K2(attributeSet.getFloat(n4, 0.5f));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_horizontalAlign) {
                    this.n.z2(attributeSet.getInt(n4, 2));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_verticalAlign) {
                    this.n.J2(attributeSet.getInt(n4, 2));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_horizontalGap) {
                    this.n.B2(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_flow_verticalGap) {
                    this.n.L2(attributeSet.getDimensionPixelSize(n4, 0));
                    continue;
                }
                if (n4 != y.d.ConstraintLayout_Layout_flow_maxElementsWrap) continue;
                this.n.H2(attributeSet.getInt(n4, -1));
            }
            attributeSet.recycle();
        }
        this.f = this.n;
        this.w();
    }

    @Override
    public void onMeasure(int n3, int n4) {
        this.x(this.n, n3, n4);
    }

    @Override
    public void p(b.a object, j j3, ConstraintLayout.LayoutParams layoutParams, SparseArray sparseArray) {
        super.p((b.a)object, j3, layoutParams, sparseArray);
        if (j3 instanceof g) {
            object = (g)j3;
            int n3 = layoutParams.Z;
            if (n3 != -1) {
                ((g)object).I2(n3);
            }
        }
    }

    @Override
    public void q(e e3, boolean bl) {
        this.n.y1(bl);
    }

    public void setFirstHorizontalBias(float f3) {
        this.n.v2(f3);
        this.requestLayout();
    }

    public void setFirstHorizontalStyle(int n3) {
        this.n.w2(n3);
        this.requestLayout();
    }

    public void setFirstVerticalBias(float f3) {
        this.n.x2(f3);
        this.requestLayout();
    }

    public void setFirstVerticalStyle(int n3) {
        this.n.y2(n3);
        this.requestLayout();
    }

    public void setHorizontalAlign(int n3) {
        this.n.z2(n3);
        this.requestLayout();
    }

    public void setHorizontalBias(float f3) {
        this.n.A2(f3);
        this.requestLayout();
    }

    public void setHorizontalGap(int n3) {
        this.n.B2(n3);
        this.requestLayout();
    }

    public void setHorizontalStyle(int n3) {
        this.n.C2(n3);
        this.requestLayout();
    }

    public void setLastHorizontalBias(float f3) {
        this.n.D2(f3);
        this.requestLayout();
    }

    public void setLastHorizontalStyle(int n3) {
        this.n.E2(n3);
        this.requestLayout();
    }

    public void setLastVerticalBias(float f3) {
        this.n.F2(f3);
        this.requestLayout();
    }

    public void setLastVerticalStyle(int n3) {
        this.n.G2(n3);
        this.requestLayout();
    }

    public void setMaxElementsWrap(int n3) {
        this.n.H2(n3);
        this.requestLayout();
    }

    public void setOrientation(int n3) {
        this.n.I2(n3);
        this.requestLayout();
    }

    public void setPadding(int n3) {
        this.n.N1(n3);
        this.requestLayout();
    }

    public void setPaddingBottom(int n3) {
        this.n.O1(n3);
        this.requestLayout();
    }

    public void setPaddingLeft(int n3) {
        this.n.Q1(n3);
        this.requestLayout();
    }

    public void setPaddingRight(int n3) {
        this.n.R1(n3);
        this.requestLayout();
    }

    public void setPaddingTop(int n3) {
        this.n.T1(n3);
        this.requestLayout();
    }

    public void setVerticalAlign(int n3) {
        this.n.J2(n3);
        this.requestLayout();
    }

    public void setVerticalBias(float f3) {
        this.n.K2(f3);
        this.requestLayout();
    }

    public void setVerticalGap(int n3) {
        this.n.L2(n3);
        this.requestLayout();
    }

    public void setVerticalStyle(int n3) {
        this.n.M2(n3);
        this.requestLayout();
    }

    public void setWrapMode(int n3) {
        this.n.N2(n3);
        this.requestLayout();
    }

    @Override
    public void x(m m3, int n3, int n4) {
        int n5 = View.MeasureSpec.getMode((int)n3);
        n3 = View.MeasureSpec.getSize((int)n3);
        int n6 = View.MeasureSpec.getMode((int)n4);
        n4 = View.MeasureSpec.getSize((int)n4);
        if (m3 != null) {
            m3.H1(n5, n3, n6, n4);
            this.setMeasuredDimension(m3.C1(), m3.B1());
            return;
        }
        this.setMeasuredDimension(0, 0);
    }
}

