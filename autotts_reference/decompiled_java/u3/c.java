/*
 * Decompiled with CFR 0.152.
 */
package u3;

import n3.l;
import o3.k;

public abstract class c {
    public static void a(Appendable appendable, Object object, l l3) {
        k.e(appendable, "<this>");
        if (l3 != null) {
            appendable.append((CharSequence)l3.f(object));
            return;
        }
        boolean bl = object == null ? true : object instanceof CharSequence;
        if (bl) {
            appendable.append((CharSequence)object);
            return;
        }
        if (object instanceof Character) {
            appendable.append(((Character)object).charValue());
            return;
        }
        appendable.append(String.valueOf(object));
    }
}

