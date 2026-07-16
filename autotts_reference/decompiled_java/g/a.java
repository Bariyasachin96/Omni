/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.text.method.TransformationMethod
 *  android.view.View
 */
package g;

import android.content.Context;
import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import java.util.Locale;

public class a
implements TransformationMethod {
    public Locale c;

    public a(Context context) {
        this.c = context.getResources().getConfiguration().locale;
    }

    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if (charSequence != null) {
            return charSequence.toString().toUpperCase(this.c);
        }
        return null;
    }

    public void onFocusChanged(View view, CharSequence charSequence, boolean bl, int n3, Rect rect) {
    }
}

