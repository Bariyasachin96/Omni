/*
 * Decompiled with CFR 0.152.
 */
package q;

import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class a
implements Future {
    public static final boolean f;
    public static final Logger g;
    public static final b h;
    public static final Object i;
    public volatile Object c;
    public volatile e d;
    public volatile h e;

    static {
        Object var1_1;
        b b3;
        f = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
        g = Logger.getLogger(a.class.getName());
        try {
            b3 = new f(AtomicReferenceFieldUpdater.newUpdater(h.class, Thread.class, "a"), AtomicReferenceFieldUpdater.newUpdater(h.class, h.class, "b"), AtomicReferenceFieldUpdater.newUpdater(a.class, h.class, "e"), AtomicReferenceFieldUpdater.newUpdater(a.class, e.class, "d"), AtomicReferenceFieldUpdater.newUpdater(a.class, Object.class, "c"));
            var1_1 = null;
        }
        catch (Throwable throwable) {
            b3 = new g();
        }
        h = b3;
        if (var1_1 != null) {
            g.log(Level.SEVERE, "SafeAtomicHelper is broken!", (Throwable)var1_1);
        }
        i = new Object();
    }

    public static CancellationException c(String object, Throwable throwable) {
        object = new CancellationException((String)object);
        ((Throwable)object).initCause(throwable);
        return object;
    }

    public static void e(a object) {
        ((a)object).k();
        ((a)object).b();
        object = ((a)object).d(null);
        while (object != null) {
            e e3 = ((e)object).c;
            a.f(((e)object).a, ((e)object).b);
            object = e3;
        }
    }

    public static void f(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
            return;
        }
        catch (RuntimeException runtimeException) {
            Logger logger = g;
            Level level = Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RuntimeException while executing runnable ");
            stringBuilder.append(runnable);
            stringBuilder.append(" with executor ");
            stringBuilder.append(executor);
            logger.log(level, stringBuilder.toString(), runtimeException);
            return;
        }
    }

    public static Object h(Future future) {
        boolean bl = false;
        while (true) {
            try {
                Object v3 = future.get();
                if (bl) {
                    Thread.currentThread().interrupt();
                }
                return v3;
            }
            catch (Throwable throwable) {
                if (bl) {
                    Thread.currentThread().interrupt();
                }
                throw throwable;
            }
            catch (InterruptedException interruptedException) {
                bl = true;
                continue;
            }
            break;
        }
    }

    /*
     * Loose catch block
     */
    public final void a(StringBuilder stringBuilder) {
        block4: {
            try {
                Object object = a.h(this);
                stringBuilder.append("SUCCESS, result=[");
                stringBuilder.append(this.n(object));
                stringBuilder.append("]");
                return;
            }
            catch (RuntimeException runtimeException) {
            }
            catch (ExecutionException executionException) {
            }
            stringBuilder.append("UNKNOWN, cause=[");
            stringBuilder.append(runtimeException.getClass());
            stringBuilder.append(" thrown from get()]");
            break block4;
            catch (CancellationException cancellationException) {
                stringBuilder.append("CANCELLED");
                break block4;
            }
            stringBuilder.append("FAILURE, cause=[");
            stringBuilder.append(executionException.getCause());
            stringBuilder.append("]");
        }
    }

    public void b() {
    }

    @Override
    public final boolean cancel(boolean bl) {
        c c3;
        Object object = this.c;
        boolean bl2 = object == null;
        if (bl2 && h.b(this, object, c3 = f ? new c(bl, new CancellationException("Future.cancel() was called.")) : (bl ? q.a$c.c : q.a$c.d))) {
            if (bl) {
                this.i();
            }
            a.e(this);
            return true;
        }
        return false;
    }

    public final e d(e e3) {
        e e4;
        while (!h.a(this, e4 = this.d, q.a$e.d)) {
        }
        e e5 = e3;
        e3 = e4;
        while (e3 != null) {
            e4 = e3.c;
            e3.c = e5;
            e5 = e3;
            e3 = e4;
        }
        return e5;
    }

    public final Object g(Object object) {
        if (!(object instanceof c)) {
            if (!(object instanceof d)) {
                Object object2 = object;
                if (object == i) {
                    object2 = null;
                }
                return object2;
            }
            throw new ExecutionException(((d)object).a);
        }
        throw a.c("Task was cancelled.", ((c)object).b);
    }

    public final Object get() {
        if (!Thread.interrupted()) {
            Object object = this.c;
            boolean bl = object != null;
            if (bl) {
                return this.g(object);
            }
            object = this.e;
            if (object != q.a$h.c) {
                h h3;
                h h4 = new h();
                do {
                    h4.a((h)object);
                    if (h.c(this, (h)object, h4)) {
                        block6: {
                            do {
                                LockSupport.park(this);
                                if (Thread.interrupted()) break block6;
                            } while (!(bl = (object = this.c) != null));
                            return this.g(object);
                        }
                        this.l(h4);
                        throw new InterruptedException();
                    }
                    h3 = this.e;
                    object = h3;
                } while (h3 != q.a$h.c);
            }
            return this.g(this.c);
        }
        throw new InterruptedException();
    }

    public final Object get(long l3, TimeUnit object) {
        long l4 = ((TimeUnit)((Object)object)).toNanos(l3);
        if (!Thread.interrupted()) {
            Object object2;
            Object object3;
            long l5;
            long l6;
            boolean bl;
            Object object4;
            block17: {
                object4 = this.c;
                bl = object4 != null;
                if (bl) {
                    return this.g(object4);
                }
                l6 = l4 > 0L ? System.nanoTime() + l4 : 0L;
                l5 = l4;
                if (l4 >= 1000L) {
                    object4 = this.e;
                    if (object4 != q.a$h.c) {
                        object3 = new h();
                        do {
                            ((h)object3).a((h)object4);
                            if (h.c(this, (h)object4, (h)object3)) {
                                block16: {
                                    do {
                                        LockSupport.parkNanos(this, l4);
                                        if (Thread.interrupted()) break block16;
                                        object4 = this.c;
                                        bl = object4 != null;
                                        if (bl) {
                                            return this.g(object4);
                                        }
                                        l4 = l5 = l6 - System.nanoTime();
                                    } while (l5 >= 1000L);
                                    this.l((h)object3);
                                    break block17;
                                }
                                this.l((h)object3);
                                throw new InterruptedException();
                            }
                            object4 = object2 = this.e;
                        } while (object2 != q.a$h.c);
                    }
                    return this.g(this.c);
                }
            }
            while (l5 > 0L) {
                object4 = this.c;
                bl = object4 != null;
                if (bl) {
                    return this.g(object4);
                }
                if (!Thread.interrupted()) {
                    l5 = l6 - System.nanoTime();
                    continue;
                }
                throw new InterruptedException();
            }
            object3 = this.toString();
            object2 = object.toString();
            object4 = Locale.ROOT;
            String string = ((String)object2).toLowerCase((Locale)object4);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Waited ");
            ((StringBuilder)object2).append(l3);
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(object.toString().toLowerCase((Locale)object4));
            object4 = object2 = ((StringBuilder)object2).toString();
            if (l5 + 1000L < 0L) {
                object4 = new StringBuilder();
                ((StringBuilder)object4).append((String)object2);
                ((StringBuilder)object4).append(" (plus ");
                object4 = ((StringBuilder)object4).toString();
                l5 = -l5;
                l3 = ((TimeUnit)((Object)object)).convert(l5, TimeUnit.NANOSECONDS);
                long l7 = l3 - 0L;
                long l8 = l7 == 0L ? 0 : (l7 < 0L ? -1 : 1);
                bl = l8 == false || (l5 -= ((TimeUnit)((Object)object)).toNanos(l3)) > 1000L;
                object = object4;
                if (l8 > 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)object4);
                    ((StringBuilder)object).append(l3);
                    ((StringBuilder)object).append(" ");
                    ((StringBuilder)object).append(string);
                    object = object4 = ((StringBuilder)object).toString();
                    if (bl) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append((String)object4);
                        ((StringBuilder)object).append(",");
                        object = ((StringBuilder)object).toString();
                    }
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append((String)object);
                    ((StringBuilder)object4).append(" ");
                    object = ((StringBuilder)object4).toString();
                }
                object4 = object;
                if (bl) {
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append((String)object);
                    ((StringBuilder)object4).append(l5);
                    ((StringBuilder)object4).append(" nanoseconds ");
                    object4 = ((StringBuilder)object4).toString();
                }
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object4);
                ((StringBuilder)object).append("delay)");
                object4 = ((StringBuilder)object).toString();
            }
            if (this.isDone()) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object4);
                ((StringBuilder)object).append(" but future completed as timeout expired");
                throw new TimeoutException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object4);
            ((StringBuilder)object).append(" for ");
            ((StringBuilder)object).append((String)object3);
            throw new TimeoutException(((StringBuilder)object).toString());
        }
        throw new InterruptedException();
    }

    public void i() {
    }

    @Override
    public final boolean isCancelled() {
        return this.c instanceof c;
    }

    @Override
    public final boolean isDone() {
        boolean bl = this.c != null;
        return bl;
    }

    public String j() {
        if (this instanceof ScheduledFuture) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("remaining delay=[");
            stringBuilder.append(((ScheduledFuture)((Object)this)).getDelay(TimeUnit.MILLISECONDS));
            stringBuilder.append(" ms]");
            return stringBuilder.toString();
        }
        return null;
    }

    public final void k() {
        h h3;
        while (!h.c(this, h3 = this.e, q.a$h.c)) {
        }
        while (h3 != null) {
            h3.b();
            h3 = h3.b;
        }
    }

    public final void l(h h3) {
        h3.a = null;
        block0: while ((h3 = this.e) != q.a$h.c) {
            h h4 = null;
            while (h3 != null) {
                h h5;
                h h6 = h3.b;
                if (h3.a != null) {
                    h5 = h3;
                } else if (h4 != null) {
                    h4.b = h6;
                    h5 = h4;
                    if (h4.a == null) {
                        continue block0;
                    }
                } else {
                    h5 = h4;
                    if (!h.c(this, h3, h6)) continue block0;
                }
                h3 = h6;
                h4 = h5;
            }
            break block0;
        }
    }

    public boolean m(Object object) {
        Object object2 = object;
        if (object == null) {
            object2 = i;
        }
        if (h.b(this, null, object2)) {
            a.e(this);
            return true;
        }
        return false;
    }

    public final String n(Object object) {
        if (object == this) {
            return "this future";
        }
        return String.valueOf(object);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[status=");
        if (this.isCancelled()) {
            stringBuilder.append("CANCELLED");
        } else if (this.isDone()) {
            this.a(stringBuilder);
        } else {
            CharSequence charSequence;
            try {
                charSequence = this.j();
            }
            catch (RuntimeException runtimeException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Exception thrown from implementation: ");
                ((StringBuilder)charSequence).append(runtimeException.getClass());
                charSequence = ((StringBuilder)charSequence).toString();
            }
            if (charSequence != null && !((String)charSequence).isEmpty()) {
                stringBuilder.append("PENDING, info=[");
                stringBuilder.append((String)charSequence);
                stringBuilder.append("]");
            } else if (this.isDone()) {
                this.a(stringBuilder);
            } else {
                stringBuilder.append("PENDING");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static abstract class b {
        public b() {
        }

        public /* synthetic */ b(a a4) {
            this();
        }

        public abstract boolean a(a var1, e var2, e var3);

        public abstract boolean b(a var1, Object var2, Object var3);

        public abstract boolean c(a var1, h var2, h var3);

        public abstract void d(h var1, h var2);

        public abstract void e(h var1, Thread var2);
    }

    public static final class c {
        public static final c c;
        public static final c d;
        public final boolean a;
        public final Throwable b;

        static {
            if (f) {
                d = null;
                c = null;
            } else {
                d = new c(false, null);
                c = new c(true, null);
            }
        }

        public c(boolean bl, Throwable throwable) {
            this.a = bl;
            this.b = throwable;
        }
    }

    public static final abstract class d {
        public final Throwable a;
    }

    public static final class e {
        public static final e d = new e(null, null);
        public final Runnable a;
        public final Executor b;
        public e c;

        public e(Runnable runnable, Executor executor) {
            this.a = runnable;
            this.b = executor;
        }
    }

    public static final class f
    extends b {
        public final AtomicReferenceFieldUpdater a;
        public final AtomicReferenceFieldUpdater b;
        public final AtomicReferenceFieldUpdater c;
        public final AtomicReferenceFieldUpdater d;
        public final AtomicReferenceFieldUpdater e;

        public f(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
            super(null);
            this.a = atomicReferenceFieldUpdater;
            this.b = atomicReferenceFieldUpdater2;
            this.c = atomicReferenceFieldUpdater3;
            this.d = atomicReferenceFieldUpdater4;
            this.e = atomicReferenceFieldUpdater5;
        }

        @Override
        public boolean a(a a4, e e3, e e4) {
            return q.b.a(this.d, a4, e3, e4);
        }

        @Override
        public boolean b(a a4, Object object, Object object2) {
            return q.b.a(this.e, a4, object, object2);
        }

        @Override
        public boolean c(a a4, h h3, h h4) {
            return q.b.a(this.c, a4, h3, h4);
        }

        @Override
        public void d(h h3, h h4) {
            this.b.lazySet(h3, h4);
        }

        @Override
        public void e(h h3, Thread thread) {
            this.a.lazySet(h3, thread);
        }
    }

    public static final class g
    extends b {
        public g() {
            super(null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean a(a a4, e e3, e e4) {
            synchronized (a4) {
                Throwable throwable2;
                block4: {
                    try {
                        if (a4.d == e3) {
                            a4.d = e4;
                            return true;
                        }
                    }
                    catch (Throwable throwable2) {
                        break block4;
                    }
                    return false;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean b(a a4, Object object, Object object2) {
            synchronized (a4) {
                Throwable throwable2;
                block4: {
                    try {
                        if (a4.c == object) {
                            a4.c = object2;
                            return true;
                        }
                    }
                    catch (Throwable throwable2) {
                        break block4;
                    }
                    return false;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean c(a a4, h h3, h h4) {
            synchronized (a4) {
                Throwable throwable2;
                block4: {
                    try {
                        if (a4.e == h3) {
                            a4.e = h4;
                            return true;
                        }
                    }
                    catch (Throwable throwable2) {
                        break block4;
                    }
                    return false;
                }
                throw throwable2;
            }
        }

        @Override
        public void d(h h3, h h4) {
            h3.b = h4;
        }

        @Override
        public void e(h h3, Thread thread) {
            h3.a = thread;
        }
    }

    public static final class h {
        public static final h c = new h(false);
        public volatile Thread a;
        public volatile h b;

        public h() {
            h.e(this, Thread.currentThread());
        }

        public h(boolean bl) {
        }

        public void a(h h3) {
            h.d(this, h3);
        }

        public void b() {
            Thread thread = this.a;
            if (thread != null) {
                this.a = null;
                LockSupport.unpark(thread);
            }
        }
    }
}

