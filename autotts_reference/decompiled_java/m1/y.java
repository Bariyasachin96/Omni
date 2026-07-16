/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package m1;

import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class y {
    public final Map a = new HashMap();
    public View b;
    public final ArrayList c = new ArrayList();

    public y(View view) {
        this.b = view;
    }

    public boolean equals(Object object) {
        if (object instanceof y) {
            View view = this.b;
            object = (y)object;
            if (view == ((y)object).b && this.a.equals(((y)object).a)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.b.hashCode() * 31 + this.a.hashCode();
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("TransitionValues@");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.hashCode()));
        ((StringBuilder)charSequence).append(":\n");
        charSequence = ((StringBuilder)charSequence).toString();
        Object object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("    view = ");
        ((StringBuilder)object).append(this.b);
        ((StringBuilder)object).append("\n");
        object = ((StringBuilder)object).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("    values:");
        charSequence = ((StringBuilder)charSequence).toString();
        for (String string : this.a.keySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("    ");
            stringBuilder.append(string);
            stringBuilder.append(": ");
            stringBuilder.append(this.a.get(string));
            stringBuilder.append("\n");
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }
}

