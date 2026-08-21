/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.util.Xml
 *  android.view.View
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import y.d;

public class a {
    public boolean a;
    public String b;
    public a c;
    public int d;
    public float e;
    public String f;
    public boolean g;
    public int h;

    public a(a a4, Object object) {
        this.a = false;
        this.b = a4.b;
        this.c = a4.c;
        this.k(object);
    }

    public a(String string, a a4, Object object, boolean bl) {
        this.b = string;
        this.c = a4;
        this.a = bl;
        this.k(object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static HashMap b(HashMap hashMap, View view) {
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        Class<?> clazz = view.getClass();
        Iterator iterator = hashMap.keySet().iterator();
        while (true) {
            NoSuchMethodException noSuchMethodException2;
            Object object;
            String string;
            block9: {
                IllegalAccessException illegalAccessException2;
                Object object2;
                block8: {
                    InvocationTargetException invocationTargetException2;
                    block7: {
                        if (!iterator.hasNext()) {
                            return hashMap2;
                        }
                        string = (String)iterator.next();
                        object = (a)hashMap.get(string);
                        try {
                            if (string.equals("BackgroundColor")) {
                                int n3 = ((ColorDrawable)view.getBackground()).getColor();
                                object2 = new a((a)object, n3);
                                hashMap2.put(string, object2);
                                continue;
                            }
                        }
                        catch (InvocationTargetException invocationTargetException2) {
                            break block7;
                        }
                        catch (IllegalAccessException illegalAccessException2) {
                            break block8;
                        }
                        catch (NoSuchMethodException noSuchMethodException2) {
                            break block9;
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("getMap");
                        ((StringBuilder)object2).append(string);
                        Object object3 = clazz.getMethod(((StringBuilder)object2).toString(), null).invoke((Object)view, null);
                        object2 = new a((a)object, object3);
                        hashMap2.put(string, object2);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" Custom Attribute \"");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("\" not found on ");
                    ((StringBuilder)object).append(clazz.getName());
                    Log.e((String)"TransitionLayout", (String)((StringBuilder)object).toString(), (Throwable)invocationTargetException2);
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(" Custom Attribute \"");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append("\" not found on ");
                ((StringBuilder)object2).append(clazz.getName());
                Log.e((String)"TransitionLayout", (String)((StringBuilder)object2).toString(), (Throwable)illegalAccessException2);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(clazz.getName());
            ((StringBuilder)object).append(" must have a method ");
            ((StringBuilder)object).append(string);
            Log.e((String)"TransitionLayout", (String)((StringBuilder)object).toString(), (Throwable)noSuchMethodException2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void i(Context context, XmlPullParser object, HashMap hashMap) {
        TypedArray typedArray = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)object), y.d.CustomAttribute);
        int n3 = typedArray.getIndexCount();
        String string = null;
        Boolean bl = null;
        a a4 = null;
        boolean bl2 = false;
        for (int i3 = 0; i3 < n3; ++i3) {
            boolean bl3;
            a a5;
            Object object2;
            block12: {
                block16: {
                    int n4;
                    int n5;
                    block22: {
                        block21: {
                            block20: {
                                block19: {
                                    block18: {
                                        block17: {
                                            block15: {
                                                block14: {
                                                    block13: {
                                                        block11: {
                                                            n5 = typedArray.getIndex(i3);
                                                            if (n5 != y.d.CustomAttribute_attributeName) break block11;
                                                            string = typedArray.getString(n5);
                                                            object = string;
                                                            object2 = bl;
                                                            a5 = a4;
                                                            bl3 = bl2;
                                                            if (string != null) {
                                                                object = string;
                                                                object2 = bl;
                                                                a5 = a4;
                                                                bl3 = bl2;
                                                                if (string.length() > 0) {
                                                                    object = new StringBuilder();
                                                                    ((StringBuilder)object).append(Character.toUpperCase(string.charAt(0)));
                                                                    ((StringBuilder)object).append(string.substring(1));
                                                                    object = ((StringBuilder)object).toString();
                                                                    object2 = bl;
                                                                    a5 = a4;
                                                                    bl3 = bl2;
                                                                }
                                                            }
                                                            break block12;
                                                        }
                                                        if (n5 != y.d.CustomAttribute_methodName) break block13;
                                                        object = typedArray.getString(n5);
                                                        bl3 = true;
                                                        object2 = bl;
                                                        a5 = a4;
                                                        break block12;
                                                    }
                                                    if (n5 != y.d.CustomAttribute_customBoolean) break block14;
                                                    object2 = typedArray.getBoolean(n5, false);
                                                    a5 = androidx.constraintlayout.widget.a$a.h;
                                                    object = string;
                                                    bl3 = bl2;
                                                    break block12;
                                                }
                                                if (n5 != y.d.CustomAttribute_customColorValue) break block15;
                                                object = androidx.constraintlayout.widget.a$a.e;
                                                object2 = typedArray.getColor(n5, 0);
                                                break block16;
                                            }
                                            if (n5 != y.d.CustomAttribute_customColorDrawableValue) break block17;
                                            object = androidx.constraintlayout.widget.a$a.f;
                                            object2 = typedArray.getColor(n5, 0);
                                            break block16;
                                        }
                                        if (n5 != y.d.CustomAttribute_customPixelDimension) break block18;
                                        object = androidx.constraintlayout.widget.a$a.i;
                                        object2 = Float.valueOf(TypedValue.applyDimension((int)1, (float)typedArray.getDimension(n5, 0.0f), (DisplayMetrics)context.getResources().getDisplayMetrics()));
                                        break block16;
                                    }
                                    if (n5 != y.d.CustomAttribute_customDimension) break block19;
                                    object = androidx.constraintlayout.widget.a$a.i;
                                    object2 = Float.valueOf(typedArray.getDimension(n5, 0.0f));
                                    break block16;
                                }
                                if (n5 != y.d.CustomAttribute_customFloatValue) break block20;
                                object = androidx.constraintlayout.widget.a$a.d;
                                object2 = Float.valueOf(typedArray.getFloat(n5, Float.NaN));
                                break block16;
                            }
                            if (n5 != y.d.CustomAttribute_customIntegerValue) break block21;
                            object = androidx.constraintlayout.widget.a$a.c;
                            object2 = typedArray.getInteger(n5, -1);
                            break block16;
                        }
                        if (n5 != y.d.CustomAttribute_customStringValue) break block22;
                        object = androidx.constraintlayout.widget.a$a.g;
                        object2 = typedArray.getString(n5);
                        break block16;
                    }
                    object = string;
                    object2 = bl;
                    a5 = a4;
                    bl3 = bl2;
                    if (n5 != y.d.CustomAttribute_customReference) break block12;
                    object = androidx.constraintlayout.widget.a$a.j;
                    int n6 = n4 = typedArray.getResourceId(n5, -1);
                    if (n4 == -1) {
                        n6 = typedArray.getInt(n5, -1);
                    }
                    object2 = n6;
                }
                a5 = object;
                object = string;
                bl3 = bl2;
            }
            string = object;
            bl = object2;
            a4 = a5;
            bl2 = bl3;
        }
        if (string != null && bl != null) {
            hashMap.put(string, new a(string, a4, bl, bl2));
        }
        typedArray.recycle();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void j(View view, HashMap hashMap) {
        Class<?> clazz = view.getClass();
        Iterator iterator = hashMap.keySet().iterator();
        block15: while (true) {
            NoSuchMethodException noSuchMethodException2;
            CharSequence charSequence;
            Object object;
            block20: {
                IllegalAccessException illegalAccessException2;
                String string;
                block19: {
                    InvocationTargetException invocationTargetException2;
                    if (!iterator.hasNext()) {
                        return;
                    }
                    string = (String)iterator.next();
                    object = (a)hashMap.get(string);
                    if (!((a)object).a) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("set");
                        ((StringBuilder)charSequence).append(string);
                        charSequence = ((StringBuilder)charSequence).toString();
                    } else {
                        charSequence = string;
                    }
                    int n3 = ((a)object).c.ordinal();
                    ColorDrawable colorDrawable = Float.TYPE;
                    GenericDeclaration genericDeclaration = Integer.TYPE;
                    switch (n3) {
                        default: {
                            continue block15;
                        }
                        case 7: {
                            try {
                                clazz.getMethod((String)charSequence, new Class[]{genericDeclaration}).invoke((Object)view, ((a)object).d);
                                continue block15;
                            }
                            catch (InvocationTargetException invocationTargetException2) {
                                break;
                            }
                            catch (IllegalAccessException illegalAccessException2) {
                                break block19;
                            }
                            catch (NoSuchMethodException noSuchMethodException2) {
                                break block20;
                            }
                        }
                        case 6: {
                            clazz.getMethod((String)charSequence, new Class[]{colorDrawable}).invoke((Object)view, Float.valueOf(((a)object).e));
                            continue block15;
                        }
                        case 5: {
                            clazz.getMethod((String)charSequence, Boolean.TYPE).invoke((Object)view, ((a)object).g);
                            continue block15;
                        }
                        case 4: {
                            clazz.getMethod((String)charSequence, CharSequence.class).invoke((Object)view, ((a)object).f);
                            continue block15;
                        }
                        case 3: {
                            genericDeclaration = clazz.getMethod((String)charSequence, Drawable.class);
                            colorDrawable = new ColorDrawable();
                            colorDrawable.setColor(((a)object).h);
                            ((Method)genericDeclaration).invoke(view, colorDrawable);
                            continue block15;
                        }
                        case 2: {
                            clazz.getMethod((String)charSequence, new Class[]{genericDeclaration}).invoke((Object)view, ((a)object).h);
                            continue block15;
                        }
                        case 1: {
                            clazz.getMethod((String)charSequence, new Class[]{colorDrawable}).invoke((Object)view, Float.valueOf(((a)object).e));
                            continue block15;
                        }
                        case 0: {
                            clazz.getMethod((String)charSequence, new Class[]{genericDeclaration}).invoke((Object)view, ((a)object).d);
                            continue block15;
                        }
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(" Custom Attribute \"");
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append("\" not found on ");
                    ((StringBuilder)charSequence).append(clazz.getName());
                    Log.e((String)"TransitionLayout", (String)((StringBuilder)charSequence).toString(), (Throwable)invocationTargetException2);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(" Custom Attribute \"");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append("\" not found on ");
                ((StringBuilder)object).append(clazz.getName());
                Log.e((String)"TransitionLayout", (String)((StringBuilder)object).toString(), (Throwable)illegalAccessException2);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(clazz.getName());
            ((StringBuilder)object).append(" must have a method ");
            ((StringBuilder)object).append((String)charSequence);
            Log.e((String)"TransitionLayout", (String)((StringBuilder)object).toString(), (Throwable)noSuchMethodException2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(View object) {
        NoSuchMethodException noSuchMethodException2;
        CharSequence charSequence;
        Class<?> clazz;
        block17: {
            IllegalAccessException illegalAccessException2;
            String string;
            block16: {
                InvocationTargetException invocationTargetException2;
                clazz = object.getClass();
                string = this.b;
                if (!this.a) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("set");
                    ((StringBuilder)charSequence).append(string);
                    charSequence = ((StringBuilder)charSequence).toString();
                } else {
                    charSequence = string;
                }
                int n3 = this.c.ordinal();
                ColorDrawable colorDrawable = Integer.TYPE;
                GenericDeclaration genericDeclaration = Float.TYPE;
                switch (n3) {
                    default: {
                        return;
                    }
                    case 6: {
                        try {
                            clazz.getMethod((String)charSequence, new Class[]{genericDeclaration}).invoke(object, Float.valueOf(this.e));
                            return;
                        }
                        catch (InvocationTargetException invocationTargetException2) {
                            break;
                        }
                        catch (IllegalAccessException illegalAccessException2) {
                            break block16;
                        }
                        catch (NoSuchMethodException noSuchMethodException2) {
                            break block17;
                        }
                    }
                    case 5: {
                        clazz.getMethod((String)charSequence, Boolean.TYPE).invoke(object, this.g);
                        return;
                    }
                    case 4: {
                        clazz.getMethod((String)charSequence, CharSequence.class).invoke(object, this.f);
                        return;
                    }
                    case 3: {
                        genericDeclaration = clazz.getMethod((String)charSequence, Drawable.class);
                        colorDrawable = new ColorDrawable();
                        colorDrawable.setColor(this.h);
                        ((Method)genericDeclaration).invoke(object, colorDrawable);
                        return;
                    }
                    case 2: {
                        clazz.getMethod((String)charSequence, new Class[]{colorDrawable}).invoke(object, this.h);
                        return;
                    }
                    case 1: {
                        clazz.getMethod((String)charSequence, new Class[]{genericDeclaration}).invoke(object, Float.valueOf(this.e));
                        return;
                    }
                    case 0: 
                    case 7: {
                        clazz.getMethod((String)charSequence, new Class[]{colorDrawable}).invoke(object, this.d);
                        return;
                    }
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" Custom Attribute \"");
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append("\" not found on ");
                ((StringBuilder)charSequence).append(clazz.getName());
                Log.e((String)"TransitionLayout", (String)((StringBuilder)charSequence).toString(), (Throwable)invocationTargetException2);
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" Custom Attribute \"");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("\" not found on ");
            ((StringBuilder)charSequence).append(clazz.getName());
            Log.e((String)"TransitionLayout", (String)((StringBuilder)charSequence).toString(), (Throwable)illegalAccessException2);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(clazz.getName());
        ((StringBuilder)object).append(" must have a method ");
        ((StringBuilder)object).append((String)charSequence);
        Log.e((String)"TransitionLayout", (String)((StringBuilder)object).toString(), (Throwable)noSuchMethodException2);
    }

    public String c() {
        return this.b;
    }

    public a d() {
        return this.c;
    }

    public float e() {
        switch (this.c.ordinal()) {
            default: {
                return Float.NaN;
            }
            case 5: {
                if (this.g) {
                    return 1.0f;
                }
                return 0.0f;
            }
            case 4: {
                throw new RuntimeException("Cannot interpolate String");
            }
            case 2: 
            case 3: {
                throw new RuntimeException("Color does not have a single color to interpolate");
            }
            case 1: 
            case 6: {
                return this.e;
            }
            case 0: 
        }
        return this.d;
    }

    public void f(float[] fArray) {
        switch (this.c.ordinal()) {
            default: {
                return;
            }
            case 6: {
                fArray[0] = this.e;
                return;
            }
            case 5: {
                float f3 = this.g ? 1.0f : 0.0f;
                fArray[0] = f3;
                return;
            }
            case 4: {
                throw new RuntimeException("Color does not have a single color to interpolate");
            }
            case 2: 
            case 3: {
                int n3 = this.h;
                float f4 = (float)Math.pow((float)(n3 >> 16 & 0xFF) / 255.0f, 2.2);
                float f5 = (float)Math.pow((float)(n3 >> 8 & 0xFF) / 255.0f, 2.2);
                float f6 = (float)Math.pow((float)(n3 & 0xFF) / 255.0f, 2.2);
                fArray[0] = f4;
                fArray[1] = f5;
                fArray[2] = f6;
                fArray[3] = (float)(n3 >> 24 & 0xFF) / 255.0f;
                return;
            }
            case 1: {
                fArray[0] = this.e;
                return;
            }
            case 0: 
        }
        fArray[0] = this.d;
    }

    public boolean g() {
        int n3 = this.c.ordinal();
        return n3 != 4 && n3 != 5 && n3 != 7;
    }

    public int h() {
        int n3 = this.c.ordinal();
        if (n3 != 2 && n3 != 3) {
            return 1;
        }
        return 4;
    }

    public void k(Object object) {
        switch (this.c.ordinal()) {
            default: {
                return;
            }
            case 6: {
                this.e = ((Float)object).floatValue();
                return;
            }
            case 5: {
                this.g = (Boolean)object;
                return;
            }
            case 4: {
                this.f = (String)object;
                return;
            }
            case 2: 
            case 3: {
                this.h = (Integer)object;
                return;
            }
            case 1: {
                this.e = ((Float)object).floatValue();
                return;
            }
            case 0: 
            case 7: 
        }
        this.d = (Integer)object;
    }

    public static final class a
    extends Enum {
        public static final /* enum */ a c = new a("INT_TYPE", 0);
        public static final /* enum */ a d = new a("FLOAT_TYPE", 1);
        public static final /* enum */ a e = new a("COLOR_TYPE", 2);
        public static final /* enum */ a f = new a("COLOR_DRAWABLE_TYPE", 3);
        public static final /* enum */ a g = new a("STRING_TYPE", 4);
        public static final /* enum */ a h = new a("BOOLEAN_TYPE", 5);
        public static final /* enum */ a i = new a("DIMENSION_TYPE", 6);
        public static final /* enum */ a j = new a("REFERENCE_TYPE", 7);
        public static final a[] k = androidx.constraintlayout.widget.a$a.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public a() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ a[] a() {
            return new a[]{c, d, e, f, g, h, i, j};
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        public static a[] values() {
            return (a[])k.clone();
        }
    }
}

