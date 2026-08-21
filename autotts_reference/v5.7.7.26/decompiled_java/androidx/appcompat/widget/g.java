/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.appcompat.widget.e0;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.k0;
import c.c;
import c.d;
import c.e;
import g0.a;

public final class g {
    public static final PorterDuff.Mode b = PorterDuff.Mode.SRC_IN;
    public static g c;
    public e0 a;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static g b() {
        synchronized (g.class) {
            try {
                if (c != null) return c;
                g.h();
                return c;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static PorterDuffColorFilter e(int n3, PorterDuff.Mode mode) {
        synchronized (g.class) {
            return e0.k(n3, mode);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void h() {
        synchronized (g.class) {
            Throwable throwable2;
            block4: {
                try {
                    if (c != null) break block4;
                    e0.c c3 = new g();
                    c = c3;
                    ((g)((Object)c3)).a = e0.g();
                    e0 e02 = g.c.a;
                    c3 = new e0.c(){
                        public final int[] a = new int[]{e.abc_textfield_search_default_mtrl_alpha, e.abc_textfield_default_mtrl_alpha, e.abc_ab_share_pack_mtrl_alpha};
                        public final int[] b = new int[]{e.abc_ic_commit_search_api_mtrl_alpha, e.abc_seekbar_tick_mark_material, e.abc_ic_menu_share_mtrl_alpha, e.abc_ic_menu_copy_mtrl_am_alpha, e.abc_ic_menu_cut_mtrl_alpha, e.abc_ic_menu_selectall_mtrl_alpha, e.abc_ic_menu_paste_mtrl_am_alpha};
                        public final int[] c = new int[]{e.abc_textfield_activated_mtrl_alpha, e.abc_textfield_search_activated_mtrl_alpha, e.abc_cab_background_top_mtrl_alpha, e.abc_text_cursor_material, e.abc_text_select_handle_left_mtrl, e.abc_text_select_handle_middle_mtrl, e.abc_text_select_handle_right_mtrl};
                        public final int[] d = new int[]{e.abc_popup_background_mtrl_mult, e.abc_cab_background_internal_bg, e.abc_menu_hardkey_panel_mtrl_mult};
                        public final int[] e = new int[]{e.abc_tab_indicator_material, e.abc_textfield_search_material};
                        public final int[] f = new int[]{e.abc_btn_check_material, e.abc_btn_radio_material, e.abc_btn_check_material_anim, e.abc_btn_radio_material_anim};

                        /*
                         * Unable to fully structure code
                         */
                        @Override
                        public boolean a(Context var1_1, int var2_2, Drawable var3_3) {
                            var7_5 = var8_4 = g.a();
                            if (this.f(this.a, var2_2)) {
                                var2_2 = c.a.colorControlNormal;
lbl4:
                                // 2 sources

                                while (true) {
                                    var4_6 = 1;
lbl6:
                                    // 2 sources

                                    while (true) {
                                        var7_5 = var8_4;
                                        var5_7 = -1;
                                        ** GOTO lbl31
                                        break;
                                    }
                                    break;
                                }
                            } else {
                                if (this.f(this.c, var2_2)) {
                                    var2_2 = c.a.colorControlActivated;
                                    ** continue;
                                }
                                if (this.f(this.d, var2_2)) {
                                    var7_5 = PorterDuff.Mode.MULTIPLY;
                                    while (true) {
                                        var4_6 = -1;
                                        var2_2 = 0x1010031;
lbl18:
                                        // 2 sources

                                        while (true) {
                                            var6_8 = 1;
                                            var5_7 = var4_6;
                                            var4_6 = var6_8;
                                            ** GOTO lbl31
                                            break;
                                        }
                                        break;
                                    }
                                } else {
                                    if (var2_2 == e.abc_list_divider_mtrl_alpha) {
                                        var4_6 = Math.round(40.8f);
                                        var2_2 = 0x1010030;
                                        ** continue;
                                    }
                                    if (var2_2 == e.abc_dialog_material_background) ** continue;
                                    var2_2 = 0;
                                    var4_6 = 0;
                                    ** continue;
                                }
                            }
lbl31:
                            // 2 sources

                            if (var4_6 != 0) {
                                var3_3 = var3_3.mutate();
                                var3_3.setColorFilter((ColorFilter)g.e(i0.c(var1_1, var2_2), var7_5));
                                if (var5_7 != -1) {
                                    var3_3.setAlpha(var5_7);
                                }
                                return true;
                            }
                            return false;
                        }

                        @Override
                        public PorterDuff.Mode b(int n3) {
                            if (n3 == e.abc_switch_thumb_material) {
                                return PorterDuff.Mode.MULTIPLY;
                            }
                            return null;
                        }

                        @Override
                        public Drawable c(e0 e02, Context context, int n3) {
                            if (n3 == e.abc_cab_background_top_material) {
                                return new LayerDrawable(new Drawable[]{e02.i(context, e.abc_cab_background_internal_bg), e02.i(context, e.abc_cab_background_top_mtrl_alpha)});
                            }
                            if (n3 == e.abc_ratingbar_material) {
                                return this.l(e02, context, d.abc_star_big);
                            }
                            if (n3 == e.abc_ratingbar_indicator_material) {
                                return this.l(e02, context, d.abc_star_medium);
                            }
                            if (n3 == e.abc_ratingbar_small_material) {
                                return this.l(e02, context, d.abc_star_small);
                            }
                            return null;
                        }

                        @Override
                        public ColorStateList d(Context context, int n3) {
                            if (n3 == e.abc_edit_text_material) {
                                return d.a.a(context, c.c.abc_tint_edittext);
                            }
                            if (n3 == e.abc_switch_track_mtrl_alpha) {
                                return d.a.a(context, c.c.abc_tint_switch_track);
                            }
                            if (n3 == e.abc_switch_thumb_material) {
                                return this.k(context);
                            }
                            if (n3 == e.abc_btn_default_mtrl_shape) {
                                return this.j(context);
                            }
                            if (n3 == e.abc_btn_borderless_material) {
                                return this.g(context);
                            }
                            if (n3 == e.abc_btn_colored_material) {
                                return this.i(context);
                            }
                            if (n3 != e.abc_spinner_mtrl_am_alpha && n3 != e.abc_spinner_textfield_background_material) {
                                if (this.f(this.b, n3)) {
                                    return i0.e(context, c.a.colorControlNormal);
                                }
                                if (this.f(this.e, n3)) {
                                    return d.a.a(context, c.c.abc_tint_default);
                                }
                                if (this.f(this.f, n3)) {
                                    return d.a.a(context, c.c.abc_tint_btn_checkable);
                                }
                                if (n3 == e.abc_seekbar_thumb_material) {
                                    return d.a.a(context, c.c.abc_tint_seek_thumb);
                                }
                                return null;
                            }
                            return d.a.a(context, c.c.abc_tint_spinner);
                        }

                        @Override
                        public boolean e(Context context, int n3, Drawable drawable) {
                            if (n3 == e.abc_seekbar_track_material) {
                                LayerDrawable layerDrawable = (LayerDrawable)drawable;
                                drawable = layerDrawable.findDrawableByLayerId(0x1020000);
                                n3 = c.a.colorControlNormal;
                                this.m(drawable, i0.c(context, n3), b);
                                this.m(layerDrawable.findDrawableByLayerId(16908303), i0.c(context, n3), b);
                                this.m(layerDrawable.findDrawableByLayerId(16908301), i0.c(context, c.a.colorControlActivated), b);
                                return true;
                            }
                            if (n3 != e.abc_ratingbar_material && n3 != e.abc_ratingbar_indicator_material && n3 != e.abc_ratingbar_small_material) {
                                return false;
                            }
                            drawable = (LayerDrawable)drawable;
                            this.m(drawable.findDrawableByLayerId(0x1020000), i0.b(context, c.a.colorControlNormal), b);
                            Drawable drawable2 = drawable.findDrawableByLayerId(16908303);
                            n3 = c.a.colorControlActivated;
                            this.m(drawable2, i0.c(context, n3), b);
                            this.m(drawable.findDrawableByLayerId(16908301), i0.c(context, n3), b);
                            return true;
                        }

                        public final boolean f(int[] nArray, int n3) {
                            int n4 = nArray.length;
                            for (int i3 = 0; i3 < n4; ++i3) {
                                if (nArray[i3] != n3) continue;
                                return true;
                            }
                            return false;
                        }

                        public final ColorStateList g(Context context) {
                            return this.h(context, 0);
                        }

                        public final ColorStateList h(Context object, int n3) {
                            int n4 = i0.c(object, c.a.colorControlHighlight);
                            int n5 = i0.b(object, c.a.colorButtonNormal);
                            int[] nArray = i0.b;
                            int[] nArray2 = i0.e;
                            int n6 = g0.a.g(n4, n3);
                            object = i0.c;
                            n4 = g0.a.g(n4, n3);
                            return new ColorStateList((int[][])new int[][]{nArray, nArray2, (int[])object, i0.i}, new int[]{n5, n6, n4, n3});
                        }

                        public final ColorStateList i(Context context) {
                            return this.h(context, i0.c(context, c.a.colorAccent));
                        }

                        public final ColorStateList j(Context context) {
                            return this.h(context, i0.c(context, c.a.colorButtonNormal));
                        }

                        public final ColorStateList k(Context context) {
                            int[][] nArrayArray = new int[3][];
                            int[] nArray = new int[3];
                            int n3 = c.a.colorSwitchThumbNormal;
                            ColorStateList colorStateList = i0.e(context, n3);
                            if (colorStateList != null && colorStateList.isStateful()) {
                                int[] nArray2 = i0.b;
                                nArrayArray[0] = nArray2;
                                nArray[0] = colorStateList.getColorForState(nArray2, 0);
                                nArrayArray[1] = i0.f;
                                nArray[1] = i0.c(context, c.a.colorControlActivated);
                                nArrayArray[2] = i0.i;
                                nArray[2] = colorStateList.getDefaultColor();
                            } else {
                                nArrayArray[0] = i0.b;
                                nArray[0] = i0.b(context, n3);
                                nArrayArray[1] = i0.f;
                                nArray[1] = i0.c(context, c.a.colorControlActivated);
                                nArrayArray[2] = i0.i;
                                nArray[2] = i0.c(context, n3);
                            }
                            return new ColorStateList((int[][])nArrayArray, nArray);
                        }

                        public final LayerDrawable l(e0 e02, Context context, int n3) {
                            n3 = context.getResources().getDimensionPixelSize(n3);
                            Drawable drawable = e02.i(context, e.abc_star_black_48dp);
                            Drawable drawable2 = e02.i(context, e.abc_star_half_black_48dp);
                            if (drawable instanceof BitmapDrawable && drawable.getIntrinsicWidth() == n3 && drawable.getIntrinsicHeight() == n3) {
                                e02 = (BitmapDrawable)drawable;
                                context = new BitmapDrawable(e02.getBitmap());
                            } else {
                                context = Bitmap.createBitmap((int)n3, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                                e02 = new Canvas((Bitmap)context);
                                drawable.setBounds(0, 0, n3, n3);
                                drawable.draw((Canvas)e02);
                                e02 = new BitmapDrawable((Bitmap)context);
                                context = new BitmapDrawable((Bitmap)context);
                            }
                            context.setTileModeX(Shader.TileMode.REPEAT);
                            if (drawable2 instanceof BitmapDrawable && drawable2.getIntrinsicWidth() == n3 && drawable2.getIntrinsicHeight() == n3) {
                                drawable2 = (BitmapDrawable)drawable2;
                            } else {
                                drawable = Bitmap.createBitmap((int)n3, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas((Bitmap)drawable);
                                drawable2.setBounds(0, 0, n3, n3);
                                drawable2.draw(canvas);
                                drawable2 = new BitmapDrawable((Bitmap)drawable);
                            }
                            e02 = new LayerDrawable(new Drawable[]{e02, drawable2, context});
                            e02.setId(0, 0x1020000);
                            e02.setId(1, 16908303);
                            e02.setId(2, 16908301);
                            return e02;
                        }

                        public final void m(Drawable drawable, int n3, PorterDuff.Mode mode) {
                            Drawable drawable2 = drawable.mutate();
                            drawable = mode;
                            if (mode == null) {
                                drawable = b;
                            }
                            drawable2.setColorFilter((ColorFilter)g.e(n3, (PorterDuff.Mode)drawable));
                        }
                    };
                    e02.t(c3);
                }
                catch (Throwable throwable2) {}
            }
            return;
            throw throwable2;
        }
    }

    public static void i(Drawable drawable, k0 k02, int[] nArray) {
        e0.v(drawable, k02, nArray);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Drawable c(Context context, int n3) {
        synchronized (this) {
            return this.a.i(context, n3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Drawable d(Context context, int n3, boolean bl) {
        synchronized (this) {
            return this.a.j(context, n3, bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ColorStateList f(Context context, int n3) {
        synchronized (this) {
            return this.a.l(context, n3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void g(Context context) {
        synchronized (this) {
            this.a.r(context);
            return;
        }
    }
}

