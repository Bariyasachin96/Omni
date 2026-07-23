/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.widget.Button
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.c0;
import c.j;
import i.f;

public class ActionMenuItemView
extends AppCompatTextView
implements j.a,
View.OnClickListener,
ActionMenuView.a {
    public g j;
    public CharSequence k;
    public Drawable l;
    public e.b m;
    public c0 n;
    public b o;
    public boolean p;
    public boolean q;
    public int r;
    public int s;
    public int t;

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        Resources resources = context.getResources();
        this.p = this.t();
        context = context.obtainStyledAttributes(attributeSet, c.j.ActionMenuItemView, n3, 0);
        this.r = context.getDimensionPixelSize(c.j.ActionMenuItemView_android_minWidth, 0);
        context.recycle();
        this.t = (int)(resources.getDisplayMetrics().density * 32.0f + 0.5f);
        this.setOnClickListener(this);
        this.s = -1;
        this.setSaveEnabled(false);
    }

    @Override
    public boolean a() {
        return this.s();
    }

    @Override
    public boolean b() {
        return this.s() && this.j.getIcon() == null;
    }

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public void d(g g3, int n3) {
        this.j = g3;
        this.setIcon(g3.getIcon());
        this.setTitle(g3.i(this));
        this.setId(g3.getItemId());
        n3 = g3.isVisible() ? 0 : 8;
        this.setVisibility(n3);
        this.setEnabled(g3.isEnabled());
        if (g3.hasSubMenu() && this.n == null) {
            this.n = new a(this);
        }
    }

    public CharSequence getAccessibilityClassName() {
        return Button.class.getName();
    }

    @Override
    public g getItemData() {
        return this.j;
    }

    public void onClick(View object) {
        object = this.m;
        if (object != null) {
            object.a(this.j);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.p = this.t();
        this.u();
    }

    @Override
    public void onMeasure(int n3, int n4) {
        int n5;
        boolean bl = this.s();
        if (bl && (n5 = this.s) >= 0) {
            super.setPadding(n5, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        }
        super.onMeasure(n3, n4);
        n5 = View.MeasureSpec.getMode((int)n3);
        n3 = View.MeasureSpec.getSize((int)n3);
        int n6 = this.getMeasuredWidth();
        n3 = n5 == Integer.MIN_VALUE ? Math.min(n3, this.r) : this.r;
        if (n5 != 0x40000000 && this.r > 0 && n6 < n3) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000), n4);
        }
        if (!bl && this.l != null) {
            super.setPadding((this.getMeasuredWidth() - this.l.getBounds().width()) / 2, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        c0 c02;
        if (this.j.hasSubMenu() && (c02 = this.n) != null && c02.onTouch((View)this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean s() {
        return TextUtils.isEmpty((CharSequence)this.getText()) ^ true;
    }

    public void setCheckable(boolean bl) {
    }

    public void setChecked(boolean bl) {
    }

    public void setExpandedFormat(boolean bl) {
        if (this.q != bl) {
            this.q = bl;
            g g3 = this.j;
            if (g3 != null) {
                g3.c();
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.l = drawable;
        if (drawable != null) {
            float f3;
            int n3 = drawable.getIntrinsicWidth();
            int n4 = drawable.getIntrinsicHeight();
            int n5 = this.t;
            int n6 = n3;
            int n7 = n4;
            if (n3 > n5) {
                f3 = (float)n5 / (float)n3;
                n7 = (int)((float)n4 * f3);
                n6 = n5;
            }
            if (n7 > n5) {
                f3 = (float)n5 / (float)n7;
                n6 = (int)((float)n6 * f3);
            } else {
                n5 = n7;
            }
            drawable.setBounds(0, 0, n6, n5);
        }
        this.setCompoundDrawables(drawable, null, null, null);
        this.u();
    }

    public void setItemInvoker(e.b b3) {
        this.m = b3;
    }

    public void setPadding(int n3, int n4, int n5, int n6) {
        this.s = n3;
        super.setPadding(n3, n4, n5, n6);
    }

    public void setPopupCallback(b b3) {
        this.o = b3;
    }

    public void setShortcut(boolean bl, char c3) {
    }

    public void setTitle(CharSequence charSequence) {
        this.k = charSequence;
        this.u();
    }

    public final boolean t() {
        Configuration configuration = this.getContext().getResources().getConfiguration();
        int n3 = configuration.screenWidthDp;
        int n4 = configuration.screenHeightDp;
        return n3 >= 480 || n3 >= 640 && n4 >= 480 || configuration.orientation == 2;
        {
        }
    }

    /*
     * Exception decompiling
     */
    public final void u() {
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

    public class a
    extends c0 {
        public final ActionMenuItemView l;

        public a(ActionMenuItemView actionMenuItemView) {
            this.l = actionMenuItemView;
            super((View)actionMenuItemView);
        }

        @Override
        public f b() {
            b b3 = this.l.o;
            if (b3 != null) {
                return b3.a();
            }
            return null;
        }

        @Override
        public boolean c() {
            Object object = this.l;
            e.b b3 = ((ActionMenuItemView)object).m;
            return b3 != null && b3.a(((ActionMenuItemView)object).j) && (object = this.b()) != null && object.c();
        }
    }

    public static abstract class b {
        public abstract f a();
    }
}

