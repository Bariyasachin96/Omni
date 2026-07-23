/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.Window
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.material.search;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.s;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.TouchObserverFrameLayout;
import com.google.android.material.internal.a0;
import com.google.android.material.internal.c0;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.c;
import com.google.android.material.search.d;
import com.google.android.material.search.e;
import com.google.android.material.search.f;
import com.google.android.material.search.g;
import com.google.android.material.search.h;
import com.google.android.material.search.i;
import com.google.android.material.search.j;
import com.google.android.material.search.k;
import com.google.android.material.search.l;
import com.google.android.material.search.m;
import com.google.android.material.search.n;
import com.google.android.material.search.o;
import com.google.android.material.search.p;
import com.google.android.material.search.z;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import k2.a;
import o0.x0;
import o0.z1;

public class SearchView
extends FrameLayout
implements CoordinatorLayout.b,
p2.b {
    public static final int G = z1.l.Widget_Material3_SearchView;
    public boolean A;
    public final int B;
    public boolean C;
    public boolean D;
    public b E;
    public Map F;
    public final View c;
    public final ClippableRoundedCornerLayout d;
    public final View e;
    public final View f;
    public final FrameLayout g;
    public final FrameLayout h;
    public final MaterialToolbar i;
    public final Toolbar j;
    public final TextView k;
    public final LinearLayout l;
    public final EditText m;
    public final ImageButton n;
    public final View o;
    public final TouchObserverFrameLayout p;
    public final boolean q;
    public final z r;
    public final p2.c s;
    public final boolean t;
    public final a u;
    public final Set v;
    public SearchBar w;
    public int x;
    public boolean y;
    public boolean z;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialSearchViewStyle);
    }

    public SearchView(Context context, AttributeSet object, int n3) {
        int n4 = G;
        super(y2.a.d(context, object, n3, n4), object, n3);
        this.s = new p2.c((View)this);
        this.v = new LinkedHashSet();
        this.x = 16;
        this.E = b.d;
        context = this.getContext();
        TypedArray typedArray = com.google.android.material.internal.z.i(context, object, z1.m.SearchView, n3, n4, new int[0]);
        this.B = typedArray.getColor(z1.m.SearchView_backgroundTint, 0);
        n4 = typedArray.getResourceId(z1.m.SearchView_headerLayout, -1);
        n3 = typedArray.getResourceId(z1.m.SearchView_android_textAppearance, -1);
        object = typedArray.getString(z1.m.SearchView_android_text);
        String string = typedArray.getString(z1.m.SearchView_android_hint);
        String string2 = typedArray.getString(z1.m.SearchView_searchPrefixText);
        boolean bl = typedArray.getBoolean(z1.m.SearchView_useDrawerArrowDrawable, false);
        this.y = typedArray.getBoolean(z1.m.SearchView_animateNavigationIcon, true);
        this.z = typedArray.getBoolean(z1.m.SearchView_animateMenuItems, true);
        boolean bl2 = typedArray.getBoolean(z1.m.SearchView_hideNavigationIcon, false);
        this.A = typedArray.getBoolean(z1.m.SearchView_autoShowKeyboard, true);
        this.t = typedArray.getBoolean(z1.m.SearchView_backHandlingEnabled, true);
        typedArray.recycle();
        LayoutInflater.from((Context)context).inflate(z1.i.mtrl_search_view, (ViewGroup)this);
        this.q = true;
        this.c = this.findViewById(z1.g.open_search_view_scrim);
        this.d = (ClippableRoundedCornerLayout)this.findViewById(z1.g.open_search_view_root);
        this.e = this.findViewById(z1.g.open_search_view_background);
        this.f = this.findViewById(z1.g.open_search_view_status_bar_spacer);
        this.g = (FrameLayout)this.findViewById(z1.g.open_search_view_header_container);
        this.h = (FrameLayout)this.findViewById(z1.g.open_search_view_toolbar_container);
        this.i = (MaterialToolbar)this.findViewById(z1.g.open_search_view_toolbar);
        this.j = (Toolbar)this.findViewById(z1.g.open_search_view_dummy_toolbar);
        this.k = (TextView)this.findViewById(z1.g.open_search_view_search_prefix);
        this.l = (LinearLayout)this.findViewById(z1.g.open_search_view_text_container);
        this.m = (EditText)this.findViewById(z1.g.open_search_view_edit_text);
        this.n = (ImageButton)this.findViewById(z1.g.open_search_view_clear_button);
        this.o = this.findViewById(z1.g.open_search_view_divider);
        this.p = (TouchObserverFrameLayout)this.findViewById(z1.g.open_search_view_content_container);
        this.r = new z(this);
        this.u = new a(context);
        this.I();
        this.C();
        this.setUpHeaderLayout(n4);
        this.setSearchPrefixText(string2);
        this.G(n3, (String)object, string);
        this.B(bl, bl2);
        this.D();
        this.E();
        this.H();
    }

    public static /* synthetic */ void e(SearchView searchView, View view) {
        searchView.L();
    }

    public static /* synthetic */ z1 f(ViewGroup.MarginLayoutParams marginLayoutParams, int n3, int n4, View object, z1 z12) {
        object = z12.f(z1.m.e() | z1.m.a());
        marginLayoutParams.leftMargin = n3 + object.a;
        marginLayoutParams.rightMargin = n4 + object.c;
        return z12;
    }

    public static /* synthetic */ boolean g(SearchView searchView, View view, MotionEvent motionEvent) {
        if (searchView.s()) {
            searchView.p();
        }
        return false;
    }

    private Window getActivityWindow() {
        Activity activity = com.google.android.material.internal.c.a(this.getContext());
        if (activity == null) {
            return null;
        }
        return activity.getWindow();
    }

    private float getOverlayElevation() {
        SearchBar searchBar = this.w;
        if (searchBar != null) {
            return searchBar.getCompatElevation();
        }
        return this.getResources().getDimension(z1.e.m3_searchview_elevation);
    }

    private int getStatusBarHeight() {
        int n3 = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (n3 > 0) {
            return this.getResources().getDimensionPixelSize(n3);
        }
        return 0;
    }

    public static /* synthetic */ void h(SearchView searchView) {
        searchView.m.clearFocus();
        c0.l((View)searchView.m, searchView.C);
    }

    public static /* synthetic */ void i(SearchView searchView) {
        if (searchView.m.requestFocus()) {
            searchView.m.sendAccessibilityEvent(8);
        }
        c0.q((View)searchView.m, searchView.C);
    }

    public static /* synthetic */ void j(SearchView searchView, View view) {
        searchView.q();
        searchView.z();
    }

    public static /* synthetic */ z1 k(SearchView searchView, View object, z1 z12, c0.e e3) {
        boolean bl = c0.m((View)searchView.i);
        int n3 = bl ? e3.c : e3.a;
        int n4 = bl ? e3.a : e3.c;
        object = z12.f(z1.m.e() | z1.m.a());
        int n5 = object.a;
        int n6 = object.c;
        searchView.i.setPadding(n3 + n5, e3.b, n4 + n6, e3.d);
        return z12;
    }

    public static /* synthetic */ z1 l(SearchView searchView, View view, z1 z12) {
        searchView.getClass();
        int n3 = z12.f((int)(z1.m.e() | z1.m.a())).b;
        searchView.setUpStatusBarSpacer(n3);
        if (!searchView.D) {
            boolean bl = n3 > 0;
            searchView.setStatusBarSpacerEnabledInternal(bl);
        }
        return z12;
    }

    public static /* synthetic */ boolean m(View view, MotionEvent motionEvent) {
        return true;
    }

    public static /* synthetic */ void n(SearchView searchView, View view) {
        searchView.r();
    }

    private void setStatusBarSpacerEnabledInternal(boolean bl) {
        View view = this.f;
        int n3 = bl ? 0 : 8;
        view.setVisibility(n3);
    }

    private void setUpBackgroundViewElevationOverlay(float f3) {
        a a4 = this.u;
        if (a4 != null && this.e != null) {
            int n3 = a4.c(this.B, f3);
            this.e.setBackgroundColor(n3);
        }
    }

    private void setUpHeaderLayout(int n3) {
        if (n3 != -1) {
            this.o(LayoutInflater.from((Context)this.getContext()).inflate(n3, (ViewGroup)this.g, false));
        }
    }

    private void setUpStatusBarSpacer(int n3) {
        if (this.f.getLayoutParams().height != n3) {
            this.f.getLayoutParams().height = n3;
            this.f.requestLayout();
        }
    }

    public final void A(b b3, boolean bl) {
        Iterator iterator;
        block6: {
            block5: {
                if (((Object)((Object)this.E)).equals((Object)b3)) break block5;
                if (bl) {
                    this.O(b3);
                }
                this.E = b3;
                iterator = new LinkedHashSet(this.v).iterator();
                if (iterator.hasNext()) break block6;
                this.N(b3);
                iterator = this.w;
                if (iterator != null && b3 == b.d) {
                    iterator.sendAccessibilityEvent(8);
                }
            }
            return;
        }
        androidx.appcompat.app.s.a(iterator.next());
        throw null;
    }

    public final void B(boolean bl, boolean bl2) {
        if (bl2) {
            this.i.setNavigationIcon(null);
            return;
        }
        this.i.setNavigationOnClickListener(new p(this));
        if (bl) {
            e.b b3 = new e.b(this.getContext());
            b3.c(h2.a.d((View)this, z1.c.colorOnSurface));
            this.i.setNavigationIcon(b3);
        }
    }

    public final void C() {
        this.setUpBackgroundViewElevationOverlay(this.getOverlayElevation());
    }

    public final void D() {
        this.n.setOnClickListener((View.OnClickListener)new e(this));
        this.m.addTextChangedListener(new TextWatcher(this){
            public final SearchView c;
            {
                this.c = searchView;
            }

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
            }

            public void onTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
                ImageButton imageButton = this.c.n;
                n3 = charSequence.length() > 0 ? 0 : 8;
                imageButton.setVisibility(n3);
            }
        });
    }

    public final void E() {
        this.p.setOnTouchListener(new o(this));
    }

    public final void F() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.o.getLayoutParams();
        int n3 = marginLayoutParams.leftMargin;
        int n4 = marginLayoutParams.rightMargin;
        x0.r0(this.o, new i(marginLayoutParams, n3, n4));
    }

    public final void G(int n3, String string, String string2) {
        if (n3 != -1) {
            androidx.core.widget.j.m((TextView)this.m, n3);
        }
        this.m.setText((CharSequence)string);
        this.m.setHint((CharSequence)string2);
    }

    public final void H() {
        this.K();
        this.F();
        this.J();
    }

    public final void I() {
        this.d.setOnTouchListener(new n());
    }

    public final void J() {
        this.setUpStatusBarSpacer(this.getStatusBarHeight());
        x0.r0(this.f, new k(this));
    }

    public final void K() {
        c0.f((View)this.i, new j(this));
    }

    public void L() {
        if (!((Object)((Object)this.E)).equals((Object)b.f) && !((Object)((Object)this.E)).equals((Object)b.e)) {
            this.r.i0();
        }
    }

    public final void M(ViewGroup viewGroup, boolean bl) {
        for (int i3 = 0; i3 < viewGroup.getChildCount(); ++i3) {
            View view = viewGroup.getChildAt(i3);
            if (view == this) continue;
            if (view.findViewById(this.d.getId()) != null) {
                this.M((ViewGroup)view, bl);
                continue;
            }
            if (!bl) {
                Map map = this.F;
                if (map == null || !map.containsKey(view)) continue;
                view.setImportantForAccessibility(((Integer)this.F.get(view)).intValue());
                continue;
            }
            this.F.put(view, view.getImportantForAccessibility());
            view.setImportantForAccessibility(4);
        }
    }

    public final void N(b b3) {
        if (this.w != null && this.t) {
            if (((Object)((Object)b3)).equals((Object)b.f)) {
                this.s.c();
                return;
            }
            if (((Object)((Object)b3)).equals((Object)b.d)) {
                this.s.f();
            }
        }
    }

    public final void O(b b3) {
        if (b3 == b.f) {
            this.setModalForAccessibility(true);
            return;
        }
        if (b3 == b.d) {
            this.setModalForAccessibility(false);
        }
    }

    public final void P() {
        MaterialToolbar materialToolbar = this.i;
        if (materialToolbar == null || this.w(materialToolbar)) {
            return;
        }
        int n3 = this.getDefaultNavigationIconResource();
        if (this.w == null) {
            this.i.setNavigationIcon(n3);
            return;
        }
        materialToolbar = h0.a.r(d.a.b(this.getContext(), n3).mutate());
        if (this.i.getNavigationIconTint() != null) {
            materialToolbar.setTint(this.i.getNavigationIconTint());
        }
        h0.a.m((Drawable)materialToolbar, this.getLayoutDirection());
        this.i.setNavigationIcon(new com.google.android.material.internal.f(this.w.getNavigationIcon(), (Drawable)materialToolbar));
        this.Q();
    }

    public final void Q() {
        ImageButton imageButton = a0.d(this.i);
        if (imageButton != null) {
            boolean bl = this.d.getVisibility() == 0;
            if ((imageButton = h0.a.q(imageButton.getDrawable())) instanceof e.b) {
                ((e.b)imageButton).e((float)bl);
            }
            if (imageButton instanceof com.google.android.material.internal.f) {
                ((com.google.android.material.internal.f)imageButton).a((float)bl);
            }
        }
    }

    public void R() {
        Window window = this.getActivityWindow();
        if (window != null) {
            this.x = window.getAttributes().softInputMode;
        }
    }

    @Override
    public void a() {
        if (this.u()) {
            return;
        }
        androidx.activity.b b3 = this.r.b0();
        if (Build.VERSION.SDK_INT >= 34 && this.w != null && b3 != null) {
            this.r.x();
            return;
        }
        this.r();
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        if (this.q) {
            this.p.addView(view, n3, layoutParams);
            return;
        }
        super.addView(view, n3, layoutParams);
    }

    @Override
    public void b(androidx.activity.b b3) {
        SearchBar searchBar;
        if (!this.u() && (searchBar = this.w) != null) {
            if (searchBar != null) {
                searchBar.setPlaceholderText(this.m.getText().toString());
            }
            this.r.j0(b3);
        }
    }

    @Override
    public void c(androidx.activity.b b3) {
        if (!this.u() && this.w != null && Build.VERSION.SDK_INT >= 34) {
            this.r.o0(b3);
        }
    }

    @Override
    public void d() {
        if (!this.u() && this.w != null && Build.VERSION.SDK_INT >= 34) {
            this.r.w();
        }
    }

    public p2.i getBackHelper() {
        return this.r.z();
    }

    @Override
    public CoordinatorLayout.Behavior<SearchView> getBehavior() {
        return new Behavior();
    }

    public b getCurrentTransitionState() {
        return this.E;
    }

    public int getDefaultNavigationIconResource() {
        return z1.f.ic_arrow_back_black_24;
    }

    public EditText getEditText() {
        return this.m;
    }

    public CharSequence getHint() {
        return this.m.getHint();
    }

    public TextView getSearchPrefix() {
        return this.k;
    }

    public CharSequence getSearchPrefixText() {
        return this.k.getText();
    }

    public int getSoftInputMode() {
        return this.x;
    }

    public Editable getText() {
        return this.m.getText();
    }

    public Toolbar getToolbar() {
        return this.i;
    }

    public void o(View view) {
        this.g.addView(view);
        this.g.setVisibility(0);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v2.j.e((View)this);
        b b3 = this.getCurrentTransitionState();
        this.O(b3);
        this.N(b3);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.setModalForAccessibility(false);
        this.s.f();
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.R();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.setText(parcelable.e);
        boolean bl = parcelable.f == 0;
        this.setVisible(bl);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Object object = this.getText();
        object = object == null ? null : object.toString();
        savedState.e = object;
        savedState.f = this.d.getVisibility();
        return savedState;
    }

    public void p() {
        this.m.post((Runnable)new g(this));
    }

    public void q() {
        this.m.setText((CharSequence)"");
    }

    public void r() {
        if (!((Object)((Object)this.E)).equals((Object)b.d) && !((Object)((Object)this.E)).equals((Object)b.c)) {
            Object object = this.w;
            if (object != null && object.isAttachedToWindow()) {
                this.w.setPlaceholderText(this.m.getText().toString());
                SearchBar searchBar = this.w;
                object = this.r;
                Objects.requireNonNull(object);
                searchBar.post(new h((z)object));
                return;
            }
            this.r.Y();
        }
    }

    public boolean s() {
        return this.x == 48;
    }

    public void setAnimatedNavigationIcon(boolean bl) {
        this.y = bl;
    }

    public void setAutoShowKeyboard(boolean bl) {
        this.A = bl;
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        this.setUpBackgroundViewElevationOverlay(f3);
    }

    public void setHint(int n3) {
        this.m.setHint(n3);
    }

    public void setHint(CharSequence charSequence) {
        this.m.setHint(charSequence);
    }

    public void setMenuItemsAnimated(boolean bl) {
        this.z = bl;
    }

    public void setModalForAccessibility(boolean bl) {
        ViewGroup viewGroup = (ViewGroup)this.getRootView();
        if (bl) {
            this.F = new HashMap(viewGroup.getChildCount());
        }
        this.M(viewGroup, bl);
        if (!bl) {
            this.F = null;
        }
    }

    public void setOnMenuItemClickListener(Toolbar.g g3) {
        this.i.setOnMenuItemClickListener(g3);
    }

    public void setSearchPrefixText(CharSequence charSequence) {
        this.k.setText(charSequence);
        TextView textView = this.k;
        int n3 = TextUtils.isEmpty((CharSequence)charSequence) ? 8 : 0;
        textView.setVisibility(n3);
    }

    public void setStatusBarSpacerEnabled(boolean bl) {
        this.D = true;
        this.setStatusBarSpacerEnabledInternal(bl);
    }

    public void setText(int n3) {
        this.m.setText(n3);
    }

    public void setText(CharSequence charSequence) {
        this.m.setText(charSequence);
    }

    public void setToolbarTouchscreenBlocksFocus(boolean bl) {
        this.i.setTouchscreenBlocksFocus(bl);
    }

    public void setTransitionState(b b3) {
        this.A(b3, true);
    }

    public void setUseWindowInsetsController(boolean bl) {
        this.C = bl;
    }

    public void setVisible(boolean bl) {
        int n3 = this.d.getVisibility();
        boolean bl2 = true;
        boolean bl3 = n3 == 0;
        Object object = this.d;
        n3 = bl ? 0 : 8;
        object.setVisibility(n3);
        this.Q();
        object = bl ? b.f : b.d;
        bl = bl3 != bl ? bl2 : false;
        this.A((b)((Object)object), bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setupWithSearchBar(SearchBar searchBar) {
        this.w = searchBar;
        this.r.g0(searchBar);
        if (searchBar != null) {
            searchBar.setOnClickListener(new l(this));
            if (Build.VERSION.SDK_INT >= 34) {
                try {
                    m m3 = new m(this);
                    com.google.android.material.search.c.a(searchBar, m3);
                    com.google.android.material.search.d.a(this.m, true);
                }
                catch (LinkageError linkageError) {}
            }
        }
        this.P();
        this.C();
        this.N(this.getCurrentTransitionState());
    }

    public boolean t() {
        return this.y;
    }

    public final boolean u() {
        return ((Object)((Object)this.E)).equals((Object)b.d) || ((Object)((Object)this.E)).equals((Object)b.c);
        {
        }
    }

    public boolean v() {
        return this.z;
    }

    public final boolean w(Toolbar toolbar) {
        return h0.a.q(toolbar.getNavigationIcon()) instanceof e.b;
    }

    public boolean x() {
        return this.w != null;
    }

    public void y() {
        this.m.postDelayed((Runnable)new f(this), 100L);
    }

    public void z() {
        if (this.A) {
            this.y();
        }
    }

    public static class Behavior
    extends CoordinatorLayout.Behavior<SearchView> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public boolean I(CoordinatorLayout coordinatorLayout, SearchView searchView, View view) {
            if (!searchView.x() && view instanceof SearchBar) {
                searchView.setupWithSearchBar((SearchBar)view);
            }
            return false;
        }
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState b(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] c(int n3) {
                return new SavedState[n3];
            }
        };
        public String e;
        public int f;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = parcel.readString();
            this.f = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeString(this.e);
            parcel.writeInt(this.f);
        }
    }

    public static final class b
    extends Enum {
        public static final /* enum */ b c = new b("HIDING", 0);
        public static final /* enum */ b d = new b("HIDDEN", 1);
        public static final /* enum */ b e = new b("SHOWING", 2);
        public static final /* enum */ b f = new b("SHOWN", 3);
        public static final b[] g = b.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public b() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ b[] a() {
            return new b[]{c, d, e, f};
        }

        public static b valueOf(String string) {
            return Enum.valueOf(b.class, string);
        }

        public static b[] values() {
            return (b[])g.clone();
        }
    }
}

