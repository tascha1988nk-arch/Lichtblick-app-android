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

    val imprint: List<LegalSection> = listOf(
        LegalSection(
            "Angaben zur Anbieterin",
            "[Platzhalter: Vor- und Nachname]\n[Platzhalter: Praxisname]\n" +
                "[Platzhalter: Straße und Hausnummer]\n[Platzhalter: PLZ] Bad Dürkheim",
        ),
        LegalSection("Kontakt", "E-Mail: [Platzhalter]\nTelefon: [Platzhalter]"),
        LegalSection(
            "Berufsbezeichnung und weitere Pflichtangaben",
            "[Platzhalter: Berufsbezeichnung, ggf. Aufsichtsbehörde/Kammer, Umsatzsteuer-ID " +
                "o. Ä. – nur sofern zutreffend, bitte prüfen lassen]",
        ),
        LegalSection("Verantwortlich für den Inhalt", "[Platzhalter]"),
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
            "Erledigte Impulse und Übungen sowie freiwillige Notizen bleiben zunächst nur, " +
                "solange die App geöffnet ist. Nur wenn du das Speichern ausdrücklich einschaltest, " +
                "werden sie in einem geschützten Bereich der App auf deinem Gerät abgelegt. " +
                "Du kannst sie einzeln oder vollständig löschen. Beim Deinstallieren der App " +
                "werden sie ebenfalls gelöscht. Die App schließt diese Daten von Android-Sicherungen aus.",
        ),
        LegalSection(
            "Links nach außen",
            "Wenn du auf „Termin vereinbaren“ oder die Website tippst, öffnet sich dein Browser " +
                "mit der Website lichtblick-raum.de. Dort gelten die Datenschutzhinweise der Website. " +
                "[Platzhalter: prüfen, ob ein dort eingebundener Buchungsdienst erwähnt werden muss]",
        ),
        LegalSection("Verantwortliche Stelle", "[Platzhalter: Name, Anschrift, Kontakt]"),
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

    /** Allgemein bekannte, kostenfreie Hilfsangebote in Deutschland – bitte vor Veröffentlichung prüfen. */
    const val CRISIS_TITLE = "Wenn es dir gerade sehr schlecht geht"
    const val CRISIS_TEXT =
        "Diese App ist nicht für Krisen gedacht. In akuten Notfällen wähle bitte 112. " +
            "Die TelefonSeelsorge ist rund um die Uhr kostenfrei und anonym erreichbar: " +
            "0800 111 0 111, 0800 111 0 222 oder 116 123."
}

data class LegalSection(val title: String, val body: String)
