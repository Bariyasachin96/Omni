/*
 * Decompiled with CFR 0.152.
 */
package e3;

import d3.d;
import d3.h;
import e3.l;
import e3.m;
import e3.s;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import o3.k;
import u3.c;

public abstract class t
extends s {
    public static boolean o(Iterable iterable, Object object) {
        k.e(iterable, "<this>");
        if (iterable instanceof Collection) {
            return ((Collection)iterable).contains(object);
        }
        return t.s(iterable, object) >= 0;
    }

    public static Object p(List list) {
        k.e(list, "<this>");
        if (!list.isEmpty()) {
            return list.get(0);
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static Object q(Iterable object) {
        k.e(object, "<this>");
        if (object instanceof List) {
            if ((object = (List)object).isEmpty()) {
                return null;
            }
            return object.get(0);
        }
        if (!(object = object.iterator()).hasNext()) {
            return null;
        }
        return object.next();
    }

    public static Object r(List list, int n3) {
        k.e(list, "<this>");
        if (n3 >= 0 && n3 <= l.g(list)) {
            return list.get(n3);
        }
        return null;
    }

    public static final int s(Iterable object, Object object2) {
        k.e(object, "<this>");
        if (object instanceof List) {
            return ((List)object).indexOf(object2);
        }
        object = object.iterator();
        int n3 = 0;
        while (object.hasNext()) {
            Object e3 = object.next();
            if (n3 < 0) {
                l.k();
            }
            if (k.a(object2, e3)) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    public static final Appendable t(Iterable object, Appendable appendable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int n3, CharSequence charSequence4, n3.l l3) {
        int n4;
        k.e(object, "<this>");
        k.e(appendable, "buffer");
        k.e(charSequence, "separator");
        k.e(charSequence2, "prefix");
        k.e(charSequence3, "postfix");
        k.e(charSequence4, "truncated");
        appendable.append(charSequence2);
        object = object.iterator();
        int n5 = 0;
        while (true) {
            n4 = n5++;
            if (!object.hasNext()) break;
            charSequence2 = object.next();
            if (n5 > 1) {
                appendable.append(charSequence);
            }
            if (n3 >= 0) {
                n4 = n5;
                if (n5 > n3) break;
            }
            c.a(appendable, charSequence2, l3);
        }
        if (n3 >= 0 && n4 > n3) {
            appendable.append(charSequence4);
        }
        appendable.append(charSequence3);
        return appendable;
    }

    public static final String u(Iterable object, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int n3, CharSequence charSequence4, n3.l l3) {
        k.e(object, "<this>");
        k.e(charSequence, "separator");
        k.e(charSequence2, "prefix");
        k.e(charSequence3, "postfix");
        k.e(charSequence4, "truncated");
        object = ((StringBuilder)t.t((Iterable)object, new StringBuilder(), charSequence, charSequence2, charSequence3, n3, charSequence4, l3)).toString();
        k.d(object, "joinTo(StringBuilder(), …ed, transform).toString()");
        return object;
    }

    public static /* synthetic */ String v(Iterable iterable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int n3, CharSequence charSequence4, n3.l l3, int n4, Object object) {
        if ((n4 & 1) != 0) {
            charSequence = ", ";
        }
        if ((n4 & 2) != 0) {
            charSequence2 = "";
        }
        if ((n4 & 4) != 0) {
            charSequence3 = "";
        }
        if ((n4 & 8) != 0) {
            n3 = -1;
        }
        if ((n4 & 0x10) != 0) {
            charSequence4 = "...";
        }
        if ((n4 & 0x20) != 0) {
            l3 = null;
        }
        return t.u(iterable, charSequence, charSequence2, charSequence3, n3, charSequence4, l3);
    }

    public static Object w(List list) {
        k.e(list, "<this>");
        if (!list.isEmpty()) {
            return list.get(l.g(list));
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static List x(Iterable arrayList, Iterable iterable) {
        k.e(arrayList, "<this>");
        k.e(iterable, "other");
        Iterator iterator = arrayList.iterator();
        Iterator iterator2 = iterable.iterator();
        arrayList = new ArrayList<d>(Math.min(m.l(arrayList, 10), m.l(iterable, 10)));
        while (iterator.hasNext() && iterator2.hasNext()) {
            arrayList.add(h.a(iterator.next(), iterator2.next()));
        }
        return arrayList;
    }
}

