/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ProgressBar
 *  android.widget.RatingBar
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.l;
import c.a;

public class AppCompatRatingBar
extends RatingBar {
    public final l c;

    public AppCompatRatingBar(Context context) {
        this(context, null);
    }

    public AppCompatRatingBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.ratingBarStyle);
    }

    public AppCompatRatingBar(Context object, AttributeSet attributeSet, int n3) {
        super((Context)object, attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new l((ProgressBar)this);
        this.c = object;
        ((l)object).c(attributeSet, n3);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onMeasure(int n3, int n4) {
        synchronized (this) {
            Throwable throwable2;
            block4: {
                try {
                    super.onMeasure(n3, n4);
                    Bitmap bitmap = this.c.b();
                    if (bitmap == null) break block4;
                    this.setMeasuredDimension(View.resolveSizeAndState((int)(bitmap.getWidth() * this.getNumStars()), (int)n3, (int)0), this.getMeasuredHeight());
                }
                catch (Throwable throwable2) {}
            }
            return;
            throw throwable2;
        }
    }
}

