// See run.sh: Java's own answers (the JDK's Unicode) against the core's.
import java.io.*; import java.util.*;
public class Check {
    static String hex(String s){ StringBuilder b = new StringBuilder(); s.codePoints().forEach(c -> { if (b.length() > 0) b.append(' '); b.append(Integer.toHexString(c).toUpperCase()); }); return b.toString(); }
    static String unhex(String h){ StringBuilder b = new StringBuilder(); if (h.isEmpty()) return ""; for (String x : h.split(" ")) b.appendCodePoint(Integer.parseInt(x, 16)); return b.toString(); }
    public static void main(String[] a) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        int n = 0, wsBad = 0, wsOld = 0, dBad = 0, dNew = 0, cBad = 0, cNew = 0, lBad = 0, lNew = 0, sBad = 0, strings = 0, nospace = 0;
        String line; StringBuilder show = new StringBuilder();
        while ((line = r.readLine()) != null) {
            String[] f = line.split("\t", -1);
            if (f[0].equals("S")) {
                strings++;
                String in = unhex(f[1]); String want = hex(in.toLowerCase(Locale.ROOT));
                if (!want.equals(f[2])) { sBad++; if (sBad <= 5) show.append("string ").append(f[1]).append(" java ").append(want).append(" core ").append(f[2]).append('\n'); }
                continue;
            }
            n++;
            int cp = Integer.parseInt(f[1], 16);
            boolean assigned = Character.getType(cp) != Character.UNASSIGNED;
            boolean ws = f[2].equals("1"), oldWs = f[3].equals("1");
            if (ws != oldWs) wsOld++;
            if (ws != Character.isWhitespace(cp)) { wsBad++; if (wsBad <= 5) show.append("ws ").append(f[1]).append('\n'); }
            int dv = Integer.parseInt(f[4]);
            int jd = Character.isDigit(cp) ? Character.digit(cp, 10) : -1;
            if (dv != jd) { if (!assigned && jd == -1) dNew++; else { dBad++; if (dBad <= 5) show.append("digit ").append(f[1]).append(' ').append(dv).append(' ').append(jd).append('\n'); } }
            boolean cur = f[5].equals("1");
            boolean jcur = (cp >= 0x80 && Character.getType(cp) == Character.CURRENCY_SYMBOL) || (cp >= 0x20A0 && cp <= 0x20CF);
            if (cur != jcur) { if (!assigned) cNew++; else { cBad++; if (cBad <= 5) show.append("currency ").append(f[1]).append('\n'); } }
            if (f[6].equals("1")) nospace++;
            String jl = hex(new String(Character.toChars(cp)).toLowerCase(Locale.ROOT));
            if (!jl.equals(f[7])) {
                // Java knows nothing of a code point (or of its case) assigned after its Unicode version
                boolean jdkOlder = !assigned || (Character.isUpperCase(cp) == false && Character.isTitleCase(cp) == false && !f[7].equals(f[1]) && Character.getType(cp) != Character.UPPERCASE_LETTER);
                if (jdkOlder) lNew++; else { lBad++; if (lBad <= 8) show.append("lower ").append(f[1]).append(" java ").append(jl).append(" core ").append(f[7]).append('\n'); }
            }
        }
        System.out.print(show);
        System.out.printf("%d code points; whitespace vs Java %d differ (vs the old hand list %d); digits %d differ (%d newer than the JDK); currency %d differ (%d newer); lowercase %d differ (%d newer than the JDK); no-space letters %d; %d strings (final sigma, I-dot), %d differ%n",
            n, wsBad, wsOld, dBad, dNew, cBad, cNew, lBad, lNew, nospace, strings, sBad);
        boolean ok = wsBad == 0 && wsOld == 0 && dBad == 0 && cBad == 0 && lBad == 0 && sBad == 0;
        System.out.println(ok ? "IDENTICAL wherever the JDK's Unicode knows the code point" : "FAILED");
        System.exit(ok ? 0 : 1);
    }
}
