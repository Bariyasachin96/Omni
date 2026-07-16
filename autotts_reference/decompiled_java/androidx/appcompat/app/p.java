/*
 * Decompiled with CFR 0.152.
 */
package androidx.appcompat.app;

import java.util.LinkedHashSet;
import java.util.Locale;
import k0.b;

public abstract class p {
    public static b a(b b3, b b4) {
        LinkedHashSet<Locale> linkedHashSet = new LinkedHashSet<Locale>();
        for (int i3 = 0; i3 < b3.f() + b4.f(); ++i3) {
            Locale locale = i3 < b3.f() ? b3.c(i3) : b4.c(i3 - b3.f());
            if (locale == null) continue;
            linkedHashSet.add(locale);
        }
        return b.a(linkedHashSet.toArray(new Locale[linkedHashSet.size()]));
    }

    public static b b(b b3, b b4) {
        if (b3 != null && !b3.e()) {
            return p.a(b3, b4);
        }
        return b.d();
    }
}

