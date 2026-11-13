package de.lichtblickraum.core.exercises

/**
 * Angeleitete, traumasensible Übungen zum Beruhigen und Halt finden.
 *
 * Grundsätze (nach traumasensibler Achtsamkeit, u. a. D. Treleaven):
 * - einladende Sprache und Wahlfreiheit („wenn es für dich passt“)
 * - äußere Anker (Raum, Sinne, Boden) vor innerer Körperwahrnehmung
 * - kurz, dosiert, mit Rückweg zu Ruhe; Augen offen erlaubt; kein Luftanhalten
 * - jederzeit beendbar; „Wenn es zu viel wird“ führt sofort zu einer Erdung
 *
 * Die Texte werden auf dem Gerät vorgelesen; [ExerciseStep.pauseSeconds] lässt danach Raum.
 */
object CalmingExercises {

    const val TOO_MUCH_TITLE = "Wenn es gerade zu viel wird"
    const val TOO_MUCH_TEXT =
        "Öffne die Augen und schau dich im Raum um. Drück deine Füße fest in den Boden. " +
            "Benenne drei Dinge, die du gerade sehen kannst. Du darfst die Übung jetzt beenden – " +
            "auch das ist ein guter Schritt der Selbstfürsorge."

    private fun guided(
        id: String, title: String, subtitle: String, minutes: Int, intro: String,
        steps: List<ExerciseStep>, closing: String,
    ) = Exercise(
        id = id, category = ExerciseCategory.CALMING, title = title, subtitle = subtitle,
        durationText = "ca. $minutes Minuten", minutes = minutes, intro = intro, steps = steps,
        closing = closing, guided = true,
    )

    private fun s(title: String, text: String, pause: Int) = ExerciseStep(title, text, pauseSeconds = pause)

    val arriving = guided(
        "ankommen-im-raum", "Ankommen im Raum", "Mit den Augen im Hier und Jetzt ankommen", 3,
        "Diese Übung lädt dich ein, mit den Augen im Raum anzukommen. Deine Augen bleiben dabei offen. " +
            "Sie eignet sich gut, wenn Gedanken kreisen oder alte Gefühle auftauchen.",
        listOf(
            s("Willkommen", "Willkommen. Du kannst sitzen, stehen oder gehen – so, wie es gerade für dich passt. Deine Augen dürfen offen bleiben.", 4),
            s("Den Blick wandern lassen", "Lass deinen Blick langsam durch den Raum wandern. Ganz in deinem Tempo. Du musst nichts Bestimmtes finden.", 15),
            s("Etwas Angenehmes", "Wenn du magst, bleib mit dem Blick an etwas hängen, das angenehm oder einfach neutral ist. Eine Farbe, eine Form, ein Gegenstand.", 15),
            s("Umschauen", "Vielleicht magst du auch den Kopf ein wenig drehen und schauen, was neben oder hinter dir ist. Nur so weit, wie es angenehm ist.", 12),
            s("Hier bin ich", "Bemerke: Hier bist du jetzt. In diesem Raum. An diesem Tag.", 8),
            s("Nachspüren", "Wie ist es jetzt in dir? Du musst nichts verändern. Nur bemerken.", 10),
        ),
        "Danke, dass du dir diesen Moment genommen hast. Du kannst jederzeit zu dieser Übung zurückkehren.",
    )

    val fiveSenses = guided(
        "fuenf-sinne", "5-4-3-2-1 mit allen Sinnen", "Über die Sinne ins Jetzt kommen", 4,
        "Diese Übung lenkt deine Aufmerksamkeit behutsam nach außen – auf das, was du sehen, spüren, " +
            "hören, riechen und schmecken kannst. Wenn dir zu einem Sinn nichts einfällt, ist das in Ordnung.",
        listOf(
            s("Willkommen", "Willkommen. Mach es dir so bequem, wie es gerade geht. Deine Augen bleiben offen.", 4),
            s("Fünf Dinge sehen", "Schau dich um und benenne innerlich fünf Dinge, die du sehen kannst.", 20),
            s("Vier Dinge spüren", "Spüre vier Dinge, die du gerade berührst oder fühlst. Den Stoff deiner Kleidung, den Boden unter den Füßen, die Lehne hinter dir.", 20),
            s("Drei Dinge hören", "Lausche. Welche drei Geräusche kannst du hören? Nah oder weit weg.", 20),
            s("Zwei Dinge riechen", "Gibt es zwei Dinge, die du riechen kannst? Wenn nicht, ist das völlig in Ordnung.", 15),
            s("Eine Sache schmecken", "Und eine Sache, die du schmeckst. Oder einfach den Geschmack in deinem Mund.", 10),
            s("Im Jetzt", "Du bist hier, im Jetzt. Nimm wahr, wie es dir gerade geht.", 8),
        ),
        "Schön, dass du dir diesen Moment genommen hast. Diese Übung kannst du überall machen – auch ohne App.",
    )

