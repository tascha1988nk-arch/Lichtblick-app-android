package de.lichtblickraum.app.ui.selftest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.BookingButton
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.LichtblickTopBar
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.PrimaryButton
import de.lichtblickraum.app.ui.components.QuietButton
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.components.SecondaryButton
import de.lichtblickraum.app.ui.components.TouchHeight
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.about.LegalContent
import de.lichtblickraum.core.selftest.SelfTestCatalog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.ui.components.ContentText
import de.lichtblickraum.app.ui.lichtblicke.formatDate
import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.selftest.Questionnaire
import de.lichtblickraum.core.selftest.ResultBand
import de.lichtblickraum.core.selftest.SelfTestResults
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SelfTestScreen(questionnaireId: String, container: AppContainer, onExit: () -> Unit) {
    val questionnaire = SelfTestCatalog.byId(questionnaireId)?.takeIf { it.isAvailable }
    if (questionnaire == null) {
        ScreenColumn {
            BodyText("Dieser Test ist noch nicht verfügbar.")
            PrimaryButton("Zurück", onExit)
        }
        return
    }
    val vm: SelfTestViewModel = viewModel(key = "selftest-$questionnaireId") {
        SelfTestViewModel(questionnaire) { q, r ->
            container.journal.add(
                EntryKind.SELF_TEST, q.id, q.title, container.today(), null,
                scores = SelfTestResults.toScores(q, r),
            ).id
        }
    }
    var confirmExit by remember { mutableStateOf(false) }

    val askBeforeExit = vm.phase == SelfTestPhase.QUESTIONS && vm.hasAnswers
    val requestExit: () -> Unit = { if (askBeforeExit) { confirmExit = true } else { onExit() } }
    BackHandler(onBack = requestExit)

    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = "Selbsttest", onClose = requestExit, closeLabel = "Test verlassen") },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            when (vm.phase) {
                SelfTestPhase.INTRO -> Intro(vm)
                SelfTestPhase.QUESTIONS -> Questions(vm)
                SelfTestPhase.RESULT -> Result(vm, container, onExit)
            }
        }
    }

    if (confirmExit) {
        AlertDialog(
            onDismissRequest = { confirmExit = false },
            title = { Text("Test verlassen?") },
            text = { Text("Das ist völlig in Ordnung. Deine bisherigen Antworten werden dabei verworfen und nicht gespeichert.") },
            confirmButton = { TextButton(onClick = { confirmExit = false; onExit() }) { Text("Verlassen") } },
            dismissButton = { TextButton(onClick = { confirmExit = false }) { Text("Weitermachen") } },
        )
    }
}

@Composable
private fun Intro(vm: SelfTestViewModel) {
    val q = vm.questionnaire
    ScreenColumn {
        if (q.isExample) {
            NoteBox(
                title = "Beispiel-Fragebogen",
                text = "Dieser Fragebogen ist ein unverbindliches Beispiel zum Ausprobieren der App. " +
                    "Er ist nicht wissenschaftlich geprüft.",
            )
        }
        Heading(q.title)
        BodyText(q.intro)
        Text("${q.questions.size} Aussagen · ca. ${q.estimatedMinutes} Minuten", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
        PrimaryButton("Test beginnen", vm::start)
        NoteBox(title = "Gut zu wissen", text = LegalContent.SELF_TEST_GOOD_TO_KNOW)
    }
}

@Composable
private fun Questions(vm: SelfTestViewModel) {
    val q = vm.questionnaire
    ScreenColumn {
        Text(
            "Frage ${vm.index + 1} von ${vm.count}",
            style = MaterialTheme.typography.labelLarge,
            color = LichtblickColors.GoldDark,
        )
        LinearProgressIndicator(
            progress = { vm.progress },
            modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Fortschritt: Frage ${vm.index + 1} von ${vm.count}" },
            color = LichtblickColors.GoldDark,
            trackColor = LichtblickColors.GoldSoft,
        )
        Spacer(Modifier.heightIn(min = 4.dp))
        Text(vm.question.text, style = MaterialTheme.typography.headlineSmall)

        Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            q.options.forEach { option ->
                val selected = vm.selected == option.value
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = TouchHeight)
                        .selectable(selected = selected, role = Role.RadioButton, onClick = { vm.select(option.value) }),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) LichtblickColors.GoldSoft else LichtblickColors.CreamLight,
                        contentColor = LichtblickColors.Text,
                    ),
                    border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) LichtblickColors.GoldDark else LichtblickColors.GoldSoft),
                ) {
                    Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selected,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(selectedColor = LichtblickColors.GoldDark, unselectedColor = LichtblickColors.Gold),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(option.label, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        PrimaryButton(
            text = if (vm.isLast) "Zur Auswertung" else "Weiter",
            onClick = vm::next,
            enabled = vm.canGoNext,
        )
        QuietButton("Zurück", vm::previous)
        Text(
            "Du kannst jederzeit oben rechts aufhören. Deine Antworten werden dann verworfen.",
            style = MaterialTheme.typography.bodyMedium,
            color = LichtblickColors.TextMuted,
        )
    }
}

