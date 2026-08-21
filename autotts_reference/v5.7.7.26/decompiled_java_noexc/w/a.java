/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package w;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import java.io.Serializable;
import java.lang.reflect.Method;

public abstract class a {
    public static int a(int n3) {
        n3 = (n3 & ~(n3 >> 31)) - 255;
        return (n3 & n3 >> 31) + 255;
    }

    public static void b(androidx.constraintlayout.widget.a object, View view, float[] object2) {
        Serializable serializable = view.getClass();
        CharSequence charSequence = new StringBuilder();
        charSequence.append("set");
        charSequence.append(((androidx.constraintlayout.widget.a)object).c());
        charSequence = charSequence.toString();
        int n3 = a.a[((androidx.constraintlayout.widget.a)object).d().ordinal()];
        Class<Integer> clazz = Integer.TYPE;
        Class<Float> clazz2 = Float.TYPE;
        boolean bl = true;
        switch (n3) {
            default: {
                break;
            }
            case 7: {
                ((Class)serializable).getMethod((String)charSequence, clazz2).invoke((Object)view, Float.valueOf(object2[0]));
                return;
            }
            case 6: {
                object = ((Class)serializable).getMethod((String)charSequence, Boolean.TYPE);
                if (!(object2[0] > 0.5f)) {
                    bl = false;
                }
                ((Method)object).invoke((Object)view, bl);
                return;
            }
            case 5: {
                object2 = new RuntimeException;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("unable to interpolate strings ");
                ((StringBuilder)serializable).append(((androidx.constraintlayout.widget.a)object).c());
                object2(((StringBuilder)serializable).toString());
                throw object2;
            }
            case 4: {
                object = ((Class)serializable).getMethod((String)charSequence, clazz);
                int n4 = a.a((int)((float)Math.pow(object2[0], 0.45454545454545453) * 255.0f));
                int n5 = a.a((int)((float)Math.pow(object2[1], 0.45454545454545453) * 255.0f));
                n3 = a.a((int)((float)Math.pow(object2[2], 0.45454545454545453) * 255.0f));
                ((Method)object).invoke((Object)view, n4 << 16 | a.a((int)(object2[3] * 255.0f)) << 24 | n5 << 8 | n3);
                return;
            }
            case 3: {
                object = ((Class)serializable).getMethod((String)charSequence, Drawable.class);
                int n6 = a.a((int)((float)Math.pow(object2[0], 0.45454545454545453) * 255.0f));
                int n7 = a.a((int)((float)Math.pow(object2[1], 0.45454545454545453) * 255.0f));
                n3 = a.a((int)((float)Math.pow(object2[2], 0.45454545454545453) * 255.0f));
                int n8 = a.a((int)(object2[3] * 255.0f));
                object2 = new ColorDrawable;
                object2();
                object2.setColor(n6 << 16 | n8 << 24 | n7 << 8 | n3);
                ((Method)object).invoke((Object)view, object2);
                return;
            }
            case 2: {
                ((Class)serializable).getMethod((String)charSequence, clazz2).invoke((Object)view, Float.valueOf(object2[0]));
                return;
            }
            case 1: {
                ((Class)serializable).getMethod((String)charSequence, clazz).invoke((Object)view, (int)object2[0]);
                return;
            }
        }
    }
}

