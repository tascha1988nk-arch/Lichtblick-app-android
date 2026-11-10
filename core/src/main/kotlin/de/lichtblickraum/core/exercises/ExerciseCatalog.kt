package de.lichtblickraum.core.exercises

/**
 * Alle Übungen, nach Kategorien geordnet. Neue Übung ergänzen: weiteres Exercise(...) mit
 * passender category anlegen und in [all] eintragen. Neue Kategorie: in ExerciseCategory ergänzen.
 * Alle Übungen sind freiwillig und können in der App jederzeit beendet werden.
 */
object ExerciseCatalog {

    /** Wird vor jeder Übung angezeigt. */
    const val GENERAL_NOTE: String =
        "Alle Übungen sind freiwillig. Du kannst jederzeit pausieren oder aufhören – " +
            "auch mittendrin. Wenn sich etwas unangenehm anfühlt, darfst du gut auf dich achten " +
            "und die Übung beenden."

    val orientation = Exercise(
        id = "orientierung",
        minutes = 3,
        category = ExerciseCategory.EMOTIONS,
        title = "Ankommen im Hier und Jetzt",
        subtitle = "Eine Orientierungsübung für stressige Momente",
        durationText = "ca. 3 Minuten",
        intro = "Wenn vieles gleichzeitig auf dich einströmt, kann es helfen, die Aufmerksamkeit " +
            "behutsam nach außen zu lenken: auf den Raum, in dem du gerade bist. Du kannst die " +
            "Übung im Sitzen, Stehen oder Gehen machen. Die Augen dürfen dabei offen bleiben.",
        steps = listOf(
            ExerciseStep(
                "Boden unter dir",
                "Spüre, wo dein Körper gerade Kontakt hat: die Füße auf dem Boden, " +
                    "vielleicht der Rücken an der Lehne. Du musst nichts verändern – nur bemerken.",
            ),
            ExerciseStep(
                "Schauen",
                "Lass deinen Blick langsam durch den Raum wandern. Benenne innerlich ein paar " +
                    "Dinge, die du siehst – zum Beispiel ihre Farbe oder Form. " +
                    "„Da ist ein grüner Becher.“ „Da ist ein Fenster.“",
            ),
            ExerciseStep(
                "Hören",
                "Lausche für einen Moment. Welche Geräusche sind gerade da – nah oder weit weg? " +
                    "Auch Stille ist in Ordnung.",
            ),
            ExerciseStep(
                "Atmen",
                "Wenn es sich für dich stimmig anfühlt, lass den nächsten Ausatem etwas länger " +
                    "werden als den Einatem. Ganz ohne Anstrengung. Wenn dir das gerade nicht " +
                    "guttut, lass deinen Atem einfach so, wie er ist.",
            ),
            ExerciseStep(
                "Hier bin ich",
                "Sag dir innerlich, wo du gerade bist und welcher Tag heute ist. " +
                    "Zum Beispiel: „Ich bin in meiner Küche. Heute ist Dienstag.“",
                inputPrompt = "Möchtest du etwas notieren, das dir gerade auffällt? (freiwillig)",
            ),
        ),
        closing = "Du hast dir einen Moment für dich genommen. Vielleicht hat sich etwas " +
            "verändert, vielleicht auch nicht – beides ist in Ordnung.",
    )

