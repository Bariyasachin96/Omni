/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.app.job.JobParameters
 *  android.app.job.JobServiceEngine
 *  android.app.job.JobWorkItem
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.IBinder
 */
package androidx.core.app;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
public abstract class JobIntentService
extends Service {
    public static final Object j = new Object();
    public static final HashMap k = new HashMap();
    public b c;
    public f d;
    public a e;
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    public final ArrayList i = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public d a() {
        Object object = this.c;
        if (object != null) {
            return object.a();
        }
        object = this.i;
        synchronized (object) {
            try {
                if (this.i.size() <= 0) return null;
                return (d)this.i.remove(0);
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public boolean b() {
        a a4 = this.e;
        if (a4 != null) {
            a4.cancel(this.f);
        }
        this.g = true;
        return this.e();
    }

    public void c(boolean bl) {
        if (this.e == null) {
            this.e = new a(this);
            this.e.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    public abstract void d(Intent var1);

    public boolean e() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void f() {
        ArrayList arrayList = this.i;
        if (arrayList == null) {
            return;
        }
        synchronized (arrayList) {
            Throwable throwable2;
            block7: {
                block6: {
                    block5: {
                        try {
                            this.e = null;
                            ArrayList arrayList2 = this.i;
                            if (arrayList2 == null || arrayList2.size() <= 0) break block5;
                            this.c(false);
                            break block6;
                        }
                        catch (Throwable throwable2) {
                            break block7;
                        }
                    }
                    if (!this.h) {
                        this.d.a();
                    }
                }
                return;
            }
            throw throwable2;
        }
    }

    public IBinder onBind(Intent object) {
        object = this.c;
        if (object != null) {
            return object.b();
        }
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.c = new e(this);
        this.d = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onDestroy() {
        super.onDestroy();
        ArrayList arrayList = this.i;
        if (arrayList != null) {
            synchronized (arrayList) {
                this.h = true;
                this.d.a();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int onStartCommand(Intent intent, int n3, int n4) {
        if (this.i == null) {
            return 2;
        }
        this.d.c();
        ArrayList arrayList = this.i;
        synchronized (arrayList) {
            ArrayList arrayList2 = this.i;
            if (intent == null) {
                intent = new Intent();
            }
            c c3 = new c(this, intent, n4);
            arrayList2.add(c3);
            this.c(true);
            return 3;
        }
    }

    public final class a
    extends AsyncTask {
        public final JobIntentService a;

        public a(JobIntentService jobIntentService) {
            this.a = jobIntentService;
        }

        public Void a(Void ... object) {
            while ((object = this.a.a()) != null) {
                this.a.d(object.getIntent());
                object.a();
            }
            return null;
        }

        public void b(Void void_) {
            this.a.f();
        }

        public void c(Void void_) {
            this.a.f();
        }
    }

    public static interface b {
        public d a();

        public IBinder b();
    }

    public final class c
    implements d {
        public final Intent a;
        public final int b;
        public final JobIntentService c;

        public c(JobIntentService jobIntentService, Intent intent, int n3) {
            this.c = jobIntentService;
            this.a = intent;
            this.b = n3;
        }

        @Override
        public void a() {
            this.c.stopSelf(this.b);
        }

        @Override
        public Intent getIntent() {
            return this.a;
        }
    }

    public static interface d {
        public void a();

        public Intent getIntent();
    }

    public static final class e
    extends JobServiceEngine
    implements b {
        public final JobIntentService a;
        public final Object b = new Object();
        public JobParameters c;

        public e(JobIntentService jobIntentService) {
            super((Service)jobIntentService);
            this.a = jobIntentService;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public d a() {
            Object object = this.b;
            // MONITORENTER : object
            JobParameters jobParameters = this.c;
            if (jobParameters == null) {
                // MONITOREXIT : object
                return null;
            }
            jobParameters = jobParameters.dequeueWork();
            // MONITOREXIT : object
            if (jobParameters == null) return null;
            jobParameters.getIntent().setExtrasClassLoader(this.a.getClassLoader());
            return new a(this, (JobWorkItem)jobParameters);
        }

        @Override
        public IBinder b() {
            return this.getBinder();
        }

        public boolean onStartJob(JobParameters jobParameters) {
            this.c = jobParameters;
            this.a.c(false);
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean onStopJob(JobParameters object) {
            boolean bl = this.a.b();
            object = this.b;
            synchronized (object) {
                this.c = null;
                return bl;
            }
        }

        public final class a
        implements d {
            public final JobWorkItem a;
            public final e b;

            public a(e e3, JobWorkItem jobWorkItem) {
                this.b = e3;
                this.a = jobWorkItem;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a() {
                Object object = this.b.b;
                synchronized (object) {
                    Throwable throwable2;
                    block4: {
                        block3: {
                            try {
                                JobParameters jobParameters = this.b.c;
                                if (jobParameters == null) break block3;
                                jobParameters.completeWork(this.a);
                            }
                            catch (Throwable throwable2) {
                                break block4;
                            }
                        }
                        return;
                    }
                    throw throwable2;
                }
            }

            @Override
            public Intent getIntent() {
                return this.a.getIntent();
            }
        }
    }

    public static abstract class f {
        public abstract void a();

        public abstract void b();

        public abstract void c();
    }
}

