/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.method.KeyListener
 *  android.text.method.NumberKeyListener
 *  android.util.AttributeSet
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.widget.EditText
 */
package androidx.appcompat.widget;

import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import c.j;
import z0.a;

public class h {
    public final EditText a;
    public final a b;

    public h(EditText editText) {
        this.a = editText;
        this.b = new a(editText, false);
    }

    public KeyListener a(KeyListener keyListener) {
        KeyListener keyListener2 = keyListener;
        if (this.b(keyListener)) {
            keyListener2 = this.b.a(keyListener);
        }
        return keyListener2;
    }

    public boolean b(KeyListener keyListener) {
        return keyListener instanceof NumberKeyListener ^ true;
    }

    public void c(AttributeSet attributeSet, int n3) {
        Throwable throwable2;
        block4: {
            boolean bl;
            block3: {
                attributeSet = this.a.getContext().obtainStyledAttributes(attributeSet, j.AppCompatTextView, n3, 0);
                try {
                    n3 = j.AppCompatTextView_emojiCompatEnabled;
                    boolean bl2 = attributeSet.hasValue(n3);
                    bl = true;
                    if (!bl2) break block3;
                }
                catch (Throwable throwable2) {
                    break block4;
                }
                bl = attributeSet.getBoolean(n3, true);
            }
            attributeSet.recycle();
            this.e(bl);
            return;
        }
        attributeSet.recycle();
        throw throwable2;
    }

    public InputConnection d(InputConnection inputConnection, EditorInfo editorInfo) {
        return this.b.b(inputConnection, editorInfo);
    }

    public void e(boolean bl) {
        this.b.c(bl);
    }
}