    val boundaries = Exercise(
        id = "grenzen-reflexion",
        minutes = 8,
        category = ExerciseCategory.BOUNDARIES,
        title = "Meine Grenzen wahrnehmen",
        subtitle = "Eine Reflexion zu persönlichen Grenzen",
        durationText = "ca. 5–10 Minuten",
        intro = "Grenzen zeigen uns, was uns wichtig ist. In dieser Reflexion schaust du auf eine " +
            "kleine, alltägliche Situation. Bitte wähle bewusst etwas Leichtes – nichts, was dich " +
            "stark belastet. Alle Textfelder sind freiwillig; du kannst auch nur innerlich " +
            "nachspüren.",
        steps = listOf(
            ExerciseStep(
                "Eine kleine Situation",
                "Denk an einen kurzen Moment aus den letzten Tagen, in dem sich etwas für dich " +
                    "nicht ganz stimmig angefühlt hat. Etwa eine Bitte, zu der du Ja gesagt hast, " +
                    "obwohl du lieber Nein gesagt hättest.",
                inputPrompt = "Was war das für eine Situation? (Stichworte genügen)",
            ),
            ExerciseStep(
                "Was hast du bemerkt?",
                "Vielleicht hast du damals etwas im Körper gespürt – ein Ziehen, Enge, Unruhe – " +
                    "oder einen Gedanken gehabt. Es ist auch in Ordnung, wenn dir nichts einfällt.",
                inputPrompt = "Was hast du gespürt oder gedacht?",
            ),
            ExerciseStep(
                "Was war dir wichtig?",
                "Hinter einem Unbehagen steckt oft ein Bedürfnis: zum Beispiel Ruhe, Zeit, " +
                    "Respekt oder Selbstbestimmung. Was könnte es bei dir gewesen sein?",
                inputPrompt = "Was wäre dir in dem Moment wichtig gewesen?",
            ),
            ExerciseStep(
                "Ein Satz für dich",
                "Wenn eine ähnliche Situation wieder auftaucht: Gibt es einen kurzen Satz, der " +
                    "dir helfen könnte? Zum Beispiel: „Ich überlege es mir und sage dir morgen " +
                    "Bescheid.“ Du musst ihn nicht verwenden – es geht nur ums Ausprobieren.",
                inputPrompt = "Dein möglicher Satz",
            ),
        ),
        closing = "Grenzen zu spüren ist ein erster Schritt. Sie auszusprechen darf Zeit brauchen. " +
            "Danke, dass du hingeschaut hast.",
    )

    val innerDialogue = Exercise(
        id = "freundlicher-dialog",
        minutes = 5,
        category = ExerciseCategory.SELF_WORTH,
        title = "Ein freundlicherer innerer Dialog",
        subtitle = "Eine Übung für einen wohlwollenderen Umgang mit dir",
        durationText = "ca. 5 Minuten",
        intro = "Viele Menschen sprechen innerlich strenger mit sich, als sie es mit anderen tun " +
            "würden. In dieser Übung probierst du aus, wie ein etwas freundlicherer Ton klingen " +
            "könnte. Wähle dafür einen kleinen, alltäglichen Gedanken.",
        steps = listOf(
            ExerciseStep(
                "Den Gedanken bemerken",
                "Gibt es einen kritischen Gedanken, der dir heute oder in letzter Zeit öfter " +
                    "begegnet ist? Zum Beispiel: „Das hätte ich besser machen müssen.“",
                inputPrompt = "Der Gedanke in deinen Worten (freiwillig)",
            ),
            ExerciseStep(
                "Mit Abstand betrachten",
                "Stell dir vor, ein Mensch, den du gernhast, würde so über sich sprechen. " +
                    "Was würdest du ihm antworten?",
                inputPrompt = "Was würdest du diesem Menschen sagen?",
            ),
            ExerciseStep(
                "Für dich übersetzen",
                "Formuliere nun einen Satz für dich, der freundlich ist und sich trotzdem ehrlich " +
                    "anfühlt. Er muss nicht überschwänglich sein. Manchmal reicht schon: " +
                    "„Ich habe getan, was ich gerade konnte.“",
                inputPrompt = "Dein freundlicherer Satz",
            ),
            ExerciseStep(
                "Nachspüren",
                "Lies dir den Satz in Ruhe durch oder sag ihn leise. Wie fühlt es sich an? " +
                    "Ungewohnt ist völlig in Ordnung. Freundlichkeit mit sich selbst ist etwas, " +
                    "das man üben kann.",
            ),
        ),
        closing = "Du hast einem anderen Ton in dir Raum gegeben. Er darf leise sein und " +
            "mit der Zeit vertrauter werden.",
    )

    // ── Selbstwert ───────────────────────────────────────────────────────────────

