/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityManager
 *  android.widget.EditText
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.f;
import com.google.android.material.datepicker.g;
import com.google.android.material.datepicker.r;
import com.google.android.material.internal.c0;
import java.util.Collection;

public interface DateSelector<S>
extends Parcelable {
    public static /* synthetic */ void a(EditText[] editTextArray, View view, boolean bl) {
        int n3 = editTextArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (!editTextArray[i3].hasFocus()) continue;
            return;
        }
        c0.l(view, false);
    }

    public static /* synthetic */ void c(View view) {
        c0.p(view, false);
    }

    public static boolean l(Context context) {
        return (context = (AccessibilityManager)context.getSystemService("accessibility")) != null && context.isTouchExplorationEnabled();
    }

    public static void m(EditText ... editText) {
        if (((EditText[])editText).length == 0) {
            return;
        }
        f f3 = new f((EditText[])editText);
        int n3 = ((EditText[])editText).length;
        for (int i3 = 0; i3 < n3; ++i3) {
            editText[i3].setOnFocusChangeListener((View.OnFocusChangeListener)f3);
        }
        editText = editText[0];
        editText.postDelayed((Runnable)new g((View)editText), 100L);
    }

    public String b(Context var1);

    public String d(Context var1);

    public int e(Context var1);

    public Collection f();

    public boolean h();

    public Collection i();

    public Object j();

    public void k(long var1);

    public View n(LayoutInflater var1, ViewGroup var2, Bundle var3, CalendarConstraints var4, r var5);
}

