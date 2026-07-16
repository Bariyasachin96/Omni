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
import androidx.constraintlayout.widget.VirtualLayout;
import u.f;
import u.i;
import u.l;
import u.m;

public class MotionPlaceholder
extends VirtualLayout {
    public l n;

    public MotionPlaceholder(Context context) {
        super(context);
    }

    public MotionPlaceholder(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MotionPlaceholder(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    @Override
    public void o(AttributeSet attributeSet) {
        super.o(attributeSet);
        this.f = new l();
        this.w();
    }

    @Override
    public void onMeasure(int n3, int n4) {
        this.x(this.n, n3, n4);
    }

    @Override
    public void v(f f3, i i3, SparseArray sparseArray) {
    }

    @Override
    public void x(m m3, int n3, int n4) {
        int n5 = View.MeasureSpec.getMode((int)n3);
        int n6 = View.MeasureSpec.getSize((int)n3);
        n3 = View.MeasureSpec.getMode((int)n4);
        n4 = View.MeasureSpec.getSize((int)n4);
        if (m3 != null) {
            m3.H1(n5, n6, n3, n4);
            this.setMeasuredDimension(m3.C1(), m3.B1());
            return;
        }
        this.setMeasuredDimension(0, 0);
    }
}

