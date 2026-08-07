/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.lifecycle;

import android.os.Bundle;
import androidx.appcompat.app.s;
import androidx.lifecycle.c0;
import androidx.lifecycle.u;
import androidx.lifecycle.w;
import androidx.savedstate.a;
import d3.b;
import d3.c;
import java.util.Map;
import n3.a;
import o3.k;

public final class v
implements a.c {
    public final androidx.savedstate.a a;
    public boolean b;
    public Bundle c;
    public final b d;

    public v(androidx.savedstate.a a4, c0 c02) {
        k.e(a4, "savedStateRegistry");
        k.e(c02, "viewModelStoreOwner");
        this.a = a4;
        this.d = d3.c.a(new a(c02){
            public final c0 d;
            {
                this.d = c02;
                super(0);
            }

            public final w b() {
                return u.b(this.d);
            }
        });
    }

    @Override
    public Bundle a() {
        Object object = new Bundle();
        Object object2 = this.c;
        if (object2 != null) {
            object.putAll((Bundle)object2);
        }
        if (!(object2 = this.b().e().entrySet().iterator()).hasNext()) {
            this.b = false;
            return object;
        }
        object = (Map.Entry)object2.next();
        object2 = (String)object.getKey();
        s.a(object.getValue());
        throw null;
    }

    public final w b() {
        return (w)this.d.getValue();
    }

    public final void c() {
        if (!this.b) {
            Bundle bundle = this.a.b("androidx.lifecycle.internal.SavedStateHandlesProvider");
            Bundle bundle2 = new Bundle();
            Bundle bundle3 = this.c;
            if (bundle3 != null) {
                bundle2.putAll(bundle3);
            }
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            this.c = bundle2;
            this.b = true;
            this.b();
        }
    }
}

