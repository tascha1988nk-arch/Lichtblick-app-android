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

    // ── Ergänzung: zwei weitere Übungen je Thema ─────────────────────────────────

    val innererUnterstuetzer = Exercise(
        id = "innerer-unterstuetzer",
        minutes = 5,
        category = ExerciseCategory.SELF_WORTH,
        title = "Mein innerer Unterstützer",
        subtitle = "Eine freundliche Stimme in dir stärken",
        durationText = "ca. 5 Minuten",
        intro = "Neben der kritischen Stimme gibt es in uns auch eine wohlwollende. Manchmal ist sie leise. In dieser Übung gibst du ihr ein Gesicht und eine Stimme.",
        steps = listOf(
            ExerciseStep(
                "Eine wohlwollende Gestalt",
                "Denk an einen Menschen, ein Wesen oder eine Gestalt, die dir freundlich gesinnt ist – real oder vorgestellt. Zum Beispiel eine warmherzige Großmutter, ein guter Freund oder eine Figur aus einem Buch.",
                inputPrompt = "Wer begleitet dich in dieser Übung?",
            ),
            ExerciseStep(
                "Wie sie dich sieht",
                "Stell dir vor, diese Gestalt schaut dich gerade an – mit Wärme und ohne Urteil. Was sieht sie an dir, das du selbst manchmal übersiehst?",
                inputPrompt = "Was sieht sie an dir?",
            ),
            ExerciseStep(
                "Was sie dir sagt",
                "Was würde sie dir heute sagen, vielleicht zu etwas, das dich gerade beschäftigt?",
                inputPrompt = "Ihr Satz an dich",
            ),
            ExerciseStep(
                "Mitnehmen",
                "Du kannst diese Stimme jederzeit wieder einladen – besonders dann, wenn die kritische Stimme laut wird.",
            ),
        ),
        closing = "Die freundliche Stimme gehört auch zu dir. Je öfter du ihr zuhörst, desto vertrauter wird sie.",
    )

    val wertOhneLeistung = Exercise(
        id = "wert-ohne-leistung",
        minutes = 8,
        category = ExerciseCategory.SELF_WORTH,
        title = "Wert ohne Leistung",
        subtitle = "Entdecken, was dich jenseits von Erfolg ausmacht",
        durationText = "ca. 5–10 Minuten",
        intro = "Oft messen wir unseren Wert an dem, was wir schaffen. Diese Übung lädt dich ein, einmal auf das zu schauen, was bleibt, wenn alle Leistungen beiseitegelegt sind.",
        steps = listOf(
            ExerciseStep(
                "Leistungen beiseitelegen",
                "Stell dir vor, du legst für einen Moment alles ab, was du leistest: Arbeit, Aufgaben, Rollen. Was bleibt von dir übrig?",
            ),
            ExerciseStep(
                "Was dich ausmacht",
                "Welche Eigenschaften, Werte oder Eigenheiten gehören zu dir – ganz unabhängig davon, was du tust? Zum Beispiel Humor, Neugier oder deine Art zuzuhören.",
                inputPrompt = "Was macht dich aus?",
            ),
            ExerciseStep(
                "Gemocht, einfach so",
                "Erinnere dich an einen Moment, in dem dich jemand einfach gern bei sich hatte – ohne dass du etwas leisten musstest.",
                inputPrompt = "Dein Moment",
            ),
            ExerciseStep(
                "Ein Satz für dich",
                "Formuliere einen Satz, der dich daran erinnert. Zum Beispiel: „Ich bin wertvoll, auch an Tagen, an denen ich wenig schaffe.“",
                inputPrompt = "Dein Satz",
            ),
        ),
        closing = "Dein Wert war schon da, bevor du etwas geleistet hast. Und er bleibt.",
    )

    val energieCheck = Exercise(
        id = "energie-check",
        minutes = 5,
        category = ExerciseCategory.BOUNDARIES,
        title = "Mein Energie-Check",
        subtitle = "Spüren, was dir Kraft gibt und was sie kostet",
        durationText = "ca. 5 Minuten",
        intro = "Grenzen haben viel mit Energie zu tun. Wer weiß, was Kraft kostet, kann leichter für sich sorgen. Diese Übung hilft dir, deinen Tag mit diesem Blick anzuschauen.",
        steps = listOf(
            ExerciseStep(
                "Dein Energielevel",
                "Wie voll ist dein innerer Akku gerade – eher leer, halb voll oder gut geladen? Es gibt keine falsche Antwort.",
                inputPrompt = "Wie voll ist dein Akku?",
            ),
            ExerciseStep(
                "Was Kraft gekostet hat",
                "Was hat dich heute oder in den letzten Tagen besonders viel Energie gekostet? Denk an Aufgaben, Begegnungen oder Gedanken.",
                inputPrompt = "Was hat Kraft gekostet?",
            ),
            ExerciseStep(
                "Was Kraft gegeben hat",
                "Und was hat dir Energie geschenkt – auch wenn es nur ein kleiner Moment war?",
                inputPrompt = "Was hat Kraft gegeben?",
            ),
            ExerciseStep(
                "Eine kleine Grenze",
                "Gibt es eine kleine Grenze, die deine Energie in den nächsten Tagen schützen könnte? Zum Beispiel eine Pause einplanen oder eine Anfrage später beantworten.",
                inputPrompt = "Deine kleine Grenze",
            ),
        ),
        closing = "Auf deine Energie zu achten, ist kein Egoismus. Es ist eine Form von Fürsorge – für dich und für die Menschen um dich.",
    )

    val neinVorbereiten = Exercise(
        id = "nein-vorbereiten",
        minutes = 8,
        category = ExerciseCategory.BOUNDARIES,
        title = "Ein Nein vorbereiten",
        subtitle = "Ein freundliches, klares Nein in Ruhe vorbereiten",
        durationText = "ca. 5–10 Minuten",
        intro = "Ein Nein fällt leichter, wenn wir es nicht spontan finden müssen. In dieser Übung bereitest du eines für eine kleine, alltägliche Situation vor. Ob du es aussprichst, entscheidest du.",
        steps = listOf(
            ExerciseStep(
                "Die Situation",
                "Denk an eine kleine Bitte oder Anfrage, die bald auf dich zukommen könnte und zu der du eigentlich Nein sagen möchtest.",
                inputPrompt = "Welche Situation ist es?",
            ),
            ExerciseStep(
                "Was du möchtest",
                "Was möchtest du stattdessen – oder was brauchst du? Zum Beispiel Zeit für dich, Ruhe oder weniger Verpflichtungen.",
                inputPrompt = "Was möchtest du?",
            ),
            ExerciseStep(
                "Deine Worte",
                "Ein freundliches Nein kann aus drei Teilen bestehen: Wertschätzung, ein klares Nein und – wenn du magst – eine Alternative. Zum Beispiel: „Danke, dass du an mich denkst. Diesmal schaffe ich es nicht. Nächste Woche könnte ich helfen.“",
                inputPrompt = "Dein freundliches Nein",
            ),
            ExerciseStep(
                "Ausprobieren",
                "Wenn du magst, sprich deinen Satz einmal leise oder laut aus. Wie fühlt er sich an? Du darfst ihn so lange anpassen, bis er zu dir passt.",
            ),
        ),
        closing = "Ein vorbereitetes Nein ist wie ein Werkzeug, das du bei dir trägst. Du darfst es nutzen, wenn du bereit bist.",
    )

    val denAnderenVerstehen = Exercise(
        id = "den-anderen-verstehen",
        minutes = 8,
        category = ExerciseCategory.COUPLE,
        title = "Den Blick des anderen verstehen",
        subtitle = "Eine kleine Meinungsverschiedenheit aus zwei Richtungen sehen",
        durationText = "ca. 5–10 Minuten",
        intro = "Bei Missverständnissen hilft es oft, für einen Moment die Perspektive zu wechseln. Wähle dafür bitte eine kleine, alltägliche Meinungsverschiedenheit – kein großes Streitthema.",
        steps = listOf(
            ExerciseStep(
                "Die Situation",
                "Beschreibe kurz und möglichst neutral eine kleine Meinungsverschiedenheit aus der letzten Zeit.",
                inputPrompt = "Was ist passiert?",
            ),
            ExerciseStep(
                "Was dir wichtig war",
                "Was war dir in diesem Moment wichtig? Welches Bedürfnis stand bei dir dahinter?",
                inputPrompt = "Dein Bedürfnis",
            ),
            ExerciseStep(
                "Was dem anderen wichtig gewesen sein könnte",
                "Stell dir vor, du schaust durch die Augen deines Gegenübers. Was könnte ihm oder ihr wichtig gewesen sein?",
                inputPrompt = "Sein oder ihr mögliches Bedürfnis",
            ),
            ExerciseStep(
                "Was verbinden könnte",
                "Gibt es etwas, das beide Bedürfnisse berücksichtigt? Es muss keine Lösung sein – manchmal reicht schon ein neues Verständnis.",
                inputPrompt = "Deine Gedanken",
            ),
        ),
        closing = "Verständnis heißt nicht, dass du deine Sicht aufgibst. Es öffnet nur eine Tür, durch die ihr beide gehen könnt.",
    )

    val unsereRituale = Exercise(
        id = "unsere-rituale",
        minutes = 5,
        category = ExerciseCategory.COUPLE,
        title = "Unsere kleinen Rituale",
        subtitle = "Schauen, was euch im Alltag verbindet",
        durationText = "ca. 5 Minuten",
        intro = "Kleine Rituale können in einer Partnerschaft viel Nähe schaffen: der Kaffee am Morgen, ein Gruß zum Abschied, der Spaziergang am Sonntag. In dieser Übung schaust du darauf, was euch verbindet.",
        steps = listOf(
            ExerciseStep(
                "Was es schon gibt",
                "Welche kleinen Gewohnheiten oder Rituale teilt ihr bereits? Auch Unscheinbares zählt.",
                inputPrompt = "Eure Rituale",
            ),
            ExerciseStep(
                "Was dir guttut",
                "Welches davon ist dir besonders wichtig – und warum?",
                inputPrompt = "Was dir guttut",
            ),
            ExerciseStep(
                "Was du vermisst",
                "Gibt es etwas, das es früher gab oder das du dir wünschst?",
                inputPrompt = "Was du dir wünschst",
            ),
            ExerciseStep(
                "Eine kleine Idee",
                "Gibt es ein kleines, leicht umsetzbares Ritual, das du vorschlagen möchtest? Du entscheidest, ob und wann du es ansprichst.",
                inputPrompt = "Deine Idee",
            ),
        ),
        closing = "Nähe entsteht oft in kleinen, wiederkehrenden Momenten. Schön, dass du darauf geschaut hast.",
    )

    val tagSortieren = Exercise(
        id = "tag-sortieren",
        minutes = 5,
        category = ExerciseCategory.ADHD,
        title = "Den Tag sortieren",
        subtitle = "Den Kopf leeren und eine machbare Auswahl treffen",
        durationText = "ca. 5 Minuten",
        intro = "Wenn der Kopf voller Aufgaben ist, wirkt alles gleich wichtig. Diese Übung hilft dir, die Gedanken nach außen zu holen und eine freundliche Auswahl zu treffen.",
        steps = listOf(
            ExerciseStep(
                "Alles raus",
                "Schreib alles auf, was dir gerade durch den Kopf geht – Aufgaben, Termine, Gedanken. Ohne Ordnung, ohne Bewertung.",
                inputPrompt = "Alles, was gerade da ist",
            ),
            ExerciseStep(
                "Drei für heute",
                "Wähle höchstens drei Dinge aus, die heute wirklich dran sind. Kleine Dinge zählen genauso.",
                inputPrompt = "Deine drei Dinge",
            ),
            ExerciseStep(
                "Was warten darf",
                "Was darf ausdrücklich warten – auf morgen, nächste Woche oder auf später?",
                inputPrompt = "Was warten darf",
            ),
            ExerciseStep(
                "Ein freundlicher Satz",
                "Sag dir zum Abschluss zum Beispiel: „Ich muss nicht alles auf einmal schaffen. Eins nach dem anderen.“",
            ),
        ),
        closing = "Dein Kopf darf jetzt etwas leichter sein. Alles andere wartet geduldig auf seinen Moment.",
    )

    val vergesslichkeit = Exercise(
        id = "vergesslichkeit",
        minutes = 3,
        category = ExerciseCategory.ADHD,
        title = "Freundlich mit Vergesslichkeit",
        subtitle = "Wenn wieder etwas untergegangen ist",
        durationText = "ca. 3 Minuten",
        intro = "Etwas vergessen zu haben, kann schnell Ärger über sich selbst auslösen. Diese kurze Übung hilft dir, freundlicher damit umzugehen – und einen kleinen Schritt nach vorn zu finden.",
        steps = listOf(
            ExerciseStep(
                "Bemerken",
                "Was ist untergegangen? Und was sagt deine innere Stimme gerade dazu? Vielleicht etwas wie: „Typisch, schon wieder.“",
                inputPrompt = "Was ist passiert?",
            ),
            ExerciseStep(
                "Freundlich antworten",
                "Antworte dieser Stimme so, wie du einem lieben Menschen antworten würdest. Zum Beispiel: „Das passiert. Mein Kopf hatte gerade viel zu tragen.“",
                inputPrompt = "Deine freundliche Antwort",
            ),
            ExerciseStep(
                "Eine kleine Hilfe",
                "Gibt es eine kleine Hilfe für das nächste Mal? Etwa eine Erinnerung im Handy, ein fester Platz für den Schlüssel oder ein Zettel an der Tür.",
                inputPrompt = "Deine kleine Hilfe",
            ),
            ExerciseStep(
                "Weitergehen",
                "Du darfst den Moment jetzt loslassen. Ein Fehler sagt nichts über deinen Wert.",
            ),
        ),
        closing = "Vergesslichkeit ist kein Charakterfehler. Schön, dass du dir mit Verständnis begegnet bist.",
    )

    val fuenfMinutenPause = Exercise(
        id = "fuenf-minuten-pause",
        minutes = 5,
        category = ExerciseCategory.EMOTIONS,
        title = "Die Fünf-Minuten-Pause",
        subtitle = "Kurz innehalten, wenn es viel ist",
        durationText = "ca. 5 Minuten",
        intro = "Manchmal reicht eine kurze Pause, um wieder etwas Boden unter den Füßen zu spüren. Diese Übung führt dich in wenigen Schritten durch den Körper.",
        steps = listOf(
            ExerciseStep(
                "Füße und Boden",
                "Spüre deine Füße auf dem Boden. Vielleicht magst du die Zehen kurz bewegen.",
            ),
            ExerciseStep(
                "Schultern und Kiefer",
                "Lass deine Schultern ein wenig sinken. Spüre, ob dein Kiefer angespannt ist, und lass ihn weich werden.",
            ),
            ExerciseStep(
                "Der Atem",
                "Atme ein paar Mal in deinem eigenen Rhythmus. Wenn es sich gut anfühlt, lass den Ausatem etwas länger werden.",
            ),
            ExerciseStep(
                "Der nächste Schritt",
                "Frag dich: Was ist jetzt ein kleiner, guter nächster Schritt?",
                inputPrompt = "Dein nächster Schritt",
            ),
        ),
        closing = "Fünf Minuten für dich sind eine kleine Insel im Tag. Du kannst jederzeit wieder dorthin zurück.",
    )

    val sorgenParken = Exercise(
        id = "sorgen-parken",
        minutes = 8,
        category = ExerciseCategory.EMOTIONS,
        title = "Sorgen parken",
        subtitle = "Kreisende Gedanken sortieren und zur Seite stellen",
        durationText = "ca. 5–10 Minuten",
        intro = "Sorgen können sich anfühlen wie ein Karussell, das nicht anhält. In dieser Übung schreibst du sie auf, sortierst sie und gibst ihnen einen Parkplatz. Wähle eine Sorge, die sich gerade aushalten lässt.",
        steps = listOf(
            ExerciseStep(
                "Die Sorge",
                "Schreib die Sorge auf, die dich gerade am meisten beschäftigt.",
                inputPrompt = "Deine Sorge",
            ),
            ExerciseStep(
                "Was in deiner Hand liegt",
                "Was davon kannst du beeinflussen – und was nicht?",
                inputPrompt = "Was du beeinflussen kannst",
            ),
            ExerciseStep(
                "Der kleinste Schritt",
                "Gibt es einen kleinen Schritt, den du tun kannst? Wenn nicht, ist das auch in Ordnung.",
                inputPrompt = "Dein kleinster Schritt",
            ),
            ExerciseStep(
                "Parken",
                "Stell dir vor, du legst die Sorge an einen sicheren Ort. Du kannst später zu ihr zurückkehren, zum Beispiel zu einer festen Zeit am Abend. Bis dahin darf sie dort warten.",
            ),
        ),
        closing = "Deine Sorge ist ernst zu nehmen – und sie muss dich nicht die ganze Zeit begleiten.",
    )

    // ── Aus Nataschas Workbooks („Crazy Ex“ und Beziehungs-Workbook) ──────────────

    val wbKonfliktprotokoll = Exercise(
        id = "konfliktprotokoll",
        minutes = 10,
        together = false,
        category = ExerciseCategory.COUPLE,
        title = "Das Konfliktprotokoll",
        subtitle = "Einen Streit im Nachhinein bewusst verstehen",
        durationText = "ca. 10 Minuten",
        intro = "Wenn der erste Sturm vorbei ist, nimm dir zehn Minuten Zeit. Diese Übung gibt dir einen klaren Rahmen, um einen Streit im Nachhinein zu verstehen.\n\nDu kannst die vier Fragen allein beantworten oder zu zweit: Einer von euch beantwortet nacheinander die Fragen, der andere hört nur zu – ohne Unterbrechungen, ohne Rechtfertigungen. Danach wiederholt der Zuhörende mit eigenen Worten, was er verstanden hat. Anschließend wechselt ihr die Rollen.",
        steps = listOf(
            ExerciseStep(
                "Wovor hatte ich Angst?",
                "Wovor hatte ich in dieser Situation eigentlich Angst? Zum Beispiel davor, nicht gehört oder verlassen zu werden, Kontrolle zu verlieren oder abgelehnt zu werden.",
                inputPrompt = "Wovor hattest du Angst?",
            ),
            ExerciseStep(
                "Wofür habe ich mich geschämt?",
                "Gab es etwas, wofür ich mich in dem Moment geschämt habe? Zum Beispiel, dass ich zu emotional reagiert habe, dass ich mich schwach fühlte oder dass ich meine Grenzen nicht klar gezeigt habe.",
                inputPrompt = "Wofür hast du dich geschämt?",
            ),
            ExerciseStep(
                "Was habe ich erwartet?",
                "Was habe ich vom anderen erwartet? Zum Beispiel Verständnis, Nähe, Unterstützung, Sicherheit – dass er oder sie mich in den Arm nimmt oder einfach da bleibt.",
                inputPrompt = "Was hast du erwartet?",
            ),
            ExerciseStep(
                "Welches Bedürfnis war ungestillt?",
                "Welches ungestillte Bedürfnis hatte ich? Zum Beispiel nach Zugehörigkeit, Wertschätzung, Ruhe, Klarheit, Respekt oder Geborgenheit.",
                inputPrompt = "Dein ungestilltes Bedürfnis",
            ),
            ExerciseStep(
                "Ein altes Gefühl? (freiwillig)",
                "Wenn du magst, frag das jüngere Ich in dir: „Was hast du gerade gebraucht?“ Bei einem Streit meldet sich oft ein altes Gefühl, das viel älter ist als diese Beziehung.",
                inputPrompt = "Was hättest du gebraucht?",
            ),
            ExerciseStep(
                "Zu zweit: Wiederholen und tauschen",
                "Wenn ihr die Übung zu zweit macht: Der Zuhörende fasst jetzt mit eigenen Worten zusammen, was er verstanden hat – ohne zu bewerten. Danach wechselt ihr die Rollen.",
            ),
        ),
        closing = "Diese Übung entschleunigt, schafft Verständnis und bringt Klarheit über die tieferen Ebenen eines Streits.",
    )

    val wbFaktOderDeutung = Exercise(
        id = "fakt-oder-deutung",
        minutes = 8,
        together = false,
        category = ExerciseCategory.COUPLE,
        title = "Fakt oder Deutung?",
        subtitle = "Eine Deutung prüfen, ohne sie wegzuschieben",
        durationText = "ca. 5–10 Minuten",
        intro = "Was jemand sagt oder tut, wird schnell gedeutet. In dieser Übung prüfst du eine Deutung, ohne sie wegzuschieben. Wähle eine kleine, alltägliche Situation.",
        steps = listOf(
            ExerciseStep(
                "Das ist passiert",
                "Was ist passiert? Schreib nur auf, was man hätte sehen oder hören können – ohne Bewertung.",
                inputPrompt = "Der Fakt",
            ),
            ExerciseStep(
                "So habe ich es gedeutet",
                "Wie hast du das Geschehene gedeutet? Was hast du daraus geschlossen?",
                inputPrompt = "Deine Deutung",
            ),
            ExerciseStep(
                "Was könnte es noch bedeuten?",
                "Gibt es andere mögliche Erklärungen? Sammle ruhig mehrere, auch wenn sie dir zunächst unwahrscheinlich vorkommen.",
                inputPrompt = "Andere Möglichkeiten",
            ),
            ExerciseStep(
                "Wen könnte ich fragen?",
                "Wen könntest du fragen, um mehr Klarheit zu bekommen – vielleicht dein Gegenüber selbst?",
                inputPrompt = "Wen könntest du fragen?",
            ),
        ),
        closing = "Eine Deutung ist ein Gedanke, keine Gewissheit. Schön, dass du ihr mit Neugier begegnet bist.",
    )

    val wbEtikettAbloesen = Exercise(
        id = "etikett-abloesen",
        minutes = 10,
        together = false,
        category = ExerciseCategory.SELF_WORTH,
        title = "Das Etikett ablösen",
        subtitle = "Ein Urteil über dich hinterfragen",
        durationText = "ca. 10 Minuten",
        intro = "Manchmal tragen wir ein Etikett mit uns herum – ein Urteil, das andere über uns gefällt haben oder das wir selbst über uns denken. Ein ruhiger Moment ist am besten für diese Übung.",
        steps = listOf(
            ExerciseStep(
                "Mein Etikett",
                "Welches Etikett trage ich oder trug ich? Wie lautet es genau? Zum Beispiel: „Du bist zu empfindlich.“",
                inputPrompt = "Dein Etikett",
            ),
            ExerciseStep(
                "Was es behauptet",
                "Was sagt dieses Etikett über mich, was ein einzelner Moment gar nicht belegen kann? Etiketten machen aus einem Moment oft ein „immer“.",
                inputPrompt = "Was behauptet es?",
            ),
            ExerciseStep(
                "Was wahr ist",
                "Was ist hinter diesem Etikett wahr, ohne dass es mich als Ganzes beschreibt? Zum Beispiel ein Verhalten, das ich ändern möchte.",
                inputPrompt = "Was ist wahr daran?",
            ),
            ExerciseStep(
                "Wer ich außerdem bin",
                "Wer bin ich außerdem? Schreib mindestens fünf Eigenschaften auf, die das Etikett nicht kennt.",
                inputPrompt = "Fünf Eigenschaften",
            ),
            ExerciseStep(
                "Mein neuer Satz",
                "Mein neuer Satz über mich beginnt mit: „Ich bin ein Mensch, der …“",
                inputPrompt = "Dein neuer Satz",
            ),
        ),
        closing = "Ein Etikett ist nie die ganze Geschichte. Du bist so viel mehr.",
    )

    val wbFaktOderUrteil = Exercise(
        id = "fakt-oder-urteil",
        minutes = 8,
        together = false,
        category = ExerciseCategory.SELF_WORTH,
        title = "Fakt oder Urteil?",
        subtitle = "Urteile über dich in Beschreibungen übersetzen",
        durationText = "ca. 5–10 Minuten",
        intro = "Ein Urteil erklärt einen ganzen Menschen. Ein Fakt beschreibt, was man hätte sehen können. In dieser Übung übersetzt du ein Urteil über dich in einen Fakt – und schaust, was darunter lag.",
        steps = listOf(
            ExerciseStep(
                "Das Urteil",
                "Schreib einen Satz auf, der über dich gesagt wurde oder den du über dich gedacht hast. Zum Beispiel: „Ich bin so chaotisch.“",
                inputPrompt = "Das Urteil",
            ),
            ExerciseStep(
                "Der Fakt",
                "Übersetze das Urteil in einen Fakt – in das, was man hätte sehen können. Zum Beispiel: „Ich habe heute einen Termin vergessen.“",
                inputPrompt = "Der Fakt",
            ),
            ExerciseStep(
                "Was darunter lag",
                "Was lag darunter? Vielleicht Angst, Ohnmacht, Traurigkeit, Scham oder Sehnsucht.",
                inputPrompt = "Was lag darunter?",
            ),
            ExerciseStep(
                "Nachspüren",
                "Ein Fakt kann trotzdem etwas sein, das du ändern möchtest. Der Unterschied: Du benennst ein Verhalten, ohne dich als Mensch kleinzumachen.",
            ),
        ),
        closing = "Du bist mehr als ein einzelner Moment. Danke, dass du genauer hingeschaut hast.",
    )

    val wbWertOhneBestaetigung = Exercise(
        id = "wert-ohne-bestaetigung",
        minutes = 10,
        together = false,
        category = ExerciseCategory.SELF_WORTH,
        title = "Wer bin ich ohne Bestätigung?",
        subtitle = "Den Blick wieder auf dich richten",
        durationText = "ca. 10 Minuten",
        intro = "Wenn andere lange mitbestimmt haben, wie wertvoll oder wichtig du dich fühlst, kann es ungewohnt sein, den Blick wieder auf dich zu richten. Schau auf das, was von dir bleibt, wenn für einen Moment niemand darüber entscheidet, ob du genug bist.",
        steps = listOf(
            ExerciseStep(
                "Einfach weiterschreiben",
                "Ergänze den Satz, möglichst ohne lange nachzudenken: „Wenn mich niemand auswählen müsste, um mir meinen Wert zu bestätigen, wäre ich …“",
                inputPrompt = "Dein Satz",
            ),
            ExerciseStep(
                "Was bleibt",
                "Welche Eigenschaften an mir bleiben bestehen, auch wenn jemand sie nicht sieht oder schätzt?",
                inputPrompt = "Was bleibt?",
            ),
            ExerciseStep(
                "Was ich an mir mag",
                "Was mag ich an mir, ohne dafür Bestätigung zu brauchen?",
                inputPrompt = "Was magst du an dir?",
            ),
        ),
        closing = "Dein Wert braucht keine Zustimmung von außen. Er ist da – auch jetzt.",
    )

    val wbMehrIchSein = Exercise(
        id = "mehr-ich-sein",
        minutes = 10,
        together = false,
        category = ExerciseCategory.SELF_WORTH,
        title = "Mehr ich sein",
        subtitle = "Dich selbst wieder besser kennenlernen",
        durationText = "ca. 10 Minuten",
        intro = "Die Frage „Wer bin ich?“ klingt groß. Wenn die Antwort nicht sofort da ist, lass das „Ich weiß es nicht“ erst mal stehen. Manches in dir war vielleicht nur lange leise. Schreib auch Kleines auf: „Ich mag morgens meine Ruhe“ sagt genauso viel über dich wie eine große Lebensentscheidung.",
        steps = listOf(
            ExerciseStep(
                "Ein Tag nach mir",
                "Wie sähe ein Tag aus, den ich ganz nach mir ausrichte?",
                inputPrompt = "Dein Tag",
            ),
            ExerciseStep(
                "Ganz ich selbst",
                "Wann fühle ich mich am meisten wie ich selbst?",
                inputPrompt = "Wann bist du ganz du?",
            ),
            ExerciseStep(
                "Wieder oder neu",
                "Was möchte ich wieder oder neu in mein Leben holen?",
                inputPrompt = "Was möchtest du (wieder) mehr leben?",
            ),
            ExerciseStep(
                "Ein kleiner Schritt",
                "Was wäre ein kleiner Schritt, um mich besser kennenzulernen?",
                inputPrompt = "Dein kleiner Schritt",
            ),
        ),
        closing = "Du darfst dich immer wieder neu entdecken – in deinem Tempo.",
    )

    val wbBeduerfnisCheck = Exercise(
        id = "beduerfnis-check",
        minutes = 8,
        together = false,
        category = ExerciseCategory.SELF_WORTH,
        title = "Mein Bedürfnis-Check",
        subtitle = "Schauen, wo du gerade stehst",
        durationText = "ca. 5–10 Minuten",
        intro = "Keine Checkliste, die du vollständig erfüllen musst – nur ein Blick darauf, wo du gerade stehst. Wichtige Bedürfnisse sind zum Beispiel: Bindung (jemand, dem ich nicht gleichgültig bin), Orientierung (wissen, woran ich bin), Selbstwert, Wohlgefühl, Autonomie (selbst entscheiden), Kompetenz (etwas können und bewirken) und Eingebundenheit (dazugehören).",
        steps = listOf(
            ExerciseStep(
                "Was gut erfüllt ist",
                "Welche dieser Bedürfnisse sind gerade gut erfüllt? Wer oder was trägt dazu bei?",
                inputPrompt = "Was ist gut erfüllt?",
            ),
            ExerciseStep(
                "Was zu kurz kommt",
                "Welches Bedürfnis kommt gerade zu kurz?",
                inputPrompt = "Was kommt zu kurz?",
            ),
            ExerciseStep(
                "Was ich mir selbst geben kann",
                "Was könntest du dir selbst geben, um dieses Bedürfnis ein wenig zu nähren? Es darf klein sein.",
                inputPrompt = "Was kannst du dir geben?",
            ),
        ),
        closing = "Deine Bedürfnisse sind wichtig. Schon das Hinschauen ist ein Schritt der Fürsorge.",
    )

    val wbVerantwortungsWaage = Exercise(
        id = "verantwortungs-waage",
        minutes = 8,
        together = false,
        category = ExerciseCategory.BOUNDARIES,
        title = "Die Verantwortungs-Waage",
        subtitle = "Genau hinschauen, wer was getan hat",
        durationText = "ca. 5–10 Minuten",
        intro = "Verantwortung ist nicht automatisch fifty-fifty. In dieser Übung schaust du bei einer schwierigen Situation genau hin, wer was getan hat. Wähle eine Situation, die sich aushalten lässt.",
        steps = listOf(
            ExerciseStep(
                "Das habe ich getan",
                "Was habe ich in dieser Situation getan?",
                inputPrompt = "Dein Teil",
            ),
            ExerciseStep(
                "Das hat der andere getan",
                "Was hat der andere getan?",
                inputPrompt = "Der Teil des anderen",
            ),
            ExerciseStep(
                "Was sich nicht verteilen lässt",
                "Gibt es etwas, das sich gar nicht verteilen lässt – zum Beispiel Umstände, die niemand in der Hand hatte?",
                inputPrompt = "Was lässt sich nicht verteilen?",
            ),
            ExerciseStep(
                "Was mir gehört",
                "Welche Verantwortung habe ich übernommen, die mir nicht gehört? Und welche möchte ich bewusst übernehmen?",
                inputPrompt = "Deine Gedanken",
            ),
        ),
        closing = "Klarheit über Verantwortung entlastet – von Schuld, die nicht deine ist, und für das, was du gestalten kannst.",
    )

    val wbHerzensraum = Exercise(
        id = "herzensraum",
        minutes = 8,
        together = false,
        category = ExerciseCategory.EMOTIONS,
        title = "Der Weg zu deinem Herzensraum",
        subtitle = "Einen ruhigen Ort in dir finden",
        durationText = "ca. 5–10 Minuten",
        intro = "Lies die Schritte in Ruhe und setz sie in deinem eigenen Tempo um. Setz oder leg dich gemütlich hin, sodass dein Körper Halt hat. Du musst nichts verändern, nicht tiefer atmen und nicht ruhiger werden.\n\nWenn die Aufmerksamkeit nach innen dich unruhig macht, öffne die Augen und drück die Füße in den Boden. Beende die Übung dann für heute. Auch das gehört zum Weg.",
        steps = listOf(
            ExerciseStep(
                "Kontakt spüren",
                "Spüre zuerst, wo dein Körper Kontakt hat: die Füße auf dem Boden, das Gewicht auf dem Stuhl, die Hände auf den Beinen.",
            ),
            ExerciseStep(
                "Nach innen wandern",
                "Wandere langsam nach innen – durch den Bauch, die Brust und den Rücken. Bleib überall nur so lange, wie es sich angenehm anfühlt.",
            ),
            ExerciseStep(
                "Dein Herzensraum",
                "Irgendwo in dir gibt es einen Ort, an dem es ruhiger ist als an den anderen Stellen. Er muss nicht groß sein und nicht im Herzen liegen. Nenne ihn „deinen Herzensraum“. Wo liegt er? Wie weit reicht er?",
                inputPrompt = "Wo liegt dein Herzensraum?",
            ),
            ExerciseStep(
                "Wie er sich anfühlt",
                "Spürst du dort Weite oder Wärme, Licht oder Sanftheit? Oder etwas ganz anderes – Stilles, Schweres, Kleines? Was verändert sich, wenn du eine Weile dort bleibst?",
                inputPrompt = "Was spürst du?",
            ),
            ExerciseStep(
                "Nach der Übung",
                "Woran erkennst du, dass du dich sicherer fühlst? Und welche Menschen, Orte oder Situationen unterstützen dieses Erleben? Es gibt keine richtige Beschreibung, nur deine.",
                inputPrompt = "Deine Gedanken",
            ),
        ),
        closing = "Du musst aus deinen Antworten noch nichts machen. Es reicht, deinen Herzensraum kennenzulernen – du kannst jederzeit zurückkehren.",
    )

    val wbWellenProtokoll = Exercise(
        id = "wellen-protokoll",
        minutes = 5,
        together = false,
        category = ExerciseCategory.EMOTIONS,
        title = "Mein Wellen-Protokoll",
        subtitle = "Gefühle als Wellen erleben",
        durationText = "ca. 5 Minuten",
        intro = "Gefühle kommen wie Wellen: Sie steigen, erreichen einen Höhepunkt und lassen von selbst wieder nach. Mit diesem Protokoll beobachtest du eine Welle – und lernst, dass sie vorübergeht.",
        steps = listOf(
            ExerciseStep(
                "Der Auslöser",
                "Was hat die Welle ausgelöst?",
                inputPrompt = "Der Auslöser",
            ),
            ExerciseStep(
                "Stärke zu Beginn",
                "Wie stark ist das Gefühl gerade, von 0 bis 10?",
                inputPrompt = "Stärke jetzt (0–10)",
            ),
            ExerciseStep(
                "Was ich tue",
                "Was tust du, während die Welle da ist? Zum Beispiel atmen, dich bewegen, jemanden anrufen oder einfach abwarten.",
                inputPrompt = "Was tust du?",
            ),
            ExerciseStep(
                "Nach 20 Minuten",
                "Komm nach etwa 20 Minuten zurück: Wie stark ist das Gefühl jetzt, von 0 bis 10? Was hast du über deine Wellen gelernt?",
                inputPrompt = "Stärke danach und was du gelernt hast",
            ),
        ),
        closing = "Jede Welle, die du begleitest, zeigt dir: Gefühle verändern sich. Du kannst sie durchstehen.",
    )

    val wbAktivesZuhoeren = Exercise(
        id = "aktives-zuhoeren",
        minutes = 15,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Aktives Zuhören",
        subtitle = "Einander wirklich hören",
        durationText = "ca. 15 Minuten",
        intro = "Eine Übung für euch zu zweit. Einer erzählt, der andere hört nur zu. Danach tauscht ihr die Rollen.",
        steps = listOf(
            ExerciseStep(
                "Erzählen",
                "Einer von euch erzählt fünf Minuten lang von etwas, das ihm oder ihr wichtig ist. Der andere hört nur zu – ohne zu unterbrechen, ohne Ratschläge.",
            ),
            ExerciseStep(
                "Wiederholen",
                "Der Zuhörende wiederholt danach mit eigenen Worten, was er verstanden hat. Der Erzählende darf ergänzen, was gefehlt hat.",
            ),
            ExerciseStep(
                "Rollen tauschen",
                "Jetzt tauscht ihr die Rollen.",
            ),
            ExerciseStep(
                "Nachspüren",
                "Wie hat es sich angefühlt, so zugehört zu bekommen? Was war ungewohnt?",
                inputPrompt = "Was ist dir aufgefallen?",
            ),
        ),
        closing = "Echtes Zuhören ist eines der schönsten Geschenke, die ihr euch machen könnt.",
    )

    val wbWertschaetzungsrunde = Exercise(
        id = "wertschaetzungsrunde",
        minutes = 10,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Wertschätzung sichtbar machen",
        subtitle = "Einander sagen, was ihr schätzt",
        durationText = "ca. 10 Minuten",
        intro = "Eine Übung für euch zu zweit. Im Alltag bleibt oft unausgesprochen, was wir aneinander schätzen. Hier macht ihr es sichtbar.",
        steps = listOf(
            ExerciseStep(
                "Aufschreiben",
                "Jeder schreibt für sich drei Dinge auf, die er oder sie am anderen schätzt – zum Beispiel an seiner Art, an seiner Kommunikation (Geduld, Humor, Klarheit) oder an dem, was er für euch tut.",
                inputPrompt = "Deine drei Dinge",
            ),
            ExerciseStep(
                "Vorlesen",
                "Lest euch eure Sätze gegenseitig vor. Der andere hört nur zu und lässt die Worte ankommen.",
            ),
            ExerciseStep(
                "Nachspüren",
                "Wie war es, Wertschätzung zu hören – und zu schenken?",
                inputPrompt = "Wie war es für dich?",
            ),
        ),
        closing = "Wertschätzung, die ausgesprochen wird, kommt an. Schön, dass ihr euch diesen Moment geschenkt habt.",
    )

    val wbKleinesGestaendnis = Exercise(
        id = "kleines-gestaendnis",
        minutes = 10,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Das kleine Geständnis",
        subtitle = "Sich ein Stück verletzlich zeigen",
        durationText = "ca. 10 Minuten",
        intro = "Eine Übung für euch zu zweit. Nähe wächst, wenn wir uns zeigen, wie wir sind – auch mit unseren Unsicherheiten. Wählt etwas Kleines.",
        steps = listOf(
            ExerciseStep(
                "Teilen",
                "Jeder teilt eine kleine Sorge oder Unsicherheit, die er oder sie sonst lieber verschweigen würde. Auch ein Gedanke oder Gefühl, das ihr bisher zurückgehalten habt, ist möglich.",
            ),
            ExerciseStep(
                "Nur zuhören",
                "Der andere hört nur zu – ohne zu bewerten, ohne gleich eine Lösung anzubieten.",
            ),
            ExerciseStep(
                "Danke sagen",
                "Bedankt euch kurz beieinander für das Vertrauen.",
            ),
            ExerciseStep(
                "Nachspüren",
                "Wie hat es sich angefühlt, etwas Persönliches zu teilen?",
                inputPrompt = "Wie war es für dich?",
            ),
        ),
        closing = "Sich zu zeigen, braucht Mut. Und es schafft Nähe.",
    )

    val wbIchBrauche = Exercise(
        id = "ich-brauche",
        minutes = 5,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "„Ich brauche …“",
        subtitle = "Bedürfnisse aussprechen",
        durationText = "ca. 5 Minuten",
        intro = "Eine Übung für euch zu zweit. Viele Konflikte entstehen, weil Bedürfnisse unausgesprochen bleiben. Hier sprecht ihr sie aus – ohne Vorwürfe.",
        steps = listOf(
            ExerciseStep(
                "Vorbereiten",
                "Jeder überlegt sich mindestens einen Satz, der mit „Ich brauche …“ beginnt. Zum Beispiel: „Ich brauche abends ein paar Minuten Ruhe, bevor wir reden.“",
                inputPrompt = "Dein Satz",
            ),
            ExerciseStep(
                "Aussprechen",
                "Sagt euch eure Sätze gegenseitig. Der andere hört zu und fragt höchstens nach, wenn etwas unklar ist.",
            ),
            ExerciseStep(
                "Nachspüren",
                "Was hast du über dein Gegenüber erfahren? Was über dich?",
                inputPrompt = "Was hast du erfahren?",
            ),
        ),
        closing = "Ein ausgesprochenes Bedürfnis ist eine Einladung, einander besser zu verstehen.",
    )

    val wbStillerBlickkontakt = Exercise(
        id = "stiller-blickkontakt",
        minutes = 3,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Stille Nähe",
        subtitle = "Nähe ohne Worte spüren",
        durationText = "ca. 3–5 Minuten",
        intro = "Eine Übung für euch zu zweit. Manchmal sagt Nähe ohne Worte mehr als jedes Gespräch. Wenn sich etwas unangenehm anfühlt, dürft ihr jederzeit aufhören.",
        steps = listOf(
            ExerciseStep(
                "Gegenüber sitzen",
                "Setzt euch einander gegenüber, so dass ihr euch gut ansehen könnt.",
            ),
            ExerciseStep(
                "Zwei Minuten Stille",
                "Schaut euch zwei Minuten lang schweigend an. Wenn es sich stimmig anfühlt, legt eine Hand auf die Brust eures Gegenübers oder haltet euch an den Händen. Ihr könnt euch auch gemütlich nebeneinanderlegen, euch in den Arm nehmen und fünf Minuten gemeinsam schweigen und bewusst atmen.",
            ),
            ExerciseStep(
                "Nachspüren",
                "Wie hat sich Nähe ohne Worte angefühlt?",
                inputPrompt = "Was hast du gespürt?",
            ),
        ),
        closing = "Ein paar stille Minuten können verbinden – ganz ohne etwas sagen zu müssen.",
    )

    val wbStoppSignal = Exercise(
        id = "stopp-signal",
        minutes = 5,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Unser Stopp-Signal",
        subtitle = "Einander liebevoll an neue Wege erinnern",
        durationText = "ca. 5 Minuten",
        intro = "Eine Übung für euch zu zweit. Alte Muster tauchen oft auf, bevor wir es merken. Ein gemeinsames Signal kann helfen, sie freundlich zu unterbrechen.",
        steps = listOf(
            ExerciseStep(
                "Ein Signal finden",
                "Vereinbart ein Signal, das ihr euch geben könnt, wenn ihr bemerkt, dass ein altes Muster gerade aktiv ist – ein Wort, eine Geste oder ein Zeichen.",
                inputPrompt = "Euer Signal",
            ),
            ExerciseStep(
                "Die Bedeutung",
                "Das Signal soll liebevoll daran erinnern: „Stopp – wir probieren einen neuen Weg.“ Es ist kein Vorwurf, sondern eine Einladung.",
            ),
            ExerciseStep(
                "Was danach passiert",
                "Besprecht, was ihr tun möchtet, wenn das Signal kommt. Zum Beispiel kurz innehalten, durchatmen oder das Gespräch später fortsetzen.",
                inputPrompt = "Was ihr dann tun möchtet",
            ),
        ),
        closing = "Ein gemeinsames Signal zeigt: Ihr seid ein Team – auch gegen alte Muster.",
    )

    val wbNeueWahl = Exercise(
        id = "neue-wahl",
        minutes = 10,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Eine neue Wahl treffen",
        subtitle = "Gemeinsam anders reagieren lernen",
        durationText = "ca. 10 Minuten",
        intro = "Eine Übung für euch zu zweit. Wählt eine konkrete Situation, in der ein altes Muster aufgetaucht ist – etwa Rückzug, Streit oder Anpassung. Bitte nehmt etwas Überschaubares.",
        steps = listOf(
            ExerciseStep(
                "Die Situation",
                "Beschreibt gemeinsam kurz die Situation. Was ist passiert?",
                inputPrompt = "Die Situation",
            ),
            ExerciseStep(
                "Das Muster",
                "Welches Muster ist aufgetaucht? Wie habt ihr beide reagiert?",
                inputPrompt = "Das Muster",
            ),
            ExerciseStep(
                "Eine neue Wahl",
                "Besprecht gemeinsam: „Wie hätten wir anders reagieren können?“ Sammelt ein, zwei Ideen.",
                inputPrompt = "Eure Ideen",
            ),
        ),
        closing = "Jedes Mal, wenn ihr ein Muster bemerkt, habt ihr eine neue Wahl. Das ist der Anfang von Veränderung.",
    )

    val wbHoffnungTeilen = Exercise(
        id = "hoffnung-teilen",
        minutes = 10,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Was uns Hoffnung gibt",
        subtitle = "Den Blick auf das Tragende richten",
        durationText = "ca. 10 Minuten",
        intro = "Eine Übung für euch zu zweit. Gerade in schwierigen Phasen tut es gut, auf das zu schauen, was trägt.",
        steps = listOf(
            ExerciseStep(
                "Für sich überlegen",
                "Jeder überlegt für sich: Was gibt mir in unserer Beziehung gerade am meisten Hoffnung?",
                inputPrompt = "Was gibt dir Hoffnung?",
            ),
            ExerciseStep(
                "Teilen",
                "Erzählt einander davon. Hört zu, ohne zu kommentieren.",
            ),
            ExerciseStep(
                "Nähe",
                "In welchen Momenten fühlt ihr euch einander am nächsten? Sammelt sie gemeinsam.",
                inputPrompt = "Eure Momente",
            ),
        ),
        closing = "Hoffnung ist eine leise Kraft. Schön, dass ihr sie miteinander geteilt habt.",
    )

    val wbSpracheDerLiebe = Exercise(
        id = "sprache-der-liebe",
        minutes = 10,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Die Sprache der Liebe entdecken",
        subtitle = "Einander in der eigenen Sprache Liebe zeigen",
        durationText = "ca. 10 Minuten",
        intro = "Eine Übung für euch zu zweit. Menschen zeigen und spüren Liebe auf unterschiedliche Weise: durch Worte, Berührung, gemeinsame Zeit, kleine Aufmerksamkeiten oder Unterstützung im Alltag. Wenn ihr mögt, macht vorher beide den Selbsttest „Was ist meine Liebessprache?“.",
        steps = listOf(
            ExerciseStep(
                "Für sich überlegen",
                "Jeder überlegt für sich: Welche Liebessprache berührt mich am meisten?",
                inputPrompt = "Deine Liebessprache",
            ),
            ExerciseStep(
                "Teilen",
                "Teilt euch eure Antworten gegenseitig mit.",
            ),
            ExerciseStep(
                "In der Sprache des anderen",
                "Überlegt dann: Wie kann ich dir in deiner Sprache Liebe zeigen? Sammelt ein, zwei konkrete Ideen.",
                inputPrompt = "Deine Ideen",
            ),
            ExerciseStep(
                "In den nächsten Tagen",
                "Versucht in den nächsten Tagen bewusst, die Sprache des anderen zu sprechen.",
            ),
        ),
        closing = "Wenn ihr die Sprache des anderen sprecht, kommt Liebe dort an, wo sie gebraucht wird.",
    )

    val wbNaeheRitual = Exercise(
        id = "naehe-ritual",
        minutes = 5,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Nähe-Ritual im Alltag",
        subtitle = "Ein kleines tägliches Zeichen der Verbundenheit",
        durationText = "ca. 5 Minuten",
        intro = "Eine Übung für euch zu zweit. Kleine, regelmäßige Rituale schaffen im Alltag verlässliche Momente der Nähe.",
        steps = listOf(
            ExerciseStep(
                "Ein Ritual wählen",
                "Wählt gemeinsam ein kleines tägliches Ritual. Zum Beispiel ein Kuss am Morgen, eine Umarmung am Abend oder ein gemeinsamer Spaziergang.",
                inputPrompt = "Euer Ritual",
            ),
            ExerciseStep(
                "Wann und wo",
                "Wann und wo passt es gut in euren Tag? Je selbstverständlicher es dazugehört, desto leichter bleibt es.",
                inputPrompt = "Wann und wo",
            ),
            ExerciseStep(
                "Nach einer Woche",
                "Schaut nach einer Woche gemeinsam: Wie fühlt sich das Ritual an? Möchtet ihr es beibehalten oder verändern?",
            ),
        ),
        closing = "Ein kleines Ritual kann jeden Tag sagen: Du bist mir wichtig.",
    )

    val wbWerteCheck = Exercise(
        id = "werte-check",
        minutes = 15,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Werte-Check",
        subtitle = "Gemeinsamkeiten und Unterschiede entdecken",
        durationText = "ca. 15 Minuten",
        intro = "Eine Übung für euch zu zweit. Werte geben Orientierung – im Leben und in der Beziehung. Unterschiede sind dabei kein Problem, sondern eine Einladung, einander besser zu verstehen.",
        steps = listOf(
            ExerciseStep(
                "Fünf Werte",
                "Jeder schreibt fünf Werte auf, die ihm im Leben am wichtigsten sind – zum Beispiel Freiheit, Sicherheit, Familie oder Abenteuer.",
                inputPrompt = "Deine fünf Werte",
            ),
            ExerciseStep(
                "Vergleichen",
                "Vergleicht eure Listen. Wo gibt es Gemeinsamkeiten, wo Unterschiede?",
                inputPrompt = "Gemeinsamkeiten und Unterschiede",
            ),
            ExerciseStep(
                "Im Alltag leben",
                "Sprecht darüber: Wie können wir unsere Werte im Alltag leben – die gemeinsamen und die unterschiedlichen?",
                inputPrompt = "Eure Ideen",
            ),
        ),
        closing = "Wer die Werte des anderen kennt, versteht vieles leichter. Schön, dass ihr darüber gesprochen habt.",
    )

    val wbZeitFuereinander = Exercise(
        id = "zeit-fuereinander",
        minutes = 10,
        together = true,
        category = ExerciseCategory.COUPLE,
        title = "Zeit füreinander planen",
        subtitle = "Die 7er-Regel praktisch umsetzen",
        durationText = "ca. 10 Minuten",
        intro = "Eine Übung für euch zu zweit. Gemeinsame Zeit geht im Alltag schnell unter. Wenn ihr sie fest einplant, bekommt sie den Platz, den sie verdient.",
        steps = listOf(
            ExerciseStep(
                "Ein Mini-Date",
                "Plant gemeinsam ein Mini-Date für die kommende Woche.",
                inputPrompt = "Euer Mini-Date",
            ),
            ExerciseStep(
                "Ein Tagesdate",
                "Sucht einen Termin für ein Tagesdate in den nächsten zwei Monaten.",
                inputPrompt = "Euer Tagesdate",
            ),
            ExerciseStep(
                "Ein paar Tage weg",
                "Überlegt, wann ihr das nächste Mal für mehrere Tage wegfahren könnt.",
                inputPrompt = "Eure Idee",
            ),
            ExerciseStep(
                "In den Kalender",
                "Schreibt die Termine in den Kalender und behandelt sie so verbindlich wie andere Verpflichtungen.",
            ),
        ),
        closing = "Zeit füreinander ist eine Investition in eure Verbindung.",
    )

    val all: List<Exercise> = listOf(
        innerDialogue, strengths, innererUnterstuetzer, wertOhneLeistung, wbEtikettAbloesen, wbFaktOderUrteil, wbWertOhneBestaetigung, wbMehrIchSein, wbBeduerfnisCheck,
        boundaries, yesAndNo, energieCheck, neinVorbereiten, wbVerantwortungsWaage,
        appreciation, iMessage, denAnderenVerstehen, unsereRituale, wbKonfliktprotokoll, wbFaktOderDeutung, wbAktivesZuhoeren, wbWertschaetzungsrunde, wbKleinesGestaendnis, wbIchBrauche, wbStillerBlickkontakt, wbStoppSignal, wbNeueWahl, wbHoffnungTeilen, wbSpracheDerLiebe, wbNaeheRitual, wbWerteCheck, wbZeitFuereinander,
        oneThing, stimulusBreak, whatWorked, tagSortieren, vergesslichkeit,
        orientation, nameFeeling, fuenfMinutenPause, sorgenParken, wbHerzensraum, wbWellenProtokoll,
    )
    // Angeleitete Übungen (CalmingExercises) sind vorerst ausgeblendet – die Vorlesestimme passt noch nicht.

    val categories: List<ExerciseCategory> = ExerciseCategory.entries.filter { c -> all.any { it.category == c } }

    fun inCategory(category: ExerciseCategory): List<Exercise> = all.filter { it.category == category }

    fun byId(id: String): Exercise? = all.firstOrNull { it.id == id }
}
