/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.os.IBinder
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager$LayoutParams
 */
package androidx.appcompat.view.menu;

import android.content.DialogInterface;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.a;
import androidx.appcompat.view.menu.c;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;

public class f
implements DialogInterface.OnKeyListener,
DialogInterface.OnClickListener,
DialogInterface.OnDismissListener,
i.a {
    public e c;
    public a d;
    public c e;
    public i.a f;

    public f(e e3) {
        this.c = e3;
    }

    @Override
    public void a(e e3, boolean bl) {
        i.a a4;
        if (bl || e3 == this.c) {
            this.c();
        }
        if ((a4 = this.f) != null) {
            a4.a(e3, bl);
        }
    }

    @Override
    public boolean b(e e3) {
        i.a a4 = this.f;
        if (a4 != null) {
            return a4.b(e3);
        }
        return false;
    }

    public void c() {
        a a4 = this.d;
        if (a4 != null) {
            a4.dismiss();
        }
    }

    public void d(IBinder iBinder) {
        c c3;
        e e3 = this.c;
        Object object = new a.a(e3.w());
        this.e = c3 = new c(((a.a)object).b(), c.g.abc_list_menu_item_layout);
        c3.m(this);
        this.c.b(this.e);
        ((a.a)object).c(this.e.c(), this);
        c3 = e3.A();
        if (c3 != null) {
            ((a.a)object).d((View)c3);
        } else {
            ((a.a)object).e(e3.y()).h(e3.z());
        }
        ((a.a)object).f(this);
        this.d = object = ((a.a)object).a();
        object.setOnDismissListener((DialogInterface.OnDismissListener)this);
        object = this.d.getWindow().getAttributes();
        ((WindowManager.LayoutParams)object).type = 1003;
        if (iBinder != null) {
            ((WindowManager.LayoutParams)object).token = iBinder;
        }
        ((WindowManager.LayoutParams)object).flags |= 0x20000;
        this.d.show();
    }

    public void onClick(DialogInterface dialogInterface, int n3) {
        this.c.O((g)this.e.c().getItem(n3), 0);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        this.e.a(this.c, true);
    }

    public boolean onKey(DialogInterface dialogInterface, int n3, KeyEvent keyEvent) {
        if (n3 == 82 || n3 == 4) {
            Window window;
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                dialogInterface = this.d.getWindow();
                if (dialogInterface != null && (dialogInterface = dialogInterface.getDecorView()) != null && (dialogInterface = dialogInterface.getKeyDispatcherState()) != null) {
                    dialogInterface.startTracking(keyEvent, (Object)this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.d.getWindow()) != null && (window = window.getDecorView()) != null && (window = window.getKeyDispatcherState()) != null && window.isTracking(keyEvent)) {
                this.c.e(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return this.c.performShortcut(n3, keyEvent, 0);
    }
}

