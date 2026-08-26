/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.SpannableString
 *  android.text.style.LocaleSpan
 */



public class e0 {
    public String a;
    public int b;
    public String c;

    public e0(String string, int n3) {
        this.a = string;
        this.b = n3;
        this.c = "";
    }

    public e0(String string, String string2) {
        this.a = string;
        this.b = -1;
        this.c = string2;
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
        stringBuilder.append(": ");
        stringBuilder.append(this.c);
        stringBuilder.append("): '");
        stringBuilder.append(this.a);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}

