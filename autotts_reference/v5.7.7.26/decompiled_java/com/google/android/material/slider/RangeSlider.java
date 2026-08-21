/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.AbsSavedState
 */
package com.google.android.material.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import com.google.android.material.internal.z;
import com.google.android.material.slider.BaseSlider;
import java.util.ArrayList;
import java.util.List;
import z1.c;
import z1.m;

public class RangeSlider
extends BaseSlider<RangeSlider, Object, Object> {
    public float d1;
    public int e1;

    public RangeSlider(Context context) {
        this(context, null);
    }

    public RangeSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.sliderStyle);
    }

    public RangeSlider(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        context = com.google.android.material.internal.z.i(context, attributeSet, z1.m.RangeSlider, n3, BaseSlider.Y0, new int[0]);
        n3 = z1.m.RangeSlider_values;
        if (context.hasValue(n3)) {
            n3 = context.getResourceId(n3, 0);
            this.setValues(RangeSlider.o1(context.getResources().obtainTypedArray(n3)));
        }
        this.d1 = context.getDimension(z1.m.RangeSlider_minSeparation, 0.0f);
        context.recycle();
    }

    public static List o1(TypedArray typedArray) {
        ArrayList<Float> arrayList = new ArrayList<Float>();
        for (int i3 = 0; i3 < typedArray.length(); ++i3) {
            arrayList.add(Float.valueOf(typedArray.getFloat(i3, -1.0f)));
        }
        return arrayList;
    }

    @Override
    public float getMinSeparation() {
        return this.d1;
    }

    @Override
    public List<Float> getValues() {
        return super.getValues();
    }

    @Override
    public void onRestoreInstanceState(Parcelable object) {
        int n3;
        object = (RangeSliderState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.d1 = ((RangeSliderState)((Object)object)).c;
        this.e1 = n3 = ((RangeSliderState)((Object)object)).d;
        this.setSeparationUnit(n3);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        RangeSliderState rangeSliderState = new RangeSliderState(super.onSaveInstanceState());
        RangeSliderState.p(rangeSliderState, this.d1);
        RangeSliderState.r(rangeSliderState, this.e1);
        return rangeSliderState;
    }

    @Override
    public void setCustomThumbDrawable(int n3) {
        super.setCustomThumbDrawable(n3);
    }

    @Override
    public void setCustomThumbDrawable(Drawable drawable) {
        super.setCustomThumbDrawable(drawable);
    }

    @Override
    public void setCustomThumbDrawablesForValues(int ... nArray) {
        super.setCustomThumbDrawablesForValues(nArray);
    }

    @Override
    public void setCustomThumbDrawablesForValues(Drawable ... drawableArray) {
        super.setCustomThumbDrawablesForValues(drawableArray);
    }

    public void setMinSeparation(float f3) {
        this.d1 = f3;
        this.e1 = 0;
        this.setSeparationUnit(0);
    }

    public void setMinSeparationValue(float f3) {
        this.d1 = f3;
        this.e1 = 1;
        this.setSeparationUnit(1);
    }

    @Override
    public void setValues(List<Float> list) {
        super.setValues(list);
    }

    @Override
    public void setValues(Float ... floatArray) {
        super.setValues(floatArray);
    }

    public static class RangeSliderState
    extends AbsSavedState {
        public static final Parcelable.Creator<RangeSliderState> CREATOR = new Parcelable.Creator(){

            public RangeSliderState a(Parcel parcel) {
                return new RangeSliderState(parcel, null);
            }

            public RangeSliderState[] b(int n3) {
                return new RangeSliderState[n3];
            }
        };
        public float c;
        public int d;

        public RangeSliderState(Parcel parcel) {
            super(parcel.readParcelable(RangeSliderState.class.getClassLoader()));
            this.c = parcel.readFloat();
            this.d = parcel.readInt();
        }

        public /* synthetic */ RangeSliderState(Parcel parcel, a a4) {
            this(parcel);
        }

        public RangeSliderState(Parcelable parcelable) {
            super(parcelable);
        }

        public static /* synthetic */ float p(RangeSliderState rangeSliderState, float f3) {
            rangeSliderState.c = f3;
            return f3;
        }

        public static /* synthetic */ int r(RangeSliderState rangeSliderState, int n3) {
            rangeSliderState.d = n3;
            return n3;
        }

        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeFloat(this.c);
            parcel.writeInt(this.d);
        }
    }
}

