/*
 * Decompiled with CFR 0.152.
 */
package g1;

import androidx.lifecycle.b0;
import androidx.lifecycle.k;
import androidx.lifecycle.y;
import androidx.lifecycle.z;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import o.s;

public class b
extends g1.a {
    public final k a;
    public final a b;

    public b(k k3, b0 b02) {
        this.a = k3;
        this.b = g1.b$a.f(b02);
    }

    @Override
    public void a(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        this.b.e(string, fileDescriptor, printWriter, stringArray);
    }

    @Override
    public void c() {
        this.b.g();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("LoaderManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        n0.b.a(this.a, stringBuilder);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    public static class a
    extends y {
        public static final z.b f = new z.b(){

            @Override
            public y a(Class clazz) {
                return new a();
            }
        };
        public s d = new s();
        public boolean e = false;

        public static a f(b0 b02) {
            return (a)new z(b02, f).a(a.class);
        }

        @Override
        public void d() {
            super.d();
            if (this.d.h() <= 0) {
                this.d.b();
                return;
            }
            androidx.appcompat.app.s.a(this.d.i(0));
            throw null;
        }

        public void e(String string, FileDescriptor object, PrintWriter printWriter, String[] stringArray) {
            if (this.d.h() > 0) {
                printWriter.print(string);
                printWriter.println("Loaders:");
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append("    ");
                if (this.d.h() > 0) {
                    androidx.appcompat.app.s.a(this.d.i(0));
                    printWriter.print(string);
                    printWriter.print("  #");
                    printWriter.print(this.d.f(0));
                    printWriter.print(": ");
                    throw null;
                }
            }
        }

        public void g() {
            if (this.d.h() <= 0) {
                return;
            }
            androidx.appcompat.app.s.a(this.d.i(0));
            throw null;
        }
    }
}

