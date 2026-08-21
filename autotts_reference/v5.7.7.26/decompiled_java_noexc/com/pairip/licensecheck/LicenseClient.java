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
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda10;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda11;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda12;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda13;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda2;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda3;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda4;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda5;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda6;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda7;
import com.pairip.licensecheck.LicenseClient$$ExternalSyntheticLambda8;
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
    private static final String EXTRA_END_CUSTOM_TRIAL = "end_custom_trial";
    private static final int FIRST_ISOLATED_UID = 99000;
    private static final int FLAG_RPC_CALL = 0;
    private static final int LAST_ISOLATED_UID = 99999;
    private static final int LICENSED = 0;
    private static final int MAX_RETRIES = 3;
    private static final int MILLIS_PER_SEC = 1000;
    private static final long MIN_TRIAL_END_INTERVAL_MILLIS = 3000L;
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
    protected static boolean customTrialEndTriggered = false;
    protected static boolean eventualShutdownEnabled = true;
    protected static Runnable exitAction;
    public static boolean gracefulShutdownEnabled = true;
    private static final Handler handler;
    private static LicenseClient instance;
    protected static long lastTrialEndElapsedRealtimeMillis = 0L;
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
    protected int retryNum = 0;
    protected boolean waitingForRepeatedCheck = false;

    public static /* synthetic */ void $r8$lambda$8YRQpF8qc5JOZUcKq79QHnbGjYY(LicenseClient licenseClient, RepeatedCheckMetadata repeatedCheckMetadata) {
        licenseClient.lambda$scheduleRepeatedLicenseCheck$0(repeatedCheckMetadata);
    }

    public static /* synthetic */ void $r8$lambda$GS82Fij7VQePgSFog_s63_Rcyb0(LicenseClient licenseClient) {
        licenseClient.lambda$initializeLicenseCheck$0();
    }

    public static /* synthetic */ void $r8$lambda$gb_vmUiJUmqdCloCudVdY_igh7I(LicenseClient licenseClient, IBinder iBinder) {
        licenseClient.lambda$onServiceConnected$1(iBinder);
    }

    public static /* synthetic */ void $r8$lambda$nn58bl0tYjCXTurL0z1br2IkQ_g(LicenseClient licenseClient, LicenseCheckException licenseCheckException) {
        licenseClient.lambda$handleError$0(licenseCheckException);
    }

    public static /* synthetic */ void $r8$lambda$ot_XkRbEJeEFG1Hy_d3H6N4DX_I(LicenseClient licenseClient, RepeatedCheckMetadata repeatedCheckMetadata, Bundle bundle) {
        licenseClient.lambda$processResponse$0(repeatedCheckMetadata, bundle);
    }

    public static /* synthetic */ void $r8$lambda$q2q7YKfx3jIZHqiUNn7fQ55wwzI(LicenseClient licenseClient, boolean bl) {
        licenseClient.lambda$initializeLicenseCheck$1(bl);
    }

    public static /* synthetic */ void $r8$lambda$tB8S6FJPE8_x6_ohvmHs84x5lek(LicenseClient licenseClient, boolean bl, LicenseCheckException licenseCheckException, boolean bl2) {
        licenseClient.lambda$retryOrThrow$0(bl, licenseCheckException, bl2);
    }

    public static /* synthetic */ void $r8$lambda$x_INbtAE1cLJhbPOU3l3uRiKDN8(LicenseClient licenseClient, boolean bl) {
        licenseClient.lambda$retryOrThrow$1(bl);
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
        backgroundRunner = new LicenseClient$$ExternalSyntheticLambda4();
        LicenseClient.handler = handler = new Handler(Looper.getMainLooper());
        Objects.requireNonNull(handler);
        mainThreadRunner = new LicenseClient$$ExternalSyntheticLambda5(handler);
        customTrialEndTriggered = false;
        lastTrialEndElapsedRealtimeMillis = 0L;
    }

    public LicenseClient(Context context) {
        this.context = context;
    }

    private void bindToLicensingService(boolean bl) {
        String string = bl ? "Connecting to the background licensing service..." : "Connecting to the main licensing service...";
        Log.d((String)TAG, (String)string);
        string = bl ? BACKGROUND_SERVICE_INTERFACE_CLASS_NAME : SERVICE_INTERFACE_CLASS_NAME;
        Intent intent = new Intent(string).setPackage(SERVICE_PACKAGE).setAction(string);
        boolean bl2 = this.context.bindService(intent, (ServiceConnection)this, 1);
        if (!bl2) {
            this.retryOrThrow(new LicenseCheckException("Could not bind with the licensing service: ".concat(string)), bl, bl);
        }
    }

    public static void checkLicense(Context context) {
        if (context == null) {
            Log.w((String)TAG, (String)"Cannot check license with null context.");
            return;
        }
        if (LicenseClient.isIsolatedProcess()) {
            Log.i((String)TAG, (String)"Skipping license check in isolated process.");
            return;
        }
        mainThreadRunner.run(new LicenseClient$$ExternalSyntheticLambda6(context));
    }

    private void checkLicenseInternal(IBinder object) throws LicenseCheckException, RemoteException {
        if (object == null) {
            this.retryOrThrow(new LicenseCheckException("Received a null binder."));
            return;
        }
        if (!object.getInterfaceDescriptor().equals(BACKGROUND_SERVICE_INTERFACE_CLASS_NAME)) {
            Log.d((String)TAG, (String)"Sending request to licensing service...");
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            this.populateInputDataForLicenseCheckV2(parcel, (IBinder)object);
            if (!object.transact(2, parcel, parcel2, 0)) {
                object = new LicenseCheckException("Licensing service could not process request.");
                this.handleError((LicenseCheckException)object);
            }
            parcel.recycle();
            parcel2.recycle();
            Log.d((String)TAG, (String)"Request to licensing service sent.");
            return;
        }
        throw new LicenseCheckException("Background licensing service does not support full license check.");
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

    private static LicenseClient getInstance(Context context) {
        synchronized (LicenseClient.class) {
            LicenseClient licenseClient;
            LicenseClient licenseClient2 = licenseClient = instance;
            if (licenseClient == null) {
                instance = licenseClient2 = new LicenseClient(context);
            }
            return licenseClient2;
        }
    }

    public static String getLicensePubKey() {
        return licensePubKey;
    }

    private void handleError(LicenseCheckException licenseCheckException) {
        mainThreadRunner.run(new LicenseClient$$ExternalSyntheticLambda3(this, licenseCheckException));
    }

    private void initiateFreshLicensingServiceConnection(boolean bl) {
        this.retryNum = 0;
        this.bindToLicensingService(bl);
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

    static /* synthetic */ void lambda$checkLicense$0(Context context) {
        LicenseClient.getInstance(context).initializeLicenseCheck();
    }

    private /* synthetic */ void lambda$handleError$0(LicenseCheckException object) {
        object = Log.getStackTraceString((Throwable)object);
        StringBuilder stringBuilder = new StringBuilder("Error while checking license: ");
        stringBuilder.append((String)object);
        Log.e((String)TAG, (String)stringBuilder.toString());
        if (licenseCheckState.equals((Object)LicenseCheckState.FULL_CHECK_OK)) {
            return;
        }
        this.startErrorDialogActivity();
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
        this.initiateFreshLicensingServiceConnection(bl);
    }

    private /* synthetic */ void lambda$onServiceConnected$0(IBinder iBinder) {
        this.checkLicenseInternal(iBinder);
    }

    private /* synthetic */ void lambda$onServiceConnected$1(IBinder iBinder) {
        this.reportSuccessfulLicenseCheck(iBinder);
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

    private /* synthetic */ void lambda$retryOrThrow$0(boolean bl, LicenseCheckException object, boolean bl2) {
        int n3 = this.retryNum;
        if (n3 < 3) {
            this.retryNum = n3 + 1;
            this.delayedTaskExecutor.schedule(new LicenseClient$$ExternalSyntheticLambda12(this, bl), 1000L);
            n3 = this.retryNum;
            object = object == null ? "null" : ((Throwable)object).getMessage();
            Log.d((String)TAG, (String)String.format("Retry #%d. License check failed with error '%s'. Next try in %ds...", n3, object, 1L));
            return;
        }
        if (bl2) {
            object = String.valueOf(object);
            StringBuilder stringBuilder = new StringBuilder("Retry limit reached for: ");
            stringBuilder.append((String)object);
            Log.e((String)TAG, (String)stringBuilder.toString());
            return;
        }
        this.handleError((LicenseCheckException)object);
    }

    private /* synthetic */ void lambda$retryOrThrow$1(boolean bl) {
        this.bindToLicensingService(bl);
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
        this.initiateFreshLicensingServiceConnection(false);
    }

    static /* synthetic */ void lambda$static$0(Runnable runnable) {
        new Thread(runnable).start();
    }

    static /* synthetic */ void lambda$stopTrial$0(Context context) {
        LicenseClient.getInstance(context).handleTrialEnd();
    }

    private boolean performLocalInstallerCheck() {
        if (Build.VERSION.SDK_INT < 30) {
            Log.i((String)TAG, (String)"Local install check bypassed due to old SDK version.");
            return false;
        }
        PackageManager packageManager = this.context.getPackageManager();
        if (packageManager == null) {
            Log.i((String)TAG, (String)"Local install check bypassed due to package manager not found.");
            return false;
        }
        Object object = packageManager.getPackageInfo(packageName, 0);
        if (object != null && ((PackageInfo)object).applicationInfo != null) {
            int n3 = ((PackageInfo)object).applicationInfo.flags;
            if ((n3 & 1) == 0 && (n3 & 0x80) == 0) {
                object = packageManager.getInstallSourceInfo(packageName);
                if (object == null) {
                    Log.i((String)TAG, (String)"Local install check bypassed due to install source info not found.");
                    return false;
                }
                if ((object = object.getInstallingPackageName()) != null && ((String)object).equals(SERVICE_PACKAGE)) {
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
        iBinder = new Bundle();
        if (customTrialEndTriggered) {
            iBinder.putBoolean(EXTRA_END_CUSTOM_TRIAL, true);
        }
        if (!iBinder.isEmpty()) {
            parcel.writeInt(1);
            iBinder.writeToParcel(parcel, 0);
            return;
        }
        parcel.writeInt(0);
    }

    private void populateInputDataForReportAutoVerifiedLicense(Parcel parcel, IBinder iBinder) throws RemoteException {
        parcel.writeInterfaceToken(iBinder.getInterfaceDescriptor());
        parcel.writeString(packageName);
        parcel.writeInt(0);
    }

    private void processResponse(int n3, Bundle object) {
        if (n3 != 3) {
            if (n3 == 0) {
                LicenseResponseHelper.validateResponse(object, packageName);
                Log.i((String)TAG, (String)"License check succeeded.");
                RepeatedCheckMetadata repeatedCheckMetadata = repeatedCheckEnabled ? LicenseResponseHelper.getRepeatedCheckMetadata(object) : null;
                ImmediateTaskExecutor immediateTaskExecutor = mainThreadRunner;
                LicenseClient$$ExternalSyntheticLambda10 licenseClient$$ExternalSyntheticLambda10 = new LicenseClient$$ExternalSyntheticLambda10(this, repeatedCheckMetadata, (Bundle)object);
                immediateTaskExecutor.run(licenseClient$$ExternalSyntheticLambda10);
                return;
            }
            if (n3 == 2) {
                this.startPaywallActivity((PendingIntent)object.getParcelable(PAYLOAD_PAYWALL));
                return;
            }
            object = new LicenseCheckException(String.format("Unexpected response code %d received.", n3));
            throw object;
        }
        object = new LicenseCheckException("Request package name invalid.");
        throw object;
    }

    private void retryOrThrow(LicenseCheckException licenseCheckException) {
        this.retryOrThrow(licenseCheckException, false, false);
    }

    private void retryOrThrow(LicenseCheckException licenseCheckException, boolean bl, boolean bl2) {
        mainThreadRunner.run(new LicenseClient$$ExternalSyntheticLambda1(this, bl2, licenseCheckException, bl));
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
            this.context.unbindService((ServiceConnection)this);
        }
        this.delayedTaskExecutor.schedule(new LicenseClient$$ExternalSyntheticLambda13(this, repeatedCheckMetadata), l3);
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

    static void stopTrial(Context context) {
        if (context == null) {
            Log.w((String)TAG, (String)"Cannot trigger trial end with null context.");
            return;
        }
        if (LicenseClient.isIsolatedProcess()) {
            Log.i((String)TAG, (String)"Skipping trial end in isolated process.");
            return;
        }
        mainThreadRunner.run(new LicenseClient$$ExternalSyntheticLambda2(context));
    }

    protected long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    protected long getElapsedRealtimeMillis() {
        return SystemClock.elapsedRealtime();
    }

    protected void handleTrialEnd() {
        long l3 = this.getElapsedRealtimeMillis();
        long l4 = lastTrialEndElapsedRealtimeMillis;
        long l5 = l3 - l4;
        if (l4 > 0L && l5 < 3000L) {
            Log.w((String)TAG, (String)String.format("Trial end trigger throttled. Ignoring request (sent %d ms ago).", l5));
            return;
        }
        Log.i((String)TAG, (String)"Trial end event triggered; initiating full license check.");
        lastTrialEndElapsedRealtimeMillis = l3;
        customTrialEndTriggered = true;
        licenseCheckState = LicenseCheckState.CHECK_REQUIRED;
        this.waitingForRepeatedCheck = false;
        this.initiateFreshLicensingServiceConnection(false);
    }

    public void initializeLicenseCheck() {
        int n3 = licenseCheckState.ordinal();
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 4) {
                    return;
                }
                this.initiateFreshLicensingServiceConnection(false);
                return;
            }
            LicenseResponseHelper.validateResponse(responsePayload, packageName);
            return;
        }
        if (localCheckEnabled && !customTrialEndTriggered) {
            backgroundRunner.run(new LicenseClient$$ExternalSyntheticLambda7(this));
            return;
        }
        this.initiateFreshLicensingServiceConnection(false);
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
                backgroundRunner.run(new LicenseClient$$ExternalSyntheticLambda9(this, iBinder));
                return;
            }
        }
        backgroundRunner.run(new LicenseClient$$ExternalSyntheticLambda8(this, iBinder));
    }

    public void onServiceDisconnected(ComponentName componentName) {
        if (licenseCheckState.equals((Object)LicenseCheckState.REPEATED_CHECK_REQUIRED) && this.waitingForRepeatedCheck) {
            Log.d((String)TAG, (String)"Ignoring service disconnection in REPEATED_CHECK_REQUIRED state.");
            return;
        }
        Log.w((String)TAG, (String)"Unexpectedly disconnected from the licensing service.");
        this.retryOrThrow(new LicenseCheckException("Licensing service unexpectedly disconnected."));
    }

    public void reportSuccessfulLicenseCheck(IBinder object) throws LicenseCheckException {
        if (object == null) {
            this.retryOrThrow(new LicenseCheckException("Received a null binder."), true, backgroundLicensingServiceEnabled);
            return;
        }
        Log.d((String)TAG, (String)"Sending request to license reporting service...");
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        this.populateInputDataForReportAutoVerifiedLicense(parcel, (IBinder)object);
        boolean bl = object.transact(3, parcel, parcel2, 0);
        if (!bl) {
            Log.e((String)TAG, (String)"Error sending request to license reporting service.");
        }
        if (bl) {
            object = mainThreadRunner;
            LicenseClient$$ExternalSyntheticLambda11 licenseClient$$ExternalSyntheticLambda11 = new LicenseClient$$ExternalSyntheticLambda11();
            object.run(licenseClient$$ExternalSyntheticLambda11);
        }
        parcel.recycle();
        parcel2.recycle();
        Log.d((String)TAG, (String)"Request to licensing reporting service sent.");
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

