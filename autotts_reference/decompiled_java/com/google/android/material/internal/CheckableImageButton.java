/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 *  android.widget.Checkable
 */
package com.google.android.material.internal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Checkable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.customview.view.AbsSavedState;
import c.a;
import o0.x0;
import p0.s;

public class CheckableImageButton
extends AppCompatImageButton
implements Checkable {
    public static final int[] i = new int[]{0x10100A0};
    public boolean f;
    public boolean g = true;
    public boolean h = true;

    public CheckableImageButton(Context context) {
        this(context, null);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.imageButtonStyle);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        x0.h0((View)this, new o0.a(this){
            public final CheckableImageButton d;
            {
                this.d = checkableImageButton;
            }

            @Override
            public void f(View view, AccessibilityEvent accessibilityEvent) {
                super.f(view, accessibilityEvent);
                accessibilityEvent.setChecked(this.d.isChecked());
            }

            @Override
            public void g(View view, s s3) {
                super.g(view, s3);
                s3.f0(this.d.a());
                s3.g0(this.d.isChecked());
            }
        });
    }

    public boolean a() {
        return this.g;
    }

    public boolean isChecked() {
        return this.f;
    }

    public int[] onCreateDrawableState(int n3) {
        if (this.f) {
            int[] nArray = i;
            return View.mergeDrawableStates((int[])super.onCreateDrawableState(n3 + nArray.length), (int[])nArray);
        }
        return super.onCreateDrawableState(n3);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.setChecked(parcelable.e);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = this.f;
        return savedState;
    }

    public void setCheckable(boolean bl) {
        if (this.g != bl) {
            this.g = bl;
            this.sendAccessibilityEvent(0);
        }
    }

    public void setChecked(boolean bl) {
        if (this.g && this.f != bl) {
            this.f = bl;
            this.refreshDrawableState();
            this.sendAccessibilityEvent(2048);
        }
    }

    public void setPressable(boolean bl) {
        this.h = bl;
    }

    public void setPressed(boolean bl) {
        if (this.h) {
            super.setPressed(bl);
        }
    }

    public void toggle() {
        this.setChecked(this.f ^ true);
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
        public boolean e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.p(parcel);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private void p(Parcel parcel) {
            int n3 = parcel.readInt();
            boolean bl = true;
            if (n3 != 1) {
                bl = false;
            }
            this.e = bl;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.e ? 1 : 0);
        }
    }
}

