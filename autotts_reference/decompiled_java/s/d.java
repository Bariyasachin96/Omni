/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.util.Arrays;
import java.util.HashMap;

public class d {
    public HashMap a = new HashMap();

    public float a(Object object, String string, int n3) {
        if (!this.a.containsKey(object)) {
            return Float.NaN;
        }
        if ((object = (HashMap)this.a.get(object)) != null && ((HashMap)object).containsKey(string)) {
            if ((object = (Object)((float[])((HashMap)object).get(string))) == null) {
                return Float.NaN;
            }
            if (((Object)object).length > n3) {
                return (float)object[n3];
            }
        }
        return Float.NaN;
    }

    public void b(Object object, String string, int n3, float f3) {
        if (!this.a.containsKey(object)) {
            HashMap<String, float[]> hashMap = new HashMap<String, float[]>();
            float[] fArray = new float[n3 + 1];
            fArray[n3] = f3;
            hashMap.put(string, fArray);
            this.a.put(object, hashMap);
            return;
        }
        Object object2 = (HashMap<String, Object>)this.a.get(object);
        HashMap<String, Object> hashMap = object2;
        if (object2 == null) {
            hashMap = new HashMap<String, Object>();
        }
        if (!hashMap.containsKey(string)) {
            object2 = new float[n3 + 1];
            object2[n3] = f3;
            hashMap.put(string, object2);
            this.a.put(object, hashMap);
            return;
        }
        object = object2 = (Object)((float[])hashMap.get(string));
        if (object2 == null) {
            object = new float[0];
        }
        object2 = object;
        if (((Object)object).length <= n3) {
            object2 = Arrays.copyOf((float[])object, n3 + 1);
        }
        object2[n3] = f3;
        hashMap.put(string, object2);
    }
}

