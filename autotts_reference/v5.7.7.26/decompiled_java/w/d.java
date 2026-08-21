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

public abstract class d
extends s.j {
    public static d f(String string, SparseArray sparseArray) {
        return new b(string, sparseArray);
    }

    public static d g(String string) {
        string.getClass();
        int n3 = string.hashCode();
        int n4 = -1;
        switch (n3) {
            default: {
                break;
            }
            case 156108012: {
                if (!string.equals("waveOffset")) break;
                n4 = 15;
                break;
            }
            case 92909918: {
                if (!string.equals("alpha")) break;
                n4 = 14;
                break;
            }
            case 37232917: {
                if (!string.equals("transitionPathRotate")) break;
                n4 = 13;
                break;
            }
            case -4379043: {
                if (!string.equals("elevation")) break;
                n4 = 12;
                break;
            }
            case -40300674: {
                if (!string.equals("rotation")) break;
                n4 = 11;
                break;
            }
            case -760884509: {
                if (!string.equals("transformPivotY")) break;
                n4 = 10;
                break;
            }
            case -760884510: {
                if (!string.equals("transformPivotX")) break;
                n4 = 9;
                break;
            }
            case -797520672: {
                if (!string.equals("waveVariesBy")) break;
                n4 = 8;
                break;
            }
            case -908189617: {
                if (!string.equals("scaleY")) break;
                n4 = 7;
                break;
            }
            case -908189618: {
                if (!string.equals("scaleX")) break;
                n4 = 6;
                break;
            }
            case -1001078227: {
                if (!string.equals("progress")) break;
                n4 = 5;
                break;
            }
            case -1225497655: {
                if (!string.equals("translationZ")) break;
                n4 = 4;
                break;
            }
            case -1225497656: {
                if (!string.equals("translationY")) break;
                n4 = 3;
                break;
            }
            case -1225497657: {
                if (!string.equals("translationX")) break;
                n4 = 2;
                break;
            }
            case -1249320805: {
                if (!string.equals("rotationY")) break;
                n4 = 1;
                break;
            }
            case -1249320806: {
                if (!string.equals("rotationX")) break;
                n4 = 0;
            }
        }
        switch (n4) {
            default: {
                return null;
            }
            case 15: {
                return new a();
            }
            case 14: {
                return new a();
            }
            case 13: {
                return new d();
            }
            case 12: {
                return new c();
            }
            case 11: {
                return new h();
            }
            case 10: {
                return new f();
            }
            case 9: {
                return new e();
            }
            case 8: {
                return new a();
            }
            case 7: {
                return new l();
            }
            case 6: {
                return new k();
            }
            case 5: {
                return new g();
            }
            case 4: {
                return new o();
            }
            case 3: {
                return new n();
            }
            case 2: {
                return new m();
            }
            case 1: {
                return new j();
            }
            case 0: 
        }
        return new i();
    }

    public abstract void h(View var1, float var2);

    public static class a
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setAlpha(this.a(f3));
        }
    }

    public static class b
    extends d {
        public String f;
        public SparseArray g;
        public float[] h;

        public b(String string, SparseArray sparseArray) {
            this.f = string.split(",")[1];
            this.g = sparseArray;
        }

        @Override
        public void c(int n3, float f3) {
            throw new RuntimeException("call of custom attribute setPoint");
        }

        @Override
        public void e(int n3) {
            int n4 = this.g.size();
            int n5 = ((androidx.constraintlayout.widget.a)this.g.valueAt(0)).h();
            double[] dArray = new double[n4];
            this.h = new float[n5];
            double[][] dArray2 = new double[n4][n5];
            for (n5 = 0; n5 < n4; ++n5) {
                int n6 = this.g.keyAt(n5);
                Object object = (androidx.constraintlayout.widget.a)this.g.valueAt(n5);
                dArray[n5] = (double)n6 * 0.01;
                ((androidx.constraintlayout.widget.a)object).f(this.h);
                for (n6 = 0; n6 < ((Object)(object = (Object)this.h)).length; ++n6) {
                    dArray2[n5][n6] = (double)object[n6];
                }
            }
            this.a = s.b.a(n3, dArray, dArray2);
        }

        @Override
        public void h(View view, float f3) {
            this.a.e(f3, this.h);
            w.a.b((androidx.constraintlayout.widget.a)this.g.valueAt(0), view, this.h);
        }

        public void i(int n3, androidx.constraintlayout.widget.a a4) {
            this.g.append(n3, (Object)a4);
        }
    }

    public static class c
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setElevation(this.a(f3));
        }
    }

    public static class d
    extends d {
        @Override
        public void h(View view, float f3) {
        }

        public void i(View view, float f3, double d3, double d4) {
            view.setRotation(this.a(f3) + (float)Math.toDegrees(Math.atan2(d4, d3)));
        }
    }

    public static class e
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setPivotX(this.a(f3));
        }
    }

    public static class f
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setPivotY(this.a(f3));
        }
    }

    public static class g
    extends d {
        public boolean f = false;

        @Override
        public void h(View view, float f3) {
            block8: {
                if (view instanceof MotionLayout) {
                    ((MotionLayout)view).setProgress(this.a(f3));
                    return;
                }
                if (!this.f) {
                    Method method;
                    try {
                        method = view.getClass().getMethod("setProgress", Float.TYPE);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        this.f = true;
                        method = null;
                    }
                    if (method != null) {
                        IllegalAccessException illegalAccessException2;
                        block9: {
                            try {
                                method.invoke((Object)view, Float.valueOf(this.a(f3)));
                                break block8;
                            }
                            catch (InvocationTargetException invocationTargetException) {
                            }
                            catch (IllegalAccessException illegalAccessException2) {
                                break block9;
                            }
                            Log.e((String)"ViewSpline", (String)"unable to setProgress", (Throwable)invocationTargetException);
                            break block8;
                        }
                        Log.e((String)"ViewSpline", (String)"unable to setProgress", (Throwable)illegalAccessException2);
                    }
                }
            }
        }
    }

    public static class h
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setRotation(this.a(f3));
        }
    }

    public static class i
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setRotationX(this.a(f3));
        }
    }

    public static class j
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setRotationY(this.a(f3));
        }
    }

    public static class k
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setScaleX(this.a(f3));
        }
    }

    public static class l
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setScaleY(this.a(f3));
        }
    }

    public static class m
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setTranslationX(this.a(f3));
        }
    }

    public static class n
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setTranslationY(this.a(f3));
        }
    }

    public static class o
    extends d {
        @Override
        public void h(View view, float f3) {
            view.setTranslationZ(this.a(f3));
        }
    }
}

