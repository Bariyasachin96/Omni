/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.util.AttributeSet
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.b;
import c.f;
import c.j;

public class ActionBarContainer
extends FrameLayout {
    public boolean c;
    public View d;
    public View e;
    public View f;
    public Drawable g;
    public Drawable h;
    public Drawable i;
    public boolean j;
    public boolean k;
    public int l;

    public ActionBarContainer(Context context) {
        this(context, null);
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBackground(new b(this));
        context = context.obtainStyledAttributes(attributeSet, c.j.ActionBar);
        this.g = context.getDrawable(c.j.ActionBar_background);
        this.h = context.getDrawable(c.j.ActionBar_backgroundStacked);
        this.l = context.getDimensionPixelSize(c.j.ActionBar_height, -1);
        int n3 = this.getId();
        int n4 = c.f.split_action_bar;
        boolean bl = true;
        if (n3 == n4) {
            this.j = true;
            this.i = context.getDrawable(c.j.ActionBar_backgroundSplit);
        }
        context.recycle();
        if (!(this.j ? this.i == null : this.g == null && this.h == null)) {
            bl = false;
        }
        this.setWillNotDraw(bl);
    }

    public final int a(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    public final boolean b(View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
        {
        }
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.g;
        if (drawable != null && drawable.isStateful()) {
            this.g.setState(this.getDrawableState());
        }
        if ((drawable = this.h) != null && drawable.isStateful()) {
            this.h.setState(this.getDrawableState());
        }
        if ((drawable = this.i) != null && drawable.isStateful()) {
            this.i.setState(this.getDrawableState());
        }
    }

    public View getTabContainer() {
        return this.d;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.g;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        if ((drawable = this.h) != null) {
            drawable.jumpToCurrentState();
        }
        if ((drawable = this.i) != null) {
            drawable.jumpToCurrentState();
        }
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.e = this.findViewById(c.f.action_bar);
        this.f = this.findViewById(c.f.action_context_bar);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.c || super.onInterceptTouchEvent(motionEvent);
        {
        }
    }

    /*
     * Unable to fully structure code
     */
    public void onLayout(boolean var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block8: {
            block9: {
                block10: {
                    super.onLayout(var1_1, var2_2, var3_3, var4_4, var5_5);
                    var10_6 = this.d;
                    var5_5 = 1;
                    var3_3 = 0;
                    var6_7 = 0;
                    var1_1 = var10_6 != null && var10_6.getVisibility() != 8;
                    if (var10_6 != null && var10_6.getVisibility() != 8) {
                        var7_8 = this.getMeasuredHeight();
                        var11_9 = (FrameLayout.LayoutParams)var10_6.getLayoutParams();
                        var9_10 = var10_6.getMeasuredHeight();
                        var8_11 = var11_9.bottomMargin;
                        var10_6.layout(var2_2, var7_8 - var9_10 - var8_11, var4_4, var7_8 - var8_11);
                    }
                    if (!this.j) break block9;
                    var10_6 = this.i;
                    var3_3 = var6_7;
                    if (var10_6 == null) break block10;
                    var10_6.setBounds(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
                    var2_2 = var5_5;
                    break block8;
                }
lbl21:
                // 3 sources

                while (true) {
                    var2_2 = var3_3;
                    break block8;
                    break;
                }
            }
            var2_2 = var3_3;
            if (this.g != null) {
                if (this.e.getVisibility() == 0) {
                    this.g.setBounds(this.e.getLeft(), this.e.getTop(), this.e.getRight(), this.e.getBottom());
                } else {
                    var11_9 = this.f;
                    if (var11_9 != null && var11_9.getVisibility() == 0) {
                        this.g.setBounds(this.f.getLeft(), this.f.getTop(), this.f.getRight(), this.f.getBottom());
                    } else {
                        this.g.setBounds(0, 0, 0, 0);
                    }
                }
                var2_2 = 1;
            }
            this.k = var1_1;
            var3_3 = var2_2;
            if (!var1_1) ** GOTO lbl21
            var11_9 = this.h;
            var3_3 = var2_2;
            if (var11_9 != null) ** break;
            ** while (true)
            var11_9.setBounds(var10_6.getLeft(), var10_6.getTop(), var10_6.getRight(), var10_6.getBottom());
            var2_2 = var5_5;
        }
        if (var2_2 != 0) {
            this.invalidate();
        }
    }

    public void onMeasure(int n3, int n4) {
        int n5 = n4;
        if (this.e == null) {
            n5 = n4;
            if (View.MeasureSpec.getMode((int)n4) == Integer.MIN_VALUE) {
                int n6 = this.l;
                n5 = n4;
                if (n6 >= 0) {
                    n5 = View.MeasureSpec.makeMeasureSpec((int)Math.min(n6, View.MeasureSpec.getSize((int)n4)), (int)Integer.MIN_VALUE);
                }
            }
        }
        super.onMeasure(n3, n5);
        if (this.e != null) {
            n4 = View.MeasureSpec.getMode((int)n5);
            View view = this.d;
            if (view != null && view.getVisibility() != 8 && n4 != 0x40000000) {
                n3 = !this.b(this.e) ? this.a(this.e) : (!this.b(this.f) ? this.a(this.f) : 0);
                n4 = n4 == Integer.MIN_VALUE ? View.MeasureSpec.getSize((int)n5) : Integer.MAX_VALUE;
                this.setMeasuredDimension(this.getMeasuredWidth(), Math.min(n3 + this.a(this.d), n4));
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPrimaryBackground(Drawable drawable) {
        boolean bl;
        block7: {
            block8: {
                boolean bl2;
                block6: {
                    Drawable drawable2 = this.g;
                    if (drawable2 != null) {
                        drawable2.setCallback(null);
                        this.unscheduleDrawable(this.g);
                    }
                    this.g = drawable;
                    if (drawable != null) {
                        drawable.setCallback((Drawable.Callback)this);
                        drawable = this.e;
                        if (drawable != null) {
                            this.g.setBounds(drawable.getLeft(), this.e.getTop(), this.e.getRight(), this.e.getBottom());
                        }
                    }
                    bl = this.j;
                    bl2 = false;
                    if (!bl) break block6;
                    bl = bl2;
                    if (this.i != null) break block7;
                    break block8;
                }
                bl = bl2;
                if (this.g != null) break block7;
                bl = bl2;
                if (this.h != null) break block7;
            }
            bl = true;
        }
        this.setWillNotDraw(bl);
        this.invalidate();
        a.a(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSplitBackground(Drawable drawable) {
        boolean bl;
        block7: {
            block8: {
                boolean bl2;
                block6: {
                    Drawable drawable2 = this.i;
                    if (drawable2 != null) {
                        drawable2.setCallback(null);
                        this.unscheduleDrawable(this.i);
                    }
                    this.i = drawable;
                    bl2 = false;
                    if (drawable != null) {
                        drawable.setCallback((Drawable.Callback)this);
                        if (this.j && (drawable = this.i) != null) {
                            drawable.setBounds(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
                        }
                    }
                    if (!this.j) break block6;
                    bl = bl2;
                    if (this.i != null) break block7;
                    break block8;
                }
                bl = bl2;
                if (this.g != null) break block7;
                bl = bl2;
                if (this.h != null) break block7;
            }
            bl = true;
        }
        this.setWillNotDraw(bl);
        this.invalidate();
        a.a(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStackedBackground(Drawable drawable) {
        boolean bl;
        block7: {
            block8: {
                boolean bl2;
                block6: {
                    Drawable drawable2 = this.h;
                    if (drawable2 != null) {
                        drawable2.setCallback(null);
                        this.unscheduleDrawable(this.h);
                    }
                    this.h = drawable;
                    if (drawable != null) {
                        drawable.setCallback((Drawable.Callback)this);
                        if (this.k && (drawable = this.h) != null) {
                            drawable.setBounds(this.d.getLeft(), this.d.getTop(), this.d.getRight(), this.d.getBottom());
                        }
                    }
                    bl = this.j;
                    bl2 = false;
                    if (!bl) break block6;
                    bl = bl2;
                    if (this.i != null) break block7;
                    break block8;
                }
                bl = bl2;
                if (this.g != null) break block7;
                bl = bl2;
                if (this.h != null) break block7;
            }
            bl = true;
        }
        this.setWillNotDraw(bl);
        this.invalidate();
        a.a(this);
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        View view = this.d;
        if (view != null) {
            this.removeView(view);
        }
        this.d = scrollingTabContainerView;
        if (scrollingTabContainerView != null) {
            this.addView((View)scrollingTabContainerView);
            view = scrollingTabContainerView.getLayoutParams();
            view.width = -1;
            view.height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean bl) {
        this.c = bl;
        int n3 = bl ? 393216 : 262144;
        this.setDescendantFocusability(n3);
    }

    public void setVisibility(int n3) {
        super.setVisibility(n3);
        boolean bl = n3 == 0;
        Drawable drawable = this.g;
        if (drawable != null) {
            drawable.setVisible(bl, false);
        }
        if ((drawable = this.h) != null) {
            drawable.setVisible(bl, false);
        }
        if ((drawable = this.i) != null) {
            drawable.setVisible(bl, false);
        }
    }

    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int n3) {
        if (n3 != 0) {
            return super.startActionModeForChild(view, callback, n3);
        }
        return null;
    }

    public boolean verifyDrawable(Drawable drawable) {
        return drawable == this.g && !this.j || drawable == this.h && this.k || drawable == this.i && this.j || super.verifyDrawable(drawable);
    }

    public static abstract class a {
        public static void a(ActionBarContainer actionBarContainer) {
            actionBarContainer.invalidateOutline();
        }
    }
}

