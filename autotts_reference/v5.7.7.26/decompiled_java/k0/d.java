/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.LocaleList
 */
package k0;

import android.os.LocaleList;
import java.util.Locale;
import k0.c;

public final class d
implements c {
    public final LocaleList a;

    public d(Object object) {
        this.a = (LocaleList)object;
    }

    @Override
    public String a() {
        return this.a.toLanguageTags();
    }

    @Override
    public Object b() {
        return this.a;
    }

    public boolean equals(Object object) {
        return this.a.equals(((c)object).b());
    }

    @Override
    public Locale get(int n3) {
        return this.a.get(n3);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override
    public int size() {
        return this.a.size();
    }

    public String toString() {
        return this.a.toString();
    }
}

