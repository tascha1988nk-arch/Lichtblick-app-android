package de.lichtblickraum.app.ui.lichtblicke

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.LichtblickTopBar
import de.lichtblickraum.app.ui.components.QuietButton
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.components.SectionTitle
import de.lichtblickraum.app.ui.components.TouchHeight
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.journal.JournalEntry
import de.lichtblickraum.core.mood.MoodLevel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dateFormat = DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy", Locale.GERMAN)
fun formatDate(date: LocalDate): String = date.format(dateFormat)

private sealed interface Confirm {
    data object EnableSaving : Confirm
    data object DisableSaving : Confirm
    data object DeleteAll : Confirm
    data class DeleteOne(val entry: JournalEntry) : Confirm
}

@Composable
fun ReviewScreen(container: AppContainer, onBack: () -> Unit) {
    val entries by container.journal.entries.collectAsStateWithLifecycle()
    val saving by container.journal.isPersistent.collectAsStateWithLifecycle()
    var confirm by remember { mutableStateOf<Confirm?>(null) }

    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = "Rückschau", onBack = onBack) },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                BodyText(
                    "Hier findest du, was du festgehalten hast – erledigte Impulse, Übungen und deine " +
                        "Notizen. Es geht nicht um Vollständigkeit. Jeder Eintrag ist einfach ein Moment für dich.",
                )

                LichtblickCard(highlighted = true) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = TouchHeight)
                            .toggleable(
                                value = saving,
                                role = Role.Switch,
                                onValueChange = { on -> confirm = if (on) Confirm.EnableSaving else Confirm.DisableSaving },
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("Einträge auf diesem Gerät speichern", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                        Spacer(Modifier.width(12.dp))
                        Switch(
                            checked = saving,
                            onCheckedChange = null,
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = LichtblickColors.GoldDark,
                                uncheckedBorderColor = LichtblickColors.Gold,
                                uncheckedThumbColor = LichtblickColors.Gold,
                            ),
                        )
                    }
                    Text(
                        if (saving) {
                            "Eingeschaltet: Deine Einträge liegen nur auf diesem Gerät. Sie werden nicht an " +
                                "Lichtblick oder andere übermittelt und nicht in Sicherungen übernommen. " +
                                "Du kannst das Speichern hier jederzeit ausschalten."
                        } else {
                            "Ausgeschaltet: Deine Einträge bleiben nur, solange die App geöffnet ist. " +
                                "Du kannst das Speichern hier jederzeit wieder einschalten."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = LichtblickColors.TextMuted,
                    )
                }

                if (entries.isEmpty()) {
                    BodyText("Hier ist noch nichts – und das ist völlig in Ordnung.", color = LichtblickColors.TextMuted)
                } else {
                    entries.groupBy { it.date }.forEach { (date, dayEntries) ->
                        SectionTitle(formatDate(date))
                        dayEntries.forEach { entry ->
                            EntryCard(entry, onDelete = { confirm = Confirm.DeleteOne(entry) })
                        }
                    }
                    QuietButton("Alle Einträge löschen", { confirm = Confirm.DeleteAll })
                }
            }
        }
    }

    when (val c = confirm) {
        null -> Unit
        Confirm.EnableSaving -> ConfirmDialog(
            title = "Einträge speichern?",
            text = "Deine erledigten Impulse, Übungen und Notizen werden dann im geschützten Speicher " +
                "dieser App auf deinem Gerät abgelegt. Sie werden nicht übertragen – weder an " +
                "Lichtblick noch an andere – und nicht in Sicherungen übernommen. Du kannst sie jederzeit " +
                "einzeln oder vollständig löschen. Beim Deinstallieren der App werden sie gelöscht.",
            confirmLabel = "Ja, speichern",
            onConfirm = { container.setSaveEntries(true) },
            onDismiss = { confirm = null },
        )
        Confirm.DisableSaving -> ConfirmDialog(
            title = "Speichern ausschalten?",
            text = "Die gespeicherten Einträge werden vom Gerät gelöscht. Was du jetzt siehst, bleibt " +
                "nur noch, solange die App geöffnet ist.",
            confirmLabel = "Ausschalten",
            onConfirm = { container.setSaveEntries(false) },
            onDismiss = { confirm = null },
        )
        Confirm.DeleteAll -> ConfirmDialog(
            title = "Alle Einträge löschen?",
            text = "Alle Einträge werden vollständig gelöscht – auch vom Gerät. Das lässt sich nicht rückgängig machen.",
            confirmLabel = "Alle löschen",
            onConfirm = { container.journal.deleteAll() },
            onDismiss = { confirm = null },
        )
        is Confirm.DeleteOne -> ConfirmDialog(
            title = "Eintrag löschen?",
            text = "„${c.entry.title}“ wird vollständig gelöscht.",
            confirmLabel = "Löschen",
            onConfirm = { container.journal.delete(c.entry.id) },
            onDismiss = { confirm = null },
        )
    }
}

@Composable
private fun EntryCard(entry: JournalEntry, onDelete: () -> Unit) {
    LichtblickCard {
        Row(verticalAlignment = Alignment.Top) {
            Column(Modifier.weight(1f)) {
                Text(entry.kind.label, style = MaterialTheme.typography.labelMedium, color = LichtblickColors.GoldDark)
                Text(entry.title, style = MaterialTheme.typography.titleMedium)
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(TouchHeight)) {
                Icon(Icons.Filled.Delete, contentDescription = "Eintrag „${entry.title}“ löschen", tint = LichtblickColors.GoldDark)
            }
        }
        MoodLevel.of(entry.level)?.let { BodyText("${it.symbol} ${it.label}") }
        if (entry.tags.isNotEmpty()) {
            Text(entry.tags.joinToString(" · "), style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
        }
        if (entry.note != null) BodyText(entry.note!!)
    }
}

@Composable
private fun ConfirmDialog(
    title: String,
    text: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = { TextButton(onClick = { onConfirm(); onDismiss() }) { Text(confirmLabel) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Abbrechen") } },
    )
}
