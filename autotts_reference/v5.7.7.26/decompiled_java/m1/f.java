/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.FrameLayout
 */
package m1;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import java.util.ArrayList;
import m1.a0;
import m1.h;
import m1.n;

public class f
extends FrameLayout {
    public ViewGroup c;
    public boolean d;

    public f(ViewGroup viewGroup) {
        super(viewGroup.getContext());
        this.setClipChildren(false);
        this.c = viewGroup;
        viewGroup.setTag(n.ghost_view_holder, (Object)this);
        this.c.getOverlay().add((View)this);
        this.d = true;
    }

    public static f b(ViewGroup viewGroup) {
        return (f)((Object)viewGroup.getTag(n.ghost_view_holder));
    }

    public static void d(View view, ArrayList arrayList) {
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof ViewGroup) {
            f.d((View)viewParent, arrayList);
        }
        arrayList.add(view);
    }

    public static boolean e(View view, View view2) {
        ViewGroup viewGroup = (ViewGroup)view.getParent();
        int n3 = viewGroup.getChildCount();
        if (a.a(view) != a.a(view2)) {
            return a.a(view) > a.a(view2);
        }
        for (int i3 = 0; i3 < n3; ++i3) {
            View view3 = viewGroup.getChildAt(a0.a(viewGroup, i3));
            if (view3 == view) {
                return false;
            }
            if (view3 != view2) continue;
            return true;
        }
        return true;
    }

    public static boolean f(ArrayList arrayList, ArrayList arrayList2) {
        if (!arrayList.isEmpty() && !arrayList2.isEmpty() && arrayList.get(0) == arrayList2.get(0)) {
            int n3 = Math.min(arrayList.size(), arrayList2.size());
            for (int i3 = 1; i3 < n3; ++i3) {
                View view;
                View view2 = (View)arrayList.get(i3);
                if (view2 == (view = (View)arrayList2.get(i3))) continue;
                return f.e(view2, view);
            }
            return arrayList2.size() == n3;
        }
        return true;
    }

    public void a(h h3) {
        ArrayList arrayList = new ArrayList();
        f.d(h3.e, arrayList);
        int n3 = this.c(arrayList);
        if (n3 >= 0 && n3 < this.getChildCount()) {
            this.addView((View)h3, n3);
            return;
        }
        this.addView((View)h3);
    }

    public final int c(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int n3 = this.getChildCount() - 1;
        int n4 = 0;
        while (n4 <= n3) {
            int n5 = (n4 + n3) / 2;
            f.d(((h)this.getChildAt((int)n5)).e, arrayList2);
            if (f.f(arrayList, arrayList2)) {
                n4 = n5 + 1;
            } else {
                n3 = n5 - 1;
            }
            arrayList2.clear();
        }
        return n4;
    }

    public void g() {
        if (this.d) {
            this.c.getOverlay().remove((View)this);
            this.c.getOverlay().add((View)this);
            return;
        }
        throw new IllegalStateException("This GhostViewHolder is detached!");
    }

    public void onViewAdded(View view) {
        if (this.d) {
            super.onViewAdded(view);
            return;
        }
        throw new IllegalStateException("This GhostViewHolder is detached!");
    }

    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (this.getChildCount() == 1 && this.getChildAt(0) == view || this.getChildCount() == 0) {
            this.c.setTag(n.ghost_view_holder, null);
            this.c.getOverlay().remove((View)this);
            this.d = false;
        }
    }

    public static abstract class a {
        public static float a(View view) {
            return view.getZ();
        }
    }
}

