/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.CancellationSignal
 *  android.os.Process
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 *  android.util.Log
 */
package g0;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import l0.g;

public abstract class k {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     */
    public static boolean b(File file, Resources object, int n3) {
        void var0_3;
        block4: {
            boolean bl;
            object = object.openRawResource(n3);
            try {
                bl = k.c(file, (InputStream)object);
            }
            catch (Throwable throwable) {
                break block4;
            }
            k.a((Closeable)object);
            return bl;
            catch (Throwable throwable) {
                object = null;
            }
        }
        k.a((Closeable)object);
        throw var0_3;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean c(File object, InputStream inputStream) {
        void var0_3;
        Object object2;
        StrictMode.ThreadPolicy threadPolicy;
        block8: {
            Object object3;
            block9: {
                threadPolicy = StrictMode.allowThreadDiskWrites();
                Object var5_9 = null;
                File file = null;
                object2 = file;
                object2 = file;
                object3 = new FileOutputStream((File)object, false);
                try {
                    int n3;
                    object = new byte[1024];
                    while ((n3 = inputStream.read((byte[])object)) != -1) {
                        ((FileOutputStream)object3).write((byte[])object, 0, n3);
                    }
                }
                catch (Throwable throwable) {
                    object2 = object3;
                    break block8;
                }
                catch (IOException iOException) {
                    object = object3;
                    break block9;
                }
                k.a((Closeable)object3);
                StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
                return true;
                catch (Throwable throwable) {
                    break block8;
                }
                catch (IOException iOException) {
                    object = var5_9;
                }
            }
            object2 = object;
            {
                void var1_7;
                object2 = object;
                object3 = new StringBuilder();
                object2 = object;
                ((StringBuilder)object3).append("Error copying resource contents to temp file: ");
                object2 = object;
                ((StringBuilder)object3).append(var1_7.getMessage());
                object2 = object;
                Log.e((String)"TypefaceCompatUtil", (String)((StringBuilder)object3).toString());
            }
            k.a((Closeable)object);
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
            return false;
        }
        k.a(object2);
        StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        throw var0_3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static File d(Context object) {
        File file = object.getCacheDir();
        if (file == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".font");
        stringBuilder.append(Process.myPid());
        stringBuilder.append("-");
        stringBuilder.append(Process.myTid());
        stringBuilder.append("-");
        String string = stringBuilder.toString();
        int n3 = 0;
        while (n3 < 100) {
            Comparable<StringBuilder> comparable = new StringBuilder();
            ((StringBuilder)comparable).append(string);
            ((StringBuilder)comparable).append(n3);
            comparable = new File(file, ((StringBuilder)comparable).toString());
            try {
                boolean bl = ((File)comparable).createNewFile();
                if (bl) {
                    return comparable;
                }
            }
            catch (IOException iOException) {}
            ++n3;
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ByteBuffer e(Context context, CancellationSignal object, Uri object2) {
        Throwable throwable4222222;
        block14: {
            context = context.getContentResolver();
            context = context.openFileDescriptor((Uri)object2, "r", (CancellationSignal)object);
            if (context != null) break block14;
            if (context == null) return null;
            context.close();
            return null;
            {
                catch (IOException iOException) {
                    return null;
                }
            }
        }
        object = new FileInputStream(context.getFileDescriptor());
        object2 = ((FileInputStream)object).getChannel();
        long l3 = ((FileChannel)object2).size();
        object2 = ((FileChannel)object2).map(FileChannel.MapMode.READ_ONLY, 0L, l3);
        ((FileInputStream)object).close();
        context.close();
        return object2;
        catch (Throwable throwable2) {
            try {
                ((FileInputStream)object).close();
                throw throwable2;
            }
            catch (Throwable throwable3) {
                try {
                    throwable2.addSuppressed(throwable3);
                    throw throwable2;
                }
                catch (Throwable throwable4222222) {}
            }
        }
        try {
            context.close();
            throw throwable4222222;
        }
        catch (Throwable throwable5) {
            throwable4222222.addSuppressed(throwable5);
            throw throwable4222222;
        }
    }

    public static Map f(Context context, g.b[] bArray, CancellationSignal cancellationSignal) {
        HashMap<g.b, ByteBuffer> hashMap = new HashMap<g.b, ByteBuffer>();
        for (g.b b3 : bArray) {
            if (b3.b() != 0 || hashMap.containsKey(b3 = b3.d())) continue;
            hashMap.put(b3, k.e(context, cancellationSignal, (Uri)b3));
        }
        return Collections.unmodifiableMap(hashMap);
    }
}

