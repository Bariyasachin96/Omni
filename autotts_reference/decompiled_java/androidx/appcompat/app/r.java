/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.location.Location
 *  android.location.LocationManager
 */
package androidx.appcompat.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import androidx.appcompat.app.q;
import e0.d;
import java.util.Calendar;

public class r {
    public static r d;
    public final Context a;
    public final LocationManager b;
    public final a c = new a();

    public r(Context context, LocationManager locationManager) {
        this.a = context;
        this.b = locationManager;
    }

    public static r a(Context context) {
        if (d == null) {
            context = context.getApplicationContext();
            d = new r(context, (LocationManager)context.getSystemService("location"));
        }
        return d;
    }

    public final Location b() {
        int n3 = e0.d.b(this.a, "android.permission.ACCESS_COARSE_LOCATION");
        Location location = null;
        Location location2 = n3 == 0 ? this.c("network") : null;
        if (e0.d.b(this.a, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            location = this.c("gps");
        }
        if (location != null && location2 != null) {
            if (location.getTime() > location2.getTime()) {
                return location;
            }
            return location2;
        }
        if (location != null) {
            return location;
        }
        return location2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final Location c(String string) {
        try {
            if (!this.b.isProviderEnabled(string)) return null;
            return this.b.getLastKnownLocation(string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public boolean d() {
        a a4 = this.c;
        if (this.e()) {
            return a4.a;
        }
        Location location = this.b();
        if (location != null) {
            this.f(location);
            return a4.a;
        }
        int n3 = Calendar.getInstance().get(11);
        return n3 < 6 || n3 >= 22;
        {
        }
    }

    public final boolean e() {
        return this.c.b > System.currentTimeMillis();
    }

    public final void f(Location location) {
        a a4 = this.c;
        long l3 = System.currentTimeMillis();
        q q3 = q.b();
        q3.a(l3 - 86400000L, location.getLatitude(), location.getLongitude());
        q3.a(l3, location.getLatitude(), location.getLongitude());
        int n3 = q3.c;
        boolean bl = true;
        if (n3 != 1) {
            bl = false;
        }
        long l4 = q3.b;
        long l5 = q3.a;
        q3.a(l3 + 86400000L, location.getLatitude(), location.getLongitude());
        long l6 = q3.b;
        if (l4 != -1L && l5 != -1L) {
            if (l3 <= l5) {
                l6 = l4;
                if (l3 > l4) {
                    l6 = l5;
                }
            }
            l6 += 60000L;
        } else {
            l6 = l3 + 43200000L;
        }
        a4.a = bl;
        a4.b = l6;
    }

    public static class a {
        public boolean a;
        public long b;
    }
}

