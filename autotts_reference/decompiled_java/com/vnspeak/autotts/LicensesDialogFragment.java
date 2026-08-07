/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.DialogFragment
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.webkit.WebView
 */
package com.vnspeak.autotts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

public class LicensesDialogFragment
extends DialogFragment {
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = (WebView)LayoutInflater.from((Context)this.getActivity()).inflate(2131427373, null);
        bundle.loadUrl("file:///android_asset/open_source_licenses.html");
        return new AlertDialog.Builder((Context)this.getActivity()).setTitle((CharSequence)this.getString(2131624192)).setView((View)bundle).setPositiveButton(17039370, null).create();
    }
}

