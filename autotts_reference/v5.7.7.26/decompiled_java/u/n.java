/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.ArrayList;
import r.c;
import u.e;

public abstract class n
extends e {
    public ArrayList V0 = new ArrayList();

    public void a(e e3) {
        this.V0.add(e3);
        if (e3.M() != null) {
            ((n)e3.M()).y1(e3);
        }
        e3.h1(this);
    }

    @Override
    public void v0() {
        this.V0.clear();
        super.v0();
    }

    public ArrayList w1() {
        return this.V0;
    }

    public abstract void x1();

    public void y1(e e3) {
        this.V0.remove(e3);
        e3.v0();
    }

    @Override
    public void z0(c c3) {
        super.z0(c3);
        int n3 = this.V0.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((e)this.V0.get(i3)).z0(c3);
        }
    }

    public void z1() {
        this.V0.clear();
    }
}

