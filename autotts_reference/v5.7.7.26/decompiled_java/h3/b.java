/*
 * Decompiled with CFR 0.152.
 */
package h3;

import d3.f;
import g3.d;
import i3.a;
import i3.c;
import i3.h;
import n3.p;
import o3.k;

public abstract class b {
    public static g3.a a(p p3, Object object, g3.a object2) {
        k.e(p3, "<this>");
        k.e(object2, "completion");
        g3.a a4 = i3.f.a((g3.a)object2);
        if (p3 instanceof a) {
            return ((a)((Object)p3)).g(object, a4);
        }
        object2 = a4.b();
        if (object2 == d.c) {
            return new h(a4, p3, object){
                public int d;
                public final p e;
                public final Object f;
                {
                    this.e = p3;
                    this.f = object;
                    k.c(a4, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                    super(a4);
                }

                @Override
                public Object j(Object object) {
                    int n3 = this.d;
                    if (n3 != 0) {
                        if (n3 == 1) {
                            this.d = 2;
                            f.b(object);
                            return object;
                        }
                        throw new IllegalStateException("This coroutine had already completed");
                    }
                    this.d = 1;
                    f.b(object);
                    k.c(this.e, "null cannot be cast to non-null type kotlin.Function2<R of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1, kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1>, kotlin.Any?>");
                    return ((p)o3.p.a(this.e, 2)).e(this.f, this);
                }
            };
        }
        return new c(a4, (g3.c)object2, p3, object){
            public int f;
            public final p g;
            public final Object h;
            {
                this.g = p3;
                this.h = object;
                k.c(a4, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
                super(a4, c3);
            }

            @Override
            public Object j(Object object) {
                int n3 = this.f;
                if (n3 != 0) {
                    if (n3 == 1) {
                        this.f = 2;
                        f.b(object);
                        return object;
                    }
                    throw new IllegalStateException("This coroutine had already completed");
                }
                this.f = 1;
                f.b(object);
                k.c(this.g, "null cannot be cast to non-null type kotlin.Function2<R of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1, kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda$1>, kotlin.Any?>");
                return ((p)o3.p.a(this.g, 2)).e(this.h, this);
            }
        };
    }
}

