/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ClipData
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.ContentInfo
 *  android.view.Display
 *  android.view.KeyEvent
 *  android.view.PointerIcon
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.View$DragShadowBuilder
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnUnhandledKeyEventListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.Window
 *  android.view.WindowInsets
 *  android.view.WindowInsetsController
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.autofill.AutofillId
 *  android.view.contentcapture.ContentCaptureSession
 */
package o0;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContentInfo;
import android.view.Display;
import android.view.KeyEvent;
import android.view.OnReceiveContentListener;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureSession;
import androidx.appcompat.app.s;
import b0.b;
import java.lang.ref.WeakReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import o0.a;
import o0.d;
import o0.f0;
import o0.g0;
import o0.h0;
import o0.h1;
import o0.j0;
import o0.l1;
import o0.m1;
import o0.w0;
import o0.y0;
import o0.y2;
import o0.z1;
import p0.s;
import p0.v;

public abstract class x0 {
    public static WeakHashMap a;
    public static Field b;
    public static boolean c = false;
    public static final int[] d;
    public static final h0 e;
    public static final e f;

    static {
        d = new int[]{b0.b.accessibility_custom_action_0, b0.b.accessibility_custom_action_1, b0.b.accessibility_custom_action_2, b0.b.accessibility_custom_action_3, b0.b.accessibility_custom_action_4, b0.b.accessibility_custom_action_5, b0.b.accessibility_custom_action_6, b0.b.accessibility_custom_action_7, b0.b.accessibility_custom_action_8, b0.b.accessibility_custom_action_9, b0.b.accessibility_custom_action_10, b0.b.accessibility_custom_action_11, b0.b.accessibility_custom_action_12, b0.b.accessibility_custom_action_13, b0.b.accessibility_custom_action_14, b0.b.accessibility_custom_action_15, b0.b.accessibility_custom_action_16, b0.b.accessibility_custom_action_17, b0.b.accessibility_custom_action_18, b0.b.accessibility_custom_action_19, b0.b.accessibility_custom_action_20, b0.b.accessibility_custom_action_21, b0.b.accessibility_custom_action_22, b0.b.accessibility_custom_action_23, b0.b.accessibility_custom_action_24, b0.b.accessibility_custom_action_25, b0.b.accessibility_custom_action_26, b0.b.accessibility_custom_action_27, b0.b.accessibility_custom_action_28, b0.b.accessibility_custom_action_29, b0.b.accessibility_custom_action_30, b0.b.accessibility_custom_action_31};
        e = new w0();
        f = new e();
    }

    public static int A(View view) {
        return view.getMinimumWidth();
    }

    public static f A0() {
        return new f(b0.b.tag_state_description, CharSequence.class, 64, 30){

            public CharSequence h(View view) {
                return n.b(view);
            }

            public void i(View view, CharSequence charSequence) {
                n.f(view, charSequence);
            }

            public boolean j(CharSequence charSequence, CharSequence charSequence2) {
                return TextUtils.equals((CharSequence)charSequence, (CharSequence)charSequence2) ^ true;
            }
        };
    }

    public static String[] B(View view) {
        if (Build.VERSION.SDK_INT >= 31) {
            return o.a(view);
        }
        return (String[])view.getTag(b0.b.tag_on_receive_content_mime_types);
    }

    public static void B0(View view) {
        h.z(view);
    }

    public static ViewParent C(View view) {
        return view.getParentForAccessibility();
    }

    public static z1 D(View view) {
        return i.a(view);
    }

    public static CharSequence E(View view) {
        return (CharSequence)x0.A0().e(view);
    }

    public static String F(View view) {
        return h.k(view);
    }

    public static float G(View view) {
        return h.l(view);
    }

