/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.view.View;

public class o {
    public final b a;
    public a b;

    public o(b b3) {
        this.a = b3;
        this.b = new a();
    }

    public View a(int n3, int n4, int n5, int n6) {
        int n7 = this.a.d();
        int n8 = this.a.b();
        int n9 = n4 > n3 ? 1 : -1;
        View view = null;
        while (n3 != n4) {
            View view2 = this.a.a(n3);
            int n10 = this.a.c(view2);
            int n11 = this.a.e(view2);
            this.b.e(n7, n8, n10, n11);
            if (n5 != 0) {
                this.b.d();
                this.b.a(n5);
                if (this.b.b()) {
                    return view2;
                }
            }
            View view3 = view;
            if (n6 != 0) {
                this.b.d();
                this.b.a(n6);
                view3 = view;
                if (this.b.b()) {
                    view3 = view2;
                }
            }
            n3 += n9;
            view = view3;
        }
        return view;
    }

    public boolean b(View view, int n3) {
        this.b.e(this.a.d(), this.a.b(), this.a.c(view), this.a.e(view));
        if (n3 != 0) {
            this.b.d();
            this.b.a(n3);
            return this.b.b();
        }
        return false;
    }

    public static class a {
        public int a = 0;
        public int b;
        public int c;
        public int d;
        public int e;

        public void a(int n3) {
            this.a = n3 | this.a;
        }

        public boolean b() {
            int n3 = this.a;
            if ((n3 & 7) != 0 && (n3 & this.c(this.d, this.b)) == 0) {
                return false;
            }
            n3 = this.a;
            if ((n3 & 0x70) != 0 && (n3 & this.c(this.d, this.c) << 4) == 0) {
                return false;
            }
            n3 = this.a;
            if ((n3 & 0x700) != 0 && (n3 & this.c(this.e, this.b) << 8) == 0) {
                return false;
            }
            n3 = this.a;
            return (n3 & 0x7000) == 0 || (n3 & this.c(this.e, this.c) << 12) != 0;
        }

        public int c(int n3, int n4) {
            if (n3 > n4) {
                return 1;
            }
            if (n3 == n4) {
                return 2;
            }
            return 4;
        }

        public void d() {
            this.a = 0;
        }

        public void e(int n3, int n4, int n5, int n6) {
            this.b = n3;
            this.c = n4;
            this.d = n5;
            this.e = n6;
        }
    }

    public static interface b {
        public View a(int var1);

        public int b();

        public int c(View var1);

        public int d();

        public int e(View var1);
    }
}

