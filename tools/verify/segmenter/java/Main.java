import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    static String esc(String s) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\n') b.append("\\n");
            else if (c < 0x20 || c == 0x7f) b.append(String.format("\\u%04x", (int) c));
            else b.append(c);
        }
        return b.toString();
    }
    public static void main(String[] argv) throws Exception {
        java.io.BufferedReader in = new java.io.BufferedReader(
            new java.io.InputStreamReader(System.in, "UTF-8"));
        java.io.PrintStream ps = new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.out), true, "UTF-8");
        String line;
        while ((line = in.readLine()) != null) {
            if (line.isEmpty()) continue;
            // TAB separated: mode  numMode  puncMode  emojiMode  inFlow  smart  group  dualLang  mixNonLat  deviceIso3  hints  text
            String[] f = line.split("\t", -1);
            AutoTtsService.T  = Integer.parseInt(f[0]);
            int numMode       = Integer.parseInt(f[1]);
            int puncMode      = Integer.parseInt(f[2]);
            int emojiMode     = Integer.parseInt(f[3]);
            AutoTtsService.d0 = f[4].equals("1");
            AutoTtsService.e0 = f[5].equals("1");
            AutoTtsService.f0 = Integer.parseInt(f[6]);
            AutoTtsService.I  = f[7];
            AutoTtsService.Q  = f[8];
            n.deviceIso3      = f[9];
            HashSet<String> hints = new HashSet<String>();
            if (!f[10].isEmpty()) hints.addAll(Arrays.asList(f[10].split(",")));
            n.f = hints;
            d0.j = null;   // reset the cached keyword set
            String text = f[11].replace("\\t", "\t");
            ArrayList out = d0.t(text, numMode, puncMode, emojiMode, 0, 0);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < out.size(); i++) {
                e0 seg = (e0) out.get(i);
                if (i > 0) sb.append(" | ");
                sb.append(seg.a()).append(":'").append(esc(seg.c())).append("'");
            }
            ps.println(sb.toString());
        }
    }
}
