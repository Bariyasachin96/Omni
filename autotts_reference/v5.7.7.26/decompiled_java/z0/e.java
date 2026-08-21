/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.text.method.KeyListener
 *  android.view.KeyEvent
 *  android.view.View
 */
package z0;

import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import androidx.emoji2.text.f;

public final class e
implements KeyListener {
    public final KeyListener a;
    public final a b;

    public e(KeyListener keyListener) {
        this(keyListener, new a());
    }

    public e(KeyListener keyListener, a a4) {
        this.a = keyListener;
        this.b = a4;
    }

    public void clearMetaKeyState(View view, Editable editable, int n3) {
        this.a.clearMetaKeyState(view, editable, n3);
    }

    public int getInputType() {
        return this.a.getInputType();
    }

    public boolean onKeyDown(View view, Editable editable, int n3, KeyEvent keyEvent) {
        return this.b.a(editable, n3, keyEvent) || this.a.onKeyDown(view, editable, n3, keyEvent);
        {
        }
    }

    public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
        return this.a.onKeyOther(view, editable, keyEvent);
    }

    public boolean onKeyUp(View view, Editable editable, int n3, KeyEvent keyEvent) {
        return this.a.onKeyUp(view, editable, n3, keyEvent);
    }

    public static class a {
        public boolean a(Editable editable, int n3, KeyEvent keyEvent) {
            return f.g(editable, n3, keyEvent);
        }
    }
}