    public static y2 H(View view) {
        if (Build.VERSION.SDK_INT >= 30) {
            return n.c(view);
        }
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                if ((context = ((Activity)context).getWindow()) != null) {
                    return l1.a((Window)context, view);
                }
                return null;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public static int I(View view) {
        return view.getWindowSystemUiVisibility();
    }

    public static float J(View view) {
        return h.m(view);
    }

    public static boolean K(View view) {
        return x0.l(view) != null;
    }

    public static boolean L(View view) {
        return view.hasTransientState();
    }

    public static boolean M(View object) {
        object = (Boolean)x0.b().e((View)object);
        return object != null && ((Boolean)object).booleanValue();
    }

    public static boolean N(View view) {
        return view.isAttachedToWindow();
    }

    public static boolean O(View view) {
        return view.isLaidOut();
    }

    public static boolean P(View object) {
        object = (Boolean)x0.g0().e((View)object);
        return object != null && ((Boolean)object).booleanValue();
    }

    public static void Q(View view, int n3) {
        int n4;
        boolean bl;
        AccessibilityManager accessibilityManager;
        block10: {
            block9: {
                accessibilityManager = (AccessibilityManager)view.getContext().getSystemService("accessibility");
                if (!accessibilityManager.isEnabled()) break block9;
                bl = x0.n(view) != null && view.isShown() && view.getWindowVisibility() == 0;
                int n5 = view.getAccessibilityLiveRegion();
                n4 = 32;
                if (n5 != 0 || bl) break block10;
                if (n3 == 32) {
                    AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
                    view.onInitializeAccessibilityEvent(accessibilityEvent);
                    accessibilityEvent.setEventType(32);
                    accessibilityEvent.setContentChangeTypes(n3);
                    accessibilityEvent.setSource(view);
                    view.onPopulateAccessibilityEvent(accessibilityEvent);
                    accessibilityEvent.getText().add(x0.n(view));
                    accessibilityManager.sendAccessibilityEvent(accessibilityEvent);
                    return;
                }
                if (view.getParent() != null) {
                    accessibilityManager = view.getParent();
                    try {
                        accessibilityManager.notifySubtreeAccessibilityStateChanged(view, view, n3);
                        return;
                    }
                    catch (AbstractMethodError abstractMethodError) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(view.getParent().getClass().getSimpleName());
                        stringBuilder.append(" does not fully implement ViewParent");
                        Log.e((String)"ViewCompat", (String)stringBuilder.toString(), (Throwable)abstractMethodError);
                    }
                }
            }
            return;
        }
        accessibilityManager = AccessibilityEvent.obtain();
        if (!bl) {
            n4 = 2048;
        }
        accessibilityManager.setEventType(n4);
        accessibilityManager.setContentChangeTypes(n3);
        if (bl) {
            accessibilityManager.getText().add(x0.n(view));
            x0.p0(view);
        }
        view.sendAccessibilityEventUnchecked((AccessibilityEvent)accessibilityManager);
    }

    public static void R(View view, int n3) {
        view.offsetLeftAndRight(n3);
    }

    public static void S(View view, int n3) {
        view.offsetTopAndBottom(n3);
    }

    public static z1 T(View view, z1 z12) {
        WindowInsets windowInsets;
        WindowInsets windowInsets2 = z12.v();
        if (windowInsets2 != null && !(windowInsets = g.b(view, windowInsets2)).equals((Object)windowInsets2)) {
            return z1.x(windowInsets, view);
        }
        return z12;
    }

    public static void U(View view, p0.s s3) {
        view.onInitializeAccessibilityNodeInfo(s3.K0());
    }

    public static f V() {
        return new f(b0.b.tag_accessibility_pane_title, CharSequence.class, 8, 28){

            public CharSequence h(View view) {
                return l.b(view);
            }

            public void i(View view, CharSequence charSequence) {
                l.h(view, charSequence);
            }

            public boolean j(CharSequence charSequence, CharSequence charSequence2) {
                return TextUtils.equals((CharSequence)charSequence, (CharSequence)charSequence2) ^ true;
            }
        };
    }

    public static boolean W(View view, int n3, Bundle bundle) {
        return view.performAccessibilityAction(n3, bundle);
    }

    public static d X(View view, d d3) {
        if (Log.isLoggable((String)"ViewCompat", (int)3)) {
            Objects.toString(d3);
            view.getClass();
            view.getId();
        }
        if (Build.VERSION.SDK_INT >= 31) {
            return o.b(view, d3);
        }
        g0 g02 = (g0)view.getTag(b0.b.tag_on_receive_content_listener);
        if (g02 != null) {
            if ((d3 = g02.a(view, d3)) == null) {
                return null;
            }
            return x0.u(view).a(d3);
        }
        return x0.u(view).a(d3);
    }

    public static void Y(View view) {
        view.postInvalidateOnAnimation();
    }

