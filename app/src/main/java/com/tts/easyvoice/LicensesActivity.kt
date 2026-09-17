package com.tts.easyvoice
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// THE OPEN SOURCE LICENCES, ON THEIR OWN SCREEN (owner, 2026-09-17: "a
// dedicated 'View Licenses' button that displays all open-source license
// information"). Every word here was lifted out of AboutScreen unchanged -- the
// heading, the proprietary-software sentence, the CLD2 entry, the AOSP entry and
// the two verbatim Apache paragraphs -- so the notice this app carries is byte
// for byte what it carried before, in a place a reader can leave.
//
// WHY IT IS AN ACTIVITY and not an expanding section on About: a screen reader
// speaks a window's title when the window appears, so "Open source licenses" --
// the android:label in the manifest -- is announced on entry, and the system
// back gesture leaves. An in-place disclosure gets neither. That is the same
// reasoning that made Languages, Mode settings and Voice setup screens.
//
// THE TEXT IS READ OFF CLD2's OWN REPOSITORY and was checked there rather than
// remembered -- github.com/CLD2Owners/cld2, which is what build.yml clones:
//   LICENSE        the Apache 2.0 text, byte-identical to our clone's copy
//   README.md      "Compact Language Detector 2", Dick Sites (dsites@google.com)
//   every .cc/.h   "Copyright 2013 Google Inc. All Rights Reserved." plus the
//                  Apache notice; 21 of the 24 files compiled say 2013 and three
//                  say 2014, which is why the notice reads "2013, 2014"
//   NOTICE         DOES NOT EXIST (HTTP 404), so Apache 2.0 section 4(d) asks us
//                  to reproduce nothing extra -- the copyright line plus a
//                  pointer to the License is the whole obligation
class LicensesActivity : EvActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EasyVoiceTheme { LicensesScreen() } }
    }
}

@Composable
fun LicensesScreen() {
    ResponsiveContent {
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            // "Open source licenses", not "License". On a PAID listing the
            // heading itself is the first thing that has to draw the line: a
            // section called "License" above an Apache 2.0 notice invites the
            // reader to think the app carries that licence, while this name says
            // plainly that the licences below belong to the open source PARTS.
            // It is also what a store listing's own section is normally called.
            SectionHeader("Open source licenses")
            // Everything in this block is taken from CLD2's OWN repository --
            // its README, its LICENSE and the header its source files carry --
            // not from any second-hand summary. See the comment above CLD2_URL.
            // THIS SENTENCE IS COMMERCIALLY LOAD-BEARING -- do not shorten it back.
            // It used to read "Easy Voice is built on open source work, and all of
            // it is used under the Apache License, Version 2.0", and the owner
            // caught that while preparing a PAID Play Store listing. Three things
            // were wrong with it, and the replacement fixes all three:
            //   - "all of it" reads back to "Easy Voice", so the line could be
            //     understood as putting the WHOLE APP under Apache 2.0, which is
            //     false and would undercut a paid app outright;
            //   - "built on open source work" invites the reader to assume the app
            //     itself is open source, and therefore free somewhere else;
            //   - nothing in the section said who owns Easy Voice, so there was no
            //     line between our code and the components we merely include.
            // Selling it is not the problem: Apache 2.0 section 2 grants a
            // "perpetual, worldwide, non-exclusive, no-charge, royalty-free,
            // irrevocable" licence to "sublicense, and distribute", and section 4
            // sets only four conditions, all of which this screen already meets.
            SettingDescription("Easy Voice itself is proprietary software. The third-party components below are open source, and each one is used under the Apache License, Version 2.0.")
            // THE COUNT WAS STALE AND IS NOW OURS RATHER THAN THE README'S
            // (owner, 2026-09-10: "humne CLD2 full kar diya hai to usko sahi
            // karna hai"). This line used to say 83, quoting CLD2's README --
            // and that sentence describes the DEFAULT build, whose quadgram
            // table is 256k. Since 2026-09-08 Easy Voice compiles
            // compile_full.sh's table set instead, and the proof of which
            // evaluation that matches is a number rather than a claim:
            // kQuad0122Size is 262,144 buckets of four entries = 1,048,576, and
            // CLD2's own docs/evaluate_cld2_large_20140122.txt is headed
            // "Evaluate CLD2 20140122 1024k" while the small one says 256k. That
            // large file scores 170 distinct language codes against the small
            // one's 78, so "over 170" is read off CLD2's own evaluation of the
            // exact table this app links.
            //
            // It is deliberately "over 170" and not an exact figure: the file
            // counts languages the QUADGRAM scorer was evaluated on, while the
            // script-defined ones -- Gujarati, Tamil, Telugu, Kannada and the
            // rest -- are decided by their Unicode script and are detected
            // whichever table is built, so an exact number here would be
            // answering a different question from the one a reader is asking.
            SettingDescription("Compact Language Detector 2 (CLD2), written by Dick Sites at Google, is what reads the language of your text. Easy Voice compiles it from the sources at github.com/CLD2Owners/cld2 with CLD2's full detection tables, which cover over 170 languages. Copyright 2013, 2014 Google Inc. All Rights Reserved.")
            // This used to be one run-on sentence stranded BELOW the buttons,
            // which left the page ending on a footnote instead of on its
            // actions. It is a licence entry like the one above it, so it reads
            // like one and sits beside it.
            SettingDescription("The Android Open Source Project and the Jetpack libraries are what the app itself is written with. Copyright The Android Open Source Project.")
            // The two paragraphs below are the Apache 2.0 notice verbatim, as it
            // appears at the top of every CLD2 file Easy Voice compiles and in
            // the appendix of the repository's LICENSE. Do not paraphrase them.
            SettingDescription("Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0")
            SettingDescription("Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.")
        }
    }
}
