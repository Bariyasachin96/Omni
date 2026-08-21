/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.PropertyValuesHolder
 *  android.graphics.Path
 *  android.util.Property
 */
package m1;

import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.util.Property;

public abstract class m {
    public static PropertyValuesHolder a(Property property, Path path) {
        return a.a(property, path);
    }

    public static abstract class a {
        public static <V> PropertyValuesHolder a(Property<?, V> property, Path path) {
            return PropertyValuesHolder.ofObject(property, null, (Path)path);
        }
    }
}

