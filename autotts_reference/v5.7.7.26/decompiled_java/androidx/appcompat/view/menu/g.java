/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 *  android.view.ActionProvider
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package androidx.appcompat.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.view.menu.l;
import c.h;
import h0.a;
import o0.b;

public final class g
implements i0.b {
    public View A;
    public b B;
    public MenuItem.OnActionExpandListener C;
    public boolean D = false;
    public ContextMenu.ContextMenuInfo E;
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    public CharSequence e;
    public CharSequence f;
    public Intent g;
    public char h;
    public int i = 4096;
    public char j;
    public int k = 4096;
    public Drawable l;
    public int m = 0;
    public e n;
    public l o;
    public Runnable p;
    public MenuItem.OnMenuItemClickListener q;
    public CharSequence r;
    public CharSequence s;
    public ColorStateList t = null;
    public PorterDuff.Mode u = null;
    public boolean v = false;
    public boolean w = false;
    public boolean x = false;
    public int y = 16;
    public int z;

    public g(e e3, int n3, int n4, int n5, int n6, CharSequence charSequence, int n7) {
        this.n = e3;
        this.a = n4;
        this.b = n3;
        this.c = n5;
        this.d = n6;
        this.e = charSequence;
        this.z = n7;
    }

    public static void d(StringBuilder stringBuilder, int n3, int n4, String string) {
        if ((n3 & n4) == n4) {
            stringBuilder.append(string);
        }
    }

    public boolean A() {
        return this.n.K() && this.g() != '\u0000';
    }

    public boolean B() {
        return (this.z & 4) == 4;
    }

    @Override
    public b a() {
        return this.B;
    }

    @Override
    public i0.b b(b b3) {
        b b4 = this.B;
        if (b4 != null) {
            b4.g();
        }
        this.A = null;
        this.B = b3;
        this.n.N(true);
        b3 = this.B;
        if (b3 != null) {
            b3.i(new b.b(this){
                public final g a;
                {
                    this.a = g3;
                }

                @Override
                public void onActionProviderVisibilityChanged(boolean bl) {
                    g g3 = this.a;
                    g3.n.M(g3);
                }
            });
        }
        return this;
    }

    public void c() {
        this.n.L(this);
    }

    @Override
    public boolean collapseActionView() {
        if ((this.z & 8) == 0) {
            return false;
        }
        if (this.A == null) {
            return true;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.C;
        if (onActionExpandListener != null && !onActionExpandListener.onMenuItemActionCollapse((MenuItem)this)) {
            return false;
        }
        return this.n.f(this);
    }

    public final Drawable e(Drawable drawable) {
        Drawable drawable2;
        block5: {
            block6: {
                drawable2 = drawable;
                if (drawable == null) break block5;
                drawable2 = drawable;
                if (!this.x) break block5;
                if (this.v) break block6;
                drawable2 = drawable;
                if (!this.w) break block5;
            }
            drawable2 = h0.a.r(drawable).mutate();
            if (this.v) {
                h0.a.o(drawable2, this.t);
            }
            if (this.w) {
                h0.a.p(drawable2, this.u);
            }
            this.x = false;
        }
        return drawable2;
    }

    @Override
    public boolean expandActionView() {
        if (!this.j()) {
            return false;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.C;
        if (onActionExpandListener != null && !onActionExpandListener.onMenuItemActionExpand((MenuItem)this)) {
            return false;
        }
        return this.n.m(this);
    }

    public int f() {
        return this.d;
    }

    public char g() {
        if (this.n.J()) {
            return this.j;
        }
        return this.h;
    }

    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    @Override
    public View getActionView() {
        Object object = this.A;
        if (object != null) {
            return object;
        }
        object = this.B;
        if (object != null) {
            object = ((b)object).c(this);
            this.A = object;
            return object;
        }
        return null;
    }

    @Override
    public int getAlphabeticModifiers() {
        return this.k;
    }

    public char getAlphabeticShortcut() {
        return this.j;
    }

    @Override
    public CharSequence getContentDescription() {
        return this.r;
    }

    public int getGroupId() {
        return this.b;
    }

    public Drawable getIcon() {
        Drawable drawable = this.l;
        if (drawable != null) {
            return this.e(drawable);
        }
        if (this.m != 0) {
            drawable = d.a.b(this.n.w(), this.m);
            this.m = 0;
            this.l = drawable;
            return this.e(drawable);
        }
        return null;
    }

    @Override
    public ColorStateList getIconTintList() {
        return this.t;
    }

    @Override
    public PorterDuff.Mode getIconTintMode() {
        return this.u;
    }

    public Intent getIntent() {
        return this.g;
    }

    public int getItemId() {
        return this.a;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.E;
    }

    @Override
    public int getNumericModifiers() {
        return this.i;
    }

    public char getNumericShortcut() {
        return this.h;
    }

    public int getOrder() {
        return this.c;
    }

    public SubMenu getSubMenu() {
        return this.o;
    }

    public CharSequence getTitle() {
        return this.e;
    }

    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f;
        if (charSequence != null) {
            return charSequence;
        }
        return this.e;
    }

    @Override
    public CharSequence getTooltipText() {
        return this.s;
    }

    public String h() {
        char c3 = this.g();
        if (c3 == '\u0000') {
            return "";
        }
        Resources resources = this.n.w().getResources();
        StringBuilder stringBuilder = new StringBuilder();
        if (ViewConfiguration.get((Context)this.n.w()).hasPermanentMenuKey()) {
            stringBuilder.append(resources.getString(c.h.abc_prepend_shortcut_label));
        }
        int n3 = this.n.J() ? this.k : this.i;
        androidx.appcompat.view.menu.g.d(stringBuilder, n3, 65536, resources.getString(c.h.abc_menu_meta_shortcut_label));
        androidx.appcompat.view.menu.g.d(stringBuilder, n3, 4096, resources.getString(c.h.abc_menu_ctrl_shortcut_label));
        androidx.appcompat.view.menu.g.d(stringBuilder, n3, 2, resources.getString(c.h.abc_menu_alt_shortcut_label));
        androidx.appcompat.view.menu.g.d(stringBuilder, n3, 1, resources.getString(c.h.abc_menu_shift_shortcut_label));
        androidx.appcompat.view.menu.g.d(stringBuilder, n3, 4, resources.getString(c.h.abc_menu_sym_shortcut_label));
        androidx.appcompat.view.menu.g.d(stringBuilder, n3, 8, resources.getString(c.h.abc_menu_function_shortcut_label));
        if (c3 != '\b') {
            if (c3 != '\n') {
                if (c3 != ' ') {
                    stringBuilder.append(c3);
                } else {
                    stringBuilder.append(resources.getString(c.h.abc_menu_space_shortcut_label));
                }
            } else {
                stringBuilder.append(resources.getString(c.h.abc_menu_enter_shortcut_label));
            }
        } else {
            stringBuilder.append(resources.getString(c.h.abc_menu_delete_shortcut_label));
        }
        return stringBuilder.toString();
    }

    public boolean hasSubMenu() {
        return this.o != null;
    }

    public CharSequence i(j.a a4) {
        if (a4 != null && a4.c()) {
            return this.getTitleCondensed();
        }
        return this.getTitle();
    }

    @Override
    public boolean isActionViewExpanded() {
        return this.D;
    }

    public boolean isCheckable() {
        return (this.y & 1) == 1;
    }

    public boolean isChecked() {
        return (this.y & 2) == 2;
    }

    public boolean isEnabled() {
        return (this.y & 0x10) != 0;
    }

    public boolean isVisible() {
        b b3 = this.B;
        if (b3 != null && b3.f()) {
            return (this.y & 8) == 0 && this.B.b();
        }
        return (this.y & 8) == 0;
    }

    public boolean j() {
        if ((this.z & 8) != 0) {
            b b3;
            if (this.A == null && (b3 = this.B) != null) {
                this.A = b3.c(this);
            }
            if (this.A != null) {
                return true;
            }
        }
        return false;
    }

    public boolean k() {
        Object object = this.q;
        if (object != null && object.onMenuItemClick((MenuItem)this)) {
            return true;
        }
        object = this.n;
        if (((e)object).h((e)object, this)) {
            return true;
        }
        object = this.p;
        if (object != null) {
            object.run();
            return true;
        }
        if (this.g != null) {
            try {
                this.n.w().startActivity(this.g);
                return true;
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                Log.e((String)"MenuItemImpl", (String)"Can't find activity to handle intent; ignoring", (Throwable)activityNotFoundException);
            }
        }
        return (object = this.B) != null && ((b)object).d();
    }

    public boolean l() {
        return (this.y & 0x20) == 32;
    }

    public boolean m() {
        return (this.y & 4) != 0;
    }

    public boolean n() {
        return (this.z & 1) == 1;
    }

    public boolean o() {
        return (this.z & 2) == 2;
    }

    public i0.b p(int n3) {
        Context context = this.n.w();
        this.q(LayoutInflater.from((Context)context).inflate(n3, (ViewGroup)new LinearLayout(context), false));
        return this;
    }

    public i0.b q(View view) {
        int n3;
        this.A = view;
        this.B = null;
        if (view != null && view.getId() == -1 && (n3 = this.a) > 0) {
            view.setId(n3);
        }
        this.n.L(this);
        return this;
    }

    public void r(boolean bl) {
        this.D = bl;
        this.n.N(false);
    }

    public void s(boolean bl) {
        int n3 = this.y;
        int n4 = bl ? 2 : 0;
        this.y = n4 |= n3 & 0xFFFFFFFD;
        if (n3 != n4) {
            this.n.N(false);
        }
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    public MenuItem setAlphabeticShortcut(char c3) {
        if (this.j == c3) {
            return this;
        }
        this.j = Character.toLowerCase(c3);
        this.n.N(false);
        return this;
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c3, int n3) {
        if (this.j == c3 && this.k == n3) {
            return this;
        }
        this.j = Character.toLowerCase(c3);
        this.k = KeyEvent.normalizeMetaState((int)n3);
        this.n.N(false);
        return this;
    }

    public MenuItem setCheckable(boolean bl) {
        int n3;
        int n4 = this.y;
        this.y = n3 = bl | n4 & 0xFFFFFFFE;
        if (n4 != n3) {
            this.n.N(false);
        }
        return this;
    }

    public MenuItem setChecked(boolean bl) {
        if ((this.y & 4) != 0) {
            this.n.Y(this);
            return this;
        }
        this.s(bl);
        return this;
    }

    @Override
    public i0.b setContentDescription(CharSequence charSequence) {
        this.r = charSequence;
        this.n.N(false);
        return this;
    }

    public MenuItem setEnabled(boolean bl) {
        this.y = bl ? (this.y |= 0x10) : (this.y &= 0xFFFFFFEF);
        this.n.N(false);
        return this;
    }

    public MenuItem setIcon(int n3) {
        this.l = null;
        this.m = n3;
        this.x = true;
        this.n.N(false);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        this.m = 0;
        this.l = drawable;
        this.x = true;
        this.n.N(false);
        return this;
    }

    @Override
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.t = colorStateList;
        this.v = true;
        this.x = true;
        this.n.N(false);
        return this;
    }

    @Override
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.u = mode;
        this.w = true;
        this.x = true;
        this.n.N(false);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.g = intent;
        return this;
    }

    public MenuItem setNumericShortcut(char c3) {
        if (this.h == c3) {
            return this;
        }
        this.h = c3;
        this.n.N(false);
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char c3, int n3) {
        if (this.h == c3 && this.i == n3) {
            return this;
        }
        this.h = c3;
        this.i = KeyEvent.normalizeMetaState((int)n3);
        this.n.N(false);
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.C = onActionExpandListener;
        return this;
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.q = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c3, char c4) {
        this.h = c3;
        this.j = Character.toLowerCase(c4);
        this.n.N(false);
        return this;
    }

    @Override
    public MenuItem setShortcut(char c3, char c4, int n3, int n4) {
        this.h = c3;
        this.i = KeyEvent.normalizeMetaState((int)n3);
        this.j = Character.toLowerCase(c4);
        this.k = KeyEvent.normalizeMetaState((int)n4);
        this.n.N(false);
        return this;
    }

    @Override
    public void setShowAsAction(int n3) {
        int n4 = n3 & 3;
        if (n4 != 0 && n4 != 1 && n4 != 2) {
            throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
        this.z = n3;
        this.n.L(this);
    }

    public MenuItem setTitle(int n3) {
        return this.setTitle(this.n.w().getString(n3));
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.e = charSequence;
        this.n.N(false);
        l l3 = this.o;
        if (l3 != null) {
            l3.setHeaderTitle(charSequence);
        }
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f = charSequence;
        this.n.N(false);
        return this;
    }

    @Override
    public i0.b setTooltipText(CharSequence charSequence) {
        this.s = charSequence;
        this.n.N(false);
        return this;
    }

    public MenuItem setVisible(boolean bl) {
        if (this.y(bl)) {
            this.n.M(this);
        }
        return this;
    }

    public void t(boolean bl) {
        int n3 = this.y;
        int n4 = bl ? 4 : 0;
        this.y = n4 | n3 & 0xFFFFFFFB;
    }

    public String toString() {
        CharSequence charSequence = this.e;
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }

    public void u(boolean bl) {
        if (bl) {
            this.y |= 0x20;
            return;
        }
        this.y &= 0xFFFFFFDF;
    }

    public void v(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.E = contextMenuInfo;
    }

    public i0.b w(int n3) {
        this.setShowAsAction(n3);
        return this;
    }

    public void x(l l3) {
        this.o = l3;
        l3.setHeaderTitle(this.getTitle());
    }

    public boolean y(boolean bl) {
        int n3 = this.y;
        int n4 = bl ? 0 : 8;
        this.y = n4 |= n3 & 0xFFFFFFF7;
        return n3 != n4;
    }

    public boolean z() {
        return this.n.C();
    }
}

