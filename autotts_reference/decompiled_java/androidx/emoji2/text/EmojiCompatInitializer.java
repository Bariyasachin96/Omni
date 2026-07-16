/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package androidx.emoji2.text;

import android.content.Context;
import androidx.emoji2.text.d;
import androidx.emoji2.text.f;
import androidx.emoji2.text.g;
import androidx.emoji2.text.k;
import androidx.emoji2.text.n;
import androidx.lifecycle.ProcessLifecycleInitializer;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import k0.e;

public class EmojiCompatInitializer
implements k1.b {
    @Override
    public List a() {
        return Collections.singletonList(ProcessLifecycleInitializer.class);
    }

    public Boolean c(Context context) {
        f.h(new a(context));
        this.d(context);
        return Boolean.TRUE;
    }

    public void d(Context object) {
        object = ((androidx.lifecycle.k)k1.a.e((Context)object).f(ProcessLifecycleInitializer.class)).t();
        ((androidx.lifecycle.f)object).a(new androidx.lifecycle.b(this, (androidx.lifecycle.f)object){
            public final androidx.lifecycle.f a;
            public final EmojiCompatInitializer b;
            {
                this.b = emojiCompatInitializer;
                this.a = f3;
            }

            @Override
            public void a(androidx.lifecycle.k k3) {
                this.b.e();
                this.a.c(this);
            }
        });
    }

    public void e() {
        androidx.emoji2.text.c.c().postDelayed((Runnable)new c(), 500L);
    }

    public static class a
    extends f.c {
        public a(Context context) {
            super(new b(context));
            this.b(1);
        }
    }

    public static class b
    implements f.h {
        public final Context a;

        public b(Context context) {
            this.a = context.getApplicationContext();
        }

        public static /* synthetic */ void b(b b3, f.i i3, ThreadPoolExecutor threadPoolExecutor) {
            b3.c(i3, threadPoolExecutor);
        }

        @Override
        public void a(f.i i3) {
            ThreadPoolExecutor threadPoolExecutor = androidx.emoji2.text.c.b("EmojiCompatInitializer");
            threadPoolExecutor.execute(new g(this, i3, threadPoolExecutor));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void c(f.i i3, ThreadPoolExecutor threadPoolExecutor) {
            Throwable throwable2;
            block3: {
                Object object;
                try {
                    object = d.a(this.a);
                    if (object != null) {
                        ((k)object).c(threadPoolExecutor);
                        object = ((f.c)object).a();
                        f.i i4 = new f.i(this, i3, threadPoolExecutor){
                            public final f.i a;
                            public final ThreadPoolExecutor b;
                            public final b c;
                            {
                                this.c = b3;
                                this.a = i3;
                                this.b = threadPoolExecutor;
                            }

                            @Override
                            public void a(Throwable throwable) {
                                try {
                                    this.a.a(throwable);
                                    return;
                                }
                                finally {
                                    this.b.shutdown();
                                }
                            }

                            @Override
                            public void b(n n3) {
                                try {
                                    this.a.b(n3);
                                    return;
                                }
                                finally {
                                    this.b.shutdown();
                                }
                            }
                        };
                        object.a(i4);
                        return;
                    }
                }
                catch (Throwable throwable2) {
                    break block3;
                }
                object = new RuntimeException("EmojiCompat font provider not available on this device.");
                throw object;
            }
            i3.a(throwable2);
            threadPoolExecutor.shutdown();
        }
    }

    public static class c
    implements Runnable {
        @Override
        public void run() {
            Throwable throwable2;
            block3: {
                block2: {
                    try {
                        e.a("EmojiCompat.EmojiCompatInitializer.run");
                        if (!f.i()) break block2;
                        f.c().l();
                    }
                    catch (Throwable throwable2) {
                        break block3;
                    }
                }
                e.b();
                return;
            }
            e.b();
            throw throwable2;
        }
    }
}

