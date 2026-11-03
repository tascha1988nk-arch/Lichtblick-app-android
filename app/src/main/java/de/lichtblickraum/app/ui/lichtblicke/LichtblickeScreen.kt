package de.lichtblickraum.app.ui.lichtblicke

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.PrimaryButton
import de.lichtblickraum.app.ui.components.QuietButton
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.components.SecondaryButton
import de.lichtblickraum.app.ui.components.SectionTitle
import de.lichtblickraum.app.ui.exercises.NoteField
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.impulses.Feedback
import de.lichtblickraum.core.impulses.ImpulseCard
import de.lichtblickraum.core.impulses.ImpulseStatus

@Composable
fun LichtblickeScreen(container: AppContainer, onOpenReview: () -> Unit) {
    val paused by container.settings.impulsesPaused.collectAsStateWithLifecycle()
    var welcomeBack by remember { mutableStateOf(false) }

    ScreenColumn {
        Heading("Kleine Lichtblicke")

        if (paused) {
            LichtblickCard(highlighted = true) {
                Text("Pause", style = MaterialTheme.typography.titleLarge)
                BodyText(Feedback.PAUSED)
                PrimaryButton("Wieder einsteigen", { welcomeBack = true; container.settings.setImpulsesPaused(false) })
            }
            SecondaryButton("Rückschau ansehen", onOpenReview)
        } else {
            if (welcomeBack) NoteBox(text = Feedback.WELCOME_BACK)
            ActiveImpulses(container, onOpenReview)
        }
    }
}

@Composable
private fun ActiveImpulses(container: AppContainer, onOpenReview: () -> Unit) {
    val entries by container.journal.entries.collectAsStateWithLifecycle()
    val choices by container.lichtblicke.choices.collectAsStateWithLifecycle()
    val saving by container.journal.isPersistent.collectAsStateWithLifecycle()
    val today = container.today()
    val controller = container.lichtblicke

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        BodyText(
            "Kleine Impulse für deinen Alltag – als Einladung, nicht als Pflicht. " +
                "Du kannst jede Aufgabe überspringen, austauschen oder alles pausieren.",
            color = LichtblickColors.TextMuted,
        )

        val state = controller.state(today, choices, entries)

        SectionTitle("Dein Lichtblick für heute")
        ImpulseCardView(
            card = state.today,
            skipLabel = "Heute auslassen",
            onDone = { note -> controller.completeDaily(today, state.today.impulse, note) },
            onSwap = { controller.swapDaily(today) },
            onSkip = { controller.skipDaily(today) },
            onReopen = { controller.reopenDaily(today) },
        )

        SectionTitle("Dein Wochenimpuls")
        ImpulseCardView(
            card = state.week,
            skipLabel = "Diese Woche auslassen",
            onDone = { note -> controller.completeWeekly(today, state.week.impulse, note) },
            onSwap = { controller.swapWeekly(today) },
            onSkip = { controller.skipWeekly(today) },
            onReopen = { controller.reopenWeekly(today) },
        )

        NoteBox(
            title = if (saving) "Speichern ist eingeschaltet" else "Nur für diese Sitzung",
            text = if (saving) {
                "Erledigte Impulse und Notizen werden nur auf diesem Gerät gespeichert. " +
                    "In der Rückschau kannst du sie jederzeit löschen."
            } else {
                "Erledigte Impulse und Notizen bleiben nur, solange die App geöffnet ist. " +
                    "In der Rückschau kannst du das Speichern auf diesem Gerät einschalten."
            },
        )
        SecondaryButton("Rückschau ansehen", onOpenReview)
        QuietButton("Kleine Lichtblicke pausieren", { container.settings.setImpulsesPaused(true) })
    }
}

@Composable
private fun ImpulseCardView(
    card: ImpulseCard,
    skipLabel: String,
    onDone: (String?) -> Unit,
    onSwap: () -> Unit,
    onSkip: () -> Unit,
    onReopen: () -> Unit,
) {
    val impulse = card.impulse
    var note by remember(impulse.id) { mutableStateOf("") }

    LichtblickCard(highlighted = card.status == ImpulseStatus.OPEN) {
        when (card.status) {
            ImpulseStatus.DONE -> {
                Text(impulse.title, style = MaterialTheme.typography.titleMedium, color = LichtblickColors.TextMuted)
                Text(card.feedback.orEmpty(), style = MaterialTheme.typography.titleLarge)
            }
            ImpulseStatus.SKIPPED -> {
                BodyText(Feedback.AFTER_SKIP)
                QuietButton("Doch anschauen", onReopen)
            }
            ImpulseStatus.OPEN -> {
                Text(
                    "${impulse.theme.label} · ${impulse.durationText}",
                    style = MaterialTheme.typography.labelMedium,
                    color = LichtblickColors.GoldDark,
                )
                Text(impulse.title, style = MaterialTheme.typography.titleLarge)
                BodyText(impulse.task)
                Text(impulse.explanation, style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                if (impulse.textPrompt != null) {
                    NoteField(value = note, onValueChange = { note = it }, label = impulse.textPrompt!! + " – freiwillig")
                }
                PrimaryButton("Für mich gemacht", { onDone(note); note = "" })
                SecondaryButton("Andere Aufgabe", onSwap)
                QuietButton(skipLabel, onSkip)
            }
        }
    }
}
