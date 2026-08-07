/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 */
package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.google.android.material.internal.z;
import java.io.Serializable;
import java.util.Locale;
import s2.c;
import s2.d;
import z1.e;
import z1.j;
import z1.k;
import z1.l;
import z1.m;

public final class BadgeState {
    public final State a;
    public final State b;
    public final float c;
    public final float d;
    public final float e;
    public final float f;
    public final float g;
    public final float h;
    public final int i;
    public final int j;
    public int k;
    public int l;

    public BadgeState(Context context, int n3, int n4, int n5, State object) {
        State state;
        this.b = state = new State();
        State state2 = object;
        if (object == null) {
            state2 = new State();
        }
        if (n3 != 0) {
            State.p(state2, n3);
        }
        TypedArray typedArray = this.a(context, state2.c, n4, n5);
        Resources resources = context.getResources();
        this.c = typedArray.getDimensionPixelSize(m.Badge_badgeRadius, -1);
        this.i = context.getResources().getDimensionPixelSize(z1.e.mtrl_badge_horizontal_edge_offset);
        this.j = context.getResources().getDimensionPixelSize(z1.e.mtrl_badge_text_horizontal_edge_offset);
        this.d = typedArray.getDimensionPixelSize(m.Badge_badgeWithTextRadius, -1);
        n4 = m.Badge_badgeWidth;
        n3 = z1.e.m3_badge_size;
        this.e = typedArray.getDimension(n4, resources.getDimension(n3));
        n5 = m.Badge_badgeWithTextWidth;
        n4 = z1.e.m3_badge_with_text_size;
        this.g = typedArray.getDimension(n5, resources.getDimension(n4));
        this.f = typedArray.getDimension(m.Badge_badgeHeight, resources.getDimension(n3));
        this.h = typedArray.getDimension(m.Badge_badgeWithTextHeight, resources.getDimension(n4));
        n3 = m.Badge_offsetAlignmentMode;
        boolean bl = true;
        this.k = typedArray.getInt(n3, 1);
        this.l = typedArray.getInt(m.Badge_badgeFixedEdge, 0);
        n3 = state2.k == -2 ? 255 : state2.k;
        State.t(state, n3);
        if (state2.m != -2) {
            State.P(state, state2.m);
        } else {
            n3 = m.Badge_number;
            if (typedArray.hasValue(n3)) {
                State.P(state, typedArray.getInt(n3, 0));
            } else {
                State.P(state, -1);
            }
        }
        if (state2.l != null) {
            State.j0(state, state2.l);
        } else {
            n3 = m.Badge_badgeText;
            if (typedArray.hasValue(n3)) {
                State.j0(state, typedArray.getString(n3));
            }
        }
        State.l0(state, state2.q);
        object = state2.r == null ? context.getString(z1.k.mtrl_badge_numberless_content_description) : state2.r;
        State.n0(state, (CharSequence)object);
        n3 = state2.s == 0 ? z1.j.mtrl_badge_content_description : state2.s;
        State.p0(state, n3);
        n3 = state2.t == 0 ? z1.k.mtrl_exceed_max_badge_number_content_description : state2.t;
        State.r0(state, n3);
        boolean bl2 = bl;
        if (state2.v != null) {
            bl2 = state2.v != false ? bl : false;
        }
        State.t0(state, bl2);
        n3 = state2.n == -2 ? typedArray.getInt(m.Badge_maxCharacterCount, -2) : state2.n;
        State.v0(state, n3);
        n3 = state2.o == -2 ? typedArray.getInt(m.Badge_maxNumber, -2) : state2.o;
        State.s(state, n3);
        n3 = state2.g == null ? typedArray.getResourceId(m.Badge_badgeShapeAppearance, z1.l.ShapeAppearance_M3_Sys_Shape_Corner_Full) : state2.g.intValue();
        State.v(state, n3);
        n3 = state2.h == null ? typedArray.getResourceId(m.Badge_badgeShapeAppearanceOverlay, 0) : state2.h.intValue();
        State.x(state, n3);
        n3 = state2.i == null ? typedArray.getResourceId(m.Badge_badgeWithTextShapeAppearance, z1.l.ShapeAppearance_M3_Sys_Shape_Corner_Full) : state2.i.intValue();
        State.z(state, n3);
        n3 = state2.j == null ? typedArray.getResourceId(m.Badge_badgeWithTextShapeAppearanceOverlay, 0) : state2.j.intValue();
        State.B(state, n3);
        n3 = state2.d == null ? BadgeState.H(context, typedArray, m.Badge_backgroundColor) : state2.d;
        State.D(state, n3);
        n3 = state2.f == null ? typedArray.getResourceId(m.Badge_badgeTextAppearance, z1.l.TextAppearance_MaterialComponents_Badge) : state2.f.intValue();
        State.F(state, n3);
        if (state2.e != null) {
            State.H(state, state2.e);
        } else {
            n3 = m.Badge_badgeTextColor;
            if (typedArray.hasValue(n3)) {
                State.H(state, BadgeState.H(context, typedArray, n3));
            } else {
                State.H(state, new d(context, state.f).j().getDefaultColor());
            }
        }
        n3 = state2.u == null ? typedArray.getInt(m.Badge_badgeGravity, 8388661) : state2.u.intValue();
        State.J(state, n3);
        n3 = state2.w == null ? typedArray.getDimensionPixelSize(m.Badge_badgeWidePadding, resources.getDimensionPixelSize(z1.e.mtrl_badge_long_text_horizontal_padding)) : state2.w.intValue();
        State.L(state, n3);
        n3 = state2.x == null ? typedArray.getDimensionPixelSize(m.Badge_badgeVerticalPadding, resources.getDimensionPixelSize(z1.e.m3_badge_with_text_vertical_padding)) : state2.x.intValue();
        State.O(state, n3);
        n3 = state2.y == null ? typedArray.getDimensionPixelOffset(m.Badge_horizontalOffset, 0) : state2.y.intValue();
        State.R(state, n3);
        n3 = state2.z == null ? typedArray.getDimensionPixelOffset(m.Badge_verticalOffset, 0) : state2.z.intValue();
        State.T(state, n3);
        n3 = state2.A == null ? typedArray.getDimensionPixelOffset(m.Badge_horizontalOffsetWithText, state.y.intValue()) : state2.A.intValue();
        State.V(state, n3);
        n3 = state2.B == null ? typedArray.getDimensionPixelOffset(m.Badge_verticalOffsetWithText, state.z.intValue()) : state2.B.intValue();
        State.X(state, n3);
        n3 = state2.E == null ? typedArray.getDimensionPixelOffset(m.Badge_largeFontVerticalOffsetAdjustment, 0) : state2.E.intValue();
        State.Z(state, n3);
        n3 = state2.C == null ? 0 : state2.C;
        State.b0(state, n3);
        n3 = state2.D == null ? 0 : state2.D;
        State.d0(state, n3);
        bl2 = state2.F == null ? typedArray.getBoolean(m.Badge_autoAdjustToWithinGrandparentBounds, false) : state2.F.booleanValue();
        State.f0(state, bl2);
        typedArray.recycle();
        if (state2.p == null) {
            State.h0(state, Locale.getDefault(Locale.Category.FORMAT));
        } else {
            State.h0(state, state2.p);
        }
        this.a = state2;
    }

