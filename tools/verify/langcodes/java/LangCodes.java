import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

// Prove that every language code CLD2's FULL build can return is resolved the
// same way by us and by AutoTTS.
//
// The tables are read out of app/src/main/java/com/tts/easyvoice/IsoCodes.kt at
// run time, so this cannot pass against a stale copy: change the Kotlin and this
// changes with it.
//
// Two routes are compared for each code.
//
//   AutoTTS  c3.n.n(lang) decides whether a detected code is routable. It does
//            NOT normalise a three-letter code -- it scans the language list for
//            it verbatim -- and for anything else it runs c3.e.c and answers
//            FALSE outright when that returns null.
//
//   ours     the native toIso3() in tts_engine_core.cpp, whose result is looked
//            up in detectOkIso3Set. It never returns null; an unmappable code
//            comes back unchanged.
//
// The two are equal exactly when a code that c3.e.c cannot map also produces, on
// our side, a string that is not an iso3 and therefore cannot be in the set.
// That is what this asserts, code by code.
public class LangCodes {
    static final Map<String,String> toIso3Map = new HashMap<>(256);
    static final Map<String,String> toIso2Map = new HashMap<>(320);

    // ---- read the four literal lists out of IsoCodes.kt -------------------
    static List<String[]> pairs(String kotlin, String name) {
        Matcher m = Pattern.compile("val\\s+" + name + "\\s*=\\s*listOf\\((.*?)\\)\\s*\\n", Pattern.DOTALL).matcher(kotlin);
        if (!m.find()) throw new RuntimeException("IsoCodes.kt: cannot find " + name);
        List<String[]> out = new ArrayList<>();
        Matcher p = Pattern.compile("\"([^\"]+)\"\\s+to\\s+\"([^\"]+)\"").matcher(m.group(1));
        while (p.find()) out.add(new String[]{p.group(1), p.group(2)});
        if (out.isEmpty()) throw new RuntimeException("IsoCodes.kt: " + name + " parsed empty");
        return out;
    }
    static List<String> plain(String kotlin, String name) {
        Matcher m = Pattern.compile("val\\s+" + name + "\\s*=\\s*listOf\\((.*?)\\)\\s*\\n", Pattern.DOTALL).matcher(kotlin);
        if (!m.find()) throw new RuntimeException("IsoCodes.kt: cannot find " + name);
        List<String> out = new ArrayList<>();
        Matcher p = Pattern.compile("\"([^\"]+)\"").matcher(m.group(1));
        while (p.find()) out.add(p.group(1));
        if (out.isEmpty()) throw new RuntimeException("IsoCodes.kt: " + name + " parsed empty");
        return out;
    }

    // every hand-written `toIso3Map["x"] = "y"` / `toIso2Map["x"] = "y"` line,
    // in source order. Reading them rather than restating them is what makes the
    // deprecated-spelling assertion below real.
    static List<String[]> handPuts(String kotlin, String map) {
        List<String[]> out = new ArrayList<>();
        Matcher m = Pattern.compile(map + "\\[\"([^\"]+)\"\\]\\s*=\\s*\"([^\"]+)\"").matcher(kotlin);
        while (m.find()) out.add(new String[]{m.group(1), m.group(2)});
        return out;
    }

    static void buildMaps(String kotlin) {
        List<String[]> bib = pairs(kotlin, "bibliographicToTerminological");
        List<String[]> i2t = pairs(kotlin, "iso2ToTerminological");
        List<String> chinese = plain(kotlin, "chineseVariants");
        List<String> no2 = plain(kotlin, "codesWithoutIso2");
        List<String[]> put3 = handPuts(kotlin, "toIso3Map");
        List<String[]> put2 = handPuts(kotlin, "toIso2Map");

        // AutoTTS's c3.e states exactly three deprecated ISO 639-1 spellings and
        // no fourth. A jw -> jav line was added on 2026-09-09 and the owner
        // reversed it the same day: "agar CLD2 jw kehta hai to jw hi rehne do".
        // Detection is rule 5 territory, so a fourth pair here would be our own
        // decision. Fail loudly rather than let it come back.
        Set<String> deprecated = new TreeSet<>();
        for (String[] p : put3) if (p[0].equals("iw")||p[0].equals("in")||p[0].equals("ji")||p[0].equals("jw")) deprecated.add(p[0]);
        if (!deprecated.toString().equals("[in, iw, ji]"))
            throw new RuntimeException("IsoCodes.kt's deprecated-spelling pairs are " + deprecated
                + ", AutoTTS c3.e has [in, iw, ji]. jw is deliberately absent -- see CLAUDE.md, 2026-09-09.");

        for (String iso2 : Locale.getISOLanguages()) {
            String iso3;
            try { iso3 = new Locale(iso2).getISO3Language(); } catch (Exception e) { iso3 = ""; }
            if (iso3.isEmpty()) continue;
            toIso3Map.put(iso2, iso3);
            if (!toIso2Map.containsKey(iso3)) toIso2Map.put(iso3, iso2);
        }
        for (String[] p : i2t) { toIso3Map.put(p[0], p[1]); toIso2Map.put(p[1], p[0]); }
        for (String[] p : bib) { String i2 = toIso2Map.get(p[1]); if (i2 != null) toIso2Map.put(p[0], i2); }
        // The hand-written puts, the chineseVariants loop and the codesWithoutIso2
        // loop write disjoint keys, so their order relative to each other is not
        // load-bearing and this ordering matches IsoCodes.kt's.
        for (String[] p : put3) toIso3Map.put(p[0], p[1]);
        for (String[] p : put2) toIso2Map.put(p[0], p[1]);
        for (String v : chinese) toIso2Map.put(v, "zh");
        for (String c : no2) { toIso2Map.put(c, c); toIso3Map.put(c, c); }
    }

