/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.provider.Settings$Global
 */
package r2;

import android.content.ContentResolver;
import android.provider.Settings;

public class a {
    public float a(ContentResolver contentResolver) {
        return Settings.Global.getFloat((ContentResolver)contentResolver, (String)"animator_duration_scale", (float)1.0f);
    }
}

