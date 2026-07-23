/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.Signature
 *  android.os.Build
 *  android.util.Base64
 */
package c3;

import android.content.Context;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class g0 {
    public static String a(String object) {
        object = MessageDigest.getInstance("MD5").digest(((String)object).getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        int n3 = 0;
        while (true) {
            if (n3 >= ((Object)object).length) break;
            stringBuffer.append(Integer.toHexString(object[n3] & 0xFF | 0x100).substring(1, 3));
            ++n3;
            continue;
            break;
        }
        try {
            object = stringBuffer.toString();
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return null;
        }
    }

    public static int b(Context object) {
        block4: {
            g0.a("XWfn7mKmfs1mS9OHpaqUOxoHMoo=");
            object = object.getPackageManager().getPackageInfo((String)object.getPackageName(), (int)64).signatures;
            if (((Signature[])object).length <= 0) break block4;
            Signature signature = object[0];
            try {
                signature.toByteArray();
                object = MessageDigest.getInstance("SHA");
                ((MessageDigest)object).update(signature.toByteArray());
                boolean bl = "XWfn7mKmfs1mS9OHpaqUOxoHMoo=".equals(Base64.encodeToString((byte[])((MessageDigest)object).digest(), (int)0).substring(0, 28));
                if (bl) {
                    return 0;
                }
                return -1;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return -1;
    }

    public static int c(int n3, int n4, int n5, int n6) {
        int n7 = n3;
        if (n3 < -49) {
            n7 = n3 + n4 + n6;
        }
        int n8 = n7;
        if (n7 > 51) {
            n8 = n7 + n4 + n5;
        }
        n3 = n4;
        if (n4 < -51) {
            n3 = n4 + n8 + n6;
        }
        n4 = n3;
        if (n3 > 49) {
            n4 = n3 + n8 + n5;
        }
        if (n8 == n5) {
            return 1;
        }
        if (n4 == n6) {
            return 101;
        }
        if (n8 == n6) {
            return 2;
        }
        if (n4 == n5) {
            return 102;
        }
        return 1002;
    }

    public static boolean d() {
        String string;
        return Build.BRAND.contains("generic") && Build.DEVICE.contains("generic") || (string = Build.FINGERPRINT).contains("generic") || string.contains("unknown") || (string = Build.HARDWARE).contains("goldfish") || string.contains("ranchu") || (string = Build.MODEL).contains("google_sdk") || string.contains("Emulator") || string.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || (string = Build.PRODUCT).contains("sdk_google") || string.contains("google_sdk") || string.contains("sdk") || string.contains("sdk_x86") || string.contains("vbox86p") || string.contains("emulator") || string.contains("simulator");
        {
        }
    }

    public static String e(String object) {
        object = ((String)object).toCharArray();
        int n3 = ((Object)object).length - 1;
        for (int i3 = 0; n3 > i3; --n3, ++i3) {
            Object object2 = object[i3];
            object[i3] = object[n3];
            object[n3] = object2;
        }
        return new String((char[])object);
    }
}

