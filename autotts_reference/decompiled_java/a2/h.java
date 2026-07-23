/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.Log
 *  android.util.Property
 */
package a2;

import a2.i;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.Property;
import java.util.ArrayList;
import java.util.List;
import o.r;

public class h {
    public final r a = new r();
    public final r b = new r();

    public static void a(h object, Animator animator) {
        if (animator instanceof ObjectAnimator) {
            animator = (ObjectAnimator)animator;
            ((h)object).l(animator.getPropertyName(), animator.getValues());
            ((h)object).m(animator.getPropertyName(), i.b((ValueAnimator)animator));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Animator must be an ObjectAnimator: ");
        ((StringBuilder)object).append(animator);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static h c(Context context, TypedArray typedArray, int n3) {
        if (typedArray.hasValue(n3) && (n3 = typedArray.getResourceId(n3, 0)) != 0) {
            return h.d(context, n3);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static h d(Context object, int n3) {
        Exception exception2;
        block3: {
            Animator animator;
            try {
                animator = AnimatorInflater.loadAnimator((Context)object, (int)n3);
                if (animator instanceof AnimatorSet) {
                    return h.e(((AnimatorSet)animator).getChildAnimations());
                }
            }
            catch (Exception exception2) {
                break block3;
            }
            if (animator == null) return null;
            object = new ArrayList();
            object.add(animator);
            return h.e((List)object);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't load animation resource ID #0x");
        stringBuilder.append(Integer.toHexString(n3));
        Log.w((String)"MotionSpec", (String)stringBuilder.toString(), (Throwable)exception2);
        return null;
    }

    public static h e(List list) {
        h h3 = new h();
        int n3 = list.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            h.a(h3, (Animator)list.get(i3));
        }
        return h3;
    }

    public final PropertyValuesHolder[] b(PropertyValuesHolder[] propertyValuesHolderArray) {
        PropertyValuesHolder[] propertyValuesHolderArray2 = new PropertyValuesHolder[propertyValuesHolderArray.length];
        for (int i3 = 0; i3 < propertyValuesHolderArray.length; ++i3) {
            propertyValuesHolderArray2[i3] = propertyValuesHolderArray[i3].clone();
        }
        return propertyValuesHolderArray2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof h)) {
            return false;
        }
        object = (h)object;
        return this.a.equals(((h)object).a);
    }

    public ObjectAnimator f(String string, Object object, Property property) {
        object = ObjectAnimator.ofPropertyValuesHolder((Object)object, (PropertyValuesHolder[])this.g(string));
        object.setProperty(property);
        this.h(string).a((Animator)object);
        return object;
    }

    public PropertyValuesHolder[] g(String string) {
        if (this.j(string)) {
            return this.b((PropertyValuesHolder[])this.b.get(string));
        }
        throw new IllegalArgumentException();
    }

    public i h(String string) {
        if (this.k(string)) {
            return (i)this.a.get(string);
        }
        throw new IllegalArgumentException();
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public long i() {
        int n3 = this.a.size();
        long l3 = 0L;
        for (int i3 = 0; i3 < n3; ++i3) {
            i i4 = (i)this.a.j(i3);
            l3 = Math.max(l3, i4.c() + i4.d());
        }
        return l3;
    }

    public boolean j(String string) {
        return this.b.get(string) != null;
    }

    public boolean k(String string) {
        return this.a.get(string) != null;
    }

    public void l(String string, PropertyValuesHolder[] propertyValuesHolderArray) {
        this.b.put(string, propertyValuesHolderArray);
    }

    public void m(String string, i i3) {
        this.a.put(string, i3);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\n');
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append('{');
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" timings: ");
        stringBuilder.append(this.a);
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }
}

