/*
 * Decompiled with CFR 0.152.
 */
package i3;

import g3.a;
import i3.h;
import o3.k;
import o3.n;

public abstract class i
extends h
implements o3.h {
    public final int d;

    public i(int n3, a a4) {
        super(a4);
        this.d = n3;
    }

    @Override
    public int c() {
        return this.d;
    }

    @Override
    public String toString() {
        if (this.h() == null) {
            String string = n.d(this);
            k.d(string, "renderLambdaToString(this)");
            return string;
        }
        return super.toString();
    }
}

