/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.activity;

import android.view.View;
import androidx.activity.n;
import androidx.activity.r;
import o3.k;

public abstract class s {
    public static final void a(View view, n n3) {
        k.e(view, "<this>");
        k.e(n3, "fullyDrawnReporterOwner");
        view.setTag(r.report_drawn, (Object)n3);
    }
}

