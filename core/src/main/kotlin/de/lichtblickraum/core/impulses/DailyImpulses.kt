package de.lichtblickraum.core.impulses

import de.lichtblickraum.core.impulses.ImpulseCadence.DAILY
import de.lichtblickraum.core.impulses.ImpulseTheme.BOUNDARIES
import de.lichtblickraum.core.impulses.ImpulseTheme.NEEDS
import de.lichtblickraum.core.impulses.ImpulseTheme.PERCEPTION
import de.lichtblickraum.core.impulses.ImpulseTheme.SELF_COMPASSION
import de.lichtblickraum.core.impulses.ImpulseTheme.SELF_WORTH

/**
 * Tägliche Impulse (ca. 1–5 Minuten). Neue Impulse einfach unten anhängen.
 * Die ID muss eindeutig bleiben und sollte nach Veröffentlichung nicht mehr geändert werden.
 */
object DailyImpulses {

    val all: List<Impulse> = listOf(
        Impulse(
            "t01", DAILY, SELF_WORTH, "Etwas, das du an dir magst",
            "Schreibe eine Sache auf, die du an dir magst. Sie darf ganz unabhängig von deinem Aussehen oder deiner Leistung sein.",
            "Wir übersehen leicht, was uns als Mensch ausmacht. Es darf etwas Kleines sein – etwa, dass du gut zuhören kannst.",
            textPrompt = "Was magst du an dir?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t02", DAILY, SELF_COMPASSION, "Ein freundlicher Satz",
            "Sag dir heute einen freundlichen Satz. Wenn ein Kompliment gerade nicht glaubwürdig klingt, reicht: „Ich darf heute freundlich mit mir umgehen.“",
            "Freundliche Worte müssen nicht überschwänglich sein. Ein Satz, der sich ehrlich anfühlt, ist oft wirksamer als ein großes Lob.",
            textPrompt = "Dein Satz für heute (freiwillig)", durationText = "1 Minute",
        ),
        Impulse(
            "t03", DAILY, SELF_COMPASSION, "Gut für dich gesorgt",
            "Notiere etwas, das du heute gut für dich gemacht hast.",
            "Selbstfürsorge zeigt sich oft in kleinen Dingen: ein Glas Wasser, eine Pause, ein Nein. Es lohnt sich, sie zu bemerken.",
            textPrompt = "Was hast du heute für dich getan?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t04", DAILY, NEEDS, "Was brauche ich gerade?",
            "Frage dich: „Was brauche ich gerade?“ Du musst das Bedürfnis nicht sofort erfüllen können.",
            "Ein Bedürfnis wahrzunehmen ist schon ein wertvoller Schritt – auch wenn gerade kein Raum ist, es zu erfüllen.",
            textPrompt = "Was brauchst du gerade?", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t05", DAILY, PERCEPTION, "Etwas Angenehmes bemerken",
            "Nimm dir einen Moment und bemerke etwas Angenehmes um dich herum – ein Geräusch, ein Licht, eine Farbe, eine Temperatur.",
            "Unsere Aufmerksamkeit richtet sich oft auf Probleme. Ein kurzer Blick auf etwas Angenehmes kann ein kleines Gegengewicht sein.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t06", DAILY, SELF_WORTH, "Eine unsichtbare Anstrengung",
            "Erkenne eine kleine Anstrengung an, auch wenn niemand anderes sie gesehen hat.",
            "Vieles, was wir leisten, bleibt unsichtbar – zum Beispiel, wenn wir uns zu etwas aufraffen. Das darf trotzdem zählen.",
            textPrompt = "Welche Anstrengung möchtest du anerkennen?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t07", DAILY, NEEDS, "Eine echte Pause",
            "Gönn dir heute eine kurze Pause, in der du nichts erledigen musst – auch wenn es nur zwei Minuten sind.",
            "Pausen sind kein Luxus, den man sich erst verdienen muss. Schon kurze Unterbrechungen dürfen sein.",
            durationText = "2–5 Minuten",
        ),
        Impulse(
            "t08", DAILY, PERCEPTION, "Kurz in den Körper spüren",
            "Halte für einen Moment inne und frage dich: Wie geht es meinem Körper gerade? Vielleicht magst du die Schultern ein wenig sinken lassen.",
            "Der Körper gibt oft früh Hinweise darauf, wie es uns geht. Es geht nur ums Bemerken, nicht ums Verändern.",
            durationText = "1–2 Minuten",
        ),
        Impulse(
            "t09", DAILY, BOUNDARIES, "Ein kleines Nein",
            "Beobachte heute, ob es einen Moment gibt, in dem du etwas nicht möchtest. Du musst nichts ändern – nur wahrnehmen.",
            "Grenzen fangen mit dem Spüren an. Das Aussprechen darf später kommen, wenn es sich stimmig anfühlt.",
            textPrompt = "Was hast du bemerkt? (freiwillig)", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t10", DAILY, SELF_COMPASSION, "Wie mit einem guten Freund",
            "Wenn heute etwas nicht klappt: Wie würdest du mit einem lieben Menschen sprechen, dem das passiert ist? Versuch es einmal mit dir im selben Ton.",
            "Mit anderen sind viele Menschen geduldiger als mit sich selbst. Dieser Perspektivwechsel kann den inneren Ton etwas weicher machen.",
            durationText = "1–3 Minuten",
        ),
        Impulse(
            "t11", DAILY, SELF_WORTH, "Danke an dich",
            "Bedanke dich innerlich bei dir für etwas, das du heute getan hast – ganz egal, wie klein es ist.",
            "Dankbarkeit richten wir meist an andere. Es darf auch einmal dir selbst gelten.",
            textPrompt = "Wofür möchtest du dir danken?", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t12", DAILY, NEEDS, "Etwas trinken, etwas essen",
            "Prüfe kurz: Hast du heute schon genug getrunken und gegessen? Vielleicht magst du dir jetzt etwas gönnen.",
            "Grundbedürfnisse geraten in vollen Tagen leicht aus dem Blick. Auf sie zu achten ist eine einfache Form von Fürsorge.",
            durationText = "1–5 Minuten",
        ),
        Impulse(
            "t13", DAILY, PERCEPTION, "Ein Moment Himmel",
            "Schau heute einmal bewusst aus dem Fenster oder nach oben in den Himmel. Was siehst du?",
            "Ein kurzer Blick in die Weite kann eine kleine Unterbrechung im Alltag sein.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t14", DAILY, SELF_COMPASSION, "Das darf schwer sein",
            "Wenn heute etwas schwer ist, sag dir: „Das ist gerade schwer – und es ist verständlich, dass es mich beschäftigt.“",
            "Schwieriges anzuerkennen, statt es wegzuschieben, kann entlasten. Es muss nicht gleich gelöst werden.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t15", DAILY, SELF_WORTH, "Eine Stärke im Alltag",
            "Bemerke eine Eigenschaft, die dir heute geholfen hat – zum Beispiel Geduld, Humor oder Neugier.",
            "Stärken zeigen sich oft in gewöhnlichen Momenten. Sie zu benennen, macht sie sichtbarer.",
            textPrompt = "Welche Eigenschaft war heute hilfreich?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t16", DAILY, BOUNDARIES, "Kurz überlegen dürfen",
            "Wenn dich heute jemand um etwas bittet, nimm dir die Freiheit, kurz zu überlegen, bevor du antwortest. Zum Beispiel: „Ich sage dir gleich Bescheid.“",
            "Ein kleiner Moment Bedenkzeit hilft, zu spüren, was du wirklich möchtest.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t17", DAILY, NEEDS, "Was tut mir heute gut?",
            "Überlege, was dir heute guttun könnte – und ob ein kleiner Teil davon möglich ist.",
            "Oft muss es nicht das Ganze sein. Schon ein kleiner Schritt in Richtung dessen, was dir guttut, zählt.",
            textPrompt = "Was könnte dir heute guttun?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t18", DAILY, PERCEPTION, "Ein Geschmack, ein Duft",
            "Nimm beim nächsten Getränk oder Essen den ersten Schluck oder Bissen ganz bewusst wahr.",
            "Bewusstes Wahrnehmen holt uns für einen Augenblick aus dem Gedankenkarussell.",
            durationText = "1 Minute",
        ),
        Impulse(
            "t19", DAILY, SELF_COMPASSION, "Unperfekt ist in Ordnung",
            "Erlaube dir heute, eine Sache nur „gut genug“ statt perfekt zu machen.",
            "Hohe Ansprüche können anstrengend sein. „Gut genug“ ist oft genau richtig.",
            textPrompt = "Was darf heute gut genug sein? (freiwillig)", durationText = "1–2 Minuten",
        ),
        Impulse(
            "t20", DAILY, SELF_WORTH, "Ein gelungener Moment",
            "Erinnere dich an einen Moment von heute oder gestern, der dir gelungen ist – auch wenn er klein war.",
            "Gelungenes geht im Alltag schnell unter. Es bewusst zu bemerken, kann dir einen freundlicheren Blick auf dich schenken.",
            textPrompt = "Was ist dir gelungen?", durationText = "1–3 Minuten",
        ),
        Impulse(
            "t21", DAILY, NEEDS, "Ein Gefühl benennen",
            "Frage dich: Welches Gefühl ist gerade da? Ein Wort genügt – zum Beispiel müde, ruhig, angespannt oder froh.",
            "Gefühle zu benennen kann helfen, sie besser zu verstehen. Jedes Gefühl darf da sein.",
            textPrompt = "Dein Wort für dieses Gefühl", durationText = "1 Minute",
        ),
        Impulse(
            "t22", DAILY, BOUNDARIES, "Etwas weglassen",
            "Gibt es heute eine Kleinigkeit auf deiner Liste, die du weglassen oder verschieben darfst?",
            "Nicht alles muss sofort erledigt werden. Etwas bewusst wegzulassen, schafft manchmal ein wenig Luft.",
            textPrompt = "Was darf heute warten? (freiwillig)", durationText = "1–2 Minuten",
        ),
    )
}
