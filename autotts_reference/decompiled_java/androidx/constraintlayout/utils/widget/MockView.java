/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 */
package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import y.d;

public class MockView
extends View {
    public Paint c = new Paint();
    public Paint d = new Paint();
    public Paint e = new Paint();
    public boolean f = true;
    public boolean g = true;
    public String h = null;
    public Rect i = new Rect();
    public int j = Color.argb((int)255, (int)0, (int)0, (int)0);
    public int k = Color.argb((int)255, (int)200, (int)200, (int)200);
    public int l = Color.argb((int)255, (int)50, (int)50, (int)50);
    public int m = 4;

    public MockView(Context context) {
        super(context);
        this.a(context, null);
    }

    public MockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a(context, attributeSet);
    }

    public MockView(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.a(context, attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            attributeSet = context.obtainStyledAttributes(attributeSet, y.d.MockView);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.MockView_mock_label) {
                    this.h = attributeSet.getString(n4);
                    continue;
                }
                if (n4 == y.d.MockView_mock_showDiagonals) {
                    this.f = attributeSet.getBoolean(n4, this.f);
                    continue;
                }
                if (n4 == y.d.MockView_mock_diagonalsColor) {
                    this.j = attributeSet.getColor(n4, this.j);
                    continue;
                }
                if (n4 == y.d.MockView_mock_labelBackgroundColor) {
                    this.l = attributeSet.getColor(n4, this.l);
                    continue;
                }
                if (n4 == y.d.MockView_mock_labelColor) {
                    this.k = attributeSet.getColor(n4, this.k);
                    continue;
                }
                if (n4 != y.d.MockView_mock_showLabel) continue;
                this.g = attributeSet.getBoolean(n4, this.g);
            }
            attributeSet.recycle();
        }
        if (this.h == null) {
            try {
                this.h = context.getResources().getResourceEntryName(this.getId());
            }
            catch (Exception exception) {}
        }
        this.c.setColor(this.j);
        this.c.setAntiAlias(true);
        this.d.setColor(this.k);
        this.d.setAntiAlias(true);
        this.e.setColor(this.l);
        this.m = Math.round((float)this.m * (this.getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    public void onDraw(Canvas canvas) {
        String string;
        float f3;
        float f4;
        super.onDraw(canvas);
        int n3 = this.getWidth();
        int n4 = this.getHeight();
        if (this.f) {
            f4 = --n3;
            f3 = --n4;
            string = this.c;
            canvas.drawLine(0.0f, 0.0f, f4, f3, (Paint)string);
            canvas.drawLine(0.0f, f3, f4, 0.0f, this.c);
            canvas.drawLine(0.0f, 0.0f, f4, 0.0f, this.c);
            canvas.drawLine(f4, 0.0f, f4, f3, this.c);
            canvas.drawLine(f4, f3, 0.0f, f3, this.c);
            canvas.drawLine(0.0f, f3, 0.0f, 0.0f, this.c);
        }
        if ((string = this.h) != null && this.g) {
            this.d.getTextBounds(string, 0, string.length(), this.i);
            f4 = (float)(n3 - this.i.width()) / 2.0f;
            f3 = (float)(n4 - this.i.height()) / 2.0f + (float)this.i.height();
            this.i.offset((int)f4, (int)f3);
            string = this.i;
            n4 = ((Rect)string).left;
            n3 = this.m;
            string.set(n4 - n3, ((Rect)string).top - n3, ((Rect)string).right + n3, ((Rect)string).bottom + n3);
            canvas.drawRect(this.i, this.e);
            canvas.drawText(this.h, f4, f3, this.d);
        }
    }
}

