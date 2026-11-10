package de.lichtblickraum.app.ui.about

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import de.lichtblickraum.app.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.lichtblickraum.app.navigation.Routes
import de.lichtblickraum.app.ui.components.BodyText
import de.lichtblickraum.app.ui.components.BookingButton
import de.lichtblickraum.app.ui.components.ContentText
import de.lichtblickraum.app.ui.components.Heading
import de.lichtblickraum.app.ui.components.LichtblickCard
import de.lichtblickraum.app.ui.components.QuietButton
import de.lichtblickraum.app.ui.components.ScreenColumn
import de.lichtblickraum.app.ui.components.SecondaryButton
import de.lichtblickraum.app.ui.components.SectionTitle
import de.lichtblickraum.app.ui.components.openInBrowser
import de.lichtblickraum.app.ui.theme.LichtblickColors
import de.lichtblickraum.core.about.AboutContent
import de.lichtblickraum.core.config.AppLinks

@Composable
fun AboutScreen(onOpenLegal: (String) -> Unit) {
    val context = LocalContext.current
    ScreenColumn {
        Heading("Über Lichtblick")
        Text(
            "Psychologische Beratung in ${AboutContent.LOCATION} und online",
            style = MaterialTheme.typography.titleMedium,
            color = LichtblickColors.GoldDark,
        )

        Portrait(Modifier.align(Alignment.CenterHorizontally))
        Text(
            AboutContent.COUNSELOR_NAME,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        ContentText(AboutContent.INTRODUCTION)
        ContentText(AboutContent.QUALIFICATIONS)

        SectionTitle("Schwerpunkte")
        LichtblickCard {
            AboutContent.focusAreas.forEach { area ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(8.dp).clip(CircleShape).background(LichtblickColors.Gold))
                    Spacer(Modifier.width(12.dp))
                    BodyText(area)
                }
            }
        }

        SectionTitle("Beratungsangebote")
        AboutContent.offers.forEach { offer ->
            LichtblickCard {
                Text(offer.title, style = MaterialTheme.typography.titleLarge)
                Text(offer.tagline, style = MaterialTheme.typography.titleMedium, color = LichtblickColors.GoldDark)
                ContentText(offer.description)
            }
        }

        BodyText(AboutContent.APP_PURPOSE, color = LichtblickColors.TextMuted)

        BookingButton()
        SecondaryButton("Website besuchen", { openInBrowser(context, AppLinks.WEBSITE_URL) })

        SectionTitle("Rechtliches")
        QuietButton("Impressum", { onOpenLegal(Routes.LEGAL_IMPRINT) })
        QuietButton("Datenschutzhinweise", { onOpenLegal(Routes.LEGAL_PRIVACY) })
        Text(
            "App-Version 0.1 · Prototyp",
            style = MaterialTheme.typography.labelMedium,
            color = LichtblickColors.TextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

/**
 * Dein Porträt. Austauschen: neues Bild als app/src/main/res/drawable-nodpi/portrait.jpg ablegen
 * (quadratisch, Gesicht mittig).
 */
@Composable
private fun Portrait(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.portrait),
        contentDescription = "Porträtfoto",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(200.dp)
            .clip(CircleShape)
            .border(BorderStroke(3.dp, LichtblickColors.Gold), CircleShape),
    )
}
