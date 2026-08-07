/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.EditText
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.e;
import com.google.android.material.datepicker.h;
import com.google.android.material.datepicker.n;
import com.google.android.material.datepicker.r;
import com.google.android.material.datepicker.v;
import com.google.android.material.internal.i;
import com.google.android.material.textfield.TextInputLayout;
import h2.a;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import n0.d;
import s2.b;
import z1.c;
import z1.g;
import z1.k;

public class RangeDateSelector
implements DateSelector<d> {
    public static final Parcelable.Creator<RangeDateSelector> CREATOR = new Parcelable.Creator(){

        public RangeDateSelector a(Parcel parcel) {
            RangeDateSelector rangeDateSelector = new RangeDateSelector();
            RangeDateSelector.r(rangeDateSelector, (Long)parcel.readValue(Long.class.getClassLoader()));
            RangeDateSelector.s(rangeDateSelector, (Long)parcel.readValue(Long.class.getClassLoader()));
            return rangeDateSelector;
        }

        public RangeDateSelector[] b(int n3) {
            return new RangeDateSelector[n3];
        }
    };
    public CharSequence c;
    public String d;
    public final String e;
    public Long f = null;
    public Long g = null;
    public Long h = null;
    public Long i = null;
    public SimpleDateFormat j;

    public RangeDateSelector() {
        this.e = " ";
    }

    public static /* synthetic */ Long o(RangeDateSelector rangeDateSelector, Long l3) {
        rangeDateSelector.h = l3;
        return l3;
    }

    public static /* synthetic */ Long q(RangeDateSelector rangeDateSelector, Long l3) {
        rangeDateSelector.i = l3;
        return l3;
    }

    public static /* synthetic */ Long r(RangeDateSelector rangeDateSelector, Long l3) {
        rangeDateSelector.f = l3;
        return l3;
    }

    public static /* synthetic */ Long s(RangeDateSelector rangeDateSelector, Long l3) {
        rangeDateSelector.g = l3;
        return l3;
    }

    @Override
    public String b(Context object) {
        Resources resources = object.getResources();
        Object object2 = com.google.android.material.datepicker.h.a(this.f, this.g);
        object = ((d)object2).a;
        object = object == null ? resources.getString(k.mtrl_picker_announce_current_selection_none) : (String)object;
        object2 = ((d)object2).b;
        object2 = object2 == null ? resources.getString(k.mtrl_picker_announce_current_selection_none) : (String)object2;
        return resources.getString(k.mtrl_picker_announce_current_range_selection, new Object[]{object, object2});
    }

    @Override
    public String d(Context context) {
        context = context.getResources();
        Long l3 = this.f;
        if (l3 == null && this.g == null) {
            return context.getString(k.mtrl_picker_range_header_unselected);
        }
        Object object = this.g;
        if (object == null) {
            return context.getString(k.mtrl_picker_range_header_only_start_selected, new Object[]{com.google.android.material.datepicker.h.c(l3)});
        }
        if (l3 == null) {
            return context.getString(k.mtrl_picker_range_header_only_end_selected, new Object[]{com.google.android.material.datepicker.h.c((Long)object)});
        }
        object = com.google.android.material.datepicker.h.a(l3, (Long)object);
        return context.getString(k.mtrl_picker_range_header_selected, new Object[]{((d)object).a, ((d)object).b});
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public int e(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int n3 = resources.getDimensionPixelSize(z1.e.mtrl_calendar_maximum_default_fullscreen_minor_axis);
        n3 = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) > n3 ? z1.c.materialCalendarTheme : z1.c.materialCalendarFullscreenTheme;
        return b.f(context, n3, n.class.getCanonicalName());
    }

    @Override
    public Collection f() {
        ArrayList<d> arrayList = new ArrayList<d>();
        arrayList.add(new d(this.f, this.g));
        return arrayList;
    }

    @Override
    public boolean h() {
        Long l3 = this.f;
        return l3 != null && this.g != null && this.v(l3, this.g);
    }

    @Override
    public Collection i() {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        Long l3 = this.f;
        if (l3 != null) {
            arrayList.add(l3);
        }
        if ((l3 = this.g) != null) {
            arrayList.add(l3);
        }
        return arrayList;
    }

    @Override
    public void k(long l3) {
        Long l4 = this.f;
        if (l4 == null) {
            this.f = l3;
            return;
        }
        if (this.g == null && this.v(l4, l3)) {
            this.g = l3;
            return;
        }
        this.g = null;
        this.f = l3;
    }

    @Override
    public View n(LayoutInflater object, ViewGroup object2, Bundle object3, CalendarConstraints calendarConstraints, r r3) {
        int n3 = z1.i.mtrl_picker_text_input_date_range;
        boolean bl = false;
        View view = object.inflate(n3, object2, false);
        TextInputLayout textInputLayout = (TextInputLayout)view.findViewById(z1.g.mtrl_picker_text_input_range_start);
        object3 = (TextInputLayout)view.findViewById(z1.g.mtrl_picker_text_input_range_end);
        EditText editText = textInputLayout.getEditText();
        EditText editText2 = ((TextInputLayout)((Object)object3)).getEditText();
        object = a.f(view.getContext(), z1.c.colorOnSurfaceVariant);
        if (object != null) {
            editText.setHintTextColor(((Integer)object).intValue());
            editText2.setHintTextColor(((Integer)object).intValue());
        }
        if (com.google.android.material.internal.i.b()) {
            editText.setInputType(17);
            editText2.setInputType(17);
        }
        this.d = view.getResources().getString(k.mtrl_picker_invalid_range);
        object = this.j;
        if (object != null) {
            bl = true;
        }
        if (!bl) {
            object = v.f();
        }
        object2 = this.f;
        if (object2 != null) {
            editText.setText((CharSequence)((Format)object).format(object2));
            this.h = this.f;
        }
        if ((object2 = this.g) != null) {
            editText2.setText((CharSequence)((Format)object).format(object2));
            this.i = this.g;
        }
        object2 = bl ? ((SimpleDateFormat)object).toPattern() : v.g(view.getResources(), (SimpleDateFormat)object);
        textInputLayout.setPlaceholderText((CharSequence)object2);
        ((TextInputLayout)((Object)object3)).setPlaceholderText((CharSequence)object2);
        editText.addTextChangedListener((TextWatcher)new e(this, (String)object2, (DateFormat)object, textInputLayout, calendarConstraints, textInputLayout, (TextInputLayout)((Object)object3), r3){
            public final TextInputLayout k;
            public final TextInputLayout l;
            public final r m;
            public final RangeDateSelector n;
            {
                this.n = rangeDateSelector;
                this.k = textInputLayout2;
                this.l = textInputLayout3;
                this.m = r3;
                super(string, dateFormat, textInputLayout, calendarConstraints);
            }

            @Override
            public void d() {
                RangeDateSelector.o(this.n, null);
                this.n.y(this.k, this.l, this.m);
            }

            @Override
            public void e(Long l3) {
                RangeDateSelector.o(this.n, l3);
                this.n.y(this.k, this.l, this.m);
            }
        });
        editText2.addTextChangedListener((TextWatcher)new e(this, (String)object2, (DateFormat)object, (TextInputLayout)((Object)object3), calendarConstraints, textInputLayout, (TextInputLayout)((Object)object3), r3){
            public final TextInputLayout k;
            public final TextInputLayout l;
            public final r m;
            public final RangeDateSelector n;
            {
                this.n = rangeDateSelector;
                this.k = textInputLayout2;
                this.l = textInputLayout3;
                this.m = r3;
                super(string, dateFormat, textInputLayout, calendarConstraints);
            }

            @Override
            public void d() {
                RangeDateSelector.q(this.n, null);
                this.n.y(this.k, this.l, this.m);
            }

            @Override
            public void e(Long l3) {
                RangeDateSelector.q(this.n, l3);
                this.n.y(this.k, this.l, this.m);
            }
        });
        if (!DateSelector.l(view.getContext())) {
            DateSelector.m(editText, editText2);
        }
        return view;
    }

    public final void t(TextInputLayout textInputLayout, TextInputLayout textInputLayout2) {
        if (textInputLayout.getError() != null && this.d.contentEquals(textInputLayout.getError())) {
            textInputLayout.setError(null);
        }
        if (textInputLayout2.getError() != null && " ".contentEquals(textInputLayout2.getError())) {
            textInputLayout2.setError(null);
        }
    }

    public d u() {
        return new d(this.f, this.g);
    }

    public final boolean v(long l3, long l4) {
        return l3 <= l4;
    }

    public final void w(TextInputLayout textInputLayout, TextInputLayout textInputLayout2) {
        textInputLayout.setError(this.d);
        textInputLayout2.setError(" ");
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeValue((Object)this.f);
        parcel.writeValue((Object)this.g);
    }

    public final void x(TextInputLayout textInputLayout, TextInputLayout textInputLayout2) {
        if (!TextUtils.isEmpty((CharSequence)textInputLayout.getError())) {
            this.c = textInputLayout.getError();
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)textInputLayout2.getError())) {
            this.c = textInputLayout2.getError();
            return;
        }
        this.c = null;
    }

    public final void y(TextInputLayout textInputLayout, TextInputLayout textInputLayout2, r r3) {
        Long l3 = this.h;
        if (l3 != null && this.i != null) {
            if (this.v(l3, this.i)) {
                this.f = this.h;
                this.g = this.i;
                r3.b(this.u());
            } else {
                this.w(textInputLayout, textInputLayout2);
                r3.a();
            }
        } else {
            this.t(textInputLayout, textInputLayout2);
            r3.a();
        }
        this.x(textInputLayout, textInputLayout2);
    }
}

