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
import de.lichtblickraum.app.ui.components.BodyText
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
    onOpenSelfTests: () -> Unit,
    onOpenExercises: () -> Unit,
    onOpenLichtblicke: () -> Unit,
    onOpenAbout: () -> Unit,
) {
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
            BodyText(AboutContent.HOME_SUBTITLE, color = LichtblickColors.TextMuted)
        }

        EntryCard(
            icon = Icons.Filled.Star,
            title = "Kleine Lichtblicke",
            text = "Dein Impuls für heute und diese Woche – freiwillig und in deinem Tempo.",
            onClick = onOpenLichtblicke,
            highlighted = true,
        )
        EntryCard(
            icon = Icons.AutoMirrored.Filled.List,
            title = "Selbsttests",
            text = "Kleine Fragebögen zur Selbstreflexion – keine Diagnose.",
            onClick = onOpenSelfTests,
        )
        EntryCard(
            icon = Icons.Filled.Favorite,
            title = "Übungen",
            text = "Kurze Übungen zu Selbstwert, Grenzen, Paarthemen, ADHS im Alltag sowie Gefühlen und Stress.",
            onClick = onOpenExercises,
        )
        EntryCard(
            icon = Icons.Filled.Person,
            title = "Über Lichtblick",
            text = "Wer hinter Lichtblick steht und welche Beratung es gibt.",
            onClick = onOpenAbout,
        )

        LichtblickCard {
            Text("Persönliche Begleitung", style = MaterialTheme.typography.titleLarge)
            BodyText("Wenn du dir Unterstützung wünschst, kannst du einen Termin vereinbaren.")
            BookingButton()
        }

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
