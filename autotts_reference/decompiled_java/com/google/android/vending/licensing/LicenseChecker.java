/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.vending.licensing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import b3.b;
import com.google.android.vending.licensing.ILicenseResultListener;
import com.google.android.vending.licensing.ILicensingService;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.NullDeviceLimiter;
import com.google.android.vending.licensing.Policy;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LicenseChecker
implements ServiceConnection {
    public static final SecureRandom j = new SecureRandom();
    public ILicensingService a;
    public PublicKey b;
    public final Context c;
    public final Policy d;
    public Handler e;
    public final String f;
    public final String g;
    public final Set h = new HashSet();
    public final Queue i = new LinkedList();

    public LicenseChecker(Context context, Policy object, String string) {
        this.c = context;
        this.d = object;
        this.b = LicenseChecker.j(string);
        this.f = object = context.getPackageName();
        this.g = LicenseChecker.k(context, (String)object);
        context = new HandlerThread("background thread");
        context.start();
        this.e = new Handler(context.getLooper());
    }

    /*
     * Loose catch block
     */
    public static PublicKey j(String object) {
        b b322222222222;
        block4: {
            try {
                byte[] byArray = b3.a.a((String)object);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                object = new X509EncodedKeySpec(byArray);
                object = keyFactory.generatePublic((KeySpec)object);
                return object;
            }
            catch (InvalidKeySpecException invalidKeySpecException) {
            }
            catch (b b322222222222) {
                break block4;
            }
            Log.e((String)"LicenseChecker", (String)"Invalid key specification.");
            throw new IllegalArgumentException(invalidKeySpecException);
        }
        Log.e((String)"LicenseChecker", (String)"Could not decode from Base64.");
        throw new IllegalArgumentException(b322222222222);
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    public static String k(Context context, String string) {
        int n3;
        try {
            n3 = context.getPackageManager().getPackageInfo((String)string, (int)0).versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)"LicenseChecker", (String)"Package not found. could not get version code.");
            return "";
        }
        return String.valueOf(n3);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void f(LicenseCheckerCallback licenseCheckerCallback) {
        synchronized (this) {
            Throwable throwable222;
            block8: {
                block7: {
                    a3.a a4;
                    block11: {
                        b b322222;
                        block10: {
                            block9: {
                                block6: {
                                    try {
                                        if (!this.d.a()) break block6;
                                        licenseCheckerCallback.a(256);
                                        break block7;
                                    }
                                    catch (Throwable throwable222) {
                                        break block8;
                                    }
                                }
                                Object object = this.d;
                                NullDeviceLimiter nullDeviceLimiter = new NullDeviceLimiter();
                                a4 = new a3.a((Policy)object, nullDeviceLimiter, licenseCheckerCallback, this.i(), this.f, this.g);
                                object = this.a;
                                if (object != null) break block11;
                                try {
                                    nullDeviceLimiter = this.c;
                                    String string = new String(b3.a.a("Y29tLmFuZHJvaWQudmVuZGluZy5saWNlbnNpbmcuSUxpY2Vuc2luZ1NlcnZpY2U="));
                                    object = new Intent(string);
                                    string = new String(b3.a.a("Y29tLmFuZHJvaWQudmVuZGluZw=="));
                                    if (!nullDeviceLimiter.bindService(object.setPackage(string), this, 1)) break block9;
                                    this.i.offer(a4);
                                    break block7;
                                }
                                catch (b b322222) {
                                    break block10;
                                }
                            }
                            Log.e((String)"LicenseChecker", (String)"Could not bind to service.");
                            this.l(a4);
                            break block7;
                        }
                        b322222.printStackTrace();
                        break block7;
                        catch (SecurityException securityException) {
                            licenseCheckerCallback.b(6);
                            break block7;
                        }
                    }
                    this.i.offer(a4);
                    this.n();
                }
                return;
            }
            throw throwable222;
        }
    }

    public final void g() {
        if (this.a != null) {
            try {
                this.c.unbindService((ServiceConnection)this);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e((String)"LicenseChecker", (String)"Unable to unbind from licensing service (already unbound)");
            }
            this.a = null;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void h(a3.a a4) {
        synchronized (this) {
            Throwable throwable2;
            block4: {
                try {
                    this.h.remove(a4);
                    if (!this.h.isEmpty()) break block4;
                    this.g();
                }
                catch (Throwable throwable2) {}
            }
            return;
            throw throwable2;
        }
    }

    public final int i() {
        return j.nextInt();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void l(a3.a a4) {
        synchronized (this) {
            Throwable throwable2;
            block5: {
                block4: {
                    block3: {
                        try {
                            this.d.b(291, null);
                            if (!this.d.a()) break block3;
                            a4.a().a(291);
                            break block4;
                        }
                        catch (Throwable throwable2) {
                            break block5;
                        }
                    }
                    a4.a().c(291);
                }
                return;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void m() {
        synchronized (this) {
            this.g();
            this.e.getLooper().quit();
            return;
        }
    }

    public final void n() {
        a3.a a4;
        while ((a4 = (a3.a)this.i.poll()) != null) {
            try {
                a4.c();
                ILicensingService iLicensingService = this.a;
                long l3 = a4.b();
                String string = a4.c();
                a a5 = new a(this, a4);
                iLicensingService.f(l3, string, a5);
                this.h.add(a4);
            }
            catch (RemoteException remoteException) {
                Log.w((String)"LicenseChecker", (String)"RemoteException in checkLicense call.", (Throwable)remoteException);
                this.l(a4);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.a = ILicensingService.Stub.h(iBinder);
            this.n();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onServiceDisconnected(ComponentName componentName) {
        synchronized (this) {
            Log.w((String)"LicenseChecker", (String)"Service unexpectedly disconnected.");
            this.a = null;
            return;
        }
    }

    public class a
    extends ILicenseResultListener.Stub {
        public final a3.a d;
        public Runnable e;
        public final LicenseChecker f;

        public a(LicenseChecker licenseChecker, a3.a a4) {
            this.f = licenseChecker;
            this.d = a4;
            this.e = new Runnable(this, licenseChecker){
                public final LicenseChecker c;
                public final a d;
                {
                    this.d = a4;
                    this.c = licenseChecker;
                }

                @Override
                public void run() {
                    a a4 = this.d;
                    a4.f.l(a4.d);
                    a4 = this.d;
                    a4.f.h(a4.d);
                }
            };
            this.l();
        }

        @Override
        public void e(int n3, String string, String string2) {
            this.f.e.post(new Runnable(this, n3, string, string2){
                public final int c;
                public final String d;
                public final String e;
                public final a f;
                {
                    this.f = a4;
                    this.c = n3;
                    this.d = string;
                    this.e = string2;
                }

                @Override
                public void run() {
                    if (this.f.f.h.contains(this.f.d)) {
                        this.f.k();
                        this.f.d.g(this.f.f.b, this.c, this.d, this.e);
                        a a4 = this.f;
                        a4.f.h(a4.d);
                    }
                }
            });
        }

        public final void k() {
            this.f.e.removeCallbacks(this.e);
        }

        public final void l() {
            this.f.e.postDelayed(this.e, 10000L);
        }
    }
}

