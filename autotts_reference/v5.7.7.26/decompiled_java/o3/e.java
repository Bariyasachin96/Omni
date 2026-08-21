/*
 * Decompiled with CFR 0.152.
 */
package o3;

import d3.h;
import e3.a0;
import e3.b0;
import e3.l;
import e3.m;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import n3.c;
import n3.f;
import n3.i;
import n3.j;
import n3.n;
import n3.o;
import n3.p;
import n3.q;
import n3.r;
import n3.s;
import n3.t;
import n3.u;
import n3.v;
import n3.w;
import o3.d;
import o3.g;
import o3.k;
import s3.b;

public final class e
implements b,
d {
    public static final a b;
    public static final Map c;
    public static final HashMap d;
    public static final HashMap e;
    public static final HashMap f;
    public static final Map g;
    public final Class a;

    static {
        Object object;
        b = new a(null);
        Object object2 = l.h(n3.a.class, n3.l.class, p.class, q.class, r.class, s.class, t.class, u.class, v.class, w.class, n3.b.class, c.class, n3.d.class, n3.e.class, f.class, n3.g.class, n3.h.class, i.class, j.class, n3.k.class, n3.m.class, n.class, o.class);
        ArrayList<d3.d> object32 = new ArrayList<d3.d>(m.l((Iterable)object2, 10));
        object2 = object2.iterator();
        int n3 = 0;
        while (object2.hasNext()) {
            object = object2.next();
            if (n3 < 0) {
                l.k();
            }
            object32.add(h.a((Class)object, n3));
            ++n3;
        }
        c = b0.g(object32);
        object2 = new HashMap<String, String>();
        ((HashMap)object2).put("boolean", "kotlin.Boolean");
        ((HashMap)object2).put("char", "kotlin.Char");
        ((HashMap)object2).put("byte", "kotlin.Byte");
        ((HashMap)object2).put("short", "kotlin.Short");
        ((HashMap)object2).put("int", "kotlin.Int");
        ((HashMap)object2).put("float", "kotlin.Float");
        ((HashMap)object2).put("long", "kotlin.Long");
        ((HashMap)object2).put("double", "kotlin.Double");
        d = object2;
        object = new HashMap();
        ((HashMap)object).put((String)"java.lang.Boolean", (String)"kotlin.Boolean");
        ((HashMap)object).put("java.lang.Character", "kotlin.Char");
        ((HashMap)object).put("java.lang.Byte", "kotlin.Byte");
        ((HashMap)object).put("java.lang.Short", "kotlin.Short");
        ((HashMap)object).put("java.lang.Integer", "kotlin.Int");
        ((HashMap)object).put("java.lang.Float", "kotlin.Float");
        ((HashMap)object).put("java.lang.Long", "kotlin.Long");
        ((HashMap)object).put("java.lang.Double", "kotlin.Double");
        e = object;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("java.lang.Object", "kotlin.Any");
        hashMap.put("java.lang.String", "kotlin.String");
        hashMap.put("java.lang.CharSequence", "kotlin.CharSequence");
        hashMap.put("java.lang.Throwable", "kotlin.Throwable");
        hashMap.put("java.lang.Cloneable", "kotlin.Cloneable");
        hashMap.put("java.lang.Number", "kotlin.Number");
        hashMap.put("java.lang.Comparable", "kotlin.Comparable");
        hashMap.put("java.lang.Enum", "kotlin.Enum");
        hashMap.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        hashMap.put("java.lang.Iterable", "kotlin.collections.Iterable");
        hashMap.put("java.util.Iterator", "kotlin.collections.Iterator");
        hashMap.put("java.util.Collection", "kotlin.collections.Collection");
        hashMap.put("java.util.List", "kotlin.collections.List");
        hashMap.put("java.util.Set", "kotlin.collections.Set");
        hashMap.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        hashMap.put("java.util.Map", "kotlin.collections.Map");
        hashMap.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        hashMap.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        hashMap.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        hashMap.putAll((Map<Object, Object>)object2);
        hashMap.putAll((Map<Object, Object>)object);
        object2 = ((HashMap)object2).values();
        k.d(object2, "primitiveFqNames.values");
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object = (String)object2.next();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("kotlin.jvm.internal.");
            k.d(object, "kotlinName");
            stringBuilder.append(u3.l.n((String)object, '.', null, 2, null));
            stringBuilder.append("CompanionObject");
            String string = stringBuilder.toString();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String)object);
            stringBuilder2.append(".Companion");
            object = h.a(string, stringBuilder2.toString());
            hashMap.put(((d3.d)object).c(), ((d3.d)object).d());
        }
        for (Map.Entry entry : c.entrySet()) {
            object = (Class)entry.getKey();
            n3 = ((Number)entry.getValue()).intValue();
            String string = ((Class)object).getName();
            object = new StringBuilder();
            ((StringBuilder)object).append("kotlin.Function");
            ((StringBuilder)object).append(n3);
            hashMap.put(string, ((StringBuilder)object).toString());
        }
        f = hashMap;
        object2 = new LinkedHashMap(a0.a(hashMap.size()));
        for (Map.Entry entry : hashMap.entrySet()) {
            object2.put(entry.getKey(), u3.l.n((String)entry.getValue(), '.', null, 2, null));
        }
        g = object2;
    }

    public e(Class clazz) {
        k.e(clazz, "jClass");
        this.a = clazz;
    }

    @Override
    public Class a() {
        return this.a;
    }

    public boolean equals(Object object) {
        return object instanceof e && k.a(m3.a.b(this), m3.a.b((b)object));
    }

    public int hashCode() {
        return m3.a.b(this).hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.a().toString());
        stringBuilder.append(" (Kotlin reflection is not available)");
        return stringBuilder.toString();
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }
    }
}

