/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.SpannableStringBuilder
 */
package m0;

import android.text.SpannableStringBuilder;
import java.util.Locale;
import m0.m;
import m0.n;
import m0.o;

public final class a {
    public static final m d;
    public static final String e;
    public static final String f;
    public static final a g;
    public static final a h;
    public final boolean a;
    public final int b;
    public final m c;

    static {
        m m3;
        d = m3 = n.c;
        e = Character.toString('\u200e');
        f = Character.toString('\u200f');
        g = new a(false, 2, m3);
        h = new a(true, 2, m3);
    }

    public a(boolean bl, int n3, m m3) {
        this.a = bl;
        this.b = n3;
        this.c = m3;
    }

    public static int a(CharSequence charSequence) {
        return new b(charSequence, false).d();
    }

    public static int b(CharSequence charSequence) {
        return new b(charSequence, false).e();
    }

    public static a c() {
        return new a().a();
    }

    public static boolean e(Locale locale) {
        return o.a(locale) == 1;
    }

    public boolean d() {
        return (this.b & 2) != 0;
    }

    public final String f(CharSequence charSequence, m m3) {
        boolean bl = m3.isRtl(charSequence, 0, charSequence.length());
        if (!this.a && (bl || m0.a.b(charSequence) == 1)) {
            return e;
        }
        if (this.a && (!bl || m0.a.b(charSequence) == -1)) {
            return f;
        }
        return "";
    }

    public final String g(CharSequence charSequence, m m3) {
        boolean bl = m3.isRtl(charSequence, 0, charSequence.length());
        if (!this.a && (bl || m0.a.a(charSequence) == 1)) {
            return e;
        }
        if (this.a && (!bl || m0.a.a(charSequence) == -1)) {
            return f;
        }
        return "";
    }

    public CharSequence h(CharSequence charSequence) {
        return this.i(charSequence, this.c, true);
    }

