/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Property
 *  android.view.ViewGroup
 */
package a2;

import android.util.Property;
import android.view.ViewGroup;
import z1.g;

public class d
extends Property {
    public static final Property a = new d("childrenAlpha");

    public d(String string) {
        super(Float.class, string);
    }

    public Float a(ViewGroup object) {
        if ((object = (Float)object.getTag(g.mtrl_internal_children_alpha_tag)) != null) {
            return object;
        }
        return Float.valueOf(1.0f);
    }

    public void b(ViewGroup viewGroup, Float f3) {
        float f4 = f3.floatValue();
        viewGroup.setTag(g.mtrl_internal_children_alpha_tag, (Object)f3);
        int n3 = viewGroup.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            viewGroup.getChildAt(i3).setAlpha(f4);
        }
    }
}

