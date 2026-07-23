/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package androidx.appcompat.app;

import android.util.AttributeSet;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class o {
    public final Deque a = new ArrayDeque();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean b(XmlPullParser xmlPullParser) {
        if (xmlPullParser == null) return true;
        try {
            if (xmlPullParser.getEventType() == 3) return true;
            int n3 = xmlPullParser.getEventType();
            if (n3 != 1) return false;
            return true;
        }
        catch (XmlPullParserException xmlPullParserException) {
            return true;
        }
    }

    public static XmlPullParser c(Deque deque) {
        while (!deque.isEmpty()) {
            XmlPullParser xmlPullParser = (XmlPullParser)((WeakReference)deque.peek()).get();
            if (o.b(xmlPullParser)) {
                deque.pop();
                continue;
            }
            return xmlPullParser;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean d(XmlPullParser xmlPullParser, XmlPullParser xmlPullParser2) {
        if (xmlPullParser2 == null) return false;
        if (xmlPullParser == xmlPullParser2) return false;
        try {
            if (xmlPullParser2.getEventType() != 2) return false;
            return "include".equals(xmlPullParser2.getName());
        }
        catch (XmlPullParserException xmlPullParserException) {
            return false;
        }
    }

    public boolean a(AttributeSet attributeSet) {
        XmlPullParser xmlPullParser;
        if (attributeSet instanceof XmlPullParser && (xmlPullParser = (XmlPullParser)attributeSet).getDepth() == 1) {
            attributeSet = o.c(this.a);
            this.a.push(new WeakReference<XmlPullParser>(xmlPullParser));
            if (o.d(xmlPullParser, (XmlPullParser)attributeSet)) {
                return true;
            }
        }
        return false;
    }
}

