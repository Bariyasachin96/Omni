/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.AssetManager
 *  android.os.Build$VERSION
 */
package androidx.profileinstaller;

import android.content.res.AssetManager;
import android.os.Build;
import androidx.profileinstaller.c;
import h1.a;
import h1.c;
import h1.j;
import h1.k;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;

public class b {
    public final AssetManager a;
    public final Executor b;
    public final c.c c;
    public final byte[] d;
    public final File e;
    public final String f;
    public final String g;
    public final String h;
    public boolean i = false;
    public h1.b[] j;
    public byte[] k;

    public b(AssetManager assetManager, Executor executor, c.c c3, String string, String string2, String string3, File file) {
        this.a = assetManager;
        this.b = executor;
        this.c = c3;
        this.f = string;
        this.g = string2;
        this.h = string3;
        this.e = file;
        this.d = androidx.profileinstaller.b.d();
    }

    public static /* synthetic */ void a(b b3, int n3, Object object) {
        b3.c.b(n3, object);
    }

    public static byte[] d() {
        int n3 = Build.VERSION.SDK_INT;
        if (n3 >= 31) {
            return h1.k.a;
        }
        switch (n3) {
            default: {
                return null;
            }
            case 28: 
            case 29: 
            case 30: {
                return h1.k.b;
            }
            case 27: {
                return h1.k.c;
            }
            case 26: 
        }
        return h1.k.d;
    }

