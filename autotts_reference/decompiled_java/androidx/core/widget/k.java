/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ClipData
 *  android.content.ClipData$Item
 *  android.content.Context
 *  android.text.Editable
 *  android.text.Selection
 *  android.text.Spannable
 *  android.text.Spanned
 *  android.util.Log
 *  android.view.View
 *  android.widget.TextView
 */
package androidx.core.widget;

import android.content.ClipData;
import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Objects;
import o0.d;
import o0.g0;

public final class k
implements g0 {
    public static CharSequence b(Context object, ClipData.Item object2, int n3) {
        if ((n3 & 1) != 0) {
            object2 = object2.coerceToText(object);
            object = object2;
            if (object2 instanceof Spanned) {
                object = object2.toString();
            }
            return object;
        }
        return object2.coerceToStyledText(object);
    }

    public static void c(Editable editable, CharSequence charSequence) {
        int n3 = Selection.getSelectionStart((CharSequence)editable);
        int n4 = Selection.getSelectionEnd((CharSequence)editable);
        int n5 = Math.max(0, Math.min(n3, n4));
        n3 = Math.max(0, Math.max(n3, n4));
        Selection.setSelection((Spannable)editable, (int)n3);
        editable.replace(n5, n3, charSequence);
    }

    @Override
    public d a(View view, d d3) {
        if (Log.isLoggable((String)"ReceiveContent", (int)3)) {
            Objects.toString(d3);
        }
        if (d3.d() == 2) {
            return d3;
        }
        ClipData clipData = d3.b();
        int n3 = d3.c();
        d3 = (TextView)view;
        view = (Editable)d3.getText();
        d3 = d3.getContext();
        boolean bl = false;
        for (int i3 = 0; i3 < clipData.getItemCount(); ++i3) {
            CharSequence charSequence = k.b((Context)d3, clipData.getItemAt(i3), n3);
            boolean bl2 = bl;
            if (charSequence != null) {
                if (!bl) {
                    k.c((Editable)view, charSequence);
                    bl2 = true;
                } else {
                    view.insert(Selection.getSelectionEnd((CharSequence)view), (CharSequence)"\n");
                    view.insert(Selection.getSelectionEnd((CharSequence)view), charSequence);
                    bl2 = bl;
                }
            }
            bl = bl2;
        }
        return null;
    }
}

