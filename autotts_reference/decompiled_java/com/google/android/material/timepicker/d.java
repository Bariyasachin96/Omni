/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.timepicker;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.timepicker.TimePickerView;

public final class d
implements MaterialButtonToggleGroup.b {
    public final TimePickerView a;

    public /* synthetic */ d(TimePickerView timePickerView) {
        this.a = timePickerView;
    }

    @Override
    public final void a(MaterialButtonToggleGroup materialButtonToggleGroup, int n3, boolean bl) {
        TimePickerView.C(this.a, materialButtonToggleGroup, n3, bl);
    }
}