    val feetAndSupport = guided(
        "fuesse-und-halt", "Füße und Halt spüren", "Spüren, dass der Boden dich trägt", 3,
        "Diese Übung verbindet dich mit dem Boden unter dir. Sie ist besonders hilfreich, wenn sich alles " +
            "unsicher oder wackelig anfühlt.",
        listOf(
            s("Willkommen", "Setz dich, wenn du magst, so hin, dass deine Füße den Boden berühren. Augen offen oder geschlossen – wie es für dich passt.", 5),
            s("Die Füße", "Spüre deine Füße auf dem Boden. Vielleicht die Fersen, die Ballen, die Zehen.", 12),
            s("Sanfter Druck", "Wenn du magst, drück die Füße sanft in den Boden. Und lass wieder locker. Probier aus, wie viel Druck sich gut anfühlt.", 15),
            s("Getragen werden", "Spüre, wie der Boden dich trägt. Er ist da, ohne dass du etwas dafür tun musst.", 12),
            s("Weiterer Halt", "Wenn es sich stimmig anfühlt, nimm auch wahr, wo dein Körper sonst noch gehalten wird. Vom Stuhl, von der Lehne, von der Unterlage.", 15),
        ),
        "Du darfst dich gehalten fühlen. Wann immer du magst, kannst du hierher zurückkommen.",
    )

    val calmBreath = guided(
        "ruhiger-atem", "Ruhiger Atem", "Den Atem sanft verlangsamen – ohne Druck", 4,
        "Diese Übung lädt dich ein, deinen Atem ein wenig zu verlangsamen. Es gibt kein Luftanhalten. " +
            "Wenn dir die Aufmerksamkeit auf den Atem unangenehm ist, öffne die Augen, achte auf deine " +
            "Füße oder beende die Übung. Das ist völlig in Ordnung.",
        listOf(
            s("Willkommen", "Willkommen. Mach es dir bequem. Die Augen dürfen offen bleiben.", 4),
            s("Einfach atmen", "Lass deinen Atem zunächst einfach so, wie er ist. Du musst nichts verändern.", 10),
            s("Etwas länger aus", "Wenn du magst, lass den Ausatem ein wenig länger werden als den Einatem. Ganz sanft, ohne Druck.", 6),
            s("Gemeinsam atmen", "Atme ein. Zwei. Drei. Vier. Und langsam aus. Zwei. Drei. Vier. Fünf. Sechs.", 3),
            s("Weiter im eigenen Tempo", "Atme ein. Zwei. Drei. Vier. Und aus. Zwei. Drei. Vier. Fünf. Sechs.", 3),
            s("Noch einmal", "Und noch einmal ein. Zwei. Drei. Vier. Und aus. Zwei. Drei. Vier. Fünf. Sechs.", 10),
            s("Frei atmen", "Wenn dir etwas schwindelig wird, atme einfach wieder normal. Dein Atem weiß, was er braucht.", 8),
            s("Nachspüren", "Lass deinen Atem jetzt wieder ganz frei fließen. Wie fühlst du dich gerade?", 10),
        ),
        "Dein Atem ist immer bei dir. Du kannst ihn jederzeit als kleinen Anker nutzen – wenn es sich gut anfühlt.",
    )

    val butterflyHug = guided(
        "schmetterlingsumarmung", "Schmetterlingsumarmung", "Dich selbst durch sanftes Klopfen beruhigen", 3,
        "Bei der Schmetterlingsumarmung klopfst du dir selbst abwechselnd sanft auf Schultern oder Oberarme. " +
            "Viele Menschen erleben das als beruhigend. Sie ist eine Möglichkeit zur Selbstberuhigung und " +
            "ersetzt keine Traumatherapie.",
        listOf(
            s("Die Haltung", "Kreuze deine Arme vor der Brust, so dass deine Hände auf den Oberarmen oder Schultern liegen. Wie eine Umarmung für dich selbst.", 6),
            s("Sanftes Klopfen", "Klopfe jetzt langsam abwechselnd mit der linken und der rechten Hand. Links, rechts, links, rechts. In einem ruhigen Tempo.", 20),
            s("Weiter atmen", "Atme dabei ruhig weiter. Du kannst die Augen offen lassen und auf einen Punkt im Raum schauen.", 20),
            s("Ein freundlicher Satz", "Wenn du magst, sag dir innerlich: Jetzt gerade bin ich hier. Oder denk an etwas, das dir guttut.", 20),
            s("Ausklingen", "Lass das Klopfen langsam ausklingen. Spüre nach, wie es dir jetzt geht.", 10),
        ),
        "Du hast dir selbst Halt gegeben. Die Schmetterlingsumarmung kannst du überall nutzen.",
    )