    public static int H(Context context, TypedArray typedArray, int n3) {
        return s2.c.a(context, typedArray, n3).getDefaultColor();
    }

    public int A() {
        return this.b.f;
    }

    public int B() {
        return this.b.B;
    }

    public int C() {
        return this.b.z;
    }

    public boolean D() {
        return this.b.m != -1;
    }

    public boolean E() {
        return this.b.l != null;
    }

    public boolean F() {
        return this.b.F;
    }

    public boolean G() {
        return this.b.v;
    }

    public void I(int n3) {
        State.t(this.a, n3);
        State.t(this.b, n3);
    }

    public final TypedArray a(Context context, int n3, int n4, int n5) {
        AttributeSet attributeSet;
        block2: {
            if (n3 != 0) {
                attributeSet = j2.d.k(context, n3, "badge");
                n3 = attributeSet.getStyleAttribute();
            } else {
                attributeSet = null;
                n3 = 0;
            }
            if (n3 != 0) break block2;
            n3 = n5;
        }
        return z.i(context, attributeSet, m.Badge, n4, n3, new int[0]);
    }

    public int b() {
        return this.b.C;
    }

    public int c() {
        return this.b.D;
    }

    public int d() {
        return this.b.k;
    }

    public int e() {
        return this.b.d;
    }

