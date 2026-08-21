/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 */
package x;

import android.util.Pair;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.util.HashMap;

public class b {
    public static final HashMap f;
    public static final HashMap g;
    public final MotionLayout a;
    public String b = null;
    public String c = null;
    public int d = -1;
    public int e = -1;

    static {
        HashMap<String, String> hashMap;
        HashMap<Pair, String> hashMap2;
        f = hashMap2 = new HashMap<Pair, String>();
        g = hashMap = new HashMap<String, String>();
        Integer n3 = 4;
        hashMap2.put(Pair.create((Object)n3, (Object)n3), "layout_constraintBottom_toBottomOf");
        Integer n4 = 3;
        hashMap2.put(Pair.create((Object)n3, (Object)n4), "layout_constraintBottom_toTopOf");
        hashMap2.put(Pair.create((Object)n4, (Object)n3), "layout_constraintTop_toBottomOf");
        hashMap2.put(Pair.create((Object)n4, (Object)n4), "layout_constraintTop_toTopOf");
        n3 = 6;
        hashMap2.put(Pair.create((Object)n3, (Object)n3), "layout_constraintStart_toStartOf");
        n4 = 7;
        hashMap2.put(Pair.create((Object)n3, (Object)n4), "layout_constraintStart_toEndOf");
        hashMap2.put(Pair.create((Object)n4, (Object)n3), "layout_constraintEnd_toStartOf");
        hashMap2.put(Pair.create((Object)n4, (Object)n4), "layout_constraintEnd_toEndOf");
        n3 = 1;
        hashMap2.put(Pair.create((Object)n3, (Object)n3), "layout_constraintLeft_toLeftOf");
        n4 = 2;
        hashMap2.put(Pair.create((Object)n3, (Object)n4), "layout_constraintLeft_toRightOf");
        hashMap2.put(Pair.create((Object)n4, (Object)n4), "layout_constraintRight_toRightOf");
        hashMap2.put(Pair.create((Object)n4, (Object)n3), "layout_constraintRight_toLeftOf");
        n3 = 5;
        hashMap2.put(Pair.create((Object)n3, (Object)n3), "layout_constraintBaseline_toBaselineOf");
        hashMap.put("layout_constraintBottom_toBottomOf", "layout_marginBottom");
        hashMap.put("layout_constraintBottom_toTopOf", "layout_marginBottom");
        hashMap.put("layout_constraintTop_toBottomOf", "layout_marginTop");
        hashMap.put("layout_constraintTop_toTopOf", "layout_marginTop");
        hashMap.put("layout_constraintStart_toStartOf", "layout_marginStart");
        hashMap.put("layout_constraintStart_toEndOf", "layout_marginStart");
        hashMap.put("layout_constraintEnd_toStartOf", "layout_marginEnd");
        hashMap.put("layout_constraintEnd_toEndOf", "layout_marginEnd");
        hashMap.put("layout_constraintLeft_toLeftOf", "layout_marginLeft");
        hashMap.put("layout_constraintLeft_toRightOf", "layout_marginLeft");
        hashMap.put("layout_constraintRight_toRightOf", "layout_marginRight");
        hashMap.put("layout_constraintRight_toLeftOf", "layout_marginRight");
    }

    public b(MotionLayout motionLayout) {
        this.a = motionLayout;
    }
}

