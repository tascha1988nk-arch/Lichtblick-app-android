package de.lichtblickraum.core.about

/**
 * Texte für Startseite und "Über Lichtblick".
 *
 * Alles in [eckigen Klammern] mit "Platzhalter" ist noch nicht ausgefüllt und
 * wird in der App sichtbar als Platzhalter markiert. Bitte nur eigene, geprüfte
 * Angaben eintragen.
 */
object AboutContent {

    const val PRACTICE_NAME = "Lichtblick"
    const val LOCATION = "Bad Dürkheim"

    const val HOME_GREETING = "Schön, dass du da bist."
    const val HOME_SUBTITLE =
        "Diese App lädt dich ein, dich selbst besser kennenzulernen und im Alltag " +
            "etwas freundlicher mit dir umzugehen – in deinem Tempo."

    const val COUNSELOR_NAME = "Natascha Helbig"

    /** Vorstellung (von Natascha freigegeben). */
    const val INTRODUCTION =
        "Ich bin Natascha Helbig und begleite in meiner Praxis in Bad Dürkheim und online Menschen, " +
            "die sich selbst besser verstehen und liebevoller mit sich umgehen möchten.\n\n" +
            "Gemeinsam schauen wir auf das, was dich gerade beschäftigt – in deiner Partnerschaft, " +
            "im Alltag mit ADHS oder bei Themen wie Selbstwert, Grenzen, Beziehungsmustern und " +
            "Gefühlen, die manchmal sehr groß werden.\n\n" +
            "Mir ist wichtig, dass du dich bei mir angenommen fühlst, so wie du bist. Du bestimmst " +
            "das Tempo. Diese App ist eine kleine Begleitung dafür – zwischen unseren Terminen " +
            "oder ganz für dich allein."

    /** PLATZHALTER: Nur selbst geprüfte Angaben zu Ausbildung/Qualifikation eintragen. */
    const val QUALIFICATIONS = "[Platzhalter: Deine Ausbildung und Qualifikationen]"

    /** Von dir genannte Schwerpunkte. */
    val focusAreas: List<String> = listOf(
        "Paarberatung",
        "ADHS-Coaching für Erwachsene",
        "Selbstwert",
        "Grenzen",
        "Beziehungsmuster",
        "Emotionsregulation",
    )

    /** Deine Angebote: Name, kurzer Zusatz und Beschreibung. */
    val offers: List<Offer> = listOf(
        Offer(
            "Paarberatung", "Gemeinsam neu hinschauen",
            "Für Paare, die festgefahrene Muster verstehen, wieder besser miteinander ins " +
                "Gespräch kommen und gemeinsam neue Wege zu echter Nähe und Verbindung finden möchten.",
        ),
        Offer(
            "Kennenlerngespräch", "Für das Einzelcoaching",
            "Bevor wir mit dem Einzelcoaching beginnen, lernen wir uns in Ruhe kennen. Du " +
                "erzählst, was dich beschäftigt, und wir schauen gemeinsam, ob und wie ich dich " +
                "begleiten kann.",
        ),
        Offer(
            "Einzelcoaching", "Zeit für dich",
            "Raum für deine Themen – ob Selbstwert, Grenzen, Beziehungsmuster, Gefühle oder " +
                "ADHS im Alltag. In deinem Tempo und mit einem wohlwollenden Blick auf dich.",
        ),
    )

    const val APP_PURPOSE =
        "Die App ergänzt die Beratung bei Lichtblick und kann auch unabhängig davon genutzt werden."

    fun isPlaceholder(text: String): Boolean = text.contains("[Platzhalter")
}

data class Offer(val title: String, val tagline: String, val description: String)