    public static boolean j() {
        return Build.VERSION.SDK_INT >= 31;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final b b(h1.b[] bArray, byte[] byArray) {
        FileNotFoundException fileNotFoundException2;
        block13: {
            IOException iOException2;
            block12: {
                IllegalStateException illegalStateException2;
                block11: {
                    InputStream inputStream;
                    block10: {
                        inputStream = this.g(this.a, this.h);
                        if (inputStream == null) break block10;
                        try {
                            this.j = h1.j.r(inputStream, h1.j.p(inputStream, h1.j.b), byArray, bArray);
                        }
                        catch (Throwable throwable) {
                            try {
                                inputStream.close();
                                throw throwable;
                            }
                            catch (Throwable throwable2) {
                                try {
                                    throwable.addSuppressed(throwable2);
                                    throw throwable;
                                }
                                catch (IllegalStateException illegalStateException2) {
                                    break block11;
                                }
                                catch (IOException iOException2) {
                                    break block12;
                                }
                                catch (FileNotFoundException fileNotFoundException2) {
                                    break block13;
                                }
                            }
                        }
                        inputStream.close();
                        return this;
                    }
                    if (inputStream == null) return null;
                    inputStream.close();
                    return null;
                }
                this.j = null;
                this.c.b(8, illegalStateException2);
                return null;
            }
            this.c.b(7, iOException2);
            return null;
        }
        this.c.b(9, fileNotFoundException2);
        return null;
    }

    public final void c() {
        if (this.i) {
            return;
        }
        throw new IllegalStateException("This device doesn't support aot. Did you call deviceSupportsAotProfile()?");
    }

    /*
     * Loose catch block
     */
    public boolean e() {
        block6: {
            if (this.d == null) {
                this.k(3, Build.VERSION.SDK_INT);
                return false;
            }
            if (this.e.exists()) {
                if (!this.e.canWrite()) {
                    this.k(4, null);
                    return false;
                }
            } else {
                if (this.e.createNewFile()) break block6;
                this.k(4, null);
                return false;
            }
        }
        this.i = true;
        return true;
        catch (IOException iOException) {
            this.k(4, null);
            return false;
        }
    }

    public final InputStream f(AssetManager object) {
        block4: {
            FileNotFoundException fileNotFoundException2;
            block3: {
                try {
                    object = this.g((AssetManager)object, this.g);
                    return object;
                }
                catch (IOException iOException) {
                }
                catch (FileNotFoundException fileNotFoundException2) {
                    break block3;
                }
                this.c.b(7, iOException);
                break block4;
            }
            this.c.b(6, fileNotFoundException2);
        }
        return null;
    }

    public final InputStream g(AssetManager object, String string) {
        try {
            object = object.openFd(string).createInputStream();
            return object;
        }
        catch (FileNotFoundException fileNotFoundException) {
            String string2 = fileNotFoundException.getMessage();
            if (string2 != null && string2.contains("compressed")) {
                this.c.a(5, null);
            }
            return null;
        }
    }

    public b h() {
        this.c();
        if (this.d != null) {
            Object object = this.f(this.a);
            if (object != null) {
                this.j = this.i((InputStream)object);
            }
            if ((object = this.j) != null && androidx.profileinstaller.b.j() && (object = this.b((h1.b[])object, this.d)) != null) {
                return object;
            }
        }
        return this;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final h1.b[] i(InputStream var1_1) {
        block12: {
            block13: {
                var2_5 = h1.j.x((InputStream)var1_1, h1.j.p((InputStream)var1_1, h1.j.a), this.f);
                try {
                    var1_1.close();
                    return var2_5;
                }
                catch (IOException var1_2) {
                    this.c.b(7, var1_2);
                    return var2_5;
                }
                catch (Throwable var2_6) {
                    break block12;
                }
                catch (IllegalStateException var2_7) {
                }
                catch (IOException var2_8) {
                    ** GOTO lbl-1000
                }
                {
                    this.c.b(8, var2_7);
                    break block13;
                }
lbl-1000:
                // 1 sources

                {
                    this.c.b(7, var2_8);
                }
            }
            try {
                var1_1.close();
                return null;
            }
            catch (IOException var1_3) {
                this.c.b(7, var1_3);
            }
            return null;
        }
        try {
            var1_1.close();
            throw var2_6;
        }
        catch (IOException var1_4) {
            this.c.b(7, var1_4);
        }
        throw var2_6;
    }

    public final void k(int n3, Object object) {
        this.b.execute(new a(this, n3, object));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public b l() {
        block13: {
            IOException iOException2;
            block15: {
                IllegalStateException illegalStateException2;
                Throwable throwable2;
                ByteArrayOutputStream byteArrayOutputStream;
                block16: {
                    block12: {
                        h1.b[] bArray = this.j;
                        byte[] byArray = this.d;
                        if (bArray == null) return this;
                        if (byArray == null) {
                            return this;
                        }
                        this.c();
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        try {
                            h1.j.F(byteArrayOutputStream, byArray);
                            if (h1.j.C(byteArrayOutputStream, byArray, bArray)) break block12;
                            this.c.b(5, null);
                            this.j = null;
                        }
                        catch (Throwable throwable2) {}
                        byteArrayOutputStream.close();
                        return this;
                    }
                    this.k = byteArrayOutputStream.toByteArray();
                    break block16;
                    byteArrayOutputStream.close();
                    break block13;
                }
                try {
                    byteArrayOutputStream.close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throwable2.addSuppressed(throwable3);
                        throw throwable2;
                    }
                    catch (IllegalStateException illegalStateException2) {
                    }
                    catch (IOException iOException2) {
                        break block15;
                    }
                }
                this.c.b(8, illegalStateException2);
                break block13;
            }
            this.c.b(7, iOException2);
        }
        this.j = null;
        return this;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean m() {
        block32: {
            var2_1 /* !! */  = this.k;
            if (var2_1 /* !! */  == null) {
                return false;
            }
            this.c();
            var1_4 = new ByteArrayInputStream(var2_1 /* !! */ );
            var2_1 /* !! */  = (byte[])new FileOutputStream;
            var2_1 /* !! */ (this.e);
            {
                catch (Throwable var2_2) {}
            }
            var3_9 = var2_1 /* !! */ .getChannel();
            var5_12 = var3_9.tryLock();
            h1.c.l(var1_4, (OutputStream)var2_1 /* !! */ , var5_12);
            this.k(1, null);
            if (var5_12 == null) break block32;
            var5_12.close();
        }
        var3_9.close();
        var2_1 /* !! */ .close();
        var1_4.close();
        this.k = null;
        this.j = null;
        return true;
        catch (Throwable var4_15) {
            if (var5_12 == null) throw var4_15;
            try {
                var5_12.close();
                throw var4_15;
            }
            catch (Throwable var5_13) {
                try {
                    var4_15.addSuppressed(var5_13);
                    throw var4_15;
                }
                catch (Throwable var4_14) {}
            }
        }
        if (var3_9 == null) throw var4_14;
        try {
            var3_9.close();
            throw var4_14;
        }
        catch (Throwable var3_11) {
            try {
                var4_14.addSuppressed(var3_11);
                throw var4_14;
            }
            catch (Throwable var3_10) {}
        }
        try {
            var2_1 /* !! */ .close();
            throw var3_10;
        }
        catch (Throwable var2_3) {
            var3_10.addSuppressed(var2_3);
            throw var3_10;
        }
        try {
            var1_4.close();
            throw var2_2;
        }
        catch (Throwable var1_8) {
            try {
                var2_2.addSuppressed(var1_8);
                throw var2_2;
            }
            catch (Throwable var1_5) {}
            catch (IOException var1_6) {}
            catch (FileNotFoundException var1_7) {}
        }
        ** finally { 
lbl69:
        // 1 sources

        this.k = null;
        this.j = null;
        throw var1_5;
    }
}

