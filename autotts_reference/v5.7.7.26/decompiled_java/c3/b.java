/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.media.AudioManager$OnAudioFocusChangeListener
 */
package c3;

import android.media.AudioManager;
import com.vnspeak.autotts.AutoTtsService;

public final class b
implements AudioManager.OnAudioFocusChangeListener {
    public final void onAudioFocusChange(int n3) {
        AutoTtsService.a(n3);
    }
}

