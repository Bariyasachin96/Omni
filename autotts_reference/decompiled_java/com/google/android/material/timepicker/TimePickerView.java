/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.widget.Checkable
 */
package com.google.android.material.timepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.timepicker.ClockFaceView;
import com.google.android.material.timepicker.ClockHandView;
import z1.g;
import z1.i;

class TimePickerView
extends ConstraintLayout {
    public final Chip B;
    public final Chip C;
    public final ClockHandView D;
    public final ClockFaceView E;
    public final MaterialButtonToggleGroup F;
    public final View.OnClickListener G = new View.OnClickListener(this){
        public final TimePickerView c;
        {
            this.c = timePickerView;
        }

        public void onClick(View view) {
            TimePickerView.D(this.c);
        }
    };

    public TimePickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TimePickerView(Context object, AttributeSet attributeSet, int n3) {
        super((Context)object, attributeSet, n3);
        LayoutInflater.from((Context)object).inflate(z1.i.material_timepicker, (ViewGroup)this);
        this.E = (ClockFaceView)this.findViewById(z1.g.material_clock_face);
        object = (MaterialButtonToggleGroup)this.findViewById(z1.g.material_clock_period_toggle);
        this.F = object;
        ((MaterialButtonToggleGroup)((Object)object)).q(new com.google.android.material.timepicker.d(this));
        this.B = (Chip)this.findViewById(z1.g.material_minute_tv);
        this.C = (Chip)this.findViewById(z1.g.material_hour_tv);
        this.D = (ClockHandView)this.findViewById(z1.g.material_clock_hand);
        this.G();
        this.F();
    }

    public static /* synthetic */ void C(TimePickerView timePickerView, MaterialButtonToggleGroup materialButtonToggleGroup, int n3, boolean bl) {
        ((Object)((Object)timePickerView)).getClass();
    }

    public static /* synthetic */ e D(TimePickerView timePickerView) {
        ((Object)((Object)timePickerView)).getClass();
        return null;
    }

    public static /* synthetic */ d E(TimePickerView timePickerView) {
        ((Object)((Object)timePickerView)).getClass();
        return null;
    }

    public final void F() {
        Chip chip = this.B;
        int n3 = z1.g.selection_type;
        chip.setTag(n3, 12);
        this.C.setTag(n3, 10);
        this.B.setOnClickListener(this.G);
        this.C.setOnClickListener(this.G);
        this.B.setAccessibilityClassName("android.view.View");
        this.C.setAccessibilityClassName("android.view.View");
    }

    public final void G() {
        View.OnTouchListener onTouchListener = new View.OnTouchListener(this, new GestureDetector(this.getContext(), (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(this){
            public final TimePickerView a;
            {
                this.a = timePickerView;
            }

            public boolean onDoubleTap(MotionEvent motionEvent) {
                TimePickerView.E(this.a);
                return false;
            }
        })){
            public final GestureDetector c;
            public final TimePickerView d;
            {
                this.d = timePickerView;
                this.c = gestureDetector;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (((Checkable)view).isChecked()) {
                    return this.c.onTouchEvent(motionEvent);
                }
                return false;
            }
        };
        this.B.setOnTouchListener(onTouchListener);
        this.C.setOnTouchListener(onTouchListener);
    }

    public void onVisibilityChanged(View view, int n3) {
        super.onVisibilityChanged(view, n3);
        if (view == this && n3 == 0) {
            this.C.sendAccessibilityEvent(8);
        }
    }

    public static interface d {
    }

    public static interface e {
    }
}

