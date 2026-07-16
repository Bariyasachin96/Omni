/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.res.ColorStateList
 *  android.graphics.Paint
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.PorterDuff$Mode
 *  android.icu.text.DecimalFormatSymbols
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.text.PrecomputedText
 *  android.text.PrecomputedText$Params
 *  android.text.TextDirectionHeuristic
 *  android.text.TextDirectionHeuristics
 *  android.text.TextPaint
 *  android.text.method.PasswordTransformationMethod
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.widget.TextView
 */
package androidx.core.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.icu.text.DecimalFormatSymbols;
import android.os.Build;
import android.text.Editable;
import android.text.PrecomputedText;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import m0.l;
import n0.h;

public abstract class j {
    public static int a(TextView textView) {
        return textView.getPaddingTop() - textView.getPaint().getFontMetricsInt().top;
    }

    public static int b(TextView textView) {
        return textView.getPaddingBottom() + textView.getPaint().getFontMetricsInt().bottom;
    }

    public static int c(TextDirectionHeuristic textDirectionHeuristic) {
        TextDirectionHeuristic textDirectionHeuristic2 = TextDirectionHeuristics.FIRSTSTRONG_RTL;
        if (textDirectionHeuristic == textDirectionHeuristic2) {
            return 1;
        }
        TextDirectionHeuristic textDirectionHeuristic3 = TextDirectionHeuristics.FIRSTSTRONG_LTR;
        if (textDirectionHeuristic == textDirectionHeuristic3) {
            return 1;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) {
            return 2;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
            return 3;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
            return 4;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LOCALE) {
            return 5;
        }
        if (textDirectionHeuristic == textDirectionHeuristic3) {
            return 6;
        }
        if (textDirectionHeuristic == textDirectionHeuristic2) {
            return 7;
        }
        return 1;
    }

    public static TextDirectionHeuristic d(TextView textView) {
        if (textView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            return TextDirectionHeuristics.LTR;
        }
        int n3 = Build.VERSION.SDK_INT;
        byte by = 1;
        if (n3 >= 28 && (textView.getInputType() & 0xF) == 3) {
            by = Character.getDirectionality(c.b(b.a(textView.getTextLocale()))[0].codePointAt(0));
            if (by != 1 && by != 2) {
                return TextDirectionHeuristics.LTR;
            }
            return TextDirectionHeuristics.RTL;
        }
        if (textView.getLayoutDirection() != 1) {
            by = 0;
        }
        switch (textView.getTextDirection()) {
            default: {
                if (by != 0) {
                    return TextDirectionHeuristics.FIRSTSTRONG_RTL;
                }
                return TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }
            case 7: {
                return TextDirectionHeuristics.FIRSTSTRONG_RTL;
            }
            case 6: {
                return TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }
            case 5: {
                return TextDirectionHeuristics.LOCALE;
            }
            case 4: {
                return TextDirectionHeuristics.RTL;
            }
            case 3: {
                return TextDirectionHeuristics.LTR;
            }
            case 2: 
        }
        return TextDirectionHeuristics.ANYRTL_LTR;
    }

    public static l.a e(TextView textView) {
        if (Build.VERSION.SDK_INT >= 28) {
            return new l.a(c.c(textView));
        }
        l.a.a a4 = new l.a.a(new TextPaint((Paint)textView.getPaint()));
        a4.b(a.a(textView));
        a4.c(a.d(textView));
        a4.d(j.d(textView));
        return a4.a();
    }

    public static void f(TextView textView, ColorStateList colorStateList) {
        h.g(textView);
        a.f(textView, colorStateList);
    }

    public static void g(TextView textView, PorterDuff.Mode mode) {
        h.g(textView);
        a.g(textView, mode);
    }

    public static void h(TextView textView, int n3) {
        h.d(n3);
        if (Build.VERSION.SDK_INT >= 28) {
            c.d(textView, n3);
            return;
        }
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int n4 = textView.getIncludeFontPadding() ? fontMetricsInt.top : fontMetricsInt.ascent;
        if (n3 > Math.abs(n4)) {
            textView.setPadding(textView.getPaddingLeft(), n3 + n4, textView.getPaddingRight(), textView.getPaddingBottom());
        }
    }

