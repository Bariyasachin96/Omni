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
    static native void nativeRewarmTables();
    public static void main(String[] a) {
        System.load(a[0]);
        setLanguageHints(new String[]{"en", "hi"});
        setDetectSets(new String[]{"eng", "hin"}, new String[]{"en", "hi"});
        nativeRewarmTables();
        String[] f = nativeFoldAndDetect("Hello friends, नमस्ते दोस्तों", false);
        String[] p = processDirect("Hello नमस्ते 123", "eng", "hin", "mix", 0, "", 0, "", 0, "", true, false, 1, "eng", 1, false, true, false);
        String[] w = detectWindowOrSpans("Hello friends", "eng", "hin", false, false, false);
        System.out.println("bound by RegisterNatives: fold " + f.length + " spans, processDirect " + p.length + " fields, window " + w.length + " (" + w[0].split("\u0001")[0] + ")");
    }
}
