/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.VirtualLayout;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import y.d;

public class Grid
extends VirtualLayout {
    public float A;
    public int B;
    public int C = 0;
    public boolean D;
    public boolean E;
    public boolean[][] F;
    public Set G = new HashSet();
    public int[] H;
    public final int n;
    public final int o;
    public View[] p;
    public ConstraintLayout q;
    public int r;
    public int s;
    public int t;
    public int u;
    public String v;
    public String w;
    public String x;
    public String y;
    public float z;

    public Grid(Context context) {
        super(context);
        this.n = 50;
        this.o = 50;
    }

    public Grid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.n = 50;
        this.o = 50;
    }

    public Grid(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.n = 50;
        this.o = 50;
    }

    private int getNextPosition() {
        boolean bl = false;
        int n3 = 0;
        while (!bl) {
            int n4;
            n3 = this.C;
            if (n3 >= this.r * this.t) {
                return -1;
            }
            int n5 = this.F(n3);
            boolean[] blArray = this.F[n5];
            if (blArray[n4 = this.E(this.C)]) {
                blArray[n4] = false;
                bl = true;
            }
            ++this.C;
        }
        return n3;
    }

    public final void A(View view) {
        ConstraintLayout.LayoutParams layoutParams = this.N(view);
        layoutParams.L = -1.0f;
        layoutParams.f = -1;
        layoutParams.e = -1;
        layoutParams.g = -1;
        layoutParams.h = -1;
        layoutParams.leftMargin = -1;
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public final void B(View view) {
        ConstraintLayout.LayoutParams layoutParams = this.N(view);
        layoutParams.M = -1.0f;
        layoutParams.j = -1;
        layoutParams.i = -1;
        layoutParams.k = -1;
        layoutParams.l = -1;
        layoutParams.topMargin = -1;
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public final void C(View view, int n3, int n4, int n5, int n6) {
        ConstraintLayout.LayoutParams layoutParams = this.N(view);
        int[] nArray = this.H;
        layoutParams.e = nArray[n4];
        layoutParams.i = nArray[n3];
        layoutParams.h = nArray[n4 + n6 - 1];
        layoutParams.l = nArray[n3 + n5 - 1];
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public final boolean D(boolean bl) {
        if (this.q != null && this.r >= 1 && this.t >= 1) {
            Object object;
            if (bl) {
                for (int i3 = 0; i3 < this.F.length; ++i3) {
                    for (int i4 = 0; i4 < ((Object)(object = (Object)this.F)[0]).length; ++i4) {
                        object[i3][i4] = true;
                    }
                }
                this.G.clear();
            }
            this.C = 0;
            this.z();
            object = this.w;
            bl = object != null && !((String)object).trim().isEmpty() && (object = (Object)this.O(this.w)) != null ? this.G((int[][])object) : true;
            object = this.v;
            boolean bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (!((String)object).trim().isEmpty()) {
                    object = this.O(this.v);
                    bl2 = bl;
                    if (object != null) {
                        bl2 = bl & this.H(this.c, (int[][])object);
                    }
                }
            }
            return bl2 & this.y() || !this.D;
            {
            }
        }
        return false;
    }

    public final int E(int n3) {
        if (this.B == 1) {
            return n3 / this.r;
        }
        return n3 % this.t;
    }

    public final int F(int n3) {
        if (this.B == 1) {
            return n3 % this.r;
        }
        return n3 / this.t;
    }

    public final boolean G(int[][] nArray) {
        for (int i3 = 0; i3 < nArray.length; ++i3) {
            int[] nArray2;
            int n3;
            int n4 = this.F(nArray[i3][0]);
            if (this.J(n4, n3 = this.E(nArray[i3][0]), (nArray2 = nArray[i3])[1], nArray2[2])) continue;
            return false;
        }
        return true;
    }

    public final boolean H(int[] nArray, int[][] nArray2) {
        View[] viewArray = this.n(this.q);
        for (int i3 = 0; i3 < nArray2.length; ++i3) {
            Object object;
            int n3;
            int n4 = this.F(nArray2[i3][0]);
            if (!this.J(n4, n3 = this.E(nArray2[i3][0]), (object = nArray2[i3])[1], object[2])) {
                return false;
            }
            object = viewArray[i3];
            int[] nArray3 = nArray2[i3];
            this.C((View)object, n4, n3, nArray3[1], nArray3[2]);
            this.G.add(nArray[i3]);
        }
        return true;
    }

    public final void I() {
        int n3 = this.r;
        int n4 = this.t;
        boolean[][] blArray = new boolean[n3][n4];
        this.F = blArray;
        n3 = blArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            Arrays.fill(blArray[i3], true);
        }
    }

    public final boolean J(int n3, int n4, int n5, int n6) {
        for (int i3 = n3; i3 < n3 + n5; ++i3) {
            for (int i4 = n4; i4 < n4 + n6; ++i4) {
                Object object = this.F;
                if (i3 < ((boolean[][])object).length && i4 < object[0].length && (object = (Object)object[i3])[i4] != false) {
                    object[i4] = (boolean[])false;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public final boolean K(CharSequence charSequence) {
        return true;
    }

    public final boolean L(String string) {
        return true;
    }

    public final View M() {
        View view = new View(this.getContext());
        view.setId(View.generateViewId());
        view.setVisibility(4);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(0, 0);
        this.q.addView(view, (ViewGroup.LayoutParams)layoutParams);
        return view;
    }

    public final ConstraintLayout.LayoutParams N(View view) {
        return (ConstraintLayout.LayoutParams)view.getLayoutParams();
    }

    public final int[][] O(String stringArray) {
        if (!this.K((CharSequence)stringArray)) {
            return null;
        }
        String[] stringArray2 = stringArray.split(",");
        int n3 = stringArray2.length;
        int[][] nArray = new int[n3][3];
        for (n3 = 0; n3 < stringArray2.length; ++n3) {
            String[] stringArray3 = stringArray2[n3].trim().split(":");
            stringArray = stringArray3[1].split("x");
            nArray[n3][0] = Integer.parseInt(stringArray3[0]);
            nArray[n3][1] = Integer.parseInt(stringArray[0]);
            nArray[n3][2] = Integer.parseInt(stringArray[1]);
        }
        return nArray;
    }

    public final float[] P(int n3, String object) {
        String[] stringArray;
        Object object2 = stringArray = null;
        if (object != null) {
            if (((String)object).trim().isEmpty()) {
                object2 = stringArray;
            } else {
                stringArray = ((String)object).split(",");
                if (stringArray.length != n3) {
                    return null;
                }
                object = new float[n3];
                int n4 = 0;
                while (true) {
                    object2 = object;
                    if (n4 >= n3) break;
                    object[n4] = Float.parseFloat(stringArray[n4].trim());
                    ++n4;
                }
            }
        }
        return object2;
    }

    public final void Q() {
        int n3;
        int n4 = this.getId();
        int n5 = Math.max(this.r, this.t);
        Object object = this.P(this.t, this.y);
        Object object2 = this.p;
        int n6 = 0;
        object2 = this.N(object2[0]);
        if (this.t == 1) {
            this.A(this.p[0]);
            object2.e = n4;
            object2.h = n4;
            this.p[0].setLayoutParams((ViewGroup.LayoutParams)object2);
            return;
        }
        while (true) {
            if (n6 >= n3) break;
            object2 = this.N(this.p[n6]);
            this.A(this.p[n6]);
            if (object != null) {
                object2.L = object[n6];
            }
            if (n6 > 0) {
                object2.f = this.H[n6 - 1];
            } else {
                object2.e = n4;
            }
            if (n6 < this.t - 1) {
                object2.g = this.H[n6 + 1];
            } else {
                object2.h = n4;
            }
            if (n6 > 0) {
                object2.leftMargin = (int)this.z;
            }
            this.p[n6].setLayoutParams((ViewGroup.LayoutParams)object2);
            ++n6;
        }
        for (int i3 = n3 = this.t; i3 < n5; ++i3) {
            object = (Object)this.N(this.p[i3]);
            this.A(this.p[i3]);
            object.e = n4;
            object.h = n4;
            this.p[i3].setLayoutParams((ViewGroup.LayoutParams)object);
        }
    }

    public final void R() {
        int n3;
        int n4 = this.getId();
        int n5 = Math.max(this.r, this.t);
        Object object = this.P(this.r, this.x);
        int n6 = this.r;
        int n7 = 0;
        if (n6 == 1) {
            object = (Object)this.N(this.p[0]);
            this.B(this.p[0]);
            object.i = n4;
            object.l = n4;
            this.p[0].setLayoutParams((ViewGroup.LayoutParams)object);
            return;
        }
        while (true) {
            if (n7 >= n3) break;
            ConstraintLayout.LayoutParams layoutParams = this.N(this.p[n7]);
            this.B(this.p[n7]);
            if (object != null) {
                layoutParams.M = object[n7];
            }
            if (n7 > 0) {
                layoutParams.j = this.H[n7 - 1];
            } else {
                layoutParams.i = n4;
            }
            if (n7 < this.r - 1) {
                layoutParams.k = this.H[n7 + 1];
            } else {
                layoutParams.l = n4;
            }
            if (n7 > 0) {
                layoutParams.topMargin = (int)this.z;
            }
            this.p[n7].setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            ++n7;
        }
        for (n6 = n3 = this.r; n6 < n5; ++n6) {
            object = (Object)this.N(this.p[n6]);
            this.B(this.p[n6]);
            object.i = n4;
            object.l = n4;
            this.p[n6].setLayoutParams((ViewGroup.LayoutParams)object);
        }
    }

    public final void S() {
        int n3;
        int n4 = this.s;
        if (n4 != 0 && (n3 = this.u) != 0) {
            this.r = n4;
            this.t = n3;
            return;
        }
        n3 = this.u;
        if (n3 > 0) {
            this.t = n3;
            this.r = (this.d + n3 - 1) / n3;
            return;
        }
        if (n4 > 0) {
            this.r = n4;
            this.t = (this.d + n4 - 1) / n4;
            return;
        }
        this.r = n4 = (int)(Math.sqrt(this.d) + 1.5);
        this.t = (this.d + n4 - 1) / n4;
    }

    public String getColumnWeights() {
        return this.y;
    }

    public int getColumns() {
        return this.u;
    }

    public float getHorizontalGaps() {
        return this.z;
    }

    public int getOrientation() {
        return this.B;
    }

    public String getRowWeights() {
        return this.x;
    }

    public int getRows() {
        return this.s;
    }

    public String getSkips() {
        return this.w;
    }

    public String getSpans() {
        return this.v;
    }

    public float getVerticalGaps() {
        return this.A;
    }

    @Override
    public void o(AttributeSet attributeSet) {
        super.o(attributeSet);
        this.g = true;
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.Grid);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.Grid_grid_rows) {
                    this.s = attributeSet.getInteger(n4, 0);
                    continue;
                }
                if (n4 == y.d.Grid_grid_columns) {
                    this.u = attributeSet.getInteger(n4, 0);
                    continue;
                }
                if (n4 == y.d.Grid_grid_spans) {
                    this.v = attributeSet.getString(n4);
                    continue;
                }
                if (n4 == y.d.Grid_grid_skips) {
                    this.w = attributeSet.getString(n4);
                    continue;
                }
                if (n4 == y.d.Grid_grid_rowWeights) {
                    this.x = attributeSet.getString(n4);
                    continue;
                }
                if (n4 == y.d.Grid_grid_columnWeights) {
                    this.y = attributeSet.getString(n4);
                    continue;
                }
                if (n4 == y.d.Grid_grid_orientation) {
                    this.B = attributeSet.getInt(n4, 0);
                    continue;
                }
                if (n4 == y.d.Grid_grid_horizontalGaps) {
                    this.z = attributeSet.getDimension(n4, 0.0f);
                    continue;
                }
                if (n4 == y.d.Grid_grid_verticalGaps) {
                    this.A = attributeSet.getDimension(n4, 0.0f);
                    continue;
                }
                if (n4 == y.d.Grid_grid_validateInputs) {
                    this.D = attributeSet.getBoolean(n4, false);
                    continue;
                }
                if (n4 != y.d.Grid_grid_useRtl) continue;
                this.E = attributeSet.getBoolean(n4, false);
            }
            this.S();
            this.I();
            attributeSet.recycle();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.q = (ConstraintLayout)this.getParent();
        this.D(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isInEditMode()) {
            Paint paint = new Paint();
            paint.setColor(-65536);
            paint.setStyle(Paint.Style.STROKE);
            int n3 = this.getTop();
            int n4 = this.getLeft();
            int n5 = this.getBottom();
            int n6 = this.getRight();
            for (View view : this.p) {
                int n7 = view.getLeft();
                int n8 = view.getTop();
                int n9 = view.getRight();
                int n10 = view.getBottom();
                canvas.drawRect((float)(n7 - n4), 0.0f, (float)(n9 - n4), (float)(n5 - n3), paint);
                canvas.drawRect(0.0f, (float)(n8 - n3), (float)(n6 - n4), (float)(n10 - n3), paint);
            }
        }
    }

    public void setColumnWeights(String string) {
        String string2;
        if (!this.L(string) || (string2 = this.y) != null && string2.equals(string)) {
            return;
        }
        this.y = string;
        this.D(true);
        this.invalidate();
    }

    public void setColumns(int n3) {
        if (n3 > 50 || this.u == n3) {
            return;
        }
        this.u = n3;
        this.S();
        this.I();
        this.D(false);
        this.invalidate();
    }

    public void setHorizontalGaps(float f3) {
        if (f3 < 0.0f || this.z == f3) {
            return;
        }
        this.z = f3;
        this.D(true);
        this.invalidate();
    }

    public void setOrientation(int n3) {
        if (n3 != 0 && n3 != 1 || this.B == n3) {
            return;
        }
        this.B = n3;
        this.D(true);
        this.invalidate();
    }

    public void setRowWeights(String string) {
        String string2;
        if (!this.L(string) || (string2 = this.x) != null && string2.equals(string)) {
            return;
        }
        this.x = string;
        this.D(true);
        this.invalidate();
    }

    public void setRows(int n3) {
        if (n3 > 50 || this.s == n3) {
            return;
        }
        this.s = n3;
        this.S();
        this.I();
        this.D(false);
        this.invalidate();
    }

    public void setSkips(String string) {
        String string2;
        if (!this.K(string) || (string2 = this.w) != null && string2.equals(string)) {
            return;
        }
        this.w = string;
        this.D(true);
        this.invalidate();
    }

    public void setSpans(CharSequence charSequence) {
        String string;
        if (!this.K(charSequence) || (string = this.v) != null && string.contentEquals(charSequence)) {
            return;
        }
        this.v = charSequence.toString();
        this.D(true);
        this.invalidate();
    }

    public void setVerticalGaps(float f3) {
        if (f3 < 0.0f || this.A == f3) {
            return;
        }
        this.A = f3;
        this.D(true);
        this.invalidate();
    }

    public final boolean y() {
        View[] viewArray = this.n(this.q);
        for (int i3 = 0; i3 < this.d; ++i3) {
            if (this.G.contains(this.c[i3])) continue;
            int n3 = this.getNextPosition();
            int n4 = this.F(n3);
            int n5 = this.E(n3);
            if (n3 == -1) {
                return false;
            }
            this.C(viewArray[i3], n4, n5, 1, 1);
        }
        return true;
    }

    public final void z() {
        int n3;
        int n4 = Math.max(this.r, this.t);
        View[] viewArray = this.p;
        int n5 = 0;
        if (viewArray == null) {
            this.p = new View[n4];
            for (n3 = 0; n3 < (viewArray = this.p).length; ++n3) {
                viewArray[n3] = this.M();
            }
        } else if (n4 != viewArray.length) {
            View view;
            viewArray = new View[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                view = this.p;
                viewArray[n3] = n3 < ((View[])view).length ? view[n3] : this.M();
            }
            for (n3 = n4; n3 < ((View[])(view = this.p)).length; ++n3) {
                view = view[n3];
                this.q.removeView(view);
            }
            this.p = viewArray;
        }
        this.H = new int[n4];
        for (n3 = n5; n3 < (viewArray = this.p).length; ++n3) {
            this.H[n3] = viewArray[n3].getId();
        }
        this.R();
        this.Q();
    }
}

