// Writes the app's REAL compile classpath -- what Gradle and AGP resolve, AARs
// already unpacked to their classes jars -- to the file named by -PevOut.
// tools/bootstrap.sh uses it for the minSdk check. It replaced tools/fetch-deps.py
// (2026-09-23), a hand-written resolver that walked every version it met rather
// than the ones Gradle selects, and so put jars on the classpath (transition,
// dynamicanimation, legacy-support) that the real build does not have.
// Local only: CI never loads this file.
allprojects {
    if (path == ":app") {
        afterEvaluate {
            tasks.register("evClasspath") {
                val cfg = configurations.getByName("debugCompileClasspath")
                val files = cfg.incoming.artifactView {
                    attributes {
                        attribute(Attribute.of("artifactType", String::class.java), "android-classes-jar")
                    }
                }.files
                val out = file(project.property("evOut") as String)
                inputs.files(files)
                doLast { out.writeText(files.files.joinToString("\n") { it.absolutePath } + "\n") }
            }
        }
    }
}
