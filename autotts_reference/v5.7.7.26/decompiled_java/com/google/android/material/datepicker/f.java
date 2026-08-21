/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 *  android.widget.EditText
 */
package com.google.android.material.datepicker;

import android.view.View;
import android.widget.EditText;
import com.google.android.material.datepicker.DateSelector;

public final class f
implements View.OnFocusChangeListener {
    public final EditText[] c;

    public /* synthetic */ f(EditText[] editTextArray) {
        this.c = editTextArray;
    }

    public final void onFocusChange(View view, boolean bl) {
        DateSelector.a(this.c, view, bl);
    }
}

