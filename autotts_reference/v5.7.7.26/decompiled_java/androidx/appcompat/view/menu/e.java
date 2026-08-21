/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.SparseArray
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.KeyCharacterMap$KeyData
 *  android.view.KeyEvent
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.ViewConfiguration
 */
package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.l;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import o0.b1;

public class e
implements i0.a {
    public static final int[] A = new int[]{1, 4, 5, 3, 2, 0};
    public final Context a;
    public final Resources b;
    public boolean c;
    public boolean d;
    public a e;
    public ArrayList f;
    public ArrayList g;
    public boolean h;
    public ArrayList i;
    public ArrayList j;
    public boolean k;
    public int l = 0;
    public ContextMenu.ContextMenuInfo m;
    public CharSequence n;
    public Drawable o;
    public View p;
    public boolean q = false;
    public boolean r = false;
    public boolean s = false;
    public boolean t = false;
    public boolean u = false;
    public ArrayList v = new ArrayList();
    public CopyOnWriteArrayList w = new CopyOnWriteArrayList();
    public g x;
    public boolean y = false;
    public boolean z;

    public e(Context context) {
        this.a = context;
        this.b = context.getResources();
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.h = true;
        this.i = new ArrayList();
        this.j = new ArrayList();
        this.k = true;
        this.g0(true);
    }

    public static int D(int n3) {
        int[] nArray;
        int n4 = (0xFFFF0000 & n3) >> 16;
        if (n4 >= 0 && n4 < (nArray = A).length) {
            return n3 & 0xFFFF | nArray[n4] << 16;
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    public static int p(ArrayList arrayList, int n3) {
        for (int i3 = arrayList.size() - 1; i3 >= 0; --i3) {
            if (((g)arrayList.get(i3)).f() > n3) continue;
            return i3 + 1;
        }
        return 0;
    }

    public View A() {
        return this.p;
    }

    public ArrayList B() {
        this.t();
        return this.j;
    }

    public boolean C() {
        return this.t;
    }

    public Resources E() {
        return this.b;
    }

    public e F() {
        return this;
    }

    public ArrayList G() {
        if (!this.h) {
            return this.g;
        }
        this.g.clear();
        int n3 = this.f.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            g g3 = (g)this.f.get(i3);
            if (!g3.isVisible()) continue;
            this.g.add(g3);
        }
        this.h = false;
        this.k = true;
        return this.g;
    }

    public boolean H() {
        return this.q ^ true;
    }

    public boolean I() {
        return this.y;
    }

    public boolean J() {
        return this.c;
    }

    public boolean K() {
        return this.d;
    }

    public void L(g g3) {
        this.k = true;
        this.N(true);
    }

    public void M(g g3) {
        this.h = true;
        this.N(true);
    }

    public void N(boolean bl) {
        if (!this.q) {
            if (bl) {
                this.h = true;
                this.k = true;
            }
            this.i(bl);
            return;
        }
        this.r = true;
        if (bl) {
            this.s = true;
        }
    }

    public boolean O(MenuItem menuItem, int n3) {
        return this.P(menuItem, null, n3);
    }

    public boolean P(MenuItem object, i i3, int n3) {
        Object object2 = (g)object;
        if (object2 != null && ((g)object2).isEnabled()) {
            boolean bl = ((g)object2).k();
            object = ((g)object2).a();
            boolean bl2 = object != null && ((o0.b)object).a();
            if (((g)object2).j()) {
                bl = ((g)object2).expandActionView() | bl;
                if (bl) {
                    this.e(true);
                }
                return bl;
            }
            if (!((g)object2).hasSubMenu() && !bl2) {
                if ((n3 & 1) == 0) {
                    this.e(true);
                }
                return bl;
            }
            if ((n3 & 4) == 0) {
                this.e(false);
            }
            if (!((g)object2).hasSubMenu()) {
                ((g)object2).x(new l(this.w(), this, (g)object2));
            }
            object2 = (l)((g)object2).getSubMenu();
            if (bl2) {
                ((o0.b)object).e((SubMenu)object2);
            }
            if (!(bl = this.l((l)object2, i3) | bl)) {
                this.e(true);
            }
            return bl;
        }
        return false;
    }

    public final void Q(int n3, boolean bl) {
        if (n3 >= 0 && n3 < this.f.size()) {
            this.f.remove(n3);
            if (bl) {
                this.N(true);
            }
        }
    }

    public void R(i i3) {
        for (WeakReference weakReference : this.w) {
            i i4 = (i)weakReference.get();
            if (i4 != null && i4 != i3) continue;
            this.w.remove(weakReference);
        }
    }

    public void S(Bundle bundle) {
        if (bundle != null) {
            int n3;
            SparseArray sparseArray = bundle.getSparseParcelableArray(this.v());
            int n4 = this.size();
            for (n3 = 0; n3 < n4; ++n3) {
                MenuItem menuItem = this.getItem(n3);
                View view = menuItem.getActionView();
                if (view != null && view.getId() != -1) {
                    view.restoreHierarchyState(sparseArray);
                }
                if (!menuItem.hasSubMenu()) continue;
                ((l)menuItem.getSubMenu()).S(bundle);
            }
            n3 = bundle.getInt("android:menu:expandedactionview");
            if (n3 > 0 && (bundle = this.findItem(n3)) != null) {
                bundle.expandActionView();
            }
        }
    }

    public void T(Bundle bundle) {
        this.j(bundle);
    }

    public void U(Bundle bundle) {
        int n3 = this.size();
        SparseArray sparseArray = null;
        for (int i3 = 0; i3 < n3; ++i3) {
            MenuItem menuItem = this.getItem(i3);
            View view = menuItem.getActionView();
            SparseArray sparseArray2 = sparseArray;
            if (view != null) {
                sparseArray2 = sparseArray;
                if (view.getId() != -1) {
                    SparseArray sparseArray3 = sparseArray;
                    if (sparseArray == null) {
                        sparseArray3 = new SparseArray();
                    }
                    view.saveHierarchyState(sparseArray3);
                    sparseArray2 = sparseArray3;
                    if (menuItem.isActionViewExpanded()) {
                        bundle.putInt("android:menu:expandedactionview", menuItem.getItemId());
                        sparseArray2 = sparseArray3;
                    }
                }
            }
            if (menuItem.hasSubMenu()) {
                ((l)menuItem.getSubMenu()).U(bundle);
            }
            sparseArray = sparseArray2;
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(this.v(), sparseArray);
        }
    }

    public void V(Bundle bundle) {
        this.k(bundle);
    }

    public void W(a a4) {
        this.e = a4;
    }

    public e X(int n3) {
        this.l = n3;
        return this;
    }

    public void Y(MenuItem menuItem) {
        int n3 = menuItem.getGroupId();
        int n4 = this.f.size();
        this.i0();
        for (int i3 = 0; i3 < n4; ++i3) {
            g g3 = (g)this.f.get(i3);
            if (g3.getGroupId() != n3 || !g3.m() || !g3.isCheckable()) continue;
            boolean bl = g3 == menuItem;
            g3.s(bl);
        }
        this.h0();
    }

    public e Z(int n3) {
        this.b0(0, null, n3, null, null);
        return this;
    }

    public MenuItem a(int n3, int n4, int n5, CharSequence object) {
        int n6 = androidx.appcompat.view.menu.e.D(n5);
        object = this.g(n3, n4, n5, n6, (CharSequence)object, this.l);
        Object object2 = this.m;
        if (object2 != null) {
            ((g)object).v((ContextMenu.ContextMenuInfo)object2);
        }
        object2 = this.f;
        ((ArrayList)object2).add(androidx.appcompat.view.menu.e.p((ArrayList)object2, n6), object);
        this.N(true);
        return object;
    }

    public e a0(Drawable drawable) {
        this.b0(0, null, 0, drawable, null);
        return this;
    }

    public MenuItem add(int n3) {
        return this.a(0, 0, 0, this.b.getString(n3));
    }

    public MenuItem add(int n3, int n4, int n5, int n6) {
        return this.a(n3, n4, n5, this.b.getString(n6));
    }

    public MenuItem add(int n3, int n4, int n5, CharSequence charSequence) {
        return this.a(n3, n4, n5, charSequence);
    }

    public MenuItem add(CharSequence charSequence) {
        return this.a(0, 0, 0, charSequence);
    }

    public int addIntentOptions(int n3, int n4, int n5, ComponentName componentName, Intent[] intentArray, Intent intent, int n6, MenuItem[] menuItemArray) {
        PackageManager packageManager = this.a.getPackageManager();
        int n7 = 0;
        List list = packageManager.queryIntentActivityOptions(componentName, intentArray, intent, 0);
        int n8 = list != null ? list.size() : 0;
        int n9 = n7;
        if ((n6 & 1) == 0) {
            this.removeGroup(n3);
            n9 = n7;
        }
        while (n9 < n8) {
            ResolveInfo resolveInfo = (ResolveInfo)list.get(n9);
            n6 = resolveInfo.specificIndex;
            componentName = n6 < 0 ? intent : intentArray[n6];
            Intent intent2 = new Intent((Intent)componentName);
            componentName = resolveInfo.activityInfo;
            intent2.setComponent(new ComponentName(componentName.applicationInfo.packageName, componentName.name));
            componentName = this.add(n3, n4, n5, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArray != null && (n6 = resolveInfo.specificIndex) >= 0) {
                menuItemArray[n6] = componentName;
            }
            ++n9;
        }
        return n8;
    }

    public SubMenu addSubMenu(int n3) {
        return this.addSubMenu(0, 0, 0, this.b.getString(n3));
    }

    public SubMenu addSubMenu(int n3, int n4, int n5, int n6) {
        return this.addSubMenu(n3, n4, n5, this.b.getString(n6));
    }

    public SubMenu addSubMenu(int n3, int n4, int n5, CharSequence object) {
        object = (g)this.a(n3, n4, n5, (CharSequence)object);
        l l3 = new l(this.a, this, (g)object);
        ((g)object).x(l3);
        return l3;
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return this.addSubMenu(0, 0, 0, charSequence);
    }

    public void b(i i3) {
        this.c(i3, this.a);
    }

    public final void b0(int n3, CharSequence charSequence, int n4, Drawable drawable, View view) {
        Resources resources = this.E();
        if (view != null) {
            this.p = view;
            this.n = null;
            this.o = null;
        } else {
            if (n3 > 0) {
                this.n = resources.getText(n3);
            } else if (charSequence != null) {
                this.n = charSequence;
            }
            if (n4 > 0) {
                this.o = e0.a.d(this.w(), n4);
            } else if (drawable != null) {
                this.o = drawable;
            }
            this.p = null;
        }
        this.N(false);
    }

    public void c(i i3, Context context) {
        this.w.add(new WeakReference<i>(i3));
        i3.b(context, this);
        this.k = true;
    }

    public e c0(int n3) {
        this.b0(n3, null, 0, null, null);
        return this;
    }

    public void clear() {
        g g3 = this.x;
        if (g3 != null) {
            this.f(g3);
        }
        this.f.clear();
        this.N(true);
    }

    public void clearHeader() {
        this.o = null;
        this.n = null;
        this.p = null;
        this.N(false);
    }

    public void close() {
        this.e(true);
    }

    public void d() {
        a a4 = this.e;
        if (a4 != null) {
            a4.b(this);
        }
    }

    public e d0(CharSequence charSequence) {
        this.b0(0, charSequence, 0, null, null);
        return this;
    }

    public final void e(boolean bl) {
        if (this.u) {
            return;
        }
        this.u = true;
        for (WeakReference weakReference : this.w) {
            i i3 = (i)weakReference.get();
            if (i3 == null) {
                this.w.remove(weakReference);
                continue;
            }
            i3.a(this, bl);
        }
        this.u = false;
    }

    public e e0(View view) {
        this.b0(0, null, 0, null, view);
        return this;
    }

    public boolean f(g g3) {
        boolean bl = this.w.isEmpty();
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (!bl) {
            if (this.x != g3) {
                bl4 = bl2;
            } else {
                this.i0();
                Iterator iterator = this.w.iterator();
                bl4 = bl3;
                while (true) {
                    bl2 = bl4;
                    if (!iterator.hasNext()) break;
                    WeakReference weakReference = (WeakReference)iterator.next();
                    i i3 = (i)weakReference.get();
                    if (i3 == null) {
                        this.w.remove(weakReference);
                        continue;
                    }
                    bl4 = bl2 = i3.k(this, g3);
                    if (bl2) break;
                }
                this.h0();
                bl4 = bl2;
                if (bl2) {
                    this.x = null;
                    bl4 = bl2;
                }
            }
        }
        return bl4;
    }

    public void f0(boolean bl) {
        this.z = bl;
    }

    public MenuItem findItem(int n3) {
        int n4 = this.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            g g3 = (g)this.f.get(i3);
            if (g3.getItemId() == n3) {
                return g3;
            }
            if (!g3.hasSubMenu() || (g3 = g3.getSubMenu().findItem(n3)) == null) continue;
            return g3;
        }
        return null;
    }

    public final g g(int n3, int n4, int n5, int n6, CharSequence charSequence, int n7) {
        return new g(this, n3, n4, n5, n6, charSequence, n7);
    }

    public final void g0(boolean bl) {
        block3: {
            block2: {
                if (!bl) break block2;
                int n3 = this.b.getConfiguration().keyboard;
                bl = true;
                if (n3 != 1 && b1.j(ViewConfiguration.get((Context)this.a), this.a)) break block3;
            }
            bl = false;
        }
        this.d = bl;
    }

    public MenuItem getItem(int n3) {
        return (MenuItem)this.f.get(n3);
    }

    public boolean h(e e3, MenuItem menuItem) {
        a a4 = this.e;
        return a4 != null && a4.a(e3, menuItem);
    }

    public void h0() {
        this.q = false;
        if (this.r) {
            this.r = false;
            this.N(this.s);
        }
    }

    public boolean hasVisibleItems() {
        if (this.z) {
            return true;
        }
        int n3 = this.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            if (!((g)this.f.get(i3)).isVisible()) continue;
            return true;
        }
        return false;
    }

    public final void i(boolean bl) {
        if (this.w.isEmpty()) {
            return;
        }
        this.i0();
        for (WeakReference weakReference : this.w) {
            i i3 = (i)weakReference.get();
            if (i3 == null) {
                this.w.remove(weakReference);
                continue;
            }
            i3.g(bl);
        }
        this.h0();
    }

    public void i0() {
        if (!this.q) {
            this.q = true;
            this.r = false;
            this.s = false;
        }
    }

    public boolean isShortcutKey(int n3, KeyEvent keyEvent) {
        return this.r(n3, keyEvent) != null;
    }

    public final void j(Bundle bundle) {
        if ((bundle = bundle.getSparseParcelableArray("android:menu:presenters")) != null && !this.w.isEmpty()) {
            for (WeakReference weakReference : this.w) {
                i i3 = (i)weakReference.get();
                if (i3 == null) {
                    this.w.remove(weakReference);
                    continue;
                }
                int n3 = i3.getId();
                if (n3 <= 0 || (weakReference = (Parcelable)bundle.get(n3)) == null) continue;
                i3.d((Parcelable)weakReference);
            }
        }
    }

    public final void k(Bundle bundle) {
        if (this.w.isEmpty()) {
            return;
        }
        SparseArray sparseArray = new SparseArray();
        for (WeakReference weakReference : this.w) {
            i i3 = (i)weakReference.get();
            if (i3 == null) {
                this.w.remove(weakReference);
                continue;
            }
            int n3 = i3.getId();
            if (n3 <= 0 || (i3 = i3.j()) == null) continue;
            sparseArray.put(n3, (Object)i3);
        }
        bundle.putSparseParcelableArray("android:menu:presenters", sparseArray);
    }

    public final boolean l(l l3, i object2) {
        boolean bl = this.w.isEmpty();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (object2 != null) {
            bl2 = object2.f(l3);
        }
        for (Object object2 : this.w) {
            i i3 = (i)((Reference)object2).get();
            if (i3 == null) {
                this.w.remove(object2);
                continue;
            }
            if (bl2) continue;
            bl2 = i3.f(l3);
        }
        return bl2;
    }

    public boolean m(g g3) {
        boolean bl = this.w.isEmpty();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        this.i0();
        Iterator iterator = this.w.iterator();
        while (true) {
            bl = bl2;
            if (!iterator.hasNext()) break;
            WeakReference weakReference = (WeakReference)iterator.next();
            i i3 = (i)weakReference.get();
            if (i3 == null) {
                this.w.remove(weakReference);
                continue;
            }
            bl2 = bl = i3.l(this, g3);
            if (bl) break;
        }
        this.h0();
        if (bl) {
            this.x = g3;
        }
        return bl;
    }

    public int n(int n3) {
        return this.o(n3, 0);
    }

    public int o(int n3, int n4) {
        int n5 = this.size();
        int n6 = n4;
        if (n4 < 0) {
            n6 = 0;
        }
        while (n6 < n5) {
            if (((g)this.f.get(n6)).getGroupId() == n3) {
                return n6;
            }
            ++n6;
        }
        return -1;
    }

    public boolean performIdentifierAction(int n3, int n4) {
        return this.O(this.findItem(n3), n4);
    }

    public boolean performShortcut(int n3, KeyEvent object, int n4) {
        boolean bl = (object = this.r(n3, (KeyEvent)object)) != null ? this.O((MenuItem)object, n4) : false;
        if ((n4 & 2) != 0) {
            this.e(true);
        }
        return bl;
    }

    public int q(int n3) {
        int n4 = this.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            if (((g)this.f.get(i3)).getItemId() != n3) continue;
            return i3;
        }
        return -1;
    }

    public g r(int n3, KeyEvent object) {
        ArrayList arrayList = this.v;
        arrayList.clear();
        this.s(arrayList, n3, (KeyEvent)object);
        if (arrayList.isEmpty()) {
            return null;
        }
        int n4 = object.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        object.getKeyData(keyData);
        int n5 = arrayList.size();
        if (n5 == 1) {
            return (g)arrayList.get(0);
        }
        boolean bl = this.J();
        for (int i3 = 0; i3 < n5; ++i3) {
            char[] cArray;
            object = (g)arrayList.get(i3);
            char c3 = bl ? ((g)object).getAlphabeticShortcut() : ((g)object).getNumericShortcut();
            if (!(c3 == (cArray = keyData.meta)[0] && (n4 & 2) == 0 || c3 == cArray[2] && (n4 & 2) != 0) && (!bl || c3 != '\b' || n3 != 67)) continue;
            return object;
        }
        return null;
    }

    public void removeGroup(int n3) {
        int n4 = this.n(n3);
        if (n4 >= 0) {
            int n5 = this.f.size();
            for (int i3 = 0; i3 < n5 - n4 && ((g)this.f.get(n4)).getGroupId() == n3; ++i3) {
                this.Q(n4, false);
            }
            this.N(true);
        }
    }

    public void removeItem(int n3) {
        this.Q(this.q(n3), true);
    }

    public void s(List list, int n3, KeyEvent keyEvent) {
        boolean bl = this.J();
        int n4 = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || n3 == 67) {
            int n5 = this.f.size();
            for (int i3 = 0; i3 < n5; ++i3) {
                char[] cArray;
                g g3 = (g)this.f.get(i3);
                if (g3.hasSubMenu()) {
                    ((e)g3.getSubMenu()).s(list, n3, keyEvent);
                }
                char c3 = bl ? g3.getAlphabeticShortcut() : g3.getNumericShortcut();
                int n6 = bl ? g3.getAlphabeticModifiers() : g3.getNumericModifiers();
                if ((n4 & 0x1100F) != (n6 & 0x1100F) || c3 == '\u0000' || c3 != (cArray = keyData.meta)[0] && c3 != cArray[2] && (!bl || c3 != '\b' || n3 != 67) || !g3.isEnabled()) continue;
                list.add(g3);
            }
        }
    }

    public void setGroupCheckable(int n3, boolean bl, boolean bl2) {
        int n4 = this.f.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            g g3 = (g)this.f.get(i3);
            if (g3.getGroupId() != n3) continue;
            g3.t(bl2);
            g3.setCheckable(bl);
        }
    }

    public void setGroupDividerEnabled(boolean bl) {
        this.y = bl;
    }

    public void setGroupEnabled(int n3, boolean bl) {
        int n4 = this.f.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            g g3 = (g)this.f.get(i3);
            if (g3.getGroupId() != n3) continue;
            g3.setEnabled(bl);
        }
    }

    public void setGroupVisible(int n3, boolean bl) {
        int n4 = this.f.size();
        boolean bl2 = false;
        for (int i3 = 0; i3 < n4; ++i3) {
            g g3 = (g)this.f.get(i3);
            boolean bl3 = bl2;
            if (g3.getGroupId() == n3) {
                bl3 = bl2;
                if (g3.y(bl)) {
                    bl3 = true;
                }
            }
            bl2 = bl3;
        }
        if (bl2) {
            this.N(true);
        }
    }

    public void setQwertyMode(boolean bl) {
        this.c = bl;
        this.N(false);
    }

    public int size() {
        return this.f.size();
    }

    public void t() {
        ArrayList arrayList = this.G();
        if (!this.k) {
            return;
        }
        Object object = this.w.iterator();
        int n3 = 0;
        while (object.hasNext()) {
            WeakReference weakReference = (WeakReference)object.next();
            i i3 = (i)weakReference.get();
            if (i3 == null) {
                this.w.remove(weakReference);
                continue;
            }
            n3 |= i3.i();
        }
        if (n3 != 0) {
            this.i.clear();
            this.j.clear();
            int n4 = arrayList.size();
            for (n3 = 0; n3 < n4; ++n3) {
                object = (g)arrayList.get(n3);
                if (((g)object).l()) {
                    this.i.add(object);
                    continue;
                }
                this.j.add(object);
            }
        } else {
            this.i.clear();
            this.j.clear();
            this.j.addAll(this.G());
        }
        this.k = false;
    }

    public ArrayList u() {
        this.t();
        return this.i;
    }

    public String v() {
        return "android:menu:actionviewstates";
    }

    public Context w() {
        return this.a;
    }

    public g x() {
        return this.x;
    }

    public Drawable y() {
        return this.o;
    }

    public CharSequence z() {
        return this.n;
    }

    public static interface a {
        public boolean a(e var1, MenuItem var2);

        public void b(e var1);
    }

    public static interface b {
        public boolean a(g var1);
    }
}

