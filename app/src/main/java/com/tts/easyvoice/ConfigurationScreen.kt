package com.tts.easyvoice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
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
                    val status = if (enginePkg.isEmpty()) "Not set up" else EngineFinder.friendlyName(enginePkg)
                    // Replaces the long press. A hidden gesture is a poor way to
                    // reach anything destructive; a named button is discoverable
                    // by everyone and gets its own focus stop.
                    var menuOpen by remember { mutableStateOf(false) }
                    ListItem(
                        headlineContent = { Text(labels[index], modifier = Modifier.clearAndSetSemantics { }) },
                        supportingContent = { Text(status, modifier = Modifier.clearAndSetSemantics { }) },
                        trailingContent = {
                            Box {
                                IconButton(
                                    onClick = { menuOpen = true },
                                    modifier = Modifier.semantics {
                                        contentDescription = "More actions for " + labels[index]
                                    }
                                ) {
                                    Icon(painterResource(R.drawable.ic_more_vert), contentDescription = null)
                                }
                                DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                                    DropdownMenuItem(
                                        text = { Text("Delete configuration") },
                                        onClick = { menuOpen = false; onDeleteConfiguration(index) },
                                        modifier = Modifier.evControl(
                                            "Delete configuration",
                                            listItem = CollectionItemInfo(0, 1, 0, 1),
                                            action = { menuOpen = false; onDeleteConfiguration(index) }
                                        )
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Disable language") },
                                        onClick = { menuOpen = false; onDisable(index) },
                                        modifier = Modifier.evControl(
                                            "Disable language",
                                            listItem = CollectionItemInfo(1, 1, 0, 1),
                                            action = { menuOpen = false; onDisable(index) }
                                        )
                                    )
                                }
                            }
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        // THIS ROW IS A LIST ITEM AND IS ANNOUNCED AS ONE, not as a
                        // button. `Modifier.clickable(onClickLabel = ...)` leaves `role`
                        // null on purpose: a Role here would make Compose emit a fake
                        // role child and the row would be called a button again.
                        //
                        // The LazyColumn publishes its own `CollectionInfo`
                        // (LazyLayoutSemanticState: `CollectionInfo(rowCount =
                        // totalItemsCount, columnCount = 1)`), and Compose sets
                        // `collectionItemInfo` on NO lazy item by itself, so the per-row
                        // half is declared here. The delegate assigns it with NO gate, so
                        // it reaches the FOCUSED node and a reader can say the row's
                        // position in the list.
                        //
                        // THE THREE-DOT BUTTON IN trailingContent STAYS REACHABLE, and
                        // this was checked at the source rather than assumed, because I
                        // once claimed the opposite and was wrong. From the delegate:
                        //
                        //   private fun isScreenReaderFocusable(node, ...): Boolean {
                        //       if (node.isHidden || isInMergingHiddenSubtree) return false
                        //       // If the node explicitly merges its descendants, we map it
                        //       // directly to the merging algorithm on the a11y side.
                        //       if (node.unmergedConfig.isMergingSemanticsOfDescendants)
                        //           return true
                        //
                        // An `IconButton` merges its own descendants, so it is its own
                        // focus stop no matter what its parent row carries, and
                        // `collectionItemInfo` is not consulted anywhere in that function.
                        // `setCollectionItemInfo` likewise only writes
                        // `info.setCollectionItemInfo(...)` on this one node and touches
                        // nothing about children. Build 834's accessibility job proves it
                        // on a real emulator: `configurationRowMenuOpen` finds and clicks
                        // "More actions for English (eng)" in the MERGED tree, and that
                        // run was green.
                        //
                        // WHAT DID break it, and the rule that follows: `evControl`
                        // (= `clearAndSetSemantics`) on this row. Clearing a node drops
                        // its WHOLE SUBTREE from the accessibility tree, so the IconButton
                        // vanished and took Delete configuration and Disable language with
                        // it. Builds 832 and 833 failed that same test on exactly this.
                        // NEVER put `evControl` on a node that contains an interactive
                        // child; a plain `semantics {}` block adds without clearing, which
                        // is what this row uses.
                        modifier = Modifier
                            .clickable(onClickLabel = "Set up this voice") { onLanguage(index) }
                            .semantics {
                                contentDescription = labels[index] + ", " + status
                                collectionItemInfo = CollectionItemInfo(index, 1, 0, 1)
                            }
                    )
                }
            }
        }
    }
}
