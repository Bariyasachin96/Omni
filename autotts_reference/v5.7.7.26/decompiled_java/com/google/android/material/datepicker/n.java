/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.widget.Button
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.c;
import androidx.fragment.app.y;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.DayViewDecorator;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.j;
import com.google.android.material.datepicker.k;
import com.google.android.material.datepicker.l;
import com.google.android.material.datepicker.m;
import com.google.android.material.datepicker.o;
import com.google.android.material.datepicker.r;
import com.google.android.material.datepicker.s;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.e;
import i2.a;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import o0.f0;
import o0.x0;
import o0.z1;
import s2.b;
import v2.i;
import z1.f;
import z1.g;

public class n<S>
extends c {
    public static final Object Y0 = "CONFIRM_BUTTON_TAG";
    public static final Object Z0 = "CANCEL_BUTTON_TAG";
    public static final Object a1 = "TOGGLE_BUTTON_TAG";
    public s A0;
    public CalendarConstraints B0;
    public DayViewDecorator C0;
    public j D0;
    public int E0;
    public CharSequence F0;
    public boolean G0;
    public int H0;
    public int I0;
    public CharSequence J0;
    public int K0;
    public CharSequence L0;
    public int M0;
    public CharSequence N0;
    public int O0;
    public CharSequence P0;
    public TextView Q0;
    public TextView R0;
    public CheckableImageButton S0;
    public i T0;
    public Button U0;
    public boolean V0;
    public CharSequence W0;
    public CharSequence X0;
    public final LinkedHashSet u0 = new LinkedHashSet();
    public final LinkedHashSet v0 = new LinkedHashSet();
    public final LinkedHashSet w0 = new LinkedHashSet();
    public final LinkedHashSet x0 = new LinkedHashSet();
    public int y0;
    public DateSelector z0;

    public static /* synthetic */ void R1(n n3, View view) {
        n3.U0.setEnabled(n3.W1().h());
        n3.S0.toggle();
        int n4 = n3.H0;
        int n5 = 1;
        if (n4 == 1) {
            n5 = 0;
        }
        n3.H0 = n5;
        n3.n2(n3.S0);
        n3.k2();
    }

    public static Drawable U1(Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable drawable = d.a.b(context, z1.f.material_ic_calendar_black_24dp);
        stateListDrawable.addState(new int[]{0x10100A0}, drawable);
        context = d.a.b(context, z1.f.material_ic_edit_black_24dp);
        stateListDrawable.addState(new int[0], (Drawable)context);
        return stateListDrawable;
    }

    private DateSelector W1() {
        if (this.z0 == null) {
            this.z0 = (DateSelector)this.n().getParcelable("DATE_SELECTOR_KEY");
        }
        return this.z0;
    }

    public static CharSequence X1(CharSequence charSequence) {
        if (charSequence != null) {
            String[] stringArray = TextUtils.split((String)String.valueOf(charSequence), (String)"\n");
            if (stringArray.length > 1) {
                charSequence = stringArray[0];
            }
            return charSequence;
        }
        return null;
    }

    public static int a2(Context context) {
        context = context.getResources();
        int n3 = context.getDimensionPixelOffset(z1.e.mtrl_calendar_content_padding);
        int n4 = Month.r().f;
        return n3 * 2 + context.getDimensionPixelSize(z1.e.mtrl_calendar_day_width) * n4 + (n4 - 1) * context.getDimensionPixelOffset(z1.e.mtrl_calendar_month_horizontal_padding);
    }

    public static boolean e2(Context context) {
        return com.google.android.material.datepicker.n.j2(context, 16843277);
    }

    public static boolean g2(Context context) {
        return com.google.android.material.datepicker.n.j2(context, z1.c.nestedScrollable);
    }

    public static boolean j2(Context context, int n3) {
        context = context.obtainStyledAttributes(b.f(context, z1.c.materialCalendarStyle, j.class.getCanonicalName()), new int[]{n3});
        boolean bl = context.getBoolean(0, false);
        context.recycle();
        return bl;
    }

    @Override
    public final void G0(Bundle bundle) {
        super.G0(bundle);
        bundle.putInt("OVERRIDE_THEME_RES_ID", this.y0);
        bundle.putParcelable("DATE_SELECTOR_KEY", (Parcelable)this.z0);
        CalendarConstraints.b b3 = new CalendarConstraints.b(this.B0);
        Object object = this.D0;
        object = object == null ? null : ((j)object).T1();
        if (object != null) {
            b3.b(((Month)object).h);
        }
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", (Parcelable)b3.a());
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", (Parcelable)this.C0);
        bundle.putInt("TITLE_TEXT_RES_ID_KEY", this.E0);
        bundle.putCharSequence("TITLE_TEXT_KEY", this.F0);
        bundle.putInt("INPUT_MODE_KEY", this.H0);
        bundle.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", this.I0);
        bundle.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", this.J0);
        bundle.putInt("POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY", this.K0);
        bundle.putCharSequence("POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY", this.L0);
        bundle.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", this.M0);
        bundle.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", this.N0);
        bundle.putInt("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY", this.O0);
        bundle.putCharSequence("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY", this.P0);
    }

    @Override
    public void H0() {
        super.H0();
        Window window = this.P1().getWindow();
        if (this.G0) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable((Drawable)this.T0);
            this.V1(window);
        } else {
            window.setLayout(-2, -2);
            int n3 = this.K().getDimensionPixelOffset(z1.e.mtrl_calendar_dialog_background_inset);
            Rect rect = new Rect(n3, n3, n3, n3);
            window.setBackgroundDrawable((Drawable)new InsetDrawable((Drawable)this.T0, n3, n3, n3, n3));
            window.getDecorView().setOnTouchListener((View.OnTouchListener)new a(this.P1(), rect));
        }
        this.k2();
    }

    @Override
    public void I0() {
        this.A0.F1();
        super.I0();
    }

    @Override
    public final Dialog L1(Bundle bundle) {
        bundle = new Dialog(this.n1(), this.c2(this.n1()));
        Context context = bundle.getContext();
        this.G0 = com.google.android.material.datepicker.n.e2(context);
        int n3 = z1.c.materialCalendarStyle;
        int n4 = z1.l.Widget_MaterialComponents_MaterialCalendar;
        this.T0 = new i(context, null, n3, n4);
        TypedArray typedArray = context.obtainStyledAttributes(null, z1.m.MaterialCalendar, n3, n4);
        n3 = typedArray.getColor(z1.m.MaterialCalendar_backgroundTint, 0);
        typedArray.recycle();
        this.T0.W(context);
        this.T0.i0(ColorStateList.valueOf((int)n3));
        this.T0.h0(bundle.getWindow().getDecorView().getElevation());
        return bundle;
    }

    public final void V1(Window window) {
        if (this.V0) {
            return;
        }
        View view = this.o1().findViewById(z1.g.fullscreen_header);
        com.google.android.material.internal.e.a(window, true, com.google.android.material.internal.c0.h(view), null);
        int n3 = view.getPaddingTop();
        int n4 = view.getPaddingLeft();
        int n5 = view.getPaddingRight();
        o0.x0.r0(view, new f0(this, view.getLayoutParams().height, view, n4, n3, n5){
            public final int a;
            public final View b;
            public final int c;
            public final int d;
            public final int e;
            public final n f;
            {
                this.f = n3;
                this.a = n4;
                this.b = view;
                this.c = n5;
                this.d = n6;
                this.e = n7;
            }

            @Override
            public z1 a(View object, z1 z12) {
                View view;
                object = z12.f(z1.m.e());
                if (this.a >= 0) {
                    this.b.getLayoutParams().height = this.a + object.b;
                    view = this.b;
                    view.setLayoutParams(view.getLayoutParams());
                }
                view = this.b;
                view.setPadding(this.c + object.a, this.d + object.b, this.e + object.c, view.getPaddingBottom());
                return z12;
            }
        });
        this.V0 = true;
    }

    public final String Y1() {
        return this.W1().b(this.n1());
    }

    public String Z1() {
        return this.W1().d(this.p());
    }

    public final Object b2() {
        return this.W1().j();
    }

    public final int c2(Context context) {
        int n3 = this.y0;
        if (n3 != 0) {
            return n3;
        }
        return this.W1().e(context);
    }

    public final void d2(Context object) {
        this.S0.setTag(a1);
        this.S0.setImageDrawable(com.google.android.material.datepicker.n.U1((Context)object));
        object = this.S0;
        boolean bl = this.H0 != 0;
        ((CheckableImageButton)((Object)object)).setChecked(bl);
        o0.x0.h0((View)this.S0, null);
        this.n2(this.S0);
        this.S0.setOnClickListener(new m(this));
    }

    public final boolean f2() {
        return this.K().getConfiguration().orientation == 2;
    }

    public void h2(View view) {
        Iterator iterator = ((AbstractCollection)this.v0).iterator();
        while (iterator.hasNext()) {
            ((View.OnClickListener)iterator.next()).onClick(view);
        }
        this.H1();
    }

    public void i2(View object) {
        object = ((AbstractCollection)this.u0).iterator();
        if (!object.hasNext()) {
            this.H1();
            return;
        }
        androidx.appcompat.app.s.a(object.next());
        this.b2();
        throw null;
    }

    @Override
    public final void k0(Bundle object) {
        super.k0((Bundle)object);
        Bundle bundle = object;
        if (object == null) {
            bundle = this.n();
        }
        this.y0 = bundle.getInt("OVERRIDE_THEME_RES_ID");
        this.z0 = (DateSelector)bundle.getParcelable("DATE_SELECTOR_KEY");
        this.B0 = (CalendarConstraints)bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.C0 = (DayViewDecorator)bundle.getParcelable("DAY_VIEW_DECORATOR_KEY");
        this.E0 = bundle.getInt("TITLE_TEXT_RES_ID_KEY");
        this.F0 = bundle.getCharSequence("TITLE_TEXT_KEY");
        this.H0 = bundle.getInt("INPUT_MODE_KEY");
        this.I0 = bundle.getInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY");
        this.J0 = bundle.getCharSequence("POSITIVE_BUTTON_TEXT_KEY");
        this.K0 = bundle.getInt("POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY");
        this.L0 = bundle.getCharSequence("POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY");
        this.M0 = bundle.getInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY");
        this.N0 = bundle.getCharSequence("NEGATIVE_BUTTON_TEXT_KEY");
        this.O0 = bundle.getInt("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY");
        this.P0 = bundle.getCharSequence("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY");
        object = this.F0;
        if (object == null) {
            object = this.n1().getResources().getText(this.E0);
        }
        this.W0 = object;
        this.X0 = com.google.android.material.datepicker.n.X1((CharSequence)object);
    }

    public final void k2() {
        int n3 = this.c2(this.n1());
        Object object = com.google.android.material.datepicker.j.Y1(this.W1(), n3, this.B0, this.C0);
        this.D0 = object;
        if (this.H0 == 1) {
            object = com.google.android.material.datepicker.o.G1(this.W1(), n3, this.B0);
        }
        this.A0 = object;
        this.m2();
        this.l2(this.Z1());
        object = this.o().o();
        ((y)object).n(z1.g.mtrl_calendar_frame, this.A0);
        ((y)object).h();
        this.A0.E1(new r(this){
            public final n a;
            {
                this.a = n3;
            }

            @Override
            public void a() {
                this.a.U0.setEnabled(false);
            }

            @Override
            public void b(Object object) {
                object = this.a;
                ((n)object).l2(((n)object).Z1());
                this.a.U0.setEnabled(this.a.W1().h());
            }
        });
    }

    public void l2(String string) {
        this.R0.setContentDescription((CharSequence)this.Y1());
        this.R0.setText((CharSequence)string);
    }

    public final void m2() {
        TextView textView = this.Q0;
        CharSequence charSequence = this.H0 == 1 && this.f2() ? this.X0 : this.W0;
        textView.setText(charSequence);
    }

    public final void n2(CheckableImageButton object) {
        object = this.H0 == 1 ? object.getContext().getString(z1.k.mtrl_picker_toggle_to_calendar_input_mode) : object.getContext().getString(z1.k.mtrl_picker_toggle_to_text_input_mode);
        this.S0.setContentDescription((CharSequence)object);
    }

    @Override
    public final View o0(LayoutInflater layoutInflater, ViewGroup object, Bundle object2) {
        int n3 = this.G0 ? z1.i.mtrl_picker_fullscreen : z1.i.mtrl_picker_dialog;
        layoutInflater = layoutInflater.inflate(n3, object);
        object = layoutInflater.getContext();
        object2 = this.C0;
        if (object2 != null) {
            ((DayViewDecorator)object2).v((Context)object);
        }
        if (this.G0) {
            layoutInflater.findViewById(z1.g.mtrl_calendar_frame).setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(com.google.android.material.datepicker.n.a2((Context)object), -2));
        } else {
            layoutInflater.findViewById(z1.g.mtrl_calendar_main_pane).setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(com.google.android.material.datepicker.n.a2((Context)object), -1));
        }
        object2 = (TextView)layoutInflater.findViewById(z1.g.mtrl_picker_header_selection_text);
        this.R0 = object2;
        object2.setAccessibilityLiveRegion(1);
        this.S0 = (CheckableImageButton)layoutInflater.findViewById(z1.g.mtrl_picker_header_toggle);
        this.Q0 = (TextView)layoutInflater.findViewById(z1.g.mtrl_picker_title_text);
        this.d2((Context)object);
        this.U0 = (Button)layoutInflater.findViewById(z1.g.confirm_button);
        if (this.W1().h()) {
            this.U0.setEnabled(true);
        } else {
            this.U0.setEnabled(false);
        }
        this.U0.setTag(Y0);
        object = this.J0;
        if (object != null) {
            this.U0.setText((CharSequence)object);
        } else {
            n3 = this.I0;
            if (n3 != 0) {
                this.U0.setText(n3);
            }
        }
        object = this.L0;
        if (object != null) {
            this.U0.setContentDescription((CharSequence)object);
        } else if (this.K0 != 0) {
            this.U0.setContentDescription(this.p().getResources().getText(this.K0));
        }
        this.U0.setOnClickListener((View.OnClickListener)new k(this));
        object = (Button)layoutInflater.findViewById(z1.g.cancel_button);
        object.setTag(Z0);
        object2 = this.N0;
        if (object2 != null) {
            object.setText((CharSequence)object2);
        } else {
            n3 = this.M0;
            if (n3 != 0) {
                object.setText(n3);
            }
        }
        object2 = this.P0;
        if (object2 != null) {
            object.setContentDescription((CharSequence)object2);
        } else if (this.O0 != 0) {
            object.setContentDescription(this.p().getResources().getText(this.O0));
        }
        object.setOnClickListener((View.OnClickListener)new l(this));
        return layoutInflater;
    }

    @Override
    public final void onCancel(DialogInterface dialogInterface) {
        Iterator iterator = ((AbstractCollection)this.w0).iterator();
        while (iterator.hasNext()) {
            ((DialogInterface.OnCancelListener)iterator.next()).onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    @Override
    public final void onDismiss(DialogInterface dialogInterface) {
        ViewGroup viewGroup = ((AbstractCollection)this.x0).iterator();
        while (viewGroup.hasNext()) {
            ((DialogInterface.OnDismissListener)viewGroup.next()).onDismiss(dialogInterface);
        }
        viewGroup = (ViewGroup)this.S();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }
}

