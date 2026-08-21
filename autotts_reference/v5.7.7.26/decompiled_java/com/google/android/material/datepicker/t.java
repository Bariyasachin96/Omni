/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.g;

public abstract class t
extends LinearLayoutManager {
    public t(Context context, int n3, boolean bl) {
        super(context, n3, bl);
    }

    @Override
    public void M1(RecyclerView object, RecyclerView.z z3, int n3) {
        object = new g(this, object.getContext()){
            public final t q;
            {
                this.q = t3;
                super(context);
            }

            @Override
            public float v(DisplayMetrics displayMetrics) {
                return 100.0f / (float)displayMetrics.densityDpi;
            }
        };
        ((RecyclerView.y)object).p(n3);
        this.N1((RecyclerView.y)object);
    }
}

