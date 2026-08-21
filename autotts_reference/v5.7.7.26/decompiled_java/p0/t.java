/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeProvider
 */
package p0;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.ArrayList;
import java.util.List;
import p0.s;

public class t {
    public final Object a;

    public t() {
        this.a = new b(this);
    }

    public t(Object object) {
        this.a = object;
    }

    public void a(int n3, s s3, String string, Bundle bundle) {
    }

    public s b(int n3) {
        return null;
    }

    public List c(String string, int n3) {
        return null;
    }

    public s d(int n3) {
        return null;
    }

    public Object e() {
        return this.a;
    }

    public boolean f(int n3, int n4, Bundle bundle) {
        return false;
    }

    public static abstract class a
    extends AccessibilityNodeProvider {
        public final t a;

        public a(t t3) {
            this.a = t3;
        }

        public AccessibilityNodeInfo createAccessibilityNodeInfo(int n3) {
            s s3 = this.a.b(n3);
            if (s3 == null) {
                return null;
            }
            return s3.K0();
        }

        public List findAccessibilityNodeInfosByText(String object, int n3) {
            if ((object = this.a.c((String)object, n3)) == null) {
                return null;
            }
            ArrayList<AccessibilityNodeInfo> arrayList = new ArrayList<AccessibilityNodeInfo>();
            int n4 = object.size();
            for (n3 = 0; n3 < n4; ++n3) {
                arrayList.add(((s)object.get(n3)).K0());
            }
            return arrayList;
        }

        public AccessibilityNodeInfo findFocus(int n3) {
            s s3 = this.a.d(n3);
            if (s3 == null) {
                return null;
            }
            return s3.K0();
        }

        public boolean performAction(int n3, int n4, Bundle bundle) {
            return this.a.f(n3, n4, bundle);
        }
    }

    public static class b
    extends a {
        public b(t t3) {
            super(t3);
        }

        public void addExtraDataToAccessibilityNodeInfo(int n3, AccessibilityNodeInfo accessibilityNodeInfo, String string, Bundle bundle) {
            this.a.a(n3, s.L0(accessibilityNodeInfo), string, bundle);
        }
    }
}

