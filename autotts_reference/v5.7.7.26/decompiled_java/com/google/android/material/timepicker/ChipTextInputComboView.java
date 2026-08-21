/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.os.LocaleList
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Checkable
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.os.LocaleList;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.y;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.TimeModel;
import z1.g;
import z1.i;

class ChipTextInputComboView
extends FrameLayout
implements Checkable {
    public final Chip c;
    public final TextInputLayout d;
    public final EditText e;
    public TextWatcher f;
    public TextView g;

    public ChipTextInputComboView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChipTextInputComboView(Context object, AttributeSet object2, int n3) {
        super((Context)object, (AttributeSet)object2, n3);
        EditText editText;
        object2 = LayoutInflater.from((Context)object);
        object = (Chip)object2.inflate(i.material_time_chip, (ViewGroup)this, false);
        this.c = object;
        ((Chip)object).setAccessibilityClassName("android.view.View");
        object2 = (TextInputLayout)object2.inflate(i.material_time_input, (ViewGroup)this, false);
        this.d = object2;
        this.e = editText = ((TextInputLayout)((Object)object2)).getEditText();
        editText.setVisibility(4);
        b b3 = new b(this, null);
        this.f = b3;
        editText.addTextChangedListener((TextWatcher)b3);
        this.d();
        this.addView((View)object);
        this.addView((View)object2);
        this.g = (TextView)this.findViewById(z1.g.material_label);
        editText.setId(View.generateViewId());
        this.g.setLabelFor(editText.getId());
        editText.setSaveEnabled(false);
        editText.setLongClickable(false);
    }

    public final String c(CharSequence charSequence) {
        return TimeModel.o(this.getResources(), charSequence);
    }

    public final void d() {
        LocaleList localeList = this.getContext().getResources().getConfiguration().getLocales();
        this.e.setImeHintLocales(localeList);
    }

    public boolean isChecked() {
        return this.c.isChecked();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.d();
    }

    public void setChecked(boolean bl) {
        this.c.setChecked(bl);
        Object object = this.e;
        int n3 = bl ? 0 : 4;
        object.setVisibility(n3);
        object = this.c;
        n3 = bl ? 8 : 0;
        object.setVisibility(n3);
        if (this.isChecked()) {
            c0.p((View)this.e, false);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.c.setOnClickListener(onClickListener);
    }

    public void setTag(int n3, Object object) {
        this.c.setTag(n3, object);
    }

    public void toggle() {
        this.c.toggle();
    }

    public class b
    extends y {
        public final ChipTextInputComboView c;

        public b(ChipTextInputComboView chipTextInputComboView) {
            this.c = chipTextInputComboView;
        }

        public /* synthetic */ b(ChipTextInputComboView chipTextInputComboView, a a4) {
            this(chipTextInputComboView);
        }

        public void afterTextChanged(Editable object) {
            if (TextUtils.isEmpty((CharSequence)object)) {
                this.c.c.setText(this.c.c("00"));
                return;
            }
            String string = this.c.c((CharSequence)object);
            Chip chip = this.c.c;
            object = string;
            if (TextUtils.isEmpty((CharSequence)string)) {
                object = this.c.c("00");
            }
            chip.setText((CharSequence)object);
        }
    }
}

