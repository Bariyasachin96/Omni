/*
 * Decompiled with CFR 0.152.
 */


public abstract class c0 {
    public static /* synthetic */ boolean a(String string) {
        int n3;
        int n4 = string.length();
        for (int i3 = 0; i3 < n4; i3 += Character.charCount(n3)) {
            n3 = string.codePointAt(i3);
            if (Character.isWhitespace(n3)) continue;
            return false;
        }
        return true;
    }
}

