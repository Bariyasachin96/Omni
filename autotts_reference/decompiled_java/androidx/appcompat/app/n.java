/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.TypedArray
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package androidx.appcompat.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.j0;
import c.j;
import h.d;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import o.r;
import o0.x0;

public class n {
    public static final Class[] b = new Class[]{Context.class, AttributeSet.class};
    public static final int[] c = new int[]{16843375};
    public static final int[] d = new int[]{16844160};
    public static final int[] e = new int[]{16844156};
    public static final int[] f = new int[]{16844148};
    public static final String[] g = new String[]{"android.widget.", "android.view.", "android.webkit."};
    public static final r h = new r();
    public final Object[] a = new Object[2];

    public static Context u(Context context, AttributeSet attributeSet, boolean bl, boolean bl2) {
        attributeSet = context.obtainStyledAttributes(attributeSet, j.View, 0, 0);
        int n3 = bl ? attributeSet.getResourceId(j.View_android_theme, 0) : 0;
        int n4 = n3;
        if (bl2) {
            n4 = n3;
            if (n3 == 0) {
                n4 = attributeSet.getResourceId(j.View_theme, 0);
            }
        }
        attributeSet.recycle();
        if (!(n4 == 0 || context instanceof d && ((d)context).c() == n4)) {
            return new d(context, n4);
        }
        return context;
    }

