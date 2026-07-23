/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.text.Selection
 *  android.text.Spannable
 *  android.text.SpannableString
 *  android.text.Spanned
 *  android.text.TextUtils
 *  android.text.method.MetaKeyKeyListener
 *  android.view.KeyEvent
 *  android.view.inputmethod.InputConnection
 */
package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import androidx.emoji2.text.f;
import androidx.emoji2.text.j;
import androidx.emoji2.text.n;
import androidx.emoji2.text.o;
import androidx.emoji2.text.p;
import androidx.emoji2.text.r;
import java.util.Arrays;
import java.util.Set;

public final class i {
    public final f.j a;
    public final n b;
    public f.e c;
    public final boolean d;
    public final int[] e;

    public i(n n3, f.j j3, f.e e3, boolean bl, int[] nArray, Set set) {
        this.a = j3;
        this.b = n3;
        this.c = e3;
        this.d = bl;
        this.e = nArray;
        this.g(set);
    }

    /*
     * WARNING - void declaration
     */
    public static boolean a(Editable editable, KeyEvent object2, boolean bl) {
        int n3;
        if (i.f((KeyEvent)object2)) {
            return false;
        }
        int n4 = Selection.getSelectionStart((CharSequence)editable);
        if (i.e(n4, n3 = Selection.getSelectionEnd((CharSequence)editable))) {
            return false;
        }
        j[] jArray = (j[])editable.getSpans(n4, n3, j.class);
        if (jArray != null && jArray.length > 0) {
            for (j j3 : jArray) {
                void var2_4;
                int n5 = editable.getSpanStart((Object)j3);
                int n6 = editable.getSpanEnd((Object)j3);
                if (!(var2_4 != false && n5 == n4 || var2_4 == false && n6 == n4) && (n4 <= n5 || n4 >= n6)) continue;
                editable.delete(n5, n6);
                return true;
            }
        }
        return false;
    }

    public static boolean b(InputConnection inputConnection, Editable editable, int n3, int n4, boolean bl) {
        block5: {
            j[] jArray;
            block8: {
                int n5;
                int n6;
                block6: {
                    block7: {
                        if (editable == null || inputConnection == null || n3 < 0 || n4 < 0) break block5;
                        n6 = Selection.getSelectionStart((CharSequence)editable);
                        if (i.e(n6, n5 = Selection.getSelectionEnd((CharSequence)editable))) {
                            return false;
                        }
                        if (!bl) break block6;
                        n3 = androidx.emoji2.text.i$a.a((CharSequence)editable, n6, Math.max(n3, 0));
                        n5 = androidx.emoji2.text.i$a.b((CharSequence)editable, n5, Math.max(n4, 0));
                        if (n3 == -1) break block7;
                        n4 = n5;
                        if (n5 != -1) break block8;
                    }
                    return false;
                }
                n3 = Math.max(n6 - n3, 0);
                n4 = Math.min(n5 + n4, editable.length());
            }
            if ((jArray = (j[])editable.getSpans(n3, n4, j.class)) != null && jArray.length > 0) {
                for (j j3 : jArray) {
                    int n7 = editable.getSpanStart((Object)j3);
                    int n8 = editable.getSpanEnd((Object)j3);
                    n3 = Math.min(n7, n3);
                    n4 = Math.max(n8, n4);
                }
                n3 = Math.max(n3, 0);
                n4 = Math.min(n4, editable.length());
                inputConnection.beginBatchEdit();
                editable.delete(n3, n4);
                inputConnection.endBatchEdit();
                return true;
            }
        }
        return false;
    }

    public static boolean c(Editable editable, int n3, KeyEvent keyEvent) {
        boolean bl = n3 != 67 ? (n3 != 112 ? false : i.a(editable, keyEvent, true)) : i.a(editable, keyEvent, false);
        if (bl) {
            MetaKeyKeyListener.adjustMetaAfterKeypress((Spannable)editable);
            return true;
        }
        return false;
    }

    public static boolean e(int n3, int n4) {
        return n3 == -1 || n4 == -1 || n3 != n4;
        {
        }
    }

    public static boolean f(KeyEvent keyEvent) {
        return KeyEvent.metaStateHasNoModifiers((int)keyEvent.getMetaState()) ^ true;
    }

    public final boolean d(CharSequence charSequence, int n3, int n4, p p3) {
        if (p3.d() == 0) {
            p3.m(this.c.a(charSequence, n3, n4, p3.h()));
        }
        return p3.d() == 2;
    }

