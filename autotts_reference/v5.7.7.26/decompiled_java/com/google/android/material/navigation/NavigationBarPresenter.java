/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseArray
 */
package com.google.android.material.navigation;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.l;
import com.google.android.material.badge.b;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.navigation.NavigationBarMenuView;

public class NavigationBarPresenter
implements i {
    public NavigationBarMenuView c;
    public boolean d = false;
    public int e;

    @Override
    public void a(e e3, boolean bl) {
    }

    @Override
    public void b(Context context, e e3) {
        this.c.b(e3);
    }

    public void c(int n3) {
        this.e = n3;
    }

    @Override
    public void d(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            NavigationBarMenuView navigationBarMenuView = this.c;
            parcelable = (SavedState)parcelable;
            navigationBarMenuView.o(parcelable.c);
            parcelable = b.c(this.c.getContext(), parcelable.d);
            this.c.n((SparseArray)parcelable);
        }
    }

    public void e(NavigationBarMenuView navigationBarMenuView) {
        this.c = navigationBarMenuView;
    }

    @Override
    public boolean f(l l3) {
        return false;
    }

    @Override
    public void g(boolean bl) {
        if (this.d) {
            return;
        }
        if (bl) {
            this.c.d();
            return;
        }
        this.c.q();
    }

    @Override
    public int getId() {
        return this.e;
    }

    public void h(boolean bl) {
        this.d = bl;
    }

    @Override
    public boolean i() {
        return false;
    }

    @Override
    public Parcelable j() {
        SavedState savedState = new SavedState();
        savedState.c = this.c.getSelectedItemId();
        savedState.d = b.d(this.c.getBadgeDrawables());
        return savedState;
    }

    @Override
    public boolean k(e e3, g g3) {
        return false;
    }

    @Override
    public boolean l(e e3, g g3) {
        return false;
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] b(int n3) {
                return new SavedState[n3];
            }
        };
        public int c;
        public ParcelableSparseArray d;

        public SavedState() {
        }

        public SavedState(Parcel parcel) {
            this.c = parcel.readInt();
            this.d = (ParcelableSparseArray)parcel.readParcelable(this.getClass().getClassLoader());
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n3) {
            parcel.writeInt(this.c);
            parcel.writeParcelable((Parcelable)this.d, 0);
        }
    }
}

