/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.ColorStateList
 *  android.content.res.XmlResourceParser
 *  android.graphics.PorterDuff$Mode
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 *  android.view.InflateException
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package h;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.z;
import c.j;
import i.c;
import java.io.IOException;
import java.lang.reflect.Method;
import o0.x;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class g
extends MenuInflater {
    public static final Class[] e;
    public static final Class[] f;
    public final Object[] a;
    public final Object[] b;
    public Context c;
    public Object d;

    static {
        Class[] classArray = new Class[]{Context.class};
        e = classArray;
        f = classArray;
    }

    public g(Context context) {
        super(context);
        this.c = context;
        Object[] objectArray = new Object[]{context};
        this.a = objectArray;
        this.b = objectArray;
    }

    public final Object a(Object object) {
        if (object instanceof Activity) {
            return object;
        }
        Object object2 = object;
        if (object instanceof ContextWrapper) {
            object2 = this.a(((ContextWrapper)object).getBaseContext());
        }
        return object2;
    }

    public Object b() {
        if (this.d == null) {
            this.d = this.a(this.c);
        }
        return this.d;
    }

    public final void c(XmlPullParser object, AttributeSet attributeSet, Menu object2) {
        int n3;
        int n4;
        b b3;
        block27: {
            b3 = new b(this, (Menu)object2);
            n4 = object.getEventType();
            do {
                if (n4 == 2) {
                    object2 = object.getName();
                    if (((String)object2).equals("menu")) {
                        n4 = object.next();
                        break block27;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Expecting menu, got ");
                    ((StringBuilder)object).append((String)object2);
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                n4 = n3 = object.next();
            } while (n3 != 1);
            n4 = n3;
        }
        boolean bl = false;
        n3 = 0;
        Object object3 = null;
        while (!bl) {
            if (n4 != 1) {
                boolean bl2;
                if (n4 != 2) {
                    if (n4 != 3) {
                        bl2 = bl;
                        n4 = n3;
                        object2 = object3;
                    } else {
                        String string = object.getName();
                        if (n3 != 0 && string.equals(object3)) {
                            n4 = 0;
                            object2 = null;
                            bl2 = bl;
                        } else if (string.equals("group")) {
                            b3.h();
                            bl2 = bl;
                            n4 = n3;
                            object2 = object3;
                        } else if (string.equals("item")) {
                            bl2 = bl;
                            n4 = n3;
                            object2 = object3;
                            if (!b3.d()) {
                                object2 = b3.A;
                                if (object2 != null && ((o0.b)object2).a()) {
                                    b3.b();
                                    bl2 = bl;
                                    n4 = n3;
                                    object2 = object3;
                                } else {
                                    b3.a();
                                    bl2 = bl;
                                    n4 = n3;
                                    object2 = object3;
                                }
                            }
                        } else {
                            bl2 = bl;
                            n4 = n3;
                            object2 = object3;
                            if (string.equals("menu")) {
                                bl2 = true;
                                n4 = n3;
                                object2 = object3;
                            }
                        }
                    }
                } else if (n3 != 0) {
                    bl2 = bl;
                    n4 = n3;
                    object2 = object3;
                } else {
                    object2 = object.getName();
                    if (((String)object2).equals("group")) {
                        b3.f(attributeSet);
                        bl2 = bl;
                        n4 = n3;
                        object2 = object3;
                    } else if (((String)object2).equals("item")) {
                        b3.g(attributeSet);
                        bl2 = bl;
                        n4 = n3;
                        object2 = object3;
                    } else if (((String)object2).equals("menu")) {
                        this.c((XmlPullParser)object, attributeSet, (Menu)b3.b());
                        bl2 = bl;
                        n4 = n3;
                        object2 = object3;
                    } else {
                        n4 = 1;
                        bl2 = bl;
                    }
                }
                int n5 = object.next();
                bl = bl2;
                n3 = n4;
                object3 = object2;
                n4 = n5;
                continue;
            }
            throw new RuntimeException("Unexpected end of document");
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void inflate(int n3, Menu menu) {
        Throwable throwable222;
        int n4;
        int n5;
        int n6;
        XmlResourceParser xmlResourceParser;
        XmlResourceParser xmlResourceParser2;
        XmlResourceParser xmlResourceParser3;
        block11: {
            XmlResourceParser xmlResourceParser4;
            int n7;
            if (!(menu instanceof i0.a)) {
                super.inflate(n3, menu);
                return;
            }
            xmlResourceParser3 = null;
            xmlResourceParser2 = null;
            xmlResourceParser = null;
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            n6 = n7 = 0;
            n5 = n8;
            n4 = n9;
            try {
                xmlResourceParser = xmlResourceParser4 = this.c.getResources().getLayout(n3);
                n6 = n7;
                xmlResourceParser3 = xmlResourceParser4;
                n5 = n8;
                xmlResourceParser2 = xmlResourceParser4;
                n4 = n9;
                AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser4);
                xmlResourceParser = xmlResourceParser4;
                n6 = n7;
                xmlResourceParser3 = xmlResourceParser4;
                n5 = n8;
                xmlResourceParser2 = xmlResourceParser4;
                n4 = n9;
                n3 = n10;
                if (menu instanceof e) {
                    xmlResourceParser = xmlResourceParser4;
                    n6 = n7;
                    xmlResourceParser3 = xmlResourceParser4;
                    n5 = n8;
                    xmlResourceParser2 = xmlResourceParser4;
                    n4 = n9;
                    e e3 = (e)menu;
                    xmlResourceParser = xmlResourceParser4;
                    n6 = n7;
                    xmlResourceParser3 = xmlResourceParser4;
                    n5 = n8;
                    xmlResourceParser2 = xmlResourceParser4;
                    n4 = n9;
                    n3 = n10;
                    if (e3.H()) {
                        xmlResourceParser = xmlResourceParser4;
                        n6 = n7;
                        xmlResourceParser3 = xmlResourceParser4;
                        n5 = n8;
                        xmlResourceParser2 = xmlResourceParser4;
                        n4 = n9;
                        e3.i0();
                        n3 = 1;
                    }
                }
                xmlResourceParser = xmlResourceParser4;
                n6 = n3;
                xmlResourceParser3 = xmlResourceParser4;
                n5 = n3;
                xmlResourceParser2 = xmlResourceParser4;
                n4 = n3;
                this.c((XmlPullParser)xmlResourceParser4, attributeSet, menu);
                if (n3 != 0) {
                    ((e)menu).h0();
                }
                if (xmlResourceParser4 == null) break block11;
            }
            catch (Throwable throwable222) {}
            xmlResourceParser4.close();
        }
        return;
        if (n6 != 0) {
            ((e)menu).h0();
        }
        if (xmlResourceParser != null) {
            xmlResourceParser.close();
        }
        throw throwable222;
        catch (IOException iOException) {}
        xmlResourceParser = xmlResourceParser3;
        n6 = n5;
        {
            xmlResourceParser = xmlResourceParser3;
            n6 = n5;
            InflateException inflateException = new InflateException("Error inflating menu XML", (Throwable)iOException);
            xmlResourceParser = xmlResourceParser3;
            n6 = n5;
            throw inflateException;
            catch (XmlPullParserException xmlPullParserException) {}
            xmlResourceParser = xmlResourceParser2;
            n6 = n4;
            xmlResourceParser = xmlResourceParser2;
            n6 = n4;
            xmlResourceParser3 = new InflateException("Error inflating menu XML", (Throwable)xmlPullParserException);
            xmlResourceParser = xmlResourceParser2;
            n6 = n4;
            throw xmlResourceParser3;
        }
    }

    public static class a
    implements MenuItem.OnMenuItemClickListener {
        public static final Class[] c = new Class[]{MenuItem.class};
        public Object a;
        public Method b;

        public a(Object object, String string) {
            this.a = object;
            Class<?> clazz = object.getClass();
            try {
                this.b = clazz.getMethod(string, c);
                return;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't resolve menu item onClick handler ");
                stringBuilder.append(string);
                stringBuilder.append(" in class ");
                stringBuilder.append(clazz.getName());
                string = new InflateException(stringBuilder.toString());
                ((Throwable)((Object)string)).initCause(exception);
                throw string;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.b.getReturnType() == Boolean.TYPE) {
                    return (Boolean)this.b.invoke(this.a, menuItem);
                }
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
            this.b.invoke(this.a, menuItem);
            return true;
        }
    }

    public class b {
        public o0.b A;
        public CharSequence B;
        public CharSequence C;
        public ColorStateList D;
        public PorterDuff.Mode E;
        public final g F;
        public Menu a;
        public int b;
        public int c;
        public int d;
        public int e;
        public boolean f;
        public boolean g;
        public boolean h;
        public int i;
        public int j;
        public CharSequence k;
        public CharSequence l;
        public int m;
        public char n;
        public int o;
        public char p;
        public int q;
        public int r;
        public boolean s;
        public boolean t;
        public boolean u;
        public int v;
        public int w;
        public String x;
        public String y;
        public String z;

        public b(g g3, Menu menu) {
            this.F = g3;
            this.D = null;
            this.E = null;
            this.a = menu;
            this.h();
        }

        public void a() {
            this.h = true;
            this.i(this.a.add(this.b, this.i, this.j, this.k));
        }

        public SubMenu b() {
            this.h = true;
            SubMenu subMenu = this.a.addSubMenu(this.b, this.i, this.j, this.k);
            this.i(subMenu.getItem());
            return subMenu;
        }

        public final char c(String string) {
            if (string == null) {
                return '\u0000';
            }
            return string.charAt(0);
        }

        public boolean d() {
            return this.h;
        }

        public final Object e(String string, Class[] object, Object[] object2) {
            try {
                object = Class.forName(string, false, this.F.c.getClassLoader()).getConstructor((Class<?>)object);
                object.setAccessible(true);
                object = object.newInstance((Object[])object2);
                return object;
            }
            catch (Exception exception) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Cannot instantiate class: ");
                ((StringBuilder)object2).append(string);
                Log.w((String)"SupportMenuInflater", (String)((StringBuilder)object2).toString(), (Throwable)exception);
                return null;
            }
        }

        public void f(AttributeSet attributeSet) {
            attributeSet = this.F.c.obtainStyledAttributes(attributeSet, c.j.MenuGroup);
            this.b = attributeSet.getResourceId(c.j.MenuGroup_android_id, 0);
            this.c = attributeSet.getInt(c.j.MenuGroup_android_menuCategory, 0);
            this.d = attributeSet.getInt(c.j.MenuGroup_android_orderInCategory, 0);
            this.e = attributeSet.getInt(c.j.MenuGroup_android_checkableBehavior, 0);
            this.f = attributeSet.getBoolean(c.j.MenuGroup_android_visible, true);
            this.g = attributeSet.getBoolean(c.j.MenuGroup_android_enabled, true);
            attributeSet.recycle();
        }

        public void g(AttributeSet object) {
            String string;
            object = m0.u(this.F.c, (AttributeSet)object, c.j.MenuItem);
            this.i = ((m0)object).n(c.j.MenuItem_android_id, 0);
            this.j = ((m0)object).k(c.j.MenuItem_android_menuCategory, this.c) & 0xFFFF0000 | ((m0)object).k(c.j.MenuItem_android_orderInCategory, this.d) & 0xFFFF;
            this.k = ((m0)object).p(c.j.MenuItem_android_title);
            this.l = ((m0)object).p(c.j.MenuItem_android_titleCondensed);
            this.m = ((m0)object).n(c.j.MenuItem_android_icon, 0);
            this.n = this.c(((m0)object).o(c.j.MenuItem_android_alphabeticShortcut));
            this.o = ((m0)object).k(c.j.MenuItem_alphabeticModifiers, 4096);
            this.p = this.c(((m0)object).o(c.j.MenuItem_android_numericShortcut));
            this.q = ((m0)object).k(c.j.MenuItem_numericModifiers, 4096);
            int n3 = c.j.MenuItem_android_checkable;
            this.r = ((m0)object).s(n3) ? (int)(((m0)object).a(n3, false) ? 1 : 0) : this.e;
            this.s = ((m0)object).a(c.j.MenuItem_android_checked, false);
            this.t = ((m0)object).a(c.j.MenuItem_android_visible, this.f);
            this.u = ((m0)object).a(c.j.MenuItem_android_enabled, this.g);
            this.v = ((m0)object).k(c.j.MenuItem_showAsAction, -1);
            this.z = ((m0)object).o(c.j.MenuItem_android_onClick);
            this.w = ((m0)object).n(c.j.MenuItem_actionLayout, 0);
            this.x = ((m0)object).o(c.j.MenuItem_actionViewClass);
            this.y = string = ((m0)object).o(c.j.MenuItem_actionProviderClass);
            n3 = string != null ? 1 : 0;
            if (n3 != 0 && this.w == 0 && this.x == null) {
                this.A = (o0.b)this.e(string, f, this.F.b);
            } else {
                if (n3 != 0) {
                    Log.w((String)"SupportMenuInflater", (String)"Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.A = null;
            }
            this.B = ((m0)object).p(c.j.MenuItem_contentDescription);
            this.C = ((m0)object).p(c.j.MenuItem_tooltipText);
            n3 = c.j.MenuItem_iconTintMode;
            this.E = ((m0)object).s(n3) ? androidx.appcompat.widget.z.e(((m0)object).k(n3, -1), this.E) : null;
            n3 = c.j.MenuItem_iconTint;
            this.D = ((m0)object).s(n3) ? ((m0)object).c(n3) : null;
            ((m0)object).x();
            this.h = false;
        }

        public void h() {
            this.b = 0;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f = true;
            this.g = true;
        }

        public final void i(MenuItem menuItem) {
            Object object = menuItem.setChecked(this.s).setVisible(this.t).setEnabled(this.u);
            int n3 = this.r;
            boolean bl = false;
            boolean bl2 = n3 >= 1;
            object.setCheckable(bl2).setTitleCondensed(this.l).setIcon(this.m);
            n3 = this.v;
            if (n3 >= 0) {
                menuItem.setShowAsAction(n3);
            }
            if (this.z != null) {
                if (!this.F.c.isRestricted()) {
                    menuItem.setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener)new a(this.F.b(), this.z));
                } else {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
            }
            if (this.r >= 2) {
                if (menuItem instanceof androidx.appcompat.view.menu.g) {
                    ((androidx.appcompat.view.menu.g)menuItem).t(true);
                } else if (menuItem instanceof c) {
                    ((c)menuItem).h(true);
                }
            }
            if ((object = this.x) != null) {
                menuItem.setActionView((View)this.e((String)object, e, this.F.a));
                bl = true;
            }
            if ((n3 = this.w) > 0) {
                if (!bl) {
                    menuItem.setActionView(n3);
                } else {
                    Log.w((String)"SupportMenuInflater", (String)"Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if ((object = this.A) != null) {
                o0.x.a(menuItem, (o0.b)object);
            }
            o0.x.c(menuItem, this.B);
            o0.x.g(menuItem, this.C);
            o0.x.b(menuItem, this.n, this.o);
            o0.x.f(menuItem, this.p, this.q);
            object = this.E;
            if (object != null) {
                o0.x.e(menuItem, (PorterDuff.Mode)object);
            }
            if ((object = this.D) != null) {
                o0.x.d(menuItem, (ColorStateList)object);
            }
        }
    }
}

