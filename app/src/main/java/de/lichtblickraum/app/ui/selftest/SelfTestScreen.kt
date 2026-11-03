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

@Composable
fun SelfTestScreen(questionnaireId: String, onExit: () -> Unit) {
    val questionnaire = SelfTestCatalog.byId(questionnaireId)?.takeIf { it.isAvailable }
    if (questionnaire == null) {
        ScreenColumn {
            BodyText("Dieser Test ist noch nicht verfügbar.")
            PrimaryButton("Zurück", onExit)
        }
        return
    }
    val vm: SelfTestViewModel = viewModel(key = "selftest-$questionnaireId") { SelfTestViewModel(questionnaire) }
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
                SelfTestPhase.RESULT -> Result(vm, onExit)
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
        NoteBox(text = LegalContent.SELF_TEST_DISCLAIMER)
        NoteBox(title = "Datenschutz", text = LegalContent.DATA_NOTICE_SELF_TEST)
        PrimaryButton("Test beginnen", vm::start)
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
private fun Result(vm: SelfTestViewModel, onExit: () -> Unit) {
    val result = vm.result ?: return
    val q = vm.questionnaire
    ScreenColumn {
        Text("Deine Auswertung", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
        if (q.isExample) {
            Text("BEISPIEL-AUSWERTUNG · nicht fachlich geprüft", style = MaterialTheme.typography.labelMedium, color = LichtblickColors.Placeholder)
        }
        Heading(result.band.title)
        BodyText(result.band.text)

        if (result.dimensions.isNotEmpty()) {
            LichtblickCard {
                Text("Einzelne Bereiche", style = MaterialTheme.typography.titleMedium)
                result.dimensions.forEach { d ->
                    Text(d.dimension, style = MaterialTheme.typography.bodyMedium)
                    LinearProgressIndicator(
                        progress = { d.percent / 100f },
                        modifier = Modifier.fillMaxWidth(),
                        color = LichtblickColors.GoldDark,
                        trackColor = LichtblickColors.GoldSoft,
                    )
                }
            }
        }

        NoteBox(title = "Bitte beachte", text = q.resultNote.ifBlank { LegalContent.SELF_TEST_DISCLAIMER })
        NoteBox(
            title = "Nicht gespeichert",
            text = "Dieses Ergebnis wird nicht gespeichert. Wenn du die Seite verlässt, ist es verworfen.",
        )

        LichtblickCard {
            Text("Magst du darüber sprechen?", style = MaterialTheme.typography.titleMedium)
            BodyText("In einem persönlichen Gespräch kannst du in Ruhe schauen, was dich bewegt.")
            BookingButton(text = "Gespräch vereinbaren")
        }
        SecondaryButton("Test noch einmal machen", vm::restart)
        PrimaryButton("Fertig", onExit)
    }
}
