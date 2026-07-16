/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package com.google.android.material.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.c;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import v2.d;
import v2.o;
import v2.v;
import v2.w;
import v2.x;
import z1.l;
import z1.m;

public class MaterialButtonGroup
extends LinearLayout {
    public static final int m = z1.l.Widget_Material3_MaterialButtonGroup;
    public final List c;
    public final List d;
    public final b e;
    public final Comparator f;
    public Integer[] g;
    public v h;
    public w i;
    public int j;
    public x k;
    public boolean l;

    public MaterialButtonGroup(Context context) {
        this(context, null);
    }

    public MaterialButtonGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialButtonGroupStyle);
    }

    public MaterialButtonGroup(Context context, AttributeSet attributeSet, int n3) {
        int n4 = m;
        super(y2.a.d(context, attributeSet, n3, n4), attributeSet, n3);
        this.c = new ArrayList();
        this.d = new ArrayList();
        this.e = new b(this, null);
        this.f = new c(this);
        this.l = true;
        context = this.getContext();
        attributeSet = z.i(context, attributeSet, z1.m.MaterialButtonGroup, n3, n4, new int[0]);
        n3 = z1.m.MaterialButtonGroup_buttonSizeChange;
        if (attributeSet.hasValue(n3)) {
            this.k = x.b(context, (TypedArray)attributeSet, n3);
        }
        if (attributeSet.hasValue(n3 = z1.m.MaterialButtonGroup_shapeAppearance)) {
            w w3;
            this.i = w3 = w.b(context, (TypedArray)attributeSet, n3);
            if (w3 == null) {
                this.i = new w.b(o.b(context, attributeSet.getResourceId(n3, 0), attributeSet.getResourceId(z1.m.MaterialButtonGroup_shapeAppearanceOverlay, 0)).m()).j();
            }
        }
        if (attributeSet.hasValue(n3 = z1.m.MaterialButtonGroup_innerCornerSize)) {
            this.h = v.b(context, (TypedArray)attributeSet, n3, new v2.a(0.0f));
        }
        this.j = attributeSet.getDimensionPixelSize(z1.m.MaterialButtonGroup_android_spacing, 0);
        this.setChildrenDrawingOrderEnabled(true);
        this.setEnabled(attributeSet.getBoolean(z1.m.MaterialButtonGroup_android_enabled, true));
        attributeSet.recycle();
    }

    public static /* synthetic */ int a(MaterialButtonGroup materialButtonGroup, MaterialButton materialButton, MaterialButton materialButton2) {
        ((Object)((Object)materialButtonGroup)).getClass();
        int n3 = Boolean.valueOf(materialButton.isChecked()).compareTo(materialButton2.isChecked());
        if (n3 != 0) {
            return n3;
        }
        n3 = Boolean.valueOf(materialButton.isPressed()).compareTo(materialButton2.isPressed());
        if (n3 != 0) {
            return n3;
        }
        return Integer.compare(materialButtonGroup.indexOfChild((View)materialButton), materialButtonGroup.indexOfChild((View)materialButton2));
    }

    private int getFirstVisibleChildIndex() {
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            if (!this.j(i3)) continue;
            return i3;
        }
        return -1;
    }

    private int getLastVisibleChildIndex() {
        for (int i3 = this.getChildCount() - 1; i3 >= 0; --i3) {
            if (!this.j(i3)) continue;
            return i3;
        }
        return -1;
    }

    private boolean j(int n3) {
        return this.getChildAt(n3).getVisibility() != 8;
    }

    private void setGeneratedIdIfNeeded(MaterialButton materialButton) {
        if (materialButton.getId() == -1) {
            materialButton.setId(View.generateViewId());
        }
    }

    public void addView(View object, int n3, ViewGroup.LayoutParams layoutParams) {
        if (!(object instanceof MaterialButton)) {
            Log.e((String)"MButtonGroup", (String)"Child views must be of type MaterialButton.");
            return;
        }
        this.l();
        this.l = true;
        super.addView((View)object, n3, layoutParams);
        object = (MaterialButton)object;
        this.setGeneratedIdIfNeeded((MaterialButton)object);
        ((MaterialButton)object).setOnPressedChangeListenerInternal(this.e);
        this.c.add(((MaterialButton)object).getShapeAppearanceModel());
        this.d.add(((MaterialButton)object).getStateListShapeAppearanceModel());
        object.setEnabled(this.isEnabled());
    }

    public final void b() {
        int n3 = this.getFirstVisibleChildIndex();
        if (n3 == -1) {
            return;
        }
        for (int i3 = n3 + 1; i3 < this.getChildCount(); ++i3) {
            int n4;
            MaterialButton materialButton = this.f(i3);
            MaterialButton materialButton2 = this.f(i3 - 1);
            if (this.j <= 0) {
                n4 = Math.min(materialButton.getStrokeWidth(), materialButton2.getStrokeWidth());
                materialButton.setShouldDrawSurfaceColorStroke(true);
                materialButton2.setShouldDrawSurfaceColorStroke(true);
            } else {
                materialButton.setShouldDrawSurfaceColorStroke(false);
                materialButton2.setShouldDrawSurfaceColorStroke(false);
                n4 = 0;
            }
            materialButton2 = this.d((View)materialButton);
            if (this.getOrientation() == 0) {
                materialButton2.setMarginEnd(0);
                materialButton2.setMarginStart(this.j - n4);
                ((LinearLayout.LayoutParams)materialButton2).topMargin = 0;
            } else {
                ((LinearLayout.LayoutParams)materialButton2).bottomMargin = 0;
                ((LinearLayout.LayoutParams)materialButton2).topMargin = this.j - n4;
                materialButton2.setMarginStart(0);
            }
            materialButton.setLayoutParams((ViewGroup.LayoutParams)materialButton2);
        }
        this.m(n3);
    }

    public final void c() {
        if (this.k != null && this.getChildCount() != 0) {
            int n3;
            int n4;
            int n5 = this.getFirstVisibleChildIndex();
            int n6 = this.getLastVisibleChildIndex();
            int n7 = Integer.MAX_VALUE;
            for (n4 = n5; n4 <= n6; ++n4) {
                int n8;
                if (!this.j(n4)) continue;
                n3 = n8 = this.e(n4);
                if (n4 != n5) {
                    n3 = n8;
                    if (n4 != n6) {
                        n3 = n8 / 2;
                    }
                }
                n7 = Math.min(n7, n3);
            }
            for (n4 = n5; n4 <= n6; ++n4) {
                if (!this.j(n4)) continue;
                this.f(n4).setSizeChange(this.k);
                MaterialButton materialButton = this.f(n4);
                n3 = n4 != n5 && n4 != n6 ? n7 * 2 : n7;
                materialButton.setWidthChangeMax(n3);
            }
        }
    }

    public LinearLayout.LayoutParams d(View view) {
        if ((view = view.getLayoutParams()) instanceof LinearLayout.LayoutParams) {
            return (LinearLayout.LayoutParams)view;
        }
        return new LinearLayout.LayoutParams(view.width, view.height);
    }

    public void dispatchDraw(Canvas canvas) {
        this.n();
        super.dispatchDraw(canvas);
    }

    public final int e(int n3) {
        boolean bl = this.j(n3);
        int n4 = 0;
        if (bl && this.k != null) {
            MaterialButton materialButton = this.f(n3);
            int n5 = Math.max(0, this.k.c(materialButton.getWidth()));
            materialButton = this.i(n3);
            int n6 = materialButton == null ? 0 : materialButton.getAllowedWidthDecrease();
            materialButton = this.g(n3);
            n3 = materialButton == null ? n4 : materialButton.getAllowedWidthDecrease();
            return Math.min(n5, n6 + n3);
        }
        return 0;
    }

    public MaterialButton f(int n3) {
        return (MaterialButton)this.getChildAt(n3);
    }

    public final MaterialButton g(int n3) {
        int n4;
        int n5 = this.getChildCount();
        while ((n4 = n3 + 1) < n5) {
            n3 = n4;
            if (!this.j(n4)) continue;
            return this.f(n4);
        }
        return null;
    }

    public x getButtonSizeChange() {
        return this.k;
    }

    public int getChildDrawingOrder(int n3, int n4) {
        Integer[] integerArray = this.g;
        if (integerArray != null && n4 < integerArray.length) {
            return integerArray[n4];
        }
        Log.w((String)"MButtonGroup", (String)"Child order wasn't updated");
        return n4;
    }

    public d getInnerCornerSize() {
        return this.h.e();
    }

    public v getInnerCornerSizeStateList() {
        return this.h;
    }

    public o getShapeAppearance() {
        w w3 = this.i;
        if (w3 == null) {
            return null;
        }
        return w3.c(true);
    }

    public int getSpacing() {
        return this.j;
    }

    public w getStateListShapeAppearance() {
        return this.i;
    }

    /*
     * Exception decompiling
     */
    public final w.b h(boolean var1_1, boolean var2_2, int var3_3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:461)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:251)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:673)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:56)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:722)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public final MaterialButton i(int n3) {
        --n3;
        while (n3 >= 0) {
            if (this.j(n3)) {
                return this.f(n3);
            }
            --n3;
        }
        return null;
    }

    public void k(MaterialButton materialButton, int n3) {
        int n4 = this.indexOfChild((View)materialButton);
        if (n4 >= 0) {
            materialButton = this.i(n4);
            MaterialButton materialButton2 = this.g(n4);
            if (materialButton != null || materialButton2 != null) {
                if (materialButton == null) {
                    materialButton2.setDisplayedWidthDecrease(n3);
                }
                if (materialButton2 == null) {
                    materialButton.setDisplayedWidthDecrease(n3);
                }
                if (materialButton != null && materialButton2 != null) {
                    materialButton.setDisplayedWidthDecrease(n3 / 2);
                    materialButton2.setDisplayedWidthDecrease((n3 + 1) / 2);
                }
            }
        }
    }

    public final void l() {
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            this.f(i3).p();
        }
    }

    public final void m(int n3) {
        if (this.getChildCount() != 0 && n3 != -1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.f(n3).getLayoutParams();
            if (this.getOrientation() == 1) {
                layoutParams.topMargin = 0;
                layoutParams.bottomMargin = 0;
                return;
            }
            layoutParams.setMarginEnd(0);
            layoutParams.setMarginStart(0);
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        }
    }

    public final void n() {
        TreeMap<MaterialButton, Integer> treeMap = new TreeMap<MaterialButton, Integer>(this.f);
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            treeMap.put(this.f(i3), i3);
        }
        this.g = treeMap.values().toArray(new Integer[0]);
    }

    public void o() {
        if ((this.h != null || this.i != null) && this.l) {
            this.l = false;
            int n3 = this.getChildCount();
            int n4 = this.getFirstVisibleChildIndex();
            int n5 = this.getLastVisibleChildIndex();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n6;
                MaterialButton materialButton = this.f(i3);
                if (materialButton.getVisibility() == 8) continue;
                boolean bl = i3 == n4;
                boolean bl2 = i3 == n5;
                Object object = this.h(bl, bl2, i3);
                int n7 = this.getOrientation() == 0 ? 1 : 0;
                boolean bl3 = c0.m((View)this);
                if (n7 != 0) {
                    n7 = bl ? 5 : 0;
                    n6 = n7;
                    if (bl2) {
                        n6 = n7 | 0xA;
                    }
                    n7 = n6;
                    if (bl3) {
                        n7 = w.h(n6);
                    }
                } else {
                    n6 = bl ? 3 : 0;
                    n7 = n6;
                    if (bl2) {
                        n7 = n6 | 0xC;
                    }
                }
                if (((w)(object = ((w.b)object).n(this.h, ~n7).j())).f()) {
                    materialButton.setStateListShapeAppearanceModel((w)object);
                    continue;
                }
                materialButton.setShapeAppearanceModel(((w)object).c(true));
            }
        }
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        if (bl) {
            this.l();
            this.c();
        }
    }

    public void onMeasure(int n3, int n4) {
        this.o();
        this.b();
        super.onMeasure(n3, n4);
    }

    public void onViewRemoved(View view) {
        int n3;
        super.onViewRemoved(view);
        if (view instanceof MaterialButton) {
            ((MaterialButton)view).setOnPressedChangeListenerInternal(null);
        }
        if ((n3 = this.indexOfChild(view)) >= 0) {
            this.c.remove(n3);
            this.d.remove(n3);
        }
        this.l = true;
        this.o();
        this.l();
        this.b();
    }

    public void setButtonSizeChange(x x3) {
        if (this.k != x3) {
            this.k = x3;
            this.c();
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            this.f(i3).setEnabled(bl);
        }
    }

    public void setInnerCornerSize(d d3) {
        this.h = v.c(d3);
        this.l = true;
        this.o();
        this.invalidate();
    }

    public void setInnerCornerSizeStateList(v v3) {
        this.h = v3;
        this.l = true;
        this.o();
        this.invalidate();
    }

    public void setOrientation(int n3) {
        if (this.getOrientation() != n3) {
            this.l = true;
        }
        super.setOrientation(n3);
    }

    public void setShapeAppearance(o o3) {
        this.i = new w.b(o3).j();
        this.l = true;
        this.o();
        this.invalidate();
    }

    public void setSpacing(int n3) {
        this.j = n3;
        this.invalidate();
        this.requestLayout();
    }

    public void setStateListShapeAppearance(w w3) {
        this.i = w3;
        this.l = true;
        this.o();
        this.invalidate();
    }

    public class b
    implements MaterialButton.c {
        public final MaterialButtonGroup a;

        public b(MaterialButtonGroup materialButtonGroup) {
            this.a = materialButtonGroup;
        }

        public /* synthetic */ b(MaterialButtonGroup materialButtonGroup, a a4) {
            this(materialButtonGroup);
        }

        @Override
        public void a(MaterialButton materialButton, boolean bl) {
            this.a.invalidate();
        }
    }
}

