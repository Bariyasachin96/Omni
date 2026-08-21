/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.text.method.TransformationMethod
 *  android.view.View
 */
package z0;

import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import androidx.emoji2.text.f;

public class h
implements TransformationMethod {
    public final TransformationMethod c;

    public h(TransformationMethod transformationMethod) {
        this.c = transformationMethod;
    }

    public TransformationMethod a() {
        return this.c;
    }

    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if (view.isInEditMode()) {
            return charSequence;
        }
        TransformationMethod transformationMethod = this.c;
        CharSequence charSequence2 = charSequence;
        if (transformationMethod != null) {
            charSequence2 = transformationMethod.getTransformation(charSequence, view);
        }
        charSequence = charSequence2;
        if (charSequence2 != null) {
            charSequence = f.c().e() != 1 ? charSequence2 : f.c().p(charSequence2);
        }
        return charSequence;
    }

    public void onFocusChanged(View view, CharSequence charSequence, boolean bl, int n3, Rect rect) {
        TransformationMethod transformationMethod = this.c;
        if (transformationMethod != null) {
            transformationMethod.onFocusChanged(view, charSequence, bl, n3, rect);
        }
    }
}

