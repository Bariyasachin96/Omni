/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package c2;

import a2.a;
import android.view.View;
import java.util.Iterator;
import java.util.List;
import o0.m1;
import o0.z1;

public class d
extends m1.b {
    public final View c;
    public int d;
    public int e;
    public final int[] f = new int[2];

    public d(View view) {
        super(0);
        this.c = view;
    }

    @Override
    public void b(m1 m12) {
        this.c.setTranslationY(0.0f);
    }

    @Override
    public void c(m1 m12) {
        this.c.getLocationOnScreen(this.f);
        this.d = this.f[1];
    }

    @Override
    public z1 d(z1 z12, List object) {
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (m1)iterator.next();
            if ((((m1)object).c() & z1.m.b()) == 0) continue;
            float f3 = a2.a.c(this.e, 0, ((m1)object).b());
            this.c.setTranslationY(f3);
            break;
        }
        return z12;
    }

    @Override
    public m1.a e(m1 m12, m1.a a4) {
        this.c.getLocationOnScreen(this.f);
        int n3 = this.f[1];
        this.e = n3 = this.d - n3;
        this.c.setTranslationY((float)n3);
        return a4;
    }
}

