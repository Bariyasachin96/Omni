/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package androidx.recyclerview.widget;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.g;
import androidx.recyclerview.widget.i;
import androidx.recyclerview.widget.n;

public class j
extends n {
    public i d;
    public i e;

    @Override
    public int[] c(RecyclerView.p p3, View view) {
        int[] nArray = new int[2];
        nArray[0] = p3.p() ? this.k(view, this.m(p3)) : 0;
        if (p3.q()) {
            nArray[1] = this.k(view, this.o(p3));
            return nArray;
        }
        nArray[1] = 0;
        return nArray;
    }

    @Override
    public RecyclerView.y d(RecyclerView.p p3) {
        if (!(p3 instanceof RecyclerView.y.b)) {
            return null;
        }
        return new g(this, this.a.getContext()){
            public final j q;
            {
                this.q = j3;
                super(context);
            }

            @Override
            public void o(View object, RecyclerView.z object2, RecyclerView.y.a a4) {
                object2 = this.q;
                object = ((j)object2).c(((n)object2).a.getLayoutManager(), (View)object);
                View view = object[0];
                View view2 = object[1];
                int n3 = this.w(Math.max(Math.abs((int)view), Math.abs((int)view2)));
                if (n3 > 0) {
                    a4.d((int)view, (int)view2, n3, (Interpolator)this.j);
                }
            }

            @Override
            public float v(DisplayMetrics displayMetrics) {
                return 100.0f / (float)displayMetrics.densityDpi;
            }

            @Override
            public int x(int n3) {
                return Math.min(100, super.x(n3));
            }
        };
    }

    @Override
    public View f(RecyclerView.p p3) {
        if (p3.q()) {
            return this.l(p3, this.o(p3));
        }
        if (p3.p()) {
            return this.l(p3, this.m(p3));
        }
        return null;
    }

    @Override
    public int g(RecyclerView.p p3, int n3, int n4) {
        int n5 = p3.e();
        if (n5 == 0) {
            return -1;
        }
        i i3 = this.n(p3);
        if (i3 == null) {
            return -1;
        }
        int n6 = p3.O();
        View view = null;
        int n7 = Integer.MAX_VALUE;
        int n8 = Integer.MIN_VALUE;
        View view2 = null;
        for (int i4 = 0; i4 < n6; ++i4) {
            int n9;
            View view3;
            View view4 = p3.N(i4);
            if (view4 == null) {
                view3 = view;
                n9 = n7;
            } else {
                int n10 = this.k(view4, i3);
                View view5 = view2;
                int n11 = n8;
                if (n10 <= 0) {
                    view5 = view2;
                    n11 = n8;
                    if (n10 > n8) {
                        view5 = view4;
                        n11 = n10;
                    }
                }
                view3 = view;
                view2 = view5;
                n8 = n11;
                n9 = n7;
                if (n10 >= 0) {
                    view3 = view;
                    view2 = view5;
                    n8 = n11;
                    n9 = n7;
                    if (n10 < n7) {
                        n9 = n10;
                        n8 = n11;
                        view2 = view5;
                        view3 = view4;
                    }
                }
            }
            view = view3;
            n7 = n9;
        }
        boolean bl = this.p(p3, n3, n4);
        if (bl && view != null) {
            return p3.l0(view);
        }
        if (!bl && view2 != null) {
            return p3.l0(view2);
        }
        if (bl) {
            view = view2;
        }
        if (view == null) {
            return -1;
        }
        n4 = p3.l0(view);
        n3 = this.q(p3) == bl ? -1 : 1;
        if ((n3 = n4 + n3) >= 0 && n3 < n5) {
            return n3;
        }
        return -1;
    }

    public final int k(View view, i i3) {
        return i3.g(view) + i3.e(view) / 2 - (i3.m() + i3.n() / 2);
    }

    public final View l(RecyclerView.p p3, i i3) {
        int n3 = p3.O();
        View view = null;
        if (n3 == 0) {
            return null;
        }
        int n4 = i3.m();
        int n5 = i3.n() / 2;
        int n6 = Integer.MAX_VALUE;
        for (int i4 = 0; i4 < n3; ++i4) {
            View view2 = p3.N(i4);
            int n7 = Math.abs(i3.g(view2) + i3.e(view2) / 2 - (n4 + n5));
            int n8 = n6;
            if (n7 < n6) {
                view = view2;
                n8 = n7;
            }
            n6 = n8;
        }
        return view;
    }

    public final i m(RecyclerView.p p3) {
        i i3 = this.e;
        if (i3 == null || i3.a != p3) {
            this.e = i.a(p3);
        }
        return this.e;
    }

    public final i n(RecyclerView.p p3) {
        if (p3.q()) {
            return this.o(p3);
        }
        if (p3.p()) {
            return this.m(p3);
        }
        return null;
    }

    public final i o(RecyclerView.p p3) {
        i i3 = this.d;
        if (i3 == null || i3.a != p3) {
            this.d = i.c(p3);
        }
        return this.d;
    }

    public final boolean p(RecyclerView.p p3, int n3, int n4) {
        if (p3.p()) {
            return n3 > 0;
        }
        return n4 > 0;
    }

    public final boolean q(RecyclerView.p p3) {
        int n3 = p3.e();
        if (p3 instanceof RecyclerView.y.b && (p3 = ((RecyclerView.y.b)((Object)p3)).d(n3 - 1)) != null) {
            return ((PointF)p3).x < 0.0f || ((PointF)p3).y < 0.0f;
            {
            }
        }
        return false;
    }
}

