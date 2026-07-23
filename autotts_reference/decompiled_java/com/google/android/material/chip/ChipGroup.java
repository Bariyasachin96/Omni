/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package com.google.android.material.chip;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.a;
import com.google.android.material.internal.z;
import java.util.List;
import java.util.Set;
import p0.s;
import z1.l;
import z1.m;

public class ChipGroup
extends FlowLayout {
    public static final int m = z1.l.Widget_MaterialComponents_ChipGroup;
    public int g;
    public int h;
    public d i;
    public final com.google.android.material.internal.a j;
    public final int k;
    public final e l;

    public ChipGroup(Context context) {
        this(context, null);
    }

    public ChipGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.chipGroupStyle);
    }

    public ChipGroup(Context object, AttributeSet attributeSet, int n3) {
        com.google.android.material.internal.a a4;
        int n4 = m;
        super(y2.a.d(object, attributeSet, n3, n4), attributeSet, n3);
        this.j = a4 = new com.google.android.material.internal.a();
        object = new e(this, null);
        this.l = object;
        attributeSet = z.i(this.getContext(), attributeSet, z1.m.ChipGroup, n3, n4, new int[0]);
        n3 = attributeSet.getDimensionPixelOffset(z1.m.ChipGroup_chipSpacing, 0);
        this.setChipSpacingHorizontal(attributeSet.getDimensionPixelOffset(z1.m.ChipGroup_chipSpacingHorizontal, n3));
        this.setChipSpacingVertical(attributeSet.getDimensionPixelOffset(z1.m.ChipGroup_chipSpacingVertical, n3));
        this.setSingleLine(attributeSet.getBoolean(z1.m.ChipGroup_singleLine, false));
        this.setSingleSelection(attributeSet.getBoolean(z1.m.ChipGroup_singleSelection, false));
        this.setSelectionRequired(attributeSet.getBoolean(z1.m.ChipGroup_selectionRequired, false));
        this.k = attributeSet.getResourceId(z1.m.ChipGroup_checkedChip, -1);
        attributeSet.recycle();
        a4.o(new a.b(this){
            public final ChipGroup a;
            {
                this.a = chipGroup;
            }

            @Override
            public void a(Set object) {
                if (this.a.i != null) {
                    object = this.a.i;
                    ChipGroup chipGroup = this.a;
                    object.a(chipGroup, chipGroup.j.j(this.a));
                }
            }
        });
        super.setOnHierarchyChangeListener((ViewGroup.OnHierarchyChangeListener)object);
        this.setImportantForAccessibility(1);
    }

    private int getVisibleChipCount() {
        int n3 = 0;
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            int n4 = n3;
            if (this.getChildAt(i3) instanceof Chip) {
                n4 = n3;
                if (this.h(i3)) {
                    n4 = n3 + 1;
                }
            }
            n3 = n4;
        }
        return n3;
    }

    @Override
    public boolean c() {
        return super.c();
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && layoutParams instanceof LayoutParams;
    }

    public int g(View view) {
        if (!(view instanceof Chip)) {
            return -1;
        }
        int n3 = 0;
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            View view2 = this.getChildAt(i3);
            int n4 = n3;
            if (view2 instanceof Chip) {
                n4 = n3;
                if (this.h(i3)) {
                    if ((Chip)view2 == view) {
                        return n3;
                    }
                    n4 = n3 + 1;
                }
            }
            n3 = n4;
        }
        return -1;
    }

    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getCheckedChipId() {
        return this.j.k();
    }

    public List<Integer> getCheckedChipIds() {
        return this.j.j(this);
    }

    public int getChipSpacingHorizontal() {
        return this.g;
    }

    public int getChipSpacingVertical() {
        return this.h;
    }

    public final boolean h(int n3) {
        return this.getChildAt(n3).getVisibility() == 0;
    }

    public boolean i() {
        return this.j.l();
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        int n3 = this.k;
        if (n3 != -1) {
            this.j.f(n3);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        object = s.L0((AccessibilityNodeInfo)object);
        int n3 = this.c() ? this.getVisibleChipCount() : -1;
        int n4 = this.getRowCount();
        int n5 = this.i() ? 1 : 2;
        ((s)object).j0(s.e.b(n4, n3, false, n5));
    }

    public void setChipSpacing(int n3) {
        this.setChipSpacingHorizontal(n3);
        this.setChipSpacingVertical(n3);
    }

    public void setChipSpacingHorizontal(int n3) {
        if (this.g != n3) {
            this.g = n3;
            this.setItemSpacing(n3);
            this.requestLayout();
        }
    }

    public void setChipSpacingHorizontalResource(int n3) {
        this.setChipSpacingHorizontal(this.getResources().getDimensionPixelOffset(n3));
    }

    public void setChipSpacingResource(int n3) {
        this.setChipSpacing(this.getResources().getDimensionPixelOffset(n3));
    }

    public void setChipSpacingVertical(int n3) {
        if (this.h != n3) {
            this.h = n3;
            this.setLineSpacing(n3);
            this.requestLayout();
        }
    }

    public void setChipSpacingVerticalResource(int n3) {
        this.setChipSpacingVertical(this.getResources().getDimensionPixelOffset(n3));
    }

    @Deprecated
    public void setDividerDrawableHorizontal(Drawable drawable) {
        throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setDividerDrawableVertical(Drawable drawable) {
        throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setFlexWrap(int n3) {
        throw new UnsupportedOperationException("Changing flex wrap not allowed. ChipGroup exposes a singleLine attribute instead.");
    }

    @Deprecated
    public void setOnCheckedChangeListener(c c3) {
        if (c3 == null) {
            this.setOnCheckedStateChangeListener(null);
            return;
        }
        this.setOnCheckedStateChangeListener(new d(this, c3){
            public final ChipGroup a;
            {
                this.a = chipGroup;
            }

            @Override
            public void a(ChipGroup chipGroup, List list) {
                if (!this.a.j.l()) {
                    return;
                }
                this.a.getCheckedChipId();
                throw null;
            }
        });
    }

    public void setOnCheckedStateChangeListener(d d3) {
        this.i = d3;
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        com.google.android.material.chip.ChipGroup$e.a(this.l, onHierarchyChangeListener);
    }

    public void setSelectionRequired(boolean bl) {
        this.j.p(bl);
    }

    @Deprecated
    public void setShowDividerHorizontal(int n3) {
        throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setShowDividerVertical(int n3) {
        throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
    }

    public void setSingleLine(int n3) {
        this.setSingleLine(this.getResources().getBoolean(n3));
    }

    @Override
    public void setSingleLine(boolean bl) {
        super.setSingleLine(bl);
    }

    public void setSingleSelection(int n3) {
        this.setSingleSelection(this.getResources().getBoolean(n3));
    }

    public void setSingleSelection(boolean bl) {
        this.j.q(bl);
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public static interface c {
    }

    public static interface d {
        public void a(ChipGroup var1, List var2);
    }

    public class e
    implements ViewGroup.OnHierarchyChangeListener {
        public ViewGroup.OnHierarchyChangeListener c;
        public final ChipGroup d;

        public e(ChipGroup chipGroup) {
            this.d = chipGroup;
        }

        public /* synthetic */ e(ChipGroup chipGroup, a a4) {
            this(chipGroup);
        }

        public static /* synthetic */ ViewGroup.OnHierarchyChangeListener a(e e3, ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
            e3.c = onHierarchyChangeListener;
            return onHierarchyChangeListener;
        }

        public void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;
            if (view == this.d && view2 instanceof Chip) {
                if (view2.getId() == -1) {
                    view2.setId(View.generateViewId());
                }
                this.d.j.e((Chip)view2);
            }
            if ((onHierarchyChangeListener = this.c) != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            ChipGroup chipGroup = this.d;
            if (view == chipGroup && view2 instanceof Chip) {
                chipGroup.j.n((Chip)view2);
            }
            if ((chipGroup = this.c) != null) {
                chipGroup.onChildViewRemoved(view, view2);
            }
        }
    }
}

