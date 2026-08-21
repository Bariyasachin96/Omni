/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.text.Layout
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.material.snackbar;

import a2.a;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import p2.k;
import z1.c;
import z1.e;
import z1.g;

public class SnackbarContentLayout
extends LinearLayout {
    public TextView c;
    public Button d;
    public final TimeInterpolator e;
    public int f;

    public SnackbarContentLayout(Context context) {
        this(context, null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = k.g(context, z1.c.motionEasingEmphasizedInterpolator, a.b);
    }

    public static void a(View view, int n3, int n4) {
        if (view.isPaddingRelative()) {
            view.setPaddingRelative(view.getPaddingStart(), n3, view.getPaddingEnd(), n4);
            return;
        }
        view.setPadding(view.getPaddingLeft(), n3, view.getPaddingRight(), n4);
    }

    public final boolean b(int n3, int n4, int n5) {
        boolean bl;
        if (n3 != this.getOrientation()) {
            this.setOrientation(n3);
            bl = true;
        } else {
            bl = false;
        }
        if (this.c.getPaddingTop() == n4 && this.c.getPaddingBottom() == n5) {
            return bl;
        }
        SnackbarContentLayout.a((View)this.c, n4, n5);
        return true;
    }

    public Button getActionView() {
        return this.d;
    }

    public TextView getMessageView() {
        return this.c;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.c = (TextView)this.findViewById(g.snackbar_text);
        this.d = (Button)this.findViewById(g.snackbar_action);
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        if (this.getOrientation() != 1) {
            int n5 = this.getResources().getDimensionPixelSize(z1.e.design_snackbar_padding_vertical_2lines);
            int n6 = this.getResources().getDimensionPixelSize(z1.e.design_snackbar_padding_vertical);
            Layout layout = this.c.getLayout();
            int n7 = layout != null && layout.getLineCount() > 1 ? 1 : 0;
            if (n7 != 0 && this.f > 0 && this.d.getMeasuredWidth() > this.f ? this.b(1, n5, n5 - n6) : this.b(0, n7 = n7 != 0 ? n5 : n6, n7)) {
                super.onMeasure(n3, n4);
            }
        }
    }

    public void setMaxInlineActionWidth(int n3) {
        this.f = n3;
    }
}

