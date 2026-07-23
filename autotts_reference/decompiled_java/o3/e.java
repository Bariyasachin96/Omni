/*
 * Decompiled with CFR 0.152.
 */
package o3;

import d3.h;
import e3.a0;
import e3.b0;
import e3.l;
import e3.m;
import java.io.Serializable;
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
        Serializable serializable;
        b = new a(null);
        Object object2 = l.h(n3.a.class, n3.l.class, p.class, q.class, r.class, s.class, t.class, u.class, v.class, w.class, n3.b.class, c.class, n3.d.class, n3.e.class, f.class, n3.g.class, n3.h.class, i.class, j.class, n3.k.class, n3.m.class, n.class, o.class);
        ArrayList<d3.d> object32 = new ArrayList<d3.d>(m.l((Iterable)object2, 10));
        object2 = object2.iterator();
        int n3 = 0;
        while (object2.hasNext()) {
            Object e3 = object2.next();
            if (n3 < 0) {
                l.k();
            }
            object32.add(h.a((Class)e3, n3));
            ++n3;
        }
        c = b0.g(object32);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("boolean", "kotlin.Boolean");
        hashMap.put("char", "kotlin.Char");
        hashMap.put("byte", "kotlin.Byte");
        hashMap.put("short", "kotlin.Short");
        hashMap.put("int", "kotlin.Int");
        hashMap.put("float", "kotlin.Float");
        hashMap.put("long", "kotlin.Long");
        hashMap.put("double", "kotlin.Double");
        d = hashMap;
        object2 = new HashMap<String, String>();
        ((HashMap)object2).put("java.lang.Boolean", "kotlin.Boolean");
        ((HashMap)object2).put("java.lang.Character", "kotlin.Char");
        ((HashMap)object2).put("java.lang.Byte", "kotlin.Byte");
        ((HashMap)object2).put("java.lang.Short", "kotlin.Short");
        ((HashMap)object2).put("java.lang.Integer", "kotlin.Int");
        ((HashMap)object2).put("java.lang.Float", "kotlin.Float");
        ((HashMap)object2).put("java.lang.Long", "kotlin.Long");
        ((HashMap)object2).put("java.lang.Double", "kotlin.Double");
        e = object2;
        HashMap<Object, Object> hashMap2 = new HashMap<Object, Object>();
        hashMap2.put("java.lang.Object", "kotlin.Any");
        hashMap2.put("java.lang.String", "kotlin.String");
        hashMap2.put("java.lang.CharSequence", "kotlin.CharSequence");
        hashMap2.put("java.lang.Throwable", "kotlin.Throwable");
        hashMap2.put("java.lang.Cloneable", "kotlin.Cloneable");
        hashMap2.put("java.lang.Number", "kotlin.Number");
        hashMap2.put("java.lang.Comparable", "kotlin.Comparable");
        hashMap2.put("java.lang.Enum", "kotlin.Enum");
        hashMap2.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        hashMap2.put("java.lang.Iterable", "kotlin.collections.Iterable");
        hashMap2.put("java.util.Iterator", "kotlin.collections.Iterator");
        hashMap2.put("java.util.Collection", "kotlin.collections.Collection");
        hashMap2.put("java.util.List", "kotlin.collections.List");
        hashMap2.put("java.util.Set", "kotlin.collections.Set");
        hashMap2.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        hashMap2.put("java.util.Map", "kotlin.collections.Map");
        hashMap2.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        hashMap2.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        hashMap2.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        hashMap2.putAll(hashMap);
        hashMap2.putAll((Map<Object, Object>)object2);
        object2 = hashMap.values();
        k.d(object2, "primitiveFqNames.values");
        object2 = object2.iterator();
        while (object2.hasNext()) {
            String string = (String)object2.next();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("kotlin.jvm.internal.");
            k.d(string, "kotlinName");
            ((StringBuilder)serializable).append(u3.l.n(string, '.', null, 2, null));
            ((StringBuilder)serializable).append("CompanionObject");
            String string2 = ((StringBuilder)serializable).toString();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append(".Companion");
            d3.d d3 = h.a(string2, ((StringBuilder)serializable).toString());
            hashMap2.put(d3.c(), d3.d());
        }
        for (Map.Entry entry : c.entrySet()) {
            serializable = (Class)entry.getKey();
            n3 = ((Number)entry.getValue()).intValue();
            String string = ((Class)serializable).getName();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("kotlin.Function");
            ((StringBuilder)serializable).append(n3);
            hashMap2.put(string, ((StringBuilder)serializable).toString());
        }
        f = hashMap2;
        object2 = new LinkedHashMap(a0.a(hashMap2.size()));
        for (Map.Entry entry : hashMap2.entrySet()) {
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

