/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Xml
 *  org.xmlpull.v1.XmlSerializer
 */
package c0;

import android.content.Context;
import android.util.Xml;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.xmlpull.v1.XmlSerializer;

public abstract class e {
    public static final Object a = new Object();

    public static void a(Context object, String string) {
        Object object2 = a;
        synchronized (object2) {
            if (string.equals("")) {
                object.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                return;
            }
            object = object.openFileOutput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file", 0);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput((OutputStream)object, null);
            xmlSerializer.startDocument("UTF-8", Boolean.TRUE);
            xmlSerializer.startTag(null, "locales");
            xmlSerializer.attribute(null, "application_locales", string);
            xmlSerializer.endTag(null, "locales");
            xmlSerializer.endDocument();
            if (object != null) {
                ((FileOutputStream)object).close();
            }
        }
    }

    public static String b(Context context) {
        Object object = a;
        synchronized (object) {
            String string;
            Object object2;
            FileInputStream fileInputStream;
            block4: {
                int n3;
                String string2 = "";
                fileInputStream = context.openFileInput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                object2 = Xml.newPullParser();
                object2.setInput((InputStream)fileInputStream, "UTF-8");
                int n4 = object2.getDepth();
                do {
                    n3 = object2.next();
                    string = string2;
                    if (n3 == 1) break block4;
                    if (n3 != 3) continue;
                    string = string2;
                    if (object2.getDepth() <= n4) break block4;
                } while (n3 == 3 || n3 == 4 || !object2.getName().equals("locales"));
                string = object2.getAttributeValue(null, "application_locales");
            }
            object2 = string;
            if (fileInputStream != null) {
                fileInputStream.close();
                object2 = string;
            }
            if (((String)object2).isEmpty()) {
                context.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
            }
            return object2;
        }
    }
}

