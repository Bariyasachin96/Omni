/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.os.Parcelable
 */
package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.os.Parcelable;
import androidx.core.graphics.drawable.IconCompat;
import o1.a;

public class IconCompatParcelizer {
    public static IconCompat read(a a4) {
        IconCompat iconCompat = new IconCompat();
        iconCompat.a = a4.p(iconCompat.a, 1);
        iconCompat.c = a4.j(iconCompat.c, 2);
        iconCompat.d = a4.r(iconCompat.d, 3);
        iconCompat.e = a4.p(iconCompat.e, 4);
        iconCompat.f = a4.p(iconCompat.f, 5);
        iconCompat.g = (ColorStateList)a4.r((Parcelable)iconCompat.g, 6);
        iconCompat.i = a4.t(iconCompat.i, 7);
        iconCompat.j = a4.t(iconCompat.j, 8);
        iconCompat.e();
        return iconCompat;
    }

    public static void write(IconCompat object, a a4) {
        Object object2;
        a4.x(true, true);
        ((IconCompat)object).f(a4.f());
        int n3 = ((IconCompat)object).a;
        if (-1 != n3) {
            a4.F(n3, 1);
        }
        if ((object2 = ((IconCompat)object).c) != null) {
            a4.B((byte[])object2, 2);
        }
        if ((object2 = (Object)((IconCompat)object).d) != null) {
            a4.H((Parcelable)object2, 3);
        }
        if ((n3 = ((IconCompat)object).e) != 0) {
            a4.F(n3, 4);
        }
        if ((n3 = ((IconCompat)object).f) != 0) {
            a4.F(n3, 5);
        }
        if ((object2 = (Object)((IconCompat)object).g) != null) {
            a4.H((Parcelable)object2, 6);
        }
        if ((object2 = (Object)((IconCompat)object).i) != null) {
            a4.J((String)object2, 7);
        }
        if ((object = ((IconCompat)object).j) != null) {
            a4.J((String)object, 8);
        }
    }
}

