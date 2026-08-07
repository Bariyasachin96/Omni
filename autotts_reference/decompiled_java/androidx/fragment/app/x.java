/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentState;
import androidx.fragment.app.u;
import androidx.fragment.app.w;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class x {
    public final ArrayList a = new ArrayList();
    public final HashMap b = new HashMap();
    public final HashMap c = new HashMap();
    public u d;

    public void A(u u3) {
        this.d = u3;
    }

    public FragmentState B(String string, FragmentState fragmentState) {
        if (fragmentState != null) {
            return this.c.put(string, fragmentState);
        }
        return (FragmentState)this.c.remove(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(Fragment fragment) {
        if (!this.a.contains(fragment)) {
            ArrayList arrayList = this.a;
            synchronized (arrayList) {
                this.a.add(fragment);
            }
            fragment.n = true;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment already added: ");
        stringBuilder.append(fragment);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void b() {
        this.b.values().removeAll(Collections.singleton(null));
    }

    public boolean c(String string) {
        return this.b.get(string) != null;
    }

    public void d(int n3) {
        for (w w3 : this.b.values()) {
            if (w3 == null) continue;
            w3.u(n3);
        }
    }

    public void e(String string, FileDescriptor object, PrintWriter printWriter, String[] stringArray) {
        int n3;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string);
        charSequence.append("    ");
        charSequence = charSequence.toString();
        if (!this.b.isEmpty()) {
            printWriter.print(string);
            printWriter.println("Active Fragments:");
            for (Object object2 : this.b.values()) {
                printWriter.print(string);
                if (object2 != null) {
                    object2 = ((w)object2).k();
                    printWriter.println(object2);
                    ((Fragment)object2).e((String)charSequence, (FileDescriptor)object, printWriter, stringArray);
                    continue;
                }
                printWriter.println("null");
            }
        }
        if ((n3 = this.a.size()) > 0) {
            printWriter.print(string);
            printWriter.println("Added Fragments:");
            for (int i3 = 0; i3 < n3; ++i3) {
                object = (Fragment)this.a.get(i3);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(((Fragment)object).toString());
            }
        }
    }

    public Fragment f(String object) {
        if ((object = (w)this.b.get(object)) != null) {
            return ((w)object).k();
        }
        return null;
    }

    public Fragment g(int n3) {
        for (int i3 = this.a.size() - 1; i3 >= 0; --i3) {
            Fragment fragment = (Fragment)this.a.get(i3);
            if (fragment == null || fragment.z != n3) continue;
            return fragment;
        }
        for (Object object : this.b.values()) {
            if (object == null) continue;
            object = ((w)object).k();
            if (((Fragment)object).z != n3) continue;
            return object;
        }
        return null;
    }

    public Fragment h(String string) {
        if (string != null) {
            for (int i3 = this.a.size() - 1; i3 >= 0; --i3) {
                Fragment fragment = (Fragment)this.a.get(i3);
                if (fragment == null || !string.equals(fragment.B)) continue;
                return fragment;
            }
        }
        if (string != null) {
            for (Object object : this.b.values()) {
                if (object == null) continue;
                object = ((w)object).k();
                if (!string.equals(((Fragment)object).B)) continue;
                return object;
            }
        }
        return null;
    }

    public Fragment i(String string) {
        for (Object object : this.b.values()) {
            if (object == null || (object = ((w)object).k().g(string)) == null) continue;
            return object;
        }
        return null;
    }

    public int j(Fragment fragment) {
        int n3;
        ViewGroup viewGroup = fragment.J;
        if (viewGroup == null) {
            return -1;
        }
        int n4 = this.a.indexOf(fragment);
        int n5 = n4 - 1;
        while (true) {
            n3 = n4;
            if (n5 < 0) break;
            fragment = (Fragment)this.a.get(n5);
            if (fragment.J == viewGroup && (fragment = fragment.K) != null) {
                return viewGroup.indexOfChild((View)fragment) + 1;
            }
            --n5;
        }
        while (++n3 < this.a.size()) {
            fragment = (Fragment)this.a.get(n3);
            if (fragment.J != viewGroup || (fragment = fragment.K) == null) continue;
            return viewGroup.indexOfChild((View)fragment);
        }
        return -1;
    }

    public List k() {
        ArrayList<w> arrayList = new ArrayList<w>();
        for (w w3 : this.b.values()) {
            if (w3 == null) continue;
            arrayList.add(w3);
        }
        return arrayList;
    }

    public List l() {
        ArrayList<Fragment> arrayList = new ArrayList<Fragment>();
        for (w w3 : this.b.values()) {
            if (w3 != null) {
                arrayList.add(w3.k());
                continue;
            }
            arrayList.add(null);
        }
        return arrayList;
    }

    public ArrayList m() {
        return new ArrayList(this.c.values());
    }

    public w n(String string) {
        return (w)this.b.get(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List o() {
        if (this.a.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = this.a;
        synchronized (arrayList) {
            return new ArrayList(this.a);
        }
    }

    public u p() {
        return this.d;
    }

    public FragmentState q(String string) {
        return (FragmentState)this.c.get(string);
    }

    public void r(w w3) {
        Fragment fragment = w3.k();
        if (!this.c(fragment.h)) {
            this.b.put(fragment.h, w3);
            if (fragment.F) {
                if (fragment.E) {
                    this.d.e(fragment);
                } else {
                    this.d.o(fragment);
                }
                fragment.F = false;
            }
            if (FragmentManager.I0(2)) {
                ((Object)fragment).toString();
            }
        }
    }

    public void s(w object) {
        object = ((w)object).k();
        if (((Fragment)object).E) {
            this.d.o((Fragment)object);
        }
        if ((w)this.b.put(((Fragment)object).h, null) != null && FragmentManager.I0(2)) {
            object.toString();
        }
    }

    public void t() {
        Object object = this.a;
        int n3 = ((ArrayList)object).size();
        int n4 = 0;
        while (n4 < n3) {
            Object object2 = ((ArrayList)object).get(n4);
            int n5 = n4 + 1;
            object2 = (Fragment)object2;
            object2 = (w)this.b.get(((Fragment)object2).h);
            n4 = n5;
            if (object2 == null) continue;
            ((w)object2).m();
            n4 = n5;
        }
        for (Object object2 : this.b.values()) {
            if (object2 == null) continue;
            ((w)object2).m();
            object = ((w)object2).k();
            if (!((Fragment)object).o || ((Fragment)object).Z()) continue;
            if (((Fragment)object).p && !this.c.containsKey(((Fragment)object).h)) {
                ((w)object2).s();
            }
            this.s((w)object2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void u(Fragment fragment) {
        ArrayList arrayList = this.a;
        synchronized (arrayList) {
            this.a.remove(fragment);
        }
        fragment.n = false;
    }

    public void v() {
        this.b.clear();
    }

    public void w(List object) {
        this.a.clear();
        if (object != null) {
            Object object2 = object.iterator();
            while (object2.hasNext()) {
                object = (String)object2.next();
                Fragment fragment = this.f((String)object);
                if (fragment != null) {
                    if (FragmentManager.I0(2)) {
                        ((Object)fragment).toString();
                    }
                    this.a(fragment);
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("No instantiated fragment for (");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(")");
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }
        }
    }

    public void x(ArrayList arrayList) {
        this.c.clear();
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = arrayList.get(i3);
            object = (FragmentState)object;
            this.c.put(((FragmentState)object).d, object);
        }
    }

    public ArrayList y() {
        ArrayList<String> arrayList = new ArrayList<String>(this.b.size());
        for (w w3 : this.b.values()) {
            if (w3 == null) continue;
            Fragment fragment = w3.k();
            w3.s();
            arrayList.add(fragment.h);
            if (!FragmentManager.I0(2)) continue;
            ((Object)fragment).toString();
            Objects.toString(fragment.d);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayList z() {
        ArrayList arrayList = this.a;
        synchronized (arrayList) {
            Throwable throwable2;
            block6: {
                try {
                    if (this.a.isEmpty()) {
                        return null;
                    }
                }
                catch (Throwable throwable2) {
                    break block6;
                }
                ArrayList<String> arrayList2 = new ArrayList<String>(this.a.size());
                ArrayList arrayList3 = this.a;
                int n3 = arrayList3.size();
                int n4 = 0;
                while (true) {
                    if (n4 >= n3) {
                        return arrayList2;
                    }
                    Object object = arrayList3.get(n4);
                    int n5 = n4 + 1;
                    object = (Fragment)object;
                    arrayList2.add(((Fragment)object).h);
                    n4 = n5;
                    if (!FragmentManager.I0(2)) continue;
                    object.toString();
                    n4 = n5;
                }
            }
            throw throwable2;
        }
    }
}

