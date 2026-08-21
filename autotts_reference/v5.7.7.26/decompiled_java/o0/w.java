/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 */
package o0;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.s;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import o0.y;

public class w {
    public final Runnable a;
    public final CopyOnWriteArrayList b = new CopyOnWriteArrayList();
    public final Map c = new HashMap();

    public w(Runnable runnable) {
        this.a = runnable;
    }

    public void a(y y3) {
        this.b.add(y3);
        this.a.run();
    }

    public void b(Menu menu, MenuInflater menuInflater) {
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            ((y)iterator.next()).c(menu, menuInflater);
        }
    }

    public void c(Menu menu) {
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            ((y)iterator.next()).b(menu);
        }
    }

    public boolean d(MenuItem menuItem) {
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            if (!((y)iterator.next()).a(menuItem)) continue;
            return true;
        }
        return false;
    }

    public void e(Menu menu) {
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            ((y)iterator.next()).d(menu);
        }
    }

    public void f(y y3) {
        this.b.remove(y3);
        s.a(this.c.remove(y3));
        this.a.run();
    }
}

