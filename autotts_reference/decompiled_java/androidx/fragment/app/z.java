/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.fragment.app;

import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.a0;
import androidx.fragment.app.b0;
import java.util.ArrayList;
import o.a;

public abstract class z {
    public static final b0 a = new a0();
    public static final b0 b = z.b();

    public static void a(Fragment fragment, Fragment fragment2, boolean bl, a a4, boolean bl2) {
        if (bl) {
            fragment2.u();
            return;
        }
        fragment.u();
    }

    public static b0 b() {
        try {
            b0 b02 = (b0)androidx.transition.a.class.getDeclaredConstructor(null).newInstance(null);
            return b02;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static void c(a a4, a a5) {
        for (int i3 = a4.size() - 1; i3 >= 0; --i3) {
            if (a5.containsKey((String)a4.j(i3))) continue;
            a4.h(i3);
        }
    }

    public static void d(ArrayList arrayList, int n3) {
        if (arrayList != null) {
            for (int i3 = arrayList.size() - 1; i3 >= 0; --i3) {
                ((View)arrayList.get(i3)).setVisibility(n3);
            }
        }
    }
}

