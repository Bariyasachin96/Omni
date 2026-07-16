/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.os.Handler
 *  android.os.Message
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.ViewStub
 *  android.view.Window
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.CheckedTextView
 *  android.widget.CursorAdapter
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.SimpleCursorAdapter
 *  android.widget.TextView
 */
package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.appcompat.app.m;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import c.a;
import c.f;
import c.j;
import java.lang.ref.WeakReference;
import o0.x0;

public class AlertController {
    public NestedScrollView A;
    public int B = 0;
    public Drawable C;
    public ImageView D;
    public TextView E;
    public TextView F;
    public View G;
    public ListAdapter H;
    public int I = -1;
    public int J;
    public int K;
    public int L;
    public int M;
    public int N;
    public int O;
    public boolean P;
    public int Q = 0;
    public Handler R;
    public final View.OnClickListener S = new View.OnClickListener(this){
        public final AlertController c;
        {
            this.c = alertController;
        }

        public void onClick(View object) {
            Message message;
            AlertController alertController = this.c;
            if ((object = object == alertController.o && (message = alertController.q) != null ? Message.obtain((Message)message) : (object == alertController.s && (message = alertController.u) != null ? Message.obtain((Message)message) : (object == alertController.w && (object = alertController.y) != null ? Message.obtain((Message)object) : null))) != null) {
                object.sendToTarget();
            }
            object = this.c;
            object.R.obtainMessage(1, (Object)object.b).sendToTarget();
        }
    };
    public final Context a;
    public final m b;
    public final Window c;
    public final int d;
    public CharSequence e;
    public CharSequence f;
    public ListView g;
    public View h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public boolean n = false;
    public Button o;
    public CharSequence p;
    public Message q;
    public Drawable r;
    public Button s;
    public CharSequence t;
    public Message u;
    public Drawable v;
    public Button w;
    public CharSequence x;
    public Message y;
    public Drawable z;

    public AlertController(Context context, m m3, Window window) {
        this.a = context;
        this.b = m3;
        this.c = window;
        this.R = new c((DialogInterface)m3);
        context = context.obtainStyledAttributes(null, c.j.AlertDialog, c.a.alertDialogStyle, 0);
        this.J = context.getResourceId(c.j.AlertDialog_android_layout, 0);
        this.K = context.getResourceId(c.j.AlertDialog_buttonPanelSideLayout, 0);
        this.L = context.getResourceId(c.j.AlertDialog_listLayout, 0);
        this.M = context.getResourceId(c.j.AlertDialog_multiChoiceItemLayout, 0);
        this.N = context.getResourceId(c.j.AlertDialog_singleChoiceItemLayout, 0);
        this.O = context.getResourceId(c.j.AlertDialog_listItemLayout, 0);
        this.P = context.getBoolean(c.j.AlertDialog_showTitle, true);
        this.d = context.getDimensionPixelSize(c.j.AlertDialog_buttonIconDimen, 0);
        context.recycle();
        m3.k(1);
    }

    public static boolean a(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        view = (ViewGroup)view;
        int n3 = view.getChildCount();
        while (n3 > 0) {
            int n4;
            n3 = n4 = n3 - 1;
            if (!AlertController.a(view.getChildAt(n4))) continue;
            return true;
        }
        return false;
    }

