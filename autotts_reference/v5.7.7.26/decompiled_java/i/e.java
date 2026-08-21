/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package i;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import i.b;
import i0.a;

public class e
extends b
implements Menu {
    public final a d;

    public e(Context context, a a4) {
        super(context);
        if (a4 != null) {
            this.d = a4;
            return;
        }
        throw new IllegalArgumentException("Wrapped Object can not be null.");
    }

    public MenuItem add(int n3) {
        return this.c(this.d.add(n3));
    }

    public MenuItem add(int n3, int n4, int n5, int n6) {
        return this.c(this.d.add(n3, n4, n5, n6));
    }

    public MenuItem add(int n3, int n4, int n5, CharSequence charSequence) {
        return this.c(this.d.add(n3, n4, n5, charSequence));
    }

    public MenuItem add(CharSequence charSequence) {
        return this.c(this.d.add(charSequence));
    }

    public int addIntentOptions(int n3, int n4, int n5, ComponentName componentName, Intent[] intentArray, Intent intent, int n6, MenuItem[] menuItemArray) {
        MenuItem[] menuItemArray2 = menuItemArray != null ? new MenuItem[menuItemArray.length] : null;
        n5 = this.d.addIntentOptions(n3, n4, n5, componentName, intentArray, intent, n6, menuItemArray2);
        if (menuItemArray2 != null) {
            n4 = menuItemArray2.length;
            for (n3 = 0; n3 < n4; ++n3) {
                menuItemArray[n3] = this.c(menuItemArray2[n3]);
            }
        }
        return n5;
    }

    public SubMenu addSubMenu(int n3) {
        return this.d(this.d.addSubMenu(n3));
    }

    public SubMenu addSubMenu(int n3, int n4, int n5, int n6) {
        return this.d(this.d.addSubMenu(n3, n4, n5, n6));
    }

    public SubMenu addSubMenu(int n3, int n4, int n5, CharSequence charSequence) {
        return this.d(this.d.addSubMenu(n3, n4, n5, charSequence));
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return this.d(this.d.addSubMenu(charSequence));
    }

    public void clear() {
        this.e();
        this.d.clear();
    }

    public void close() {
        this.d.close();
    }

    public MenuItem findItem(int n3) {
        return this.c(this.d.findItem(n3));
    }

    public MenuItem getItem(int n3) {
        return this.c(this.d.getItem(n3));
    }

    public boolean hasVisibleItems() {
        return this.d.hasVisibleItems();
    }

    public boolean isShortcutKey(int n3, KeyEvent keyEvent) {
        return this.d.isShortcutKey(n3, keyEvent);
    }

    public boolean performIdentifierAction(int n3, int n4) {
        return this.d.performIdentifierAction(n3, n4);
    }

    public boolean performShortcut(int n3, KeyEvent keyEvent, int n4) {
        return this.d.performShortcut(n3, keyEvent, n4);
    }

    public void removeGroup(int n3) {
        this.f(n3);
        this.d.removeGroup(n3);
    }

    public void removeItem(int n3) {
        this.g(n3);
        this.d.removeItem(n3);
    }

    public void setGroupCheckable(int n3, boolean bl, boolean bl2) {
        this.d.setGroupCheckable(n3, bl, bl2);
    }

    public void setGroupEnabled(int n3, boolean bl) {
        this.d.setGroupEnabled(n3, bl);
    }

    public void setGroupVisible(int n3, boolean bl) {
        this.d.setGroupVisible(n3, bl);
    }

    public void setQwertyMode(boolean bl) {
        this.d.setQwertyMode(bl);
    }

    public int size() {
        return this.d.size();
    }
}

