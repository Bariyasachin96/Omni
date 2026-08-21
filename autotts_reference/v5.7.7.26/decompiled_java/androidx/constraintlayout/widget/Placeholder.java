/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.view.View
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import u.e;
import y.d;

public class Placeholder
extends View {
    public int c = -1;
    public View d = null;
    public int e = 4;

    public Placeholder(Context context) {
        super(context);
        this.a(null);
    }

    public Placeholder(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a(attributeSet);
    }

    public Placeholder(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.a(attributeSet);
    }

    public final void a(AttributeSet attributeSet) {
        super.setVisibility(this.e);
        this.c = -1;
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_placeholder);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.ConstraintLayout_placeholder_content) {
                    this.c = attributeSet.getResourceId(n4, this.c);
                    continue;
                }
                if (n4 != y.d.ConstraintLayout_placeholder_placeholder_emptyVisibility) continue;
                this.e = attributeSet.getInt(n4, this.e);
            }
            attributeSet.recycle();
        }
    }

    public void b(ConstraintLayout object) {
        if (this.d == null) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)this.getLayoutParams();
        object = (ConstraintLayout.LayoutParams)this.d.getLayoutParams();
        ((ConstraintLayout.LayoutParams)((Object)object)).v0.o1(0);
        e.b b3 = layoutParams.v0.C();
        e.b b4 = e.b.c;
        if (b3 != b4) {
            layoutParams.v0.p1(((ConstraintLayout.LayoutParams)((Object)object)).v0.Y());
        }
        if (layoutParams.v0.V() != b4) {
            layoutParams.v0.Q0(((ConstraintLayout.LayoutParams)((Object)object)).v0.z());
        }
        ((ConstraintLayout.LayoutParams)((Object)object)).v0.o1(8);
    }

    public void c(ConstraintLayout constraintLayout) {
        if (this.c == -1 && !this.isInEditMode()) {
            this.setVisibility(this.e);
        }
        constraintLayout = constraintLayout.findViewById(this.c);
        this.d = constraintLayout;
        if (constraintLayout != null) {
            ((ConstraintLayout.LayoutParams)constraintLayout.getLayoutParams()).j0 = true;
            this.d.setVisibility(0);
            this.setVisibility(0);
        }
    }

    public View getContent() {
        return this.d;
    }

    public int getEmptyVisibility() {
        return this.e;
    }

    public void onDraw(Canvas canvas) {
        if (this.isInEditMode()) {
            canvas.drawRGB(223, 223, 223);
            Paint paint = new Paint();
            paint.setARGB(255, 210, 210, 210);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create((Typeface)Typeface.DEFAULT, (int)0));
            Rect rect = new Rect();
            canvas.getClipBounds(rect);
            paint.setTextSize((float)rect.height());
            int n3 = rect.height();
            int n4 = rect.width();
            paint.setTextAlign(Paint.Align.LEFT);
            paint.getTextBounds("?", 0, 1, rect);
            canvas.drawText("?", (float)n4 / 2.0f - (float)rect.width() / 2.0f - (float)rect.left, (float)n3 / 2.0f + (float)rect.height() / 2.0f - (float)rect.bottom, paint);
        }
    }

    public void setContentId(int n3) {
        if (this.c != n3) {
            View view = this.d;
            if (view != null) {
                view.setVisibility(0);
                ((ConstraintLayout.LayoutParams)this.d.getLayoutParams()).j0 = false;
                this.d = null;
            }
            this.c = n3;
            if (n3 != -1 && (view = ((View)this.getParent()).findViewById(n3)) != null) {
                view.setVisibility(8);
            }
        }
    }

    public void setEmptyVisibility(int n3) {
        this.e = n3;
    }
}

