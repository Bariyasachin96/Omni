/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Outline
 *  android.graphics.RectF
 *  android.view.View
 *  android.view.ViewOutlineProvider
 */
package v2;

import android.graphics.Outline;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewOutlineProvider;
import v2.n;
import v2.o;
import v2.s;

public class t
extends s {
    public boolean f = false;
    public float g = 0.0f;

    public t(View view) {
        this.n(view);
    }

    private void n(View view) {
        view.setOutlineProvider(new ViewOutlineProvider(this){
            public final t a;
            {
                this.a = t3;
            }

            public void getOutline(View object, Outline outline) {
                object = this.a;
                if (object.c != null && !object.d.isEmpty()) {
                    t t3 = this.a;
                    object = t3.d;
                    outline.setRoundRect((int)object.left, (int)object.top, (int)object.right, (int)object.bottom, t3.g);
                }
            }
        });
    }

    public static boolean q(o o3) {
        return o3.q() instanceof n && o3.s() instanceof n && o3.i() instanceof n && o3.k() instanceof n;
    }

    @Override
    public void b(View view) {
        this.g = this.m();
        boolean bl = this.o() || this.p();
        this.f = bl;
        view.setClipToOutline(this.j() ^ true);
        if (this.j()) {
            view.invalidate();
            return;
        }
        view.invalidateOutline();
    }

    @Override
    public boolean j() {
        return !this.f || this.a;
        {
        }
    }

    public final float m() {
        RectF rectF;
        o o3 = this.c;
        if (o3 != null && (rectF = this.d) != null) {
            return o3.f.a(rectF);
        }
        return 0.0f;
    }

    public final boolean o() {
        o o3;
        if (!this.d.isEmpty() && (o3 = this.c) != null) {
            return o3.v(this.d);
        }
        return false;
    }

    public final boolean p() {
        block2: {
            block4: {
                float f3;
                float f4;
                float f5;
                float f6;
                o o3;
                block6: {
                    block5: {
                        float f7;
                        block3: {
                            if (this.d.isEmpty() || (o3 = this.c) == null || !this.b || o3.v(this.d) || !t.q(this.c)) break block2;
                            f6 = this.c.r().a(this.d);
                            f5 = this.c.t().a(this.d);
                            f4 = this.c.j().a(this.d);
                            f3 = this.c.l().a(this.d);
                            float f8 = f6 - 0.0f;
                            f7 = f8 == 0.0f ? 0 : (f8 > 0.0f ? 1 : -1);
                            if (f7 != false || f4 != 0.0f || f5 != f3) break block3;
                            o3 = this.d;
                            o3.set(((RectF)o3).left - f5, ((RectF)o3).top, ((RectF)o3).right, ((RectF)o3).bottom);
                            this.g = f5;
                            break block4;
                        }
                        if (f7 != false || f5 != 0.0f || f4 != f3) break block5;
                        o3 = this.d;
                        o3.set(((RectF)o3).left, ((RectF)o3).top - f4, ((RectF)o3).right, ((RectF)o3).bottom);
                        this.g = f4;
                        break block4;
                    }
                    if (f5 != 0.0f || f3 != 0.0f || f6 != f4) break block6;
                    o3 = this.d;
                    o3.set(((RectF)o3).left, ((RectF)o3).top, ((RectF)o3).right + f6, ((RectF)o3).bottom);
                    this.g = f6;
                    break block4;
                }
                if (f4 != 0.0f || f3 != 0.0f || f6 != f5) break block2;
                o3 = this.d;
                o3.set(((RectF)o3).left, ((RectF)o3).top, ((RectF)o3).right, ((RectF)o3).bottom + f6);
                this.g = f6;
            }
            return true;
        }
        return false;
    }
}

