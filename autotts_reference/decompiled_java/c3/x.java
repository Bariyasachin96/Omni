/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.SpannableString
 *  android.text.style.LocaleSpan
 */
package c3;

import android.text.SpannableString;
import android.text.style.LocaleSpan;
import com.vnspeak.autotts.AutoTtsService;
import java.util.ArrayList;

public class x {
    public String a;
    public int b;
    public String c;

    public x(String string, int n3) {
        this.a = string;
        this.b = n3;
        this.c = "";
    }

    public x(String string, String string2) {
        this.a = string;
        this.b = -1;
        this.c = string2;
    }

    public static ArrayList g(CharSequence charSequence) {
        ArrayList<x> arrayList = new ArrayList<x>();
        if (AutoTtsService.N) {
            SpannableString spannableString = new SpannableString(charSequence);
            int n3 = spannableString.length();
            LocaleSpan[] localeSpanArray = (LocaleSpan[])spannableString.getSpans(0, n3 - 1, LocaleSpan.class);
            int n4 = localeSpanArray.length;
            n3 = 0;
            for (int i3 = 0; i3 < n4; ++i3) {
                charSequence = localeSpanArray[i3];
                int n5 = spannableString.getSpanStart((Object)charSequence);
                int n6 = spannableString.getSpanEnd((Object)charSequence);
                if (n5 > n3) {
                    arrayList.add(new x(spannableString.subSequence(n3, n5).toString(), "UNKNOWN"));
                }
                arrayList.add(new x(spannableString.subSequence(n5, n6).toString(), charSequence.getLocale().getLanguage()));
                n3 = n6 + 1;
            }
            if (n3 < spannableString.length()) {
                arrayList.add(new x(spannableString.subSequence(n3, spannableString.length()).toString(), "UNKNOWN"));
            }
            return arrayList;
        }
        arrayList.add(new x(charSequence.toString(), "UNKNOWN"));
        return arrayList;
    }

    public int a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.a;
    }

    public void d(int n3) {
        this.b = n3;
    }

    public void e(String string) {
        this.c = string;
    }

    public void f(String string) {
        this.a = string;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(this.b);
        stringBuilder.append("): '");
        stringBuilder.append(this.a);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}

