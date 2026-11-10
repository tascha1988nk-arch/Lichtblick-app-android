package de.lichtblickraum.core.selftest

/**
 * Alle Selbsttests der App.
 *
 * NEUEN TEST ERGÄNZEN: einen weiteren Questionnaire(...) anlegen (z. B. in einer eigenen Datei)
 * und unten in [all] eintragen. Für die vorbereiteten Themen reicht es, bei
 * status = AVAILABLE zu setzen und options, questions und bands zu füllen.
 *
 * WICHTIG: Die folgenden Inhalte sind ein BEISPIEL zum Ausprobieren der App.
 * Sie sind nicht wissenschaftlich geprüft und enthalten keine diagnostischen Grenzwerte.
 */
object SelfTestCatalog {

    /** Standard-Antwortskala, kann pro Test ersetzt werden. */
    val agreementScale: List<AnswerOption> = listOf(
        AnswerOption("Trifft gar nicht zu", 0),
        AnswerOption("Trifft eher nicht zu", 1),
        AnswerOption("Teils, teils", 2),
        AnswerOption("Trifft eher zu", 3),
        AnswerOption("Trifft voll zu", 4),
    )

    private const val EXAMPLE_NOTE =
        "Dies ist eine Beispiel-Auswertung, um die App auszuprobieren. Sie beruht auf keinem " +
            "geprüften Verfahren und sagt nichts Verbindliches über dich aus. Sie ist eine " +
            "Momentaufnahme und eine Einladung zum Nachdenken – keine Diagnose und kein Ersatz " +
            "für Beratung, Therapie oder ärztliche Behandlung."

    val example = Questionnaire(
        id = "beispiel-freundlich-mit-mir",
        title = "Beispiel: Wie freundlich gehe ich gerade mit mir um?",
        shortDescription = "Eine kurze, unverbindliche Reflexion mit 8 Aussagen.",
        intro = "Dieser Fragebogen ist ein Beispiel zum Ausprobieren. Lies jede Aussage und " +
            "wähle, was im Moment am ehesten zu dir passt. Es gibt kein Richtig oder Falsch. " +
            "Nimm dir so viel Zeit, wie du magst – und hör jederzeit auf, wenn es sich nicht " +
            "stimmig anfühlt.",
        status = QuestionnaireStatus.AVAILABLE,
        isExample = true,
        estimatedMinutes = 3,
        options = agreementScale,
        questions = listOf(
            Question("q1", "Wenn mir ein Fehler passiert, spreche ich innerlich eher verständnisvoll mit mir."),
            Question("q2", "Ich nehme mir im Alltag Zeit für Dinge, die mir guttun."),
            Question("q3", "Ich merke meistens, wenn mir etwas zu viel wird."),
            Question("q4", "Es fällt mir leicht, um Unterstützung zu bitten."),
            Question("q5", "Ich kann Nein sagen, ohne mich danach lange schuldig zu fühlen."),
            Question("q6", "Ich erkenne auch kleine Dinge an, die mir gelungen sind."),
            Question("q7", "Mit mir selbst bin ich oft strenger als mit anderen Menschen.", reversed = true),
            Question("q8", "Ich stelle meine Bedürfnisse häufig zurück, damit es anderen gut geht.", reversed = true),
        ),
        bands = listOf(
            ResultBand(
                0, 39,
                "Gerade bist du eher streng mit dir",
                "Deine Antworten deuten an, dass du im Moment eher hohe Ansprüche an dich hast " +
                    "und dir selbst wenig Raum gibst. Das ist nichts, was du sofort ändern musst. " +
                    "Vielleicht magst du einfach neugierig bemerken, wann du besonders streng mit " +
                    "dir bist. Die Übungen und kleinen Lichtblicke können dich dabei begleiten.",
            ),
            ResultBand(
                40, 69,
                "Mal so, mal so",
                "Deine Antworten deuten an, dass es dir in manchen Bereichen schon gelingt, " +
                    "freundlich mit dir umzugehen, und in anderen weniger. Das ist sehr menschlich. " +
                    "Vielleicht magst du schauen, welche Aussagen dir leichter und welche dir " +
                    "schwerer gefallen sind.",
            ),
            ResultBand(
                70, 100,
                "Oft gehst du schon freundlich mit dir um",
                "Deine Antworten deuten an, dass du dir im Moment häufig mit Verständnis begegnest. " +
                    "Schön, wenn du das bemerken kannst. Auch an anderen Tagen darf es anders " +
                    "aussehen – das gehört dazu.",
            ),
        ),
        resultNote = EXAMPLE_NOTE,
    )

