/*
 * Decompiled with CFR 0.152.
 */
package r;

import java.util.Arrays;
import java.util.Comparator;
import r.c;
import r.d;
import r.i;

public class h
extends r.b {
    public int g = 128;
    public i[] h = new i[128];
    public i[] i = new i[128];
    public int j = 0;
    public b k = new b(this, this);
    public c l;

    public h(c c3) {
        super(c3);
        this.l = c3;
    }

    @Override
    public void B(d object, r.b b3, boolean bl) {
        i i3 = b3.a;
        if (i3 == null) {
            return;
        }
        object = b3.e;
        int n3 = object.f();
        for (int i4 = 0; i4 < n3; ++i4) {
            i i5 = object.h(i4);
            float f3 = object.a(i4);
            this.k.b(i5);
            if (this.k.a(i3, f3)) {
                this.F(i5);
            }
            this.b += b3.b * f3;
        }
        this.G(i3);
    }

    public final void F(i i3) {
        int n3 = this.j;
        i[] iArray = this.h;
        if (n3 + 1 > iArray.length) {
            this.h = iArray = Arrays.copyOf(iArray, iArray.length * 2);
            this.i = Arrays.copyOf(iArray, iArray.length * 2);
        }
        iArray = this.h;
        int n4 = this.j;
        iArray[n4] = i3;
        this.j = n3 = n4 + 1;
        if (n3 > 1 && iArray[n4].e > i3.e) {
            int n5;
            n4 = 0;
            for (n3 = 0; n3 < (n5 = this.j); ++n3) {
                this.i[n3] = this.h[n3];
            }
            Arrays.sort(this.i, 0, n5, new Comparator(this){
                public final h c;
                {
                    this.c = h3;
                }

                public int a(i i3, i i4) {
                    return i3.e - i4.e;
                }
            });
            for (n3 = n4; n3 < this.j; ++n3) {
                this.h[n3] = this.i[n3];
            }
        }
        i3.c = true;
        i3.a(this);
    }

    public final void G(i i3) {
        for (int i4 = 0; i4 < this.j; ++i4) {
            int n3;
            if (this.h[i4] != i3) continue;
            while (i4 < (n3 = this.j) - 1) {
                i[] iArray = this.h;
                n3 = i4 + 1;
                iArray[i4] = iArray[n3];
                i4 = n3;
            }
            this.j = n3 - 1;
            i3.c = false;
            return;
        }
    }

    @Override
    public void b(i i3) {
        this.k.b(i3);
        this.k.e();
        i3.k[i3.g] = 1.0f;
        this.F(i3);
    }

    @Override
    public i c(d object, boolean[] blArray) {
        int n3 = -1;
        for (int i3 = 0; i3 < this.j; ++i3) {
            int n4;
            block6: {
                block8: {
                    block7: {
                        block5: {
                            object = this.h[i3];
                            if (!blArray[((i)object).e]) break block5;
                            n4 = n3;
                            break block6;
                        }
                        this.k.b((i)object);
                        if (n3 != -1) break block7;
                        n4 = n3;
                        if (!this.k.c()) break block6;
                        break block8;
                    }
                    n4 = n3;
                    if (!this.k.d(this.h[n3])) break block6;
                }
                n4 = i3;
            }
            n3 = n4;
        }
        if (n3 == -1) {
            return null;
        }
        return this.h[n3];
    }

    @Override
    public void clear() {
        this.j = 0;
        this.b = 0.0f;
    }

    @Override
    public boolean isEmpty() {
        return this.j == 0;
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("");
        charSequence.append(" goal -> (");
        charSequence.append(this.b);
        charSequence.append(") : ");
        charSequence = charSequence.toString();
        for (int i3 = 0; i3 < this.j; ++i3) {
            Comparable comparable = this.h[i3];
            this.k.b((i)comparable);
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append((String)charSequence);
            ((StringBuilder)comparable).append(this.k);
            ((StringBuilder)comparable).append(" ");
            charSequence = ((StringBuilder)comparable).toString();
        }
        return charSequence;
    }

    public class b {
        public i a;
        public h b;
        public final h c;

        public b(h h3, h h4) {
            this.c = h3;
            this.b = h4;
        }

        public boolean a(i i3, float f3) {
            int n3;
            boolean bl = this.a.c;
            boolean bl2 = true;
            if (bl) {
                for (n3 = 0; n3 < 9; ++n3) {
                    float f4;
                    float[] fArray = this.a.k;
                    fArray[n3] = f4 = fArray[n3] + i3.k[n3] * f3;
                    if (Math.abs(f4) < 1.0E-4f) {
                        this.a.k[n3] = 0.0f;
                        continue;
                    }
                    bl2 = false;
                }
                if (bl2) {
                    this.c.G(this.a);
                }
                return false;
            }
            for (n3 = 0; n3 < 9; ++n3) {
                float f5 = i3.k[n3];
                if (f5 != 0.0f) {
                    float f6;
                    f5 = f6 = f5 * f3;
                    if (Math.abs(f6) < 1.0E-4f) {
                        f5 = 0.0f;
                    }
                    this.a.k[n3] = f5;
                    continue;
                }
                this.a.k[n3] = 0.0f;
            }
            return true;
        }

        public void b(i i3) {
            this.a = i3;
        }

        public final boolean c() {
            for (int i3 = 8; i3 >= 0; --i3) {
                float f3 = this.a.k[i3];
                if (f3 > 0.0f) {
                    return false;
                }
                if (!(f3 < 0.0f)) continue;
                return true;
            }
            return false;
        }

        public final boolean d(i i3) {
            for (int i4 = 8; i4 >= 0; --i4) {
                float f3 = this.a.k[i4];
                float f4 = i3.k[i4];
                if (f3 == f4) {
                    continue;
                }
                if (!(f3 < f4)) break;
                return true;
            }
            return false;
        }

        public void e() {
            Arrays.fill(this.a.k, 0.0f);
        }

        public String toString() {
            CharSequence charSequence;
            i i3 = this.a;
            CharSequence charSequence2 = charSequence = "[ ";
            if (i3 != null) {
                int n3 = 0;
                while (true) {
                    charSequence2 = charSequence;
                    if (n3 >= 9) break;
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append(this.a.k[n3]);
                    ((StringBuilder)charSequence2).append(" ");
                    charSequence = ((StringBuilder)charSequence2).toString();
                    ++n3;
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("] ");
            ((StringBuilder)charSequence).append(this.a);
            return ((StringBuilder)charSequence).toString();
        }
    }
}

