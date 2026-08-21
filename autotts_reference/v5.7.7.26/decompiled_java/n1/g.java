/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.PathMeasure
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.VectorDrawable
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package n1;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import f0.k;
import g0.d;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class g
extends n1.f {
    public static final PorterDuff.Mode m = PorterDuff.Mode.SRC_IN;
    public h d;
    public PorterDuffColorFilter e;
    public ColorFilter f;
    public boolean g;
    public boolean h = true;
    public Drawable.ConstantState i;
    public final float[] j = new float[9];
    public final Matrix k = new Matrix();
    public final Rect l = new Rect();

    public g() {
        this.d = new h();
    }

    public g(h h3) {
        this.d = h3;
        this.e = this.i(this.e, h3.c, h3.d);
    }

    public static int a(int n3, float f3) {
        return n3 & 0xFFFFFF | (int)((float)Color.alpha((int)n3) * f3) << 24;
    }

    public static g b(Resources resources, int n3, Resources.Theme theme) {
        g g3 = new g();
        g3.c = f0.h.e(resources, n3, theme);
        g3.i = new i(g3.c.getConstantState());
        return g3;
    }

    public static PorterDuff.Mode f(int n3, PorterDuff.Mode mode) {
        if (n3 != 3) {
            if (n3 != 5) {
                if (n3 != 9) {
                    switch (n3) {
                        default: {
                            return mode;
                        }
                        case 16: {
                            return PorterDuff.Mode.ADD;
                        }
                        case 15: {
                            return PorterDuff.Mode.SCREEN;
                        }
                        case 14: 
                    }
                    return PorterDuff.Mode.MULTIPLY;
                }
                return PorterDuff.Mode.SRC_ATOP;
            }
            return PorterDuff.Mode.SRC_IN;
        }
        return PorterDuff.Mode.SRC_OVER;
    }

    public Object c(String string) {
        return this.d.b.p.get(string);
    }

    public boolean canApplyTheme() {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.b(drawable);
        }
        return false;
    }

    public final void d(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        h h3 = this.d;
        g g3 = h3.b;
        ArrayDeque<Object> arrayDeque = new ArrayDeque<Object>();
        arrayDeque.push(g3.h);
        int n3 = xmlPullParser.getEventType();
        int n4 = xmlPullParser.getDepth();
        int n5 = 1;
        while (n3 != 1 && (xmlPullParser.getDepth() >= n4 + 1 || n3 != 3)) {
            int n6;
            if (n3 == 2) {
                Object object = xmlPullParser.getName();
                d d3 = (d)arrayDeque.peek();
                if ("path".equals(object)) {
                    object = new c();
                    ((c)object).g(resources, attributeSet, theme, xmlPullParser);
                    d3.b.add(object);
                    if (((f)object).getPathName() != null) {
                        g3.p.put(((f)object).getPathName(), object);
                    }
                    n5 = h3.a;
                    h3.a = ((f)object).d | n5;
                    n6 = 0;
                } else if ("clip-path".equals(object)) {
                    object = new b();
                    ((b)object).e(resources, attributeSet, theme, xmlPullParser);
                    d3.b.add(object);
                    if (((f)object).getPathName() != null) {
                        g3.p.put(((f)object).getPathName(), object);
                    }
                    n6 = h3.a;
                    h3.a = ((f)object).d | n6;
                    n6 = n5;
                } else {
                    n6 = n5;
                    if ("group".equals(object)) {
                        object = new d();
                        ((d)object).c(resources, attributeSet, theme, xmlPullParser);
                        d3.b.add(object);
                        arrayDeque.push(object);
                        if (((d)object).getGroupName() != null) {
                            g3.p.put(((d)object).getGroupName(), object);
                        }
                        n6 = h3.a;
                        h3.a = ((d)object).k | n6;
                        n6 = n5;
                    }
                }
            } else {
                n6 = n5;
                if (n3 == 3) {
                    n6 = n5;
                    if ("group".equals(xmlPullParser.getName())) {
                        arrayDeque.pop();
                        n6 = n5;
                    }
                }
            }
            n3 = xmlPullParser.next();
            n5 = n6;
        }
        if (n5 == 0) {
            return;
        }
        throw new XmlPullParserException("no path defined");
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        this.copyBounds(this.l);
        if (this.l.width() > 0 && this.l.height() > 0) {
            ColorFilter colorFilter = this.f;
            drawable = colorFilter;
            if (colorFilter == null) {
                drawable = this.e;
            }
            canvas.getMatrix(this.k);
            this.k.getValues(this.j);
            float f3 = Math.abs(this.j[0]);
            float f4 = Math.abs(this.j[4]);
            float f5 = Math.abs(this.j[1]);
            float f6 = Math.abs(this.j[3]);
            if (f5 != 0.0f || f6 != 0.0f) {
                f3 = 1.0f;
                f4 = 1.0f;
            }
            int n3 = (int)((float)this.l.width() * f3);
            int n4 = (int)((float)this.l.height() * f4);
            n3 = Math.min(2048, n3);
            int n5 = Math.min(2048, n4);
            if (n3 > 0 && n5 > 0) {
                n4 = canvas.save();
                colorFilter = this.l;
                canvas.translate((float)colorFilter.left, (float)colorFilter.top);
                if (this.e()) {
                    canvas.translate((float)this.l.width(), 0.0f);
                    canvas.scale(-1.0f, 1.0f);
                }
                this.l.offsetTo(0, 0);
                this.d.c(n3, n5);
                if (!this.h) {
                    this.d.j(n3, n5);
                } else if (!this.d.b()) {
                    this.d.j(n3, n5);
                    this.d.i();
                }
                this.d.d(canvas, (ColorFilter)drawable, this.l);
                canvas.restoreToCount(n4);
            }
        }
    }

    public final boolean e() {
        return this.isAutoMirrored() && h0.a.f(this) == 1;
    }

    public void g(boolean bl) {
        this.h = bl;
    }

    public int getAlpha() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return h0.a.d(drawable);
        }
        return this.d.b.getRootAlpha();
    }

    public int getChangingConfigurations() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.d.getChangingConfigurations();
    }

    public ColorFilter getColorFilter() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return h0.a.e(drawable);
        }
        return this.f;
    }

    public Drawable.ConstantState getConstantState() {
        if (this.c != null) {
            return new i(this.c.getConstantState());
        }
        this.d.a = this.getChangingConfigurations();
        return this.d;
    }

    public int getIntrinsicHeight() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return (int)this.d.b.j;
    }

    public int getIntrinsicWidth() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return (int)this.d.b.i;
    }

    public int getOpacity() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    public final void h(TypedArray object, XmlPullParser object2, Resources.Theme theme) {
        float f3;
        h h3 = this.d;
        g g3 = h3.b;
        h3.d = n1.g.f(f0.k.g(object, (XmlPullParser)object2, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        if ((theme = f0.k.c(object, (XmlPullParser)object2, theme, "tint", 1)) != null) {
            h3.c = theme;
        }
        h3.e = f0.k.a(object, (XmlPullParser)object2, "autoMirrored", 5, h3.e);
        g3.k = f0.k.f(object, (XmlPullParser)object2, "viewportWidth", 7, g3.k);
        g3.l = f3 = f0.k.f(object, (XmlPullParser)object2, "viewportHeight", 8, g3.l);
        if (!(g3.k <= 0.0f)) {
            if (!(f3 <= 0.0f)) {
                g3.i = object.getDimension(3, g3.i);
                g3.j = f3 = object.getDimension(2, g3.j);
                if (!(g3.i <= 0.0f)) {
                    if (!(f3 <= 0.0f)) {
                        g3.setAlpha(f0.k.f(object, (XmlPullParser)object2, "alpha", 4, g3.getAlpha()));
                        object = object.getString(0);
                        if (object != null) {
                            g3.n = object;
                            g3.p.put(object, g3);
                        }
                        return;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(object.getPositionDescription());
                    ((StringBuilder)object2).append("<vector> tag requires height > 0");
                    throw new XmlPullParserException(((StringBuilder)object2).toString());
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(object.getPositionDescription());
                ((StringBuilder)object2).append("<vector> tag requires width > 0");
                throw new XmlPullParserException(((StringBuilder)object2).toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(object.getPositionDescription());
            ((StringBuilder)object2).append("<vector> tag requires viewportHeight > 0");
            throw new XmlPullParserException(((StringBuilder)object2).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(object.getPositionDescription());
        ((StringBuilder)object2).append("<vector> tag requires viewportWidth > 0");
        throw new XmlPullParserException(((StringBuilder)object2).toString());
    }

    public PorterDuffColorFilter i(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList != null && mode != null) {
            return new PorterDuffColorFilter(colorStateList.getColorForState(this.getState(), 0), mode);
        }
        return null;
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
            return;
        }
        this.inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.g(drawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        h h3 = this.d;
        h3.b = new g();
        drawable = f0.k.k(resources, theme, attributeSet, n1.a.a);
        this.h((TypedArray)drawable, xmlPullParser, theme);
        drawable.recycle();
        h3.a = this.getChangingConfigurations();
        h3.k = true;
        this.d(resources, xmlPullParser, attributeSet, theme);
        this.e = this.i(this.e, h3.c, h3.d);
    }

    public void invalidateSelf() {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.invalidateSelf();
            return;
        }
        super.invalidateSelf();
    }

    public boolean isAutoMirrored() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return h0.a.h(drawable);
        }
        return this.d.e;
    }

    public boolean isStateful() {
        Object object = this.c;
        if (object != null) {
            return object.isStateful();
        }
        return super.isStateful() || (object = this.d) != null && (((h)((Object)object)).g() || (object = this.d.c) != null && object.isStateful());
        {
        }
    }

    public Drawable mutate() {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.g && super.mutate() == this) {
            this.d = new h(this.d);
            this.g = true;
        }
        return this;
    }

    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    public boolean onStateChange(int[] nArray) {
        boolean bl;
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setState(nArray);
        }
        h h3 = this.d;
        ColorStateList colorStateList = h3.c;
        if (colorStateList != null && (drawable = h3.d) != null) {
            this.e = this.i(this.e, colorStateList, (PorterDuff.Mode)drawable);
            this.invalidateSelf();
            bl = true;
        } else {
            bl = false;
        }
        if (h3.g() && h3.h(nArray)) {
            this.invalidateSelf();
            return true;
        }
        return bl;
    }

    public void scheduleSelf(Runnable runnable, long l3) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, l3);
            return;
        }
        super.scheduleSelf(runnable, l3);
    }

    public void setAlpha(int n3) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setAlpha(n3);
            return;
        }
        if (this.d.b.getRootAlpha() != n3) {
            this.d.b.setRootAlpha(n3);
            this.invalidateSelf();
        }
    }

    public void setAutoMirrored(boolean bl) {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.j(drawable, bl);
            return;
        }
        this.d.e = bl;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
            return;
        }
        this.f = colorFilter;
        this.invalidateSelf();
    }

    public void setTint(int n3) {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.n(drawable, n3);
            return;
        }
        this.setTintList(ColorStateList.valueOf((int)n3));
    }

    public void setTintList(ColorStateList colorStateList) {
        Object object = this.c;
        if (object != null) {
            h0.a.o(object, colorStateList);
            return;
        }
        object = this.d;
        if (object.c != colorStateList) {
            object.c = colorStateList;
            this.e = this.i(this.e, colorStateList, object.d);
            this.invalidateSelf();
        }
    }

    public void setTintMode(PorterDuff.Mode mode) {
        Object object = this.c;
        if (object != null) {
            h0.a.p(object, mode);
            return;
        }
        object = this.d;
        if (object.d != mode) {
            object.d = mode;
            this.e = this.i(this.e, object.c, mode);
            this.invalidateSelf();
        }
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setVisible(bl, bl2);
        }
        return super.setVisible(bl, bl2);
    }

    public void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
            return;
        }
        super.unscheduleSelf(runnable);
    }

    public static class b
    extends f {
        public b() {
        }

        public b(b b3) {
            super(b3);
        }

        private void f(TypedArray typedArray, XmlPullParser xmlPullParser) {
            String string = typedArray.getString(0);
            if (string != null) {
                this.b = string;
            }
            if ((string = typedArray.getString(1)) != null) {
                this.a = g0.d.d(string);
            }
            this.c = f0.k.g(typedArray, xmlPullParser, "fillType", 2, 0);
        }

        @Override
        public boolean c() {
            return true;
        }

        public void e(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (!f0.k.j(xmlPullParser, "pathData")) {
                return;
            }
            resources = f0.k.k(resources, theme, attributeSet, n1.a.d);
            this.f((TypedArray)resources, xmlPullParser);
            resources.recycle();
        }
    }

    public static class c
    extends f {
        public int[] e;
        public f0.d f;
        public float g = 0.0f;
        public f0.d h;
        public float i = 1.0f;
        public float j = 1.0f;
        public float k = 0.0f;
        public float l = 1.0f;
        public float m = 0.0f;
        public Paint.Cap n = Paint.Cap.BUTT;
        public Paint.Join o = Paint.Join.MITER;
        public float p = 4.0f;

        public c() {
        }

        public c(c c3) {
            super(c3);
            this.e = c3.e;
            this.f = c3.f;
            this.g = c3.g;
            this.i = c3.i;
            this.h = c3.h;
            this.c = c3.c;
            this.j = c3.j;
            this.k = c3.k;
            this.l = c3.l;
            this.m = c3.m;
            this.n = c3.n;
            this.o = c3.o;
            this.p = c3.p;
        }

        @Override
        public boolean a() {
            return this.h.i() || this.f.i();
            {
            }
        }

        @Override
        public boolean b(int[] nArray) {
            boolean bl = this.h.j(nArray);
            return this.f.j(nArray) | bl;
        }

        public final Paint.Cap e(int n3, Paint.Cap cap) {
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 != 2) {
                        return cap;
                    }
                    return Paint.Cap.SQUARE;
                }
                return Paint.Cap.ROUND;
            }
            return Paint.Cap.BUTT;
        }

        public final Paint.Join f(int n3, Paint.Join join) {
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 != 2) {
                        return join;
                    }
                    return Paint.Join.BEVEL;
                }
                return Paint.Join.ROUND;
            }
            return Paint.Join.MITER;
        }

        public void g(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            resources = f0.k.k(resources, theme, attributeSet, n1.a.c);
            this.h((TypedArray)resources, xmlPullParser, theme);
            resources.recycle();
        }

        public float getFillAlpha() {
            return this.j;
        }

        public int getFillColor() {
            return this.h.e();
        }

        public float getStrokeAlpha() {
            return this.i;
        }

        public int getStrokeColor() {
            return this.f.e();
        }

        public float getStrokeWidth() {
            return this.g;
        }

        public float getTrimPathEnd() {
            return this.l;
        }

        public float getTrimPathOffset() {
            return this.m;
        }

        public float getTrimPathStart() {
            return this.k;
        }

        public final void h(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
            this.e = null;
            if (!f0.k.j(xmlPullParser, "pathData")) {
                return;
            }
            String string = typedArray.getString(0);
            if (string != null) {
                this.b = string;
            }
            if ((string = typedArray.getString(2)) != null) {
                this.a = g0.d.d(string);
            }
            this.h = f0.k.e(typedArray, xmlPullParser, theme, "fillColor", 1, 0);
            this.j = f0.k.f(typedArray, xmlPullParser, "fillAlpha", 12, this.j);
            this.n = this.e(f0.k.g(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.n);
            this.o = this.f(f0.k.g(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.o);
            this.p = f0.k.f(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.p);
            this.f = f0.k.e(typedArray, xmlPullParser, theme, "strokeColor", 3, 0);
            this.i = f0.k.f(typedArray, xmlPullParser, "strokeAlpha", 11, this.i);
            this.g = f0.k.f(typedArray, xmlPullParser, "strokeWidth", 4, this.g);
            this.l = f0.k.f(typedArray, xmlPullParser, "trimPathEnd", 6, this.l);
            this.m = f0.k.f(typedArray, xmlPullParser, "trimPathOffset", 7, this.m);
            this.k = f0.k.f(typedArray, xmlPullParser, "trimPathStart", 5, this.k);
            this.c = f0.k.g(typedArray, xmlPullParser, "fillType", 13, this.c);
        }

        public void setFillAlpha(float f3) {
            this.j = f3;
        }

        public void setFillColor(int n3) {
            this.h.k(n3);
        }

        public void setStrokeAlpha(float f3) {
            this.i = f3;
        }

        public void setStrokeColor(int n3) {
            this.f.k(n3);
        }

        public void setStrokeWidth(float f3) {
            this.g = f3;
        }

        public void setTrimPathEnd(float f3) {
            this.l = f3;
        }

        public void setTrimPathOffset(float f3) {
            this.m = f3;
        }

        public void setTrimPathStart(float f3) {
            this.k = f3;
        }
    }

    public static class d
    extends e {
        public final Matrix a = new Matrix();
        public final ArrayList b = new ArrayList();
        public float c = 0.0f;
        public float d = 0.0f;
        public float e = 0.0f;
        public float f = 1.0f;
        public float g = 1.0f;
        public float h = 0.0f;
        public float i = 0.0f;
        public final Matrix j;
        public int k;
        public int[] l;
        public String m;

        public d() {
            super(null);
            this.j = new Matrix();
            this.m = null;
        }

        public d(d e3, o.a a4) {
            super(null);
            Object object;
            this.j = object = new Matrix();
            this.m = null;
            this.c = e3.c;
            this.d = e3.d;
            this.e = e3.e;
            this.f = e3.f;
            this.g = e3.g;
            this.h = e3.h;
            this.i = e3.i;
            this.l = e3.l;
            Object object2 = e3.m;
            this.m = object2;
            this.k = e3.k;
            if (object2 != null) {
                a4.put(object2, this);
            }
            object.set(e3.j);
            object2 = e3.b;
            for (int i3 = 0; i3 < ((ArrayList)object2).size(); ++i3) {
                block8: {
                    block7: {
                        block6: {
                            e3 = ((ArrayList)object2).get(i3);
                            if (e3 instanceof d) {
                                this.b.add(new d((d)e3, a4));
                                continue;
                            }
                            if (!(e3 instanceof c)) break block6;
                            e3 = new c((c)e3);
                            break block7;
                        }
                        if (!(e3 instanceof b)) break block8;
                        e3 = new b((b)e3);
                    }
                    this.b.add(e3);
                    object = ((f)e3).b;
                    if (object == null) continue;
                    a4.put(object, e3);
                    continue;
                }
                throw new IllegalStateException("Unknown object in the tree!");
            }
        }

        @Override
        public boolean a() {
            for (int i3 = 0; i3 < this.b.size(); ++i3) {
                if (!((e)this.b.get(i3)).a()) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean b(int[] nArray) {
            boolean bl = false;
            for (int i3 = 0; i3 < this.b.size(); ++i3) {
                bl |= ((e)this.b.get(i3)).b(nArray);
            }
            return bl;
        }

        public void c(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            resources = f0.k.k(resources, theme, attributeSet, n1.a.b);
            this.e((TypedArray)resources, xmlPullParser);
            resources.recycle();
        }

        public final void d() {
            this.j.reset();
            this.j.postTranslate(-this.d, -this.e);
            this.j.postScale(this.f, this.g);
            this.j.postRotate(this.c, 0.0f, 0.0f);
            this.j.postTranslate(this.h + this.d, this.i + this.e);
        }

        public final void e(TypedArray object, XmlPullParser xmlPullParser) {
            this.l = null;
            this.c = f0.k.f(object, xmlPullParser, "rotation", 5, this.c);
            this.d = object.getFloat(1, this.d);
            this.e = object.getFloat(2, this.e);
            this.f = f0.k.f(object, xmlPullParser, "scaleX", 3, this.f);
            this.g = f0.k.f(object, xmlPullParser, "scaleY", 4, this.g);
            this.h = f0.k.f(object, xmlPullParser, "translateX", 6, this.h);
            this.i = f0.k.f(object, xmlPullParser, "translateY", 7, this.i);
            if ((object = object.getString(0)) != null) {
                this.m = object;
            }
            this.d();
        }

        public String getGroupName() {
            return this.m;
        }

        public Matrix getLocalMatrix() {
            return this.j;
        }

        public float getPivotX() {
            return this.d;
        }

        public float getPivotY() {
            return this.e;
        }

        public float getRotation() {
            return this.c;
        }

        public float getScaleX() {
            return this.f;
        }

        public float getScaleY() {
            return this.g;
        }

        public float getTranslateX() {
            return this.h;
        }

        public float getTranslateY() {
            return this.i;
        }

        public void setPivotX(float f3) {
            if (f3 != this.d) {
                this.d = f3;
                this.d();
            }
        }

        public void setPivotY(float f3) {
            if (f3 != this.e) {
                this.e = f3;
                this.d();
            }
        }

        public void setRotation(float f3) {
            if (f3 != this.c) {
                this.c = f3;
                this.d();
            }
        }

        public void setScaleX(float f3) {
            if (f3 != this.f) {
                this.f = f3;
                this.d();
            }
        }

        public void setScaleY(float f3) {
            if (f3 != this.g) {
                this.g = f3;
                this.d();
            }
        }

        public void setTranslateX(float f3) {
            if (f3 != this.h) {
                this.h = f3;
                this.d();
            }
        }

        public void setTranslateY(float f3) {
            if (f3 != this.i) {
                this.i = f3;
                this.d();
            }
        }
    }

    public static abstract class e {
        public e() {
        }

        public /* synthetic */ e(a a4) {
            this();
        }

        public boolean a() {
            return false;
        }

        public boolean b(int[] nArray) {
            return false;
        }
    }

    public static abstract class f
    extends e {
        public d.b[] a = null;
        public String b;
        public int c = 0;
        public int d;

        public f() {
            super(null);
        }

        public f(f f3) {
            super(null);
            this.b = f3.b;
            this.d = f3.d;
            this.a = g0.d.f(f3.a);
        }

        public boolean c() {
            return false;
        }

        public void d(Path path) {
            path.reset();
            d.b[] bArray = this.a;
            if (bArray != null) {
                d.b.h(bArray, path);
            }
        }

        public d.b[] getPathData() {
            return this.a;
        }

        public String getPathName() {
            return this.b;
        }

        public void setPathData(d.b[] bArray) {
            if (!g0.d.b(this.a, bArray)) {
                this.a = g0.d.f(bArray);
                return;
            }
            g0.d.k(this.a, bArray);
        }
    }

    public static class g {
        public static final Matrix q = new Matrix();
        public final Path a;
        public final Path b;
        public final Matrix c = new Matrix();
        public Paint d;
        public Paint e;
        public PathMeasure f;
        public int g;
        public final d h;
        public float i = 0.0f;
        public float j = 0.0f;
        public float k = 0.0f;
        public float l = 0.0f;
        public int m = 255;
        public String n = null;
        public Boolean o = null;
        public final o.a p;

        public g() {
            this.p = new o.a();
            this.h = new d();
            this.a = new Path();
            this.b = new Path();
        }

        public g(g g3) {
            o.a a4;
            this.p = a4 = new o.a();
            this.h = new d(g3.h, a4);
            this.a = new Path(g3.a);
            this.b = new Path(g3.b);
            this.i = g3.i;
            this.j = g3.j;
            this.k = g3.k;
            this.l = g3.l;
            this.g = g3.g;
            this.m = g3.m;
            this.n = g3.n;
            String string = g3.n;
            if (string != null) {
                a4.put(string, this);
            }
            this.o = g3.o;
        }

        public static float a(float f3, float f4, float f5, float f6) {
            return f3 * f6 - f4 * f5;
        }

        public void b(Canvas canvas, int n3, int n4, ColorFilter colorFilter) {
            this.c(this.h, q, canvas, n3, n4, colorFilter);
        }

        public final void c(d d3, Matrix object, Canvas canvas, int n3, int n4, ColorFilter colorFilter) {
            d3.a.set(object);
            d3.a.preConcat(d3.j);
            canvas.save();
            for (int i3 = 0; i3 < d3.b.size(); ++i3) {
                object = (e)d3.b.get(i3);
                if (object instanceof d) {
                    this.c((d)object, d3.a, canvas, n3, n4, colorFilter);
                    continue;
                }
                if (!(object instanceof f)) continue;
                this.d(d3, (f)object, canvas, n3, n4, colorFilter);
            }
            canvas.restore();
        }

        public final void d(d object, f f3, Canvas canvas, int n3, int n4, ColorFilter colorFilter) {
            float f4 = (float)n3 / this.k;
            float f5 = (float)n4 / this.l;
            float f6 = Math.min(f4, f5);
            object = ((d)object).a;
            this.c.set((Matrix)object);
            this.c.postScale(f4, f5);
            f4 = this.e((Matrix)object);
            if (f4 != 0.0f) {
                Paint.Join join;
                f3.d(this.a);
                Path path = this.a;
                this.b.reset();
                if (f3.c()) {
                    colorFilter = this.b;
                    object = f3.c == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                    colorFilter.setFillType((Path.FillType)object);
                    this.b.addPath(path, this.c);
                    canvas.clipPath(this.b);
                    return;
                }
                f3 = (c)f3;
                float f7 = ((c)f3).k;
                if (f7 != 0.0f || ((c)f3).l != 1.0f) {
                    float f8 = ((c)f3).m;
                    float f9 = ((c)f3).l;
                    if (this.f == null) {
                        this.f = new PathMeasure();
                    }
                    this.f.setPath(this.a, false);
                    f5 = this.f.getLength();
                    f7 = (f7 + f8) % 1.0f * f5;
                    f9 = (f9 + f8) % 1.0f * f5;
                    path.reset();
                    if (f7 > f9) {
                        this.f.getSegment(f7, f5, path, true);
                        this.f.getSegment(0.0f, f9, path, true);
                    } else {
                        this.f.getSegment(f7, f9, path, true);
                    }
                    path.rLineTo(0.0f, 0.0f);
                }
                this.b.addPath(path, this.c);
                if (((c)f3).h.l()) {
                    object = ((c)f3).h;
                    if (this.e == null) {
                        path = new Paint(1);
                        this.e = path;
                        path.setStyle(Paint.Style.FILL);
                    }
                    path = this.e;
                    if (((f0.d)object).h()) {
                        object = ((f0.d)object).f();
                        object.setLocalMatrix(this.c);
                        path.setShader((Shader)object);
                        path.setAlpha(Math.round(((c)f3).j * 255.0f));
                    } else {
                        path.setShader(null);
                        path.setAlpha(255);
                        path.setColor(n1.g.a(((f0.d)object).e(), ((c)f3).j));
                    }
                    path.setColorFilter(colorFilter);
                    join = this.b;
                    object = f3.c == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                    join.setFillType((Path.FillType)object);
                    canvas.drawPath(this.b, (Paint)path);
                }
                if (((c)f3).f.l()) {
                    object = ((c)f3).f;
                    if (this.d == null) {
                        path = new Paint(1);
                        this.d = path;
                        path.setStyle(Paint.Style.STROKE);
                    }
                    path = this.d;
                    join = ((c)f3).o;
                    if (join != null) {
                        path.setStrokeJoin(join);
                    }
                    if ((join = ((c)f3).n) != null) {
                        path.setStrokeCap((Paint.Cap)join);
                    }
                    path.setStrokeMiter(((c)f3).p);
                    if (((f0.d)object).h()) {
                        object = ((f0.d)object).f();
                        object.setLocalMatrix(this.c);
                        path.setShader((Shader)object);
                        path.setAlpha(Math.round(((c)f3).i * 255.0f));
                    } else {
                        path.setShader(null);
                        path.setAlpha(255);
                        path.setColor(n1.g.a(((f0.d)object).e(), ((c)f3).i));
                    }
                    path.setColorFilter(colorFilter);
                    path.setStrokeWidth(((c)f3).g * (f6 * f4));
                    canvas.drawPath(this.b, (Paint)path);
                }
            }
        }

        public final float e(Matrix matrix) {
            float[] fArray;
            float[] fArray2 = fArray = new float[4];
            fArray[0] = 0.0f;
            fArray2[1] = 1.0f;
            fArray2[2] = 1.0f;
            fArray2[3] = 0.0f;
            matrix.mapVectors(fArray);
            float f3 = (float)Math.hypot(fArray[0], fArray[1]);
            float f4 = (float)Math.hypot(fArray[2], fArray[3]);
            float f5 = n1.g$g.a(fArray[0], fArray[1], fArray[2], fArray[3]);
            f4 = Math.max(f3, f4);
            if (f4 > 0.0f) {
                return Math.abs(f5) / f4;
            }
            return 0.0f;
        }

        public boolean f() {
            if (this.o == null) {
                this.o = this.h.a();
            }
            return this.o;
        }

        public boolean g(int[] nArray) {
            return this.h.b(nArray);
        }

        public float getAlpha() {
            return (float)this.getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.m;
        }

        public void setAlpha(float f3) {
            this.setRootAlpha((int)(f3 * 255.0f));
        }

        public void setRootAlpha(int n3) {
            this.m = n3;
        }
    }

    public static class h
    extends Drawable.ConstantState {
        public int a;
        public g b;
        public ColorStateList c = null;
        public PorterDuff.Mode d = m;
        public boolean e;
        public Bitmap f;
        public ColorStateList g;
        public PorterDuff.Mode h;
        public int i;
        public boolean j;
        public boolean k;
        public Paint l;

        public h() {
            this.b = new g();
        }

        public h(h h3) {
            if (h3 != null) {
                g g3;
                this.a = h3.a;
                this.b = g3 = new g(h3.b);
                if (h3.b.e != null) {
                    g3.e = new Paint(h3.b.e);
                }
                if (h3.b.d != null) {
                    this.b.d = new Paint(h3.b.d);
                }
                this.c = h3.c;
                this.d = h3.d;
                this.e = h3.e;
            }
        }

        public boolean a(int n3, int n4) {
            return n3 == this.f.getWidth() && n4 == this.f.getHeight();
        }

        public boolean b() {
            return !this.k && this.g == this.c && this.h == this.d && this.j == this.e && this.i == this.b.getRootAlpha();
        }

        public void c(int n3, int n4) {
            if (this.f != null && this.a(n3, n4)) {
                return;
            }
            this.f = Bitmap.createBitmap((int)n3, (int)n4, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            this.k = true;
        }

        public void d(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            colorFilter = this.e(colorFilter);
            canvas.drawBitmap(this.f, null, rect, (Paint)colorFilter);
        }

        public Paint e(ColorFilter colorFilter) {
            if (!this.f() && colorFilter == null) {
                return null;
            }
            if (this.l == null) {
                Paint paint;
                this.l = paint = new Paint();
                paint.setFilterBitmap(true);
            }
            this.l.setAlpha(this.b.getRootAlpha());
            this.l.setColorFilter(colorFilter);
            return this.l;
        }

        public boolean f() {
            return this.b.getRootAlpha() < 255;
        }

        public boolean g() {
            return this.b.f();
        }

        public int getChangingConfigurations() {
            return this.a;
        }

        public boolean h(int[] nArray) {
            boolean bl = this.b.g(nArray);
            this.k |= bl;
            return bl;
        }

        public void i() {
            this.g = this.c;
            this.h = this.d;
            this.i = this.b.getRootAlpha();
            this.j = this.e;
            this.k = false;
        }

        public void j(int n3, int n4) {
            this.f.eraseColor(0);
            Canvas canvas = new Canvas(this.f);
            this.b.b(canvas, n3, n4, null);
        }

        public Drawable newDrawable() {
            return new g(this);
        }

        public Drawable newDrawable(Resources resources) {
            return new g(this);
        }
    }

    public static class i
    extends Drawable.ConstantState {
        public final Drawable.ConstantState a;

        public i(Drawable.ConstantState constantState) {
            this.a = constantState;
        }

        public boolean canApplyTheme() {
            return this.a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            g g3 = new g();
            g3.c = (VectorDrawable)this.a.newDrawable();
            return g3;
        }

        public Drawable newDrawable(Resources resources) {
            g g3 = new g();
            g3.c = (VectorDrawable)this.a.newDrawable(resources);
            return g3;
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            g g3 = new g();
            g3.c = (VectorDrawable)this.a.newDrawable(resources, theme);
            return g3;
        }
    }
}

