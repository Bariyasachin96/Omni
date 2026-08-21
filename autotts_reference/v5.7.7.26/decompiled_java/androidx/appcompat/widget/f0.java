/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.AssetFileDescriptor
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Movie
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package androidx.appcompat.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import f0.h;
import java.io.InputStream;

public abstract class f0
extends Resources {
    public final Resources a;

    public f0(Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.a = resources;
    }

    public final Drawable a(int n3) {
        return super.getDrawable(n3);
    }

    public XmlResourceParser getAnimation(int n3) {
        return this.a.getAnimation(n3);
    }

    public boolean getBoolean(int n3) {
        return this.a.getBoolean(n3);
    }

    public int getColor(int n3) {
        return this.a.getColor(n3);
    }

    public ColorStateList getColorStateList(int n3) {
        return this.a.getColorStateList(n3);
    }

    public Configuration getConfiguration() {
        return this.a.getConfiguration();
    }

    public float getDimension(int n3) {
        return this.a.getDimension(n3);
    }

    public int getDimensionPixelOffset(int n3) {
        return this.a.getDimensionPixelOffset(n3);
    }

    public int getDimensionPixelSize(int n3) {
        return this.a.getDimensionPixelSize(n3);
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.a.getDisplayMetrics();
    }

    public Drawable getDrawable(int n3, Resources.Theme theme) {
        return h.e(this.a, n3, theme);
    }

    public Drawable getDrawableForDensity(int n3, int n4) {
        return h.f(this.a, n3, n4, null);
    }

    public Drawable getDrawableForDensity(int n3, int n4, Resources.Theme theme) {
        return h.f(this.a, n3, n4, theme);
    }

    public float getFraction(int n3, int n4, int n5) {
        return this.a.getFraction(n3, n4, n5);
    }

    public int getIdentifier(String string, String string2, String string3) {
        return this.a.getIdentifier(string, string2, string3);
    }

    public int[] getIntArray(int n3) {
        return this.a.getIntArray(n3);
    }

    public int getInteger(int n3) {
        return this.a.getInteger(n3);
    }

    public XmlResourceParser getLayout(int n3) {
        return this.a.getLayout(n3);
    }

    public Movie getMovie(int n3) {
        return this.a.getMovie(n3);
    }

    public String getQuantityString(int n3, int n4) {
        return this.a.getQuantityString(n3, n4);
    }

    public String getQuantityString(int n3, int n4, Object ... objectArray) {
        return this.a.getQuantityString(n3, n4, objectArray);
    }

    public CharSequence getQuantityText(int n3, int n4) {
        return this.a.getQuantityText(n3, n4);
    }

    public String getResourceEntryName(int n3) {
        return this.a.getResourceEntryName(n3);
    }

    public String getResourceName(int n3) {
        return this.a.getResourceName(n3);
    }

    public String getResourcePackageName(int n3) {
        return this.a.getResourcePackageName(n3);
    }

    public String getResourceTypeName(int n3) {
        return this.a.getResourceTypeName(n3);
    }

    public String getString(int n3) {
        return this.a.getString(n3);
    }

    public String getString(int n3, Object ... objectArray) {
        return this.a.getString(n3, objectArray);
    }

    public String[] getStringArray(int n3) {
        return this.a.getStringArray(n3);
    }

    public CharSequence getText(int n3) {
        return this.a.getText(n3);
    }

    public CharSequence getText(int n3, CharSequence charSequence) {
        return this.a.getText(n3, charSequence);
    }

    public CharSequence[] getTextArray(int n3) {
        return this.a.getTextArray(n3);
    }

    public void getValue(int n3, TypedValue typedValue, boolean bl) {
        this.a.getValue(n3, typedValue, bl);
    }

    public void getValue(String string, TypedValue typedValue, boolean bl) {
        this.a.getValue(string, typedValue, bl);
    }

    public void getValueForDensity(int n3, int n4, TypedValue typedValue, boolean bl) {
        this.a.getValueForDensity(n3, n4, typedValue, bl);
    }

    public XmlResourceParser getXml(int n3) {
        return this.a.getXml(n3);
    }

    public TypedArray obtainAttributes(AttributeSet attributeSet, int[] nArray) {
        return this.a.obtainAttributes(attributeSet, nArray);
    }

    public TypedArray obtainTypedArray(int n3) {
        return this.a.obtainTypedArray(n3);
    }

    public InputStream openRawResource(int n3) {
        return this.a.openRawResource(n3);
    }

    public InputStream openRawResource(int n3, TypedValue typedValue) {
        return this.a.openRawResource(n3, typedValue);
    }

    public AssetFileDescriptor openRawResourceFd(int n3) {
        return this.a.openRawResourceFd(n3);
    }

    public void parseBundleExtra(String string, AttributeSet attributeSet, Bundle bundle) {
        this.a.parseBundleExtra(string, attributeSet, bundle);
    }

    public void parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) {
        this.a.parseBundleExtras(xmlResourceParser, bundle);
    }

    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        super.updateConfiguration(configuration, displayMetrics);
        Resources resources = this.a;
        if (resources != null) {
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }
}

