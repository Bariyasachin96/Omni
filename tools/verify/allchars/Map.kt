// The Kotlin half of tools/verify/allchars: the REAL IsoCodes (and the generated
// UnicodeTables) turn each code the detector answered into the iso3 a voice is
// looked up by, as languageForDetectedRun and the auto branch do
// (IsoCodes.toIso3, then iso3OfCode). Appended to a copy of those two files.
//   prep <langs.tsv>  ->  script, iso2, iso3 of each living script's language
//   map <codes.txt>   ->  code<TAB>iso3 ("-" when IsoCodes knows no such language)
fun main(args: Array<String>) {
    if (args[0] == "prep") {
        val langs = java.io.File(args[1]).readLines().map { it.split('\t') }.filter { it.size >= 3 }
        for (f in langs) {
            if (f[1].isEmpty() || (f[2] != "RECOMMENDED" && f[2] != "LIMITED_USE")) continue
            val iso3 = iso3OfCode(f[1]); println(f[0] + "\t" + (IsoCodes.toIso2(iso3) ?: f[1]) + "\t" + iso3)
        }
        return
    }
    for (code in java.io.File(args[1]).readLines()) {
        if (code.isEmpty()) continue
        println(code + "\t" + (IsoCodes.toIso3(code)?.let { iso3OfCode(it) } ?: "-"))
    }
}
