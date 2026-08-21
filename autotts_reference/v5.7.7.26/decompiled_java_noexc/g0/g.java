/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.graphics.Typeface$Builder
 *  android.graphics.fonts.FontVariationAxis
 *  android.os.CancellationSignal
 *  android.util.Log
 */
package g0;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.os.CancellationSignal;
import android.util.Log;
import f0.e;
import g0.f;
import g0.k;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import l0.g;

public class g
extends f {
    public final Class g;
    public final Constructor h;
    public final Method i;
    public final Method j;
    public final Method k;
    public final Method l;
    public final Method m;

    public g() {
        Class clazz = this.t();
        Constructor constructor = this.u(clazz);
        Method method = this.q(clazz);
        Method method2 = this.r(clazz);
        Method method3 = this.v(clazz);
        Method method4 = this.p(clazz);
        Method method5 = this.s(clazz);
        this.g = clazz;
        this.h = constructor;
        this.i = method;
        this.j = method2;
        this.k = method3;
        this.l = method4;
        this.m = method5;
    }

    @Override
    public Typeface a(Context context, e.c dArray, Resources object, int n3) {
        if (!this.o()) {
            return super.a(context, (e.c)dArray, (Resources)object, n3);
        }
        object = this.j();
        if (object == null) {
            return null;
        }
        for (e.d d3 : dArray.a()) {
            if (this.l(context, object, d3.a(), d3.c(), d3.e(), d3.f() ? 1 : 0, FontVariationAxis.fromFontVariationSettings((String)d3.d()))) continue;
            this.k(object);
            return null;
        }
        if (!this.n(object)) {
            return null;
        }
        return this.h(object);
    }

    @Override
    public Typeface b(Context object, CancellationSignal object2, g.b[] object3, int n3) {
        if (((g.b[])object3).length < 1) {
            return null;
        }
        if (!this.o()) {
            object3 = this.f((g.b[])object3, n3);
            object = object.getContentResolver();
            if ((object = object.openFileDescriptor(((g.b)object3).d(), "r", (CancellationSignal)object2)) == null) {
                if (object != null) {
                    object.close();
                }
                return null;
            }
            object2 = new Typeface.Builder(object.getFileDescriptor());
            object2 = object2.setWeight(((g.b)object3).e()).setItalic(((g.b)object3).f()).build();
            object.close();
            return object2;
        }
        object2 = g0.k.f(object, (g.b[])object3, (CancellationSignal)object2);
        object = this.j();
        if (object == null) {
            return null;
        }
        int n4 = ((Object)object3).length;
        boolean bl = false;
        for (int i3 = 0; i3 < n4; ++i3) {
            Object object4 = object3[i3];
            ByteBuffer byteBuffer = (ByteBuffer)object2.get(((g.b)object4).d());
            if (byteBuffer == null) continue;
            if (!this.m(object, byteBuffer, ((g.b)object4).c(), ((g.b)object4).e(), ((g.b)object4).f() ? 1 : 0)) {
                this.k(object);
                return null;
            }
            bl = true;
        }
        if (!bl) {
            this.k(object);
            return null;
        }
        if (!this.n(object)) {
            return null;
        }
        if ((object = this.h(object)) == null) {
            return null;
        }
        return Typeface.create((Typeface)object, (int)n3);
    }

    @Override
    public Typeface c(Context context, Resources object, int n3, String string, int n4) {
        if (!this.o()) {
            return super.c(context, (Resources)object, n3, string, n4);
        }
        object = this.j();
        if (object == null) {
            return null;
        }
        if (!this.l(context, object, string, 0, -1, -1, null)) {
            this.k(object);
            return null;
        }
        if (!this.n(object)) {
            return null;
        }
        return this.h(object);
    }

    public Typeface h(Object object) {
        Integer n3 = -1;
        Object object2 = Array.newInstance(this.g, 1);
        Array.set(object2, 0, object);
        object = (Typeface)this.m.invoke(null, object2, n3, n3);
        return object;
    }

    public final Object j() {
        Object var1_1 = null;
        Object t3 = this.h.newInstance(null);
        var1_1 = t3;
        return var1_1;
    }

    public final void k(Object object) {
        this.l.invoke(object, null);
    }

    public final boolean l(Context context, Object object, String string, int n3, int n4, int n5, FontVariationAxis[] fontVariationAxisArray) {
        boolean bl = (Boolean)this.i.invoke(object, context.getAssets(), string, 0, Boolean.FALSE, n3, n4, n5, fontVariationAxisArray);
        return bl;
    }

    public final boolean m(Object object, ByteBuffer byteBuffer, int n3, int n4, int n5) {
        boolean bl = (Boolean)this.j.invoke(object, byteBuffer, n3, null, n4, n5);
        return bl;
    }

    public final boolean n(Object object) {
        boolean bl = (Boolean)this.k.invoke(object, null);
        return bl;
    }

    public final boolean o() {
        if (this.i == null) {
            Log.w((String)"TypefaceCompatApi26Impl", (String)"Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.i != null;
    }

    public Method p(Class clazz) {
        return clazz.getMethod("abortCreation", null);
    }

    public Method q(Class clazz) {
        Class<Boolean> clazz2 = Boolean.TYPE;
        Class<Integer> clazz3 = Integer.TYPE;
        return clazz.getMethod("addFontFromAssetManager", AssetManager.class, String.class, clazz3, clazz2, clazz3, clazz3, clazz3, FontVariationAxis[].class);
    }

    public Method r(Class clazz) {
        Class<Integer> clazz2 = Integer.TYPE;
        return clazz.getMethod("addFontFromBuffer", ByteBuffer.class, clazz2, FontVariationAxis[].class, clazz2, clazz2);
    }

    public Method s(Class genericDeclaration) {
        Class<?> clazz = Array.newInstance(genericDeclaration, 1).getClass();
        genericDeclaration = Integer.TYPE;
        genericDeclaration = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[]{clazz, genericDeclaration, genericDeclaration});
        ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
        return genericDeclaration;
    }

    public Class t() {
        return Class.forName("android.graphics.FontFamily");
    }

    public Constructor u(Class clazz) {
        return clazz.getConstructor(null);
    }

    public Method v(Class clazz) {
        return clazz.getMethod("freeze", null);
    }
}

