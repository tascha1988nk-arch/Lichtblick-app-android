package de.lichtblickraum.core.about

/**
 * Impressum, Datenschutz und wichtige Hinweise.
 *
 * ACHTUNG: Das ist ein ENTWURF und nicht rechtlich geprüft. Vor einer Veröffentlichung
 * bitte vollständig ausfüllen und fachkundig prüfen lassen.
 */
object LegalContent {

    const val DRAFT_NOTICE =
        "Entwurf – noch nicht rechtlich geprüft. Mit [Platzhalter] markierte Angaben fehlen noch."

    const val IMPRINT_SOURCE_NOTICE =
        "Übernommen aus dem Impressum von lichtblick-raum.de (Stand: 10.11.2026). " +
            "Noch nicht rechtlich für die App geprüft."

    /**
     * Von https://lichtblick-raum.de/impressum/ übernommen, angepasst: § 5 DDG statt § 5 TMG,
     * Absatz zur abgeschalteten EU-Streitschlichtungsplattform entfernt.
     */
    val imprint: List<LegalSection> = listOf(
        LegalSection(
            "Angaben gemäß § 5 DDG",
            "Lichtblick\nNatascha Helbig\nKurbrunnenstraße 11\n67098 Bad Dürkheim",
        ),
        LegalSection("Kontakt", "Mobil: 01520-3981836\nE-Mail: kontakt@lichtblick-raum.de"),
        LegalSection("Vertreten durch", "Natascha Helbig"),
        LegalSection(
            "Verbraucherstreitbeilegung / Universalschlichtungsstelle",
            "Wir sind nicht bereit oder verpflichtet, an Streitbeilegungsverfahren vor einer " +
                "Verbraucherschlichtungsstelle teilzunehmen.",
        ),
    )

    val privacy: List<LegalSection> = listOf(
        LegalSection(
            "Kurz gesagt",
            "Die App funktioniert ohne Registrierung, ohne Werbung und ohne Tracking. " +
                "Sie enthält keine Analyse-Werkzeuge und überträgt deine Eingaben nicht an " +
                "Lichtblick oder an andere.",
        ),
        LegalSection(
            "Selbsttests",
            "Deine Antworten werden nur auf deinem Gerät verarbeitet und nicht gespeichert. " +
                "Sobald du einen Test verlässt, werden Antworten und Ergebnis verworfen.",
        ),
        LegalSection(
            "Kleine Lichtblicke und Übungen",
            "Erledigte Impulse und Übungen sowie freiwillige Notizen werden in einem geschützten " +
                "Bereich der App auf deinem Gerät gespeichert, damit du sie in der Rückschau " +
                "wiederfindest. Das Speichern ist zu Beginn eingeschaltet. In der Rückschau kannst " +
                "du es jederzeit ausschalten; dann bleiben Einträge nur, solange die App geöffnet ist. " +
                "Du kannst sie einzeln oder vollständig löschen. Beim Deinstallieren der App " +
                "werden sie ebenfalls gelöscht. Die App schließt diese Daten von Android-Sicherungen aus.",
        ),
        LegalSection(
            "Eingangsfragen, Gefühlsbarometer und Gefühlstagebuch",
            "Deine gewählten Themen und dein Wunsch („Kompass“) werden nur auf deinem Gerät " +
                "gespeichert, damit die App passende Inhalte zeigen kann. Einträge im " +
                "Gefühlsbarometer und Gefühlstagebuch werden wie alle Rückschau-Einträge " +
                "behandelt: nur auf dem Gerät, abschaltbar und einzeln oder vollständig löschbar. " +
                "Nichts davon wird übertragen.",
        ),
        LegalSection(
            "Erinnerungen",
            "Erinnerungen sind freiwillig und zu Beginn ausgeschaltet. Wenn du sie einschaltest, " +
                "fragt Android nach der Erlaubnis für Benachrichtigungen. Die Erinnerungen werden " +
                "direkt auf deinem Gerät geplant; dabei werden keine Daten übertragen.",
        ),
        LegalSection(
            "Links nach außen",
            "Wenn du auf „Termin vereinbaren“ oder die Website tippst, öffnet sich dein Browser " +
                "mit der Website lichtblick-raum.de. Dort gelten die Datenschutzhinweise der Website. " +
                "[Platzhalter: prüfen, ob ein dort eingebundener Buchungsdienst erwähnt werden muss]",
        ),
        LegalSection(
            "Verantwortliche Stelle",
            "Lichtblick, Natascha Helbig\nKurbrunnenstraße 11, 67098 Bad Dürkheim\n" +
                "E-Mail: kontakt@lichtblick-raum.de",
        ),
        LegalSection(
            "Deine Rechte",
            "[Platzhalter: Hinweise zu Auskunft, Löschung, Beschwerderecht usw. – bitte " +
                "fachkundig formulieren lassen]",
        ),
    )

    const val SELF_TEST_DISCLAIMER =
        "Die Selbsttests laden dich zur Selbstreflexion ein. Sie sind keine Diagnose und " +
            "ersetzen keine Beratung, Psychotherapie oder ärztliche Behandlung. Wenn dich etwas " +
            "länger belastet, darfst du dir Unterstützung holen – das ist ein Zeichen von Fürsorge."

    const val DATA_NOTICE_SELF_TEST =
        "Deine Antworten bleiben auf deinem Gerät und werden nicht gespeichert. " +
            "Wenn du den Test verlässt, werden sie verworfen."

    /** Eine Nummer für alle: TelefonSeelsorge (kostenfrei, anonym, rund um die Uhr). */
    const val CRISIS_TITLE = "Wenn es dir gerade sehr schlecht geht"
    const val CRISIS_TEXT =
        "Diese App ist nicht für Krisen gedacht. Die TelefonSeelsorge ist rund um die Uhr für " +
            "dich da – kostenfrei und anonym: 116 123."
}

data class LegalSection(val title: String, val body: String)
