/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 */
package androidx.startup;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import k1.a;
import k1.d;

public class InitializationProvider
extends ContentProvider {
    public final int delete(Uri uri, String string, String[] stringArray) {
        throw new IllegalStateException("Not allowed.");
    }

    public final String getType(Uri uri) {
        throw new IllegalStateException("Not allowed.");
    }

    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new IllegalStateException("Not allowed.");
    }

    public final boolean onCreate() {
        Context context = this.getContext();
        if (context != null) {
            if (context.getApplicationContext() != null) {
                a.e(context).a();
            }
            return true;
        }
        throw new d("Context cannot be null");
    }

    public final Cursor query(Uri uri, String[] stringArray, String string, String[] stringArray2, String string2) {
        throw new IllegalStateException("Not allowed.");
    }

    public final int update(Uri uri, ContentValues contentValues, String string, String[] stringArray) {
        throw new IllegalStateException("Not allowed.");
    }
}

