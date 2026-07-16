/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.SpannableString
 *  android.text.Spanned
 *  android.text.TextUtils
 *  android.text.style.ClickableSpan
 *  android.util.Log
 *  android.view.View
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionInfo
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionItemInfo
 *  android.view.accessibility.AccessibilityNodeInfo$CollectionItemInfo$Builder
 *  android.view.accessibility.AccessibilityNodeInfo$ExtraRenderingInfo
 *  android.view.accessibility.AccessibilityNodeInfo$RangeInfo
 */
package p0;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p0.h;
import p0.i;
import p0.j;
import p0.k;
import p0.l;
import p0.m;
import p0.n;
import p0.o;
import p0.p;
import p0.q;
import p0.r;
import p0.v;

public class s {
    public final AccessibilityNodeInfo a;
    public int b = -1;
    public int c = -1;

    public s(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.a = accessibilityNodeInfo;
    }

    public s(Object object) {
        this.a = (AccessibilityNodeInfo)object;
    }

    public static s L0(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new s(accessibilityNodeInfo);
    }

    public static s M0(Object object) {
        if (object != null) {
            return new s(object);
        }
        return null;
    }

    public static s U() {
        return s.L0(AccessibilityNodeInfo.obtain());
    }

    public static s V(View view) {
        return s.L0(AccessibilityNodeInfo.obtain((View)view));
    }

    public static s W(s s3) {
        return s.L0(AccessibilityNodeInfo.obtain((AccessibilityNodeInfo)s3.a));
    }

    public static String h(int n3) {
        if (n3 != 1) {
            if (n3 != 2) {
                switch (n3) {
                    default: {
                        switch (n3) {
                            default: {
                                switch (n3) {
                                    default: {
                                        switch (n3) {
                                            default: {
                                                return "ACTION_UNKNOWN";
                                            }
                                            case 16908375: {
                                                return "ACTION_DRAG_CANCEL";
                                            }
                                            case 16908374: {
                                                return "ACTION_DRAG_DROP";
                                            }
                                            case 16908373: {
                                                return "ACTION_DRAG_START";
                                            }
                                            case 16908372: 
                                        }
                                        return "ACTION_IME_ENTER";
                                    }
                                    case 16908362: {
                                        return "ACTION_PRESS_AND_HOLD";
                                    }
                                    case 16908361: {
                                        return "ACTION_PAGE_RIGHT";
                                    }
                                    case 16908360: {
                                        return "ACTION_PAGE_LEFT";
                                    }
                                    case 16908359: {
                                        return "ACTION_PAGE_DOWN";
                                    }
                                    case 16908358: {
                                        return "ACTION_PAGE_UP";
                                    }
                                    case 16908357: {
                                        return "ACTION_HIDE_TOOLTIP";
                                    }
                                    case 16908356: 
                                }
                                return "ACTION_SHOW_TOOLTIP";
                            }
                            case 16908349: {
                                return "ACTION_SET_PROGRESS";
                            }
                            case 16908348: {
                                return "ACTION_CONTEXT_CLICK";
                            }
                            case 16908347: {
                                return "ACTION_SCROLL_RIGHT";
                            }
                            case 16908346: {
                                return "ACTION_SCROLL_DOWN";
                            }
                            case 16908345: {
                                return "ACTION_SCROLL_LEFT";
                            }
                            case 16908344: {
                                return "ACTION_SCROLL_UP";
                            }
                            case 16908343: {
                                return "ACTION_SCROLL_TO_POSITION";
                            }
                            case 16908342: 
                        }
                        return "ACTION_SHOW_ON_SCREEN";
                    }
                    case 16908382: {
                        return "ACTION_SCROLL_IN_DIRECTION";
                    }
                    case 16908354: {
                        return "ACTION_MOVE_WINDOW";
                    }
                    case 0x200000: {
                        return "ACTION_SET_TEXT";
                    }
                    case 524288: {
                        return "ACTION_COLLAPSE";
                    }
                    case 262144: {
                        return "ACTION_EXPAND";
                    }
                    case 131072: {
                        return "ACTION_SET_SELECTION";
                    }
                    case 65536: {
                        return "ACTION_CUT";
                    }
                    case 32768: {
                        return "ACTION_PASTE";
                    }
                    case 16384: {
                        return "ACTION_COPY";
                    }
                    case 8192: {
                        return "ACTION_SCROLL_BACKWARD";
                    }
                    case 4096: {
                        return "ACTION_SCROLL_FORWARD";
                    }
                    case 2048: {
                        return "ACTION_PREVIOUS_HTML_ELEMENT";
                    }
                    case 1024: {
                        return "ACTION_NEXT_HTML_ELEMENT";
                    }
                    case 512: {
                        return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
                    }
                    case 256: {
                        return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
                    }
                    case 128: {
                        return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
                    }
                    case 64: {
                        return "ACTION_ACCESSIBILITY_FOCUS";
                    }
                    case 32: {
                        return "ACTION_LONG_CLICK";
                    }
                    case 16: {
                        return "ACTION_CLICK";
                    }
                    case 8: {
                        return "ACTION_CLEAR_SELECTION";
                    }
                    case 4: 
                }
                return "ACTION_SELECT";
            }
            return "ACTION_CLEAR_FOCUS";
        }
        return "ACTION_FOCUS";
    }

