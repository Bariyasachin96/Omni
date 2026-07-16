/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.view.inputmethod.EditorInfo
 */
package s0;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import n0.h;

public abstract class a {
    public static final String[] a = new String[0];

    public static boolean a(CharSequence charSequence, int n3, int n4) {
        if (n4 != 0) {
            if (n4 != 1) {
                return false;
            }
            return Character.isHighSurrogate(charSequence.charAt(n3));
        }
        return Character.isLowSurrogate(charSequence.charAt(n3));
    }

    public static boolean b(int n3) {
        return (n3 &= 0xFFF) == 129 || n3 == 225 || n3 == 18;
        {
        }
    }

    public static void c(EditorInfo editorInfo, String[] stringArray) {
        editorInfo.contentMimeTypes = stringArray;
    }

    public static void d(EditorInfo editorInfo, CharSequence charSequence, int n3) {
        h.g(charSequence);
        if (Build.VERSION.SDK_INT >= 30) {
            s0.a$a.a(editorInfo, charSequence, n3);
            return;
        }
        int n4 = editorInfo.initialSelStart;
        int n5 = editorInfo.initialSelEnd;
        int n6 = n4 > n5 ? n5 - n3 : n4 - n3;
        n5 = n4 > n5 ? n4 - n3 : (n5 -= n3);
        n4 = charSequence.length();
        if (n3 >= 0 && n6 >= 0 && n5 <= n4) {
            if (s0.a.b(editorInfo.inputType)) {
                s0.a.f(editorInfo, null, 0, 0);
                return;
            }
            if (n4 <= 2048) {
                s0.a.f(editorInfo, charSequence, n6, n5);
                return;
            }
            s0.a.g(editorInfo, charSequence, n6, n5);
            return;
        }
        s0.a.f(editorInfo, null, 0, 0);
    }

    public static void e(EditorInfo editorInfo, CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 30) {
            s0.a$a.a(editorInfo, charSequence, 0);
            return;
        }
        s0.a.d(editorInfo, charSequence, 0);
    }

    public static void f(EditorInfo editorInfo, CharSequence charSequence, int n3, int n4) {
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        charSequence = charSequence != null ? new SpannableStringBuilder(charSequence) : null;
        editorInfo.extras.putCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT", charSequence);
        editorInfo.extras.putInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD", n3);
        editorInfo.extras.putInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END", n4);
    }

    public static void g(EditorInfo editorInfo, CharSequence charSequence, int n3, int n4) {
        int n5 = n4 - n3;
        int n6 = n5 > 1024 ? 0 : n5;
        int n7 = charSequence.length();
        int n8 = 2048 - n6;
        int n9 = Math.min(n7 - n4, n8 - Math.min(n3, (int)((double)n8 * 0.8)));
        n7 = Math.min(n3, n8 - n9);
        int n10 = n3 - n7;
        n8 = n7;
        n3 = n10;
        if (s0.a.a(charSequence, n10, 0)) {
            n3 = n10 + 1;
            n8 = n7 - 1;
        }
        n7 = n9;
        if (s0.a.a(charSequence, n4 + n9 - 1, 1)) {
            n7 = n9 - 1;
        }
        charSequence = n6 != n5 ? TextUtils.concat((CharSequence[])new CharSequence[]{charSequence.subSequence(n3, n3 + n8), charSequence.subSequence(n4, n7 + n4)}) : charSequence.subSequence(n3, n8 + n6 + n7 + n3);
        s0.a.f(editorInfo, charSequence, n8, n6 + n8);
    }

    public static abstract class a {
        public static void a(EditorInfo editorInfo, CharSequence charSequence, int n3) {
            editorInfo.setInitialSurroundingSubText(charSequence, n3);
        }
    }
}

