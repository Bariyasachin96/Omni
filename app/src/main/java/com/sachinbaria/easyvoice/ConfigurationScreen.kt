package com.sachinbaria.easyvoice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.CollectionItemInfo
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
// A tab in MainActivity's pager, not its own screen any more. The Scaffold and
// the "Add language" button it used to carry now live in MainScreen, which owns
// the only Scaffold: Material puts a floating action button in the Scaffold, and
// nesting a second one inside a pager page would fight the first for placement.
@Composable
fun ConfigurationScreen(labels: List<String>, engines: List<String>, onLanguage: (Int) -> Unit, onDeleteConfiguration: (Int) -> Unit, onDisable: (Int) -> Unit) {
    val context = LocalContext.current
    ResponsiveContent {
        // The heading and the instruction sit ABOVE the list, not inside it.
        // A LazyColumn publishes collectionInfo covering everything it holds,
        // so as `item {}` entries they counted as members of the language
        // collection and shifted every row's position.
        Column(modifier = Modifier.fillMaxSize()) {
            // NOT "Languages": that is the heading of the screen the Add
            // language button opens, so a screen reader announced the same word
            // on both and there was no way to tell them apart by heading alone.
            // This page is the one described by the line under it -- pick a
            // language, set up its voice.
            SectionHeader("Set up voices")
            Text(
                text = "Choose a language to set up its voice.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            if (labels.isEmpty()) {
                Text(
                    text = "No languages are set up yet. Use Add language to pick some.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            // contentPadding keeps the last row clear of the floating button.
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                items(labels.size) { index ->
                    // Without this the list gave no way to tell which languages
                    // already have a voice chosen and which are still untouched.
                    // Naming the engine answers both at once: whether it is set
                    // up, and which engine is doing the speaking.
                    val enginePkg = engines.getOrElse(index) { "" }
                    val status = if (enginePkg.isEmpty()) "Not set up" else EngineFinder.engineLabel(context, enginePkg)
                    // Replaces the long press. A hidden gesture is a poor way to
                    // reach anything destructive; a named button is discoverable
                    // by everyone and gets its own focus stop.
                    var menuOpen by remember { mutableStateOf(false) }
                    // TWO SIBLINGS, NOT A ROW WITH A BUTTON INSIDE IT (owner,
                    // 2026-09-30: every reader, fast swipes read in full). The
                    // language row and its three-dot button used to be parent and
                    // child, so the row had a semantics child and its name reached
                    // a reader only through a fake child node. Side by side, the
                    // row can drop its own children (the two Texts) with a clearing
                    // node at the TAIL of its chain -- `clickable`, head-ward of
                    // it, still writes its own click on top -- and the IconButton
                    // stays its own focus stop beside it, as before.
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ListItem(
                            headlineContent = { Text(labels[index]) },
                            supportingContent = { Text(status) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            // A LIST ITEM, not a button: `clickable(onClickLabel)`
                            // leaves `role` null on purpose. The LazyColumn
                            // publishes its own CollectionInfo, and Compose sets
                            // `collectionItemInfo` on no lazy item by itself, so
                            // the row's position is declared here.
                            //
                            // NEVER put the clearing node on anything that CONTAINS
                            // an interactive child: clearing drops the whole
                            // subtree, which is how builds 832 and 833 lost the
                            // three-dot menu. That is why the button is a sibling.
                            modifier = Modifier
                                .weight(1f)
                                .clickable(onClickLabel = "Set up this voice") { onLanguage(index) }
                                .clearAndSetSemantics {
                                    contentDescription = labels[index] + ", " + status
                                    collectionItemInfo = CollectionItemInfo(index, 1, 0, 1)
                                }
                        )
                        Box(modifier = Modifier.padding(end = 8.dp)) {
                            IconButton(
                                onClick = { menuOpen = true },
                                modifier = Modifier.semantics {
                                    contentDescription = "More actions for " + labels[index]
                                }
                            ) {
                                Icon(painterResource(R.drawable.ic_more_vert), contentDescription = null)
                            }
                            // NO `listItem` ON THESE TWO (owner, 2026-09-09).
                            // They used to carry CollectionItemInfo(0/1) with
                            // NOTHING publishing the matching CollectionInfo --
                            // this menu's content is a bare Column, unlike the
                            // language dropdown in VoiceScreen, which declares
                            // both. The delegate writes an item index for a
                            // collection whose size it was never told
                            // (CollectionInfo.android.kt sets item info from the
                            // developer property with no cross-check), so a
                            // reader was given a position out of nowhere on a
                            // two-line action menu. Removing the half-declaration
                            // is the fix; do not "complete" it by adding a
                            // collectionInfo Column here -- the owner asked for
                            // less position noise, not more.
                            DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                                // The click is the item's own; the name is the one
                                // thing added (INVARIANTS #7).
                                DropdownMenuItem(
                                    text = { SilentText("Delete configuration") },
                                    onClick = { menuOpen = false; onDeleteConfiguration(index) },
                                    modifier = Modifier.semantics { contentDescription = "Delete configuration" }
                                )
                                DropdownMenuItem(
                                    text = { SilentText("Disable language") },
                                    onClick = { menuOpen = false; onDisable(index) },
                                    modifier = Modifier.semantics { contentDescription = "Disable language" }
                                )
                        }
                    }
                    }
                }
            }
        }
    }
}
