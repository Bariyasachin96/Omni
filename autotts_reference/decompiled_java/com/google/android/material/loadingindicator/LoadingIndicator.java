/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.ProgressBar
 */
package com.google.android.material.loadingindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import c.a;
import com.google.android.material.loadingindicator.LoadingIndicatorSpec;
import java.util.Arrays;
import n2.b;
import n2.c;
import z1.l;

public final class LoadingIndicator
extends View
implements Drawable.Callback {
    public static final int e = l.Widget_Material3_LoadingIndicator;
    public final b c;
    public final LoadingIndicatorSpec d;

    public LoadingIndicator(Context context) {
        this(context, null);
    }

    public LoadingIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.loadingIndicatorStyle);
    }

    public LoadingIndicator(Context object, AttributeSet attributeSet, int n3) {
        super(y2.a.d((Context)object, attributeSet, n3, e), attributeSet, n3);
        object = this.getContext();
        object = b.a((Context)object, new LoadingIndicatorSpec((Context)object, attributeSet, n3));
        this.c = object;
        object.setCallback((Drawable.Callback)this);
        this.d = ((b)((Object)object)).c().a;
        this.setAnimatorDurationScaleProvider(new r2.a());
    }

    public boolean a() {
        LoadingIndicator loadingIndicator = this;
        while (loadingIndicator.getVisibility() == 0) {
            if ((loadingIndicator = loadingIndicator.getParent()) == null) {
                return this.getWindowVisibility() == 0;
            }
            if (!(loadingIndicator instanceof View)) {
                return true;
            }
            loadingIndicator = loadingIndicator;
        }
        return false;
    }

    public boolean b() {
        return this.isAttachedToWindow() && this.getWindowVisibility() == 0 && this.a();
    }

    public CharSequence getAccessibilityClassName() {
        return ProgressBar.class.getName();
    }

    public int getContainerColor() {
        return this.d.f;
    }

    public int getContainerHeight() {
        return this.d.d;
    }

    public int getContainerWidth() {
        return this.d.c;
    }

    public b getDrawable() {
        return this.c;
    }

    public int[] getIndicatorColor() {
        return this.d.e;
    }

    public int getIndicatorSize() {
        return this.d.b;
    }

    public void invalidateDrawable(Drawable drawable) {
        this.invalidate();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n3 = canvas.save();
        if (this.getPaddingLeft() != 0 || this.getPaddingTop() != 0) {
            canvas.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());
        }
        if (this.getPaddingRight() != 0 || this.getPaddingBottom() != 0) {
            canvas.clipRect(0, 0, this.getWidth() - (this.getPaddingLeft() + this.getPaddingRight()), this.getHeight() - (this.getPaddingTop() + this.getPaddingBottom()));
        }
        this.c.draw(canvas);
        canvas.restoreToCount(n3);
    }

    public void onMeasure(int n3, int n4) {
        int n5 = View.MeasureSpec.getMode((int)n3);
        int n6 = View.MeasureSpec.getMode((int)n4);
        int n7 = View.MeasureSpec.getSize((int)n3);
        int n8 = View.MeasureSpec.getSize((int)n4);
        c c3 = this.c.c();
        int n9 = c3.e() + this.getPaddingLeft() + this.getPaddingRight();
        int n10 = c3.d() + this.getPaddingTop() + this.getPaddingBottom();
        if (n5 == Integer.MIN_VALUE) {
            n3 = View.MeasureSpec.makeMeasureSpec((int)Math.min(n7, n9), (int)0x40000000);
        } else if (n5 == 0) {
            n3 = View.MeasureSpec.makeMeasureSpec((int)n9, (int)0x40000000);
        }
        if (n6 == Integer.MIN_VALUE) {
            n4 = View.MeasureSpec.makeMeasureSpec((int)Math.min(n8, n10), (int)0x40000000);
        } else if (n6 == 0) {
            n4 = View.MeasureSpec.makeMeasureSpec((int)n10, (int)0x40000000);
        }
        super.onMeasure(n3, n4);
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        this.c.setBounds(0, 0, n3, n4);
    }

    public void onVisibilityChanged(View object, int n3) {
        super.onVisibilityChanged((View)object, n3);
        object = this.c;
        boolean bl = this.b();
        boolean bl2 = n3 == 0;
        ((b)((Object)object)).f(bl, false, bl2);
    }

    public void onWindowVisibilityChanged(int n3) {
        super.onWindowVisibilityChanged(n3);
        b b3 = this.c;
        boolean bl = this.b();
        boolean bl2 = n3 == 0;
        b3.f(bl, false, bl2);
    }

    public void setAnimatorDurationScaleProvider(r2.a a4) {
        this.c.c = a4;
    }

    public void setContainerColor(int n3) {
        LoadingIndicatorSpec loadingIndicatorSpec = this.d;
        if (loadingIndicatorSpec.f != n3) {
            loadingIndicatorSpec.f = n3;
            this.invalidate();
        }
    }

    public void setContainerHeight(int n3) {
        LoadingIndicatorSpec loadingIndicatorSpec = this.d;
        if (loadingIndicatorSpec.d != n3) {
            loadingIndicatorSpec.d = n3;
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setContainerWidth(int n3) {
        LoadingIndicatorSpec loadingIndicatorSpec = this.d;
        if (loadingIndicatorSpec.c != n3) {
            loadingIndicatorSpec.c = n3;
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setIndicatorColor(int ... nArray) {
        int[] nArray2 = nArray;
        if (nArray.length == 0) {
            nArray2 = new int[]{h2.a.b(this.getContext(), a.colorPrimary, -1)};
        }
        if (!Arrays.equals(this.getIndicatorColor(), nArray2)) {
            this.d.e = nArray2;
            this.c.b().h();
            this.invalidate();
        }
    }

    public void setIndicatorSize(int n3) {
        LoadingIndicatorSpec loadingIndicatorSpec = this.d;
        if (loadingIndicatorSpec.b != n3) {
            loadingIndicatorSpec.b = n3;
            this.requestLayout();
            this.invalidate();
        }
    }
}

