/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Outline
 *  android.graphics.drawable.Drawable
 */
package androidx.appcompat.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.ActionBarContainer;

public class b
extends Drawable {
    public final ActionBarContainer a;

    public b(ActionBarContainer actionBarContainer) {
        this.a = actionBarContainer;
    }

    public void draw(Canvas canvas) {
        ActionBarContainer actionBarContainer = this.a;
        if (actionBarContainer.j) {
            actionBarContainer = actionBarContainer.i;
            if (actionBarContainer != null) {
                actionBarContainer.draw(canvas);
                return;
            }
        } else {
            actionBarContainer = actionBarContainer.g;
            if (actionBarContainer != null) {
                actionBarContainer.draw(canvas);
            }
            actionBarContainer = this.a;
            Drawable drawable = actionBarContainer.h;
            if (drawable != null && actionBarContainer.k) {
                drawable.draw(canvas);
            }
        }
    }

    public int getOpacity() {
        return 0;
    }

    public void getOutline(Outline outline) {
        ActionBarContainer actionBarContainer = this.a;
        if (actionBarContainer.j) {
            if (actionBarContainer.i != null) {
                androidx.appcompat.widget.b$a.a(actionBarContainer.g, outline);
                return;
            }
        } else {
            actionBarContainer = actionBarContainer.g;
            if (actionBarContainer != null) {
                androidx.appcompat.widget.b$a.a((Drawable)actionBarContainer, outline);
            }
        }
    }

    public void setAlpha(int n3) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public static abstract class a {
        public static void a(Drawable drawable, Outline outline) {
            drawable.getOutline(outline);
        }
    }
}

