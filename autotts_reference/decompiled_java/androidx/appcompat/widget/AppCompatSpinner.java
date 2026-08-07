/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$Theme
 *  android.database.DataSetObserver
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.ThemedSpinnerAdapter
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;
import androidx.appcompat.app.a;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.c0;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.t0;
import c.a;
import c.j;

public class AppCompatSpinner
extends Spinner {
    public static final int[] k = new int[]{16843505};
    public final androidx.appcompat.widget.d c;
    public final Context d;
    public c0 e;
    public SpinnerAdapter f;
    public final boolean g;
    public g h;
    public int i;
    public final Rect j;

    public AppCompatSpinner(Context context) {
        this(context, null);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n3, int n4) {
        this(context, attributeSet, n3, n4, null);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AppCompatSpinner(Context object, AttributeSet attributeSet, int n3, int n4, Resources.Theme object2) {
        Object object3;
        int n5;
        m0 m02;
        block21: {
            block18: {
                block20: {
                    void var2_5;
                    block19: {
                        super((Context)object, attributeSet, n3);
                        this.j = new Rect();
                        i0.a((View)this, this.getContext());
                        m02 = m0.v((Context)object, attributeSet, c.j.Spinner, n3, 0);
                        this.c = new androidx.appcompat.widget.d((View)this);
                        this.d = object2 != null ? new h.d((Context)object, (Resources.Theme)object2) : ((n5 = m02.n(c.j.Spinner_popupTheme, 0)) != 0 ? new h.d((Context)object, n5) : object);
                        object3 = null;
                        n5 = n4;
                        if (n4 != -1) break block21;
                        object2 = object.obtainStyledAttributes(attributeSet, k, n3, 0);
                        n5 = n4;
                        object3 = object2;
                        try {
                            if (object2.hasValue(0)) {
                                n5 = object2.getInt(0, 0);
                                object3 = object2;
                            }
                            break block18;
                        }
                        catch (Throwable throwable) {
                            object = object2;
                            break block19;
                        }
                        catch (Throwable throwable) {
                            object = object3;
                        }
                        catch (Exception exception) {
                            object2 = null;
                            break block20;
                        }
                    }
                    if (object != null) {
                        object.recycle();
                    }
                    throw var2_5;
                    catch (Exception exception) {}
                }
                n5 = n4;
                if (object2 == null) break block21;
                n5 = n4;
                object3 = object2;
            }
            object3.recycle();
        }
        if (n5 != 0) {
            if (n5 == 1) {
                object3 = new f(this, this.d, attributeSet, n3);
                object2 = m0.v(this.d, attributeSet, c.j.Spinner, n3, 0);
                this.i = ((m0)object2).m(c.j.Spinner_android_dropDownWidth, -2);
                ((ListPopupWindow)object3).b(((m0)object2).g(c.j.Spinner_android_popupBackground));
                ((f)object3).i(m02.o(c.j.Spinner_android_prompt));
                ((m0)object2).x();
                this.h = object3;
                this.e = new c0(this, (View)this, (f)object3){
                    public final f l;
                    public final AppCompatSpinner m;
                    {
                        this.m = appCompatSpinner;
                        this.l = f3;
                        super(view);
                    }

                    @Override
                    public i.f b() {
                        return this.l;
                    }

                    @Override
                    public boolean c() {
                        if (!this.m.getInternalPopup().c()) {
                            this.m.b();
                        }
                        return true;
                    }
                };
            }
        } else {
            this.h = object2 = new d(this);
            object2.i(m02.o(c.j.Spinner_android_prompt));
        }
        object2 = m02.q(c.j.Spinner_android_entries);
        if (object2 != null) {
            object = new ArrayAdapter((Context)object, 17367048, (Object[])object2);
            object.setDropDownViewResource(c.g.support_simple_spinner_dropdown_item);
            this.setAdapter((SpinnerAdapter)object);
        }
        m02.x();
        this.g = true;
        object = this.f;
        if (object != null) {
            this.setAdapter((SpinnerAdapter)object);
            this.f = null;
        }
        this.c.e(attributeSet, n3);
    }

    public int a(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int n3 = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int n4 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0);
        int n5 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0);
        int n6 = Math.max(0, this.getSelectedItemPosition());
        int n7 = Math.min(spinnerAdapter.getCount(), n6 + 15);
        int n8 = Math.max(0, n6 - (15 - (n7 - n6)));
        View view = null;
        n6 = 0;
        while (n8 < n7) {
            int n9 = spinnerAdapter.getItemViewType(n8);
            int n10 = n3;
            if (n9 != n3) {
                view = null;
                n10 = n9;
            }
            if ((view = spinnerAdapter.getView(n8, view, (ViewGroup)this)).getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(n4, n5);
            n6 = Math.max(n6, view.getMeasuredWidth());
            ++n8;
            n3 = n10;
        }
        n8 = n6;
        if (drawable != null) {
            drawable.getPadding(this.j);
            spinnerAdapter = this.j;
            n8 = n6 + (spinnerAdapter.left + spinnerAdapter.right);
        }
        return n8;
    }

    public void b() {
        this.h.m(this.getTextDirection(), this.getTextAlignment());
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            d3.b();
        }
    }

    public int getDropDownHorizontalOffset() {
        g g3 = this.h;
        if (g3 != null) {
            return g3.d();
        }
        return super.getDropDownHorizontalOffset();
    }

    public int getDropDownVerticalOffset() {
        g g3 = this.h;
        if (g3 != null) {
            return g3.n();
        }
        return super.getDropDownVerticalOffset();
    }

    public int getDropDownWidth() {
        if (this.h != null) {
            return this.i;
        }
        return super.getDropDownWidth();
    }

    public final g getInternalPopup() {
        return this.h;
    }

    public Drawable getPopupBackground() {
        g g3 = this.h;
        if (g3 != null) {
            return g3.g();
        }
        return super.getPopupBackground();
    }

    public Context getPopupContext() {
        return this.d;
    }

    public CharSequence getPrompt() {
        g g3 = this.h;
        if (g3 != null) {
            return g3.o();
        }
        return super.getPrompt();
    }

    public ColorStateList getSupportBackgroundTintList() {
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            return d3.c();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            return d3.d();
        }
        return null;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        g g3 = this.h;
        if (g3 != null && g3.c()) {
            this.h.dismiss();
        }
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        if (this.h != null && View.MeasureSpec.getMode((int)n3) == Integer.MIN_VALUE) {
            this.setMeasuredDimension(Math.min(Math.max(this.getMeasuredWidth(), this.a(this.getAdapter(), this.getBackground())), View.MeasureSpec.getSize((int)n3)), this.getMeasuredHeight());
        }
    }

    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        if (object.c && (object = this.getViewTreeObserver()) != null) {
            object.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(this){
                public final AppCompatSpinner c;
                {
                    this.c = appCompatSpinner;
                }

                public void onGlobalLayout() {
                    ViewTreeObserver viewTreeObserver;
                    if (!this.c.getInternalPopup().c()) {
                        this.c.b();
                    }
                    if ((viewTreeObserver = this.c.getViewTreeObserver()) != null) {
                        viewTreeObserver.removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                    }
                }
            });
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        g g3 = this.h;
        boolean bl = g3 != null && g3.c();
        savedState.c = bl;
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        c0 c02 = this.e;
        if (c02 != null && c02.onTouch((View)this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean performClick() {
        g g3 = this.h;
        if (g3 != null) {
            if (!g3.c()) {
                this.b();
            }
            return true;
        }
        return super.performClick();
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.g) {
            this.f = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        if (this.h != null) {
            Context context;
            Context context2 = context = this.d;
            if (context == null) {
                context2 = this.getContext();
            }
            this.h.p(new e(spinnerAdapter, context2.getTheme()));
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            d3.f(drawable);
        }
    }

    public void setBackgroundResource(int n3) {
        super.setBackgroundResource(n3);
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            d3.g(n3);
        }
    }

    public void setDropDownHorizontalOffset(int n3) {
        g g3 = this.h;
        if (g3 != null) {
            g3.k(n3);
            this.h.l(n3);
            return;
        }
        super.setDropDownHorizontalOffset(n3);
    }

    public void setDropDownVerticalOffset(int n3) {
        g g3 = this.h;
        if (g3 != null) {
            g3.j(n3);
            return;
        }
        super.setDropDownVerticalOffset(n3);
    }

    public void setDropDownWidth(int n3) {
        if (this.h != null) {
            this.i = n3;
            return;
        }
        super.setDropDownWidth(n3);
    }

    public void setPopupBackgroundDrawable(Drawable drawable) {
        g g3 = this.h;
        if (g3 != null) {
            g3.b(drawable);
            return;
        }
        super.setPopupBackgroundDrawable(drawable);
    }

    public void setPopupBackgroundResource(int n3) {
        this.setPopupBackgroundDrawable(d.a.b(this.getPopupContext(), n3));
    }

    public void setPrompt(CharSequence charSequence) {
        g g3 = this.h;
        if (g3 != null) {
            g3.i(charSequence);
            return;
        }
        super.setPrompt(charSequence);
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            d3.i(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        androidx.appcompat.widget.d d3 = this.c;
        if (d3 != null) {
            d3.j(mode);
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
        public boolean c;

        public SavedState(Parcel parcel) {
            super(parcel);
            boolean bl = parcel.readByte() != 0;
            this.c = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeByte((byte)(this.c ? 1 : 0));
        }
    }

    public static final abstract class c {
        public static void a(ThemedSpinnerAdapter themedSpinnerAdapter, Resources.Theme theme) {
            if (!n0.c.a(themedSpinnerAdapter.getDropDownViewTheme(), theme)) {
                themedSpinnerAdapter.setDropDownViewTheme(theme);
            }
        }
    }

    public class d
    implements g,
    DialogInterface.OnClickListener {
        public androidx.appcompat.app.a c;
        public ListAdapter d;
        public CharSequence e;
        public final AppCompatSpinner f;

        public d(AppCompatSpinner appCompatSpinner) {
            this.f = appCompatSpinner;
        }

        @Override
        public void b(Drawable drawable) {
            Log.e((String)"AppCompatSpinner", (String)"Cannot set popup background for MODE_DIALOG, ignoring");
        }

        @Override
        public boolean c() {
            androidx.appcompat.app.a a4 = this.c;
            if (a4 != null) {
                return a4.isShowing();
            }
            return false;
        }

        @Override
        public int d() {
            return 0;
        }

        @Override
        public void dismiss() {
            androidx.appcompat.app.a a4 = this.c;
            if (a4 != null) {
                a4.dismiss();
                this.c = null;
            }
        }

        @Override
        public Drawable g() {
            return null;
        }

        @Override
        public void i(CharSequence charSequence) {
            this.e = charSequence;
        }

        @Override
        public void j(int n3) {
            Log.e((String)"AppCompatSpinner", (String)"Cannot set vertical offset for MODE_DIALOG, ignoring");
        }

        @Override
        public void k(int n3) {
            Log.e((String)"AppCompatSpinner", (String)"Cannot set horizontal (original) offset for MODE_DIALOG, ignoring");
        }

        @Override
        public void l(int n3) {
            Log.e((String)"AppCompatSpinner", (String)"Cannot set horizontal offset for MODE_DIALOG, ignoring");
        }

        @Override
        public void m(int n3, int n4) {
            if (this.d == null) {
                return;
            }
            Object object = new a.a(this.f.getPopupContext());
            CharSequence charSequence = this.e;
            if (charSequence != null) {
                ((a.a)object).h(charSequence);
            }
            this.c = object = ((a.a)object).g(this.d, this.f.getSelectedItemPosition(), this).a();
            object = ((androidx.appcompat.app.a)object).l();
            object.setTextDirection(n3);
            object.setTextAlignment(n4);
            this.c.show();
        }

        @Override
        public int n() {
            return 0;
        }

        @Override
        public CharSequence o() {
            return this.e;
        }

        public void onClick(DialogInterface dialogInterface, int n3) {
            this.f.setSelection(n3);
            if (this.f.getOnItemClickListener() != null) {
                this.f.performItemClick(null, n3, this.d.getItemId(n3));
            }
            this.dismiss();
        }

        @Override
        public void p(ListAdapter listAdapter) {
            this.d = listAdapter;
        }
    }

    public static class e
    implements ListAdapter,
    SpinnerAdapter {
        public SpinnerAdapter c;
        public ListAdapter d;

        public e(SpinnerAdapter spinnerAdapter, Resources.Theme theme) {
            this.c = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.d = (ListAdapter)spinnerAdapter;
            }
            if (theme != null && spinnerAdapter instanceof ThemedSpinnerAdapter) {
                androidx.appcompat.widget.AppCompatSpinner$c.a((ThemedSpinnerAdapter)spinnerAdapter, theme);
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.d;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.c;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        public View getDropDownView(int n3, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.c;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(n3, view, viewGroup);
        }

        public Object getItem(int n3) {
            SpinnerAdapter spinnerAdapter = this.c;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(n3);
        }

        public long getItemId(int n3) {
            SpinnerAdapter spinnerAdapter = this.c;
            if (spinnerAdapter == null) {
                return -1L;
            }
            return spinnerAdapter.getItemId(n3);
        }

        public int getItemViewType(int n3) {
            return 0;
        }

        public View getView(int n3, View view, ViewGroup viewGroup) {
            return this.getDropDownView(n3, view, viewGroup);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.c;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        public boolean isEmpty() {
            return this.getCount() == 0;
        }

        public boolean isEnabled(int n3) {
            ListAdapter listAdapter = this.d;
            if (listAdapter != null) {
                return listAdapter.isEnabled(n3);
            }
            return true;
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.c;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.c;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    public class f
    extends ListPopupWindow
    implements g {
        public CharSequence K;
        public ListAdapter L;
        public final Rect M;
        public int N;
        public final AppCompatSpinner O;

        public f(AppCompatSpinner appCompatSpinner, Context context, AttributeSet attributeSet, int n3) {
            this.O = appCompatSpinner;
            super(context, attributeSet, n3);
            this.M = new Rect();
            this.D((View)appCompatSpinner);
            this.J(true);
            this.P(0);
            this.L(new AdapterView.OnItemClickListener(this, appCompatSpinner){
                public final AppCompatSpinner c;
                public final f d;
                {
                    this.d = f3;
                    this.c = appCompatSpinner;
                }

                public void onItemClick(AdapterView object, View view, int n3, long l3) {
                    this.d.O.setSelection(n3);
                    if (this.d.O.getOnItemClickListener() != null) {
                        object = this.d;
                        object.O.performItemClick(view, n3, object.L.getItemId(n3));
                    }
                    this.d.dismiss();
                }
            });
        }

        public void T() {
            int n3;
            Object object = this.g();
            if (object != null) {
                object.getPadding(this.O.j);
                n3 = t0.b((View)this.O) ? this.O.j.right : -this.O.j.left;
            } else {
                object = this.O.j;
                ((Rect)object).right = 0;
                ((Rect)object).left = 0;
                n3 = 0;
            }
            int n4 = this.O.getPaddingLeft();
            int n5 = this.O.getPaddingRight();
            int n6 = this.O.getWidth();
            object = this.O;
            int n7 = ((AppCompatSpinner)((Object)object)).i;
            if (n7 == -2) {
                int n8 = ((AppCompatSpinner)((Object)object)).a((SpinnerAdapter)this.L, this.g());
                n7 = this.O.getContext().getResources().getDisplayMetrics().widthPixels;
                object = this.O.j;
                int n9 = n7 - ((Rect)object).left - ((Rect)object).right;
                n7 = n8;
                if (n8 > n9) {
                    n7 = n9;
                }
                this.F(Math.max(n7, n6 - n4 - n5));
            } else if (n7 == -1) {
                this.F(n6 - n4 - n5);
            } else {
                this.F(n7);
            }
            n3 = t0.b((View)this.O) ? (n3 += n6 - n5 - this.z() - this.U()) : (n3 += n4 + this.U());
            this.l(n3);
        }

        public int U() {
            return this.N;
        }

        public boolean V(View view) {
            return view.isAttachedToWindow() && view.getGlobalVisibleRect(this.M);
        }

        @Override
        public void i(CharSequence charSequence) {
            this.K = charSequence;
        }

        @Override
        public void k(int n3) {
            this.N = n3;
        }

        @Override
        public void m(int n3, int n4) {
            boolean bl = this.c();
            this.T();
            this.I(2);
            super.e();
            ListView listView = this.h();
            listView.setChoiceMode(1);
            listView.setTextDirection(n3);
            listView.setTextAlignment(n4);
            this.Q(this.O.getSelectedItemPosition());
            if (!bl && (listView = this.O.getViewTreeObserver()) != null) {
                ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(this){
                    public final f c;
                    {
                        this.c = f3;
                    }

                    public void onGlobalLayout() {
                        f f3 = this.c;
                        if (!f3.V((View)f3.O)) {
                            this.c.dismiss();
                            return;
                        }
                        this.c.T();
                        f.super.e();
                    }
                };
                listView.addOnGlobalLayoutListener(onGlobalLayoutListener);
                this.K(new PopupWindow.OnDismissListener(this, onGlobalLayoutListener){
                    public final ViewTreeObserver.OnGlobalLayoutListener c;
                    public final f d;
                    {
                        this.d = f3;
                        this.c = onGlobalLayoutListener;
                    }

                    public void onDismiss() {
                        ViewTreeObserver viewTreeObserver = this.d.O.getViewTreeObserver();
                        if (viewTreeObserver != null) {
                            viewTreeObserver.removeGlobalOnLayoutListener(this.c);
                        }
                    }
                });
            }
        }

        @Override
        public CharSequence o() {
            return this.K;
        }

        @Override
        public void p(ListAdapter listAdapter) {
            super.p(listAdapter);
            this.L = listAdapter;
        }
    }

    public static interface g {
        public void b(Drawable var1);

        public boolean c();

        public int d();

        public void dismiss();

        public Drawable g();

        public void i(CharSequence var1);

        public void j(int var1);

        public void k(int var1);

        public void l(int var1);

        public void m(int var1, int var2);

        public int n();

        public CharSequence o();

        public void p(ListAdapter var1);
    }
}

