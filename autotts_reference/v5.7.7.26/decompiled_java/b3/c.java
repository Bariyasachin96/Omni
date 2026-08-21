/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package b3;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Scanner;

public abstract class c {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(URI object, Map map) {
        Scanner scanner = new Scanner(((URI)object).getRawQuery());
        scanner.useDelimiter("&");
        try {
            while (true) {
                if (!scanner.hasNext()) {
                    return;
                }
                String[] stringArray = scanner.next().split("=");
                int n3 = stringArray.length;
                if (n3 == 1) {
                    object = null;
                } else {
                    if (stringArray.length != 2) {
                        object = new IllegalArgumentException("query parameter invalid");
                        throw object;
                    }
                    object = URLDecoder.decode(stringArray[1], "UTF-8");
                }
                map.put(URLDecoder.decode(stringArray[0], "UTF-8"), object);
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.e((String)"URIQueryDecoder", (String)"UTF-8 Not Recognized as a charset.  Device configuration Error.");
            return;
        }
    }
}

