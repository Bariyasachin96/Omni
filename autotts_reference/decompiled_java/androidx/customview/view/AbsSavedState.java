/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package androidx.customview.view;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbsSavedState
implements Parcelable {
    public static final Parcelable.Creator<AbsSavedState> CREATOR;
    public static final AbsSavedState d;
    public final Parcelable c;

    static {
        d = new AbsSavedState(){};
        CREATOR = new Parcelable.ClassLoaderCreator(){

            public AbsSavedState a(Parcel parcel) {
                return this.b(parcel, null);
            }

            public AbsSavedState b(Parcel parcel, ClassLoader classLoader) {
                if (parcel.readParcelable(classLoader) == null) {
                    return d;
                }
                throw new IllegalStateException("superState must be null");
            }

            public AbsSavedState[] c(int n3) {
                return new AbsSavedState[n3];
            }
        };
    }

    public AbsSavedState() {
        this.c = null;
    }

    public AbsSavedState(Parcel object, ClassLoader classLoader) {
        object = object.readParcelable(classLoader);
        if (object == null) {
            object = d;
        }
        this.c = object;
    }

    public AbsSavedState(Parcelable parcelable) {
        if (parcelable != null) {
            if (parcelable == d) {
                parcelable = null;
            }
            this.c = parcelable;
            return;
        }
        throw new IllegalArgumentException("superState must not be null");
    }

    public /* synthetic */ AbsSavedState(_1 var1_1) {
        this();
    }

    public int describeContents() {
        return 0;
    }

    public final Parcelable o() {
        return this.c;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeParcelable(this.c, n3);
    }
}

