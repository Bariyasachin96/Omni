/*
 * Decompiled with CFR 0.152.
 */
package c3;

import java.util.HashMap;
import java.util.Map;

public abstract class u {
    public static final Map a;

    static {
        HashMap<String, String> hashMap;
        a = hashMap = new HashMap<String, String>();
        hashMap.put("com.google.android.tts", "Google Text-to-Speech");
        hashMap.put("com.google.android.tts.speechpack.eng", "Google TTS - English");
        hashMap.put("com.samsung.SMT", "Samsung Text-to-Speech");
        hashMap.put("com.samsung.android.ttssmt", "Samsung TTS");
        hashMap.put("com.samsung.smt", "Samsung TTS Engine");
        hashMap.put("com.huawei.hiai.speech.tts", "Huawei Text-to-Speech");
        hashMap.put("com.huawei.tts", "Huawei TTS");
        hashMap.put("com.xiaomi.mibrain.speech", "Xiaomi Text-to-Speech");
        hashMap.put("com.miui.voiceassist", "Mi Voice Assistant TTS");
        hashMap.put("com.acapelagroup.android.tts", "Acapela TTS Voices");
        hashMap.put("com.cereproc.android.tts", "CereProc Text-to-Speech");
        hashMap.put("com.cereproc.CerePlay", "CerePlay Text-to-Speech");
        hashMap.put("com.ivona.tts", "IVONA Text-to-Speech");
        hashMap.put("com.ivona.tts.hq", "IVONA Text-to-Speech HQ");
        hashMap.put("com.ivona.tts.oem", "IVONA TTS OEM");
        hashMap.put("es.codefactory.vocalizertts", "Vocalizer TTS");
        hashMap.put("com.nuance.tts", "Nuance Vocalizer");
        hashMap.put("com.nuance.vocalizer", "Nuance Vocalizer TTS");
        hashMap.put("com.reecedunn.espeak", "eSpeak TTS");
        hashMap.put("com.googlecode.eyesfree.espeak", "eSpeak TTS (Eyes-Free)");
        hashMap.put("rhzmr.espeak", "eSpeak TTS");
        hashMap.put("com.github.olga_yakovleva.rhvoice.android", "RHVoice");
        hashMap.put("com.svox.classic", "SVOX Classic TTS");
        hashMap.put("com.svox.pico", "Pico TTS");
        hashMap.put("edu.cmu.cs.speech.tts.flite", "Flite TTS");
        hashMap.put("com.hear2read.tts.kannada", "Hear2Read Kannada");
        hashMap.put("com.hear2read.tts.telugu", "Hear2Read Telugu");
        hashMap.put("com.hear2read.tts.punjabi", "Hear2Read Punjabi");
        hashMap.put("com.hear2read.tts.tamil", "Hear2Read Tamil");
        hashMap.put("com.hear2read.tts.gujarati", "Hear2Read Gujarati");
        hashMap.put("com.hear2read.tts.marathi", "Hear2Read Marathi");
        hashMap.put("com.hear2read.tts.malayalam", "Hear2Read Malayalam");
        hashMap.put("com.hear2read.tts.sanskrit", "Hear2Read Sanskrit");
        hashMap.put("com.hear2read.tts.assamese", "Hear2Read Assamese");
        hashMap.put("com.hear2read.tts.hindi", "Hear2Read Hindi");
        hashMap.put("ru.yandex.speechkit.tts", "Yandex SpeechKit TTS");
        hashMap.put("bg.bultreebank.speechlab", "SpeechLab TTS");
        hashMap.put("io.github.aholab.ahotts", "AhoTTS");
        hashMap.put("com.k2fsa.sherpa.onnx.tts.engine", "Sherpa TTS");
        hashMap.put("com.amazon.tts", "Amazon Text-to-Speech");
        hashMap.put("com.lge.tts", "LG Text-to-Speech");
        hashMap.put("com.htc.tts", "HTC Text-to-Speech");
        hashMap.put("com.voiceforge.tts", "VoiceForge TTS");
        hashMap.put("jp.kddilabs.n2tts", "N2 TTS");
        hashMap.put("com.speech.tts.engine", "Speech TTS Engine");
        hashMap.put("com.nirenr.talkman", "Jieshuo+");
    }

    public static String a(String charSequence) {
        if (charSequence != null && !((String)charSequence).isEmpty()) {
            String[] stringArray = ((String)charSequence).split("\\.");
            charSequence = new StringBuilder();
            for (int i3 = stringArray.length > 1 && ((string = stringArray[0].toLowerCase()).equals("com") || string.equals("org") || string.equals("net") || string.equals("io") || string.equals("ru") || string.equals("jp")) ? 1 : 0; i3 < stringArray.length; ++i3) {
                String string = stringArray[i3];
                if (string.isEmpty()) continue;
                ((StringBuilder)charSequence).append(Character.toUpperCase(string.charAt(0)));
                if (string.length() > 1) {
                    ((StringBuilder)charSequence).append(string.substring(1));
                }
                if (i3 >= stringArray.length - 1) continue;
                ((StringBuilder)charSequence).append(" ");
            }
            return ((StringBuilder)charSequence).toString().trim();
        }
        return "Unknown";
    }

    public static String b(String string) {
        if (string == null) {
            return "Unknown";
        }
        return a.getOrDefault(string, u.a(string));
    }
}

