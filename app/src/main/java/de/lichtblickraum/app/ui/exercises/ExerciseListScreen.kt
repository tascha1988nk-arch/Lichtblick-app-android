package de.lichtblickraum.app.ui.exercises

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.exercises.ExerciseCatalog

@Composable
fun ExerciseListScreen(onStart: (String) -> Unit) {
    ScreenColumn {
        Heading("Übungen")
        BodyText("Kurze Übungen, die dich im Alltag begleiten können. Wähle, was sich gerade stimmig anfühlt.")
        NoteBox(text = ExerciseCatalog.GENERAL_NOTE)
        ExerciseCatalog.all.forEach { e ->
            LichtblickCard(onClick = { onStart(e.id) }) {
                Text(e.title, style = MaterialTheme.typography.titleLarge)
                Text(e.subtitle, style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                Text(e.durationText, style = MaterialTheme.typography.labelMedium, color = LichtblickColors.GoldDark)
            }
        }
    }
}
