package de.lichtblickraum.app.ui.selftest

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.NoteBox
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.components.SectionTitle
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.about.LegalContent
import de.lichtblickraum.core.selftest.SelfTestCatalog

@Composable
fun SelfTestListScreen(onStart: (String) -> Unit) {
    ScreenColumn {
        Heading("Selbsttests")
        BodyText(
            "Kleine Fragebögen laden dich ein, genauer hinzuschauen: Wie geht es mir gerade " +
                "mit mir selbst? Du entscheidest, wie weit du gehst.",
        )
        NoteBox(title = "Zur Selbstreflexion", text = LegalContent.SELF_TEST_DISCLAIMER)
        NoteBox(title = "Deine Antworten bleiben bei dir", text = LegalContent.DATA_NOTICE_SELF_TEST)

        val available = SelfTestCatalog.all.filter { it.isAvailable }
        val upcoming = SelfTestCatalog.all.filterNot { it.isAvailable }

        SectionTitle("Zum Ausprobieren")
        available.forEach { q ->
            LichtblickCard(onClick = { onStart(q.id) }, highlighted = true) {
                if (q.isExample) {
                    Text("BEISPIEL · unverbindliche Reflexion", style = MaterialTheme.typography.labelMedium, color = LichtblickColors.Placeholder)
                }
                Text(q.title, style = MaterialTheme.typography.titleLarge)
                Text(q.shortDescription, style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                Text("ca. ${q.estimatedMinutes} Minuten · ${q.questions.size} Aussagen", style = MaterialTheme.typography.labelMedium, color = LichtblickColors.GoldDark)
            }
        }

        if (upcoming.isNotEmpty()) {
            SectionTitle("In Vorbereitung")
            upcoming.forEach { q ->
                LichtblickCard {
                    Text(q.title, style = MaterialTheme.typography.titleMedium)
                    Text(q.shortDescription, style = MaterialTheme.typography.bodyMedium, color = LichtblickColors.TextMuted)
                    Text("Folgt bald", style = MaterialTheme.typography.labelMedium, color = LichtblickColors.GoldDark)
                }
            }
        }
    }
}
