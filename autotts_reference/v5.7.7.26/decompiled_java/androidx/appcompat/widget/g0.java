/*
 * Decompiled with CFR 0.152.
 */
package androidx.appcompat.widget;

public class g0 {
    public int a = 0;
    public int b = 0;
    public int c = Integer.MIN_VALUE;
    public int d = Integer.MIN_VALUE;
    public int e = 0;
    public int f = 0;
    public boolean g = false;
    public boolean h = false;

    public int a() {
        if (this.g) {
            return this.a;
        }
        return this.b;
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        if (this.g) {
            return this.b;
        }
        return this.a;
    }

    public void e(int n3, int n4) {
        this.h = false;
        if (n3 != Integer.MIN_VALUE) {
            this.e = n3;
            this.a = n3;
        }
        if (n4 != Integer.MIN_VALUE) {
            this.f = n4;
            this.b = n4;
        }
    }

    public void f(boolean bl) {
        if (bl == this.g) {
            return;
        }
        this.g = bl;
        if (this.h) {
            if (bl) {
                int n3 = this.d;
                if (n3 == Integer.MIN_VALUE) {
                    n3 = this.e;
                }
                this.a = n3;
                n3 = this.c;
                if (n3 == Integer.MIN_VALUE) {
                    n3 = this.f;
                }
                this.b = n3;
                return;
            }
            int n4 = this.c;
            if (n4 == Integer.MIN_VALUE) {
                n4 = this.e;
            }
            this.a = n4;
            n4 = this.d;
            if (n4 == Integer.MIN_VALUE) {
                n4 = this.f;
            }
            this.b = n4;
            return;
        }
        this.a = this.e;
        this.b = this.f;
    }

    public void g(int n3, int n4) {
        this.c = n3;
        this.d = n4;
        this.h = true;
        if (this.g) {
            if (n4 != Integer.MIN_VALUE) {
                this.a = n4;
            }
            if (n3 != Integer.MIN_VALUE) {
                this.b = n3;
                return;
            }
        } else {
            if (n3 != Integer.MIN_VALUE) {
                this.a = n3;
            }
            if (n4 != Integer.MIN_VALUE) {
                this.b = n4;
            }
        }
    }
}

