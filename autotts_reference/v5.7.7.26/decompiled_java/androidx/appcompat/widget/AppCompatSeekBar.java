/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.SeekBar
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.n;
import c.a;

public class AppCompatSeekBar
extends SeekBar {
    public final n c;

    public AppCompatSeekBar(Context context) {
        this(context, null);
    }

    public AppCompatSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.seekBarStyle);
    }

    public AppCompatSeekBar(Context object, AttributeSet attributeSet, int n3) {
        super((Context)object, attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new n(this);
        this.c = object;
        ((n)object).c(attributeSet, n3);
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        this.c.h();
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.c.i();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onDraw(Canvas canvas) {
        synchronized (this) {
            super.onDraw(canvas);
            this.c.g(canvas);
            return;
        }
    }
}

