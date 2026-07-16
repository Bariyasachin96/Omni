/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class f {
    public boolean a = true;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f = 0;
    public int g = 0;
    public boolean h;
    public boolean i;

    public boolean a(RecyclerView.z z3) {
        int n3 = this.c;
        return n3 >= 0 && n3 < z3.b();
    }

    public View b(RecyclerView.v v3) {
        v3 = v3.o(this.c);
        this.c += this.d;
        return v3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LayoutState{mAvailable=");
        stringBuilder.append(this.b);
        stringBuilder.append(", mCurrentPosition=");
        stringBuilder.append(this.c);
        stringBuilder.append(", mItemDirection=");
        stringBuilder.append(this.d);
        stringBuilder.append(", mLayoutDirection=");
        stringBuilder.append(this.e);
        stringBuilder.append(", mStartLine=");
        stringBuilder.append(this.f);
        stringBuilder.append(", mEndLine=");
        stringBuilder.append(this.g);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

