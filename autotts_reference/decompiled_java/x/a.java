/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 */
package x;

import android.content.Context;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.io.Serializable;
import java.nio.CharBuffer;

public abstract class a {
    public static String a() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".(");
        stringBuilder.append(stackTraceElement.getFileName());
        stringBuilder.append(":");
        stringBuilder.append(stackTraceElement.getLineNumber());
        stringBuilder.append(") ");
        stringBuilder.append(stackTraceElement.getMethodName());
        stringBuilder.append("()");
        return stringBuilder.toString();
    }

    public static String b() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".(");
        stringBuilder.append(stackTraceElement.getFileName());
        stringBuilder.append(":");
        stringBuilder.append(stackTraceElement.getLineNumber());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String c(Context context, int n3) {
        if (n3 == -1) return "UNKNOWN";
        try {
            return context.getResources().getResourceEntryName(n3);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("?");
            stringBuilder.append(n3);
            return stringBuilder.toString();
        }
    }

    public static String d(View object) {
        try {
            object = object.getContext().getResources().getResourceEntryName(object.getId());
            return object;
        }
        catch (Exception exception) {
            return "UNKNOWN";
        }
    }

    public static String e(MotionLayout motionLayout, int n3) {
        return a.f(motionLayout, n3, -1);
    }

    public static String f(MotionLayout object, int n3, int n4) {
        if (n3 == -1) {
            return "UNDEFINED";
        }
        String string = object.getContext().getResources().getResourceEntryName(n3);
        Object object2 = string;
        if (n4 != -1) {
            object = string;
            if (string.length() > n4) {
                object = string.replaceAll("([^_])[aeiou]+", "$1");
            }
            object2 = object;
            if (((String)object).length() > n4) {
                n3 = ((String)object).replaceAll("[^_]", "").length();
                object2 = object;
                if (n3 > 0) {
                    n3 = (((String)object).length() - n4) / n3;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(CharBuffer.allocate(n3).toString().replace('\u0000', '.'));
                    ((StringBuilder)object2).append("_");
                    object2 = ((String)object).replaceAll(((StringBuilder)object2).toString(), "_");
                }
            }
        }
        return object2;
    }

    public static void g(String string, String stackTraceElementArray, int n3) {
        stackTraceElementArray = new Throwable().getStackTrace();
        int n4 = stackTraceElementArray.length;
        int n5 = 1;
        n4 = Math.min(n3, n4 - 1);
        string = " ";
        for (n3 = n5; n3 <= n4; ++n3) {
            Serializable serializable = stackTraceElementArray[n3];
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(".(");
            ((StringBuilder)serializable).append(stackTraceElementArray[n3].getFileName());
            ((StringBuilder)serializable).append(":");
            ((StringBuilder)serializable).append(stackTraceElementArray[n3].getLineNumber());
            ((StringBuilder)serializable).append(") ");
            ((StringBuilder)serializable).append(stackTraceElementArray[n3].getMethodName());
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append(" ");
            string = ((StringBuilder)serializable).toString();
        }
    }
}

