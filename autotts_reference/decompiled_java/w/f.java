/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 */
package w;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import s.o;

public abstract class f
extends o {
    public static f g(String string, SparseArray sparseArray) {
        return new b(string, sparseArray);
    }

    public static f h(String object, long l3) {
        object.getClass();
        int n3 = ((String)object).hashCode();
        int n4 = -1;
        switch (n3) {
            default: {
                break;
            }
            case 92909918: {
                if (!((String)object).equals("alpha")) break;
                n4 = 11;
                break;
            }
            case 37232917: {
                if (!((String)object).equals("transitionPathRotate")) break;
                n4 = 10;
                break;
            }
            case -4379043: {
                if (!((String)object).equals("elevation")) break;
                n4 = 9;
                break;
            }
            case -40300674: {
                if (!((String)object).equals("rotation")) break;
                n4 = 8;
                break;
            }
            case -908189617: {
                if (!((String)object).equals("scaleY")) break;
                n4 = 7;
                break;
            }
            case -908189618: {
                if (!((String)object).equals("scaleX")) break;
                n4 = 6;
                break;
            }
            case -1001078227: {
                if (!((String)object).equals("progress")) break;
                n4 = 5;
                break;
            }
            case -1225497655: {
                if (!((String)object).equals("translationZ")) break;
                n4 = 4;
                break;
            }
            case -1225497656: {
                if (!((String)object).equals("translationY")) break;
                n4 = 3;
                break;
            }
            case -1225497657: {
                if (!((String)object).equals("translationX")) break;
                n4 = 2;
                break;
            }
            case -1249320805: {
                if (!((String)object).equals("rotationY")) break;
                n4 = 1;
                break;
            }
            case -1249320806: {
                if (!((String)object).equals("rotationX")) break;
                n4 = 0;
            }
        }
        switch (n4) {
            default: {
                return null;
            }
            case 11: {
                object = new a();
                break;
            }
            case 10: {
                object = new d();
                break;
            }
            case 9: {
                object = new c();
                break;
            }
            case 8: {
                object = new f();
                break;
            }
            case 7: {
                object = new j();
                break;
            }
            case 6: {
                object = new i();
                break;
            }
            case 5: {
                object = new e();
                break;
            }
            case 4: {
                object = new m();
                break;
            }
            case 3: {
                object = new l();
                break;
            }
            case 2: {
                object = new k();
                break;
            }
            case 1: {
                object = new h();
                break;
            }
            case 0: {
                object = new g();
            }
        }
        ((o)object).c(l3);
        return object;
    }

    public float f(float f3, long l3, View view, s.d d3) {
        this.a.e(f3, this.g);
        float[] fArray = this.g;
        float f4 = fArray[1];
        float f5 = f4 - 0.0f;
        float f6 = f5 == 0.0f ? 0 : (f5 > 0.0f ? 1 : -1);
        if (f6 == false) {
            this.h = false;
            return fArray[2];
        }
        if (Float.isNaN(this.j)) {
            this.j = f3 = d3.a(view, this.f, 0);
            if (Float.isNaN(f3)) {
                this.j = 0.0f;
            }
        }
        long l4 = this.i;
        this.j = f3 = (float)(((double)this.j + (double)(l3 - l4) * 1.0E-9 * (double)f4) % 1.0);
        d3.b(view, this.f, 0, f3);
        this.i = l3;
        f3 = this.g[0];
        float f7 = this.a(this.j);
        f4 = this.g[2];
        boolean bl = f3 != 0.0f || f6 != false;
        this.h = bl;
        return f7 * f3 + f4;
    }

    public abstract boolean i(View var1, float var2, long var3, s.d var5);

    public static class a
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setAlpha(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class b
    extends f {
        public String l;
        public SparseArray m;
        public SparseArray n = new SparseArray();
        public float[] o;

        public b(String string, SparseArray sparseArray) {
            this.l = string.split(",")[1];
            this.m = sparseArray;
        }

        @Override
        public void b(int n3, float f3, float f4, int n4, float f5) {
            throw new RuntimeException("Wrong call for custom attribute");
        }

        @Override
        public void e(int n3) {
            int n4 = this.m.size();
            int n5 = ((androidx.constraintlayout.widget.a)this.m.valueAt(0)).h();
            double[] dArray = new double[n4];
            int n6 = n5 + 2;
            this.o = new float[n6];
            this.g = new float[n5];
            double[][] dArray2 = new double[n4][n6];
            for (n6 = 0; n6 < n4; ++n6) {
                int n7 = this.m.keyAt(n6);
                Object object = (androidx.constraintlayout.widget.a)this.m.valueAt(n6);
                float[] fArray = (float[])this.n.valueAt(n6);
                dArray[n6] = (double)n7 * 0.01;
                ((androidx.constraintlayout.widget.a)object).f(this.o);
                for (n7 = 0; n7 < ((Object)(object = (Object)this.o)).length; ++n7) {
                    dArray2[n6][n7] = (double)object[n7];
                }
                object = dArray2[n6];
                object[n5] = (double)fArray[0];
                object[n5 + 1] = (double)fArray[1];
            }
            this.a = s.b.a(n3, dArray, dArray2);
        }

        @Override
        public boolean i(View view, float f3, long l3, s.d object) {
            float f4;
            this.a.e(f3, this.o);
            float[] fArray = this.o;
            float f5 = fArray[fArray.length - 2];
            f3 = fArray[fArray.length - 1];
            long l4 = this.i;
            if (Float.isNaN(this.j)) {
                this.j = f4 = ((s.d)object).a(view, this.l, 0);
                if (Float.isNaN(f4)) {
                    this.j = 0.0f;
                }
            }
            this.j = f4 = (float)(((double)this.j + (double)(l3 - l4) * 1.0E-9 * (double)f5) % 1.0);
            this.i = l3;
            f4 = this.a(f4);
            this.h = false;
            for (int i3 = 0; i3 < ((Object)(object = (Object)this.g)).length; ++i3) {
                boolean bl = this.h;
                float f6 = this.o[i3];
                boolean bl2 = (double)f6 != 0.0;
                this.h = bl | bl2;
                object[i3] = f6 * f4 + f3;
            }
            w.a.b((androidx.constraintlayout.widget.a)this.m.valueAt(0), view, this.g);
            if (f5 != 0.0f) {
                this.h = true;
            }
            return this.h;
        }

        public void j(int n3, androidx.constraintlayout.widget.a a4, float f3, int n4, float f4) {
            this.m.append(n3, (Object)a4);
            this.n.append(n3, (Object)new float[]{f3, f4});
            this.b = Math.max(this.b, n4);
        }
    }

    public static class c
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setElevation(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class d
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            return this.h;
        }

        public boolean j(View view, s.d d3, float f3, long l3, double d4, double d5) {
            view.setRotation(this.f(f3, l3, view, d3) + (float)Math.toDegrees(Math.atan2(d5, d4)));
            return this.h;
        }
    }

    public static class e
    extends f {
        public boolean l = false;

        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            block7: {
                Method method;
                block9: {
                    if (!(view instanceof MotionLayout)) break block9;
                    ((MotionLayout)view).setProgress(this.f(f3, l3, view, d3));
                    break block7;
                }
                if (this.l) {
                    return false;
                }
                try {
                    method = view.getClass().getMethod("setProgress", Float.TYPE);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    this.l = true;
                    method = null;
                }
                if (method != null) {
                    IllegalAccessException illegalAccessException2;
                    block8: {
                        try {
                            method.invoke((Object)view, Float.valueOf(this.f(f3, l3, view, d3)));
                            break block7;
                        }
                        catch (InvocationTargetException invocationTargetException) {
                        }
                        catch (IllegalAccessException illegalAccessException2) {
                            break block8;
                        }
                        Log.e((String)"ViewTimeCycle", (String)"unable to setProgress", (Throwable)invocationTargetException);
                        break block7;
                    }
                    Log.e((String)"ViewTimeCycle", (String)"unable to setProgress", (Throwable)illegalAccessException2);
                }
            }
            return this.h;
        }
    }

    public static class f
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setRotation(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class g
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setRotationX(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class h
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setRotationY(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class i
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setScaleX(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class j
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setScaleY(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class k
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setTranslationX(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class l
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setTranslationY(this.f(f3, l3, view, d3));
            return this.h;
        }
    }

    public static class m
    extends f {
        @Override
        public boolean i(View view, float f3, long l3, s.d d3) {
            view.setTranslationZ(this.f(f3, l3, view, d3));
            return this.h;
        }
    }
}

