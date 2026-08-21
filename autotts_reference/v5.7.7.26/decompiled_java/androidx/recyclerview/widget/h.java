/*
 * Decompiled with CFR 0.152.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.a;
import java.util.List;

public class h {
    public final a a;

    public h(a a4) {
        this.a = a4;
    }

    public final int a(List list) {
        boolean bl = false;
        for (int i3 = list.size() - 1; i3 >= 0; --i3) {
            boolean bl2;
            if (((a.b)list.get((int)i3)).a == 8) {
                bl2 = bl;
                if (bl) {
                    return i3;
                }
            } else {
                bl2 = true;
            }
            bl = bl2;
        }
        return -1;
    }

    public void b(List list) {
        int n3;
        while ((n3 = this.a(list)) != -1) {
            this.d(list, n3, n3 + 1);
        }
    }

    public final void c(List list, int n3, a.b b3, int n4, a.b b4) {
        int n5 = b3.d;
        int n6 = b4.b;
        int n7 = n5 < n6 ? -1 : 0;
        int n8 = b3.b;
        int n9 = n7;
        if (n8 < n6) {
            n9 = n7 + 1;
        }
        if (n6 <= n8) {
            b3.b = n8 + b4.d;
        }
        if ((n7 = b4.b) <= n5) {
            b3.d = n5 + b4.d;
        }
        b4.b = n7 + n9;
        list.set(n3, b4);
        list.set(n4, b3);
    }

    public final void d(List list, int n3, int n4) {
        a.b b3 = (a.b)list.get(n3);
        a.b b4 = (a.b)list.get(n4);
        int n5 = b4.a;
        if (n5 != 1) {
            if (n5 != 2) {
                if (n5 != 4) {
                    return;
                }
                this.f(list, n3, b3, n4, b4);
                return;
            }
            this.e(list, n3, b3, n4, b4);
            return;
        }
        this.c(list, n3, b3, n4, b4);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void e(List list, int n3, a.b b3, int n4, a.b b4) {
        int n5;
        int n6 = b3.b;
        int n7 = b3.d;
        boolean bl = false;
        if (n6 < n7) {
            if (b4.b == n6 && b4.d == n7 - n6) {
                n6 = 0;
                bl = true;
            } else {
                n6 = 0;
            }
        } else if (b4.b == n7 + 1 && b4.d == n6 - n7) {
            n6 = 1;
            bl = true;
        } else {
            n6 = 1;
        }
        int n8 = b4.b;
        if (n7 < n8) {
            b4.b = n8 - 1;
        } else {
            n5 = b4.d;
            if (n7 < n8 + n5) {
                b4.d = n5 - 1;
                b3.a = 2;
                b3.d = 1;
                if (b4.d != 0) return;
                list.remove(n4);
                this.a.a(b4);
                return;
            }
        }
        n5 = b3.b;
        n8 = b4.b;
        a.b b5 = null;
        if (n5 <= n8) {
            b4.b = n8 + 1;
        } else {
            n7 = b4.d;
            if (n5 < n8 + n7) {
                b5 = this.a.b(2, n5 + 1, n8 + n7 - n5, null);
                b4.d = b3.b - b4.b;
            }
        }
        if (bl) {
            list.set(n3, b4);
            list.remove(n4);
            this.a.a(b3);
            return;
        }
        if (n6 != 0) {
            if (b5 != null) {
                n6 = b3.b;
                if (n6 > b5.b) {
                    b3.b = n6 - b5.d;
                }
                if ((n6 = b3.d) > b5.b) {
                    b3.d = n6 - b5.d;
                }
            }
            if ((n6 = b3.b) > b4.b) {
                b3.b = n6 - b4.d;
            }
            if ((n6 = b3.d) > b4.b) {
                b3.d = n6 - b4.d;
            }
        } else {
            if (b5 != null) {
                n6 = b3.b;
                if (n6 >= b5.b) {
                    b3.b = n6 - b5.d;
                }
                if ((n6 = b3.d) >= b5.b) {
                    b3.d = n6 - b5.d;
                }
            }
            if ((n6 = b3.b) >= b4.b) {
                b3.b = n6 - b4.d;
            }
            if ((n6 = b3.d) >= b4.b) {
                b3.d = n6 - b4.d;
            }
        }
        list.set(n3, b4);
        if (b3.b != b3.d) {
            list.set(n4, b3);
        } else {
            list.remove(n4);
        }
        if (b5 == null) return;
        list.add(n3, b5);
    }

    /*
     * Unable to fully structure code
     */
    public void f(List var1_1, int var2_2, a.b var3_3, int var4_4, a.b var5_5) {
        block9: {
            var7_6 = var3_3.d;
            var8_7 = var5_5.b;
            var10_8 = null;
            if (var7_6 >= var8_7) break block9;
            var5_5.b = var8_7 - 1;
            ** GOTO lbl-1000
        }
        var6_9 = var5_5.d;
        if (var7_6 < var8_7 + var6_9) {
            var5_5.d = var6_9 - 1;
            var9_10 = this.a.b(4, var3_3.b, 1, var5_5.c);
        } else lbl-1000:
        // 2 sources

        {
            var9_10 = null;
        }
        var6_9 = var3_3.b;
        var8_7 = var5_5.b;
        if (var6_9 <= var8_7) {
            var5_5.b = var8_7 + 1;
        } else {
            var7_6 = var5_5.d;
            if (var6_9 < var8_7 + var7_6) {
                var7_6 = var8_7 + var7_6 - var6_9;
                var10_8 = this.a.b(4, var6_9 + 1, var7_6, var5_5.c);
                var5_5.d -= var7_6;
            }
        }
        var1_1.set(var4_4, var3_3);
        if (var5_5.d > 0) {
            var1_1.set(var2_2, var5_5);
        } else {
            var1_1.remove(var2_2);
            this.a.a(var5_5);
        }
        if (var9_10 != null) {
            var1_1.add(var2_2, var9_10);
        }
        if (var10_8 != null) {
            var1_1.add(var2_2, var10_8);
        }
    }

    public static interface a {
        public void a(a.b var1);

        public a.b b(int var1, int var2, int var3, Object var4);
    }
}

