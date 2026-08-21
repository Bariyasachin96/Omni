/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 */
package com.google.android.play.core.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

public class PlayCoreDialogWrapperActivity
extends Activity {
    public ResultReceiver c;

    public final void a() {
        ResultReceiver resultReceiver = this.c;
        if (resultReceiver != null) {
            resultReceiver.send(3, new Bundle());
        }
    }

    public final void onActivityResult(int n3, int n4, Intent intent) {
        super.onActivityResult(n3, n4, intent);
        if (n3 == 0 && (intent = this.c) != null) {
            if (n4 == -1) {
                intent.send(1, new Bundle());
            } else if (n4 == 0) {
                intent.send(2, new Bundle());
            }
        }
        this.finish();
    }

    /*
     * Unable to fully structure code
     */
    public final void onCreate(Bundle var1_1) {
        var2_4 = this.getIntent().getIntExtra("window_flags", 0);
        var4_5 = null;
        if (var2_4 != 0) {
            this.getWindow().getDecorView().setSystemUiVisibility(var2_4);
            var3_6 = new Intent();
            var3_6.putExtra("window_flags", var2_4);
        } else {
            var3_6 = null;
        }
        super.onCreate(var1_1);
        if (var1_1 == null) {
            this.c = (ResultReceiver)this.getIntent().getParcelableExtra("result_receiver");
            var5_7 = this.getIntent().getExtras();
            var1_1 = var4_5;
            if (var5_7 != null) {
                var1_1 = (PendingIntent)var5_7.get("confirmation_intent");
            }
            if (var5_7 != null && var1_1 != null) {
                try {
                    var1_1 = var1_1.getIntentSender();
                }
                catch (IntentSender.SendIntentException var1_2) lbl-1000:
                // 2 sources

                {
                    while (true) {
                        this.a();
                        this.finish();
                        return;
                    }
                }
                this.startIntentSenderForResult((IntentSender)var1_1, 0, var3_6, 0, 0, 0);
                return;
            }
            this.a();
            this.finish();
            return;
        }
        this.c = (ResultReceiver)var1_1.getParcelable("result_receiver");
        return;
        catch (IntentSender.SendIntentException var1_3) {
            ** continue;
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("result_receiver", (Parcelable)this.c);
    }
}

