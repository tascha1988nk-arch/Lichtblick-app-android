package de.lichtblickraum.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.lichtblickraum.app.ui.theme.LichtblickColors

/** Mindesthöhe für Bedienflächen – großzügiger als die üblichen 48 dp. */
val TouchHeight = 56.dp

/** Scrollbarer Bildschirminhalt mit großzügigen Abständen. */
@Composable
fun ScreenColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content,
    )
}

@Composable
fun Heading(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier.semantics { heading() },
    )
}

@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(top = 8.dp).semantics { heading() },
    )
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.bodyLarge, modifier = modifier, color = color)
}

@Composable
fun LichtblickCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    highlighted: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = CardDefaults.cardColors(
        containerColor = if (highlighted) LichtblickColors.CreamDeep else LichtblickColors.CreamLight,
        contentColor = LichtblickColors.Text,
    )
    val border = BorderStroke(1.dp, LichtblickColors.GoldSoft)
    val inner: @Composable ColumnScope.() -> Unit = {
        Column(
            Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = content,
        )
    }
    if (onClick != null) {
        Card(onClick = onClick, modifier = modifier.fillMaxWidth(), colors = colors, border = border, content = inner)
    } else {
        Card(modifier = modifier.fillMaxWidth(), colors = colors, border = border, content = inner)
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().heightIn(min = TouchHeight),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LichtblickColors.GoldDark,
            contentColor = LichtblickColors.OnGold,
            disabledContainerColor = LichtblickColors.GoldSoft,
            disabledContentColor = LichtblickColors.TextMuted,
        ),
    ) { Text(text, style = MaterialTheme.typography.labelLarge) }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().heightIn(min = TouchHeight),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        border = BorderStroke(1.5.dp, LichtblickColors.Gold),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = LichtblickColors.GoldDark),
    ) { Text(text, style = MaterialTheme.typography.labelLarge) }
}

@Composable
fun QuietButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().heightIn(min = TouchHeight),
        colors = ButtonDefaults.textButtonColors(contentColor = LichtblickColors.GoldDark),
    ) { Text(text, style = MaterialTheme.typography.labelLarge) }
}

/** Freundlicher Hinweiskasten (z. B. Datenschutz, keine Diagnose). */
@Composable
fun NoteBox(text: String, modifier: Modifier = Modifier, title: String? = null, icon: ImageVector = Icons.Filled.Info) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = LichtblickColors.GoldSoft.copy(alpha = 0.55f), contentColor = LichtblickColors.Text),
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Icon(icon, contentDescription = null, tint = LichtblickColors.GoldDark, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                if (title != null) Text(title, style = MaterialTheme.typography.titleMedium)
                Text(text, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

/** Sichtbare Markierung für noch fehlende Inhalte. */
@Composable
fun PlaceholderText(text: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = LichtblickColors.PlaceholderBg, contentColor = LichtblickColors.Placeholder),
        border = BorderStroke(1.dp, LichtblickColors.Placeholder.copy(alpha = 0.5f)),
    ) {
        Text(text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, modifier = Modifier.padding(12.dp))
    }
}

/** Text, der automatisch als Platzhalter markiert wird, wenn er "[Platzhalter" enthält. */
@Composable
fun ContentText(text: String, modifier: Modifier = Modifier) {
    if (de.lichtblickraum.core.about.AboutContent.isPlaceholder(text)) PlaceholderText(text, modifier)
    else BodyText(text, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LichtblickTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    closeLabel: String = "Beenden",
) {
    TopAppBar(
        title = { Text(title, style = MaterialTheme.typography.titleLarge, maxLines = 2) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack, modifier = Modifier.size(TouchHeight)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                }
            }
        },
        actions = {
            if (onClose != null) {
                IconButton(onClick = onClose, modifier = Modifier.size(TouchHeight)) {
                    Icon(Icons.Filled.Close, contentDescription = closeLabel)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LichtblickColors.Cream,
            titleContentColor = LichtblickColors.Text,
            navigationIconContentColor = LichtblickColors.GoldDark,
            actionIconContentColor = LichtblickColors.GoldDark,
        ),
    )
}

/** Das Lichtblick-Logo (Bild aus res/drawable-nodpi/logo.png). */
@Composable
fun LichtblickLogo(modifier: Modifier = Modifier) {
    androidx.compose.foundation.Image(
        painter = androidx.compose.ui.res.painterResource(de.lichtblickraum.app.R.drawable.logo),
        contentDescription = "Lichtblick – Psychologische Beratung",
        modifier = modifier.width(260.dp),
    )
}
