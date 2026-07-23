/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ActivityManager
 *  android.app.ActivityOptions
 *  android.app.AlertDialog$Builder
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package com.pairip.licensecheck;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda0;
import com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda1;
import com.pairip.licensecheck.LicenseActivity$$ExternalSyntheticLambda2;
import com.pairip.licensecheck.LicenseClient;

public class LicenseActivity
extends Activity {
    public static final String ACTIVITY_TYPE_ARG_NAME = "activitytype";
    public static final String PAYWALL_INTENT_ARG_NAME = "paywallintent";
    private static final String TAG = "LicenseActivity";

    public static /* synthetic */ void $r8$lambda$N5_Pzpb_eSKmOONXn3Kn0QvMbys(LicenseActivity licenseActivity) {
        licenseActivity.lambda$showErrorDialog$0();
    }

    public static /* synthetic */ void $r8$lambda$fE_XZ7S0hhHsxQNTfy8mxeJ7kEU(LicenseActivity licenseActivity, DialogInterface dialogInterface, int n3) {
        licenseActivity.lambda$showErrorDialog$1(dialogInterface, n3);
    }

    public static /* synthetic */ void $r8$lambda$x_JmBIDmuVzGN23Wk7Dd1TBpzO0(LicenseActivity licenseActivity, PendingIntent pendingIntent) {
        licenseActivity.lambda$showPaywallAndCloseApp$0(pendingIntent);
    }

    private void closeApp() {
        if (LicenseClient.gracefulShutdownEnabled) {
            this.closeAllTasks();
            return;
        }
        this.exitApp();
    }

    private /* synthetic */ void lambda$showErrorDialog$0() {
        try {
            Object object = new AlertDialog.Builder((Context)this);
            AlertDialog.Builder builder = object.setTitle((CharSequence)"Something went wrong").setMessage((CharSequence)"Check that Google Play is enabled on your device and that you're using an up-to-date version before opening the app. If the problem persists try reinstalling the app.");
            object = new LicenseActivity$$ExternalSyntheticLambda2(this);
            builder.setPositiveButton((CharSequence)"Close", (DialogInterface.OnClickListener)object).setCancelable(false).show();
            return;
        }
        catch (RuntimeException runtimeException) {
            String string = Log.getStackTraceString((Throwable)runtimeException);
            StringBuilder stringBuilder = new StringBuilder("Couldn't show the error dialog. ");
            stringBuilder.append(string);
            Log.d((String)TAG, (String)stringBuilder.toString());
            return;
        }
    }

    private /* synthetic */ void lambda$showErrorDialog$1(DialogInterface dialogInterface, int n3) {
        this.closeApp();
    }

    private /* synthetic */ void lambda$showPaywallAndCloseApp$0(PendingIntent pendingIntent) {
        try {
            if (Build.VERSION.SDK_INT >= 34) {
                pendingIntent.send(ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
            } else {
                pendingIntent.send();
            }
            this.closeApp();
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            this.logAndShowErrorDialog("Paywall intent unexpectedly cancelled.", (Exception)((Object)canceledException));
            return;
        }
    }

    private void logAndShowErrorDialog(String string) {
        Log.e((String)TAG, (String)string);
        this.showErrorDialog();
    }

    private void logAndShowErrorDialog(String string, Exception object) {
        object = Log.getStackTraceString((Throwable)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append((String)object);
        this.logAndShowErrorDialog(stringBuilder.toString());
    }

    private void showErrorDialog() {
        this.runOnUiThread(new LicenseActivity$$ExternalSyntheticLambda1(this));
    }

    private void showPaywallAndCloseApp() {
        PendingIntent pendingIntent = (PendingIntent)this.getIntent().getParcelableExtra(PAYWALL_INTENT_ARG_NAME);
        if (pendingIntent == null) {
            this.logAndShowErrorDialog("Paywall intent is not provided.");
            return;
        }
        this.runOnUiThread(new LicenseActivity$$ExternalSyntheticLambda0(this, pendingIntent));
    }

    protected void closeAllTasks() {
        ActivityManager activityManager = (ActivityManager)this.getSystemService("activity");
        if (activityManager != null) {
            for (Object object : activityManager.getAppTasks()) {
                try {
                    object.finishAndRemoveTask();
                }
                catch (RuntimeException runtimeException) {
                    int n3 = object.getTaskInfo().id;
                    object = new StringBuilder("Failed to gracefully clear task=");
                    ((StringBuilder)object).append(n3);
                    Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)runtimeException);
                }
            }
        }
        this.exitApp();
    }

    protected void exitApp() {
        this.finishAndRemoveTask();
        System.exit(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onStart() {
        super.onStart();
        try {
            int n3 = ((ActivityType)((Object)this.getIntent().getSerializableExtra(ACTIVITY_TYPE_ARG_NAME))).ordinal();
            if (n3 == 0) {
                this.showPaywallAndCloseApp();
                return;
            }
            if (n3 != 1) {
                return;
            }
            this.showErrorDialog();
            return;
        }
        catch (Exception exception) {
            this.logAndShowErrorDialog("Couldn't process license activity correctly.", exception);
            return;
        }
    }

    public static enum ActivityType {
        PAYWALL,
        ERROR_DIALOG;

    }
}

