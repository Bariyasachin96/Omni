/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.util.TypedValue
 *  android.view.animation.AnimationUtils
 *  android.view.animation.PathInterpolator
 */
package p2;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import g0.d;
import s2.b;
import x0.l;
import z1.m;

public abstract class k {
    public static float a(String[] object, int n3) {
        float f3 = Float.parseFloat(object[n3]);
        if (!(f3 < 0.0f) && !(f3 > 1.0f)) {
            return f3;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Motion easing control point value must be between 0 and 1; instead got: ");
        ((StringBuilder)object).append(f3);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static String b(String string, String string2) {
        return string.substring(string2.length() + 1, string.length() - 1);
    }

    public static TimeInterpolator c(String charSequence) {
        if (k.e((String)charSequence, "cubic-bezier")) {
            String[] stringArray = k.b((String)charSequence, "cubic-bezier").split(",");
            if (stringArray.length == 4) {
                return new PathInterpolator(k.a(stringArray, 0), k.a(stringArray, 1), k.a(stringArray, 2), k.a(stringArray, 3));
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: ");
            ((StringBuilder)charSequence).append(stringArray.length);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        if (k.e((String)charSequence, "path")) {
            return new PathInterpolator(d.e(k.b((String)charSequence, "path")));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid motion easing type: ");
        stringBuilder.append((String)charSequence);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static boolean d(String string) {
        return k.e(string, "cubic-bezier") || k.e(string, "path");
        {
        }
    }

    public static boolean e(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("(");
        return string.startsWith(stringBuilder.toString()) && string.endsWith(")");
    }

    public static int f(Context context, int n3, int n4) {
        return b.d(context, n3, n4);
    }

    public static TimeInterpolator g(Context context, int n3, TimeInterpolator object) {
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(n3, typedValue, true)) {
            return object;
        }
        if (typedValue.type == 3) {
            object = String.valueOf(typedValue.string);
            if (k.d((String)object)) {
                return k.c((String)object);
            }
            return AnimationUtils.loadInterpolator((Context)context, (int)typedValue.resourceId);
        }
        throw new IllegalArgumentException("Motion easing theme attribute must be an @interpolator resource for ?attr/motionEasing*Interpolator attributes or a string for ?attr/motionEasing* attributes.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static l h(Context context, int n3, int n4) {
        Throwable throwable2;
        block7: {
            Object object;
            block5: {
                block6: {
                    object = b.a(context, n3);
                    context = object == null ? context.obtainStyledAttributes(null, m.MaterialSpring, 0, n4) : context.obtainStyledAttributes(((TypedValue)object).resourceId, m.MaterialSpring);
                    object = new l();
                    try {
                        float f3 = context.getFloat(m.MaterialSpring_stiffness, Float.MIN_VALUE);
                        if (f3 == Float.MIN_VALUE) break block5;
                        float f4 = context.getFloat(m.MaterialSpring_damping, Float.MIN_VALUE);
                        if (f4 == Float.MIN_VALUE) break block6;
                        ((l)object).h(f3);
                        ((l)object).f(f4);
                    }
                    catch (Throwable throwable2) {}
                    context.recycle();
                    return object;
                }
                object = new IllegalArgumentException("A MaterialSpring style must have a damping value.");
                throw object;
                break block7;
            }
            object = new IllegalArgumentException("A MaterialSpring style must have stiffness value.");
            throw object;
        }
        context.recycle();
        throw throwable2;
    }
}