    public final void g(Set object) {
        if (!object.isEmpty()) {
            object = object.iterator();
            while (object.hasNext()) {
                Object object2 = (int[])object.next();
                String string = new String((int[])object2, 0, ((int[])object2).length);
                object2 = new d(string);
                this.i(string, 0, string.length(), 1, true, (c)object2);
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CharSequence h(CharSequence charSequence, int n3, int n4, int n5, boolean bl) {
        void var11_10;
        boolean bl2;
        block22: {
            block25: {
                Object object;
                block26: {
                    block23: {
                        Object object2;
                        int n6;
                        int n7;
                        block24: {
                            block21: {
                                int n8;
                                int n9;
                                j[] jArray;
                                block20: {
                                    block19: {
                                        bl2 = charSequence instanceof o;
                                        if (bl2) {
                                            ((o)((Object)charSequence)).a();
                                        }
                                        if (!bl2) {
                                            if (charSequence instanceof Spannable) break block19;
                                            object = charSequence instanceof Spanned && ((Spanned)charSequence).nextSpanTransition(n3 - 1, n4 + 1, j.class) <= n4 ? new r(charSequence) : null;
                                            break block20;
                                        }
                                    }
                                    object = new r((Spannable)charSequence);
                                }
                                n7 = n3;
                                n6 = n4;
                                if (object == null) break block21;
                                try {
                                    jArray = (j[])((r)object).getSpans(n3, n4, j.class);
                                    n7 = n3;
                                    n6 = n4;
                                    if (jArray == null) break block21;
                                    n7 = n3;
                                    n6 = n4;
                                    if (jArray.length <= 0) break block21;
                                    n9 = jArray.length;
                                    n8 = 0;
                                }
                                catch (Throwable throwable) {
                                    break block22;
                                }
                                while (true) {
                                    n7 = n3;
                                    n6 = n4;
                                    if (n8 >= n9) break;
                                    object2 = jArray[n8];
                                    {
                                        n7 = ((r)object).getSpanStart(object2);
                                        n6 = ((r)object).getSpanEnd(object2);
                                        if (n7 != n4) {
                                            ((r)object).removeSpan(object2);
                                        }
                                        n3 = Math.min(n7, n3);
                                        n4 = Math.max(n6, n4);
                                        ++n8;
                                        continue;
                                    }
                                    break;
                                }
                            }
                            if (n7 == n6) break block23;
                            try {
                                n3 = charSequence.length();
                                if (n7 >= n3) break block23;
                                n3 = n5;
                                if (n5 == Integer.MAX_VALUE) break block24;
                                n3 = n5;
                                if (object == null) break block24;
                            }
                            catch (Throwable throwable) {
                                break block22;
                            }
                            {
                                n3 = ((j[])((r)object).getSpans(0, ((r)object).length(), j.class)).length;
                                n3 = n5 - n3;
                            }
                        }
                        object2 = new b((r)object, this.a);
                        try {
                            object = (r)this.i(charSequence, n7, n6, n3, bl, (c)object2);
                            if (object != null) {
                                object = ((r)object).b();
                                if (bl2) {
                                    ((o)((Object)charSequence)).d();
                                }
                                return object;
                            }
                        }
                        catch (Throwable throwable) {
                            break block22;
                        }
                        if (!bl2) break block25;
                        object = (o)((Object)charSequence);
                        break block26;
                    }
                    if (!bl2) {
                        return charSequence;
                    }
                    object = (o)((Object)charSequence);
                }
                ((o)((Object)object)).d();
            }
            return charSequence;
        }
        if (bl2) {
            ((o)((Object)charSequence)).d();
        }
        throw var11_10;
    }

    public final Object i(CharSequence charSequence, int n3, int n4, int n5, boolean bl, c c3) {
        int n6;
        int n7;
        e e3 = new e(this.b.f(), this.d, this.e);
        int n8 = Character.codePointAt(charSequence, n3);
        int n9 = 0;
        boolean bl2 = true;
        int n10 = n3;
        block0: while (true) {
            n7 = n10;
            n6 = n10;
            n3 = n8;
            while (n6 < n4 && n9 < n5 && bl2) {
                n10 = e3.a(n3);
                if (n10 != 1) {
                    if (n10 != 2) {
                        if (n10 != 3) continue;
                        if (!bl) {
                            n8 = n3;
                            n10 = n6;
                            if (this.d(charSequence, n7, n6, e3.c())) continue block0;
                        }
                        bl2 = c3.b(charSequence, n7, n6, e3.c());
                        ++n9;
                        n8 = n3;
                        n10 = n6;
                        continue block0;
                    }
                    n6 = n10 = n6 + Character.charCount(n3);
                    if (n10 >= n4) continue;
                    n3 = Character.codePointAt(charSequence, n10);
                    n6 = n10;
                    continue;
                }
                if ((n7 += Character.charCount(Character.codePointAt(charSequence, n7))) < n4) {
                    n3 = Character.codePointAt(charSequence, n7);
                }
                n6 = n7;
            }
            break;
        }
        if (e3.e() && n9 < n5 && bl2 && (bl || !this.d(charSequence, n7, n6, e3.b()))) {
            c3.b(charSequence, n7, n6, e3.b());
        }
        return c3.a();
    }

    public static final abstract class a {
        public static int a(CharSequence charSequence, int n3, int n4) {
            int n5 = charSequence.length();
            if (n3 >= 0 && n5 >= n3) {
                if (n4 < 0) {
                    return -1;
                }
                block0: while (true) {
                    n5 = 0;
                    while (true) {
                        if (n4 == 0) {
                            return n3;
                        }
                        if (--n3 < 0) {
                            if (n5 != 0) {
                                return -1;
                            }
                            return 0;
                        }
                        char c3 = charSequence.charAt(n3);
                        if (n5 != 0) {
                            if (!Character.isHighSurrogate(c3)) {
                                return -1;
                            }
                            --n4;
                            continue block0;
                        }
                        if (!Character.isSurrogate(c3)) {
                            --n4;
                            continue;
                        }
                        if (Character.isHighSurrogate(c3)) {
                            return -1;
                        }
                        n5 = 1;
                    }
                    break;
                }
            }
            return -1;
        }

        public static int b(CharSequence charSequence, int n3, int n4) {
            int n5 = charSequence.length();
            if (n3 >= 0 && n5 >= n3) {
                if (n4 < 0) {
                    return -1;
                }
                block0: while (true) {
                    boolean bl = false;
                    while (true) {
                        if (n4 == 0) {
                            return n3;
                        }
                        if (n3 >= n5) {
                            if (bl) {
                                return -1;
                            }
                            return n5;
                        }
                        char c3 = charSequence.charAt(n3);
                        if (bl) {
                            if (!Character.isLowSurrogate(c3)) {
                                return -1;
                            }
                            --n4;
                            ++n3;
                            continue block0;
                        }
                        if (!Character.isSurrogate(c3)) {
                            --n4;
                            ++n3;
                            continue;
                        }
                        if (Character.isLowSurrogate(c3)) {
                            return -1;
                        }
                        ++n3;
                        bl = true;
                    }
                    break;
                }
            }
            return -1;
        }
    }

    public static class b
    implements c {
        public r a;
        public final f.j b;

        public b(r r3, f.j j3) {
            this.a = r3;
            this.b = j3;
        }

        @Override
        public boolean b(CharSequence object, int n3, int n4, p p3) {
            if (p3.k()) {
                return true;
            }
            if (this.a == null) {
                object = object instanceof Spannable ? (Spannable)object : new SpannableString(object);
                this.a = new r((Spannable)object);
            }
            object = this.b.a(p3);
            this.a.setSpan(object, n3, n4, 33);
            return true;
        }

        public r c() {
            return this.a;
        }
    }

    public static interface c {
        public Object a();

        public boolean b(CharSequence var1, int var2, int var3, p var4);
    }

    public static class d
    implements c {
        public final String a;

        public d(String string) {
            this.a = string;
        }

        @Override
        public boolean b(CharSequence charSequence, int n3, int n4, p p3) {
            if (TextUtils.equals((CharSequence)charSequence.subSequence(n3, n4), (CharSequence)this.a)) {
                p3.l(true);
                return false;
            }
            return true;
        }

        public d c() {
            return this;
        }
    }

    public static final class e {
        public int a = 1;
        public final n.a b;
        public n.a c;
        public n.a d;
        public int e;
        public int f;
        public final boolean g;
        public final int[] h;

        public e(n.a a4, boolean bl, int[] nArray) {
            this.b = a4;
            this.c = a4;
            this.g = bl;
            this.h = nArray;
        }

        public static boolean d(int n3) {
            return n3 == 65039;
        }

        public static boolean f(int n3) {
            return n3 == 65038;
        }

        public int a(int n3) {
            n.a a4 = this.c.a(n3);
            int n4 = this.a;
            int n5 = 2;
            if (n4 != 2) {
                if (a4 == null) {
                    n5 = this.g();
                } else {
                    this.a = 2;
                    this.c = a4;
                    this.f = 1;
                }
            } else if (a4 != null) {
                this.c = a4;
                ++this.f;
            } else if (androidx.emoji2.text.i$e.f(n3)) {
                n5 = this.g();
            } else if (!androidx.emoji2.text.i$e.d(n3)) {
                if (this.c.b() != null) {
                    n4 = this.f;
                    n5 = 3;
                    if (n4 == 1) {
                        if (this.h()) {
                            this.d = this.c;
                            this.g();
                        } else {
                            n5 = this.g();
                        }
                    } else {
                        this.d = this.c;
                        this.g();
                    }
                } else {
                    n5 = this.g();
                }
            }
            this.e = n3;
            return n5;
        }

        public p b() {
            return this.c.b();
        }

        public p c() {
            return this.d.b();
        }

        public boolean e() {
            return this.a == 2 && this.c.b() != null && (this.f > 1 || this.h());
        }

        public final int g() {
            this.a = 1;
            this.c = this.b;
            this.f = 0;
            return 1;
        }

        public final boolean h() {
            if (this.c.b().j()) {
                return true;
            }
            if (androidx.emoji2.text.i$e.d(this.e)) {
                return true;
            }
            if (this.g) {
                if (this.h == null) {
                    return true;
                }
                int n3 = this.c.b().b(0);
                if (Arrays.binarySearch(this.h, n3) < 0) {
                    return true;
                }
            }
            return false;
        }
    }
}

