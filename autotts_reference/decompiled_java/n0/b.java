/*
 * Decompiled with CFR 0.152.
 */
package n0;

public abstract class b {
    public static void a(Object object, StringBuilder stringBuilder) {
        String string;
        if (object == null) {
            stringBuilder.append("null");
            return;
        }
        String string2 = string = object.getClass().getSimpleName();
        if (string.length() <= 0) {
            string = object.getClass().getName();
            int n3 = string.lastIndexOf(46);
            string2 = string;
            if (n3 > 0) {
                string2 = string.substring(n3 + 1);
            }
        }
        stringBuilder.append(string2);
        stringBuilder.append('{');
        stringBuilder.append(Integer.toHexString(System.identityHashCode(object)));
    }
}

