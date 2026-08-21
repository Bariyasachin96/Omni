/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import u.m;
import y.d;

public abstract class VirtualLayout
extends ConstraintHelper {
    public boolean l;
    public boolean m;

    public VirtualLayout(Context context) {
        super(context);
    }

    public VirtualLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VirtualLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    @Override
    public void j(ConstraintLayout constraintLayout) {
        this.i(constraintLayout);
    }

    @Override
    public void o(AttributeSet attributeSet) {
        super.o(attributeSet);
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_Layout);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.ConstraintLayout_Layout_android_visibility) {
                    this.l = true;
                    continue;
                }
                if (n4 != y.d.ConstraintLayout_Layout_android_elevation) continue;
                this.m = true;
            }
            attributeSet.recycle();
        }
    }

    @Override
    public void onAttachedToWindow() {
        Object object;
        super.onAttachedToWindow();
        if ((this.l || this.m) && (object = this.getParent()) instanceof ConstraintLayout) {
            object = (ConstraintLayout)((Object)object);
            int n3 = this.getVisibility();
            float f3 = this.getElevation();
            for (int i3 = 0; i3 < this.d; ++i3) {
                View view = ((ConstraintLayout)((Object)object)).q(this.c[i3]);
                if (view == null) continue;
                if (this.l) {
                    view.setVisibility(n3);
                }
                if (!this.m || !(f3 > 0.0f)) continue;
                view.setTranslationZ(view.getTranslationZ() + f3);
            }
        }
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        this.h();
    }

    public void setVisibility(int n3) {
        super.setVisibility(n3);
        this.h();
    }

    public void x(m m3, int n3, int n4) {
    }
}

