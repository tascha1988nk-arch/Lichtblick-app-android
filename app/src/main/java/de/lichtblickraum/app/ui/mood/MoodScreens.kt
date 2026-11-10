package de.lichtblickraum.app.ui.mood

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.LichtblickTopBar
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.PrimaryButton
import de.lichtblickraum.app.ui.components.QuietButton
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.components.SecondaryButton
import de.lichtblickraum.app.ui.components.SectionTitle
import de.lichtblickraum.app.ui.exercises.NoteField
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.journal.JournalEntry
import de.lichtblickraum.core.mood.MoodContent
import de.lichtblickraum.core.mood.MoodInsights
import de.lichtblickraum.core.mood.MoodLevel
import de.lichtblickraum.core.profile.Focus
import java.time.format.DateTimeFormatter
import java.util.Locale

fun dialSupport(context: Context) {
    try {
        context.startActivity(
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:${MoodContent.SUPPORT_PHONE_DIAL}")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
        )
    } catch (e: ActivityNotFoundException) {
        // Kein Telefon verfügbar – die Nummer steht ja im Text.
    }
}

/** Freundlicher Hilfe-Hinweis nach mehreren schweren Tagen. */
@Composable
fun SupportCard(onHide: () -> Unit) {
    val context = LocalContext.current
    LichtblickCard(highlighted = true) {
        Text(MoodContent.SUPPORT_TITLE, style = MaterialTheme.typography.titleLarge)
        BodyText(MoodContent.SUPPORT_TEXT)
        PrimaryButton("TelefonSeelsorge anrufen: ${MoodContent.SUPPORT_PHONE}", { dialSupport(context) })
        QuietButton("Danke, jetzt nicht", onHide)
    }
}

/** Fünf Wetter-Stufen zum Antippen. */
@Composable
fun MoodLevelPicker(selected: Int?, onSelect: (MoodLevel) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        MoodLevel.entries.forEach { level ->
            val isSel = selected == level.value
            Card(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 72.dp)
                    .selectable(selected = isSel, role = Role.RadioButton, onClick = { onSelect(level) })
                    .semantics { contentDescription = level.label },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSel) LichtblickColors.GoldSoft else LichtblickColors.CreamLight,
                    contentColor = LichtblickColors.Text,
                ),
                border = BorderStroke(if (isSel) 2.dp else 1.dp, if (isSel) LichtblickColors.GoldDark else LichtblickColors.GoldSoft),
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(level.symbol, style = MaterialTheme.typography.headlineSmall)
                    Text(level.label, style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center, maxLines = 2)
                }
            }
        }
    }
}

/** Sanfter Verlauf: ein Balken je Tag mit Eintrag – Tage ohne Eintrag erscheinen gar nicht. */
@Composable
fun MoodChart(entries: List<JournalEntry>, maxDays: Int = 14) {
    val days = MoodInsights.dailyAverages(entries).entries.toList().takeLast(maxDays)
    if (days.isEmpty()) {
        BodyText("Sobald du ein paar Mal eingetragen hast, siehst du hier deinen Verlauf.", color = LichtblickColors.TextMuted)
        return
    }
    val fmt = DateTimeFormatter.ofPattern("d.M.", Locale.GERMAN)
    val bar = LichtblickColors.Gold
    val track = LichtblickColors.GoldSoft
    Column(Modifier.semantics { contentDescription = "Verlauf deines Gefühlsbarometers" }) {
        Canvas(Modifier.fillMaxWidth().height(120.dp)) {
            val slot = size.width / days.size
            val w = slot * 0.6f
            days.forEachIndexed { i, (_, avg) ->
                val x = i * slot + (slot - w) / 2
                drawRoundRect(track, Offset(x, 0f), Size(w, size.height), CornerRadius(w / 2))
                val h = size.height * (avg.toFloat() / 5f)
                drawRoundRect(bar, Offset(x, size.height - h), Size(w, h), CornerRadius(w / 2))
            }
        }
        Row(Modifier.fillMaxWidth()) {
            days.forEach { (d, _) ->
                Text(d.format(fmt), style = MaterialTheme.typography.labelMedium, color = LichtblickColors.TextMuted,
                    modifier = Modifier.weight(1f), textAlign = TextAlign.Center, maxLines = 1)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodScreen(container: AppContainer, initialLevel: Int?, onBack: () -> Unit, onOpenDiary: () -> Unit) {
    val entries by container.journal.entries.collectAsStateWithLifecycle()
    val hiddenUntil by container.settings.supportHiddenUntil.collectAsStateWithLifecycle()
    var level by remember { mutableStateOf(initialLevel) }
    val tags = remember { mutableStateMapOf<String, Boolean>() }
    var note by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf(false) }
    val today = container.today()
    val showSupport = MoodInsights.needsSupportHint(entries, today) && (hiddenUntil == null || today.isAfter(hiddenUntil))

    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = "Gefühlsbarometer", onBack = onBack) },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                if (saved) {
                    Heading("Danke, dass du hingespürt hast")
                    BodyText("Dein Eintrag ist gespeichert. Du kannst jederzeit wiederkommen – ganz ohne Regelmäßigkeit.")
                    if (level != null && level!! <= 2) {
                        NoteBox(text = "Wenn es gerade schwer ist: Vielleicht tut dir eine kurze Übung aus „Gefühle & Stress“ gut.")
                    }
                    SecondaryButton("Etwas ins Gefühlstagebuch schreiben", onOpenDiary)
                    PrimaryButton("Fertig", onBack)
                } else {
                    Heading("Wie geht es dir gerade?")
                    BodyText("Es gibt kein Richtig oder Falsch. Wähle, was im Moment am ehesten passt.", color = LichtblickColors.TextMuted)
                    MoodLevelPicker(level) { level = it.value }
                    if (level != null) {
                        SectionTitle("Welche Gefühle sind da? (freiwillig)")
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            MoodContent.feelingWords.forEach { w ->
                                FilterChip(
                                    selected = tags[w] == true,
                                    onClick = { tags[w] = tags[w] != true },
                                    label = { Text(w) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = LichtblickColors.GoldSoft,
                                        selectedLabelColor = LichtblickColors.Text,
                                    ),
                                )
                            }
                        }
                        NoteField(value = note, onValueChange = { note = it }, label = "Möchtest du etwas dazu notieren? (freiwillig)")
                        PrimaryButton("Speichern", {
                            container.addMood(level!!, MoodContent.feelingWords.filter { tags[it] == true }, note)
                            saved = true
                        })
                    }
                }
                if (showSupport) SupportCard(onHide = { container.settings.hideSupportUntil(today.plusDays(7)) })
                SectionTitle("Dein Verlauf")
                LichtblickCard { MoodChart(entries) }
                SecondaryButton("Gefühlstagebuch", onOpenDiary)
            }
        }
    }
}

