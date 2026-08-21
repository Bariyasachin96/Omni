/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.KeyEvent
 *  android.view.KeyboardShortcutGroup
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.SearchEvent
 *  android.view.View
 *  android.view.Window$Callback
 *  android.view.WindowManager$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package h;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public abstract class i
implements Window.Callback {
    public final Window.Callback c;

    public i(Window.Callback callback) {
        if (callback != null) {
            this.c = callback;
            return;
        }
        throw new IllegalArgumentException("Window callback may not be null");
    }

    public final Window.Callback a() {
        return this.c;
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.c.dispatchGenericMotionEvent(motionEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.c.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.c.dispatchKeyShortcutEvent(keyEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.c.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.c.dispatchTouchEvent(motionEvent);
    }

    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return this.c.dispatchTrackballEvent(motionEvent);
    }

    public void onActionModeFinished(ActionMode actionMode) {
        this.c.onActionModeFinished(actionMode);
    }

    public void onActionModeStarted(ActionMode actionMode) {
        this.c.onActionModeStarted(actionMode);
    }

    public void onAttachedToWindow() {
        this.c.onAttachedToWindow();
    }

    public boolean onCreatePanelMenu(int n3, Menu menu) {
        return this.c.onCreatePanelMenu(n3, menu);
    }

    public View onCreatePanelView(int n3) {
        return this.c.onCreatePanelView(n3);
    }

    public void onDetachedFromWindow() {
        this.c.onDetachedFromWindow();
    }

    public boolean onMenuItemSelected(int n3, MenuItem menuItem) {
        return this.c.onMenuItemSelected(n3, menuItem);
    }

    public boolean onMenuOpened(int n3, Menu menu) {
        return this.c.onMenuOpened(n3, menu);
    }

    public void onPanelClosed(int n3, Menu menu) {
        this.c.onPanelClosed(n3, menu);
    }

    public void onPointerCaptureChanged(boolean bl) {
        h.i$c.a(this.c, bl);
    }

    public boolean onPreparePanel(int n3, View view, Menu menu) {
        return this.c.onPreparePanel(n3, view, menu);
    }

    public void onProvideKeyboardShortcuts(List list, Menu menu, int n3) {
        b.a(this.c, list, menu, n3);
    }

    public boolean onSearchRequested() {
        return this.c.onSearchRequested();
    }

    public boolean onSearchRequested(SearchEvent searchEvent) {
        return a.a(this.c, searchEvent);
    }

    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        this.c.onWindowAttributesChanged(layoutParams);
    }

    public void onWindowFocusChanged(boolean bl) {
        this.c.onWindowFocusChanged(bl);
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int n3) {
        return a.b(this.c, callback, n3);
    }

    public static abstract class a {
        public static boolean a(Window.Callback callback, SearchEvent searchEvent) {
            return callback.onSearchRequested(searchEvent);
        }

        public static ActionMode b(Window.Callback callback, ActionMode.Callback callback2, int n3) {
            return callback.onWindowStartingActionMode(callback2, n3);
        }
    }

    public static abstract class b {
        public static void a(Window.Callback callback, List<KeyboardShortcutGroup> list, Menu menu, int n3) {
            callback.onProvideKeyboardShortcuts(list, menu, n3);
        }
    }

    public static abstract class c {
        public static void a(Window.Callback callback, boolean bl) {
            callback.onPointerCaptureChanged(bl);
        }
    }
}

