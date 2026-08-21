/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.database.DataSetObserver
 *  android.os.Handler
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.Filter
 *  android.widget.Filterable
 */
package t0;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import t0.b;

public abstract class a
extends BaseAdapter
implements Filterable,
b.a {
    public boolean c;
    public boolean d;
    public Cursor e;
    public Context f;
    public int g;
    public a h;
    public DataSetObserver i;
    public t0.b j;

    public a(Context context, Cursor cursor, boolean bl) {
        int n3 = bl ? 1 : 2;
        this.e(context, cursor, n3);
    }

    @Override
    public void a(Cursor cursor) {
        if ((cursor = this.i(cursor)) != null) {
            cursor.close();
        }
    }

    @Override
    public Cursor b() {
        return this.e;
    }

    @Override
    public abstract CharSequence convertToString(Cursor var1);

    public abstract void d(View var1, Context var2, Cursor var3);

    public void e(Context object, Cursor cursor, int n3) {
        boolean bl = false;
        if ((n3 & 1) == 1) {
            n3 |= 2;
            this.d = true;
        } else {
            this.d = false;
        }
        if (cursor != null) {
            bl = true;
        }
        this.e = cursor;
        this.c = bl;
        this.f = object;
        int n4 = bl ? cursor.getColumnIndexOrThrow("_id") : -1;
        this.g = n4;
        if ((n3 & 2) == 2) {
            this.h = new a(this);
            this.i = new b(this);
        } else {
            this.h = null;
            this.i = null;
        }
        if (bl) {
            object = this.h;
            if (object != null) {
                cursor.registerContentObserver((ContentObserver)object);
            }
            if ((object = this.i) != null) {
                cursor.registerDataSetObserver((DataSetObserver)object);
            }
        }
    }

    public abstract View f(Context var1, Cursor var2, ViewGroup var3);

    public abstract View g(Context var1, Cursor var2, ViewGroup var3);

    public int getCount() {
        Cursor cursor;
        if (this.c && (cursor = this.e) != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public View getDropDownView(int n3, View view, ViewGroup viewGroup) {
        if (this.c) {
            this.e.moveToPosition(n3);
            View view2 = view;
            if (view == null) {
                view2 = this.f(this.f, this.e, viewGroup);
            }
            this.d(view2, this.f, this.e);
            return view2;
        }
        return null;
    }

    public Filter getFilter() {
        if (this.j == null) {
            this.j = new t0.b(this);
        }
        return this.j;
    }

    public Object getItem(int n3) {
        Cursor cursor;
        if (this.c && (cursor = this.e) != null) {
            cursor.moveToPosition(n3);
            return this.e;
        }
        return null;
    }

    public long getItemId(int n3) {
        Cursor cursor;
        if (this.c && (cursor = this.e) != null && cursor.moveToPosition(n3)) {
            return this.e.getLong(this.g);
        }
        return 0L;
    }

    public View getView(int n3, View object, ViewGroup viewGroup) {
        if (this.c) {
            if (this.e.moveToPosition(n3)) {
                View view = object;
                if (object == null) {
                    view = this.g(this.f, this.e, viewGroup);
                }
                this.d(view, this.f, this.e);
                return view;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("couldn't move cursor to position ");
            ((StringBuilder)object).append(n3);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        throw new IllegalStateException("this should only be called when the cursor is valid");
    }

    public void h() {
        Cursor cursor;
        if (this.d && (cursor = this.e) != null && !cursor.isClosed()) {
            this.c = this.e.requery();
        }
    }

    public Cursor i(Cursor cursor) {
        a a4;
        Cursor cursor2 = this.e;
        if (cursor == cursor2) {
            return null;
        }
        if (cursor2 != null) {
            a4 = this.h;
            if (a4 != null) {
                cursor2.unregisterContentObserver((ContentObserver)a4);
            }
            if ((a4 = this.i) != null) {
                cursor2.unregisterDataSetObserver((DataSetObserver)a4);
            }
        }
        this.e = cursor;
        if (cursor != null) {
            a4 = this.h;
            if (a4 != null) {
                cursor.registerContentObserver((ContentObserver)a4);
            }
            if ((a4 = this.i) != null) {
                cursor.registerDataSetObserver((DataSetObserver)a4);
            }
            this.g = cursor.getColumnIndexOrThrow("_id");
            this.c = true;
            this.notifyDataSetChanged();
            return cursor2;
        }
        this.g = -1;
        this.c = false;
        this.notifyDataSetInvalidated();
        return cursor2;
    }

    public class a
    extends ContentObserver {
        public final a a;

        public a(a a4) {
            this.a = a4;
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean bl) {
            this.a.h();
        }
    }

    public class b
    extends DataSetObserver {
        public final a a;

        public b(a a4) {
            this.a = a4;
        }

        public void onChanged() {
            a a4 = this.a;
            a4.c = true;
            a4.notifyDataSetChanged();
        }

        public void onInvalidated() {
            a a4 = this.a;
            a4.c = false;
            a4.notifyDataSetInvalidated();
        }
    }
}

