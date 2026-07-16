/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 */
package o1;

import android.os.Parcelable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import o1.c;

public abstract class a {
    public final o.a a;
    public final o.a b;
    public final o.a c;

    public a(o.a a4, o.a a5, o.a a6) {
        this.a = a4;
        this.b = a5;
        this.c = a6;
    }

    public abstract void A(byte[] var1);

    public void B(byte[] byArray, int n3) {
        this.w(n3);
        this.A(byArray);
    }

    public abstract void C(CharSequence var1);

    public void D(CharSequence charSequence, int n3) {
        this.w(n3);
        this.C(charSequence);
    }

    public abstract void E(int var1);

    public void F(int n3, int n4) {
        this.w(n4);
        this.E(n3);
    }

    public abstract void G(Parcelable var1);

    public void H(Parcelable parcelable, int n3) {
        this.w(n3);
        this.G(parcelable);
    }

    public abstract void I(String var1);

    public void J(String string, int n3) {
        this.w(n3);
        this.I(string);
    }

    public void K(c c3, a a4) {
        IllegalAccessException illegalAccessException2;
        block8: {
            InvocationTargetException invocationTargetException2;
            block7: {
                NoSuchMethodException noSuchMethodException2;
                block6: {
                    try {
                        this.e(c3.getClass()).invoke(null, c3, a4);
                        return;
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                    }
                    catch (NoSuchMethodException noSuchMethodException2) {
                        break block6;
                    }
                    catch (InvocationTargetException invocationTargetException2) {
                        break block7;
                    }
                    catch (IllegalAccessException illegalAccessException2) {
                        break block8;
                    }
                    throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", classNotFoundException);
                }
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", noSuchMethodException2);
            }
            if (invocationTargetException2.getCause() instanceof RuntimeException) {
                throw (RuntimeException)invocationTargetException2.getCause();
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", invocationTargetException2);
        }
        throw new RuntimeException("VersionedParcel encountered IllegalAccessException", illegalAccessException2);
    }

    public void L(c c3) {
        if (c3 == null) {
            this.I(null);
            return;
        }
        this.N(c3);
        a a4 = this.b();
        this.K(c3, a4);
        a4.a();
    }

    public void M(c c3, int n3) {
        this.w(n3);
        this.L(c3);
    }

    public final void N(c c3) {
        try {
            Class clazz = this.c(c3.getClass());
            this.I(clazz.getName());
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(c3.getClass().getSimpleName());
            stringBuilder.append(" does not have a Parcelizer");
            throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
        }
    }

    public abstract void a();

    public abstract a b();

    public final Class c(Class clazz) {
        Class<?> clazz2;
        Class<?> clazz3 = clazz2 = (Class<?>)this.c.get(clazz.getName());
        if (clazz2 == null) {
            clazz3 = Class.forName(String.format("%s.%sParcelizer", clazz.getPackage().getName(), clazz.getSimpleName()), false, clazz.getClassLoader());
            this.c.put(clazz.getName(), clazz3);
        }
        return clazz3;
    }

    public final Method d(String string) {
        Method method;
        Method method2 = method = (Method)this.a.get(string);
        if (method == null) {
            System.currentTimeMillis();
            method2 = Class.forName(string, true, a.class.getClassLoader()).getDeclaredMethod("read", a.class);
            this.a.put(string, method2);
        }
        return method2;
    }

    public final Method e(Class clazz) {
        Method method = (Method)this.b.get(clazz.getName());
        GenericDeclaration genericDeclaration = method;
        if (method == null) {
            genericDeclaration = this.c(clazz);
            System.currentTimeMillis();
            genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod("write", clazz, a.class);
            this.b.put(clazz.getName(), genericDeclaration);
        }
        return genericDeclaration;
    }

    public boolean f() {
        return false;
    }

    public abstract boolean g();

    public boolean h(boolean bl, int n3) {
        if (!this.m(n3)) {
            return bl;
        }
        return this.g();
    }

    public abstract byte[] i();

    public byte[] j(byte[] byArray, int n3) {
        if (!this.m(n3)) {
            return byArray;
        }
        return this.i();
    }

    public abstract CharSequence k();

    public CharSequence l(CharSequence charSequence, int n3) {
        if (!this.m(n3)) {
            return charSequence;
        }
        return this.k();
    }

    public abstract boolean m(int var1);

    public c n(String object, a a4) {
        IllegalAccessException illegalAccessException2;
        block8: {
            InvocationTargetException invocationTargetException2;
            block7: {
                NoSuchMethodException noSuchMethodException2;
                block6: {
                    try {
                        object = (c)this.d((String)object).invoke(null, a4);
                        return object;
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                    }
                    catch (NoSuchMethodException noSuchMethodException2) {
                        break block6;
                    }
                    catch (InvocationTargetException invocationTargetException2) {
                        break block7;
                    }
                    catch (IllegalAccessException illegalAccessException2) {
                        break block8;
                    }
                    throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", classNotFoundException);
                }
                throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", noSuchMethodException2);
            }
            if (invocationTargetException2.getCause() instanceof RuntimeException) {
                throw (RuntimeException)invocationTargetException2.getCause();
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", invocationTargetException2);
        }
        throw new RuntimeException("VersionedParcel encountered IllegalAccessException", illegalAccessException2);
    }

    public abstract int o();

    public int p(int n3, int n4) {
        if (!this.m(n4)) {
            return n3;
        }
        return this.o();
    }

    public abstract Parcelable q();

    public Parcelable r(Parcelable parcelable, int n3) {
        if (!this.m(n3)) {
            return parcelable;
        }
        return this.q();
    }

    public abstract String s();

    public String t(String string, int n3) {
        if (!this.m(n3)) {
            return string;
        }
        return this.s();
    }

    public c u() {
        String string = this.s();
        if (string == null) {
            return null;
        }
        return this.n(string, this.b());
    }

    public c v(c c3, int n3) {
        if (!this.m(n3)) {
            return c3;
        }
        return this.u();
    }

    public abstract void w(int var1);

    public void x(boolean bl, boolean bl2) {
    }

    public abstract void y(boolean var1);

    public void z(boolean bl, int n3) {
        this.w(n3);
        this.y(bl);
    }
}

