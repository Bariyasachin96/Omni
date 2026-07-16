/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package m1;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import m1.a;
import m1.b0;
import m1.e;
import m1.f;
import m1.n;

public class h
extends ViewGroup
implements e {
    public ViewGroup c;
    public View d;
    public final View e;
    public int f;
    public Matrix g;
    public final ViewTreeObserver.OnPreDrawListener h = new ViewTreeObserver.OnPreDrawListener(this){
        public final h c;
        {
            this.c = h3;
        }

        public boolean onPreDraw() {
            this.c.postInvalidateOnAnimation();
            h h3 = this.c;
            ViewGroup viewGroup = h3.c;
            if (viewGroup != null && (h3 = h3.d) != null) {
                viewGroup.endViewTransition((View)h3);
                this.c.c.postInvalidateOnAnimation();
                viewGroup = this.c;
                viewGroup.c = null;
                viewGroup.d = null;
            }
            return true;
        }
    };

    public h(View view) {
        super(view.getContext());
        this.e = view;
        this.setWillNotDraw(false);
        this.setClipChildren(false);
        this.setLayerType(2, null);
    }

    public static h b(View object, ViewGroup viewGroup, Matrix object2) {
        if (object.getParent() instanceof ViewGroup) {
            int n3;
            f f3;
            f f4 = m1.f.b(viewGroup);
            h h3 = m1.h.e((View)object);
            if (h3 != null && (f3 = (f)h3.getParent()) != f4) {
                n3 = h3.f;
                f3.removeView((View)h3);
                h3 = null;
            } else {
                n3 = 0;
            }
            if (h3 == null) {
                h3 = object2;
                if (object2 == null) {
                    h3 = new Matrix();
                    m1.h.c((View)object, viewGroup, (Matrix)h3);
                }
                object2 = new h((View)object);
                ((h)object2).h((Matrix)h3);
                if (f4 == null) {
                    object = new f(viewGroup);
                } else {
                    f4.g();
                    object = f4;
                }
                m1.h.d((View)viewGroup, (View)object);
                m1.h.d((View)viewGroup, (View)object2);
                ((f)((Object)object)).a((h)object2);
                ((h)object2).f = n3;
                object = object2;
            } else {
                object = h3;
                if (object2 != null) {
                    h3.h((Matrix)object2);
                    object = h3;
                }
            }
            ++((h)object).f;
            return object;
        }
        throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
    }

    public static void c(View view, ViewGroup viewGroup, Matrix matrix) {
        view = (ViewGroup)view.getParent();
        matrix.reset();
        b0.h(view, matrix);
        matrix.preTranslate((float)(-view.getScrollX()), (float)(-view.getScrollY()));
        b0.i((View)viewGroup, matrix);
    }

    public static void d(View view, View view2) {
        b0.e(view2, view2.getLeft(), view2.getTop(), view2.getLeft() + view.getWidth(), view2.getTop() + view.getHeight());
    }

    public static h e(View view) {
        return (h)view.getTag(n.ghost_view);
    }

    public static void f(View object) {
        if ((object = m1.h.e(object)) != null) {
            int n3;
            object.f = n3 = object.f - 1;
            if (n3 <= 0) {
                ((f)object.getParent()).removeView((View)object);
            }
        }
    }

    public static void g(View view, h h3) {
        view.setTag(n.ghost_view, (Object)h3);
    }

    @Override
    public void a(ViewGroup viewGroup, View view) {
        this.c = viewGroup;
        this.d = view;
    }

    public void h(Matrix matrix) {
        this.g = matrix;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        m1.h.g(this.e, this);
        this.e.getViewTreeObserver().addOnPreDrawListener(this.h);
        b0.g(this.e, 4);
        if (this.e.getParent() != null) {
            ((View)this.e.getParent()).invalidate();
        }
    }

    public void onDetachedFromWindow() {
        this.e.getViewTreeObserver().removeOnPreDrawListener(this.h);
        b0.g(this.e, 0);
        m1.h.g(this.e, null);
        if (this.e.getParent() != null) {
            ((View)this.e.getParent()).invalidate();
        }
        super.onDetachedFromWindow();
    }

    public void onDraw(Canvas canvas) {
        a.a(canvas, true);
        canvas.setMatrix(this.g);
        b0.g(this.e, 0);
        this.e.invalidate();
        b0.g(this.e, 4);
        this.drawChild(canvas, this.e, this.getDrawingTime());
        a.a(canvas, false);
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
    }

    @Override
    public void setVisibility(int n3) {
        super.setVisibility(n3);
        if (m1.h.e(this.e) == this) {
            n3 = n3 == 0 ? 4 : 0;
            b0.g(this.e, n3);
        }
    }
}