    val strengths = Exercise(
        id = "staerken-entdecken",
        minutes = 8,
        category = ExerciseCategory.SELF_WORTH,
        title = "Meine Stärken entdecken",
        subtitle = "Freundlich auf das schauen, was dich ausmacht",
        durationText = "ca. 5–10 Minuten",
        intro = "Stärken zeigen sich oft in ganz gewöhnlichen Momenten – so selbstverständlich, " +
            "dass wir sie übersehen. In dieser Übung sammelst du ein paar davon. Es gibt kein " +
            "Richtig oder Falsch, und alles darf klein sein.",
        steps = listOf(
            ExerciseStep(
                "Ein Moment, der gut lief",
                "Denk an eine Situation aus der letzten Zeit, die gut gelaufen ist – vielleicht " +
                    "ein Gespräch, eine Aufgabe oder ein Moment, in dem du jemandem geholfen hast.",
                inputPrompt = "Was war das für ein Moment?",
            ),
            ExerciseStep(
                "Was hat dir geholfen?",
                "Welche Eigenschaft von dir hat dazu beigetragen? Zum Beispiel Geduld, Humor, " +
                    "Hartnäckigkeit, Wärme oder Kreativität.",
                inputPrompt = "Deine Eigenschaft",
            ),
            ExerciseStep(
                "Mit freundlichen Augen",
                "Wie würde dich ein Mensch beschreiben, der dich gernhat? Welche drei Worte " +
                    "würde er oder sie vielleicht wählen?",
                inputPrompt = "Drei Worte",
            ),
            ExerciseStep(
                "Nachklingen lassen",
                "Lies dir deine Antworten in Ruhe durch. Vielleicht fühlt es sich ungewohnt an, " +
                    "so über dich zu denken. Das ist in Ordnung. Du musst nichts davon glauben – " +
                    "es reicht, es einmal stehen zu lassen.",
            ),
        ),
        closing = "Deine Stärken waren schon vorher da. Du hast sie heute nur ein wenig sichtbarer gemacht.",
    )

    // ── Grenzen ──────────────────────────────────────────────────────────────────

    val yesAndNo = Exercise(
        id = "ja-und-nein",
        minutes = 5,
        category = ExerciseCategory.BOUNDARIES,
        title = "Mein Ja und mein Nein spüren",
        subtitle = "Eine kleine Körperübung zu Zustimmung und Ablehnung",
        durationText = "ca. 5 Minuten",
        intro = "Bevor wir eine Grenze aussprechen, spüren wir sie oft schon im Körper. In dieser " +
            "Übung lernst du dein inneres Ja und Nein ein wenig besser kennen – mit ganz " +
            "alltäglichen, leichten Beispielen.",
        steps = listOf(
            ExerciseStep(
                "Ein leichtes Ja",
                "Denk an etwas Angenehmes, zu dem du gern Ja sagst – zum Beispiel eine Tasse Tee " +
                    "oder einen Spaziergang bei schönem Wetter. Sag innerlich „Ja“. Was bemerkst " +
                    "du im Körper? Vielleicht Weite, Wärme oder ein leichtes Lächeln.",
                inputPrompt = "Wie fühlt sich dein Ja an?",
            ),
            ExerciseStep(
                "Ein leichtes Nein",
                "Denk an etwas Kleines, das du gerade nicht möchtest – zum Beispiel ein " +
                    "Lebensmittel, das du nicht magst. Sag innerlich „Nein“. Was bemerkst du jetzt?",
                inputPrompt = "Wie fühlt sich dein Nein an?",
            ),
            ExerciseStep(
                "Den Unterschied bemerken",
                "Vergleiche die beiden Empfindungen. Es gibt keine richtigen Antworten – manche " +
                    "spüren viel, andere wenig. Beides ist in Ordnung.",
            ),
            ExerciseStep(
                "Für den Alltag",
                "Wenn du in den nächsten Tagen um etwas gebeten wirst, kannst du kurz nachspüren: " +
                    "Fühlt es sich eher nach meinem Ja oder nach meinem Nein an? Du musst nichts " +
                    "anders machen – nur bemerken.",
            ),
        ),
        closing = "Dein Körper ist ein guter Verbündeter, wenn es um Grenzen geht. Danke, dass du hingespürt hast.",
    )

