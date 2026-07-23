/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Insets
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 */
package androidx.appcompat.widget;

import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.appcompat.widget.v;
import androidx.appcompat.widget.w;
import androidx.appcompat.widget.x;
import androidx.appcompat.widget.y;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class z {
    public static final int[] a = new int[]{0x10100A0};
    public static final int[] b = new int[0];
    public static final Rect c = new Rect();

    public static boolean a(Drawable drawable) {
        return true;
    }

    public static void b(Drawable drawable) {
        String string = drawable.getClass().getName();
        int n3 = Build.VERSION.SDK_INT;
        if (n3 >= 29 && n3 < 31 && "android.graphics.drawable.ColorStateListDrawable".equals(string)) {
            z.c(drawable);
        }
    }

    public static void c(Drawable drawable) {
        int[] nArray = drawable.getState();
        if (nArray != null && nArray.length != 0) {
            drawable.setState(b);
        } else {
            drawable.setState(a);
        }
        drawable.setState(nArray);
    }

    public static Rect d(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 29) {
            drawable = androidx.appcompat.widget.z$b.a(drawable);
            return new Rect(v.a((Insets)drawable), w.a((Insets)drawable), x.a((Insets)drawable), y.a((Insets)drawable));
        }
        return androidx.appcompat.widget.z$a.a(h0.a.q(drawable));
    }

    public static PorterDuff.Mode e(int n3, PorterDuff.Mode mode) {
        if (n3 != 3) {
            if (n3 != 5) {
                if (n3 != 9) {
                    switch (n3) {
                        default: {
                            return mode;
                        }
                        case 16: {
                            return PorterDuff.Mode.ADD;
                        }
                        case 15: {
                            return PorterDuff.Mode.SCREEN;
                        }
                        case 14: 
                    }
                    return PorterDuff.Mode.MULTIPLY;
                }
                return PorterDuff.Mode.SRC_ATOP;
            }
            return PorterDuff.Mode.SRC_IN;
        }
        return PorterDuff.Mode.SRC_OVER;
    }

    public static abstract class a {
        public static final boolean a;
        public static final Method b;
        public static final Field c;
        public static final Field d;
        public static final Field e;
        public static final Field f;

        /*
         * Unable to fully structure code
         * Could not resolve type clashes
         */
        static {
            block23: {
                var3 = Class.forName("android.graphics.Insets");
                var1_6 /* !! */  = Drawable.class.getMethod("getOpticalInsets", null);
                var2_10 = var3.getField("left");
                var4_14 = var3.getField("top");
                var5_15 = var3.getField("right");
                var6_18 = var3.getField("bottom");
                var0_19 = true;
                var3 = var1_6 /* !! */ ;
                var1_6 /* !! */  = var5_15;
                ** GOTO lbl57
                catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException var3_1) {
                    var5_16 = null;
                    var3 = var1_6 /* !! */ ;
                    var1_6 /* !! */  = var5_16;
                    break block23;
                }
                catch (NoSuchFieldException var3_2) lbl-1000:
                // 6 sources

                {
                    while (true) {
                        var4_14 = null;
                        var5_17 = null;
                        var3 = var1_6 /* !! */ ;
                        var1_6 /* !! */  = var5_17;
                        break block23;
                        break;
                    }
                }
                catch (ClassNotFoundException var3_3) {
                    ** GOTO lbl-1000
                }
                catch (NoSuchMethodException var3_4) {
                    ** GOTO lbl-1000
                }
                catch (NoSuchFieldException var2_11) lbl-1000:
                // 2 sources

                {
                    while (true) {
                        var2_10 = null;
                        ** GOTO lbl-1000
                        break;
                    }
                }
                catch (ClassNotFoundException var2_12) lbl-1000:
                // 2 sources

                {
                    while (true) {
                        var2_10 = null;
                        ** GOTO lbl-1000
                        break;
                    }
                }
                catch (NoSuchMethodException var2_13) lbl-1000:
                // 2 sources

                {
                    while (true) {
                        var2_10 = null;
                        ** continue;
                        break;
                    }
                }
                catch (NoSuchFieldException var1_7) {
                    var1_6 /* !! */  = null;
                    ** continue;
                }
                catch (ClassNotFoundException var1_8) {
                    var1_6 /* !! */  = null;
                    ** continue;
                }
                catch (NoSuchMethodException var1_9) {
                    var1_6 /* !! */  = null;
                    ** continue;
                }
            }
lbl54:
            // 2 sources

            while (true) {
                var6_18 = null;
                var0_19 = false;
lbl57:
                // 2 sources

                if (var0_19) {
                    androidx.appcompat.widget.z$a.b = var3;
                    androidx.appcompat.widget.z$a.c = var2_10;
                    androidx.appcompat.widget.z$a.d = var4_14;
                    androidx.appcompat.widget.z$a.e = var1_6 /* !! */ ;
                    androidx.appcompat.widget.z$a.f = var6_18;
                    androidx.appcompat.widget.z$a.a = true;
                } else {
                    androidx.appcompat.widget.z$a.b = null;
                    androidx.appcompat.widget.z$a.c = null;
                    androidx.appcompat.widget.z$a.d = null;
                    androidx.appcompat.widget.z$a.e = null;
                    androidx.appcompat.widget.z$a.f = null;
                    androidx.appcompat.widget.z$a.a = false;
                }
                return;
            }
            catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException var3_5) {
                var3 = var1_6 /* !! */ ;
                var1_6 /* !! */  = var5_15;
                ** continue;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static Rect a(Drawable object) {
            if (Build.VERSION.SDK_INT >= 29) return c;
            if (!a) return c;
            Object object2 = b.invoke(object, null);
            if (object2 == null) return c;
            try {
                return new Rect(c.getInt(object2), d.getInt(object2), e.getInt(object2), f.getInt(object2));
            }
            catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                return c;
            }
        }
    }

    public static abstract class b {
        public static Insets a(Drawable drawable) {
            return drawable.getOpticalInsets();
        }
    }
}