    // ---- c3.e.a / IsoCodes.normalizeTag ----------------------------------
    static String normalizeTag(String tag) {
        if (tag == null) return null;
        tag = tag.trim();
        if (tag.isEmpty()) return null;
        int cut = tag.length();
        for (int i = 0; i < tag.length(); i++) { char c = tag.charAt(i); if (c == '-' || c == '_') { cut = i; break; } }
        return tag.substring(0, cut).toLowerCase(Locale.ROOT);
    }
    // ---- c3.e.c / IsoCodes.toIso3 ----------------------------------------
    static String eDotC(String tag) {
        tag = normalizeTag(tag);
        if (tag == null) return null;
        String hit = toIso3Map.get(tag);
        if (hit != null) return hit;
        if (tag.length() == 3) {
            String i2 = toIso2Map.get(tag);
            if (i2 != null) { String back = toIso3Map.get(i2); return back != null ? back : tag; }
        }
        return null;
    }
    // ---- c3.n.n's normalisation, without the list lookup ------------------
    static String autottsKey(String code) { return code.length() == 3 ? code : eDotC(code); }

    // ---- the native toIso3() in tts_engine_core.cpp -----------------------
    // Kept in step with the C++ by run.sh, which fingerprints that function's
    // body and fails when it changes.
    static String ourKey(String code) {
        String s = code;
        for (int i = 0; i < s.length(); i++) { char c = s.charAt(i); if (c=='-'||c=='_') { s = s.substring(0, i); break; } }
        s = s.toLowerCase(Locale.ROOT);
        String iso3 = s;
        if (s.length() == 2) { String f = toIso3Map.get(s); if (f != null && !f.isEmpty()) iso3 = f; }
        if (iso3.equals("cmn")||iso3.equals("lzh")||iso3.equals("gan")||iso3.equals("hak")) return "zho";
        return iso3;
    }

    public static void main(String[] args) throws Exception {
        String kotlin = new String(Files.readAllBytes(Paths.get(args[0])), "UTF-8");
        buildMaps(kotlin);
        List<String> codes = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(args[1]))) {
            line = line.trim();
            if (!line.isEmpty() && !line.startsWith("#")) codes.add(line);
        }
        int real = 0, benign = 0;
        for (String c : codes) {
            String ak = autottsKey(c), ok = ourKey(c);
            if (ak == null) {
                // c3.n.n answers FALSE. Ours must produce something that cannot
                // be an iso3, or it could match an enabled language by accident.
                // For a three-letter tag this holds by construction -- c3.e.c
                // only returns null there when toIso2Map has no entry, which is
                // the same table this tests -- so what it really guards is the
                // two-letter and script-subtag paths, where the native rule
                // could start inventing a code that c3.e.c refused.
                if (ok.length() == 3 && toIso2Map.containsKey(ok)) {
                    System.out.println("REAL  " + c + ": n.n=FALSE but ours makes the real iso3 '" + ok + "'");
                    real++;
                } else benign++;
                continue;
            }
            if (!ak.equals(ok)) { System.out.println("REAL  " + c + ": n.n key '" + ak + "' vs ours '" + ok + "'"); real++; }
        }
        System.out.println();
        System.out.println("codes swept:                    " + codes.size());
        System.out.println("c3.e.c cannot map (n.n FALSE):  " + benign + "  -- ours cannot match either");
        System.out.println("REAL differences:               " + real);
        if (real != 0) { System.out.println("\nFAILED"); System.exit(1); }
        System.out.println("\nIDENTICAL over " + codes.size() + " CLD2 language codes");
    }
}
