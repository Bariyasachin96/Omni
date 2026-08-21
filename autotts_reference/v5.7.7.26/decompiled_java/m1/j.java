/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.os.Build$VERSION
 *  android.widget.ImageView
 */
package m1;

import android.graphics.Matrix;
import android.os.Build;
import android.widget.ImageView;

public abstract class j {
    public static boolean a = true;

    public static void a(ImageView imageView, Matrix matrix) {
        if (Build.VERSION.SDK_INT >= 29) {
            m1.j$a.a(imageView, matrix);
            return;
        }
        if (matrix == null) {
            matrix = imageView.getDrawable();
            if (matrix != null) {
                matrix.setBounds(0, 0, imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight(), imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom());
                imageView.invalidate();
            }
            return;
        }
        j.b(imageView, matrix);
    }

    public static void b(ImageView imageView, Matrix matrix) {
        if (a) {
            try {
                m1.j$a.a(imageView, matrix);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                a = false;
            }
        }
    }

    public static abstract class a {
        public static void a(ImageView imageView, Matrix matrix) {
            imageView.animateTransform(matrix);
        }
    }
}

