/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.RadioButton
 *  android.widget.ToggleButton
 */
package com.google.android.material.button;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RadioButton;
import android.widget.ToggleButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonGroup;
import com.google.android.material.internal.z;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import o0.a;
import o0.x0;
import p0.s;
import v2.v;
import z1.c;
import z1.l;
import z1.m;

public class MaterialButtonToggleGroup
extends MaterialButtonGroup {
    public static final int t = z1.l.Widget_MaterialComponents_MaterialButtonToggleGroup;
    public final LinkedHashSet n;
    public boolean o;
    public boolean p;
    public boolean q;
    public final int r;
    public Set s;

    public MaterialButtonToggleGroup(Context context) {
        this(context, null);
    }

    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialButtonToggleGroupStyle);
    }

    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet, int n3) {
        int n4 = t;
        super(y2.a.d(context, attributeSet, n3, n4), attributeSet, n3);
        this.n = new LinkedHashSet();
        this.o = false;
        this.s = new HashSet();
        context = z.i(this.getContext(), attributeSet, z1.m.MaterialButtonToggleGroup, n3, n4, new int[0]);
        this.setSingleSelection(context.getBoolean(z1.m.MaterialButtonToggleGroup_singleSelection, false));
        this.r = context.getResourceId(z1.m.MaterialButtonToggleGroup_checkedButton, -1);
        this.q = context.getBoolean(z1.m.MaterialButtonToggleGroup_selectionRequired, false);
        if (this.h == null) {
            this.h = v.c(new v2.a(0.0f));
        }
        this.setEnabled(context.getBoolean(z1.m.MaterialButtonToggleGroup_android_enabled, true));
        context.recycle();
        this.setImportantForAccessibility(1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String getChildrenA11yClassName() {
        Class<RadioButton> clazz;
        if (this.p) {
            clazz = RadioButton.class;
            return clazz.getName();
        }
        clazz = ToggleButton.class;
        return clazz.getName();
    }

    private int getVisibleButtonCount() {
        int n3 = 0;
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            int n4 = n3;
            if (this.getChildAt(i3) instanceof MaterialButton) {
                n4 = n3;
                if (this.j(i3)) {
                    n4 = n3 + 1;
                }
            }
            n3 = n4;
        }
        return n3;
    }

    private void setupButtonChild(MaterialButton materialButton) {
        materialButton.setMaxLines(1);
        materialButton.setEllipsize(TextUtils.TruncateAt.END);
        materialButton.setCheckable(true);
        materialButton.setA11yClassName(this.getChildrenA11yClassName());
    }

    @Override
    public void addView(View object, int n3, ViewGroup.LayoutParams layoutParams) {
        if (!(object instanceof MaterialButton)) {
            Log.e((String)"MButtonToggleGroup", (String)"Child views must be of type MaterialButton.");
            return;
        }
        super.addView((View)object, n3, layoutParams);
        object = (MaterialButton)object;
        this.setupButtonChild((MaterialButton)object);
        this.r(object.getId(), ((MaterialButton)object).isChecked());
        x0.h0((View)object, new a(this){
            public final MaterialButtonToggleGroup d;
            {
                this.d = materialButtonToggleGroup;
            }

            @Override
            public void g(View view, s s3) {
                super.g(view, s3);
                s3.k0(s.f.a(0, 1, this.d.u(view), 1, false, ((MaterialButton)view).isChecked()));
            }
        });
    }

    public int getCheckedButtonId() {
        if (this.p && !this.s.isEmpty()) {
            return (Integer)this.s.iterator().next();
        }
        return -1;
    }

    public List<Integer> getCheckedButtonIds() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            int n3 = this.f(i3).getId();
            if (!this.s.contains(n3)) continue;
            arrayList.add(n3);
        }
        return arrayList;
    }

    public final boolean j(int n3) {
        return this.getChildAt(n3).getVisibility() != 8;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        int n3 = this.r;
        if (n3 != -1) {
            this.y(Collections.singleton(n3));
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        object = p0.s.L0((AccessibilityNodeInfo)object);
        int n3 = this.getVisibleButtonCount();
        int n4 = this.v() ? 1 : 2;
        ((s)object).j0(s.e.b(1, n3, false, n4));
    }

    public void q(b b3) {
        ((AbstractCollection)this.n).add(b3);
    }

    public final void r(int n3, boolean bl) {
        block8: {
            HashSet<Integer> hashSet;
            block7: {
                block6: {
                    if (n3 == -1) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Button ID is not valid: ");
                        stringBuilder.append(n3);
                        Log.e((String)"MButtonToggleGroup", (String)stringBuilder.toString());
                        return;
                    }
                    hashSet = new HashSet<Integer>(this.s);
                    if (!bl || hashSet.contains(n3)) break block6;
                    if (this.p && !hashSet.isEmpty()) {
                        hashSet.clear();
                    }
                    hashSet.add(n3);
                    break block7;
                }
                if (bl || !hashSet.contains(n3)) break block8;
                if (!this.q || hashSet.size() > 1) {
                    hashSet.remove(n3);
                }
            }
            this.y(hashSet);
        }
    }

    public void s() {
        this.y(new HashSet());
    }

    public void setSelectionRequired(boolean bl) {
        this.q = bl;
    }

    public void setSingleSelection(int n3) {
        this.setSingleSelection(this.getResources().getBoolean(n3));
    }

    public void setSingleSelection(boolean bl) {
        if (this.p != bl) {
            this.p = bl;
            this.s();
        }
        this.z();
    }

    public final void t(int n3, boolean bl) {
        Iterator iterator = ((AbstractCollection)this.n).iterator();
        while (iterator.hasNext()) {
            ((b)iterator.next()).a(this, n3, bl);
        }
    }

    public final int u(View view) {
        if (!(view instanceof MaterialButton)) {
            return -1;
        }
        int n3 = 0;
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            if (this.getChildAt(i3) == view) {
                return n3;
            }
            int n4 = n3;
            if (this.getChildAt(i3) instanceof MaterialButton) {
                n4 = n3;
                if (this.j(i3)) {
                    n4 = n3 + 1;
                }
            }
            n3 = n4;
        }
        return -1;
    }

    public boolean v() {
        return this.p;
    }

    public void w(MaterialButton materialButton, boolean bl) {
        if (this.o) {
            return;
        }
        this.r(materialButton.getId(), bl);
    }

    public final void x(int n3, boolean bl) {
        View view = this.findViewById(n3);
        if (view instanceof MaterialButton) {
            this.o = true;
            ((MaterialButton)view).setChecked(bl);
            this.o = false;
        }
    }

    public final void y(Set set) {
        Set set2 = this.s;
        this.s = new HashSet(set);
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            int n3 = this.f(i3).getId();
            this.x(n3, set.contains(n3));
            if (set2.contains(n3) == set.contains(n3)) continue;
            this.t(n3, set.contains(n3));
        }
        this.invalidate();
    }

    public final void z() {
        String string = this.getChildrenA11yClassName();
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            this.f(i3).setA11yClassName(string);
        }
    }

    public static interface b {
        public void a(MaterialButtonToggleGroup var1, int var2, boolean var3);
    }
}

