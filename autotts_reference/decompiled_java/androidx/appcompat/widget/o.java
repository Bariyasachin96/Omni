/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.TextClassificationManager
 *  android.view.textclassifier.TextClassifier
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import n0.h;

public final class o {
    public TextView a;
    public TextClassifier b;

    public o(TextView textView) {
        this.a = (TextView)h.g(textView);
    }

    public TextClassifier a() {
        TextClassifier textClassifier;
        TextClassifier textClassifier2 = textClassifier = this.b;
        if (textClassifier == null) {
            textClassifier2 = androidx.appcompat.widget.o$a.a(this.a);
        }
        return textClassifier2;
    }

    public void b(TextClassifier textClassifier) {
        this.b = textClassifier;
    }

    public static final abstract class a {
        public static TextClassifier a(TextView textView) {
            if ((textView = (TextClassificationManager)textView.getContext().getSystemService(TextClassificationManager.class)) != null) {
                return textView.getTextClassifier();
            }
            return TextClassifier.NO_OP;
        }
    }
}

