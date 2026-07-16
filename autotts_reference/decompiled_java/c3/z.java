/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcelable
 *  android.widget.Toast
 *  org.xmlpull.v1.XmlPullParserFactory
 */
package c3;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserFactory;

public abstract class z {
    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(File object, File object2) {
        Throwable throwable3222222;
        Throwable throwable22222222;
        FileOutputStream fileOutputStream;
        block11: {
            object = new FileInputStream((File)object);
            fileOutputStream = new FileOutputStream((File)object2);
            try {
                int n3;
                object2 = new byte[1024];
                while ((n3 = ((InputStream)object).read((byte[])object2)) > 0) {
                    ((OutputStream)fileOutputStream).write((byte[])object2, 0, n3);
                }
            }
            catch (Throwable throwable22222222) {
                break block11;
            }
            ((OutputStream)fileOutputStream).close();
            {
                catch (Throwable throwable3222222) {}
            }
            ((InputStream)object).close();
            return;
        }
        try {
            ((OutputStream)fileOutputStream).close();
            throw throwable22222222;
        }
        catch (Throwable throwable4) {
            throwable22222222.addSuppressed(throwable4);
            throw throwable22222222;
        }
        try {
            ((InputStream)object).close();
            throw throwable3222222;
        }
        catch (Throwable throwable5) {
            throwable3222222.addSuppressed(throwable5);
        }
        throw throwable3222222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Set b(String object) {
        Object object2;
        Object object3;
        HashSet<String> hashSet;
        Object object4;
        block12: {
            int n3;
            block11: {
                System.out.println("getActiveEnginePackages");
                object4 = new HashMap();
                hashSet = new HashSet<String>();
                try {
                    object3 = XmlPullParserFactory.newInstance().newPullParser();
                    object2 = new StringReader((String)object);
                    object3.setInput((Reader)object2);
                    n3 = object3.getEventType();
                    break block11;
                }
                catch (Exception exception) {}
                exception.printStackTrace();
                break block12;
            }
            while (n3 != 1) {
                if (n3 == 2) {
                    object2 = object3.getName();
                    object = object3.getAttributeValue(null, "name");
                    if (object != null) {
                        if (((String)object2).equals("boolean") && ((String)object).endsWith("_disabled")) {
                            if ("true".equals(object3.getAttributeValue(null, "value"))) {
                                hashSet.add(((String)object).substring(0, ((String)object).length() - 9));
                            }
                        } else if (((String)object2).equals("string") && ((String)object).length() == 3 && z.c((String)object) && (object2 = object3.nextText()) != null && ((String)object2).contains("#")) {
                            object4.put(object, object2);
                        }
                    }
                }
                n3 = object3.next();
            }
        }
        object = new HashSet();
        object4 = object4.entrySet().iterator();
        while (object4.hasNext()) {
            object2 = (Map.Entry)object4.next();
            object3 = (String)object2.getKey();
            String string = (String)object2.getValue();
            object2 = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("- ");
            stringBuilder.append((String)object3);
            stringBuilder.append(" ");
            stringBuilder.append(string);
            ((PrintStream)object2).println(stringBuilder.toString());
            if (hashSet.contains(object3) || (string = string.split("#")[0]).isEmpty() || string.equalsIgnoreCase("disable")) continue;
            object3 = System.out;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" -> ");
            ((StringBuilder)object2).append(string);
            ((PrintStream)object3).println(((StringBuilder)object2).toString());
            object.add(string);
        }
        return object;
    }

    public static boolean c(String object) {
        object = ((String)object).toCharArray();
        int n3 = ((Object)object).length;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (Character.isLetter((char)object[i3])) continue;
            return false;
        }
        return true;
    }

    public static void d(Context context) {
        Comparable<File> comparable = new File(context.getApplicationInfo().dataDir, "shared_prefs/auto_tts_settings.xml");
        if (!((File)comparable).exists()) {
            Toast.makeText((Context)context, (CharSequence)"Settings file not found", (int)0).show();
            return;
        }
        File file = new File(context.getCacheDir(), "shared");
        file.mkdirs();
        file = new File(file, "auto_tts_settings.xml");
        try {
            z.a((File)comparable, file);
            comparable = new StringBuilder();
            ((StringBuilder)comparable).append(context.getPackageName());
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
        ((StringBuilder)comparable).append(".fileprovider");
        file = FileProvider.h(context, ((StringBuilder)comparable).toString(), file);
        comparable = new Intent("android.intent.action.SEND");
        comparable.setType("text/xml");
        comparable.putExtra("android.intent.extra.STREAM", (Parcelable)file);
        comparable.addFlags(1);
        context.startActivity(Intent.createChooser(comparable, (CharSequence)"Share Settings"));
    }
}

