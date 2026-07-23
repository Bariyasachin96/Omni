/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.LayoutTransition
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowInsets
 *  android.widget.FrameLayout
 */
package androidx.fragment.app;

import a1.c;
import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import o0.x0;
import o0.z1;
import o3.g;
import o3.k;

public final class FragmentContainerView
extends FrameLayout {
    public final List c;
    public final List d;
    public View.OnApplyWindowInsetsListener e;
    public boolean f;

    public FragmentContainerView(Context context) {
        k.e(context, "context");
        super(context);
        this.c = new ArrayList();
        this.d = new ArrayList();
        this.f = true;
    }

    public FragmentContainerView(Context context, AttributeSet attributeSet) {
        k.e(context, "context");
        this(context, attributeSet, 0, 4, null);
    }

    public FragmentContainerView(Context object, AttributeSet object2, int n3) {
        k.e(object, "context");
        super(object, object2, n3);
        this.c = new ArrayList();
        this.d = new ArrayList();
        this.f = true;
        if (object2 != null) {
            CharSequence charSequence = object2.getClassAttribute();
            Object object3 = a1.c.FragmentContainerView;
            k.d(object3, "FragmentContainerView");
            object3 = object.obtainStyledAttributes(object2, object3, 0, 0);
            if (charSequence == null) {
                object = object3.getString(a1.c.FragmentContainerView_android_name);
                object2 = "android:name";
            } else {
                object2 = "class";
                object = charSequence;
            }
            object3.recycle();
            if (object != null && !this.isInEditMode()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("FragmentContainerView must be within a FragmentActivity to use ");
                ((StringBuilder)charSequence).append((String)object2);
                ((StringBuilder)charSequence).append("=\"");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append('\"');
                throw new UnsupportedOperationException(((StringBuilder)charSequence).toString());
            }
        }
    }

    public /* synthetic */ FragmentContainerView(Context context, AttributeSet attributeSet, int n3, int n4, g g3) {
        if ((n4 & 4) != 0) {
            n3 = 0;
        }
        this(context, attributeSet, n3);
    }

    public FragmentContainerView(Context object, AttributeSet object2, FragmentManager fragmentManager) {
        k.e(object, "context");
        k.e(object2, "attrs");
        k.e(fragmentManager, "fm");
        super((Context)object, (AttributeSet)object2);
        this.c = new ArrayList();
        this.d = new ArrayList();
        this.f = true;
        String string = object2.getClassAttribute();
        Object object3 = a1.c.FragmentContainerView;
        k.d(object3, "FragmentContainerView");
        Object object4 = object.obtainStyledAttributes((AttributeSet)object2, (int[])object3, 0, 0);
        object3 = string;
        if (string == null) {
            object3 = object4.getString(a1.c.FragmentContainerView_android_name);
        }
        string = object4.getString(a1.c.FragmentContainerView_android_tag);
        object4.recycle();
        int n3 = this.getId();
        object4 = fragmentManager.h0(n3);
        if (object3 != null && object4 == null) {
            if (n3 == -1) {
                if (string != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" with tag ");
                    ((StringBuilder)object).append(string);
                    object = ((StringBuilder)object).toString();
                } else {
                    object = "";
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("FragmentContainerView must have an android:id to add Fragment ");
                ((StringBuilder)object2).append((String)object3);
                ((StringBuilder)object2).append((String)object);
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }
            object3 = fragmentManager.t0().a(object.getClassLoader(), (String)object3);
            k.d(object3, "fm.fragmentFactory.insta…ontext.classLoader, name)");
            ((Fragment)object3).w0((Context)object, (AttributeSet)object2, null);
            fragmentManager.o().q(true).c((ViewGroup)this, (Fragment)object3, string).i();
        }
        fragmentManager.S0(this);
    }

    public final void a(View view) {
        if (this.d.contains(view)) {
            this.c.add(view);
        }
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams object) {
        k.e(view, "child");
        if (FragmentManager.C0(view) != null) {
            super.addView(view, n3, (ViewGroup.LayoutParams)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Views added to a FragmentContainerView must be associated with a Fragment. View ");
        ((StringBuilder)object).append(view);
        ((StringBuilder)object).append(" is not associated with a Fragment.");
        throw new IllegalStateException(((StringBuilder)object).toString().toString());
    }

    public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
        k.e(windowInsets, "insets");
        Object object = z1.w(windowInsets);
        k.d(object, "toWindowInsetsCompat(insets)");
        Object object2 = this.e;
        if (object2 != null) {
            object = a.a;
            k.b(object2);
            object2 = z1.w(((a)object).a((View.OnApplyWindowInsetsListener)object2, (View)this, windowInsets));
        } else {
            object2 = x0.T((View)this, (z1)object);
        }
        k.d(object2, "if (applyWindowInsetsLis…, insetsCompat)\n        }");
        if (!((z1)object2).p()) {
            int n3 = this.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                x0.g(this.getChildAt(i3), (z1)object2);
            }
        }
        return windowInsets;
    }

    public void dispatchDraw(Canvas canvas) {
        k.e(canvas, "canvas");
        if (this.f) {
            Iterator iterator = this.c.iterator();
            while (iterator.hasNext()) {
                super.drawChild(canvas, (View)iterator.next(), this.getDrawingTime());
            }
        }
        super.dispatchDraw(canvas);
    }

    public boolean drawChild(Canvas canvas, View view, long l3) {
        k.e(canvas, "canvas");
        k.e(view, "child");
        if (this.f && !this.c.isEmpty() && this.c.contains(view)) {
            return false;
        }
        return super.drawChild(canvas, view, l3);
    }

    public void endViewTransition(View view) {
        k.e(view, "view");
        this.d.remove(view);
        if (this.c.remove(view)) {
            this.f = true;
        }
        super.endViewTransition(view);
    }

    public final <F extends Fragment> F getFragment() {
        return (F)FragmentManager.k0((View)this).h0(this.getId());
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        k.e(windowInsets, "insets");
        return windowInsets;
    }

    public void removeAllViewsInLayout() {
        for (int i3 = this.getChildCount() - 1; -1 < i3; --i3) {
            View view = this.getChildAt(i3);
            k.d(view, "view");
            this.a(view);
        }
        super.removeAllViewsInLayout();
    }

    public void removeView(View view) {
        k.e(view, "view");
        this.a(view);
        super.removeView(view);
    }

    public void removeViewAt(int n3) {
        View view = this.getChildAt(n3);
        k.d(view, "view");
        this.a(view);
        super.removeViewAt(n3);
    }

    public void removeViewInLayout(View view) {
        k.e(view, "view");
        this.a(view);
        super.removeViewInLayout(view);
    }

    public void removeViews(int n3, int n4) {
        for (int i3 = n3; i3 < n3 + n4; ++i3) {
            View view = this.getChildAt(i3);
            k.d(view, "view");
            this.a(view);
        }
        super.removeViews(n3, n4);
    }

    public void removeViewsInLayout(int n3, int n4) {
        for (int i3 = n3; i3 < n3 + n4; ++i3) {
            View view = this.getChildAt(i3);
            k.d(view, "view");
            this.a(view);
        }
        super.removeViewsInLayout(n3, n4);
    }

    public final void setDrawDisappearingViewsLast(boolean bl) {
        this.f = bl;
    }

    public void setLayoutTransition(LayoutTransition layoutTransition) {
        throw new UnsupportedOperationException("FragmentContainerView does not support Layout Transitions or animateLayoutChanges=\"true\".");
    }

    public void setOnApplyWindowInsetsListener(View.OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        k.e(onApplyWindowInsetsListener, "listener");
        this.e = onApplyWindowInsetsListener;
    }

    public void startViewTransition(View view) {
        k.e(view, "view");
        if (view.getParent() == this) {
            this.d.add(view);
        }
        super.startViewTransition(view);
    }

    public static final class a {
        public static final a a = new a();

        public final WindowInsets a(View.OnApplyWindowInsetsListener onApplyWindowInsetsListener, View view, WindowInsets windowInsets) {
            k.e(onApplyWindowInsetsListener, "onApplyWindowInsetsListener");
            k.e(view, "v");
            k.e(windowInsets, "insets");
            onApplyWindowInsetsListener = onApplyWindowInsetsListener.onApplyWindowInsets(view, windowInsets);
            k.d(onApplyWindowInsetsListener, "onApplyWindowInsetsListe…lyWindowInsets(v, insets)");
            return onApplyWindowInsetsListener;
        }
    }
}

