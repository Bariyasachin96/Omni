/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.transition.Transition
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.HeaderViewListAdapter
 *  android.widget.PopupWindow
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.os.Build;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.d;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.a0;
import androidx.appcompat.widget.d0;
import java.lang.reflect.Method;

public class MenuPopupWindow
extends ListPopupWindow
implements d0 {
    public static Method L;
    public d0 K;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static {
        try {
            if (Build.VERSION.SDK_INT <= 28) {
                L = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE);
            }
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return;
        }
    }

    public MenuPopupWindow(Context context, AttributeSet attributeSet, int n3, int n4) {
        super(context, attributeSet, n3, n4);
    }

    public void S(Object object) {
        a.a(this.H, (Transition)object);
    }

    public void T(Object object) {
        a.b(this.H, (Transition)object);
    }

    public void U(d0 d02) {
        this.K = d02;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void V(boolean bl) {
        if (Build.VERSION.SDK_INT > 28) {
            b.a(this.H, bl);
            return;
        }
        Method method = L;
        if (method == null) return;
        try {
            method.invoke((Object)this.H, bl);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    @Override
    public void a(e e3, MenuItem menuItem) {
        d0 d02 = this.K;
        if (d02 != null) {
            d02.a(e3, menuItem);
        }
    }

    @Override
    public void f(e e3, MenuItem menuItem) {
        d0 d02 = this.K;
        if (d02 != null) {
            d02.f(e3, menuItem);
        }
    }

    @Override
    public a0 s(Context object, boolean bl) {
        object = new MenuDropDownListView((Context)object, bl);
        ((MenuDropDownListView)((Object)object)).setHoverListener(this);
        return object;
    }

    public static class MenuDropDownListView
    extends a0 {
        public final int p;
        public final int q;
        public d0 r;
        public MenuItem s;

        public MenuDropDownListView(Context context, boolean bl) {
            super(context, bl);
            if (1 == context.getResources().getConfiguration().getLayoutDirection()) {
                this.p = 21;
                this.q = 22;
                return;
            }
            this.p = 22;
            this.q = 21;
        }

        @Override
        public boolean onHoverEvent(MotionEvent motionEvent) {
            if (this.r != null) {
                int n3;
                int n4;
                Object object = this.getAdapter();
                if (object instanceof HeaderViewListAdapter) {
                    object = (HeaderViewListAdapter)object;
                    n4 = object.getHeadersCount();
                    object = (d)object.getWrappedAdapter();
                } else {
                    object = (d)((Object)object);
                    n4 = 0;
                }
                g g3 = motionEvent.getAction() != 10 && (n3 = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY())) != -1 && (n4 = n3 - n4) >= 0 && n4 < ((d)((Object)object)).getCount() ? ((d)((Object)object)).c(n4) : null;
                MenuItem menuItem = this.s;
                if (menuItem != g3) {
                    object = ((d)((Object)object)).b();
                    if (menuItem != null) {
                        this.r.f((e)object, menuItem);
                    }
                    this.s = g3;
                    if (g3 != null) {
                        this.r.a((e)object, g3);
                    }
                }
            }
            return super.onHoverEvent(motionEvent);
        }

        public boolean onKeyDown(int n3, KeyEvent object) {
            ListMenuItemView listMenuItemView = (ListMenuItemView)this.getSelectedView();
            if (listMenuItemView != null && n3 == this.p) {
                if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                    this.performItemClick((View)listMenuItemView, this.getSelectedItemPosition(), this.getSelectedItemId());
                }
                return true;
            }
            if (listMenuItemView != null && n3 == this.q) {
                this.setSelection(-1);
                object = this.getAdapter();
                object = object instanceof HeaderViewListAdapter ? (d)((HeaderViewListAdapter)object).getWrappedAdapter() : (d)((Object)object);
                object.b().e(false);
                return true;
            }
            return super.onKeyDown(n3, object);
        }

        public void setHoverListener(d0 d02) {
            this.r = d02;
        }
    }

    public static abstract class a {
        public static void a(PopupWindow popupWindow, Transition transition) {
            popupWindow.setEnterTransition(transition);
        }

        public static void b(PopupWindow popupWindow, Transition transition) {
            popupWindow.setExitTransition(transition);
        }
    }

    public static abstract class b {
        public static void a(PopupWindow popupWindow, boolean bl) {
            popupWindow.setTouchModal(bl);
        }
    }
}

