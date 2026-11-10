package de.lichtblickraum.app.ui.about

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.lichtblickraum.app.R
import de.lichtblickraum.app.ui.theme.LichtblickColors

/**
 * Fotos der Praxis. Austauschen: Bild in app/src/main/res/drawable-nodpi/ ablegen und hier eintragen.
 * @param aspect Breite / Höhe des Bildes
 */
data class PracticePhoto(@DrawableRes val res: Int, val caption: String, val aspect: Float)

val practicePhotos = listOf(
    PracticePhoto(R.drawable.praxis_raum, "Der Beratungsraum", 16f / 9f),
    PracticePhoto(R.drawable.praxis_sessel, "Platz für dein Gespräch", 16f / 9f),
    PracticePhoto(R.drawable.praxis_ankommen, "Ankommen", 9f / 16f),
)

/** Waagerecht wischbare Fotoreihe – Bilder werden nicht beschnitten. */
@Composable
fun PracticeGallery(modifier: Modifier = Modifier) {
    val height = 220.dp
    LazyRow(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(practicePhotos) { photo ->
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Image(
                    painter = painterResource(photo.res),
                    contentDescription = "Foto der Praxis: ${photo.caption}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(height)
                        .width(height * photo.aspect)
                        .clip(RoundedCornerShape(16.dp)),
                )
                Text(photo.caption, style = MaterialTheme.typography.labelMedium, color = LichtblickColors.TextMuted)
            }
        }
    }
}

/** Ein ruhiges Foto als Kopf einer Karte. */
@Composable
fun PracticeHeaderPhoto(@DrawableRes res: Int, description: String, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(res),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(16.dp)),
    )
}
