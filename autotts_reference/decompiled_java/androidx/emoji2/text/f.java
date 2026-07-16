/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.text.Editable
 *  android.view.KeyEvent
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 */
package androidx.emoji2.text;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.emoji2.text.n;
import androidx.emoji2.text.p;
import androidx.emoji2.text.q;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class f {
    public static final Object o = new Object();
    public static final Object p = new Object();
    public static volatile f q;
    public final ReadWriteLock a = new ReentrantReadWriteLock();
    public final Set b;
    public volatile int c = 3;
    public final Handler d;
    public final b e;
    public final h f;
    public final j g;
    public final boolean h;
    public final boolean i;
    public final int[] j;
    public final boolean k;
    public final int l;
    public final int m;
    public final e n;

    public f(c c3) {
        this.h = c3.c;
        this.i = c3.d;
        this.j = c3.e;
        this.k = c3.g;
        this.l = c3.h;
        this.f = c3.a;
        this.m = c3.i;
        this.n = c3.j;
        this.d = new Handler(Looper.getMainLooper());
        o.b b3 = new o.b();
        this.b = b3;
        Object object = c3.b;
        if (object == null) {
            object = new d();
        }
        this.g = object;
        object = c3.f;
        if (object != null && !object.isEmpty()) {
            b3.addAll(c3.f);
        }
        this.e = new a(this);
        this.m();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static f c() {
        Object object = o;
        synchronized (object) {
            f f3 = q;
            boolean bl = f3 != null;
            n0.h.i(bl, "EmojiCompat is not initialized.\n\nYou must initialize EmojiCompat prior to referencing the EmojiCompat instance.\n\nThe most likely cause of this error is disabling the EmojiCompatInitializer\neither explicitly in AndroidManifest.xml, or by including\nandroidx.emoji2:emoji2-bundled.\n\nAutomatic initialization is typically performed by EmojiCompatInitializer. If\nyou are not expecting to initialize EmojiCompat manually in your application,\nplease check to ensure it has not been removed from your APK's manifest. You can\ndo this in Android Studio using Build > Analyze APK.\n\nIn the APK Analyzer, ensure that the startup entry for\nEmojiCompatInitializer and InitializationProvider is present in\n AndroidManifest.xml. If it is missing or contains tools:node=\"remove\", and you\nintend to use automatic configuration, verify:\n\n  1. Your application does not include emoji2-bundled\n  2. All modules do not contain an exclusion manifest rule for\n     EmojiCompatInitializer or InitializationProvider. For more information\n     about manifest exclusions see the documentation for the androidx startup\n     library.\n\nIf you intend to use emoji2-bundled, please call EmojiCompat.init. You can\nlearn more in the documentation for BundledEmojiCompatConfig.\n\nIf you intended to perform manual configuration, it is recommended that you call\nEmojiCompat.init immediately on application startup.\n\nIf you still cannot resolve this issue, please open a bug with your specific\nconfiguration to help improve error message.");
            return f3;
        }
    }

    public static boolean f(InputConnection inputConnection, Editable editable, int n3, int n4, boolean bl) {
        return androidx.emoji2.text.i.b(inputConnection, editable, n3, n4, bl);
    }

    public static boolean g(Editable editable, int n3, KeyEvent keyEvent) {
        return androidx.emoji2.text.i.c(editable, n3, keyEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static f h(c c3) {
        f f3 = q;
        if (f3 != null) {
            return f3;
        }
        Object object = o;
        synchronized (object) {
            Throwable throwable2;
            block5: {
                block4: {
                    try {
                        f f4;
                        f3 = f4 = q;
                        if (f4 != null) break block4;
                        q = f3 = new f(c3);
                    }
                    catch (Throwable throwable2) {
                        break block5;
                    }
                }
                return f3;
            }
            throw throwable2;
        }
    }

    public static boolean i() {
        return q != null;
    }

    public int d() {
        return this.l;
    }

    public int e() {
        this.a.readLock().lock();
        try {
            int n3 = this.c;
            return n3;
        }
        finally {
            this.a.readLock().unlock();
        }
    }

    public boolean j() {
        return this.k;
    }

    public final boolean k() {
        return this.e() == 1;
    }

    public void l() {
        block6: {
            int n3 = this.m;
            boolean bl = true;
            if (n3 != 1) {
                bl = false;
            }
            n0.h.i(bl, "Set metadataLoadStrategy to LOAD_STRATEGY_MANUAL to execute manual loading");
            if (this.k()) {
                return;
            }
            this.a.writeLock().lock();
            n3 = this.c;
            if (n3 != 0) break block6;
            this.a.writeLock().unlock();
            return;
        }
        try {
            this.c = 0;
            this.e.a();
            return;
        }
        finally {
            this.a.writeLock().unlock();
        }
    }

    public final void m() {
        Throwable throwable2;
        block4: {
            block3: {
                this.a.writeLock().lock();
                try {
                    if (this.m != 0) break block3;
                    this.c = 0;
                }
                catch (Throwable throwable2) {
                    break block4;
                }
            }
            this.a.writeLock().unlock();
            if (this.e() == 0) {
                this.e.a();
            }
            return;
        }
        this.a.writeLock().unlock();
        throw throwable2;
    }

    public void n(Throwable throwable) {
        ArrayList arrayList = new ArrayList();
        this.a.writeLock().lock();
        this.c = 2;
        arrayList.addAll(this.b);
        this.b.clear();
        this.d.post((Runnable)new g(arrayList, this.c, throwable));
        return;
        finally {
            this.a.writeLock().unlock();
        }
    }

    public void o() {
        ArrayList arrayList = new ArrayList();
        this.a.writeLock().lock();
        this.c = 1;
        arrayList.addAll(this.b);
        this.b.clear();
        this.d.post((Runnable)new g(arrayList, this.c));
        return;
        finally {
            this.a.writeLock().unlock();
        }
    }

    public CharSequence p(CharSequence charSequence) {
        int n3 = charSequence == null ? 0 : charSequence.length();
        return this.q(charSequence, 0, n3);
    }

    public CharSequence q(CharSequence charSequence, int n3, int n4) {
        return this.r(charSequence, n3, n4, Integer.MAX_VALUE);
    }

    public CharSequence r(CharSequence charSequence, int n3, int n4, int n5) {
        return this.s(charSequence, n3, n4, n5, 0);
    }

    public CharSequence s(CharSequence charSequence, int n3, int n4, int n5, int n6) {
        n0.h.i(this.k(), "Not initialized yet");
        n0.h.e(n3, "start cannot be negative");
        n0.h.e(n4, "end cannot be negative");
        n0.h.e(n5, "maxEmojiCount cannot be negative");
        boolean bl = false;
        boolean bl2 = n3 <= n4;
        n0.h.b(bl2, "start should be <= than end");
        if (charSequence == null) {
            return null;
        }
        bl2 = n3 <= charSequence.length();
        n0.h.b(bl2, "start should be < than charSequence length");
        bl2 = n4 <= charSequence.length();
        n0.h.b(bl2, "end should be < than charSequence length");
        if (charSequence.length() != 0 && n3 != n4) {
            if (n6 != 1) {
                bl2 = bl;
                if (n6 != 2) {
                    bl2 = this.h;
                }
            } else {
                bl2 = true;
            }
            return this.e.b(charSequence, n3, n4, n5, bl2);
        }
        return charSequence;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void t(f f3) {
        Throwable throwable2;
        block4: {
            block3: {
                block2: {
                    n0.h.h(f3, "initCallback cannot be null");
                    this.a.writeLock().lock();
                    try {
                        if (this.c == 1 || this.c == 2) break block2;
                        this.b.add(f3);
                        break block3;
                    }
                    catch (Throwable throwable2) {
                        break block4;
                    }
                }
                Handler handler = this.d;
                g g3 = new g(f3, this.c);
                handler.post((Runnable)g3);
            }
            this.a.writeLock().unlock();
            return;
        }
        this.a.writeLock().unlock();
        throw throwable2;
    }

    public void u(f f3) {
        n0.h.h(f3, "initCallback cannot be null");
        this.a.writeLock().lock();
        try {
            this.b.remove(f3);
            return;
        }
        finally {
            this.a.writeLock().unlock();
        }
    }

    public void v(EditorInfo editorInfo) {
        if (this.k() && editorInfo != null) {
            if (editorInfo.extras == null) {
                editorInfo.extras = new Bundle();
            }
            this.e.c(editorInfo);
        }
    }

    public static final class a
    extends b {
        public volatile androidx.emoji2.text.i b;
        public volatile n c;

        public a(f f3) {
            super(f3);
        }

        @Override
        public void a() {
            try {
                i i3 = new i(this){
                    public final a a;
                    {
                        this.a = a4;
                    }

                    @Override
                    public void a(Throwable throwable) {
                        this.a.a.n(throwable);
                    }

                    @Override
                    public void b(n n3) {
                        this.a.d(n3);
                    }
                };
                this.a.f.a(i3);
                return;
            }
            catch (Throwable throwable) {
                this.a.n(throwable);
                return;
            }
        }

        @Override
        public CharSequence b(CharSequence charSequence, int n3, int n4, int n5, boolean bl) {
            return this.b.h(charSequence, n3, n4, n5, bl);
        }

        @Override
        public void c(EditorInfo editorInfo) {
            editorInfo.extras.putInt("android.support.text.emoji.emojiCompat_metadataVersion", this.c.e());
            editorInfo.extras.putBoolean("android.support.text.emoji.emojiCompat_replaceAll", this.a.h);
        }

        public void d(n object) {
            if (object == null) {
                this.a.n(new IllegalArgumentException("metadataRepo cannot be null"));
                return;
            }
            n n3 = this.c = object;
            j j3 = this.a.g;
            object = this.a.n;
            f f3 = this.a;
            this.b = new androidx.emoji2.text.i(n3, j3, (e)object, f3.i, f3.j, androidx.emoji2.text.h.a());
            this.a.o();
        }
    }

    public static abstract class b {
        public final f a;

        public b(f f3) {
            this.a = f3;
        }

        public abstract void a();

        public abstract CharSequence b(CharSequence var1, int var2, int var3, int var4, boolean var5);

        public abstract void c(EditorInfo var1);
    }

    public static abstract class c {
        public final h a;
        public j b;
        public boolean c;
        public boolean d;
        public int[] e;
        public Set f;
        public boolean g;
        public int h = -16711936;
        public int i = 0;
        public e j = new androidx.emoji2.text.e();

        public c(h h3) {
            n0.h.h(h3, "metadataLoader cannot be null.");
            this.a = h3;
        }

        public final h a() {
            return this.a;
        }

        public c b(int n3) {
            this.i = n3;
            return this;
        }
    }

    public static class d
    implements j {
        @Override
        public androidx.emoji2.text.j a(p p3) {
            return new q(p3);
        }
    }

    public static interface e {
        public boolean a(CharSequence var1, int var2, int var3, int var4);
    }

    public static abstract class f {
        public void a(Throwable throwable) {
        }

        public void b() {
        }
    }

    public static class g
    implements Runnable {
        public final List c;
        public final Throwable d;
        public final int e;

        public g(f f3, int n3) {
            this(Arrays.asList((f)n0.h.h(f3, "initCallback cannot be null")), n3, null);
        }

        public g(Collection collection, int n3) {
            this(collection, n3, null);
        }

        public g(Collection collection, int n3, Throwable throwable) {
            n0.h.h(collection, "initCallbacks cannot be null");
            this.c = new ArrayList(collection);
            this.e = n3;
            this.d = throwable;
        }

        @Override
        public void run() {
            int n3;
            int n4 = this.c.size();
            int n5 = this.e;
            int n6 = 0;
            if (n5 != 1) {
                for (n3 = n6; n3 < n4; ++n3) {
                    ((f)this.c.get(n3)).a(this.d);
                }
            } else {
                for (n3 = 0; n3 < n4; ++n3) {
                    ((f)this.c.get(n3)).b();
                }
            }
        }
    }

    public static interface h {
        public void a(i var1);
    }

    public static abstract class i {
        public abstract void a(Throwable var1);

        public abstract void b(n var1);
    }

    public static interface j {
        public androidx.emoji2.text.j a(p var1);
    }
}

