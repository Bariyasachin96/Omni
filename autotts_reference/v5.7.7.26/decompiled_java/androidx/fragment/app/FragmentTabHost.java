/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TabHost
 *  android.widget.TabHost$OnTabChangeListener
 *  android.widget.TabWidget
 */
package androidx.fragment.app;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import androidx.appcompat.app.s;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.y;
import java.util.ArrayList;

@Deprecated
public class FragmentTabHost
extends TabHost
implements TabHost.OnTabChangeListener {
    public final ArrayList c = new ArrayList();
    public FrameLayout d;
    public Context e;
    public FragmentManager f;
    public int g;
    public TabHost.OnTabChangeListener h;
    public boolean i;

    @Deprecated
    public FragmentTabHost(Context context) {
        super(context, null);
        this.e(context, null);
    }

    @Deprecated
    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e(context, attributeSet);
    }

    public final y a(String string, y y3) {
        this.d(string);
        return y3;
    }

    public final void b() {
        if (this.d == null) {
            Object object = (FrameLayout)this.findViewById(this.g);
            this.d = object;
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("No tab content FrameLayout found for id ");
                ((StringBuilder)object).append(this.g);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        }
    }

    public final void c(Context context) {
        if (this.findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            this.addView((View)linearLayout, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView((View)tabWidget, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2, 0.0f));
            tabWidget = new FrameLayout(context);
            tabWidget.setId(0x1020011);
            linearLayout.addView((View)tabWidget, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, 0, 0.0f));
            context = new FrameLayout(context);
            this.d = context;
            context.setId(this.g);
            linearLayout.addView((View)context, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    public final a d(String string) {
        if (this.c.size() <= 0) {
            return null;
        }
        s.a(this.c.get(0));
        throw null;
    }

    public final void e(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.g = context.getResourceId(0, 0);
        context.recycle();
        super.setOnTabChangedListener((TabHost.OnTabChangeListener)this);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object object = this.getCurrentTabTag();
        if (this.c.size() <= 0) {
            this.i = true;
            if ((object = this.a((String)object, null)) != null) {
                ((y)object).f();
                this.f.e0();
            }
            return;
        }
        s.a(this.c.get(0));
        throw null;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.i = false;
    }

    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setCurrentTabByTag(object.c);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.c = this.getCurrentTabTag();
        return savedState;
    }

    public void onTabChanged(String string) {
        Object object;
        if (this.i && (object = this.a(string, null)) != null) {
            ((y)object).f();
        }
        if ((object = this.h) != null) {
            object.onTabChanged(string);
        }
    }

    @Deprecated
    public void setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.h = onTabChangeListener;
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    @Deprecated
    public void setup(Context context, FragmentManager fragmentManager) {
        this.c(context);
        super.setup();
        this.e = context;
        this.f = fragmentManager;
        this.b();
    }

    @Deprecated
    public void setup(Context context, FragmentManager fragmentManager, int n3) {
        this.c(context);
        super.setup();
        this.e = context;
        this.f = fragmentManager;
        this.g = n3;
        this.b();
        this.d.setId(n3);
        if (this.getId() == -1) {
            this.setId(0x1020012);
        }
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] b(int n3) {
                return new SavedState[n3];
            }
        };
        public String c;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.c = parcel.readString();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FragmentTabHost.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode((Object)this)));
            stringBuilder.append(" curTab=");
            stringBuilder.append(this.c);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeString(this.c);
        }
    }

    public static final abstract class a {
    }
}

