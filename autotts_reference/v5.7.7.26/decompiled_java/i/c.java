/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 *  android.view.ActionProvider
 *  android.view.ActionProvider$VisibilityListener
 *  android.view.CollapsibleActionView
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 *  android.widget.FrameLayout
 */
package i;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.reflect.Method;
import o0.b;

public class c
extends i.b
implements MenuItem {
    public final i0.b d;
    public Method e;

    public c(Context context, i0.b b3) {
        super(context);
        if (b3 != null) {
            this.d = b3;
            return;
        }
        throw new IllegalArgumentException("Wrapped Object can not be null.");
    }

    public boolean collapseActionView() {
        return this.d.collapseActionView();
    }

    public boolean expandActionView() {
        return this.d.expandActionView();
    }

    public ActionProvider getActionProvider() {
        o0.b b3 = this.d.a();
        if (b3 instanceof a) {
            return ((a)b3).e;
        }
        return null;
    }

    public View getActionView() {
        View view;
        View view2 = view = this.d.getActionView();
        if (view instanceof b) {
            view2 = ((b)view).a();
        }
        return view2;
    }

    public int getAlphabeticModifiers() {
        return this.d.getAlphabeticModifiers();
    }

    public char getAlphabeticShortcut() {
        return this.d.getAlphabeticShortcut();
    }

    public CharSequence getContentDescription() {
        return this.d.getContentDescription();
    }

    public int getGroupId() {
        return this.d.getGroupId();
    }

    public Drawable getIcon() {
        return this.d.getIcon();
    }

    public ColorStateList getIconTintList() {
        return this.d.getIconTintList();
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.d.getIconTintMode();
    }

    public Intent getIntent() {
        return this.d.getIntent();
    }

    public int getItemId() {
        return this.d.getItemId();
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.d.getMenuInfo();
    }

    public int getNumericModifiers() {
        return this.d.getNumericModifiers();
    }

    public char getNumericShortcut() {
        return this.d.getNumericShortcut();
    }

    public int getOrder() {
        return this.d.getOrder();
    }

    public SubMenu getSubMenu() {
        return this.d(this.d.getSubMenu());
    }

    public CharSequence getTitle() {
        return this.d.getTitle();
    }

    public CharSequence getTitleCondensed() {
        return this.d.getTitleCondensed();
    }

    public CharSequence getTooltipText() {
        return this.d.getTooltipText();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void h(boolean bl) {
        Exception exception2;
        block3: {
            block2: {
                try {
                    if (this.e != null) break block2;
                    this.e = this.d.getClass().getDeclaredMethod("setExclusiveCheckable", Boolean.TYPE);
                }
                catch (Exception exception2) {
                    break block3;
                }
            }
            this.e.invoke((Object)this.d, bl);
            return;
        }
        Log.w((String)"MenuItemWrapper", (String)"Error while calling setExclusiveCheckable", (Throwable)exception2);
    }

    public boolean hasSubMenu() {
        return this.d.hasSubMenu();
    }

    public boolean isActionViewExpanded() {
        return this.d.isActionViewExpanded();
    }

    public boolean isCheckable() {
        return this.d.isCheckable();
    }

    public boolean isChecked() {
        return this.d.isChecked();
    }

    public boolean isEnabled() {
        return this.d.isEnabled();
    }

    public boolean isVisible() {
        return this.d.isVisible();
    }

    public MenuItem setActionProvider(ActionProvider object) {
        a a4 = new a(this, this.a, (ActionProvider)object);
        i0.b b3 = this.d;
        object = object != null ? a4 : null;
        b3.b((o0.b)object);
        return this;
    }

    public MenuItem setActionView(int n3) {
        this.d.setActionView(n3);
        View view = this.d.getActionView();
        if (view instanceof CollapsibleActionView) {
            this.d.setActionView((View)new b(view));
        }
        return this;
    }

    public MenuItem setActionView(View view) {
        Object object = view;
        if (view instanceof CollapsibleActionView) {
            object = new b(view);
        }
        this.d.setActionView((View)object);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c3) {
        this.d.setAlphabeticShortcut(c3);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c3, int n3) {
        this.d.setAlphabeticShortcut(c3, n3);
        return this;
    }

    public MenuItem setCheckable(boolean bl) {
        this.d.setCheckable(bl);
        return this;
    }

    public MenuItem setChecked(boolean bl) {
        this.d.setChecked(bl);
        return this;
    }

    public MenuItem setContentDescription(CharSequence charSequence) {
        this.d.setContentDescription(charSequence);
        return this;
    }

    public MenuItem setEnabled(boolean bl) {
        this.d.setEnabled(bl);
        return this;
    }

    public MenuItem setIcon(int n3) {
        this.d.setIcon(n3);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        this.d.setIcon(drawable);
        return this;
    }

    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.d.setIconTintList(colorStateList);
        return this;
    }

    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.d.setIconTintMode(mode);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.d.setIntent(intent);
        return this;
    }

    public MenuItem setNumericShortcut(char c3) {
        this.d.setNumericShortcut(c3);
        return this;
    }

    public MenuItem setNumericShortcut(char c3, int n3) {
        this.d.setNumericShortcut(c3, n3);
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        i0.b b3 = this.d;
        onActionExpandListener = onActionExpandListener != null ? new c(this, onActionExpandListener) : null;
        b3.setOnActionExpandListener(onActionExpandListener);
        return this;
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        i0.b b3 = this.d;
        onMenuItemClickListener = onMenuItemClickListener != null ? new d(this, onMenuItemClickListener) : null;
        b3.setOnMenuItemClickListener(onMenuItemClickListener);
        return this;
    }

    public MenuItem setShortcut(char c3, char c4) {
        this.d.setShortcut(c3, c4);
        return this;
    }

    public MenuItem setShortcut(char c3, char c4, int n3, int n4) {
        this.d.setShortcut(c3, c4, n3, n4);
        return this;
    }

    public void setShowAsAction(int n3) {
        this.d.setShowAsAction(n3);
    }

    public MenuItem setShowAsActionFlags(int n3) {
        this.d.setShowAsActionFlags(n3);
        return this;
    }

    public MenuItem setTitle(int n3) {
        this.d.setTitle(n3);
        return this;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.d.setTitle(charSequence);
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.d.setTitleCondensed(charSequence);
        return this;
    }

    public MenuItem setTooltipText(CharSequence charSequence) {
        this.d.setTooltipText(charSequence);
        return this;
    }

    public MenuItem setVisible(boolean bl) {
        return this.d.setVisible(bl);
    }

    public class a
    extends o0.b
    implements ActionProvider.VisibilityListener {
        public b.b d;
        public final ActionProvider e;
        public final c f;

        public a(c c3, Context context, ActionProvider actionProvider) {
            this.f = c3;
            super(context);
            this.e = actionProvider;
        }

        @Override
        public boolean a() {
            return this.e.hasSubMenu();
        }

        @Override
        public boolean b() {
            return this.e.isVisible();
        }

        @Override
        public View c(MenuItem menuItem) {
            return this.e.onCreateActionView(menuItem);
        }

        @Override
        public boolean d() {
            return this.e.onPerformDefaultAction();
        }

        @Override
        public void e(SubMenu subMenu) {
            this.e.onPrepareSubMenu(this.f.d(subMenu));
        }

        @Override
        public boolean f() {
            return this.e.overridesItemVisibility();
        }

        @Override
        public void i(b.b object) {
            this.d = object;
            ActionProvider actionProvider = this.e;
            object = object != null ? this : null;
            actionProvider.setVisibilityListener((ActionProvider.VisibilityListener)object);
        }

        public void onActionProviderVisibilityChanged(boolean bl) {
            b.b b3 = this.d;
            if (b3 != null) {
                b3.onActionProviderVisibilityChanged(bl);
            }
        }
    }

    public static class b
    extends FrameLayout
    implements h.c {
        public final CollapsibleActionView c;

        public b(View view) {
            super(view.getContext());
            this.c = (CollapsibleActionView)view;
            this.addView(view);
        }

        public View a() {
            return (View)this.c;
        }

        @Override
        public void onActionViewCollapsed() {
            this.c.onActionViewCollapsed();
        }

        @Override
        public void onActionViewExpanded() {
            this.c.onActionViewExpanded();
        }
    }

    public class c
    implements MenuItem.OnActionExpandListener {
        public final MenuItem.OnActionExpandListener a;
        public final c b;

        public c(c c3, MenuItem.OnActionExpandListener onActionExpandListener) {
            this.b = c3;
            this.a = onActionExpandListener;
        }

        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return this.a.onMenuItemActionCollapse(this.b.c(menuItem));
        }

        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return this.a.onMenuItemActionExpand(this.b.c(menuItem));
        }
    }

    public class d
    implements MenuItem.OnMenuItemClickListener {
        public final MenuItem.OnMenuItemClickListener a;
        public final c b;

        public d(c c3, MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            this.b = c3;
            this.a = onMenuItemClickListener;
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return this.a.onMenuItemClick(this.b.c(menuItem));
        }
    }
}

