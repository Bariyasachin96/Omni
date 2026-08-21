/*
 * Decompiled with CFR 0.152.
 */
package c3;

import java.util.LinkedHashSet;

public class f {
    public String a;
    public String b;
    public int c;
    public int d;
    public int e;
    public String f;
    public String g;
    public String h = "*Default";
    public boolean i = false;
    public LinkedHashSet j = new LinkedHashSet();

    public f(String string, String string2) {
        this.a = string;
        this.b = string2;
        this.e = 100;
        this.d = 100;
        this.c = 100;
    }

    public f(String string, String string2, int n3, int n4, int n5, String string3, String string4, String string5) {
        this.a = string;
        this.b = string2;
        this.c = n3;
        this.d = n4;
        this.e = n5;
        this.f = string3;
        this.g = string4;
        this.h = string5;
    }
}

