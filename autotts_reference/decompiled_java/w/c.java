/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 */
package w;

import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class c
extends s.e {
    public static c i(String string) {
        if (string.startsWith("CUSTOM")) {
            return new b();
        }
        int n3 = string.hashCode();
        int n4 = -1;
        switch (n3) {
            default: {
                break;
            }
            case 156108012: {
                if (!string.equals("waveOffset")) break;
                n4 = 13;
                break;
            }
            case 92909918: {
                if (!string.equals("alpha")) break;
                n4 = 12;
                break;
            }
            case 37232917: {
                if (!string.equals("transitionPathRotate")) break;
                n4 = 11;
                break;
            }
            case -4379043: {
                if (!string.equals("elevation")) break;
                n4 = 10;
                break;
            }
            case -40300674: {
                if (!string.equals("rotation")) break;
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
            case 13: {
                return new a();
            }
            case 12: {
                return new a();
            }
            case 11: {
                return new d();
            }
            case 10: {
                return new c();
            }
            case 9: {
                return new f();
            }
            case 8: {
                return new a();
            }
            case 7: {
                return new j();
            }
            case 6: {
                return new i();
            }
            case 5: {
                return new e();
            }
            case 4: {
                return new m();
            }
            case 3: {
                return new l();
            }
            case 2: {
                return new k();
            }
            case 1: {
                return new h();
            }
            case 0: 
        }
        return new g();
    }

    public abstract void j(View var1, float var2);

    public static class a
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setAlpha(this.a(f3));
        }
    }

    public static class b
    extends c {
        public float[] h = new float[1];
        public androidx.constraintlayout.widget.a i;

        @Override
        public void c(Object object) {
            this.i = (androidx.constraintlayout.widget.a)object;
        }

        @Override
        public void j(View view, float f3) {
            this.h[0] = this.a(f3);
            w.a.b(this.i, view, this.h);
        }
    }

    public static class c
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setElevation(this.a(f3));
        }
    }

    public static class d
    extends c {
        @Override
        public void j(View view, float f3) {
        }

        public void k(View view, float f3, double d3, double d4) {
            view.setRotation(this.a(f3) + (float)Math.toDegrees(Math.atan2(d4, d3)));
        }
    }

    public static class e
    extends c {
        public boolean h = false;

        @Override
        public void j(View view, float f3) {
            block8: {
                if (view instanceof MotionLayout) {
                    ((MotionLayout)view).setProgress(this.a(f3));
                    return;
                }
                if (!this.h) {
                    Method method;
                    try {
                        method = view.getClass().getMethod("setProgress", Float.TYPE);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        this.h = true;
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
                            Log.e((String)"ViewOscillator", (String)"unable to setProgress", (Throwable)invocationTargetException);
                            break block8;
                        }
                        Log.e((String)"ViewOscillator", (String)"unable to setProgress", (Throwable)illegalAccessException2);
                    }
                }
            }
        }
    }

    public static class f
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setRotation(this.a(f3));
        }
    }

    public static class g
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setRotationX(this.a(f3));
        }
    }

    public static class h
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setRotationY(this.a(f3));
        }
    }

    public static class i
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setScaleX(this.a(f3));
        }
    }

    public static class j
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setScaleY(this.a(f3));
        }
    }

    public static class k
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setTranslationX(this.a(f3));
        }
    }

    public static class l
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setTranslationY(this.a(f3));
        }
    }

    public static class m
    extends c {
        @Override
        public void j(View view, float f3) {
            view.setTranslationZ(this.a(f3));
        }
    }
}

