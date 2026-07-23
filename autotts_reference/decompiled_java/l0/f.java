/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.graphics.Typeface
 */
package l0;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import l0.a;
import l0.d;
import l0.g;
import l0.h;
import o.l;
import o.r;

public abstract class f {
    public static final l a = new l(16);
    public static final ExecutorService b = h.a("fonts-androidx", 10, 10000);
    public static final Object c = new Object();
    public static final r d = new r();

    public static String a(l0.e e3, int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e3.d());
        stringBuilder.append("-");
        stringBuilder.append(n3);
        return stringBuilder.toString();
    }

    public static int b(g.a bArray) {
        int n3 = bArray.c();
        int n4 = 1;
        if (n3 != 0) {
            if (bArray.c() != 1) {
                return -3;
            }
            return -2;
        }
        bArray = bArray.b();
        n3 = n4;
        if (bArray != null) {
            if (bArray.length == 0) {
                n3 = n4;
            } else {
                int n5 = bArray.length;
                int n6 = 0;
                n4 = 0;
                while (true) {
                    n3 = n6;
                    if (n4 >= n5) break;
                    n3 = bArray[n4].b();
                    if (n3 != 0) {
                        if (n3 < 0) {
                            return -3;
                        }
                        return n3;
                    }
                    ++n4;
                }
            }
        }
        return n3;
    }

    public static e c(String string, Context context, l0.e object, int n3) {
        l l3 = a;
        Typeface typeface = (Typeface)l3.c(string);
        if (typeface != null) {
            return new e(typeface);
        }
        try {
            object = l0.d.e(context, (l0.e)object, null);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return new e(-1);
        }
        int n4 = f.b((g.a)object);
        if (n4 != 0) {
            return new e(n4);
        }
        if ((context = g0.e.b(context, null, ((g.a)object).b(), n3)) != null) {
            l3.d(string, context);
            return new e((Typeface)context);
        }
        return new e(-3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static Typeface d(Context object, l0.e object2, int n3, Executor executor, a object3) {
        String string = f.a((l0.e)object2, n3);
        Object object4 = (Typeface)a.c(string);
        if (object4 != null) {
            ((a)object3).b(new e((Typeface)object4));
            return object4;
        }
        object4 = new n0.a((a)object3){
            public final a a;
            {
                this.a = a4;
            }

            public void a(e e3) {
                e e4 = e3;
                if (e3 == null) {
                    e4 = new e(-3);
                }
                this.a.b(e4);
            }
        };
        object3 = c;
        // MONITORENTER : object3
        r r3 = d;
        ArrayList<Typeface> arrayList = (ArrayList<Typeface>)r3.get(string);
        if (arrayList != null) {
            arrayList.add((Typeface)object4);
            // MONITOREXIT : object3
            return null;
        }
        arrayList = new ArrayList<Typeface>();
        arrayList.add((Typeface)object4);
        r3.put(string, arrayList);
        // MONITOREXIT : object3
        object2 = new Callable(string, (Context)object, (l0.e)object2, n3){
            public final String a;
            public final Context b;
            public final l0.e c;
            public final int d;
            {
                this.a = string;
                this.b = context;
                this.c = e3;
                this.d = n3;
            }

            public e a() {
                try {
                    e e3 = f.c(this.a, this.b, this.c, this.d);
                    return e3;
                }
                catch (Throwable throwable) {
                    return new e(-3);
                }
            }
        };
        object = executor;
        if (executor == null) {
            object = b;
        }
        h.b((Executor)object, (Callable)object2, new n0.a(string){
            public final String a;
            {
                this.a = string;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            public void a(e e3) {
                Object object = c;
                // MONITORENTER : object
                r r3 = d;
                ArrayList arrayList = (ArrayList)r3.get(this.a);
                if (arrayList == null) {
                    // MONITOREXIT : object
                    return;
                }
                r3.remove(this.a);
                // MONITOREXIT : object
                int n3 = 0;
                while (n3 < arrayList.size()) {
                    ((n0.a)arrayList.get(n3)).accept(e3);
                    ++n3;
                }
            }
        });
        return null;
    }

    public static Typeface e(Context object, l0.e e3, a a4, int n3, int n4) {
        String string = f.a(e3, n3);
        Typeface typeface = (Typeface)a.c(string);
        if (typeface != null) {
            a4.b(new e(typeface));
            return typeface;
        }
        if (n4 == -1) {
            object = f.c(string, object, e3, n3);
            a4.b((e)object);
            return object.a;
        }
        object = new Callable(string, (Context)object, e3, n3){
            public final String a;
            public final Context b;
            public final l0.e c;
            public final int d;
            {
                this.a = string;
                this.b = context;
                this.c = e3;
                this.d = n3;
            }

            public e a() {
                return f.c(this.a, this.b, this.c, this.d);
            }
        };
        try {
            object = (e)h.c(b, (Callable)object, n4);
            a4.b((e)object);
            object = object.a;
            return object;
        }
        catch (InterruptedException interruptedException) {
            a4.b(new e(-3));
            return null;
        }
    }

    public static final class e {
        public final Typeface a;
        public final int b;

        public e(int n3) {
            this.a = null;
            this.b = n3;
        }

        public e(Typeface typeface) {
            this.a = typeface;
            this.b = 0;
        }

        public boolean a() {
            return this.b == 0;
        }
    }
}