    public static void Z(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    public static /* synthetic */ d a(d d3) {
        return d3;
    }

    public static void a0(View view, Runnable runnable, long l3) {
        view.postOnAnimationDelayed(runnable, l3);
    }

    public static f b() {
        return new f(b0.b.tag_accessibility_heading, Boolean.class, 28){

            public Boolean h(View view) {
                return l.c(view);
            }

            public void i(View view, Boolean bl) {
                l.g(view, bl);
            }

            public boolean j(Boolean bl, Boolean bl2) {
                return this.a(bl, bl2) ^ true;
            }
        };
    }

    public static void b0(View view, int n3) {
        x0.c0(n3, view);
        x0.Q(view, 0);
    }

    public static int c(View view, CharSequence charSequence, v v3) {
        int n3 = x0.p(view, charSequence);
        if (n3 != -1) {
            x0.d(view, new s.a(n3, charSequence, v3));
        }
        return n3;
    }

    public static void c0(int n3, View object) {
        object = x0.o((View)object);
        for (int i3 = 0; i3 < object.size(); ++i3) {
            if (((s.a)object.get(i3)).b() != n3) continue;
            object.remove(i3);
            return;
        }
    }

    public static void d(View view, s.a a4) {
        x0.j(view);
        x0.c0(a4.b(), view);
        x0.o(view).add(a4);
        x0.Q(view, 0);
    }

    public static void d0(View view, s.a a4, CharSequence charSequence, v v3) {
        if (v3 == null && charSequence == null) {
            x0.b0(view, a4.b());
            return;
        }
        x0.d(view, a4.a(charSequence, v3));
    }

    public static h1 e(View view) {
        h1 h12;
        if (a == null) {
            a = new WeakHashMap();
        }
        h1 h13 = h12 = (h1)a.get(view);
        if (h12 == null) {
            h13 = new h1(view);
            a.put(view, h13);
        }
        return h13;
    }

    public static void e0(View view) {
        g.c(view);
    }

    public static z1 f(View view, z1 z12, Rect rect) {
        return h.b(view, z12, rect);
    }

    public static void f0(View view, Context context, int[] nArray, AttributeSet attributeSet, TypedArray typedArray, int n3, int n4) {
        if (Build.VERSION.SDK_INT >= 29) {
            m.d(view, context, nArray, attributeSet, typedArray, n3, n4);
        }
    }

    public static z1 g(View view, z1 z12) {
        WindowInsets windowInsets;
        WindowInsets windowInsets2 = z12.v();
        if (windowInsets2 != null && !(windowInsets = g.a(view, windowInsets2)).equals((Object)windowInsets2)) {
            return z1.x(windowInsets, view);
        }
        return z12;
    }

    public static f g0() {
        return new f(b0.b.tag_screen_reader_focusable, Boolean.class, 28){

            public Boolean h(View view) {
                return l.d(view);
            }

            public void i(View view, Boolean bl) {
                l.j(view, bl);
            }

            public boolean j(Boolean bl, Boolean bl2) {
                return this.a(bl, bl2) ^ true;
            }
        };
    }

    public static boolean h(View view, KeyEvent keyEvent) {
        if (Build.VERSION.SDK_INT >= 28) {
            return false;
        }
        return r.a(view).b(view, keyEvent);
    }

    public static void h0(View view, a a4) {
        a a5 = a4;
        if (a4 == null) {
            a5 = a4;
            if (x0.l(view) instanceof a.a) {
                a5 = new a();
            }
        }
        x0.p0(view);
        a4 = a5 == null ? null : a5.d();
        view.setAccessibilityDelegate((View.AccessibilityDelegate)a4);
    }

    public static boolean i(View view, KeyEvent keyEvent) {
        if (Build.VERSION.SDK_INT >= 28) {
            return false;
        }
        return r.a(view).f(keyEvent);
    }

    public static void i0(View view, boolean bl) {
        x0.b().f(view, bl);
    }

    public static void j(View view) {
        a a4;
        a a5 = a4 = x0.k(view);
        if (a4 == null) {
            a5 = new a();
        }
        x0.h0(view, a5);
    }

    public static void j0(View view, CharSequence charSequence) {
        x0.V().f(view, charSequence);
        if (charSequence != null) {
            f.a(view);
            return;
        }
        f.d(view);
    }

    public static a k(View view) {
        if ((view = x0.l(view)) == null) {
            return null;
        }
        if (view instanceof a.a) {
            return ((a.a)view).a;
        }
        return new a((View.AccessibilityDelegate)view);
    }

    public static void k0(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    public static View.AccessibilityDelegate l(View view) {
        if (Build.VERSION.SDK_INT >= 29) {
            return m.a(view);
        }
        return x0.m(view);
    }

    public static void l0(View view, ColorStateList colorStateList) {
        h.q(view, colorStateList);
    }

    public static View.AccessibilityDelegate m(View object) {
        if (c) {
            return null;
        }
        if (b == null) {
            try {
                Field field;
                b = field = View.class.getDeclaredField("mAccessibilityDelegate");
                ((AccessibleObject)field).setAccessible(true);
            }
            catch (Throwable throwable) {
                c = true;
                return null;
            }
        }
        try {
            object = b.get(object);
            if (object instanceof View.AccessibilityDelegate) {
                object = (View.AccessibilityDelegate)object;
                return object;
            }
            return null;
        }
        catch (Throwable throwable) {
            c = true;
            return null;
        }
    }

    public static void m0(View view, PorterDuff.Mode mode) {
        h.r(view, mode);
    }

    public static CharSequence n(View view) {
        return (CharSequence)x0.V().e(view);
    }

    public static void n0(View view, float f3) {
        h.s(view, f3);
    }

    public static List o(View view) {
        ArrayList arrayList;
        int n3 = b0.b.tag_accessibility_actions;
        ArrayList arrayList2 = arrayList = (ArrayList)view.getTag(n3);
        if (arrayList == null) {
            arrayList2 = new ArrayList();
            view.setTag(n3, arrayList2);
        }
        return arrayList2;
    }

    public static void o0(View view, int n3) {
        view.setImportantForAccessibility(n3);
    }

    public static int p(View object, CharSequence object2) {
        int n3;
        object = x0.o((View)object);
        for (n3 = 0; n3 < object.size(); ++n3) {
            if (!TextUtils.equals((CharSequence)object2, (CharSequence)((s.a)object.get(n3)).c())) continue;
            return ((s.a)object.get(n3)).b();
        }
        Object object3 = -1;
        for (n3 = 0; n3 < ((Object)(object2 = (Object)d)).length && object3 == -1; ++n3) {
            Object object4 = object2[n3];
            boolean bl = true;
            for (int i3 = 0; i3 < object.size(); ++i3) {
                boolean bl2 = ((s.a)object.get(i3)).b() != object4;
                bl &= bl2;
            }
            if (!bl) continue;
            object3 = object4;
        }
        return object3;
    }

    public static void p0(View view) {
        if (view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
        }
    }

    public static ColorStateList q(View view) {
        return h.g(view);
    }

    public static void q0(View view, int n3) {
        k.m(view, n3);
    }

    public static PorterDuff.Mode r(View view) {
        return h.h(view);
    }

    public static void r0(View view, f0 f02) {
        h.u(view, f02);
    }

    public static Display s(View view) {
        return view.getDisplay();
    }

    public static void s0(View view, j0 object) {
        object = object != null ? ((j0)object).a() : null;
        j.d(view, (PointerIcon)object);
    }

    public static float t(View view) {
        return h.i(view);
    }

    public static void t0(View view, boolean bl) {
        x0.g0().f(view, bl);
    }

    public static h0 u(View view) {
        if (view instanceof h0) {
            return (h0)view;
        }
        return e;
    }

    public static void u0(View view, int n3, int n4) {
        i.d(view, n3, n4);
    }

    public static boolean v(View view) {
        return view.getFitsSystemWindows();
    }

    public static void v0(View view, CharSequence charSequence) {
        x0.A0().f(view, charSequence);
    }

    public static int w(View view) {
        return view.getImportantForAccessibility();
    }

    public static void w0(View view, String string) {
        h.v(view, string);
    }

    public static int x(View view) {
        return k.c(view);
    }

    public static void x0(View view, float f3) {
        h.w(view, f3);
    }

    public static int y(View view) {
        return view.getLayoutDirection();
    }

    public static void y0(View view, m1.b b3) {
        m1.d(view, b3);
    }

    public static int z(View view) {
        return view.getMinimumHeight();
    }

    public static void z0(View view, float f3) {
        h.x(view, f3);
    }

    public static class e
    implements ViewTreeObserver.OnGlobalLayoutListener,
    View.OnAttachStateChangeListener {
        public final WeakHashMap c = new WeakHashMap();

        public void a(View view) {
            WeakHashMap weakHashMap = this.c;
            boolean bl = view.isShown() && view.getWindowVisibility() == 0;
            weakHashMap.put(view, bl);
            view.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
            if (view.isAttachedToWindow()) {
                this.c(view);
            }
        }

        public final void b(Map.Entry entry) {
            boolean bl;
            View view = (View)entry.getKey();
            boolean bl2 = (Boolean)entry.getValue();
            if (bl2 != (bl = view.isShown() && view.getWindowVisibility() == 0)) {
                int n3 = bl ? 16 : 32;
                x0.Q(view, n3);
                entry.setValue(bl);
            }
        }

        public final void c(View view) {
            view.getViewTreeObserver().addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
        }

        public void d(View view) {
            this.c.remove(view);
            view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
            this.e(view);
        }

        public final void e(View view) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
        }

        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT < 28) {
                Iterator iterator = this.c.entrySet().iterator();
                while (iterator.hasNext()) {
                    this.b(iterator.next());
                }
            }
        }

