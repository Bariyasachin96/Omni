/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.HashMap;
import y.d;

public class MotionHelper
extends ConstraintHelper
implements MotionLayout.i {
    public boolean l = false;
    public boolean m = false;
    public float n;
    public View[] o;

    public MotionHelper(Context context) {
        super(context);
    }

    public MotionHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.o(attributeSet);
    }

    public MotionHelper(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.o(attributeSet);
    }

    public void A(MotionLayout motionLayout) {
    }

    public void B(Canvas canvas) {
    }

    public void C(Canvas canvas) {
    }

    public void D(MotionLayout motionLayout, HashMap hashMap) {
    }

    @Override
    public void a(MotionLayout motionLayout, int n3, int n4, float f3) {
    }

    @Override
    public void b(MotionLayout motionLayout, int n3, int n4) {
    }

    @Override
    public void c(MotionLayout motionLayout, int n3, boolean bl, float f3) {
    }

    @Override
    public void d(MotionLayout motionLayout, int n3) {
    }

    public float getProgress() {
        return this.n;
    }

    @Override
    public void o(AttributeSet attributeSet) {
        super.o(attributeSet);
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.MotionHelper);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.MotionHelper_onShow) {
                    this.l = attributeSet.getBoolean(n4, this.l);
                    continue;
                }
                if (n4 != y.d.MotionHelper_onHide) continue;
                this.m = attributeSet.getBoolean(n4, this.m);
            }
            attributeSet.recycle();
        }
    }

    public void setProgress(float f3) {
        int n3;
        this.n = f3;
        int n4 = this.d;
        int n5 = 0;
        if (n4 > 0) {
            this.o = this.n((ConstraintLayout)this.getParent());
            for (n3 = n5; n3 < this.d; ++n3) {
                this.setProgress(this.o[n3], f3);
            }
        } else {
            ViewGroup viewGroup = (ViewGroup)this.getParent();
            n5 = viewGroup.getChildCount();
            for (n3 = 0; n3 < n5; ++n3) {
                View view = viewGroup.getChildAt(n3);
                if (view instanceof MotionHelper) continue;
                this.setProgress(view, f3);
            }
        }
    }

    public void setProgress(View view, float f3) {
    }

    public boolean x() {
        return false;
    }

    public boolean y() {
        return this.m;
    }

    public boolean z() {
        return this.l;
    }
}

