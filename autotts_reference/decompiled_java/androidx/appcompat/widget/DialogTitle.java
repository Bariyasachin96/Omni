/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Layout
 *  android.util.AttributeSet
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import c.j;

public class DialogTitle
extends AppCompatTextView {
    public DialogTitle(Context context) {
        super(context);
    }

    public DialogTitle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DialogTitle(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    @Override
    public void onMeasure(int n3, int n4) {
        int n5;
        super.onMeasure(n3, n4);
        Layout layout = this.getLayout();
        if (layout != null && (n5 = layout.getLineCount()) > 0 && layout.getEllipsisCount(n5 - 1) > 0) {
            this.setSingleLine(false);
            this.setMaxLines(2);
            layout = this.getContext().obtainStyledAttributes(null, j.TextAppearance, 0x1010041, 16973892);
            n5 = layout.getDimensionPixelSize(j.TextAppearance_android_textSize, 0);
            if (n5 != 0) {
                this.setTextSize(0, n5);
            }
            layout.recycle();
            super.onMeasure(n3, n4);
        }
    }
}

