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

    /** PLATZHALTER: z. B. "Ich bin Natascha …" – bitte selbst formulieren. */
    const val COUNSELOR_NAME = "[Platzhalter: Dein Name]"

    /** PLATZHALTER: Deine persönliche Vorstellung. */
    const val INTRODUCTION =
        "[Platzhalter: Hier steht später deine persönliche Vorstellung – wer du bist, " +
            "wie du arbeitest und was dir in der Begleitung wichtig ist.]"

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

    /** PLATZHALTER: Angebote mit Format, Dauer, Ablauf, Kosten – bitte selbst ergänzen. */
    val offers: List<Offer> = listOf(
        Offer("[Platzhalter: Angebot 1, z. B. Einzelberatung]", "[Platzhalter: kurze Beschreibung]"),
        Offer("[Platzhalter: Angebot 2, z. B. Paarberatung]", "[Platzhalter: kurze Beschreibung]"),
        Offer("[Platzhalter: Angebot 3, z. B. ADHS-Coaching]", "[Platzhalter: kurze Beschreibung]"),
    )

    const val APP_PURPOSE =
        "Die App ergänzt die Beratung bei Lichtblick und kann auch unabhängig davon genutzt werden."

    fun isPlaceholder(text: String): Boolean = text.contains("[Platzhalter")
}

data class Offer(val title: String, val description: String)
