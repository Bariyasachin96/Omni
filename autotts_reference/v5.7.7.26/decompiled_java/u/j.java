/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import u.e;
import u.f;
import u.i;
import v.o;

public class j
extends e
implements i {
    public e[] V0 = new e[4];
    public int W0 = 0;

    @Override
    public void a(e e3) {
        if (e3 != this && e3 != null) {
            int n3 = this.W0;
            e[] eArray = this.V0;
            if (n3 + 1 > eArray.length) {
                this.V0 = Arrays.copyOf(eArray, eArray.length * 2);
            }
            eArray = this.V0;
            n3 = this.W0;
            eArray[n3] = e3;
            this.W0 = n3 + 1;
        }
    }

    @Override
    public void b(f f3) {
    }

    @Override
    public void c() {
        this.W0 = 0;
        Arrays.fill(this.V0, null);
    }

    @Override
    public void n(e e3, HashMap hashMap) {
        super.n(e3, hashMap);
        e3 = (j)e3;
        this.W0 = 0;
        int n3 = ((j)e3).W0;
        for (int i3 = 0; i3 < n3; ++i3) {
            this.a((e)hashMap.get(((j)e3).V0[i3]));
        }
    }

    public void w1(ArrayList arrayList, int n3, o o3) {
        int n4 = 0;
        int n5 = 0;
        while (true) {
            if (n5 >= this.W0) break;
            o3.a(this.V0[n5]);
            ++n5;
        }
        for (int i3 = n4; i3 < this.W0; ++i3) {
            v.i.a(this.V0[i3], n3, arrayList, o3);
        }
    }

    public int x1(int n3) {
        for (int i3 = 0; i3 < this.W0; ++i3) {
            int n4;
            e e3 = this.V0[i3];
            if (n3 == 0 && (n4 = e3.S0) != -1) {
                return n4;
            }
            if (n3 != 1 || (n4 = e3.T0) == -1) continue;
            return n4;
        }
        return -1;
    }
}

