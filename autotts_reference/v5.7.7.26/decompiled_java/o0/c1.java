/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package o0;

import android.view.ViewGroup;

public abstract class c1 {
    public static boolean a(ViewGroup viewGroup) {
        return a.b(viewGroup);
    }

    public static abstract class a {
        public static int a(ViewGroup viewGroup) {
            return viewGroup.getNestedScrollAxes();
        }

        public static boolean b(ViewGroup viewGroup) {
            return viewGroup.isTransitionGroup();
        }

        public static void c(ViewGroup viewGroup, boolean bl) {
            viewGroup.setTransitionGroup(bl);
        }
    }
}

