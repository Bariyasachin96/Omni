/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView$SelectionBoundsAdjuster
 *  android.widget.CheckBox
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.RadioButton
 *  android.widget.TextView
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.widget.m0;
import c.a;
import c.f;
import c.j;

public class ListMenuItemView
extends LinearLayout
implements j.a,
AbsListView.SelectionBoundsAdjuster {
    public g c;
    public ImageView d;
    public RadioButton e;
    public TextView f;
    public CheckBox g;
    public TextView h;
    public ImageView i;
    public ImageView j;
    public LinearLayout k;
    public Drawable l;
    public int m;
    public Context n;
    public boolean o;
    public Drawable p;
    public boolean q;
    public LayoutInflater r;
    public boolean s;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.listMenuViewStyle);
    }

    public ListMenuItemView(Context context, AttributeSet object, int n3) {
        super(context, (AttributeSet)object);
        object = m0.v(this.getContext(), (AttributeSet)object, c.j.MenuView, n3, 0);
        this.l = ((m0)object).g(c.j.MenuView_android_itemBackground);
        this.m = ((m0)object).n(c.j.MenuView_android_itemTextAppearance, -1);
        this.o = ((m0)object).a(c.j.MenuView_preserveIconSpacing, false);
        this.n = context;
        this.p = ((m0)object).g(c.j.MenuView_subMenuArrow);
        context = context.getTheme();
        n3 = a.dropDownListViewStyle;
        context = context.obtainStyledAttributes(null, new int[]{16843049}, n3, 0);
        this.q = context.hasValue(0);
        ((m0)object).x();
        context.recycle();
    }

    private LayoutInflater getInflater() {
        if (this.r == null) {
            this.r = LayoutInflater.from((Context)this.getContext());
        }
        return this.r;
    }

    private void setSubMenuArrowVisible(boolean bl) {
        ImageView imageView = this.i;
        if (imageView != null) {
            int n3 = bl ? 0 : 8;
            imageView.setVisibility(n3);
        }
    }

    public final void a(View view) {
        this.b(view, -1);
    }

    public void adjustListItemSelectionBounds(Rect rect) {
        ImageView imageView = this.j;
        if (imageView != null && imageView.getVisibility() == 0) {
            imageView = (LinearLayout.LayoutParams)this.j.getLayoutParams();
            rect.top += this.j.getHeight() + imageView.topMargin + imageView.bottomMargin;
        }
    }

    public final void b(View view, int n3) {
        LinearLayout linearLayout = this.k;
        if (linearLayout != null) {
            linearLayout.addView(view, n3);
            return;
        }
        this.addView(view, n3);
    }

    @Override
    public boolean c() {
        return false;
    }

    @Override
    public void d(g g3, int n3) {
        this.c = g3;
        n3 = g3.isVisible() ? 0 : 8;
        this.setVisibility(n3);
        this.setTitle(g3.i(this));
        this.setCheckable(g3.isCheckable());
        this.setShortcut(g3.A(), g3.g());
        this.setIcon(g3.getIcon());
        this.setEnabled(g3.isEnabled());
        this.setSubMenuArrowVisible(g3.hasSubMenu());
        this.setContentDescription(g3.getContentDescription());
    }

    public final void e() {
        CheckBox checkBox;
        this.g = checkBox = (CheckBox)this.getInflater().inflate(c.g.abc_list_menu_item_checkbox, (ViewGroup)this, false);
        this.a((View)checkBox);
    }

    public final void f() {
        ImageView imageView;
        this.d = imageView = (ImageView)this.getInflater().inflate(c.g.abc_list_menu_item_icon, (ViewGroup)this, false);
        this.b((View)imageView, 0);
    }

    public final void g() {
        RadioButton radioButton;
        this.e = radioButton = (RadioButton)this.getInflater().inflate(c.g.abc_list_menu_item_radio, (ViewGroup)this, false);
        this.a((View)radioButton);
    }

    @Override
    public g getItemData() {
        return this.c;
    }

    public void onFinishInflate() {
        TextView textView;
        super.onFinishInflate();
        this.setBackground(this.l);
        this.f = textView = (TextView)this.findViewById(c.f.title);
        int n3 = this.m;
        if (n3 != -1) {
            textView.setTextAppearance(this.n, n3);
        }
        this.h = (TextView)this.findViewById(c.f.shortcut);
        textView = (ImageView)this.findViewById(c.f.submenuarrow);
        this.i = textView;
        if (textView != null) {
            textView.setImageDrawable(this.p);
        }
        this.j = (ImageView)this.findViewById(c.f.group_divider);
        this.k = (LinearLayout)this.findViewById(c.f.content);
    }

    public void onMeasure(int n3, int n4) {
        if (this.d != null && this.o) {
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams)this.d.getLayoutParams();
            int n5 = layoutParams.height;
            if (n5 > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = n5;
            }
        }
        super.onMeasure(n3, n4);
    }

    public void setCheckable(boolean bl) {
        if (bl || this.e != null || this.g != null) {
            CheckBox checkBox;
            RadioButton radioButton;
            if (this.c.m()) {
                if (this.e == null) {
                    this.g();
                }
                radioButton = this.e;
                checkBox = this.g;
            } else {
                if (this.g == null) {
                    this.e();
                }
                radioButton = this.g;
                checkBox = this.e;
            }
            if (bl) {
                radioButton.setChecked(this.c.isChecked());
                if (radioButton.getVisibility() != 0) {
                    radioButton.setVisibility(0);
                }
                if (checkBox != null && checkBox.getVisibility() != 8) {
                    checkBox.setVisibility(8);
                    return;
                }
            } else {
                checkBox = this.g;
                if (checkBox != null) {
                    checkBox.setVisibility(8);
                }
                if ((checkBox = this.e) != null) {
                    checkBox.setVisibility(8);
                }
            }
        }
    }

    public void setChecked(boolean bl) {
        RadioButton radioButton;
        if (this.c.m()) {
            if (this.e == null) {
                this.g();
            }
            radioButton = this.e;
        } else {
            if (this.g == null) {
                this.e();
            }
            radioButton = this.g;
        }
        radioButton.setChecked(bl);
    }

    public void setForceShowIcon(boolean bl) {
        this.s = bl;
        this.o = bl;
    }

    public void setGroupDividerEnabled(boolean bl) {
        ImageView imageView = this.j;
        if (imageView != null) {
            int n3 = !this.q && bl ? 0 : 8;
            imageView.setVisibility(n3);
        }
    }

    public void setIcon(Drawable drawable) {
        ImageView imageView;
        boolean bl = this.c.z() || this.s;
        if ((bl || this.o) && ((imageView = this.d) != null || drawable != null || this.o)) {
            if (imageView == null) {
                this.f();
            }
            if (drawable == null && !this.o) {
                this.d.setVisibility(8);
                return;
            }
            imageView = this.d;
            if (!bl) {
                drawable = null;
            }
            imageView.setImageDrawable(drawable);
            if (this.d.getVisibility() != 0) {
                this.d.setVisibility(0);
            }
        }
    }

    public void setShortcut(boolean bl, char c3) {
        c3 = bl && this.c.A() ? (char)'\u0000' : (char)8;
        if (c3 == '\u0000') {
            this.h.setText((CharSequence)this.c.h());
        }
        if (this.h.getVisibility() != c3) {
            this.h.setVisibility((int)c3);
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence != null) {
            this.f.setText(charSequence);
            if (this.f.getVisibility() != 0) {
                this.f.setVisibility(0);
                return;
            }
        } else if (this.f.getVisibility() != 8) {
            this.f.setVisibility(8);
        }
    }
}

