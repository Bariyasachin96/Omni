/*
 * Decompiled with CFR 0.152.
 */
package c3;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import c3.i;

public class j
extends FragmentStateAdapter {
    public static final int[] n = new int[]{2131624212, 2131624213, 2131624214, 2131624215, 2131624216};
    public final FragmentActivity m;

    public j(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.m = fragmentActivity;
    }

    @Override
    public Fragment B(int n3) {
        return c3.i.D2(n3 + 1);
    }

    public CharSequence T(int n3) {
        return this.m.getResources().getString(n[n3]);
    }

    @Override
    public int f() {
        return 5;
    }
}

