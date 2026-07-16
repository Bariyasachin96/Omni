/*
 * Decompiled with CFR 0.152.
 */
package i3;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface d {
    public String c() default "";

    public String f() default "";

    public int[] l() default {};

    public String m() default "";

    public int v() default 1;
}

