/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package a0;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public abstract class c {
    public static final ThreadLocal a = new ThreadLocal();
    public static final ThreadLocal b = new ThreadLocal();

    public static void a(ViewGroup viewGroup, View view, Rect rect) {
        rect.set(0, 0, view.getWidth(), view.getHeight());
        c.c(viewGroup, view, rect);
    }

    public static void b(ViewParent viewParent, View view, Matrix matrix) {
        ViewParent viewParent2 = view.getParent();
        if (viewParent2 instanceof View && viewParent2 != viewParent) {
            viewParent2 = (View)viewParent2;
            c.b(viewParent, (View)viewParent2, matrix);
            matrix.preTranslate((float)(-viewParent2.getScrollX()), (float)(-viewParent2.getScrollY()));
        }
        matrix.preTranslate((float)view.getLeft(), (float)view.getTop());
        if (!view.getMatrix().isIdentity()) {
            matrix.preConcat(view.getMatrix());
        }
    }

    public static void c(ViewGroup viewGroup, View view, Rect rect) {
        ThreadLocal threadLocal = a;
        Matrix matrix = (Matrix)threadLocal.get();
        if (matrix == null) {
            matrix = new Matrix();
            threadLocal.set(matrix);
        } else {
            matrix.reset();
        }
        c.b((ViewParent)viewGroup, view, matrix);
        threadLocal = b;
        view = (RectF)threadLocal.get();
        viewGroup = view;
        if (view == null) {
            viewGroup = new RectF();
            threadLocal.set(viewGroup);
        }
        viewGroup.set(rect);
        matrix.mapRect((RectF)viewGroup);
        rect.set((int)(viewGroup.left + 0.5f), (int)(viewGroup.top + 0.5f), (int)(viewGroup.right + 0.5f), (int)(viewGroup.bottom + 0.5f));
    }
}

