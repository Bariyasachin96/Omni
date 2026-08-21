/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window$Callback
 */
package androidx.appcompat.app;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.k;
import androidx.appcompat.app.b;
import androidx.appcompat.app.d;
import androidx.appcompat.app.l;
import c.a;
import h.b;
import o0.t;

public abstract class m
extends k
implements b {
    public d f;
    public final t.a g = new l(this);

    public m(Context context, int n3) {
        super(context, m.i(context, n3));
        d d3 = this.h();
        d3.K(m.i(context, n3));
        d3.w(null);
    }

    public static int i(Context context, int n3) {
        if (n3 == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(a.dialogTheme, typedValue, true);
            return typedValue.resourceId;
        }
        return n3;
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.e();
        this.h().e(view, layoutParams);
    }

    public void dismiss() {
        super.dismiss();
        this.h().x();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View view = this.getWindow().getDecorView();
        return t.e(this.g, view, (Window.Callback)this, keyEvent);
    }

    @Override
    public h.b f(b.a a4) {
        return null;
    }

    public View findViewById(int n3) {
        return this.h().j(n3);
    }

    public d h() {
        if (this.f == null) {
            this.f = androidx.appcompat.app.d.i(this, this);
        }
        return this.f;
    }

    public void invalidateOptionsMenu() {
        this.h().t();
    }

    public boolean j(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean k(int n3) {
        return this.h().F(n3);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.h().s();
        super.onCreate(bundle);
        this.h().w(bundle);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.h().C();
    }

    @Override
    public void q(h.b b3) {
    }

    @Override
    public void setContentView(int n3) {
        this.e();
        this.h().G(n3);
    }

    @Override
    public void setContentView(View view) {
        this.e();
        this.h().H(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.e();
        this.h().I(view, layoutParams);
    }

    public void setTitle(int n3) {
        super.setTitle(n3);
        this.h().L(this.getContext().getString(n3));
    }

    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.h().L(charSequence);
    }

    @Override
    public void v(h.b b3) {
    }
}

