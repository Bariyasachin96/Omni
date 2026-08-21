/*
 * Decompiled with CFR 0.152.
 */
package h3;

public final class a
extends Enum {
    public static final /* enum */ a c = new a("COROUTINE_SUSPENDED", 0);
    public static final /* enum */ a d = new a("UNDECIDED", 1);
    public static final /* enum */ a e = new a("RESUMED", 2);
    public static final a[] f = a.a();

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public a() {
        void cfr_renamed_1;
        void cfr_renamed_2;
    }

    public static final /* synthetic */ a[] a() {
        return new a[]{c, d, e};
    }

    public static a valueOf(String string) {
        return Enum.valueOf(a.class, string);
    }

    public static a[] values() {
        return (a[])f.clone();
    }
}

