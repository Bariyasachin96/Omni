/*
 * Decompiled with CFR 0.152.
 */
package c3;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import c3.j;

public class l
extends FragmentStateAdapter {
    public static final int[] n = new int[]{2131624219, 2131624220, 2131624221, 2131624222, 2131624223};
    public final FragmentActivity m;

    public l(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.m = fragmentActivity;
    }

    @Override
    public Fragment B(int n3) {
        return c3.j.K2(n3 + 1);
    }

    public CharSequence T(int n3) {
        return this.m.getResources().getString(n[n3]);
    }

    @Override
    public int f() {
        return 5;
    }
}

