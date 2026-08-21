/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.Menu
 *  android.view.Window$Callback
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.Window;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.widget.ScrollingTabContainerView;
import o0.h1;

public interface u {
    public boolean a();

    public Context b();

    public boolean c();

    public void collapseActionView();

    public boolean d();

    public boolean e();

    public boolean f();

    public void g();

    public CharSequence getTitle();

    public void h(ScrollingTabContainerView var1);

    public void i(boolean var1);

    public boolean j();

    public void k(int var1);

    public int l();

    public void m(int var1);

    public int n();

    public h1 o(int var1, long var2);

    public void p();

    public void q();

    public void r(boolean var1);

    public void setIcon(int var1);

    public void setIcon(Drawable var1);

    public void setMenu(Menu var1, i.a var2);

    public void setMenuPrepared();

    public void setVisibility(int var1);

    public void setWindowCallback(Window.Callback var1);

    public void setWindowTitle(CharSequence var1);
}

