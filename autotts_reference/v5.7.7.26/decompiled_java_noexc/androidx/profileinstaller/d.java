/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$PackageInfoFlags
 *  android.os.Build$VERSION
 */
package androidx.profileinstaller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public abstract class d {
    public static final q.c a = q.c.o();
    public static final Object b = new Object();
    public static c c = null;

    public static long a(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (Build.VERSION.SDK_INT >= 33) {
            return androidx.profileinstaller.d$a.a((PackageManager)packageManager, (Context)context).lastUpdateTime;
        }
        return packageManager.getPackageInfo((String)context.getPackageName(), (int)0).lastUpdateTime;
    }

    public static c b(int n3, boolean bl, boolean bl2, boolean bl3) {
        c c3;
        c = c3 = new c(n3, bl, bl2, bl3);
        a.m(c3);
        return c;
    }

    public static c c(Context object, boolean bl) {
        Object object2;
        if (!bl && (object2 = c) != null) {
            return object2;
        }
        object2 = b;
        synchronized (object2) {
            boolean bl2;
            block20: {
                boolean bl3;
                boolean bl4;
                int n3;
                block22: {
                    int n4;
                    Object object3;
                    Object object4;
                    block21: {
                        if (!bl && (object4 = c) != null) {
                            return object4;
                        }
                        n3 = 0;
                        object3 = object.getAssets().openFd("dexopt/baseline.prof");
                        long l3 = object3.getLength();
                        bl2 = l3 > 0L;
                        object3.close();
                        n4 = Build.VERSION.SDK_INT;
                        if (n4 < 28 || n4 == 30) break block20;
                        object3 = new File("/data/misc/profiles/ref/", object.getPackageName());
                        object4 = new File((File)object3, "primary.prof");
                        long l4 = ((File)object4).length();
                        bl4 = ((File)object4).exists() && l4 > 0L;
                        object4 = new File("/data/misc/profiles/cur/0/", object.getPackageName());
                        object3 = new File((File)object4, "primary.prof");
                        l3 = ((File)object3).length();
                        bl3 = ((File)object3).exists();
                        bl3 = bl3 && l3 > 0L;
                        long l5 = d.a((Context)object);
                        object4 = new File(object.getFilesDir(), "profileInstalled");
                        boolean bl5 = ((File)object4).exists();
                        if ((object = bl5 ? androidx.profileinstaller.d$b.a((File)object4) : null) != null && ((b)object).c == l5 && (n4 = ((b)object).b) != 2) {
                            n3 = n4;
                        } else if (!bl2) {
                            n3 = 327680;
                        } else if (bl4) {
                            n3 = 1;
                        } else if (bl3) {
                            n3 = 2;
                        }
                        n4 = n3;
                        if (bl) {
                            n4 = n3;
                            if (bl3) {
                                n4 = n3;
                                if (n3 != 1) {
                                    n4 = 2;
                                }
                            }
                        }
                        n3 = n4;
                        if (object != null) {
                            n3 = n4;
                            if (((b)object).b == 2) {
                                n3 = n4;
                                if (n4 == 1) {
                                    n3 = n4;
                                    if (l4 < ((b)object).d) {
                                        n3 = 3;
                                    }
                                }
                            }
                        }
                        n4 = n3;
                        object3 = new b(1, n4, l5, l3);
                        if (object == null) break block21;
                        bl = ((b)object).equals(object3);
                        n3 = n4;
                        if (bl) break block22;
                    }
                    ((b)object3).b((File)object4);
                    n3 = n4;
                }
                object = d.b(n3, bl4, bl3, bl2);
                return object;
            }
            object = d.b(262144, false, false, bl2);
            return object;
        }
    }

    public static abstract class a {
        public static PackageInfo a(PackageManager packageManager, Context context) {
            return packageManager.getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of((long)0L));
        }
    }

    public static class b {
        public final int a;
        public final int b;
        public final long c;
        public final long d;

        public b(int n3, int n4, long l3, long l4) {
            this.a = n3;
            this.b = n4;
            this.c = l3;
            this.d = l4;
        }

        public static b a(File object) {
            object = new DataInputStream(new FileInputStream((File)object));
            b b3 = new b(((DataInputStream)object).readInt(), ((DataInputStream)object).readInt(), ((DataInputStream)object).readLong(), ((DataInputStream)object).readLong());
            ((InputStream)object).close();
            return b3;
        }

        public void b(File file) {
            file.delete();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeLong(this.c);
            dataOutputStream.writeLong(this.d);
            ((OutputStream)dataOutputStream).close();
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && object instanceof b) {
                object = (b)object;
                if (this.b == ((b)object).b && this.c == ((b)object).c && this.a == ((b)object).a && this.d == ((b)object).d) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.b, this.c, this.a, this.d);
        }
    }

    public static class c {
        public final int a;
        public final boolean b;
        public final boolean c;
        public final boolean d;

        public c(int n3, boolean bl, boolean bl2, boolean bl3) {
            this.a = n3;
            this.c = bl2;
            this.b = bl;
            this.d = bl3;
        }
    }
}

