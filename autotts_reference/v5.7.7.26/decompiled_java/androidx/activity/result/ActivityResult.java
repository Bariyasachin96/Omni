/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package androidx.activity.result;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public final class ActivityResult
implements Parcelable {
    public static final Parcelable.Creator<ActivityResult> CREATOR = new Parcelable.Creator(){

        public ActivityResult a(Parcel parcel) {
            return new ActivityResult(parcel);
        }

        public ActivityResult[] b(int n3) {
            return new ActivityResult[n3];
        }
    };
    public final int c;
    public final Intent d;

    public ActivityResult(int n3, Intent intent) {
        this.c = n3;
        this.d = intent;
    }

    public ActivityResult(Parcel object) {
        this.c = object.readInt();
        object = object.readInt() == 0 ? null : (Intent)Intent.CREATOR.createFromParcel(object);
        this.d = object;
    }

    public static String q(int n3) {
        if (n3 != -1) {
            if (n3 != 0) {
                return String.valueOf(n3);
            }
            return "RESULT_CANCELED";
        }
        return "RESULT_OK";
    }

    public int describeContents() {
        return 0;
    }

    public Intent o() {
        return this.d;
    }

    public int p() {
        return this.c;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ActivityResult{resultCode=");
        stringBuilder.append(ActivityResult.q(this.c));
        stringBuilder.append(", data=");
        stringBuilder.append(this.d);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeInt(this.c);
        int n4 = this.d == null ? 0 : 1;
        parcel.writeInt(n4);
        Intent intent = this.d;
        if (intent != null) {
            intent.writeToParcel(parcel, n3);
        }
    }
}

