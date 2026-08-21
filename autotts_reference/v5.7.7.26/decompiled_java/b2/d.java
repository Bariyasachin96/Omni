/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewPropertyAnimator
 */
package b2;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import b2.f;

public final class d
extends f {
    @Override
    public int a(View view, ViewGroup.MarginLayoutParams marginLayoutParams) {
        return view.getMeasuredWidth() + marginLayoutParams.rightMargin;
    }

    @Override
    public int b() {
        return 0;
    }

    @Override
    public int c() {
        return 0;
    }

    @Override
    public ViewPropertyAnimator d(View view, int n3) {
        return view.animate().translationX((float)n3);
    }

    @Override
    public void e(View view, int n3) {
        view.setTranslationX((float)n3);
    }
}

