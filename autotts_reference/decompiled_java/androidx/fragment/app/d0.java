/*
 * Decompiled with CFR 0.152.
 */
package androidx.fragment.app;

import java.io.Writer;

public final class d0
extends Writer {
    public final String c;
    public StringBuilder d = new StringBuilder(128);

    public d0(String string) {
        this.c = string;
    }

    public final void a() {
        if (this.d.length() > 0) {
            this.d.toString();
            StringBuilder stringBuilder = this.d;
            stringBuilder.delete(0, stringBuilder.length());
        }
    }

    @Override
    public void close() {
        this.a();
    }

    @Override
    public void flush() {
        this.a();
    }

    @Override
    public void write(char[] cArray, int n3, int n4) {
        for (int i3 = 0; i3 < n4; ++i3) {
            char c3 = cArray[n3 + i3];
            if (c3 == '\n') {
                this.a();
                continue;
            }
            this.d.append(c3);
        }
    }
}

