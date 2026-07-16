/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.text.style.ClickableSpan
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeProvider
 */
package o0;

import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import b0.b;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import o0.x0;
import p0.s;
import p0.t;

public class a {
    public static final View.AccessibilityDelegate c = new View.AccessibilityDelegate();
    public final View.AccessibilityDelegate a;
    public final View.AccessibilityDelegate b;

    public a() {
        this(c);
    }

    public a(View.AccessibilityDelegate accessibilityDelegate) {
        this.a = accessibilityDelegate;
        this.b = new a(this);
    }

    public static List c(View object) {
        List list = (List)object.getTag(b0.b.tag_accessibility_actions);
        object = list;
        if (list == null) {
            object = Collections.EMPTY_LIST;
        }
        return object;
    }

    public boolean a(View view, AccessibilityEvent accessibilityEvent) {
        return this.a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public t b(View view) {
        if ((view = this.a.getAccessibilityNodeProvider(view)) != null) {
            return new t(view);
        }
        return null;
    }

    public View.AccessibilityDelegate d() {
        return this.b;
    }

    public final boolean e(ClickableSpan clickableSpan, View clickableSpanArray) {
        if (clickableSpan != null) {
            clickableSpanArray = s.p(clickableSpanArray.createAccessibilityNodeInfo().getText());
            for (int i3 = 0; clickableSpanArray != null && i3 < clickableSpanArray.length; ++i3) {
                if (!clickableSpan.equals(clickableSpanArray[i3])) continue;
                return true;
            }
        }
        return false;
    }

    public void f(View view, AccessibilityEvent accessibilityEvent) {
        this.a.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public void g(View view, s s3) {
        this.a.onInitializeAccessibilityNodeInfo(view, s3.K0());
    }

    public void h(View view, AccessibilityEvent accessibilityEvent) {
        this.a.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public boolean i(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return this.a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    public boolean j(View view, int n3, Bundle bundle) {
        boolean bl;
        List list = o0.a.c(view);
        boolean bl2 = false;
        int n4 = 0;
        while (true) {
            bl = bl2;
            if (n4 >= list.size()) break;
            s.a a4 = (s.a)list.get(n4);
            if (a4.b() == n3) {
                bl = a4.d(view, bundle);
                break;
            }
            ++n4;
        }
        bl2 = bl;
        if (!bl) {
            bl2 = this.a.performAccessibilityAction(view, n3, bundle);
        }
        if (!bl2 && n3 == b0.b.accessibility_action_clickable_span && bundle != null) {
            return this.k(bundle.getInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", -1), view);
        }
        return bl2;
    }

    public final boolean k(int n3, View view) {
        Object object = (SparseArray)view.getTag(b0.b.tag_accessibility_clickable_spans);
        if (object != null && (object = (WeakReference)object.get(n3)) != null && this.e((ClickableSpan)(object = (ClickableSpan)((Reference)object).get()), view)) {
            object.onClick(view);
            return true;
        }
        return false;
    }

    public void l(View view, int n3) {
        this.a.sendAccessibilityEvent(view, n3);
    }

    public void m(View view, AccessibilityEvent accessibilityEvent) {
        this.a.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }

    public static final class a
    extends View.AccessibilityDelegate {
        public final a a;

        public a(a a4) {
            this.a = a4;
        }

        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            return this.a.a(view, accessibilityEvent);
        }

        public AccessibilityNodeProvider getAccessibilityNodeProvider(View object) {
            if ((object = this.a.b((View)object)) != null) {
                return (AccessibilityNodeProvider)((t)object).e();
            }
            return null;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            this.a.f(view, accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfo accessibilityNodeInfo) {
            s s3 = s.L0(accessibilityNodeInfo);
            s3.A0(x0.P((View)object));
            s3.q0(x0.M((View)object));
            s3.v0(x0.n((View)object));
            s3.G0(x0.E((View)object));
            this.a.g((View)object, s3);
            s3.e(accessibilityNodeInfo.getText(), (View)object);
            object = o0.a.c((View)object);
            for (int i3 = 0; i3 < object.size(); ++i3) {
                s3.b((s.a)object.get(i3));
            }
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            this.a.h(view, accessibilityEvent);
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return this.a.i(viewGroup, view, accessibilityEvent);
        }

        public boolean performAccessibilityAction(View view, int n3, Bundle bundle) {
            return this.a.j(view, n3, bundle);
        }

        public void sendAccessibilityEvent(View view, int n3) {
            this.a.l(view, n3);
        }

        public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            this.a.m(view, accessibilityEvent);
        }
    }
}