    // ── Paarthemen ───────────────────────────────────────────────────────────────

    val appreciation = Exercise(
        id = "was-ich-an-uns-schaetze",
        minutes = 8,
        category = ExerciseCategory.COUPLE,
        title = "Was ich an uns schätze",
        subtitle = "Den Blick auf das Verbindende richten",
        durationText = "ca. 5–10 Minuten",
        intro = "Im Alltag rückt oft das in den Vordergrund, was nicht klappt. Diese Übung lädt " +
            "dich ein, für einen Moment auf das zu schauen, was euch verbindet. Wenn dir gerade " +
            "nichts einfällt, ist das auch in Ordnung – du kannst die Übung jederzeit beenden.",
        steps = listOf(
            ExerciseStep(
                "Ein schöner Moment",
                "Erinnere dich an einen Moment mit deinem Partner oder deiner Partnerin, der sich " +
                    "gut angefühlt hat. Er darf klein sein – ein gemeinsames Lachen, ein Blick, " +
                    "ein ruhiger Abend.",
                inputPrompt = "Dein Moment",
            ),
            ExerciseStep(
                "Was du schätzt",
                "Was schätzt du an deinem Gegenüber? Vielleicht eine Eigenschaft, eine Geste oder " +
                    "etwas, das er oder sie für dich tut.",
                inputPrompt = "Was du schätzt",
            ),
            ExerciseStep(
                "Was du einbringst",
                "Und was bringst du in eure Beziehung ein? Auch das darf gesehen werden.",
                inputPrompt = "Was du einbringst",
            ),
            ExerciseStep(
                "Wenn du magst",
                "Falls es sich stimmig anfühlt, kannst du in den nächsten Tagen eine kleine " +
                    "Wertschätzung aussprechen. Das ist eine Einladung, keine Aufgabe.",
            ),
        ),
        closing = "Wertschätzung ist wie ein kleines Licht in einer Beziehung. Schön, dass du es heute angezündet hast.",
    )

    val iMessage = Exercise(
        id = "wunsch-in-ich-botschaften",
        minutes = 10,
        category = ExerciseCategory.COUPLE,
        title = "Einen Wunsch in Ich-Botschaften",
        subtitle = "Ein Anliegen so formulieren, dass es gehört werden kann",
        durationText = "ca. 10 Minuten",
        intro = "Wünsche kommen leichter an, wenn wir von uns sprechen statt über den anderen. In " +
            "dieser Übung formulierst du einen kleinen, alltäglichen Wunsch als Ich-Botschaft. " +
            "Bitte wähle bewusst etwas Leichtes – kein großes Streitthema. Du musst den Satz " +
            "später nicht aussprechen.",
        steps = listOf(
            ExerciseStep(
                "Die Situation",
                "Beschreibe kurz und möglichst neutral eine kleine Situation, die du dir anders " +
                    "wünschst. Zum Beispiel: „Wenn wir abends beide am Handy sind …“",
                inputPrompt = "Die Situation in einem Satz",
            ),
            ExerciseStep(
                "Dein Gefühl",
                "Wie geht es dir in dieser Situation? Zum Beispiel: „… fühle ich mich ein bisschen " +
                    "einsam.“",
                inputPrompt = "Dein Gefühl",
            ),
            ExerciseStep(
                "Dein Bedürfnis",
                "Was wäre dir wichtig? Zum Beispiel Nähe, Aufmerksamkeit, Ruhe oder Unterstützung.",
                inputPrompt = "Dein Bedürfnis",
            ),
            ExerciseStep(
                "Dein Wunsch",
                "Formuliere nun eine konkrete, freundliche Bitte. Zum Beispiel: „Ich würde mich " +
                    "freuen, wenn wir uns eine halbe Stunde ohne Handy nehmen.“",
                inputPrompt = "Deine Bitte",
            ),
            ExerciseStep(
                "Zusammensetzen",
                "Lies die vier Teile hintereinander. So klingt eine Ich-Botschaft: Situation, " +
                    "Gefühl, Bedürfnis, Bitte. Ob und wann du sie aussprichst, entscheidest allein du.",
            ),
        ),
        closing = "Einen Wunsch klar zu formulieren, ist schon ein Schritt – für dich und für eure Verbindung.",
    )

