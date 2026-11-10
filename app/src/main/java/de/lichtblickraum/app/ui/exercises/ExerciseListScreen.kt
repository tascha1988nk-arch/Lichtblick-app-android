package de.lichtblickraum.app.ui.exercises

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.LichtblickTopBar
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.PrimaryButton
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.app.ui.components.SectionTitle
import de.lichtblickraum.core.exercises.Exercise
import de.lichtblickraum.core.exercises.ExerciseCatalog
import de.lichtblickraum.core.exercises.ExerciseCategory

/** Übersicht: zuerst eine Kategorie wählen. */
@Composable
fun ExerciseListScreen(
    suggested: List<Exercise>,
    onOpenCategory: (ExerciseCategory) -> Unit,
    onStart: (String) -> Unit,
) {
    ScreenColumn {
        Heading("Übungen")
        BodyText("Kurze Übungen, die dich im Alltag begleiten können. Wähle ein Thema, das gerade zu dir passt.")
        if (suggested.isNotEmpty()) {
            SectionTitle("Für dich ausgewählt")
            suggested.take(3).forEach { e ->
                LichtblickCard(onClick = { onStart(e.id) }, highlighted = true) {
                    Text(e.title, style = MaterialTheme.typography.titleLarge)
                    Text("${e.category.title} · ${e.durationText}", style = MaterialTheme.typography.labelMedium, color = LichtblickColors.GoldDark)
                }
            }
            SectionTitle("Alle Themen")
        }
        ExerciseCatalog.categories.forEach { category ->
            val count = ExerciseCatalog.inCategory(category).size
            LichtblickCard(onClick = { onOpenCategory(category) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(category.title, style = MaterialTheme.typography.titleLarge)
                        Text(category.description, style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                        Text(if (count == 1) "1 Übung" else "$count Übungen", style = MaterialTheme.typography.labelMedium, color = LichtblickColors.GoldDark)
                    }
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = LichtblickColors.GoldDark)
                }
            }
        }
        NoteBox(title = "Gut zu wissen", text = ExerciseCatalog.GENERAL_NOTE)
    }
}

/** Die Übungen einer Kategorie. */
@Composable
fun ExerciseCategoryScreen(categoryName: String, onStart: (String) -> Unit, onBack: () -> Unit) {
    val category = ExerciseCategory.entries.firstOrNull { it.name == categoryName }
    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = category?.title ?: "Übungen", onBack = onBack) },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                if (category == null) {
                    BodyText("Dieses Thema gibt es nicht.")
                    PrimaryButton("Zurück", onBack)
                } else {
                    BodyText(category.description, color = LichtblickColors.TextMuted)
                    category.note?.let { NoteBox(text = it) }
                    ExerciseCatalog.inCategory(category).forEach { e ->
                        LichtblickCard(onClick = { onStart(e.id) }) {
                            Text(e.title, style = MaterialTheme.typography.titleLarge)
                            Text(e.subtitle, style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                            Text(e.durationText, style = MaterialTheme.typography.labelMedium, color = LichtblickColors.GoldDark)
                        }
                    }
                }
            }
        }
    }
}
