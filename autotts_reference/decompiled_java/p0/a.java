/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.text.style.ClickableSpan
 *  android.view.View
 */
package p0;

import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import p0.s;

public final class a
extends ClickableSpan {
    public final int c;
    public final s d;
    public final int e;

    public a(int n3, s s3, int n4) {
        this.c = n3;
        this.d = s3;
        this.e = n4;
    }

    public void onClick(View view) {
        view = new Bundle();
        view.putInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", this.c);
        this.d.X(this.e, (Bundle)view);
    }
}

