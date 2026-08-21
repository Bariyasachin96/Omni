/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 */
package k1;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.startup.InitializationProvider;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import k1.b;
import k1.c;
import k1.d;

public final class a {
    public static volatile a d;
    public static final Object e;
    public final Map a;
    public final Set b;
    public final Context c;

    static {
        e = new Object();
    }

    public a(Context context) {
        this.c = context.getApplicationContext();
        this.b = new HashSet();
        this.a = new HashMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static a e(Context context) {
        if (d != null) return d;
        Object object = e;
        synchronized (object) {
            try {
                a a4;
                if (d != null) return d;
                d = a4 = new a(context);
                return d;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a() {
        Throwable throwable2222222;
        l1.b.a("Startup");
        ComponentName componentName = new ComponentName(this.c.getPackageName(), InitializationProvider.class.getName());
        this.b(this.c.getPackageManager().getProviderInfo((ComponentName)componentName, (int)128).metaData);
        l1.b.b();
        return;
        {
            catch (Throwable throwable2222222) {
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {}
            {
                d d3 = new d(nameNotFoundException);
                throw d3;
            }
        }
        l1.b.b();
        throw throwable2222222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(Bundle object) {
        HashSet hashSet;
        String string = this.c.getString(k1.c.androidx_startup);
        if (object == null) return;
        try {
            hashSet = new HashSet();
            for (Object object2 : object.keySet()) {
                if (!string.equals(object.getString((String)object2, null)) || !b.class.isAssignableFrom((Class<?>)(object2 = Class.forName((String)object2)))) continue;
                this.b.add(object2);
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new d(classNotFoundException);
        }
        object = this.b.iterator();
        while (object.hasNext()) {
            this.d((Class)object.next(), hashSet);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object c(Class clazz) {
        Object object = e;
        synchronized (object) {
            try {
                Object v3;
                Object object2 = v3 = this.a.get(clazz);
                if (v3 != null) return object2;
                object2 = new Object();
                return this.d(clazz, (Set)object2);
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Object d(Class var1_1, Set var2_4) {
        block7: {
            block10: {
                block9: {
                    block8: {
                        if (!l1.b.d()) ** GOTO lbl4
                        l1.b.a(var1_1.getSimpleName());
lbl4:
                        // 2 sources

                        if (var2_4.contains(var1_1)) break block7;
                        if (this.a.containsKey(var1_1)) ** GOTO lbl34
                        var2_4.add(var1_1);
                        try {
                            var3_5 = (b)var1_1.getDeclaredConstructor(null).newInstance(null);
                            var4_6 = var3_5.a();
                            if (var4_6.isEmpty()) break block8;
                            var5_7 = var4_6.iterator();
                            while (var5_7.hasNext()) {
                                var4_6 = (Class)var5_7.next();
                                if (this.a.containsKey(var4_6)) continue;
                                this.d((Class)var4_6, (Set)var2_4);
                            }
                        }
                        catch (Throwable var1_3) {
                            break block9;
                        }
                    }
                    var3_5 = var3_5.b(this.c);
                    var2_4.remove(var1_1);
                    this.a.put(var1_1, var3_5);
                    var1_1 = var3_5;
                    break block10;
                }
                var2_4 = new d(var1_3);
                throw var2_4;
lbl34:
                // 1 sources

                var1_1 = this.a.get(var1_1);
            }
            l1.b.b();
            return var1_1;
        }
        try {
            var1_1 = String.format("Cannot initialize %s. Cycle detected.", new Object[]{var1_1.getName()});
            var2_4 = new IllegalStateException((String)var1_1);
            throw var2_4;
        }
        catch (Throwable var1_2) {}
        l1.b.b();
        throw var1_2;
    }

    public Object f(Class clazz) {
        return this.c(clazz);
    }

    public boolean g(Class clazz) {
        return this.b.contains(clazz);
    }
}

