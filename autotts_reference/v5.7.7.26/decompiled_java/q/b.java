/*
 * Decompiled with CFR 0.152.
 */
package q;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public abstract class b {
    public static /* synthetic */ boolean a(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Object object, Object object2, Object object3) {
        do {
            if (!atomicReferenceFieldUpdater.compareAndSet(object, object2, object3)) continue;
            return true;
        } while (atomicReferenceFieldUpdater.get(object) == object2);
        return false;
    }
}

