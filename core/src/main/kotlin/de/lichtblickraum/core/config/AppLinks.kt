package de.lichtblickraum.core.config

/**
 * Alle Links der App an EINER Stelle.
 *
 * Zum Austauschen einfach den Text zwischen den Anführungszeichen ändern.
 * Solange ein Link leer ist, zeigt die App statt des Browsers einen freundlichen Hinweis.
 */
object AppLinks {

    /**
     * Seite zur Terminvereinbarung auf deiner Website (dort ist die Buchung eingebunden).
     * Wird hier ein leerer Text eingetragen, zeigt die App statt des Browsers einen Hinweis.
     */
    const val BOOKING_URL: String = "https://lichtblick-raum.de/termin-vereinbaren/"

    /** Deine Website (von dir angegeben). */
    const val WEBSITE_URL: String = "https://lichtblick-raum.de"

    /** Prüft, ob ein Link gesetzt ist und sicher (https) aussieht. */
    fun isUsable(url: String): Boolean =
        url.isNotBlank() && url.trim().startsWith("https://")

    val isBookingConfigured: Boolean get() = isUsable(BOOKING_URL)
}
