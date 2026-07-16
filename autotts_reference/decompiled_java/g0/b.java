/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Insets
 *  android.graphics.Rect
 */
package g0;

import android.graphics.Insets;
import android.graphics.Rect;
import androidx.appcompat.widget.v;
import androidx.appcompat.widget.w;
import androidx.appcompat.widget.x;
import androidx.appcompat.widget.y;

public final class b {
    public static final b e = new b(0, 0, 0, 0);
    public final int a;
    public final int b;
    public final int c;
    public final int d;

    public b(int n3, int n4, int n5, int n6) {
        this.a = n3;
        this.b = n4;
        this.c = n5;
        this.d = n6;
    }

    public static b a(b b3, b b4) {
        return g0.b.b(Math.max(b3.a, b4.a), Math.max(b3.b, b4.b), Math.max(b3.c, b4.c), Math.max(b3.d, b4.d));
    }

    public static b b(int n3, int n4, int n5, int n6) {
        if (n3 == 0 && n4 == 0 && n5 == 0 && n6 == 0) {
            return e;
        }
        return new b(n3, n4, n5, n6);
    }

    public static b c(Rect rect) {
        return g0.b.b(rect.left, rect.top, rect.right, rect.bottom);
    }

    public static b d(Insets insets) {
        return g0.b.b(v.a(insets), w.a(insets), x.a(insets), y.a(insets));
    }

    public Insets e() {
        return g0.b$a.a(this.a, this.b, this.c, this.d);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && b.class == object.getClass()) {
            object = (b)object;
            if (this.d != ((b)object).d) {
                return false;
            }
            if (this.a != ((b)object).a) {
                return false;
            }
            if (this.c != ((b)object).c) {
                return false;
            }
            return this.b == ((b)object).b;
        }
        return false;
    }

    public int hashCode() {
        return ((this.a * 31 + this.b) * 31 + this.c) * 31 + this.d;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Insets{left=");
        stringBuilder.append(this.a);
        stringBuilder.append(", top=");
        stringBuilder.append(this.b);
        stringBuilder.append(", right=");
        stringBuilder.append(this.c);
        stringBuilder.append(", bottom=");
        stringBuilder.append(this.d);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static abstract class a {
        public static Insets a(int n3, int n4, int n5, int n6) {
            return Insets.of((int)n3, (int)n4, (int)n5, (int)n6);
        }
    }
}

