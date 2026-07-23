/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.database.ContentObserver
 *  android.graphics.Typeface
 *  android.os.Handler
 */
package androidx.emoji2.text;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.os.Handler;
import androidx.emoji2.text.c;
import androidx.emoji2.text.f;
import androidx.emoji2.text.l;
import androidx.emoji2.text.n;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import k0.e;
import l0.g;
import n0.h;

public class k
extends f.c {
    public static final a k = new a();

    public k(Context context, l0.e e3) {
        super(new b(context, e3, k));
    }

    public k c(Executor executor) {
        ((b)this.a()).f(executor);
        return this;
    }

    public static class a {
        public Typeface a(Context context, g.b b3) {
            return l0.g.a(context, null, new g.b[]{b3});
        }

        public g.a b(Context context, l0.e e3) {
            return l0.g.b(context, null, e3);
        }

        public void c(Context context, ContentObserver contentObserver) {
            context.getContentResolver().unregisterContentObserver(contentObserver);
        }
    }

    public static class b
    implements f.h {
        public final Context a;
        public final l0.e b;
        public final a c;
        public final Object d = new Object();
        public Handler e;
        public Executor f;
        public ThreadPoolExecutor g;
        public f.i h;
        public ContentObserver i;
        public Runnable j;

        public b(Context context, l0.e e3, a a4) {
            n0.h.h(context, "Context cannot be null");
            n0.h.h(e3, "FontRequest cannot be null");
            this.a = context.getApplicationContext();
            this.b = e3;
            this.c = a4;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void a(f.i i3) {
            n0.h.h(i3, "LoaderCallback cannot be null");
            Object object = this.d;
            synchronized (object) {
                this.h = i3;
            }
            this.d();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void b() {
            Object object = this.d;
            synchronized (object) {
                Throwable throwable2;
                block6: {
                    Object object2;
                    block5: {
                        try {
                            this.h = null;
                            object2 = this.i;
                            if (object2 == null) break block5;
                            this.c.c(this.a, (ContentObserver)object2);
                            this.i = null;
                        }
                        catch (Throwable throwable2) {
                            break block6;
                        }
                    }
                    if ((object2 = this.e) != null) {
                        object2.removeCallbacks(this.j);
                    }
                    this.e = null;
                    object2 = this.g;
                    if (object2 != null) {
                        ((ThreadPoolExecutor)object2).shutdown();
                    }
                    this.f = null;
                    this.g = null;
                    return;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void c() {
            Throwable throwable2;
            Object object;
            Object object2;
            block28: {
                int n3;
                block26: {
                    block27: {
                        object2 = this.d;
                        // MONITORENTER : object2
                        if (this.h == null) {
                            // MONITOREXIT : object2
                            return;
                        }
                        try {
                            object2 = this.e();
                            n3 = ((g.b)object2).b();
                            if (n3 == 2) {
                                object = this.d;
                                // MONITORENTER : object
                                // MONITOREXIT : object
                            }
                            if (n3 != 0) break block26;
                        }
                        catch (Throwable throwable2) {}
                        k0.e.a("EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface");
                        object = this.c.a(this.a, (g.b)object2);
                        object2 = g0.k.e(this.a, null, ((g.b)object2).d());
                        if (object2 == null || object == null) break block27;
                        n n4 = n.b((Typeface)object, (ByteBuffer)object2);
                        k0.e.b();
                        object2 = this.d;
                        // MONITORENTER : object2
                        object = this.h;
                        if (object != null) {
                            ((f.i)object).b(n4);
                        }
                        this.b();
                        return;
                    }
                    try {
                        object2 = new RuntimeException("Unable to open file.");
                        throw object2;
                    }
                    catch (Throwable throwable3) {}
                    k0.e.b();
                    throw throwable3;
                    break block28;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("fetchFonts result is not OK. (");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(")");
                object2 = new RuntimeException(((StringBuilder)object).toString());
                throw object2;
            }
            object2 = this.d;
            // MONITORENTER : object2
            object = this.h;
            if (object != null) {
                ((f.i)object).a(throwable2);
            }
            this.b();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void d() {
            Object object = this.d;
            synchronized (object) {
                Throwable throwable2;
                block5: {
                    Object object2;
                    try {
                        if (this.h == null) {
                            return;
                        }
                    }
                    catch (Throwable throwable2) {
                        break block5;
                    }
                    if (this.f == null) {
                        object2 = androidx.emoji2.text.c.b("emojiCompat");
                        this.g = object2;
                        this.f = object2;
                    }
                    Executor executor = this.f;
                    object2 = new l(this);
                    executor.execute((Runnable)object2);
                    return;
                }
                throw throwable2;
            }
        }

        public final g.b e() {
            g.b[] bArray;
            block3: {
                try {
                    bArray = this.c.b(this.a, this.b);
                    if (bArray.c() != 0) break block3;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    throw new RuntimeException("provider not found", nameNotFoundException);
                }
                if ((bArray = bArray.b()) != null && bArray.length != 0) {
                    return bArray[0];
                }
                throw new RuntimeException("fetchFonts failed (empty result)");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fetchFonts failed (");
            stringBuilder.append(bArray.c());
            stringBuilder.append(")");
            throw new RuntimeException(stringBuilder.toString());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void f(Executor executor) {
            Object object = this.d;
            synchronized (object) {
                this.f = executor;
                return;
            }
        }
    }
}

