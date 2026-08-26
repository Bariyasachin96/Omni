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
import androidx.compose.ui.semantics.clearAndSetSemantics
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
            SectionHeader("Languages")
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
                                        text = { Text("Delete configuration", modifier = Modifier.clearAndSetSemantics { }) },
                                        onClick = { menuOpen = false; onDeleteConfiguration(index) },
                                        modifier = Modifier.semantics { contentDescription = "Delete configuration" }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Disable language", modifier = Modifier.clearAndSetSemantics { }) },
                                        onClick = { menuOpen = false; onDisable(index) },
                                        modifier = Modifier.semantics { contentDescription = "Disable language" }
                                    )
                                }
                            }
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .clickable(onClickLabel = "Set up this voice") { onLanguage(index) }
                            // One node, so a screen reader says the language and
                            // its status together in a single swipe.
                            .semantics { contentDescription = labels[index] + ", " + status }
                    )
                }
            }
        }
    }
}
