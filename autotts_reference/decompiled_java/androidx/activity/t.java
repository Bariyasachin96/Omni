/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.activity;

import android.view.View;
import androidx.activity.q;
import androidx.activity.r;
import o3.k;

public abstract class t {
    public static final void a(View view, q q3) {
        k.e(view, "<this>");
        k.e(q3, "onBackPressedDispatcherOwner");
        view.setTag(r.view_tree_on_back_pressed_dispatcher_owner, (Object)q3);
    }
}

