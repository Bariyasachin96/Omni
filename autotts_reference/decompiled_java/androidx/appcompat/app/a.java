/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.KeyEvent
 *  android.view.View
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.m;

public class a
extends m
implements DialogInterface {
    public final AlertController h = new AlertController(this.getContext(), this, this.getWindow());

    public a(Context context, int n3) {
        super(context, a.m(context, n3));
    }

    public static int m(Context context, int n3) {
        if ((n3 >>> 24 & 0xFF) >= 1) {
            return n3;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(c.a.alertDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    public ListView l() {
        return this.h.d();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.h.e();
    }

    public boolean onKeyDown(int n3, KeyEvent keyEvent) {
        if (this.h.f(n3, keyEvent)) {
            return true;
        }
        return super.onKeyDown(n3, keyEvent);
    }

    public boolean onKeyUp(int n3, KeyEvent keyEvent) {
        if (this.h.g(n3, keyEvent)) {
            return true;
        }
        return super.onKeyUp(n3, keyEvent);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.h.p(charSequence);
    }

    public static class a {
        public final AlertController.b a;
        public final int b;

        public a(Context context) {
            this(context, androidx.appcompat.app.a.m(context, 0));
        }

        public a(Context context, int n3) {
            this.a = new AlertController.b((Context)new ContextThemeWrapper(context, androidx.appcompat.app.a.m(context, n3)));
            this.b = n3;
        }

        public a a() {
            a a4 = new a(this.a.a, this.b);
            this.a.a(a4.h);
            a4.setCancelable(this.a.r);
            if (this.a.r) {
                a4.setCanceledOnTouchOutside(true);
            }
            a4.setOnCancelListener(this.a.s);
            a4.setOnDismissListener(this.a.t);
            DialogInterface.OnKeyListener onKeyListener = this.a.u;
            if (onKeyListener != null) {
                a4.setOnKeyListener(onKeyListener);
            }
            return a4;
        }

        public Context b() {
            return this.a.a;
        }

        public a c(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            AlertController.b b3 = this.a;
            b3.w = listAdapter;
            b3.x = onClickListener;
            return this;
        }

        public a d(View view) {
            this.a.g = view;
            return this;
        }

        public a e(Drawable drawable) {
            this.a.d = drawable;
            return this;
        }

        public a f(DialogInterface.OnKeyListener onKeyListener) {
            this.a.u = onKeyListener;
            return this;
        }

        public a g(ListAdapter listAdapter, int n3, DialogInterface.OnClickListener onClickListener) {
            AlertController.b b3 = this.a;
            b3.w = listAdapter;
            b3.x = onClickListener;
            b3.I = n3;
            b3.H = true;
            return this;
        }

        public a h(CharSequence charSequence) {
            this.a.f = charSequence;
            return this;
        }
    }
}

