/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.pm.ProviderInfo
 *  android.content.res.XmlResourceParser
 *  android.database.Cursor
 *  android.database.MatrixCursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.ParcelFileDescriptor
 *  android.text.TextUtils
 *  android.webkit.MimeTypeMap
 *  org.xmlpull.v1.XmlPullParserException
 */
package androidx.core.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider
extends ContentProvider {
    public static final String[] g = new String[]{"_display_name", "_size"};
    public static final File h = new File("/");
    public static final HashMap i = new HashMap();
    public final Object c = new Object();
    public String d;
    public b e;
    public final int f;

    public FileProvider() {
        this(0);
    }

    public FileProvider(int n3) {
        this.f = n3;
    }

    public static File b(File file, String ... stringArray) {
        int n3 = stringArray.length;
        File file2 = file;
        for (int i3 = 0; i3 < n3; ++i3) {
            String string = stringArray[i3];
            file = file2;
            if (string != null) {
                file = new File(file2, string);
            }
            file2 = file;
        }
        return file2;
    }

    public static Object[] c(Object[] objectArray, int n3) {
        Object[] objectArray2 = new Object[n3];
        System.arraycopy(objectArray, 0, objectArray2, 0, n3);
        return objectArray2;
    }

    public static String[] d(String[] stringArray, int n3) {
        String[] stringArray2 = new String[n3];
        System.arraycopy(stringArray, 0, stringArray2, 0, n3);
        return stringArray2;
    }

    public static XmlResourceParser e(Context object, String string, ProviderInfo providerInfo, int n3) {
        if (providerInfo != null) {
            if (providerInfo.metaData == null && n3 != 0) {
                string = new Bundle(1);
                providerInfo.metaData = string;
                string.putInt("android.support.FILE_PROVIDER_PATHS", n3);
            }
            if ((object = providerInfo.loadXmlMetaData(object.getPackageManager(), "android.support.FILE_PROVIDER_PATHS")) != null) {
                return object;
            }
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Couldn't find meta-data for provider with authority ");
        ((StringBuilder)object).append(string);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static b g(Context object, String string, int n3) {
        HashMap hashMap = i;
        synchronized (hashMap) {
            try {
                b b4;
                b b3 = b4 = (b)hashMap.get(string);
                if (b4 == null) {
                    try {
                        b3 = FileProvider.j((Context)object, string, n3);
                        hashMap.put(string, b3);
                    }
                    catch (XmlPullParserException xmlPullParserException) {
                        object = new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", xmlPullParserException);
                        throw object;
                    }
                    catch (IOException iOException) {
                        object = new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", iOException);
                        throw object;
                    }
                }
                return b3;
            }
            catch (Throwable throwable22) {}
            throw throwable22;
        }
    }

    public static Uri h(Context context, String string, File file) {
        return FileProvider.g(context, string, 0).b(file);
    }

    public static int i(String string) {
        if ("r".equals(string)) {
            return 0x10000000;
        }
        if (!"w".equals(string) && !"wt".equals(string)) {
            if ("wa".equals(string)) {
                return 0x2A000000;
            }
            if ("rw".equals(string)) {
                return 0x38000000;
            }
            if ("rwt".equals(string)) {
                return 0x3C000000;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid mode: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return 0x2C000000;
    }

    public static b j(Context context, String object, int n3) {
        c c3 = new c((String)object);
        XmlResourceParser xmlResourceParser = FileProvider.e(context, (String)object, context.getPackageManager().resolveContentProvider((String)object, 128), n3);
        while ((n3 = xmlResourceParser.next()) != 1) {
            if (n3 != 2) continue;
            File[] fileArray = xmlResourceParser.getName();
            Object var3_5 = null;
            String string = xmlResourceParser.getAttributeValue(null, "name");
            String string2 = xmlResourceParser.getAttributeValue(null, "path");
            if ("root-path".equals(fileArray)) {
                object = h;
            } else if ("files-path".equals(fileArray)) {
                object = context.getFilesDir();
            } else if ("cache-path".equals(fileArray)) {
                object = context.getCacheDir();
            } else if ("external-path".equals(fileArray)) {
                object = Environment.getExternalStorageDirectory();
            } else if ("external-files-path".equals(fileArray)) {
                fileArray = e0.a.f(context, null);
                object = var3_5;
                if (fileArray.length > 0) {
                    object = fileArray[0];
                }
            } else if ("external-cache-path".equals(fileArray)) {
                fileArray = e0.a.e(context);
                object = var3_5;
                if (fileArray.length > 0) {
                    object = fileArray[0];
                }
            } else {
                object = var3_5;
                if ("external-media-path".equals(fileArray)) {
                    fileArray = a.a(context);
                    object = var3_5;
                    if (fileArray.length > 0) {
                        object = fileArray[0];
                    }
                }
            }
            if (object == null) continue;
            c3.c(string, FileProvider.b((File)object, string2));
        }
        return c3;
    }

    public static String k(String string) {
        String string2 = string;
        if (string.length() > 0) {
            string2 = string;
            if (string.charAt(string.length() - 1) == '/') {
                string2 = string.substring(0, string.length() - 1);
            }
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void attachInfo(Context object, ProviderInfo object2) {
        super.attachInfo((Context)object, object2);
        if (object2.exported) {
            throw new SecurityException("Provider must not be exported");
        }
        if (object2.grantUriPermissions) {
            object2 = object2.authority.split(";")[0];
            object = this.c;
            synchronized (object) {
                this.d = object2;
            }
            object = i;
            synchronized (object) {
                ((HashMap)object).remove(object2);
                return;
            }
        }
        throw new SecurityException("Provider must grant uri permissions");
    }

    public int delete(Uri uri, String string, String[] stringArray) {
        return this.f().a(uri).delete() ? 1 : 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final b f() {
        Object object = this.c;
        synchronized (object) {
            try {
                n0.c.c(this.d, "mAuthority is null. Did you override attachInfo and did not call super.attachInfo()?");
                if (this.e != null) return this.e;
                this.e = FileProvider.g(this.getContext(), this.d, this.f);
                return this.e;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public String getType(Uri object) {
        object = this.f().a((Uri)object);
        int n3 = ((File)object).getName().lastIndexOf(46);
        if (n3 >= 0) {
            object = ((File)object).getName().substring(n3 + 1);
            object = MimeTypeMap.getSingleton().getMimeTypeFromExtension((String)object);
            if (object != null) {
                return object;
            }
        }
        return "application/octet-stream";
    }

    public String getTypeAnonymous(Uri uri) {
        return "application/octet-stream";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    public boolean onCreate() {
        return true;
    }

    public ParcelFileDescriptor openFile(Uri uri, String string) {
        return ParcelFileDescriptor.open((File)this.f().a(uri), (int)FileProvider.i(string));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public Cursor query(Uri stringArray, String[] stringArray2, String string, String[] object, String objectArray) {
        File file = this.f().a((Uri)stringArray);
        String string2 = stringArray.getQueryParameter("displayName");
        stringArray = stringArray2;
        if (stringArray2 == null) {
            stringArray = g;
        }
        String[] stringArray3 = new String[stringArray.length];
        Object[] objectArray2 = new Object[stringArray.length];
        int n3 = stringArray.length;
        int n4 = 0;
        int n5 = 0;
        while (true) {
            int n6;
            block10: {
                block9: {
                    String string3;
                    block8: {
                        void var2_7;
                        if (n4 >= n3) {
                            stringArray = FileProvider.d(stringArray3, n5);
                            Object[] objectArray3 = FileProvider.c(objectArray2, n5);
                            stringArray = new MatrixCursor(stringArray, 1);
                            stringArray.addRow(objectArray3);
                            return stringArray;
                        }
                        string3 = stringArray[n4];
                        if (!"_display_name".equals(string3)) break block8;
                        stringArray3[n5] = "_display_name";
                        n6 = n5 + 1;
                        if (string2 == null) {
                            String string4 = file.getName();
                        } else {
                            String string5 = string2;
                        }
                        objectArray2[n5] = var2_7;
                        n5 = n6;
                        break block9;
                    }
                    n6 = n5;
                    if (!"_size".equals(string3)) break block10;
                    stringArray3[n5] = "_size";
                    n6 = n5 + 1;
                    objectArray2[n5] = file.length();
                    n5 = n6;
                }
                n6 = n5;
            }
            ++n4;
            n5 = n6;
        }
    }

    public int update(Uri uri, ContentValues contentValues, String string, String[] stringArray) {
        throw new UnsupportedOperationException("No external updates");
    }

    public static abstract class a {
        public static File[] a(Context context) {
            return context.getExternalMediaDirs();
        }
    }

    public static interface b {
        public File a(Uri var1);

        public Uri b(File var1);
    }

    public static class c
    implements b {
        public final String a;
        public final HashMap b = new HashMap();

        public c(String string) {
            this.a = string;
        }

        @Override
        public File a(Uri object) {
            Object object2 = object.getEncodedPath();
            int n3 = ((String)object2).indexOf(47, 1);
            Object object3 = Uri.decode((String)((String)object2).substring(1, n3));
            object2 = Uri.decode((String)((String)object2).substring(n3 + 1));
            if ((object3 = (File)this.b.get(object3)) != null) {
                object = new File((File)object3, (String)object2);
                try {
                    object2 = ((File)object).getCanonicalFile();
                    if (this.d(((File)object2).getPath(), ((File)object3).getPath())) {
                        return object2;
                    }
                    throw new SecurityException("Resolved path jumped beyond configured root");
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to resolve canonical path for ");
                    stringBuilder.append(object);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Unable to find configured root for ");
            ((StringBuilder)object3).append(object);
            throw new IllegalArgumentException(((StringBuilder)object3).toString());
        }

        @Override
        public Uri b(File object) {
            Object object2;
            Iterator iterator;
            CharSequence charSequence;
            try {
                charSequence = ((File)object).getCanonicalPath();
                iterator = this.b.entrySet().iterator();
                object = null;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve canonical path for ");
                stringBuilder.append(object);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            while (iterator.hasNext()) {
                object2 = iterator.next();
                String string = ((File)object2.getValue()).getPath();
                if (!this.d((String)charSequence, string) || object != null && string.length() <= ((File)object.getValue()).getPath().length()) continue;
                object = object2;
            }
            if (object != null) {
                object2 = ((File)object.getValue()).getPath();
                object2 = ((String)object2).endsWith("/") ? ((String)charSequence).substring(((String)object2).length()) : ((String)charSequence).substring(((String)object2).length() + 1);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(Uri.encode((String)((String)object.getKey())));
                ((StringBuilder)charSequence).append('/');
                ((StringBuilder)charSequence).append(Uri.encode((String)object2, (String)"/"));
                object = ((StringBuilder)charSequence).toString();
                return new Uri.Builder().scheme("content").authority(this.a).encodedPath((String)object).build();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to find configured root that contains ");
            ((StringBuilder)object).append((String)charSequence);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public void c(String string, File file) {
            if (!TextUtils.isEmpty((CharSequence)string)) {
                try {
                    File file2 = file.getCanonicalFile();
                    this.b.put(string, file2);
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to resolve canonical path for ");
                    stringBuilder.append(file);
                    throw new IllegalArgumentException(stringBuilder.toString(), iOException);
                }
                return;
            }
            throw new IllegalArgumentException("Name must not be empty");
        }

        public final boolean d(String string, String charSequence) {
            String string2;
            if (!(string = FileProvider.k(string)).equals(string2 = FileProvider.k((String)charSequence))) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append('/');
                if (!string.startsWith(((StringBuilder)charSequence).toString())) {
                    return false;
                }
            }
            return true;
        }
    }
}