@Composable
private fun Result(vm: SelfTestViewModel, container: AppContainer, onExit: () -> Unit) {
    val result = vm.result ?: return
    val q = vm.questionnaire
    val entries by container.journal.entries.collectAsStateWithLifecycle()
    val saving by container.journal.isPersistent.collectAsStateWithLifecycle()
    val entry = entries.firstOrNull { it.id == vm.savedEntryId }
    val scores = entry?.scores ?: SelfTestResults.toScores(q, result)
    val previous = entry?.let { SelfTestResults.previous(entries, it) }
    ScreenColumn {
        ResultContent(q, scores, previous?.scores, previous?.date)
        NoteBox(
            title = if (saving) "Gespeichert" else "Nur für diese Sitzung",
            text = if (saving) {
                "Dein Ergebnis ist auf diesem Gerät gespeichert. Deine einzelnen Antworten wurden verworfen. " +
                    "Du findest es unter „Selbsttests“ und in der Rückschau."
            } else {
                "Dein Ergebnis bleibt nur, solange die App geöffnet ist. Deine einzelnen Antworten wurden verworfen."
            },
        )
        SecondaryButton("Test noch einmal machen", vm::restart)
        PrimaryButton("Fertig", onExit)
    }
}

/** Auswertung aus gespeicherten Werten – für das frische Ergebnis und für frühere Ergebnisse. */
@Composable
fun ResultContent(q: Questionnaire, scores: Map<String, Int>, previousScores: Map<String, Int>?, previousDate: LocalDate?) {
    Text("Dein persönliches Ergebnis", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
    if (q.isExample) {
        Text("BEISPIEL-AUSWERTUNG · nicht fachlich geprüft", style = MaterialTheme.typography.labelMedium, color = LichtblickColors.Placeholder)
    }
    val overallBand = SelfTestResults.overallBand(q, scores)
    overallBand?.let { band ->
        if (q.dimensions.isNotEmpty()) {
            Text("Gesamtwert: ${scores[SelfTestResults.OVERALL]} / 100", style = MaterialTheme.typography.titleMedium, color = LichtblickColors.GoldDark)
        }
        Heading(band.title)
        BandText(band, q)
    }
    val dims = SelfTestResults.dimensionScores(q, scores).let { list ->
        if (q.rankDimensions) list.sortedByDescending { it.percent } else list
    }
    if (dims.isNotEmpty()) {
        Heading(q.dimensionsHeading ?: if (overallBand != null) "Deine Bereiche" else q.title)
        LichtblickCard {
            dims.forEach { d ->
                Row(Modifier.fillMaxWidth()) {
                    Text(d.dimension, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    Text("${d.percent} / 100", style = MaterialTheme.typography.titleMedium, color = LichtblickColors.GoldDark)
                }
                LinearProgressIndicator(
                    progress = { d.percent / 100f },
                    modifier = Modifier.fillMaxWidth(),
                    color = LichtblickColors.GoldDark,
                    trackColor = LichtblickColors.GoldSoft,
                )
            }
        }
        val top = dims.maxByOrNull { it.percent }
        if (q.strongestHint != null && dims.size > 1 && top != null && top.percent >= 34) {
            val tops = dims.filter { it.percent == top.percent }.joinToString(" und ") { it.dimension }
            NoteBox(text = "${q.strongestHint} $tops. Unten findest du zu jedem Bereich eine kurze Beschreibung.")
        }
        dims.forEach { d ->
            val band = d.band ?: return@forEach
            LichtblickCard {
                Text("${d.dimension} · ${d.percent} / 100", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
                Text(band.title, style = MaterialTheme.typography.titleLarge)
                BandText(band, q)
            }
        }
    }

    if (previousScores != null) {
        val changes = SelfTestResults.compare(previousScores, scores)
        if (changes.isNotEmpty()) {
            LichtblickCard(highlighted = true) {
                Text("Im Vergleich zu deinem letzten Ergebnis", style = MaterialTheme.typography.titleLarge)
                previousDate?.let {
                    Text("vom ${it.format(DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.GERMAN))}", style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                }
                changes.forEach { c ->
                    val label = if (c.name == SelfTestResults.OVERALL) "Gesamt" else c.name
                    Text(label, style = MaterialTheme.typography.titleMedium)
                    Text("${c.before} → ${c.after} · ${SelfTestResults.describe(c)}", style = MaterialTheme.typography.bodyMedium)
                }
                Text(
                    "Veränderungen sind weder gut noch schlecht. Sie können zeigen, was sich gerade in dir bewegt.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LichtblickColors.TextMuted,
                )
            }
        }
    }

    NoteBox(title = "Bitte beachte", text = q.resultNote.ifBlank { LegalContent.SELF_TEST_DISCLAIMER })

    LichtblickCard {
        Text(q.closingTitle ?: "Magst du darüber sprechen?", style = MaterialTheme.typography.titleMedium)
        ContentText(q.closingText ?: "In einem persönlichen Gespräch kannst du in Ruhe schauen, was dich bewegt.")
        BookingButton(text = "Gespräch vereinbaren")
    }
}

@Composable
private fun BandText(band: ResultBand, q: Questionnaire) {
    band.text.split("\n\n").forEach { ContentText(it) }
    band.permission?.let {
        Text(q.permissionLabel, style = MaterialTheme.typography.titleMedium, color = LichtblickColors.GoldDark)
        ContentText(it)
    }
    band.reflection?.let {
        Text(q.reflectionLabel, style = MaterialTheme.typography.titleMedium, color = LichtblickColors.GoldDark)
        ContentText(it)
    }
}

/** Ein früheres, gespeichertes Ergebnis ansehen. */
@Composable
fun StoredResultScreen(entryId: String, container: AppContainer, onBack: () -> Unit) {
    val entries by container.journal.entries.collectAsStateWithLifecycle()
    val entry = entries.firstOrNull { it.id == entryId }
    val q = entry?.let { SelfTestCatalog.byId(it.itemId) }
    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = "Dein Ergebnis", onBack = onBack) },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                if (entry == null || q == null) {
                    BodyText("Dieses Ergebnis gibt es nicht mehr.")
                    PrimaryButton("Zurück", onBack)
                } else {
                    Text("vom ${formatDate(entry.date)}", style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                    val previous = SelfTestResults.previous(entries, entry)
                    ResultContent(q, entry.scores, previous?.scores, previous?.date)
                    PrimaryButton("Zurück", onBack)
                }
            }
        }
    }
}
