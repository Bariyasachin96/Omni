/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.util.Xml
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import u.j;

public class b {
    public static final int[] i = new int[]{0, 4, 8};
    public static SparseIntArray j = new SparseIntArray();
    public static SparseIntArray k = new SparseIntArray();
    public boolean a;
    public String b;
    public String c = "";
    public String[] d = new String[0];
    public int e = 0;
    public HashMap f = new HashMap();
    public boolean g = true;
    public HashMap h = new HashMap();

    static {
        j.append(y.d.Constraint_layout_constraintLeft_toLeftOf, 25);
        j.append(y.d.Constraint_layout_constraintLeft_toRightOf, 26);
        j.append(y.d.Constraint_layout_constraintRight_toLeftOf, 29);
        j.append(y.d.Constraint_layout_constraintRight_toRightOf, 30);
        j.append(y.d.Constraint_layout_constraintTop_toTopOf, 36);
        j.append(y.d.Constraint_layout_constraintTop_toBottomOf, 35);
        j.append(y.d.Constraint_layout_constraintBottom_toTopOf, 4);
        j.append(y.d.Constraint_layout_constraintBottom_toBottomOf, 3);
        j.append(y.d.Constraint_layout_constraintBaseline_toBaselineOf, 1);
        j.append(y.d.Constraint_layout_constraintBaseline_toTopOf, 91);
        j.append(y.d.Constraint_layout_constraintBaseline_toBottomOf, 92);
        j.append(y.d.Constraint_layout_editor_absoluteX, 6);
        j.append(y.d.Constraint_layout_editor_absoluteY, 7);
        j.append(y.d.Constraint_layout_constraintGuide_begin, 17);
        j.append(y.d.Constraint_layout_constraintGuide_end, 18);
        j.append(y.d.Constraint_layout_constraintGuide_percent, 19);
        j.append(y.d.Constraint_guidelineUseRtl, 99);
        j.append(y.d.Constraint_android_orientation, 27);
        j.append(y.d.Constraint_layout_constraintStart_toEndOf, 32);
        j.append(y.d.Constraint_layout_constraintStart_toStartOf, 33);
        j.append(y.d.Constraint_layout_constraintEnd_toStartOf, 10);
        j.append(y.d.Constraint_layout_constraintEnd_toEndOf, 9);
        j.append(y.d.Constraint_layout_goneMarginLeft, 13);
        j.append(y.d.Constraint_layout_goneMarginTop, 16);
        j.append(y.d.Constraint_layout_goneMarginRight, 14);
        j.append(y.d.Constraint_layout_goneMarginBottom, 11);
        j.append(y.d.Constraint_layout_goneMarginStart, 15);
        j.append(y.d.Constraint_layout_goneMarginEnd, 12);
        j.append(y.d.Constraint_layout_constraintVertical_weight, 40);
        j.append(y.d.Constraint_layout_constraintHorizontal_weight, 39);
        j.append(y.d.Constraint_layout_constraintHorizontal_chainStyle, 41);
        j.append(y.d.Constraint_layout_constraintVertical_chainStyle, 42);
        j.append(y.d.Constraint_layout_constraintHorizontal_bias, 20);
        j.append(y.d.Constraint_layout_constraintVertical_bias, 37);
        j.append(y.d.Constraint_layout_constraintDimensionRatio, 5);
        j.append(y.d.Constraint_layout_constraintLeft_creator, 87);
        j.append(y.d.Constraint_layout_constraintTop_creator, 87);
        j.append(y.d.Constraint_layout_constraintRight_creator, 87);
        j.append(y.d.Constraint_layout_constraintBottom_creator, 87);
        j.append(y.d.Constraint_layout_constraintBaseline_creator, 87);
        j.append(y.d.Constraint_android_layout_marginLeft, 24);
        j.append(y.d.Constraint_android_layout_marginRight, 28);
        j.append(y.d.Constraint_android_layout_marginStart, 31);
        j.append(y.d.Constraint_android_layout_marginEnd, 8);
        j.append(y.d.Constraint_android_layout_marginTop, 34);
        j.append(y.d.Constraint_android_layout_marginBottom, 2);
        j.append(y.d.Constraint_android_layout_width, 23);
        j.append(y.d.Constraint_android_layout_height, 21);
        j.append(y.d.Constraint_layout_constraintWidth, 95);
        j.append(y.d.Constraint_layout_constraintHeight, 96);
        j.append(y.d.Constraint_android_visibility, 22);
        j.append(y.d.Constraint_android_alpha, 43);
        j.append(y.d.Constraint_android_elevation, 44);
        j.append(y.d.Constraint_android_rotationX, 45);
        j.append(y.d.Constraint_android_rotationY, 46);
        j.append(y.d.Constraint_android_rotation, 60);
        j.append(y.d.Constraint_android_scaleX, 47);
        j.append(y.d.Constraint_android_scaleY, 48);
        j.append(y.d.Constraint_android_transformPivotX, 49);
        j.append(y.d.Constraint_android_transformPivotY, 50);
        j.append(y.d.Constraint_android_translationX, 51);
        j.append(y.d.Constraint_android_translationY, 52);
        j.append(y.d.Constraint_android_translationZ, 53);
        j.append(y.d.Constraint_layout_constraintWidth_default, 54);
        j.append(y.d.Constraint_layout_constraintHeight_default, 55);
        j.append(y.d.Constraint_layout_constraintWidth_max, 56);
        j.append(y.d.Constraint_layout_constraintHeight_max, 57);
        j.append(y.d.Constraint_layout_constraintWidth_min, 58);
        j.append(y.d.Constraint_layout_constraintHeight_min, 59);
        j.append(y.d.Constraint_layout_constraintCircle, 61);
        j.append(y.d.Constraint_layout_constraintCircleRadius, 62);
        j.append(y.d.Constraint_layout_constraintCircleAngle, 63);
        j.append(y.d.Constraint_animateRelativeTo, 64);
        j.append(y.d.Constraint_transitionEasing, 65);
        j.append(y.d.Constraint_drawPath, 66);
        j.append(y.d.Constraint_transitionPathRotate, 67);
        j.append(y.d.Constraint_motionStagger, 79);
        j.append(y.d.Constraint_android_id, 38);
        j.append(y.d.Constraint_motionProgress, 68);
        j.append(y.d.Constraint_layout_constraintWidth_percent, 69);
        j.append(y.d.Constraint_layout_constraintHeight_percent, 70);
        j.append(y.d.Constraint_layout_wrapBehaviorInParent, 97);
        j.append(y.d.Constraint_chainUseRtl, 71);
        j.append(y.d.Constraint_barrierDirection, 72);
        j.append(y.d.Constraint_barrierMargin, 73);
        j.append(y.d.Constraint_constraint_referenced_ids, 74);
        j.append(y.d.Constraint_barrierAllowsGoneWidgets, 75);
        j.append(y.d.Constraint_pathMotionArc, 76);
        j.append(y.d.Constraint_layout_constraintTag, 77);
        j.append(y.d.Constraint_visibilityMode, 78);
        j.append(y.d.Constraint_layout_constrainedWidth, 80);
        j.append(y.d.Constraint_layout_constrainedHeight, 81);
        j.append(y.d.Constraint_polarRelativeTo, 82);
        j.append(y.d.Constraint_transformPivotTarget, 83);
        j.append(y.d.Constraint_quantizeMotionSteps, 84);
        j.append(y.d.Constraint_quantizeMotionPhase, 85);
        j.append(y.d.Constraint_quantizeMotionInterpolator, 86);
        SparseIntArray sparseIntArray = k;
        int n3 = y.d.ConstraintOverride_layout_editor_absoluteY;
        sparseIntArray.append(n3, 6);
        k.append(n3, 7);
        k.append(y.d.ConstraintOverride_android_orientation, 27);
        k.append(y.d.ConstraintOverride_layout_goneMarginLeft, 13);
        k.append(y.d.ConstraintOverride_layout_goneMarginTop, 16);
        k.append(y.d.ConstraintOverride_layout_goneMarginRight, 14);
        k.append(y.d.ConstraintOverride_layout_goneMarginBottom, 11);
        k.append(y.d.ConstraintOverride_layout_goneMarginStart, 15);
        k.append(y.d.ConstraintOverride_layout_goneMarginEnd, 12);
        k.append(y.d.ConstraintOverride_layout_constraintVertical_weight, 40);
        k.append(y.d.ConstraintOverride_layout_constraintHorizontal_weight, 39);
        k.append(y.d.ConstraintOverride_layout_constraintHorizontal_chainStyle, 41);
        k.append(y.d.ConstraintOverride_layout_constraintVertical_chainStyle, 42);
        k.append(y.d.ConstraintOverride_layout_constraintHorizontal_bias, 20);
        k.append(y.d.ConstraintOverride_layout_constraintVertical_bias, 37);
        k.append(y.d.ConstraintOverride_layout_constraintDimensionRatio, 5);
        k.append(y.d.ConstraintOverride_layout_constraintLeft_creator, 87);
        k.append(y.d.ConstraintOverride_layout_constraintTop_creator, 87);
        k.append(y.d.ConstraintOverride_layout_constraintRight_creator, 87);
        k.append(y.d.ConstraintOverride_layout_constraintBottom_creator, 87);
        k.append(y.d.ConstraintOverride_layout_constraintBaseline_creator, 87);
        k.append(y.d.ConstraintOverride_android_layout_marginLeft, 24);
        k.append(y.d.ConstraintOverride_android_layout_marginRight, 28);
        k.append(y.d.ConstraintOverride_android_layout_marginStart, 31);
        k.append(y.d.ConstraintOverride_android_layout_marginEnd, 8);
        k.append(y.d.ConstraintOverride_android_layout_marginTop, 34);
        k.append(y.d.ConstraintOverride_android_layout_marginBottom, 2);
        k.append(y.d.ConstraintOverride_android_layout_width, 23);
        k.append(y.d.ConstraintOverride_android_layout_height, 21);
        k.append(y.d.ConstraintOverride_layout_constraintWidth, 95);
        k.append(y.d.ConstraintOverride_layout_constraintHeight, 96);
        k.append(y.d.ConstraintOverride_android_visibility, 22);
        k.append(y.d.ConstraintOverride_android_alpha, 43);
        k.append(y.d.ConstraintOverride_android_elevation, 44);
        k.append(y.d.ConstraintOverride_android_rotationX, 45);
        k.append(y.d.ConstraintOverride_android_rotationY, 46);
        k.append(y.d.ConstraintOverride_android_rotation, 60);
        k.append(y.d.ConstraintOverride_android_scaleX, 47);
        k.append(y.d.ConstraintOverride_android_scaleY, 48);
        k.append(y.d.ConstraintOverride_android_transformPivotX, 49);
        k.append(y.d.ConstraintOverride_android_transformPivotY, 50);
        k.append(y.d.ConstraintOverride_android_translationX, 51);
        k.append(y.d.ConstraintOverride_android_translationY, 52);
        k.append(y.d.ConstraintOverride_android_translationZ, 53);
        k.append(y.d.ConstraintOverride_layout_constraintWidth_default, 54);
        k.append(y.d.ConstraintOverride_layout_constraintHeight_default, 55);
        k.append(y.d.ConstraintOverride_layout_constraintWidth_max, 56);
        k.append(y.d.ConstraintOverride_layout_constraintHeight_max, 57);
        k.append(y.d.ConstraintOverride_layout_constraintWidth_min, 58);
        k.append(y.d.ConstraintOverride_layout_constraintHeight_min, 59);
        k.append(y.d.ConstraintOverride_layout_constraintCircleRadius, 62);
        k.append(y.d.ConstraintOverride_layout_constraintCircleAngle, 63);
        k.append(y.d.ConstraintOverride_animateRelativeTo, 64);
        k.append(y.d.ConstraintOverride_transitionEasing, 65);
        k.append(y.d.ConstraintOverride_drawPath, 66);
        k.append(y.d.ConstraintOverride_transitionPathRotate, 67);
        k.append(y.d.ConstraintOverride_motionStagger, 79);
        k.append(y.d.ConstraintOverride_android_id, 38);
        k.append(y.d.ConstraintOverride_motionTarget, 98);
        k.append(y.d.ConstraintOverride_motionProgress, 68);
        k.append(y.d.ConstraintOverride_layout_constraintWidth_percent, 69);
        k.append(y.d.ConstraintOverride_layout_constraintHeight_percent, 70);
        k.append(y.d.ConstraintOverride_chainUseRtl, 71);
        k.append(y.d.ConstraintOverride_barrierDirection, 72);
        k.append(y.d.ConstraintOverride_barrierMargin, 73);
        k.append(y.d.ConstraintOverride_constraint_referenced_ids, 74);
        k.append(y.d.ConstraintOverride_barrierAllowsGoneWidgets, 75);
        k.append(y.d.ConstraintOverride_pathMotionArc, 76);
        k.append(y.d.ConstraintOverride_layout_constraintTag, 77);
        k.append(y.d.ConstraintOverride_visibilityMode, 78);
        k.append(y.d.ConstraintOverride_layout_constrainedWidth, 80);
        k.append(y.d.ConstraintOverride_layout_constrainedHeight, 81);
        k.append(y.d.ConstraintOverride_polarRelativeTo, 82);
        k.append(y.d.ConstraintOverride_transformPivotTarget, 83);
        k.append(y.d.ConstraintOverride_quantizeMotionSteps, 84);
        k.append(y.d.ConstraintOverride_quantizeMotionPhase, 85);
        k.append(y.d.ConstraintOverride_quantizeMotionInterpolator, 86);
        k.append(y.d.ConstraintOverride_layout_wrapBehaviorInParent, 97);
    }

