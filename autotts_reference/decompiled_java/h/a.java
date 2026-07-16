/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 */
package h;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import c.b;
import c.d;
import c.j;

public class a {
    public Context a;

    public a(Context context) {
        this.a = context;
    }

    public static a b(Context context) {
        return new a(context);
    }

    public boolean a() {
        return this.a.getApplicationInfo().targetSdkVersion < 14;
    }

    public int c() {
        return this.a.getResources().getDisplayMetrics().widthPixels / 2;
    }

    public int d() {
        Configuration configuration = this.a.getResources().getConfiguration();
        int n3 = configuration.screenWidthDp;
        int n4 = configuration.screenHeightDp;
        if (!(configuration.smallestScreenWidthDp > 600 || n3 > 600 || n3 > 960 && n4 > 720 || n3 > 720 && n4 > 960)) {
            if (!(n3 >= 500 || n3 > 640 && n4 > 480 || n3 > 480 && n4 > 640)) {
                if (n3 >= 360) {
                    return 3;
                }
                return 2;
            }
            return 4;
        }
        return 5;
    }

    public int e() {
        return this.a.getResources().getDimensionPixelSize(d.abc_action_bar_stacked_tab_max_width);
    }

    public int f() {
        TypedArray typedArray = this.a.obtainStyledAttributes(null, j.ActionBar, c.a.actionBarStyle, 0);
        int n3 = typedArray.getLayoutDimension(j.ActionBar_height, 0);
        Resources resources = this.a.getResources();
        int n4 = n3;
        if (!this.g()) {
            n4 = Math.min(n3, resources.getDimensionPixelSize(d.abc_action_bar_stacked_max_height));
        }
        typedArray.recycle();
        return n4;
    }

    public boolean g() {
        return this.a.getResources().getBoolean(b.abc_action_bar_embed_tabs);
    }

    public boolean h() {
        return true;
    }
}

