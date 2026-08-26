import java.util.*;

public class Main {
    public static void main(String[] argv) throws Exception {
        java.io.PrintStream ps = new java.io.PrintStream(
            new java.io.BufferedOutputStream(new java.io.FileOutputStream(java.io.FileDescriptor.out), 1 << 20), false, "UTF-8");
        // argv[0] = comma separated enabled set ("" = empty)
        Set<String> enabled = new HashSet<String>();
        if (argv.length > 0 && !argv[0].isEmpty())
            enabled.addAll(Arrays.asList(argv[0].split(",")));
        for (int cp = 0; cp <= 0x10FFFF; cp++) {
            ScriptFam.a fam = ScriptFam.e(cp, enabled.isEmpty() ? null : enabled);
            String out = "";
            if (fam != null) {
                String primary = fam.b();
                if (primary != null && enabled.contains(primary)) {
                    out = primary;
                } else {
                    Iterator it = fam.a().iterator();
                    while (it.hasNext()) {
                        String m = (String) it.next();
                        if (m != null && enabled.contains(m)) { out = m; break; }
                    }
                }
            }
            ps.println(Integer.toHexString(cp) + " " + out);
        }
        ps.flush();
    }
}
