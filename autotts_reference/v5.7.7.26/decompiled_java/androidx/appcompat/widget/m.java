/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ClipData
 *  android.content.ClipboardManager
 *  android.content.ContextWrapper
 *  android.os.Build$VERSION
 *  android.text.Selection
 *  android.text.Spannable
 *  android.view.DragEvent
 *  android.view.View
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContextWrapper;
import android.os.Build;
import android.text.Selection;
import android.text.Spannable;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;
import java.util.Objects;
import o0.d;
import o0.x0;

public abstract class m {
    public static boolean a(View view, DragEvent dragEvent) {
        if (Build.VERSION.SDK_INT < 31 && dragEvent.getLocalState() == null && x0.B(view) != null) {
            Activity activity = m.c(view);
            if (activity == null) {
                Objects.toString(view);
                return false;
            }
            if (dragEvent.getAction() == 1) {
                return !(view instanceof TextView);
            }
            if (dragEvent.getAction() == 3) {
                if (view instanceof TextView) {
                    return a.a(dragEvent, (TextView)view, activity);
                }
                return a.b(dragEvent, view, activity);
            }
        }
        return false;
    }

    public static boolean b(TextView textView, int n3) {
        int n4 = Build.VERSION.SDK_INT;
        int n5 = 0;
        if (n4 < 31 && x0.B((View)textView) != null && (n3 == 0x1020022 || n3 == 16908337)) {
            Object object = (ClipboardManager)textView.getContext().getSystemService("clipboard");
            object = object == null ? null : object.getPrimaryClip();
            if (object != null && object.getItemCount() > 0) {
                object = new d.a((ClipData)object, 1);
                n3 = n3 == 0x1020022 ? n5 : 1;
                x0.X((View)textView, ((d.a)object).c(n3).a());
            }
            return true;
        }
        return false;
    }

    public static Activity c(View view) {
        view = view.getContext();
        while (view instanceof ContextWrapper) {
            if (view instanceof Activity) {
                return (Activity)view;
            }
            view = ((ContextWrapper)view).getBaseContext();
        }
        return null;
    }

    public static final abstract class a {
        public static boolean a(DragEvent dragEvent, TextView textView, Activity object) {
            object.requestDragAndDropPermissions(dragEvent);
            int n3 = textView.getOffsetForPosition(dragEvent.getX(), dragEvent.getY());
            textView.beginBatchEdit();
            try {
                Selection.setSelection((Spannable)((Spannable)textView.getText()), (int)n3);
                object = new d.a(dragEvent.getClipData(), 3);
                x0.X((View)textView, ((d.a)object).a());
                return true;
            }
            finally {
                textView.endBatchEdit();
            }
        }

        public static boolean b(DragEvent dragEvent, View view, Activity activity) {
            activity.requestDragAndDropPermissions(dragEvent);
            x0.X(view, new d.a(dragEvent.getClipData(), 3).a());
            return true;
        }
    }
}

