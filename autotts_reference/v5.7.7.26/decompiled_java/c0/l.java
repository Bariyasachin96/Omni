/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Action
 *  android.app.Notification$Action$Builder
 *  android.app.Notification$BubbleMetadata
 *  android.app.Notification$Builder
 *  android.app.PendingIntent
 *  android.app.RemoteInput
 *  android.content.Context
 *  android.content.LocusId
 *  android.graphics.drawable.Icon
 *  android.media.AudioAttributes
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.widget.RemoteViews
 */
package c0;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.LocusId;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.appcompat.app.s;
import androidx.core.graphics.drawable.IconCompat;
import c0.i;
import c0.j;
import c0.k;
import c0.m;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class l {
    public final Context a;
    public final Notification.Builder b;
    public final k c;
    public RemoteViews d;
    public RemoteViews e;
    public final List f = new ArrayList();
    public final Bundle g = new Bundle();
    public int h;
    public RemoteViews i;

    public l(k k3) {
        int n3;
        this.c = k3;
        Object object = k3.a;
        this.a = object;
        Object object2 = c0.l$e.a((Context)object, k3.J);
        this.b = object2;
        Notification notification = k3.Q;
        Object object3 = object2.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, k3.i).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
        boolean bl = (notification.flags & 2) != 0;
        object3 = object3.setOngoing(bl);
        bl = (notification.flags & 8) != 0;
        object3 = object3.setOnlyAlertOnce(bl);
        bl = (notification.flags & 0x10) != 0;
        Object object4 = object3.setAutoCancel(bl).setDefaults(notification.defaults).setContentTitle(k3.e).setContentText(k3.f).setContentInfo(k3.k).setContentIntent(k3.g).setDeleteIntent(notification.deleteIntent);
        object3 = k3.h;
        bl = (notification.flags & 0x80) != 0;
        object4.setFullScreenIntent((PendingIntent)object3, bl).setNumber(k3.l).setProgress(k3.s, k3.t, k3.u);
        object3 = k3.j;
        object = object3 == null ? null : ((IconCompat)object3).g((Context)object);
        c0.l$c.b((Notification.Builder)object2, (Icon)object);
        object2.setSubText(k3.p).setUsesChronometer(k3.o).setPriority(k3.m);
        object2 = k3.b;
        int n4 = ((ArrayList)object2).size();
        for (n3 = 0; n3 < n4; ++n3) {
            object = ((ArrayList)object2).get(n3);
            s.a(object);
            this.a(null);
        }
        object = k3.C;
        if (object != null) {
            this.g.putAll((Bundle)object);
        }
        n3 = Build.VERSION.SDK_INT;
        this.d = k3.G;
        this.e = k3.H;
        this.b.setShowWhen(k3.n);
        c0.l$a.i(this.b, k3.y);
        c0.l$a.g(this.b, k3.v);
        c0.l$a.j(this.b, k3.x);
        c0.l$a.h(this.b, k3.w);
        this.h = k3.N;
        c0.l$b.b(this.b, k3.B);
        c0.l$b.c(this.b, k3.D);
        c0.l$b.f(this.b, k3.E);
        c0.l$b.d(this.b, k3.F);
        c0.l$b.e(this.b, notification.sound, notification.audioAttributes);
        object = n3 < 28 ? l.d(l.e(k3.c), k3.T) : k3.T;
        if (object != null && !object.isEmpty()) {
            object2 = object.iterator();
            while (object2.hasNext()) {
                object = (String)object2.next();
                c0.l$b.a(this.b, (String)object);
            }
        }
        this.i = k3.I;
        if (k3.d.size() > 0) {
            object = object2 = k3.b().getBundle("android.car.EXTENSIONS");
            if (object2 == null) {
                object = new Bundle();
            }
            object3 = new Bundle((Bundle)object);
            object2 = new Bundle();
            for (n3 = 0; n3 < k3.d.size(); ++n3) {
                object4 = Integer.toString(n3);
                s.a(k3.d.get(n3));
                object2.putBundle((String)object4, m.a(null));
            }
            object.putBundle("invisible_actions", (Bundle)object2);
            object3.putBundle("invisible_actions", (Bundle)object2);
            k3.b().putBundle("android.car.EXTENSIONS", (Bundle)object);
            this.g.putBundle("android.car.EXTENSIONS", (Bundle)object3);
        }
        n3 = Build.VERSION.SDK_INT;
        object = k3.S;
        if (object != null) {
            c0.l$c.c(this.b, object);
        }
        this.b.setExtras(k3.C);
        c0.l$d.e(this.b, k3.r);
        object = k3.G;
        if (object != null) {
            c0.l$d.c(this.b, (RemoteViews)object);
        }
        if ((object = k3.H) != null) {
            c0.l$d.b(this.b, (RemoteViews)object);
        }
        if ((object = k3.I) != null) {
            c0.l$d.d(this.b, (RemoteViews)object);
        }
        c0.l$e.b(this.b, k3.K);
        c0.l$e.e(this.b, k3.q);
        c0.l$e.f(this.b, k3.L);
        c0.l$e.g(this.b, k3.M);
        c0.l$e.d(this.b, k3.N);
        if (k3.A) {
            c0.l$e.c(this.b, k3.z);
        }
        if (!TextUtils.isEmpty((CharSequence)k3.J)) {
            this.b.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
        }
        if (n3 >= 28 && (object = k3.c.iterator()).hasNext()) {
            s.a(object.next());
            throw null;
        }
        if (n3 >= 29) {
            c0.l$f.a(this.b, k3.P);
            c0.l$f.b(this.b, j.a(null));
        }
        if (n3 >= 31 && (n3 = k3.O) != 0) {
            c0.l$g.b(this.b, n3);
        }
        if (k3.R) {
            this.h = this.c.w ? 2 : 1;
            this.b.setVibrate(null);
            this.b.setSound(null);
            notification.defaults = n3 = notification.defaults & 0xFFFFFFFC;
            this.b.setDefaults(n3);
            if (TextUtils.isEmpty((CharSequence)this.c.v)) {
                c0.l$a.g(this.b, "silent");
            }
            c0.l$e.d(this.b, this.h);
        }
    }

    public static List d(List list, List list2) {
        if (list == null) {
            return list2;
        }
        if (list2 == null) {
            return list;
        }
        o.b b3 = new o.b(list.size() + list2.size());
        b3.addAll((Collection)list);
        b3.addAll((Collection)list2);
        return new ArrayList(b3);
    }

    public static List e(List object) {
        if (object == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(object.size());
        if (!(object = object.iterator()).hasNext()) {
            return arrayList;
        }
        s.a(object.next());
        throw null;
    }

    public final void a(i i3) {
        throw null;
    }

    public Notification b() {
        this.c.getClass();
        Notification notification = this.c();
        RemoteViews remoteViews = this.c.G;
        if (remoteViews != null) {
            notification.contentView = remoteViews;
        }
        return notification;
    }

    public Notification c() {
        return this.b.build();
    }

    public static abstract class a {
        public static Notification.Builder a(Notification.Builder builder, Notification.Action action) {
            return builder.addAction(action);
        }

        public static Notification.Action.Builder b(Notification.Action.Builder builder, Bundle bundle) {
            return builder.addExtras(bundle);
        }

        public static Notification.Action.Builder c(Notification.Action.Builder builder, RemoteInput remoteInput) {
            return builder.addRemoteInput(remoteInput);
        }

        public static Notification.Action d(Notification.Action.Builder builder) {
            return builder.build();
        }

        public static Notification.Action.Builder e(int n3, CharSequence charSequence, PendingIntent pendingIntent) {
            return new Notification.Action.Builder(n3, charSequence, pendingIntent);
        }

        public static String f(Notification notification) {
            return notification.getGroup();
        }

        public static Notification.Builder g(Notification.Builder builder, String string) {
            return builder.setGroup(string);
        }

        public static Notification.Builder h(Notification.Builder builder, boolean bl) {
            return builder.setGroupSummary(bl);
        }

        public static Notification.Builder i(Notification.Builder builder, boolean bl) {
            return builder.setLocalOnly(bl);
        }

        public static Notification.Builder j(Notification.Builder builder, String string) {
            return builder.setSortKey(string);
        }
    }

    public static abstract class b {
        public static Notification.Builder a(Notification.Builder builder, String string) {
            return builder.addPerson(string);
        }

        public static Notification.Builder b(Notification.Builder builder, String string) {
            return builder.setCategory(string);
        }

        public static Notification.Builder c(Notification.Builder builder, int n3) {
            return builder.setColor(n3);
        }

        public static Notification.Builder d(Notification.Builder builder, Notification notification) {
            return builder.setPublicVersion(notification);
        }

        public static Notification.Builder e(Notification.Builder builder, Uri uri, Object object) {
            return builder.setSound(uri, (AudioAttributes)object);
        }

        public static Notification.Builder f(Notification.Builder builder, int n3) {
            return builder.setVisibility(n3);
        }
    }

    public static abstract class c {
        public static Notification.Action.Builder a(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
            return new Notification.Action.Builder(icon, charSequence, pendingIntent);
        }

        public static Notification.Builder b(Notification.Builder builder, Icon icon) {
            return builder.setLargeIcon(icon);
        }

        public static Notification.Builder c(Notification.Builder builder, Object object) {
            return builder.setSmallIcon((Icon)object);
        }
    }

    public static abstract class d {
        public static Notification.Action.Builder a(Notification.Action.Builder builder, boolean bl) {
            return builder.setAllowGeneratedReplies(bl);
        }

        public static Notification.Builder b(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomBigContentView(remoteViews);
        }

        public static Notification.Builder c(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomContentView(remoteViews);
        }

        public static Notification.Builder d(Notification.Builder builder, RemoteViews remoteViews) {
            return builder.setCustomHeadsUpContentView(remoteViews);
        }

        public static Notification.Builder e(Notification.Builder builder, CharSequence[] charSequenceArray) {
            return builder.setRemoteInputHistory(charSequenceArray);
        }
    }

    public static abstract class e {
        public static Notification.Builder a(Context context, String string) {
            return new Notification.Builder(context, string);
        }

        public static Notification.Builder b(Notification.Builder builder, int n3) {
            return builder.setBadgeIconType(n3);
        }

        public static Notification.Builder c(Notification.Builder builder, boolean bl) {
            return builder.setColorized(bl);
        }

        public static Notification.Builder d(Notification.Builder builder, int n3) {
            return builder.setGroupAlertBehavior(n3);
        }

        public static Notification.Builder e(Notification.Builder builder, CharSequence charSequence) {
            return builder.setSettingsText(charSequence);
        }

        public static Notification.Builder f(Notification.Builder builder, String string) {
            return builder.setShortcutId(string);
        }

        public static Notification.Builder g(Notification.Builder builder, long l3) {
            return builder.setTimeoutAfter(l3);
        }
    }

    public static abstract class f {
        public static Notification.Builder a(Notification.Builder builder, boolean bl) {
            return builder.setAllowSystemGeneratedContextualActions(bl);
        }

        public static Notification.Builder b(Notification.Builder builder, Notification.BubbleMetadata bubbleMetadata) {
            return builder.setBubbleMetadata(bubbleMetadata);
        }

        public static Notification.Action.Builder c(Notification.Action.Builder builder, boolean bl) {
            return builder.setContextual(bl);
        }

        public static Notification.Builder d(Notification.Builder builder, Object object) {
            return builder.setLocusId((LocusId)object);
        }
    }

    public static abstract class g {
        public static Notification.Action.Builder a(Notification.Action.Builder builder, boolean bl) {
            return builder.setAuthenticationRequired(bl);
        }

        public static Notification.Builder b(Notification.Builder builder, int n3) {
            return builder.setForegroundServiceBehavior(n3);
        }
    }
}

