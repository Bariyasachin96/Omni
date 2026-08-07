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
        charSequence.append("TransitionValues@");
        charSequence.append(Integer.toHexString(this.hashCode()));
        charSequence.append(":\n");
        Object object = charSequence.toString();
        charSequence = new StringBuilder();
        charSequence.append((String)object);
        charSequence.append("    view = ");
        charSequence.append(this.b);
        charSequence.append("\n");
        charSequence = charSequence.toString();
        object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("    values:");
        charSequence = ((StringBuilder)object).toString();
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

