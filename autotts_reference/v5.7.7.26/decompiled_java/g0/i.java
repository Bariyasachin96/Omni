/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.graphics.Typeface$CustomFallbackBuilder
 *  android.graphics.fonts.Font
 *  android.graphics.fonts.Font$Builder
 *  android.graphics.fonts.FontFamily
 *  android.graphics.fonts.FontFamily$Builder
 *  android.graphics.fonts.FontStyle
 *  android.os.CancellationSignal
 */
package g0;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import f0.e;
import g0.j;
import java.io.IOException;
import l0.g;

public class i
extends j {
    public static int h(FontStyle fontStyle, FontStyle fontStyle2) {
        int n3 = Math.abs(fontStyle.getWeight() - fontStyle2.getWeight()) / 100;
        int n4 = fontStyle.getSlant() == fontStyle2.getSlant() ? 0 : 2;
        return n3 + n4;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Typeface a(Context object, e.c object2, Resources resources, int n3) {
        e.d d3;
        Object var7_15 = null;
        e.d[] dArray = ((e.c)((Object)d3)).a();
        int n4 = dArray.length;
        int n5 = 0;
        Object var1_2 = null;
        while (true) {
            block10: {
                void var1_7;
                void var1_3;
                if (n5 < n4) {
                    void var3_13;
                    d3 = dArray[n5];
                    Font.Builder builder = new Font.Builder((Resources)var3_13, d3.b());
                    builder = builder.setWeight(d3.e()).setSlant(d3.f() ? 1 : 0).setTtcIndex(d3.c()).setFontVariationSettings(d3.d()).build();
                    if (var1_3 == null) {
                        e.d d4 = d3 = new FontFamily.Builder((Font)builder);
                        break block10;
                    } else {
                        var1_3.addFont((Font)builder);
                    }
                    break block10;
                }
                if (var1_3 == null) {
                    return null;
                }
                try {
                    void var4_14;
                    d3 = var1_3.build();
                    Typeface.CustomFallbackBuilder customFallbackBuilder = new Typeface.CustomFallbackBuilder((FontFamily)d3);
                    Typeface typeface = customFallbackBuilder.setStyle(this.g((FontFamily)d3, (int)var4_14).getStyle()).build();
                    return var1_7;
                }
                catch (Exception exception) {
                    Object var1_9 = var7_15;
                    return var1_7;
                }
                catch (IOException iOException) {}
            }
            ++n5;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Typeface b(Context var1_1, CancellationSignal var2_4, g.b[] var3_5, int var4_6) {
        var8_7 = var1_1.getContentResolver();
        var6_8 = var3_5.length;
        var5_9 = 0;
        var1_1 = null;
        while (true) {
            block14: {
                block17: {
                    block16: {
                        if (var5_9 >= var6_8) ** GOTO lbl42
                        var10_13 = var3_5[var5_9];
                        var7_10 = var1_1;
                        var9_11 = var8_7.openFileDescriptor(var10_13.d(), "r", var2_4);
                        if (var9_11 == null) {
                            var7_10 = var1_1;
                            if (var9_11 == null) break block14;
                        } else {
                            block15: {
                                try {
                                    var7_10 = new Font.Builder(var9_11);
                                    var7_10 = var7_10.setWeight(var10_13.e()).setSlant((int)var10_13.f()).setTtcIndex(var10_13.c()).build();
                                    if (var1_1 != null) break block15;
                                    var1_1 = var7_10 = new FontFamily.Builder((Font)var7_10);
                                    break block16;
                                }
                                catch (Throwable var10_14) {
                                    break block17;
                                }
                            }
                            var1_1.addFont((Font)var7_10);
                        }
                    }
                    var7_10 = var1_1;
                    var9_11.close();
                    var7_10 = var1_1;
                    break block14;
                }
                try {
                    var9_11.close();
                    ** GOTO lbl40
                }
                catch (Throwable var9_12) {
                    var7_10 = var1_1;
                    var10_14.addSuppressed(var9_12);
lbl40:
                    // 2 sources

                    var7_10 = var1_1;
                    throw var10_14;
lbl42:
                    // 1 sources

                    if (var1_1 == null) {
                        return null;
                    }
                    try {
                        var2_4 = var1_1.build();
                        var1_1 = new Typeface.CustomFallbackBuilder((FontFamily)var2_4);
                        return var1_1.setStyle(this.g((FontFamily)var2_4, var4_6).getStyle()).build();
                    }
                    catch (Exception var1_2) {
                        return null;
                    }
                    catch (IOException var1_3) {}
                }
            }
            ++var5_9;
            var1_1 = var7_10;
        }
    }

    @Override
    public Typeface c(Context context, Resources resources, int n3, String string, int n4) {
        try {
            context = new Font.Builder(resources, n3);
            context = context.build();
            resources = new FontFamily.Builder((Font)context);
            resources = resources.build();
            string = new Typeface.CustomFallbackBuilder((FontFamily)resources);
            context = string.setStyle(context.getStyle()).build();
            return context;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public g.b f(g.b[] bArray, int n3) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    public final Font g(FontFamily fontFamily, int n3) {
        int n4 = (n3 & 1) != 0 ? 700 : 400;
        int n5 = 1;
        n3 = (n3 & 2) != 0 ? 1 : 0;
        FontStyle fontStyle = new FontStyle(n4, n3);
        Font font = fontFamily.getFont(0);
        n4 = i.h(fontStyle, font.getStyle());
        n3 = n5;
        n5 = n4;
        while (n3 < fontFamily.getSize()) {
            Font font2 = fontFamily.getFont(n3);
            int n6 = i.h(fontStyle, font2.getStyle());
            n4 = n5;
            if (n6 < n5) {
                font = font2;
                n4 = n6;
            }
            ++n3;
            n5 = n4;
        }
        return font;
    }
}

