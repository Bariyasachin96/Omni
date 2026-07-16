/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 */
package r2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;
import n1.g;
import r2.a;
import r2.b;
import r2.c;
import r2.d;
import r2.e;
import r2.i;
import r2.j;
import r2.k;
import r2.m;
import r2.n;
import r2.o;
import z1.f;

public final class l
extends i {
    public j t;
    public k u;
    public Drawable v;

    public l(Context context, b b3, j j3, k k3) {
        super(context, b3);
        this.B(j3);
        this.A(k3);
    }

    public static l v(Context context, CircularProgressIndicatorSpec object, c c3) {
        k k3 = ((CircularProgressIndicatorSpec)object).o == 1 ? new e(context, (CircularProgressIndicatorSpec)object) : new d((CircularProgressIndicatorSpec)object);
        object = new l(context, (b)object, c3, k3);
        ((l)((Object)object)).C(n1.g.b(context.getResources(), z1.f.ic_mtrl_arrow_circle, null));
        return object;
    }

    public static l w(Context context, LinearProgressIndicatorSpec linearProgressIndicatorSpec, m m3) {
        k k3 = linearProgressIndicatorSpec.o == 0 ? new n(linearProgressIndicatorSpec) : new o(context, linearProgressIndicatorSpec);
        return new l(context, linearProgressIndicatorSpec, m3, k3);
    }

    public void A(k k3) {
        this.u = k3;
        k3.e(this);
    }

    public void B(j j3) {
        this.t = j3;
    }

    public void C(Drawable drawable) {
        this.v = drawable;
    }

    public void draw(Canvas canvas) {
        if (!this.getBounds().isEmpty() && this.isVisible() && canvas.getClipBounds(this.r)) {
            Object object;
            Object object2;
            boolean bl = this.z();
            int n3 = 0;
            if (bl && (object2 = this.v) != null) {
                object2.setBounds(this.getBounds());
                this.v.setTint(this.d.e[0]);
                this.v.draw(canvas);
                return;
            }
            canvas.save();
            this.t.h(canvas, this.getBounds(), this.h(), this.m(), this.l());
            int n4 = this.d.i;
            int n5 = this.getAlpha();
            object2 = this.d;
            boolean bl2 = object2 instanceof LinearProgressIndicatorSpec || object2 instanceof CircularProgressIndicatorSpec && ((CircularProgressIndicatorSpec)object2).s;
            boolean bl3 = bl2 && n4 == 0 && !((b)object2).b(false);
            if (bl3) {
                this.t.d(canvas, this.p, 0.0f, 1.0f, this.d.f, n5, 0);
            } else if (bl2) {
                object2 = (j.a)this.u.b.get(0);
                object = this.u.b;
                object = (j.a)object.get(object.size() - 1);
                j j3 = this.t;
                if (j3 instanceof m) {
                    Paint paint = this.p;
                    float f3 = ((j.a)object2).a;
                    int n6 = this.d.f;
                    j3.d(canvas, paint, 0.0f, f3, n6, n5, n4);
                    this.t.d(canvas, this.p, ((j.a)object).b, 1.0f, this.d.f, n5, n4);
                } else {
                    canvas.save();
                    canvas.rotate(((j.a)object).g);
                    this.t.d(canvas, this.p, ((j.a)object).b, ((j.a)object2).a + 1.0f, this.d.f, n5, n4);
                    canvas.restore();
                }
            }
            while (n3 < this.u.b.size()) {
                object = (j.a)this.u.b.get(n3);
                ((j.a)object).f = this.i();
                this.t.c(canvas, this.p, (j.a)object, this.getAlpha());
                if (n3 > 0 && !bl3 && bl2) {
                    object2 = (j.a)this.u.b.get(n3 - 1);
                    this.t.d(canvas, this.p, ((j.a)object2).b, ((j.a)object).a, this.d.f, n5, n4);
                }
                ++n3;
            }
            canvas.restore();
        }
    }

    public int getIntrinsicHeight() {
        return this.t.e();
    }

    public int getIntrinsicWidth() {
        return this.t.f();
    }

    @Override
    public boolean t(boolean bl, boolean bl2, boolean bl3) {
        Drawable drawable;
        boolean bl4 = super.t(bl, bl2, bl3);
        if (this.z() && (drawable = this.v) != null) {
            return drawable.setVisible(bl, bl2);
        }
        if (!this.isRunning()) {
            this.u.a();
        }
        if (bl) {
            if (!bl3) {
                return bl4;
            }
            this.u.g();
        }
        return bl4;
    }

    public k x() {
        return this.u;
    }

    public j y() {
        return this.t;
    }

    public final boolean z() {
        a a4 = this.e;
        return a4 != null && a4.a(this.c.getContentResolver()) == 0.0f;
    }
}

