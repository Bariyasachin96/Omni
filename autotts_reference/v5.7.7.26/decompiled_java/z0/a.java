/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextWatcher
 *  android.text.method.KeyListener
 *  android.text.method.NumberKeyListener
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.widget.EditText
 *  android.widget.TextView
 */
package z0;

import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.TextView;
import n0.h;
import z0.c;
import z0.e;
import z0.g;

public final class a {
    public final b a;
    public int b = Integer.MAX_VALUE;
    public int c = 0;

    public a(EditText editText, boolean bl) {
        h.h(editText, "editText cannot be null");
        this.a = new a(editText, bl);
    }

    public KeyListener a(KeyListener keyListener) {
        return this.a.a(keyListener);
    }

    public InputConnection b(InputConnection inputConnection, EditorInfo editorInfo) {
        if (inputConnection == null) {
            return null;
        }
        return this.a.b(inputConnection, editorInfo);
    }

    public void c(boolean bl) {
        this.a.c(bl);
    }

    public static class a
    extends b {
        public final EditText a;
        public final g b;

        public a(EditText editText, boolean bl) {
            g g3;
            this.a = editText;
            this.b = g3 = new g(editText, bl);
            editText.addTextChangedListener((TextWatcher)g3);
            editText.setEditableFactory(z0.b.getInstance());
        }

        @Override
        public KeyListener a(KeyListener keyListener) {
            if (keyListener instanceof e) {
                return keyListener;
            }
            if (keyListener == null) {
                return null;
            }
            if (keyListener instanceof NumberKeyListener) {
                return keyListener;
            }
            return new e(keyListener);
        }

        @Override
        public InputConnection b(InputConnection inputConnection, EditorInfo editorInfo) {
            if (inputConnection instanceof c) {
                return inputConnection;
            }
            return new c((TextView)this.a, inputConnection, editorInfo);
        }

        @Override
        public void c(boolean bl) {
            this.b.c(bl);
        }
    }

    public static abstract class b {
        public abstract KeyListener a(KeyListener var1);

        public abstract InputConnection b(InputConnection var1, EditorInfo var2);

        public abstract void c(boolean var1);
    }
}