    public final void a(Context context, View view, AttributeSet attributeSet) {
        if (Build.VERSION.SDK_INT > 28) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, d);
        if (typedArray.hasValue(0)) {
            x0.i0(view, typedArray.getBoolean(0, false));
        }
        typedArray.recycle();
        typedArray = context.obtainStyledAttributes(attributeSet, e);
        if (typedArray.hasValue(0)) {
            x0.j0(view, typedArray.getString(0));
        }
        typedArray.recycle();
        context = context.obtainStyledAttributes(attributeSet, f);
        if (context.hasValue(0)) {
            x0.t0(view, context.getBoolean(0, false));
        }
        context.recycle();
    }

    public final void b(View view, AttributeSet object) {
        Context context = view.getContext();
        if (context instanceof ContextWrapper && view.hasOnClickListeners()) {
            if ((object = (context = context.obtainStyledAttributes(object, c)).getString(0)) != null) {
                view.setOnClickListener((View.OnClickListener)new a(view, (String)object));
            }
            context.recycle();
        }
    }

    public AppCompatAutoCompleteTextView c(Context context, AttributeSet attributeSet) {
        return new AppCompatAutoCompleteTextView(context, attributeSet);
    }

    public AppCompatButton d(Context context, AttributeSet attributeSet) {
        return new AppCompatButton(context, attributeSet);
    }

    public AppCompatCheckBox e(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckBox(context, attributeSet);
    }

    public AppCompatCheckedTextView f(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckedTextView(context, attributeSet);
    }

    public AppCompatEditText g(Context context, AttributeSet attributeSet) {
        return new AppCompatEditText(context, attributeSet);
    }

    public AppCompatImageButton h(Context context, AttributeSet attributeSet) {
        return new AppCompatImageButton(context, attributeSet);
    }

    public AppCompatImageView i(Context context, AttributeSet attributeSet) {
        return new AppCompatImageView(context, attributeSet);
    }

    public AppCompatMultiAutoCompleteTextView j(Context context, AttributeSet attributeSet) {
        return new AppCompatMultiAutoCompleteTextView(context, attributeSet);
    }

    public AppCompatRadioButton k(Context context, AttributeSet attributeSet) {
        return new AppCompatRadioButton(context, attributeSet);
    }

    public AppCompatRatingBar l(Context context, AttributeSet attributeSet) {
        return new AppCompatRatingBar(context, attributeSet);
    }

    public AppCompatSeekBar m(Context context, AttributeSet attributeSet) {
        return new AppCompatSeekBar(context, attributeSet);
    }

    public AppCompatSpinner n(Context context, AttributeSet attributeSet) {
        return new AppCompatSpinner(context, attributeSet);
    }

    public AppCompatTextView o(Context context, AttributeSet attributeSet) {
        return new AppCompatTextView(context, attributeSet);
    }

    public AppCompatToggleButton p(Context context, AttributeSet attributeSet) {
        return new AppCompatToggleButton(context, attributeSet);
    }

    public View q(Context context, String string, AttributeSet attributeSet) {
        return null;
    }

    public final View r(View object, String string, Context context, AttributeSet attributeSet, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        Context context2;
        block40: {
            block39: {
                context2 = bl && object != null ? object.getContext() : context;
                if (bl2) break block39;
                object = context2;
                if (!bl3) break block40;
            }
            object = n.u(context2, attributeSet, bl2, bl3);
        }
        context2 = object;
        if (bl4) {
            context2 = j0.b((Context)object);
        }
        string.getClass();
        int n3 = string.hashCode();
        int n4 = -1;
        switch (n3) {
            default: {
                break;
            }
            case 2001146706: {
                if (!string.equals("Button")) break;
                n4 = 13;
                break;
            }
            case 1666676343: {
                if (!string.equals("EditText")) break;
                n4 = 12;
                break;
            }
            case 1601505219: {
                if (!string.equals("CheckBox")) break;
                n4 = 11;
                break;
            }
            case 1413872058: {
                if (!string.equals("AutoCompleteTextView")) break;
                n4 = 10;
                break;
            }
            case 1125864064: {
                if (!string.equals("ImageView")) break;
                n4 = 9;
                break;
            }
            case 799298502: {
                if (!string.equals("ToggleButton")) break;
                n4 = 8;
                break;
            }
            case 776382189: {
                if (!string.equals("RadioButton")) break;
                n4 = 7;
                break;
            }
            case -339785223: {
                if (!string.equals("Spinner")) break;
                n4 = 6;
                break;
            }
            case -658531749: {
                if (!string.equals("SeekBar")) break;
                n4 = 5;
                break;
            }
            case -937446323: {
                if (!string.equals("ImageButton")) break;
                n4 = 4;
                break;
            }
            case -938935918: {
                if (!string.equals("TextView")) break;
                n4 = 3;
                break;
            }
            case -1346021293: {
                if (!string.equals("MultiAutoCompleteTextView")) break;
                n4 = 2;
                break;
            }
            case -1455429095: {
                if (!string.equals("CheckedTextView")) break;
                n4 = 1;
                break;
            }
            case -1946472170: {
                if (!string.equals("RatingBar")) break;
                n4 = 0;
            }
        }
        switch (n4) {
            default: {
                object = this.q(context2, string, attributeSet);
                break;
            }
            case 13: {
                object = this.d(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 12: {
                object = this.g(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 11: {
                object = this.e(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 10: {
                object = this.c(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 9: {
                object = this.i(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 8: {
                object = this.p(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 7: {
                object = this.k(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 6: {
                object = this.n(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 5: {
                object = this.m(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 4: {
                object = this.h(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 3: {
                object = this.o(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 2: {
                object = this.j(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 1: {
                object = this.f(context2, attributeSet);
                this.v((View)object, string);
                break;
            }
            case 0: {
                object = this.l(context2, attributeSet);
                this.v((View)object, string);
            }
        }
        Object object2 = object;
        if (object == null) {
            object2 = object;
            if (context != context2) {
                object2 = this.t(context2, string, attributeSet);
            }
        }
        if (object2 != null) {
            this.b((View)object2, attributeSet);
            this.a(context2, (View)object2, attributeSet);
        }
        return object2;
    }

    /*
     * Unable to fully structure code
     */
    public final View s(Context var1_1, String var2_3, String var3_4) {
        var6_5 = n.h;
        var5_6 = (Constructor)var6_5.get(var2_3);
        var4_7 = var5_6;
        if (var5_6 != null) ** GOTO lbl19
        if (var3_4 != null) {
            var4_7 = new Constructor<View>();
            var4_7.append(var3_4);
            var4_7.append(var2_3);
            var3_4 = var4_7.toString();
        } else {
            var3_4 = var2_3;
        }
        try {
            var4_7 = Class.forName(var3_4, false, var1_1.getClassLoader()).asSubclass(View.class).getConstructor(n.b);
            var6_5.put(var2_3, var4_7);
lbl19:
            // 2 sources

            var4_7.setAccessible(true);
            var1_1 = (View)var4_7.newInstance(this.a);
            return var1_1;
        }
        catch (Exception var1_2) {
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final View t(Context objectArray, String view, AttributeSet attributeSet) {
        Object object = view;
        if (view.equals("view")) {
            object = attributeSet.getAttributeValue(null, "class");
        }
        try {
            view = this.a;
            view[0] = objectArray;
            view[1] = attributeSet;
            if (-1 == ((String)object).indexOf(46)) {
                for (int i3 = 0; i3 < ((Object[])(view = g)).length; ++i3) {
                    if ((view = this.s((Context)objectArray, (String)object, (String)view[i3])) == null) continue;
                    objectArray = this.a;
                    objectArray[0] = null;
                    objectArray[1] = null;
                    return view;
                }
                objectArray = this.a;
                objectArray[0] = null;
                objectArray[1] = null;
                return null;
            }
            objectArray = this.s((Context)objectArray, (String)object, null);
            view = this.a;
            view[0] = null;
            view[1] = null;
            return objectArray;
        }
        catch (Throwable throwable) {}
        objectArray = this.a;
        objectArray[0] = null;
        objectArray[1] = null;
        throw throwable;
        catch (Exception exception) {
            Object[] objectArray2 = this.a;
            objectArray2[0] = null;
            objectArray2[1] = null;
            return null;
        }
    }

    public final void v(View object, String string) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getClass().getName());
        ((StringBuilder)object).append(" asked to inflate view for <");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(">, but returned null");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public static class a
    implements View.OnClickListener {
        public final View c;
        public final String d;
        public Method e;
        public Context f;

        public a(View view, String string) {
            this.c = view;
            this.d = string;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void a(Context object) {
            while (true) {
                Object object2;
                if (object != null) {
                    if (!object.isRestricted() && (object2 = object.getClass().getMethod(this.d, View.class)) != null) {
                        this.e = object2;
                        this.f = object;
                        return;
                    }
                } else {
                    int n3 = this.c.getId();
                    if (n3 == -1) {
                        object = "";
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(" with id '");
                        ((StringBuilder)object).append(this.c.getContext().getResources().getResourceEntryName(n3));
                        ((StringBuilder)object).append("'");
                        object = ((StringBuilder)object).toString();
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Could not find method ");
                    ((StringBuilder)object2).append(this.d);
                    ((StringBuilder)object2).append("(View) in a parent or ancestor Context for android:onClick attribute defined on view ");
                    ((StringBuilder)object2).append(this.c.getClass());
                    ((StringBuilder)object2).append((String)object);
                    throw new IllegalStateException(((StringBuilder)object2).toString());
                    catch (NoSuchMethodException noSuchMethodException) {}
                }
                if (object instanceof ContextWrapper) {
                    object = ((ContextWrapper)object).getBaseContext();
                    continue;
                }
                object = null;
            }
        }

        public void onClick(View view) {
            IllegalAccessException illegalAccessException2;
            block4: {
                if (this.e == null) {
                    this.a(this.c.getContext());
                }
                try {
                    this.e.invoke((Object)this.f, view);
                    return;
                }
                catch (InvocationTargetException invocationTargetException) {
                }
                catch (IllegalAccessException illegalAccessException2) {
                    break block4;
                }
                throw new IllegalStateException("Could not execute method for android:onClick", invocationTargetException);
            }
            throw new IllegalStateException("Could not execute non-public method for android:onClick", illegalAccessException2);
        }
    }
}

