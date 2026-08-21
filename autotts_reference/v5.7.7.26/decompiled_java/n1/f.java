/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources$Theme
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.drawable.Drawable
 */
package n1;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import h0.a;

public abstract class f
extends Drawable {
    public Drawable c;

    public void applyTheme(Resources.Theme theme) {
        Drawable drawable = this.c;
        if (drawable != null) {
            a.a(drawable, theme);
        }
    }

    public void clearColorFilter() {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.clearColorFilter();
            return;
        }
        super.clearColorFilter();
    }

    public Drawable getCurrent() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getCurrent();
        }
        return super.getCurrent();
    }

    public int getMinimumHeight() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getMinimumHeight();
        }
        return super.getMinimumHeight();
    }

    public int getMinimumWidth() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getMinimumWidth();
        }
        return super.getMinimumWidth();
    }

    public boolean getPadding(Rect rect) {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getPadding(rect);
        }
        return super.getPadding(rect);
    }

    public int[] getState() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getState();
        }
        return super.getState();
    }

    public Region getTransparentRegion() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getTransparentRegion();
        }
        return super.getTransparentRegion();
    }

    public void jumpToCurrentState() {
        Drawable drawable = this.c;
        if (drawable != null) {
            a.i(drawable);
        }
    }

    public boolean onLevelChange(int n3) {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setLevel(n3);
        }
        return super.onLevelChange(n3);
    }

    public void setChangingConfigurations(int n3) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setChangingConfigurations(n3);
            return;
        }
        super.setChangingConfigurations(n3);
    }

    public void setColorFilter(int n3, PorterDuff.Mode mode) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setColorFilter(n3, mode);
            return;
        }
        super.setColorFilter(n3, mode);
    }

    public void setFilterBitmap(boolean bl) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setFilterBitmap(bl);
        }
    }

    public void setHotspot(float f3, float f4) {
        Drawable drawable = this.c;
        if (drawable != null) {
            a.k(drawable, f3, f4);
        }
    }

    public void setHotspotBounds(int n3, int n4, int n5, int n6) {
        Drawable drawable = this.c;
        if (drawable != null) {
            a.l(drawable, n3, n4, n5, n6);
        }
    }

    public boolean setState(int[] nArray) {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setState(nArray);
        }
        return super.setState(nArray);
    }
}

