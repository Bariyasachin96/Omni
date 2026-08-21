/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  android.widget.Filter
 *  android.widget.Filter$FilterResults
 */
package t0;

import android.database.Cursor;
import android.widget.Filter;

public class b
extends Filter {
    public a a;

    public b(a a4) {
        this.a = a4;
    }

    public CharSequence convertResultToString(Object object) {
        return this.a.convertToString((Cursor)object);
    }

    public Filter.FilterResults performFiltering(CharSequence charSequence) {
        Cursor cursor = this.a.c(charSequence);
        charSequence = new Filter.FilterResults();
        if (cursor != null) {
            ((Filter.FilterResults)charSequence).count = cursor.getCount();
            ((Filter.FilterResults)charSequence).values = cursor;
            return charSequence;
        }
        ((Filter.FilterResults)charSequence).count = 0;
        ((Filter.FilterResults)charSequence).values = null;
        return charSequence;
    }

    public void publishResults(CharSequence charSequence, Filter.FilterResults object) {
        charSequence = this.a.b();
        object = object.values;
        if (object != null && object != charSequence) {
            this.a.a((Cursor)object);
        }
    }

    public static interface a {
        public void a(Cursor var1);

        public Cursor b();

        public Cursor c(CharSequence var1);

        public CharSequence convertToString(Cursor var1);
    }
}