    // ── ADHS im Alltag ───────────────────────────────────────────────────────────

    val oneThing = Exercise(
        id = "eine-sache-zur-zeit",
        minutes = 4,
        category = ExerciseCategory.ADHD,
        title = "Eine Sache zur Zeit",
        subtitle = "Den Anfang leichter machen",
        durationText = "ca. 3–5 Minuten",
        intro = "Wenn vieles gleichzeitig ruft, ist der Anfang oft das Schwerste. Diese Übung hilft " +
            "dir, eine einzige Sache auszuwählen und einen winzigen ersten Schritt zu finden.",
        steps = listOf(
            ExerciseStep(
                "Eine Sache auswählen",
                "Was beschäftigt dich gerade am meisten? Wähle nur eine Sache aus – nicht die " +
                    "wichtigste, sondern eine, die sich machbar anfühlt.",
                inputPrompt = "Deine eine Sache",
            ),
            ExerciseStep(
                "Der kleinste Schritt",
                "Was wäre der allerkleinste erste Schritt? So klein, dass er fast lächerlich " +
                    "wirkt – zum Beispiel „Das Dokument öffnen“ oder „Die Socken vom Boden aufheben“.",
                inputPrompt = "Dein kleinster Schritt",
            ),
            ExerciseStep(
                "Ein kurzes Zeitfenster",
                "Nimm dir für diesen Schritt ein kurzes Zeitfenster vor, etwa fünf Minuten. " +
                    "Danach darfst du aufhören – oder weitermachen, wenn du in Schwung kommst.",
            ),
            ExerciseStep(
                "Freundlich mit Ablenkung",
                "Wenn deine Gedanken abschweifen, ist das kein Versagen. Bemerke es, und kehre " +
                    "freundlich zu deinem Schritt zurück – so oft wie nötig.",
            ),
        ),
        closing = "Ein Anfang ist ein Anfang, egal wie klein. Danke, dass du dir diesen Moment genommen hast.",
    )

    val stimulusBreak = Exercise(
        id = "reizpause",
        minutes = 3,
        category = ExerciseCategory.ADHD,
        title = "Eine Reizpause",
        subtitle = "Wenn alles zu laut, zu viel, zu schnell ist",
        durationText = "ca. 3 Minuten",
        intro = "Manchmal ist der Kopf so voll, dass nichts mehr hineinpasst. Eine kurze Reizpause " +
            "kann helfen, ein wenig Ruhe zurückzugewinnen.",
        steps = listOf(
            ExerciseStep(
                "Bemerken",
                "Sag dir innerlich: „Gerade ist es sehr viel.“ Das Bemerken allein ist schon ein " +
                    "erster Schritt.",
            ),
            ExerciseStep(
                "Einen Reiz weniger",
                "Was kannst du jetzt verringern? Zum Beispiel Musik leiser stellen, ein paar " +
                    "Fenster am Bildschirm schließen, Benachrichtigungen stummschalten oder kurz " +
                    "den Raum wechseln.",
            ),
            ExerciseStep(
                "Ein Punkt für die Augen",
                "Such dir einen ruhigen Punkt, auf dem dein Blick ein paar Atemzüge lang " +
                    "verweilen darf – eine Wand, ein Baum vor dem Fenster, deine Hände.",
            ),
            ExerciseStep(
                "Ein freundlicher Satz",
                "Sag dir zum Beispiel: „Mein Kopf ist gerade sehr voll. Das ist verständlich, und " +
                    "es geht vorbei.“",
                inputPrompt = "Dein Satz (freiwillig)",
            ),
        ),
        closing = "Du hast dir eine kleine Insel geschaffen. Du kannst jederzeit dorthin zurückkehren.",
    )

