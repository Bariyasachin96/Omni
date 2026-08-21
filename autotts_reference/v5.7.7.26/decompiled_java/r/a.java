/*
 * Decompiled with CFR 0.152.
 */
package r;

import java.util.Arrays;
import r.b;
import r.c;
import r.i;

public class a
implements b.a {
    public static float l = 0.001f;
    public int a = 0;
    public final b b;
    public final c c;
    public int d = 8;
    public i e = null;
    public int[] f = new int[8];
    public int[] g = new int[8];
    public float[] h = new float[8];
    public int i = -1;
    public int j = -1;
    public boolean k = false;

    public a(b b3, c c3) {
        this.b = b3;
        this.c = c3;
    }

    @Override
    public float a(int n3) {
        int n4 = this.i;
        for (int i3 = 0; n4 != -1 && i3 < this.a; ++i3) {
            if (i3 == n3) {
                return this.h[n4];
            }
            n4 = this.g[n4];
        }
        return 0.0f;
    }

    @Override
    public boolean b(i i3) {
        int n3 = this.i;
        if (n3 == -1) {
            return false;
        }
        for (int i4 = 0; n3 != -1 && i4 < this.a; ++i4) {
            if (this.f[n3] == i3.e) {
                return true;
            }
            n3 = this.g[n3];
        }
        return false;
    }

    @Override
    public final float c(i i3) {
        int n3 = this.i;
        for (int i4 = 0; n3 != -1 && i4 < this.a; ++i4) {
            if (this.f[n3] == i3.e) {
                return this.h[n3];
            }
            n3 = this.g[n3];
        }
        return 0.0f;
    }

    @Override
    public final void clear() {
        int n3 = this.i;
        for (int i3 = 0; n3 != -1 && i3 < this.a; ++i3) {
            i i4 = this.c.d[this.f[n3]];
            if (i4 != null) {
                i4.d(this.b);
            }
            n3 = this.g[n3];
        }
        this.i = -1;
        this.j = -1;
        this.k = false;
        this.a = 0;
    }

    @Override
    public final void d(i object, float f3) {
        if (f3 == 0.0f) {
            this.g((i)object, true);
            return;
        }
        int n3 = this.i;
        if (n3 == -1) {
            this.i = 0;
            this.h[0] = f3;
            this.f[0] = ((i)object).e;
            this.g[0] = -1;
            ++((i)object).o;
            ((i)object).a(this.b);
            ++this.a;
            if (!this.k) {
                this.j = n3 = this.j + 1;
                object = this.f;
                if (n3 >= ((Object)object).length) {
                    this.k = true;
                    this.j = ((Object)object).length - 1;
                    return;
                }
            }
        } else {
            int[] nArray;
            int n4;
            int n5;
            int n6 = -1;
            for (n5 = 0; n3 != -1 && n5 < this.a; ++n5) {
                n4 = this.f[n3];
                int n7 = ((i)object).e;
                if (n4 == n7) {
                    this.h[n3] = f3;
                    return;
                }
                if (n4 < n7) {
                    n6 = n3;
                }
                n3 = this.g[n3];
            }
            n3 = this.j;
            if (this.k) {
                nArray = this.f;
                if (nArray[n3] != -1) {
                    n3 = nArray.length;
                }
            } else {
                ++n3;
            }
            nArray = this.f;
            n5 = n3;
            if (n3 >= nArray.length) {
                n5 = n3;
                if (this.a < nArray.length) {
                    n4 = 0;
                    while (true) {
                        nArray = this.f;
                        n5 = n3;
                        if (n4 >= nArray.length) break;
                        if (nArray[n4] == -1) {
                            n5 = n4;
                            break;
                        }
                        ++n4;
                    }
                }
            }
            nArray = this.f;
            n3 = n5;
            if (n5 >= nArray.length) {
                n3 = nArray.length;
                this.d = n5 = this.d * 2;
                this.k = false;
                this.j = n3 - 1;
                this.h = Arrays.copyOf(this.h, n5);
                this.f = Arrays.copyOf(this.f, this.d);
                this.g = Arrays.copyOf(this.g, this.d);
            }
            this.f[n3] = ((i)object).e;
            this.h[n3] = f3;
            if (n6 != -1) {
                nArray = this.g;
                nArray[n3] = nArray[n6];
                nArray[n6] = n3;
            } else {
                this.g[n3] = this.i;
                this.i = n3;
            }
            ++((i)object).o;
            ((i)object).a(this.b);
            this.a = n3 = this.a + 1;
            if (!this.k) {
                ++this.j;
            }
            if (n3 >= ((Object)(object = (Object)this.f)).length) {
                this.k = true;
            }
            if (this.j >= ((Object)object).length) {
                this.k = true;
                this.j = ((Object)object).length - 1;
            }
        }
    }

    @Override
    public float e(b object, boolean bl) {
        float f3 = this.c(((b)object).a);
        this.g(((b)object).a, bl);
        b.a a4 = ((b)object).e;
        int n3 = a4.f();
        for (int i3 = 0; i3 < n3; ++i3) {
            object = a4.h(i3);
            this.i((i)object, a4.c((i)object) * f3, bl);
        }
        return f3;
    }

    @Override
    public int f() {
        return this.a;
    }

    @Override
    public final float g(i i3, boolean bl) {
        int n3;
        if (this.e == i3) {
            this.e = null;
        }
        if ((n3 = this.i) == -1) {
            return 0.0f;
        }
        int n4 = -1;
        for (int i4 = 0; n3 != -1 && i4 < this.a; ++i4) {
            if (this.f[n3] == i3.e) {
                if (n3 == this.i) {
                    this.i = this.g[n3];
                } else {
                    int[] nArray = this.g;
                    nArray[n4] = nArray[n3];
                }
                if (bl) {
                    i3.d(this.b);
                }
                --i3.o;
                --this.a;
                this.f[n3] = -1;
                if (this.k) {
                    this.j = n3;
                }
                return this.h[n3];
            }
            int n5 = this.g[n3];
            n4 = n3;
            n3 = n5;
        }
        return 0.0f;
    }

    @Override
    public i h(int n3) {
        int n4 = this.i;
        for (int i3 = 0; n4 != -1 && i3 < this.a; ++i3) {
            if (i3 == n3) {
                return this.c.d[this.f[n4]];
            }
            n4 = this.g[n4];
        }
        return null;
    }

    @Override
    public void i(i object, float f3, boolean bl) {
        block27: {
            float f4 = l;
            if (!(f3 > -f4) || !(f3 < f4)) {
                int n3 = this.i;
                if (n3 == -1) {
                    this.i = 0;
                    this.h[0] = f3;
                    this.f[0] = ((i)object).e;
                    this.g[0] = -1;
                    ++((i)object).o;
                    ((i)object).a(this.b);
                    ++this.a;
                    if (!this.k) {
                        this.j = n3 = this.j + 1;
                        object = this.f;
                        if (n3 >= ((Object)object).length) {
                            this.k = true;
                            this.j = ((Object)object).length - 1;
                            return;
                        }
                    }
                } else {
                    int[] nArray;
                    int n4;
                    int n5;
                    int n6 = -1;
                    for (n5 = 0; n3 != -1 && n5 < this.a; ++n5) {
                        int n7 = this.f[n3];
                        n4 = ((i)object).e;
                        if (n7 == n4) {
                            Object[] objectArray = this.h;
                            f4 = objectArray[n3] + f3;
                            float f5 = l;
                            f3 = f4;
                            if (f4 > -f5) {
                                f3 = f4;
                                if (f4 < f5) {
                                    f3 = 0.0f;
                                }
                            }
                            objectArray[n3] = f3;
                            if (f3 == 0.0f) {
                                if (n3 == this.i) {
                                    this.i = this.g[n3];
                                } else {
                                    objectArray = this.g;
                                    objectArray[n6] = objectArray[n3];
                                }
                                if (bl) {
                                    ((i)object).d(this.b);
                                }
                                if (this.k) {
                                    this.j = n3;
                                }
                                --((i)object).o;
                                --this.a;
                                return;
                            }
                            break block27;
                        }
                        if (n7 < n4) {
                            n6 = n3;
                        }
                        n3 = this.g[n3];
                    }
                    n3 = this.j;
                    if (this.k) {
                        nArray = this.f;
                        if (nArray[n3] != -1) {
                            n3 = nArray.length;
                        }
                    } else {
                        ++n3;
                    }
                    nArray = this.f;
                    n5 = n3;
                    if (n3 >= nArray.length) {
                        n5 = n3;
                        if (this.a < nArray.length) {
                            n4 = 0;
                            while (true) {
                                nArray = this.f;
                                n5 = n3;
                                if (n4 >= nArray.length) break;
                                if (nArray[n4] == -1) {
                                    n5 = n4;
                                    break;
                                }
                                ++n4;
                            }
                        }
                    }
                    nArray = this.f;
                    n3 = n5;
                    if (n5 >= nArray.length) {
                        n3 = nArray.length;
                        this.d = n5 = this.d * 2;
                        this.k = false;
                        this.j = n3 - 1;
                        this.h = Arrays.copyOf(this.h, n5);
                        this.f = Arrays.copyOf(this.f, this.d);
                        this.g = Arrays.copyOf(this.g, this.d);
                    }
                    this.f[n3] = ((i)object).e;
                    this.h[n3] = f3;
                    if (n6 != -1) {
                        nArray = this.g;
                        nArray[n3] = nArray[n6];
                        nArray[n6] = n3;
                    } else {
                        this.g[n3] = this.i;
                        this.i = n3;
                    }
                    ++((i)object).o;
                    ((i)object).a(this.b);
                    ++this.a;
                    if (!this.k) {
                        ++this.j;
                    }
                    if ((n3 = this.j) >= ((Object)(object = (Object)this.f)).length) {
                        this.k = true;
                        this.j = ((Object)object).length - 1;
                    }
                }
            }
        }
    }

    @Override
    public void j(float f3) {
        int n3 = this.i;
        for (int i3 = 0; n3 != -1 && i3 < this.a; ++i3) {
            float[] fArray = this.h;
            fArray[n3] = fArray[n3] / f3;
            n3 = this.g[n3];
        }
    }

    @Override
    public void k() {
        int n3 = this.i;
        for (int i3 = 0; n3 != -1 && i3 < this.a; ++i3) {
            float[] fArray = this.h;
            fArray[n3] = fArray[n3] * -1.0f;
            n3 = this.g[n3];
        }
    }

    public String toString() {
        int n3 = this.i;
        String string = "";
        for (int i3 = 0; n3 != -1 && i3 < this.a; ++i3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" -> ");
            string = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(this.h[n3]);
            stringBuilder.append(" : ");
            string = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(this.c.d[this.f[n3]]);
            string = stringBuilder.toString();
            n3 = this.g[n3];
        }
        return string;
    }
}

