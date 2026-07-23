/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.b;
import java.util.Arrays;
import java.util.HashMap;
import u.e;
import u.f;
import u.i;
import u.j;
import y.c;
import y.d;

public abstract class ConstraintHelper
extends View {
    public int[] c = new int[32];
    public int d;
    public Context e;
    public i f;
    public boolean g = false;
    public String h;
    public String i;
    public View[] j = null;
    public HashMap k = new HashMap();

    public ConstraintHelper(Context context) {
        super(context);
        this.e = context;
        this.o(null);
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = context;
        this.o(attributeSet);
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.e = context;
        this.o(attributeSet);
    }

    public final void e(String charSequence) {
        if (charSequence != null && ((String)charSequence).length() != 0 && this.e != null) {
            String string = ((String)charSequence).trim();
            int n3 = this.m(string);
            if (n3 != 0) {
                this.k.put(n3, string);
                this.f(n3);
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Could not find id of \"");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("\"");
            Log.w((String)"ConstraintHelper", (String)((StringBuilder)charSequence).toString());
        }
    }

    public final void f(int n3) {
        if (n3 == this.getId()) {
            return;
        }
        int n4 = this.d;
        int[] nArray = this.c;
        if (n4 + 1 > nArray.length) {
            this.c = Arrays.copyOf(nArray, nArray.length * 2);
        }
        nArray = this.c;
        n4 = this.d;
        nArray[n4] = n3;
        this.d = n4 + 1;
    }

    public final void g(String object) {
        if (object != null && object.length() != 0 && this.e != null) {
            String string = object.trim();
            object = this.getParent() instanceof ConstraintLayout ? (ConstraintLayout)this.getParent() : null;
            if (object == null) {
                Log.w((String)"ConstraintHelper", (String)"Parent not a ConstraintLayout");
                return;
            }
            int n3 = object.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                View view = object.getChildAt(i3);
                Object object2 = view.getLayoutParams();
                if (!(object2 instanceof ConstraintLayout.LayoutParams) || !string.equals(((ConstraintLayout.LayoutParams)((Object)object2)).c0)) continue;
                if (view.getId() == -1) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("to use ConstraintTag view ");
                    ((StringBuilder)object2).append(view.getClass().getSimpleName());
                    ((StringBuilder)object2).append(" must have an ID");
                    Log.w((String)"ConstraintHelper", (String)((StringBuilder)object2).toString());
                    continue;
                }
                this.f(view.getId());
            }
        }
    }

    public int[] getReferencedIds() {
        return Arrays.copyOf(this.c, this.d);
    }

    public void h() {
        ViewParent viewParent = this.getParent();
        if (viewParent != null && viewParent instanceof ConstraintLayout) {
            this.i((ConstraintLayout)viewParent);
        }
    }

    public void i(ConstraintLayout constraintLayout) {
        int n3 = this.getVisibility();
        float f3 = this.getElevation();
        for (int i3 = 0; i3 < this.d; ++i3) {
            View view = constraintLayout.q(this.c[i3]);
            if (view == null) continue;
            view.setVisibility(n3);
            if (!(f3 > 0.0f)) continue;
            view.setTranslationZ(view.getTranslationZ() + f3);
        }
    }

    public void j(ConstraintLayout constraintLayout) {
    }

    public final int[] k(String stringArray) {
        stringArray = stringArray.split(",");
        int[] nArray = new int[stringArray.length];
        int n3 = 0;
        for (int i3 = 0; i3 < stringArray.length; ++i3) {
            int n4 = this.m(stringArray[i3].trim());
            int n5 = n3;
            if (n4 != 0) {
                nArray[n3] = n4;
                n5 = n3 + 1;
            }
            n3 = n5;
        }
        if (n3 != stringArray.length) {
            return Arrays.copyOf(nArray, n3);
        }
        return nArray;
    }

    public final int l(ConstraintLayout constraintLayout, String string) {
        if (string != null && constraintLayout != null) {
            Resources resources = this.e.getResources();
            if (resources == null) {
                return 0;
            }
            int n3 = constraintLayout.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                String string2;
                View view = constraintLayout.getChildAt(i3);
                if (view.getId() == -1) continue;
                try {
                    string2 = resources.getResourceEntryName(view.getId());
                }
                catch (Resources.NotFoundException notFoundException) {
                    string2 = null;
                }
                if (!string.equals(string2)) continue;
                return view.getId();
            }
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int m(String string) {
        int n3;
        ConstraintLayout constraintLayout = this.getParent() instanceof ConstraintLayout ? (ConstraintLayout)this.getParent() : null;
        boolean bl = this.isInEditMode();
        int n4 = n3 = 0;
        if (bl) {
            n4 = n3;
            if (constraintLayout != null) {
                Object object = constraintLayout.o(0, string);
                n4 = n3;
                if (object instanceof Integer) {
                    n4 = (Integer)object;
                }
            }
        }
        n3 = n4;
        if (n4 == 0) {
            n3 = n4;
            if (constraintLayout != null) {
                n3 = this.l(constraintLayout, string);
            }
        }
        n4 = n3;
        if (n3 == 0) {
            try {
                n4 = c.class.getField(string).getInt(null);
            }
            catch (Exception exception) {
                n4 = n3;
            }
        }
        n3 = n4;
        if (n4 != 0) return n3;
        return this.e.getResources().getIdentifier(string, "id", this.e.getPackageName());
    }

    public View[] n(ConstraintLayout constraintLayout) {
        View[] viewArray = this.j;
        if (viewArray == null || viewArray.length != this.d) {
            this.j = new View[this.d];
        }
        for (int i3 = 0; i3 < this.d; ++i3) {
            int n3 = this.c[i3];
            this.j[i3] = constraintLayout.q(n3);
        }
        return this.j;
    }

    public void o(AttributeSet attributeSet) {
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_Layout);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                String string;
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.ConstraintLayout_Layout_constraint_referenced_ids) {
                    this.h = string = attributeSet.getString(n4);
                    this.setIds(string);
                    continue;
                }
                if (n4 != y.d.ConstraintLayout_Layout_constraint_referenced_tags) continue;
                this.i = string = attributeSet.getString(n4);
                this.setReferenceTags(string);
            }
            attributeSet.recycle();
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String string = this.h;
        if (string != null) {
            this.setIds(string);
        }
        if ((string = this.i) != null) {
            this.setReferenceTags(string);
        }
    }

    public void onDraw(Canvas canvas) {
    }

    public void onMeasure(int n3, int n4) {
        if (this.g) {
            super.onMeasure(n3, n4);
            return;
        }
        this.setMeasuredDimension(0, 0);
    }

    public void p(b.a a4, j j3, ConstraintLayout.LayoutParams object, SparseArray sparseArray) {
        b.b b3 = a4.e;
        object = b3.k0;
        if (object != null) {
            this.setReferencedIds((int[])object);
        } else {
            object = b3.l0;
            if (object != null) {
                if (((String)object).length() > 0) {
                    object = a4.e;
                    ((b.b)object).k0 = this.k(((b.b)object).l0);
                } else {
                    a4.e.k0 = null;
                }
            }
        }
        if (j3 != null) {
            j3.c();
            if (a4.e.k0 != null) {
                for (int i3 = 0; i3 < ((ConstraintLayout.LayoutParams)(object = (Object)a4.e.k0)).length; ++i3) {
                    if ((object = (e)sparseArray.get((int)object[i3])) == null) continue;
                    j3.a((e)object);
                }
            }
        }
    }

    public void q(e e3, boolean bl) {
    }

    public void r(ConstraintLayout constraintLayout) {
    }

    public void s(ConstraintLayout constraintLayout) {
    }

    public void setIds(String string) {
        this.h = string;
        if (string == null) {
            return;
        }
        int n3 = 0;
        this.d = 0;
        while (true) {
            int n4;
            if ((n4 = string.indexOf(44, n3)) == -1) {
                this.e(string.substring(n3));
                return;
            }
            this.e(string.substring(n3, n4));
            n3 = n4 + 1;
        }
    }

    public void setReferenceTags(String string) {
        this.i = string;
        if (string == null) {
            return;
        }
        int n3 = 0;
        this.d = 0;
        while (true) {
            int n4;
            if ((n4 = string.indexOf(44, n3)) == -1) {
                this.g(string.substring(n3));
                return;
            }
            this.g(string.substring(n3, n4));
            n3 = n4 + 1;
        }
    }

    public void setReferencedIds(int[] nArray) {
        this.h = null;
        this.d = 0;
        for (int i3 = 0; i3 < nArray.length; ++i3) {
            this.f(nArray[i3]);
        }
    }

    public void setTag(int n3, Object object) {
        super.setTag(n3, object);
        if (object == null && this.h == null) {
            this.f(n3);
        }
    }

    public void t(ConstraintLayout constraintLayout) {
    }

    public void u(ConstraintLayout constraintLayout) {
        i i3;
        if (this.isInEditMode()) {
            this.setIds(this.h);
        }
        if ((i3 = this.f) == null) {
            return;
        }
        i3.c();
        for (int i4 = 0; i4 < this.d; ++i4) {
            int n3 = this.c[i4];
            View view = constraintLayout.q(n3);
            i3 = view;
            if (view == null) {
                String string = (String)this.k.get(n3);
                n3 = this.l(constraintLayout, string);
                i3 = view;
                if (n3 != 0) {
                    this.c[i4] = n3;
                    this.k.put(n3, string);
                    i3 = constraintLayout.q(n3);
                }
            }
            if (i3 == null) continue;
            this.f.a(constraintLayout.r((View)i3));
        }
        this.f.b(constraintLayout.e);
    }

    public void v(f f3, i i3, SparseArray sparseArray) {
        i3.c();
        for (int i4 = 0; i4 < this.d; ++i4) {
            i3.a((e)sparseArray.get(this.c[i4]));
        }
    }

    public void w() {
        ViewGroup.LayoutParams layoutParams;
        if (this.f != null && (layoutParams = this.getLayoutParams()) instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams)layoutParams).v0 = (e)((Object)this.f);
        }
    }
}

