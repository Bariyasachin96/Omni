/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.InputFilter
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.SparseArray
 *  android.widget.TextView
 */
package z0;

import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.SparseArray;
import android.widget.TextView;
import z0.d;
import z0.h;

public final class f {
    public final b a;

    public f(TextView textView, boolean bl) {
        n0.h.h(textView, "textView cannot be null");
        if (!bl) {
            this.a = new c(textView);
            return;
        }
        this.a = new a(textView);
    }

    public InputFilter[] a(InputFilter[] inputFilterArray) {
        return this.a.a(inputFilterArray);
    }

    public boolean b() {
        return this.a.b();
    }

    public void c(boolean bl) {
        this.a.c(bl);
    }

    public void d(boolean bl) {
        this.a.d(bl);
    }

    public TransformationMethod e(TransformationMethod transformationMethod) {
        return this.a.e(transformationMethod);
    }

    public static class a
    extends b {
        public final TextView a;
        public final d b;
        public boolean c;

        public a(TextView textView) {
            this.a = textView;
            this.c = true;
            this.b = new d(textView);
        }

        @Override
        public InputFilter[] a(InputFilter[] inputFilterArray) {
            if (!this.c) {
                return this.h(inputFilterArray);
            }
            return this.f(inputFilterArray);
        }

        @Override
        public boolean b() {
            return this.c;
        }

        @Override
        public void c(boolean bl) {
            if (bl) {
                this.l();
            }
        }

        @Override
        public void d(boolean bl) {
            this.c = bl;
            this.l();
            this.k();
        }

        @Override
        public TransformationMethod e(TransformationMethod transformationMethod) {
            if (this.c) {
                return this.m(transformationMethod);
            }
            return this.j(transformationMethod);
        }

        public final InputFilter[] f(InputFilter[] inputFilterArray) {
            int n3 = inputFilterArray.length;
            for (int i3 = 0; i3 < n3; ++i3) {
                if (inputFilterArray[i3] != this.b) continue;
                return inputFilterArray;
            }
            InputFilter[] inputFilterArray2 = new InputFilter[inputFilterArray.length + 1];
            System.arraycopy(inputFilterArray, 0, inputFilterArray2, 0, n3);
            inputFilterArray2[n3] = this.b;
            return inputFilterArray2;
        }

        public final SparseArray g(InputFilter[] inputFilterArray) {
            SparseArray sparseArray = new SparseArray(1);
            for (int i3 = 0; i3 < inputFilterArray.length; ++i3) {
                InputFilter inputFilter = inputFilterArray[i3];
                if (!(inputFilter instanceof d)) continue;
                sparseArray.put(i3, (Object)inputFilter);
            }
            return sparseArray;
        }

        public final InputFilter[] h(InputFilter[] inputFilterArray) {
            SparseArray sparseArray = this.g(inputFilterArray);
            if (sparseArray.size() == 0) {
                return inputFilterArray;
            }
            int n3 = inputFilterArray.length;
            InputFilter[] inputFilterArray2 = new InputFilter[inputFilterArray.length - sparseArray.size()];
            int n4 = 0;
            for (int i3 = 0; i3 < n3; ++i3) {
                int n5 = n4;
                if (sparseArray.indexOfKey(i3) < 0) {
                    inputFilterArray2[n4] = inputFilterArray[i3];
                    n5 = n4 + 1;
                }
                n4 = n5;
            }
            return inputFilterArray2;
        }

        public void i(boolean bl) {
            this.c = bl;
        }

        public final TransformationMethod j(TransformationMethod transformationMethod) {
            TransformationMethod transformationMethod2 = transformationMethod;
            if (transformationMethod instanceof h) {
                transformationMethod2 = ((h)transformationMethod).a();
            }
            return transformationMethod2;
        }

        public final void k() {
            InputFilter[] inputFilterArray = this.a.getFilters();
            this.a.setFilters(this.a(inputFilterArray));
        }

        public void l() {
            TransformationMethod transformationMethod = this.e(this.a.getTransformationMethod());
            this.a.setTransformationMethod(transformationMethod);
        }

        public final TransformationMethod m(TransformationMethod transformationMethod) {
            if (transformationMethod instanceof h) {
                return transformationMethod;
            }
            if (transformationMethod instanceof PasswordTransformationMethod) {
                return transformationMethod;
            }
            return new h(transformationMethod);
        }
    }

    public static abstract class b {
        public abstract InputFilter[] a(InputFilter[] var1);

        public abstract boolean b();

        public abstract void c(boolean var1);

        public abstract void d(boolean var1);

        public abstract TransformationMethod e(TransformationMethod var1);
    }

    public static class c
    extends b {
        public final a a;

        public c(TextView textView) {
            this.a = new a(textView);
        }

        @Override
        public InputFilter[] a(InputFilter[] inputFilterArray) {
            if (this.f()) {
                return inputFilterArray;
            }
            return this.a.a(inputFilterArray);
        }

        @Override
        public boolean b() {
            return this.a.b();
        }

        @Override
        public void c(boolean bl) {
            if (this.f()) {
                return;
            }
            this.a.c(bl);
        }

        @Override
        public void d(boolean bl) {
            if (this.f()) {
                this.a.i(bl);
                return;
            }
            this.a.d(bl);
        }

        @Override
        public TransformationMethod e(TransformationMethod transformationMethod) {
            if (this.f()) {
                return transformationMethod;
            }
            return this.a.e(transformationMethod);
        }

        public final boolean f() {
            return androidx.emoji2.text.f.i() ^ true;
        }
    }
}

