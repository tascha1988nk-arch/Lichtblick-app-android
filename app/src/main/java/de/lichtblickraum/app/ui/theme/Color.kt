package de.lichtblickraum.app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * ALLE FARBEN DER APP – hier zentral änderbar.
 *
 * Kontraste (nach WCAG, gegen den Cremeton):
 *  - Text #3B2A1E: sehr gut lesbar (über 12:1)
 *  - GoldDark #7D5522: ca. 5,8:1 – geeignet für Text, Buttons und Links
 *  - Gold #A27330 (deine Wunschfarbe): ca. 3,7:1 – genutzt für Symbole, Linien und
 *    Verzierungen. Für normalen Fließtext wäre sie zu hell, daher dort GoldDark.
 */
object LichtblickColors {
    val Cream = Color(0xFFFBEFE2)         // Hintergrund
    val CreamLight = Color(0xFFFFF8F0)    // Karten
    val CreamDeep = Color(0xFFF3E1CC)     // hervorgehobene Flächen
    val Gold = Color(0xFFA27330)          // Akzent (Symbole, Linien)
    val GoldDark = Color(0xFF7D5522)      // Buttons, Links, Hervorhebungen mit Text
    val GoldSoft = Color(0xFFE9D3B4)      // sanfte Akzentfläche
    val Text = Color(0xFF3B2A1E)          // Haupttext
    val TextMuted = Color(0xFF5E4A3A)     // Nebentext (ca. 7:1)
    val Placeholder = Color(0xFF8A3B12)   // Platzhalter-Markierung
    val PlaceholderBg = Color(0xFFFBE2CF)
    val OnGold = Color(0xFFFFFFFF)
    val Error = Color(0xFF9A2B1C)
}
