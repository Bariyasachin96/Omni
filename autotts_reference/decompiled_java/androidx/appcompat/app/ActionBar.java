/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package androidx.appcompat.app;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.ViewGroup;
import c.j;
import h.b;

public abstract class ActionBar {
    public boolean f() {
        return false;
    }

    public abstract boolean g();

    public abstract void h(boolean var1);

    public abstract int i();

    public abstract Context j();

    public boolean k() {
        return false;
    }

    public abstract void l(Configuration var1);

    public void m() {
    }

    public abstract boolean n(int var1, KeyEvent var2);

    public boolean o(KeyEvent keyEvent) {
        return false;
    }

    public boolean p() {
        return false;
    }

    public abstract void q(boolean var1);

    public abstract void r(boolean var1);

    public abstract void s(CharSequence var1);

    public abstract b t(b.a var1);

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int a;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
            this.a = 8388627;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 0;
            context = context.obtainStyledAttributes(attributeSet, j.ActionBarLayout);
            this.a = context.getInt(j.ActionBarLayout_android_layout_gravity, 0);
            context.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.a = 0;
            this.a = layoutParams.a;
        }
    }

    public static abstract class a {
    }
}

