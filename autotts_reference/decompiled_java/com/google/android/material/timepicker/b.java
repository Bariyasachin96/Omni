/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.InputFilter
 *  android.text.Spanned
 */
package com.google.android.material.timepicker;

import android.text.InputFilter;
import android.text.Spanned;

public class b
implements InputFilter {
    public int a;

    public b(int n3) {
        this.a = n3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public CharSequence filter(CharSequence charSequence, int n3, int n4, Spanned spanned, int n5, int n6) {
        block3: {
            try {
                StringBuilder stringBuilder = new StringBuilder((CharSequence)spanned);
                stringBuilder.replace(n5, n6, charSequence.subSequence(n3, n4).toString());
                n4 = Integer.parseInt(stringBuilder.toString());
                n3 = this.a;
                if (n4 > n3) break block3;
                return null;
            }
            catch (NumberFormatException numberFormatException) {
                return "";
            }
        }
        return "";
    }
}

