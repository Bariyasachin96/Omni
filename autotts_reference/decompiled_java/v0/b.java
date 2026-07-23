/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 */
package v0;

import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class b {
    public static boolean a(int n3, Rect rect, Rect rect2, Rect rect3) {
        boolean bl = b.b(n3, rect, rect2);
        if (!b.b(n3, rect, rect3) && bl) {
            if (!b.j(n3, rect, rect3)) {
                return true;
            }
            if (n3 != 17 && n3 != 66) {
                return b.k(n3, rect, rect2) < b.m(n3, rect, rect3);
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean b(int n3, Rect rect, Rect rect2) {
        if (n3 == 17) return rect2.bottom >= rect.top && rect2.top <= rect.bottom;
        if (n3 == 33) return rect2.right >= rect.left && rect2.left <= rect.right;
        if (n3 == 66) return rect2.bottom >= rect.top && rect2.top <= rect.bottom;
        if (n3 == 130) return rect2.right >= rect.left && rect2.left <= rect.right;
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Object c(Object object, b b3, a a4, Object object2, Rect rect, int n3) {
        Rect rect2 = new Rect(rect);
        int n4 = 0;
        if (n3 != 17) {
            if (n3 != 33) {
                if (n3 != 66) {
                    if (n3 != 130) throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    rect2.offset(0, -(rect.height() + 1));
                } else {
                    rect2.offset(-(rect.width() + 1), 0);
                }
            } else {
                rect2.offset(0, rect.height() + 1);
            }
        } else {
            rect2.offset(rect.width() + 1, 0);
        }
        int n5 = b3.b(object);
        Rect rect3 = new Rect();
        Object object3 = null;
        while (n4 < n5) {
            Object object4 = b3.a(object, n4);
            if (object4 != object2) {
                a4.a(object4, rect3);
                if (b.h(n3, rect, rect3, rect2)) {
                    rect2.set(rect3);
                    object3 = object4;
                }
            }
            ++n4;
        }
        return object3;
    }

    public static Object d(Object object, b b3, a a4, Object object2, int n3, boolean bl, boolean bl2) {
        int n4 = b3.b(object);
        ArrayList<Object> arrayList = new ArrayList<Object>(n4);
        for (int i3 = 0; i3 < n4; ++i3) {
            arrayList.add(b3.a(object, i3));
        }
        Collections.sort(arrayList, new c(bl, a4));
        if (n3 != 1) {
            if (n3 == 2) {
                return b.e(object2, arrayList, bl2);
            }
            throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
        return b.f(object2, arrayList, bl2);
    }

    public static Object e(Object object, ArrayList arrayList, boolean bl) {
        int n3 = arrayList.size();
        int n4 = object == null ? -1 : arrayList.lastIndexOf(object);
        if (++n4 < n3) {
            return arrayList.get(n4);
        }
        if (bl && n3 > 0) {
            return arrayList.get(0);
        }
        return null;
    }

    public static Object f(Object object, ArrayList arrayList, boolean bl) {
        int n3 = arrayList.size();
        int n4 = object == null ? n3 : arrayList.indexOf(object);
        if (--n4 >= 0) {
            return arrayList.get(n4);
        }
        if (bl && n3 > 0) {
            return arrayList.get(n3 - 1);
        }
        return null;
    }

    public static int g(int n3, int n4) {
        return n3 * 13 * n3 + n4 * n4;
    }

    public static boolean h(int n3, Rect rect, Rect rect2, Rect rect3) {
        if (!b.i(rect, rect2, n3)) {
            return false;
        }
        if (!b.i(rect, rect3, n3)) {
            return true;
        }
        if (b.a(n3, rect, rect2, rect3)) {
            return true;
        }
        if (b.a(n3, rect, rect3, rect2)) {
            return false;
        }
        return b.g(b.k(n3, rect, rect2), b.o(n3, rect, rect2)) < b.g(b.k(n3, rect, rect3), b.o(n3, rect, rect3));
    }

    public static boolean i(Rect rect, Rect rect2, int n3) {
        if (n3 != 17) {
            if (n3 != 33) {
                if (n3 != 66) {
                    if (n3 == 130) {
                        n3 = rect.top;
                        int n4 = rect2.top;
                        return (n3 < n4 || rect.bottom <= n4) && rect.bottom < rect2.bottom;
                    }
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                int n5 = rect.left;
                n3 = rect2.left;
                return (n5 < n3 || rect.right <= n3) && rect.right < rect2.right;
            }
            int n6 = rect.bottom;
            n3 = rect2.bottom;
            return (n6 > n3 || rect.top >= n3) && rect.top > rect2.top;
        }
        int n7 = rect.right;
        n3 = rect2.right;
        return (n7 > n3 || rect.left >= n3) && rect.left > rect2.left;
    }

    public static boolean j(int n3, Rect rect, Rect rect2) {
        if (n3 != 17) {
            if (n3 != 33) {
                if (n3 != 66) {
                    if (n3 == 130) {
                        return rect.bottom <= rect2.top;
                    }
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                return rect.right <= rect2.left;
            }
            return rect.top >= rect2.bottom;
        }
        return rect.left >= rect2.right;
    }

    public static int k(int n3, Rect rect, Rect rect2) {
        return Math.max(0, b.l(n3, rect, rect2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int l(int n3, Rect rect, Rect rect2) {
        int n4;
        if (n3 != 17) {
            if (n3 != 33) {
                if (n3 != 66) {
                    if (n3 != 130) throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    n3 = rect2.top;
                    n4 = rect.bottom;
                    return n3 - n4;
                }
                n3 = rect2.left;
                n4 = rect.right;
                return n3 - n4;
            }
            n3 = rect.top;
            n4 = rect2.bottom;
            return n3 - n4;
        }
        n3 = rect.left;
        n4 = rect2.right;
        return n3 - n4;
    }

    public static int m(int n3, Rect rect, Rect rect2) {
        return Math.max(1, b.n(n3, rect, rect2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int n(int n3, Rect rect, Rect rect2) {
        int n4;
        if (n3 != 17) {
            if (n3 != 33) {
                if (n3 != 66) {
                    if (n3 != 130) throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    n3 = rect2.bottom;
                    n4 = rect.bottom;
                    return n3 - n4;
                }
                n3 = rect2.right;
                n4 = rect.right;
                return n3 - n4;
            }
            n3 = rect.top;
            n4 = rect2.top;
            return n3 - n4;
        }
        n3 = rect.left;
        n4 = rect2.left;
        return n3 - n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int o(int n3, Rect rect, Rect rect2) {
        if (n3 == 17) return Math.abs(rect.top + rect.height() / 2 - (rect2.top + rect2.height() / 2));
        if (n3 == 33) return Math.abs(rect.left + rect.width() / 2 - (rect2.left + rect2.width() / 2));
        if (n3 == 66) return Math.abs(rect.top + rect.height() / 2 - (rect2.top + rect2.height() / 2));
        if (n3 == 130) return Math.abs(rect.left + rect.width() / 2 - (rect2.left + rect2.width() / 2));
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    public static interface a {
        public void a(Object var1, Rect var2);
    }

    public static interface b {
        public Object a(Object var1, int var2);

        public int b(Object var1);
    }

    public static class c
    implements Comparator {
        public final Rect c = new Rect();
        public final Rect d = new Rect();
        public final boolean e;
        public final a f;

        public c(boolean bl, a a4) {
            this.e = bl;
            this.f = a4;
        }

        public int compare(Object object, Object object2) {
            Rect rect = this.c;
            Rect rect2 = this.d;
            this.f.a(object, rect);
            this.f.a(object2, rect2);
            int n3 = rect.top;
            int n4 = rect2.top;
            if (n3 < n4) {
                return -1;
            }
            if (n3 > n4) {
                return 1;
            }
            n3 = rect.left;
            n4 = rect2.left;
            if (n3 < n4) {
                if (this.e) {
                    return 1;
                }
                return -1;
            }
            if (n3 > n4) {
                if (this.e) {
                    return -1;
                }
                return 1;
            }
            n3 = rect.bottom;
            n4 = rect2.bottom;
            if (n3 < n4) {
                return -1;
            }
            if (n3 > n4) {
                return 1;
            }
            n3 = rect.right;
            n4 = rect2.right;
            if (n3 < n4) {
                if (this.e) {
                    return 1;
                }
                return -1;
            }
            if (n3 > n4) {
                if (this.e) {
                    return -1;
                }
                return 1;
            }
            return 0;
        }
    }
}

