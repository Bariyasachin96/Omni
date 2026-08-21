/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 */
package com.google.android.material.textfield;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.AppCompatEditText;
import c.a;
import com.google.android.material.internal.i;
import com.google.android.material.internal.z;
import com.google.android.material.textfield.TextInputLayout;
import z1.l;
import z1.m;

public class TextInputEditText
extends AppCompatEditText {
    public final Rect i = new Rect();
    public boolean j;

    public TextInputEditText(Context context) {
        this(context, null);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.editTextStyle);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet, int n3) {
        super(y2.a.d(context, attributeSet, n3, 0), attributeSet, n3);
        context = z.i(context, attributeSet, m.TextInputEditText, n3, l.Widget_Design_TextInputEditText, new int[0]);
        this.setTextInputLayoutFocusedRectEnabled(context.getBoolean(m.TextInputEditText_textInputLayoutFocusedRectEnabled, false));
        context.recycle();
    }

    private CharSequence getHintFromLayout() {
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (textInputLayout != null) {
            return textInputLayout.getHint();
        }
        return null;
    }

    private TextInputLayout getTextInputLayout() {
        ViewParent viewParent = this.getParent();
        while (viewParent instanceof View) {
            if (viewParent instanceof TextInputLayout) {
                return (TextInputLayout)viewParent;
            }
            viewParent = viewParent.getParent();
        }
        return null;
    }

    public final boolean e(TextInputLayout textInputLayout) {
        return textInputLayout != null && this.j;
    }

    public void getFocusedRect(Rect rect) {
        super.getFocusedRect(rect);
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (this.e(textInputLayout) && rect != null) {
            textInputLayout.getFocusedRect(this.i);
            rect.bottom = this.i.bottom;
        }
    }

    public boolean getGlobalVisibleRect(Rect rect, Point point) {
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (this.e(textInputLayout)) {
            boolean bl = textInputLayout.getGlobalVisibleRect(rect, point);
            if (bl && point != null) {
                point.offset(-this.getScrollX(), -this.getScrollY());
            }
            return bl;
        }
        return super.getGlobalVisibleRect(rect, point);
    }

    public CharSequence getHint() {
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (textInputLayout != null && textInputLayout.T()) {
            return textInputLayout.getHint();
        }
        return super.getHint();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (textInputLayout != null && textInputLayout.T() && super.getHint() == null && com.google.android.material.internal.i.d()) {
            this.setHint("");
        }
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnection = super.onCreateInputConnection(editorInfo);
        if (inputConnection != null && editorInfo.hintText == null) {
            editorInfo.hintText = this.getHintFromLayout();
        }
        return inputConnection;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        this.getTextInputLayout();
    }

    public boolean requestRectangleOnScreen(Rect rect) {
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (this.e(textInputLayout) && rect != null) {
            int n3 = textInputLayout.getHeight();
            int n4 = this.getHeight();
            this.i.set(rect.left, rect.top, rect.right, rect.bottom + (n3 - n4));
            return super.requestRectangleOnScreen(this.i);
        }
        return super.requestRectangleOnScreen(rect);
    }

    public void setTextInputLayoutFocusedRectEnabled(boolean bl) {
        this.j = bl;
    }
}

