/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.XmlResourceParser
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package f0;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import f0.e;
import f0.i;
import f0.j;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class h {
    public static final ThreadLocal a = new ThreadLocal();
    public static final WeakHashMap b = new WeakHashMap(0);
    public static final Object c = new Object();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(d d3, int n3, ColorStateList colorStateList, Resources.Theme theme) {
        Object object = c;
        synchronized (object) {
            Throwable throwable2;
            block4: {
                SparseArray sparseArray;
                Object object2;
                block3: {
                    try {
                        WeakHashMap weakHashMap = b;
                        sparseArray = object2 = (SparseArray)weakHashMap.get(d3);
                        if (object2 != null) break block3;
                        sparseArray = new SparseArray();
                        weakHashMap.put(d3, sparseArray);
                    }
                    catch (Throwable throwable2) {
                        break block4;
                    }
                }
                object2 = new c(colorStateList, d3.a.getConfiguration(), theme);
                sparseArray.append(n3, object2);
                return;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ColorStateList b(d d3, int n3) {
        Object object = c;
        synchronized (object) {
            Throwable throwable2;
            block6: {
                SparseArray sparseArray;
                block4: {
                    c c3;
                    block5: {
                        try {
                            sparseArray = (SparseArray)b.get(d3);
                            if (sparseArray == null) return null;
                            if (sparseArray.size() <= 0) return null;
                            c3 = (c)sparseArray.get(n3);
                            if (c3 == null) return null;
                            if (!c3.b.equals(d3.a.getConfiguration())) break block4;
                            d3 = d3.b;
                            if (d3 != null) break block5;
                            if (c3.c == 0) return c3.a;
                        }
                        catch (Throwable throwable2) {
                            break block6;
                        }
                    }
                    if (d3 != null && c3.c == d3.hashCode()) {
                        return c3.a;
                    }
                }
                sparseArray.remove(n3);
                return null;
            }
            throw throwable2;
        }
    }

    public static Typeface c(Context context, int n3) {
        if (context.isRestricted()) {
            return null;
        }
        return h.m(context, n3, new TypedValue(), 0, null, null, false, true);
    }

    public static ColorStateList d(Resources resources, int n3, Resources.Theme theme) {
        d d3 = new d(resources, theme);
        ColorStateList colorStateList = h.b(d3, n3);
        if (colorStateList != null) {
            return colorStateList;
        }
        colorStateList = h.k(resources, n3, theme);
        if (colorStateList != null) {
            h.a(d3, n3, colorStateList, theme);
            return colorStateList;
        }
        return f0.h$b.b(resources, n3, theme);
    }

    public static Drawable e(Resources resources, int n3, Resources.Theme theme) {
        return f0.h$a.a(resources, n3, theme);
    }

    public static Drawable f(Resources resources, int n3, int n4, Resources.Theme theme) {
        return f0.h$a.b(resources, n3, n4, theme);
    }

    public static Typeface g(Context context, int n3) {
        if (context.isRestricted()) {
            return null;
        }
        return h.m(context, n3, new TypedValue(), 0, null, null, false, false);
    }

    public static Typeface h(Context context, int n3, TypedValue typedValue, int n4, e e3) {
        if (context.isRestricted()) {
            return null;
        }
        return h.m(context, n3, typedValue, n4, e3, null, true, false);
    }

    public static void i(Context context, int n3, e e3, Handler handler) {
        n0.h.g(e3);
        if (context.isRestricted()) {
            e3.c(-4, handler);
            return;
        }
        h.m(context, n3, new TypedValue(), 0, e3, handler, false, false);
    }

    public static TypedValue j() {
        TypedValue typedValue;
        ThreadLocal threadLocal = a;
        TypedValue typedValue2 = typedValue = (TypedValue)threadLocal.get();
        if (typedValue == null) {
            typedValue2 = new TypedValue();
            threadLocal.set(typedValue2);
        }
        return typedValue2;
    }

    public static ColorStateList k(Resources resources, int n3, Resources.Theme theme) {
        if (h.l(resources, n3)) {
            return null;
        }
        XmlResourceParser xmlResourceParser = resources.getXml(n3);
        try {
            resources = f0.c.a(resources, (XmlPullParser)xmlResourceParser, theme);
            return resources;
        }
        catch (Exception exception) {
            Log.w((String)"ResourcesCompat", (String)"Failed to inflate ColorStateList, leaving it to the framework", (Throwable)exception);
            return null;
        }
    }

    public static boolean l(Resources resources, int n3) {
        TypedValue typedValue = h.j();
        resources.getValue(n3, typedValue, true);
        n3 = typedValue.type;
        return n3 >= 28 && n3 <= 31;
    }

    public static Typeface m(Context object, int n3, TypedValue typedValue, int n4, e e3, Handler handler, boolean bl, boolean bl2) {
        Resources resources = object.getResources();
        resources.getValue(n3, typedValue, true);
        object = h.n((Context)object, resources, typedValue, n3, n4, e3, handler, bl, bl2);
        if (object == null && e3 == null && !bl2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Font resource ID #0x");
            ((StringBuilder)object).append(Integer.toHexString(n3));
            ((StringBuilder)object).append(" could not be retrieved.");
            throw new Resources.NotFoundException(((StringBuilder)object).toString());
        }
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Typeface n(Context object, Resources object2, TypedValue typedValue, int n3, int n4, e e3, Handler handler, boolean bl, boolean bl2) {
        block16: {
            void var0_4;
            CharSequence charSequence;
            block15: {
                void var0_2;
                block14: {
                    block13: {
                        charSequence = typedValue.string;
                        if (charSequence == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Resource \"");
                            ((StringBuilder)object).append(object2.getResourceName(n3));
                            ((StringBuilder)object).append("\" (");
                            ((StringBuilder)object).append(Integer.toHexString(n3));
                            ((StringBuilder)object).append(") is not a Font: ");
                            ((StringBuilder)object).append(typedValue);
                            throw new Resources.NotFoundException(((StringBuilder)object).toString());
                        }
                        if (!((String)(charSequence = charSequence.toString())).startsWith("res/")) {
                            if (e3 == null) return null;
                            e3.c(-3, handler);
                            return null;
                        }
                        Object object3 = g0.e.f((Resources)object2, n3, (String)charSequence, typedValue.assetCookie, n4);
                        if (object3 != null) {
                            if (e3 == null) return object3;
                            e3.d((Typeface)object3, handler);
                            return object3;
                        }
                        if (bl2) {
                            return null;
                        }
                        if (!((String)charSequence).toLowerCase().endsWith(".xml")) break block13;
                        object3 = f0.e.b((XmlPullParser)object2.getXml(n3), (Resources)object2);
                        if (object3 == null) {
                            Log.e((String)"ResourcesCompat", (String)"Failed to find font-family tag");
                            if (e3 == null) return null;
                            e3.c(-3, handler);
                            return null;
                        }
                        int n5 = typedValue.assetCookie;
                        try {
                            return g0.e.c((Context)object, (e.b)object3, (Resources)object2, n3, (String)charSequence, n5, n4, e3, handler, bl);
                        }
                        catch (IOException iOException) {
                            break block14;
                        }
                        catch (XmlPullParserException xmlPullParserException) {
                            break block15;
                        }
                    }
                    try {
                        object = g0.e.d((Context)object, (Resources)object2, n3, (String)charSequence, typedValue.assetCookie, n4);
                        if (e3 == null) return object;
                        if (object != null) {
                            e3.d((Typeface)object, handler);
                            return object;
                        }
                    }
                    catch (IOException iOException) {
                        break block14;
                    }
                    catch (XmlPullParserException xmlPullParserException) {
                        break block15;
                    }
                    e3.c(-3, handler);
                    return object;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Failed to read xml resource ");
                ((StringBuilder)object2).append((String)charSequence);
                Log.e((String)"ResourcesCompat", (String)((StringBuilder)object2).toString(), (Throwable)var0_2);
                break block16;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failed to parse xml resource ");
            ((StringBuilder)object2).append((String)charSequence);
            Log.e((String)"ResourcesCompat", (String)((StringBuilder)object2).toString(), (Throwable)var0_4);
        }
        if (e3 == null) return null;
        e3.c(-3, handler);
        return null;
    }

    public static abstract class a {
        public static Drawable a(Resources resources, int n3, Resources.Theme theme) {
            return resources.getDrawable(n3, theme);
        }

        public static Drawable b(Resources resources, int n3, int n4, Resources.Theme theme) {
            return resources.getDrawableForDensity(n3, n4, theme);
        }
    }

    public static abstract class b {
        public static int a(Resources resources, int n3, Resources.Theme theme) {
            return resources.getColor(n3, theme);
        }

        public static ColorStateList b(Resources resources, int n3, Resources.Theme theme) {
            return resources.getColorStateList(n3, theme);
        }
    }

    public static class c {
        public final ColorStateList a;
        public final Configuration b;
        public final int c;

        public c(ColorStateList colorStateList, Configuration configuration, Resources.Theme theme) {
            this.a = colorStateList;
            this.b = configuration;
            int n3 = theme == null ? 0 : theme.hashCode();
            this.c = n3;
        }
    }

    public static final class d {
        public final Resources a;
        public final Resources.Theme b;

        public d(Resources resources, Resources.Theme theme) {
            this.a = resources;
            this.b = theme;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && d.class == object.getClass()) {
                object = (d)object;
                if (this.a.equals(((d)object).a) && n0.c.a(this.b, ((d)object).b)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return n0.c.b(this.a, this.b);
        }
    }

    public static abstract class e {
        public static /* synthetic */ void a(e e3, Typeface typeface) {
            e3.g(typeface);
        }

        public static /* synthetic */ void b(e e3, int n3) {
            e3.f(n3);
        }

        public static Handler e(Handler handler) {
            Handler handler2 = handler;
            if (handler == null) {
                handler2 = new Handler(Looper.getMainLooper());
            }
            return handler2;
        }

        public final void c(int n3, Handler handler) {
            e.e(handler).post((Runnable)new j(this, n3));
        }

        public final void d(Typeface typeface, Handler handler) {
            e.e(handler).post((Runnable)new i(this, typeface));
        }

        public abstract void f(int var1);

        public abstract void g(Typeface var1);
    }

    public static final abstract class f {
        public static void a(Resources.Theme theme) {
            if (Build.VERSION.SDK_INT >= 29) {
                b.a(theme);
                return;
            }
            a.a(theme);
        }

        public static abstract class a {
            public static final Object a = new Object();
            public static Method b;
            public static boolean c;

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static void a(Resources.Theme theme) {
                Object object = a;
                synchronized (object) {
                    try {
                        Method method;
                        boolean bl = c;
                        if (!bl) {
                            try {
                                b = method = Resources.Theme.class.getDeclaredMethod("rebase", null);
                                ((AccessibleObject)method).setAccessible(true);
                            }
                            catch (NoSuchMethodException noSuchMethodException) {}
                            c = true;
                        }
                        if ((method = b) != null) {
                            try {
                                method.invoke((Object)theme, null);
                            }
                            catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                                b = null;
                            }
                        }
                        return;
                    }
                    catch (Throwable throwable) {}
                    throw throwable;
                }
            }
        }

        public static abstract class b {
            public static void a(Resources.Theme theme) {
                theme.rebase();
            }
        }
    }
}

