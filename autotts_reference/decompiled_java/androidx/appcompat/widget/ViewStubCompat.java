/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import c.j;
import java.lang.ref.WeakReference;

public final class ViewStubCompat
extends View {
    public int c = 0;
    public int d;
    public WeakReference e;
    public LayoutInflater f;

    public ViewStubCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewStubCompat(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        context = context.obtainStyledAttributes(attributeSet, j.ViewStubCompat, n3, 0);
        this.d = context.getResourceId(j.ViewStubCompat_android_inflatedId, -1);
        this.c = context.getResourceId(j.ViewStubCompat_android_layout, 0);
        this.setId(context.getResourceId(j.ViewStubCompat_android_id, -1));
        context.recycle();
        this.setVisibility(8);
        this.setWillNotDraw(true);
    }

    public View a() {
        ViewParent viewParent = this.getParent();
        if (viewParent instanceof ViewGroup) {
            if (this.c != 0) {
                ViewGroup viewGroup = (ViewGroup)viewParent;
                viewParent = this.f;
                if (viewParent == null) {
                    viewParent = LayoutInflater.from((Context)this.getContext());
                }
                viewParent = viewParent.inflate(this.c, viewGroup, false);
                int n3 = this.d;
                if (n3 != -1) {
                    viewParent.setId(n3);
                }
                n3 = viewGroup.indexOfChild((View)this);
                viewGroup.removeViewInLayout((View)this);
                ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
                if (layoutParams != null) {
                    viewGroup.addView((View)viewParent, n3, layoutParams);
                } else {
                    viewGroup.addView((View)viewParent, n3);
                }
                this.e = new WeakReference<ViewParent>(viewParent);
                return viewParent;
            }
            throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        }
        throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
    }

    public void dispatchDraw(Canvas canvas) {
    }

    public void draw(Canvas canvas) {
    }

    public int getInflatedId() {
        return this.d;
    }

    public LayoutInflater getLayoutInflater() {
        return this.f;
    }

    public int getLayoutResource() {
        return this.c;
    }

    public void onMeasure(int n3, int n4) {
        this.setMeasuredDimension(0, 0);
    }

    public void setInflatedId(int n3) {
        this.d = n3;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.f = layoutInflater;
    }

    public void setLayoutResource(int n3) {
        this.c = n3;
    }

    public void setOnInflateListener(a a4) {
    }

    public void setVisibility(int n3) {
        WeakReference weakReference = this.e;
        if (weakReference != null) {
            if ((weakReference = (View)weakReference.get()) != null) {
                weakReference.setVisibility(n3);
                return;
            }
            throw new IllegalStateException("setVisibility called on un-referenced view");
        }
        super.setVisibility(n3);
        if (n3 != 0 && n3 != 4) {
            return;
        }
        this.a();
    }

    public static interface a {
    }
}

