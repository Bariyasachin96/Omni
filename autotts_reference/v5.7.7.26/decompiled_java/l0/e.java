/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package l0;

import android.util.Base64;
import java.util.List;
import n0.h;

public final class e {
    public final String a;
    public final String b;
    public final String c;
    public final List d;
    public final int e;
    public final String f;

    public e(String string, String string2, String string3, List list) {
        this.a = (String)h.g(string);
        this.b = (String)h.g(string2);
        this.c = (String)h.g(string3);
        this.d = (List)h.g(list);
        this.e = 0;
        this.f = this.a(string, string2, string3);
    }

    public final String a(String string, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("-");
        stringBuilder.append(string2);
        stringBuilder.append("-");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    public List b() {
        return this.d;
    }

    public int c() {
        return this.e;
    }

    public String d() {
        return this.f;
    }

    public String e() {
        return this.a;
    }

    public String f() {
        return this.b;
    }

    public String g() {
        return this.c;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("FontRequest {mProviderAuthority: ");
        ((StringBuilder)object).append(this.a);
        ((StringBuilder)object).append(", mProviderPackage: ");
        ((StringBuilder)object).append(this.b);
        ((StringBuilder)object).append(", mQuery: ");
        ((StringBuilder)object).append(this.c);
        ((StringBuilder)object).append(", mCertificates:");
        stringBuilder.append(((StringBuilder)object).toString());
        for (int i3 = 0; i3 < this.d.size(); ++i3) {
            stringBuilder.append(" [");
            object = (List)this.d.get(i3);
            for (int i4 = 0; i4 < object.size(); ++i4) {
                stringBuilder.append(" \"");
                stringBuilder.append(Base64.encodeToString((byte[])((byte[])object.get(i4)), (int)0));
                stringBuilder.append("\"");
            }
            stringBuilder.append(" ]");
        }
        stringBuilder.append("}");
        object = new StringBuilder();
        ((StringBuilder)object).append("mCertificatesArray: ");
        ((StringBuilder)object).append(this.e);
        stringBuilder.append(((StringBuilder)object).toString());
        return stringBuilder.toString();
    }
}

