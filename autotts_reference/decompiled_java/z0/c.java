/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputConnectionWrapper
 *  android.widget.TextView
 */
package z0;

import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.TextView;
import androidx.emoji2.text.f;

public final class c
extends InputConnectionWrapper {
    public final TextView a;
    public final a b;

    public c(TextView textView, InputConnection inputConnection, EditorInfo editorInfo) {
        this(textView, inputConnection, editorInfo, new a());
    }

    public c(TextView textView, InputConnection inputConnection, EditorInfo editorInfo, a a4) {
        super(inputConnection, false);
        this.a = textView;
        this.b = a4;
        a4.b(editorInfo);
    }

    public final Editable a() {
        return this.a.getEditableText();
    }

    public boolean deleteSurroundingText(int n3, int n4) {
        return this.b.a((InputConnection)this, this.a(), n3, n4, false) || super.deleteSurroundingText(n3, n4);
        {
        }
    }

    public boolean deleteSurroundingTextInCodePoints(int n3, int n4) {
        return this.b.a((InputConnection)this, this.a(), n3, n4, true) || super.deleteSurroundingTextInCodePoints(n3, n4);
        {
        }
    }

    public static class a {
        public boolean a(InputConnection inputConnection, Editable editable, int n3, int n4, boolean bl) {
            return f.f(inputConnection, editable, n3, n4, bl);
        }

        public void b(EditorInfo editorInfo) {
            if (f.i()) {
                f.c().v(editorInfo);
            }
        }
    }
}