    public static int E(TypedArray typedArray, int n3, int n4) {
        if ((n4 = typedArray.getResourceId(n3, n4)) == -1) {
            return typedArray.getInt(n3, -1);
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void F(Object object, TypedArray typedArray, int n3, int n4) {
        boolean bl;
        block11: {
            block10: {
                block7: {
                    block8: {
                        int n5;
                        block9: {
                            if (object == null) {
                                return;
                            }
                            int n6 = typedArray.peekValue((int)n3).type;
                            if (n6 == 3) {
                                androidx.constraintlayout.widget.b.G(object, typedArray.getString(n3), n4);
                                return;
                            }
                            n5 = 0;
                            if (n6 == 5) break block7;
                            n6 = typedArray.getInt(n3, 0);
                            if (n6 == -4) break block8;
                            if (n6 == -3) break block9;
                            n3 = n6;
                            if (n6 == -2) break block10;
                            n3 = n6;
                            if (n6 == -1) break block10;
                        }
                        bl = false;
                        n3 = n5;
                        break block11;
                    }
                    bl = true;
                    n3 = -2;
                    break block11;
                }
                n3 = typedArray.getDimensionPixelSize(n3, 0);
            }
            bl = false;
        }
        if (object instanceof ConstraintLayout.LayoutParams) {
            object = (ConstraintLayout.LayoutParams)((Object)object);
            if (n4 == 0) {
                ((ViewGroup.MarginLayoutParams)object).width = n3;
                ((ConstraintLayout.LayoutParams)((Object)object)).a0 = bl;
                return;
            }
            ((ViewGroup.MarginLayoutParams)object).height = n3;
            ((ConstraintLayout.LayoutParams)((Object)object)).b0 = bl;
            return;
        }
        if (object instanceof b) {
            object = (b)object;
            if (n4 == 0) {
                ((b)object).d = n3;
                ((b)object).n0 = bl;
                return;
            }
            ((b)object).e = n3;
            ((b)object).o0 = bl;
            return;
        }
        if (!(object instanceof a.a)) return;
        object = (a.a)object;
        if (n4 == 0) {
            ((a.a)object).b(23, n3);
            ((a.a)object).d(80, bl);
            return;
        }
        ((a.a)object).b(21, n3);
        ((a.a)object).d(81, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void G(Object object, String string, int n3) {
        if (string == null) {
            return;
        }
        int n4 = string.indexOf(61);
        int n5 = string.length();
        if (n4 <= 0) return;
        if (n4 >= n5 - 1) return;
        String string2 = string.substring(0, n4);
        String string3 = string.substring(n4 + 1);
        if (string3.length() <= 0) return;
        string = string2.trim();
        string3 = string3.trim();
        if ("ratio".equalsIgnoreCase(string)) {
            if (object instanceof ConstraintLayout.LayoutParams) {
                object = (ConstraintLayout.LayoutParams)((Object)object);
                if (n3 == 0) {
                    ((ViewGroup.MarginLayoutParams)object).width = 0;
                } else {
                    ((ViewGroup.MarginLayoutParams)object).height = 0;
                }
                androidx.constraintlayout.widget.b.H((ConstraintLayout.LayoutParams)((Object)object), string3);
                return;
            }
            if (object instanceof b) {
                ((b)object).A = string3;
                return;
            }
            if (!(object instanceof a.a)) return;
            ((a.a)object).c(5, string3);
            return;
        }
        if ("weight".equalsIgnoreCase(string)) {
            float f3 = Float.parseFloat(string3);
            if (object instanceof ConstraintLayout.LayoutParams) {
                object = (ConstraintLayout.LayoutParams)((Object)object);
                if (n3 == 0) {
                    ((ViewGroup.MarginLayoutParams)object).width = 0;
                    ((ConstraintLayout.LayoutParams)((Object)object)).L = f3;
                    return;
                }
                ((ViewGroup.MarginLayoutParams)object).height = 0;
                ((ConstraintLayout.LayoutParams)((Object)object)).M = f3;
                return;
            }
            if (object instanceof b) {
                object = (b)object;
                if (n3 == 0) {
                    ((b)object).d = 0;
                    ((b)object).W = f3;
                    return;
                }
                ((b)object).e = 0;
                ((b)object).V = f3;
                return;
            }
            if (!(object instanceof a.a)) return;
            object = (a.a)object;
            if (n3 == 0) {
                ((a.a)object).b(23, 0);
                ((a.a)object).a(39, f3);
                return;
            }
            ((a.a)object).b(21, 0);
            ((a.a)object).a(40, f3);
            return;
        }
        if (!"parent".equalsIgnoreCase(string)) return;
        try {
            float f4 = Math.max(0.0f, Math.min(1.0f, Float.parseFloat(string3)));
            if (object instanceof ConstraintLayout.LayoutParams) {
                object = (ConstraintLayout.LayoutParams)((Object)object);
                if (n3 == 0) {
                    ((ViewGroup.MarginLayoutParams)object).width = 0;
                    ((ConstraintLayout.LayoutParams)((Object)object)).V = f4;
                    ((ConstraintLayout.LayoutParams)((Object)object)).P = 2;
                    return;
                }
                ((ViewGroup.MarginLayoutParams)object).height = 0;
                ((ConstraintLayout.LayoutParams)((Object)object)).W = f4;
                ((ConstraintLayout.LayoutParams)((Object)object)).Q = 2;
                return;
            }
            if (object instanceof b) {
                object = (b)object;
                if (n3 == 0) {
                    ((b)object).d = 0;
                    ((b)object).f0 = f4;
                    ((b)object).Z = 2;
                    return;
                }
                ((b)object).e = 0;
                ((b)object).g0 = f4;
                ((b)object).a0 = 2;
                return;
            }
            if (!(object instanceof a.a)) return;
            object = (a.a)object;
            if (n3 == 0) {
                ((a.a)object).b(23, 0);
                ((a.a)object).b(54, 2);
                return;
            }
            ((a.a)object).b(21, 0);
            ((a.a)object).b(55, 2);
            return;
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void H(ConstraintLayout.LayoutParams var0, String var1_1) {
        block13: {
            block14: {
                var3_2 = NaNf;
                var7_3 = -1;
                var2_4 = var3_2;
                var8_5 = var7_3;
                if (var1_1 == null) break block13;
                var10_6 = var1_1.length();
                var11_7 = var1_1.indexOf(44);
                var9_8 = 0;
                var6_9 = var7_3;
                var8_5 = var9_8;
                if (var11_7 > 0) {
                    var6_9 = var7_3;
                    var8_5 = var9_8;
                    if (var11_7 < var10_6 - 1) {
                        var12_10 = var1_1.substring(0, var11_7);
                        if (var12_10.equalsIgnoreCase("W")) {
                            var6_9 = 0;
                        } else {
                            var6_9 = var7_3;
                            if (var12_10.equalsIgnoreCase("H")) {
                                var6_9 = 1;
                            }
                        }
                        var8_5 = var11_7 + 1;
                    }
                }
                if ((var7_3 = var1_1.indexOf(58)) < 0 || var7_3 >= var10_6 - 1) break block14;
                var12_10 = var1_1.substring(var8_5, var7_3);
                var13_12 = var1_1.substring(var7_3 + 1);
                var2_4 = var3_2;
                var8_5 = var6_9;
                if (var12_10.length() <= 0) break block13;
                var2_4 = var3_2;
                var8_5 = var6_9;
                if (var13_12.length() <= 0) break block13;
                try {
                    var5_13 = Float.parseFloat(var12_10);
                    var4_14 = Float.parseFloat(var13_12);
                    var2_4 = var3_2;
                    var8_5 = var6_9;
                }
                catch (NumberFormatException var12_11) {
                    var2_4 = var3_2;
                    var8_5 = var6_9;
                    ** continue;
                }
                if (!(var5_13 > 0.0f)) break block13;
                var2_4 = var3_2;
                var8_5 = var6_9;
                if (!(var4_14 > 0.0f)) break block13;
                if (var6_9 == 1) {
                    var2_4 = Math.abs(var4_14 / var5_13);
                    var8_5 = var6_9;
                    break block13;
                }
                var2_4 = Math.abs(var5_13 / var4_14);
                var8_5 = var6_9;
                break block13;
            }
            var12_10 = var1_1.substring(var8_5);
            var2_4 = var3_2;
            var8_5 = var6_9;
            if (var12_10.length() > 0) {
                var2_4 = Float.parseFloat(var12_10);
                var8_5 = var6_9;
            }
        }
lbl63:
        // 2 sources

        while (true) {
            var0.I = var1_1;
            var0.J = var2_4;
            var0.K = var8_5;
            return;
        }
    }

    public static void J(a a4, TypedArray typedArray) {
        a.a a5;
        int n3 = typedArray.getIndexCount();
        a4.h = a5 = new a.a();
        a4.d.a = false;
        a4.e.b = false;
        a4.c.a = false;
        a4.f.a = false;
        block82: for (int i3 = 0; i3 < n3; ++i3) {
            int n4 = typedArray.getIndex(i3);
            switch (k.get(n4)) {
                default: {
                    Object object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown attribute 0x");
                    ((StringBuilder)object).append(Integer.toHexString(n4));
                    ((StringBuilder)object).append("   ");
                    ((StringBuilder)object).append(j.get(n4));
                    Log.w((String)"ConstraintSet", (String)((StringBuilder)object).toString());
                    continue block82;
                }
                case 99: {
                    a5.d(99, typedArray.getBoolean(n4, a4.e.i));
                    continue block82;
                }
                case 98: {
                    int n5;
                    if (MotionLayout.f1) {
                        a4.a = n5 = typedArray.getResourceId(n4, a4.a);
                        if (n5 != -1) continue block82;
                        a4.b = typedArray.getString(n4);
                        continue block82;
                    }
                    if (typedArray.peekValue((int)n4).type == 3) {
                        a4.b = typedArray.getString(n4);
                        continue block82;
                    }
                    a4.a = typedArray.getResourceId(n4, a4.a);
                    continue block82;
                }
                case 97: {
                    a5.b(97, typedArray.getInt(n4, a4.e.q0));
                    continue block82;
                }
                case 96: {
                    androidx.constraintlayout.widget.b.F(a5, typedArray, n4, 1);
                    continue block82;
                }
                case 95: {
                    androidx.constraintlayout.widget.b.F(a5, typedArray, n4, 0);
                    continue block82;
                }
                case 94: {
                    a5.b(94, typedArray.getDimensionPixelSize(n4, a4.e.U));
                    continue block82;
                }
                case 93: {
                    a5.b(93, typedArray.getDimensionPixelSize(n4, a4.e.N));
                    continue block82;
                }
                case 87: {
                    Object object = new StringBuilder();
                    ((StringBuilder)object).append("unused attribute 0x");
                    ((StringBuilder)object).append(Integer.toHexString(n4));
                    ((StringBuilder)object).append("   ");
                    ((StringBuilder)object).append(j.get(n4));
                    Log.w((String)"ConstraintSet", (String)((StringBuilder)object).toString());
                    continue block82;
                }
                case 86: {
                    Object object;
                    int n5 = typedArray.peekValue((int)n4).type;
                    if (n5 == 1) {
                        a4.d.n = typedArray.getResourceId(n4, -1);
                        a5.b(89, a4.d.n);
                        object = a4.d;
                        if (((c)object).n == -1) continue block82;
                        ((c)object).m = -2;
                        a5.b(88, -2);
                        continue block82;
                    }
                    if (n5 == 3) {
                        a4.d.l = typedArray.getString(n4);
                        a5.c(90, a4.d.l);
                        if (a4.d.l.indexOf("/") > 0) {
                            a4.d.n = typedArray.getResourceId(n4, -1);
                            a5.b(89, a4.d.n);
                            a4.d.m = -2;
                            a5.b(88, -2);
                            continue block82;
                        }
                        a4.d.m = -1;
                        a5.b(88, -1);
                        continue block82;
                    }
                    object = a4.d;
                    ((c)object).m = typedArray.getInteger(n4, ((c)object).n);
                    a5.b(88, a4.d.m);
                    continue block82;
                }
                case 85: {
                    a5.a(85, typedArray.getFloat(n4, a4.d.j));
                    continue block82;
                }
                case 84: {
                    a5.b(84, typedArray.getInteger(n4, a4.d.k));
                    continue block82;
                }
                case 83: {
                    a5.b(83, androidx.constraintlayout.widget.b.E(typedArray, n4, a4.f.i));
                    continue block82;
                }
                case 82: {
                    a5.b(82, typedArray.getInteger(n4, a4.d.c));
                    continue block82;
                }
                case 81: {
                    a5.d(81, typedArray.getBoolean(n4, a4.e.o0));
                    continue block82;
                }
                case 80: {
                    a5.d(80, typedArray.getBoolean(n4, a4.e.n0));
                    continue block82;
                }
                case 79: {
                    a5.a(79, typedArray.getFloat(n4, a4.d.g));
                    continue block82;
                }
                case 78: {
                    a5.b(78, typedArray.getInt(n4, a4.c.c));
                    continue block82;
                }
                case 77: {
                    a5.c(77, typedArray.getString(n4));
                    continue block82;
                }
                case 76: {
                    a5.b(76, typedArray.getInt(n4, a4.d.e));
                    continue block82;
                }
                case 75: {
                    a5.d(75, typedArray.getBoolean(n4, a4.e.p0));
                    continue block82;
                }
                case 74: {
                    a5.c(74, typedArray.getString(n4));
                    continue block82;
                }
                case 73: {
                    a5.b(73, typedArray.getDimensionPixelSize(n4, a4.e.i0));
                    continue block82;
                }
                case 72: {
                    a5.b(72, typedArray.getInt(n4, a4.e.h0));
                    continue block82;
                }
                case 71: {
                    Log.e((String)"ConstraintSet", (String)"CURRENTLY UNSUPPORTED");
                    continue block82;
                }
                case 70: {
                    a5.a(70, typedArray.getFloat(n4, 1.0f));
                    continue block82;
                }
                case 69: {
                    a5.a(69, typedArray.getFloat(n4, 1.0f));
                    continue block82;
                }
                case 68: {
                    a5.a(68, typedArray.getFloat(n4, a4.c.e));
                    continue block82;
                }
                case 67: {
                    a5.a(67, typedArray.getFloat(n4, a4.d.i));
                    continue block82;
                }
                case 66: {
                    a5.b(66, typedArray.getInt(n4, 0));
                    continue block82;
                }
                case 65: {
                    if (typedArray.peekValue((int)n4).type == 3) {
                        a5.c(65, typedArray.getString(n4));
                        continue block82;
                    }
                    a5.c(65, s.c.c[typedArray.getInteger(n4, 0)]);
                    continue block82;
                }
                case 64: {
                    a5.b(64, androidx.constraintlayout.widget.b.E(typedArray, n4, a4.d.b));
                    continue block82;
                }
                case 63: {
                    a5.a(63, typedArray.getFloat(n4, a4.e.D));
                    continue block82;
                }
                case 62: {
                    a5.b(62, typedArray.getDimensionPixelSize(n4, a4.e.C));
                    continue block82;
                }
                case 60: {
                    a5.a(60, typedArray.getFloat(n4, a4.f.b));
                    continue block82;
                }
                case 59: {
                    a5.b(59, typedArray.getDimensionPixelSize(n4, a4.e.e0));
                    continue block82;
                }
                case 58: {
                    a5.b(58, typedArray.getDimensionPixelSize(n4, a4.e.d0));
                    continue block82;
                }
                case 57: {
                    a5.b(57, typedArray.getDimensionPixelSize(n4, a4.e.c0));
                    continue block82;
                }
                case 56: {
                    a5.b(56, typedArray.getDimensionPixelSize(n4, a4.e.b0));
                    continue block82;
                }
                case 55: {
                    a5.b(55, typedArray.getInt(n4, a4.e.a0));
                    continue block82;
                }
                case 54: {
                    a5.b(54, typedArray.getInt(n4, a4.e.Z));
                    continue block82;
                }
                case 53: {
                    a5.a(53, typedArray.getDimension(n4, a4.f.l));
                    continue block82;
                }
                case 52: {
                    a5.a(52, typedArray.getDimension(n4, a4.f.k));
                    continue block82;
                }
                case 51: {
                    a5.a(51, typedArray.getDimension(n4, a4.f.j));
                    continue block82;
                }
                case 50: {
                    a5.a(50, typedArray.getDimension(n4, a4.f.h));
                    continue block82;
                }
                case 49: {
                    a5.a(49, typedArray.getDimension(n4, a4.f.g));
                    continue block82;
                }
                case 48: {
                    a5.a(48, typedArray.getFloat(n4, a4.f.f));
                    continue block82;
                }
                case 47: {
                    a5.a(47, typedArray.getFloat(n4, a4.f.e));
                    continue block82;
                }
                case 46: {
                    a5.a(46, typedArray.getFloat(n4, a4.f.d));
                    continue block82;
                }
                case 45: {
                    a5.a(45, typedArray.getFloat(n4, a4.f.c));
                    continue block82;
                }
                case 44: {
                    a5.d(44, true);
                    a5.a(44, typedArray.getDimension(n4, a4.f.n));
                    continue block82;
                }
                case 43: {
                    a5.a(43, typedArray.getFloat(n4, a4.c.d));
                    continue block82;
                }
                case 42: {
                    a5.b(42, typedArray.getInt(n4, a4.e.Y));
                    continue block82;
                }
                case 41: {
                    a5.b(41, typedArray.getInt(n4, a4.e.X));
                    continue block82;
                }
                case 40: {
                    a5.a(40, typedArray.getFloat(n4, a4.e.V));
                    continue block82;
                }
                case 39: {
                    a5.a(39, typedArray.getFloat(n4, a4.e.W));
                    continue block82;
                }
                case 38: {
                    a4.a = n4 = typedArray.getResourceId(n4, a4.a);
                    a5.b(38, n4);
                    continue block82;
                }
                case 37: {
                    a5.a(37, typedArray.getFloat(n4, a4.e.z));
                    continue block82;
                }
                case 34: {
                    a5.b(34, typedArray.getDimensionPixelSize(n4, a4.e.J));
                    continue block82;
                }
                case 31: {
                    a5.b(31, typedArray.getDimensionPixelSize(n4, a4.e.M));
                    continue block82;
                }
                case 28: {
                    a5.b(28, typedArray.getDimensionPixelSize(n4, a4.e.I));
                    continue block82;
                }
                case 27: {
                    a5.b(27, typedArray.getInt(n4, a4.e.G));
                    continue block82;
                }
                case 24: {
                    a5.b(24, typedArray.getDimensionPixelSize(n4, a4.e.H));
                    continue block82;
                }
                case 23: {
                    a5.b(23, typedArray.getLayoutDimension(n4, a4.e.d));
                    continue block82;
                }
                case 22: {
                    a5.b(22, i[typedArray.getInt(n4, a4.c.b)]);
                    continue block82;
                }
                case 21: {
                    a5.b(21, typedArray.getLayoutDimension(n4, a4.e.e));
                    continue block82;
                }
                case 20: {
                    a5.a(20, typedArray.getFloat(n4, a4.e.y));
                    continue block82;
                }
                case 19: {
                    a5.a(19, typedArray.getFloat(n4, a4.e.h));
                    continue block82;
                }
                case 18: {
                    a5.b(18, typedArray.getDimensionPixelOffset(n4, a4.e.g));
                    continue block82;
                }
                case 17: {
                    a5.b(17, typedArray.getDimensionPixelOffset(n4, a4.e.f));
                    continue block82;
                }
                case 16: {
                    a5.b(16, typedArray.getDimensionPixelSize(n4, a4.e.P));
                    continue block82;
                }
                case 15: {
                    a5.b(15, typedArray.getDimensionPixelSize(n4, a4.e.T));
                    continue block82;
                }
                case 14: {
                    a5.b(14, typedArray.getDimensionPixelSize(n4, a4.e.Q));
                    continue block82;
                }
                case 13: {
                    a5.b(13, typedArray.getDimensionPixelSize(n4, a4.e.O));
                    continue block82;
                }
                case 12: {
                    a5.b(12, typedArray.getDimensionPixelSize(n4, a4.e.S));
                    continue block82;
                }
                case 11: {
                    a5.b(11, typedArray.getDimensionPixelSize(n4, a4.e.R));
                    continue block82;
                }
                case 8: {
                    a5.b(8, typedArray.getDimensionPixelSize(n4, a4.e.L));
                    continue block82;
                }
                case 7: {
                    a5.b(7, typedArray.getDimensionPixelOffset(n4, a4.e.F));
                    continue block82;
                }
                case 6: {
                    a5.b(6, typedArray.getDimensionPixelOffset(n4, a4.e.E));
                    continue block82;
                }
                case 5: {
                    a5.c(5, typedArray.getString(n4));
                    continue block82;
                }
                case 2: {
                    a5.b(2, typedArray.getDimensionPixelSize(n4, a4.e.K));
                }
            }
        }
    }

    public static void M(a object, int n3, float f3) {
        if (n3 != 19) {
            if (n3 != 20) {
                if (n3 != 37) {
                    if (n3 != 60) {
                        if (n3 != 63) {
                            if (n3 != 79) {
                                if (n3 != 85) {
                                    if (n3 != 87) {
                                        if (n3 != 39) {
                                            if (n3 != 40) {
                                                switch (n3) {
                                                    default: {
                                                        switch (n3) {
                                                            default: {
                                                                Log.w((String)"ConstraintSet", (String)"Unknown attribute 0x");
                                                                return;
                                                            }
                                                            case 70: {
                                                                ((a)object).e.g0 = f3;
                                                                return;
                                                            }
                                                            case 69: {
                                                                ((a)object).e.f0 = f3;
                                                                return;
                                                            }
                                                            case 68: {
                                                                ((a)object).c.e = f3;
                                                                return;
                                                            }
                                                            case 67: 
                                                        }
                                                        ((a)object).d.i = f3;
                                                        return;
                                                    }
                                                    case 53: {
                                                        ((a)object).f.l = f3;
                                                        return;
                                                    }
                                                    case 52: {
                                                        ((a)object).f.k = f3;
                                                        return;
                                                    }
                                                    case 51: {
                                                        ((a)object).f.j = f3;
                                                        return;
                                                    }
                                                    case 50: {
                                                        ((a)object).f.h = f3;
                                                        return;
                                                    }
                                                    case 49: {
                                                        ((a)object).f.g = f3;
                                                        return;
                                                    }
                                                    case 48: {
                                                        ((a)object).f.f = f3;
                                                        return;
                                                    }
                                                    case 47: {
                                                        ((a)object).f.e = f3;
                                                        return;
                                                    }
                                                    case 46: {
                                                        ((a)object).f.d = f3;
                                                        return;
                                                    }
                                                    case 45: {
                                                        ((a)object).f.c = f3;
                                                        return;
                                                    }
                                                    case 44: {
                                                        object = ((a)object).f;
                                                        ((e)object).n = f3;
                                                        ((e)object).m = true;
                                                        return;
                                                    }
                                                    case 43: 
                                                }
                                                ((a)object).c.d = f3;
                                                return;
                                            }
                                            ((a)object).e.V = f3;
                                            return;
                                        }
                                        ((a)object).e.W = f3;
                                    }
                                    return;
                                }
                                ((a)object).d.j = f3;
                                return;
                            }
                            ((a)object).d.g = f3;
                            return;
                        }
                        ((a)object).e.D = f3;
                        return;
                    }
                    ((a)object).f.b = f3;
                    return;
                }
                ((a)object).e.z = f3;
                return;
            }
            ((a)object).e.y = f3;
            return;
        }
        ((a)object).e.h = f3;
    }

    public static void N(a a4, int n3, int n4) {
        if (n3 != 6) {
            if (n3 != 7) {
                if (n3 != 8) {
                    if (n3 != 27) {
                        if (n3 != 28) {
                            if (n3 != 41) {
                                if (n3 != 42) {
                                    if (n3 != 61) {
                                        if (n3 != 62) {
                                            if (n3 != 72) {
                                                if (n3 != 73) {
                                                    switch (n3) {
                                                        default: {
                                                            switch (n3) {
                                                                default: {
                                                                    switch (n3) {
                                                                        default: {
                                                                            switch (n3) {
                                                                                default: {
                                                                                    switch (n3) {
                                                                                        default: {
                                                                                            Log.w((String)"ConstraintSet", (String)"Unknown attribute 0x");
                                                                                            return;
                                                                                        }
                                                                                        case 89: {
                                                                                            a4.d.n = n4;
                                                                                            return;
                                                                                        }
                                                                                        case 88: {
                                                                                            a4.d.m = n4;
                                                                                        }
                                                                                        case 87: 
                                                                                    }
                                                                                    return;
                                                                                }
                                                                                case 84: {
                                                                                    a4.d.k = n4;
                                                                                    return;
                                                                                }
                                                                                case 83: {
                                                                                    a4.f.i = n4;
                                                                                    return;
                                                                                }
                                                                                case 82: 
                                                                            }
                                                                            a4.d.c = n4;
                                                                            return;
                                                                        }
                                                                        case 59: {
                                                                            a4.e.e0 = n4;
                                                                            return;
                                                                        }
                                                                        case 58: {
                                                                            a4.e.d0 = n4;
                                                                            return;
                                                                        }
                                                                        case 57: {
                                                                            a4.e.c0 = n4;
                                                                            return;
                                                                        }
                                                                        case 56: {
                                                                            a4.e.b0 = n4;
                                                                            return;
                                                                        }
                                                                        case 55: {
                                                                            a4.e.a0 = n4;
                                                                            return;
                                                                        }
                                                                        case 54: 
                                                                    }
                                                                    a4.e.Z = n4;
                                                                    return;
                                                                }
                                                                case 24: {
                                                                    a4.e.H = n4;
                                                                    return;
                                                                }
                                                                case 23: {
                                                                    a4.e.d = n4;
                                                                    return;
                                                                }
                                                                case 22: {
                                                                    a4.c.b = n4;
                                                                    return;
                                                                }
                                                                case 21: 
                                                            }
                                                            a4.e.e = n4;
                                                            return;
                                                        }
                                                        case 97: {
                                                            a4.e.q0 = n4;
                                                            return;
                                                        }
                                                        case 94: {
                                                            a4.e.U = n4;
                                                            return;
                                                        }
                                                        case 93: {
                                                            a4.e.N = n4;
                                                            return;
                                                        }
                                                        case 78: {
                                                            a4.c.c = n4;
                                                            return;
                                                        }
                                                        case 76: {
                                                            a4.d.e = n4;
                                                            return;
                                                        }
                                                        case 66: {
                                                            a4.d.f = n4;
                                                            return;
                                                        }
                                                        case 64: {
                                                            a4.d.b = n4;
                                                            return;
                                                        }
                                                        case 38: {
                                                            a4.a = n4;
                                                            return;
                                                        }
                                                        case 34: {
                                                            a4.e.J = n4;
                                                            return;
                                                        }
                                                        case 31: {
                                                            a4.e.M = n4;
                                                            return;
                                                        }
                                                        case 18: {
                                                            a4.e.g = n4;
                                                            return;
                                                        }
                                                        case 17: {
                                                            a4.e.f = n4;
                                                            return;
                                                        }
                                                        case 16: {
                                                            a4.e.P = n4;
                                                            return;
                                                        }
                                                        case 15: {
                                                            a4.e.T = n4;
                                                            return;
                                                        }
                                                        case 14: {
                                                            a4.e.Q = n4;
                                                            return;
                                                        }
                                                        case 13: {
                                                            a4.e.O = n4;
                                                            return;
                                                        }
                                                        case 12: {
                                                            a4.e.S = n4;
                                                            return;
                                                        }
                                                        case 11: {
                                                            a4.e.R = n4;
                                                            return;
                                                        }
                                                        case 2: 
                                                    }
                                                    a4.e.K = n4;
                                                    return;
                                                }
                                                a4.e.i0 = n4;
                                                return;
                                            }
                                            a4.e.h0 = n4;
                                            return;
                                        }
                                        a4.e.C = n4;
                                        return;
                                    }
                                    a4.e.B = n4;
                                    return;
                                }
                                a4.e.Y = n4;
                                return;
                            }
                            a4.e.X = n4;
                            return;
                        }
                        a4.e.I = n4;
                        return;
                    }
                    a4.e.G = n4;
                    return;
                }
                a4.e.L = n4;
                return;
            }
            a4.e.F = n4;
            return;
        }
        a4.e.E = n4;
    }

    public static void O(a object, int n3, String string) {
        if (n3 != 5) {
            if (n3 != 65) {
                if (n3 != 74) {
                    if (n3 != 77) {
                        if (n3 != 87) {
                            if (n3 != 90) {
                                Log.w((String)"ConstraintSet", (String)"Unknown attribute 0x");
                                return;
                            }
                            ((a)object).d.l = string;
                        }
                        return;
                    }
                    ((a)object).e.m0 = string;
                    return;
                }
                object = ((a)object).e;
                ((b)object).l0 = string;
                ((b)object).k0 = null;
                return;
            }
            ((a)object).d.d = string;
            return;
        }
        ((a)object).e.A = string;
    }

    public static void P(a a4, int n3, boolean bl) {
        if (n3 != 44) {
            if (n3 != 75) {
                if (n3 != 87) {
                    if (n3 != 80) {
                        if (n3 != 81) {
                            Log.w((String)"ConstraintSet", (String)"Unknown attribute 0x");
                            return;
                        }
                        a4.e.o0 = bl;
                        return;
                    }
                    a4.e.n0 = bl;
                }
                return;
            }
            a4.e.p0 = bl;
            return;
        }
        a4.f.m = bl;
    }

    public static a m(Context context, XmlPullParser object) {
        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)object);
        object = new a();
        context = context.obtainStyledAttributes(attributeSet, y.d.ConstraintOverride);
        androidx.constraintlayout.widget.b.J((a)object, (TypedArray)context);
        context.recycle();
        return object;
    }

    public int A(int n3) {
        return this.u((int)n3).c.c;
    }

    public int B(int n3) {
        return this.u((int)n3).e.d;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void C(Context object, int n3) {
        int n4;
        XmlResourceParser xmlResourceParser;
        block6: {
            XmlPullParserException xmlPullParserException2;
            block7: {
                xmlResourceParser = object.getResources().getXml(n3);
                try {
                    n4 = xmlResourceParser.getEventType();
                    break block6;
                }
                catch (IOException iOException) {
                }
                catch (XmlPullParserException xmlPullParserException2) {
                    break block7;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Error parsing resource: ");
                ((StringBuilder)object).append(n3);
                Log.e((String)"ConstraintSet", (String)((StringBuilder)object).toString(), (Throwable)iOException);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error parsing resource: ");
            stringBuilder.append(n3);
            Log.e((String)"ConstraintSet", (String)stringBuilder.toString(), (Throwable)xmlPullParserException2);
            return;
        }
        while (n4 != 1) {
            if (n4 == 2) {
                String string = xmlResourceParser.getName();
                a a4 = this.t((Context)object, Xml.asAttributeSet((XmlPullParser)xmlResourceParser), false);
                if (string.equalsIgnoreCase("Guideline")) {
                    a4.e.a = true;
                }
                this.h.put(a4.a, a4);
            }
            n4 = xmlResourceParser.next();
        }
    }

    /*
     * Exception decompiling
     */
    public void D(Context var1_1, XmlPullParser var2_4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 3[TRYBLOCK] [7, 6 : 131->145)] java.io.IOException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public final void I(a object, TypedArray typedArray, boolean bl) {
        if (bl) {
            androidx.constraintlayout.widget.b.J((a)object, typedArray);
            return;
        }
        int n3 = typedArray.getIndexCount();
        block96: for (int i3 = 0; i3 < n3; ++i3) {
            int n4 = typedArray.getIndex(i3);
            if (n4 != y.d.Constraint_android_id && y.d.Constraint_android_layout_marginStart != n4 && y.d.Constraint_android_layout_marginEnd != n4) {
                ((a)object).d.a = true;
                ((a)object).e.b = true;
                ((a)object).c.a = true;
                ((a)object).f.a = true;
            }
            switch (j.get(n4)) {
                default: {
                    Object object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unknown attribute 0x");
                    ((StringBuilder)object2).append(Integer.toHexString(n4));
                    ((StringBuilder)object2).append("   ");
                    ((StringBuilder)object2).append(j.get(n4));
                    Log.w((String)"ConstraintSet", (String)((StringBuilder)object2).toString());
                    continue block96;
                }
                case 97: {
                    Object object2 = ((a)object).e;
                    ((b)object2).q0 = typedArray.getInt(n4, ((b)object2).q0);
                    continue block96;
                }
                case 96: {
                    androidx.constraintlayout.widget.b.F(((a)object).e, typedArray, n4, 1);
                    continue block96;
                }
                case 95: {
                    androidx.constraintlayout.widget.b.F(((a)object).e, typedArray, n4, 0);
                    continue block96;
                }
                case 94: {
                    Object object2 = ((a)object).e;
                    ((b)object2).U = typedArray.getDimensionPixelSize(n4, ((b)object2).U);
                    continue block96;
                }
                case 93: {
                    Object object2 = ((a)object).e;
                    ((b)object2).N = typedArray.getDimensionPixelSize(n4, ((b)object2).N);
                    continue block96;
                }
                case 92: {
                    Object object2 = ((a)object).e;
                    ((b)object2).t = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).t);
                    continue block96;
                }
                case 91: {
                    Object object2 = ((a)object).e;
                    ((b)object2).s = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).s);
                    continue block96;
                }
                case 87: {
                    Object object2 = new StringBuilder();
                    ((StringBuilder)object2).append("unused attribute 0x");
                    ((StringBuilder)object2).append(Integer.toHexString(n4));
                    ((StringBuilder)object2).append("   ");
                    ((StringBuilder)object2).append(j.get(n4));
                    Log.w((String)"ConstraintSet", (String)((StringBuilder)object2).toString());
                    continue block96;
                }
                case 86: {
                    Object object2;
                    int n5 = typedArray.peekValue((int)n4).type;
                    if (n5 == 1) {
                        ((a)object).d.n = typedArray.getResourceId(n4, -1);
                        object2 = ((a)object).d;
                        if (((c)object2).n == -1) continue block96;
                        ((c)object2).m = -2;
                        continue block96;
                    }
                    if (n5 == 3) {
                        ((a)object).d.l = typedArray.getString(n4);
                        if (((a)object).d.l.indexOf("/") > 0) {
                            ((a)object).d.n = typedArray.getResourceId(n4, -1);
                            ((a)object).d.m = -2;
                            continue block96;
                        }
                        ((a)object).d.m = -1;
                        continue block96;
                    }
                    object2 = ((a)object).d;
                    ((c)object2).m = typedArray.getInteger(n4, ((c)object2).n);
                    continue block96;
                }
                case 85: {
                    Object object2 = ((a)object).d;
                    ((c)object2).j = typedArray.getFloat(n4, ((c)object2).j);
                    continue block96;
                }
                case 84: {
                    Object object2 = ((a)object).d;
                    ((c)object2).k = typedArray.getInteger(n4, ((c)object2).k);
                    continue block96;
                }
                case 83: {
                    Object object2 = ((a)object).f;
                    ((e)object2).i = androidx.constraintlayout.widget.b.E(typedArray, n4, ((e)object2).i);
                    continue block96;
                }
                case 82: {
                    Object object2 = ((a)object).d;
                    ((c)object2).c = typedArray.getInteger(n4, ((c)object2).c);
                    continue block96;
                }
                case 81: {
                    Object object2 = ((a)object).e;
                    ((b)object2).o0 = typedArray.getBoolean(n4, ((b)object2).o0);
                    continue block96;
                }
                case 80: {
                    Object object2 = ((a)object).e;
                    ((b)object2).n0 = typedArray.getBoolean(n4, ((b)object2).n0);
                    continue block96;
                }
                case 79: {
                    Object object2 = ((a)object).d;
                    ((c)object2).g = typedArray.getFloat(n4, ((c)object2).g);
                    continue block96;
                }
                case 78: {
                    Object object2 = ((a)object).c;
                    ((d)object2).c = typedArray.getInt(n4, ((d)object2).c);
                    continue block96;
                }
                case 77: {
                    ((a)object).e.m0 = typedArray.getString(n4);
                    continue block96;
                }
                case 76: {
                    Object object2 = ((a)object).d;
                    ((c)object2).e = typedArray.getInt(n4, ((c)object2).e);
                    continue block96;
                }
                case 75: {
                    Object object2 = ((a)object).e;
                    ((b)object2).p0 = typedArray.getBoolean(n4, ((b)object2).p0);
                    continue block96;
                }
                case 74: {
                    ((a)object).e.l0 = typedArray.getString(n4);
                    continue block96;
                }
                case 73: {
                    Object object2 = ((a)object).e;
                    ((b)object2).i0 = typedArray.getDimensionPixelSize(n4, ((b)object2).i0);
                    continue block96;
                }
                case 72: {
                    Object object2 = ((a)object).e;
                    ((b)object2).h0 = typedArray.getInt(n4, ((b)object2).h0);
                    continue block96;
                }
                case 71: {
                    Log.e((String)"ConstraintSet", (String)"CURRENTLY UNSUPPORTED");
                    continue block96;
                }
                case 70: {
                    ((a)object).e.g0 = typedArray.getFloat(n4, 1.0f);
                    continue block96;
                }
                case 69: {
                    ((a)object).e.f0 = typedArray.getFloat(n4, 1.0f);
                    continue block96;
                }
                case 68: {
                    Object object2 = ((a)object).c;
                    ((d)object2).e = typedArray.getFloat(n4, ((d)object2).e);
                    continue block96;
                }
                case 67: {
                    Object object2 = ((a)object).d;
                    ((c)object2).i = typedArray.getFloat(n4, ((c)object2).i);
                    continue block96;
                }
                case 66: {
                    ((a)object).d.f = typedArray.getInt(n4, 0);
                    continue block96;
                }
                case 65: {
                    if (typedArray.peekValue((int)n4).type == 3) {
                        ((a)object).d.d = typedArray.getString(n4);
                        continue block96;
                    }
                    ((a)object).d.d = s.c.c[typedArray.getInteger(n4, 0)];
                    continue block96;
                }
                case 64: {
                    Object object2 = ((a)object).d;
                    ((c)object2).b = androidx.constraintlayout.widget.b.E(typedArray, n4, ((c)object2).b);
                    continue block96;
                }
                case 63: {
                    Object object2 = ((a)object).e;
                    ((b)object2).D = typedArray.getFloat(n4, ((b)object2).D);
                    continue block96;
                }
                case 62: {
                    Object object2 = ((a)object).e;
                    ((b)object2).C = typedArray.getDimensionPixelSize(n4, ((b)object2).C);
                    continue block96;
                }
                case 61: {
                    Object object2 = ((a)object).e;
                    ((b)object2).B = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).B);
                    continue block96;
                }
                case 60: {
                    Object object2 = ((a)object).f;
                    ((e)object2).b = typedArray.getFloat(n4, ((e)object2).b);
                    continue block96;
                }
                case 59: {
                    Object object2 = ((a)object).e;
                    ((b)object2).e0 = typedArray.getDimensionPixelSize(n4, ((b)object2).e0);
                    continue block96;
                }
                case 58: {
                    Object object2 = ((a)object).e;
                    ((b)object2).d0 = typedArray.getDimensionPixelSize(n4, ((b)object2).d0);
                    continue block96;
                }
                case 57: {
                    Object object2 = ((a)object).e;
                    ((b)object2).c0 = typedArray.getDimensionPixelSize(n4, ((b)object2).c0);
                    continue block96;
                }
                case 56: {
                    Object object2 = ((a)object).e;
                    ((b)object2).b0 = typedArray.getDimensionPixelSize(n4, ((b)object2).b0);
                    continue block96;
                }
                case 55: {
                    Object object2 = ((a)object).e;
                    ((b)object2).a0 = typedArray.getInt(n4, ((b)object2).a0);
                    continue block96;
                }
                case 54: {
                    Object object2 = ((a)object).e;
                    ((b)object2).Z = typedArray.getInt(n4, ((b)object2).Z);
                    continue block96;
                }
                case 53: {
                    Object object2 = ((a)object).f;
                    ((e)object2).l = typedArray.getDimension(n4, ((e)object2).l);
                    continue block96;
                }
                case 52: {
                    Object object2 = ((a)object).f;
                    ((e)object2).k = typedArray.getDimension(n4, ((e)object2).k);
                    continue block96;
                }
                case 51: {
                    Object object2 = ((a)object).f;
                    ((e)object2).j = typedArray.getDimension(n4, ((e)object2).j);
                    continue block96;
                }
                case 50: {
                    Object object2 = ((a)object).f;
                    ((e)object2).h = typedArray.getDimension(n4, ((e)object2).h);
                    continue block96;
                }
                case 49: {
                    Object object2 = ((a)object).f;
                    ((e)object2).g = typedArray.getDimension(n4, ((e)object2).g);
                    continue block96;
                }
                case 48: {
                    Object object2 = ((a)object).f;
                    ((e)object2).f = typedArray.getFloat(n4, ((e)object2).f);
                    continue block96;
                }
                case 47: {
                    Object object2 = ((a)object).f;
                    ((e)object2).e = typedArray.getFloat(n4, ((e)object2).e);
                    continue block96;
                }
                case 46: {
                    Object object2 = ((a)object).f;
                    ((e)object2).d = typedArray.getFloat(n4, ((e)object2).d);
                    continue block96;
                }
                case 45: {
                    Object object2 = ((a)object).f;
                    ((e)object2).c = typedArray.getFloat(n4, ((e)object2).c);
                    continue block96;
                }
                case 44: {
                    Object object2 = ((a)object).f;
                    ((e)object2).m = true;
                    ((e)object2).n = typedArray.getDimension(n4, ((e)object2).n);
                    continue block96;
                }
                case 43: {
                    Object object2 = ((a)object).c;
                    ((d)object2).d = typedArray.getFloat(n4, ((d)object2).d);
                    continue block96;
                }
                case 42: {
                    Object object2 = ((a)object).e;
                    ((b)object2).Y = typedArray.getInt(n4, ((b)object2).Y);
                    continue block96;
                }
                case 41: {
                    Object object2 = ((a)object).e;
                    ((b)object2).X = typedArray.getInt(n4, ((b)object2).X);
                    continue block96;
                }
                case 40: {
                    Object object2 = ((a)object).e;
                    ((b)object2).V = typedArray.getFloat(n4, ((b)object2).V);
                    continue block96;
                }
                case 39: {
                    Object object2 = ((a)object).e;
                    ((b)object2).W = typedArray.getFloat(n4, ((b)object2).W);
                    continue block96;
                }
                case 38: {
                    ((a)object).a = typedArray.getResourceId(n4, ((a)object).a);
                    continue block96;
                }
                case 37: {
                    Object object2 = ((a)object).e;
                    ((b)object2).z = typedArray.getFloat(n4, ((b)object2).z);
                    continue block96;
                }
                case 36: {
                    Object object2 = ((a)object).e;
                    ((b)object2).n = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).n);
                    continue block96;
                }
                case 35: {
                    Object object2 = ((a)object).e;
                    ((b)object2).o = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).o);
                    continue block96;
                }
                case 34: {
                    Object object2 = ((a)object).e;
                    ((b)object2).J = typedArray.getDimensionPixelSize(n4, ((b)object2).J);
                    continue block96;
                }
                case 33: {
                    Object object2 = ((a)object).e;
                    ((b)object2).v = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).v);
                    continue block96;
                }
                case 32: {
                    Object object2 = ((a)object).e;
                    ((b)object2).u = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).u);
                    continue block96;
                }
                case 31: {
                    Object object2 = ((a)object).e;
                    ((b)object2).M = typedArray.getDimensionPixelSize(n4, ((b)object2).M);
                    continue block96;
                }
                case 30: {
                    Object object2 = ((a)object).e;
                    ((b)object2).m = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).m);
                    continue block96;
                }
                case 29: {
                    Object object2 = ((a)object).e;
                    ((b)object2).l = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).l);
                    continue block96;
                }
                case 28: {
                    Object object2 = ((a)object).e;
                    ((b)object2).I = typedArray.getDimensionPixelSize(n4, ((b)object2).I);
                    continue block96;
                }
                case 27: {
                    Object object2 = ((a)object).e;
                    ((b)object2).G = typedArray.getInt(n4, ((b)object2).G);
                    continue block96;
                }
                case 26: {
                    Object object2 = ((a)object).e;
                    ((b)object2).k = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).k);
                    continue block96;
                }
                case 25: {
                    Object object2 = ((a)object).e;
                    ((b)object2).j = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).j);
                    continue block96;
                }
                case 24: {
                    Object object2 = ((a)object).e;
                    ((b)object2).H = typedArray.getDimensionPixelSize(n4, ((b)object2).H);
                    continue block96;
                }
                case 23: {
                    Object object2 = ((a)object).e;
                    ((b)object2).d = typedArray.getLayoutDimension(n4, ((b)object2).d);
                    continue block96;
                }
                case 22: {
                    Object object2 = ((a)object).c;
                    ((d)object2).b = typedArray.getInt(n4, ((d)object2).b);
                    object2 = ((a)object).c;
                    ((d)object2).b = i[((d)object2).b];
                    continue block96;
                }
                case 21: {
                    Object object2 = ((a)object).e;
                    ((b)object2).e = typedArray.getLayoutDimension(n4, ((b)object2).e);
                    continue block96;
                }
                case 20: {
                    Object object2 = ((a)object).e;
                    ((b)object2).y = typedArray.getFloat(n4, ((b)object2).y);
                    continue block96;
                }
                case 19: {
                    Object object2 = ((a)object).e;
                    ((b)object2).h = typedArray.getFloat(n4, ((b)object2).h);
                    continue block96;
                }
                case 18: {
                    Object object2 = ((a)object).e;
                    ((b)object2).g = typedArray.getDimensionPixelOffset(n4, ((b)object2).g);
                    continue block96;
                }
                case 17: {
                    Object object2 = ((a)object).e;
                    ((b)object2).f = typedArray.getDimensionPixelOffset(n4, ((b)object2).f);
                    continue block96;
                }
                case 16: {
                    Object object2 = ((a)object).e;
                    ((b)object2).P = typedArray.getDimensionPixelSize(n4, ((b)object2).P);
                    continue block96;
                }
                case 15: {
                    Object object2 = ((a)object).e;
                    ((b)object2).T = typedArray.getDimensionPixelSize(n4, ((b)object2).T);
                    continue block96;
                }
                case 14: {
                    Object object2 = ((a)object).e;
                    ((b)object2).Q = typedArray.getDimensionPixelSize(n4, ((b)object2).Q);
                    continue block96;
                }
                case 13: {
                    Object object2 = ((a)object).e;
                    ((b)object2).O = typedArray.getDimensionPixelSize(n4, ((b)object2).O);
                    continue block96;
                }
                case 12: {
                    Object object2 = ((a)object).e;
                    ((b)object2).S = typedArray.getDimensionPixelSize(n4, ((b)object2).S);
                    continue block96;
                }
                case 11: {
                    Object object2 = ((a)object).e;
                    ((b)object2).R = typedArray.getDimensionPixelSize(n4, ((b)object2).R);
                    continue block96;
                }
                case 10: {
                    Object object2 = ((a)object).e;
                    ((b)object2).w = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).w);
                    continue block96;
                }
                case 9: {
                    Object object2 = ((a)object).e;
                    ((b)object2).x = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).x);
                    continue block96;
                }
                case 8: {
                    Object object2 = ((a)object).e;
                    ((b)object2).L = typedArray.getDimensionPixelSize(n4, ((b)object2).L);
                    continue block96;
                }
                case 7: {
                    Object object2 = ((a)object).e;
                    ((b)object2).F = typedArray.getDimensionPixelOffset(n4, ((b)object2).F);
                    continue block96;
                }
                case 6: {
                    Object object2 = ((a)object).e;
                    ((b)object2).E = typedArray.getDimensionPixelOffset(n4, ((b)object2).E);
                    continue block96;
                }
                case 5: {
                    ((a)object).e.A = typedArray.getString(n4);
                    continue block96;
                }
                case 4: {
                    Object object2 = ((a)object).e;
                    ((b)object2).p = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).p);
                    continue block96;
                }
                case 3: {
                    Object object2 = ((a)object).e;
                    ((b)object2).q = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).q);
                    continue block96;
                }
                case 2: {
                    Object object2 = ((a)object).e;
                    ((b)object2).K = typedArray.getDimensionPixelSize(n4, ((b)object2).K);
                    continue block96;
                }
                case 1: {
                    Object object2 = ((a)object).e;
                    ((b)object2).r = androidx.constraintlayout.widget.b.E(typedArray, n4, ((b)object2).r);
                }
            }
        }
        object = ((a)object).e;
        if (((b)object).l0 != null) {
            ((b)object).k0 = null;
        }
    }

    public void K(ConstraintLayout constraintLayout) {
        int n3 = constraintLayout.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object;
            View view = constraintLayout.getChildAt(i3);
            Object object2 = (ConstraintLayout.LayoutParams)view.getLayoutParams();
            int n4 = view.getId();
            if (this.g && n4 == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.h.containsKey(n4)) {
                this.h.put(n4, new a());
            }
            if ((object = (a)this.h.get(n4)) == null) continue;
            if (!((a)object).e.b) {
                ((a)object).g(n4, (ConstraintLayout.LayoutParams)((Object)object2));
                if (view instanceof ConstraintHelper) {
                    ((a)object).e.k0 = ((ConstraintHelper)view).getReferencedIds();
                    if (view instanceof Barrier) {
                        object2 = (Barrier)view;
                        ((a)object).e.p0 = ((Barrier)((Object)object2)).getAllowsGoneWidget();
                        ((a)object).e.h0 = ((Barrier)((Object)object2)).getType();
                        ((a)object).e.i0 = ((Barrier)((Object)object2)).getMargin();
                    }
                }
                ((a)object).e.b = true;
            }
            object2 = ((a)object).c;
            if (!((d)object2).a) {
                ((d)object2).b = view.getVisibility();
                ((a)object).c.d = view.getAlpha();
                ((a)object).c.a = true;
            }
            object2 = ((a)object).f;
            if (((e)object2).a) continue;
            ((e)object2).a = true;
            ((e)object2).b = view.getRotation();
            ((a)object).f.c = view.getRotationX();
            ((a)object).f.d = view.getRotationY();
            ((a)object).f.e = view.getScaleX();
            ((a)object).f.f = view.getScaleY();
            float f3 = view.getPivotX();
            float f4 = view.getPivotY();
            if ((double)f3 != 0.0 || (double)f4 != 0.0) {
                object2 = ((a)object).f;
                ((e)object2).g = f3;
                ((e)object2).h = f4;
            }
            ((a)object).f.j = view.getTranslationX();
            ((a)object).f.k = view.getTranslationY();
            ((a)object).f.l = view.getTranslationZ();
            object = ((a)object).f;
            if (!((e)object).m) continue;
            ((e)object).n = view.getElevation();
        }
    }

    public void L(b b3) {
        for (Object object : b3.h.keySet()) {
            ((Integer)object).intValue();
            a a4 = (a)b3.h.get(object);
            if (!this.h.containsKey(object)) {
                this.h.put(object, new a());
            }
            if ((object = (a)this.h.get(object)) == null) continue;
            Iterator iterator = ((a)object).e;
            if (!((b)((Object)iterator)).b) {
                ((b)((Object)iterator)).a(a4.e);
            }
            iterator = ((a)object).c;
            if (!((d)((Object)iterator)).a) {
                ((d)((Object)iterator)).a(a4.c);
            }
            iterator = ((a)object).f;
            if (!((e)((Object)iterator)).a) {
                ((e)((Object)iterator)).a(a4.f);
            }
            iterator = ((a)object).d;
            if (!((c)((Object)iterator)).a) {
                ((c)((Object)iterator)).a(a4.d);
            }
            for (String string : a4.g.keySet()) {
                if (((a)object).g.containsKey(string)) continue;
                ((a)object).g.put(string, (androidx.constraintlayout.widget.a)a4.g.get(string));
            }
        }
    }

    public void Q(boolean bl) {
        this.g = bl;
    }

    public void R(String stringArray) {
        this.d = stringArray.split(",");
        for (int i3 = 0; i3 < (stringArray = this.d).length; ++i3) {
            stringArray[i3] = stringArray[i3].trim();
        }
    }

    public void S(boolean bl) {
        this.a = bl;
    }

    public void g(ConstraintLayout constraintLayout) {
        int n3 = constraintLayout.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object;
            View view = constraintLayout.getChildAt(i3);
            int n4 = view.getId();
            if (!this.h.containsKey(n4)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("id unknown ");
                ((StringBuilder)object).append(x.a.d(view));
                Log.w((String)"ConstraintSet", (String)((StringBuilder)object).toString());
                continue;
            }
            if (this.g && n4 == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.h.containsKey(n4) || (object = (a)this.h.get(n4)) == null) continue;
            androidx.constraintlayout.widget.a.j(view, ((a)object).g);
        }
    }

    public void h(b object2) {
        for (Object object2 : ((b)object2).h.values()) {
            a a4;
            if (((a)object2).h == null) continue;
            if (((a)object2).b == null) {
                a4 = this.v(((a)object2).a);
                ((a)object2).h.e(a4);
                continue;
            }
            Iterator iterator = this.h.keySet().iterator();
            while (iterator.hasNext()) {
                a4 = this.v((Integer)iterator.next());
                Object object3 = a4.e.m0;
                if (object3 == null || !((a)object2).b.matches((String)object3)) continue;
                ((a)object2).h.e(a4);
                object3 = (HashMap)((a)object2).g.clone();
                a4.g.putAll(object3);
            }
        }
    }

    public void i(ConstraintLayout constraintLayout) {
        this.k(constraintLayout, true);
        constraintLayout.setConstraintSet(null);
        constraintLayout.requestLayout();
    }

    public void j(ConstraintHelper constraintHelper, u.e e3, ConstraintLayout.LayoutParams layoutParams, SparseArray sparseArray) {
        a a4;
        int n3 = constraintHelper.getId();
        if (this.h.containsKey(n3) && (a4 = (a)this.h.get(n3)) != null && e3 instanceof j) {
            constraintHelper.p(a4, (j)e3, layoutParams, sparseArray);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void k(ConstraintLayout constraintLayout, boolean bl) {
        Object object;
        Object object2;
        Object object3;
        Object object4;
        Object object5;
        int n3;
        int n4 = constraintLayout.getChildCount();
        Object object6 = new HashSet(this.h.keySet());
        int n5 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            block22: {
                block21: {
                    object5 = constraintLayout.getChildAt(n3);
                    int n6 = object5.getId();
                    if (!this.h.containsKey(n6)) {
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("id unknown ");
                        ((StringBuilder)object4).append(x.a.d((View)object5));
                        Log.w((String)"ConstraintSet", (String)((StringBuilder)object4).toString());
                        continue;
                    }
                    if (this.g && n6 == -1) {
                        throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                    }
                    if (n6 == -1 || !this.h.containsKey(n6)) continue;
                    ((HashSet)object6).remove(n6);
                    object4 = (a)this.h.get(n6);
                    if (object4 == null) continue;
                    if (object5 instanceof Barrier) {
                        ((a)object4).e.j0 = 1;
                        object3 = (Barrier)((Object)object5);
                        object3.setId(n6);
                        ((Barrier)((Object)object3)).setType(((a)object4).e.h0);
                        ((Barrier)((Object)object3)).setMargin(((a)object4).e.i0);
                        ((Barrier)((Object)object3)).setAllowsGoneWidget(((a)object4).e.p0);
                        object2 = ((a)object4).e;
                        object = object2.k0;
                        if (object != null) {
                            ((ConstraintHelper)((Object)object3)).setReferencedIds((int[])object);
                        } else {
                            object = object2.l0;
                            if (object != null) {
                                object2.k0 = this.s((View)object3, (String)object);
                                ((ConstraintHelper)((Object)object3)).setReferencedIds(((a)object4).e.k0);
                            }
                        }
                    }
                    object3 = (ConstraintLayout.LayoutParams)object5.getLayoutParams();
                    ((ConstraintLayout.LayoutParams)((Object)object3)).c();
                    ((a)object4).e((ConstraintLayout.LayoutParams)((Object)object3));
                    if (bl) {
                        androidx.constraintlayout.widget.a.j((View)object5, ((a)object4).g);
                    }
                    object5.setLayoutParams((ViewGroup.LayoutParams)object3);
                    object3 = ((a)object4).c;
                    if (((d)object3).c == 0) {
                        object5.setVisibility(((d)object3).b);
                    }
                    object5.setAlpha(((a)object4).c.d);
                    object5.setRotation(((a)object4).f.b);
                    object5.setRotationX(((a)object4).f.c);
                    object5.setRotationY(((a)object4).f.d);
                    object5.setScaleX(((a)object4).f.e);
                    object5.setScaleY(((a)object4).f.f);
                    object3 = ((a)object4).f;
                    if (((e)object3).i == -1) break block21;
                    object3 = ((View)object5.getParent()).findViewById(((a)object4).f.i);
                    if (object3 != null) {
                        float f3 = (float)(object3.getTop() + object3.getBottom()) / 2.0f;
                        float f4 = (float)(object3.getLeft() + object3.getRight()) / 2.0f;
                        if (object5.getRight() - object5.getLeft() > 0 && object5.getBottom() - object5.getTop() > 0) {
                            float f5 = object5.getLeft();
                            float f6 = object5.getTop();
                            object5.setPivotX(f4 - f5);
                            object5.setPivotY(f3 - f6);
                        }
                    }
                    break block22;
                }
                if (!Float.isNaN(((e)object3).g)) {
                    object5.setPivotX(((a)object4).f.g);
                }
                if (!Float.isNaN(((a)object4).f.h)) {
                    object5.setPivotY(((a)object4).f.h);
                }
            }
            object5.setTranslationX(((a)object4).f.j);
            object5.setTranslationY(((a)object4).f.k);
            object5.setTranslationZ(((a)object4).f.l);
            object4 = ((a)object4).f;
            if (!((e)object4).m) continue;
            object5.setElevation(((e)object4).n);
        }
        object6 = ((HashSet)object6).iterator();
        while (true) {
            n3 = n5;
            if (!object6.hasNext()) break;
            object4 = (Integer)object6.next();
            object5 = (a)this.h.get(object4);
            if (object5 == null) continue;
            if (((a)object5).e.j0 == 1) {
                object3 = new Barrier(constraintLayout.getContext());
                object3.setId(((Integer)object4).intValue());
                object2 = ((a)object5).e;
                object = object2.k0;
                if (object != null) {
                    ((ConstraintHelper)((Object)object3)).setReferencedIds((int[])object);
                } else {
                    object = object2.l0;
                    if (object != null) {
                        object2.k0 = this.s((View)object3, (String)object);
                        ((ConstraintHelper)((Object)object3)).setReferencedIds(((a)object5).e.k0);
                    }
                }
                ((Barrier)((Object)object3)).setType(((a)object5).e.h0);
                ((Barrier)((Object)object3)).setMargin(((a)object5).e.i0);
                object2 = constraintLayout.j();
                ((ConstraintHelper)((Object)object3)).w();
                ((a)object5).e((ConstraintLayout.LayoutParams)((Object)object2));
                constraintLayout.addView((View)object3, (ViewGroup.LayoutParams)object2);
            }
            if (!((a)object5).e.a) continue;
            object3 = new Guideline(constraintLayout.getContext());
            object3.setId(((Integer)object4).intValue());
            object4 = constraintLayout.j();
            ((a)object5).e((ConstraintLayout.LayoutParams)((Object)object4));
            constraintLayout.addView((View)object3, (ViewGroup.LayoutParams)object4);
        }
        while (n3 < n4) {
            object5 = constraintLayout.getChildAt(n3);
            if (object5 instanceof ConstraintHelper) {
                ((ConstraintHelper)((Object)object5)).j(constraintLayout);
            }
            ++n3;
        }
        return;
    }

    public void l(int n3, ConstraintLayout.LayoutParams layoutParams) {
        a a4;
        if (this.h.containsKey(n3) && (a4 = (a)this.h.get(n3)) != null) {
            a4.e(layoutParams);
        }
    }

    public void n(Context context, int n3) {
        this.o((ConstraintLayout)LayoutInflater.from((Context)context).inflate(n3, null));
    }

    public void o(ConstraintLayout constraintLayout) {
        int n3 = constraintLayout.getChildCount();
        this.h.clear();
        for (int i3 = 0; i3 < n3; ++i3) {
            a a4;
            View view = constraintLayout.getChildAt(i3);
            Object object = (ConstraintLayout.LayoutParams)view.getLayoutParams();
            int n4 = view.getId();
            if (this.g && n4 == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.h.containsKey(n4)) {
                this.h.put(n4, new a());
            }
            if ((a4 = (a)this.h.get(n4)) == null) continue;
            a4.g = androidx.constraintlayout.widget.a.b(this.f, view);
            a4.g(n4, object);
            a4.c.b = view.getVisibility();
            a4.c.d = view.getAlpha();
            a4.f.b = view.getRotation();
            a4.f.c = view.getRotationX();
            a4.f.d = view.getRotationY();
            a4.f.e = view.getScaleX();
            a4.f.f = view.getScaleY();
            float f3 = view.getPivotX();
            float f4 = view.getPivotY();
            if ((double)f3 != 0.0 || (double)f4 != 0.0) {
                object = a4.f;
                ((e)object).g = f3;
                ((e)object).h = f4;
            }
            a4.f.j = view.getTranslationX();
            a4.f.k = view.getTranslationY();
            a4.f.l = view.getTranslationZ();
            object = a4.f;
            if (((e)object).m) {
                ((e)object).n = view.getElevation();
            }
            if (!(view instanceof Barrier)) continue;
            view = (Barrier)view;
            a4.e.p0 = view.getAllowsGoneWidget();
            a4.e.k0 = view.getReferencedIds();
            a4.e.h0 = view.getType();
            a4.e.i0 = view.getMargin();
        }
    }

    public void p(b b3) {
        this.h.clear();
        for (Integer n3 : b3.h.keySet()) {
            a a4 = (a)b3.h.get(n3);
            if (a4 == null) continue;
            this.h.put(n3, a4.f());
        }
    }

    public void q(Constraints constraints) {
        int n3 = constraints.getChildCount();
        this.h.clear();
        for (int i3 = 0; i3 < n3; ++i3) {
            a a4;
            View view = constraints.getChildAt(i3);
            Constraints.LayoutParams layoutParams = (Constraints.LayoutParams)view.getLayoutParams();
            int n4 = view.getId();
            if (this.g && n4 == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.h.containsKey(n4)) {
                this.h.put(n4, new a());
            }
            if ((a4 = (a)this.h.get(n4)) == null) continue;
            if (view instanceof ConstraintHelper) {
                a4.i((ConstraintHelper)view, n4, layoutParams);
            }
            a4.h(n4, layoutParams);
        }
    }

    public void r(int n3, int n4, int n5, float f3) {
        b b3 = this.u((int)n3).e;
        b3.B = n4;
        b3.C = n5;
        b3.D = f3;
    }

    public final int[] s(View object, String object2) {
        String[] stringArray = ((String)object2).split(",");
        Context context = object.getContext();
        object2 = new int[stringArray.length];
        int n3 = 0;
        int n4 = 0;
        while (n3 < stringArray.length) {
            int n5;
            Object object3 = stringArray[n3].trim();
            try {
                n5 = y.c.class.getField((String)object3).getInt(null);
            }
            catch (Exception exception) {
                n5 = 0;
            }
            int n6 = n5;
            if (n5 == 0) {
                n6 = context.getResources().getIdentifier((String)object3, "id", context.getPackageName());
            }
            n5 = n6;
            if (n6 == 0) {
                n5 = n6;
                if (object.isInEditMode()) {
                    n5 = n6;
                    if (object.getParent() instanceof ConstraintLayout) {
                        object3 = ((ConstraintLayout)object.getParent()).o(0, object3);
                        n5 = n6;
                        if (object3 != null) {
                            n5 = n6;
                            if (object3 instanceof Integer) {
                                n5 = (Integer)object3;
                            }
                        }
                    }
                }
            }
            object2[n4] = n5;
            ++n3;
            ++n4;
        }
        object = object2;
        if (n4 != stringArray.length) {
            object = Arrays.copyOf((int[])object2, n4);
        }
        return object;
    }

    public final a t(Context context, AttributeSet attributeSet, boolean bl) {
        a a4 = new a();
        int[] nArray = bl ? y.d.ConstraintOverride : y.d.Constraint;
        context = context.obtainStyledAttributes(attributeSet, nArray);
        this.I(a4, (TypedArray)context, bl);
        context.recycle();
        return a4;
    }

    public final a u(int n3) {
        if (!this.h.containsKey(n3)) {
            this.h.put(n3, new a());
        }
        return (a)this.h.get(n3);
    }

    public a v(int n3) {
        if (this.h.containsKey(n3)) {
            return (a)this.h.get(n3);
        }
        return null;
    }

    public int w(int n3) {
        return this.u((int)n3).e.e;
    }

    public int[] x() {
        Object object = this.h.keySet();
        Integer[] integerArray = object.toArray(new Integer[0]);
        int n3 = integerArray.length;
        object = new int[n3];
        for (int i3 = 0; i3 < n3; ++i3) {
            object[i3] = integerArray[i3];
        }
        return object;
    }

    public a y(int n3) {
        return this.u(n3);
    }

    public int z(int n3) {
        return this.u((int)n3).c.b;
    }

    public static class androidx.constraintlayout.widget.b$a {
        public int a;
        public String b;
        public final d c = new d();
        public final c d = new c();
        public final b e = new b();
        public final e f = new e();
        public HashMap g = new HashMap();
        public a h;

        public void d(androidx.constraintlayout.widget.b$a a4) {
            a a5 = this.h;
            if (a5 != null) {
                a5.e(a4);
            }
        }

        public void e(ConstraintLayout.LayoutParams layoutParams) {
            b b3 = this.e;
            layoutParams.e = b3.j;
            layoutParams.f = b3.k;
            layoutParams.g = b3.l;
            layoutParams.h = b3.m;
            layoutParams.i = b3.n;
            layoutParams.j = b3.o;
            layoutParams.k = b3.p;
            layoutParams.l = b3.q;
            layoutParams.m = b3.r;
            layoutParams.n = b3.s;
            layoutParams.o = b3.t;
            layoutParams.s = b3.u;
            layoutParams.t = b3.v;
            layoutParams.u = b3.w;
            layoutParams.v = b3.x;
            layoutParams.leftMargin = b3.H;
            layoutParams.rightMargin = b3.I;
            layoutParams.topMargin = b3.J;
            layoutParams.bottomMargin = b3.K;
            layoutParams.A = b3.T;
            layoutParams.B = b3.S;
            layoutParams.x = b3.P;
            layoutParams.z = b3.R;
            layoutParams.G = b3.y;
            layoutParams.H = b3.z;
            layoutParams.p = b3.B;
            layoutParams.q = b3.C;
            layoutParams.r = b3.D;
            layoutParams.I = b3.A;
            layoutParams.X = b3.E;
            layoutParams.Y = b3.F;
            layoutParams.M = b3.V;
            layoutParams.L = b3.W;
            layoutParams.O = b3.Y;
            layoutParams.N = b3.X;
            layoutParams.a0 = b3.n0;
            layoutParams.b0 = b3.o0;
            layoutParams.P = b3.Z;
            layoutParams.Q = b3.a0;
            layoutParams.T = b3.b0;
            layoutParams.U = b3.c0;
            layoutParams.R = b3.d0;
            layoutParams.S = b3.e0;
            layoutParams.V = b3.f0;
            layoutParams.W = b3.g0;
            layoutParams.Z = b3.G;
            layoutParams.c = b3.h;
            layoutParams.a = b3.f;
            layoutParams.b = b3.g;
            layoutParams.width = b3.d;
            layoutParams.height = b3.e;
            String string = b3.m0;
            if (string != null) {
                layoutParams.c0 = string;
            }
            layoutParams.d0 = b3.q0;
            layoutParams.setMarginStart(b3.M);
            layoutParams.setMarginEnd(this.e.L);
            layoutParams.c();
        }

        public androidx.constraintlayout.widget.b$a f() {
            androidx.constraintlayout.widget.b$a a4 = new androidx.constraintlayout.widget.b$a();
            a4.e.a(this.e);
            a4.d.a(this.d);
            a4.c.a(this.c);
            a4.f.a(this.f);
            a4.a = this.a;
            a4.h = this.h;
            return a4;
        }

        public final void g(int n3, ConstraintLayout.LayoutParams layoutParams) {
            this.a = n3;
            b b3 = this.e;
            b3.j = layoutParams.e;
            b3.k = layoutParams.f;
            b3.l = layoutParams.g;
            b3.m = layoutParams.h;
            b3.n = layoutParams.i;
            b3.o = layoutParams.j;
            b3.p = layoutParams.k;
            b3.q = layoutParams.l;
            b3.r = layoutParams.m;
            b3.s = layoutParams.n;
            b3.t = layoutParams.o;
            b3.u = layoutParams.s;
            b3.v = layoutParams.t;
            b3.w = layoutParams.u;
            b3.x = layoutParams.v;
            b3.y = layoutParams.G;
            b3.z = layoutParams.H;
            b3.A = layoutParams.I;
            b3.B = layoutParams.p;
            b3.C = layoutParams.q;
            b3.D = layoutParams.r;
            b3.E = layoutParams.X;
            b3.F = layoutParams.Y;
            b3.G = layoutParams.Z;
            b3.h = layoutParams.c;
            b3.f = layoutParams.a;
            b3.g = layoutParams.b;
            b3.d = layoutParams.width;
            b3.e = layoutParams.height;
            b3.H = layoutParams.leftMargin;
            b3.I = layoutParams.rightMargin;
            b3.J = layoutParams.topMargin;
            b3.K = layoutParams.bottomMargin;
            b3.N = layoutParams.D;
            b3.V = layoutParams.M;
            b3.W = layoutParams.L;
            b3.Y = layoutParams.O;
            b3.X = layoutParams.N;
            b3.n0 = layoutParams.a0;
            b3.o0 = layoutParams.b0;
            b3.Z = layoutParams.P;
            b3.a0 = layoutParams.Q;
            b3.b0 = layoutParams.T;
            b3.c0 = layoutParams.U;
            b3.d0 = layoutParams.R;
            b3.e0 = layoutParams.S;
            b3.f0 = layoutParams.V;
            b3.g0 = layoutParams.W;
            b3.m0 = layoutParams.c0;
            b3.P = layoutParams.x;
            b3.R = layoutParams.z;
            b3.O = layoutParams.w;
            b3.Q = layoutParams.y;
            b3.T = layoutParams.A;
            b3.S = layoutParams.B;
            b3.U = layoutParams.C;
            b3.q0 = layoutParams.d0;
            b3.L = layoutParams.getMarginEnd();
            this.e.M = layoutParams.getMarginStart();
        }

        public final void h(int n3, Constraints.LayoutParams layoutParams) {
            this.g(n3, layoutParams);
            this.c.d = layoutParams.x0;
            e e3 = this.f;
            e3.b = layoutParams.A0;
            e3.c = layoutParams.B0;
            e3.d = layoutParams.C0;
            e3.e = layoutParams.D0;
            e3.f = layoutParams.E0;
            e3.g = layoutParams.F0;
            e3.h = layoutParams.G0;
            e3.j = layoutParams.H0;
            e3.k = layoutParams.I0;
            e3.l = layoutParams.J0;
            e3.n = layoutParams.z0;
            e3.m = layoutParams.y0;
        }

        public final void i(ConstraintHelper constraintHelper, int n3, Constraints.LayoutParams object) {
            this.h(n3, (Constraints.LayoutParams)((Object)object));
            if (constraintHelper instanceof Barrier) {
                object = this.e;
                ((b)object).j0 = 1;
                constraintHelper = (Barrier)constraintHelper;
                ((b)object).h0 = ((Barrier)constraintHelper).getType();
                this.e.k0 = constraintHelper.getReferencedIds();
                this.e.i0 = ((Barrier)constraintHelper).getMargin();
            }
        }

        public static class a {
            public int[] a = new int[10];
            public int[] b = new int[10];
            public int c = 0;
            public int[] d = new int[10];
            public float[] e = new float[10];
            public int f = 0;
            public int[] g = new int[5];
            public String[] h = new String[5];
            public int i = 0;
            public int[] j = new int[4];
            public boolean[] k = new boolean[4];
            public int l = 0;

            public void a(int n3, float f3) {
                int n4 = this.f;
                Object[] objectArray = this.d;
                if (n4 >= objectArray.length) {
                    this.d = Arrays.copyOf(objectArray, objectArray.length * 2);
                    objectArray = this.e;
                    this.e = Arrays.copyOf((float[])objectArray, objectArray.length * 2);
                }
                objectArray = this.d;
                n4 = this.f;
                objectArray[n4] = n3;
                objectArray = this.e;
                this.f = n4 + 1;
                objectArray[n4] = (int)f3;
            }

            public void b(int n3, int n4) {
                int n5 = this.c;
                int[] nArray = this.a;
                if (n5 >= nArray.length) {
                    this.a = Arrays.copyOf(nArray, nArray.length * 2);
                    nArray = this.b;
                    this.b = Arrays.copyOf(nArray, nArray.length * 2);
                }
                nArray = this.a;
                n5 = this.c;
                nArray[n5] = n3;
                nArray = this.b;
                this.c = n5 + 1;
                nArray[n5] = n4;
            }

            public void c(int n3, String string) {
                int n4 = this.i;
                Object[] objectArray = this.g;
                if (n4 >= objectArray.length) {
                    this.g = Arrays.copyOf(objectArray, objectArray.length * 2);
                    objectArray = this.h;
                    this.h = (String[])Arrays.copyOf(objectArray, objectArray.length * 2);
                }
                objectArray = this.g;
                n4 = this.i;
                objectArray[n4] = n3;
                objectArray = this.h;
                this.i = n4 + 1;
                objectArray[n4] = (int)string;
            }

            public void d(int n3, boolean bl) {
                int n4 = this.l;
                Object[] objectArray = this.j;
                if (n4 >= objectArray.length) {
                    this.j = Arrays.copyOf(objectArray, objectArray.length * 2);
                    objectArray = this.k;
                    this.k = Arrays.copyOf((boolean[])objectArray, objectArray.length * 2);
                }
                objectArray = this.j;
                n4 = this.l;
                objectArray[n4] = n3;
                objectArray = this.k;
                this.l = n4 + 1;
                objectArray[n4] = bl ? 1 : 0;
            }

            public void e(androidx.constraintlayout.widget.b$a a4) {
                int n3;
                int n4 = 0;
                for (n3 = 0; n3 < this.c; ++n3) {
                    androidx.constraintlayout.widget.b.N(a4, this.a[n3], this.b[n3]);
                }
                for (n3 = 0; n3 < this.f; ++n3) {
                    androidx.constraintlayout.widget.b.M(a4, this.d[n3], this.e[n3]);
                }
                int n5 = 0;
                while (true) {
                    if (n5 >= this.i) break;
                    androidx.constraintlayout.widget.b.O(a4, this.g[n5], this.h[n5]);
                    ++n5;
                }
                for (n3 = n4; n3 < this.l; ++n3) {
                    androidx.constraintlayout.widget.b.P(a4, this.j[n3], this.k[n3]);
                }
            }
        }
    }

    public static class b {
        public static SparseIntArray r0;
        public String A = null;
        public int B = -1;
        public int C = 0;
        public float D = 0.0f;
        public int E = -1;
        public int F = -1;
        public int G = -1;
        public int H = 0;
        public int I = 0;
        public int J = 0;
        public int K = 0;
        public int L = 0;
        public int M = 0;
        public int N = 0;
        public int O = Integer.MIN_VALUE;
        public int P = Integer.MIN_VALUE;
        public int Q = Integer.MIN_VALUE;
        public int R = Integer.MIN_VALUE;
        public int S = Integer.MIN_VALUE;
        public int T = Integer.MIN_VALUE;
        public int U = Integer.MIN_VALUE;
        public float V = -1.0f;
        public float W = -1.0f;
        public int X = 0;
        public int Y = 0;
        public int Z = 0;
        public boolean a = false;
        public int a0 = 0;
        public boolean b = false;
        public int b0 = 0;
        public boolean c = false;
        public int c0 = 0;
        public int d;
        public int d0 = 0;
        public int e;
        public int e0 = 0;
        public int f = -1;
        public float f0 = 1.0f;
        public int g = -1;
        public float g0 = 1.0f;
        public float h = -1.0f;
        public int h0 = -1;
        public boolean i = true;
        public int i0 = 0;
        public int j = -1;
        public int j0 = -1;
        public int k = -1;
        public int[] k0;
        public int l = -1;
        public String l0;
        public int m = -1;
        public String m0;
        public int n = -1;
        public boolean n0 = false;
        public int o = -1;
        public boolean o0 = false;
        public int p = -1;
        public boolean p0 = true;
        public int q = -1;
        public int q0 = 0;
        public int r = -1;
        public int s = -1;
        public int t = -1;
        public int u = -1;
        public int v = -1;
        public int w = -1;
        public int x = -1;
        public float y = 0.5f;
        public float z = 0.5f;

        static {
            SparseIntArray sparseIntArray;
            r0 = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(y.d.Layout_layout_constraintLeft_toLeftOf, 24);
            r0.append(y.d.Layout_layout_constraintLeft_toRightOf, 25);
            r0.append(y.d.Layout_layout_constraintRight_toLeftOf, 28);
            r0.append(y.d.Layout_layout_constraintRight_toRightOf, 29);
            r0.append(y.d.Layout_layout_constraintTop_toTopOf, 35);
            r0.append(y.d.Layout_layout_constraintTop_toBottomOf, 34);
            r0.append(y.d.Layout_layout_constraintBottom_toTopOf, 4);
            r0.append(y.d.Layout_layout_constraintBottom_toBottomOf, 3);
            r0.append(y.d.Layout_layout_constraintBaseline_toBaselineOf, 1);
            r0.append(y.d.Layout_layout_editor_absoluteX, 6);
            r0.append(y.d.Layout_layout_editor_absoluteY, 7);
            r0.append(y.d.Layout_layout_constraintGuide_begin, 17);
            r0.append(y.d.Layout_layout_constraintGuide_end, 18);
            r0.append(y.d.Layout_layout_constraintGuide_percent, 19);
            sparseIntArray = r0;
            int n3 = y.d.Layout_guidelineUseRtl;
            sparseIntArray.append(n3, 90);
            r0.append(y.d.Layout_android_orientation, 26);
            r0.append(y.d.Layout_layout_constraintStart_toEndOf, 31);
            r0.append(y.d.Layout_layout_constraintStart_toStartOf, 32);
            r0.append(y.d.Layout_layout_constraintEnd_toStartOf, 10);
            r0.append(y.d.Layout_layout_constraintEnd_toEndOf, 9);
            r0.append(y.d.Layout_layout_goneMarginLeft, 13);
            r0.append(y.d.Layout_layout_goneMarginTop, 16);
            r0.append(y.d.Layout_layout_goneMarginRight, 14);
            r0.append(y.d.Layout_layout_goneMarginBottom, 11);
            r0.append(y.d.Layout_layout_goneMarginStart, 15);
            r0.append(y.d.Layout_layout_goneMarginEnd, 12);
            r0.append(y.d.Layout_layout_constraintVertical_weight, 38);
            r0.append(y.d.Layout_layout_constraintHorizontal_weight, 37);
            r0.append(y.d.Layout_layout_constraintHorizontal_chainStyle, 39);
            r0.append(y.d.Layout_layout_constraintVertical_chainStyle, 40);
            r0.append(y.d.Layout_layout_constraintHorizontal_bias, 20);
            r0.append(y.d.Layout_layout_constraintVertical_bias, 36);
            r0.append(y.d.Layout_layout_constraintDimensionRatio, 5);
            r0.append(y.d.Layout_layout_constraintLeft_creator, 91);
            r0.append(y.d.Layout_layout_constraintTop_creator, 91);
            r0.append(y.d.Layout_layout_constraintRight_creator, 91);
            r0.append(y.d.Layout_layout_constraintBottom_creator, 91);
            r0.append(y.d.Layout_layout_constraintBaseline_creator, 91);
            r0.append(y.d.Layout_android_layout_marginLeft, 23);
            r0.append(y.d.Layout_android_layout_marginRight, 27);
            r0.append(y.d.Layout_android_layout_marginStart, 30);
            r0.append(y.d.Layout_android_layout_marginEnd, 8);
            r0.append(y.d.Layout_android_layout_marginTop, 33);
            r0.append(y.d.Layout_android_layout_marginBottom, 2);
            r0.append(y.d.Layout_android_layout_width, 22);
            r0.append(y.d.Layout_android_layout_height, 21);
            sparseIntArray = r0;
            int n4 = y.d.Layout_layout_constraintWidth;
            sparseIntArray.append(n4, 41);
            sparseIntArray = r0;
            int n5 = y.d.Layout_layout_constraintHeight;
            sparseIntArray.append(n5, 42);
            r0.append(y.d.Layout_layout_constrainedWidth, 87);
            r0.append(y.d.Layout_layout_constrainedHeight, 88);
            r0.append(y.d.Layout_layout_wrapBehaviorInParent, 76);
            r0.append(y.d.Layout_layout_constraintCircle, 61);
            r0.append(y.d.Layout_layout_constraintCircleRadius, 62);
            r0.append(y.d.Layout_layout_constraintCircleAngle, 63);
            r0.append(y.d.Layout_layout_constraintWidth_percent, 69);
            r0.append(y.d.Layout_layout_constraintHeight_percent, 70);
            r0.append(y.d.Layout_chainUseRtl, 71);
            r0.append(y.d.Layout_barrierDirection, 72);
            r0.append(y.d.Layout_barrierMargin, 73);
            r0.append(y.d.Layout_constraint_referenced_ids, 74);
            r0.append(y.d.Layout_barrierAllowsGoneWidgets, 75);
            sparseIntArray = r0;
            int n6 = y.d.Layout_layout_constraintWidth_max;
            sparseIntArray.append(n6, 84);
            r0.append(y.d.Layout_layout_constraintWidth_min, 86);
            r0.append(n6, 83);
            r0.append(y.d.Layout_layout_constraintHeight_min, 85);
            r0.append(n4, 87);
            r0.append(n5, 88);
            r0.append(y.d.ConstraintLayout_Layout_layout_constraintTag, 89);
            r0.append(n3, 90);
        }

        public void a(b b3) {
            this.a = b3.a;
            this.d = b3.d;
            this.b = b3.b;
            this.e = b3.e;
            this.f = b3.f;
            this.g = b3.g;
            this.h = b3.h;
            this.i = b3.i;
            this.j = b3.j;
            this.k = b3.k;
            this.l = b3.l;
            this.m = b3.m;
            this.n = b3.n;
            this.o = b3.o;
            this.p = b3.p;
            this.q = b3.q;
            this.r = b3.r;
            this.s = b3.s;
            this.t = b3.t;
            this.u = b3.u;
            this.v = b3.v;
            this.w = b3.w;
            this.x = b3.x;
            this.y = b3.y;
            this.z = b3.z;
            this.A = b3.A;
            this.B = b3.B;
            this.C = b3.C;
            this.D = b3.D;
            this.E = b3.E;
            this.F = b3.F;
            this.G = b3.G;
            this.H = b3.H;
            this.I = b3.I;
            this.J = b3.J;
            this.K = b3.K;
            this.L = b3.L;
            this.M = b3.M;
            this.N = b3.N;
            this.O = b3.O;
            this.P = b3.P;
            this.Q = b3.Q;
            this.R = b3.R;
            this.S = b3.S;
            this.T = b3.T;
            this.U = b3.U;
            this.V = b3.V;
            this.W = b3.W;
            this.X = b3.X;
            this.Y = b3.Y;
            this.Z = b3.Z;
            this.a0 = b3.a0;
            this.b0 = b3.b0;
            this.c0 = b3.c0;
            this.d0 = b3.d0;
            this.e0 = b3.e0;
            this.f0 = b3.f0;
            this.g0 = b3.g0;
            this.h0 = b3.h0;
            this.i0 = b3.i0;
            this.j0 = b3.j0;
            this.m0 = b3.m0;
            int[] nArray = b3.k0;
            this.k0 = (int[])(nArray != null && b3.l0 == null ? Arrays.copyOf(nArray, nArray.length) : null);
            this.l0 = b3.l0;
            this.n0 = b3.n0;
            this.o0 = b3.o0;
            this.p0 = b3.p0;
            this.q0 = b3.q0;
        }

        public void b(Context context, AttributeSet object) {
            context = context.obtainStyledAttributes((AttributeSet)object, y.d.Layout);
            this.b = true;
            int n3 = context.getIndexCount();
            block74: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = context.getIndex(i3);
                int n5 = r0.get(n4);
                switch (n5) {
                    default: {
                        switch (n5) {
                            default: {
                                switch (n5) {
                                    default: {
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Unknown attribute 0x");
                                        ((StringBuilder)object).append(Integer.toHexString(n4));
                                        ((StringBuilder)object).append("   ");
                                        ((StringBuilder)object).append(r0.get(n4));
                                        Log.w((String)"ConstraintSet", (String)((StringBuilder)object).toString());
                                        continue block74;
                                    }
                                    case 91: {
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("unused attribute 0x");
                                        ((StringBuilder)object).append(Integer.toHexString(n4));
                                        ((StringBuilder)object).append("   ");
                                        ((StringBuilder)object).append(r0.get(n4));
                                        Log.w((String)"ConstraintSet", (String)((StringBuilder)object).toString());
                                        continue block74;
                                    }
                                    case 90: {
                                        this.i = context.getBoolean(n4, this.i);
                                        continue block74;
                                    }
                                    case 89: {
                                        this.m0 = context.getString(n4);
                                        continue block74;
                                    }
                                    case 88: {
                                        this.o0 = context.getBoolean(n4, this.o0);
                                        continue block74;
                                    }
                                    case 87: {
                                        this.n0 = context.getBoolean(n4, this.n0);
                                        continue block74;
                                    }
                                    case 86: {
                                        this.d0 = context.getDimensionPixelSize(n4, this.d0);
                                        continue block74;
                                    }
                                    case 85: {
                                        this.e0 = context.getDimensionPixelSize(n4, this.e0);
                                        continue block74;
                                    }
                                    case 84: {
                                        this.b0 = context.getDimensionPixelSize(n4, this.b0);
                                        continue block74;
                                    }
                                    case 83: {
                                        this.c0 = context.getDimensionPixelSize(n4, this.c0);
                                        continue block74;
                                    }
                                    case 82: {
                                        this.a0 = context.getInt(n4, this.a0);
                                        continue block74;
                                    }
                                    case 81: {
                                        this.Z = context.getInt(n4, this.Z);
                                        continue block74;
                                    }
                                    case 80: {
                                        this.N = context.getDimensionPixelSize(n4, this.N);
                                        continue block74;
                                    }
                                    case 79: {
                                        this.U = context.getDimensionPixelSize(n4, this.U);
                                        continue block74;
                                    }
                                    case 78: {
                                        this.t = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.t);
                                        continue block74;
                                    }
                                    case 77: {
                                        this.s = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.s);
                                        continue block74;
                                    }
                                    case 76: {
                                        this.q0 = context.getInt(n4, this.q0);
                                        continue block74;
                                    }
                                    case 75: {
                                        this.p0 = context.getBoolean(n4, this.p0);
                                        continue block74;
                                    }
                                    case 74: {
                                        this.l0 = context.getString(n4);
                                        continue block74;
                                    }
                                    case 73: {
                                        this.i0 = context.getDimensionPixelSize(n4, this.i0);
                                        continue block74;
                                    }
                                    case 72: {
                                        this.h0 = context.getInt(n4, this.h0);
                                        continue block74;
                                    }
                                    case 71: {
                                        Log.e((String)"ConstraintSet", (String)"CURRENTLY UNSUPPORTED");
                                        continue block74;
                                    }
                                    case 70: {
                                        this.g0 = context.getFloat(n4, 1.0f);
                                        continue block74;
                                    }
                                    case 69: 
                                }
                                this.f0 = context.getFloat(n4, 1.0f);
                                continue block74;
                            }
                            case 63: {
                                this.D = context.getFloat(n4, this.D);
                                continue block74;
                            }
                            case 62: {
                                this.C = context.getDimensionPixelSize(n4, this.C);
                                continue block74;
                            }
                            case 61: 
                        }
                        this.B = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.B);
                        continue block74;
                    }
                    case 42: {
                        androidx.constraintlayout.widget.b.F(this, (TypedArray)context, n4, 1);
                        continue block74;
                    }
                    case 41: {
                        androidx.constraintlayout.widget.b.F(this, (TypedArray)context, n4, 0);
                        continue block74;
                    }
                    case 40: {
                        this.Y = context.getInt(n4, this.Y);
                        continue block74;
                    }
                    case 39: {
                        this.X = context.getInt(n4, this.X);
                        continue block74;
                    }
                    case 38: {
                        this.V = context.getFloat(n4, this.V);
                        continue block74;
                    }
                    case 37: {
                        this.W = context.getFloat(n4, this.W);
                        continue block74;
                    }
                    case 36: {
                        this.z = context.getFloat(n4, this.z);
                        continue block74;
                    }
                    case 35: {
                        this.n = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.n);
                        continue block74;
                    }
                    case 34: {
                        this.o = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.o);
                        continue block74;
                    }
                    case 33: {
                        this.J = context.getDimensionPixelSize(n4, this.J);
                        continue block74;
                    }
                    case 32: {
                        this.v = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.v);
                        continue block74;
                    }
                    case 31: {
                        this.u = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.u);
                        continue block74;
                    }
                    case 30: {
                        this.M = context.getDimensionPixelSize(n4, this.M);
                        continue block74;
                    }
                    case 29: {
                        this.m = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.m);
                        continue block74;
                    }
                    case 28: {
                        this.l = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.l);
                        continue block74;
                    }
                    case 27: {
                        this.I = context.getDimensionPixelSize(n4, this.I);
                        continue block74;
                    }
                    case 26: {
                        this.G = context.getInt(n4, this.G);
                        continue block74;
                    }
                    case 25: {
                        this.k = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.k);
                        continue block74;
                    }
                    case 24: {
                        this.j = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.j);
                        continue block74;
                    }
                    case 23: {
                        this.H = context.getDimensionPixelSize(n4, this.H);
                        continue block74;
                    }
                    case 22: {
                        this.d = context.getLayoutDimension(n4, this.d);
                        continue block74;
                    }
                    case 21: {
                        this.e = context.getLayoutDimension(n4, this.e);
                        continue block74;
                    }
                    case 20: {
                        this.y = context.getFloat(n4, this.y);
                        continue block74;
                    }
                    case 19: {
                        this.h = context.getFloat(n4, this.h);
                        continue block74;
                    }
                    case 18: {
                        this.g = context.getDimensionPixelOffset(n4, this.g);
                        continue block74;
                    }
                    case 17: {
                        this.f = context.getDimensionPixelOffset(n4, this.f);
                        continue block74;
                    }
                    case 16: {
                        this.P = context.getDimensionPixelSize(n4, this.P);
                        continue block74;
                    }
                    case 15: {
                        this.T = context.getDimensionPixelSize(n4, this.T);
                        continue block74;
                    }
                    case 14: {
                        this.Q = context.getDimensionPixelSize(n4, this.Q);
                        continue block74;
                    }
                    case 13: {
                        this.O = context.getDimensionPixelSize(n4, this.O);
                        continue block74;
                    }
                    case 12: {
                        this.S = context.getDimensionPixelSize(n4, this.S);
                        continue block74;
                    }
                    case 11: {
                        this.R = context.getDimensionPixelSize(n4, this.R);
                        continue block74;
                    }
                    case 10: {
                        this.w = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.w);
                        continue block74;
                    }
                    case 9: {
                        this.x = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.x);
                        continue block74;
                    }
                    case 8: {
                        this.L = context.getDimensionPixelSize(n4, this.L);
                        continue block74;
                    }
                    case 7: {
                        this.F = context.getDimensionPixelOffset(n4, this.F);
                        continue block74;
                    }
                    case 6: {
                        this.E = context.getDimensionPixelOffset(n4, this.E);
                        continue block74;
                    }
                    case 5: {
                        this.A = context.getString(n4);
                        continue block74;
                    }
                    case 4: {
                        this.p = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.p);
                        continue block74;
                    }
                    case 3: {
                        this.q = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.q);
                        continue block74;
                    }
                    case 2: {
                        this.K = context.getDimensionPixelSize(n4, this.K);
                        continue block74;
                    }
                    case 1: {
                        this.r = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.r);
                    }
                }
            }
            context.recycle();
        }
    }

    public static class c {
        public static SparseIntArray o;
        public boolean a = false;
        public int b = -1;
        public int c = 0;
        public String d = null;
        public int e = -1;
        public int f = 0;
        public float g = Float.NaN;
        public int h = -1;
        public float i = Float.NaN;
        public float j = Float.NaN;
        public int k = -1;
        public String l = null;
        public int m = -3;
        public int n = -1;

        static {
            SparseIntArray sparseIntArray;
            o = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(y.d.Motion_motionPathRotate, 1);
            o.append(y.d.Motion_pathMotionArc, 2);
            o.append(y.d.Motion_transitionEasing, 3);
            o.append(y.d.Motion_drawPath, 4);
            o.append(y.d.Motion_animateRelativeTo, 5);
            o.append(y.d.Motion_animateCircleAngleTo, 6);
            o.append(y.d.Motion_motionStagger, 7);
            o.append(y.d.Motion_quantizeMotionSteps, 8);
            o.append(y.d.Motion_quantizeMotionPhase, 9);
            o.append(y.d.Motion_quantizeMotionInterpolator, 10);
        }

        public void a(c c3) {
            this.a = c3.a;
            this.b = c3.b;
            this.d = c3.d;
            this.e = c3.e;
            this.f = c3.f;
            this.i = c3.i;
            this.g = c3.g;
            this.h = c3.h;
        }

        public void b(Context object, AttributeSet attributeSet) {
            attributeSet = object.obtainStyledAttributes(attributeSet, y.d.Motion);
            this.a = true;
            int n3 = attributeSet.getIndexCount();
            block12: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = attributeSet.getIndex(i3);
                switch (o.get(n4)) {
                    default: {
                        continue block12;
                    }
                    case 10: {
                        int n5 = attributeSet.peekValue((int)n4).type;
                        if (n5 == 1) {
                            this.n = n4 = attributeSet.getResourceId(n4, -1);
                            if (n4 == -1) continue block12;
                            this.m = -2;
                            continue block12;
                        }
                        if (n5 == 3) {
                            object = attributeSet.getString(n4);
                            this.l = object;
                            if (((String)object).indexOf("/") > 0) {
                                this.n = attributeSet.getResourceId(n4, -1);
                                this.m = -2;
                                continue block12;
                            }
                            this.m = -1;
                            continue block12;
                        }
                        this.m = attributeSet.getInteger(n4, this.n);
                        continue block12;
                    }
                    case 9: {
                        this.j = attributeSet.getFloat(n4, this.j);
                        continue block12;
                    }
                    case 8: {
                        this.k = attributeSet.getInteger(n4, this.k);
                        continue block12;
                    }
                    case 7: {
                        this.g = attributeSet.getFloat(n4, this.g);
                        continue block12;
                    }
                    case 6: {
                        this.c = attributeSet.getInteger(n4, this.c);
                        continue block12;
                    }
                    case 5: {
                        this.b = androidx.constraintlayout.widget.b.E((TypedArray)attributeSet, n4, this.b);
                        continue block12;
                    }
                    case 4: {
                        this.f = attributeSet.getInt(n4, 0);
                        continue block12;
                    }
                    case 3: {
                        if (attributeSet.peekValue((int)n4).type == 3) {
                            this.d = attributeSet.getString(n4);
                            continue block12;
                        }
                        this.d = s.c.c[attributeSet.getInteger(n4, 0)];
                        continue block12;
                    }
                    case 2: {
                        this.e = attributeSet.getInt(n4, this.e);
                        continue block12;
                    }
                    case 1: {
                        this.i = attributeSet.getFloat(n4, this.i);
                    }
                }
            }
            attributeSet.recycle();
        }
    }

    public static class d {
        public boolean a = false;
        public int b = 0;
        public int c = 0;
        public float d = 1.0f;
        public float e = Float.NaN;

        public void a(d d3) {
            this.a = d3.a;
            this.b = d3.b;
            this.d = d3.d;
            this.e = d3.e;
            this.c = d3.c;
        }

        public void b(Context context, AttributeSet attributeSet) {
            context = context.obtainStyledAttributes(attributeSet, y.d.PropertySet);
            this.a = true;
            int n3 = context.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = context.getIndex(i3);
                if (n4 == y.d.PropertySet_android_alpha) {
                    this.d = context.getFloat(n4, this.d);
                    continue;
                }
                if (n4 == y.d.PropertySet_android_visibility) {
                    this.b = context.getInt(n4, this.b);
                    this.b = i[this.b];
                    continue;
                }
                if (n4 == y.d.PropertySet_visibilityMode) {
                    this.c = context.getInt(n4, this.c);
                    continue;
                }
                if (n4 != y.d.PropertySet_motionProgress) continue;
                this.e = context.getFloat(n4, this.e);
            }
            context.recycle();
        }
    }

    public static class e {
        public static SparseIntArray o;
        public boolean a = false;
        public float b = 0.0f;
        public float c = 0.0f;
        public float d = 0.0f;
        public float e = 1.0f;
        public float f = 1.0f;
        public float g = Float.NaN;
        public float h = Float.NaN;
        public int i = -1;
        public float j = 0.0f;
        public float k = 0.0f;
        public float l = 0.0f;
        public boolean m = false;
        public float n = 0.0f;

        static {
            SparseIntArray sparseIntArray;
            o = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(y.d.Transform_android_rotation, 1);
            o.append(y.d.Transform_android_rotationX, 2);
            o.append(y.d.Transform_android_rotationY, 3);
            o.append(y.d.Transform_android_scaleX, 4);
            o.append(y.d.Transform_android_scaleY, 5);
            o.append(y.d.Transform_android_transformPivotX, 6);
            o.append(y.d.Transform_android_transformPivotY, 7);
            o.append(y.d.Transform_android_translationX, 8);
            o.append(y.d.Transform_android_translationY, 9);
            o.append(y.d.Transform_android_translationZ, 10);
            o.append(y.d.Transform_android_elevation, 11);
            o.append(y.d.Transform_transformPivotTarget, 12);
        }

        public void a(e e3) {
            this.a = e3.a;
            this.b = e3.b;
            this.c = e3.c;
            this.d = e3.d;
            this.e = e3.e;
            this.f = e3.f;
            this.g = e3.g;
            this.h = e3.h;
            this.i = e3.i;
            this.j = e3.j;
            this.k = e3.k;
            this.l = e3.l;
            this.m = e3.m;
            this.n = e3.n;
        }

        public void b(Context context, AttributeSet attributeSet) {
            context = context.obtainStyledAttributes(attributeSet, y.d.Transform);
            this.a = true;
            int n3 = context.getIndexCount();
            block14: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = context.getIndex(i3);
                switch (o.get(n4)) {
                    default: {
                        continue block14;
                    }
                    case 12: {
                        this.i = androidx.constraintlayout.widget.b.E((TypedArray)context, n4, this.i);
                        continue block14;
                    }
                    case 11: {
                        this.m = true;
                        this.n = context.getDimension(n4, this.n);
                        continue block14;
                    }
                    case 10: {
                        this.l = context.getDimension(n4, this.l);
                        continue block14;
                    }
                    case 9: {
                        this.k = context.getDimension(n4, this.k);
                        continue block14;
                    }
                    case 8: {
                        this.j = context.getDimension(n4, this.j);
                        continue block14;
                    }
                    case 7: {
                        this.h = context.getDimension(n4, this.h);
                        continue block14;
                    }
                    case 6: {
                        this.g = context.getDimension(n4, this.g);
                        continue block14;
                    }
                    case 5: {
                        this.f = context.getFloat(n4, this.f);
                        continue block14;
                    }
                    case 4: {
                        this.e = context.getFloat(n4, this.e);
                        continue block14;
                    }
                    case 3: {
                        this.d = context.getFloat(n4, this.d);
                        continue block14;
                    }
                    case 2: {
                        this.c = context.getFloat(n4, this.c);
                        continue block14;
                    }
                    case 1: {
                        this.b = context.getFloat(n4, this.b);
                    }
                }
            }
            context.recycle();
        }
    }
}

