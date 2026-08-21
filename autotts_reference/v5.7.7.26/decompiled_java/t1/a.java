/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 */
package t1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.s;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public final class a {
    public final Map a = Collections.synchronizedMap(new o.a());
    public int b = 0;
    public Bundle c;

    public final void a(Bundle bundle) {
        this.b = 1;
        this.c = bundle;
        Iterator iterator = this.a.entrySet().iterator();
        if (iterator.hasNext()) {
            iterator = iterator.next();
            s.a(iterator.getValue());
            if (bundle != null) {
                bundle.getBundle((String)iterator.getKey());
            }
            throw null;
        }
    }

    public final void b() {
        this.b = 2;
        Iterator iterator = this.a.values().iterator();
        if (!iterator.hasNext()) {
            return;
        }
        s.a(iterator.next());
        throw null;
    }

    public final void c() {
        this.b = 3;
        Iterator iterator = this.a.values().iterator();
        if (!iterator.hasNext()) {
            return;
        }
        s.a(iterator.next());
        throw null;
    }

    public final void d(int n3, int n4, Intent object) {
        object = this.a.values().iterator();
        if (!object.hasNext()) {
            return;
        }
        s.a(object.next());
        throw null;
    }

    public final void e(Bundle object) {
        if (object == null || !(object = this.a.entrySet().iterator()).hasNext()) {
            return;
        }
        object = (Map.Entry)object.next();
        new Bundle();
        s.a(object.getValue());
        throw null;
    }

    public final void f() {
        this.b = 4;
        Iterator iterator = this.a.values().iterator();
        if (!iterator.hasNext()) {
            return;
        }
        s.a(iterator.next());
        throw null;
    }

    public final void g() {
        this.b = 5;
        Iterator iterator = this.a.values().iterator();
        if (!iterator.hasNext()) {
            return;
        }
        s.a(iterator.next());
        throw null;
    }

    public final void h(String iterator, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        iterator = this.a.values().iterator();
        if (!iterator.hasNext()) {
            return;
        }
        s.a(iterator.next());
        throw null;
    }
}

