/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.util.Property
 */
package a2;

import android.graphics.drawable.Drawable;
import android.util.Property;

public class e
extends Property {
    public static final Property a = new e();

    public e() {
        super(Integer.class, "drawableAlphaCompat");
    }

    public Integer a(Drawable drawable) {
        return drawable.getAlpha();
    }

    public void b(Drawable drawable, Integer n3) {
        drawable.setAlpha(n3.intValue());
    }
}

