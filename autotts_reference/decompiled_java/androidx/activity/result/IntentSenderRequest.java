/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package androidx.activity.result;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import o3.g;
import o3.k;

public final class IntentSenderRequest
implements Parcelable {
    public static final Parcelable.Creator<IntentSenderRequest> CREATOR;
    public static final c g;
    public final IntentSender c;
    public final Intent d;
    public final int e;
    public final int f;

    static {
        g = new c(null);
        CREATOR = new Parcelable.Creator(){

            public IntentSenderRequest a(Parcel parcel) {
                k.e(parcel, "inParcel");
                return new IntentSenderRequest(parcel);
            }

            public IntentSenderRequest[] b(int n3) {
                return new IntentSenderRequest[n3];
            }
        };
    }

    public IntentSenderRequest(IntentSender intentSender, Intent intent, int n3, int n4) {
        k.e(intentSender, "intentSender");
        this.c = intentSender;
        this.d = intent;
        this.e = n3;
        this.f = n4;
    }

    public IntentSenderRequest(Parcel parcel) {
        k.e(parcel, "parcel");
        Parcelable parcelable = parcel.readParcelable(IntentSender.class.getClassLoader());
        k.b(parcelable);
        this((IntentSender)parcelable, (Intent)parcel.readParcelable(Intent.class.getClassLoader()), parcel.readInt(), parcel.readInt());
    }

    public int describeContents() {
        return 0;
    }

    public final Intent o() {
        return this.d;
    }

    public final int p() {
        return this.e;
    }

    public final int q() {
        return this.f;
    }

    public final IntentSender r() {
        return this.c;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        k.e(parcel, "dest");
        parcel.writeParcelable((Parcelable)this.c, n3);
        parcel.writeParcelable((Parcelable)this.d, n3);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
    }

    public static final class a {
        public final IntentSender a;
        public Intent b;
        public int c;
        public int d;

        public a(IntentSender intentSender) {
            k.e(intentSender, "intentSender");
            this.a = intentSender;
        }

        public final IntentSenderRequest a() {
            return new IntentSenderRequest(this.a, this.b, this.c, this.d);
        }

        public final a b(Intent intent) {
            this.b = intent;
            return this;
        }

        public final a c(int n3, int n4) {
            this.d = n3;
            this.c = n4;
            return this;
        }
    }

    public static final class c {
        public c() {
        }

        public /* synthetic */ c(g g3) {
            this();
        }
    }
}

