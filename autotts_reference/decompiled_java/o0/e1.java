/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package o0;

import android.view.View;
import android.view.ViewGroup;
import d3.f;
import d3.j;
import n3.p;
import o0.d1;
import t3.a;
import t3.c;
import t3.d;

public abstract class e1 {
    public static final a a(View view) {
        return d.b(new p(view, null){
            public int e;
            public Object f;
            public final View g;
            {
                this.g = view;
                super(2, a4);
            }

            @Override
            public final g3.a g(Object object, g3.a a4) {
                a4 = new /* invalid duplicate definition of identical inner class */;
                a4.f = object;
                return a4;
            }

            @Override
            public final Object j(Object object) {
                block5: {
                    Object object2;
                    block7: {
                        Object object3;
                        block6: {
                            block2: {
                                block3: {
                                    block4: {
                                        object2 = h3.c.b();
                                        int n3 = this.e;
                                        if (n3 == 0) break block2;
                                        if (n3 == 1) break block3;
                                        if (n3 != 2) break block4;
                                        f.b(object);
                                        break block5;
                                    }
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                object3 = (c)this.f;
                                f.b(object);
                                object = object3;
                                break block6;
                            }
                            f.b(object);
                            object3 = (c)this.f;
                            View view = this.g;
                            this.f = object3;
                            this.e = 1;
                            object = object3;
                            if (((c)object3).a(view, this) == object2) break block7;
                        }
                        object3 = this.g;
                        if (!(object3 instanceof ViewGroup)) break block5;
                        object3 = d1.b((ViewGroup)object3);
                        this.f = null;
                        this.e = 2;
                        if (((c)object).e((a)object3, this) != object2) break block5;
                    }
                    return object2;
                }
                return j.a;
            }

            public final Object l(c c3, g3.a a4) {
                return (this.g(c3, a4)).j(j.a);
            }
        });
    }
}

