/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.text.SpanWatcher
 *  android.text.Spannable
 *  android.text.SpannableStringBuilder
 *  android.text.TextWatcher
 */
package androidx.emoji2.text;

import android.os.Build;
import android.text.Editable;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import androidx.emoji2.text.j;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import n0.h;

public final class o
extends SpannableStringBuilder {
    public final Class c;
    public final List d = new ArrayList();

    public o(Class clazz, CharSequence charSequence) {
        super(charSequence);
        h.h(clazz, "watcherClass cannot be null");
        this.c = clazz;
    }

    public o(Class clazz, CharSequence charSequence, int n3, int n4) {
        super(charSequence, n3, n4);
        h.h(clazz, "watcherClass cannot be null");
        this.c = clazz;
    }

    public static o c(Class clazz, CharSequence charSequence) {
        return new o(clazz, charSequence);
    }

    public void a() {
        this.b();
    }

    public SpannableStringBuilder append(char c3) {
        super.append(c3);
        return this;
    }

    public SpannableStringBuilder append(CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    public SpannableStringBuilder append(CharSequence charSequence, int n3, int n4) {
        super.append(charSequence, n3, n4);
        return this;
    }

    public SpannableStringBuilder append(CharSequence charSequence, Object object, int n3) {
        super.append(charSequence, object, n3);
        return this;
    }

    public final void b() {
        for (int i3 = 0; i3 < this.d.size(); ++i3) {
            ((a)this.d.get(i3)).a();
        }
    }

    public void d() {
        this.i();
        this.e();
    }

    public SpannableStringBuilder delete(int n3, int n4) {
        super.delete(n3, n4);
        return this;
    }

    public final void e() {
        for (int i3 = 0; i3 < this.d.size(); ++i3) {
            ((a)this.d.get(i3)).onTextChanged((CharSequence)((Object)this), 0, this.length(), this.length());
        }
    }

    public final a f(Object object) {
        for (int i3 = 0; i3 < this.d.size(); ++i3) {
            a a4 = (a)this.d.get(i3);
            if (a4.c != object) continue;
            return a4;
        }
        return null;
    }

    public final boolean g(Class clazz) {
        return this.c == clazz;
    }

    public int getSpanEnd(Object object) {
        Object object2 = object;
        if (this.h(object)) {
            a a4 = this.f(object);
            object2 = object;
            if (a4 != null) {
                object2 = a4;
            }
        }
        return super.getSpanEnd(object2);
    }

    public int getSpanFlags(Object object) {
        Object object2 = object;
        if (this.h(object)) {
            a a4 = this.f(object);
            object2 = object;
            if (a4 != null) {
                object2 = a4;
            }
        }
        return super.getSpanFlags(object2);
    }

    public int getSpanStart(Object object) {
        Object object2 = object;
        if (this.h(object)) {
            a a4 = this.f(object);
            object2 = object;
            if (a4 != null) {
                object2 = a4;
            }
        }
        return super.getSpanStart(object2);
    }

    public Object[] getSpans(int n3, int n4, Class objectArray) {
        if (this.g((Class)objectArray)) {
            a[] aArray = (a[])super.getSpans(n3, n4, a.class);
            objectArray = (Object[])Array.newInstance(objectArray, aArray.length);
            for (n3 = 0; n3 < aArray.length; ++n3) {
                objectArray[n3] = aArray[n3].c;
            }
            return objectArray;
        }
        return super.getSpans(n3, n4, (Class)objectArray);
    }

    public final boolean h(Object object) {
        return object != null && this.g(object.getClass());
    }

    public final void i() {
        for (int i3 = 0; i3 < this.d.size(); ++i3) {
            ((a)this.d.get(i3)).c();
        }
    }

    public SpannableStringBuilder insert(int n3, CharSequence charSequence) {
        super.insert(n3, charSequence);
        return this;
    }

    public SpannableStringBuilder insert(int n3, CharSequence charSequence, int n4, int n5) {
        super.insert(n3, charSequence, n4, n5);
        return this;
    }

    public int nextSpanTransition(int n3, int n4, Class clazz) {
        Class<a> clazz2;
        block3: {
            block2: {
                if (clazz == null) break block2;
                clazz2 = clazz;
                if (!this.g(clazz)) break block3;
            }
            clazz2 = a.class;
        }
        return super.nextSpanTransition(n3, n4, clazz2);
    }

    public void removeSpan(Object object) {
        a a4;
        if (this.h(object)) {
            a a5;
            a4 = a5 = this.f(object);
            if (a5 != null) {
                object = a5;
                a4 = a5;
            }
        } else {
            a4 = null;
        }
        super.removeSpan(object);
        if (a4 != null) {
            this.d.remove(a4);
        }
    }

    public SpannableStringBuilder replace(int n3, int n4, CharSequence charSequence) {
        this.b();
        super.replace(n3, n4, charSequence);
        this.i();
        return this;
    }

    public SpannableStringBuilder replace(int n3, int n4, CharSequence charSequence, int n5, int n6) {
        this.b();
        super.replace(n3, n4, charSequence, n5, n6);
        this.i();
        return this;
    }

    public void setSpan(Object object, int n3, int n4, int n5) {
        Object object2 = object;
        if (this.h(object)) {
            object2 = new a(object);
            this.d.add(object2);
        }
        super.setSpan(object2, n3, n4, n5);
    }

    public CharSequence subSequence(int n3, int n4) {
        return new o(this.c, (CharSequence)((Object)this), n3, n4);
    }

    public static class a
    implements TextWatcher,
    SpanWatcher {
        public final Object c;
        public final AtomicInteger d = new AtomicInteger(0);

        public a(Object object) {
            this.c = object;
        }

        public final void a() {
            this.d.incrementAndGet();
        }

        public void afterTextChanged(Editable editable) {
            ((TextWatcher)this.c).afterTextChanged(editable);
        }

        public final boolean b(Object object) {
            return object instanceof j;
        }

        public void beforeTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
            ((TextWatcher)this.c).beforeTextChanged(charSequence, n3, n4, n5);
        }

        public final void c() {
            this.d.decrementAndGet();
        }

        public void onSpanAdded(Spannable spannable, Object object, int n3, int n4) {
            if (this.d.get() > 0 && this.b(object)) {
                return;
            }
            ((SpanWatcher)this.c).onSpanAdded(spannable, object, n3, n4);
        }

        /*
         * Unable to fully structure code
         */
        public void onSpanChanged(Spannable var1_1, Object var2_2, int var3_3, int var4_4, int var5_5, int var6_6) {
            if (this.d.get() > 0 && this.b(var2_2)) {
                return;
            }
            var8_7 = var3_3;
            if (Build.VERSION.SDK_INT >= 28) ** GOTO lbl-1000
            var7_8 = var3_3;
            if (var3_3 > var4_4) {
                var7_8 = 0;
            }
            var8_7 = var7_8;
            if (var5_5 > var6_6) {
                var3_3 = 0;
                var8_7 = var7_8;
            } else lbl-1000:
            // 2 sources

            {
                var3_3 = var5_5;
            }
            ((SpanWatcher)this.c).onSpanChanged(var1_1, var2_2, var8_7, var4_4, var3_3, var6_6);
        }

        public void onSpanRemoved(Spannable spannable, Object object, int n3, int n4) {
            if (this.d.get() > 0 && this.b(object)) {
                return;
            }
            ((SpanWatcher)this.c).onSpanRemoved(spannable, object, n3, n4);
        }

        public void onTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
            ((TextWatcher)this.c).onTextChanged(charSequence, n3, n4, n5);
        }
    }
}