        public void onViewAttachedToWindow(View view) {
            this.c(view);
        }

        public void onViewDetachedFromWindow(View view) {
        }
    }

    public static abstract class f {
        public final int a;
        public final Class b;
        public final int c;
        public final int d;

        public f(int n3, Class clazz, int n4) {
            this(n3, clazz, 0, n4);
        }

        public f(int n3, Class clazz, int n4, int n5) {
            this.a = n3;
            this.b = clazz;
            this.d = n4;
            this.c = n5;
        }

        public boolean a(Boolean bl, Boolean bl2) {
            boolean bl3;
            boolean bl4 = bl != null && bl != false;
            return bl4 == (bl3 = bl2 != null && bl2 != false);
        }

        public final boolean b() {
            return Build.VERSION.SDK_INT >= this.c;
        }

        public abstract Object c(View var1);

        public abstract void d(View var1, Object var2);

        public Object e(View object) {
            if (this.b()) {
                return this.c((View)object);
            }
            if (this.b.isInstance(object = object.getTag(this.a))) {
                return object;
            }
            return null;
        }

        public void f(View view, Object object) {
            if (this.b()) {
                this.d(view, object);
                return;
            }
            if (this.g(this.e(view), object)) {
                x0.j(view);
                view.setTag(this.a, object);
                x0.Q(view, this.d);
            }
        }

        public abstract boolean g(Object var1, Object var2);
    }

    public static abstract class g {
        public static WindowInsets a(View view, WindowInsets windowInsets) {
            return view.dispatchApplyWindowInsets(windowInsets);
        }

        public static WindowInsets b(View view, WindowInsets windowInsets) {
            return view.onApplyWindowInsets(windowInsets);
        }

        public static void c(View view) {
            view.requestApplyInsets();
        }
    }

    public static abstract class h {
        public static void a(WindowInsets windowInsets, View view) {
            View.OnApplyWindowInsetsListener onApplyWindowInsetsListener = (View.OnApplyWindowInsetsListener)view.getTag(b0.b.tag_window_insets_animation_callback);
            if (onApplyWindowInsetsListener != null) {
                onApplyWindowInsetsListener.onApplyWindowInsets(view, windowInsets);
            }
        }

        public static z1 b(View view, z1 z12, Rect rect) {
            WindowInsets windowInsets = z12.v();
            if (windowInsets != null) {
                return z1.x(view.computeSystemWindowInsets(windowInsets, rect), view);
            }
            rect.setEmpty();
            return z12;
        }

        public static boolean c(View view, float f3, float f4, boolean bl) {
            return view.dispatchNestedFling(f3, f4, bl);
        }

        public static boolean d(View view, float f3, float f4) {
            return view.dispatchNestedPreFling(f3, f4);
        }

        public static boolean e(View view, int n3, int n4, int[] nArray, int[] nArray2) {
            return view.dispatchNestedPreScroll(n3, n4, nArray, nArray2);
        }

        public static boolean f(View view, int n3, int n4, int n5, int n6, int[] nArray) {
            return view.dispatchNestedScroll(n3, n4, n5, n6, nArray);
        }

        public static ColorStateList g(View view) {
            return view.getBackgroundTintList();
        }

        public static PorterDuff.Mode h(View view) {
            return view.getBackgroundTintMode();
        }

        public static float i(View view) {
            return view.getElevation();
        }

        public static z1 j(View view) {
            return z1.a.a(view);
        }

        public static String k(View view) {
            return view.getTransitionName();
        }

        public static float l(View view) {
            return view.getTranslationZ();
        }

        public static float m(View view) {
            return view.getZ();
        }

        public static boolean n(View view) {
            return view.hasNestedScrollingParent();
        }

        public static boolean o(View view) {
            return view.isImportantForAccessibility();
        }

        public static boolean p(View view) {
            return view.isNestedScrollingEnabled();
        }

        public static void q(View view, ColorStateList colorStateList) {
            view.setBackgroundTintList(colorStateList);
        }

        public static void r(View view, PorterDuff.Mode mode) {
            view.setBackgroundTintMode(mode);
        }

        public static void s(View view, float f3) {
            view.setElevation(f3);
        }

        public static void t(View view, boolean bl) {
            view.setNestedScrollingEnabled(bl);
        }

        public static void u(View view, f0 f02) {
            if (Build.VERSION.SDK_INT < 30) {
                view.setTag(b0.b.tag_on_apply_window_listener, (Object)f02);
            }
            if (f02 == null) {
                view.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener)view.getTag(b0.b.tag_window_insets_animation_callback));
                return;
            }
            view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(){
                public z1 a;
                public final View b;
                public final f0 c;
                {
                    this.b = view;
                    this.c = f02;
                    this.a = null;
                }

                public WindowInsets onApplyWindowInsets(View view, WindowInsets object) {
                    z1 z12 = z1.x((WindowInsets)object, view);
                    int n3 = Build.VERSION.SDK_INT;
                    if (n3 < 30) {
                        h.a((WindowInsets)object, this.b);
                        if (z12.equals(this.a)) {
                            return this.c.a(view, z12).v();
                        }
                    }
                    this.a = z12;
                    object = this.c.a(view, z12);
                    if (n3 >= 30) {
                        return ((z1)object).v();
                    }
                    x0.e0(view);
                    return ((z1)object).v();
                }
            });
        }

        public static void v(View view, String string) {
            view.setTransitionName(string);
        }

        public static void w(View view, float f3) {
            view.setTranslationZ(f3);
        }

        public static void x(View view, float f3) {
            view.setZ(f3);
        }

        public static boolean y(View view, int n3) {
            return view.startNestedScroll(n3);
        }

        public static void z(View view) {
            view.stopNestedScroll();
        }
    }

    public static abstract class i {
        public static z1 a(View view) {
            Object object = view.getRootWindowInsets();
            if (object == null) {
                return null;
            }
            object = z1.w((WindowInsets)object);
            ((z1)object).t((z1)object);
            ((z1)object).d(view.getRootView());
            return object;
        }

        public static int b(View view) {
            return view.getScrollIndicators();
        }

        public static void c(View view, int n3) {
            view.setScrollIndicators(n3);
        }

        public static void d(View view, int n3, int n4) {
            view.setScrollIndicators(n3, n4);
        }
    }

    public static abstract class j {
        public static void a(View view) {
            view.cancelDragAndDrop();
        }

        public static void b(View view) {
            view.dispatchFinishTemporaryDetach();
        }

        public static void c(View view) {
            view.dispatchStartTemporaryDetach();
        }

        public static void d(View view, PointerIcon pointerIcon) {
            view.setPointerIcon(pointerIcon);
        }

        public static boolean e(View view, ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object object, int n3) {
            return view.startDragAndDrop(clipData, dragShadowBuilder, object, n3);
        }

        public static void f(View view, View.DragShadowBuilder dragShadowBuilder) {
            view.updateDragShadow(dragShadowBuilder);
        }
    }

    public static abstract class k {
        public static void a(View view, Collection<View> collection, int n3) {
            view.addKeyboardNavigationClusters(collection, n3);
        }

        public static AutofillId b(View view) {
            return view.getAutofillId();
        }

        public static int c(View view) {
            return view.getImportantForAutofill();
        }

        public static int d(View view) {
            return view.getNextClusterForwardId();
        }

        public static boolean e(View view) {
            return view.hasExplicitFocusable();
        }

        public static boolean f(View view) {
            return view.isFocusedByDefault();
        }

        public static boolean g(View view) {
            return view.isImportantForAutofill();
        }

        public static boolean h(View view) {
            return view.isKeyboardNavigationCluster();
        }

        public static View i(View view, View view2, int n3) {
            return view.keyboardNavigationClusterSearch(view2, n3);
        }

        public static boolean j(View view) {
            return view.restoreDefaultFocus();
        }

        public static void k(View view, String ... stringArray) {
            view.setAutofillHints(stringArray);
        }

        public static void l(View view, boolean bl) {
            view.setFocusedByDefault(bl);
        }

        public static void m(View view, int n3) {
            view.setImportantForAutofill(n3);
        }

        public static void n(View view, boolean bl) {
            view.setKeyboardNavigationCluster(bl);
        }

        public static void o(View view, int n3) {
            view.setNextClusterForwardId(n3);
        }

        public static void p(View view, CharSequence charSequence) {
            view.setTooltipText(charSequence);
        }
    }

    public static abstract class l {
        public static void a(View view, q q3) {
            int n3 = b0.b.tag_unhandled_key_listeners;
            Object object = (o.r)view.getTag(n3);
            o.r r3 = object;
            if (object == null) {
                r3 = new o.r();
                view.setTag(n3, (Object)r3);
            }
            Objects.requireNonNull(q3);
            object = new y0(q3);
            r3.put(q3, object);
            view.addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener)object);
        }

        public static CharSequence b(View view) {
            return view.getAccessibilityPaneTitle();
        }

        public static boolean c(View view) {
            return view.isAccessibilityHeading();
        }

        public static boolean d(View view) {
            return view.isScreenReaderFocusable();
        }

        public static void e(View view, q q3) {
            o.r r3 = (o.r)view.getTag(b0.b.tag_unhandled_key_listeners);
            if (r3 != null && (q3 = (View.OnUnhandledKeyEventListener)r3.get(q3)) != null) {
                view.removeOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener)q3);
            }
        }

        public static <T> T f(View view, int n3) {
            return (T)view.requireViewById(n3);
        }

        public static void g(View view, boolean bl) {
            view.setAccessibilityHeading(bl);
        }

        public static void h(View view, CharSequence charSequence) {
            view.setAccessibilityPaneTitle(charSequence);
        }

        public static void i(View view, q0.a a4) {
            view.setAutofillId(null);
        }

        public static void j(View view, boolean bl) {
            view.setScreenReaderFocusable(bl);
        }
    }

    public static abstract class m {
        public static View.AccessibilityDelegate a(View view) {
            return view.getAccessibilityDelegate();
        }

        public static ContentCaptureSession b(View view) {
            return view.getContentCaptureSession();
        }

        public static List<Rect> c(View view) {
            return view.getSystemGestureExclusionRects();
        }

        public static void d(View view, Context context, int[] nArray, AttributeSet attributeSet, TypedArray typedArray, int n3, int n4) {
            view.saveAttributeDataForStyleable(context, nArray, attributeSet, typedArray, n3, n4);
        }

        public static void e(View view, r0.a a4) {
            view.setContentCaptureSession(null);
        }

        public static void f(View view, List<Rect> list) {
            view.setSystemGestureExclusionRects(list);
        }
    }

    public static abstract class n {
        public static int a(View view) {
            return view.getImportantForContentCapture();
        }

        public static CharSequence b(View view) {
            return view.getStateDescription();
        }

        public static y2 c(View view) {
            if ((view = view.getWindowInsetsController()) != null) {
                return y2.e((WindowInsetsController)view);
            }
            return null;
        }

        public static boolean d(View view) {
            return view.isImportantForContentCapture();
        }

        public static void e(View view, int n3) {
            view.setImportantForContentCapture(n3);
        }

        public static void f(View view, CharSequence charSequence) {
            view.setStateDescription(charSequence);
        }
    }

    public static final abstract class o {
        public static String[] a(View view) {
            return view.getReceiveContentMimeTypes();
        }

        public static d b(View view, d d3) {
            ContentInfo contentInfo = d3.f();
            if ((view = view.performReceiveContent(contentInfo)) == null) {
                return null;
            }
            if (view == contentInfo) {
                return d3;
            }
            return o0.d.g((ContentInfo)view);
        }

        public static void c(View view, String[] stringArray, g0 g02) {
            if (g02 == null) {
                view.setOnReceiveContentListener(stringArray, null);
                return;
            }
            view.setOnReceiveContentListener(stringArray, (OnReceiveContentListener)new p(g02));
        }
    }

    public static final class p
    implements OnReceiveContentListener {
        public final g0 a;

        public p(g0 g02) {
            this.a = g02;
        }

        public ContentInfo onReceiveContent(View object, ContentInfo contentInfo) {
            d d3 = o0.d.g(contentInfo);
            if ((object = this.a.a((View)object, d3)) == null) {
                return null;
            }
            if (object == d3) {
                return contentInfo;
            }
            return ((d)object).f();
        }
    }

    public static interface q {
    }

    public static class r {
        public static final ArrayList d = new ArrayList();
        public WeakHashMap a = null;
        public SparseArray b = null;
        public WeakReference c = null;

        public static r a(View view) {
            r r3;
            int n3 = b0.b.tag_unhandled_key_event_manager;
            r r4 = r3 = (r)view.getTag(n3);
            if (r3 == null) {
                r4 = new r();
                view.setTag(n3, (Object)r4);
            }
            return r4;
        }

        public boolean b(View view, KeyEvent keyEvent) {
            if (keyEvent.getAction() == 0) {
                this.g();
            }
            view = this.c(view, keyEvent);
            if (keyEvent.getAction() == 0) {
                int n3 = keyEvent.getKeyCode();
                if (view != null && !KeyEvent.isModifierKey((int)n3)) {
                    this.d().put(n3, new WeakReference<View>(view));
                }
            }
            return view != null;
        }

        public final View c(View view, KeyEvent keyEvent) {
            WeakHashMap weakHashMap = this.a;
            if (weakHashMap != null && weakHashMap.containsKey(view)) {
                if (view instanceof ViewGroup) {
                    weakHashMap = (ViewGroup)view;
                    for (int i3 = weakHashMap.getChildCount() - 1; i3 >= 0; --i3) {
                        View view2 = this.c(weakHashMap.getChildAt(i3), keyEvent);
                        if (view2 == null) continue;
                        return view2;
                    }
                }
                if (this.e(view, keyEvent)) {
                    return view;
                }
            }
            return null;
        }

        public final SparseArray d() {
            if (this.b == null) {
                this.b = new SparseArray();
            }
            return this.b;
        }

        public final boolean e(View object, KeyEvent keyEvent) {
            int n3;
            if ((object = (ArrayList)object.getTag(b0.b.tag_unhandled_key_listeners)) != null && (n3 = ((ArrayList)object).size() - 1) >= 0) {
                s.a(((ArrayList)object).get(n3));
                throw null;
            }
            return false;
        }

        public boolean f(KeyEvent keyEvent) {
            int n3;
            WeakReference weakReference = this.c;
            if (weakReference != null && weakReference.get() == keyEvent) {
                return false;
            }
            this.c = new WeakReference<KeyEvent>(keyEvent);
            SparseArray sparseArray = this.d();
            if (keyEvent.getAction() == 1 && (n3 = sparseArray.indexOfKey(keyEvent.getKeyCode())) >= 0) {
                weakReference = (WeakReference)sparseArray.valueAt(n3);
                sparseArray.removeAt(n3);
            } else {
                weakReference = null;
            }
            WeakReference weakReference2 = weakReference;
            if (weakReference == null) {
                weakReference2 = (WeakReference)sparseArray.get(keyEvent.getKeyCode());
            }
            if (weakReference2 != null) {
                weakReference = (View)weakReference2.get();
                if (weakReference != null && weakReference.isAttachedToWindow()) {
                    this.e((View)weakReference, keyEvent);
                }
                return true;
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void g() {
            ArrayList arrayList;
            View view = this.a;
            if (view != null) {
                view.clear();
            }
            if ((arrayList = d).isEmpty()) {
                return;
            }
            synchronized (arrayList) {
                try {
                    if (this.a == null) {
                        this.a = view = new WeakHashMap();
                    }
                }
                catch (Throwable throwable) {}
                throw throwable;
                for (int i3 = arrayList.size() - 1; i3 >= 0; --i3) {
                    ArrayList arrayList2 = d;
                    view = (View)((WeakReference)arrayList2.get(i3)).get();
                    if (view == null) {
                        arrayList2.remove(i3);
                        continue;
                    }
                    this.a.put(view, Boolean.TRUE);
                    view = view.getParent();
                    while (view instanceof View) {
                        this.a.put(view, Boolean.TRUE);
                        view = view.getParent();
                    }
                }
                return;
            }
        }
    }
}

