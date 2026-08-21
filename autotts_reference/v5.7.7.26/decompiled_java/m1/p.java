/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package m1;

import android.view.ViewGroup;
import androidx.appcompat.app.s;
import m1.n;

public abstract class p {
    public static p a(ViewGroup viewGroup) {
        s.a(viewGroup.getTag(n.transition_current_scene));
        return null;
    }

    public static void b(ViewGroup viewGroup, p p3) {
        viewGroup.setTag(n.transition_current_scene, (Object)p3);
    }
}

