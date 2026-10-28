// A stand-in with the real natives' declarations (Kotlin's @JvmStatic external
// funs are static natives on this class). See run.sh.
package com.sachinbaria.easyvoice;
public class EasyVoiceTtsService {
    static native String[] processDirect(String text, String lat, String nonLat, String mode,
        int numberMode, String numberSpecific, int punctuationMode, String punctuationSpecific,
        int emojiMode, String emojiSpecific, boolean punctuationInFlow, boolean smartNumber,
        int smartNumberGroupSize, String neutralDefault, int neutralType,
        boolean disableAdvancedDetection, boolean detect, boolean singleStops);
    static native String[] nativeFoldAndDetect(String text, boolean singleStops);
    static native void setIsoMap(String[] iso2, String[] iso3);
    static native void setLanguageHints(String[] langs);
    static native void setDetectSets(String[] detectOkIso3, String[] enabledLangs);
    static native String[] detectWindowOrSpans(String text, String lat, String nonLat, boolean disableAdv, boolean wantLog, boolean singleStops);
    // The Kotlin function the core asks ML Kit through (JNI_OnLoad finds it by
    // name): here a stand-in that answers "mr" for a text holding आहे, else "hi",
    // in the app's "tag confidence-in-millionths" lines.
    static int asked;
    static String mlkitIdentify(String text) {
        asked++;
        return text.contains("आहे") ? "mr 970000\nhi 20000\n" : "hi 950000\nmr 30000\n";
    }
    static String devanagari(String text) {
        String[] f = nativeFoldAndDetect(text, false);
        return f.length >= 4 ? f[1] : "-";
    }
    public static void main(String[] a) {
        System.load(a[0]);
        // two enabled languages write Devanagari, so the core asks: the answer
        // must come back through jniIdentify
        setLanguageHints(new String[]{"en", "hi", "mr"});
        setDetectSets(new String[]{"eng", "hin", "mar"}, new String[]{"en", "hi", "mr"});
        String hi = devanagari("मेरा नाम सचिन है");
        String mr = devanagari("माझे नाव सचिन आहे");
        System.out.println("ML Kit call through JNI: " + asked + " asked, Hindi -> " + hi + ", Marathi -> " + mr);
        if (asked != 2 || !hi.equals("hi") || !mr.equals("mr")) { System.out.println("FAILED: the ML Kit call did not decide"); System.exit(1); }
        setLanguageHints(new String[]{"en", "hi"});
        setDetectSets(new String[]{"eng", "hin"}, new String[]{"en", "hi"});
        String[] f = nativeFoldAndDetect("Hello friends, नमस्ते दोस्तों", false);
        String[] p = processDirect("Hello नमस्ते 123", "eng", "hin", "mix", 0, "", 0, "", 0, "", true, false, 1, "eng", 1, false, true, false);
        String[] w = detectWindowOrSpans("Hello friends", "eng", "hin", false, false, false);
        System.out.println("bound by RegisterNatives: fold " + f.length + " spans, processDirect " + p.length + " fields, window " + w.length + " (" + w[0].split("\u0001")[0] + ")");
    }
}
