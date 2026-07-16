/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 */
package androidx.activity.result;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.a;
import androidx.activity.result.b;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.j;
import androidx.lifecycle.k;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ActivityResultRegistry {
    public final Map a = new HashMap();
    public final Map b = new HashMap();
    public final Map c = new HashMap();
    public ArrayList d = new ArrayList();
    public final transient Map e = new HashMap();
    public final Map f = new HashMap();
    public final Bundle g = new Bundle();

    public final void a(int n3, String string) {
        this.a.put(n3, string);
        this.b.put(string, n3);
    }

    public final boolean b(int n3, int n4, Intent intent) {
        String string = (String)this.a.get(n3);
        if (string == null) {
            return false;
        }
        this.d(string, n4, intent, (c)this.e.get(string));
        return true;
    }

    public final boolean c(int n3, Object object) {
        String string = (String)this.a.get(n3);
        if (string == null) {
            return false;
        }
        Object object2 = (c)this.e.get(string);
        if (object2 != null && (object2 = ((c)object2).a) != null) {
            if (this.d.remove(string)) {
                object2.a(object);
            }
        } else {
            this.g.remove(string);
            this.f.put(string, object);
        }
        return true;
    }

    public final void d(String string, int n3, Intent intent, c c3) {
        if (c3 != null && c3.a != null && this.d.contains(string)) {
            c3.a.a(c3.b.c(n3, intent));
            this.d.remove(string);
            return;
        }
        this.f.remove(string);
        this.g.putParcelable(string, (Parcelable)new ActivityResult(n3, intent));
    }

    public final int e() {
        int n3 = p3.c.c.b(0x7FFF0000);
        while (this.a.containsKey(n3 += 65536)) {
            n3 = p3.c.c.b(0x7FFF0000);
        }
        return n3;
    }

    public abstract void f(int var1, b.a var2, Object var3, c0.c var4);

    public final void g(Bundle object) {
        if (object != null) {
            ArrayList arrayList = object.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
            ArrayList arrayList2 = object.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
            if (arrayList2 != null && arrayList != null) {
                this.d = object.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
                this.g.putAll(object.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT"));
                for (int i3 = 0; i3 < arrayList2.size(); ++i3) {
                    object = (String)arrayList2.get(i3);
                    if (this.b.containsKey(object)) {
                        Integer n3 = (Integer)this.b.remove(object);
                        if (!this.g.containsKey((String)object)) {
                            this.a.remove(n3);
                        }
                    }
                    this.a((Integer)arrayList.get(i3), (String)arrayList2.get(i3));
                }
            }
        }
    }

    public final void h(Bundle bundle) {
        bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList(this.b.values()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList(this.b.keySet()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList(this.d));
        bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle)this.g.clone());
    }

    public final b i(String charSequence, k object, b.a a4, a a5) {
        f f3 = object.t();
        if (!f3.b().b(f.b.f)) {
            this.k((String)charSequence);
            d d3 = (d)this.c.get(charSequence);
            object = d3;
            if (d3 == null) {
                object = new d(f3);
            }
            ((d)object).a(new i(this, (String)charSequence, a5, a4){
                public final String a;
                public final a b;
                public final b.a c;
                public final ActivityResultRegistry d;
                {
                    this.d = activityResultRegistry;
                    this.a = string;
                    this.b = a4;
                    this.c = a5;
                }

                @Override
                public void d(k object, f.a a4) {
                    if (((Object)((Object)f.a.ON_START)).equals((Object)a4)) {
                        this.d.e.put(this.a, new c(this.b, this.c));
                        if (this.d.f.containsKey(this.a)) {
                            object = this.d.f.get(this.a);
                            this.d.f.remove(this.a);
                            this.b.a(object);
                        }
                        if ((object = (ActivityResult)this.d.g.getParcelable(this.a)) != null) {
                            this.d.g.remove(this.a);
                            this.b.a(this.c.c(((ActivityResult)object).p(), ((ActivityResult)object).o()));
                            return;
                        }
                    } else {
                        if (((Object)((Object)f.a.ON_STOP)).equals((Object)a4)) {
                            this.d.e.remove(this.a);
                            return;
                        }
                        if (((Object)((Object)f.a.ON_DESTROY)).equals((Object)a4)) {
                            this.d.l(this.a);
                        }
                    }
                }
            });
            this.c.put(charSequence, object);
            return new b(this, (String)charSequence, a4){
                public final String a;
                public final b.a b;
                public final ActivityResultRegistry c;
                {
                    this.c = activityResultRegistry;
                    this.a = string;
                    this.b = a4;
                }

                @Override
                public void b(Object object, c0.c object2) {
                    Integer n3 = (Integer)this.c.b.get(this.a);
                    if (n3 != null) {
                        this.c.d.add(this.a);
                        try {
                            this.c.f(n3, this.b, object, (c0.c)object2);
                            return;
                        }
                        catch (Exception exception) {
                            this.c.d.remove(this.a);
                            throw exception;
                        }
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Attempting to launch an unregistered ActivityResultLauncher with contract ");
                    ((StringBuilder)object2).append(this.b);
                    ((StringBuilder)object2).append(" and input ");
                    ((StringBuilder)object2).append(object);
                    ((StringBuilder)object2).append(". You must ensure the ActivityResultLauncher is registered before calling launch().");
                    throw new IllegalStateException(((StringBuilder)object2).toString());
                }

                @Override
                public void c() {
                    this.c.l(this.a);
                }
            };
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("LifecycleOwner ");
        ((StringBuilder)charSequence).append(object);
        ((StringBuilder)charSequence).append(" is attempting to register while current state is ");
        ((StringBuilder)charSequence).append((Object)f3.b());
        ((StringBuilder)charSequence).append(". LifecycleOwners must call register before they are STARTED.");
        throw new IllegalStateException(((StringBuilder)charSequence).toString());
    }

    public final b j(String string, b.a a4, a a5) {
        Object object;
        this.k(string);
        this.e.put(string, new c(a5, a4));
        if (this.f.containsKey(string)) {
            object = this.f.get(string);
            this.f.remove(string);
            a5.a(object);
        }
        if ((object = (ActivityResult)this.g.getParcelable(string)) != null) {
            this.g.remove(string);
            a5.a(a4.c(((ActivityResult)object).p(), ((ActivityResult)object).o()));
        }
        return new b(this, string, a4){
            public final String a;
            public final b.a b;
            public final ActivityResultRegistry c;
            {
                this.c = activityResultRegistry;
                this.a = string;
                this.b = a4;
            }

            @Override
            public void b(Object object, c0.c object2) {
                Integer n3 = (Integer)this.c.b.get(this.a);
                if (n3 != null) {
                    this.c.d.add(this.a);
                    try {
                        this.c.f(n3, this.b, object, (c0.c)object2);
                        return;
                    }
                    catch (Exception exception) {
                        this.c.d.remove(this.a);
                        throw exception;
                    }
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Attempting to launch an unregistered ActivityResultLauncher with contract ");
                ((StringBuilder)object2).append(this.b);
                ((StringBuilder)object2).append(" and input ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(". You must ensure the ActivityResultLauncher is registered before calling launch().");
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }

            @Override
            public void c() {
                this.c.l(this.a);
            }
        };
    }

    public final void k(String string) {
        if ((Integer)this.b.get(string) != null) {
            return;
        }
        this.a(this.e(), string);
    }

    public final void l(String string) {
        Object object;
        if (!this.d.contains(string) && (object = (Integer)this.b.remove(string)) != null) {
            this.a.remove(object);
        }
        this.e.remove(string);
        if (this.f.containsKey(string)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Dropping pending result for request ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(this.f.get(string));
            Log.w((String)"ActivityResultRegistry", (String)((StringBuilder)object).toString());
            this.f.remove(string);
        }
        if (this.g.containsKey(string)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Dropping pending result for request ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(this.g.getParcelable(string));
            Log.w((String)"ActivityResultRegistry", (String)((StringBuilder)object).toString());
            this.g.remove(string);
        }
        if ((object = (d)this.c.get(string)) != null) {
            ((d)object).b();
            this.c.remove(string);
        }
    }

    public static class c {
        public final a a;
        public final b.a b;

        public c(a a4, b.a a5) {
            this.a = a4;
            this.b = a5;
        }
    }

    public static class d {
        public final f a;
        public final ArrayList b;

        public d(f f3) {
            this.a = f3;
            this.b = new ArrayList();
        }

        public void a(i i3) {
            this.a.a(i3);
            this.b.add(i3);
        }

        public void b() {
            ArrayList arrayList = this.b;
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                Object object = arrayList.get(i3);
                object = (i)object;
                this.a.c((j)object);
            }
            this.b.clear();
        }
    }
}

