/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 */
package com.google.android.material.search;

import android.animation.Animator;
import com.google.android.material.search.SearchBar;
import java.util.LinkedHashSet;
import java.util.Set;

public class b {
    public final Set a = new LinkedHashSet();
    public final Set b = new LinkedHashSet();
    public final Set c = new LinkedHashSet();
    public Animator d;
    public Animator e;
    public boolean f = true;
    public Animator g = null;

    public void a(boolean bl) {
        this.f = bl;
    }

    public void b(SearchBar searchBar) {
        Animator animator = this.d;
        if (animator != null) {
            animator.end();
        }
        if ((animator = this.e) != null) {
            animator.end();
        }
        if ((searchBar = searchBar.getCenterView()) != null) {
            searchBar.setAlpha(0.0f);
        }
    }
}

