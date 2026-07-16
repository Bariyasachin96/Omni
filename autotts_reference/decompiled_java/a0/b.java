/*
 * Decompiled with CFR 0.152.
 */
package a0;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import n0.e;
import n0.f;
import o.r;

public final class b {
    public final e a = new f(10);
    public final r b = new r();
    public final ArrayList c = new ArrayList();
    public final HashSet d = new HashSet();

    public void a(Object object, Object object2) {
        if (this.b.containsKey(object) && this.b.containsKey(object2)) {
            ArrayList arrayList;
            ArrayList arrayList2 = arrayList = (ArrayList)this.b.get(object);
            if (arrayList == null) {
                arrayList2 = this.f();
                this.b.put(object, arrayList2);
            }
            arrayList2.add(object2);
            return;
        }
        throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
    }

    public void b(Object object) {
        if (!this.b.containsKey(object)) {
            this.b.put(object, null);
        }
    }

    public void c() {
        int n3 = this.b.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ArrayList arrayList = (ArrayList)this.b.j(i3);
            if (arrayList == null) continue;
            this.k(arrayList);
        }
        this.b.clear();
    }

    public boolean d(Object object) {
        return this.b.containsKey(object);
    }

    public final void e(Object object, ArrayList arrayList, HashSet hashSet) {
        if (arrayList.contains(object)) {
            return;
        }
        if (!hashSet.contains(object)) {
            hashSet.add(object);
            ArrayList arrayList2 = (ArrayList)this.b.get(object);
            if (arrayList2 != null) {
                int n3 = arrayList2.size();
                for (int i3 = 0; i3 < n3; ++i3) {
                    this.e(arrayList2.get(i3), arrayList, hashSet);
                }
            }
            hashSet.remove(object);
            arrayList.add(object);
            return;
        }
        throw new RuntimeException("This graph contains cyclic dependencies");
    }

    public final ArrayList f() {
        ArrayList arrayList;
        ArrayList arrayList2 = arrayList = (ArrayList)this.a.b();
        if (arrayList == null) {
            arrayList2 = new ArrayList();
        }
        return arrayList2;
    }

    public List g(Object object) {
        return (List)this.b.get(object);
    }

    public List h(Object object) {
        int n3 = this.b.size();
        ArrayList<Object> arrayList = null;
        for (int i3 = 0; i3 < n3; ++i3) {
            ArrayList arrayList2 = (ArrayList)this.b.j(i3);
            ArrayList<Object> arrayList3 = arrayList;
            if (arrayList2 != null) {
                arrayList3 = arrayList;
                if (arrayList2.contains(object)) {
                    arrayList3 = arrayList;
                    if (arrayList == null) {
                        arrayList3 = new ArrayList<Object>();
                    }
                    arrayList3.add(this.b.f(i3));
                }
            }
            arrayList = arrayList3;
        }
        return arrayList;
    }

    public ArrayList i() {
        this.c.clear();
        this.d.clear();
        int n3 = this.b.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            this.e(this.b.f(i3), this.c, this.d);
        }
        return this.c;
    }

    public boolean j(Object object) {
        int n3 = this.b.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ArrayList arrayList = (ArrayList)this.b.j(i3);
            if (arrayList == null || !arrayList.contains(object)) continue;
            return true;
        }
        return false;
    }

    public final void k(ArrayList arrayList) {
        arrayList.clear();
        this.a.a(arrayList);
    }
}

