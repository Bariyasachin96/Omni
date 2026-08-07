/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ArgbEvaluator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Animatable
 *  android.graphics.drawable.AnimatedVectorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 */
package n1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import f0.h;
import f0.k;
import java.util.ArrayList;
import java.util.Collection;
import n1.a;
import n1.b;
import n1.e;
import n1.f;
import n1.g;
import org.xmlpull.v1.XmlPullParser;

public class c
extends f
implements Animatable {
    public c d;
    public Context e;
    public ArgbEvaluator f = null;
    public d g;
    public Animator.AnimatorListener h = null;
    public ArrayList i = null;
    public final Drawable.Callback j;

    public c() {
        this(null, null, null);
    }

    public c(Context context) {
        this(context, null, null);
    }

    public c(Context context, c c3, Resources resources) {
        Drawable.Callback callback;
        this.j = callback = new Drawable.Callback(this){
            public final c c;
            {
                this.c = c3;
            }

            public void invalidateDrawable(Drawable drawable) {
                this.c.invalidateSelf();
            }

            public void scheduleDrawable(Drawable drawable, Runnable runnable, long l3) {
                this.c.scheduleSelf(runnable, l3);
            }

            public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                this.c.unscheduleSelf(runnable);
            }
        };
        this.e = context;
        if (c3 != null) {
            this.d = c3;
            return;
        }
        this.d = new c(context, c3, callback, resources);
    }

    public static c a(Context context, int n3) {
        c c3 = new c(context);
        context = f0.h.e(context.getResources(), n3, context.getTheme());
        c3.c = context;
        context.setCallback(c3.j);
        c3.g = new d(c3.c.getConstantState());
        return c3;
    }

    public static void c(AnimatedVectorDrawable animatedVectorDrawable, b b3) {
        animatedVectorDrawable.registerAnimationCallback(b3.a());
    }

    public static boolean g(AnimatedVectorDrawable animatedVectorDrawable, b b3) {
        return animatedVectorDrawable.unregisterAnimationCallback(b3.a());
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.a(drawable, theme);
        }
    }

    public void b(b b3) {
        block7: {
            block6: {
                Drawable drawable = this.c;
                if (drawable != null) {
                    n1.c.c((AnimatedVectorDrawable)drawable, b3);
                    return;
                }
                if (b3 == null) break block6;
                if (this.i == null) {
                    this.i = new ArrayList();
                }
                if (!this.i.contains(b3)) break block7;
            }
            return;
        }
        this.i.add(b3);
        if (this.h == null) {
            this.h = new AnimatorListenerAdapter(this){
                public final c a;
                {
                    this.a = c3;
                }

                public void onAnimationEnd(Animator object) {
                    object = new ArrayList(this.a.i);
                    int n3 = ((ArrayList)object).size();
                    for (int i3 = 0; i3 < n3; ++i3) {
                        ((b)((ArrayList)object).get(i3)).b(this.a);
                    }
                }

                public void onAnimationStart(Animator object) {
                    object = new ArrayList(this.a.i);
                    int n3 = ((ArrayList)object).size();
                    for (int i3 = 0; i3 < n3; ++i3) {
                        ((b)((ArrayList)object).get(i3)).c(this.a);
                    }
                }
            };
        }
        this.d.c.addListener(this.h);
    }

    public boolean canApplyTheme() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return h0.a.b(drawable);
        }
        return false;
    }

    public final void d() {
        Animator.AnimatorListener animatorListener = this.h;
        if (animatorListener != null) {
            this.d.c.removeListener(animatorListener);
            this.h = null;
        }
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        this.d.b.draw(canvas);
        if (this.d.c.isStarted()) {
            this.invalidateSelf();
        }
    }

    public final void e(String string, Animator animator) {
        animator.setTarget(this.d.b.c(string));
        c c3 = this.d;
        if (c3.d == null) {
            c3.d = new ArrayList();
            this.d.e = new o.a();
        }
        this.d.d.add(animator);
        this.d.e.put(animator, string);
    }

    public boolean f(b b3) {
        Object object = this.c;
        if (object != null) {
            n1.c.g((AnimatedVectorDrawable)object, b3);
        }
        if ((object = this.i) != null && b3 != null) {
            boolean bl = ((ArrayList)object).remove(b3);
            if (this.i.size() == 0) {
                this.d();
            }
            return bl;
        }
        return false;
    }

    public int getAlpha() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return h0.a.d(drawable);
        }
        return this.d.b.getAlpha();
    }

    public int getChangingConfigurations() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.d.a;
    }

    public ColorFilter getColorFilter() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return h0.a.e(drawable);
        }
        return this.d.b.getColorFilter();
    }

    public Drawable.ConstantState getConstantState() {
        if (this.c != null) {
            return new d(this.c.getConstantState());
        }
        return null;
    }

    public int getIntrinsicHeight() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return this.d.b.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return this.d.b.getIntrinsicWidth();
    }

    public int getOpacity() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return this.d.b.getOpacity();
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        this.inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        Object object = this.c;
        if (object != null) {
            h0.a.g(object, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        int n3 = xmlPullParser.getEventType();
        int n4 = xmlPullParser.getDepth();
        while (n3 != 1 && (xmlPullParser.getDepth() >= n4 + 1 || n3 != 3)) {
            if (n3 == 2) {
                Object object2;
                g g3;
                object = xmlPullParser.getName();
                if ("animated-vector".equals(object)) {
                    object = k.k(resources, theme, attributeSet, a.e);
                    n3 = object.getResourceId(0, 0);
                    if (n3 != 0) {
                        g3 = n1.g.b(resources, n3, theme);
                        g3.g(false);
                        g3.setCallback(this.j);
                        object2 = this.d.b;
                        if (object2 != null) {
                            object2.setCallback(null);
                        }
                        this.d.b = g3;
                    }
                    object.recycle();
                } else if ("target".equals(object)) {
                    object = resources.obtainAttributes(attributeSet, a.f);
                    object2 = object.getString(0);
                    n3 = object.getResourceId(1, 0);
                    if (n3 != 0) {
                        g3 = this.e;
                        if (g3 != null) {
                            this.e((String)object2, n1.e.a((Context)g3, n3));
                        } else {
                            object.recycle();
                            throw new IllegalStateException("Context can't be null when inflating animators");
                        }
                    }
                    object.recycle();
                }
            }
            n3 = xmlPullParser.next();
        }
        this.d.a();
    }

    public boolean isAutoMirrored() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return h0.a.h(drawable);
        }
        return this.d.b.isAutoMirrored();
    }

    public boolean isRunning() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return ((AnimatedVectorDrawable)drawable).isRunning();
        }
        return this.d.c.isRunning();
    }

    public boolean isStateful() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.isStateful();
        }
        return this.d.b.isStateful();
    }

    public Drawable mutate() {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.mutate();
        }
        return this;
    }

    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setBounds(rect);
            return;
        }
        this.d.b.setBounds(rect);
    }

    @Override
    public boolean onLevelChange(int n3) {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setLevel(n3);
        }
        return this.d.b.setLevel(n3);
    }

    public boolean onStateChange(int[] nArray) {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setState(nArray);
        }
        return this.d.b.setState(nArray);
    }

    public void setAlpha(int n3) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setAlpha(n3);
            return;
        }
        this.d.b.setAlpha(n3);
    }

    public void setAutoMirrored(boolean bl) {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.j(drawable, bl);
            return;
        }
        this.d.b.setAutoMirrored(bl);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
            return;
        }
        this.d.b.setColorFilter(colorFilter);
    }

    public void setTint(int n3) {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.n(drawable, n3);
            return;
        }
        this.d.b.setTint(n3);
    }

    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.o(drawable, colorStateList);
            return;
        }
        this.d.b.setTintList(colorStateList);
    }

    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.c;
        if (drawable != null) {
            h0.a.p(drawable, mode);
            return;
        }
        this.d.b.setTintMode(mode);
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setVisible(bl, bl2);
        }
        this.d.b.setVisible(bl, bl2);
        return super.setVisible(bl, bl2);
    }

    public void start() {
        Drawable drawable = this.c;
        if (drawable != null) {
            ((AnimatedVectorDrawable)drawable).start();
            return;
        }
        if (this.d.c.isStarted()) {
            return;
        }
        this.d.c.start();
        this.invalidateSelf();
    }

    public void stop() {
        Drawable drawable = this.c;
        if (drawable != null) {
            ((AnimatedVectorDrawable)drawable).stop();
            return;
        }
        this.d.c.end();
    }

    public static class c
    extends Drawable.ConstantState {
        public int a;
        public g b;
        public AnimatorSet c;
        public ArrayList d;
        public o.a e;

        public c(Context object, c c3, Drawable.Callback object2, Resources resources) {
            if (c3 != null) {
                this.a = c3.a;
                object = c3.b;
                int n3 = 0;
                if (object != null) {
                    object = object.getConstantState();
                    this.b = resources != null ? (g)object.newDrawable(resources) : (g)object.newDrawable();
                    object = (g)this.b.mutate();
                    this.b = object;
                    object.setCallback(object2);
                    this.b.setBounds(c3.b.getBounds());
                    this.b.g(false);
                }
                if ((object = c3.d) != null) {
                    int n4 = object.size();
                    this.d = new ArrayList(n4);
                    this.e = new o.a(n4);
                    while (n3 < n4) {
                        object2 = (Animator)c3.d.get(n3);
                        object = object2.clone();
                        object2 = (String)c3.e.get(object2);
                        object.setTarget(this.b.c((String)object2));
                        this.d.add(object);
                        this.e.put(object, object2);
                        ++n3;
                    }
                    this.a();
                }
            }
        }

        public void a() {
            if (this.c == null) {
                this.c = new AnimatorSet();
            }
            this.c.playTogether((Collection)this.d);
        }

        public int getChangingConfigurations() {
            return this.a;
        }

        public Drawable newDrawable() {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }

        public Drawable newDrawable(Resources resources) {
            throw new IllegalStateException("No constant state support for SDK < 24.");
        }
    }

    public static class d
    extends Drawable.ConstantState {
        public final Drawable.ConstantState a;

        public d(Drawable.ConstantState constantState) {
            this.a = constantState;
        }

        public boolean canApplyTheme() {
            return this.a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            Drawable drawable;
            c c3 = new c();
            c3.c = drawable = this.a.newDrawable();
            drawable.setCallback(c3.j);
            return c3;
        }

        public Drawable newDrawable(Resources resources) {
            c c3 = new c();
            resources = this.a.newDrawable(resources);
            c3.c = resources;
            resources.setCallback(c3.j);
            return c3;
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            c c3 = new c();
            resources = this.a.newDrawable(resources, theme);
            c3.c = resources;
            resources.setCallback(c3.j);
            return c3;
        }
    }
}

