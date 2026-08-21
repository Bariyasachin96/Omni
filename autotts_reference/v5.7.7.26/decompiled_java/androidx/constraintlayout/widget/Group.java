/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Group
extends ConstraintHelper {
    public Group(Context context) {
        super(context);
    }

    public Group(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Group(Context context, AttributeSet attributeSet, int n3) {
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
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.h();
    }

    @Override
    public void r(ConstraintLayout object) {
        object = (ConstraintLayout.LayoutParams)this.getLayoutParams();
        ((ConstraintLayout.LayoutParams)((Object)object)).v0.p1(0);
        ((ConstraintLayout.LayoutParams)((Object)object)).v0.Q0(0);
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        this.h();
    }

    public void setVisibility(int n3) {
        super.setVisibility(n3);
        this.h();
    }
}

