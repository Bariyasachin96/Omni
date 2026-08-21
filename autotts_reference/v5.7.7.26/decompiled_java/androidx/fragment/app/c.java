/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 */
package androidx.fragment.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.k;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.i;
import androidx.fragment.app.y;
import androidx.lifecycle.d0;
import androidx.lifecycle.e0;
import androidx.lifecycle.p;
import j1.e;
import java.util.Objects;

public class c
extends Fragment
implements DialogInterface.OnCancelListener,
DialogInterface.OnDismissListener {
    public Handler e0;
    public Runnable f0 = new Runnable(this){
        public final c c;
        {
            this.c = c3;
        }

        @Override
        public void run() {
            this.c.h0.onDismiss((DialogInterface)this.c.p0);
        }
    };
    public DialogInterface.OnCancelListener g0 = new DialogInterface.OnCancelListener(this){
        public final c c;
        {
            this.c = c3;
        }

        public void onCancel(DialogInterface object) {
            if (this.c.p0 != null) {
                object = this.c;
                ((c)object).onCancel((DialogInterface)((c)object).p0);
            }
        }
    };
    public DialogInterface.OnDismissListener h0 = new DialogInterface.OnDismissListener(this){
        public final c c;
        {
            this.c = c3;
        }

        public void onDismiss(DialogInterface object) {
            if (this.c.p0 != null) {
                object = this.c;
                ((c)object).onDismiss((DialogInterface)((c)object).p0);
            }
        }
    };
    public int i0 = 0;
    public int j0 = 0;
    public boolean k0 = true;
    public boolean l0 = true;
    public int m0 = -1;
    public boolean n0;
    public p o0 = new p(this){
        public final c a;
        {
            this.a = c3;
        }

        public void b(androidx.lifecycle.k k3) {
            if (k3 != null && this.a.l0) {
                k3 = this.a.o1();
                if (k3.getParent() == null) {
                    if (this.a.p0 != null) {
                        if (FragmentManager.I0(3)) {
                            this.toString();
                            Objects.toString(this.a.p0);
                        }
                        this.a.p0.setContentView((View)k3);
                        return;
                    }
                } else {
                    throw new IllegalStateException("DialogFragment can not be attached to a container view");
                }
            }
        }
    };
    public Dialog p0;
    public boolean q0;
    public boolean r0;
    public boolean s0;
    public boolean t0 = false;

    @Override
    public void G0(Bundle bundle) {
        boolean bl;
        int n3;
        super.G0(bundle);
        Dialog dialog = this.p0;
        if (dialog != null) {
            dialog = dialog.onSaveInstanceState();
            dialog.putBoolean("android:dialogShowing", false);
            bundle.putBundle("android:savedDialogState", (Bundle)dialog);
        }
        if ((n3 = this.i0) != 0) {
            bundle.putInt("android:style", n3);
        }
        if ((n3 = this.j0) != 0) {
            bundle.putInt("android:theme", n3);
        }
        if (!(bl = this.k0)) {
            bundle.putBoolean("android:cancelable", bl);
        }
        if (!(bl = this.l0)) {
            bundle.putBoolean("android:showsDialog", bl);
        }
        if ((n3 = this.m0) != -1) {
            bundle.putInt("android:backStackId", n3);
        }
    }

    @Override
    public void H0() {
        super.H0();
        Dialog dialog = this.p0;
        if (dialog != null) {
            this.q0 = false;
            dialog.show();
            dialog = this.p0.getWindow().getDecorView();
            androidx.lifecycle.d0.a((View)dialog, this);
            androidx.lifecycle.e0.a((View)dialog, this);
            j1.e.a((View)dialog, this);
        }
    }

    public void H1() {
        this.I1(false, false, false);
    }

    @Override
    public void I0() {
        super.I0();
        Dialog dialog = this.p0;
        if (dialog != null) {
            dialog.hide();
        }
    }

    public final void I1(boolean bl, boolean bl2, boolean bl3) {
        if (this.r0) {
            return;
        }
        this.r0 = true;
        this.s0 = false;
        Object object = this.p0;
        if (object != null) {
            object.setOnDismissListener(null);
            this.p0.dismiss();
            if (!bl2) {
                if (Looper.myLooper() == this.e0.getLooper()) {
                    this.onDismiss((DialogInterface)this.p0);
                } else {
                    this.e0.post(this.f0);
                }
            }
        }
        this.q0 = true;
        if (this.m0 >= 0) {
            if (bl3) {
                this.E().W0(this.m0, 1);
            } else {
                this.E().U0(this.m0, 1, bl);
            }
            this.m0 = -1;
            return;
        }
        object = this.E().o();
        ((y)object).q(true);
        ((y)object).m(this);
        if (bl3) {
            ((y)object).h();
            return;
        }
        if (bl) {
            ((y)object).g();
            return;
        }
        ((y)object).f();
    }

    public Dialog J1() {
        return this.p0;
    }

    @Override
    public void K0(Bundle bundle) {
        super.K0(bundle);
        if (this.p0 != null && bundle != null && (bundle = bundle.getBundle("android:savedDialogState")) != null) {
            this.p0.onRestoreInstanceState(bundle);
        }
    }

    public int K1() {
        return this.j0;
    }

    public Dialog L1(Bundle bundle) {
        if (FragmentManager.I0(3)) {
            ((Object)this).toString();
        }
        return new k(this.n1(), this.K1());
    }

    public View M1(int n3) {
        Dialog dialog = this.p0;
        if (dialog != null) {
            return dialog.findViewById(n3);
        }
        return null;
    }

    public boolean N1() {
        return this.t0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void O1(Bundle bundle) {
        Throwable throwable2;
        block5: {
            block6: {
                block3: {
                    block4: {
                        if (!this.l0) {
                            return;
                        }
                        if (this.t0) return;
                        try {
                            this.n0 = true;
                            bundle = this.L1(bundle);
                            this.p0 = bundle;
                            if (!this.l0) break block3;
                            this.Q1((Dialog)bundle, this.i0);
                            bundle = this.p();
                            if (!(bundle instanceof Activity)) break block4;
                            this.p0.setOwnerActivity((Activity)bundle);
                        }
                        catch (Throwable throwable2) {
                            break block5;
                        }
                    }
                    this.p0.setCancelable(this.k0);
                    this.p0.setOnCancelListener(this.g0);
                    this.p0.setOnDismissListener(this.h0);
                    this.t0 = true;
                    break block6;
                }
                this.p0 = null;
            }
            this.n0 = false;
            return;
        }
        this.n0 = false;
        throw throwable2;
    }

    public final Dialog P1() {
        Object object = this.J1();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("DialogFragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" does not have a Dialog.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void Q1(Dialog dialog, int n3) {
        if (n3 != 1 && n3 != 2) {
            if (n3 != 3) {
                return;
            }
            Window window = dialog.getWindow();
            if (window != null) {
                window.addFlags(24);
            }
        }
        dialog.requestWindowFeature(1);
    }

    @Override
    public void R0(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.R0(layoutInflater, viewGroup, bundle);
        if (this.K == null && this.p0 != null && bundle != null && (layoutInflater = bundle.getBundle("android:savedDialogState")) != null) {
            this.p0.onRestoreInstanceState((Bundle)layoutInflater);
        }
    }

    @Override
    public i d() {
        return new i(this, super.d()){
            public final i c;
            public final c d;
            {
                this.d = c3;
                this.c = i3;
            }

            @Override
            public View f(int n3) {
                if (this.c.j()) {
                    return this.c.f(n3);
                }
                return this.d.M1(n3);
            }

            @Override
            public boolean j() {
                return this.c.j() || this.d.N1();
                {
                }
            }
        };
    }

    @Override
    public void e0(Bundle bundle) {
        super.e0(bundle);
    }

    @Override
    public void h0(Context context) {
        super.h0(context);
        this.T().e(this.o0);
        if (!this.s0) {
            this.r0 = false;
        }
    }

    @Override
    public void k0(Bundle bundle) {
        super.k0(bundle);
        this.e0 = new Handler();
        boolean bl = this.A == 0;
        this.l0 = bl;
        if (bundle != null) {
            this.i0 = bundle.getInt("android:style", 0);
            this.j0 = bundle.getInt("android:theme", 0);
            this.k0 = bundle.getBoolean("android:cancelable", true);
            this.l0 = bundle.getBoolean("android:showsDialog", this.l0);
            this.m0 = bundle.getInt("android:backStackId", -1);
        }
    }

    public void onCancel(DialogInterface dialogInterface) {
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (!this.q0) {
            if (FragmentManager.I0(3)) {
                ((Object)this).toString();
            }
            this.I1(true, true, false);
        }
    }

    @Override
    public void r0() {
        super.r0();
        Dialog dialog = this.p0;
        if (dialog != null) {
            this.q0 = true;
            dialog.setOnDismissListener(null);
            this.p0.dismiss();
            if (!this.r0) {
                this.onDismiss((DialogInterface)this.p0);
            }
            this.p0 = null;
            this.t0 = false;
        }
    }

    @Override
    public void s0() {
        super.s0();
        if (!this.s0 && !this.r0) {
            this.r0 = true;
        }
        this.T().h(this.o0);
    }

    @Override
    public LayoutInflater t0(Bundle object) {
        LayoutInflater layoutInflater = super.t0((Bundle)object);
        if (this.l0 && !this.n0) {
            this.O1((Bundle)object);
            if (FragmentManager.I0(2)) {
                ((Object)this).toString();
            }
            if ((object = this.p0) != null) {
                return layoutInflater.cloneInContext(object.getContext());
            }
        } else if (FragmentManager.I0(2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("getting layout inflater for DialogFragment ");
            ((StringBuilder)object).append(this);
        }
        return layoutInflater;
    }
}

