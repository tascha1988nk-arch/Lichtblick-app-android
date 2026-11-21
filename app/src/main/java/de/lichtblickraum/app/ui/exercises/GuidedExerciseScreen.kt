package de.lichtblickraum.app.ui.exercises

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.audio.Ambience
import de.lichtblickraum.app.audio.AmbiencePlayer
import de.lichtblickraum.app.audio.GuidedVoice
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
import de.lichtblickraum.app.ui.components.TouchHeight
import de.lichtblickraum.app.ui.mood.dialSupport
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.about.LegalContent
import de.lichtblickraum.core.exercises.CalmingExercises
import de.lichtblickraum.core.exercises.Exercise
import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.mood.MoodContent
import kotlinx.coroutines.delay

private enum class GuidedPhase { INTRO, RUNNING, DONE }

/** Angeleitete Übung: Vorlesestimme des Geräts, optionaler Hintergrundklang, jederzeit beendbar. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GuidedExerciseScreen(exercise: Exercise, container: AppContainer, onExit: () -> Unit) {
    val context = LocalContext.current
    val voice = remember { GuidedVoice(context) }
    DisposableEffect(Unit) { onDispose { voice.shutdown() } }

    var phase by rememberSaveable { mutableStateOf(GuidedPhase.INTRO) }
    var step by rememberSaveable { mutableIntStateOf(0) }
    var voiceOn by rememberSaveable { mutableStateOf(true) }
    var ambience by rememberSaveable { mutableStateOf(Ambience.PAD) }
    var paused by rememberSaveable { mutableStateOf(false) }
    var tooMuch by rememberSaveable { mutableStateOf(false) }
    val n = exercise.steps.size
    val running = phase == GuidedPhase.RUNNING && !paused && !tooMuch

    // Bildschirm während der Übung nicht ausschalten
    val view = LocalView.current
    DisposableEffect(running) {
        view.keepScreenOn = running
        onDispose { view.keepScreenOn = false }
    }

    // Vorlesen und automatisch weitergehen – nur wenn die Stimme verfügbar und eingeschaltet ist
    LaunchedEffect(step, running, voiceOn, voice.ready) {
        if (!running) { voice.stop(); return@LaunchedEffect }
        if (!voiceOn || voice.ready != true) return@LaunchedEffect
        val current = exercise.steps[step]
        voice.speak(current.text)
        delay(current.pauseSeconds * 1000L)
        if (step < n - 1) step++ else phase = GuidedPhase.DONE
    }

    // Hintergrundklang
    LaunchedEffect(running, ambience) {
        if (running) AmbiencePlayer.play(ambience)
    }

    BackHandler { onExit() }

    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = exercise.title, onClose = onExit, closeLabel = "Übung beenden") },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                when {
                    tooMuch -> {
                        Heading(CalmingExercises.TOO_MUCH_TITLE)
                        BodyText(CalmingExercises.TOO_MUCH_TEXT)
                        PrimaryButton("Übung beenden", onExit)
                        SecondaryButton("Weitermachen, wenn es wieder geht", { tooMuch = false })
                        LichtblickCard {
                            Text(LegalContent.CRISIS_TITLE, style = MaterialTheme.typography.titleMedium)
                            BodyText(LegalContent.CRISIS_TEXT)
                            QuietButton("TelefonSeelsorge anrufen: ${MoodContent.SUPPORT_PHONE}", { dialSupport(context) })
                        }
                    }
                    phase == GuidedPhase.INTRO -> {
                        Text(exercise.durationText, style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
                        Heading(exercise.subtitle)
                        BodyText(exercise.intro)
                        SectionTitle("Einstellungen")
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .heightIn(min = TouchHeight)
                                .toggleable(value = voiceOn, role = Role.Switch, onValueChange = { voiceOn = it }),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("Anleitung vorlesen", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                            Spacer(Modifier.width(12.dp))
                            Switch(
                                checked = voiceOn,
                                onCheckedChange = null,
                                colors = SwitchDefaults.colors(checkedTrackColor = LichtblickColors.GoldDark),
                            )
                        }
                        if (voice.ready == false) {
                            Text(
                                "Die Vorlesestimme ist auf diesem Gerät gerade nicht verfügbar. Du kannst die Anleitung in deinem Tempo lesen.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = LichtblickColors.TextMuted,
                            )
                        }
                        Text("Hintergrundklang", style = MaterialTheme.typography.titleMedium)
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Ambience.entries.forEach { a ->
                                FilterChip(
                                    selected = ambience == a,
                                    onClick = { ambience = a },
                                    label = { Text(a.label) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = LichtblickColors.GoldSoft,
                                        selectedLabelColor = LichtblickColors.Text,
                                    ),
                                )
                            }
                        }
                        PrimaryButton("Übung beginnen", { step = 0; paused = false; phase = GuidedPhase.RUNNING })
                        NoteBox(title = "Gut zu wissen", text = exercise.category.note ?: "")
                    }
                    phase == GuidedPhase.RUNNING -> {
                        val current = exercise.steps[step]
                        Text("Schritt ${step + 1} von $n", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
                        LinearProgressIndicator(
                            progress = { (step + 1f) / n },
                            modifier = Modifier.fillMaxWidth(),
                            color = LichtblickColors.GoldDark,
                            trackColor = LichtblickColors.GoldSoft,
                        )
                        Heading(current.title)
                        Text(current.text, style = MaterialTheme.typography.headlineSmall)
                        if (voiceOn && voice.ready == true && !paused) {
                            Text("Wird vorgelesen – du kannst einfach zuhören.", style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                        }
                        SecondaryButton(if (step < n - 1) "Weiter" else "Abschließen", {
                            if (step < n - 1) step++ else phase = GuidedPhase.DONE
                        })
                        SecondaryButton(if (paused) "Fortsetzen" else "Pause", { paused = !paused })
                        if (step > 0) QuietButton("Zurück", { step-- })
                        QuietButton(CalmingExercises.TOO_MUCH_TITLE, { tooMuch = true })
                        QuietButton("Übung beenden", onExit)
                    }
                    else -> {
                        Heading("Schön, dass du dir Zeit genommen hast")
                        BodyText(exercise.closing)
                        PrimaryButton("In der Rückschau vermerken", {
                            container.journal.add(EntryKind.EXERCISE, exercise.id, exercise.title, container.today(), null)
                            onExit()
                        })
                        QuietButton("Ohne Eintrag beenden", onExit)
                    }
                }
            }
        }
    }
}
