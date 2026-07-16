/*
 * Decompiled with CFR 0.152.
 */
package o;

import java.util.NoSuchElementException;
import o.h;
import o3.g;
import o3.k;
import r3.c;
import r3.e;

public abstract class f {
    public float[] a;
    public int b;

    public f(int n3) {
        float[] fArray = n3 == 0 ? h.a() : new float[n3];
        this.a = fArray;
    }

    public /* synthetic */ f(int n3, g g3) {
        this(n3);
    }

    public static /* synthetic */ String f(f f3, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int n3, CharSequence charSequence4, int n4, Object object) {
        if (object == null) {
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
            return f3.e(charSequence, charSequence2, charSequence3, n3, charSequence4);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: joinToString");
    }

    public final float a() {
        if (!this.d()) {
            return this.a[0];
        }
        throw new NoSuchElementException("FloatList is empty.");
    }

    public final float b(int n3) {
        if (n3 >= 0 && n3 < this.b) {
            return this.a[n3];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index ");
        stringBuilder.append(n3);
        stringBuilder.append(" must be in 0..");
        stringBuilder.append(this.b - 1);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public final int c() {
        return this.b;
    }

    public final boolean d() {
        return this.b == 0;
    }

    public final String e(CharSequence charSequence, CharSequence object, CharSequence charSequence2, int n3, CharSequence charSequence3) {
        StringBuilder stringBuilder;
        block3: {
            k.e(charSequence, "separator");
            k.e(object, "prefix");
            k.e(charSequence2, "postfix");
            k.e(charSequence3, "truncated");
            stringBuilder = new StringBuilder();
            stringBuilder.append((CharSequence)object);
            object = this.a;
            int n4 = this.b;
            for (int i3 = 0; i3 < n4; ++i3) {
                Object object2 = object[i3];
                if (i3 == n3) {
                    stringBuilder.append(charSequence3);
                    break block3;
                }
                if (i3 != 0) {
                    stringBuilder.append(charSequence);
                }
                stringBuilder.append((float)object2);
            }
            stringBuilder.append(charSequence2);
        }
        charSequence = stringBuilder.toString();
        k.d(charSequence, "StringBuilder().apply(builderAction).toString()");
        return charSequence;
    }

    public boolean equals(Object object) {
        if (object instanceof f) {
            Object object2 = (f)object;
            int n3 = ((f)object2).b;
            int n4 = this.b;
            if (n3 == n4) {
                block5: {
                    object = this.a;
                    object2 = ((f)object2).a;
                    c c3 = e.e(0, n4);
                    if ((n4 = c3.a()) <= (n3 = c3.b())) {
                        while (object[n4] == object2[n4]) {
                            if (n4 != n3) {
                                ++n4;
                                continue;
                            }
                            break block5;
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final float g() {
        if (!this.d()) {
            return this.a[this.b - 1];
        }
        throw new NoSuchElementException("FloatList is empty.");
    }

    public int hashCode() {
        float[] fArray = this.a;
        int n3 = this.b;
        int n4 = 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            n4 += Float.hashCode(fArray[i3]) * 31;
        }
        return n4;
    }

    public String toString() {
        return f.f(this, null, "[", "]", 0, null, 25, null);
    }
}

