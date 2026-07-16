/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.BitmapFactory
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.AdaptiveIconDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Icon
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 */
package androidx.core.graphics.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

public class IconCompat
extends CustomVersionedParcelable {
    public static final PorterDuff.Mode k = PorterDuff.Mode.SRC_IN;
    public int a = -1;
    public Object b;
    public byte[] c = null;
    public Parcelable d = null;
    public int e = 0;
    public int f = 0;
    public ColorStateList g = null;
    public PorterDuff.Mode h = k;
    public String i = null;
    public String j;

    public static String h(int n3) {
        switch (n3) {
            default: {
                return "UNKNOWN";
            }
            case 6: {
                return "URI_MASKABLE";
            }
            case 5: {
                return "BITMAP_MASKABLE";
            }
            case 4: {
                return "URI";
            }
            case 3: {
                return "DATA";
            }
            case 2: {
                return "RESOURCE";
            }
            case 1: 
        }
        return "BITMAP";
    }

    public int a() {
        int n3 = this.a;
        if (n3 == -1) {
            return androidx.core.graphics.drawable.IconCompat$a.a(this.b);
        }
        if (n3 == 2) {
            return this.e;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getResId() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public String b() {
        int n3 = this.a;
        if (n3 == -1) {
            return androidx.core.graphics.drawable.IconCompat$a.b(this.b);
        }
        if (n3 == 2) {
            String string = this.j;
            if (string != null && !TextUtils.isEmpty((CharSequence)string)) {
                return this.j;
            }
            return ((String)this.b).split(":", -1)[0];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("called getResPackage() on ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Uri c() {
        int n3 = this.a;
        if (n3 == -1) {
            return androidx.core.graphics.drawable.IconCompat$a.c(this.b);
        }
        if (n3 != 4 && n3 != 6) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("called getUri() on ");
            stringBuilder.append(this);
            throw new IllegalStateException(stringBuilder.toString());
        }
        return Uri.parse((String)((String)this.b));
    }

    public InputStream d(Context object) {
        Uri uri = this.c();
        String string = uri.getScheme();
        if (!"content".equals(string) && !"file".equals(string)) {
            try {
                object = new File((String)this.b);
                object = new FileInputStream((File)object);
                return object;
            }
            catch (FileNotFoundException fileNotFoundException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to load image from path: ");
                ((StringBuilder)object).append(uri);
                Log.w((String)"IconCompat", (String)((StringBuilder)object).toString(), (Throwable)fileNotFoundException);
            }
        } else {
            try {
                object = object.getContentResolver().openInputStream(uri);
                return object;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to load image from URI: ");
                ((StringBuilder)object).append(uri);
                Log.w((String)"IconCompat", (String)((StringBuilder)object).toString(), (Throwable)exception);
            }
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void e() {
        this.h = PorterDuff.Mode.valueOf((String)this.i);
        switch (this.a) {
            default: {
                return;
            }
            case 3: {
                this.b = this.c;
                return;
            }
            case 2: 
            case 4: 
            case 6: {
                String string = new String(this.c, Charset.forName("UTF-16"));
                this.b = string;
                if (this.a != 2 || this.j != null) return;
                this.j = string.split(":", -1)[0];
                return;
            }
            case 1: 
            case 5: {
                Parcelable parcelable = this.d;
                if (parcelable != null) {
                    this.b = parcelable;
                    return;
                }
                byte[] byArray = this.c;
                this.b = byArray;
                this.a = 3;
                this.e = 0;
                this.f = byArray.length;
                return;
            }
            case -1: 
        }
        Parcelable parcelable = this.d;
        if (parcelable == null) throw new IllegalArgumentException("Invalid icon");
        this.b = parcelable;
    }

    public void f(boolean bl) {
        this.i = this.h.name();
        switch (this.a) {
            default: {
                return;
            }
            case 4: 
            case 6: {
                this.c = this.b.toString().getBytes(Charset.forName("UTF-16"));
                return;
            }
            case 3: {
                this.c = (byte[])this.b;
                return;
            }
            case 2: {
                this.c = ((String)this.b).getBytes(Charset.forName("UTF-16"));
                return;
            }
            case 1: 
            case 5: {
                if (bl) {
                    Bitmap bitmap = (Bitmap)this.b;
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, (OutputStream)byteArrayOutputStream);
                    this.c = byteArrayOutputStream.toByteArray();
                    return;
                }
                this.d = (Parcelable)this.b;
                return;
            }
            case -1: 
        }
        if (!bl) {
            this.d = (Parcelable)this.b;
            return;
        }
        throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
    }

    public Icon g(Context context) {
        return androidx.core.graphics.drawable.IconCompat$a.e(this, context);
    }

    public String toString() {
        if (this.a == -1) {
            return String.valueOf(this.b);
        }
        StringBuilder stringBuilder = new StringBuilder("Icon(typ=");
        stringBuilder.append(IconCompat.h(this.a));
        switch (this.a) {
            default: {
                break;
            }
            case 4: 
            case 6: {
                stringBuilder.append(" uri=");
                stringBuilder.append(this.b);
                break;
            }
            case 3: {
                stringBuilder.append(" len=");
                stringBuilder.append(this.e);
                if (this.f == 0) break;
                stringBuilder.append(" off=");
                stringBuilder.append(this.f);
                break;
            }
            case 2: {
                stringBuilder.append(" pkg=");
                stringBuilder.append(this.j);
                stringBuilder.append(" id=");
                stringBuilder.append(String.format("0x%08x", this.a()));
                break;
            }
            case 1: 
            case 5: {
                stringBuilder.append(" size=");
                stringBuilder.append(((Bitmap)this.b).getWidth());
                stringBuilder.append("x");
                stringBuilder.append(((Bitmap)this.b).getHeight());
            }
        }
        if (this.g != null) {
            stringBuilder.append(" tint=");
            stringBuilder.append(this.g);
        }
        if (this.h != k) {
            stringBuilder.append(" mode=");
            stringBuilder.append(this.h);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static abstract class a {
        public static int a(Object object) {
            IllegalAccessException illegalAccessException2;
            block6: {
                InvocationTargetException invocationTargetException2;
                block5: {
                    if (Build.VERSION.SDK_INT >= 28) {
                        return androidx.core.graphics.drawable.IconCompat$c.a(object);
                    }
                    try {
                        int n3 = (Integer)object.getClass().getMethod("getResId", null).invoke(object, null);
                        return n3;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                    }
                    catch (InvocationTargetException invocationTargetException2) {
                        break block5;
                    }
                    catch (IllegalAccessException illegalAccessException2) {
                        break block6;
                    }
                    Log.e((String)"IconCompat", (String)"Unable to get icon resource", (Throwable)noSuchMethodException);
                    return 0;
                }
                Log.e((String)"IconCompat", (String)"Unable to get icon resource", (Throwable)invocationTargetException2);
                return 0;
            }
            Log.e((String)"IconCompat", (String)"Unable to get icon resource", (Throwable)illegalAccessException2);
            return 0;
        }

        public static String b(Object object) {
            IllegalAccessException illegalAccessException2;
            block6: {
                InvocationTargetException invocationTargetException2;
                block5: {
                    if (Build.VERSION.SDK_INT >= 28) {
                        return androidx.core.graphics.drawable.IconCompat$c.b(object);
                    }
                    try {
                        object = (String)object.getClass().getMethod("getResPackage", null).invoke(object, null);
                        return object;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                    }
                    catch (InvocationTargetException invocationTargetException2) {
                        break block5;
                    }
                    catch (IllegalAccessException illegalAccessException2) {
                        break block6;
                    }
                    Log.e((String)"IconCompat", (String)"Unable to get icon package", (Throwable)noSuchMethodException);
                    return null;
                }
                Log.e((String)"IconCompat", (String)"Unable to get icon package", (Throwable)invocationTargetException2);
                return null;
            }
            Log.e((String)"IconCompat", (String)"Unable to get icon package", (Throwable)illegalAccessException2);
            return null;
        }

        public static Uri c(Object object) {
            IllegalAccessException illegalAccessException2;
            block6: {
                InvocationTargetException invocationTargetException2;
                block5: {
                    if (Build.VERSION.SDK_INT >= 28) {
                        return androidx.core.graphics.drawable.IconCompat$c.d(object);
                    }
                    try {
                        object = (Uri)object.getClass().getMethod("getUri", null).invoke(object, null);
                        return object;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                    }
                    catch (InvocationTargetException invocationTargetException2) {
                        break block5;
                    }
                    catch (IllegalAccessException illegalAccessException2) {
                        break block6;
                    }
                    Log.e((String)"IconCompat", (String)"Unable to get icon uri", (Throwable)noSuchMethodException);
                    return null;
                }
                Log.e((String)"IconCompat", (String)"Unable to get icon uri", (Throwable)invocationTargetException2);
                return null;
            }
            Log.e((String)"IconCompat", (String)"Unable to get icon uri", (Throwable)illegalAccessException2);
            return null;
        }

        public static Drawable d(Icon icon, Context context) {
            return icon.loadDrawable(context);
        }

        /*
         * Unable to fully structure code
         */
        public static Icon e(IconCompat var0, Context var1_1) {
            switch (var0.a) {
                default: {
                    throw new IllegalArgumentException("Unknown type");
                }
                case 6: {
                    if (Build.VERSION.SDK_INT < 30) ** GOTO lbl8
                    var1_1 = androidx.core.graphics.drawable.IconCompat$d.a(var0.c());
                    ** GOTO lbl38
lbl8:
                    // 1 sources

                    if (var1_1 == null) ** GOTO lbl18
                    if ((var1_1 = var0.d((Context)var1_1)) == null) ** GOTO lbl12
                    var1_1 = androidx.core.graphics.drawable.IconCompat$b.b(BitmapFactory.decodeStream((InputStream)var1_1));
                    ** GOTO lbl38
lbl12:
                    // 1 sources

                    var1_1 = new StringBuilder();
                    var1_1.append("Cannot load adaptive icon from uri: ");
                    var1_1.append(var0.c());
                    throw new IllegalStateException(var1_1.toString());
lbl18:
                    // 1 sources

                    var1_1 = new StringBuilder();
                    var1_1.append("Context is required to resolve the file uri of the icon: ");
                    var1_1.append(var0.c());
                    throw new IllegalArgumentException(var1_1.toString());
                }
                case 5: {
                    var1_1 = androidx.core.graphics.drawable.IconCompat$b.b((Bitmap)var0.b);
                    ** GOTO lbl38
                }
                case 4: {
                    var1_1 = Icon.createWithContentUri((String)((String)var0.b));
                    ** GOTO lbl38
                }
                case 3: {
                    var1_1 = Icon.createWithData((byte[])((byte[])var0.b), (int)var0.e, (int)var0.f);
                    ** GOTO lbl38
                }
                case 2: {
                    var1_1 = Icon.createWithResource((String)var0.b(), (int)var0.e);
                    ** GOTO lbl38
                }
                case 1: {
                    var1_1 = Icon.createWithBitmap((Bitmap)((Bitmap)var0.b));
lbl38:
                    // 7 sources

                    var2_2 = var0.g;
                    if (var2_2 != null) {
                        var1_1.setTintList(var2_2);
                    }
                    if ((var0 = var0.h) != IconCompat.k) {
                        var1_1.setTintMode((PorterDuff.Mode)var0);
                    }
                    return var1_1;
                }
                case -1: 
            }
            return (Icon)var0.b;
        }
    }

    public static abstract class b {
        public static Drawable a(Drawable drawable, Drawable drawable2) {
            return new AdaptiveIconDrawable(drawable, drawable2);
        }

        public static Icon b(Bitmap bitmap) {
            return Icon.createWithAdaptiveBitmap((Bitmap)bitmap);
        }
    }

    public static abstract class c {
        public static int a(Object object) {
            return ((Icon)object).getResId();
        }

        public static String b(Object object) {
            return ((Icon)object).getResPackage();
        }

        public static int c(Object object) {
            return ((Icon)object).getType();
        }

        public static Uri d(Object object) {
            return ((Icon)object).getUri();
        }
    }

    public static abstract class d {
        public static Icon a(Uri uri) {
            return Icon.createWithAdaptiveBitmapContentUri((Uri)uri);
        }
    }
}

