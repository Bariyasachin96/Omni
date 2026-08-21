/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 */
package com.google.android.material.button;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonGroup;
import com.google.android.material.button.e;
import com.google.android.material.button.f;
import y2.a;
import z1.c;
import z1.k;
import z1.l;

public class MaterialSplitButton
extends MaterialButtonGroup {
    public static final int n = z1.l.Widget_Material3_MaterialSplitButton;

    public MaterialSplitButton(Context context) {
        this(context, null);
    }

    public MaterialSplitButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialSplitButtonStyle);
    }

    public MaterialSplitButton(Context context, AttributeSet attributeSet, int n3) {
        super(a.d(context, attributeSet, n3, n), attributeSet, n3);
    }

    public static /* synthetic */ void p(MaterialSplitButton materialSplitButton, MaterialButton materialButton, boolean bl) {
        materialSplitButton = materialSplitButton.getResources();
        int n3 = bl ? z1.k.mtrl_button_expanded_content_description : z1.k.mtrl_button_collapsed_content_description;
        com.google.android.material.button.e.a(materialButton, materialSplitButton.getString(n3));
    }

    @Override
    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof MaterialButton) {
            if (this.getChildCount() <= 2) {
                MaterialButton materialButton = (MaterialButton)view;
                super.addView(view, n3, layoutParams);
                if (this.indexOfChild(view) == 1) {
                    materialButton.setCheckable(true);
                    materialButton.setA11yClassName(Button.class.getName());
                    if (Build.VERSION.SDK_INT >= 30) {
                        view = this.getResources();
                        n3 = materialButton.isChecked() ? z1.k.mtrl_button_expanded_content_description : z1.k.mtrl_button_collapsed_content_description;
                        com.google.android.material.button.e.a(materialButton, view.getString(n3));
                        materialButton.e(new f(this));
                    }
                }
                return;
            }
            throw new IllegalArgumentException("MaterialSplitButton can only hold two MaterialButtons.");
        }
        throw new IllegalArgumentException("MaterialSplitButton can only hold MaterialButtons.");
    }
}

