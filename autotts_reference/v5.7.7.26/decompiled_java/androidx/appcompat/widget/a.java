/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ActionMenuView;
import c.j;
import o0.h1;
import o0.i1;
import o0.x0;

public abstract class a
extends ViewGroup {
    public final a c = new a(this);
    public final Context d;
    public ActionMenuView e;
    public ActionMenuPresenter f;
    public int g;
    public h1 h;
    public boolean i;
    public boolean j;

    public a(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        attributeSet = new TypedValue();
        if (context.getTheme().resolveAttribute(c.a.actionBarPopupTheme, (TypedValue)attributeSet, true) && attributeSet.resourceId != 0) {
            this.d = new ContextThemeWrapper(context, attributeSet.resourceId);
            return;
        }
        this.d = context;
    }

    public static int d(int n3, int n4, boolean bl) {
        if (bl) {
            return n3 - n4;
        }
        return n3 + n4;
    }

    public int c(View view, int n3, int n4, int n5) {
        view.measure(View.MeasureSpec.makeMeasureSpec((int)n3, (int)Integer.MIN_VALUE), n4);
        return Math.max(0, n3 - view.getMeasuredWidth() - n5);
    }

    public int e(View view, int n3, int n4, int n5, boolean bl) {
        int n6 = view.getMeasuredWidth();
        int n7 = view.getMeasuredHeight();
        n4 += (n5 - n7) / 2;
        if (bl) {
            view.layout(n3 - n6, n4, n3, n7 + n4);
        } else {
            view.layout(n3, n4, n3 + n6, n7 + n4);
        }
        if (bl) {
            return -n6;
        }
        return n6;
    }

    public h1 f(int n3, long l3) {
        h1 h12 = this.h;
        if (h12 != null) {
            h12.c();
        }
        if (n3 == 0) {
            if (this.getVisibility() != 0) {
                this.setAlpha(0.0f);
            }
            h12 = x0.e((View)this).b(1.0f);
            h12.e(l3);
            h12.g(this.c.d(h12, n3));
            return h12;
        }
        h12 = x0.e((View)this).b(0.0f);
        h12.e(l3);
        h12.g(this.c.d(h12, n3));
        return h12;
    }

    public int getAnimatedVisibility() {
        if (this.h != null) {
            return this.c.b;
        }
        return this.getVisibility();
    }

    public int getContentHeight() {
        return this.g;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Object object = this.getContext().obtainStyledAttributes(null, c.j.ActionBar, c.a.actionBarStyle, 0);
        this.setContentHeight(object.getLayoutDimension(c.j.ActionBar_height, 0));
        object.recycle();
        object = this.f;
        if (object != null) {
            ((ActionMenuPresenter)object).I(configuration);
        }
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionMasked();
        if (n3 == 9) {
            this.j = false;
        }
        if (!this.j) {
            boolean bl = super.onHoverEvent(motionEvent);
            if (n3 == 9 && !bl) {
                this.j = true;
            }
        }
        if (n3 == 10 || n3 == 3) {
            this.j = false;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionMasked();
        if (n3 == 0) {
            this.i = false;
        }
        if (!this.i) {
            boolean bl = super.onTouchEvent(motionEvent);
            if (n3 == 0 && !bl) {
                this.i = true;
            }
        }
        if (n3 == 1 || n3 == 3) {
            this.i = false;
        }
        return true;
    }

    public abstract void setContentHeight(int var1);

    public void setVisibility(int n3) {
        if (n3 != this.getVisibility()) {
            h1 h12 = this.h;
            if (h12 != null) {
                h12.c();
            }
            super.setVisibility(n3);
        }
    }

    public class a
    implements i1 {
        public boolean a;
        public int b;
        public final a c;

        public a(a a4) {
            this.c = a4;
            this.a = false;
        }

        @Override
        public void a(View view) {
            this.a = true;
        }

        @Override
        public void b(View object) {
            if (this.a) {
                return;
            }
            object = this.c;
            object.h = null;
            a.super.setVisibility(this.b);
        }

        @Override
        public void c(View view) {
            a.super.setVisibility(0);
            this.a = false;
        }

        public a d(h1 h12, int n3) {
            this.c.h = h12;
            this.b = n3;
            return this;
        }
    }
}

