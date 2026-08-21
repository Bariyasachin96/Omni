/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.PopupWindow
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import androidx.appcompat.widget.m0;
import androidx.core.widget.i;
import c.j;

class AppCompatPopupWindow
extends PopupWindow {
    public static final boolean b = false;
    public boolean a;

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.a(context, attributeSet, n3, 0);
    }

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int n3, int n4) {
        super(context, attributeSet, n3, n4);
        this.a(context, attributeSet, n3, n4);
    }

    public final void a(Context object, AttributeSet attributeSet, int n3, int n4) {
        if (((m0)(object = m0.v((Context)object, attributeSet, j.PopupWindow, n3, n4))).s(n3 = j.PopupWindow_overlapAnchor)) {
            this.b(((m0)object).a(n3, false));
        }
        this.setBackgroundDrawable(((m0)object).g(j.PopupWindow_android_popupBackground));
        ((m0)object).x();
    }

    public final void b(boolean bl) {
        if (b) {
            this.a = bl;
            return;
        }
        i.a(this, bl);
    }

    public void showAsDropDown(View view, int n3, int n4) {
        int n5 = n4;
        if (b) {
            n5 = n4;
            if (this.a) {
                n5 = n4 - view.getHeight();
            }
        }
        super.showAsDropDown(view, n3, n5);
    }

    public void showAsDropDown(View view, int n3, int n4, int n5) {
        int n6 = n4;
        if (b) {
            n6 = n4;
            if (this.a) {
                n6 = n4 - view.getHeight();
            }
        }
        super.showAsDropDown(view, n3, n6, n5);
    }

    public void update(View view, int n3, int n4, int n5, int n6) {
        int n7 = n4;
        if (b) {
            n7 = n4;
            if (this.a) {
                n7 = n4 - view.getHeight();
            }
        }
        super.update(view, n3, n7, n5, n6);
    }
}

