/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.c;
import y.d;

public class ReactiveGuide
extends View
implements c.a {
    public int c = -1;
    public boolean d = false;
    public int e = 0;
    public boolean f = true;

    public ReactiveGuide(Context context) {
        super(context);
        super.setVisibility(8);
        this.a(null);
    }

    public ReactiveGuide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        super.setVisibility(8);
        this.a(attributeSet);
    }

    public ReactiveGuide(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        super.setVisibility(8);
        this.a(attributeSet);
    }

    public final void a(AttributeSet attributeSet) {
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_ReactiveGuide);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.ConstraintLayout_ReactiveGuide_reactiveGuide_valueId) {
                    this.c = attributeSet.getResourceId(n4, this.c);
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_ReactiveGuide_reactiveGuide_animateChange) {
                    this.d = attributeSet.getBoolean(n4, this.d);
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_ReactiveGuide_reactiveGuide_applyToConstraintSet) {
                    this.e = attributeSet.getResourceId(n4, this.e);
                    continue;
                }
                if (n4 != y.d.ConstraintLayout_ReactiveGuide_reactiveGuide_applyToAllConstraintSets) continue;
                this.f = attributeSet.getBoolean(n4, this.f);
            }
            attributeSet.recycle();
        }
        if (this.c != -1) {
            ConstraintLayout.getSharedValues().a(this.c, this);
        }
    }

    public void draw(Canvas canvas) {
    }

    public int getApplyToConstraintSetId() {
        return this.e;
    }

    public int getAttributeId() {
        return this.c;
    }

    public void onMeasure(int n3, int n4) {
        this.setMeasuredDimension(0, 0);
    }

    public void setAnimateChange(boolean bl) {
        this.d = bl;
    }

    public void setApplyToConstraintSetId(int n3) {
        this.e = n3;
    }

    public void setAttributeId(int n3) {
        c c3 = ConstraintLayout.getSharedValues();
        int n4 = this.c;
        if (n4 != -1) {
            c3.b(n4, this);
        }
        this.c = n3;
        if (n3 != -1) {
            c3.a(n3, this);
        }
    }

    public void setGuidelineBegin(int n3) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)this.getLayoutParams();
        layoutParams.a = n3;
        this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void setGuidelineEnd(int n3) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)this.getLayoutParams();
        layoutParams.b = n3;
        this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void setGuidelinePercent(float f3) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)this.getLayoutParams();
        layoutParams.c = f3;
        this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void setVisibility(int n3) {
    }
}

