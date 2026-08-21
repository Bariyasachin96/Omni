/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.j;
import java.util.concurrent.atomic.AtomicReference;
import o3.g;
import o3.k;

public abstract class f {
    public AtomicReference a = new AtomicReference();

    public abstract void a(j var1);

    public abstract b b();

    public abstract void c(j var1);

    public static final class androidx.lifecycle.f$a
    extends Enum {
        private static final androidx.lifecycle.f$a[] $VALUES;
        public static final a Companion;
        public static final /* enum */ androidx.lifecycle.f$a ON_ANY;
        public static final /* enum */ androidx.lifecycle.f$a ON_CREATE;
        public static final /* enum */ androidx.lifecycle.f$a ON_DESTROY;
        public static final /* enum */ androidx.lifecycle.f$a ON_PAUSE;
        public static final /* enum */ androidx.lifecycle.f$a ON_RESUME;
        public static final /* enum */ androidx.lifecycle.f$a ON_START;
        public static final /* enum */ androidx.lifecycle.f$a ON_STOP;

        static {
            ON_CREATE = new androidx.lifecycle.f$a("ON_CREATE", 0);
            ON_START = new androidx.lifecycle.f$a("ON_START", 1);
            ON_RESUME = new androidx.lifecycle.f$a("ON_RESUME", 2);
            ON_PAUSE = new androidx.lifecycle.f$a("ON_PAUSE", 3);
            ON_STOP = new androidx.lifecycle.f$a("ON_STOP", 4);
            ON_DESTROY = new androidx.lifecycle.f$a("ON_DESTROY", 5);
            ON_ANY = new androidx.lifecycle.f$a("ON_ANY", 6);
            $VALUES = androidx.lifecycle.f$a.a();
            Companion = new a(null);
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public cfr_renamed_3() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static final /* synthetic */ androidx.lifecycle.f$a[] a() {
            return new androidx.lifecycle.f$a[]{ON_CREATE, ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY, ON_ANY};
        }

        public static androidx.lifecycle.f$a valueOf(String string) {
            return Enum.valueOf(androidx.lifecycle.f$a.class, string);
        }

        public static androidx.lifecycle.f$a[] values() {
            return (androidx.lifecycle.f$a[])$VALUES.clone();
        }

        public final androidx.lifecycle.f$b b() {
            switch (b.a[this.ordinal()]) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((Object)this);
                    stringBuilder.append(" has no target state");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                case 6: {
                    return androidx.lifecycle.f$b.c;
                }
                case 5: {
                    return androidx.lifecycle.f$b.g;
                }
                case 3: 
                case 4: {
                    return androidx.lifecycle.f$b.f;
                }
                case 1: 
                case 2: 
            }
            return androidx.lifecycle.f$b.e;
        }

        public static final class androidx.lifecycle.f$a$a {
            public cfr_renamed_4() {
            }

            public /* synthetic */ cfr_renamed_4(g g3) {
                this();
            }

            public final androidx.lifecycle.f$a a(androidx.lifecycle.f$b b3) {
                k.e((Object)b3, "state");
                int n3 = a.a[b3.ordinal()];
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 3) {
                            return null;
                        }
                        return ON_PAUSE;
                    }
                    return ON_STOP;
                }
                return ON_DESTROY;
            }

            public final androidx.lifecycle.f$a b(androidx.lifecycle.f$b b3) {
                k.e((Object)b3, "state");
                int n3 = a.a[b3.ordinal()];
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 5) {
                            return null;
                        }
                        return ON_CREATE;
                    }
                    return ON_RESUME;
                }
                return ON_START;
            }

            public abstract class a {
                public static final int[] a;

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                static {
                    int[] nArray = new int[androidx.lifecycle.f$b.values().length];
                    try {
                        nArray[androidx.lifecycle.f$b.e.ordinal()] = 1;
                    }
                    catch (NoSuchFieldError noSuchFieldError) {}
                    try {
                        nArray[androidx.lifecycle.f$b.f.ordinal()] = 2;
                    }
                    catch (NoSuchFieldError noSuchFieldError) {}
                    try {
                        nArray[androidx.lifecycle.f$b.g.ordinal()] = 3;
                    }
                    catch (NoSuchFieldError noSuchFieldError) {}
                    try {
                        nArray[androidx.lifecycle.f$b.c.ordinal()] = 4;
                    }
                    catch (NoSuchFieldError noSuchFieldError) {}
                    try {
                        nArray[androidx.lifecycle.f$b.d.ordinal()] = 5;
                    }
                    catch (NoSuchFieldError noSuchFieldError) {}
                    a = nArray;
                }
            }
        }

        public abstract class b {
            public static final int[] a;

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            static {
                int[] nArray = new int[androidx.lifecycle.f$a.values().length];
                try {
                    nArray[androidx.lifecycle.f$a.ON_CREATE.ordinal()] = 1;
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    nArray[androidx.lifecycle.f$a.ON_STOP.ordinal()] = 2;
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    nArray[androidx.lifecycle.f$a.ON_START.ordinal()] = 3;
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    nArray[androidx.lifecycle.f$a.ON_PAUSE.ordinal()] = 4;
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    nArray[androidx.lifecycle.f$a.ON_RESUME.ordinal()] = 5;
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    nArray[androidx.lifecycle.f$a.ON_DESTROY.ordinal()] = 6;
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                try {
                    nArray[androidx.lifecycle.f$a.ON_ANY.ordinal()] = 7;
                }
                catch (NoSuchFieldError noSuchFieldError) {}
                a = nArray;
            }
        }
    }

    public static final class b
    extends Enum {
        public static final /* enum */ b c = new b("DESTROYED", 0);
        public static final /* enum */ b d = new b("INITIALIZED", 1);
        public static final /* enum */ b e = new b("CREATED", 2);
        public static final /* enum */ b f = new b("STARTED", 3);
        public static final /* enum */ b g = new b("RESUMED", 4);
        public static final b[] h = b.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public b() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static final /* synthetic */ b[] a() {
            return new b[]{c, d, e, f, g};
        }

        public static b valueOf(String string) {
            return Enum.valueOf(b.class, string);
        }

        public static b[] values() {
            return (b[])h.clone();
        }

        public final boolean b(b b3) {
            k.e((Object)b3, "state");
            return this.compareTo(b3) >= 0;
        }
    }
}