@Composable
fun DiaryScreen(container: AppContainer, onBack: () -> Unit) {
    val answers = remember { mutableStateMapOf<Int, String>() }
    var saved by remember { mutableStateOf(false) }
    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = "Gefühlstagebuch", onBack = onBack) },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                if (saved) {
                    Heading("Festgehalten")
                    BodyText("Dein Eintrag steht jetzt in deiner Rückschau. Danke, dass du dir Zeit für dich genommen hast.")
                    PrimaryButton("Fertig", onBack)
                } else {
                    BodyText(
                        "Schreib auf, was dich bewegt – so viel oder so wenig du magst. Alle Fragen sind " +
                            "freiwillig; du kannst auch nur eine beantworten.",
                    )
                    MoodContent.diaryPrompts.forEachIndexed { i, prompt ->
                        NoteField(value = answers[i].orEmpty(), onValueChange = { answers[i] = it }, label = prompt)
                    }
                    val text = MoodContent.diaryText(answers.toMap())
                    PrimaryButton("Speichern", {
                        container.journal.add(EntryKind.DIARY, "tagebuch", "Gefühlstagebuch", container.today(), text)
                        saved = true
                    }, enabled = text != null)
                }
            }
        }
    }
}

/** Gelegentlicher, freiwilliger Rückblick – keine Prüfung, kein Ergebnis. */
@Composable
fun ReflectionScreen(container: AppContainer, onBack: () -> Unit) {
    val entries by container.journal.entries.collectAsStateWithLifecycle()
    val profile by container.profileStore.profile.collectAsStateWithLifecycle()
    var changed by remember { mutableStateOf("") }
    var takeAway by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf(false) }
    val since = container.today().minusDays(30)
    val recent = entries.filter { !it.date.isBefore(since) && it.kind != EntryKind.MOOD }

    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = "Rückblick", onBack = onBack) },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                if (saved) {
                    Heading("Danke für deinen Rückblick")
                    BodyText("Er steht jetzt in deiner Rückschau. In etwa einem Monat lade ich dich wieder ein – wenn du magst.")
                    PrimaryButton("Fertig", onBack)
                } else {
                BodyText("Nimm dir einen Moment und schau auf die letzten Wochen – ohne zu bewerten.")
                profile.wish?.let {
                    LichtblickCard(highlighted = true) {
                        Text("Dein Kompass", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
                        Text("„$it“", style = MaterialTheme.typography.titleLarge)
                    }
                }
                if (profile.focus.isNotEmpty()) {
                    BodyText("Deine Themen: " + profile.focus.joinToString(" · ") { it.title }, color = LichtblickColors.TextMuted)
                }
                SectionTitle("Dein Gefühlsbarometer")
                LichtblickCard { MoodChart(entries, maxDays = 30) }
                if (recent.isNotEmpty()) {
                    SectionTitle("Was du festgehalten hast")
                    recent.take(8).forEach { e ->
                        LichtblickCard {
                            Text(e.kind.label, style = MaterialTheme.typography.labelMedium, color = LichtblickColors.GoldDark)
                            Text(e.title, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
                SectionTitle("Deine Gedanken (freiwillig)")
                NoteField(value = changed, onValueChange = { changed = it }, label = "Was hat sich verändert – auch im Kleinen?")
                NoteField(value = takeAway, onValueChange = { takeAway = it }, label = "Was möchtest du mitnehmen?")
                val text = listOf("Was hat sich verändert?" to changed, "Was möchte ich mitnehmen?" to takeAway)
                    .filter { it.second.isNotBlank() }
                    .joinToString("\n\n") { "${it.first}\n${it.second.trim()}" }
                    .ifEmpty { null }
                PrimaryButton("Rückblick speichern", {
                    container.journal.add(EntryKind.REFLECTION, "rueckblick", "Rückblick", container.today(), text)
                    container.postponeReview()
                    saved = true
                })
                QuietButton("Ohne Speichern schließen", { container.postponeReview(); onBack() })
                }
            }
        }
    }
}