    val whatWorked = Exercise(
        id = "was-heute-geklappt-hat",
        minutes = 5,
        category = ExerciseCategory.ADHD,
        title = "Was heute geklappt hat",
        subtitle = "Ein freundlicher Blick zurück auf den Tag",
        durationText = "ca. 5 Minuten",
        intro = "Mit ADHS bleiben am Ende eines Tages oft die Dinge hängen, die nicht geklappt haben. " +
            "Diese Übung lädt dich ein, bewusst auch das Gelungene zu sammeln – und was dir dabei " +
            "geholfen hat.",
        steps = listOf(
            ExerciseStep(
                "Etwas, das geklappt hat",
                "Was hat heute funktioniert? Es darf ganz klein sein – zum Beispiel pünktlich " +
                    "losgekommen, an etwas gedacht oder eine Pause gemacht.",
                inputPrompt = "Was hat geklappt?",
            ),
            ExerciseStep(
                "Was hat geholfen?",
                "Was hat dazu beigetragen? Vielleicht eine Erinnerung, eine Routine, ein Mensch " +
                    "oder einfach ein guter Moment.",
                inputPrompt = "Was hat dir geholfen?",
            ),
            ExerciseStep(
                "Mitnehmen",
                "Gibt es etwas davon, das du morgen wieder nutzen möchtest? Du musst dir nichts " +
                    "vornehmen – nur eine Idee notieren, wenn du magst.",
                inputPrompt = "Deine Idee für morgen (freiwillig)",
            ),
        ),
        closing = "Dein Tag bestand nicht nur aus Stolpersteinen. Schön, dass du auch das Gelungene gesehen hast.",
    )

    // ── Gefühle & Stress ─────────────────────────────────────────────────────────

    val nameFeeling = Exercise(
        id = "gefuehl-begleiten",
        minutes = 5,
        category = ExerciseCategory.EMOTIONS,
        title = "Ein Gefühl benennen und begleiten",
        subtitle = "Gefühlen mit Neugier statt mit Druck begegnen",
        durationText = "ca. 5 Minuten",
        intro = "Gefühle können manchmal sehr groß werden. Wenn wir sie benennen, entsteht oft ein " +
            "kleines bisschen Abstand. Wähle für diese Übung ein Gefühl, das gerade da ist und " +
            "sich aushaltbar anfühlt. Wenn es zu viel wird, darfst du jederzeit aufhören.",
        steps = listOf(
            ExerciseStep(
                "Im Körper nachspüren",
                "Wo spürst du gerade etwas im Körper? Vielleicht im Bauch, in der Brust, im Hals " +
                    "oder in den Schultern. Du musst nichts verändern.",
                inputPrompt = "Wo spürst du etwas?",
            ),
            ExerciseStep(
                "Einen Namen geben",
                "Welches Wort passt zu diesem Gefühl? Zum Beispiel traurig, ärgerlich, unruhig, " +
                    "erschöpft oder enttäuscht. Ein ungefähres Wort genügt.",
                inputPrompt = "Dein Wort",
            ),
            ExerciseStep(
                "Was braucht es?",
                "Wenn dieses Gefühl sprechen könnte – was würde es sich wünschen? Vielleicht Trost, " +
                    "Ruhe, Bewegung oder einfach, gesehen zu werden.",
                inputPrompt = "Was es sich wünscht",
            ),
            ExerciseStep(
                "Eine freundliche Geste",
                "Gibt es eine kleine Geste, die du dir jetzt schenken kannst? Ein Schluck Wasser, " +
                    "ein paar Schritte, eine Hand auf dem Herzen oder ein freundlicher Satz.",
            ),
        ),
        closing = "Du hast deinem Gefühl zugehört, statt es wegzuschieben. Das ist eine Form von Fürsorge.",
    )

    val all: List<Exercise> = listOf(
        innerDialogue, strengths,
        boundaries, yesAndNo,
        appreciation, iMessage,
        oneThing, stimulusBreak, whatWorked,
        orientation, nameFeeling,
    )

    val categories: List<ExerciseCategory> = ExerciseCategory.entries.filter { c -> all.any { it.category == c } }

    fun inCategory(category: ExerciseCategory): List<Exercise> = all.filter { it.category == category }

    fun byId(id: String): Exercise? = all.firstOrNull { it.id == id }
}
