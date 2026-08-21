/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ContentProviderClient
 *  android.content.ContentUris
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ProviderInfo
 *  android.content.pm.Signature
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.CancellationSignal
 *  android.os.RemoteException
 *  android.util.Log
 */
package l0;

import android.content.ContentProviderClient;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import l0.c;
import l0.e;
import l0.g;

public abstract class d {
    public static final Comparator a = new c();

    public static /* synthetic */ int a(byte[] byArray, byte[] byArray2) {
        if (byArray.length != byArray2.length) {
            return byArray.length - byArray2.length;
        }
        for (int i3 = 0; i3 < byArray.length; ++i3) {
            byte by = byArray[i3];
            byte by2 = byArray2[i3];
            if (by == by2) continue;
            return by - by2;
        }
        return 0;
    }

    public static List b(Signature[] signatureArray) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        int n3 = signatureArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            arrayList.add(signatureArray[i3].toByteArray());
        }
        return arrayList;
    }

    public static boolean c(List list, List list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i3 = 0; i3 < list.size(); ++i3) {
            if (Arrays.equals((byte[])list.get(i3), (byte[])list2.get(i3))) continue;
            return false;
        }
        return true;
    }

    public static List d(e e3, Resources resources) {
        if (e3.b() != null) {
            return e3.b();
        }
        return f0.e.c(resources, e3.c());
    }

    public static g.a e(Context context, e e3, CancellationSignal cancellationSignal) {
        ProviderInfo providerInfo = d.f(context.getPackageManager(), e3, context.getResources());
        if (providerInfo == null) {
            return g.a.a(1, null);
        }
        return g.a.a(0, d.g(context, e3, providerInfo.authority, cancellationSignal));
    }

    public static ProviderInfo f(PackageManager object, e object2, Resources object3) {
        String string = ((e)object2).e();
        ProviderInfo providerInfo = object.resolveContentProvider(string, 0);
        if (providerInfo != null) {
            if (providerInfo.packageName.equals(((e)object2).f())) {
                object = d.b(object.getPackageInfo((String)providerInfo.packageName, (int)64).signatures);
                Collections.sort(object, a);
                object2 = d.d((e)object2, object3);
                for (int i3 = 0; i3 < object2.size(); ++i3) {
                    object3 = new ArrayList((Collection)object2.get(i3));
                    Collections.sort(object3, a);
                    if (!d.c((List)object, (List)object3)) continue;
                    return providerInfo;
                }
                return null;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Found content provider ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(", but package was not ");
            ((StringBuilder)object).append(((e)object2).f());
            throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No package found for authority: ");
        ((StringBuilder)object).append(string);
        throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static g.b[] g(Context object, e object2, String object3, CancellationSignal object4) {
        block18: {
            block20: {
                int n3;
                int n4;
                int n5;
                int n6;
                int n7;
                int n8;
                Object object5;
                Uri uri;
                Uri uri2;
                block17: {
                    ArrayList arrayList;
                    block16: {
                        arrayList = new ArrayList();
                        uri2 = new Uri.Builder().scheme("content").authority((String)object3).build();
                        uri = new Uri.Builder().scheme("content").authority((String)object3).appendPath("file").build();
                        object = l0.d$a.a((Context)object, uri2);
                        Object var17_8 = null;
                        object3 = var17_8;
                        object5 = object;
                        try {
                            object2 = ((e)object2).g();
                            object3 = var17_8;
                            object5 = object;
                            object2 = object.b(uri2, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{object2}, null, (CancellationSignal)object4);
                            if (object2 == null) break block16;
                            object3 = object2;
                            object5 = object;
                            if (object2.getCount() <= 0) break block16;
                            object3 = object2;
                            object5 = object;
                            n8 = object2.getColumnIndex("result_code");
                            object3 = object2;
                            object5 = object;
                            object3 = object2;
                            object5 = object;
                            object4 = new ArrayList();
                            object3 = object2;
                            object5 = object;
                            n7 = object2.getColumnIndex("_id");
                            object3 = object2;
                            object5 = object;
                            n6 = object2.getColumnIndex("file_id");
                            object3 = object2;
                            object5 = object;
                            n5 = object2.getColumnIndex("font_ttc_index");
                            object3 = object2;
                            object5 = object;
                            n4 = object2.getColumnIndex("font_weight");
                            object3 = object2;
                            object5 = object;
                            n3 = object2.getColumnIndex("font_italic");
                            break block17;
                        }
                        catch (Throwable throwable) {
                            object2 = object3;
                            object3 = object5;
                            break block18;
                        }
                    }
                    object3 = arrayList;
                    break block20;
                }
                while (true) {
                    boolean bl;
                    int n9;
                    int n10;
                    int n11;
                    block22: {
                        block21: {
                            block19: {
                                object3 = object2;
                                object5 = object;
                                if (!object2.moveToNext()) break;
                                if (n8 != -1) {
                                    object3 = object2;
                                    object5 = object;
                                    n11 = object2.getInt(n8);
                                } else {
                                    n11 = 0;
                                }
                                if (n5 != -1) {
                                    object3 = object2;
                                    object5 = object;
                                    n10 = object2.getInt(n5);
                                } else {
                                    n10 = 0;
                                }
                                if (n6 == -1) {
                                    try {
                                        object3 = ContentUris.withAppendedId((Uri)uri2, (long)object2.getLong(n7));
                                        break block19;
                                    }
                                    catch (Throwable throwable) {
                                        object3 = object;
                                        object = throwable;
                                        break block18;
                                    }
                                }
                                object3 = ContentUris.withAppendedId((Uri)uri, (long)object2.getLong(n6));
                            }
                            n9 = n4 != -1 ? object2.getInt(n4) : 400;
                            if (n3 == -1) break block21;
                            int n12 = object2.getInt(n3);
                            bl = true;
                            if (n12 == 1) break block22;
                        }
                        bl = false;
                    }
                    ((ArrayList)object4).add(g.b.a((Uri)object3, n10, n9, bl, n11));
                }
                object3 = object4;
            }
            if (object2 != null) {
                object2.close();
            }
            object.close();
            return ((ArrayList)object3).toArray(new g.b[0]);
        }
        if (object2 != null) {
            object2.close();
        }
        object3.close();
        throw object;
    }

    public static interface a {
        public static a a(Context context, Uri uri) {
            return new b(context, uri);
        }

        public Cursor b(Uri var1, String[] var2, String var3, String[] var4, String var5, CancellationSignal var6);

        public void close();
    }

    public static class b
    implements a {
        public final ContentProviderClient a;

        public b(Context context, Uri uri) {
            this.a = context.getContentResolver().acquireUnstableContentProviderClient(uri);
        }

        @Override
        public Cursor b(Uri uri, String[] stringArray, String string, String[] stringArray2, String string2, CancellationSignal cancellationSignal) {
            ContentProviderClient contentProviderClient = this.a;
            if (contentProviderClient == null) {
                return null;
            }
            try {
                uri = contentProviderClient.query(uri, stringArray, string, stringArray2, string2, cancellationSignal);
                return uri;
            }
            catch (RemoteException remoteException) {
                Log.w((String)"FontsProvider", (String)"Unable to query the content provider", (Throwable)remoteException);
                return null;
            }
        }

        @Override
        public void close() {
            ContentProviderClient contentProviderClient = this.a;
            if (contentProviderClient != null) {
                contentProviderClient.close();
            }
        }
    }
}

