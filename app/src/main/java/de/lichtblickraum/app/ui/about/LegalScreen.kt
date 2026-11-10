package de.lichtblickraum.app.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.lichtblickraum.app.navigation.Routes
import de.lichtblickraum.app.ui.components.ContentText
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.LichtblickTopBar
import de.lichtblickraum.app.ui.components.PlaceholderText
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.about.LegalContent

@Composable
fun LegalScreen(type: String, onBack: () -> Unit) {
    val isImprint = type == Routes.LEGAL_IMPRINT
    val title = if (isImprint) "Impressum" else "Datenschutzhinweise"
    val sections = if (isImprint) LegalContent.imprint else LegalContent.privacy

    Scaffold(
        containerColor = LichtblickColors.Cream,
        topBar = { LichtblickTopBar(title = title, onBack = onBack) },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            ScreenColumn {
                PlaceholderText(if (isImprint) LegalContent.IMPRINT_SOURCE_NOTICE else LegalContent.DRAFT_NOTICE)
                sections.forEach { section ->
                    LichtblickCard {
                        Text(section.title, style = MaterialTheme.typography.titleMedium)
                        section.body.split("\n").forEach { ContentText(it) }
                    }
                }
            }
        }
    }
}
