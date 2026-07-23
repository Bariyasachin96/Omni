/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.SearchableInfo
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$NotFoundException
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
import android.content.pm.PackageManager;
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
        try {
            object = object.getString(n3);
            return object;
        }
        catch (Exception exception) {
            Log.e((String)"SuggestionsAdapter", (String)"unexpected error retrieving valid column from cursor, did the remote process die?", (Throwable)exception);
            return null;
        }
    }

    public final void A(Cursor object) {
        if ((object = object != null ? object.getExtras() : null) != null) {
            object.getBoolean("in_progress");
        }
    }

    @Override
    public void a(Cursor cursor) {
        Exception exception2;
        block6: {
            block4: {
                block5: {
                    if (!this.s) break block5;
                    Log.w((String)"SuggestionsAdapter", (String)"Tried to change cursor after adapter was closed.");
                    if (cursor != null) {
                        cursor.close();
                        return;
                    }
                    break block4;
                }
                try {
                    super.a(cursor);
                    if (cursor == null) break block4;
                }
                catch (Exception exception2) {}
                this.v = cursor.getColumnIndex("suggest_text_1");
                this.w = cursor.getColumnIndex("suggest_text_2");
                this.x = cursor.getColumnIndex("suggest_text_2_url");
                this.y = cursor.getColumnIndex("suggest_icon_1");
                this.z = cursor.getColumnIndex("suggest_icon_2");
                this.A = cursor.getColumnIndex("suggest_flags");
                return;
                break block6;
            }
            return;
        }
        Log.e((String)"SuggestionsAdapter", (String)"error changing cursor and caching columns", (Throwable)exception2);
    }

    @Override
    public Cursor c(CharSequence charSequence) {
        block4: {
            charSequence = charSequence == null ? "" : charSequence.toString();
            if (this.n.getVisibility() == 0 && this.n.getWindowVisibility() == 0) {
                charSequence = this.u(this.o, (String)charSequence, 50);
                if (charSequence == null) break block4;
                try {
                    charSequence.getCount();
                    return charSequence;
                }
                catch (RuntimeException runtimeException) {
                    Log.w((String)"SuggestionsAdapter", (String)"Search suggestions query threw an exception.", (Throwable)runtimeException);
                }
            }
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
        try {
            view = super.getDropDownView(n3, view, viewGroup);
            return view;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)"SuggestionsAdapter", (String)"Search suggestions cursor threw exception.", (Throwable)runtimeException);
            viewGroup = this.f(this.p, this.b(), viewGroup);
            if (viewGroup != null) {
                ((a)viewGroup.getTag()).a.setText((CharSequence)((Object)runtimeException).toString());
            }
            return viewGroup;
        }
    }

    @Override
    public View getView(int n3, View view, ViewGroup viewGroup) {
        try {
            view = super.getView(n3, view, viewGroup);
            return view;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)"SuggestionsAdapter", (String)"Search suggestions cursor threw exception.", (Throwable)runtimeException);
            viewGroup = this.g(this.p, this.b(), viewGroup);
            if (viewGroup != null) {
                ((a)viewGroup.getTag()).a.setText((CharSequence)((Object)runtimeException).toString());
            }
            return viewGroup;
        }
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
        Object object;
        PackageManager packageManager = this.p.getPackageManager();
        try {
            object = packageManager.getActivityInfo(componentName, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w((String)"SuggestionsAdapter", (String)((Object)((Object)nameNotFoundException)).toString());
            return null;
        }
        int n3 = object.getIconResource();
        if (n3 == 0) {
            return null;
        }
        object = packageManager.getDrawable(componentName.getPackageName(), n3, ((ActivityInfo)object).applicationInfo);
        if (object == null) {
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

    /*
     * Exception decompiling
     */
    public final Drawable p(Uri var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 2[TRYBLOCK] [3 : 28->80)] java.io.FileNotFoundException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public Drawable q(Uri uri) {
        block10: {
            Object object;
            block11: {
                block12: {
                    String string = uri.getAuthority();
                    if (TextUtils.isEmpty((CharSequence)string)) break block10;
                    try {
                        object = this.p.getPackageManager().getResourcesForApplication(string);
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("No package found for authority: ");
                        stringBuilder.append(uri);
                        throw new FileNotFoundException(stringBuilder.toString());
                    }
                    List list = uri.getPathSegments();
                    if (list == null) break block11;
                    int n3 = list.size();
                    if (n3 == 1) {
                        try {
                            n3 = Integer.parseInt((String)list.get(0));
                        }
                        catch (NumberFormatException numberFormatException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Single path segment is not a resource ID: ");
                            stringBuilder.append(uri);
                            throw new FileNotFoundException(stringBuilder.toString());
                        }
                    }
                    if (n3 != 2) break block12;
                    n3 = object.getIdentifier((String)list.get(1), (String)list.get(0), string);
                    if (n3 != 0) {
                        return object.getDrawable(n3);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("No resource found for: ");
                    ((StringBuilder)object).append(uri);
                    throw new FileNotFoundException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("More than two path segments: ");
                ((StringBuilder)object).append(uri);
                throw new FileNotFoundException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No path: ");
            ((StringBuilder)object).append(uri);
            throw new FileNotFoundException(((StringBuilder)object).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No authority: ");
        stringBuilder.append(uri);
        throw new FileNotFoundException(stringBuilder.toString());
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
                    int n3;
                    block9: {
                        n3 = Integer.parseInt(string);
                        charSequence = new StringBuilder();
                        charSequence.append("android.resource://");
                        charSequence.append(this.p.getPackageName());
                        charSequence.append("/");
                        charSequence.append(n3);
                        charSequence = charSequence.toString();
                        stringBuilder = this.j((String)charSequence);
                        if (stringBuilder == null) break block9;
                        return stringBuilder;
                    }
                    try {
                        stringBuilder = e0.a.d(this.p, n3);
                        this.z((String)charSequence, (Drawable)stringBuilder);
                        return stringBuilder;
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Icon resource not found: ");
                        stringBuilder2.append(string);
                        Log.w((String)"SuggestionsAdapter", (String)stringBuilder2.toString());
                        return null;
                    }
                    catch (NumberFormatException numberFormatException) {
                        charSequence = this.j(string);
                        if (charSequence != null) {
                            return charSequence;
                        }
                        charSequence = this.p(Uri.parse((String)string));
                        this.z(string, (Drawable)charSequence);
                    }
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

