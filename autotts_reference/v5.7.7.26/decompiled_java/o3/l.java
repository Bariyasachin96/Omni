/*
 * Decompiled with CFR 0.152.
 */
package o3;

import java.io.Serializable;
import o3.h;
import o3.k;
import o3.n;

public abstract class l
implements h,
Serializable {
    public final int c;

    public l(int n3) {
        this.c = n3;
    }

    @Override
    public int c() {
        return this.c;
    }

    public String toString() {
        String string = n.e(this);
        k.d(string, "renderLambdaToString(this)");
        return string;
    }
}

