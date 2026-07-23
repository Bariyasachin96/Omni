/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.TypedArray
 *  android.text.InputFilter
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import c.j;
import z0.f;

public class i {
    public final TextView a;
    public final f b;

    public i(TextView textView) {
        this.a = textView;
        this.b = new f(textView, false);
    }

    public InputFilter[] a(InputFilter[] inputFilterArray) {
        return this.b.a(inputFilterArray);
    }

    public boolean b() {
        return this.b.b();
    }

    public void c(AttributeSet attributeSet, int n3) {
        Throwable throwable2;
        TypedArray typedArray;
        block4: {
            boolean bl;
            block3: {
                typedArray = this.a.getContext().obtainStyledAttributes(attributeSet, j.AppCompatTextView, n3, 0);
                try {
                    n3 = j.AppCompatTextView_emojiCompatEnabled;
                    boolean bl2 = typedArray.hasValue(n3);
                    bl = true;
                    if (!bl2) break block3;
                }
                catch (Throwable throwable2) {
                    break block4;
                }
                bl = typedArray.getBoolean(n3, true);
            }
            typedArray.recycle();
            this.e(bl);
            return;
        }
        typedArray.recycle();
        throw throwable2;
    }

    public void d(boolean bl) {
        this.b.c(bl);
    }

    public void e(boolean bl) {
        this.b.d(bl);
    }

    public TransformationMethod f(TransformationMethod transformationMethod) {
        return this.b.e(transformationMethod);
    }
}

