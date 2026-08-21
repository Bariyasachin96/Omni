/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ImageButton
 *  android.widget.ImageView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.widget.d;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.j0;
import androidx.appcompat.widget.k;
import c.a;

public class AppCompatImageButton
extends ImageButton {
    public final d c;
    public final k d;
    public boolean e = false;

    public AppCompatImageButton(Context context) {
        this(context, null);
    }

    public AppCompatImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.imageButtonStyle);
    }

    public AppCompatImageButton(Context object, AttributeSet attributeSet, int n3) {
        super(j0.b((Context)object), attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new d((View)this);
        this.c = object;
        ((d)object).e(attributeSet, n3);
        this.d = object = new k((ImageView)this);
        ((k)object).g(attributeSet, n3);
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        Object object = this.c;
        if (object != null) {
            ((d)object).b();
        }
        if ((object = this.d) != null) {
            ((k)object).c();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        d d3 = this.c;
        if (d3 != null) {
            return d3.c();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        d d3 = this.c;
        if (d3 != null) {
            return d3.d();
        }
        return null;
    }

    public ColorStateList getSupportImageTintList() {
        k k3 = this.d;
        if (k3 != null) {
            return k3.d();
        }
        return null;
    }

    public PorterDuff.Mode getSupportImageTintMode() {
        k k3 = this.d;
        if (k3 != null) {
            return k3.e();
        }
        return null;
    }

    public boolean hasOverlappingRendering() {
        return this.d.f() && super.hasOverlappingRendering();
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        d d3 = this.c;
        if (d3 != null) {
            d3.f(drawable);
        }
    }

    public void setBackgroundResource(int n3) {
        super.setBackgroundResource(n3);
        d d3 = this.c;
        if (d3 != null) {
            d3.g(n3);
        }
    }

    public void setImageBitmap(Bitmap object) {
        super.setImageBitmap((Bitmap)object);
        object = this.d;
        if (object != null) {
            ((k)object).c();
        }
    }

    public void setImageDrawable(Drawable object) {
        k k3 = this.d;
        if (k3 != null && object != null && !this.e) {
            k3.h((Drawable)object);
        }
        super.setImageDrawable((Drawable)object);
        object = this.d;
        if (object != null) {
            ((k)object).c();
            if (!this.e) {
                this.d.b();
            }
        }
    }

    public void setImageLevel(int n3) {
        super.setImageLevel(n3);
        this.e = true;
    }

    public void setImageResource(int n3) {
        this.d.i(n3);
    }

    public void setImageURI(Uri object) {
        super.setImageURI((Uri)object);
        object = this.d;
        if (object != null) {
            ((k)object).c();
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        d d3 = this.c;
        if (d3 != null) {
            d3.i(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        d d3 = this.c;
        if (d3 != null) {
            d3.j(mode);
        }
    }

    public void setSupportImageTintList(ColorStateList colorStateList) {
        k k3 = this.d;
        if (k3 != null) {
            k3.j(colorStateList);
        }
    }

    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        k k3 = this.d;
        if (k3 != null) {
            k3.k(mode);
        }
    }
}

