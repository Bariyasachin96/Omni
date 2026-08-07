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
        Object object3 = l.h(n3.a.class, n3.l.class, p.class, q.class, r.class, s.class, t.class, u.class, v.class, w.class, n3.b.class, c.class, n3.d.class, n3.e.class, f.class, n3.g.class, n3.h.class, i.class, j.class, n3.k.class, n3.m.class, n.class, o.class);
        Object object4 = new ArrayList<d3.d>(m.l((Iterable)object3, 10));
        object3 = object3.iterator();
        int n3 = 0;
        while (object3.hasNext()) {
            Object object22 = object3.next();
            if (n3 < 0) {
                l.k();
            }
            object4.add(h.a((Class)object22, n3));
            ++n3;
        }
        c = b0.g(object4);
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
        object3 = new HashMap<String, String>();
        ((HashMap)object3).put("java.lang.Boolean", "kotlin.Boolean");
        ((HashMap)object3).put("java.lang.Character", "kotlin.Char");
        ((HashMap)object3).put("java.lang.Byte", "kotlin.Byte");
        ((HashMap)object3).put("java.lang.Short", "kotlin.Short");
        ((HashMap)object3).put("java.lang.Integer", "kotlin.Int");
        ((HashMap)object3).put("java.lang.Float", "kotlin.Float");
        ((HashMap)object3).put("java.lang.Long", "kotlin.Long");
        ((HashMap)object3).put("java.lang.Double", "kotlin.Double");
        e = object3;
        object4 = new HashMap();
        ((HashMap)object4).put("java.lang.Object", "kotlin.Any");
        ((HashMap)object4).put("java.lang.String", "kotlin.String");
        ((HashMap)object4).put("java.lang.CharSequence", "kotlin.CharSequence");
        ((HashMap)object4).put("java.lang.Throwable", "kotlin.Throwable");
        ((HashMap)object4).put("java.lang.Cloneable", "kotlin.Cloneable");
        ((HashMap)object4).put("java.lang.Number", "kotlin.Number");
        ((HashMap)object4).put("java.lang.Comparable", "kotlin.Comparable");
        ((HashMap)object4).put("java.lang.Enum", "kotlin.Enum");
        ((HashMap)object4).put("java.lang.annotation.Annotation", "kotlin.Annotation");
        ((HashMap)object4).put("java.lang.Iterable", "kotlin.collections.Iterable");
        ((HashMap)object4).put("java.util.Iterator", "kotlin.collections.Iterator");
        ((HashMap)object4).put("java.util.Collection", "kotlin.collections.Collection");
        ((HashMap)object4).put("java.util.List", "kotlin.collections.List");
        ((HashMap)object4).put("java.util.Set", "kotlin.collections.Set");
        ((HashMap)object4).put("java.util.ListIterator", "kotlin.collections.ListIterator");
        ((HashMap)object4).put("java.util.Map", "kotlin.collections.Map");
        ((HashMap)object4).put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        ((HashMap)object4).put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        ((HashMap)object4).put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        ((HashMap)object4).putAll(hashMap);
        ((HashMap)object4).putAll(object3);
        object3 = hashMap.values();
        k.d(object3, "primitiveFqNames.values");
        object3 = object3.iterator();
        while (object3.hasNext()) {
            String string = (String)object3.next();
            object = new StringBuilder();
            ((StringBuilder)object).append("kotlin.jvm.internal.");
            k.d(string, "kotlinName");
            ((StringBuilder)object).append(u3.l.n(string, '.', null, 2, null));
            ((StringBuilder)object).append("CompanionObject");
            String string2 = ((StringBuilder)object).toString();
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(".Companion");
            d3.d d3 = h.a(string2, ((StringBuilder)object).toString());
            object4.put(d3.c(), d3.d());
        }
        for (Map.Entry entry : c.entrySet()) {
            object = (Class)entry.getKey();
            n3 = ((Number)entry.getValue()).intValue();
            object = ((Class)object).getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("kotlin.Function");
            stringBuilder.append(n3);
            ((HashMap)object4).put(object, stringBuilder.toString());
        }
        f = object4;
        object3 = new LinkedHashMap(a0.a(object4.size()));
        for (Map.Entry entry : object4.entrySet()) {
            object3.put(entry.getKey(), u3.l.n((String)entry.getValue(), '.', null, 2, null));
        }
        g = object3;
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