    private fun inPreparation(id: String, title: String, description: String) = Questionnaire(
        id = id,
        title = title,
        shortDescription = description,
        intro = "",
        status = QuestionnaireStatus.IN_PREPARATION,
        isExample = false,
        estimatedMinutes = 0,
    )

    private fun placeholderBands(part: String) = listOf(
        ResultBand(0, 33, "[Platzhalter: $part 0–33 – Überschrift]", "[Platzhalter: Text]",
            permission = "[Platzhalter: Erlauber]", reflection = "[Platzhalter: Reflexionsfrage]"),
        ResultBand(34, 66, "[Platzhalter: $part 34–66 – Überschrift]", "[Platzhalter: Text]",
            permission = "[Platzhalter: Erlauber]", reflection = "[Platzhalter: Reflexionsfrage]"),
        ResultBand(67, 100, "[Platzhalter: $part 67–100 – Überschrift]", "[Platzhalter: Text]",
            permission = "[Platzhalter: Erlauber]", reflection = "[Platzhalter: Reflexionsfrage]"),
    )

    /**
     * Test von Natascha (bisher bei Formly). Struktur steht, Inhalte folgen:
     * Antwortskala, 47 Aussagen mit Zuordnung zu den vier Bereichen und alle Auswertungstexte.
     * Sobald alles eingetragen ist: status = AVAILABLE setzen.
     */
    val innerCritic = Questionnaire(
        id = "innerer-kritiker",
        title = "Wie stark ist dein innerer Kritiker?",
        shortDescription = "Welche Seiten deines inneren Kritikers sind bei dir besonders aktiv?",
        intro = "[Platzhalter: Einleitungstext]",
        status = QuestionnaireStatus.IN_PREPARATION,
        isExample = false,
        estimatedMinutes = 10,
        dimensions = listOf(
            Dimension(
                "Perfektionist & Antreiber",
                placeholderBands("Perfektionist & Antreiber").toMutableList().also {
                    it[1] = ResultBand(
                        34, 66,
                        "Dein innerer Perfektionist ist spürbar aktiv",
                        "Du stellst hohe Ansprüche an dich und möchtest Dinge gerne richtig und gut machen. " +
                            "Das kann eine große Stärke sein. Du bist vermutlich gewissenhaft, engagiert und " +
                            "übernimmst Verantwortung. Gleichzeitig scheint dein innerer Antreiber sich immer " +
                            "wieder einzumischen, besonders dann, wenn dir etwas wichtig ist oder du unter " +
                            "Druck stehst.\n\n" +
                            "Vielleicht fällt es dir dann schwer, mit deiner eigenen Leistung wirklich zufrieden " +
                            "zu sein. Aus „Ich möchte das gut machen“ wird schnell ein „Ich muss das richtig " +
                            "machen“. Fehler oder unerledigte Dinge beschäftigen dich dann möglicherweise länger, " +
                            "als dir eigentlich guttut.\n" +
                            "Es geht nicht darum, deine Ansprüche aufzugeben. Achte vielmehr darauf, wann dein " +
                            "Ehrgeiz dich unterstützt und wann er beginnt, Druck zu erzeugen. Du darfst etwas " +
                            "gut genug sein lassen.",
                        permission = "„Ich darf gut genug sein, ohne perfekt zu sein.“",
                        reflection = "„Wann helfen mir meine hohen Ansprüche wirklich und wann setze ich mich " +
                            "damit selbst unter Druck?“",
                    )
                },
            ),
            Dimension("People Pleaser & Anpasser", placeholderBands("People Pleaser & Anpasser")),
            Dimension("Katastrophenplaner & Kontrolleur", placeholderBands("Katastrophenplaner & Kontrolleur")),
            Dimension("Abwerter & Zerstörer", placeholderBands("Abwerter & Zerstörer")),
        ),
        closingTitle = "Möchtest du genauer hinschauen?",
        closingText = "[Platzhalter: Abschlusstext]",
        resultNote = "Dieser Test lädt dich zur Selbstreflexion ein. Er ist keine Diagnose und ersetzt " +
            "keine Beratung, Psychotherapie oder ärztliche Behandlung.",
    )
    val peoplePleasing = inPreparation(
        "people-pleasing", "People Pleasing",
        "Wie sehr richtest du dich nach den Erwartungen anderer?",
    )
    val selfWorth = inPreparation(
        "selbstwert", "Selbstwert",
        "Wie begegnest du dir selbst – unabhängig von Leistung?",
    )
    val boundaries = inPreparation(
        "grenzen", "Grenzen",
        "Wie gut spürst und schützt du deine Grenzen?",
    )

    val all: List<Questionnaire> = listOf(example, innerCritic, peoplePleasing, selfWorth, boundaries)

    fun byId(id: String): Questionnaire? = all.firstOrNull { it.id == id }
}
