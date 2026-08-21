/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.graphics.Path
 *  android.util.Property
 */
package m1;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.util.Property;

public abstract class l {
    public static ObjectAnimator a(Object object, Property property, Path path) {
        return a.a(object, property, path);
    }

    public static abstract class a {
        public static <T, V> ObjectAnimator a(T t3, Property<T, V> property, Path path) {
            return ObjectAnimator.ofObject(t3, property, null, (Path)path);
        }
    }
}

