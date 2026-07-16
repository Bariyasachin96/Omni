/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextWatcher
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
import android.text.TextWatcher;
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
import s2.b;
import z1.c;
import z1.g;
import z1.k;

public class SingleDateSelector
implements DateSelector<Long> {
    public static final Parcelable.Creator<SingleDateSelector> CREATOR = new Parcelable.Creator(){

        public SingleDateSelector a(Parcel parcel) {
            SingleDateSelector singleDateSelector = new SingleDateSelector();
            SingleDateSelector.q(singleDateSelector, (Long)parcel.readValue(Long.class.getClassLoader()));
            return singleDateSelector;
        }

        public SingleDateSelector[] b(int n3) {
            return new SingleDateSelector[n3];
        }
    };
    public CharSequence c;
    public Long d;
    public SimpleDateFormat e;

    public static /* synthetic */ CharSequence p(SingleDateSelector singleDateSelector, CharSequence charSequence) {
        singleDateSelector.c = charSequence;
        return charSequence;
    }

    public static /* synthetic */ Long q(SingleDateSelector singleDateSelector, Long l3) {
        singleDateSelector.d = l3;
        return l3;
    }

    @Override
    public String b(Context object) {
        Resources resources = object.getResources();
        object = this.d;
        object = object == null ? resources.getString(k.mtrl_picker_announce_current_selection_none) : h.m((Long)object);
        return resources.getString(k.mtrl_picker_announce_current_selection, new Object[]{object});
    }

    @Override
    public String d(Context context) {
        context = context.getResources();
        Object object = this.d;
        if (object == null) {
            return context.getString(k.mtrl_picker_date_header_unselected);
        }
        object = h.m((Long)object);
        return context.getString(k.mtrl_picker_date_header_selected, new Object[]{object});
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public int e(Context context) {
        return b.f(context, z1.c.materialCalendarTheme, n.class.getCanonicalName());
    }

    @Override
    public Collection f() {
        return new ArrayList();
    }

    @Override
    public boolean h() {
        return this.d != null;
    }

    @Override
    public Collection i() {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        Long l3 = this.d;
        if (l3 != null) {
            arrayList.add(l3);
        }
        return arrayList;
    }

    @Override
    public void k(long l3) {
        this.d = l3;
    }

    @Override
    public View n(LayoutInflater object, ViewGroup object2, Bundle bundle, CalendarConstraints calendarConstraints, r r3) {
        int n3 = z1.i.mtrl_picker_text_input_date;
        boolean bl = false;
        View view = object.inflate(n3, object2, false);
        TextInputLayout textInputLayout = (TextInputLayout)view.findViewById(g.mtrl_picker_text_input_date);
        bundle = textInputLayout.getEditText();
        object = a.f(view.getContext(), z1.c.colorOnSurfaceVariant);
        if (object != null) {
            bundle.setHintTextColor(((Integer)object).intValue());
        }
        if (i.b()) {
            bundle.setInputType(17);
        }
        if ((object = this.e) != null) {
            bl = true;
        }
        if (!bl) {
            object = v.f();
        }
        object2 = bl ? ((SimpleDateFormat)object).toPattern() : v.g(view.getResources(), (SimpleDateFormat)object);
        textInputLayout.setPlaceholderText((CharSequence)object2);
        Long l3 = this.d;
        if (l3 != null) {
            bundle.setText((CharSequence)((Format)object).format(l3));
        }
        bundle.addTextChangedListener((TextWatcher)new e(this, (String)object2, (DateFormat)object, textInputLayout, calendarConstraints, r3, textInputLayout){
            public final r k;
            public final TextInputLayout l;
            public final SingleDateSelector m;
            {
                this.m = singleDateSelector;
                this.k = r3;
                this.l = textInputLayout2;
                super(string, dateFormat, textInputLayout, calendarConstraints);
            }

            @Override
            public void d() {
                SingleDateSelector.p(this.m, this.l.getError());
                this.k.a();
            }

            @Override
            public void e(Long l3) {
                if (l3 == null) {
                    this.m.r();
                } else {
                    this.m.k(l3);
                }
                SingleDateSelector.p(this.m, null);
                this.k.b(this.m.s());
            }
        });
        if (!DateSelector.l(view.getContext())) {
            DateSelector.m(new EditText[]{bundle});
        }
        return view;
    }

    public final void r() {
        this.d = null;
    }

    public Long s() {
        return this.d;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeValue((Object)this.d);
    }
}

