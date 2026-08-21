/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 *  android.os.Handler
 */
package l0;

import android.graphics.Typeface;
import android.os.Handler;
import l0.f;
import l0.g;

public class a {
    public final g.c a;
    public final Handler b;

    public a(g.c c3, Handler handler) {
        this.a = c3;
        this.b = handler;
    }

    public final void a(int n3) {
        g.c c3 = this.a;
        this.b.post(new Runnable(this, c3, n3){
            public final g.c c;
            public final int d;
            public final a e;
            {
                this.e = a4;
                this.c = c3;
                this.d = n3;
            }

            @Override
            public void run() {
                this.c.a(this.d);
            }
        });
    }

    public void b(f.e e3) {
        if (e3.a()) {
            this.c(e3.a);
            return;
        }
        this.a(e3.b);
    }

    public final void c(Typeface typeface) {
        g.c c3 = this.a;
        this.b.post(new Runnable(this, c3, typeface){
            public final g.c c;
            public final Typeface d;
            public final a e;
            {
                this.e = a4;
                this.c = c3;
                this.d = typeface;
            }

            @Override
            public void run() {
                this.c.b(this.d);
            }
        });
    }
}

