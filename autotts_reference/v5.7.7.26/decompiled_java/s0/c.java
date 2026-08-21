/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ClipData
 *  android.content.ClipData$Item
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.View
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputConnectionWrapper
 *  android.view.inputmethod.InputContentInfo
 */
package s0;

import android.content.ClipData;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import n0.h;
import o0.d;
import o0.x0;
import s0.d;

public abstract class c {
    public static /* synthetic */ boolean a(View view, d d3, int n3, Bundle bundle) {
        Bundle bundle2 = bundle;
        if ((n3 & 1) != 0) {
            try {
                d3.d();
            }
            catch (Exception exception) {
                Log.w((String)"InputConnectionCompat", (String)"Can't insert content from IME; requestPermission() failed", (Throwable)exception);
                return false;
            }
            bundle2 = (Parcelable)d3.e();
            bundle = bundle == null ? new Bundle() : new Bundle(bundle);
            bundle.putParcelable("androidx.core.view.extra.INPUT_CONTENT_INFO", (Parcelable)bundle2);
            bundle2 = bundle;
        }
        return x0.X(view, new d.a(new ClipData(d3.b(), new ClipData.Item(d3.a())), 2).d(d3.c()).b(bundle2).a()) == null;
    }

    public static b b(View view) {
        h.g(view);
        return new s0.b(view);
    }

    public static InputConnection c(View view, InputConnection inputConnection, EditorInfo editorInfo) {
        return c.d(inputConnection, editorInfo, c.b(view));
    }

    public static InputConnection d(InputConnection inputConnection, EditorInfo editorInfo, b b3) {
        n0.c.c(inputConnection, "inputConnection must be non-null");
        n0.c.c(editorInfo, "editorInfo must be non-null");
        n0.c.c(b3, "onCommitContentListener must be non-null");
        return new InputConnectionWrapper(inputConnection, false, b3){
            public final b a;
            {
                this.a = b3;
                super(inputConnection, bl);
            }

            public boolean commitContent(InputContentInfo inputContentInfo, int n3, Bundle bundle) {
                if (this.a.a(d.f(inputContentInfo), n3, bundle)) {
                    return true;
                }
                return super.commitContent(inputContentInfo, n3, bundle);
            }
        };
    }

    public static interface b {
        public boolean a(d var1, int var2, Bundle var3);
    }
}

