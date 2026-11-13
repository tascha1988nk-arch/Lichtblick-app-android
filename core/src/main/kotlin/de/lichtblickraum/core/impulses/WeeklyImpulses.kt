package de.lichtblickraum.core.impulses

import de.lichtblickraum.core.impulses.ImpulseCadence.WEEKLY
import de.lichtblickraum.core.impulses.ImpulseTheme.BOUNDARIES
import de.lichtblickraum.core.impulses.ImpulseTheme.NEEDS
import de.lichtblickraum.core.impulses.ImpulseTheme.PERCEPTION
import de.lichtblickraum.core.impulses.ImpulseTheme.SELF_COMPASSION
import de.lichtblickraum.core.impulses.ImpulseTheme.SELF_WORTH

/** Freiwillige Wochenimpulse. Neue Impulse einfach unten anhängen (eindeutige ID!). */
object WeeklyImpulses {

    val all: List<Impulse> = listOf(
        Impulse(
            "w01", WEEKLY, NEEDS, "Ein Moment nur für dich",
            "Plane in dieser Woche einen kleinen Moment, der nur dir gehört – zum Beispiel einen Spaziergang, ein Bad oder eine Tasse Tee in Ruhe.",
            "Zeit für dich darf einen festen Platz bekommen, statt nur übrig zu bleiben.",
            textPrompt = "Was planst du – und wann?", durationText = "über die Woche",
        ),
        Impulse(
            "w02", WEEKLY, BOUNDARIES, "Ja gesagt, Nein gemeint",
            "Beobachte eine Situation, in der du Ja sagen möchtest, obwohl du Nein meinst. Aufschreiben genügt zunächst.",
            "Solche Momente zu bemerken, ist ein wichtiger erster Schritt. Du musst noch nichts anders machen.",
            textPrompt = "Was hast du beobachtet?", durationText = "über die Woche",
        ),
        Impulse(
            "w03", WEEKLY, SELF_COMPASSION, "Ein wohlwollender Brief",
            "Schreibe dir einen kurzen Brief aus der Sicht eines wohlwollenden Menschen – real oder vorgestellt.",
            "Ein freundlicher Blick von außen kann helfen, uns selbst mit mehr Verständnis zu sehen.",
            textPrompt = "Dein Brief", durationText = "ca. 10 Minuten",
        ),
        Impulse(
            "w04", WEEKLY, SELF_WORTH, "Drei Eigenschaften",
            "Sammle in dieser Woche drei Eigenschaften, die du an dir schätzt.",
            "Du kannst sie nach und nach aufschreiben, wenn sie dir im Alltag begegnen.",
            textPrompt = "Deine drei Eigenschaften", durationText = "über die Woche",
        ),
        Impulse(
            "w05", WEEKLY, PERCEPTION, "Wochenrückblick",
            "Blicke auf die Woche zurück: Was hat dir gutgetan, und wovon wünschst du dir weniger?",
            "Ein ruhiger Rückblick kann helfen, die nächste Woche ein wenig bewusster zu gestalten.",
            textPrompt = "Was tat gut? Wovon weniger?", durationText = "ca. 5–10 Minuten",
        ),
        Impulse(
            "w06", WEEKLY, NEEDS, "Energiegeber und Energieräuber",
            "Achte in dieser Woche darauf, welche Tätigkeiten oder Begegnungen dir Kraft geben und welche eher Kraft kosten.",
            "Es geht nicht darum, sofort etwas zu ändern, sondern darum, dich selbst besser kennenzulernen.",
            textPrompt = "Was gibt dir Kraft, was kostet Kraft?", durationText = "über die Woche",
        ),
        Impulse(
            "w07", WEEKLY, SELF_COMPASSION, "Etwas Schönes ohne Zweck",
            "Tu in dieser Woche einmal etwas, das dir Freude macht, ohne dass es nützlich sein muss.",
            "Freude braucht keine Begründung. Sie darf einfach Platz haben.",
            textPrompt = "Was hast du dir gegönnt? (freiwillig)", durationText = "über die Woche",
        ),
        Impulse(
            "w08", WEEKLY, BOUNDARIES, "Ein Ort zum Auftanken",
            "Überlege, wo du dich gut erholen kannst – ein Ort, ein Raum, eine Ecke. Vielleicht findest du in dieser Woche einen Moment dort.",
            "Zu wissen, wo du auftanken kannst, ist hilfreich – gerade an vollen Tagen.",
            textPrompt = "Dein Ort zum Auftanken", durationText = "über die Woche",
        ),
        Impulse(
            "w09", WEEKLY, SELF_WORTH, "Was ich gut gemeistert habe",
            "Notiere am Ende der Woche eine Sache, die du gemeistert hast – auch wenn sie unspektakulär war.",
            "Auch das Bewältigen von Alltag ist eine Leistung, die gesehen werden darf.",
            textPrompt = "Was hast du gemeistert?", durationText = "ca. 5 Minuten",
        ),
    )
}
