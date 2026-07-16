/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import h0.a;
import s2.c;
import z1.e;
import z1.l;
import z1.m;

public class MaterialDividerItemDecoration
extends RecyclerView.o {
    public static final int i = l.Widget_MaterialComponents_MaterialDivider;
    public Drawable a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public boolean g;
    public final Rect h = new Rect();

    public MaterialDividerItemDecoration(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, z1.c.materialDividerStyle, n3);
    }

    public MaterialDividerItemDecoration(Context context, AttributeSet attributeSet, int n3, int n4) {
        attributeSet = z.i(context, attributeSet, m.MaterialDivider, n3, i, new int[0]);
        this.c = s2.c.a(context, (TypedArray)attributeSet, m.MaterialDivider_dividerColor).getDefaultColor();
        this.b = attributeSet.getDimensionPixelSize(m.MaterialDivider_dividerThickness, context.getResources().getDimensionPixelSize(z1.e.material_divider_thickness));
        this.e = attributeSet.getDimensionPixelOffset(m.MaterialDivider_dividerInsetStart, 0);
        this.f = attributeSet.getDimensionPixelOffset(m.MaterialDivider_dividerInsetEnd, 0);
        this.g = attributeSet.getBoolean(m.MaterialDivider_lastItemDecorated, true);
        attributeSet.recycle();
        this.a = new ShapeDrawable();
        this.l(this.c);
        this.m(n4);
    }

    @Override
    public void e(Rect rect, View view, RecyclerView recyclerView, RecyclerView.z z3) {
        rect.set(0, 0, 0, 0);
        if (this.o(recyclerView, view)) {
            if (this.d == 1) {
                rect.bottom = this.b;
                return;
            }
            if (c0.m((View)recyclerView)) {
                rect.left = this.b;
                return;
            }
            rect.right = this.b;
        }
    }

    @Override
    public void g(Canvas canvas, RecyclerView recyclerView, RecyclerView.z z3) {
        if (recyclerView.getLayoutManager() == null) {
            return;
        }
        if (this.d == 1) {
            this.k(canvas, recyclerView);
            return;
        }
        this.j(canvas, recyclerView);
    }

    public final void j(Canvas canvas, RecyclerView recyclerView) {
        int n3;
        int n4;
        canvas.save();
        boolean bl = recyclerView.getClipToPadding();
        int n5 = 0;
        if (bl) {
            n4 = recyclerView.getPaddingTop();
            n3 = recyclerView.getHeight() - recyclerView.getPaddingBottom();
            canvas.clipRect(recyclerView.getPaddingLeft(), n4, recyclerView.getWidth() - recyclerView.getPaddingRight(), n3);
        } else {
            n3 = recyclerView.getHeight();
            n4 = 0;
        }
        int n6 = this.e;
        int n7 = this.f;
        bl = c0.m((View)recyclerView);
        int n8 = recyclerView.getChildCount();
        while (n5 < n8) {
            View view = recyclerView.getChildAt(n5);
            if (this.o(recyclerView, view)) {
                int n9;
                recyclerView.getLayoutManager().U(view, this.h);
                int n10 = Math.round(view.getTranslationX());
                if (bl) {
                    n9 = this.h.left + n10;
                    n10 = this.b + n9;
                } else {
                    n9 = (n10 += this.h.right) - this.b;
                }
                this.a.setBounds(n9, n4 + n6, n10, n3 - n7);
                n10 = Math.round(view.getAlpha() * 255.0f);
                this.a.setAlpha(n10);
                this.a.draw(canvas);
            }
            ++n5;
        }
        canvas.restore();
    }

    public final void k(Canvas canvas, RecyclerView recyclerView) {
        int n3;
        int n4;
        canvas.save();
        boolean bl = recyclerView.getClipToPadding();
        int n5 = 0;
        if (bl) {
            n4 = recyclerView.getPaddingLeft();
            n3 = recyclerView.getWidth() - recyclerView.getPaddingRight();
            canvas.clipRect(n4, recyclerView.getPaddingTop(), n3, recyclerView.getHeight() - recyclerView.getPaddingBottom());
        } else {
            n3 = recyclerView.getWidth();
            n4 = 0;
        }
        bl = c0.m((View)recyclerView);
        int n6 = bl ? this.f : this.e;
        int n7 = bl ? this.e : this.f;
        int n8 = recyclerView.getChildCount();
        while (n5 < n8) {
            View view = recyclerView.getChildAt(n5);
            if (this.o(recyclerView, view)) {
                recyclerView.getLayoutManager().U(view, this.h);
                int n9 = this.h.bottom + Math.round(view.getTranslationY());
                int n10 = this.b;
                this.a.setBounds(n4 + n6, n9 - n10, n3 - n7, n9);
                n9 = Math.round(view.getAlpha() * 255.0f);
                this.a.setAlpha(n9);
                this.a.draw(canvas);
            }
            ++n5;
        }
        canvas.restore();
    }

    public void l(int n3) {
        Drawable drawable;
        this.c = n3;
        this.a = drawable = h0.a.r(this.a);
        drawable.setTint(n3);
    }

    public void m(int n3) {
        if (n3 != 0 && n3 != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid orientation: ");
            stringBuilder.append(n3);
            stringBuilder.append(". It should be either HORIZONTAL or VERTICAL");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.d = n3;
    }

    public boolean n(int n3, RecyclerView.h h3) {
        return true;
    }

    public final boolean o(RecyclerView object, View view) {
        int n3 = ((RecyclerView)object).j0(view);
        boolean bl = (object = ((RecyclerView)object).getAdapter()) != null && n3 == ((RecyclerView.h)object).f() - 1;
        return n3 != -1 && (!bl || this.g) && this.n(n3, (RecyclerView.h)object);
    }
}

