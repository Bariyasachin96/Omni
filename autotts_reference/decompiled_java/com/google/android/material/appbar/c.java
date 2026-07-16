/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.google.android.material.appbar;

import android.view.View;
import o0.x0;

public class c {
    public final View a;
    public int b;
    public int c;
    public int d;
    public int e;
    public boolean f = true;
    public boolean g = true;

    public c(View view) {
        this.a = view;
    }

    public void a() {
        View view = this.a;
        x0.S(view, this.d - (view.getTop() - this.b));
        view = this.a;
        x0.R(view, this.e - (view.getLeft() - this.c));
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.d;
    }

    public void d() {
        this.b = this.a.getTop();
        this.c = this.a.getLeft();
    }

    public boolean e(int n3) {
        if (this.g && this.e != n3) {
            this.e = n3;
            this.a();
            return true;
        }
        return false;
    }

    public boolean f(int n3) {
        if (this.f && this.d != n3) {
            this.d = n3;
            this.a();
            return true;
        }
        return false;
    }
}

