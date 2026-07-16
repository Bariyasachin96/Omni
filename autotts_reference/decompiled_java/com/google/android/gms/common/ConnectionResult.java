/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import r1.a;
import u1.a;
import v1.b;

public final class ConnectionResult
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ConnectionResult> CREATOR;
    public static final ConnectionResult h;
    public final int c;
    public final int d;
    public final PendingIntent e;
    public final String f;
    public final Integer g;

    static {
        h = new ConnectionResult(0);
        CREATOR = new a();
    }

    public ConnectionResult(int n3) {
        this(n3, null, null);
    }

    public ConnectionResult(int n3, int n4, PendingIntent pendingIntent, String string, Integer n5) {
        this.c = n3;
        this.d = n4;
        this.e = pendingIntent;
        this.f = string;
        this.g = n5;
    }

    public ConnectionResult(int n3, PendingIntent pendingIntent, String string) {
        this(1, n3, pendingIntent, string, null);
    }

    public static String s(int n3) {
        if (n3 != 99) {
            if (n3 != 1500) {
                switch (n3) {
                    default: {
                        switch (n3) {
                            default: {
                                StringBuilder stringBuilder = new StringBuilder(String.valueOf(n3).length() + 20);
                                stringBuilder.append("UNKNOWN_ERROR_CODE(");
                                stringBuilder.append(n3);
                                stringBuilder.append(")");
                                return stringBuilder.toString();
                            }
                            case 25: {
                                return "API_INSTALL_REQUIRED";
                            }
                            case 24: {
                                return "API_DISABLED_FOR_CONNECTION";
                            }
                            case 23: {
                                return "API_DISABLED";
                            }
                            case 22: {
                                return "RESOLUTION_ACTIVITY_NOT_FOUND";
                            }
                            case 21: {
                                return "API_VERSION_UPDATE_REQUIRED";
                            }
                            case 20: {
                                return "RESTRICTED_PROFILE";
                            }
                            case 19: {
                                return "SERVICE_MISSING_PERMISSION";
                            }
                            case 18: {
                                return "SERVICE_UPDATING";
                            }
                            case 17: {
                                return "SIGN_IN_FAILED";
                            }
                            case 16: {
                                return "API_UNAVAILABLE";
                            }
                            case 15: {
                                return "INTERRUPTED";
                            }
                            case 14: {
                                return "TIMEOUT";
                            }
                            case 13: 
                        }
                        return "CANCELED";
                    }
                    case 11: {
                        return "LICENSE_CHECK_FAILED";
                    }
                    case 10: {
                        return "DEVELOPER_ERROR";
                    }
                    case 9: {
                        return "SERVICE_INVALID";
                    }
                    case 8: {
                        return "INTERNAL_ERROR";
                    }
                    case 7: {
                        return "NETWORK_ERROR";
                    }
                    case 6: {
                        return "RESOLUTION_REQUIRED";
                    }
                    case 5: {
                        return "INVALID_ACCOUNT";
                    }
                    case 4: {
                        return "SIGN_IN_REQUIRED";
                    }
                    case 3: {
                        return "SERVICE_DISABLED";
                    }
                    case 2: {
                        return "SERVICE_VERSION_UPDATE_REQUIRED";
                    }
                    case 1: {
                        return "SERVICE_MISSING";
                    }
                    case 0: {
                        return "SUCCESS";
                    }
                    case -1: 
                }
                return "UNKNOWN";
            }
            return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
        }
        return "UNFINISHED";
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ConnectionResult)) {
            return false;
        }
        object = (ConnectionResult)object;
        return this.d == ((ConnectionResult)object).d && u1.a.a(this.e, ((ConnectionResult)object).e) && u1.a.a(this.f, ((ConnectionResult)object).f) && u1.a.a(this.g, ((ConnectionResult)object).g);
    }

    public int hashCode() {
        return u1.a.b(this.d, this.e, this.f, this.g);
    }

    public Integer o() {
        return this.g;
    }

    public int p() {
        return this.d;
    }

    public String q() {
        return this.f;
    }

    public PendingIntent r() {
        return this.e;
    }

    public String toString() {
        a.a a4 = u1.a.c(this);
        a4.a("statusCode", ConnectionResult.s(this.d));
        a4.a("resolution", this.e);
        a4.a("message", this.f);
        a4.a("clientMethodKey", this.g);
        return a4.toString();
    }

    public void writeToParcel(Parcel parcel, int n3) {
        int n4 = this.c;
        int n5 = b.a(parcel);
        b.g(parcel, 1, n4);
        b.g(parcel, 2, this.p());
        b.k(parcel, 3, (Parcelable)this.r(), n3, false);
        b.l(parcel, 4, this.q(), false);
        b.i(parcel, 5, this.o(), false);
        b.b(parcel, n5);
    }
}

