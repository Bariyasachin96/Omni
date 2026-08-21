/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package o0;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import o0.t0;
import t3.a;

public abstract class d1 {
    public static final a a(ViewGroup viewGroup) {
        return new a(viewGroup){
            public final ViewGroup a;
            {
                this.a = viewGroup;
            }

            @Override
            public Iterator iterator() {
                return d1.c(this.a);
            }
        };
    }

    public static final a b(ViewGroup viewGroup) {
        return new a(viewGroup){
            public final ViewGroup a;
            {
                this.a = viewGroup;
            }

            @Override
            public Iterator iterator() {
                return new t0(d1.a(this.a).iterator(), b.d);
            }
        };
    }

    public static final Iterator c(ViewGroup viewGroup) {
        return new Iterator(viewGroup){
            public int c;
            public final ViewGroup d;
            {
                this.d = viewGroup;
            }

            public View a() {
                ViewGroup viewGroup = this.d;
                int n3 = this.c;
                this.c = n3 + 1;
                if ((viewGroup = viewGroup.getChildAt(n3)) != null) {
                    return viewGroup;
                }
                throw new IndexOutOfBoundsException();
            }

            @Override
            public boolean hasNext() {
                return this.c < this.d.getChildCount();
            }

            @Override
            public void remove() {
                int n3;
                ViewGroup viewGroup = this.d;
                this.c = n3 = this.c - 1;
                viewGroup.removeViewAt(n3);
            }
        };
    }
}

