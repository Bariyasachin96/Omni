/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowInsetsController
 */
package o0;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import o.r;
import o0.a3;
import o0.l0;
import o0.o0;
import o0.r0;
import o0.z2;

public final class y2 {
    public final e a;

    public y2(Window window, View object) {
        object = new l0((View)object);
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new d(window, this, (l0)object);
            return;
        }
        this.a = new c(window, (l0)object);
    }

    public y2(WindowInsetsController windowInsetsController) {
        this.a = new d(windowInsetsController, this, new l0(windowInsetsController));
    }

    public static y2 e(WindowInsetsController windowInsetsController) {
        return new y2(windowInsetsController);
    }

    public void a(int n3) {
        this.a.a(n3);
    }

    public void b(boolean bl) {
        this.a.b(bl);
    }

    public void c(boolean bl) {
        this.a.c(bl);
    }

    public void d(int n3) {
        this.a.d(n3);
    }

    public static abstract class a
    extends e {
        public final Window a;
        public final l0 b;

        public a(Window window, l0 l02) {
            this.a = window;
            this.b = l02;
        }

        @Override
        public void a(int n3) {
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((n3 & i3) == 0) continue;
                this.e(i3);
            }
        }

        @Override
        public void d(int n3) {
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((n3 & i3) == 0) continue;
                this.h(i3);
            }
        }

        public final void e(int n3) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 8) {
                        return;
                    }
                    this.b.a();
                    return;
                }
                this.f(2);
                return;
            }
            this.f(4);
        }

        public void f(int n3) {
            View view = this.a.getDecorView();
            view.setSystemUiVisibility(n3 | view.getSystemUiVisibility());
        }

        public void g(int n3) {
            this.a.addFlags(n3);
        }

        public final void h(int n3) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 8) {
                        return;
                    }
                    this.b.b();
                    return;
                }
                this.i(2);
                return;
            }
            this.i(4);
            this.j(1024);
        }

        public void i(int n3) {
            View view = this.a.getDecorView();
            view.setSystemUiVisibility(~n3 & view.getSystemUiVisibility());
        }

        public void j(int n3) {
            this.a.clearFlags(n3);
        }
    }

    public static abstract class b
    extends a {
        public b(Window window, l0 l02) {
            super(window, l02);
        }

        @Override
        public void c(boolean bl) {
            if (bl) {
                this.j(0x4000000);
                this.g(Integer.MIN_VALUE);
                this.f(8192);
                return;
            }
            this.i(8192);
        }
    }

    public static class c
    extends b {
        public c(Window window, l0 l02) {
            super(window, l02);
        }

        @Override
        public void b(boolean bl) {
            if (bl) {
                this.j(0x8000000);
                this.g(Integer.MIN_VALUE);
                this.f(16);
                return;
            }
            this.i(16);
        }
    }

    public static class d
    extends e {
        public final y2 a;
        public final WindowInsetsController b;
        public final l0 c;
        public final r d = new r();
        public Window e;

        public d(Window window, y2 y22, l0 l02) {
            this(z2.a(window), y22, l02);
            this.e = window;
        }

        public d(WindowInsetsController windowInsetsController, y2 y22, l0 l02) {
            this.b = windowInsetsController;
            this.a = y22;
            this.c = l02;
        }

        @Override
        public void a(int n3) {
            if ((n3 & 8) != 0) {
                this.c.a();
            }
            r0.a(this.b, n3 & 0xFFFFFFF7);
        }

        @Override
        public void b(boolean bl) {
            if (bl) {
                if (this.e != null) {
                    this.e(16);
                }
                a3.a(this.b, 16, 16);
                return;
            }
            if (this.e != null) {
                this.f(16);
            }
            a3.a(this.b, 0, 16);
        }

        @Override
        public void c(boolean bl) {
            if (bl) {
                if (this.e != null) {
                    this.e(8192);
                }
                a3.a(this.b, 8, 8);
                return;
            }
            if (this.e != null) {
                this.f(8192);
            }
            a3.a(this.b, 0, 8);
        }

        @Override
        public void d(int n3) {
            if ((n3 & 8) != 0) {
                this.c.b();
            }
            o0.a(this.b, n3 & 0xFFFFFFF7);
        }

        public void e(int n3) {
            View view = this.e.getDecorView();
            view.setSystemUiVisibility(n3 | view.getSystemUiVisibility());
        }

        public void f(int n3) {
            View view = this.e.getDecorView();
            view.setSystemUiVisibility(~n3 & view.getSystemUiVisibility());
        }
    }

    public static abstract class e {
        public abstract void a(int var1);

        public abstract void b(boolean var1);

        public abstract void c(boolean var1);

        public abstract void d(int var1);
    }
}