    public CharSequence i(CharSequence charSequence, m m3, boolean bl) {
        if (charSequence == null) {
            return null;
        }
        boolean bl2 = m3.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (this.d() && bl) {
            m3 = bl2 ? n.b : n.a;
            spannableStringBuilder.append((CharSequence)this.g(charSequence, m3));
        }
        if (bl2 != this.a) {
            char c3 = bl2 ? (char)'\u202b' : '\u202a';
            spannableStringBuilder.append(c3);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append('\u202c');
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (bl) {
            m3 = bl2 ? n.b : n.a;
            spannableStringBuilder.append((CharSequence)this.f(charSequence, m3));
        }
        return spannableStringBuilder;
    }

    public String j(String string) {
        return this.k(string, this.c, true);
    }

    public String k(String string, m m3, boolean bl) {
        if (string == null) {
            return null;
        }
        return this.i(string, m3, bl).toString();
    }

    public static final class a {
        public boolean a;
        public int b;
        public m c;

        public a() {
            this.c(m0.a.e(Locale.getDefault()));
        }

        public static a b(boolean bl) {
            if (bl) {
                return h;
            }
            return g;
        }

        public a a() {
            if (this.b == 2 && this.c == d) {
                return m0.a$a.b(this.a);
            }
            return new a(this.a, this.b, this.c);
        }

        public final void c(boolean bl) {
            this.a = bl;
            this.c = d;
            this.b = 2;
        }
    }

    public static class b {
        public static final byte[] f = new byte[1792];
        public final CharSequence a;
        public final boolean b;
        public final int c;
        public int d;
        public char e;

        static {
            for (int i3 = 0; i3 < 1792; ++i3) {
                m0.a$b.f[i3] = Character.getDirectionality(i3);
            }
        }

        public b(CharSequence charSequence, boolean bl) {
            this.a = charSequence;
            this.b = bl;
            this.c = charSequence.length();
        }

        public static byte c(char c3) {
            if (c3 < '\u0700') {
                return f[c3];
            }
            return Character.getDirectionality(c3);
        }

        public byte a() {
            byte by;
            char c3;
            this.e = c3 = this.a.charAt(this.d - 1);
            if (Character.isLowSurrogate(c3)) {
                int n3 = Character.codePointBefore(this.a, this.d);
                this.d -= Character.charCount(n3);
                return Character.getDirectionality(n3);
            }
            --this.d;
            byte by2 = by = m0.a$b.c(this.e);
            if (this.b) {
                char c4 = this.e;
                if (c4 == '>') {
                    return this.h();
                }
                by2 = by;
                if (c4 == ';') {
                    by2 = this.f();
                }
            }
            return by2;
        }

        public byte b() {
            byte by;
            char c3;
            this.e = c3 = this.a.charAt(this.d);
            if (Character.isHighSurrogate(c3)) {
                int n3 = Character.codePointAt(this.a, this.d);
                this.d += Character.charCount(n3);
                return Character.getDirectionality(n3);
            }
            ++this.d;
            byte by2 = by = m0.a$b.c(this.e);
            if (this.b) {
                char c4 = this.e;
                if (c4 == '<') {
                    return this.i();
                }
                by2 = by;
                if (c4 == '&') {
                    by2 = this.g();
                }
            }
            return by2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int d() {
            int n3;
            this.d = 0;
            int n4 = 0;
            int n5 = n3 = 0;
            while (this.d < this.c && n4 == 0) {
                block19: {
                    block17: {
                        block18: {
                            byte by = this.b();
                            if (by == 0) break block17;
                            if (by == 1 || by == 2) break block18;
                            if (by == 9) continue;
                            switch (by) {
                                default: {
                                    break block19;
                                }
                                case 18: {
                                    --n5;
                                    n3 = 0;
                                    break;
                                }
                                case 16: 
                                case 17: {
                                    ++n5;
                                    n3 = 1;
                                    break;
                                }
                                case 14: 
                                case 15: {
                                    ++n5;
                                    n3 = -1;
                                    break;
                                }
                            }
                            continue;
                        }
                        if (n5 == 0) {
                            return 1;
                        }
                        break block19;
                    }
                    if (n5 == 0) {
                        return -1;
                    }
                }
                n4 = n5;
            }
            if (n4 == 0) {
                return 0;
            }
            if (n3 != 0) {
                return n3;
            }
            block11: while (this.d > 0) {
                switch (this.a()) {
                    default: {
                        continue block11;
                    }
                    case 18: {
                        ++n5;
                        continue block11;
                    }
                    case 16: 
                    case 17: {
                        if (n4 != n5) break;
                        return 1;
                    }
                    case 14: 
                    case 15: {
                        if (n4 != n5) break;
                        return -1;
                    }
                }
                --n5;
            }
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int e() {
            this.d = this.c;
            int n3 = 0;
            while (true) {
                int n4;
                int n5;
                n3 = n5 = n3;
                block6: while (true) {
                    n4 = n3;
                    if (this.d <= 0) {
                        return 0;
                    }
                    byte by = this.a();
                    if (by != 0) {
                        if (by != 1 && by != 2) {
                            n3 = n4;
                            if (by == 9) continue;
                            switch (by) {
                                default: {
                                    n3 = n4;
                                    if (n5 != 0) continue block6;
                                    break block6;
                                }
                                case 18: {
                                    n3 = n4 + 1;
                                    continue block6;
                                }
                                case 16: 
                                case 17: {
                                    if (n5 != n4) break;
                                    return 1;
                                }
                                case 14: 
                                case 15: {
                                    if (n5 != n4) break;
                                    return -1;
                                }
                            }
                            n3 = n4 - 1;
                            continue;
                        }
                        if (n4 == 0) {
                            return 1;
                        }
                        n3 = n4;
                        if (n5 != 0) continue;
                        break;
                    }
                    if (n4 == 0) {
                        return -1;
                    }
                    n3 = n4;
                    if (n5 == 0) break;
                }
                n3 = n4;
            }
        }

        public final byte f() {
            int n3;
            int n4 = this.d;
            while ((n3 = this.d) > 0) {
                char c3;
                CharSequence charSequence = this.a;
                this.d = --n3;
                this.e = c3 = charSequence.charAt(n3);
                if (c3 == '&') {
                    return 12;
                }
                if (c3 != ';') continue;
            }
            this.d = n4;
            this.e = (char)59;
            return 13;
        }

        public final byte g() {
            int n3;
            while ((n3 = this.d) < this.c) {
                char c3;
                CharSequence charSequence = this.a;
                this.d = n3 + 1;
                this.e = c3 = charSequence.charAt(n3);
                if (c3 != ';') continue;
            }
            return 12;
        }

        public final byte h() {
            int n3;
            int n4 = this.d;
            block0: while ((n3 = this.d) > 0) {
                char c3;
                CharSequence charSequence = this.a;
                this.d = --n3;
                this.e = c3 = charSequence.charAt(n3);
                if (c3 == '<') {
                    return 12;
                }
                if (c3 == '>') break;
                if (c3 != '\"' && c3 != '\'') continue;
                while ((n3 = this.d) > 0) {
                    char c4;
                    charSequence = this.a;
                    this.d = --n3;
                    this.e = c4 = charSequence.charAt(n3);
                    if (c4 == c3) continue block0;
                }
            }
            this.d = n4;
            this.e = (char)62;
            return 13;
        }

        public final byte i() {
            int n3;
            int n4 = this.d;
            block0: while ((n3 = this.d) < this.c) {
                char c3;
                CharSequence charSequence = this.a;
                this.d = n3 + 1;
                this.e = c3 = charSequence.charAt(n3);
                if (c3 == '>') {
                    return 12;
                }
                if (c3 != '\"' && c3 != '\'') continue;
                while ((n3 = this.d) < this.c) {
                    char c4;
                    charSequence = this.a;
                    this.d = n3 + 1;
                    this.e = c4 = charSequence.charAt(n3);
                    if (c4 == c3) continue block0;
                }
            }
            this.d = n4;
            this.e = (char)60;
            return 13;
        }
    }
}

