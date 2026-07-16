/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Outline
 *  android.view.View
 *  android.view.ViewOutlineProvider
 */
package v2;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import j2.e;
import v2.s;

public class u
extends s {
    public u(View view) {
        this.l(view);
    }

    private void l(View view) {
        view.setOutlineProvider(new ViewOutlineProvider(this){
            public final u a;
            {
                this.a = u3;
            }

            public void getOutline(View view, Outline outline) {
                if (!this.a.e.isEmpty()) {
                    j2.e.a(outline, this.a.e);
                }
            }
        });
    }

    @Override
    public void b(View view) {
        view.setClipToOutline(this.j() ^ true);
        if (this.j()) {
            view.invalidate();
            return;
        }
        view.invalidateOutline();
    }

    @Override
    public boolean j() {
        return this.a;
    }
}

