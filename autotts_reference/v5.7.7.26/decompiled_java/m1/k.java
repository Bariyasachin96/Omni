/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Matrix$ScaleToFit
 *  android.graphics.RectF
 */
package m1;

import android.graphics.Matrix;
import android.graphics.RectF;

public abstract class k {
    public static final Matrix a = new Matrix(){

        public void a() {
            throw new IllegalStateException("Matrix can not be modified");
        }

        public boolean postConcat(Matrix matrix) {
            this.a();
            return false;
        }

        public boolean postRotate(float f3) {
            this.a();
            return false;
        }

        public boolean postRotate(float f3, float f4, float f5) {
            this.a();
            return false;
        }

        public boolean postScale(float f3, float f4) {
            this.a();
            return false;
        }

        public boolean postScale(float f3, float f4, float f5, float f6) {
            this.a();
            return false;
        }

        public boolean postSkew(float f3, float f4) {
            this.a();
            return false;
        }

        public boolean postSkew(float f3, float f4, float f5, float f6) {
            this.a();
            return false;
        }

        public boolean postTranslate(float f3, float f4) {
            this.a();
            return false;
        }

        public boolean preConcat(Matrix matrix) {
            this.a();
            return false;
        }

        public boolean preRotate(float f3) {
            this.a();
            return false;
        }

        public boolean preRotate(float f3, float f4, float f5) {
            this.a();
            return false;
        }

        public boolean preScale(float f3, float f4) {
            this.a();
            return false;
        }

        public boolean preScale(float f3, float f4, float f5, float f6) {
            this.a();
            return false;
        }

        public boolean preSkew(float f3, float f4) {
            this.a();
            return false;
        }

        public boolean preSkew(float f3, float f4, float f5, float f6) {
            this.a();
            return false;
        }

        public boolean preTranslate(float f3, float f4) {
            this.a();
            return false;
        }

        public void reset() {
            this.a();
        }

        public void set(Matrix matrix) {
            this.a();
        }

        public boolean setConcat(Matrix matrix, Matrix matrix2) {
            this.a();
            return false;
        }

        public boolean setPolyToPoly(float[] fArray, int n3, float[] fArray2, int n4, int n5) {
            this.a();
            return false;
        }

        public boolean setRectToRect(RectF rectF, RectF rectF2, Matrix.ScaleToFit scaleToFit) {
            this.a();
            return false;
        }

        public void setRotate(float f3) {
            this.a();
        }

        public void setRotate(float f3, float f4, float f5) {
            this.a();
        }

        public void setScale(float f3, float f4) {
            this.a();
        }

        public void setScale(float f3, float f4, float f5, float f6) {
            this.a();
        }

        public void setSinCos(float f3, float f4) {
            this.a();
        }

        public void setSinCos(float f3, float f4, float f5, float f6) {
            this.a();
        }

        public void setSkew(float f3, float f4) {
            this.a();
        }

        public void setSkew(float f3, float f4, float f5, float f6) {
            this.a();
        }

        public void setTranslate(float f3, float f4) {
            this.a();
        }

        public void setValues(float[] fArray) {
            this.a();
        }
    };
}

