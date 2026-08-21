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

public class Guideline
extends View {
    public boolean c = true;

    public Guideline(Context context) {
        super(context);
        super.setVisibility(8);
    }

    public Guideline(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        super.setVisibility(8);
    }

    public Guideline(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        super.setVisibility(8);
    }

    public void draw(Canvas canvas) {
    }

    public void onMeasure(int n3, int n4) {
        this.setMeasuredDimension(0, 0);
    }

    public void setFilterRedundantCalls(boolean bl) {
        this.c = bl;
    }

    public void setGuidelineBegin(int n3) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)this.getLayoutParams();
        if (this.c && layoutParams.a == n3) {
            return;
        }
        layoutParams.a = n3;
        this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void setGuidelineEnd(int n3) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)this.getLayoutParams();
        if (this.c && layoutParams.b == n3) {
            return;
        }
        layoutParams.b = n3;
        this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void setGuidelinePercent(float f3) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)this.getLayoutParams();
        if (this.c && layoutParams.c == f3) {
            return;
        }
        layoutParams.c = f3;
        this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void setVisibility(int n3) {
    }
}

