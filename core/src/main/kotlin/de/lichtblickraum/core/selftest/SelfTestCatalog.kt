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

    /** Skala des Tests „Innerer Kritiker“: 1 = stimmt gar nicht … 5 = stimmt total. */
    val fivePointScale: List<AnswerOption> = listOf(
        AnswerOption("1 · Stimmt gar nicht", 1),
        AnswerOption("2 · Stimmt eher nicht", 2),
        AnswerOption("3 · Teils, teils", 3),
        AnswerOption("4 · Stimmt eher", 4),
        AnswerOption("5 · Stimmt total", 5),
    )

    private const val PERFECT = "Perfektionist & Antreiber"
    private const val PLEASER = "People Pleaser & Anpasser"
    private const val CONTROL = "Katastrophenplaner & Kontrolleur"
    private const val DEVALUE = "Abwerter & Zerstörer"

    /**
     * „Wie stark ist dein innerer Kritiker?“ – eigene Fassung für die App.
     * 40 Aussagen, 10 je Bereich (davon je 1 umgekehrt gewertet).
     * Werte je Bereich: Summe der 10 Antworten (10–50), umgerechnet auf 0–100.
     * Reflexionsangebot, kein wissenschaftlich geprüftes Verfahren.
     */
    val innerCritic = Questionnaire(
        id = "innerer-kritiker",
        title = "Wie stark ist dein innerer Kritiker?",
        shortDescription = "Lerne vier typische Seiten deines inneren Kritikers kennen.",
        intro = "Fast jeder Mensch kennt eine innere Stimme, die antreibt, warnt oder kritisiert. " +
            "Dieser Test lädt dich ein, vier typische Seiten deines inneren Kritikers kennenzulernen " +
            "– und zu sehen, welche gerade besonders aktiv sind.\n\n" +
            "Lies jede Aussage und wähle, wie sehr sie in letzter Zeit auf dich zutrifft: von " +
            "1 (stimmt gar nicht) bis 5 (stimmt total). Es gibt kein Richtig oder Falsch.",
        status = QuestionnaireStatus.AVAILABLE,
        isExample = false,
        estimatedMinutes = 8,
        options = fivePointScale,
        questions = listOf(
            Question("k01", "Ich habe das Gefühl, etwas erst dann abschließen zu dürfen, wenn es wirklich perfekt ist.", dimension = PERFECT),
            Question("k02", "Es fällt mir schwer, Nein zu sagen – auch wenn ich eigentlich keine Kraft mehr habe.", dimension = PLEASER),
            Question("k03", "Ich gehe gedanklich oft durch, was alles schiefgehen könnte.", dimension = CONTROL),
            Question("k04", "Wenn etwas nicht klappt, denke ich schnell: „Typisch ich.“", dimension = DEVALUE),
            Question("k05", "Auch wenn ich viel geschafft habe, sehe ich vor allem das, was noch fehlt.", dimension = PERFECT),
            Question("k06", "Ich mache mir viele Gedanken darüber, ob andere mit mir zufrieden sind.", dimension = PLEASER),
            Question("k07", "Ich werde unruhig, wenn ich nicht alles im Blick habe.", dimension = CONTROL),
            Question("k08", "Innerlich spreche ich manchmal hart und abwertend mit mir.", dimension = DEVALUE),
            Question("k09", "Eine Pause gönne ich mir erst, wenn alles erledigt ist.", dimension = PERFECT),
            Question("k10", "Bei Konflikten gebe ich lieber nach, damit wieder Ruhe ist.", dimension = PLEASER),
            Question("k11", "Ich bereite mich auf viele Situationen sehr gründlich vor, um bloß nichts falsch zu machen.", dimension = CONTROL),
            Question("k12", "Lob kann ich schwer annehmen, weil ich glaube, es nicht wirklich verdient zu haben.", dimension = DEVALUE),
            Question("k13", "Kleine Fehler beschäftigen mich oft noch lange.", dimension = PERFECT),
            Question("k14", "Meine eigenen Wünsche stelle ich oft zurück.", dimension = PLEASER),
            Question("k15", "Unerwartete Veränderungen bringen mich schnell aus dem Gleichgewicht.", dimension = CONTROL),
            Question("k16", "Ich vergleiche mich oft mit anderen und schneide dabei schlecht ab.", dimension = DEVALUE),
            Question("k17", "Ich treibe mich innerlich mit Sätzen an wie „Streng dich mehr an“.", dimension = PERFECT),
            Question("k18", "Wenn jemand verstimmt ist, frage ich mich zuerst, was ich falsch gemacht habe.", dimension = PLEASER),
            Question("k19", "Ich gebe Aufgaben ungern ab, weil ich sicher sein möchte, dass sie richtig gemacht werden.", dimension = CONTROL),
            Question("k20", "Manchmal zweifle ich daran, ob ich so, wie ich bin, gut genug bin.", dimension = DEVALUE),
            Question("k21", "Mit „gut genug“ bin ich selten zufrieden.", dimension = PERFECT),
            Question("k22", "Ich passe mich lieber an, als anzuecken.", dimension = PLEASER),
            Question("k23", "Ich mache mir Sorgen, auch wenn es eigentlich keinen konkreten Anlass gibt.", dimension = CONTROL),
            Question("k24", "Nach einem Fehler fällt es mir schwer, freundlich mit mir zu sein.", dimension = DEVALUE),
            Question("k25", "Bevor ich etwas abgebe, überarbeite ich es oft noch mehrmals.", dimension = PERFECT),
            Question("k26", "Ich entschuldige mich oft, auch wenn ich nichts falsch gemacht habe.", dimension = PLEASER),
            Question("k27", "Wenn ich länger nichts von jemandem höre, male ich mir schnell aus, dass etwas passiert sein könnte.", dimension = CONTROL),
            Question("k28", "Ich denke oft, dass andere mehr können als ich.", dimension = DEVALUE),
            Question("k29", "Ich habe das Gefühl, mir Erholung erst verdienen zu müssen.", dimension = PERFECT),
            Question("k30", "Ich spüre schnell, was andere brauchen – meine eigenen Bedürfnisse bemerke ich oft erst später.", dimension = PLEASER),
            Question("k31", "Spontane Planänderungen fallen mir schwer.", dimension = CONTROL),
            Question("k32", "Erfolge schreibe ich eher dem Zufall oder Glück zu als mir selbst.", dimension = DEVALUE),
            Question("k33", "Selbst kleine Aufgaben möchte ich besonders gut machen.", dimension = PERFECT),
            Question("k34", "Ich übernehme Aufgaben, um andere nicht zu enttäuschen.", dimension = PLEASER),
            Question("k35", "Ich überprüfe Dinge lieber mehrfach, um ganz sicher zu sein.", dimension = CONTROL),
            Question("k36", "Wenn ich an mich denke, fallen mir zuerst meine Schwächen ein.", dimension = DEVALUE),
            Question("k37", "Ich kann mit meiner Arbeit zufrieden sein, auch wenn sie nicht perfekt ist.", reversed = true, dimension = PERFECT),
            Question("k38", "Ich sage meine Meinung, auch wenn andere sie nicht teilen.", reversed = true, dimension = PLEASER),
            Question("k39", "Ich kann gut damit leben, nicht zu wissen, wie etwas ausgeht.", reversed = true, dimension = CONTROL),
            Question("k40", "Ich kann gut annehmen, dass ich Stärken und Schwächen habe.", reversed = true, dimension = DEVALUE),
        ),
        dimensions = listOf(
            Dimension(
                PERFECT,
                listOf(
                    ResultBand(
                        0, 33, "Dein innerer Antreiber ist eher leise",
                        "Deine Antworten deuten an, dass du dir im Moment oft erlaubst, Dinge auch einmal gut " +
                            "genug sein zu lassen. Ansprüche dürfen da sein, ohne dich zu treiben. Vielleicht kennst " +
                            "du trotzdem Situationen, in denen er lauter wird – zum Beispiel unter Zeitdruck.",
                        permission = "„Ich darf Dinge in meinem Tempo tun.“",
                        reflection = "„Was hilft mir, gelassen zu bleiben, wenn viel von mir erwartet wird?“",
                    ),
                    ResultBand(
                        34, 66, "Dein innerer Antreiber ist spürbar aktiv",
                        "Dir ist wichtig, Dinge gut zu machen – das ist eine echte Stärke. Gleichzeitig meldet " +
                            "sich dein Antreiber immer wieder, besonders wenn dir etwas am Herzen liegt. Dann wird aus " +
                            "„Ich möchte“ schnell ein „Ich muss“.\n\n" +
                            "Es geht nicht darum, deine Ansprüche aufzugeben. Spannend ist, zu bemerken, wann sie " +
                            "dich tragen und wann sie Druck machen.",
                        permission = "„Gut genug ist manchmal genau richtig.“",
                        reflection = "„Woran merke ich, dass aus meinem Anspruch Druck wird?“",
                    ),
                    ResultBand(
                        67, 100, "Dein innerer Antreiber ist gerade sehr präsent",
                        "Deine Antworten deuten an, dass du dich im Moment oft antreibst und es dir schwerfällt, " +
                            "mit dir zufrieden zu sein. Das kann sehr erschöpfend sein.\n\n" +
                            "Vielleicht hat dieser Anteil dir früher geholfen, Anerkennung oder Sicherheit zu " +
                            "bekommen. Heute darf er auch einmal Pause machen. Kleine Schritte – etwa bewusst etwas " +
                            "„gut genug“ abzuschließen – können entlasten.",
                        permission = "„Ich bin wertvoll, auch wenn ich nicht alles schaffe.“",
                        reflection = "„Was würde passieren, wenn ich heute eine Sache nur zu 80 Prozent mache?“",
                    ),
                ),
            ),
            Dimension(
                PLEASER,
                listOf(
                    ResultBand(
                        0, 33, "Du achtest gut auf dich und auf andere",
                        "Deine Antworten deuten an, dass du dich im Moment gut abgrenzen kannst und deine eigenen " +
                            "Wünsche nicht aus dem Blick verlierst. Rücksicht auf andere und Rücksicht auf dich " +
                            "dürfen nebeneinanderstehen.",
                        permission = "„Meine Bedürfnisse zählen genauso.“",
                        reflection = "„In welchen Beziehungen fällt es mir besonders leicht, ich selbst zu sein?“",
                    ),
                    ResultBand(
                        34, 66, "Dein innerer Anpasser meldet sich immer wieder",
                        "Harmonie und gute Beziehungen sind dir wichtig. Manchmal stellst du dafür deine eigenen " +
                            "Wünsche zurück oder sagst Ja, obwohl du Nein meinst.\n\n" +
                            "Das ist verständlich – und es lohnt sich, freundlich hinzuschauen, wann es dich Kraft kostet.",
                        permission = "„Ich darf Nein sagen und trotzdem gemocht werden.“",
                        reflection = "„Wo habe ich zuletzt Ja gesagt, obwohl ich Nein gemeint habe?“",
                    ),
                    ResultBand(
                        67, 100, "Dein innerer Anpasser ist gerade sehr stark",
                        "Deine Antworten deuten an, dass du dich im Moment stark nach den Erwartungen anderer " +
                            "richtest und deine eigenen Bedürfnisse oft hintanstellst. Dahinter steckt häufig der " +
                            "Wunsch nach Zugehörigkeit und Frieden.\n\n" +
                            "Du darfst Schritt für Schritt erfahren, dass Beziehungen auch dann halten, wenn du " +
                            "deine Grenzen zeigst.",
                        permission = "„Ich muss mir Zuneigung nicht verdienen.“",
                        reflection = "„Was würde ich mir wünschen, wenn niemand enttäuscht sein könnte?“",
                    ),
                ),
            ),
            Dimension(
                CONTROL,
                listOf(
                    ResultBand(
                        0, 33, "Du vertraust darauf, dass sich vieles fügt",
                        "Deine Antworten deuten an, dass du im Moment mit Unsicherheit recht gelassen umgehen " +
                            "kannst. Du darfst planen, ohne alles kontrollieren zu müssen.",
                        permission = "„Ich kann mit dem umgehen, was kommt.“",
                        reflection = "„Was hilft mir, ruhig zu bleiben, wenn etwas anders läuft als geplant?“",
                    ),
                    ResultBand(
                        34, 66, "Dein innerer Kontrolleur ist wachsam",
                        "Du möchtest gut vorbereitet sein und Unangenehmes vermeiden. Das gibt dir Sicherheit. " +
                            "Manchmal kreisen deine Gedanken aber um Dinge, die vielleicht nie eintreten.\n\n" +
                            "Dann darfst du dich freundlich daran erinnern, was gerade wirklich da ist.",
                        permission = "„Ich muss nicht alles im Griff haben.“",
                        reflection = "„Welche Sorge darf ich heute einmal beiseitelegen?“",
                    ),
                    ResultBand(
                        67, 100, "Dein innerer Kontrolleur arbeitet gerade auf Hochtouren",
                        "Deine Antworten deuten an, dass dich Sorgen und das Bedürfnis nach Kontrolle im Moment " +
                            "stark beschäftigen. Das ist anstrengend – und zeigt, wie sehr du dich nach Sicherheit " +
                            "sehnst.\n\n" +
                            "Übungen, die dich ins Hier und Jetzt holen, können eine kleine Entlastung sein. Und es " +
                            "ist in Ordnung, dir dabei Unterstützung zu holen.",
                        permission = "„Ich darf vertrauen, auch wenn ich nicht alles weiß.“",
                        reflection = "„Was liegt wirklich in meiner Hand – und was darf ich loslassen?“",
                    ),
                ),
            ),
            Dimension(
                DEVALUE,
                listOf(
                    ResultBand(
                        0, 33, "Du begegnest dir meist mit Verständnis",
                        "Deine Antworten deuten an, dass du im Moment eher freundlich mit dir sprichst – auch " +
                            "wenn etwas schiefgeht. Das ist eine wertvolle Grundlage.",
                        permission = "„Ich darf mich mögen, so wie ich bin.“",
                        reflection = "„Was hilft mir, auch an schweren Tagen freundlich mit mir zu bleiben?“",
                    ),
                    ResultBand(
                        34, 66, "Dein innerer Abwerter meldet sich ab und zu",
                        "In schwierigen Momenten wird deine innere Stimme manchmal hart. Dann zweifelst du an dir " +
                            "oder vergleichst dich mit anderen. Das kennen viele Menschen.\n\n" +
                            "Du darfst diese Stimme bemerken, ohne ihr alles zu glauben.",
                        permission = "„Ein Fehler macht mich nicht weniger wert.“",
                        reflection = "„Wie würde ich mit einem lieben Menschen in dieser Situation sprechen?“",
                    ),
                    ResultBand(
                        67, 100, "Dein innerer Abwerter ist gerade sehr laut",
                        "Deine Antworten deuten an, dass du im Moment oft hart und abwertend mit dir sprichst. " +
                            "Das kann sehr wehtun.\n\n" +
                            "Diese Stimme ist nicht die Wahrheit über dich. Oft ist sie ein altes Muster, das dich " +
                            "einmal schützen wollte. Du verdienst eine freundlichere Begleitung – und es kann " +
                            "guttun, dir dafür Unterstützung zu holen.",
                        permission = "„Ich bin gut genug, so wie ich bin.“",
                        reflection = "„Welche freundlichere Stimme möchte ich mir an die Seite stellen?“",
                    ),
                ),
            ),
        ),
        closingTitle = "Möchtest du genauer hinschauen?",
        closingText = "Dein innerer Kritiker hat oft eine lange Geschichte. In einer persönlichen Beratung " +
            "können wir gemeinsam verstehen, wofür er einmal gut war – und wie du ihm freundlicher begegnen kannst.",
        resultNote = "Dieser Test ist eine Einladung zur Selbstreflexion. Er ist kein wissenschaftlich " +
            "geprüftes Verfahren, keine Diagnose und ersetzt keine Beratung, Psychotherapie oder ärztliche Behandlung.",
    )

    val selfRelationship = inPreparation(
        "beziehung-zu-dir", "Wie ist deine Beziehung zu dir selbst?",
        "Wie begegnest du dir selbst, wie stabil ist dein Selbstwert, und wie gut stehst du für dich ein?",
    )
    val emotionalDependency = inPreparation(
        "emotionale-abhaengigkeit", "Wie emotional abhängig bin ich?",
        "Wie sehr hängt dein inneres Gleichgewicht von anderen Menschen ab?",
    )
    val loveLanguage = inPreparation(
        "liebessprache", "Was ist meine Liebessprache?",
        "Auf welche Weise zeigst du Zuneigung – und wie möchtest du sie selbst erleben?",
    )

    /** Sichtbar in der App. Der Beispiel-Fragebogen [example] bleibt nur für Tests im Code. */
    val all: List<Questionnaire> = listOf(innerCritic, selfRelationship, emotionalDependency, loveLanguage)

    fun byId(id: String): Questionnaire? = all.firstOrNull { it.id == id }
}
