/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.UiModeManager
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.media.AudioManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.LocaleList
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.PowerManager
 *  android.text.TextUtils
 *  android.util.AndroidRuntimeException
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.ContextThemeWrapper
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.Window$Callback
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.PopupWindow
 *  android.widget.TextView
 *  android.window.OnBackInvokedDispatcher
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.b;
import androidx.appcompat.app.r;
import androidx.appcompat.app.t;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.s0;
import androidx.appcompat.widget.t0;
import androidx.lifecycle.f;
import c.c;
import d.a;
import f0.h;
import h.b;
import h.e;
import h.f;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import o0.f0;
import o0.h1;
import o0.j1;
import o0.t;
import o0.u;
import o0.x0;
import o0.z1;
import org.xmlpull.v1.XmlPullParser;

public class AppCompatDelegateImpl
extends androidx.appcompat.app.d
implements e.a,
LayoutInflater.Factory2 {
    public static final o.r l0 = new o.r();
    public static final boolean m0 = false;
    public static final int[] n0 = new int[]{16842836};
    public static final boolean o0 = "robolectric".equals(Build.FINGERPRINT) ^ true;
    public h1 A = null;
    public boolean B = true;
    public boolean C;
    public ViewGroup D;
    public TextView E;
    public View F;
    public boolean G;
    public boolean H;
    public boolean I;
    public boolean J;
    public boolean K;
    public boolean L;
    public boolean M;
    public boolean N;
    public PanelFeatureState[] O;
    public PanelFeatureState P;
    public boolean Q;
    public boolean R;
    public boolean S;
    public boolean T;
    public Configuration U;
    public int V = -100;
    public int W;
    public int X;
    public boolean Y;
    public n Z;
    public n a0;
    public boolean b0;
    public int c0;
    public final Runnable d0 = new Runnable(this){
        public final AppCompatDelegateImpl c;
        {
            this.c = appCompatDelegateImpl;
        }

        @Override
        public void run() {
            AppCompatDelegateImpl appCompatDelegateImpl = this.c;
            if ((appCompatDelegateImpl.c0 & 1) != 0) {
                appCompatDelegateImpl.f0(0);
            }
            appCompatDelegateImpl = this.c;
            if ((appCompatDelegateImpl.c0 & 0x1000) != 0) {
                appCompatDelegateImpl.f0(108);
            }
            appCompatDelegateImpl = this.c;
            appCompatDelegateImpl.b0 = false;
            appCompatDelegateImpl.c0 = 0;
        }
    };
    public boolean e0;
    public Rect f0;
    public Rect g0;
    public androidx.appcompat.app.n h0;
    public androidx.appcompat.app.o i0;
    public OnBackInvokedDispatcher j0;
    public OnBackInvokedCallback k0;
    public final Object l;
    public final Context m;
    public Window n;
    public l o;
    public final b p;
    public ActionBar q;
    public MenuInflater r;
    public CharSequence s;
    public androidx.appcompat.widget.t t;
    public f u;
    public q v;
    public h.b w;
    public ActionBarContextView x;
    public PopupWindow y;
    public Runnable z;

    public AppCompatDelegateImpl(Activity activity, b b3) {
        this((Context)activity, null, b3, activity);
    }

    public AppCompatDelegateImpl(Dialog dialog, b b3) {
        this(dialog.getContext(), dialog.getWindow(), b3, dialog);
    }

    public AppCompatDelegateImpl(Context object, Window window, b object2, Object object3) {
        this.m = object;
        this.p = object2;
        this.l = object3;
        if (this.V == -100 && object3 instanceof Dialog && (object = this.X0()) != null) {
            this.V = ((AppCompatActivity)object).V().n();
        }
        if (this.V == -100 && (object = (Integer)((o.r)(object2 = l0)).get(object3.getClass().getName())) != null) {
            this.V = (Integer)object;
            ((o.r)object2).remove(object3.getClass().getName());
        }
        if (window != null) {
            this.S(window);
        }
        androidx.appcompat.widget.g.h();
    }

    public static Configuration k0(Configuration configuration, Configuration configuration2) {
        Configuration configuration3 = new Configuration();
        configuration3.fontScale = 0.0f;
        if (configuration2 != null && configuration.diff(configuration2) != 0) {
            int n3;
            int n4;
            float f3 = configuration.fontScale;
            float f4 = configuration2.fontScale;
            if (f3 != f4) {
                configuration3.fontScale = f4;
            }
            if ((n4 = configuration.mcc) != (n3 = configuration2.mcc)) {
                configuration3.mcc = n3;
            }
            if ((n3 = configuration.mnc) != (n4 = configuration2.mnc)) {
                configuration3.mnc = n4;
            }
            androidx.appcompat.app.AppCompatDelegateImpl$i.a(configuration, configuration2, configuration3);
            n4 = configuration.touchscreen;
            n3 = configuration2.touchscreen;
            if (n4 != n3) {
                configuration3.touchscreen = n3;
            }
            if ((n4 = configuration.keyboard) != (n3 = configuration2.keyboard)) {
                configuration3.keyboard = n3;
            }
            if ((n3 = configuration.keyboardHidden) != (n4 = configuration2.keyboardHidden)) {
                configuration3.keyboardHidden = n4;
            }
            if ((n3 = configuration.navigation) != (n4 = configuration2.navigation)) {
                configuration3.navigation = n4;
            }
            if ((n3 = configuration.navigationHidden) != (n4 = configuration2.navigationHidden)) {
                configuration3.navigationHidden = n4;
            }
            if ((n4 = configuration.orientation) != (n3 = configuration2.orientation)) {
                configuration3.orientation = n3;
            }
            if (((n3 = configuration.screenLayout) & 0xF) != ((n4 = configuration2.screenLayout) & 0xF)) {
                configuration3.screenLayout |= n4 & 0xF;
            }
            if (((n3 = configuration.screenLayout) & 0xC0) != ((n4 = configuration2.screenLayout) & 0xC0)) {
                configuration3.screenLayout |= n4 & 0xC0;
            }
            if (((n3 = configuration.screenLayout) & 0x30) != ((n4 = configuration2.screenLayout) & 0x30)) {
                configuration3.screenLayout |= n4 & 0x30;
            }
            if (((n3 = configuration.screenLayout) & 0x300) != ((n4 = configuration2.screenLayout) & 0x300)) {
                configuration3.screenLayout |= n4 & 0x300;
            }
            androidx.appcompat.app.AppCompatDelegateImpl$j.a(configuration, configuration2, configuration3);
            n4 = configuration.uiMode;
            n3 = configuration2.uiMode;
            if ((n4 & 0xF) != (n3 & 0xF)) {
                configuration3.uiMode |= n3 & 0xF;
            }
            if (((n3 = configuration.uiMode) & 0x30) != ((n4 = configuration2.uiMode) & 0x30)) {
                configuration3.uiMode |= n4 & 0x30;
            }
            if ((n3 = configuration.screenWidthDp) != (n4 = configuration2.screenWidthDp)) {
                configuration3.screenWidthDp = n4;
            }
            if ((n4 = configuration.screenHeightDp) != (n3 = configuration2.screenHeightDp)) {
                configuration3.screenHeightDp = n3;
            }
            if ((n4 = configuration.smallestScreenWidthDp) != (n3 = configuration2.smallestScreenWidthDp)) {
                configuration3.smallestScreenWidthDp = n3;
            }
            if ((n3 = configuration.densityDpi) != (n4 = configuration2.densityDpi)) {
                configuration3.densityDpi = n4;
            }
        }
        return configuration3;
    }

    @Override
    public void A(Bundle bundle) {
    }

    public boolean A0() {
        boolean bl = this.Q;
        this.Q = false;
        Object object = this.q0(0, false);
        if (object != null && ((PanelFeatureState)object).o) {
            if (!bl) {
                this.Z((PanelFeatureState)object, true);
            }
            return true;
        }
        object = this.w;
        if (object != null) {
            ((h.b)object).c();
            return true;
        }
        object = this.r();
        return object != null && ((ActionBar)object).g();
    }

    @Override
    public void B() {
        this.P(true, false);
    }

    public boolean B0(int n3, KeyEvent keyEvent) {
        boolean bl = true;
        if (n3 != 4) {
            if (n3 == 82) {
                this.C0(0, keyEvent);
                return true;
            }
        } else {
            if ((keyEvent.getFlags() & 0x80) == 0) {
                bl = false;
            }
            this.Q = bl;
        }
        return false;
    }

    @Override
    public void C() {
        ActionBar actionBar = this.r();
        if (actionBar != null) {
            actionBar.r(false);
        }
    }

    public final boolean C0(int n3, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() == 0) {
            PanelFeatureState panelFeatureState = this.q0(n3, true);
            if (!panelFeatureState.o) {
                return this.M0(panelFeatureState, keyEvent);
            }
        }
        return false;
    }

    public boolean D0(int n3, KeyEvent object) {
        Object object2 = this.r();
        if (object2 != null && ((ActionBar)object2).n(n3, (KeyEvent)object)) {
            return true;
        }
        object2 = this.P;
        if (object2 != null && this.L0((PanelFeatureState)object2, object.getKeyCode(), (KeyEvent)object, 1)) {
            object = this.P;
            if (object != null) {
                object.n = true;
            }
            return true;
        }
        if (this.P == null) {
            object2 = this.q0(0, true);
            this.M0((PanelFeatureState)object2, (KeyEvent)object);
            boolean bl = this.L0((PanelFeatureState)object2, object.getKeyCode(), (KeyEvent)object, 1);
            ((PanelFeatureState)object2).m = false;
            if (bl) {
                return true;
            }
        }
        return false;
    }

    public boolean E0(int n3, KeyEvent keyEvent) {
        if (n3 != 4) {
            if (n3 == 82) {
                this.F0(0, keyEvent);
                return true;
            }
        } else if (this.A0()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean F(int n3) {
        n3 = this.O0(n3);
        if (this.M && n3 == 108) {
            return false;
        }
        if (this.I && n3 == 1) {
            this.I = false;
        }
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 5) {
                    if (n3 != 10) {
                        if (n3 != 108) {
                            if (n3 != 109) {
                                return this.n.requestFeature(n3);
                            }
                            this.W0();
                            this.J = true;
                            return true;
                        }
                        this.W0();
                        this.I = true;
                        return true;
                    }
                    this.W0();
                    this.K = true;
                    return true;
                }
                this.W0();
                this.H = true;
                return true;
            }
            this.W0();
            this.G = true;
            return true;
        }
        this.W0();
        this.M = true;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean F0(int n3, KeyEvent keyEvent) {
        boolean bl;
        androidx.appcompat.widget.t t3;
        if (this.w != null) {
            return false;
        }
        boolean bl2 = true;
        PanelFeatureState panelFeatureState = this.q0(n3, true);
        if (n3 == 0 && (t3 = this.t) != null && t3.f() && !ViewConfiguration.get((Context)this.m).hasPermanentMenuKey()) {
            if (!this.t.a()) {
                if (this.T) return false;
                if (!this.M0(panelFeatureState, keyEvent)) return false;
                bl = this.t.e();
            } else {
                bl = this.t.d();
            }
        } else {
            bl = panelFeatureState.o;
            if (!bl && !panelFeatureState.n) {
                if (!panelFeatureState.m) return false;
                if (panelFeatureState.r) {
                    panelFeatureState.m = false;
                    bl = this.M0(panelFeatureState, keyEvent);
                } else {
                    bl = true;
                }
                if (!bl) return false;
                this.J0(panelFeatureState, keyEvent);
                bl = bl2;
            } else {
                this.Z(panelFeatureState, true);
            }
        }
        if (!bl) return bl;
        keyEvent = (AudioManager)this.m.getApplicationContext().getSystemService("audio");
        if (keyEvent != null) {
            keyEvent.playSoundEffect(0);
            return bl;
        }
        Log.w((String)"AppCompatDelegate", (String)"Couldn't get audio manager");
        return bl;
    }

    @Override
    public void G(int n3) {
        this.h0();
        ViewGroup viewGroup = (ViewGroup)this.D.findViewById(0x1020002);
        viewGroup.removeAllViews();
        LayoutInflater.from((Context)this.m).inflate(n3, viewGroup);
        this.o.c(this.n.getCallback());
    }

    public void G0(int n3) {
        ActionBar actionBar;
        if (n3 == 108 && (actionBar = this.r()) != null) {
            actionBar.h(true);
        }
    }

    @Override
    public void H(View view) {
        this.h0();
        ViewGroup viewGroup = (ViewGroup)this.D.findViewById(0x1020002);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.o.c(this.n.getCallback());
    }

    public void H0(int n3) {
        if (n3 == 108) {
            ActionBar actionBar = this.r();
            if (actionBar != null) {
                actionBar.h(false);
                return;
            }
        } else if (n3 == 0) {
            PanelFeatureState panelFeatureState = this.q0(n3, true);
            if (panelFeatureState.o) {
                this.Z(panelFeatureState, false);
            }
        }
    }

    @Override
    public void I(View view, ViewGroup.LayoutParams layoutParams) {
        this.h0();
        ViewGroup viewGroup = (ViewGroup)this.D.findViewById(0x1020002);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.o.c(this.n.getCallback());
    }

    public void I0(ViewGroup viewGroup) {
    }

    @Override
    public void J(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        Object object;
        super.J(onBackInvokedDispatcher);
        OnBackInvokedDispatcher onBackInvokedDispatcher2 = this.j0;
        if (onBackInvokedDispatcher2 != null && (object = this.k0) != null) {
            androidx.appcompat.app.AppCompatDelegateImpl$k.c(onBackInvokedDispatcher2, object);
            this.k0 = null;
        }
        this.j0 = onBackInvokedDispatcher == null && (object = this.l) instanceof Activity && ((Activity)object).getWindow() != null ? androidx.appcompat.app.AppCompatDelegateImpl$k.a((Activity)this.l) : onBackInvokedDispatcher;
        this.a1();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void J0(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        block17: {
            int n3;
            WindowManager windowManager;
            block16: {
                block15: {
                    Window.Callback callback;
                    block14: {
                        if (panelFeatureState.o || this.T) return;
                        if (panelFeatureState.a == 0 && (this.m.getResources().getConfiguration().screenLayout & 0xF) == 4) {
                            return;
                        }
                        callback = this.s0();
                        if (callback != null && !callback.onMenuOpened(panelFeatureState.a, (Menu)panelFeatureState.j)) {
                            this.Z(panelFeatureState, true);
                            return;
                        }
                        windowManager = (WindowManager)this.m.getSystemService("window");
                        if (windowManager == null || !this.M0(panelFeatureState, keyEvent)) return;
                        keyEvent = panelFeatureState.g;
                        if (keyEvent == null || panelFeatureState.q) break block14;
                        keyEvent = panelFeatureState.i;
                        if (keyEvent == null || (keyEvent = keyEvent.getLayoutParams()) == null || keyEvent.width != -1) break block15;
                        n3 = -1;
                        break block16;
                    }
                    if (keyEvent == null) {
                        if (!this.v0(panelFeatureState) || panelFeatureState.g == null) {
                            return;
                        }
                    } else if (panelFeatureState.q && keyEvent.getChildCount() > 0) {
                        panelFeatureState.g.removeAllViews();
                    }
                    if (!this.u0(panelFeatureState) || !panelFeatureState.b()) break block17;
                    callback = panelFeatureState.h.getLayoutParams();
                    keyEvent = callback;
                    if (callback == null) {
                        keyEvent = new ViewGroup.LayoutParams(-2, -2);
                    }
                    n3 = panelFeatureState.b;
                    panelFeatureState.g.setBackgroundResource(n3);
                    callback = panelFeatureState.h.getParent();
                    if (callback instanceof ViewGroup) {
                        ((ViewGroup)callback).removeView(panelFeatureState.h);
                    }
                    panelFeatureState.g.addView(panelFeatureState.h, (ViewGroup.LayoutParams)keyEvent);
                    if (!panelFeatureState.h.hasFocus()) {
                        panelFeatureState.h.requestFocus();
                    }
                }
                n3 = -2;
            }
            panelFeatureState.n = false;
            keyEvent = new WindowManager.LayoutParams(n3, -2, panelFeatureState.d, panelFeatureState.e, 1002, 0x820000, -3);
            keyEvent.gravity = panelFeatureState.c;
            keyEvent.windowAnimations = panelFeatureState.f;
            windowManager.addView((View)panelFeatureState.g, (ViewGroup.LayoutParams)keyEvent);
            panelFeatureState.o = true;
            if (panelFeatureState.a != 0) return;
            this.a1();
            return;
        }
        panelFeatureState.q = true;
    }

    @Override
    public void K(int n3) {
        this.W = n3;
    }

    public final ActionBar K0() {
        return this.q;
    }

    @Override
    public final void L(CharSequence charSequence) {
        this.s = charSequence;
        androidx.appcompat.widget.t t3 = this.t;
        if (t3 != null) {
            t3.setWindowTitle(charSequence);
            return;
        }
        if (this.K0() != null) {
            this.K0().s(charSequence);
            return;
        }
        t3 = this.E;
        if (t3 != null) {
            t3.setText(charSequence);
        }
    }

    public final boolean L0(PanelFeatureState panelFeatureState, int n3, KeyEvent keyEvent, int n4) {
        boolean bl;
        block7: {
            boolean bl2;
            block6: {
                bl = keyEvent.isSystem();
                bl2 = false;
                if (bl) {
                    return false;
                }
                if (panelFeatureState.m) break block6;
                bl = bl2;
                if (!this.M0(panelFeatureState, keyEvent)) break block7;
            }
            androidx.appcompat.view.menu.e e3 = panelFeatureState.j;
            bl = bl2;
            if (e3 != null) {
                bl = e3.performShortcut(n3, keyEvent, n4);
            }
        }
        if (bl && (n4 & 1) == 0 && this.t == null) {
            this.Z(panelFeatureState, true);
        }
        return bl;
    }

    public final boolean M0(PanelFeatureState object, KeyEvent object2) {
        Object object3;
        int n3;
        if (this.T) {
            return false;
        }
        if (((PanelFeatureState)object).m) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.P;
        if (panelFeatureState != null && panelFeatureState != object) {
            this.Z(panelFeatureState, false);
        }
        if ((panelFeatureState = this.s0()) != null) {
            ((PanelFeatureState)object).i = panelFeatureState.onCreatePanelView(((PanelFeatureState)object).a);
        }
        n3 = (n3 = ((PanelFeatureState)object).a) != 0 && n3 != 108 ? 0 : 1;
        if (n3 != 0 && (object3 = this.t) != null) {
            object3.setMenuPrepared();
        }
        if (((PanelFeatureState)object).i == null) {
            if (n3 != 0) {
                this.K0();
            }
            if ((object3 = ((PanelFeatureState)object).j) == null || ((PanelFeatureState)object).r) {
                if (!(object3 != null || this.w0((PanelFeatureState)object) && ((PanelFeatureState)object).j != null)) {
                    return false;
                }
                if (n3 != 0 && this.t != null) {
                    if (this.u == null) {
                        this.u = new f(this);
                    }
                    this.t.setMenu(((PanelFeatureState)object).j, this.u);
                }
                ((PanelFeatureState)object).j.i0();
                if (!panelFeatureState.onCreatePanelMenu(((PanelFeatureState)object).a, ((PanelFeatureState)object).j)) {
                    ((PanelFeatureState)object).c(null);
                    if (n3 != 0 && (object = this.t) != null) {
                        object.setMenu(null, this.u);
                    }
                    return false;
                }
                ((PanelFeatureState)object).r = false;
            }
            ((PanelFeatureState)object).j.i0();
            object3 = ((PanelFeatureState)object).s;
            if (object3 != null) {
                ((PanelFeatureState)object).j.S((Bundle)object3);
                ((PanelFeatureState)object).s = null;
            }
            if (!panelFeatureState.onPreparePanel(0, ((PanelFeatureState)object).i, ((PanelFeatureState)object).j)) {
                if (n3 != 0 && (object2 = this.t) != null) {
                    object2.setMenu(null, this.u);
                }
                ((PanelFeatureState)object).j.h0();
                return false;
            }
            n3 = object2 != null ? object2.getDeviceId() : -1;
            boolean bl = KeyCharacterMap.load((int)n3).getKeyboardType() != 1;
            ((PanelFeatureState)object).p = bl;
            ((PanelFeatureState)object).j.setQwertyMode(bl);
            ((PanelFeatureState)object).j.h0();
        }
        ((PanelFeatureState)object).m = true;
        ((PanelFeatureState)object).n = false;
        this.P = object;
        return true;
    }

    public final void N0(boolean bl) {
        Object object = this.t;
        if (object != null && object.f() && (!ViewConfiguration.get((Context)this.m).hasPermanentMenuKey() || this.t.c())) {
            Window.Callback callback = this.s0();
            if (this.t.a() && bl) {
                this.t.d();
                if (!this.T) {
                    callback.onPanelClosed(108, (Menu)this.q0((int)0, (boolean)true).j);
                    return;
                }
            } else if (callback != null && !this.T) {
                if (this.b0 && (this.c0 & 1) != 0) {
                    this.n.getDecorView().removeCallbacks(this.d0);
                    this.d0.run();
                }
                object = this.q0(0, true);
                androidx.appcompat.view.menu.e e3 = ((PanelFeatureState)object).j;
                if (e3 != null && !((PanelFeatureState)object).r && callback.onPreparePanel(0, ((PanelFeatureState)object).i, (Menu)e3)) {
                    callback.onMenuOpened(108, (Menu)((PanelFeatureState)object).j);
                    this.t.e();
                }
            }
            return;
        }
        object = this.q0(0, true);
        ((PanelFeatureState)object).q = true;
        this.Z((PanelFeatureState)object, false);
        this.J0((PanelFeatureState)object, null);
    }

    public final boolean O(boolean bl) {
        return this.P(bl, true);
    }

    public final int O0(int n3) {
        if (n3 == 8) {
            return 108;
        }
        int n4 = n3;
        if (n3 == 9) {
            n4 = 109;
        }
        return n4;
    }

    public final boolean P(boolean bl, boolean bl2) {
        if (this.T) {
            return false;
        }
        int n3 = this.U();
        int n4 = this.z0(this.m, n3);
        Object object = Build.VERSION.SDK_INT < 33 ? this.T(this.m) : null;
        k0.b b3 = object;
        if (!bl2) {
            b3 = object;
            if (object != null) {
                b3 = this.p0(this.m.getResources().getConfiguration());
            }
        }
        bl = this.Z0(n4, b3, bl);
        if (n3 == 0) {
            this.o0(this.m).e();
        } else {
            object = this.Z;
            if (object != null) {
                ((n)object).a();
            }
        }
        if (n3 == 3) {
            this.n0(this.m).e();
            return bl;
        }
        object = this.a0;
        if (object != null) {
            ((n)object).a();
        }
        return bl;
    }

    public void P0(Configuration configuration, k0.b b3) {
        androidx.appcompat.app.AppCompatDelegateImpl$i.d(configuration, b3);
    }

    public boolean Q() {
        return this.O(true);
    }

    public void Q0(k0.b b3) {
        androidx.appcompat.app.AppCompatDelegateImpl$i.c(b3);
    }

    public final void R() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout)this.D.findViewById(0x1020002);
        View view = this.n.getDecorView();
        contentFrameLayout.setDecorPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        view = this.m.obtainStyledAttributes(c.j.AppCompatTheme);
        view.getValue(c.j.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        view.getValue(c.j.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        int n3 = c.j.AppCompatTheme_windowFixedWidthMajor;
        if (view.hasValue(n3)) {
            view.getValue(n3, contentFrameLayout.getFixedWidthMajor());
        }
        if (view.hasValue(n3 = c.j.AppCompatTheme_windowFixedWidthMinor)) {
            view.getValue(n3, contentFrameLayout.getFixedWidthMinor());
        }
        if (view.hasValue(n3 = c.j.AppCompatTheme_windowFixedHeightMajor)) {
            view.getValue(n3, contentFrameLayout.getFixedHeightMajor());
        }
        if (view.hasValue(n3 = c.j.AppCompatTheme_windowFixedHeightMinor)) {
            view.getValue(n3, contentFrameLayout.getFixedHeightMinor());
        }
        view.recycle();
        contentFrameLayout.requestLayout();
    }

    public final boolean R0() {
        ViewGroup viewGroup;
        return this.C && (viewGroup = this.D) != null && viewGroup.isLaidOut();
    }

    public final void S(Window window) {
        if (this.n == null) {
            Window.Callback callback = window.getCallback();
            if (!(callback instanceof l)) {
                callback = new l(this, callback);
                this.o = callback;
                window.setCallback(callback);
                m0 m02 = androidx.appcompat.widget.m0.u(this.m, null, n0);
                callback = m02.h(0);
                if (callback != null) {
                    window.setBackgroundDrawable((Drawable)callback);
                }
                m02.x();
                this.n = window;
                if (Build.VERSION.SDK_INT >= 33 && this.j0 == null) {
                    this.J(null);
                }
                return;
            }
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }

    public final boolean S0(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View view = this.n.getDecorView();
        while (true) {
            if (viewParent == null) {
                return true;
            }
            if (viewParent == view || !(viewParent instanceof View) || ((View)viewParent).isAttachedToWindow()) break;
            viewParent = viewParent.getParent();
        }
        return false;
    }

    public k0.b T(Context object) {
        if (Build.VERSION.SDK_INT >= 33) {
            return null;
        }
        k0.b b3 = androidx.appcompat.app.d.q();
        if (b3 == null) {
            return null;
        }
        if ((b3 = androidx.appcompat.app.p.b(b3, (k0.b)(object = this.p0(object.getApplicationContext().getResources().getConfiguration())))).e()) {
            return object;
        }
        return b3;
    }

    public boolean T0() {
        if (this.j0 == null) {
            return false;
        }
        PanelFeatureState panelFeatureState = this.q0(0, false);
        if (panelFeatureState != null && panelFeatureState.o) {
            return true;
        }
        return this.w != null;
    }

    public final int U() {
        int n3 = this.V;
        if (n3 != -100) {
            return n3;
        }
        return androidx.appcompat.app.d.m();
    }

    public h.b U0(b.a a4) {
        if (a4 != null) {
            Object object = this.w;
            if (object != null) {
                ((h.b)object).c();
            }
            a4 = new g(this, a4);
            object = this.r();
            if (object != null) {
                h.b b3;
                this.w = b3 = ((ActionBar)object).t(a4);
                if (b3 != null && (object = this.p) != null) {
                    object.q(b3);
                }
            }
            if (this.w == null) {
                this.w = this.V0(a4);
            }
            this.a1();
            return this.w;
        }
        throw new IllegalArgumentException("ActionMode callback can not be null.");
    }

    public void V(int n3, PanelFeatureState panelFeatureState, Menu panelFeatureStateArray) {
        PanelFeatureState panelFeatureState2 = panelFeatureState;
        Object object = panelFeatureStateArray;
        if (panelFeatureStateArray == null) {
            PanelFeatureState panelFeatureState3 = panelFeatureState;
            if (panelFeatureState == null) {
                panelFeatureState3 = panelFeatureState;
                if (n3 >= 0) {
                    object = this.O;
                    panelFeatureState3 = panelFeatureState;
                    if (n3 < ((PanelFeatureState[])object).length) {
                        panelFeatureState3 = object[n3];
                    }
                }
            }
            panelFeatureState2 = panelFeatureState3;
            object = panelFeatureStateArray;
            if (panelFeatureState3 != null) {
                object = panelFeatureState3.j;
                panelFeatureState2 = panelFeatureState3;
            }
        }
        if ((panelFeatureState2 == null || panelFeatureState2.o) && !this.T) {
            this.o.d(this.n.getCallback(), n3, (Menu)object);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public h.b V0(b.a object) {
        b b3;
        void var1_6;
        b b4;
        this.g0();
        Object object2 = this.w;
        if (object2 != null) {
            ((h.b)object2).c();
        }
        object2 = object;
        if (!(object instanceof g)) {
            object2 = new g(this, (b.a)object);
        }
        if ((b4 = this.p) != null && !this.T) {
            try {
                h.b b5 = b4.f((b.a)object2);
            }
            catch (AbstractMethodError abstractMethodError) {}
        }
        Object var1_5 = null;
        if (var1_6 != null) {
            this.w = var1_6;
        } else {
            ActionBarContextView actionBarContextView = this.x;
            boolean bl = true;
            if (actionBarContextView == null) {
                if (this.L) {
                    void var1_11;
                    Resources.Theme theme;
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme2 = this.m.getTheme();
                    theme2.resolveAttribute(c.a.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        theme = this.m.getResources().newTheme();
                        theme.setTo(theme2);
                        theme.applyStyle(typedValue.resourceId, true);
                        h.d d3 = new h.d(this.m, 0);
                        d3.getTheme().setTo(theme);
                    } else {
                        Context context = this.m;
                    }
                    this.x = new ActionBarContextView((Context)var1_11);
                    theme = new PopupWindow((Context)var1_11, null, c.a.actionModePopupWindowStyle);
                    this.y = theme;
                    androidx.core.widget.i.b((PopupWindow)theme, 2);
                    this.y.setContentView((View)this.x);
                    this.y.setWidth(-1);
                    var1_11.getTheme().resolveAttribute(c.a.actionBarSize, typedValue, true);
                    int n3 = TypedValue.complexToDimensionPixelSize((int)typedValue.data, (DisplayMetrics)var1_11.getResources().getDisplayMetrics());
                    this.x.setContentHeight(n3);
                    this.y.setHeight(-2);
                    this.z = new Runnable(this){
                        public final AppCompatDelegateImpl c;
                        {
                            this.c = appCompatDelegateImpl;
                        }

                        @Override
                        public void run() {
                            AppCompatDelegateImpl appCompatDelegateImpl = this.c;
                            appCompatDelegateImpl.y.showAtLocation((View)appCompatDelegateImpl.x, 55, 0, 0);
                            this.c.g0();
                            if (this.c.R0()) {
                                this.c.x.setAlpha(0.0f);
                                appCompatDelegateImpl = this.c;
                                appCompatDelegateImpl.A = x0.e((View)appCompatDelegateImpl.x).b(1.0f);
                                this.c.A.g(new j1(this){
                                    public final d a;
                                    {
                                        this.a = d3;
                                    }

                                    @Override
                                    public void b(View view) {
                                        this.a.c.x.setAlpha(1.0f);
                                        this.a.c.A.g(null);
                                        this.a.c.A = null;
                                    }

                                    @Override
                                    public void c(View view) {
                                        this.a.c.x.setVisibility(0);
                                    }
                                });
                                return;
                            }
                            this.c.x.setAlpha(1.0f);
                            this.c.x.setVisibility(0);
                        }
                    };
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat)this.D.findViewById(c.f.action_mode_bar_stub);
                    if (viewStubCompat != null) {
                        viewStubCompat.setLayoutInflater(LayoutInflater.from((Context)this.l0()));
                        this.x = (ActionBarContextView)viewStubCompat.a();
                    }
                }
            }
            if (this.x != null) {
                this.g0();
                this.x.k();
                Context context = this.x.getContext();
                ActionBarContextView actionBarContextView2 = this.x;
                if (this.y != null) {
                    bl = false;
                }
                e e3 = new e(context, actionBarContextView2, (b.a)object2, bl);
                if (object2.b(e3, ((h.b)e3).e())) {
                    ((h.b)e3).k();
                    this.x.h(e3);
                    this.w = e3;
                    if (this.R0()) {
                        h1 h12;
                        this.x.setAlpha(0.0f);
                        this.A = h12 = x0.e((View)this.x).b(1.0f);
                        h12.g(new j1(this){
                            public final AppCompatDelegateImpl a;
                            {
                                this.a = appCompatDelegateImpl;
                            }

                            @Override
                            public void b(View view) {
                                this.a.x.setAlpha(1.0f);
                                this.a.A.g(null);
                                this.a.A = null;
                            }

                            @Override
                            public void c(View view) {
                                this.a.x.setVisibility(0);
                                if (this.a.x.getParent() instanceof View) {
                                    x0.e0((View)this.a.x.getParent());
                                }
                            }
                        });
                    } else {
                        this.x.setAlpha(1.0f);
                        this.x.setVisibility(0);
                        if (this.x.getParent() instanceof View) {
                            x0.e0((View)this.x.getParent());
                        }
                    }
                    if (this.y != null) {
                        this.n.getDecorView().post(this.z);
                    }
                } else {
                    this.w = null;
                }
            }
        }
        object2 = this.w;
        if (object2 != null && (b3 = this.p) != null) {
            b3.q((h.b)object2);
        }
        this.a1();
        return this.w;
    }

    public void W(androidx.appcompat.view.menu.e e3) {
        if (this.N) {
            return;
        }
        this.N = true;
        this.t.j();
        Window.Callback callback = this.s0();
        if (callback != null && !this.T) {
            callback.onPanelClosed(108, (Menu)e3);
        }
        this.N = false;
    }

    public final void W0() {
        if (!this.C) {
            return;
        }
        throw new AndroidRuntimeException("Window feature must be requested before adding content");
    }

    public final void X() {
        n n3 = this.Z;
        if (n3 != null) {
            n3.a();
        }
        if ((n3 = this.a0) != null) {
            n3.a();
        }
    }

    public final AppCompatActivity X0() {
        Context context = this.m;
        while (context != null) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity)context;
            }
            if (!(context instanceof ContextWrapper)) break;
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public void Y(int n3) {
        this.Z(this.q0(n3, true), true);
    }

    public final void Y0(Configuration configuration) {
        Activity activity = (Activity)this.l;
        if (activity instanceof androidx.lifecycle.k) {
            if (((androidx.lifecycle.k)activity).t().b().b(f.b.e)) {
                activity.onConfigurationChanged(configuration);
                return;
            }
        } else if (this.S && !this.T) {
            activity.onConfigurationChanged(configuration);
        }
    }

    public void Z(PanelFeatureState panelFeatureState, boolean bl) {
        androidx.appcompat.widget.t t3;
        if (bl && panelFeatureState.a == 0 && (t3 = this.t) != null && t3.a()) {
            this.W(panelFeatureState.j);
            return;
        }
        WindowManager windowManager = (WindowManager)this.m.getSystemService("window");
        if (windowManager != null && panelFeatureState.o && (t3 = panelFeatureState.g) != null) {
            windowManager.removeView((View)t3);
            if (bl) {
                this.V(panelFeatureState.a, panelFeatureState, null);
            }
        }
        panelFeatureState.m = false;
        panelFeatureState.n = false;
        panelFeatureState.o = false;
        panelFeatureState.h = null;
        panelFeatureState.q = true;
        if (this.P == panelFeatureState) {
            this.P = null;
        }
        if (panelFeatureState.a == 0) {
            this.a1();
        }
    }

    public final boolean Z0(int n3, k0.b b3, boolean bl) {
        Configuration configuration = this.a0(this.m, n3, b3, null, false);
        int n4 = this.m0(this.m);
        Object object = this.U;
        Object object2 = object;
        if (object == null) {
            object2 = this.m.getResources().getConfiguration();
        }
        int n5 = object2.uiMode;
        int n6 = configuration.uiMode & 0x30;
        object = this.p0((Configuration)object2);
        object2 = b3 == null ? null : this.p0(configuration);
        boolean bl2 = false;
        n5 = (n5 & 0x30) != n6 ? 512 : 0;
        int n7 = n5;
        if (object2 != null) {
            n7 = n5;
            if (!((k0.b)object).equals(object2)) {
                n7 = n5 | 0x2004;
            }
        }
        boolean bl3 = true;
        if ((~n4 & n7) != 0 && bl && this.R && (o0 || this.S) && (object = this.l) instanceof Activity && !((Activity)object).isChild()) {
            if (Build.VERSION.SDK_INT >= 31 && (n7 & 0x2000) != 0) {
                ((Activity)this.l).getWindow().getDecorView().setLayoutDirection(configuration.getLayoutDirection());
            }
            c0.b.l((Activity)this.l);
            bl = true;
        } else {
            bl = false;
        }
        if (!bl && n7 != 0) {
            bl = bl2;
            if ((n7 & n4) == n7) {
                bl = true;
            }
            this.b1(n6, (k0.b)object2, bl, null);
            bl = bl3;
        }
        if (bl && (object = this.l) instanceof AppCompatActivity) {
            if ((n7 & 0x200) != 0) {
                ((AppCompatActivity)object).a0(n3);
            }
            if ((n7 & 4) != 0) {
                ((AppCompatActivity)this.l).Z(b3);
            }
        }
        if (object2 != null) {
            this.Q0(this.p0(this.m.getResources().getConfiguration()));
        }
        return bl;
    }

    @Override
    public boolean a(androidx.appcompat.view.menu.e object, MenuItem menuItem) {
        Window.Callback callback = this.s0();
        if (callback != null && !this.T && (object = this.j0(((androidx.appcompat.view.menu.e)object).F())) != null) {
            return callback.onMenuItemSelected(((PanelFeatureState)object).a, menuItem);
        }
        return false;
    }

    public final Configuration a0(Context context, int n3, k0.b b3, Configuration configuration, boolean bl) {
        n3 = n3 != 1 ? (n3 != 2 ? (bl ? 0 : context.getApplicationContext().getResources().getConfiguration().uiMode & 0x30) : 32) : 16;
        context = new Configuration();
        context.fontScale = 0.0f;
        if (configuration != null) {
            context.setTo(configuration);
        }
        context.uiMode = n3 | context.uiMode & 0xFFFFFFCF;
        if (b3 != null) {
            this.P0((Configuration)context, b3);
        }
        return context;
    }

    public void a1() {
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackInvokedCallback onBackInvokedCallback;
            boolean bl = this.T0();
            if (bl && this.k0 == null) {
                this.k0 = androidx.appcompat.app.AppCompatDelegateImpl$k.b(this.j0, this);
                return;
            }
            if (!bl && (onBackInvokedCallback = this.k0) != null) {
                androidx.appcompat.app.AppCompatDelegateImpl$k.c(this.j0, onBackInvokedCallback);
                this.k0 = null;
            }
        }
    }

    @Override
    public void b(androidx.appcompat.view.menu.e e3) {
        this.N0(true);
    }

    public final ViewGroup b0() {
        int n3;
        Object object = this.m.obtainStyledAttributes(c.j.AppCompatTheme);
        if (object.hasValue(n3 = c.j.AppCompatTheme_windowActionBar)) {
            ContentFrameLayout contentFrameLayout;
            if (object.getBoolean(c.j.AppCompatTheme_windowNoTitle, false)) {
                this.F(1);
            } else if (object.getBoolean(n3, false)) {
                this.F(108);
            }
            if (object.getBoolean(c.j.AppCompatTheme_windowActionBarOverlay, false)) {
                this.F(109);
            }
            if (object.getBoolean(c.j.AppCompatTheme_windowActionModeOverlay, false)) {
                this.F(10);
            }
            this.L = object.getBoolean(c.j.AppCompatTheme_android_windowIsFloating, false);
            object.recycle();
            this.i0();
            this.n.getDecorView();
            object = LayoutInflater.from((Context)this.m);
            if (!this.M) {
                if (this.L) {
                    object = (ViewGroup)object.inflate(c.g.abc_dialog_title_material, null);
                    this.J = false;
                    this.I = false;
                } else if (this.I) {
                    object = new TypedValue();
                    this.m.getTheme().resolveAttribute(c.a.actionBarTheme, (TypedValue)object, true);
                    object = object.resourceId != 0 ? new h.d(this.m, object.resourceId) : this.m;
                    contentFrameLayout = (ViewGroup)LayoutInflater.from((Context)object).inflate(c.g.abc_screen_toolbar, null);
                    this.t = object = (androidx.appcompat.widget.t)contentFrameLayout.findViewById(c.f.decor_content_parent);
                    object.setWindowCallback(this.s0());
                    if (this.J) {
                        this.t.i(109);
                    }
                    if (this.G) {
                        this.t.i(2);
                    }
                    object = contentFrameLayout;
                    if (this.H) {
                        this.t.i(5);
                        object = contentFrameLayout;
                    }
                } else {
                    object = null;
                }
            } else {
                object = this.K ? (ViewGroup)object.inflate(c.g.abc_screen_simple_overlay_action_mode, null) : (ViewGroup)object.inflate(c.g.abc_screen_simple, null);
            }
            if (object != null) {
                x0.r0((View)object, new f0(this){
                    public final AppCompatDelegateImpl a;
                    {
                        this.a = appCompatDelegateImpl;
                    }

                    @Override
                    public z1 a(View view, z1 z12) {
                        int n3 = z12.l();
                        int n4 = this.a.c1(z12, null);
                        z1 z13 = z12;
                        if (n3 != n4) {
                            z13 = z12.q(z12.j(), n4, z12.k(), z12.i());
                        }
                        return x0.T(view, z13);
                    }
                });
                if (this.t == null) {
                    this.E = (TextView)object.findViewById(c.f.title);
                }
                t0.c((View)object);
                contentFrameLayout = (ContentFrameLayout)object.findViewById(c.f.action_bar_activity_content);
                ViewGroup viewGroup = (ViewGroup)this.n.findViewById(0x1020002);
                if (viewGroup != null) {
                    while (viewGroup.getChildCount() > 0) {
                        View view = viewGroup.getChildAt(0);
                        viewGroup.removeViewAt(0);
                        contentFrameLayout.addView(view);
                    }
                    viewGroup.setId(-1);
                    contentFrameLayout.setId(0x1020002);
                    if (viewGroup instanceof FrameLayout) {
                        ((FrameLayout)viewGroup).setForeground(null);
                    }
                }
                this.n.setContentView((View)object);
                contentFrameLayout.setAttachListener(new ContentFrameLayout.a(this){
                    public final AppCompatDelegateImpl a;
                    {
                        this.a = appCompatDelegateImpl;
                    }

                    @Override
                    public void a() {
                    }

                    @Override
                    public void onDetachedFromWindow() {
                        this.a.d0();
                    }
                });
                return object;
            }
            object = new StringBuilder();
            object.append("AppCompat does not support the current theme features: { windowActionBar: ");
            object.append(this.I);
            object.append(", windowActionBarOverlay: ");
            object.append(this.J);
            object.append(", android:windowIsFloating: ");
            object.append(this.L);
            object.append(", windowActionModeOverlay: ");
            object.append(this.K);
            object.append(", windowNoTitle: ");
            object.append(this.M);
            object.append(" }");
            throw new IllegalArgumentException(object.toString());
        }
        object.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }

    public final void b1(int n3, k0.b b3, boolean bl, Configuration configuration) {
        Resources resources = this.m.getResources();
        Configuration configuration2 = new Configuration(resources.getConfiguration());
        if (configuration != null) {
            configuration2.updateFrom(configuration);
        }
        configuration2.uiMode = n3 | resources.getConfiguration().uiMode & 0xFFFFFFCF;
        if (b3 != null) {
            this.P0(configuration2, b3);
        }
        resources.updateConfiguration(configuration2, null);
        n3 = this.W;
        if (n3 != 0) {
            this.m.setTheme(n3);
            this.m.getTheme().applyStyle(this.W, true);
        }
        if (bl && this.l instanceof Activity) {
            this.Y0(configuration2);
        }
    }

    public View c0(View view, String string, Context context, AttributeSet attributeSet) {
        boolean bl;
        if (this.h0 == null) {
            TypedArray typedArray = this.m.obtainStyledAttributes(c.j.AppCompatTheme);
            String string2 = typedArray.getString(c.j.AppCompatTheme_viewInflaterClass);
            typedArray.recycle();
            if (string2 == null) {
                this.h0 = new androidx.appcompat.app.n();
            } else {
                try {
                    this.h0 = (androidx.appcompat.app.n)this.m.getClassLoader().loadClass(string2).getDeclaredConstructor(null).newInstance(null);
                }
                catch (Throwable throwable) {
                    this.h0 = new androidx.appcompat.app.n();
                }
            }
        }
        boolean bl2 = m0;
        boolean bl3 = bl = false;
        if (bl2) {
            if (this.i0 == null) {
                this.i0 = new androidx.appcompat.app.o();
            }
            if (this.i0.a(attributeSet)) {
                bl3 = true;
            } else if (attributeSet instanceof XmlPullParser) {
                bl3 = bl;
                if (((XmlPullParser)attributeSet).getDepth() > 1) {
                    bl3 = true;
                }
            } else {
                bl3 = this.S0((ViewParent)view);
            }
        }
        return this.h0.r(view, string, context, attributeSet, bl3, bl2, true, s0.c());
    }

    public final int c1(z1 z12, Rect rect) {
        int n3;
        int n4;
        int n5 = 0;
        int n6 = z12 != null ? z12.l() : (rect != null ? rect.top : 0);
        ActionBarContextView actionBarContextView = this.x;
        if (actionBarContextView != null && actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            int n7;
            actionBarContextView = (ViewGroup.MarginLayoutParams)this.x.getLayoutParams();
            boolean bl = this.x.isShown();
            int n8 = 1;
            n4 = 1;
            if (bl) {
                if (this.f0 == null) {
                    this.f0 = new Rect();
                    this.g0 = new Rect();
                }
                Rect rect2 = this.f0;
                Rect rect3 = this.g0;
                if (z12 == null) {
                    rect2.set(rect);
                } else {
                    rect2.set(z12.j(), z12.l(), z12.k(), z12.i());
                }
                t0.a((View)this.D, rect2, rect3);
                int n9 = rect2.top;
                n7 = rect2.left;
                int n10 = rect2.right;
                z12 = x0.D((View)this.D);
                n8 = z12 == null ? 0 : z12.j();
                n3 = z12 == null ? 0 : z12.k();
                if (((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin == n9 && ((ViewGroup.MarginLayoutParams)actionBarContextView).leftMargin == n7 && ((ViewGroup.MarginLayoutParams)actionBarContextView).rightMargin == n10) {
                    n7 = 0;
                } else {
                    ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin = n9;
                    ((ViewGroup.MarginLayoutParams)actionBarContextView).leftMargin = n7;
                    ((ViewGroup.MarginLayoutParams)actionBarContextView).rightMargin = n10;
                    n7 = 1;
                }
                if (n9 > 0 && this.F == null) {
                    z12 = new View(this.m);
                    this.F = z12;
                    z12.setVisibility(8);
                    z12 = new FrameLayout.LayoutParams(-1, ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin, 51);
                    ((FrameLayout.LayoutParams)z12).leftMargin = n8;
                    ((FrameLayout.LayoutParams)z12).rightMargin = n3;
                    this.D.addView(this.F, -1, (ViewGroup.LayoutParams)z12);
                } else {
                    z12 = this.F;
                    if (z12 != null) {
                        z12 = (ViewGroup.MarginLayoutParams)z12.getLayoutParams();
                        n10 = ((ViewGroup.MarginLayoutParams)z12).height;
                        n9 = ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin;
                        if (n10 != n9 || ((ViewGroup.MarginLayoutParams)z12).leftMargin != n8 || ((ViewGroup.MarginLayoutParams)z12).rightMargin != n3) {
                            ((ViewGroup.MarginLayoutParams)z12).height = n9;
                            ((ViewGroup.MarginLayoutParams)z12).leftMargin = n8;
                            ((ViewGroup.MarginLayoutParams)z12).rightMargin = n3;
                            this.F.setLayoutParams((ViewGroup.LayoutParams)z12);
                        }
                    }
                }
                z12 = this.F;
                n3 = z12 != null ? n4 : 0;
                if (n3 != 0 && z12.getVisibility() != 0) {
                    this.d1(this.F);
                }
                n8 = n6;
                if (!this.K) {
                    n8 = n6;
                    if (n3 != 0) {
                        n8 = 0;
                    }
                }
                n6 = n8;
                n8 = n7;
                n7 = n3;
            } else if (((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin != 0) {
                ((ViewGroup.MarginLayoutParams)actionBarContextView).topMargin = 0;
                n7 = 0;
            } else {
                n7 = 0;
                n8 = 0;
            }
            n3 = n6;
            n4 = n7;
            if (n8 != 0) {
                this.x.setLayoutParams((ViewGroup.LayoutParams)actionBarContextView);
                n3 = n6;
                n4 = n7;
            }
        } else {
            n4 = 0;
            n3 = n6;
        }
        if ((z12 = this.F) != null) {
            n6 = n4 != 0 ? n5 : 8;
            z12.setVisibility(n6);
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void d0() {
        Object object = this.t;
        if (object != null) {
            object.j();
        }
        if (this.y != null) {
            this.n.getDecorView().removeCallbacks(this.z);
            if (this.y.isShowing()) {
                try {
                    this.y.dismiss();
                }
                catch (IllegalArgumentException illegalArgumentException) {}
            }
            this.y = null;
        }
        this.g0();
        object = this.q0(0, false);
        if (object != null && (object = ((PanelFeatureState)object).j) != null) {
            ((androidx.appcompat.view.menu.e)object).close();
        }
    }

    public final void d1(View view) {
        int n3 = (x0.I(view) & 0x2000) != 0 ? e0.a.b(this.m, c.c.abc_decor_view_status_guard_light) : e0.a.b(this.m, c.c.abc_decor_view_status_guard);
        view.setBackgroundColor(n3);
    }

    @Override
    public void e(View view, ViewGroup.LayoutParams layoutParams) {
        this.h0();
        ((ViewGroup)this.D.findViewById(0x1020002)).addView(view, layoutParams);
        this.o.c(this.n.getCallback());
    }

    public boolean e0(KeyEvent keyEvent) {
        Object object = this.l;
        if ((object instanceof t.a || object instanceof androidx.appcompat.app.m) && (object = this.n.getDecorView()) != null && o0.t.d((View)object, keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 82 && this.o.b(this.n.getCallback(), keyEvent)) {
            return true;
        }
        int n3 = keyEvent.getKeyCode();
        if (keyEvent.getAction() == 0) {
            return this.B0(n3, keyEvent);
        }
        return this.E0(n3, keyEvent);
    }

    public void f0(int n3) {
        PanelFeatureState panelFeatureState = this.q0(n3, true);
        if (panelFeatureState.j != null) {
            Bundle bundle = new Bundle();
            panelFeatureState.j.U(bundle);
            if (bundle.size() > 0) {
                panelFeatureState.s = bundle;
            }
            panelFeatureState.j.i0();
            panelFeatureState.j.clear();
        }
        panelFeatureState.r = true;
        panelFeatureState.q = true;
        if ((n3 == 108 || n3 == 0) && this.t != null && (panelFeatureState = this.q0(0, false)) != null) {
            panelFeatureState.m = false;
            this.M0(panelFeatureState, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Context g(Context context) {
        this.R = true;
        int n3 = this.z0(context, this.U());
        if (androidx.appcompat.app.d.u(context)) {
            androidx.appcompat.app.d.N(context);
        }
        k0.b b3 = this.T(context);
        if (context instanceof ContextThemeWrapper) {
            Configuration configuration = this.a0(context, n3, b3, null, false);
            try {
                ((ContextThemeWrapper)context).applyOverrideConfiguration(configuration);
                return context;
            }
            catch (IllegalStateException illegalStateException) {}
        }
        if (context instanceof h.d) {
            Configuration configuration = this.a0(context, n3, b3, null, false);
            try {
                ((h.d)context).a(configuration);
                return context;
            }
            catch (IllegalStateException illegalStateException) {}
        }
        if (!o0) {
            return super.g(context);
        }
        Object object = new Configuration();
        ((Configuration)object).uiMode = -1;
        ((Configuration)object).fontScale = 0.0f;
        Configuration configuration = context.createConfigurationContext((Configuration)object).getResources().getConfiguration();
        object = context.getResources().getConfiguration();
        configuration.uiMode = ((Configuration)object).uiMode;
        object = !configuration.equals((Configuration)object) ? AppCompatDelegateImpl.k0(configuration, (Configuration)object) : null;
        b3 = this.a0(context, n3, b3, (Configuration)object, true);
        object = new h.d(context, c.i.Theme_AppCompat_Empty);
        ((h.d)((Object)object)).a((Configuration)b3);
        try {
            context = context.getTheme();
            if (context == null) return super.g((Context)object);
        }
        catch (NullPointerException nullPointerException) {
            return super.g((Context)object);
        }
        h.f.a(((h.d)((Object)object)).getTheme());
        return super.g((Context)object);
    }

    public void g0() {
        h1 h12 = this.A;
        if (h12 != null) {
            h12.c();
        }
    }

    public final void h0() {
        if (!this.C) {
            this.D = this.b0();
            Object object = this.r0();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                androidx.appcompat.widget.t t3 = this.t;
                if (t3 != null) {
                    t3.setWindowTitle((CharSequence)object);
                } else if (this.K0() != null) {
                    this.K0().s((CharSequence)object);
                } else {
                    t3 = this.E;
                    if (t3 != null) {
                        t3.setText((CharSequence)object);
                    }
                }
            }
            this.R();
            this.I0(this.D);
            this.C = true;
            object = this.q0(0, false);
            if (!(this.T || object != null && ((PanelFeatureState)object).j != null)) {
                this.x0(108);
            }
        }
    }

    public final void i0() {
        Object object;
        if (this.n == null && (object = this.l) instanceof Activity) {
            this.S(((Activity)object).getWindow());
        }
        if (this.n != null) {
            return;
        }
        throw new IllegalStateException("We have not been given a Window");
    }

    @Override
    public View j(int n3) {
        this.h0();
        return this.n.findViewById(n3);
    }

    public PanelFeatureState j0(Menu menu) {
        PanelFeatureState[] panelFeatureStateArray = this.O;
        int n3 = panelFeatureStateArray != null ? panelFeatureStateArray.length : 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            PanelFeatureState panelFeatureState = panelFeatureStateArray[i3];
            if (panelFeatureState == null || panelFeatureState.j != menu) continue;
            return panelFeatureState;
        }
        return null;
    }

    @Override
    public Context l() {
        return this.m;
    }

    public final Context l0() {
        ActionBar actionBar = this.r();
        actionBar = actionBar != null ? actionBar.j() : null;
        ActionBar actionBar2 = actionBar;
        if (actionBar == null) {
            actionBar2 = this.m;
        }
        return actionBar2;
    }

    public final int m0(Context context) {
        block6: {
            if (!this.Y && this.l instanceof Activity) {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    return 0;
                }
                int n3 = Build.VERSION.SDK_INT >= 29 ? 0x100C0000 : 786432;
                ComponentName componentName = new ComponentName(context, this.l.getClass());
                context = packageManager.getActivityInfo(componentName, n3);
                if (context == null) break block6;
                try {
                    this.X = context.configChanges;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    this.X = 0;
                }
            }
        }
        this.Y = true;
        return this.X;
    }

    @Override
    public int n() {
        return this.V;
    }

    public final n n0(Context context) {
        if (this.a0 == null) {
            this.a0 = new m(this, context);
        }
        return this.a0;
    }

    public final n o0(Context context) {
        if (this.Z == null) {
            this.Z = new o(this, androidx.appcompat.app.r.a(context));
        }
        return this.Z;
    }

    public final View onCreateView(View view, String string, Context context, AttributeSet attributeSet) {
        return this.c0(view, string, context, attributeSet);
    }

    public View onCreateView(String string, Context context, AttributeSet attributeSet) {
        return this.onCreateView(null, string, context, attributeSet);
    }

    @Override
    public MenuInflater p() {
        if (this.r == null) {
            this.t0();
            ActionBar actionBar = this.q;
            actionBar = actionBar != null ? actionBar.j() : this.m;
            this.r = new h.g((Context)actionBar);
        }
        return this.r;
    }

    public k0.b p0(Configuration configuration) {
        return androidx.appcompat.app.AppCompatDelegateImpl$i.b(configuration);
    }

    public PanelFeatureState q0(int n3, boolean bl) {
        PanelFeatureState[] panelFeatureStateArray;
        Object object;
        block6: {
            block5: {
                object = this.O;
                if (object == null) break block5;
                panelFeatureStateArray = object;
                if (((PanelFeatureState[])object).length > n3) break block6;
            }
            panelFeatureStateArray = new PanelFeatureState[n3 + 1];
            if (object != null) {
                System.arraycopy(object, 0, panelFeatureStateArray, 0, ((PanelFeatureState[])object).length);
            }
            this.O = panelFeatureStateArray;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArray[n3];
        object = panelFeatureState;
        if (panelFeatureState == null) {
            panelFeatureStateArray[n3] = object = new PanelFeatureState(n3);
        }
        return object;
    }

    @Override
    public ActionBar r() {
        this.t0();
        return this.q;
    }

    public final CharSequence r0() {
        Object object = this.l;
        if (object instanceof Activity) {
            return ((Activity)object).getTitle();
        }
        return this.s;
    }

    @Override
    public void s() {
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this.m);
        if (layoutInflater.getFactory() == null) {
            o0.u.a(layoutInflater, this);
            return;
        }
        layoutInflater.getFactory2();
    }

    public final Window.Callback s0() {
        return this.n.getCallback();
    }

    @Override
    public void t() {
        if (this.K0() != null && !this.r().k()) {
            this.x0(0);
        }
    }

    public final void t0() {
        this.h0();
        if (this.I && this.q == null) {
            Object object = this.l;
            if (object instanceof Activity) {
                this.q = new t((Activity)this.l, this.J);
            } else if (object instanceof Dialog) {
                this.q = new t((Dialog)this.l);
            }
            object = this.q;
            if (object != null) {
                ((ActionBar)object).q(this.e0);
            }
        }
    }

    public final boolean u0(PanelFeatureState panelFeatureState) {
        View view = panelFeatureState.i;
        if (view != null) {
            panelFeatureState.h = view;
            return true;
        }
        if (panelFeatureState.j == null) {
            return false;
        }
        if (this.v == null) {
            this.v = new q(this);
        }
        panelFeatureState.h = view = (View)panelFeatureState.a(this.v);
        return view != null;
    }

    @Override
    public void v(Configuration configuration) {
        ActionBar actionBar;
        if (this.I && this.C && (actionBar = this.r()) != null) {
            actionBar.l(configuration);
        }
        androidx.appcompat.widget.g.b().g(this.m);
        this.U = new Configuration(this.m.getResources().getConfiguration());
        this.P(false, false);
    }

    public final boolean v0(PanelFeatureState panelFeatureState) {
        panelFeatureState.d(this.l0());
        panelFeatureState.g = new p(this, panelFeatureState.l);
        panelFeatureState.c = 81;
        return true;
    }

    @Override
    public void w(Bundle object) {
        this.R = true;
        this.O(false);
        this.i0();
        object = this.l;
        if (object instanceof Activity) {
            try {
                object = c0.h.c((Activity)object);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                object = null;
            }
            if (object != null) {
                object = this.K0();
                if (object == null) {
                    this.e0 = true;
                } else {
                    ((ActionBar)object).q(true);
                }
            }
            androidx.appcompat.app.d.d(this);
        }
        this.U = new Configuration(this.m.getResources().getConfiguration());
        this.S = true;
    }

    public final boolean w0(PanelFeatureState panelFeatureState) {
        Object object;
        block10: {
            Context context;
            block9: {
                context = this.m;
                int n3 = panelFeatureState.a;
                if (n3 == 0) break block9;
                object = context;
                if (n3 != 108) break block10;
            }
            object = context;
            if (this.t != null) {
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = context.getTheme();
                theme.resolveAttribute(c.a.actionBarTheme, typedValue, true);
                if (typedValue.resourceId != 0) {
                    object = context.getResources().newTheme();
                    object.setTo(theme);
                    object.applyStyle(typedValue.resourceId, true);
                    object.resolveAttribute(c.a.actionBarWidgetTheme, typedValue, true);
                } else {
                    theme.resolveAttribute(c.a.actionBarWidgetTheme, typedValue, true);
                    object = null;
                }
                Object object2 = object;
                if (typedValue.resourceId != 0) {
                    object2 = object;
                    if (object == null) {
                        object2 = context.getResources().newTheme();
                        object2.setTo(theme);
                    }
                    object2.applyStyle(typedValue.resourceId, true);
                }
                object = context;
                if (object2 != null) {
                    object = new h.d(context, 0);
                    object.getTheme().setTo((Resources.Theme)object2);
                }
            }
        }
        object = new androidx.appcompat.view.menu.e((Context)object);
        object.W(this);
        panelFeatureState.c((androidx.appcompat.view.menu.e)object);
        return true;
    }

    @Override
    public void x() {
        Object object;
        if (this.l instanceof Activity) {
            androidx.appcompat.app.d.D(this);
        }
        if (this.b0) {
            this.n.getDecorView().removeCallbacks(this.d0);
        }
        this.T = true;
        if (this.V != -100 && (object = this.l) instanceof Activity && ((Activity)object).isChangingConfigurations()) {
            l0.put(this.l.getClass().getName(), this.V);
        } else {
            l0.remove(this.l.getClass().getName());
        }
        object = this.q;
        if (object != null) {
            ((ActionBar)object).m();
        }
        this.X();
    }

    public final void x0(int n3) {
        this.c0 = 1 << n3 | this.c0;
        if (!this.b0) {
            x0.Z(this.n.getDecorView(), this.d0);
            this.b0 = true;
        }
    }

    @Override
    public void y(Bundle bundle) {
        this.h0();
    }

    public boolean y0() {
        return this.B;
    }

    @Override
    public void z() {
        ActionBar actionBar = this.r();
        if (actionBar != null) {
            actionBar.r(true);
        }
    }

    public int z0(Context context, int n3) {
        if (n3 != -100) {
            if (n3 != -1) {
                if (n3 != 0) {
                    if (n3 != 1 && n3 != 2) {
                        if (n3 == 3) {
                            return this.n0(context).c();
                        }
                        throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
                    }
                } else {
                    if (((UiModeManager)context.getApplicationContext().getSystemService("uimode")).getNightMode() == 0) {
                        return -1;
                    }
                    return this.o0(context).c();
                }
            }
            return n3;
        }
        return -1;
    }

    public static final class PanelFeatureState {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public ViewGroup g;
        public View h;
        public View i;
        public androidx.appcompat.view.menu.e j;
        public androidx.appcompat.view.menu.c k;
        public Context l;
        public boolean m;
        public boolean n;
        public boolean o;
        public boolean p;
        public boolean q;
        public boolean r;
        public Bundle s;

        public PanelFeatureState(int n3) {
            this.a = n3;
            this.q = false;
        }

        public androidx.appcompat.view.menu.j a(i.a a4) {
            if (this.j == null) {
                return null;
            }
            if (this.k == null) {
                androidx.appcompat.view.menu.c c3;
                this.k = c3 = new androidx.appcompat.view.menu.c(this.l, c.g.abc_list_menu_item_layout);
                c3.m(a4);
                this.j.b(this.k);
            }
            return this.k.e(this.g);
        }

        public boolean b() {
            if (this.h == null) {
                return false;
            }
            if (this.i != null) {
                return true;
            }
            return this.k.c().getCount() > 0;
        }

        public void c(androidx.appcompat.view.menu.e e3) {
            Object object = this.j;
            if (e3 != object) {
                if (object != null) {
                    ((androidx.appcompat.view.menu.e)object).R(this.k);
                }
                this.j = e3;
                if (e3 != null && (object = this.k) != null) {
                    e3.b((androidx.appcompat.view.menu.i)object);
                }
            }
        }

        public void d(Context object) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = object.getResources().newTheme();
            theme.setTo(object.getTheme());
            theme.resolveAttribute(c.a.actionBarPopupTheme, typedValue, true);
            int n3 = typedValue.resourceId;
            if (n3 != 0) {
                theme.applyStyle(n3, true);
            }
            theme.resolveAttribute(c.a.panelMenuListTheme, typedValue, true);
            n3 = typedValue.resourceId;
            if (n3 != 0) {
                theme.applyStyle(n3, true);
            } else {
                theme.applyStyle(c.i.Theme_AppCompat_CompactMenu, true);
            }
            object = new h.d((Context)object, 0);
            object.getTheme().setTo(theme);
            this.l = object;
            object = object.obtainStyledAttributes(c.j.AppCompatTheme);
            this.b = object.getResourceId(c.j.AppCompatTheme_panelBackground, 0);
            this.f = object.getResourceId(c.j.AppCompatTheme_android_windowAnimationStyle, 0);
            object.recycle();
        }

        public static class SavedState
        implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

                public SavedState a(Parcel parcel) {
                    return SavedState.o(parcel, null);
                }

                public SavedState b(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.o(parcel, classLoader);
                }

                public SavedState[] c(int n3) {
                    return new SavedState[n3];
                }
            };
            public int c;
            public boolean d;
            public Bundle e;

            public static SavedState o(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.c = parcel.readInt();
                int n3 = parcel.readInt();
                boolean bl = true;
                if (n3 != 1) {
                    bl = false;
                }
                savedState.d = bl;
                if (bl) {
                    savedState.e = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel parcel, int n3) {
                parcel.writeInt(this.c);
                parcel.writeInt(this.d ? 1 : 0);
                if (this.d) {
                    parcel.writeBundle(this.e);
                }
            }
        }
    }

    public final class f
    implements i.a {
        public final AppCompatDelegateImpl c;

        public f(AppCompatDelegateImpl appCompatDelegateImpl) {
            this.c = appCompatDelegateImpl;
        }

        @Override
        public void a(androidx.appcompat.view.menu.e e3, boolean bl) {
            this.c.W(e3);
        }

        @Override
        public boolean b(androidx.appcompat.view.menu.e e3) {
            Window.Callback callback = this.c.s0();
            if (callback != null) {
                callback.onMenuOpened(108, (Menu)e3);
            }
            return true;
        }
    }

    public class g
    implements b.a {
        public b.a a;
        public final AppCompatDelegateImpl b;

        public g(AppCompatDelegateImpl appCompatDelegateImpl, b.a a4) {
            this.b = appCompatDelegateImpl;
            this.a = a4;
        }

        @Override
        public boolean a(h.b b3, MenuItem menuItem) {
            return this.a.a(b3, menuItem);
        }

        @Override
        public boolean b(h.b b3, Menu menu) {
            return this.a.b(b3, menu);
        }

        @Override
        public boolean c(h.b b3, Menu menu) {
            x0.e0((View)this.b.D);
            return this.a.c(b3, menu);
        }

        @Override
        public void d(h.b object) {
            this.a.d((h.b)object);
            object = this.b;
            if (((AppCompatDelegateImpl)object).y != null) {
                ((AppCompatDelegateImpl)object).n.getDecorView().removeCallbacks(this.b.z);
            }
            object = this.b;
            if (((AppCompatDelegateImpl)object).x != null) {
                ((AppCompatDelegateImpl)object).g0();
                object = this.b;
                ((AppCompatDelegateImpl)object).A = x0.e((View)((AppCompatDelegateImpl)object).x).b(0.0f);
                this.b.A.g(new j1(this){
                    public final g a;
                    {
                        this.a = g3;
                    }

                    @Override
                    public void b(View object) {
                        this.a.b.x.setVisibility(8);
                        AppCompatDelegateImpl appCompatDelegateImpl = this.a.b;
                        object = appCompatDelegateImpl.y;
                        if (object != null) {
                            object.dismiss();
                        } else if (appCompatDelegateImpl.x.getParent() instanceof View) {
                            x0.e0((View)this.a.b.x.getParent());
                        }
                        this.a.b.x.k();
                        this.a.b.A.g(null);
                        object = this.a.b;
                        object.A = null;
                        x0.e0((View)object.D);
                    }
                });
            }
            AppCompatDelegateImpl appCompatDelegateImpl = this.b;
            object = appCompatDelegateImpl.p;
            if (object != null) {
                object.v(appCompatDelegateImpl.w);
            }
            object = this.b;
            ((AppCompatDelegateImpl)object).w = null;
            x0.e0((View)((AppCompatDelegateImpl)object).D);
            this.b.a1();
        }
    }

    public static abstract class h {
        public static boolean a(PowerManager powerManager) {
            return powerManager.isPowerSaveMode();
        }

        public static String b(Locale locale) {
            return locale.toLanguageTag();
        }
    }

    public static abstract class i {
        public static void a(Configuration configuration, Configuration configuration2, Configuration configuration3) {
            LocaleList localeList;
            if (!(configuration = configuration.getLocales()).equals((Object)(localeList = configuration2.getLocales()))) {
                configuration3.setLocales(localeList);
                configuration3.locale = configuration2.locale;
            }
        }

        public static k0.b b(Configuration configuration) {
            return k0.b.b(configuration.getLocales().toLanguageTags());
        }

        public static void c(k0.b b3) {
            LocaleList.setDefault((LocaleList)LocaleList.forLanguageTags((String)b3.g()));
        }

        public static void d(Configuration configuration, k0.b b3) {
            configuration.setLocales(LocaleList.forLanguageTags((String)b3.g()));
        }
    }

    public static abstract class j {
        public static void a(Configuration configuration, Configuration configuration2, Configuration configuration3) {
            int n3 = configuration.colorMode;
            int n4 = configuration2.colorMode;
            if ((n3 & 3) != (n4 & 3)) {
                configuration3.colorMode |= n4 & 3;
            }
            if (((n4 = configuration.colorMode) & 0xC) != ((n3 = configuration2.colorMode) & 0xC)) {
                configuration3.colorMode |= n3 & 0xC;
            }
        }
    }

    public static abstract class k {
        public static OnBackInvokedDispatcher a(Activity activity) {
            return androidx.appcompat.app.i.a(activity);
        }

        public static OnBackInvokedCallback b(Object object, AppCompatDelegateImpl object2) {
            Objects.requireNonNull(object2);
            object2 = new androidx.appcompat.app.k((AppCompatDelegateImpl)object2);
            androidx.appcompat.app.j.a(androidx.appcompat.app.g.a(object), 1000000, (OnBackInvokedCallback)object2);
            return object2;
        }

        public static void c(Object object, Object object2) {
            object2 = androidx.appcompat.app.f.a(object2);
            androidx.appcompat.app.h.a(androidx.appcompat.app.g.a(object), (OnBackInvokedCallback)object2);
        }
    }

    public class l
    extends h.i {
        public boolean d;
        public boolean e;
        public boolean f;
        public final AppCompatDelegateImpl g;

        public l(AppCompatDelegateImpl appCompatDelegateImpl, Window.Callback callback) {
            this.g = appCompatDelegateImpl;
            super(callback);
        }

        public boolean b(Window.Callback callback, KeyEvent keyEvent) {
            try {
                this.e = true;
                boolean bl = callback.dispatchKeyEvent(keyEvent);
                return bl;
            }
            finally {
                this.e = false;
            }
        }

        public void c(Window.Callback callback) {
            try {
                this.d = true;
                callback.onContentChanged();
                return;
            }
            finally {
                this.d = false;
            }
        }

        public void d(Window.Callback callback, int n3, Menu menu) {
            try {
                this.f = true;
                callback.onPanelClosed(n3, menu);
                return;
            }
            finally {
                this.f = false;
            }
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (this.e) {
                return this.a().dispatchKeyEvent(keyEvent);
            }
            return this.g.e0(keyEvent) || super.dispatchKeyEvent(keyEvent);
            {
            }
        }

        @Override
        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || this.g.D0(keyEvent.getKeyCode(), keyEvent);
            {
            }
        }

        public final ActionMode e(ActionMode.Callback object) {
            h.b b3 = this.g.U0((b.a)(object = new f.a(this.g.m, (ActionMode.Callback)object)));
            if (b3 != null) {
                return ((f.a)object).e(b3);
            }
            return null;
        }

        public void onContentChanged() {
            if (this.d) {
                this.a().onContentChanged();
            }
        }

        @Override
        public boolean onCreatePanelMenu(int n3, Menu menu) {
            if (n3 == 0 && !(menu instanceof androidx.appcompat.view.menu.e)) {
                return false;
            }
            return super.onCreatePanelMenu(n3, menu);
        }

        @Override
        public View onCreatePanelView(int n3) {
            return super.onCreatePanelView(n3);
        }

        @Override
        public boolean onMenuOpened(int n3, Menu menu) {
            super.onMenuOpened(n3, menu);
            this.g.G0(n3);
            return true;
        }

        @Override
        public void onPanelClosed(int n3, Menu menu) {
            if (this.f) {
                this.a().onPanelClosed(n3, menu);
                return;
            }
            super.onPanelClosed(n3, menu);
            this.g.H0(n3);
        }

        @Override
        public boolean onPreparePanel(int n3, View view, Menu menu) {
            androidx.appcompat.view.menu.e e3 = menu instanceof androidx.appcompat.view.menu.e ? (androidx.appcompat.view.menu.e)menu : null;
            if (n3 == 0 && e3 == null) {
                return false;
            }
            if (e3 != null) {
                e3.f0(true);
            }
            boolean bl = super.onPreparePanel(n3, view, menu);
            if (e3 != null) {
                e3.f0(false);
            }
            return bl;
        }

        @Override
        public void onProvideKeyboardShortcuts(List list, Menu menu, int n3) {
            Object object = this.g.q0(0, true);
            if (object != null && (object = ((PanelFeatureState)object).j) != null) {
                super.onProvideKeyboardShortcuts(list, (Menu)object, n3);
                return;
            }
            super.onProvideKeyboardShortcuts(list, menu, n3);
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        @Override
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int n3) {
            if (this.g.y0() && n3 == 0) {
                return this.e(callback);
            }
            return super.onWindowStartingActionMode(callback, n3);
        }
    }

    public class m
    extends n {
        public final PowerManager c;
        public final AppCompatDelegateImpl d;

        public m(AppCompatDelegateImpl appCompatDelegateImpl, Context context) {
            this.d = appCompatDelegateImpl;
            super(appCompatDelegateImpl);
            this.c = (PowerManager)context.getApplicationContext().getSystemService("power");
        }

        @Override
        public IntentFilter b() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }

        @Override
        public int c() {
            if (androidx.appcompat.app.AppCompatDelegateImpl$h.a(this.c)) {
                return 2;
            }
            return 1;
        }

        @Override
        public void d() {
            this.d.Q();
        }
    }

    public abstract class n {
        public BroadcastReceiver a;
        public final AppCompatDelegateImpl b;

        public n(AppCompatDelegateImpl appCompatDelegateImpl) {
            this.b = appCompatDelegateImpl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void a() {
            BroadcastReceiver broadcastReceiver = this.a;
            if (broadcastReceiver != null) {
                try {
                    this.b.m.unregisterReceiver(broadcastReceiver);
                }
                catch (IllegalArgumentException illegalArgumentException) {}
                this.a = null;
            }
        }

        public abstract IntentFilter b();

        public abstract int c();

        public abstract void d();

        public void e() {
            this.a();
            IntentFilter intentFilter = this.b();
            if (intentFilter != null && intentFilter.countActions() != 0) {
                if (this.a == null) {
                    this.a = new BroadcastReceiver(this){
                        public final n a;
                        {
                            this.a = n3;
                        }

                        public void onReceive(Context context, Intent intent) {
                            this.a.d();
                        }
                    };
                }
                this.b.m.registerReceiver(this.a, intentFilter);
            }
        }
    }

    public class o
    extends n {
        public final r c;
        public final AppCompatDelegateImpl d;

        public o(AppCompatDelegateImpl appCompatDelegateImpl, r r3) {
            this.d = appCompatDelegateImpl;
            super(appCompatDelegateImpl);
            this.c = r3;
        }

        @Override
        public IntentFilter b() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        @Override
        public int c() {
            if (this.c.d()) {
                return 2;
            }
            return 1;
        }

        @Override
        public void d() {
            this.d.Q();
        }
    }

    public class p
    extends ContentFrameLayout {
        public final AppCompatDelegateImpl k;

        public p(AppCompatDelegateImpl appCompatDelegateImpl, Context context) {
            this.k = appCompatDelegateImpl;
            super(context);
        }

        public final boolean a(int n3, int n4) {
            return n3 < -5 || n4 < -5 || n3 > this.getWidth() + 5 || n4 > this.getHeight() + 5;
            {
            }
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return this.k.e0(keyEvent) || super.dispatchKeyEvent(keyEvent);
            {
            }
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && this.a((int)motionEvent.getX(), (int)motionEvent.getY())) {
                this.k.Y(0);
                return true;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public void setBackgroundResource(int n3) {
            this.setBackgroundDrawable(a.b(this.getContext(), n3));
        }
    }

    public final class q
    implements i.a {
        public final AppCompatDelegateImpl c;

        public q(AppCompatDelegateImpl appCompatDelegateImpl) {
            this.c = appCompatDelegateImpl;
        }

        @Override
        public void a(androidx.appcompat.view.menu.e object, boolean bl) {
            androidx.appcompat.view.menu.e e3 = ((androidx.appcompat.view.menu.e)object).F();
            boolean bl2 = e3 != object;
            AppCompatDelegateImpl appCompatDelegateImpl = this.c;
            if (bl2) {
                object = e3;
            }
            if ((object = appCompatDelegateImpl.j0((Menu)object)) != null) {
                if (bl2) {
                    this.c.V(((PanelFeatureState)object).a, (PanelFeatureState)object, e3);
                    this.c.Z((PanelFeatureState)object, true);
                    return;
                }
                this.c.Z((PanelFeatureState)object, bl);
            }
        }

        @Override
        public boolean b(androidx.appcompat.view.menu.e e3) {
            if (e3 == e3.F()) {
                AppCompatDelegateImpl appCompatDelegateImpl = this.c;
                if (appCompatDelegateImpl.I && (appCompatDelegateImpl = appCompatDelegateImpl.s0()) != null && !this.c.T) {
                    appCompatDelegateImpl.onMenuOpened(108, e3);
                }
            }
            return true;
        }
    }
}

