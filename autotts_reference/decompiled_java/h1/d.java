/*
 * Decompiled with CFR 0.152.
 */
package h1;

public final class d
extends Enum {
    public static final /* enum */ d d = new d("DEX_FILES", 0, 0L);
    public static final /* enum */ d e = new d("EXTRA_DESCRIPTORS", 1, 1L);
    public static final /* enum */ d f = new d("CLASSES", 2, 2L);
    public static final /* enum */ d g = new d("METHODS", 3, 3L);
    public static final /* enum */ d h = new d("AGGREGATION_COUNT", 4, 4L);
    public static final d[] i = h1.d.a();
    public final long c;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public d() {
        void var3_2;
        void cfr_renamed_1;
        void cfr_renamed_2;
        this.c = var3_2;
    }

    public static /* synthetic */ d[] a() {
        return new d[]{d, e, f, g, h};
    }

    public static d valueOf(String string) {
        return Enum.valueOf(d.class, string);
    }

    public static d[] values() {
        return (d[])i.clone();
    }

    public long b() {
        return this.c;
    }
}

