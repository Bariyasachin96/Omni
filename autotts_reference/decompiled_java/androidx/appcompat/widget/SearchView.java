/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.app.SearchableInfo
 *  android.content.ActivityNotFoundException
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.style.ImageSpan
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.KeyEvent$DispatcherState
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.TouchDelegate
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.View$OnKeyListener
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.AutoCompleteTextView
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package androidx.appcompat.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.h0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.r0;
import androidx.appcompat.widget.t0;
import androidx.customview.view.AbsSavedState;
import c.a;
import c.d;
import c.f;
import c.g;
import c.h;
import c.j;
import h.c;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import o0.x0;

public class SearchView
extends LinearLayoutCompat
implements c {
    public static final o q0;
    public p A;
    public Rect B = new Rect();
    public Rect C = new Rect();
    public int[] D = new int[2];
    public int[] E = new int[2];
    public final ImageView F;
    public final Drawable G;
    public final int H;
    public final int I;
    public final Intent J;
    public final Intent K;
    public final CharSequence L;
    public m M;
    public View.OnFocusChangeListener N;
    public View.OnClickListener O;
    public boolean P;
    public boolean Q;
    public t0.a R;
    public boolean S;
    public CharSequence T;
    public boolean U;
    public boolean V;
    public int W;
    public boolean a0;
    public CharSequence b0;
    public CharSequence c0;
    public boolean d0;
    public int e0;
    public SearchableInfo f0;
    public Bundle g0;
    public final Runnable h0 = new Runnable(this){
        public final SearchView c;
        {
            this.c = searchView;
        }

        @Override
        public void run() {
            this.c.c0();
        }
    };
    public Runnable i0 = new Runnable(this){
        public final SearchView c;
        {
            this.c = searchView;
        }

        @Override
        public void run() {
            t0.a a4 = this.c.R;
            if (a4 instanceof h0) {
                a4.a(null);
            }
        }
    };
    public final WeakHashMap j0 = new WeakHashMap();
    public final View.OnClickListener k0;
    public View.OnKeyListener l0;
    public final TextView.OnEditorActionListener m0;
    public final AdapterView.OnItemClickListener n0;
    public final AdapterView.OnItemSelectedListener o0;
    public TextWatcher p0;
    public final SearchAutoComplete r;
    public final View s;
    public final View t;
    public final View u;
    public final ImageView v;
    public final ImageView w;
    public final ImageView x;
    public final ImageView y;
    public final View z;

    static {
        o o3 = Build.VERSION.SDK_INT < 29 ? new o() : null;
        q0 = o3;
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.searchViewStyle);
    }

    public SearchView(Context object, AttributeSet attributeSet, int n3) {
        super((Context)object, attributeSet, n3);
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        View view;
        AdapterView.OnItemSelectedListener onItemSelectedListener;
        AdapterView.OnItemClickListener onItemClickListener;
        TextView.OnEditorActionListener onEditorActionListener;
        View.OnClickListener onClickListener;
        this.k0 = onClickListener = new View.OnClickListener(this){
            public final SearchView c;
            {
                this.c = searchView;
            }

            public void onClick(View view) {
                SearchView searchView = this.c;
                if (view == searchView.v) {
                    searchView.T();
                    return;
                }
                if (view == searchView.x) {
                    searchView.P();
                    return;
                }
                if (view == searchView.w) {
                    searchView.U();
                    return;
                }
                if (view == searchView.y) {
                    searchView.Y();
                    return;
                }
                if (view == searchView.r) {
                    searchView.F();
                }
            }
        };
        this.l0 = new View.OnKeyListener(this){
            public final SearchView c;
            {
                this.c = searchView;
            }

            public boolean onKey(View object, int n3, KeyEvent keyEvent) {
                SearchView searchView = this.c;
                if (searchView.f0 == null) {
                    return false;
                }
                if (searchView.r.isPopupShowing() && this.c.r.getListSelection() != -1) {
                    return this.c.V((View)object, n3, keyEvent);
                }
                if (!this.c.r.c() && keyEvent.hasNoModifiers() && keyEvent.getAction() == 1 && n3 == 66) {
                    object.cancelLongPress();
                    object = this.c;
                    ((SearchView)object).N(0, null, ((SearchView)object).r.getText().toString());
                    return true;
                }
                return false;
            }
        };
        this.m0 = onEditorActionListener = new TextView.OnEditorActionListener(this){
            public final SearchView a;
            {
                this.a = searchView;
            }

            public boolean onEditorAction(TextView textView, int n3, KeyEvent keyEvent) {
                this.a.U();
                return true;
            }
        };
        this.n0 = onItemClickListener = new AdapterView.OnItemClickListener(this){
            public final SearchView c;
            {
                this.c = searchView;
            }

            public void onItemClick(AdapterView adapterView, View view, int n3, long l3) {
                this.c.Q(n3, 0, null);
            }
        };
        this.o0 = onItemSelectedListener = new AdapterView.OnItemSelectedListener(this){
            public final SearchView c;
            {
                this.c = searchView;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.R(n3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        };
        this.p0 = new TextWatcher(this){
            public final SearchView c;
            {
                this.c = searchView;
            }

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
            }

            public void onTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
                this.c.W(charSequence);
            }
        };
        Object object2 = c.j.SearchView;
        m0 m02 = androidx.appcompat.widget.m0.v((Context)object, attributeSet, object2, n3, 0);
        x0.f0((View)this, (Context)object, object2, attributeSet, m02.r(), n3, 0);
        LayoutInflater.from((Context)object).inflate(m02.n(c.j.SearchView_layout, c.g.abc_search_view), (ViewGroup)this, true);
        object = (SearchAutoComplete)this.findViewById(c.f.search_src_text);
        this.r = object;
        ((SearchAutoComplete)((Object)object)).setSearchView(this);
        this.s = this.findViewById(c.f.search_edit_frame);
        this.t = view = this.findViewById(c.f.search_plate);
        attributeSet = this.findViewById(c.f.submit_area);
        this.u = attributeSet;
        this.v = imageView4 = (ImageView)this.findViewById(c.f.search_button);
        object2 = (ImageView)this.findViewById(c.f.search_go_btn);
        this.w = (ImageView)object2;
        this.x = imageView3 = (ImageView)this.findViewById(c.f.search_close_btn);
        this.y = imageView2 = (ImageView)this.findViewById(c.f.search_voice_btn);
        this.F = imageView = (ImageView)this.findViewById(c.f.search_mag_icon);
        x0.k0(view, m02.g(c.j.SearchView_queryBackground));
        x0.k0((View)attributeSet, m02.g(c.j.SearchView_submitBackground));
        n3 = c.j.SearchView_searchIcon;
        imageView4.setImageDrawable(m02.g(n3));
        object2.setImageDrawable(m02.g(c.j.SearchView_goIcon));
        imageView3.setImageDrawable(m02.g(c.j.SearchView_closeIcon));
        imageView2.setImageDrawable(m02.g(c.j.SearchView_voiceIcon));
        imageView.setImageDrawable(m02.g(n3));
        this.G = m02.g(c.j.SearchView_searchHintIcon);
        r0.a((View)imageView4, this.getResources().getString(c.h.abc_searchview_description_search));
        this.H = m02.n(c.j.SearchView_suggestionRowLayout, c.g.abc_search_dropdown_item_icons_2line);
        this.I = m02.n(c.j.SearchView_commitIcon, 0);
        imageView4.setOnClickListener(onClickListener);
        imageView3.setOnClickListener(onClickListener);
        object2.setOnClickListener(onClickListener);
        imageView2.setOnClickListener(onClickListener);
        object.setOnClickListener(onClickListener);
        object.addTextChangedListener(this.p0);
        object.setOnEditorActionListener(onEditorActionListener);
        object.setOnItemClickListener(onItemClickListener);
        object.setOnItemSelectedListener(onItemSelectedListener);
        object.setOnKeyListener(this.l0);
        object.setOnFocusChangeListener(new View.OnFocusChangeListener(this){
            public final SearchView c;
            {
                this.c = searchView;
            }

            public void onFocusChange(View view, boolean bl) {
                SearchView searchView = this.c;
                view = searchView.N;
                if (view != null) {
                    view.onFocusChange((View)searchView, bl);
                }
            }
        });
        this.setIconifiedByDefault(m02.a(c.j.SearchView_iconifiedByDefault, true));
        n3 = m02.f(c.j.SearchView_android_maxWidth, -1);
        if (n3 != -1) {
            this.setMaxWidth(n3);
        }
        this.L = m02.p(c.j.SearchView_defaultQueryHint);
        this.T = m02.p(c.j.SearchView_queryHint);
        n3 = m02.k(c.j.SearchView_android_imeOptions, -1);
        if (n3 != -1) {
            this.setImeOptions(n3);
        }
        if ((n3 = m02.k(c.j.SearchView_android_inputType, -1)) != -1) {
            this.setInputType(n3);
        }
        this.setFocusable(m02.a(c.j.SearchView_android_focusable, true));
        m02.x();
        attributeSet = new Intent("android.speech.action.WEB_SEARCH");
        this.J = attributeSet;
        attributeSet.addFlags(0x10000000);
        attributeSet.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        attributeSet = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.K = attributeSet;
        attributeSet.addFlags(0x10000000);
        object = this.findViewById(object.getDropDownAnchor());
        this.z = object;
        if (object != null) {
            object.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this){
                public final SearchView a;
                {
                    this.a = searchView;
                }

                public void onLayoutChange(View view, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
                    this.a.z();
                }
            });
        }
        this.h0(this.P);
        this.d0();
    }

    public static boolean K(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private int getPreferredHeight() {
        return this.getContext().getResources().getDimensionPixelSize(c.d.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return this.getContext().getResources().getDimensionPixelSize(c.d.abc_search_view_preferred_width);
    }

    private void setQuery(CharSequence charSequence) {
        this.r.setText(charSequence);
        SearchAutoComplete searchAutoComplete = this.r;
        int n3 = TextUtils.isEmpty((CharSequence)charSequence) ? 0 : charSequence.length();
        searchAutoComplete.setSelection(n3);
    }

    public final Intent A(String string, Uri uri, String string2, String string3, int n3, String string4) {
        string = new Intent(string);
        string.addFlags(0x10000000);
        if (uri != null) {
            string.setData(uri);
        }
        string.putExtra("user_query", this.c0);
        if (string3 != null) {
            string.putExtra("query", string3);
        }
        if (string2 != null) {
            string.putExtra("intent_extra_data_key", string2);
        }
        if ((uri = this.g0) != null) {
            string.putExtra("app_data", (Bundle)uri);
        }
        if (n3 != 0) {
            string.putExtra("action_key", n3);
            string.putExtra("action_msg", string4);
        }
        string.setComponent(this.f0.getSearchActivity());
        return string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Intent B(Cursor object, int n3, String string) {
        RuntimeException runtimeException2;
        block9: {
            String string2;
            String string3;
            block8: {
                try {
                    string2 = string3 = androidx.appcompat.widget.h0.n((Cursor)object, "suggest_intent_action");
                    if (string3 != null) break block8;
                    string2 = this.f0.getSuggestIntentAction();
                }
                catch (RuntimeException runtimeException2) {
                    break block9;
                }
            }
            string3 = string2;
            if (string2 == null) {
                string3 = "android.intent.action.SEARCH";
            }
            CharSequence charSequence = androidx.appcompat.widget.h0.n((Cursor)object, "suggest_intent_data");
            string2 = charSequence;
            if (charSequence == null) {
                string2 = this.f0.getSuggestIntentData();
            }
            charSequence = string2;
            if (string2 != null) {
                String string4 = androidx.appcompat.widget.h0.n((Cursor)object, "suggest_intent_data_id");
                charSequence = string2;
                if (string4 != null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append("/");
                    ((StringBuilder)charSequence).append(Uri.encode((String)string4));
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            string2 = charSequence == null ? null : Uri.parse((String)charSequence);
            charSequence = androidx.appcompat.widget.h0.n((Cursor)object, "suggest_intent_query");
            return this.A(string3, (Uri)string2, androidx.appcompat.widget.h0.n((Cursor)object, "suggest_intent_extra_data"), (String)charSequence, n3, string);
        }
        try {
            n3 = object.getPosition();
        }
        catch (RuntimeException runtimeException3) {
            n3 = -1;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Search suggestions cursor at row ");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(" returned exception.");
        Log.w((String)"SearchView", (String)((StringBuilder)object).toString(), (Throwable)runtimeException2);
        return null;
    }

    public final Intent C(Intent object, SearchableInfo searchableInfo) {
        ComponentName componentName = searchableInfo.getSearchActivity();
        Object object2 = new Intent("android.intent.action.SEARCH");
        object2.setComponent(componentName);
        PendingIntent pendingIntent = PendingIntent.getActivity((Context)this.getContext(), (int)0, (Intent)object2, (int)0x42000000);
        Bundle bundle = new Bundle();
        object2 = this.g0;
        if (object2 != null) {
            bundle.putParcelable("app_data", (Parcelable)object2);
        }
        Intent intent = new Intent(object);
        Object object3 = this.getResources();
        object = searchableInfo.getVoiceLanguageModeId() != 0 ? object3.getString(searchableInfo.getVoiceLanguageModeId()) : "free_form";
        int n3 = searchableInfo.getVoicePromptTextId();
        Object var6_10 = null;
        object2 = n3 != 0 ? object3.getString(searchableInfo.getVoicePromptTextId()) : null;
        object3 = searchableInfo.getVoiceLanguageId() != 0 ? object3.getString(searchableInfo.getVoiceLanguageId()) : null;
        n3 = searchableInfo.getVoiceMaxResults() != 0 ? searchableInfo.getVoiceMaxResults() : 1;
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", (String)object);
        intent.putExtra("android.speech.extra.PROMPT", (String)object2);
        intent.putExtra("android.speech.extra.LANGUAGE", (String)object3);
        intent.putExtra("android.speech.extra.MAX_RESULTS", n3);
        object = componentName == null ? var6_10 : componentName.flattenToShortString();
        intent.putExtra("calling_package", (String)object);
        intent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", (Parcelable)pendingIntent);
        intent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent;
    }

    public final Intent D(Intent object, SearchableInfo searchableInfo) {
        Intent intent = new Intent(object);
        object = searchableInfo.getSearchActivity();
        object = object == null ? null : object.flattenToShortString();
        intent.putExtra("calling_package", (String)object);
        return intent;
    }

    public final void E() {
        this.r.dismissDropDown();
    }

    public void F() {
        if (Build.VERSION.SDK_INT >= 29) {
            androidx.appcompat.widget.SearchView$k.a(this.r);
            return;
        }
        o o3 = q0;
        o3.b(this.r);
        o3.a(this.r);
    }

    public final void G(View view, Rect rect) {
        view.getLocationInWindow(this.D);
        this.getLocationInWindow(this.E);
        int[] nArray = this.D;
        int n3 = nArray[1];
        int[] nArray2 = this.E;
        int n4 = nArray[0] - nArray2[0];
        rect.set(n4, n3 -= nArray2[1], view.getWidth() + n4, view.getHeight() + n3);
    }

    public final CharSequence H(CharSequence charSequence) {
        if (this.P && this.G != null) {
            int n3 = (int)((double)this.r.getTextSize() * 1.25);
            this.G.setBounds(0, 0, n3, n3);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder((CharSequence)"   ");
            spannableStringBuilder.setSpan((Object)new ImageSpan(this.G), 1, 2, 33);
            spannableStringBuilder.append(charSequence);
            return spannableStringBuilder;
        }
        return charSequence;
    }

    public final boolean I() {
        Object object = this.f0;
        return object != null && object.getVoiceSearchEnabled() && (object = this.f0.getVoiceSearchLaunchWebSearch() ? this.J : (this.f0.getVoiceSearchLaunchRecognizer() ? this.K : null)) != null && this.getContext().getPackageManager().resolveActivity((Intent)object, 65536) != null;
    }

    public boolean J() {
        return this.Q;
    }

    public final boolean L() {
        return (this.S || this.a0) && !this.J();
    }

    public final void M(Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            this.getContext().startActivity(intent);
            return;
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed launch activity: ");
            stringBuilder.append(intent);
            Log.e((String)"SearchView", (String)stringBuilder.toString(), (Throwable)runtimeException);
            return;
        }
    }

    public void N(int n3, String string, String string2) {
        string = this.A("android.intent.action.SEARCH", null, null, string2, n3, string);
        this.getContext().startActivity((Intent)string);
    }

    public final boolean O(int n3, int n4, String string) {
        Cursor cursor = this.R.b();
        if (cursor != null && cursor.moveToPosition(n3)) {
            this.M(this.B(cursor, n4, string));
            return true;
        }
        return false;
    }

    public void P() {
        if (TextUtils.isEmpty((CharSequence)this.r.getText())) {
            if (this.P) {
                this.clearFocus();
                this.h0(true);
            }
            return;
        }
        this.r.setText("");
        this.r.requestFocus();
        this.r.setImeVisibility(true);
    }

    public boolean Q(int n3, int n4, String string) {
        this.O(n3, 0, null);
        this.r.setImeVisibility(false);
        this.E();
        return true;
    }

    public boolean R(int n3) {
        this.a0(n3);
        return true;
    }

    public void S(CharSequence charSequence) {
        this.setQuery(charSequence);
    }

    public void T() {
        this.h0(false);
        this.r.requestFocus();
        this.r.setImeVisibility(true);
        View.OnClickListener onClickListener = this.O;
        if (onClickListener != null) {
            onClickListener.onClick((View)this);
        }
    }

    public void U() {
        m m3;
        Editable editable = this.r.getText();
        if (!(editable == null || TextUtils.getTrimmedLength((CharSequence)editable) <= 0 || (m3 = this.M) != null && m3.b(editable.toString()))) {
            if (this.f0 != null) {
                this.N(0, null, editable.toString());
            }
            this.r.setImeVisibility(false);
            this.E();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean V(View view, int n3, KeyEvent keyEvent) {
        if (this.f0 == null) {
            return false;
        }
        if (this.R == null) {
            return false;
        }
        if (keyEvent.getAction() != 0 || !keyEvent.hasNoModifiers()) return false;
        if (n3 == 66 || n3 == 84 || n3 == 61) return this.Q(this.r.getListSelection(), 0, null);
        if (n3 != 21 && n3 != 22) {
            if (n3 != 19) return false;
            this.r.getListSelection();
            return false;
        }
        n3 = n3 == 21 ? 0 : this.r.length();
        this.r.setSelection(n3);
        this.r.setListSelection(0);
        this.r.clearListSelection();
        this.r.b();
        return true;
    }

    public void W(CharSequence charSequence) {
        Editable editable = this.r.getText();
        this.c0 = editable;
        boolean bl = TextUtils.isEmpty((CharSequence)editable);
        this.g0(bl ^ true);
        this.i0(bl);
        this.b0();
        this.f0();
        if (this.M != null && !TextUtils.equals((CharSequence)charSequence, (CharSequence)this.b0)) {
            this.M.a(charSequence.toString());
        }
        this.b0 = charSequence.toString();
    }

    public void X() {
        this.h0(this.J());
        this.Z();
        if (this.r.hasFocus()) {
            this.F();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void Y() {
        SearchableInfo searchableInfo = this.f0;
        if (searchableInfo == null) return;
        try {
            if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                searchableInfo = this.D(this.J, searchableInfo);
                this.getContext().startActivity((Intent)searchableInfo);
                return;
            }
            if (!searchableInfo.getVoiceSearchLaunchRecognizer()) return;
            searchableInfo = this.C(this.K, searchableInfo);
            this.getContext().startActivity((Intent)searchableInfo);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.w((String)"SearchView", (String)"Could not find voice search activity");
            return;
        }
    }

    public final void Z() {
        this.post(this.h0);
    }

    public final void a0(int n3) {
        Editable editable = this.r.getText();
        Object object = this.R.b();
        if (object == null) {
            return;
        }
        if (object.moveToPosition(n3)) {
            if ((object = this.R.convertToString((Cursor)object)) != null) {
                this.setQuery((CharSequence)object);
                return;
            }
            this.setQuery((CharSequence)editable);
            return;
        }
        this.setQuery((CharSequence)editable);
    }

    public final void b0() {
        boolean bl = TextUtils.isEmpty((CharSequence)this.r.getText());
        int n3 = 0;
        int n4 = bl && (!this.P || this.d0) ? 0 : 1;
        Object object = this.x;
        n4 = n4 != 0 ? n3 : 8;
        object.setVisibility(n4);
        Drawable drawable = this.x.getDrawable();
        if (drawable != null) {
            object = !bl ? (Object)ViewGroup.ENABLED_STATE_SET : (Object)ViewGroup.EMPTY_STATE_SET;
            drawable.setState((int[])object);
        }
    }

    public void c0() {
        int[] nArray = this.r.hasFocus() ? ViewGroup.FOCUSED_STATE_SET : ViewGroup.EMPTY_STATE_SET;
        Drawable drawable = this.t.getBackground();
        if (drawable != null) {
            drawable.setState(nArray);
        }
        if ((drawable = this.u.getBackground()) != null) {
            drawable.setState(nArray);
        }
        this.invalidate();
    }

    public void clearFocus() {
        this.V = true;
        super.clearFocus();
        this.r.clearFocus();
        this.r.setImeVisibility(false);
        this.V = false;
    }

    public final void d0() {
        CharSequence charSequence = this.getQueryHint();
        SearchAutoComplete searchAutoComplete = this.r;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "";
        }
        searchAutoComplete.setHint(this.H(charSequence2));
    }

    public final void e0() {
        this.r.setThreshold(this.f0.getSuggestThreshold());
        this.r.setImeOptions(this.f0.getImeOptions());
        int n3 = this.f0.getInputType();
        int n4 = 1;
        int n5 = n3;
        if ((n3 & 0xF) == 1) {
            n5 = n3 &= 0xFFFEFFFF;
            if (this.f0.getSuggestAuthority() != null) {
                n5 = n3 | 0x90000;
            }
        }
        this.r.setInputType(n5);
        t0.a a4 = this.R;
        if (a4 != null) {
            a4.a(null);
        }
        if (this.f0.getSuggestAuthority() != null) {
            this.R = a4 = new h0(this.getContext(), this, this.f0, this.j0);
            this.r.setAdapter((ListAdapter)a4);
            a4 = (h0)this.R;
            n5 = n4;
            if (this.U) {
                n5 = 2;
            }
            ((h0)a4).w(n5);
        }
    }

    public final void f0() {
        int n3 = this.L() && (this.w.getVisibility() == 0 || this.y.getVisibility() == 0) ? 0 : 8;
        this.u.setVisibility(n3);
    }

    public final void g0(boolean bl) {
        int n3 = this.S && this.L() && this.hasFocus() && (bl || !this.a0) ? 0 : 8;
        this.w.setVisibility(n3);
    }

    public int getImeOptions() {
        return this.r.getImeOptions();
    }

    public int getInputType() {
        return this.r.getInputType();
    }

    public int getMaxWidth() {
        return this.W;
    }

    public CharSequence getQuery() {
        return this.r.getText();
    }

    public CharSequence getQueryHint() {
        CharSequence charSequence = this.T;
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = this.f0;
        if (charSequence != null && charSequence.getHintId() != 0) {
            return this.getContext().getText(this.f0.getHintId());
        }
        return this.L;
    }

    public int getSuggestionCommitIconResId() {
        return this.I;
    }

    public int getSuggestionRowLayout() {
        return this.H;
    }

    public t0.a getSuggestionsAdapter() {
        return this.R;
    }

    public final void h0(boolean bl) {
        this.Q = bl;
        int n3 = 8;
        int n4 = bl ? 0 : 8;
        boolean bl2 = TextUtils.isEmpty((CharSequence)this.r.getText());
        this.v.setVisibility(n4);
        this.g0(bl2 ^ true);
        View view = this.s;
        n4 = bl ? 8 : 0;
        view.setVisibility(n4);
        n4 = n3;
        if (this.F.getDrawable() != null) {
            n4 = this.P ? n3 : 0;
        }
        this.F.setVisibility(n4);
        this.b0();
        this.i0(bl2);
        this.f0();
    }

    public final void i0(boolean bl) {
        int n3;
        boolean bl2 = this.a0;
        int n4 = n3 = 8;
        if (bl2) {
            n4 = n3;
            if (!this.J()) {
                n4 = n3;
                if (bl) {
                    this.w.setVisibility(8);
                    n4 = 0;
                }
            }
        }
        this.y.setVisibility(n4);
    }

    @Override
    public void onActionViewCollapsed() {
        this.setQuery("", false);
        this.clearFocus();
        this.h0(true);
        this.r.setImeOptions(this.e0);
        this.d0 = false;
    }

    @Override
    public void onActionViewExpanded() {
        int n3;
        if (this.d0) {
            return;
        }
        this.d0 = true;
        this.e0 = n3 = this.r.getImeOptions();
        this.r.setImeOptions(n3 | 0x2000000);
        this.r.setText("");
        this.setIconified(false);
    }

    public void onDetachedFromWindow() {
        this.removeCallbacks(this.h0);
        this.post(this.i0);
        super.onDetachedFromWindow();
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        if (bl) {
            this.G((View)this.r, this.B);
            Object object = this.C;
            Rect rect = this.B;
            object.set(rect.left, 0, rect.right, n6 - n4);
            object = this.A;
            if (object == null) {
                object = new p(this.C, this.B, (View)this.r);
                this.A = object;
                this.setTouchDelegate((TouchDelegate)object);
                return;
            }
            object.a(this.C, this.B);
        }
    }

    @Override
    public void onMeasure(int n3, int n4) {
        if (this.J()) {
            super.onMeasure(n3, n4);
            return;
        }
        int n5 = View.MeasureSpec.getMode((int)n3);
        int n6 = View.MeasureSpec.getSize((int)n3);
        if (n5 != Integer.MIN_VALUE) {
            if (n5 != 0) {
                if (n5 != 0x40000000) {
                    n3 = n6;
                } else {
                    n5 = this.W;
                    n3 = n6;
                    if (n5 > 0) {
                        n3 = Math.min(n5, n6);
                    }
                }
            } else {
                n3 = this.W;
                if (n3 <= 0) {
                    n3 = this.getPreferredWidth();
                }
            }
        } else {
            n3 = this.W;
            n3 = n3 > 0 ? Math.min(n3, n6) : Math.min(this.getPreferredWidth(), n6);
        }
        n6 = View.MeasureSpec.getMode((int)n4);
        n4 = View.MeasureSpec.getSize((int)n4);
        if (n6 != Integer.MIN_VALUE) {
            if (n6 == 0) {
                n4 = this.getPreferredHeight();
            }
        } else {
            n4 = Math.min(this.getPreferredHeight(), n4);
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)n4, (int)0x40000000));
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.h0(parcelable.e);
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = this.J();
        return savedState;
    }

    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        this.Z();
    }

    public boolean requestFocus(int n3, Rect rect) {
        if (this.V) {
            return false;
        }
        if (!this.isFocusable()) {
            return false;
        }
        if (!this.J()) {
            boolean bl = this.r.requestFocus(n3, rect);
            if (bl) {
                this.h0(false);
            }
            return bl;
        }
        return super.requestFocus(n3, rect);
    }

    public void setAppSearchData(Bundle bundle) {
        this.g0 = bundle;
    }

    public void setIconified(boolean bl) {
        if (bl) {
            this.P();
            return;
        }
        this.T();
    }

    public void setIconifiedByDefault(boolean bl) {
        if (this.P == bl) {
            return;
        }
        this.P = bl;
        this.h0(bl);
        this.d0();
    }

    public void setImeOptions(int n3) {
        this.r.setImeOptions(n3);
    }

    public void setInputType(int n3) {
        this.r.setInputType(n3);
    }

    public void setMaxWidth(int n3) {
        this.W = n3;
        this.requestLayout();
    }

    public void setOnCloseListener(l l3) {
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.N = onFocusChangeListener;
    }

    public void setOnQueryTextListener(m m3) {
        this.M = m3;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.O = onClickListener;
    }

    public void setOnSuggestionListener(n n3) {
    }

    public void setQuery(CharSequence charSequence, boolean bl) {
        this.r.setText(charSequence);
        if (charSequence != null) {
            SearchAutoComplete searchAutoComplete = this.r;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.c0 = charSequence;
        }
        if (bl && !TextUtils.isEmpty((CharSequence)charSequence)) {
            this.U();
        }
    }

    public void setQueryHint(CharSequence charSequence) {
        this.T = charSequence;
        this.d0();
    }

    public void setQueryRefinementEnabled(boolean bl) {
        this.U = bl;
        t0.a a4 = this.R;
        if (a4 instanceof h0) {
            a4 = (h0)a4;
            int n3 = bl ? 2 : 1;
            ((h0)a4).w(n3);
        }
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        boolean bl;
        this.f0 = searchableInfo;
        if (searchableInfo != null) {
            this.e0();
            this.d0();
        }
        this.a0 = bl = this.I();
        if (bl) {
            this.r.setPrivateImeOptions("nm");
        }
        this.h0(this.J());
    }

    public void setSubmitButtonEnabled(boolean bl) {
        this.S = bl;
        this.h0(this.J());
    }

    public void setSuggestionsAdapter(t0.a a4) {
        this.R = a4;
        this.r.setAdapter((ListAdapter)a4);
    }

    public void z() {
        if (this.z.getWidth() > 1) {
            Resources resources = this.getContext().getResources();
            int n3 = this.t.getPaddingLeft();
            Rect rect = new Rect();
            boolean bl = t0.b((View)this);
            int n4 = this.P ? resources.getDimensionPixelSize(c.d.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(c.d.abc_dropdownitem_text_padding_left) : 0;
            this.r.getDropDownBackground().getPadding(rect);
            int n5 = bl ? -rect.left : n3 - (rect.left + n4);
            this.r.setDropDownHorizontalOffset(n5);
            int n6 = this.z.getWidth();
            n5 = rect.left;
            int n7 = rect.right;
            this.r.setDropDownWidth(n6 + n5 + n7 + n4 - n3);
        }
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState b(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] c(int n3) {
                return new SavedState[n3];
            }
        };
        public boolean e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = (Boolean)parcel.readValue(null);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SearchView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" isIconified=");
            stringBuilder.append(this.e);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeValue((Object)this.e);
        }
    }

    public static class SearchAutoComplete
    extends AppCompatAutoCompleteTextView {
        public int g;
        public SearchView h;
        public boolean i;
        public final Runnable j = new Runnable(this){
            public final SearchAutoComplete c;
            {
                this.c = searchAutoComplete;
            }

            @Override
            public void run() {
                this.c.d();
            }
        };

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, a.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int n3) {
            super(context, attributeSet, n3);
            this.g = this.getThreshold();
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = this.getResources().getConfiguration();
            int n3 = configuration.screenWidthDp;
            int n4 = configuration.screenHeightDp;
            if (n3 >= 960 && n4 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (n3 < 600 && (n3 < 640 || n4 < 480)) {
                return 160;
            }
            return 192;
        }

        public void b() {
            if (Build.VERSION.SDK_INT >= 29) {
                androidx.appcompat.widget.SearchView$k.b(this, 1);
                if (this.enoughToFilter()) {
                    this.showDropDown();
                }
                return;
            }
            q0.c(this);
        }

        public boolean c() {
            return TextUtils.getTrimmedLength((CharSequence)this.getText()) == 0;
        }

        public void d() {
            if (this.i) {
                ((InputMethodManager)this.getContext().getSystemService("input_method")).showSoftInput((View)this, 0);
                this.i = false;
            }
        }

        public boolean enoughToFilter() {
            return this.g <= 0 || super.enoughToFilter();
            {
            }
        }

        @Override
        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            editorInfo = super.onCreateInputConnection(editorInfo);
            if (this.i) {
                this.removeCallbacks(this.j);
                this.post(this.j);
            }
            return editorInfo;
        }

        public void onFinishInflate() {
            super.onFinishInflate();
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            this.setMinWidth((int)TypedValue.applyDimension((int)1, (float)this.getSearchViewTextMinWidthDp(), (DisplayMetrics)displayMetrics));
        }

        public void onFocusChanged(boolean bl, int n3, Rect rect) {
            super.onFocusChanged(bl, n3, rect);
            this.h.X();
        }

        public boolean onKeyPreIme(int n3, KeyEvent keyEvent) {
            if (n3 == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                    if (dispatcherState != null) {
                        dispatcherState.startTracking(keyEvent, (Object)this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                    if (dispatcherState != null) {
                        dispatcherState.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.h.clearFocus();
                        this.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(n3, keyEvent);
        }

        public void onWindowFocusChanged(boolean bl) {
            super.onWindowFocusChanged(bl);
            if (bl && this.h.hasFocus() && this.getVisibility() == 0) {
                this.i = true;
                if (SearchView.K(this.getContext())) {
                    this.b();
                }
            }
        }

        public void performCompletion() {
        }

        public void replaceText(CharSequence charSequence) {
        }

        public void setImeVisibility(boolean bl) {
            InputMethodManager inputMethodManager = (InputMethodManager)this.getContext().getSystemService("input_method");
            if (!bl) {
                this.i = false;
                this.removeCallbacks(this.j);
                inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
                return;
            }
            if (inputMethodManager.isActive((View)this)) {
                this.i = false;
                this.removeCallbacks(this.j);
                inputMethodManager.showSoftInput((View)this, 0);
                return;
            }
            this.i = true;
        }

        public void setSearchView(SearchView searchView) {
            this.h = searchView;
        }

        public void setThreshold(int n3) {
            super.setThreshold(n3);
            this.g = n3;
        }
    }

    public static abstract class k {
        public static void a(AutoCompleteTextView autoCompleteTextView) {
            autoCompleteTextView.refreshAutoCompleteResults();
        }

        public static void b(SearchAutoComplete searchAutoComplete, int n3) {
            searchAutoComplete.setInputMethodMode(n3);
        }
    }

    public static interface l {
    }

    public static interface m {
        public boolean a(String var1);

        public boolean b(String var1);
    }

    public static interface n {
    }

    public static class o {
        public Method a = null;
        public Method b = null;
        public Method c = null;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public o() {
            Method method;
            androidx.appcompat.widget.SearchView$o.d();
            try {
                this.a = method = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", null);
                ((AccessibleObject)method).setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.b = method = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", null);
                ((AccessibleObject)method).setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.c = method = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                ((AccessibleObject)method).setAccessible(true);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                return;
            }
        }

        public static void d() {
            if (Build.VERSION.SDK_INT < 29) {
                return;
            }
            throw new UnsupportedClassVersionError("This function can only be used for API Level < 29.");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void a(AutoCompleteTextView autoCompleteTextView) {
            androidx.appcompat.widget.SearchView$o.d();
            Method method = this.b;
            if (method == null) return;
            try {
                method.invoke((Object)autoCompleteTextView, null);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void b(AutoCompleteTextView autoCompleteTextView) {
            androidx.appcompat.widget.SearchView$o.d();
            Method method = this.a;
            if (method == null) return;
            try {
                method.invoke((Object)autoCompleteTextView, null);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void c(AutoCompleteTextView autoCompleteTextView) {
            androidx.appcompat.widget.SearchView$o.d();
            Method method = this.c;
            if (method == null) return;
            try {
                method.invoke((Object)autoCompleteTextView, Boolean.TRUE);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }
    }

    public static class p
    extends TouchDelegate {
        public final View a;
        public final Rect b;
        public final Rect c;
        public final Rect d;
        public final int e;
        public boolean f;

        public p(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.e = ViewConfiguration.get((Context)view.getContext()).getScaledTouchSlop();
            this.b = new Rect();
            this.d = new Rect();
            this.c = new Rect();
            this.a(rect, rect2);
            this.a = view;
        }

        public void a(Rect rect, Rect rect2) {
            this.b.set(rect);
            this.d.set(rect);
            rect = this.d;
            int n3 = this.e;
            rect.inset(-n3, -n3);
            this.c.set(rect2);
        }

        /*
         * Unable to fully structure code
         */
        public boolean onTouchEvent(MotionEvent var1_1) {
            block7: {
                block8: {
                    block9: {
                        var4_2 = (int)var1_1.getX();
                        var3_3 = (int)var1_1.getY();
                        var2_4 = var1_1.getAction();
                        var5_5 = true;
                        if (var2_4 == 0) break block8;
                        if (var2_4 == 1 || var2_4 == 2) break block9;
                        if (var2_4 == 3) {
                            var5_5 = this.f;
                            this.f = false;
lbl10:
                            // 3 sources

                            while (true) {
                                var2_4 = 1;
                                break block7;
                                break;
                            }
                        }
                        ** GOTO lbl-1000
                    }
                    var5_5 = var6_6 = this.f;
                    if (!var6_6) ** GOTO lbl10
                    var5_5 = var6_6;
                    if (!this.d.contains(var4_2, var3_3)) ** break;
                    ** while (true)
                    var5_5 = var6_6;
                    var2_4 = 0;
                    break block7;
                }
                if (this.b.contains(var4_2, var3_3)) {
                    this.f = true;
                    var2_4 = 1;
                } else lbl-1000:
                // 2 sources

                {
                    var2_4 = 1;
                    var5_5 = false;
                }
            }
            if (var5_5) {
                if (var2_4 != 0 && !this.c.contains(var4_2, var3_3)) {
                    var1_1.setLocation((float)(this.a.getWidth() / 2), (float)(this.a.getHeight() / 2));
                } else {
                    var7_7 = this.c;
                    var1_1.setLocation((float)(var4_2 - var7_7.left), (float)(var3_3 - var7_7.top));
                }
                return this.a.dispatchTouchEvent(var1_1);
            }
            return false;
        }
    }
}

