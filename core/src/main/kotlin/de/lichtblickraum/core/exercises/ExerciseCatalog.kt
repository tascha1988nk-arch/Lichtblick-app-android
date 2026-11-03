package de.lichtblickraum.core.exercises

/**
 * Alle Übungen. Neue Übung ergänzen: weiteres Exercise(...) anlegen und in [all] eintragen.
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

    val all: List<Exercise> = listOf(orientation, boundaries, innerDialogue)

    fun byId(id: String): Exercise? = all.firstOrNull { it.id == id }
}
