/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ClipData
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.ContentInfo
 *  android.view.ContentInfo$Builder
 */
package o0;

import android.content.ClipData;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContentInfo;
import java.util.Objects;
import o0.h;
import o0.i;
import o0.j;
import o0.k;
import o0.l;

public final class d {
    public final f a;

    public d(f f3) {
        this.a = f3;
    }

    public static String a(int n3) {
        if ((n3 & 1) != 0) {
            return "FLAG_CONVERT_TO_PLAIN_TEXT";
        }
        return String.valueOf(n3);
    }

    public static String e(int n3) {
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) {
                            if (n3 != 5) {
                                return String.valueOf(n3);
                            }
                            return "SOURCE_PROCESS_TEXT";
                        }
                        return "SOURCE_AUTOFILL";
                    }
                    return "SOURCE_DRAG_AND_DROP";
                }
                return "SOURCE_INPUT_METHOD";
            }
            return "SOURCE_CLIPBOARD";
        }
        return "SOURCE_APP";
    }

    public static d g(ContentInfo contentInfo) {
        return new d(new e(contentInfo));
    }

    public ClipData b() {
        return this.a.a();
    }

    public int c() {
        return this.a.b();
    }

    public int d() {
        return this.a.d();
    }

    public ContentInfo f() {
        ContentInfo contentInfo = this.a.c();
        Objects.requireNonNull(contentInfo);
        return o0.c.a(contentInfo);
    }

    public String toString() {
        return this.a.toString();
    }

    public static final class a {
        public final c a;

        public a(ClipData clipData, int n3) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.a = new b(clipData, n3);
                return;
            }
            this.a = new d(clipData, n3);
        }

        public d a() {
            return this.a.build();
        }

        public a b(Bundle bundle) {
            this.a.setExtras(bundle);
            return this;
        }

        public a c(int n3) {
            this.a.b(n3);
            return this;
        }

        public a d(Uri uri) {
            this.a.a(uri);
            return this;
        }
    }

    public static final class b
    implements c {
        public final ContentInfo.Builder a;

        public b(ClipData clipData, int n3) {
            this.a = i.a(clipData, n3);
        }

        @Override
        public void a(Uri uri) {
            o0.g.a(this.a, uri);
        }

        @Override
        public void b(int n3) {
            o0.f.a(this.a, n3);
        }

        @Override
        public d build() {
            return new d(new e(o0.e.a(this.a)));
        }

        @Override
        public void setExtras(Bundle bundle) {
            h.a(this.a, bundle);
        }
    }

    public static interface c {
        public void a(Uri var1);

        public void b(int var1);

        public d build();

        public void setExtras(Bundle var1);
    }

    public static final class d
    implements c {
        public ClipData a;
        public int b;
        public int c;
        public Uri d;
        public Bundle e;

        public d(ClipData clipData, int n3) {
            this.a = clipData;
            this.b = n3;
        }

        @Override
        public void a(Uri uri) {
            this.d = uri;
        }

        @Override
        public void b(int n3) {
            this.c = n3;
        }

        @Override
        public d build() {
            return new d(new g(this));
        }

        @Override
        public void setExtras(Bundle bundle) {
            this.e = bundle;
        }
    }

    public static final class e
    implements f {
        public final ContentInfo a;

        public e(ContentInfo contentInfo) {
            this.a = o0.c.a(n0.h.g(contentInfo));
        }

        @Override
        public ClipData a() {
            return j.a(this.a);
        }

        @Override
        public int b() {
            return k.a(this.a);
        }

        @Override
        public ContentInfo c() {
            return this.a;
        }

        @Override
        public int d() {
            return l.a(this.a);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ContentInfoCompat{");
            stringBuilder.append(this.a);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    public static interface f {
        public ClipData a();

        public int b();

        public ContentInfo c();

        public int d();
    }

    public static final class g
    implements f {
        public final ClipData a;
        public final int b;
        public final int c;
        public final Uri d;
        public final Bundle e;

        public g(d d3) {
            this.a = (ClipData)n0.h.g(d3.a);
            this.b = n0.h.c(d3.b, 0, 5, "source");
            this.c = n0.h.f(d3.c, 1);
            this.d = d3.d;
            this.e = d3.e;
        }

        @Override
        public ClipData a() {
            return this.a;
        }

        @Override
        public int b() {
            return this.c;
        }

        @Override
        public ContentInfo c() {
            return null;
        }

        @Override
        public int d() {
            return this.b;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ContentInfoCompat{clip=");
            stringBuilder.append(this.a.getDescription());
            stringBuilder.append(", source=");
            stringBuilder.append(o0.d.e(this.b));
            stringBuilder.append(", flags=");
            stringBuilder.append(o0.d.a(this.c));
            Object object = this.d;
            String string = "";
            if (object == null) {
                object = "";
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(", hasLinkUri(");
                ((StringBuilder)object).append(this.d.toString().length());
                ((StringBuilder)object).append(")");
                object = ((StringBuilder)object).toString();
            }
            stringBuilder.append((String)object);
            object = this.e == null ? string : ", hasExtras";
            stringBuilder.append((String)object);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }
}