    public int f() {
        return this.b.u;
    }

    public int g() {
        return this.b.w;
    }

    public int h() {
        return this.b.h;
    }

    public int i() {
        return this.b.g;
    }

    public int j() {
        return this.b.e;
    }

    public int k() {
        return this.b.x;
    }

    public int l() {
        return this.b.j;
    }

    public int m() {
        return this.b.i;
    }

    public int n() {
        return this.b.t;
    }

    public CharSequence o() {
        return this.b.q;
    }

    public CharSequence p() {
        return this.b.r;
    }

    public int q() {
        return this.b.s;
    }

    public int r() {
        return this.b.A;
    }

    public int s() {
        return this.b.y;
    }

    public int t() {
        return this.b.E;
    }

    public int u() {
        return this.b.n;
    }

    public int v() {
        return this.b.o;
    }

    public int w() {
        return this.b.m;
    }

    public Locale x() {
        return this.b.p;
    }

    public State y() {
        return this.a;
    }

    public String z() {
        return this.b.l;
    }

    public static final class State
    implements Parcelable {
        public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator(){

            public State a(Parcel parcel) {
                return new State(parcel);
            }

            public State[] b(int n3) {
                return new State[n3];
            }
        };
        public Integer A;
        public Integer B;
        public Integer C;
        public Integer D;
        public Integer E;
        public Boolean F;
        public Integer G;
        public int c;
        public Integer d;
        public Integer e;
        public Integer f;
        public Integer g;
        public Integer h;
        public Integer i;
        public Integer j;
        public int k = 255;
        public String l;
        public int m = -2;
        public int n = -2;
        public int o = -2;
        public Locale p;
        public CharSequence q;
        public CharSequence r;
        public int s;
        public int t;
        public Integer u;
        public Boolean v = Boolean.TRUE;
        public Integer w;
        public Integer x;
        public Integer y;
        public Integer z;

        public State() {
        }

        public State(Parcel parcel) {
            this.c = parcel.readInt();
            this.d = (Integer)parcel.readSerializable();
            this.e = (Integer)parcel.readSerializable();
            this.f = (Integer)parcel.readSerializable();
            this.g = (Integer)parcel.readSerializable();
            this.h = (Integer)parcel.readSerializable();
            this.i = (Integer)parcel.readSerializable();
            this.j = (Integer)parcel.readSerializable();
            this.k = parcel.readInt();
            this.l = parcel.readString();
            this.m = parcel.readInt();
            this.n = parcel.readInt();
            this.o = parcel.readInt();
            this.q = parcel.readString();
            this.r = parcel.readString();
            this.s = parcel.readInt();
            this.u = (Integer)parcel.readSerializable();
            this.w = (Integer)parcel.readSerializable();
            this.x = (Integer)parcel.readSerializable();
            this.y = (Integer)parcel.readSerializable();
            this.z = (Integer)parcel.readSerializable();
            this.A = (Integer)parcel.readSerializable();
            this.B = (Integer)parcel.readSerializable();
            this.E = (Integer)parcel.readSerializable();
            this.C = (Integer)parcel.readSerializable();
            this.D = (Integer)parcel.readSerializable();
            this.v = (Boolean)parcel.readSerializable();
            this.p = (Locale)parcel.readSerializable();
            this.F = (Boolean)parcel.readSerializable();
            this.G = (Integer)parcel.readSerializable();
        }

        public static /* synthetic */ Integer B(State state, Integer n3) {
            state.j = n3;
            return n3;
        }

        public static /* synthetic */ Integer D(State state, Integer n3) {
            state.d = n3;
            return n3;
        }

        public static /* synthetic */ Integer F(State state, Integer n3) {
            state.f = n3;
            return n3;
        }

        public static /* synthetic */ Integer H(State state, Integer n3) {
            state.e = n3;
            return n3;
        }

        public static /* synthetic */ Integer J(State state, Integer n3) {
            state.u = n3;
            return n3;
        }

        public static /* synthetic */ Integer L(State state, Integer n3) {
            state.w = n3;
            return n3;
        }

        public static /* synthetic */ Integer O(State state, Integer n3) {
            state.x = n3;
            return n3;
        }

        public static /* synthetic */ int P(State state, int n3) {
            state.m = n3;
            return n3;
        }

        public static /* synthetic */ Integer R(State state, Integer n3) {
            state.y = n3;
            return n3;
        }

        public static /* synthetic */ Integer T(State state, Integer n3) {
            state.z = n3;
            return n3;
        }

        public static /* synthetic */ Integer V(State state, Integer n3) {
            state.A = n3;
            return n3;
        }

        public static /* synthetic */ Integer X(State state, Integer n3) {
            state.B = n3;
            return n3;
        }

        public static /* synthetic */ Integer Z(State state, Integer n3) {
            state.E = n3;
            return n3;
        }

        public static /* synthetic */ Integer b0(State state, Integer n3) {
            state.C = n3;
            return n3;
        }

        public static /* synthetic */ Integer d0(State state, Integer n3) {
            state.D = n3;
            return n3;
        }

        public static /* synthetic */ Boolean f0(State state, Boolean bl) {
            state.F = bl;
            return bl;
        }

        public static /* synthetic */ Locale h0(State state, Locale locale) {
            state.p = locale;
            return locale;
        }

        public static /* synthetic */ String j0(State state, String string) {
            state.l = string;
            return string;
        }

        public static /* synthetic */ CharSequence l0(State state, CharSequence charSequence) {
            state.q = charSequence;
            return charSequence;
        }

        public static /* synthetic */ CharSequence n0(State state, CharSequence charSequence) {
            state.r = charSequence;
            return charSequence;
        }

        public static /* synthetic */ int p(State state, int n3) {
            state.c = n3;
            return n3;
        }

        public static /* synthetic */ int p0(State state, int n3) {
            state.s = n3;
            return n3;
        }

        public static /* synthetic */ int r0(State state, int n3) {
            state.t = n3;
            return n3;
        }

        public static /* synthetic */ int s(State state, int n3) {
            state.o = n3;
            return n3;
        }

        public static /* synthetic */ int t(State state, int n3) {
            state.k = n3;
            return n3;
        }

        public static /* synthetic */ Boolean t0(State state, Boolean bl) {
            state.v = bl;
            return bl;
        }

        public static /* synthetic */ Integer v(State state, Integer n3) {
            state.g = n3;
            return n3;
        }

        public static /* synthetic */ int v0(State state, int n3) {
            state.n = n3;
            return n3;
        }

        public static /* synthetic */ Integer x(State state, Integer n3) {
            state.h = n3;
            return n3;
        }

        public static /* synthetic */ Integer z(State state, Integer n3) {
            state.i = n3;
            return n3;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n3) {
            parcel.writeInt(this.c);
            parcel.writeSerializable((Serializable)this.d);
            parcel.writeSerializable((Serializable)this.e);
            parcel.writeSerializable((Serializable)this.f);
            parcel.writeSerializable((Serializable)this.g);
            parcel.writeSerializable((Serializable)this.h);
            parcel.writeSerializable((Serializable)this.i);
            parcel.writeSerializable((Serializable)this.j);
            parcel.writeInt(this.k);
            parcel.writeString(this.l);
            parcel.writeInt(this.m);
            parcel.writeInt(this.n);
            parcel.writeInt(this.o);
            CharSequence charSequence = this.q;
            Object var4_4 = null;
            charSequence = charSequence != null ? charSequence.toString() : null;
            parcel.writeString((String)charSequence);
            CharSequence charSequence2 = this.r;
            charSequence = var4_4;
            if (charSequence2 != null) {
                charSequence = charSequence2.toString();
            }
            parcel.writeString((String)charSequence);
            parcel.writeInt(this.s);
            parcel.writeSerializable((Serializable)this.u);
            parcel.writeSerializable((Serializable)this.w);
            parcel.writeSerializable((Serializable)this.x);
            parcel.writeSerializable((Serializable)this.y);
            parcel.writeSerializable((Serializable)this.z);
            parcel.writeSerializable((Serializable)this.A);
            parcel.writeSerializable((Serializable)this.B);
            parcel.writeSerializable((Serializable)this.E);
            parcel.writeSerializable((Serializable)this.C);
            parcel.writeSerializable((Serializable)this.D);
            parcel.writeSerializable((Serializable)this.v);
            parcel.writeSerializable((Serializable)this.p);
            parcel.writeSerializable((Serializable)this.F);
            parcel.writeSerializable((Serializable)this.G);
        }
    }
}

