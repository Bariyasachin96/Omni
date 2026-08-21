/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.InputFilter
 *  android.text.Selection
 *  android.text.Spannable
 *  android.text.Spanned
 *  android.widget.TextView
 */
package z0;

import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.widget.TextView;
import androidx.emoji2.text.f;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public final class d
implements InputFilter {
    public final TextView a;
    public f.f b;

    public d(TextView textView) {
        this.a = textView;
    }

    public static void b(Spannable spannable, int n3, int n4) {
        if (n3 >= 0 && n4 >= 0) {
            Selection.setSelection((Spannable)spannable, (int)n3, (int)n4);
            return;
        }
        if (n3 >= 0) {
            Selection.setSelection((Spannable)spannable, (int)n3);
            return;
        }
        if (n4 >= 0) {
            Selection.setSelection((Spannable)spannable, (int)n4);
        }
    }

    public final f.f a() {
        if (this.b == null) {
            this.b = new a(this.a, this);
        }
        return this.b;
    }

    public CharSequence filter(CharSequence charSequence, int n3, int n4, Spanned object, int n5, int n6) {
        block5: {
            block4: {
                block6: {
                    block3: {
                        if (!this.a.isInEditMode()) break block3;
                        object = charSequence;
                        break block4;
                    }
                    int n7 = f.c().e();
                    if (n7 == 0) break block5;
                    if (n7 == 1) break block6;
                    if (n7 == 3) break block5;
                    object = charSequence;
                    break block4;
                }
                if (n6 == 0 && n5 == 0 && object.length() == 0 && charSequence == this.a.getText()) {
                    return charSequence;
                }
                object = charSequence;
                if (charSequence != null) {
                    if (n3 != 0 || n4 != charSequence.length()) {
                        charSequence = charSequence.subSequence(n3, n4);
                    }
                    object = f.c().q(charSequence, 0, charSequence.length());
                }
            }
            return object;
        }
        f.c().t(this.a());
        return charSequence;
    }

    public static class a
    extends f.f {
        public final Reference a;
        public final Reference b;

        public a(TextView textView, d d3) {
            this.a = new WeakReference<TextView>(textView);
            this.b = new WeakReference<d>(d3);
        }

        @Override
        public void b() {
            CharSequence charSequence;
            CharSequence charSequence2;
            super.b();
            TextView textView = (TextView)this.a.get();
            if (this.c(textView, (InputFilter)this.b.get()) && textView.isAttachedToWindow() && (charSequence2 = textView.getText()) != (charSequence = f.c().p(charSequence2))) {
                int n3 = Selection.getSelectionStart((CharSequence)charSequence);
                int n4 = Selection.getSelectionEnd((CharSequence)charSequence);
                textView.setText(charSequence);
                if (charSequence instanceof Spannable) {
                    d.b((Spannable)charSequence, n3, n4);
                }
            }
        }

        public final boolean c(TextView inputFilterArray, InputFilter inputFilter) {
            if (inputFilter != null && inputFilterArray != null) {
                if ((inputFilterArray = inputFilterArray.getFilters()) == null) {
                    return false;
                }
                for (int i3 = 0; i3 < inputFilterArray.length; ++i3) {
                    if (inputFilterArray[i3] != inputFilter) continue;
                    return true;
                }
            }
            return false;
        }
    }
}