    public static ClickableSpan[] p(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return (ClickableSpan[])((Spanned)charSequence).getSpans(0, charSequence.length(), ClickableSpan.class);
        }
        return null;
    }

    public String A() {
        return this.a.getViewIdResourceName();
    }

    public void A0(boolean bl) {
        if (Build.VERSION.SDK_INT >= 28) {
            p0.c.a(this.a, bl);
            return;
        }
        this.b0(1, bl);
    }

    public final boolean B() {
        return this.f("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").isEmpty() ^ true;
    }

    public void B0(boolean bl) {
        this.a.setScrollable(bl);
    }

    public boolean C() {
        if (Build.VERSION.SDK_INT >= 34) {
            return d.f(this.a);
        }
        return this.j(64);
    }

    public void C0(boolean bl) {
        this.a.setSelected(bl);
    }

    public boolean D() {
        return this.a.isAccessibilityFocused();
    }

    public void D0(boolean bl) {
        this.a.setShowingHintText(bl);
    }

    public boolean E() {
        return this.a.isCheckable();
    }

    public void E0(View view) {
        this.c = -1;
        this.a.setSource(view);
    }

    public boolean F() {
        return this.a.isChecked();
    }

    public void F0(View view, int n3) {
        this.c = n3;
        this.a.setSource(view, n3);
    }

    public boolean G() {
        return this.a.isClickable();
    }

    public void G0(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 30) {
            p0.s$b.c(this.a, charSequence);
            return;
        }
        this.a.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.STATE_DESCRIPTION_KEY", charSequence);
    }

    public boolean H() {
        return this.a.isContextClickable();
    }

    public void H0(CharSequence charSequence) {
        this.a.setText(charSequence);
    }

    public boolean I() {
        return this.a.isEnabled();
    }

    public void I0(View view) {
        this.a.setTraversalAfter(view);
    }

    public boolean J() {
        return this.a.isFocusable();
    }

    public void J0(boolean bl) {
        this.a.setVisibleToUser(bl);
    }

    public boolean K() {
        return this.a.isFocused();
    }

    public AccessibilityNodeInfo K0() {
        return this.a;
    }

    public boolean L() {
        return this.j(0x4000000);
    }

    public boolean M() {
        return this.a.isImportantForAccessibility();
    }

    public boolean N() {
        return this.a.isLongClickable();
    }

    public boolean O() {
        return this.a.isPassword();
    }

    public boolean P() {
        return this.a.isScrollable();
    }

    public boolean Q() {
        return this.a.isSelected();
    }

    public boolean R() {
        return this.a.isShowingHintText();
    }

    public boolean S() {
        if (Build.VERSION.SDK_INT >= 33) {
            return p0.s$c.h(this.a);
        }
        return this.j(0x800000);
    }

    public boolean T() {
        return this.a.isVisibleToUser();
    }

    public boolean X(int n3, Bundle bundle) {
        return this.a.performAction(n3, bundle);
    }

    public void Y() {
    }

    public boolean Z(a a4) {
        return this.a.removeAction((AccessibilityNodeInfo.AccessibilityAction)a4.a);
    }

    public void a(int n3) {
        this.a.addAction(n3);
    }

    public void a0(boolean bl) {
        this.a.setAccessibilityFocused(bl);
    }

    public void b(a a4) {
        this.a.addAction((AccessibilityNodeInfo.AccessibilityAction)a4.a);
    }

    public final void b0(int n3, boolean bl) {
        Bundle bundle = this.t();
        if (bundle != null) {
            int n4 = bundle.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0);
            int n5 = bl ? n3 : 0;
            bundle.putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", n5 | n4 & ~n3);
        }
    }

    public void c(View view) {
        this.a.addChild(view);
    }

    public void c0(Rect rect) {
        this.a.setBoundsInParent(rect);
    }

    public void d(View view, int n3) {
        this.a.addChild(view, n3);
    }

    public void d0(Rect rect) {
        this.a.setBoundsInScreen(rect);
    }

    public void e(CharSequence charSequence, View view) {
    }

    public void e0(boolean bl) {
        this.a.setCanOpenPopup(bl);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof s)) {
            return false;
        }
        s s3 = (s)object;
        object = this.a;
        if (object == null ? s3.a != null : !object.equals((Object)s3.a)) {
            return false;
        }
        if (this.c != s3.c) {
            return false;
        }
        return this.b == s3.b;
    }

    public final List f(String string) {
        ArrayList arrayList;
        ArrayList arrayList2 = arrayList = this.a.getExtras().getIntegerArrayList(string);
        if (arrayList == null) {
            arrayList2 = new ArrayList();
            this.a.getExtras().putIntegerArrayList(string, arrayList2);
        }
        return arrayList2;
    }

    public void f0(boolean bl) {
        this.a.setCheckable(bl);
    }

    public List g() {
        List list = this.a.getActionList();
        if (list != null) {
            ArrayList<a> arrayList = new ArrayList<a>();
            int n3 = list.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                arrayList.add(new a(list.get(i3)));
            }
            return arrayList;
        }
        return Collections.EMPTY_LIST;
    }

    public void g0(boolean bl) {
        this.a.setChecked(bl);
    }

    public void h0(CharSequence charSequence) {
        this.a.setClassName(charSequence);
    }

    public int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.a;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public int i() {
        return this.a.getActions();
    }

    public void i0(boolean bl) {
        this.a.setClickable(bl);
    }

    public final boolean j(int n3) {
        Bundle bundle = this.t();
        if (bundle == null) {
            return false;
        }
        return (bundle.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0) & n3) == n3;
    }

    public void j0(Object object) {
        AccessibilityNodeInfo accessibilityNodeInfo = this.a;
        object = object == null ? null : (AccessibilityNodeInfo.CollectionInfo)((e)object).a;
        accessibilityNodeInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo)object);
    }

    public void k(Rect rect) {
        this.a.getBoundsInParent(rect);
    }

    public void k0(Object object) {
        AccessibilityNodeInfo accessibilityNodeInfo = this.a;
        object = object == null ? null : (AccessibilityNodeInfo.CollectionItemInfo)((f)object).a;
        accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo)object);
    }

    public void l(Rect rect) {
        this.a.getBoundsInScreen(rect);
    }

    public void l0(CharSequence charSequence) {
        this.a.setContentDescription(charSequence);
    }

    public void m(Rect rect) {
        if (Build.VERSION.SDK_INT >= 34) {
            d.b(this.a, rect);
            return;
        }
        Rect rect2 = (Rect)this.a.getExtras().getParcelable("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOUNDS_IN_WINDOW_KEY");
        if (rect2 != null) {
            rect.set(rect2.left, rect2.top, rect2.right, rect2.bottom);
        }
    }

    public void m0(boolean bl) {
        this.a.setEnabled(bl);
    }

    public int n() {
        return this.a.getChildCount();
    }

    public void n0(CharSequence charSequence) {
        this.a.setError(charSequence);
    }

    public CharSequence o() {
        return this.a.getClassName();
    }

    public void o0(boolean bl) {
        this.a.setFocusable(bl);
    }

    public void p0(boolean bl) {
        this.a.setFocused(bl);
    }

    public CharSequence q() {
        if (Build.VERSION.SDK_INT >= 34) {
            return d.c(this.a);
        }
        return this.a.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.CONTAINER_TITLE_KEY");
    }

    public void q0(boolean bl) {
        if (Build.VERSION.SDK_INT >= 28) {
            p0.e.a(this.a, bl);
            return;
        }
        this.b0(2, bl);
    }

    public CharSequence r() {
        return this.a.getContentDescription();
    }

    public void r0(CharSequence charSequence) {
        this.a.setHintText(charSequence);
    }

    public CharSequence s() {
        return this.a.getError();
    }

    public void s0(View view) {
        this.a.setLabelFor(view);
    }

    public Bundle t() {
        return this.a.getExtras();
    }

    public void t0(int n3) {
        this.a.setMaxTextLength(n3);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        Object object = new Rect();
        this.k((Rect)object);
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("; boundsInParent: ");
        ((StringBuilder)charSequence).append(object);
        stringBuilder.append(((StringBuilder)charSequence).toString());
        this.l((Rect)object);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("; boundsInScreen: ");
        ((StringBuilder)charSequence).append(object);
        stringBuilder.append(((StringBuilder)charSequence).toString());
        this.m((Rect)object);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("; boundsInWindow: ");
        ((StringBuilder)charSequence).append(object);
        stringBuilder.append(((StringBuilder)charSequence).toString());
        stringBuilder.append("; packageName: ");
        stringBuilder.append(this.v());
        stringBuilder.append("; className: ");
        stringBuilder.append(this.o());
        stringBuilder.append("; text: ");
        stringBuilder.append(this.x());
        stringBuilder.append("; error: ");
        stringBuilder.append(this.s());
        stringBuilder.append("; maxTextLength: ");
        stringBuilder.append(this.u());
        stringBuilder.append("; stateDescription: ");
        stringBuilder.append(this.w());
        stringBuilder.append("; contentDescription: ");
        stringBuilder.append(this.r());
        stringBuilder.append("; tooltipText: ");
        stringBuilder.append(this.y());
        stringBuilder.append("; viewIdResName: ");
        stringBuilder.append(this.A());
        stringBuilder.append("; uniqueId: ");
        stringBuilder.append(this.z());
        stringBuilder.append("; checkable: ");
        stringBuilder.append(this.E());
        stringBuilder.append("; checked: ");
        stringBuilder.append(this.F());
        stringBuilder.append("; focusable: ");
        stringBuilder.append(this.J());
        stringBuilder.append("; focused: ");
        stringBuilder.append(this.K());
        stringBuilder.append("; selected: ");
        stringBuilder.append(this.Q());
        stringBuilder.append("; clickable: ");
        stringBuilder.append(this.G());
        stringBuilder.append("; longClickable: ");
        stringBuilder.append(this.N());
        stringBuilder.append("; contextClickable: ");
        stringBuilder.append(this.H());
        stringBuilder.append("; enabled: ");
        stringBuilder.append(this.I());
        stringBuilder.append("; password: ");
        stringBuilder.append(this.O());
        object = new StringBuilder();
        ((StringBuilder)object).append("; scrollable: ");
        ((StringBuilder)object).append(this.P());
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append("; containerTitle: ");
        stringBuilder.append(this.q());
        stringBuilder.append("; granularScrollingSupported: ");
        stringBuilder.append(this.L());
        stringBuilder.append("; importantForAccessibility: ");
        stringBuilder.append(this.M());
        stringBuilder.append("; visible: ");
        stringBuilder.append(this.T());
        stringBuilder.append("; isTextSelectable: ");
        stringBuilder.append(this.S());
        stringBuilder.append("; accessibilityDataSensitive: ");
        stringBuilder.append(this.C());
        stringBuilder.append("; [");
        List list = this.g();
        for (int i3 = 0; i3 < list.size(); ++i3) {
            a a4 = (a)list.get(i3);
            charSequence = s.h(a4.b());
            object = charSequence;
            if (((String)charSequence).equals("ACTION_UNKNOWN")) {
                object = charSequence;
                if (a4.c() != null) {
                    object = a4.c().toString();
                }
            }
            stringBuilder.append((String)object);
            if (i3 == list.size() - 1) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public int u() {
        return this.a.getMaxTextLength();
    }

    public void u0(CharSequence charSequence) {
        this.a.setPackageName(charSequence);
    }

    public CharSequence v() {
        return this.a.getPackageName();
    }

    public void v0(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 28) {
            p0.d.a(this.a, charSequence);
            return;
        }
        this.a.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY", charSequence);
    }

    public CharSequence w() {
        if (Build.VERSION.SDK_INT >= 30) {
            return p0.s$b.b(this.a);
        }
        return this.a.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.STATE_DESCRIPTION_KEY");
    }

    public void w0(View view) {
        this.b = -1;
        this.a.setParent(view);
    }

    public CharSequence x() {
        if (this.B()) {
            List list = this.f("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
            List list2 = this.f("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
            List list3 = this.f("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
            List list4 = this.f("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
            CharSequence charSequence = this.a.getText();
            int n3 = this.a.getText().length();
            charSequence = new SpannableString((CharSequence)TextUtils.substring((CharSequence)charSequence, (int)0, (int)n3));
            for (int i3 = 0; i3 < list.size(); ++i3) {
                charSequence.setSpan((Object)new p0.a((Integer)list4.get(i3), this, this.t().getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY")), (Integer)list.get(i3), (Integer)list2.get(i3), (Integer)list3.get(i3));
            }
            return charSequence;
        }
        return this.a.getText();
    }

    public void x0(View view, int n3) {
        this.b = n3;
        this.a.setParent(view, n3);
    }

    public CharSequence y() {
        if (Build.VERSION.SDK_INT >= 28) {
            return p0.f.a(this.a);
        }
        return this.a.getExtras().getCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.TOOLTIP_TEXT_KEY");
    }

    public void y0(g g3) {
        this.a.setRangeInfo((AccessibilityNodeInfo.RangeInfo)g3.a);
    }

    public String z() {
        if (Build.VERSION.SDK_INT >= 33) {
            return p0.s$c.g(this.a);
        }
        return this.a.getExtras().getString("androidx.view.accessibility.AccessibilityNodeInfoCompat.UNIQUE_ID_KEY");
    }

    public void z0(CharSequence charSequence) {
        this.a.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", charSequence);
    }

    public static class a {
        public static final a A;
        public static final a B;
        public static final a C;
        public static final a D;
        public static final a E;
        public static final a F;
        public static final a G;
        public static final a H;
        public static final a I;
        public static final a J;
        public static final a K;
        public static final a L;
        public static final a M;
        public static final a N;
        public static final a O;
        public static final a P;
        public static final a Q;
        public static final a R;
        public static final a S;
        public static final a T;
        public static final a U;
        public static final a V;
        public static final a e;
        public static final a f;
        public static final a g;
        public static final a h;
        public static final a i;
        public static final a j;
        public static final a k;
        public static final a l;
        public static final a m;
        public static final a n;
        public static final a o;
        public static final a p;
        public static final a q;
        public static final a r;
        public static final a s;
        public static final a t;
        public static final a u;
        public static final a v;
        public static final a w;
        public static final a x;
        public static final a y;
        public static final a z;
        public final Object a;
        public final int b;
        public final Class c;
        public final v d;

        static {
            Object var2 = null;
            e = new a(1, null);
            f = new a(2, null);
            g = new a(4, null);
            h = new a(8, null);
            i = new a(16, null);
            j = new a(32, null);
            k = new a(64, null);
            l = new a(128, null);
            m = new a(256, null, v.b.class);
            n = new a(512, null, v.b.class);
            o = new a(1024, null, v.c.class);
            p = new a(2048, null, v.c.class);
            q = new a(4096, null);
            r = new a(8192, null);
            s = new a(16384, null);
            t = new a(32768, null);
            u = new a(65536, null);
            v = new a(131072, null, v.g.class);
            w = new a(262144, null);
            x = new a(524288, null);
            y = new a(0x100000, null);
            z = new a(0x200000, null, v.h.class);
            int n3 = Build.VERSION.SDK_INT;
            A = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN, 16908342, null, null, null);
            B = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION, 16908343, null, null, v.e.class);
            C = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP, 16908344, null, null, null);
            D = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT, 16908345, null, null, null);
            E = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN, 16908346, null, null, null);
            F = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT, 16908347, null, null, null);
            Object object = n3 >= 29 ? p0.g.a() : null;
            G = new a(object, 16908358, null, null, null);
            object = n3 >= 29 ? p0.l.a() : null;
            H = new a(object, 16908359, null, null, null);
            object = n3 >= 29 ? p0.m.a() : null;
            I = new a(object, 16908360, null, null, null);
            object = n3 >= 29 ? p0.n.a() : null;
            J = new a(object, 16908361, null, null, null);
            K = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK, 16908348, null, null, null);
            L = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS, 16908349, null, null, v.f.class);
            M = new a(AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW, 16908354, null, null, v.d.class);
            object = n3 >= 28 ? p0.o.a() : null;
            N = new a(object, 16908356, null, null, null);
            object = n3 >= 28 ? p0.p.a() : null;
            O = new a(object, 16908357, null, null, null);
            object = n3 >= 30 ? p0.q.a() : null;
            P = new a(object, 16908362, null, null, null);
            object = n3 >= 30 ? p0.r.a() : null;
            Q = new a(object, 16908372, null, null, null);
            object = n3 >= 32 ? p0.h.a() : null;
            R = new a(object, 16908373, null, null, null);
            object = n3 >= 32 ? p0.i.a() : null;
            S = new a(object, 16908374, null, null, null);
            object = n3 >= 32 ? p0.j.a() : null;
            T = new a(object, 16908375, null, null, null);
            object = n3 >= 33 ? p0.k.a() : null;
            U = new a(object, 16908376, null, null, null);
            object = var2;
            if (n3 >= 34) {
                object = p0.s$d.a();
            }
            V = new a(object, 16908382, null, null, null);
        }

        public a(int n3, CharSequence charSequence) {
            this(null, n3, charSequence, null, null);
        }

        public a(int n3, CharSequence charSequence, Class clazz) {
            this(null, n3, charSequence, null, clazz);
        }

        public a(int n3, CharSequence charSequence, v v3) {
            this(null, n3, charSequence, v3, null);
        }

        public a(Object object) {
            this(object, 0, null, null, null);
        }

        public a(Object object, int n3, CharSequence charSequence, v v3, Class clazz) {
            this.b = n3;
            this.d = v3;
            this.a = object == null ? new AccessibilityNodeInfo.AccessibilityAction(n3, charSequence) : object;
            this.c = clazz;
        }

        public a a(CharSequence charSequence, v v3) {
            return new a(null, this.b, charSequence, v3, this.c);
        }

        public int b() {
            return ((AccessibilityNodeInfo.AccessibilityAction)this.a).getId();
        }

        public CharSequence c() {
            return ((AccessibilityNodeInfo.AccessibilityAction)this.a).getLabel();
        }

        public boolean d(View view, Bundle object) {
            if (this.d != null) {
                object = this.c;
                if (object != null) {
                    try {
                        androidx.appcompat.app.s.a(((Class)object).getDeclaredConstructor(null).newInstance(null));
                        throw null;
                    }
                    catch (Exception exception) {
                        object = this.c;
                        object = object == null ? "null" : ((Class)object).getName();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed to execute command with argument class ViewCommandArgument: ");
                        stringBuilder.append((String)object);
                        Log.e((String)"A11yActionCompat", (String)stringBuilder.toString(), (Throwable)exception);
                    }
                }
                return this.d.a(view, null);
            }
            return false;
        }

        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (!(object instanceof a)) {
                return false;
            }
            a a4 = (a)object;
            object = this.a;
            return !(object == null ? a4.a != null : !object.equals(a4.a));
        }

        public int hashCode() {
            Object object = this.a;
            if (object != null) {
                return object.hashCode();
            }
            return 0;
        }

        public String toString() {
            String string;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AccessibilityActionCompat: ");
            String string2 = string = p0.s.h(this.b);
            if (string.equals("ACTION_UNKNOWN")) {
                string2 = string;
                if (this.c() != null) {
                    string2 = this.c().toString();
                }
            }
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
    }

    public static abstract class b {
        public static Object a(int n3, float f3, float f4, float f5) {
            return new AccessibilityNodeInfo.RangeInfo(n3, f3, f4, f5);
        }

        public static CharSequence b(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getStateDescription();
        }

        public static void c(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
            accessibilityNodeInfo.setStateDescription(charSequence);
        }
    }

    public static abstract class c {
        public static f a(boolean bl, int n3, int n4, int n5, int n6, boolean bl2, String string, String string2) {
            return new f(new AccessibilityNodeInfo.CollectionItemInfo.Builder().setHeading(bl).setColumnIndex(n3).setRowIndex(n4).setColumnSpan(n5).setRowSpan(n6).setSelected(bl2).setRowTitle(string).setColumnTitle(string2).build());
        }

        public static s b(AccessibilityNodeInfo accessibilityNodeInfo, int n3, int n4) {
            return s.M0(accessibilityNodeInfo.getChild(n3, n4));
        }

        public static String c(Object object) {
            return ((AccessibilityNodeInfo.CollectionItemInfo)object).getColumnTitle();
        }

        public static String d(Object object) {
            return ((AccessibilityNodeInfo.CollectionItemInfo)object).getRowTitle();
        }

        public static AccessibilityNodeInfo.ExtraRenderingInfo e(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getExtraRenderingInfo();
        }

        public static s f(AccessibilityNodeInfo accessibilityNodeInfo, int n3) {
            return s.M0(accessibilityNodeInfo.getParent(n3));
        }

        public static String g(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getUniqueId();
        }

        public static boolean h(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isTextSelectable();
        }

        public static void i(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            accessibilityNodeInfo.setTextSelectable(bl);
        }

        public static void j(AccessibilityNodeInfo accessibilityNodeInfo, String string) {
            accessibilityNodeInfo.setUniqueId(string);
        }
    }

    public static abstract class d {
        public static AccessibilityNodeInfo.AccessibilityAction a() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_IN_DIRECTION;
        }

        public static void b(AccessibilityNodeInfo accessibilityNodeInfo, Rect rect) {
            accessibilityNodeInfo.getBoundsInWindow(rect);
        }

        public static CharSequence c(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getContainerTitle();
        }

        public static long d(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getMinDurationBetweenContentChanges().toMillis();
        }

        public static boolean e(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.hasRequestInitialAccessibilityFocus();
        }

        public static boolean f(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isAccessibilityDataSensitive();
        }

        public static void g(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            accessibilityNodeInfo.setAccessibilityDataSensitive(bl);
        }

        public static void h(AccessibilityNodeInfo accessibilityNodeInfo, Rect rect) {
            accessibilityNodeInfo.setBoundsInWindow(rect);
        }

        public static void i(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
            accessibilityNodeInfo.setContainerTitle(charSequence);
        }

        public static void j(AccessibilityNodeInfo accessibilityNodeInfo, long l3) {
            accessibilityNodeInfo.setMinDurationBetweenContentChanges(Duration.ofMillis(l3));
        }

        public static void k(AccessibilityNodeInfo accessibilityNodeInfo, View view, boolean bl) {
            accessibilityNodeInfo.setQueryFromAppProcessEnabled(view, bl);
        }

        public static void l(AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
            accessibilityNodeInfo.setRequestInitialAccessibilityFocus(bl);
        }
    }

    public static class e {
        public final Object a;

        public e(Object object) {
            this.a = object;
        }

        public static e a(int n3, int n4, boolean bl) {
            return new e(AccessibilityNodeInfo.CollectionInfo.obtain((int)n3, (int)n4, (boolean)bl));
        }

        public static e b(int n3, int n4, boolean bl, int n5) {
            return new e(AccessibilityNodeInfo.CollectionInfo.obtain((int)n3, (int)n4, (boolean)bl, (int)n5));
        }
    }

    public static class f {
        public final Object a;

        public f(Object object) {
            this.a = object;
        }

        public static f a(int n3, int n4, int n5, int n6, boolean bl, boolean bl2) {
            return new f(AccessibilityNodeInfo.CollectionItemInfo.obtain((int)n3, (int)n4, (int)n5, (int)n6, (boolean)bl, (boolean)bl2));
        }
    }

    public static class g {
        public final Object a;

        public g(Object object) {
            this.a = object;
        }

        public static g a(int n3, float f3, float f4, float f5) {
            return new g(AccessibilityNodeInfo.RangeInfo.obtain((int)n3, (float)f3, (float)f4, (float)f5));
        }
    }
}

