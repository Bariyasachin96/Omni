/*
 * Decompiled with CFR 0.152.
 */
package u3;

import u3.a;

public abstract class b
extends a {
    public static final boolean a(char c3, char c4, boolean bl) {
        if (c3 == c4) {
            return true;
        }
        if (!bl) {
            return false;
        }
        return (c3 = Character.toUpperCase(c3)) == (c4 = Character.toUpperCase(c4)) || Character.toLowerCase(c3) == Character.toLowerCase(c4);
        {
        }
    }
}

