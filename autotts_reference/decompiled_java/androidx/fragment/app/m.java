/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.LayoutInflater$Factory2
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import a1.c;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.e0;
import androidx.fragment.app.k;
import androidx.fragment.app.w;

public class m
implements LayoutInflater.Factory2 {
    public final FragmentManager c;

    public m(FragmentManager fragmentManager) {
        this.c = fragmentManager;
    }

    public View onCreateView(View object, String object2, Context object3, AttributeSet object4) {
        block18: {
            int n3;
            String string;
            int n4;
            String string2;
            block21: {
                block20: {
                    Object object5;
                    block19: {
                        if (FragmentContainerView.class.getName().equals(object2)) {
                            return new FragmentContainerView((Context)object3, (AttributeSet)object4, this.c);
                        }
                        boolean bl = "fragment".equals(object2);
                        object2 = null;
                        if (!bl) {
                            return null;
                        }
                        object5 = object4.getAttributeValue(null, "class");
                        TypedArray typedArray = object3.obtainStyledAttributes((AttributeSet)object4, a1.c.Fragment);
                        string2 = object5;
                        if (object5 == null) {
                            string2 = typedArray.getString(a1.c.Fragment_android_name);
                        }
                        n4 = typedArray.getResourceId(a1.c.Fragment_android_id, -1);
                        string = typedArray.getString(a1.c.Fragment_android_tag);
                        typedArray.recycle();
                        if (string2 == null || !k.b(object3.getClassLoader(), string2)) break block18;
                        n3 = object != null ? object.getId() : 0;
                        if (n3 == -1 && n4 == -1 && string == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append(object4.getPositionDescription());
                            ((StringBuilder)object).append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
                            ((StringBuilder)object).append(string2);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        if (n4 != -1) {
                            object2 = this.c.h0(n4);
                        }
                        object5 = object2;
                        if (object2 == null) {
                            object5 = object2;
                            if (string != null) {
                                object5 = this.c.i0(string);
                            }
                        }
                        object2 = object5;
                        if (object5 == null) {
                            object2 = object5;
                            if (n3 != -1) {
                                object2 = this.c.h0(n3);
                            }
                        }
                        if (object2 != null) break block19;
                        object2 = this.c.t0().a(object3.getClassLoader(), string2);
                        ((Fragment)object2).q = true;
                        int n5 = n4 != 0 ? n4 : n3;
                        ((Fragment)object2).z = n5;
                        ((Fragment)object2).A = n3;
                        ((Fragment)object2).B = string;
                        ((Fragment)object2).r = true;
                        ((Fragment)object2).v = object3 = this.c;
                        ((Fragment)object2).w = ((FragmentManager)object3).v0();
                        ((Fragment)object2).w0(this.c.v0().q(), (AttributeSet)object4, ((Fragment)object2).d);
                        object5 = this.c.j((Fragment)object2);
                        object3 = object2;
                        object4 = object5;
                        if (FragmentManager.I0(2)) {
                            object2.toString();
                            Integer.toHexString(n4);
                            object3 = object2;
                            object4 = object5;
                        }
                        break block20;
                    }
                    if (((Fragment)object2).r) break block21;
                    ((Fragment)object2).r = true;
                    object3 = this.c;
                    ((Fragment)object2).v = object3;
                    ((Fragment)object2).w = ((FragmentManager)object3).v0();
                    ((Fragment)object2).w0(this.c.v0().q(), (AttributeSet)object4, ((Fragment)object2).d);
                    object5 = this.c.v((Fragment)object2);
                    object3 = object2;
                    object4 = object5;
                    if (FragmentManager.I0(2)) {
                        object2.toString();
                        Integer.toHexString(n4);
                        object4 = object5;
                        object3 = object2;
                    }
                }
                object = (ViewGroup)object;
                b1.c.g((Fragment)object3, (ViewGroup)object);
                ((Fragment)object3).J = object;
                ((w)object4).m();
                ((w)object4).j();
                object = ((Fragment)object3).K;
                if (object != null) {
                    if (n4 != 0) {
                        object.setId(n4);
                    }
                    if (((Fragment)object3).K.getTag() == null) {
                        ((Fragment)object3).K.setTag((Object)string);
                    }
                    ((Fragment)object3).K.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this, (w)object4){
                        public final w c;
                        public final m d;
                        {
                            this.d = m3;
                            this.c = w3;
                        }

                        public void onViewAttachedToWindow(View object) {
                            object = this.c.k();
                            this.c.m();
                            e0.n((ViewGroup)object.K.getParent(), this.d.c).j();
                        }

                        public void onViewDetachedFromWindow(View view) {
                        }
                    });
                    return ((Fragment)object3).K;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Fragment ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" did not create a view.");
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(object4.getPositionDescription());
            ((StringBuilder)object).append(": Duplicate id 0x");
            ((StringBuilder)object).append(Integer.toHexString(n4));
            ((StringBuilder)object).append(", tag ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(", or parent id 0x");
            ((StringBuilder)object).append(Integer.toHexString(n3));
            ((StringBuilder)object).append(" with another fragment for ");
            ((StringBuilder)object).append(string2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return null;
    }

    public View onCreateView(String string, Context context, AttributeSet attributeSet) {
        return this.onCreateView(null, string, context, attributeSet);
    }
}

