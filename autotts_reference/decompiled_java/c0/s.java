/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.util.Log
 */
package c0;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import c0.h;
import java.util.ArrayList;
import java.util.Iterator;

public final class s
implements Iterable {
    public final ArrayList c = new ArrayList();
    public final Context d;

    public s(Context context) {
        this.d = context;
    }

    public static s d(Context context) {
        return new s(context);
    }

    public s a(Intent intent) {
        this.c.add(intent);
        return this;
    }

    public s b(Activity activity) {
        Intent intent = activity instanceof a ? ((a)activity).p() : null;
        Intent intent2 = intent;
        if (intent == null) {
            intent2 = h.a(activity);
        }
        if (intent2 != null) {
            intent = intent2.getComponent();
            activity = intent;
            if (intent == null) {
                activity = intent2.resolveActivity(this.d.getPackageManager());
            }
            this.c((ComponentName)activity);
            this.a(intent2);
        }
        return this;
    }

    public s c(ComponentName componentName) {
        PackageManager.NameNotFoundException nameNotFoundException2;
        block4: {
            int n3 = this.c.size();
            componentName = h.b(this.d, componentName);
            while (componentName != null) {
                try {
                    this.c.add(n3, componentName);
                    componentName = h.b(this.d, componentName.getComponent());
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException2) {
                    break block4;
                }
            }
            return this;
        }
        Log.e((String)"TaskStackBuilder", (String)"Bad ComponentName while traversing activity parent metadata");
        throw new IllegalArgumentException(nameNotFoundException2);
    }

    public void e() {
        this.f(null);
    }

    public void f(Bundle bundle) {
        if (!this.c.isEmpty()) {
            Intent[] intentArray = this.c.toArray(new Intent[0]);
            intentArray[0] = new Intent(intentArray[0]).addFlags(0x1000C000);
            if (!e0.a.h(this.d, intentArray, bundle)) {
                bundle = new Intent(intentArray[intentArray.length - 1]);
                bundle.addFlags(0x10000000);
                this.d.startActivity((Intent)bundle);
            }
            return;
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
    }

    public Iterator iterator() {
        return this.c.iterator();
    }

    public static interface a {
        public Intent p();
    }
}

