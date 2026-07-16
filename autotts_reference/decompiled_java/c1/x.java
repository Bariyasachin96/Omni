/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Path
 */
package c1;

import android.graphics.Matrix;
import android.graphics.Path;
import c1.d;
import c1.m;
import c1.q;
import c1.u;
import java.util.List;
import o.e;
import o3.k;

public abstract class x {
    public static final void a(Path path, List list) {
        path.rewind();
        int n3 = list.size();
        boolean bl = true;
        for (int i3 = 0; i3 < n3; ++i3) {
            d d3 = (d)list.get(i3);
            boolean bl2 = bl;
            if (bl) {
                path.moveTo(d3.b(), d3.c());
                bl2 = false;
            }
            path.cubicTo(d3.f(), d3.g(), d3.h(), d3.i(), d3.d(), d3.e());
            bl = bl2;
        }
        path.close();
    }

    public static final Path b(m m3, float f3, Path path) {
        k.e(m3, "<this>");
        k.e(path, "path");
        x.a(path, m3.a(f3));
        return path;
    }

    public static final u c(u u3, Matrix matrix) {
        k.e(u3, "<this>");
        k.e(matrix, "matrix");
        return u3.h(new q(new float[2], matrix){
            public final float[] a;
            public final Matrix b;
            {
                this.a = fArray;
                this.b = matrix;
            }

            @Override
            public final long a(float f3, float f4) {
                float[] fArray = this.a;
                fArray[0] = f3;
                fArray[1] = f4;
                this.b.mapPoints(fArray);
                fArray = this.a;
                return e.b(fArray[0], fArray[1]);
            }
        });
    }
}

