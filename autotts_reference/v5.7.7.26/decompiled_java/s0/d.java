/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ClipDescription
 *  android.net.Uri
 *  android.view.inputmethod.InputContentInfo
 */
package s0;

import android.content.ClipDescription;
import android.net.Uri;
import android.view.inputmethod.InputContentInfo;

public final class d {
    public final b a;

    public d(b b3) {
        this.a = b3;
    }

    public static d f(Object object) {
        if (object == null) {
            return null;
        }
        return new d(new a(object));
    }

    public Uri a() {
        return this.a.c();
    }

    public ClipDescription b() {
        return this.a.a();
    }

    public Uri c() {
        return this.a.e();
    }

    public void d() {
        this.a.d();
    }

    public Object e() {
        return this.a.b();
    }

    public static final class a
    implements b {
        public final InputContentInfo a;

        public a(Object object) {
            this.a = (InputContentInfo)object;
        }

        @Override
        public ClipDescription a() {
            return this.a.getDescription();
        }

        @Override
        public Object b() {
            return this.a;
        }

        @Override
        public Uri c() {
            return this.a.getContentUri();
        }

        @Override
        public void d() {
            this.a.requestPermission();
        }

        @Override
        public Uri e() {
            return this.a.getLinkUri();
        }
    }

    public static interface b {
        public ClipDescription a();

        public Object b();

        public Uri c();

        public void d();

        public Uri e();
    }
}

