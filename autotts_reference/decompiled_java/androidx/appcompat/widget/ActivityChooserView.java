/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.database.DataSetObserver
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.BaseAdapter
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.c;
import androidx.appcompat.widget.c0;
import androidx.appcompat.widget.m0;
import c.d;
import c.h;
import c.j;
import o0.b;
import o0.x0;
import p0.s;

public class ActivityChooserView
extends ViewGroup {
    public final f c;
    public final g d;
    public final View e;
    public final Drawable f;
    public final FrameLayout g;
    public final ImageView h;
    public final FrameLayout i;
    public final ImageView j;
    public final int k;
    public b l;
    public final DataSetObserver m = new DataSetObserver(this){
        public final ActivityChooserView a;
        {
            this.a = activityChooserView;
        }

        public void onChanged() {
            super.onChanged();
            this.a.c.notifyDataSetChanged();
        }

        public void onInvalidated() {
            super.onInvalidated();
            this.a.c.notifyDataSetInvalidated();
        }
    };
    public final ViewTreeObserver.OnGlobalLayoutListener n = new ViewTreeObserver.OnGlobalLayoutListener(this){
        public final ActivityChooserView c;
        {
            this.c = activityChooserView;
        }

        public void onGlobalLayout() {
            if (this.c.b()) {
                if (!this.c.isShown()) {
                    this.c.getListPopupWindow().dismiss();
                    return;
                }
                this.c.getListPopupWindow().e();
                b b3 = this.c.l;
                if (b3 != null) {
                    b3.j(true);
                }
            }
        }
    };
    public ListPopupWindow o;
    public PopupWindow.OnDismissListener p;
    public boolean q;
    public int r = 4;
    public boolean s;
    public int t;

    public ActivityChooserView(Context context) {
        this(context, null);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(Context context, AttributeSet object, int n3) {
        super(context, object, n3);
        Object object2 = c.j.ActivityChooserView;
        Object object3 = context.obtainStyledAttributes(object, object2, n3, 0);
        x0.f0((View)this, context, object2, object, object3, n3, 0);
        this.r = object3.getInt(c.j.ActivityChooserView_initialActivityCount, 4);
        object = object3.getDrawable(c.j.ActivityChooserView_expandActivityOverflowButtonDrawable);
        object3.recycle();
        LayoutInflater.from((Context)this.getContext()).inflate(c.g.abc_activity_chooser_view, (ViewGroup)this, true);
        object3 = new g(this);
        this.d = object3;
        object2 = this.findViewById(c.f.activity_chooser_view_content);
        this.e = (View)object2;
        this.f = object2.getBackground();
        object2 = (FrameLayout)this.findViewById(c.f.default_activity_button);
        this.i = (FrameLayout)object2;
        object2.setOnClickListener((View.OnClickListener)object3);
        object2.setOnLongClickListener((View.OnLongClickListener)object3);
        n3 = c.f.image;
        this.j = (ImageView)object2.findViewById(n3);
        object2 = (FrameLayout)this.findViewById(c.f.expand_activities_button);
        object2.setOnClickListener((View.OnClickListener)object3);
        object2.setAccessibilityDelegate(new View.AccessibilityDelegate(this){
            public final ActivityChooserView a;
            {
                this.a = activityChooserView;
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                p0.s.L0(accessibilityNodeInfo).e0(true);
            }
        });
        object2.setOnTouchListener((View.OnTouchListener)new c0(this, (View)object2){
            public final ActivityChooserView l;
            {
                this.l = activityChooserView;
                super(view);
            }

            @Override
            public i.f b() {
                return this.l.getListPopupWindow();
            }

            @Override
            public boolean c() {
                this.l.c();
                return true;
            }

            @Override
            public boolean d() {
                this.l.a();
                return true;
            }
        });
        this.g = (FrameLayout)object2;
        object3 = (ImageView)object2.findViewById(n3);
        this.h = object3;
        object3.setImageDrawable((Drawable)object);
        object = new f(this);
        this.c = object;
        object.registerDataSetObserver(new DataSetObserver(this){
            public final ActivityChooserView a;
            {
                this.a = activityChooserView;
            }

            public void onChanged() {
                super.onChanged();
                this.a.e();
            }
        });
        context = context.getResources();
        this.k = Math.max(context.getDisplayMetrics().widthPixels / 2, context.getDimensionPixelSize(c.d.abc_config_prefDialogWidth));
    }

    public boolean a() {
        if (this.b()) {
            this.getListPopupWindow().dismiss();
            ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeGlobalOnLayoutListener(this.n);
            }
        }
        return true;
    }

    public boolean b() {
        return this.getListPopupWindow().c();
    }

    public boolean c() {
        if (!this.b() && this.s) {
            this.q = false;
            this.d(this.r);
            return true;
        }
        return false;
    }

    public void d(int n3) {
        this.c.b();
        throw new IllegalStateException("No data model. Did you call #setDataModel?");
    }

    public void e() {
        if (this.c.getCount() > 0) {
            this.g.setEnabled(true);
        } else {
            this.g.setEnabled(false);
        }
        int n3 = this.c.a();
        int n4 = this.c.d();
        if (n3 != 1 && (n3 <= 1 || n4 <= 0)) {
            this.i.setVisibility(8);
        } else {
            this.i.setVisibility(0);
            Object object = this.c.c();
            PackageManager packageManager = this.getContext().getPackageManager();
            this.j.setImageDrawable(object.loadIcon(packageManager));
            if (this.t != 0) {
                object = object.loadLabel(packageManager);
                object = this.getContext().getString(this.t, new Object[]{object});
                this.i.setContentDescription((CharSequence)object);
            }
        }
        if (this.i.getVisibility() == 0) {
            this.e.setBackgroundDrawable(this.f);
            return;
        }
        this.e.setBackgroundDrawable(null);
    }

    public c getDataModel() {
        this.c.b();
        return null;
    }

    public ListPopupWindow getListPopupWindow() {
        if (this.o == null) {
            ListPopupWindow listPopupWindow;
            this.o = listPopupWindow = new ListPopupWindow(this.getContext());
            listPopupWindow.p((ListAdapter)this.c);
            this.o.D((View)this);
            this.o.J(true);
            this.o.L(this.d);
            this.o.K(this.d);
        }
        return this.o;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.c.b();
        this.s = true;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.c.b();
        ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.n);
        }
        if (this.b()) {
            this.a();
        }
        this.s = false;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        this.e.layout(0, 0, n5 - n3, n6 - n4);
        if (!this.b()) {
            this.a();
        }
    }

    public void onMeasure(int n3, int n4) {
        View view = this.e;
        int n5 = n4;
        if (this.i.getVisibility() != 0) {
            n5 = View.MeasureSpec.makeMeasureSpec((int)View.MeasureSpec.getSize((int)n4), (int)0x40000000);
        }
        this.measureChild(view, n3, n5);
        this.setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void setActivityChooserModel(c c3) {
        this.c.f(c3);
        if (this.b()) {
            this.a();
            this.c();
        }
    }

    public void setDefaultActionButtonContentDescription(int n3) {
        this.t = n3;
    }

    public void setExpandActivityOverflowButtonContentDescription(int n3) {
        String string = this.getContext().getString(n3);
        this.h.setContentDescription((CharSequence)string);
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.h.setImageDrawable(drawable);
    }

    public void setInitialActivityCount(int n3) {
        this.r = n3;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.p = onDismissListener;
    }

    public void setProvider(b b3) {
        this.l = b3;
    }

    public static class InnerLayout
    extends LinearLayout {
        public static final int[] c = new int[]{16842964};

        public InnerLayout(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = m0.u((Context)object, attributeSet, c);
            this.setBackgroundDrawable(((m0)object).g(0));
            ((m0)object).x();
        }
    }

    public class f
    extends BaseAdapter {
        public int c;
        public boolean d;
        public boolean e;
        public boolean f;
        public final ActivityChooserView g;

        public f(ActivityChooserView activityChooserView) {
            this.g = activityChooserView;
            this.c = 4;
        }

        public int a() {
            throw null;
        }

        public c b() {
            return null;
        }

        public ResolveInfo c() {
            throw null;
        }

        public int d() {
            throw null;
        }

        public boolean e() {
            return this.d;
        }

        public void f(c c3) {
            this.g.c.b();
            this.notifyDataSetChanged();
        }

        public int getCount() {
            throw null;
        }

        public Object getItem(int n3) {
            if ((n3 = this.getItemViewType(n3)) != 0) {
                if (n3 == 1) {
                    return null;
                }
                throw new IllegalArgumentException();
            }
            if (!this.d) {
                throw null;
            }
            throw null;
        }

        public long getItemId(int n3) {
            return n3;
        }

        public int getItemViewType(int n3) {
            if (this.f && n3 == this.getCount() - 1) {
                return 1;
            }
            return 0;
        }

        public View getView(int n3, View view, ViewGroup viewGroup) {
            View view2;
            block8: {
                block7: {
                    int n4 = this.getItemViewType(n3);
                    if (n4 != 0) {
                        if (n4 == 1) {
                            if (view != null && view.getId() == 1) {
                                return view;
                            }
                            view = LayoutInflater.from((Context)this.g.getContext()).inflate(c.g.abc_activity_chooser_view_list_item, viewGroup, false);
                            view.setId(1);
                            ((TextView)view.findViewById(c.f.title)).setText((CharSequence)this.g.getContext().getString(c.h.abc_activity_chooser_view_see_all));
                            return view;
                        }
                        throw new IllegalArgumentException();
                    }
                    if (view == null) break block7;
                    view2 = view;
                    if (view.getId() == c.f.list_item) break block8;
                }
                view2 = LayoutInflater.from((Context)this.g.getContext()).inflate(c.g.abc_activity_chooser_view_list_item, viewGroup, false);
            }
            viewGroup = this.g.getContext().getPackageManager();
            ImageView imageView = (ImageView)view2.findViewById(c.f.icon);
            view = (ResolveInfo)this.getItem(n3);
            imageView.setImageDrawable(view.loadIcon((PackageManager)viewGroup));
            ((TextView)view2.findViewById(c.f.title)).setText(view.loadLabel((PackageManager)viewGroup));
            if (this.d && n3 == 0 && this.e) {
                view2.setActivated(true);
                return view2;
            }
            view2.setActivated(false);
            return view2;
        }

        public int getViewTypeCount() {
            return 3;
        }
    }

    public class g
    implements AdapterView.OnItemClickListener,
    View.OnClickListener,
    View.OnLongClickListener,
    PopupWindow.OnDismissListener {
        public final ActivityChooserView c;

        public g(ActivityChooserView activityChooserView) {
            this.c = activityChooserView;
        }

        public final void a() {
            PopupWindow.OnDismissListener onDismissListener = this.c.p;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }

        public void onClick(View view) {
            ActivityChooserView activityChooserView = this.c;
            if (view != activityChooserView.i) {
                if (view == activityChooserView.g) {
                    activityChooserView.q = false;
                    activityChooserView.d(activityChooserView.r);
                    return;
                }
                throw new IllegalArgumentException();
            }
            activityChooserView.a();
            this.c.c.c();
            this.c.c.b();
            throw null;
        }

        public void onDismiss() {
            this.a();
            b b3 = this.c.l;
            if (b3 != null) {
                b3.j(false);
            }
        }

        public void onItemClick(AdapterView object, View view, int n3, long l3) {
            int n4 = ((f)object.getAdapter()).getItemViewType(n3);
            if (n4 != 0) {
                if (n4 == 1) {
                    this.c.d(Integer.MAX_VALUE);
                    return;
                }
                throw new IllegalArgumentException();
            }
            this.c.a();
            object = this.c;
            if (object.q) {
                if (n3 <= 0) {
                    return;
                }
                object.c.b();
                throw null;
            }
            object.c.e();
            this.c.c.b();
            throw null;
        }

        public boolean onLongClick(View object) {
            ActivityChooserView activityChooserView = this.c;
            if (object == activityChooserView.i) {
                if (activityChooserView.c.getCount() > 0) {
                    object = this.c;
                    ((ActivityChooserView)((Object)object)).q = true;
                    ((ActivityChooserView)((Object)object)).d(((ActivityChooserView)((Object)object)).r);
                }
                return true;
            }
            throw new IllegalArgumentException();
        }
    }
}

