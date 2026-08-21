/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.SearchableInfo
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.SpannableString
 *  android.text.TextUtils
 *  android.text.style.TextAppearanceSpan
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import c.f;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;
import t0.c;

public class h0
extends c
implements View.OnClickListener {
    public int A = -1;
    public final SearchView n;
    public final SearchableInfo o;
    public final Context p;
    public final WeakHashMap q;
    public final int r;
    public boolean s = false;
    public int t = 1;
    public ColorStateList u;
    public int v = -1;
    public int w = -1;
    public int x = -1;
    public int y = -1;
    public int z = -1;

    public h0(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), null, true);
        this.n = searchView;
        this.o = searchableInfo;
        this.r = searchView.getSuggestionCommitIconResId();
        this.p = context;
        this.q = weakHashMap;
    }

    public static String n(Cursor cursor, String string) {
        return h0.v(cursor, cursor.getColumnIndex(string));
    }

    public static String v(Cursor object, int n3) {
        if (n3 == -1) {
            return null;
        }
        object = object.getString(n3);
        return object;
    }

    public final void A(Cursor object) {
        if ((object = object != null ? object.getExtras() : null) != null) {
            object.getBoolean("in_progress");
        }
    }

    @Override
    public void a(Cursor cursor) {
        if (this.s) {
            Log.w((String)"SuggestionsAdapter", (String)"Tried to change cursor after adapter was closed.");
            if (cursor != null) {
                cursor.close();
                return;
            }
        } else {
            super.a(cursor);
            if (cursor != null) {
                this.v = cursor.getColumnIndex("suggest_text_1");
                this.w = cursor.getColumnIndex("suggest_text_2");
                this.x = cursor.getColumnIndex("suggest_text_2_url");
                this.y = cursor.getColumnIndex("suggest_icon_1");
                this.z = cursor.getColumnIndex("suggest_icon_2");
                this.A = cursor.getColumnIndex("suggest_flags");
                return;
            }
        }
    }

    @Override
    public Cursor c(CharSequence charSequence) {
        charSequence = charSequence == null ? "" : charSequence.toString();
        if (this.n.getVisibility() == 0 && this.n.getWindowVisibility() == 0 && (charSequence = this.u(this.o, (String)charSequence, 50)) != null) {
            charSequence.getCount();
            return charSequence;
        }
        return null;
    }

    @Override
    public CharSequence convertToString(Cursor object) {
        if (object == null) {
            return null;
        }
        String string = h0.n(object, "suggest_intent_query");
        if (string != null) {
            return string;
        }
        if (this.o.shouldRewriteQueryFromData() && (string = h0.n(object, "suggest_intent_data")) != null) {
            return string;
        }
        if (this.o.shouldRewriteQueryFromText() && (object = h0.n(object, "suggest_text_1")) != null) {
            return object;
        }
        return null;
    }

    @Override
    public void d(View object, Context object2, Cursor cursor) {
        int n3;
        object2 = (a)object.getTag();
        int n4 = this.A;
        n4 = n4 != -1 ? cursor.getInt(n4) : 0;
        if (object2.a != null) {
            object = h0.v(cursor, this.v);
            this.y(object2.a, (CharSequence)object);
        }
        if (object2.b != null) {
            object = h0.v(cursor, this.x);
            object = object != null ? this.k((CharSequence)object) : h0.v(cursor, this.w);
            if (TextUtils.isEmpty((CharSequence)object)) {
                TextView textView = object2.a;
                if (textView != null) {
                    textView.setSingleLine(false);
                    object2.a.setMaxLines(2);
                }
            } else {
                TextView textView = object2.a;
                if (textView != null) {
                    textView.setSingleLine(true);
                    object2.a.setMaxLines(1);
                }
            }
            this.y(object2.b, (CharSequence)object);
        }
        if ((object = object2.c) != null) {
            this.x((ImageView)object, this.s(cursor), 4);
        }
        if ((object = object2.d) != null) {
            this.x((ImageView)object, this.t(cursor), 8);
        }
        if ((n3 = this.t) != 2 && (n3 != 1 || (n4 & 1) == 0)) {
            object2.e.setVisibility(8);
            return;
        }
        object2.e.setVisibility(0);
        object2.e.setTag((Object)object2.a.getText());
        object2.e.setOnClickListener((View.OnClickListener)this);
    }

    @Override
    public View g(Context context, Cursor cursor, ViewGroup viewGroup) {
        context = super.g(context, cursor, viewGroup);
        context.setTag((Object)new a((View)context));
        ((ImageView)context.findViewById(c.f.edit_query)).setImageResource(this.r);
        return context;
    }

    @Override
    public View getDropDownView(int n3, View view, ViewGroup viewGroup) {
        view = super.getDropDownView(n3, view, viewGroup);
        return view;
    }

    @Override
    public View getView(int n3, View view, ViewGroup viewGroup) {
        view = super.getView(n3, view, viewGroup);
        return view;
    }

    public boolean hasStableIds() {
        return false;
    }

    public final Drawable j(String string) {
        if ((string = (Drawable.ConstantState)this.q.get(string)) == null) {
            return null;
        }
        return string.newDrawable();
    }

    public final CharSequence k(CharSequence charSequence) {
        TypedValue typedValue;
        if (this.u == null) {
            typedValue = new TypedValue();
            this.p.getTheme().resolveAttribute(c.a.textColorSearchUrl, typedValue, true);
            this.u = this.p.getResources().getColorStateList(typedValue.resourceId);
        }
        typedValue = new SpannableString(charSequence);
        typedValue.setSpan((Object)new TextAppearanceSpan(null, 0, 0, this.u, null), 0, charSequence.length(), 33);
        return typedValue;
    }

    public final Drawable l(ComponentName componentName) {
        Object object = this.p.getPackageManager();
        ActivityInfo activityInfo = object.getActivityInfo(componentName, 128);
        int n3 = activityInfo.getIconResource();
        if (n3 == 0) {
            return null;
        }
        if ((object = object.getDrawable(componentName.getPackageName(), n3, activityInfo.applicationInfo)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid icon resource ");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(" for ");
            ((StringBuilder)object).append(componentName.flattenToShortString());
            Log.w((String)"SuggestionsAdapter", (String)((StringBuilder)object).toString());
            return null;
        }
        return object;
    }

    public final Drawable m(ComponentName object) {
        String string = object.flattenToShortString();
        boolean bl = this.q.containsKey(string);
        Object var3_4 = null;
        if (bl) {
            object = (Drawable.ConstantState)this.q.get(string);
            if (object == null) {
                return null;
            }
            return object.newDrawable(this.p.getResources());
        }
        Drawable drawable = this.l((ComponentName)object);
        object = drawable == null ? var3_4 : drawable.getConstantState();
        this.q.put(string, object);
        return drawable;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.A(this.b());
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        this.A(this.b());
    }

    public final Drawable o() {
        Drawable drawable = this.m(this.o.getSearchActivity());
        if (drawable != null) {
            return drawable;
        }
        return this.p.getPackageManager().getDefaultActivityIcon();
    }

    public void onClick(View object) {
        if ((object = object.getTag()) instanceof CharSequence) {
            this.n.S((CharSequence)object);
        }
    }

    public final Drawable p(Uri uri) {
        boolean bl = "android.resource".equals(uri.getScheme());
        if (bl) {
            Drawable drawable = this.q(uri);
            return drawable;
        }
        Object object = this.p.getContentResolver().openInputStream(uri);
        if (object != null) {
            Drawable drawable = Drawable.createFromStream((InputStream)object, null);
            ((InputStream)object).close();
            return drawable;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to open ");
        ((StringBuilder)object).append(uri);
        FileNotFoundException fileNotFoundException = new FileNotFoundException(((StringBuilder)object).toString());
        throw fileNotFoundException;
    }

    public Drawable q(Uri uri) {
        CharSequence charSequence;
        block4: {
            block5: {
                block8: {
                    int n3;
                    Resources resources;
                    block7: {
                        List list;
                        block6: {
                            charSequence = uri.getAuthority();
                            if (TextUtils.isEmpty((CharSequence)charSequence)) break block4;
                            resources = this.p.getPackageManager().getResourcesForApplication((String)charSequence);
                            list = uri.getPathSegments();
                            if (list == null) break block5;
                            n3 = list.size();
                            if (n3 != 1) break block6;
                            n3 = Integer.parseInt((String)list.get(0));
                            break block7;
                        }
                        if (n3 != 2) break block8;
                        n3 = resources.getIdentifier((String)list.get(1), (String)list.get(0), (String)charSequence);
                    }
                    if (n3 != 0) {
                        return resources.getDrawable(n3);
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("No resource found for: ");
                    ((StringBuilder)charSequence).append(uri);
                    throw new FileNotFoundException(((StringBuilder)charSequence).toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("More than two path segments: ");
                ((StringBuilder)charSequence).append(uri);
                throw new FileNotFoundException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("No path: ");
            ((StringBuilder)charSequence).append(uri);
            throw new FileNotFoundException(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("No authority: ");
        ((StringBuilder)charSequence).append(uri);
        throw new FileNotFoundException(((StringBuilder)charSequence).toString());
    }

    public final Drawable r(String string) {
        StringBuilder stringBuilder;
        CharSequence charSequence = stringBuilder = null;
        if (string != null) {
            charSequence = stringBuilder;
            if (!string.isEmpty()) {
                if ("0".equals(string)) {
                    charSequence = stringBuilder;
                } else {
                    int n3 = Integer.parseInt(string);
                    charSequence = new StringBuilder();
                    charSequence.append("android.resource://");
                    charSequence.append(this.p.getPackageName());
                    charSequence.append("/");
                    charSequence.append(n3);
                    charSequence = charSequence.toString();
                    stringBuilder = this.j((String)charSequence);
                    if (stringBuilder != null) {
                        return stringBuilder;
                    }
                    stringBuilder = e0.a.d(this.p, n3);
                    this.z((String)charSequence, (Drawable)stringBuilder);
                    return stringBuilder;
                }
            }
        }
        return charSequence;
    }

    public final Drawable s(Cursor cursor) {
        int n3 = this.y;
        if (n3 == -1) {
            return null;
        }
        if ((cursor = this.r(cursor.getString(n3))) != null) {
            return cursor;
        }
        return this.o();
    }

    public final Drawable t(Cursor cursor) {
        int n3 = this.z;
        if (n3 == -1) {
            return null;
        }
        return this.r(cursor.getString(n3));
    }

    public Cursor u(SearchableInfo stringArray, String string, int n3) {
        Object var4_4 = null;
        if (stringArray == null) {
            return null;
        }
        String string2 = stringArray.getSuggestAuthority();
        if (string2 == null) {
            return null;
        }
        string2 = new Uri.Builder().scheme("content").authority(string2).query("").fragment("");
        String string3 = stringArray.getSuggestPath();
        if (string3 != null) {
            string2.appendEncodedPath(string3);
        }
        string2.appendPath("search_suggest_query");
        string3 = stringArray.getSuggestSelection();
        if (string3 != null) {
            stringArray = new String[]{string};
        } else {
            string2.appendPath(string);
            stringArray = var4_4;
        }
        if (n3 > 0) {
            string2.appendQueryParameter("limit", String.valueOf(n3));
        }
        string = string2.build();
        return this.p.getContentResolver().query((Uri)string, null, string3, stringArray, null);
    }

    public void w(int n3) {
        this.t = n3;
    }

    public final void x(ImageView imageView, Drawable drawable, int n3) {
        imageView.setImageDrawable(drawable);
        if (drawable == null) {
            imageView.setVisibility(n3);
            return;
        }
        imageView.setVisibility(0);
        drawable.setVisible(false, false);
        drawable.setVisible(true, false);
    }

    public final void y(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
    }

    public final void z(String string, Drawable drawable) {
        if (drawable != null) {
            this.q.put(string, drawable.getConstantState());
        }
    }

    public static final class a {
        public final TextView a;
        public final TextView b;
        public final ImageView c;
        public final ImageView d;
        public final ImageView e;

        public a(View view) {
            this.a = (TextView)view.findViewById(16908308);
            this.b = (TextView)view.findViewById(16908309);
            this.c = (ImageView)view.findViewById(16908295);
            this.d = (ImageView)view.findViewById(16908296);
            this.e = (ImageView)view.findViewById(c.f.edit_query);
        }
    }
}

