/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.database.Cursor
 *  android.net.Uri
 */
package com.pairip.licensecheck;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.pairip.licensecheck.LicenseClient;

public class LicenseContentProvider
extends ContentProvider {
    public int delete(Uri uri, String string, String[] stringArray) {
        throw new UnsupportedOperationException("Delete is not supported ");
    }

    public String getType(Uri uri) {
        throw new UnsupportedOperationException("GetType is not supported ");
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert is not supported ");
    }

    public boolean onCreate() {
        LicenseClient.checkLicense(this.getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] stringArray, String string, String[] stringArray2, String string2) {
        throw new UnsupportedOperationException("Query is not supported ");
    }

    public int update(Uri uri, ContentValues contentValues, String string, String[] stringArray) {
        throw new UnsupportedOperationException("Update is not supported ");
    }
}

