/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewConfiguration
 */
package androidx.viewpager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;
import e0.a;

public class PagerTabStrip
extends PagerTitleStrip {
    public int A;
    public boolean B;
    public boolean C;
    public int D;
    public boolean E;
    public float F;
    public float G;
    public int H;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    public int x;
    public final Paint y;
    public final Rect z;

    public PagerTabStrip(Context context) {
        this(context, null);
    }

    public PagerTabStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int n3;
        attributeSet = new Paint();
        this.y = attributeSet;
        this.z = new Rect();
        this.A = 255;
        this.B = false;
        this.C = false;
        this.s = n3 = this.p;
        attributeSet.setColor(n3);
        float f3 = context.getResources().getDisplayMetrics().density;
        this.t = (int)(3.0f * f3 + 0.5f);
        this.u = (int)(6.0f * f3 + 0.5f);
        this.v = (int)(64.0f * f3);
        this.x = (int)(16.0f * f3 + 0.5f);
        this.D = (int)(1.0f * f3 + 0.5f);
        this.w = (int)(f3 * 32.0f + 0.5f);
        this.H = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        this.setPadding(this.getPaddingLeft(), this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        this.setTextSpacing(this.getTextSpacing());
        this.setWillNotDraw(false);
        this.d.setFocusable(true);
        this.d.setOnClickListener(new View.OnClickListener(this){
            public final PagerTabStrip c;
            {
                this.c = pagerTabStrip;
            }

            public void onClick(View object) {
                object = this.c.c;
                ((ViewPager)((Object)object)).setCurrentItem(((ViewPager)((Object)object)).getCurrentItem() - 1);
            }
        });
        this.f.setFocusable(true);
        this.f.setOnClickListener(new View.OnClickListener(this){
            public final PagerTabStrip c;
            {
                this.c = pagerTabStrip;
            }

            public void onClick(View object) {
                object = this.c.c;
                ((ViewPager)((Object)object)).setCurrentItem(((ViewPager)((Object)object)).getCurrentItem() + 1);
            }
        });
        if (this.getBackground() == null) {
            this.B = true;
        }
    }

    @Override
    public void c(int n3, float f3, boolean bl) {
        Rect rect = this.z;
        int n4 = this.getHeight();
        int n5 = this.e.getLeft();
        int n6 = this.x;
        int n7 = this.e.getRight();
        int n8 = this.x;
        int n9 = n4 - this.t;
        rect.set(n5 - n6, n9, n7 + n8, n4);
        super.c(n3, f3, bl);
        this.A = (int)(Math.abs(f3 - 0.5f) * 2.0f * 255.0f);
        rect.union(this.e.getLeft() - this.x, n9, this.e.getRight() + this.x, n4);
        this.invalidate(rect);
    }

    public boolean getDrawFullUnderline() {
        return this.B;
    }

    @Override
    public int getMinHeight() {
        return Math.max(super.getMinHeight(), this.w);
    }

    public int getTabIndicatorColor() {
        return this.s;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n3 = this.getHeight();
        int n4 = this.e.getLeft();
        int n5 = this.x;
        int n6 = this.e.getRight();
        int n7 = this.x;
        int n8 = this.t;
        this.y.setColor(this.A << 24 | this.s & 0xFFFFFF);
        float f3 = n4 - n5;
        float f4 = n3 - n8;
        float f5 = n6 + n7;
        float f6 = n3;
        canvas.drawRect(f3, f4, f5, f6, this.y);
        if (this.B) {
            this.y.setColor(this.s & 0xFFFFFF | 0xFF000000);
            canvas.drawRect((float)this.getPaddingLeft(), (float)(n3 - this.D), (float)(this.getWidth() - this.getPaddingRight()), f6, this.y);
        }
    }

    public boolean onTouchEvent(MotionEvent object) {
        int n3 = object.getAction();
        if (n3 != 0 && this.E) {
            return false;
        }
        float f3 = object.getX();
        float f4 = object.getY();
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 == 2 && (Math.abs(f3 - this.F) > (float)this.H || Math.abs(f4 - this.G) > (float)this.H)) {
                    this.E = true;
                }
            } else if (f3 < (float)(this.e.getLeft() - this.x)) {
                object = this.c;
                ((ViewPager)((Object)object)).setCurrentItem(((ViewPager)((Object)object)).getCurrentItem() - 1);
            } else if (f3 > (float)(this.e.getRight() + this.x)) {
                object = this.c;
                ((ViewPager)((Object)object)).setCurrentItem(((ViewPager)((Object)object)).getCurrentItem() + 1);
            }
        } else {
            this.F = f3;
            this.G = f4;
            this.E = false;
        }
        return true;
    }

    public void setBackgroundColor(int n3) {
        super.setBackgroundColor(n3);
        if (!this.C) {
            boolean bl = (n3 & 0xFF000000) == 0;
            this.B = bl;
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (!this.C) {
            boolean bl = drawable == null;
            this.B = bl;
        }
    }

    public void setBackgroundResource(int n3) {
        super.setBackgroundResource(n3);
        if (!this.C) {
            boolean bl = n3 == 0;
            this.B = bl;
        }
    }

    public void setDrawFullUnderline(boolean bl) {
        this.B = bl;
        this.C = true;
        this.invalidate();
    }

    public void setPadding(int n3, int n4, int n5, int n6) {
        int n7 = this.u;
        int n8 = n6;
        if (n6 < n7) {
            n8 = n7;
        }
        super.setPadding(n3, n4, n5, n8);
    }

    public void setTabIndicatorColor(int n3) {
        this.s = n3;
        this.y.setColor(n3);
        this.invalidate();
    }

    public void setTabIndicatorColorResource(int n3) {
        this.setTabIndicatorColor(a.b(this.getContext(), n3));
    }

    @Override
    public void setTextSpacing(int n3) {
        int n4 = this.v;
        int n5 = n3;
        if (n3 < n4) {
            n5 = n4;
        }
        super.setTextSpacing(n5);
    }
}

