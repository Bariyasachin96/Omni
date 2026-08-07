/*
 * Decompiled with CFR 0.152.
 */
package c3;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import c3.k;

public class m
extends FragmentStateAdapter {
    public static final int[] n = new int[]{2131624229, 2131624230, 2131624231, 2131624232, 2131624233};
    public final FragmentActivity m;

    public m(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.m = fragmentActivity;
    }

    @Override
    public Fragment B(int n3) {
        return c3.k.L2(n3 + 1);
    }

    public CharSequence T(int n3) {
        return this.m.getResources().getString(n[n3]);
    }

    @Override
    public int f() {
        return 5;
    }
}