    public static void i(TextView textView, int n3) {
        h.d(n3);
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int n4 = textView.getIncludeFontPadding() ? fontMetricsInt.bottom : fontMetricsInt.descent;
        if (n3 > Math.abs(n4)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), n3 - n4);
        }
    }

    public static void j(TextView textView, int n3) {
        h.d(n3);
        int n4 = textView.getPaint().getFontMetricsInt(null);
        if (n3 != n4) {
            textView.setLineSpacing((float)(n3 - n4), 1.0f);
        }
    }

    public static void k(TextView textView, int n3, float f3) {
        if (Build.VERSION.SDK_INT >= 34) {
            d.a(textView, n3, f3);
            return;
        }
        j.j(textView, Math.round(TypedValue.applyDimension((int)n3, (float)f3, (DisplayMetrics)textView.getResources().getDisplayMetrics())));
    }

    public static void l(TextView textView, l l3) {
        if (Build.VERSION.SDK_INT >= 29) {
            throw null;
        }
        j.e(textView);
        throw null;
    }

    public static void m(TextView textView, int n3) {
        textView.setTextAppearance(n3);
    }

    public static void n(TextView textView, l.a a4) {
        textView.setTextDirection(j.c(a4.d()));
        textView.getPaint().set(a4.e());
        a.e(textView, a4.b());
        a.h(textView, a4.c());
    }

    public static ActionMode.Callback o(ActionMode.Callback callback) {
        ActionMode.Callback callback2 = callback;
        if (callback instanceof e) {
            callback2 = ((e)callback).d();
        }
        return callback2;
    }

    public static ActionMode.Callback p(TextView textView, ActionMode.Callback callback) {
        if (Build.VERSION.SDK_INT <= 27 && !(callback instanceof e) && callback != null) {
            return new e(callback, textView);
        }
        return callback;
    }

    public static abstract class a {
        public static int a(TextView textView) {
            return textView.getBreakStrategy();
        }

        public static ColorStateList b(TextView textView) {
            return textView.getCompoundDrawableTintList();
        }

        public static PorterDuff.Mode c(TextView textView) {
            return textView.getCompoundDrawableTintMode();
        }

        public static int d(TextView textView) {
            return textView.getHyphenationFrequency();
        }

        public static void e(TextView textView, int n3) {
            textView.setBreakStrategy(n3);
        }

        public static void f(TextView textView, ColorStateList colorStateList) {
            textView.setCompoundDrawableTintList(colorStateList);
        }

        public static void g(TextView textView, PorterDuff.Mode mode) {
            textView.setCompoundDrawableTintMode(mode);
        }

        public static void h(TextView textView, int n3) {
            textView.setHyphenationFrequency(n3);
        }
    }

    public static abstract class b {
        public static DecimalFormatSymbols a(Locale locale) {
            return DecimalFormatSymbols.getInstance((Locale)locale);
        }
    }

    public static abstract class c {
        public static CharSequence a(PrecomputedText precomputedText) {
            return (CharSequence)precomputedText;
        }

        public static String[] b(DecimalFormatSymbols decimalFormatSymbols) {
            return decimalFormatSymbols.getDigitStrings();
        }

        public static PrecomputedText.Params c(TextView textView) {
            return textView.getTextMetricsParams();
        }

        public static void d(TextView textView, int n3) {
            textView.setFirstBaselineToTopHeight(n3);
        }
    }

    public static abstract class d {
        public static void a(TextView textView, int n3, float f3) {
            textView.setLineHeight(n3, f3);
        }
    }

    public static class e
    implements ActionMode.Callback {
        public final ActionMode.Callback a;
        public final TextView b;
        public Class c;
        public Method d;
        public boolean e;
        public boolean f;

        public e(ActionMode.Callback callback, TextView textView) {
            this.a = callback;
            this.b = textView;
            this.f = false;
        }

        public final Intent a() {
            return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
        }

        public final Intent b(ResolveInfo resolveInfo, TextView textView) {
            textView = this.a().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", this.e(textView) ^ true);
            resolveInfo = resolveInfo.activityInfo;
            return textView.setClassName(resolveInfo.packageName, resolveInfo.name);
        }

        public final List c(Context context, PackageManager object) {
            ArrayList<ResolveInfo> arrayList = new ArrayList<ResolveInfo>();
            if (context instanceof Activity) {
                for (ResolveInfo resolveInfo : object.queryIntentActivities(this.a(), 0)) {
                    if (!this.f(resolveInfo, context)) continue;
                    arrayList.add(resolveInfo);
                }
            }
            return arrayList;
        }

        public ActionMode.Callback d() {
            return this.a;
        }

        public final boolean e(TextView textView) {
            return textView instanceof Editable && textView.onCheckIsTextEditor() && textView.isEnabled();
        }

        public final boolean f(ResolveInfo object, Context context) {
            if (context.getPackageName().equals(object.activityInfo.packageName)) {
                return true;
            }
            object = object.activityInfo;
            if (!object.exported) {
                return false;
            }
            object = object.permission;
            return object == null || context.checkSelfPermission((String)object) == 0;
            {
            }
        }

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public final void g(Menu menu) {
            int n3;
            MenuItem menuItem;
            PackageManager packageManager;
            Object object;
            block10: {
                object = this.b.getContext();
                packageManager = object.getPackageManager();
                boolean bl = this.f;
                Class<Integer> clazz = Integer.TYPE;
                if (!bl) {
                    this.f = true;
                    try {
                        this.c = menuItem = Class.forName("com.android.internal.view.menu.MenuBuilder");
                        this.d = menuItem.getDeclaredMethod("removeItemAt", clazz);
                        this.e = true;
                    }
                    catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
                        this.c = null;
                        this.d = null;
                        this.e = false;
                    }
                }
                try {
                    if (this.e && this.c.isInstance(menu)) {
                        Method method = this.d;
                        break block10;
                    }
                    Method method = menu.getClass().getDeclaredMethod("removeItemAt", clazz);
                }
                catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
                    return;
                }
            }
            for (n3 = menu.size() - 1; n3 >= 0; --n3) {
                void var4_9;
                menuItem = menu.getItem(n3);
                if (menuItem.getIntent() == null || !"android.intent.action.PROCESS_TEXT".equals(menuItem.getIntent().getAction())) continue;
                var4_9.invoke((Object)menu, n3);
                continue;
            }
            object = this.c((Context)object, packageManager);
            for (n3 = 0; n3 < object.size(); ++n3) {
                ResolveInfo resolveInfo = (ResolveInfo)object.get(n3);
                menu.add(0, 0, n3 + 100, resolveInfo.loadLabel(packageManager)).setIntent(this.b(resolveInfo, this.b)).setShowAsAction(1);
            }
            return;
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.a.onActionItemClicked(actionMode, menuItem);
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.a.onCreateActionMode(actionMode, menu);
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            this.a.onDestroyActionMode(actionMode);
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            this.g(menu);
            return this.a.onPrepareActionMode(actionMode, menu);
        }
    }
}

