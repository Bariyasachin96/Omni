/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Typeface
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
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        Object object;
        Object object2;
        Object object3;
        Method method;
        Method method2;
        Object object4;
        Class clazz;
        block3: {
            try {
                clazz = this.t();
                object4 = this.u(clazz);
                method2 = this.q(clazz);
                method = this.r(clazz);
                object3 = this.v(clazz);
                object2 = this.p(clazz);
                object = this.s(clazz);
                break block3;
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Unable to collect necessary methods for class ");
            ((StringBuilder)object4).append(method.getClass().getName());
            Log.e((String)"TypefaceCompatApi26Impl", (String)((StringBuilder)object4).toString(), (Throwable)((Object)method));
            clazz = null;
            Object var8_10 = null;
            method = method2 = null;
            object = object3 = (object4 = method);
            object2 = object3;
            object3 = object4;
            object4 = var8_10;
        }
        this.g = clazz;
        this.h = object4;
        this.i = method2;
        this.j = method;
        this.k = object3;
        this.l = object2;
        this.m = object;
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

    /*
     * Exception decompiling
     */
    @Override
    public Typeface b(Context var1_1, CancellationSignal var2_4, g.b[] var3_6, int var4_7) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
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
        try {
            Object object2 = Array.newInstance(this.g, 1);
            Array.set(object2, 0, object);
            object = (Typeface)this.m.invoke(null, object2, n3, n3);
            return object;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final Object j() {
        Object t3;
        Object var1_1 = null;
        try {
            t3 = this.h.newInstance(null);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            return var1_1;
        }
        return t3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void k(Object object) {
        try {
            this.l.invoke(object, null);
            return;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return;
        }
    }

    public final boolean l(Context context, Object object, String string, int n3, int n4, int n5, FontVariationAxis[] fontVariationAxisArray) {
        try {
            boolean bl = (Boolean)this.i.invoke(object, context.getAssets(), string, 0, Boolean.FALSE, n3, n4, n5, fontVariationAxisArray);
            return bl;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
    }

    public final boolean m(Object object, ByteBuffer byteBuffer, int n3, int n4, int n5) {
        try {
            boolean bl = (Boolean)this.j.invoke(object, byteBuffer, n3, null, n4, n5);
            return bl;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
    }

    public final boolean n(Object object) {
        try {
            boolean bl = (Boolean)this.k.invoke(object, null);
            return bl;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
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
        genericDeclaration = Array.newInstance(genericDeclaration, 1).getClass();
        Class<Integer> clazz = Integer.TYPE;
        genericDeclaration = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[]{genericDeclaration, clazz, clazz});
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

