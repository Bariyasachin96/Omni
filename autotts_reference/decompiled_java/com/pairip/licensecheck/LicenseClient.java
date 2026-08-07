/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Process
 *  android.os.RemoteException
 *  android.os.SystemClock
 *  android.util.Log
 */
package com.pairip.licensecheck;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.pairip.licensecheck.ILicenseV2ResultListener;
import com.pairip.licensecheck.LicenseActivity;
import com.pairip.licensecheck.LicenseCheckException;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda0;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda1;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda2;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda3;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda4;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda5;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda6;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda7;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda9;
import com.pairip.licensecheck.LicenseClient_IA;
import com.pairip.licensecheck.LicenseResponseHelper;
import com.pairip.licensecheck.RepeatedCheckMetadata;
import java.io.Serializable;
import java.util.Objects;

public class LicenseClient
implements ServiceConnection {
    private static final String BACKGROUND_SERVICE_INTERFACE_CLASS_NAME = "com.android.vending.licensing.IBackgroundLicensingService";
    private static final int ERROR_INVALID_PACKAGE_NAME = 3;
    private static final int EVENTUAL_SHUTDOWN_DELAY_MILLIS = 30000;
    private static final int FIRST_ISOLATED_UID = 99000;
    private static final int FLAG_RPC_CALL = 0;
    private static final int LAST_ISOLATED_UID = 99999;
    private static final int LICENSED = 0;
    private static final int MAX_RETRIES = 3;
    private static final int MILLIS_PER_SEC = 1000;
    private static final int NOT_LICENSED = 2;
    private static final String PAYLOAD_PAYWALL = "PAYWALL_INTENT";
    private static final int PER_USER_RANGE = 100000;
    private static final int REPEATED_CHECK_RETRY_DELAY_MILLIS = 300000;
    private static final int RETRY_DELAY_MILLIS = 1000;
    private static final String SERVICE_INTERFACE_CLASS_NAME = "com.android.vending.licensing.ILicensingService";
    private static final String SERVICE_PACKAGE = "com.android.vending";
    private static final String TAG = "LicenseClient";
    private static final int TRANSACTION_CHECK_LICENSE_V2 = 2;
    private static final int TRANSACTION_REPORT_SUCCESSFUL_LICENSE_CHECK = 3;
    protected static boolean backgroundLicensingServiceEnabled = false;
    protected static ImmediateTaskExecutor backgroundRunner;
    protected static boolean eventualShutdownEnabled = true;
    protected static Runnable exitAction;
    public static boolean gracefulShutdownEnabled = true;
    private static final Handler handler;
    protected static LicenseCheckState licenseCheckState;
    protected static String licensePubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzCqD0VjR3RQYVN1f5hIVDWBBoomRgzjbHqW3g5v59YfVwTkmM4hWXvyHEXBHcE7Wcbl8Tlic9LIH0HStl7KN+Erx4mUlk8jqsPGeDC9r2f2VLKYGKm6lb5Lvjw8aNfS6auzJlFN12/NBMEBPb1wstV2B1gUaDNT/63Zz0arO6XbjFM9WAHpo54BFQoWk/vRK95G88xlWoUX3QGum0AouPMj8vKiYaBGzFjnXMTdRH70bYPY5pPmF710ox3vv/SSiM78BT/Ez1V7rshx3fL9ZjrxbmrO8YYbqtzvGu91+y0viRkLvJozU5dy5zHp147UEaX3rDnyxFBhGngO1ng3hQIDAQAB";
    protected static boolean localCheckEnabled = false;
    protected static ImmediateTaskExecutor mainThreadRunner;
    protected static String packageName = "com.vnspeak.autotts";
    protected static boolean repeatedCheckEnabled = true;
    protected static Bundle responsePayload;
    private final Context context;
    protected DelayedTaskExecutor delayedTaskExecutor = new DelayedTaskExecutorImpl(null);
    private long repeatedCheckStartElapsedRealtime = 0L;
    private int retryNum = 0;
    protected boolean waitingForRepeatedCheck = false;

    public static /* synthetic */ void $r8$lambda$8YRQpF8qc5JOZUcKq79QHnbGjYY(LicenseClient licenseClient, RepeatedCheckMetadata repeatedCheckMetadata) {
        licenseClient.lambda$scheduleRepeatedLicenseCheck$0(repeatedCheckMetadata);
    }

    public static /* synthetic */ void $r8$lambda$BhclTRnzXKpP3pw7j8AqgaeaCG4(LicenseClient licenseClient, boolean bl) {
        licenseClient.lambda$retryOrThrow$0(bl);
    }

    public static /* synthetic */ void $r8$lambda$GS82Fij7VQePgSFog_s63_Rcyb0(LicenseClient licenseClient) {
        licenseClient.lambda$initializeLicenseCheck$0();
    }

    public static /* synthetic */ void $r8$lambda$gb_vmUiJUmqdCloCudVdY_igh7I(LicenseClient licenseClient, IBinder iBinder) {
        licenseClient.lambda$onServiceConnected$1(iBinder);
    }

    public static /* synthetic */ void $r8$lambda$ot_XkRbEJeEFG1Hy_d3H6N4DX_I(LicenseClient licenseClient, RepeatedCheckMetadata repeatedCheckMetadata, Bundle bundle) {
        licenseClient.lambda$processResponse$0(repeatedCheckMetadata, bundle);
    }

    public static /* synthetic */ void $r8$lambda$q2q7YKfx3jIZHqiUNn7fQ55wwzI(LicenseClient licenseClient, boolean bl) {
        licenseClient.lambda$initializeLicenseCheck$1(bl);
    }

    public static /* synthetic */ void $r8$lambda$xzrAfByzooHDT9oIsgTdQvzthuE(LicenseClient licenseClient, IBinder iBinder) {
        licenseClient.lambda$onServiceConnected$0(iBinder);
    }

    static {
        Handler handler;
        exitAction = new Runnable(){

            @Override
            public void run() {
                System.exit(0);
            }
        };
        licenseCheckState = LicenseCheckState.CHECK_REQUIRED;
        backgroundRunner = new LicenseClient$$ExternalSyntheticLambda2();
        LicenseClient.handler = handler = new Handler(Looper.getMainLooper());
        Objects.requireNonNull(handler);
        mainThreadRunner = new LicenseClient$$ExternalSyntheticLambda3(handler);
    }

    public LicenseClient(Context context) {
        this.context = context;
    }

    public static void checkLicense(Context context) {
        if (LicenseClient.isIsolatedProcess()) {
            Log.i((String)TAG, (String)"Skipping license check in isolated process.");
            return;
        }
        new LicenseClient(context).initializeLicenseCheck();
    }

    /*
     * Exception decompiling
     */
    private void checkLicenseInternal(IBinder var1_1) throws LicenseCheckException, RemoteException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 1[TRYBLOCK] [3 : 106->129)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void connectToLicensingService(boolean bl) {
        block2: {
            String string = bl ? "Connecting to the background licensing service..." : "Connecting to the main licensing service...";
            Log.d((String)TAG, (String)string);
            string = bl ? BACKGROUND_SERVICE_INTERFACE_CLASS_NAME : SERVICE_INTERFACE_CLASS_NAME;
            Intent intent = new Intent(string).setPackage(SERVICE_PACKAGE).setAction(string);
            try {
                boolean bl2 = this.context.bindService(intent, (ServiceConnection)this, 1);
                if (bl2) break block2;
            }
            catch (SecurityException securityException) {
                this.retryOrThrow(new LicenseCheckException("Not allowed to bind with the licensing service: ".concat(string), securityException), bl, bl);
                return;
            }
            this.retryOrThrow(new LicenseCheckException("Could not bind with the licensing service: ".concat(string)), bl, bl);
        }
    }

    private Intent createCloseAppIntentOrExitIfAppInBackground() {
        if (!this.isForeground()) {
            exitAction.run();
        }
        Intent intent = new Intent(this.context, LicenseActivity.class);
        if (gracefulShutdownEnabled) {
            intent.addFlags(65536);
        } else {
            intent.addFlags(0x4000000);
            intent.addFlags(32768);
        }
        intent.addFlags(0x10000000);
        return intent;
    }

    private static ILicenseV2ResultListener createResultListener(LicenseClient licenseClient) {
        return new ILicenseV2ResultListener.Stub(licenseClient){
            final LicenseClient val$client;
            {
                this.val$client = licenseClient;
            }

            @Override
            public void verifyLicense(int n3, Bundle bundle) {
                this.val$client.processResponse(n3, bundle);
            }
        };
    }

    public static String getLicensePubKey() {
        return licensePubKey;
    }

    private void handleError(LicenseCheckException object) {
        object = Log.getStackTraceString((Throwable)object);
        StringBuilder stringBuilder = new StringBuilder("Error while checking license: ");
        stringBuilder.append((String)object);
        Log.e((String)TAG, (String)stringBuilder.toString());
        if (licenseCheckState.equals((Object)LicenseCheckState.FULL_CHECK_OK)) {
            return;
        }
        this.startErrorDialogActivity();
    }

    private boolean isForeground() {
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState((ActivityManager.RunningAppProcessInfo)runningAppProcessInfo);
        return runningAppProcessInfo.importance <= 100;
    }

    private static boolean isIsolatedProcess() {
        if (Build.VERSION.SDK_INT >= 28) {
            return Process.isIsolated();
        }
        int n3 = Process.myUid() % 100000;
        return n3 >= 99000 && n3 <= 99999;
    }

    private /* synthetic */ void lambda$initializeLicenseCheck$0() {
        boolean bl = this.performLocalInstallerCheck();
        mainThreadRunner.run(new LicenseClient$$ExternalSyntheticLambda0(this, bl));
    }

    private /* synthetic */ void lambda$initializeLicenseCheck$1(boolean bl) {
        if (bl) {
            licenseCheckState = LicenseCheckState.LOCAL_CHECK_OK;
        }
        bl = bl && backgroundLicensingServiceEnabled;
        this.connectToLicensingService(bl);
    }

    private /* synthetic */ void lambda$onServiceConnected$0(IBinder iBinder) {
        try {
            this.checkLicenseInternal(iBinder);
            return;
        }
        catch (RemoteException remoteException) {
            this.handleError(new LicenseCheckException("Error when getting interface descriptor.", remoteException));
        }
        catch (LicenseCheckException licenseCheckException) {
            this.handleError(licenseCheckException);
        }
    }

    private /* synthetic */ void lambda$onServiceConnected$1(IBinder iBinder) {
        try {
            this.reportSuccessfulLicenseCheck(iBinder);
            return;
        }
        catch (Exception exception) {
            String string = Log.getStackTraceString((Throwable)exception);
            StringBuilder stringBuilder = new StringBuilder("Error while reporting license check: ");
            stringBuilder.append(string);
            Log.e((String)TAG, (String)stringBuilder.toString());
            return;
        }
    }

    private /* synthetic */ void lambda$processResponse$0(RepeatedCheckMetadata repeatedCheckMetadata, Bundle bundle) {
        if (repeatedCheckMetadata != null) {
            licenseCheckState = LicenseCheckState.REPEATED_CHECK_REQUIRED;
            this.repeatedCheckStartElapsedRealtime = this.getElapsedRealtimeMillis();
            this.scheduleRepeatedLicenseCheck(repeatedCheckMetadata);
        } else {
            licenseCheckState = LicenseCheckState.FULL_CHECK_OK;
        }
        responsePayload = bundle;
    }

    static /* synthetic */ void lambda$reportSuccessfulLicenseCheck$0() {
        licenseCheckState = LicenseCheckState.LOCAL_CHECK_REPORTED;
    }

    private /* synthetic */ void lambda$retryOrThrow$0(boolean bl) {
        this.connectToLicensingService(bl);
    }

    private /* synthetic */ void lambda$scheduleRepeatedLicenseCheck$0(RepeatedCheckMetadata repeatedCheckMetadata) {
        long l3 = this.getElapsedRealtimeMillis();
        long l4 = this.repeatedCheckStartElapsedRealtime;
        if (this.getCurrentTimeMillis() < repeatedCheckMetadata.getTimeToRetryMillis() && l3 - l4 < repeatedCheckMetadata.getDurationToRetryMillis()) {
            Log.d((String)TAG, (String)"Repeated license check is rescheduled.");
            this.scheduleRepeatedLicenseCheck(repeatedCheckMetadata);
            return;
        }
        this.waitingForRepeatedCheck = false;
        this.connectToLicensingService(false);
    }

    static /* synthetic */ void lambda$static$0(Runnable runnable) {
        new Thread(runnable).start();
    }

    private boolean performLocalInstallerCheck() {
        block15: {
            block16: {
                block18: {
                    Object object;
                    block17: {
                        PackageManager packageManager;
                        block14: {
                            block13: {
                                try {
                                    if (Build.VERSION.SDK_INT >= 30) break block13;
                                    Log.i((String)TAG, (String)"Local install check bypassed due to old SDK version.");
                                    return false;
                                }
                                catch (Exception exception) {
                                    Log.w((String)TAG, (String)"Could not obtain package info for local installer check.", (Throwable)exception);
                                    return false;
                                }
                            }
                            packageManager = this.context.getPackageManager();
                            if (packageManager != null) break block14;
                            Log.i((String)TAG, (String)"Local install check bypassed due to package manager not found.");
                            return false;
                        }
                        object = packageManager.getPackageInfo(packageName, 0);
                        if (object == null) break block15;
                        if (((PackageInfo)object).applicationInfo == null) break block15;
                        int n3 = ((PackageInfo)object).applicationInfo.flags;
                        if ((n3 & 1) != 0 || (n3 & 0x80) != 0) break block16;
                        object = packageManager.getInstallSourceInfo(packageName);
                        if (object != null) break block17;
                        Log.i((String)TAG, (String)"Local install check bypassed due to install source info not found.");
                        return false;
                    }
                    object = object.getInstallingPackageName();
                    if (object == null) break block18;
                    if (!((String)object).equals(SERVICE_PACKAGE)) break block18;
                    return true;
                }
                Log.i((String)TAG, (String)"Local install check failed due to wrong installer.");
                return false;
            }
            Log.i((String)TAG, (String)"Local install check passed due to system app.");
            return true;
        }
        Log.i((String)TAG, (String)"Local install check bypassed due to app package info not found.");
        return false;
    }

    private void populateInputDataForLicenseCheckV2(Parcel parcel, IBinder iBinder) throws RemoteException {
        parcel.writeInterfaceToken(iBinder.getInterfaceDescriptor());
        parcel.writeString(packageName);
        parcel.writeStrongBinder(LicenseClient.createResultListener(this).asBinder());
        parcel.writeInt(0);
    }

    private void populateInputDataForReportAutoVerifiedLicense(Parcel parcel, IBinder iBinder) throws RemoteException {
        parcel.writeInterfaceToken(iBinder.getInterfaceDescriptor());
        parcel.writeString(packageName);
        parcel.writeInt(0);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private void processResponse(int var1_1, Bundle var2_2) {
        if (var1_1 == 3) ** GOTO lbl19
        if (var1_1 == 0) {
            LicenseResponseHelper.validateResponse(var2_2 /* !! */ , LicenseClient.packageName);
            Log.i((String)"LicenseClient", (String)"License check succeeded.");
            var3_4 = LicenseClient.repeatedCheckEnabled != false ? LicenseResponseHelper.getRepeatedCheckMetadata(var2_2 /* !! */ ) : null;
            var5_5 = LicenseClient.mainThreadRunner;
            var4_6 = new LicenseClient$$ExternalSyntheticLambda7(this, var3_4, var2_2 /* !! */ );
            var5_5.run(var4_6);
            return;
        }
        if (var1_1 != 2) ** GOTO lbl17
        try {
            this.startPaywallActivity((PendingIntent)var2_2 /* !! */ .getParcelable("PAYWALL_INTENT"));
            return;
lbl17:
            // 1 sources

            var2_2 /* !! */  = new LicenseCheckException(String.format("Unexpected response code %d received.", new Object[]{var1_1}));
            throw var2_2 /* !! */ ;
lbl19:
            // 1 sources

            var2_2 /* !! */  = new LicenseCheckException("Request package name invalid.");
            throw var2_2 /* !! */ ;
        }
        catch (LicenseCheckException var2_3) {
            this.handleError(var2_3);
            return;
        }
    }

    private void retryOrThrow(LicenseCheckException licenseCheckException) {
        this.retryOrThrow(licenseCheckException, false, false);
    }

    private void retryOrThrow(LicenseCheckException object, boolean bl, boolean bl2) {
        int n3 = this.retryNum;
        if (n3 < 3) {
            this.retryNum = n3 + 1;
            this.delayedTaskExecutor.schedule(new LicenseClient$$ExternalSyntheticLambda1(this, bl2), 1000L);
            n3 = this.retryNum;
            object = object == null ? "null" : ((Throwable)object).getMessage();
            Log.d((String)TAG, (String)String.format("Retry #%d. License check failed with error '%s'. Next try in %ds...", n3, object, 1L));
            return;
        }
        if (bl) {
            String string = String.valueOf(object);
            object = new StringBuilder("Retry limit reached for: ");
            ((StringBuilder)object).append(string);
            Log.e((String)TAG, (String)((StringBuilder)object).toString());
            return;
        }
        this.handleError((LicenseCheckException)object);
    }

    private void scheduleAppShutdown() {
        if (eventualShutdownEnabled) {
            this.delayedTaskExecutor.schedule(exitAction, 30000L);
        }
    }

    private void scheduleRepeatedLicenseCheck(RepeatedCheckMetadata repeatedCheckMetadata) {
        long l3 = this.getCurrentTimeMillis();
        l3 = Math.min(Math.min(repeatedCheckMetadata.getDurationToRetryMillis(), Math.max(0L, repeatedCheckMetadata.getTimeToRetryMillis() - l3)), 300000L);
        if (!this.waitingForRepeatedCheck) {
            this.waitingForRepeatedCheck = true;
            try {
                this.context.unbindService((ServiceConnection)this);
            }
            catch (RuntimeException runtimeException) {
                Log.e((String)TAG, (String)"Failed to unbind service for repeated license check.", (Throwable)runtimeException);
            }
        }
        this.delayedTaskExecutor.schedule(new LicenseClient$$ExternalSyntheticLambda9(this, repeatedCheckMetadata), l3);
        Log.d((String)TAG, (String)String.format("Repeated license check is scheduled in %d ms...", l3));
    }

    private void startErrorDialogActivity() {
        Intent intent = this.createCloseAppIntentOrExitIfAppInBackground();
        intent.putExtra("activitytype", (Serializable)((Object)LicenseActivity.ActivityType.ERROR_DIALOG));
        this.scheduleAppShutdown();
        this.context.startActivity(intent);
    }

    private void startPaywallActivity(PendingIntent pendingIntent) {
        Intent intent = this.createCloseAppIntentOrExitIfAppInBackground();
        intent.putExtra("paywallintent", (Parcelable)pendingIntent);
        intent.putExtra("activitytype", (Serializable)((Object)LicenseActivity.ActivityType.PAYWALL));
        this.scheduleAppShutdown();
        this.context.startActivity(intent);
    }

    protected long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    protected long getElapsedRealtimeMillis() {
        return SystemClock.elapsedRealtime();
    }

    public void initializeLicenseCheck() {
        int n3 = licenseCheckState.ordinal();
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 4) {
                    return;
                }
                this.connectToLicensingService(false);
                return;
            }
            try {
                LicenseResponseHelper.validateResponse(responsePayload, packageName);
                return;
            }
            catch (LicenseCheckException licenseCheckException) {
                this.handleError(licenseCheckException);
                return;
            }
        }
        if (localCheckEnabled) {
            backgroundRunner.run(new LicenseClient$$ExternalSyntheticLambda4(this));
            return;
        }
        this.connectToLicensingService(false);
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d((String)TAG, (String)"Connected to the licensing service.");
        int n3 = licenseCheckState.ordinal();
        if (n3 != 0) {
            if (n3 != 2) {
                if (n3 != 4) {
                    return;
                }
            } else {
                backgroundRunner.run(new LicenseClient$$ExternalSyntheticLambda6(this, iBinder));
                return;
            }
        }
        backgroundRunner.run(new LicenseClient$$ExternalSyntheticLambda5(this, iBinder));
    }

    public void onServiceDisconnected(ComponentName componentName) {
        if (licenseCheckState.equals((Object)LicenseCheckState.REPEATED_CHECK_REQUIRED) && this.waitingForRepeatedCheck) {
            Log.d((String)TAG, (String)"Ignoring service disconnection in REPEATED_CHECK_REQUIRED state.");
            return;
        }
        Log.w((String)TAG, (String)"Unexpectedly disconnected from the licensing service.");
        this.retryOrThrow(new LicenseCheckException("Licensing service unexpectedly disconnected."));
    }

    /*
     * Exception decompiling
     */
    public void reportSuccessfulLicenseCheck(IBinder var1_1) throws LicenseCheckException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 3[TRYBLOCK] [9 : 122->159)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static interface DelayedTaskExecutor {
        public void schedule(Runnable var1, long var2);
    }

    private static class DelayedTaskExecutorImpl
    implements DelayedTaskExecutor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        private DelayedTaskExecutorImpl() {
        }

        /* synthetic */ DelayedTaskExecutorImpl(LicenseClient_IA licenseClient_IA) {
            this();
        }

        @Override
        public void schedule(Runnable runnable, long l3) {
            this.handler.postDelayed(runnable, l3);
        }
    }

    public static interface ImmediateTaskExecutor {
        public void run(Runnable var1);
    }

    public static enum LicenseCheckState {
        CHECK_REQUIRED,
        FULL_CHECK_OK,
        LOCAL_CHECK_OK,
        LOCAL_CHECK_REPORTED,
        REPEATED_CHECK_REQUIRED;

    }
}

