/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.text.Selection
 *  android.text.Spannable
 *  android.text.TextWatcher
 *  android.widget.EditText
 */
package z0;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.emoji2.text.f;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import z0.d;

public final class g
implements TextWatcher {
    public final EditText c;
    public final boolean d;
    public f.f e;
    public int f = Integer.MAX_VALUE;
    public int g = 0;
    public boolean h;

    public g(EditText editText, boolean bl) {
        this.c = editText;
        this.d = bl;
        this.h = true;
    }

    public static void b(EditText editText, int n3) {
        if (n3 == 1 && editText != null && editText.isAttachedToWindow()) {
            editText = editText.getEditableText();
            n3 = Selection.getSelectionStart((CharSequence)editText);
            int n4 = Selection.getSelectionEnd((CharSequence)editText);
            androidx.emoji2.text.f.c().p((CharSequence)editText);
            z0.d.b((Spannable)editText, n3, n4);
        }
    }

    public final f.f a() {
        if (this.e == null) {
            this.e = new a(this.c);
        }
        return this.e;
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
    }

    public void c(boolean bl) {
        if (this.h != bl) {
            if (this.e != null) {
                androidx.emoji2.text.f.c().u(this.e);
            }
            this.h = bl;
            if (bl) {
                z0.g.b(this.c, androidx.emoji2.text.f.c().e());
            }
        }
    }

    public final boolean d() {
        return !this.h || !this.d && !androidx.emoji2.text.f.i();
        {
        }
    }

    public void onTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
        block0: {
            block1: {
                block2: {
                    if (this.c.isInEditMode() || this.d() || n4 > n5 || !(charSequence instanceof Spannable)) break block0;
                    n4 = androidx.emoji2.text.f.c().e();
                    if (n4 == 0) break block1;
                    if (n4 == 1) break block2;
                    if (n4 == 3) break block1;
                    break block0;
                }
                charSequence = (Spannable)charSequence;
                androidx.emoji2.text.f.c().s(charSequence, n3, n3 + n5, this.f, this.g);
                return;
            }
            androidx.emoji2.text.f.c().t(this.a());
        }
    }

    public static class a
    extends f.f {
        public final Reference a;

        public a(EditText editText) {
            this.a = new WeakReference<EditText>(editText);
        }

        @Override
        public void b() {
            super.b();
            z0.g.b((EditText)this.a.get(), 1);
        }
    }
}

