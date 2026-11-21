package de.lichtblickraum.core.thoughts

import de.lichtblickraum.core.impulses.ImpulsePlanner
import de.lichtblickraum.core.profile.Focus
import java.time.LocalDate

/** Ein kurzer Gedanke für heute – zum Lesen, nicht zum Erledigen. */
data class Thought(val id: String, val focus: Focus, val text: String)

/**
 * Texte für heute, 15 je Thema. Es erscheinen nur Texte zu den gewählten Themen
 * (ohne Auswahl: alle). Jeden Tag ein anderer, jede Runde neu gemischt.
 * Neue Texte einfach anhängen (eindeutige ID).
 */
object DailyThoughts {

    val all: List<Thought> = listOf(
        // SELF_WORTH
        Thought("sw01", Focus.SELF_WORTH, "Dein Wert hängt nicht davon ab, wie viel du heute schaffst."),
        Thought("sw02", Focus.SELF_WORTH, "Du darfst dich mögen – auch an Tagen, an denen dir wenig gelingt."),
        Thought("sw03", Focus.SELF_WORTH, "Freundlichkeit mit dir selbst ist kein Luxus. Sie ist eine gute Grundlage für alles andere."),
        Thought("sw04", Focus.SELF_WORTH, "Manchmal ist „gut genug“ wirklich gut genug."),
        Thought("sw05", Focus.SELF_WORTH, "Du musst dir deinen Platz nicht verdienen. Er ist schon da."),
        Thought("sw06", Focus.SELF_WORTH, "Was andere über dich denken, ist nur ein Teil des Bildes – und oft ein kleiner."),
        Thought("sw07", Focus.SELF_WORTH, "Kleine Schritte zählen. Auch die, die niemand sieht."),
        Thought("sw08", Focus.SELF_WORTH, "Du darfst stolz auf etwas sein, ohne es gleich kleinzureden."),
        Thought("sw09", Focus.SELF_WORTH, "Fehler zeigen, dass du etwas versuchst. Über deinen Wert sagen sie nichts."),
        Thought("sw10", Focus.SELF_WORTH, "Vergleiche erzählen selten die ganze Geschichte – weder deine noch die der anderen."),
        Thought("sw11", Focus.SELF_WORTH, "Du bist mehr als deine Rollen im Beruf, in der Familie oder in Beziehungen."),
        Thought("sw12", Focus.SELF_WORTH, "Wie würdest du heute über dich sprechen, wenn du dich gernhättest? Probier es einmal aus."),
        Thought("sw13", Focus.SELF_WORTH, "Deine Bedürfnisse sind genauso wichtig wie die der anderen."),
        Thought("sw14", Focus.SELF_WORTH, "Du darfst dir selbst glauben, wenn du sagst, dass etwas schwer war."),
        Thought("sw15", Focus.SELF_WORTH, "Selbstwert wächst leise – oft in Momenten, in denen du dich selbst ernst nimmst."),
        // BOUNDARIES
        Thought("gr01", Focus.BOUNDARIES, "Ein Nein zu anderen kann ein Ja zu dir sein."),
        Thought("gr02", Focus.BOUNDARIES, "Du darfst dir Zeit nehmen, bevor du antwortest."),
        Thought("gr03", Focus.BOUNDARIES, "Grenzen sind keine Mauern. Sie zeigen, wo du gut für dich sorgst."),
        Thought("gr04", Focus.BOUNDARIES, "Es ist nicht deine Aufgabe, dass sich alle immer wohlfühlen."),
        Thought("gr05", Focus.BOUNDARIES, "Unbehagen ist ein Hinweis, kein Fehler. Es lohnt sich, ihm zuzuhören."),
        Thought("gr06", Focus.BOUNDARIES, "Du darfst deine Meinung ändern."),
        Thought("gr07", Focus.BOUNDARIES, "Eine klare Grenze kann freundlich klingen."),
        Thought("gr08", Focus.BOUNDARIES, "Wer deine Grenzen respektiert, respektiert dich."),
        Thought("gr09", Focus.BOUNDARIES, "Du musst ein Nein nicht ausführlich begründen."),
        Thought("gr10", Focus.BOUNDARIES, "Auch deine Kraft ist begrenzt – und das ist menschlich."),
        Thought("gr11", Focus.BOUNDARIES, "Grenzen zu setzen darf sich am Anfang ungewohnt anfühlen. Ungewohnt heißt nicht falsch."),
        Thought("gr12", Focus.BOUNDARIES, "Du darfst Pausen machen, auch wenn noch etwas zu tun ist."),
        Thought("gr13", Focus.BOUNDARIES, "Manchmal ist die wichtigste Grenze die zu deinen eigenen Ansprüchen."),
        Thought("gr14", Focus.BOUNDARIES, "Es ist in Ordnung, nicht immer erreichbar zu sein."),
        Thought("gr15", Focus.BOUNDARIES, "Zu spüren, was du nicht willst, ist ein erster Schritt zu dem, was du willst."),
        // COUPLE
        Thought("pa01", Focus.COUPLE, "Nähe entsteht oft in kleinen Momenten: einem Blick, einer Frage, einem gemeinsamen Lachen."),
        Thought("pa02", Focus.COUPLE, "In einer Beziehung dürfen beide Bedürfnisse haben – auch du."),
        Thought("pa03", Focus.COUPLE, "Zuhören heißt nicht zustimmen. Es heißt: Ich möchte dich verstehen."),
        Thought("pa04", Focus.COUPLE, "Ein Streit heißt nicht, dass die Beziehung schlecht ist. Wichtig ist, wie ihr wieder zueinanderfindet."),
        Thought("pa05", Focus.COUPLE, "Wertschätzung, die ausgesprochen wird, kommt an."),
        Thought("pa06", Focus.COUPLE, "Ihr müsst nicht alles gleich sehen, um euch nah zu sein."),
        Thought("pa07", Focus.COUPLE, "„Ich wünsche mir …“ kommt oft leichter an als „Du machst nie …“."),
        Thought("pa08", Focus.COUPLE, "Auch in einer Partnerschaft darfst du Zeit für dich brauchen."),
        Thought("pa09", Focus.COUPLE, "Hinter Ärger steckt oft ein unerfülltes Bedürfnis – bei dir und beim anderen."),
        Thought("pa10", Focus.COUPLE, "Neugier aufeinander kann auch nach vielen gemeinsamen Jahren wachsen."),
        Thought("pa11", Focus.COUPLE, "Eine Pause im Gespräch kann helfen, später in Ruhe weiterzusprechen."),
        Thought("pa12", Focus.COUPLE, "Du bist nicht allein dafür verantwortlich, dass eure Beziehung gelingt."),
        Thought("pa13", Focus.COUPLE, "Kleine Gesten der Zuwendung sind oft wichtiger als große."),
        Thought("pa14", Focus.COUPLE, "Alte Muster zeigen sich in Beziehungen besonders deutlich. Sie zu bemerken, ist schon viel."),
        Thought("pa15", Focus.COUPLE, "Echte Nähe darf auch Unterschiede aushalten."),
        // ADHD
        Thought("ad01", Focus.ADHD, "Dein Kopf arbeitet anders – nicht falsch."),
        Thought("ad02", Focus.ADHD, "Ein kleiner Anfang zählt mehr als ein perfekter Plan."),
        Thought("ad03", Focus.ADHD, "Vergessen ist kein Charakterfehler. Es zeigt, was dir helfen könnte."),
        Thought("ad04", Focus.ADHD, "Wecker, Listen und Erinnerungen zu nutzen ist klug, nicht schwach."),
        Thought("ad05", Focus.ADHD, "Wenn der Kopf voll ist, darf eine Sache warten."),
        Thought("ad06", Focus.ADHD, "Deine Begeisterung und deine Energie sind auch Stärken."),
        Thought("ad07", Focus.ADHD, "Pausen sind keine verlorene Zeit. Sie schaffen deinem Kopf wieder Platz."),
        Thought("ad08", Focus.ADHD, "Heute muss nicht alles erledigt werden. Eine Sache reicht."),
        Thought("ad09", Focus.ADHD, "Du darfst dir deinen Alltag so einrichten, dass er zu dir passt."),
        Thought("ad10", Focus.ADHD, "Ablenkung passiert. Zurückkommen darfst du jederzeit – ohne mit dir zu schimpfen."),
        Thought("ad11", Focus.ADHD, "Ein unordentlicher Tag sagt nichts über deinen Wert."),
        Thought("ad12", Focus.ADHD, "Manchmal hilft es, Dinge aufzuschreiben, damit sie nicht verloren gehen."),
        Thought("ad13", Focus.ADHD, "Du bist nicht faul. Manche Dinge kosten dich einfach mehr Kraft als andere."),
        Thought("ad14", Focus.ADHD, "Bemerke auch die kleinen Dinge, die heute geklappt haben."),
        Thought("ad15", Focus.ADHD, "Weniger Reize sind auch Selbstfürsorge – ein leiserer Raum, ein Fenster weniger auf dem Bildschirm."),
        // EMOTIONS
        Thought("ge01", Focus.EMOTIONS, "Jedes Gefühl darf da sein. Du musst es nicht sofort verändern."),
        Thought("ge02", Focus.EMOTIONS, "Gefühle kommen und gehen wie Wetter. Auch dieses wird sich verändern."),
        Thought("ge03", Focus.EMOTIONS, "Einem Gefühl einen Namen zu geben, schafft oft ein wenig Abstand."),
        Thought("ge04", Focus.EMOTIONS, "Du darfst traurig sein, auch ohne einen guten Grund dafür zu kennen."),
        Thought("ge05", Focus.EMOTIONS, "Ärger zeigt oft, dass dir etwas wichtig ist."),
        Thought("ge06", Focus.EMOTIONS, "Ein paar ruhige Atemzüge sind immer erreichbar."),
        Thought("ge07", Focus.EMOTIONS, "Manchmal braucht ein Gefühl einfach Zeit und Raum."),
        Thought("ge08", Focus.EMOTIONS, "Du musst schwere Gefühle nicht allein tragen. Unterstützung zu suchen, ist in Ordnung."),
        Thought("ge09", Focus.EMOTIONS, "Dein Körper zeigt dir oft früh, wie es dir geht. Es lohnt sich, hinzuhören."),
        Thought("ge10", Focus.EMOTIONS, "Freude darf auch an schweren Tagen einen kleinen Platz haben."),
        Thought("ge11", Focus.EMOTIONS, "Wenn alles zu viel ist, hilft oft ein Schritt zurück ins Hier und Jetzt."),
        Thought("ge12", Focus.EMOTIONS, "Gefühle sind Botschaften, keine Befehle."),
        Thought("ge13", Focus.EMOTIONS, "Es ist mutig, hinzuschauen, wie es dir wirklich geht."),
        Thought("ge14", Focus.EMOTIONS, "Du darfst dich trösten, wie du einen lieben Menschen trösten würdest."),
        Thought("ge15", Focus.EMOTIONS, "Auch ein mittelmäßiger Tag ist ein ganz normaler Tag."),
    )

    fun forDay(date: LocalDate, focus: Set<Focus>): Thought {
        val pool = all.filter { it.focus in focus }.ifEmpty { all }
        return ImpulsePlanner.rotate(pool, date.toEpochDay(), 0x7E57L)
    }
}
