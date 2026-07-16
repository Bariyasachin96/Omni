/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 */
package h;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import h.b;
import i.c;
import i.e;
import java.util.ArrayList;
import o.r;

public class f
extends ActionMode {
    public final Context a;
    public final b b;

    public f(Context context, b b3) {
        this.a = context;
        this.b = b3;
    }

    public void finish() {
        this.b.c();
    }

    public View getCustomView() {
        return this.b.d();
    }

    public Menu getMenu() {
        return new e(this.a, (i0.a)this.b.e());
    }

    public MenuInflater getMenuInflater() {
        return this.b.f();
    }

    public CharSequence getSubtitle() {
        return this.b.g();
    }

    public Object getTag() {
        return this.b.h();
    }

    public CharSequence getTitle() {
        return this.b.i();
    }

    public boolean getTitleOptionalHint() {
        return this.b.j();
    }

    public void invalidate() {
        this.b.k();
    }

    public boolean isTitleOptional() {
        return this.b.l();
    }

    public void setCustomView(View view) {
        this.b.m(view);
    }

    public void setSubtitle(int n3) {
        this.b.n(n3);
    }

    public void setSubtitle(CharSequence charSequence) {
        this.b.o(charSequence);
    }

    public void setTag(Object object) {
        this.b.p(object);
    }

    public void setTitle(int n3) {
        this.b.q(n3);
    }

    public void setTitle(CharSequence charSequence) {
        this.b.r(charSequence);
    }

    public void setTitleOptionalHint(boolean bl) {
        this.b.s(bl);
    }

    public static class a
    implements b.a {
        public final ActionMode.Callback a;
        public final Context b;
        public final ArrayList c;
        public final r d;

        public a(Context context, ActionMode.Callback callback) {
            this.b = context;
            this.a = callback;
            this.c = new ArrayList();
            this.d = new r();
        }

        @Override
        public boolean a(b b3, MenuItem menuItem) {
            return this.a.onActionItemClicked(this.e(b3), (MenuItem)new c(this.b, (i0.b)menuItem));
        }

        @Override
        public boolean b(b b3, Menu menu) {
            return this.a.onCreateActionMode(this.e(b3), this.f(menu));
        }

        @Override
        public boolean c(b b3, Menu menu) {
            return this.a.onPrepareActionMode(this.e(b3), this.f(menu));
        }

        @Override
        public void d(b b3) {
            this.a.onDestroyActionMode(this.e(b3));
        }

        public ActionMode e(b object) {
            int n3 = this.c.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                f f3 = (f)((Object)this.c.get(i3));
                if (f3 == null || f3.b != object) continue;
                return f3;
            }
            object = new f(this.b, (b)object);
            this.c.add(object);
            return object;
        }

        public final Menu f(Menu menu) {
            Menu menu2;
            Menu menu3 = menu2 = (Menu)this.d.get(menu);
            if (menu2 == null) {
                menu3 = new e(this.b, (i0.a)menu);
                this.d.put(menu, menu3);
            }
            return menu3;
        }
    }
}

