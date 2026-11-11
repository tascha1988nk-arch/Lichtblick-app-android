package de.lichtblickraum.app.ui.exercises

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickTopBar
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.PrimaryButton
import de.lichtblickraum.app.ui.components.QuietButton
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.components.TouchHeight
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.exercises.Exercise
import de.lichtblickraum.core.exercises.ExerciseCatalog
import de.lichtblickraum.core.journal.EntryKind

/** Notizen einer Übung – nur im Arbeitsspeicher, solange die Übung offen ist. */
class ExerciseViewModel : ViewModel() {
    /** -1 = Einleitung, 0..n-1 = Schritte, n = Abschluss, n+1 = beendet */
    var step by mutableIntStateOf(-1)
    val notes = mutableStateMapOf<Int, String>()
    var keepNotes by mutableStateOf(false)
    override fun onCleared() = notes.clear()
}

@Composable
fun ExerciseScreen(exerciseId: String, container: AppContainer, onExit: () -> Unit) {
    val exercise = ExerciseCatalog.byId(exerciseId)
    if (exercise == null) {
        ScreenColumn { BodyText("Diese Übung gibt es nicht."); PrimaryButton("Zurück", onExit) }
        return
    }
    if (exercise.guided) {
        GuidedExerciseScreen(exercise, container, onExit)
        return
    }
    val vm: ExerciseViewModel = viewModel(key = "exercise-$exerciseId") { ExerciseViewModel() }
    var confirmExit by remember { mutableStateOf(false) }
    val n = exercise.steps.size
    val hasNotes = vm.notes.values.any { it.isNotBlank() }
    val requestExit: () -> Unit = { if (hasNotes && vm.step <= n) { confirmExit = true } else { onExit() } }
    BackHandler(onBack = requestExit)

    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = exercise.title, onClose = requestExit, closeLabel = "Übung beenden") },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            when {
                vm.step < 0 -> Intro(exercise, onStart = { vm.step = 0 })
                vm.step < n -> Step(exercise, vm, onEnd = requestExit)
                vm.step == n -> Closing(
                    exercise = exercise,
                    vm = vm,
                    hasNotes = hasNotes,
                    onComplete = {
                        val note = if (vm.keepNotes) {
                            exercise.steps.indices
                                .mapNotNull { i -> vm.notes[i]?.takeIf { it.isNotBlank() }?.let { "${exercise.steps[i].title}: ${it.trim()}" } }
                                .joinToString("\n")
                        } else null
                        container.journal.add(EntryKind.EXERCISE, exercise.id, exercise.title, container.today(), note)
                        vm.step = n + 1
                    },
                    onExitWithoutEntry = onExit,
                )
                else -> Done(onExit)
            }
        }
    }

    if (confirmExit) {
        AlertDialog(
            onDismissRequest = { confirmExit = false },
            title = { Text("Übung beenden?") },
            text = { Text("Du kannst jederzeit aufhören. Deine Notizen aus dieser Übung werden dabei verworfen.") },
            confirmButton = { TextButton(onClick = { confirmExit = false; onExit() }) { Text("Beenden") } },
            dismissButton = { TextButton(onClick = { confirmExit = false }) { Text("Weitermachen") } },
        )
    }
}

@Composable
private fun Intro(exercise: Exercise, onStart: () -> Unit) {
    ScreenColumn {
        Text(
            (if (exercise.together) "Zu zweit · " else "") + exercise.durationText,
            style = MaterialTheme.typography.labelLarge,
            color = LichtblickColors.GoldDark,
        )
        Heading(exercise.subtitle)
        BodyText(exercise.intro)
        PrimaryButton("Übung beginnen", onStart)
        NoteBox(title = "Gut zu wissen", text = ExerciseCatalog.GENERAL_NOTE)
    }
}

@Composable
private fun Step(exercise: Exercise, vm: ExerciseViewModel, onEnd: () -> Unit) {
    val i = vm.step
    val step = exercise.steps[i]
    ScreenColumn {
        Text("Schritt ${i + 1} von ${exercise.steps.size}", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
        LinearProgressIndicator(
            progress = { (i + 1f) / exercise.steps.size },
            modifier = Modifier.fillMaxWidth(),
            color = LichtblickColors.GoldDark,
            trackColor = LichtblickColors.GoldSoft,
        )
        Heading(step.title)
        BodyText(step.text)
        if (step.inputPrompt != null) {
            NoteField(
                value = vm.notes[i].orEmpty(),
                onValueChange = { vm.notes[i] = it },
                label = step.inputPrompt!!,
            )
        }
        PrimaryButton(if (i == exercise.steps.lastIndex) "Zum Abschluss" else "Weiter", { vm.step = i + 1 })
        QuietButton("Zurück", { vm.step = i - 1 })
        QuietButton("Übung beenden", onEnd)
    }
}

@Composable
private fun Closing(
    exercise: Exercise,
    vm: ExerciseViewModel,
    hasNotes: Boolean,
    onComplete: () -> Unit,
    onExitWithoutEntry: () -> Unit,
) {
    ScreenColumn {
        Heading("Schön, dass du dir Zeit genommen hast")
        BodyText(exercise.closing)
        if (hasNotes) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = TouchHeight)
                    .toggleable(value = vm.keepNotes, role = Role.Checkbox, onValueChange = { vm.keepNotes = it }),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = vm.keepNotes,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(checkedColor = LichtblickColors.GoldDark, uncheckedColor = LichtblickColors.Gold),
                )
                Spacer(Modifier.width(12.dp))
                Text("Meine Notizen in die Rückschau übernehmen", style = MaterialTheme.typography.bodyLarge)
            }
        }
        PrimaryButton("In der Rückschau vermerken", onComplete)
        QuietButton("Ohne Eintrag beenden", onExitWithoutEntry)
        Text(
            "Was in der Rückschau gespeichert wird, entscheidest du unter „Lichtblicke“ → „Rückschau“.",
            style = MaterialTheme.typography.bodyMedium,
            color = LichtblickColors.TextMuted,
        )
    }
}

@Composable
private fun Done(onExit: () -> Unit) {
    ScreenColumn {
        Heading("Vermerkt")
        BodyText("Die Übung steht jetzt in deiner Rückschau. Danke, dass du dir diesen Moment genommen hast.")
        PrimaryButton("Zurück zur Übersicht", onExit)
    }
}

@Composable
fun NoteField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth().heightIn(min = 120.dp),
        minLines = 3,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = LichtblickColors.GoldDark,
            unfocusedBorderColor = LichtblickColors.Gold,
            focusedLabelColor = LichtblickColors.GoldDark,
            cursorColor = LichtblickColors.GoldDark,
            focusedContainerColor = LichtblickColors.CreamLight,
            unfocusedContainerColor = LichtblickColors.CreamLight,
        ),
    )
}
