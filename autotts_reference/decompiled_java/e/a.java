/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 */
package e;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

public abstract class a
extends Drawable
implements Drawable.Callback {
    public Drawable c;

    public a(Drawable drawable) {
        this.a(drawable);
    }

    public void a(Drawable drawable) {
        Drawable drawable2 = this.c;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.c = drawable;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this);
        }
    }

    public void draw(Canvas canvas) {
        this.c.draw(canvas);
    }

    public int getChangingConfigurations() {
        return this.c.getChangingConfigurations();
    }

    public Drawable getCurrent() {
        return this.c.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.c.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.c.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.c.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.c.getMinimumWidth();
    }

    public int getOpacity() {
        return this.c.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.c.getPadding(rect);
    }

    public int[] getState() {
        return this.c.getState();
    }

    public Region getTransparentRegion() {
        return this.c.getTransparentRegion();
    }

    public void invalidateDrawable(Drawable drawable) {
        this.invalidateSelf();
    }

    public boolean isAutoMirrored() {
        return h0.a.h(this.c);
    }

    public boolean isStateful() {
        return this.c.isStateful();
    }

    public void jumpToCurrentState() {
        this.c.jumpToCurrentState();
    }

    public void onBoundsChange(Rect rect) {
        this.c.setBounds(rect);
    }

    public boolean onLevelChange(int n3) {
        return this.c.setLevel(n3);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l3) {
        this.scheduleSelf(runnable, l3);
    }

    public void setAlpha(int n3) {
        this.c.setAlpha(n3);
    }

    public void setAutoMirrored(boolean bl) {
        h0.a.j(this.c, bl);
    }

    public void setChangingConfigurations(int n3) {
        this.c.setChangingConfigurations(n3);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.c.setColorFilter(colorFilter);
    }

    public void setDither(boolean bl) {
        this.c.setDither(bl);
    }

    public void setFilterBitmap(boolean bl) {
        this.c.setFilterBitmap(bl);
    }

    public void setHotspot(float f3, float f4) {
        h0.a.k(this.c, f3, f4);
    }

    public void setHotspotBounds(int n3, int n4, int n5, int n6) {
        h0.a.l(this.c, n3, n4, n5, n6);
    }

    public boolean setState(int[] nArray) {
        return this.c.setState(nArray);
    }

    public void setTint(int n3) {
        h0.a.n(this.c, n3);
    }

    public void setTintList(ColorStateList colorStateList) {
        h0.a.o(this.c, colorStateList);
    }

    public void setTintMode(PorterDuff.Mode mode) {
        h0.a.p(this.c, mode);
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        return super.setVisible(bl, bl2) || this.c.setVisible(bl, bl2);
        {
        }
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        this.unscheduleSelf(runnable);
    }
}