    val handOnHeart = guided(
        "hand-aufs-herz", "Hand aufs Herz", "Eine freundliche Berührung für dich", 2,
        "Eine warme, freundliche Berührung kann ein Gefühl von Halt geben. Wenn Berührung gerade nicht " +
            "passt, lass die Hände einfach ruhen – die Übung funktioniert auch so.",
        listOf(
            s("Die Hand", "Lege, wenn es sich für dich stimmig anfühlt, eine Hand auf dein Herz, auf deinen Bauch oder an eine andere Stelle, die sich gut anfühlt.", 6),
            s("Die Wärme", "Spüre die Wärme deiner Hand. Vielleicht auch den leichten Druck.", 12),
            s("Ein freundlicher Satz", "Wenn du magst, sag dir innerlich einen freundlichen Satz. Zum Beispiel: Ich bin für mich da.", 12),
            s("Ruhig atmen", "Atme ein paar Mal ruhig. Ganz in deinem Rhythmus.", 15),
        ),
        "Du kannst dir diese kleine Geste jederzeit schenken.",
    )

    val pendulation = guided(
        "pendeln", "Pendeln zwischen Anspannung und Ruhe", "Behutsam hin und zurück zur Ruhe", 5,
        "Diese Übung ist für Momente leichter Anspannung gedacht, nicht für sehr starke Gefühle. Wenn es " +
            "dir gerade sehr schlecht geht, wähle lieber „Füße und Halt spüren“ oder „Ankommen im Raum“.",
        listOf(
            s("Willkommen", "Willkommen. Mach es dir bequem. Die Augen dürfen offen bleiben.", 4),
            s("Ein ruhiger Ort im Körper", "Spüre: Gibt es eine Stelle in deinem Körper, die sich ruhig, neutral oder angenehm anfühlt? Vielleicht die Hände, die Füße oder die Ohrläppchen. Es darf etwas ganz Kleines sein.", 15),
            s("Dort verweilen", "Bleib einen Moment mit deiner Aufmerksamkeit dort.", 10),
            s("Ein kurzer Blick", "Wenn du magst, bemerke nun eine Stelle, an der ein wenig Anspannung ist. Nur kurz, wie ein kurzer Blick. Du musst nichts verändern.", 8),
            s("Zurück zur Ruhe", "Und jetzt wandere wieder zurück zu der ruhigen Stelle. Bleib dort, so lange du magst.", 15),
            s("Hin und her", "Du kannst so ein paar Mal hin- und herpendeln. Und immer wieder zurück zur Ruhe.", 25),
            s("Abschluss", "Zum Schluss bleib bei der ruhigen Stelle. Wenn deine Augen geschlossen waren, öffne sie und schau dich im Raum um.", 8),
        ),
        "Du hast erlebt, dass du immer wieder zur Ruhe zurückfinden kannst. Das ist eine wertvolle Erfahrung.",
    )

    val comfortPlace = guided(
        "wohlfuehlort", "Ein Ort, an dem es dir gut geht", "Eine Vorstellungsreise zu deinem Wohlfühlort", 6,
        "Diese Übung lädt dich ein, dir einen Ort vorzustellen, an dem du dich wohlfühlst – echt oder " +
            "ausgedacht. Wenn kein Ort auftaucht, ist das in Ordnung. Du kannst stattdessen einen angenehmen " +
            "Gegenstand im Raum anschauen.",
        listOf(
            s("Willkommen", "Mach es dir bequem. Die Augen dürfen offen bleiben oder sich schließen – wie es für dich passt.", 5),
            s("Ein Ort taucht auf", "Lass einen Ort auftauchen, an dem du dich wohl und geborgen fühlst. Ein Ort aus deinem Leben oder einer, den du dir ausdenkst.", 15),
            s("Wenn kein Ort auftaucht", "Wenn kein Ort auftaucht, schau dir einfach etwas Angenehmes im Raum an. Auch das ist ein guter Weg.", 8),
            s("Sehen", "Was siehst du an deinem Ort? Welche Farben, welches Licht?", 15),
            s("Hören", "Was hörst du dort? Vielleicht ist es still, vielleicht gibt es leise Geräusche.", 15),
            s("Spüren", "Wie fühlen sich der Boden, die Luft und die Temperatur an?", 15),
            s("Gestalten", "Gestalte den Ort so, wie er dir guttut. Du darfst alles verändern, was du magst.", 20),
            s("Eine Erinnerung", "Wenn du magst, wähle eine kleine Geste oder ein Wort, das dich an diesen Ort erinnert.", 12),
            s("Zurückkommen", "Komm jetzt langsam zurück. Spüre deine Füße auf dem Boden, öffne die Augen und schau dich um.", 6),
        ),
        "Dein Wohlfühlort ist immer für dich da. Du kannst jederzeit an ihn zurückkehren.",
    )

    val all: List<Exercise> = listOf(
        arriving, fiveSenses, feetAndSupport, calmBreath, butterflyHug, handOnHeart, pendulation, comfortPlace,
    )
}
