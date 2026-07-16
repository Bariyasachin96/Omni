/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.VirtualLayout;
import java.util.Arrays;
import y.d;

public class CircularFlow
extends VirtualLayout {
    public static int x;
    public static float y;
    public ConstraintLayout n;
    public int o;
    public float[] p;
    public int[] q;
    public int r;
    public int s;
    public String t;
    public String u;
    public Float v;
    public Integer w;

    public CircularFlow(Context context) {
        super(context);
    }

    public CircularFlow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CircularFlow(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    private void setAngles(String string) {
        if (string == null) {
            return;
        }
        int n3 = 0;
        this.s = 0;
        while (true) {
            int n4;
            if ((n4 = string.indexOf(44, n3)) == -1) {
                this.y(string.substring(n3).trim());
                return;
            }
            this.y(string.substring(n3, n4).trim());
            n3 = n4 + 1;
        }
    }

    private void setRadius(String string) {
        if (string == null) {
            return;
        }
        int n3 = 0;
        this.r = 0;
        while (true) {
            int n4;
            if ((n4 = string.indexOf(44, n3)) == -1) {
                this.z(string.substring(n3).trim());
                return;
            }
            this.z(string.substring(n3, n4).trim());
            n3 = n4 + 1;
        }
    }

    public final void A() {
        this.n = (ConstraintLayout)this.getParent();
        for (int i3 = 0; i3 < this.d; ++i3) {
            View view = this.n.q(this.c[i3]);
            if (view == null) continue;
            int n3 = x;
            float f3 = y;
            Object object = this.q;
            if (object != null && i3 < ((int[])object).length) {
                n3 = object[i3];
            } else {
                object = this.w;
                if (object != null && object.intValue() != -1) {
                    ++this.r;
                    if (this.q == null) {
                        this.q = new int[1];
                    }
                    object = this.getRadius();
                    this.q = object;
                    object[this.r - 1] = n3;
                } else {
                    object = new StringBuilder();
                    object.append("Added radius to view with id: ");
                    object.append((String)this.k.get(view.getId()));
                    Log.e((String)"CircularFlow", (String)object.toString());
                }
            }
            object = this.p;
            if (object != null && i3 < ((int[])object).length) {
                f3 = object[i3];
            } else {
                object = this.v;
                if (object != null && object.floatValue() != -1.0f) {
                    ++this.s;
                    if (this.p == null) {
                        this.p = new float[1];
                    }
                    object = this.getAngles();
                    this.p = object;
                    object[this.s - 1] = f3;
                } else {
                    object = new StringBuilder();
                    object.append("Added angle to view with id: ");
                    object.append((String)this.k.get(view.getId()));
                    Log.e((String)"CircularFlow", (String)object.toString());
                }
            }
            object = (ConstraintLayout.LayoutParams)view.getLayoutParams();
            object.r = f3;
            object.p = this.o;
            object.q = n3;
            view.setLayoutParams((ViewGroup.LayoutParams)object);
        }
        this.h();
    }

    public float[] getAngles() {
        return Arrays.copyOf(this.p, this.s);
    }

    public int[] getRadius() {
        return Arrays.copyOf(this.q, this.r);
    }

    @Override
    public void o(AttributeSet attributeSet) {
        super.o(attributeSet);
        if (attributeSet != null) {
            attributeSet = this.getContext().obtainStyledAttributes(attributeSet, y.d.ConstraintLayout_Layout);
            int n3 = attributeSet.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                Object object;
                int n4 = attributeSet.getIndex(i3);
                if (n4 == y.d.ConstraintLayout_Layout_circularflow_viewCenter) {
                    this.o = attributeSet.getResourceId(n4, 0);
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_circularflow_angles) {
                    object = attributeSet.getString(n4);
                    this.t = object;
                    this.setAngles((String)object);
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_circularflow_radiusInDP) {
                    object = attributeSet.getString(n4);
                    this.u = object;
                    this.setRadius((String)object);
                    continue;
                }
                if (n4 == y.d.ConstraintLayout_Layout_circularflow_defaultAngle) {
                    this.v = object = Float.valueOf(attributeSet.getFloat(n4, y));
                    this.setDefaultAngle(((Float)object).floatValue());
                    continue;
                }
                if (n4 != y.d.ConstraintLayout_Layout_circularflow_defaultRadius) continue;
                this.w = object = Integer.valueOf(attributeSet.getDimensionPixelSize(n4, x));
                this.setDefaultRadius((Integer)object);
            }
            attributeSet.recycle();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object object = this.t;
        if (object != null) {
            this.p = new float[1];
            this.setAngles((String)object);
        }
        if ((object = this.u) != null) {
            this.q = new int[1];
            this.setRadius((String)object);
        }
        if ((object = this.v) != null) {
            this.setDefaultAngle(((Float)object).floatValue());
        }
        if ((object = this.w) != null) {
            this.setDefaultRadius((Integer)object);
        }
        this.A();
    }

    public void setDefaultAngle(float f3) {
        y = f3;
    }

    public void setDefaultRadius(int n3) {
        x = n3;
    }

    public final void y(String string) {
        float[] fArray;
        if (string != null && string.length() != 0 && this.e != null && (fArray = this.p) != null) {
            if (this.s + 1 > fArray.length) {
                this.p = Arrays.copyOf(fArray, fArray.length + 1);
            }
            this.p[this.s] = Integer.parseInt(string);
            ++this.s;
        }
    }

    public final void z(String string) {
        int[] nArray;
        if (string != null && string.length() != 0 && this.e != null && (nArray = this.q) != null) {
            if (this.r + 1 > nArray.length) {
                this.q = Arrays.copyOf(nArray, nArray.length + 1);
            }
            this.q[this.r] = (int)((float)Integer.parseInt(string) * this.e.getResources().getDisplayMetrics().density);
            ++this.r;
        }
    }
}

