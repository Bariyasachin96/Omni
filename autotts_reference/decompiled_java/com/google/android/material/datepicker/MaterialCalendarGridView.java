/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.GridView
 *  android.widget.ListAdapter
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.b;
import com.google.android.material.datepicker.n;
import com.google.android.material.datepicker.p;
import com.google.android.material.datepicker.v;
import com.google.android.material.internal.c0;
import java.util.Calendar;
import n0.d;
import o0.a;
import o0.x0;
import p0.s;
import z1.g;

final class MaterialCalendarGridView
extends GridView {
    public final Calendar c = v.m();
    public final boolean d;

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        if (n.e2(this.getContext())) {
            this.setNextFocusLeftId(g.cancel_button);
            this.setNextFocusRightId(g.confirm_button);
        }
        this.d = n.g2(this.getContext());
        x0.h0((View)this, new a(this){
            public final MaterialCalendarGridView d;
            {
                this.d = materialCalendarGridView;
            }

            @Override
            public void g(View view, s s3) {
                super.g(view, s3);
                s3.j0(null);
            }
        });
    }

    public static int d(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }

    public static boolean e(Long l3, Long l4, Long l5, Long l6) {
        return l3 == null || l4 == null || l5 == null || l6 == null || l5 > l4 || l6 < l3;
        {
        }
    }

    public final void a(int n3, Rect rect) {
        if (n3 == 33) {
            this.setSelection(this.b().m());
            return;
        }
        if (n3 == 130) {
            this.setSelection(this.b().b());
            return;
        }
        super.onFocusChanged(true, n3, rect);
    }

    public p b() {
        return (p)super.getAdapter();
    }

    public final View c(int n3) {
        return this.getChildAt(n3 - this.getFirstVisiblePosition());
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.b().notifyDataSetChanged();
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p p3 = this.b();
        DateSelector dateSelector = p3.d;
        b b3 = p3.f;
        int n3 = Math.max(p3.b(), this.getFirstVisiblePosition());
        int n4 = Math.min(p3.m(), this.getLastVisiblePosition());
        Long l3 = p3.d(n3);
        Long l4 = p3.d(n4);
        for (Object object : dateSelector.f()) {
            int n5;
            int n6;
            int n7;
            int n8;
            Object object2 = ((d)object).a;
            if (object2 == null || ((d)object).b == null) continue;
            object2 = (Long)object2;
            long l5 = (Long)object2;
            object = (Long)((d)object).b;
            long l6 = (Long)object;
            if (MaterialCalendarGridView.e(l3, l4, (Long)object2, (Long)object)) continue;
            boolean bl = c0.m((View)this);
            if (l5 < l3) {
                n8 = p3.h(n3) ? 0 : (!bl ? this.c(n3 - 1).getRight() : this.c(n3 - 1).getLeft());
                n7 = n3;
            } else {
                this.c.setTimeInMillis(l5);
                n7 = p3.a(this.c.get(5));
                n8 = MaterialCalendarGridView.d(this.c(n7));
            }
            if (l6 > l4) {
                n6 = p3.i(n4) ? this.getWidth() : (!bl ? this.c(n4).getRight() : this.c(n4).getLeft());
                n5 = n4;
            } else {
                this.c.setTimeInMillis(l6);
                n5 = p3.a(this.c.get(5));
                n6 = MaterialCalendarGridView.d(this.c(n5));
            }
            int n9 = (int)p3.getItemId(n5);
            for (int i3 = (int)p3.getItemId(n7); i3 <= n9; ++i3) {
                int n10;
                int n11 = this.getNumColumns() * i3;
                int n12 = n11 + this.getNumColumns() - 1;
                object2 = this.c(n11);
                int n13 = object2.getTop();
                int n14 = b3.a.c();
                int n15 = object2.getBottom();
                int n16 = b3.a.b();
                if (!bl) {
                    n10 = n11 > n7 ? 0 : n8;
                    if (n5 > n12) {
                        n11 = this.getWidth();
                        n12 = n10;
                        n10 = n11;
                    } else {
                        n11 = n6;
                        n12 = n10;
                        n10 = n11;
                    }
                } else {
                    n10 = n5 > n12 ? 0 : n6;
                    n12 = n11 > n7 ? this.getWidth() : n8;
                    n11 = n10;
                    n10 = n12;
                    n12 = n11;
                }
                canvas.drawRect((float)n12, (float)(n13 + n14), (float)n10, (float)(n15 - n16), b3.h);
            }
        }
    }

    public void onFocusChanged(boolean bl, int n3, Rect rect) {
        if (bl) {
            this.a(n3, rect);
            return;
        }
        super.onFocusChanged(false, n3, rect);
    }

    public boolean onKeyDown(int n3, KeyEvent keyEvent) {
        if (!super.onKeyDown(n3, keyEvent)) {
            return false;
        }
        int n4 = this.getSelectedItemPosition();
        if (n4 != -1 && (n4 < this.b().b() || n4 > this.b().m())) {
            if (19 == n3) {
                this.setSelection(this.b().b());
                return true;
            }
            return false;
        }
        return true;
    }

    public void onMeasure(int n3, int n4) {
        if (this.d) {
            super.onMeasure(n3, View.MeasureSpec.makeMeasureSpec((int)0xFFFFFF, (int)Integer.MIN_VALUE));
            this.getLayoutParams().height = this.getMeasuredHeight();
            return;
        }
        super.onMeasure(n3, n4);
    }

    public final void setAdapter(ListAdapter listAdapter) {
        if (listAdapter instanceof p) {
            super.setAdapter(listAdapter);
            return;
        }
        throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", MaterialCalendarGridView.class.getCanonicalName(), p.class.getCanonicalName()));
    }

    public void setSelection(int n3) {
        if (n3 < this.b().b()) {
            super.setSelection(this.b().b());
            return;
        }
        super.setSelection(n3);
    }
}

