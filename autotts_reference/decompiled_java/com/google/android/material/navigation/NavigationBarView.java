/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.view.menu.j;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarPresenter;
import com.google.android.material.navigation.e;
import h.g;
import v2.o;

public abstract class NavigationBarView
extends FrameLayout {
    public final e c;
    public final NavigationBarMenuView d;
    public final NavigationBarPresenter e;
    public MenuInflater f;

    /*
     * Exception decompiling
     */
    public NavigationBarView(Context var1_1, AttributeSet var2_2, int var3_3, int var4_4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:461)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:251)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:673)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:56)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:722)
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

    public static /* synthetic */ b a(NavigationBarView navigationBarView) {
        ((Object)((Object)navigationBarView)).getClass();
        return null;
    }

    public static /* synthetic */ c b(NavigationBarView navigationBarView) {
        ((Object)((Object)navigationBarView)).getClass();
        return null;
    }

    private MenuInflater getMenuInflater() {
        if (this.f == null) {
            this.f = new g(this.getContext());
        }
        return this.f;
    }

    private void setMeasureBottomPaddingFromLabelBaseline(boolean bl) {
        this.d.setMeasurePaddingFromLabelBaseline(bl);
    }

    public abstract NavigationBarMenuView c(Context var1);

    public void d(int n3) {
        this.e.h(true);
        this.getMenuInflater().inflate(n3, (Menu)this.c);
        this.e.h(false);
        this.e.g(true);
    }

    public boolean e() {
        return false;
    }

    public boolean f() {
        return false;
    }

    public int getActiveIndicatorLabelPadding() {
        return this.d.getActiveIndicatorLabelPadding();
    }

    public int getCollapsedMaxItemCount() {
        return this.getMaxItemCount();
    }

    public int getHorizontalItemTextAppearanceActive() {
        return this.d.getHorizontalItemTextAppearanceActive();
    }

    public int getHorizontalItemTextAppearanceInactive() {
        return this.d.getHorizontalItemTextAppearanceInactive();
    }

    public int getIconLabelHorizontalSpacing() {
        return this.d.getIconLabelHorizontalSpacing();
    }

    public ColorStateList getItemActiveIndicatorColor() {
        return this.d.getItemActiveIndicatorColor();
    }

    public int getItemActiveIndicatorExpandedHeight() {
        return this.d.getItemActiveIndicatorExpandedHeight();
    }

    public int getItemActiveIndicatorExpandedMarginHorizontal() {
        return this.d.getItemActiveIndicatorExpandedMarginHorizontal();
    }

    public int getItemActiveIndicatorExpandedWidth() {
        return this.d.getItemActiveIndicatorExpandedWidth();
    }

    public int getItemActiveIndicatorHeight() {
        return this.d.getItemActiveIndicatorHeight();
    }

    public int getItemActiveIndicatorMarginHorizontal() {
        return this.d.getItemActiveIndicatorMarginHorizontal();
    }

    public o getItemActiveIndicatorShapeAppearance() {
        return this.d.getItemActiveIndicatorShapeAppearance();
    }

    public int getItemActiveIndicatorWidth() {
        return this.d.getItemActiveIndicatorWidth();
    }

    public Drawable getItemBackground() {
        return this.d.getItemBackground();
    }

    @Deprecated
    public int getItemBackgroundResource() {
        return this.d.getItemBackgroundRes();
    }

    public int getItemGravity() {
        return this.d.getItemGravity();
    }

    public int getItemIconGravity() {
        return this.d.getItemIconGravity();
    }

    public int getItemIconSize() {
        return this.d.getItemIconSize();
    }

    public ColorStateList getItemIconTintList() {
        return this.d.getIconTintList();
    }

    public int getItemPaddingBottom() {
        return this.d.getItemPaddingBottom();
    }

    public int getItemPaddingTop() {
        return this.d.getItemPaddingTop();
    }

    public ColorStateList getItemRippleColor() {
        return this.d.getItemRippleColor();
    }

    public int getItemTextAppearanceActive() {
        return this.d.getItemTextAppearanceActive();
    }

    public int getItemTextAppearanceInactive() {
        return this.d.getItemTextAppearanceInactive();
    }

    public ColorStateList getItemTextColor() {
        return this.d.getItemTextColor();
    }

    public int getLabelVisibilityMode() {
        return this.d.getLabelVisibilityMode();
    }

    public abstract int getMaxItemCount();

    public Menu getMenu() {
        return this.c;
    }

    public j getMenuView() {
        return this.d;
    }

    public ViewGroup getMenuViewGroup() {
        return this.d;
    }

    public NavigationBarPresenter getPresenter() {
        return this.e;
    }

    public boolean getScaleLabelTextWithFont() {
        return this.d.getScaleLabelTextWithFont();
    }

    public int getSelectedItemId() {
        return this.d.getSelectedItemId();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v2.j.e((View)this);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.c.T(parcelable.e);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = bundle = new Bundle();
        this.c.V(bundle);
        return savedState;
    }

    public void setActiveIndicatorLabelPadding(int n3) {
        this.d.setActiveIndicatorLabelPadding(n3);
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        v2.j.d((View)this, f3);
    }

    public void setHorizontalItemTextAppearanceActive(int n3) {
        this.d.setHorizontalItemTextAppearanceActive(n3);
    }

    public void setHorizontalItemTextAppearanceInactive(int n3) {
        this.d.setHorizontalItemTextAppearanceInactive(n3);
    }

    public void setIconLabelHorizontalSpacing(int n3) {
        this.d.setIconLabelHorizontalSpacing(n3);
    }

    public void setItemActiveIndicatorColor(ColorStateList colorStateList) {
        this.d.setItemActiveIndicatorColor(colorStateList);
    }

    public void setItemActiveIndicatorEnabled(boolean bl) {
        this.d.setItemActiveIndicatorEnabled(bl);
    }

    public void setItemActiveIndicatorExpandedHeight(int n3) {
        this.d.setItemActiveIndicatorExpandedHeight(n3);
    }

    public void setItemActiveIndicatorExpandedMarginHorizontal(int n3) {
        this.d.setItemActiveIndicatorExpandedMarginHorizontal(n3);
    }

    public void setItemActiveIndicatorExpandedPadding(int n3, int n4, int n5, int n6) {
        this.d.setItemActiveIndicatorExpandedPadding(n3, n4, n5, n6);
    }

    public void setItemActiveIndicatorExpandedWidth(int n3) {
        this.d.setItemActiveIndicatorExpandedWidth(n3);
    }

    public void setItemActiveIndicatorHeight(int n3) {
        this.d.setItemActiveIndicatorHeight(n3);
    }

    public void setItemActiveIndicatorMarginHorizontal(int n3) {
        this.d.setItemActiveIndicatorMarginHorizontal(n3);
    }

    public void setItemActiveIndicatorShapeAppearance(o o3) {
        this.d.setItemActiveIndicatorShapeAppearance(o3);
    }

    public void setItemActiveIndicatorWidth(int n3) {
        this.d.setItemActiveIndicatorWidth(n3);
    }

    public void setItemBackground(Drawable drawable) {
        this.d.setItemBackground(drawable);
    }

    public void setItemBackgroundResource(int n3) {
        this.d.setItemBackgroundRes(n3);
    }

    public void setItemGravity(int n3) {
        if (this.d.getItemGravity() != n3) {
            this.d.setItemGravity(n3);
            this.e.g(false);
        }
    }

    public void setItemIconGravity(int n3) {
        if (this.d.getItemIconGravity() != n3) {
            this.d.setItemIconGravity(n3);
            this.e.g(false);
        }
    }

    public void setItemIconSize(int n3) {
        this.d.setItemIconSize(n3);
    }

    public void setItemIconSizeRes(int n3) {
        this.setItemIconSize(this.getResources().getDimensionPixelSize(n3));
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        this.d.setIconTintList(colorStateList);
    }

    public void setItemOnTouchListener(int n3, View.OnTouchListener onTouchListener) {
        this.d.setItemOnTouchListener(n3, onTouchListener);
    }

    public void setItemPaddingBottom(int n3) {
        this.d.setItemPaddingBottom(n3);
    }

    public void setItemPaddingTop(int n3) {
        this.d.setItemPaddingTop(n3);
    }

    public void setItemRippleColor(ColorStateList colorStateList) {
        this.d.setItemRippleColor(colorStateList);
    }

    public void setItemTextAppearanceActive(int n3) {
        this.d.setItemTextAppearanceActive(n3);
    }

    public void setItemTextAppearanceActiveBoldEnabled(boolean bl) {
        this.d.setItemTextAppearanceActiveBoldEnabled(bl);
    }

    public void setItemTextAppearanceInactive(int n3) {
        this.d.setItemTextAppearanceInactive(n3);
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.d.setItemTextColor(colorStateList);
    }

    public void setLabelFontScalingEnabled(boolean bl) {
        this.d.setLabelFontScalingEnabled(bl);
    }

    public void setLabelMaxLines(int n3) {
        this.d.setLabelMaxLines(n3);
    }

    public void setLabelVisibilityMode(int n3) {
        if (this.d.getLabelVisibilityMode() != n3) {
            this.d.setLabelVisibilityMode(n3);
            this.e.g(false);
        }
    }

    public void setOnItemReselectedListener(b b3) {
    }

    public void setOnItemSelectedListener(c c3) {
    }

    public void setSelectedItemId(int n3) {
        MenuItem menuItem = this.c.findItem(n3);
        if (menuItem != null) {
            boolean bl = this.c.P(menuItem, this.e, 0);
            if (menuItem.isCheckable() && (!bl || menuItem.isChecked())) {
                this.d.setCheckedItem(menuItem);
            }
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
        public Bundle e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            ClassLoader classLoader2 = classLoader;
            if (classLoader == null) {
                classLoader2 = this.getClass().getClassLoader();
            }
            this.p(parcel, classLoader2);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final void p(Parcel parcel, ClassLoader classLoader) {
            this.e = parcel.readBundle(classLoader);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeBundle(this.e);
        }
    }

    public static interface b {
    }

    public static interface c {
    }
}

