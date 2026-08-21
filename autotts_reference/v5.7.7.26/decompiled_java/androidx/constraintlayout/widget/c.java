/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.SparseIntArray
 */
package androidx.constraintlayout.widget;

import android.util.SparseIntArray;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class c {
    public SparseIntArray a = new SparseIntArray();
    public HashMap b = new HashMap();

    public void a(int n3, a a4) {
        HashSet<WeakReference<a>> hashSet;
        HashSet<WeakReference<a>> hashSet2 = hashSet = (HashSet<WeakReference<a>>)this.b.get(n3);
        if (hashSet == null) {
            hashSet2 = new HashSet<WeakReference<a>>();
            this.b.put(n3, hashSet2);
        }
        hashSet2.add(new WeakReference<a>(a4));
    }

    public void b(int n3, a a4) {
        HashSet hashSet = (HashSet)this.b.get(n3);
        if (hashSet == null) {
            return;
        }
        ArrayList<WeakReference> arrayList = new ArrayList<WeakReference>();
        for (WeakReference weakReference : hashSet) {
            a a5 = (a)weakReference.get();
            if (a5 != null && a5 != a4) continue;
            arrayList.add(weakReference);
        }
        ((AbstractCollection)hashSet).removeAll(arrayList);
    }

    public static interface a {
    }
}

