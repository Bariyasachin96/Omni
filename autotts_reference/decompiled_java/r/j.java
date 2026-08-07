/*
 * Decompiled with CFR 0.152.
 */
package r;

import java.util.Arrays;
import r.b;
import r.c;
import r.i;

public class j
implements b.a {
    public static float n = 0.001f;
    public final int a;
    public int b = 16;
    public int c = 16;
    public int[] d = new int[16];
    public int[] e = new int[16];
    public int[] f = new int[16];
    public float[] g = new float[16];
    public int[] h = new int[16];
    public int[] i = new int[16];
    public int j = 0;
    public int k = -1;
    public final b l;
    public final c m;

    public j(b b3, c c3) {
        this.a = -1;
        this.l = b3;
        this.m = c3;
        this.clear();
    }

    @Override
    public float a(int n3) {
        int n4 = this.j;
        int n5 = this.k;
        for (int i3 = 0; i3 < n4; ++i3) {
            if (i3 == n3) {
                return this.g[n5];
            }
            if ((n5 = this.i[n5]) == -1) break;
        }
        return 0.0f;
    }

    @Override
    public boolean b(i i3) {
        return this.p(i3) != -1;
    }

    @Override
    public float c(i i3) {
        int n3 = this.p(i3);
        if (n3 != -1) {
            return this.g[n3];
        }
        return 0.0f;
    }

    @Override
    public void clear() {
        int n3;
        int n4 = this.j;
        for (n3 = 0; n3 < n4; ++n3) {
            i i3 = this.h(n3);
            if (i3 == null) continue;
            i3.d(this.l);
        }
        for (n3 = 0; n3 < this.b; ++n3) {
            this.f[n3] = -1;
            this.e[n3] = -1;
        }
        for (n3 = 0; n3 < this.c; ++n3) {
            this.d[n3] = -1;
        }
        this.j = 0;
        this.k = -1;
    }

    @Override
    public void d(i i3, float f3) {
        int n3;
        float f4 = n;
        if (f3 > -f4 && f3 < f4) {
            this.g(i3, true);
            return;
        }
        int n4 = this.j;
        int n5 = 0;
        if (n4 == 0) {
            this.m(0, i3, f3);
            this.l(i3, 0);
            this.k = 0;
            return;
        }
        n4 = this.p(i3);
        if (n4 != -1) {
            this.g[n4] = f3;
            return;
        }
        if (this.j + 1 >= this.b) {
            this.o();
        }
        int n6 = this.j;
        n4 = this.k;
        int n7 = -1;
        while (true) {
            n3 = n7;
            if (n5 >= n6) break;
            int n8 = this.f[n4];
            n3 = i3.e;
            if (n8 == n3) {
                this.g[n4] = f3;
                return;
            }
            if (n8 < n3) {
                n7 = n4;
            }
            if ((n4 = this.i[n4]) == -1) {
                n3 = n7;
                break;
            }
            ++n5;
        }
        this.q(n3, i3, f3);
    }

    @Override
    public float e(b object, boolean bl) {
        float f3 = this.c(((b)object).a);
        this.g(((b)object).a, bl);
        object = (j)((b)object).e;
        int n3 = ((j)object).f();
        int n4 = 0;
        int n5 = 0;
        while (n4 < n3) {
            int n6 = ((j)object).f[n5];
            int n7 = n4;
            if (n6 != -1) {
                float f4 = ((j)object).g[n5];
                this.i(this.m.d[n6], f4 * f3, bl);
                n7 = n4 + 1;
            }
            ++n5;
            n4 = n7;
        }
        return f3;
    }

    @Override
    public int f() {
        return this.j;
    }

    @Override
    public float g(i i3, boolean bl) {
        int n3 = this.p(i3);
        if (n3 == -1) {
            return 0.0f;
        }
        this.r(i3);
        float f3 = this.g[n3];
        if (this.k == n3) {
            this.k = this.i[n3];
        }
        this.f[n3] = -1;
        int[] nArray = this.h;
        int n4 = nArray[n3];
        if (n4 != -1) {
            int[] nArray2 = this.i;
            nArray2[n4] = nArray2[n3];
        }
        if ((n4 = this.i[n3]) != -1) {
            nArray[n4] = nArray[n3];
        }
        --this.j;
        --i3.o;
        if (bl) {
            i3.d(this.l);
        }
        return f3;
    }

    @Override
    public i h(int n3) {
        int n4 = this.j;
        if (n4 == 0) {
            return null;
        }
        int n5 = this.k;
        for (int i3 = 0; i3 < n4; ++i3) {
            if (i3 == n3 && n5 != -1) {
                return this.m.d[this.f[n5]];
            }
            if ((n5 = this.i[n5]) == -1) break;
        }
        return null;
    }

    @Override
    public void i(i i3, float f3, boolean bl) {
        float f4 = n;
        if (!(f3 > -f4) || !(f3 < f4)) {
            int n3 = this.p(i3);
            if (n3 == -1) {
                this.d(i3, f3);
                return;
            }
            float[] fArray = this.g;
            fArray[n3] = f3 = fArray[n3] + f3;
            f4 = n;
            if (f3 > -f4 && f3 < f4) {
                fArray[n3] = 0.0f;
                this.g(i3, bl);
            }
        }
    }

    @Override
    public void j(float f3) {
        int n3 = this.j;
        int n4 = this.k;
        for (int i3 = 0; i3 < n3; ++i3) {
            float[] fArray = this.g;
            fArray[n4] = fArray[n4] / f3;
            if ((n4 = this.i[n4]) == -1) break;
        }
    }

    @Override
    public void k() {
        int n3 = this.j;
        int n4 = this.k;
        for (int i3 = 0; i3 < n3; ++i3) {
            float[] fArray = this.g;
            fArray[n4] = fArray[n4] * -1.0f;
            if ((n4 = this.i[n4]) == -1) break;
        }
    }

    public final void l(i object, int n3) {
        Object object2;
        int n4 = ((i)object).e % this.c;
        object = this.d;
        Object object3 = object2 = object[n4];
        if (object2 == -1) {
            object[n4] = n3;
        } else {
            while ((object2 = (object = (Object)this.e)[object3]) != -1) {
                object3 = object2;
            }
            object[object3] = n3;
        }
        this.e[n3] = -1;
    }

    public final void m(int n3, i i3, float f3) {
        this.f[n3] = i3.e;
        this.g[n3] = f3;
        this.h[n3] = -1;
        this.i[n3] = -1;
        i3.a(this.l);
        ++i3.o;
        ++this.j;
    }

    public final int n() {
        for (int i3 = 0; i3 < this.b; ++i3) {
            if (this.f[i3] != -1) continue;
            return i3;
        }
        return -1;
    }

    public final void o() {
        int n3 = this.b * 2;
        this.f = Arrays.copyOf(this.f, n3);
        this.g = Arrays.copyOf(this.g, n3);
        this.h = Arrays.copyOf(this.h, n3);
        this.i = Arrays.copyOf(this.i, n3);
        this.e = Arrays.copyOf(this.e, n3);
        for (int i3 = this.b; i3 < n3; ++i3) {
            this.f[i3] = -1;
            this.e[i3] = -1;
        }
        this.b = n3;
    }

    public int p(i i3) {
        if (this.j != 0 && i3 != null) {
            int n3 = i3.e;
            int n4 = this.c;
            int n5 = this.d[n3 % n4];
            if (n5 == -1) {
                return -1;
            }
            n4 = n5;
            if (this.f[n5] == n3) {
                return n5;
            }
            while ((n4 = this.e[n4]) != -1 && this.f[n4] != n3) {
            }
            if (n4 == -1) {
                return -1;
            }
            if (this.f[n4] == n3) {
                return n4;
            }
        }
        return -1;
    }

    public final void q(int n3, i i3, float f3) {
        int n4 = this.n();
        this.m(n4, i3, f3);
        if (n3 != -1) {
            this.h[n4] = n3;
            int[] nArray = this.i;
            nArray[n4] = nArray[n3];
            nArray[n3] = n4;
        } else {
            this.h[n4] = -1;
            if (this.j > 0) {
                this.i[n4] = this.k;
                this.k = n4;
            } else {
                this.i[n4] = -1;
            }
        }
        n3 = this.i[n4];
        if (n3 != -1) {
            this.h[n3] = n4;
        }
        this.l(i3, n4);
    }

    public final void r(i object) {
        int[] nArray = this.d;
        int n3 = ((i)object).e;
        int n4 = n3 % this.c;
        Object object2 = nArray[n4];
        if (object2 != -1) {
            int n5 = object2;
            if (this.f[object2] == n3) {
                object = this.e;
                nArray[n4] = (int)object[object2];
                object[object2] = -1;
                return;
            }
            while ((object2 = (Object)(object = (Object)this.e)[n5]) != -1 && this.f[object2] != n3) {
                n5 = object2;
            }
            if (object2 != -1 && this.f[object2] == n3) {
                object[n5] = object[object2];
                object[object2] = -1;
            }
        }
    }

    public String toString() {
        Comparable comparable;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.hashCode());
        charSequence.append(" { ");
        charSequence = charSequence.toString();
        int n3 = this.j;
        for (int i3 = 0; i3 < n3; ++i3) {
            comparable = this.h(i3);
            if (comparable == null) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(comparable);
            stringBuilder.append(" = ");
            stringBuilder.append(this.a(i3));
            stringBuilder.append(" ");
            charSequence = stringBuilder.toString();
            int n4 = this.p((i)comparable);
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append((String)charSequence);
            ((StringBuilder)comparable).append("[p: ");
            charSequence = ((StringBuilder)comparable).toString();
            if (this.h[n4] != -1) {
                comparable = new StringBuilder();
                ((StringBuilder)comparable).append((String)charSequence);
                ((StringBuilder)comparable).append(this.m.d[this.f[this.h[n4]]]);
                charSequence = ((StringBuilder)comparable).toString();
            } else {
                comparable = new StringBuilder();
                ((StringBuilder)comparable).append((String)charSequence);
                ((StringBuilder)comparable).append("none");
                charSequence = ((StringBuilder)comparable).toString();
            }
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append((String)charSequence);
            ((StringBuilder)comparable).append(", n: ");
            charSequence = ((StringBuilder)comparable).toString();
            if (this.i[n4] != -1) {
                comparable = new StringBuilder();
                ((StringBuilder)comparable).append((String)charSequence);
                ((StringBuilder)comparable).append(this.m.d[this.f[this.i[n4]]]);
                charSequence = ((StringBuilder)comparable).toString();
            } else {
                comparable = new StringBuilder();
                ((StringBuilder)comparable).append((String)charSequence);
                ((StringBuilder)comparable).append("none");
                charSequence = ((StringBuilder)comparable).toString();
            }
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append((String)charSequence);
            ((StringBuilder)comparable).append("]");
            charSequence = ((StringBuilder)comparable).toString();
        }
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append((String)charSequence);
        ((StringBuilder)comparable).append(" }");
        return ((StringBuilder)comparable).toString();
    }
}

