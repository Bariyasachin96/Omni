/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ResolveInfo
 *  android.content.pm.Signature
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package androidx.emoji2.text;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Log;
import androidx.emoji2.text.f;
import androidx.emoji2.text.k;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l0.e;
import n0.h;

public abstract class d {
    public static k a(Context context) {
        return (k)new a(null).c(context);
    }

    public static class a {
        public final b a;

        public a(b b3) {
            if (b3 == null) {
                b3 = androidx.emoji2.text.d$a.e();
            }
            this.a = b3;
        }

        public static b e() {
            if (Build.VERSION.SDK_INT >= 28) {
                return new d();
            }
            return new c();
        }

        public final f.c a(Context context, e e3) {
            if (e3 == null) {
                return null;
            }
            return new k(context, e3);
        }

        public final List b(Signature[] signatureArray) {
            ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
            int n3 = signatureArray.length;
            for (int i3 = 0; i3 < n3; ++i3) {
                arrayList.add(signatureArray[i3].toByteArray());
            }
            return Collections.singletonList(arrayList);
        }

        public f.c c(Context context) {
            return this.a(context, this.h(context));
        }

        public final e d(ProviderInfo object, PackageManager packageManager) {
            String string = object.authority;
            object = object.packageName;
            return new e(string, (String)object, "emojicompat-emoji-font", this.b(this.a.b(packageManager, (String)object)));
        }

        public final boolean f(ProviderInfo providerInfo) {
            return providerInfo != null && (providerInfo = providerInfo.applicationInfo) != null && (providerInfo.flags & 1) == 1;
        }

        public final ProviderInfo g(PackageManager object) {
            for (ResolveInfo resolveInfo : this.a.c((PackageManager)object, new Intent("androidx.content.action.LOAD_EMOJI_FONT"), 0)) {
                if (!this.f((ProviderInfo)(resolveInfo = this.a.a(resolveInfo)))) continue;
                return resolveInfo;
            }
            return null;
        }

        public e h(Context object) {
            PackageManager packageManager = object.getPackageManager();
            h.h(packageManager, "Package manager required to locate emoji font provider");
            object = this.g(packageManager);
            if (object == null) {
                return null;
            }
            try {
                object = this.d((ProviderInfo)object, packageManager);
                return object;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.wtf((String)"emoji2.text.DefaultEmojiConfig", (Throwable)nameNotFoundException);
                return null;
            }
        }
    }

    public static abstract class b {
        public abstract ProviderInfo a(ResolveInfo var1);

        public Signature[] b(PackageManager packageManager, String string) {
            return packageManager.getPackageInfo((String)string, (int)64).signatures;
        }

        public abstract List c(PackageManager var1, Intent var2, int var3);
    }

    public static class c
    extends b {
        @Override
        public ProviderInfo a(ResolveInfo resolveInfo) {
            return resolveInfo.providerInfo;
        }

        @Override
        public List c(PackageManager packageManager, Intent intent, int n3) {
            return packageManager.queryIntentContentProviders(intent, n3);
        }
    }

    public static class d
    extends c {
        @Override
        public Signature[] b(PackageManager packageManager, String string) {
            return packageManager.getPackageInfo((String)string, (int)64).signatures;
        }
    }
}

