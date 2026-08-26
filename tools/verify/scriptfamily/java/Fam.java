import java.util.*;

public class Fam {
    public static void main(String[] argv) throws Exception {
        java.io.PrintStream ps = new java.io.PrintStream(
            new java.io.BufferedOutputStream(new java.io.FileOutputStream(java.io.FileDescriptor.out), 1 << 20), false, "UTF-8");
        for (int cp = 0; cp <= 0x10FFFF; cp++) {
            ScriptFam.a fam = ScriptFam.e(cp, null);   // set == null -> a.d(cp)
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(cp)).append(' ');
            if (fam == null) { sb.append("null"); }
            else {
                sb.append(fam.b()).append('|');
                boolean first = true;
                for (Object m : fam.a()) { if (!first) sb.append(','); sb.append((String) m); first = false; }
            }
            ps.println(sb.toString());
        }
        ps.flush();
    }
}
