/*
 * Decompiled with CFR 0.152.
 */
package m0;

import java.util.Locale;
import m0.m;
import m0.o;

public abstract class n {
    public static final m a = new e(null, false);
    public static final m b = new e(null, true);
    public static final m c;
    public static final m d;
    public static final m e;
    public static final m f;

    static {
        b b3 = m0.n$b.a;
        c = new e(b3, false);
        d = new e(b3, true);
        e = new e(m0.n$a.b, false);
        f = m0.n$f.b;
    }

    public static int a(int n3) {
        if (n3 != 0) {
            if (n3 != 1 && n3 != 2) {
                return 2;
            }
            return 0;
        }
        return 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int b(int n3) {
        if (n3 == 0) return 1;
        if (n3 == 1 || n3 == 2) return 0;
        switch (n3) {
            default: {
                return 2;
            }
            case 16: 
            case 17: {
                return 0;
            }
            case 14: 
            case 15: 
        }
        return 1;
    }

    public static class a
    implements c {
        public static final a b = new a(true);
        public final boolean a;

        public a(boolean bl) {
            this.a = bl;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int a(CharSequence charSequence, int n3, int n4) {
            boolean bl = false;
            for (int i3 = n3; i3 < n4 + n3; ++i3) {
                int n5 = n.a(Character.getDirectionality(charSequence.charAt(i3)));
                if (n5 != 0) {
                    if (n5 != 1) continue;
                    if (!this.a) {
                        return 1;
                    }
                } else if (this.a) {
                    return 0;
                }
                bl = true;
            }
            if (bl) {
                return this.a ? 1 : 0;
            }
            return 2;
        }
    }

    public static class b
    implements c {
        public static final b a = new b();

        @Override
        public int a(CharSequence charSequence, int n3, int n4) {
            int n5 = 2;
            for (int i3 = n3; i3 < n4 + n3 && n5 == 2; ++i3) {
                n5 = n.b(Character.getDirectionality(charSequence.charAt(i3)));
            }
            return n5;
        }
    }

    public static interface c {
        public int a(CharSequence var1, int var2, int var3);
    }

    public static abstract class d
    implements m {
        public final c a;

        public d(c c3) {
            this.a = c3;
        }

        public abstract boolean a();

        public final boolean b(CharSequence charSequence, int n3, int n4) {
            if ((n3 = this.a.a(charSequence, n3, n4)) != 0) {
                if (n3 != 1) {
                    return this.a();
                }
                return false;
            }
            return true;
        }

        @Override
        public boolean isRtl(CharSequence charSequence, int n3, int n4) {
            if (charSequence != null && n3 >= 0 && n4 >= 0 && charSequence.length() - n4 >= n3) {
                if (this.a == null) {
                    return this.a();
                }
                return this.b(charSequence, n3, n4);
            }
            throw new IllegalArgumentException();
        }
    }

    public static class e
    extends d {
        public final boolean b;

        public e(c c3, boolean bl) {
            super(c3);
            this.b = bl;
        }

        @Override
        public boolean a() {
            return this.b;
        }
    }

    public static class f
    extends d {
        public static final f b = new f();

        public f() {
            super(null);
        }

        @Override
        public boolean a() {
            return o.a(Locale.getDefault()) == 1;
        }
    }
}

