/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.transformation;

import a2.h;
import a2.j;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.transformation.FabTransformationBehavior;
import com.google.android.material.transformation.FabTransformationScrimBehavior;
import java.util.HashMap;
import java.util.Map;
import z1.b;

@Deprecated
public class FabTransformationSheetBehavior
extends FabTransformationBehavior {
    public Map k;

    public FabTransformationSheetBehavior() {
    }

    public FabTransformationSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean L(View view, View view2, boolean bl, boolean bl2) {
        this.k0(view2, bl);
        return super.L(view, view2, bl, bl2);
    }

    @Override
    public FabTransformationBehavior.e i0(Context context, boolean bl) {
        int n3 = bl ? b.mtrl_fab_transformation_sheet_expand_spec : b.mtrl_fab_transformation_sheet_collapse_spec;
        FabTransformationBehavior.e e3 = new FabTransformationBehavior.e();
        e3.a = a2.h.d(context, n3);
        e3.b = new j(17, 0.0f, 0.0f);
        return e3;
    }

    public final void k0(View view, boolean bl) {
        Object object = view.getParent();
        if (object instanceof CoordinatorLayout) {
            object = (CoordinatorLayout)object;
            int n3 = object.getChildCount();
            if (bl) {
                this.k = new HashMap(n3);
            }
            for (int i3 = 0; i3 < n3; ++i3) {
                View view2 = object.getChildAt(i3);
                boolean bl2 = view2.getLayoutParams() instanceof CoordinatorLayout.e && ((CoordinatorLayout.e)view2.getLayoutParams()).f() instanceof FabTransformationScrimBehavior;
                if (view2 == view || bl2) continue;
                if (!bl) {
                    Map map = this.k;
                    if (map == null || !map.containsKey(view2)) continue;
                    view2.setImportantForAccessibility(((Integer)this.k.get(view2)).intValue());
                    continue;
                }
                this.k.put(view2, view2.getImportantForAccessibility());
                view2.setImportantForAccessibility(4);
            }
            if (!bl) {
                this.k = null;
            }
        }
    }
}

