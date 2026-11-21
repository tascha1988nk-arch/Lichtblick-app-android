package de.lichtblickraum.app.ui.about

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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

/** Das Foto unter „So sieht es bei mir aus“. */
val practicePhoto = PracticePhoto(R.drawable.praxis_raum, "Der Beratungsraum", 1672f / 941f)

/** Ein Foto der Praxis über die ganze Breite, mit abgerundeten Ecken. */
@Composable
fun PracticeGallery(modifier: Modifier = Modifier, photo: PracticePhoto = practicePhoto) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Image(
            painter = painterResource(photo.res),
            contentDescription = "Foto der Praxis: ${photo.caption}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(photo.aspect)
                .clip(RoundedCornerShape(16.dp)),
        )
        Text(photo.caption, style = MaterialTheme.typography.labelMedium, color = LichtblickColors.TextMuted)
    }
}

/** Ein ruhiges Foto als Kopf einer Karte. */
@Composable
fun PracticeHeaderPhoto(
    @DrawableRes res: Int,
    description: String,
    modifier: Modifier = Modifier,
    aspect: Float = 16f / 9f,
) {
    Image(
        painter = painterResource(res),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspect)
            .clip(RoundedCornerShape(16.dp)),
    )
}
