/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.widget.ImageView$ScaleType
 */
package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import h0.a;
import java.util.Arrays;

public abstract class t {
    public static void a(TextInputLayout textInputLayout, CheckableImageButton checkableImageButton, ColorStateList colorStateList, PorterDuff.Mode mode) {
        Drawable drawable;
        Drawable drawable2 = drawable = checkableImageButton.getDrawable();
        if (drawable != null) {
            drawable = a.r(drawable).mutate();
            if (colorStateList != null && colorStateList.isStateful()) {
                drawable.setTintList(ColorStateList.valueOf((int)colorStateList.getColorForState(t.c(textInputLayout, checkableImageButton), colorStateList.getDefaultColor())));
            } else {
                drawable.setTintList(colorStateList);
            }
            drawable2 = drawable;
            if (mode != null) {
                drawable.setTintMode(mode);
                drawable2 = drawable;
            }
        }
        if (checkableImageButton.getDrawable() != drawable2) {
            checkableImageButton.setImageDrawable(drawable2);
        }
    }

    public static ImageView.ScaleType b(int n3) {
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 5) {
                            if (n3 != 6) {
                                return ImageView.ScaleType.CENTER;
                            }
                            return ImageView.ScaleType.CENTER_INSIDE;
                        }
                        return ImageView.ScaleType.CENTER_CROP;
                    }
                    return ImageView.ScaleType.FIT_END;
                }
                return ImageView.ScaleType.FIT_CENTER;
            }
            return ImageView.ScaleType.FIT_START;
        }
        return ImageView.ScaleType.FIT_XY;
    }

    public static int[] c(TextInputLayout object, CheckableImageButton object2) {
        int[] nArray = object.getDrawableState();
        object = object2.getDrawableState();
        int n3 = nArray.length;
        object2 = Arrays.copyOf(nArray, nArray.length + ((TextInputLayout)object).length);
        System.arraycopy(object, 0, object2, n3, ((TextInputLayout)object).length);
        return object2;
    }

    public static void d(TextInputLayout textInputLayout, CheckableImageButton checkableImageButton, ColorStateList colorStateList) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (checkableImageButton.getDrawable() != null && colorStateList != null && colorStateList.isStateful()) {
            int n3 = colorStateList.getColorForState(t.c(textInputLayout, checkableImageButton), colorStateList.getDefaultColor());
            textInputLayout = a.r(drawable).mutate();
            textInputLayout.setTintList(ColorStateList.valueOf((int)n3));
            checkableImageButton.setImageDrawable((Drawable)textInputLayout);
        }
    }

    public static void e(CheckableImageButton checkableImageButton) {
    }

    public static void f(CheckableImageButton checkableImageButton, View.OnLongClickListener onLongClickListener) {
        boolean bl = checkableImageButton.hasOnClickListeners();
        boolean bl2 = false;
        int n3 = 1;
        boolean bl3 = onLongClickListener != null;
        if (bl || bl3) {
            bl2 = true;
        }
        checkableImageButton.setFocusable(bl2);
        checkableImageButton.setClickable(bl);
        checkableImageButton.setPressable(bl);
        checkableImageButton.setLongClickable(bl3);
        if (!bl2) {
            n3 = 2;
        }
        checkableImageButton.setImportantForAccessibility(n3);
    }

    public static void g(CheckableImageButton checkableImageButton, int n3) {
        checkableImageButton.setMinimumWidth(n3);
        checkableImageButton.setMinimumHeight(n3);
    }

    public static void h(CheckableImageButton checkableImageButton, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        checkableImageButton.setOnClickListener(onClickListener);
        t.f(checkableImageButton, onLongClickListener);
    }

    public static void i(CheckableImageButton checkableImageButton, View.OnLongClickListener onLongClickListener) {
        checkableImageButton.setOnLongClickListener(onLongClickListener);
        t.f(checkableImageButton, onLongClickListener);
    }

    public static void j(CheckableImageButton checkableImageButton, ImageView.ScaleType scaleType) {
        checkableImageButton.setScaleType(scaleType);
    }
}

