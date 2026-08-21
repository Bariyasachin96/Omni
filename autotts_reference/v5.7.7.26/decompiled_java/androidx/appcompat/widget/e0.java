/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$Theme
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.LayerDrawable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import androidx.appcompat.widget.k0;
import androidx.appcompat.widget.s0;
import androidx.appcompat.widget.z;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import n1.g;
import o.j;
import o.l;
import o.r;
import o.s;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class e0 {
    public static final PorterDuff.Mode h = PorterDuff.Mode.SRC_IN;
    public static e0 i;
    public static final a j;
    public WeakHashMap a;
    public r b;
    public s c;
    public final WeakHashMap d = new WeakHashMap(0);
    public TypedValue e;
    public boolean f;
    public c g;

    static {
        j = new a(6);
    }

    public static long d(TypedValue typedValue) {
        return (long)typedValue.assetCookie << 32 | (long)typedValue.data;
    }

    public static PorterDuffColorFilter f(ColorStateList colorStateList, PorterDuff.Mode mode, int[] nArray) {
        if (colorStateList != null && mode != null) {
            return e0.k(colorStateList.getColorForState(nArray, 0), mode);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static e0 g() {
        synchronized (e0.class) {
            try {
                e0 e02;
                if (i != null) return i;
                i = e02 = new e0();
                e0.o(e02);
                return i;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static PorterDuffColorFilter k(int n3, PorterDuff.Mode mode) {
        synchronized (e0.class) {
            Throwable throwable2;
            PorterDuffColorFilter porterDuffColorFilter;
            block4: {
                try {
                    PorterDuffColorFilter porterDuffColorFilter2;
                    a a4 = j;
                    porterDuffColorFilter = porterDuffColorFilter2 = a4.i(n3, mode);
                    if (porterDuffColorFilter2 != null) break block4;
                    porterDuffColorFilter = new PorterDuffColorFilter(n3, mode);
                    a4.j(n3, mode, porterDuffColorFilter);
                }
                catch (Throwable throwable2) {}
            }
            return porterDuffColorFilter;
            throw throwable2;
        }
    }

    public static void o(e0 e02) {
    }

    public static boolean p(Drawable drawable) {
        return drawable instanceof g || "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName());
        {
        }
    }

    public static void v(Drawable drawable, k0 k02, int[] nArray) {
        Object object = drawable.getState();
        if (drawable.mutate() == drawable) {
            boolean bl;
            if (drawable instanceof LayerDrawable && drawable.isStateful()) {
                drawable.setState(new int[0]);
                drawable.setState(object);
            }
            if (!(bl = k02.d) && !k02.c) {
                drawable.clearColorFilter();
            } else {
                object = bl ? (Object)k02.a : null;
                k02 = k02.c ? k02.b : h;
                drawable.setColorFilter((ColorFilter)e0.f((ColorStateList)object, (PorterDuff.Mode)k02, nArray));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean a(Context object, long l3, Drawable object2) {
        synchronized (this) {
            Throwable throwable2;
            block5: {
                try {
                    Drawable.ConstantState constantState = object2.getConstantState();
                    if (constantState == null) break block5;
                    j j3 = (j)this.d.get(object);
                    object2 = j3;
                    if (j3 == null) {
                        object2 = new j();
                        this.d.put(object, object2);
                    }
                    object = new WeakReference(constantState);
                    ((j)object2).h(l3, object);
                    return true;
                }
                catch (Throwable throwable2) {}
            }
            return false;
            throw throwable2;
        }
    }

    public final void b(Context context, int n3, ColorStateList colorStateList) {
        s s3;
        if (this.a == null) {
            this.a = new WeakHashMap();
        }
        s s4 = s3 = (s)this.a.get(context);
        if (s3 == null) {
            s4 = new s();
            this.a.put(context, s4);
        }
        s4.a(n3, colorStateList);
    }

    public final void c(Context context) {
        block3: {
            block2: {
                if (this.f) break block2;
                this.f = true;
                if ((context = this.i(context, f.a.abc_vector_test)) == null || !e0.p((Drawable)context)) break block3;
            }
            return;
        }
        this.f = false;
        throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
    }

    public final Drawable e(Context context, int n3) {
        if (this.e == null) {
            this.e = new TypedValue();
        }
        TypedValue typedValue = this.e;
        context.getResources().getValue(n3, typedValue, true);
        long l3 = e0.d(typedValue);
        Object object = this.h(context, l3);
        if (object != null) {
            return object;
        }
        object = this.g;
        object = object == null ? null : object.c(this, context, n3);
        if (object != null) {
            object.setChangingConfigurations(typedValue.changingConfigurations);
            this.a(context, l3, (Drawable)object);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Drawable h(Context context, long l3) {
        synchronized (this) {
            Throwable throwable2;
            block6: {
                j j3;
                block5: {
                    try {
                        j3 = (j)this.d.get(context);
                        if (j3 != null) break block5;
                    }
                    catch (Throwable throwable2) {}
                    return null;
                }
                WeakReference weakReference = (WeakReference)j3.d(l3);
                if (weakReference == null) return null;
                if ((weakReference = (Drawable.ConstantState)weakReference.get()) != null) {
                    return weakReference.newDrawable(context.getResources());
                }
                break block6;
                j3.i(l3);
                return null;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Drawable i(Context context, int n3) {
        synchronized (this) {
            return this.j(context, n3, false);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Drawable j(Context context, int n3, boolean bl) {
        synchronized (this) {
            Throwable throwable2;
            block7: {
                Drawable drawable;
                Drawable drawable2;
                block6: {
                    try {
                        this.c(context);
                        drawable = drawable2 = this.q(context, n3);
                        if (drawable2 != null) break block6;
                        drawable = this.e(context, n3);
                    }
                    catch (Throwable throwable2) {
                        break block7;
                    }
                }
                drawable2 = drawable;
                if (drawable == null) {
                    drawable2 = e0.a.d(context, n3);
                }
                drawable = drawable2;
                if (drawable2 != null) {
                    drawable = this.u(context, n3, bl, drawable2);
                }
                if (drawable != null) {
                    z.b(drawable);
                }
                return drawable;
            }
            throw throwable2;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ColorStateList l(Context context, int n3) {
        synchronized (this) {
            try {
                Object object = this.m(context, n3);
                Object object2 = object;
                if (object != null) return object2;
                object = this.g;
                if (object == null) {
                    return null;
                }
                object = object.d(context, n3);
                object2 = object;
                if (object == null) return object2;
                this.b(context, n3, (ColorStateList)object);
                return object;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final ColorStateList m(Context object, int n3) {
        WeakHashMap weakHashMap = this.a;
        if (weakHashMap != null && (object = (s)weakHashMap.get(object)) != null) {
            return (ColorStateList)((s)object).d(n3);
        }
        return null;
    }

    public PorterDuff.Mode n(int n3) {
        c c3 = this.g;
        if (c3 == null) {
            return null;
        }
        return c3.b(n3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Drawable q(Context object, int n3) {
        block15: {
            Object object2;
            block16: {
                Object object3 = this.b;
                if (object3 == null || ((r)object3).isEmpty()) break block15;
                object3 = this.c;
                if (object3 != null) {
                    if ("appcompat_skip_skip".equals(object3 = (String)((s)object3).d(n3)) || object3 != null && this.b.get(object3) == null) {
                        return null;
                    }
                } else {
                    this.c = new s();
                }
                if (this.e == null) {
                    this.e = new TypedValue();
                }
                TypedValue typedValue = this.e;
                object3 = object.getResources();
                object3.getValue(n3, typedValue, true);
                long l3 = e0.d(typedValue);
                Drawable drawable = this.h((Context)object, l3);
                if (drawable != null) {
                    return drawable;
                }
                CharSequence charSequence = typedValue.string;
                object2 = drawable;
                if (charSequence != null) {
                    object2 = drawable;
                    if (charSequence.toString().endsWith(".xml")) {
                        Exception exception2;
                        block14: {
                            block13: {
                                object2 = drawable;
                                try {
                                    int n4;
                                    charSequence = object3.getXml(n3);
                                    object2 = drawable;
                                    AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)charSequence);
                                    do {
                                        object2 = drawable;
                                    } while ((n4 = charSequence.next()) != 2 && n4 != 1);
                                    if (n4 != 2) {
                                        object2 = drawable;
                                        object2 = drawable;
                                        object = new XmlPullParserException("No start tag found");
                                        object2 = drawable;
                                        throw object;
                                    }
                                    object2 = drawable;
                                    object3 = charSequence.getName();
                                    object2 = drawable;
                                    this.c.a(n3, object3);
                                    object2 = drawable;
                                    b b3 = (b)this.b.get(object3);
                                    object3 = drawable;
                                    if (b3 == null) break block13;
                                    object2 = drawable;
                                    object3 = b3.a((Context)object, (XmlPullParser)charSequence, attributeSet, object.getTheme());
                                }
                                catch (Exception exception2) {
                                    break block14;
                                }
                            }
                            object2 = object3;
                            if (object3 != null) {
                                object2 = object3;
                                object3.setChangingConfigurations(typedValue.changingConfigurations);
                                object2 = object3;
                                this.a((Context)object, l3, (Drawable)object3);
                                object2 = object3;
                            }
                            break block16;
                        }
                        Log.e((String)"ResourceManagerInternal", (String)"Exception while inflating drawable", (Throwable)exception2);
                    }
                }
            }
            if (object2 == null) {
                this.c.a(n3, "appcompat_skip_skip");
            }
            return object2;
        }
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void r(Context object) {
        synchronized (this) {
            Throwable throwable2;
            block4: {
                try {
                    object = (j)this.d.get(object);
                    if (object == null) break block4;
                    ((j)object).a();
                }
                catch (Throwable throwable2) {}
            }
            return;
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Drawable s(Context context, s0 s02, int n3) {
        synchronized (this) {
            try {
                Drawable drawable;
                Drawable drawable2 = drawable = this.q(context, n3);
                if (drawable == null) {
                    drawable2 = s02.a(n3);
                }
                if (drawable2 == null) return null;
                return this.u(context, n3, false, drawable2);
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void t(c c3) {
        synchronized (this) {
            this.g = c3;
            return;
        }
    }

    public final Drawable u(Context context, int n3, boolean bl, Drawable drawable) {
        Object object = this.l(context, n3);
        if (object != null) {
            context = h0.a.r(drawable.mutate());
            h0.a.o((Drawable)context, (ColorStateList)object);
            drawable = this.n(n3);
            if (drawable != null) {
                h0.a.p((Drawable)context, (PorterDuff.Mode)drawable);
            }
            return context;
        }
        object = this.g;
        if (!(object != null && object.e(context, n3, drawable) || this.w(context, n3, drawable) || !bl)) {
            return null;
        }
        return drawable;
    }

    public boolean w(Context context, int n3, Drawable drawable) {
        c c3 = this.g;
        return c3 != null && c3.a(context, n3, drawable);
    }

    public static class a
    extends l {
        public a(int n3) {
            super(n3);
        }

        public static int h(int n3, PorterDuff.Mode mode) {
            return (n3 + 31) * 31 + mode.hashCode();
        }

        public PorterDuffColorFilter i(int n3, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter)this.c(androidx.appcompat.widget.e0$a.h(n3, mode));
        }

        public PorterDuffColorFilter j(int n3, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return (PorterDuffColorFilter)this.d(androidx.appcompat.widget.e0$a.h(n3, mode), porterDuffColorFilter);
        }
    }

    public static interface b {
        public Drawable a(Context var1, XmlPullParser var2, AttributeSet var3, Resources.Theme var4);
    }

    public static interface c {
        public boolean a(Context var1, int var2, Drawable var3);

        public PorterDuff.Mode b(int var1);

        public Drawable c(e0 var1, Context var2, int var3);

        public ColorStateList d(Context var1, int var2);

        public boolean e(Context var1, int var2, Drawable var3);
    }
}

