/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.a;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.t0;
import c.f;
import c.g;
import c.j;
import h.b;
import o0.x0;

public class ActionBarContextView
extends a {
    public CharSequence k;
    public CharSequence l;
    public View m;
    public View n;
    public View o;
    public LinearLayout p;
    public TextView q;
    public TextView r;
    public int s;
    public int t;
    public boolean u;
    public int v;

    public ActionBarContextView(Context context) {
        this(context, null);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, c.a.actionModeStyle);
    }

    public ActionBarContextView(Context object, AttributeSet attributeSet, int n3) {
        super((Context)object, attributeSet, n3);
        object = m0.v((Context)object, attributeSet, c.j.ActionMode, n3, 0);
        this.setBackground(((m0)object).g(c.j.ActionMode_background));
        this.s = ((m0)object).n(c.j.ActionMode_titleTextStyle, 0);
        this.t = ((m0)object).n(c.j.ActionMode_subtitleTextStyle, 0);
        this.g = ((m0)object).m(c.j.ActionMode_height, 0);
        this.v = ((m0)object).n(c.j.ActionMode_closeItemLayout, c.g.abc_action_mode_close_item_material);
        ((m0)object).x();
    }

    public void g() {
        if (this.m == null) {
            this.k();
        }
    }

    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(this.getContext(), attributeSet);
    }

    public CharSequence getSubtitle() {
        return this.l;
    }

    public CharSequence getTitle() {
        return this.k;
    }

    public void h(b object) {
        Object object2 = this.m;
        if (object2 == null) {
            object2 = LayoutInflater.from((Context)this.getContext()).inflate(this.v, (ViewGroup)this, false);
            this.m = object2;
            this.addView((View)object2);
        } else if (object2.getParent() == null) {
            this.addView(this.m);
        }
        object2 = this.m.findViewById(c.f.action_mode_close_button);
        this.n = object2;
        object2.setOnClickListener(new View.OnClickListener(this, (b)object){
            public final b c;
            public final ActionBarContextView d;
            {
                this.d = actionBarContextView;
                this.c = b3;
            }

            public void onClick(View view) {
                this.c.c();
            }
        });
        object = (e)((b)object).e();
        object2 = this.f;
        if (object2 != null) {
            ((ActionMenuPresenter)object2).B();
        }
        this.f = object2 = new ActionMenuPresenter(this.getContext());
        ((ActionMenuPresenter)object2).M(true);
        object2 = new ViewGroup.LayoutParams(-2, -1);
        ((e)object).c(this.f, this.d);
        this.e = object = (ActionMenuView)this.f.r(this);
        object.setBackground(null);
        this.addView((View)this.e, (ViewGroup.LayoutParams)object2);
    }

    public final void i() {
        int n3;
        LinearLayout linearLayout;
        block8: {
            block7: {
                if (this.p == null) {
                    LayoutInflater.from((Context)this.getContext()).inflate(c.g.abc_action_bar_title_item, (ViewGroup)this);
                    this.p = linearLayout = (LinearLayout)this.getChildAt(this.getChildCount() - 1);
                    this.q = (TextView)linearLayout.findViewById(c.f.action_bar_title);
                    this.r = (TextView)this.p.findViewById(c.f.action_bar_subtitle);
                    if (this.s != 0) {
                        this.q.setTextAppearance(this.getContext(), this.s);
                    }
                    if (this.t != 0) {
                        this.r.setTextAppearance(this.getContext(), this.t);
                    }
                }
                this.q.setText(this.k);
                this.r.setText(this.l);
                boolean bl = TextUtils.isEmpty((CharSequence)this.k);
                boolean bl2 = TextUtils.isEmpty((CharSequence)this.l);
                linearLayout = this.r;
                int n4 = 8;
                n3 = !bl2 ? 0 : 8;
                linearLayout.setVisibility(n3);
                linearLayout = this.p;
                if (!bl) break block7;
                n3 = n4;
                if (bl2) break block8;
            }
            n3 = 0;
        }
        linearLayout.setVisibility(n3);
        if (this.p.getParent() == null) {
            this.addView((View)this.p);
        }
    }

    public boolean j() {
        return this.u;
    }

    public void k() {
        this.removeAllViews();
        this.o = null;
        this.e = null;
        this.f = null;
        View view = this.n;
        if (view != null) {
            view.setOnClickListener(null);
        }
    }

    public boolean l() {
        ActionMenuPresenter actionMenuPresenter = this.f;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.N();
        }
        return false;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.f;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.E();
            this.f.F();
        }
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        bl = t0.b((View)this);
        int n7 = bl ? n5 - n3 - this.getPaddingRight() : this.getPaddingLeft();
        int n8 = this.getPaddingTop();
        int n9 = n6 - n4 - this.getPaddingTop() - this.getPaddingBottom();
        Object object = this.m;
        n4 = n7;
        if (object != null) {
            n4 = n7;
            if (object.getVisibility() != 8) {
                object = (ViewGroup.MarginLayoutParams)this.m.getLayoutParams();
                n4 = bl ? object.rightMargin : object.leftMargin;
                n6 = bl ? object.leftMargin : object.rightMargin;
                n4 = a.d(n7, n4, bl);
                n4 = a.d(n4 + this.e(this.m, n4, n8, n9, bl), n6, bl);
            }
        }
        object = this.p;
        n6 = n4;
        if (object != null) {
            n6 = n4;
            if (this.o == null) {
                n6 = n4;
                if (object.getVisibility() != 8) {
                    n6 = n4 + this.e((View)this.p, n4, n8, n9, bl);
                }
            }
        }
        if ((object = this.o) != null) {
            this.e((View)object, n6, n8, n9, bl);
        }
        n3 = bl ? this.getPaddingLeft() : n5 - n3 - this.getPaddingRight();
        object = this.e;
        if (object != null) {
            this.e((View)object, n3, n8, n9, bl ^ true);
        }
    }

    public void onMeasure(int n3, int n4) {
        int n5 = View.MeasureSpec.getMode((int)n3);
        int n6 = 0x40000000;
        if (n5 == 0x40000000) {
            if (View.MeasureSpec.getMode((int)n4) != 0) {
                int n7;
                int n8 = View.MeasureSpec.getSize((int)n3);
                n5 = this.g;
                if (n5 <= 0) {
                    n5 = View.MeasureSpec.getSize((int)n4);
                }
                int n9 = this.getPaddingTop() + this.getPaddingBottom();
                n3 = n8 - this.getPaddingLeft() - this.getPaddingRight();
                int n10 = n5 - n9;
                int n11 = View.MeasureSpec.makeMeasureSpec((int)n10, (int)Integer.MIN_VALUE);
                Object object = this.m;
                int n12 = 0;
                n4 = n3;
                if (object != null) {
                    n3 = this.c((View)object, n3, n11, 0);
                    object = (ViewGroup.MarginLayoutParams)this.m.getLayoutParams();
                    n4 = n3 - (object.leftMargin + object.rightMargin);
                }
                object = this.e;
                n3 = n4;
                if (object != null) {
                    n3 = n4;
                    if (object.getParent() == this) {
                        n3 = this.c((View)this.e, n4, n11, 0);
                    }
                }
                object = this.p;
                n4 = n3;
                if (object != null) {
                    n4 = n3;
                    if (this.o == null) {
                        if (this.u) {
                            n4 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                            this.p.measure(n4, n11);
                            n7 = this.p.getMeasuredWidth();
                            n11 = n7 <= n3 ? 1 : 0;
                            n4 = n3;
                            if (n11 != 0) {
                                n4 = n3 - n7;
                            }
                            object = this.p;
                            n3 = n11 != 0 ? 0 : 8;
                            object.setVisibility(n3);
                        } else {
                            n4 = this.c((View)object, n3, n11, 0);
                        }
                    }
                }
                if ((object = this.o) != null) {
                    object = object.getLayoutParams();
                    n7 = object.width;
                    n3 = n7 != -2 ? 0x40000000 : Integer.MIN_VALUE;
                    n11 = n4;
                    if (n7 >= 0) {
                        n11 = Math.min(n7, n4);
                    }
                    n4 = (n7 = object.height) != -2 ? n6 : Integer.MIN_VALUE;
                    n6 = n10;
                    if (n7 >= 0) {
                        n6 = Math.min(n7, n10);
                    }
                    this.o.measure(View.MeasureSpec.makeMeasureSpec((int)n11, (int)n3), View.MeasureSpec.makeMeasureSpec((int)n6, (int)n4));
                }
                if (this.g <= 0) {
                    n6 = this.getChildCount();
                    n5 = 0;
                    for (n3 = n12; n3 < n6; ++n3) {
                        n11 = this.getChildAt(n3).getMeasuredHeight() + n9;
                        n4 = n5;
                        if (n11 > n5) {
                            n4 = n11;
                        }
                        n5 = n4;
                    }
                    this.setMeasuredDimension(n8, n5);
                    return;
                }
                this.setMeasuredDimension(n8, n5);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((Object)((Object)this)).getClass().getSimpleName());
            stringBuilder.append(" can only be used with android:layout_height=\"wrap_content\"");
            throw new IllegalStateException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((Object)((Object)this)).getClass().getSimpleName());
        stringBuilder.append(" can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public void setContentHeight(int n3) {
        this.g = n3;
    }

    public void setCustomView(View view) {
        View view2 = this.o;
        if (view2 != null) {
            this.removeView(view2);
        }
        this.o = view;
        if (view != null && (view2 = this.p) != null) {
            this.removeView(view2);
            this.p = null;
        }
        if (view != null) {
            this.addView(view);
        }
        this.requestLayout();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.l = charSequence;
        this.i();
    }

    public void setTitle(CharSequence charSequence) {
        this.k = charSequence;
        this.i();
        x0.j0((View)this, charSequence);
    }

    public void setTitleOptional(boolean bl) {
        if (bl != this.u) {
            this.requestLayout();
        }
        this.u = bl;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}