    public static boolean y(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(c.a.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    public final void b(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public int c(int n3) {
        TypedValue typedValue = new TypedValue();
        this.a.getTheme().resolveAttribute(n3, typedValue, true);
        return typedValue.resourceId;
    }

    public ListView d() {
        return this.g;
    }

    public void e() {
        int n3 = this.i();
        this.b.setContentView(n3);
        this.x();
    }

    public boolean f(int n3, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.A;
        return nestedScrollView != null && nestedScrollView.t(keyEvent);
    }

    public boolean g(int n3, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.A;
        return nestedScrollView != null && nestedScrollView.t(keyEvent);
    }

    public final ViewGroup h(View view, View view2) {
        ViewParent viewParent;
        if (view == null) {
            view = view2;
            if (view2 instanceof ViewStub) {
                view = ((ViewStub)view2).inflate();
            }
            return (ViewGroup)view;
        }
        if (view2 != null && (viewParent = view2.getParent()) instanceof ViewGroup) {
            ((ViewGroup)viewParent).removeView(view2);
        }
        view2 = view;
        if (view instanceof ViewStub) {
            view2 = ((ViewStub)view).inflate();
        }
        return (ViewGroup)view2;
    }

    public final int i() {
        int n3 = this.K;
        if (n3 == 0) {
            return this.J;
        }
        if (this.Q == 1) {
            return n3;
        }
        return this.J;
    }

    public void j(int n3, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message, Drawable drawable) {
        Message message2 = message;
        if (message == null) {
            message2 = message;
            if (onClickListener != null) {
                message2 = this.R.obtainMessage(n3, (Object)onClickListener);
            }
        }
        if (n3 != -3) {
            if (n3 != -2) {
                if (n3 == -1) {
                    this.p = charSequence;
                    this.q = message2;
                    this.r = drawable;
                    return;
                }
                throw new IllegalArgumentException("Button does not exist");
            }
            this.t = charSequence;
            this.u = message2;
            this.v = drawable;
            return;
        }
        this.x = charSequence;
        this.y = message2;
        this.z = drawable;
    }

    public void k(View view) {
        this.G = view;
    }

    public void l(int n3) {
        this.C = null;
        this.B = n3;
        ImageView imageView = this.D;
        if (imageView != null) {
            if (n3 != 0) {
                imageView.setVisibility(0);
                this.D.setImageResource(this.B);
                return;
            }
            imageView.setVisibility(8);
        }
    }

    public void m(Drawable drawable) {
        this.C = drawable;
        this.B = 0;
        ImageView imageView = this.D;
        if (imageView != null) {
            if (drawable != null) {
                imageView.setVisibility(0);
                this.D.setImageDrawable(drawable);
                return;
            }
            imageView.setVisibility(8);
        }
    }

    public void n(CharSequence charSequence) {
        this.f = charSequence;
        TextView textView = this.F;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public final void o(ViewGroup viewGroup, View view, int n3, int n4) {
        View view2 = this.c.findViewById(c.f.scrollIndicatorUp);
        View view3 = this.c.findViewById(c.f.scrollIndicatorDown);
        x0.u0(view, n3, n4);
        if (view2 != null) {
            viewGroup.removeView(view2);
        }
        if (view3 != null) {
            viewGroup.removeView(view3);
        }
    }

    public void p(CharSequence charSequence) {
        this.e = charSequence;
        TextView textView = this.E;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void q(int n3) {
        this.h = null;
        this.i = n3;
        this.n = false;
    }

    public void r(View view) {
        this.h = view;
        this.i = 0;
        this.n = false;
    }

    public void s(View view, int n3, int n4, int n5, int n6) {
        this.h = view;
        this.i = 0;
        this.n = true;
        this.j = n3;
        this.k = n4;
        this.l = n5;
        this.m = n6;
    }

    public final void t(ViewGroup viewGroup) {
        int n3;
        int n4;
        Button button;
        this.o = button = (Button)viewGroup.findViewById(16908313);
        button.setOnClickListener(this.S);
        if (TextUtils.isEmpty((CharSequence)this.p) && this.r == null) {
            this.o.setVisibility(8);
            n4 = 0;
        } else {
            this.o.setText(this.p);
            button = this.r;
            if (button != null) {
                n4 = this.d;
                button.setBounds(0, 0, n4, n4);
                this.o.setCompoundDrawables(this.r, null, null, null);
            }
            this.o.setVisibility(0);
            n4 = 1;
        }
        this.s = button = (Button)viewGroup.findViewById(16908314);
        button.setOnClickListener(this.S);
        if (TextUtils.isEmpty((CharSequence)this.t) && this.v == null) {
            this.s.setVisibility(8);
        } else {
            this.s.setText(this.t);
            button = this.v;
            if (button != null) {
                n3 = this.d;
                button.setBounds(0, 0, n3, n3);
                this.s.setCompoundDrawables(this.v, null, null, null);
            }
            this.s.setVisibility(0);
            n4 |= 2;
        }
        this.w = button = (Button)viewGroup.findViewById(16908315);
        button.setOnClickListener(this.S);
        if (TextUtils.isEmpty((CharSequence)this.x) && this.z == null) {
            this.w.setVisibility(8);
        } else {
            this.w.setText(this.x);
            button = this.z;
            if (button != null) {
                n3 = this.d;
                button.setBounds(0, 0, n3, n3);
                this.w.setCompoundDrawables(this.z, null, null, null);
            }
            this.w.setVisibility(0);
            n4 |= 4;
        }
        if (AlertController.y(this.a)) {
            if (n4 == 1) {
                this.b(this.o);
            } else if (n4 == 2) {
                this.b(this.s);
            } else if (n4 == 4) {
                this.b(this.w);
            }
        }
        if (n4 != 0) {
            return;
        }
        viewGroup.setVisibility(8);
    }

    public final void u(ViewGroup viewGroup) {
        NestedScrollView nestedScrollView;
        this.A = nestedScrollView = (NestedScrollView)this.c.findViewById(c.f.scrollView);
        nestedScrollView.setFocusable(false);
        this.A.setNestedScrollingEnabled(false);
        nestedScrollView = (TextView)viewGroup.findViewById(16908299);
        this.F = nestedScrollView;
        if (nestedScrollView == null) {
            return;
        }
        CharSequence charSequence = this.f;
        if (charSequence != null) {
            nestedScrollView.setText(charSequence);
            return;
        }
        nestedScrollView.setVisibility(8);
        this.A.removeView((View)this.F);
        if (this.g != null) {
            viewGroup = (ViewGroup)this.A.getParent();
            int n3 = viewGroup.indexOfChild((View)this.A);
            viewGroup.removeViewAt(n3);
            viewGroup.addView((View)this.g, n3, new ViewGroup.LayoutParams(-1, -1));
            return;
        }
        viewGroup.setVisibility(8);
    }

    public final void v(ViewGroup viewGroup) {
        Object object = this.h;
        boolean bl = false;
        if (object == null) {
            object = this.i != 0 ? LayoutInflater.from((Context)this.a).inflate(this.i, viewGroup, false) : null;
        }
        if (object != null) {
            bl = true;
        }
        if (!bl || !AlertController.a(object)) {
            this.c.setFlags(131072, 131072);
        }
        if (bl) {
            FrameLayout frameLayout = (FrameLayout)this.c.findViewById(c.f.custom);
            frameLayout.addView(object, new ViewGroup.LayoutParams(-1, -1));
            if (this.n) {
                frameLayout.setPadding(this.j, this.k, this.l, this.m);
            }
            if (this.g != null) {
                ((LinearLayoutCompat.LayoutParams)viewGroup.getLayoutParams()).weight = 0.0f;
            }
            return;
        }
        viewGroup.setVisibility(8);
    }

    public final void w(ViewGroup viewGroup) {
        if (this.G != null) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
            viewGroup.addView(this.G, 0, layoutParams);
            this.c.findViewById(c.f.title_template).setVisibility(8);
            return;
        }
        this.D = (ImageView)this.c.findViewById(16908294);
        if (!TextUtils.isEmpty((CharSequence)this.e) && this.P) {
            viewGroup = (TextView)this.c.findViewById(c.f.alertTitle);
            this.E = viewGroup;
            viewGroup.setText(this.e);
            int n3 = this.B;
            if (n3 != 0) {
                this.D.setImageResource(n3);
                return;
            }
            viewGroup = this.C;
            if (viewGroup != null) {
                this.D.setImageDrawable((Drawable)viewGroup);
                return;
            }
            this.E.setPadding(this.D.getPaddingLeft(), this.D.getPaddingTop(), this.D.getPaddingRight(), this.D.getPaddingBottom());
            this.D.setVisibility(8);
            return;
        }
        this.c.findViewById(c.f.title_template).setVisibility(8);
        this.D.setVisibility(8);
        viewGroup.setVisibility(8);
    }

    public final void x() {
        View view = this.c.findViewById(c.f.parentPanel);
        int n3 = c.f.topPanel;
        Object object = view.findViewById(n3);
        int n4 = c.f.contentPanel;
        View view2 = view.findViewById(n4);
        int n5 = c.f.buttonPanel;
        Object object2 = view.findViewById(n5);
        view = (ViewGroup)view.findViewById(c.f.customPanel);
        this.v((ViewGroup)view);
        View view3 = view.findViewById(n3);
        View view4 = view.findViewById(n4);
        View view5 = view.findViewById(n5);
        object = this.h(view3, (View)object);
        view2 = this.h(view4, view2);
        object2 = this.h(view5, (View)object2);
        this.u((ViewGroup)view2);
        this.t((ViewGroup)object2);
        this.w((ViewGroup)object);
        n5 = view.getVisibility();
        n4 = 0;
        n5 = n5 != 8 ? 1 : 0;
        int n6 = object != null && object.getVisibility() != 8 ? 1 : 0;
        boolean bl = object2 != null && object2.getVisibility() != 8;
        if (!bl && view2 != null && (object2 = view2.findViewById(c.f.textSpacerNoButtons)) != null) {
            object2.setVisibility(0);
        }
        if (n6 != 0) {
            object2 = this.A;
            if (object2 != null) {
                object2.setClipToPadding(true);
            }
            object = this.f == null && this.g == null ? null : object.findViewById(c.f.titleDividerNoCustom);
            if (object != null) {
                object.setVisibility(0);
            }
        } else if (view2 != null && (object = view2.findViewById(c.f.textSpacerNoTitle)) != null) {
            object.setVisibility(0);
        }
        if ((object = this.g) instanceof RecycleListView) {
            ((RecycleListView)((Object)object)).setHasDecor(n6 != 0, bl);
        }
        if (n5 == 0) {
            object = this.g;
            if (object == null) {
                object = this.A;
            }
            if (object != null) {
                n5 = n4;
                if (bl) {
                    n5 = 2;
                }
                this.o((ViewGroup)view2, (View)object, n6 | n5, 3);
            }
        }
        if ((object = this.g) != null && (view2 = this.H) != null) {
            object.setAdapter((ListAdapter)view2);
            n5 = this.I;
            if (n5 > -1) {
                object.setItemChecked(n5, true);
                object.setSelection(n5);
            }
        }
    }

    public static class RecycleListView
    extends ListView {
        public final int c;
        public final int d;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, c.j.RecycleListView);
            this.d = context.getDimensionPixelOffset(c.j.RecycleListView_paddingBottomNoButtons, -1);
            this.c = context.getDimensionPixelOffset(c.j.RecycleListView_paddingTopNoTitle, -1);
        }

        public void setHasDecor(boolean bl, boolean bl2) {
            if (bl2 && bl) {
                return;
            }
            int n3 = this.getPaddingLeft();
            int n4 = bl ? this.getPaddingTop() : this.c;
            int n5 = this.getPaddingRight();
            int n6 = bl2 ? this.getPaddingBottom() : this.d;
            this.setPadding(n3, n4, n5, n6);
        }
    }

    public static class b {
        public int A;
        public int B;
        public int C;
        public int D;
        public boolean E = false;
        public boolean[] F;
        public boolean G;
        public boolean H;
        public int I = -1;
        public DialogInterface.OnMultiChoiceClickListener J;
        public Cursor K;
        public String L;
        public String M;
        public AdapterView.OnItemSelectedListener N;
        public boolean O = true;
        public final Context a;
        public final LayoutInflater b;
        public int c = 0;
        public Drawable d;
        public int e = 0;
        public CharSequence f;
        public View g;
        public CharSequence h;
        public CharSequence i;
        public Drawable j;
        public DialogInterface.OnClickListener k;
        public CharSequence l;
        public Drawable m;
        public DialogInterface.OnClickListener n;
        public CharSequence o;
        public Drawable p;
        public DialogInterface.OnClickListener q;
        public boolean r;
        public DialogInterface.OnCancelListener s;
        public DialogInterface.OnDismissListener t;
        public DialogInterface.OnKeyListener u;
        public CharSequence[] v;
        public ListAdapter w;
        public DialogInterface.OnClickListener x;
        public int y;
        public View z;

        public b(Context context) {
            this.a = context;
            this.r = true;
            this.b = (LayoutInflater)context.getSystemService("layout_inflater");
        }

        public void a(AlertController alertController) {
            int n3;
            Object object = this.g;
            if (object != null) {
                alertController.k((View)object);
            } else {
                object = this.f;
                if (object != null) {
                    alertController.p((CharSequence)object);
                }
                if ((object = this.d) != null) {
                    alertController.m((Drawable)object);
                }
                if ((n3 = this.c) != 0) {
                    alertController.l(n3);
                }
                if ((n3 = this.e) != 0) {
                    alertController.l(alertController.c(n3));
                }
            }
            object = this.h;
            if (object != null) {
                alertController.n((CharSequence)object);
            }
            if ((object = this.i) != null || this.j != null) {
                alertController.j(-1, (CharSequence)object, this.k, null, this.j);
            }
            object = this.l;
            if (object != null || this.m != null) {
                alertController.j(-2, (CharSequence)object, this.n, null, this.m);
            }
            if ((object = this.o) != null || this.p != null) {
                alertController.j(-3, (CharSequence)object, this.q, null, this.p);
            }
            if (this.v != null || this.K != null || this.w != null) {
                this.b(alertController);
            }
            if ((object = this.z) != null) {
                if (this.E) {
                    alertController.s((View)object, this.A, this.B, this.C, this.D);
                    return;
                }
                alertController.r((View)object);
                return;
            }
            n3 = this.y;
            if (n3 != 0) {
                alertController.q(n3);
            }
        }

        public final void b(AlertController alertController) {
            Object object;
            RecycleListView recycleListView = (RecycleListView)this.b.inflate(alertController.L, null);
            if (this.G) {
                object = this.K == null ? new ArrayAdapter(this, this.a, alertController.M, 16908308, this.v, recycleListView){
                    public final RecycleListView c;
                    public final b d;
                    {
                        this.d = b3;
                        this.c = recycleListView;
                        super(context, n3, n4, (Object[])charSequenceArray);
                    }

                    public View getView(int n3, View object, ViewGroup viewGroup) {
                        viewGroup = super.getView(n3, object, viewGroup);
                        object = this.d.F;
                        if (object != null && object[n3] != false) {
                            this.c.setItemChecked(n3, true);
                        }
                        return viewGroup;
                    }
                } : new CursorAdapter(this, this.a, this.K, false, recycleListView, alertController){
                    public final int c;
                    public final int d;
                    public final RecycleListView e;
                    public final AlertController f;
                    public final b g;
                    {
                        this.g = b3;
                        this.e = recycleListView;
                        this.f = alertController;
                        super(context, cursor, bl);
                        context = this.getCursor();
                        this.c = context.getColumnIndexOrThrow(b3.L);
                        this.d = context.getColumnIndexOrThrow(b3.M);
                    }

                    public void bindView(View object, Context context, Cursor cursor) {
                        ((CheckedTextView)object.findViewById(16908308)).setText((CharSequence)cursor.getString(this.c));
                        object = this.e;
                        int n3 = cursor.getPosition();
                        int n4 = cursor.getInt(this.d);
                        boolean bl = true;
                        if (n4 != 1) {
                            bl = false;
                        }
                        object.setItemChecked(n3, bl);
                    }

                    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                        return this.g.b.inflate(this.f.M, viewGroup, false);
                    }
                };
            } else {
                int n3 = this.H ? alertController.N : alertController.O;
                if (this.K != null) {
                    object = new SimpleCursorAdapter(this.a, n3, this.K, new String[]{this.L}, new int[]{16908308});
                } else {
                    object = this.w;
                    if (object == null) {
                        object = new d(this.a, n3, 16908308, this.v);
                    }
                }
            }
            alertController.H = object;
            alertController.I = this.I;
            if (this.x != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(this, alertController){
                    public final AlertController c;
                    public final b d;
                    {
                        this.d = b3;
                        this.c = alertController;
                    }

                    public void onItemClick(AdapterView adapterView, View view, int n3, long l3) {
                        this.d.x.onClick((DialogInterface)this.c.b, n3);
                        if (!this.d.H) {
                            this.c.b.dismiss();
                        }
                    }
                });
            } else if (this.J != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(this, recycleListView, alertController){
                    public final RecycleListView c;
                    public final AlertController d;
                    public final b e;
                    {
                        this.e = b3;
                        this.c = recycleListView;
                        this.d = alertController;
                    }

                    public void onItemClick(AdapterView object, View view, int n3, long l3) {
                        object = this.e.F;
                        if (object != null) {
                            object[n3] = (AdapterView)this.c.isItemChecked(n3);
                        }
                        this.e.J.onClick((DialogInterface)this.d.b, n3, this.c.isItemChecked(n3));
                    }
                });
            }
            object = this.N;
            if (object != null) {
                recycleListView.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)object);
            }
            if (this.H) {
                recycleListView.setChoiceMode(1);
            } else if (this.G) {
                recycleListView.setChoiceMode(2);
            }
            alertController.g = recycleListView;
        }
    }

    public static final class c
    extends Handler {
        public WeakReference a;

        public c(DialogInterface dialogInterface) {
            this.a = new WeakReference<DialogInterface>(dialogInterface);
        }

        public void handleMessage(Message message) {
            int n3 = message.what;
            if (n3 != -3 && n3 != -2 && n3 != -1) {
                if (n3 != 1) {
                    return;
                }
                ((DialogInterface)message.obj).dismiss();
                return;
            }
            ((DialogInterface.OnClickListener)message.obj).onClick((DialogInterface)this.a.get(), message.what);
        }
    }

    public static class d
    extends ArrayAdapter {
        public d(Context context, int n3, int n4, CharSequence[] charSequenceArray) {
            super(context, n3, n4, (Object[])charSequenceArray);
        }

        public long getItemId(int n3) {
            return n3;
        }

        public boolean hasStableIds() {
            return true;
        }
    }
}

