/*
 * Decompiled with CFR 0.152.
 */
package androidx.viewpager2.widget;

import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public final class b
extends ViewPager2.i {
    public final List a;

    public b(int n3) {
        this.a = new ArrayList(n3);
    }

    @Override
    public void a(int n3) {
        ConcurrentModificationException concurrentModificationException2;
        block3: {
            try {
                Iterator iterator = this.a.iterator();
                while (iterator.hasNext()) {
                    ((ViewPager2.i)iterator.next()).a(n3);
                }
            }
            catch (ConcurrentModificationException concurrentModificationException2) {
                break block3;
            }
            return;
        }
        this.f(concurrentModificationException2);
    }

    @Override
    public void b(int n3, float f3, int n4) {
        ConcurrentModificationException concurrentModificationException2;
        block3: {
            try {
                Iterator iterator = this.a.iterator();
                while (iterator.hasNext()) {
                    ((ViewPager2.i)iterator.next()).b(n3, f3, n4);
                }
            }
            catch (ConcurrentModificationException concurrentModificationException2) {
                break block3;
            }
            return;
        }
        this.f(concurrentModificationException2);
    }

    @Override
    public void c(int n3) {
        ConcurrentModificationException concurrentModificationException2;
        block3: {
            try {
                Iterator iterator = this.a.iterator();
                while (iterator.hasNext()) {
                    ((ViewPager2.i)iterator.next()).c(n3);
                }
            }
            catch (ConcurrentModificationException concurrentModificationException2) {
                break block3;
            }
            return;
        }
        this.f(concurrentModificationException2);
    }

    public void d(ViewPager2.i i3) {
        this.a.add(i3);
    }

    public void e(ViewPager2.i i3) {
        this.a.remove(i3);
    }

    public final void f(ConcurrentModificationException concurrentModificationException) {
        throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", concurrentModificationException);
    }
}

