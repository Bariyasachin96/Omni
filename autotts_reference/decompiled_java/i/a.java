/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.view.ActionProvider
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.KeyEvent
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 */
package i;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import i0.b;

public class a
implements b {
    public final int a;
    public final int b;
    public final int c;
    public CharSequence d;
    public CharSequence e;
    public Intent f;
    public char g;
    public int h = 4096;
    public char i;
    public int j = 4096;
    public Drawable k;
    public Context l;
    public MenuItem.OnMenuItemClickListener m;
    public CharSequence n;
    public CharSequence o;
    public ColorStateList p = null;
    public PorterDuff.Mode q = null;
    public boolean r = false;
    public boolean s = false;
    public int t = 16;

    public a(Context context, int n3, int n4, int n5, int n6, CharSequence charSequence) {
        this.l = context;
        this.a = n4;
        this.b = n3;
        this.c = n6;
        this.d = charSequence;
    }

    @Override
    public o0.b a() {
        return null;
    }

    @Override
    public b b(o0.b b3) {
        throw new UnsupportedOperationException();
    }

    public final void c() {
        Drawable drawable = this.k;
        if (drawable != null && (this.r || this.s)) {
            this.k = drawable = h0.a.r(drawable);
            this.k = drawable = drawable.mutate();
            if (this.r) {
                h0.a.o(drawable, this.p);
            }
            if (this.s) {
                h0.a.p(this.k, this.q);
            }
        }
    }

    @Override
    public boolean collapseActionView() {
        return false;
    }

    public b d(int n3) {
        throw new UnsupportedOperationException();
    }

    public b e(View view) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean expandActionView() {
        return false;
    }

    public b f(int n3) {
        this.setShowAsAction(n3);
        return this;
    }

    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    @Override
    public View getActionView() {
        return null;
    }

    @Override
    public int getAlphabeticModifiers() {
        return this.j;
    }

    public char getAlphabeticShortcut() {
        return this.i;
    }

    @Override
    public CharSequence getContentDescription() {
        return this.n;
    }

    public int getGroupId() {
        return this.b;
    }

    public Drawable getIcon() {
        return this.k;
    }

    @Override
    public ColorStateList getIconTintList() {
        return this.p;
    }

    @Override
    public PorterDuff.Mode getIconTintMode() {
        return this.q;
    }

    public Intent getIntent() {
        return this.f;
    }

    public int getItemId() {
        return this.a;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override
    public int getNumericModifiers() {
        return this.h;
    }

    public char getNumericShortcut() {
        return this.g;
    }

    public int getOrder() {
        return this.c;
    }

    public SubMenu getSubMenu() {
        return null;
    }

    public CharSequence getTitle() {
        return this.d;
    }

    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.e;
        if (charSequence != null) {
            return charSequence;
        }
        return this.d;
    }

    @Override
    public CharSequence getTooltipText() {
        return this.o;
    }

    public boolean hasSubMenu() {
        return false;
    }

    @Override
    public boolean isActionViewExpanded() {
        return false;
    }

    public boolean isCheckable() {
        return (this.t & 1) != 0;
    }

    public boolean isChecked() {
        return (this.t & 2) != 0;
    }

    public boolean isEnabled() {
        return (this.t & 0x10) != 0;
    }

    public boolean isVisible() {
        return (this.t & 8) == 0;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setAlphabeticShortcut(char c3) {
        this.i = Character.toLowerCase(c3);
        return this;
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c3, int n3) {
        this.i = Character.toLowerCase(c3);
        this.j = KeyEvent.normalizeMetaState((int)n3);
        return this;
    }

    public MenuItem setCheckable(boolean bl) {
        this.t = bl | this.t & 0xFFFFFFFE;
        return this;
    }

    public MenuItem setChecked(boolean bl) {
        int n3 = this.t;
        int n4 = bl ? 2 : 0;
        this.t = n4 | n3 & 0xFFFFFFFD;
        return this;
    }

    @Override
    public b setContentDescription(CharSequence charSequence) {
        this.n = charSequence;
        return this;
    }

    public MenuItem setEnabled(boolean bl) {
        int n3 = this.t;
        int n4 = bl ? 16 : 0;
        this.t = n4 | n3 & 0xFFFFFFEF;
        return this;
    }

    public MenuItem setIcon(int n3) {
        this.k = e0.a.d(this.l, n3);
        this.c();
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        this.k = drawable;
        this.c();
        return this;
    }

    @Override
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.p = colorStateList;
        this.r = true;
        this.c();
        return this;
    }

    @Override
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.q = mode;
        this.s = true;
        this.c();
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.f = intent;
        return this;
    }

    public MenuItem setNumericShortcut(char c3) {
        this.g = c3;
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char c3, int n3) {
        this.g = c3;
        this.h = KeyEvent.normalizeMetaState((int)n3);
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.m = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c3, char c4) {
        this.g = c3;
        this.i = Character.toLowerCase(c4);
        return this;
    }

    @Override
    public MenuItem setShortcut(char c3, char c4, int n3, int n4) {
        this.g = c3;
        this.h = KeyEvent.normalizeMetaState((int)n3);
        this.i = Character.toLowerCase(c4);
        this.j = KeyEvent.normalizeMetaState((int)n4);
        return this;
    }

    @Override
    public void setShowAsAction(int n3) {
    }

    public MenuItem setTitle(int n3) {
        this.d = this.l.getResources().getString(n3);
        return this;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.d = charSequence;
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.e = charSequence;
        return this;
    }

    @Override
    public b setTooltipText(CharSequence charSequence) {
        this.o = charSequence;
        return this;
    }

    public MenuItem setVisible(boolean bl) {
        int n3 = this.t;
        int n4 = 8;
        if (bl) {
            n4 = 0;
        }
        this.t = n3 & 8 | n4;
        return this;
    }
}

