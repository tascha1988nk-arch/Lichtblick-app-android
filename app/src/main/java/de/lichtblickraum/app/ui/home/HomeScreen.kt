package de.lichtblickraum.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.PrimaryButton
import de.lichtblickraum.app.ui.components.QuietButton
import de.lichtblickraum.app.ui.components.SectionTitle
import de.lichtblickraum.app.ui.mood.MoodLevelPicker
import de.lichtblickraum.app.ui.mood.SupportCard
import de.lichtblickraum.app.ui.about.PracticeHeaderPhoto
import de.lichtblickraum.core.impulses.ImpulseStatus
import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.mood.MoodInsights
import de.lichtblickraum.core.mood.MoodLevel
import de.lichtblickraum.core.profile.Personalization
import de.lichtblickraum.core.thoughts.DailyThoughts
import de.lichtblickraum.app.ui.components.BookingButton
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.about.AboutContent
import de.lichtblickraum.core.about.LegalContent
import java.time.LocalTime

private fun greetingFor(time: LocalTime): String = when (time.hour) {
    in 5..10 -> "Guten Morgen"
    in 11..17 -> "Hallo"
    in 18..22 -> "Guten Abend"
    else -> "Hallo"
}

@Composable
fun HomeScreen(
    container: AppContainer,
    onOpenSelfTests: () -> Unit,
    onOpenExercises: () -> Unit,
    onOpenLichtblicke: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenMood: (Int?) -> Unit,
    onOpenDiary: () -> Unit,
    onOpenReflection: () -> Unit,
    onOpenExercise: (String) -> Unit,
    onEditThemes: () -> Unit,
) {
    val profile by container.profileStore.profile.collectAsStateWithLifecycle()
    val entries by container.journal.entries.collectAsStateWithLifecycle()
    val hiddenUntil by container.settings.supportHiddenUntil.collectAsStateWithLifecycle()
    val choices by container.lichtblicke.choices.collectAsStateWithLifecycle()
    val today = container.today()
    val thought = DailyThoughts.forDay(today, profile.focus)
    val exercise = Personalization.exerciseForDay(profile, today)
    val todayImpulse = container.lichtblicke.state(today, choices, entries, Personalization.preferredThemes(profile)).today
    val showSupport = MoodInsights.needsSupportHint(entries, today) && (hiddenUntil == null || today.isAfter(hiddenUntil))
    val moodToday = entries.firstOrNull { it.kind == EntryKind.MOOD && it.date == today }

    ScreenColumn {
        Column(Modifier.padding(top = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "Lichtblick · ${AboutContent.LOCATION}",
                style = MaterialTheme.typography.labelMedium,
                color = LichtblickColors.GoldDark,
            )
            Text(
                "${greetingFor(LocalTime.now())}.\n${AboutContent.HOME_GREETING}",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.semantics { heading() },
            )
            profile.wish?.let {
                Text("Dein Kompass: „$it“", style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
            }
        }

        if (Personalization.reviewInviteDue(profile, today)) {
            LichtblickCard(highlighted = true) {
                Text("Magst du kurz zurückschauen?", style = MaterialTheme.typography.titleLarge)
                BodyText("Ein paar Wochen sind vergangen. Ein kleiner Rückblick kann zeigen, was sich bewegt hat.")
                PrimaryButton("Ja, gern", onOpenReflection)
                QuietButton("Später", { container.postponeReview() })
            }
        }

        LichtblickCard(highlighted = true) {
            Text("Für heute", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
            Text(thought.text, style = MaterialTheme.typography.titleLarge)
        }

        if (showSupport) SupportCard(onHide = { container.settings.hideSupportUntil(today.plusDays(7)) })

        LichtblickCard {
            Text("Wie geht es dir gerade?", style = MaterialTheme.typography.titleLarge)
            if (moodToday != null) {
                val level = MoodLevel.of(moodToday.level)
                Text(
                    "Heute eingetragen: ${level?.symbol.orEmpty()} ${level?.label.orEmpty()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LichtblickColors.TextMuted,
                )
            } else {
                Text("Antippen, wenn du magst.", style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
            }
            MoodLevelPicker(selected = null) { onOpenMood(it.value) }
            QuietButton("Gefühlstagebuch", onOpenDiary)
        }

        LichtblickCard(onClick = onOpenLichtblicke) {
            Text("Dein Lichtblick für heute", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
            Text(todayImpulse.impulse.title, style = MaterialTheme.typography.titleLarge)
            Text(
                if (todayImpulse.status == ImpulseStatus.DONE) "Für heute schon gemacht." else todayImpulse.impulse.durationText,
                style = MaterialTheme.typography.bodyMedium,
                color = LichtblickColors.TextMuted,
            )
        }

        LichtblickCard(onClick = { onOpenExercise(exercise.id) }) {
            Text("Eine Übung für dich", style = MaterialTheme.typography.labelLarge, color = LichtblickColors.GoldDark)
            Text(exercise.title, style = MaterialTheme.typography.titleLarge)
            Text("${exercise.category.title} · ${exercise.durationText}", style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
        }

        SectionTitle("Alle Bereiche")
        EntryCard(
            icon = Icons.Filled.Star,
            title = "Kleine Lichtblicke",
            text = "Dein Impuls für heute und diese Woche – freiwillig und in deinem Tempo.",
            onClick = onOpenLichtblicke,
        )
        EntryCard(
            icon = Icons.Filled.Favorite,
            title = "Übungen",
            text = "Kurze Übungen zu Selbstwert, Grenzen, Paarthemen, ADHS im Alltag sowie Gefühlen und Stress.",
            onClick = onOpenExercises,
        )
        EntryCard(
            icon = Icons.AutoMirrored.Filled.List,
            title = "Selbsttests",
            text = "Kleine Fragebögen zur Selbstreflexion – keine Diagnose.",
            onClick = onOpenSelfTests,
        )
        EntryCard(
            icon = Icons.Filled.Person,
            title = "Über Lichtblick",
            text = "Wer hinter Lichtblick steht und welche Beratung es gibt.",
            onClick = onOpenAbout,
        )

        LichtblickCard {
            PracticeHeaderPhoto(de.lichtblickraum.app.R.drawable.praxis_tisch, "Foto aus dem Beratungsraum von Lichtblick")
            Text("Persönliche Begleitung", style = MaterialTheme.typography.titleLarge)
            BodyText("Wenn du dir Unterstützung wünschst, kannst du einen Termin vereinbaren.")
            BookingButton()
        }

        QuietButton("Deine Themen anpassen", onEditThemes)
        NoteBox(title = LegalContent.CRISIS_TITLE, text = LegalContent.CRISIS_TEXT)
        Spacer(Modifier.size(8.dp))
    }
}

@Composable
private fun EntryCard(
    icon: ImageVector,
    title: String,
    text: String,
    onClick: () -> Unit,
    highlighted: Boolean = false,
) {
    LichtblickCard(onClick = onClick, highlighted = highlighted) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = LichtblickColors.Gold, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Text(text, style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = LichtblickColors.GoldDark)
        }
    }
}
