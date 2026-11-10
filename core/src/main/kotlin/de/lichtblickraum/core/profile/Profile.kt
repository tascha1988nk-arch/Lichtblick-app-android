package de.lichtblickraum.core.profile

import de.lichtblickraum.core.exercises.ExerciseCategory
import de.lichtblickraum.core.impulses.ImpulseTheme
import java.time.LocalDate

/**
 * Themen, die Nutzer in den Eingangsfragen wählen können.
 * Jedes Thema steuert Übungen, bevorzugte Lichtblicke und die Texte für heute.
 */
enum class Focus(
    val title: String,
    val description: String,
    val exerciseCategory: ExerciseCategory,
    val impulseThemes: Set<ImpulseTheme>,
) {
    SELF_WORTH(
        "Selbstwert", "Mich selbst freundlicher sehen",
        ExerciseCategory.SELF_WORTH, setOf(ImpulseTheme.SELF_WORTH, ImpulseTheme.SELF_RELATIONSHIP),
    ),
    BOUNDARIES(
        "Grenzen", "Leichter spüren und sagen, was ich (nicht) möchte",
        ExerciseCategory.BOUNDARIES, setOf(ImpulseTheme.BOUNDARIES),
    ),
    COUPLE(
        "Partnerschaft", "Auf meine Beziehung schauen",
        ExerciseCategory.COUPLE, setOf(ImpulseTheme.NEEDS, ImpulseTheme.SELF_RELATIONSHIP),
    ),
    ADHD(
        "ADHS im Alltag", "Mehr Ruhe und Verständnis für einen vollen Kopf",
        ExerciseCategory.ADHD, setOf(ImpulseTheme.NEEDS, ImpulseTheme.PERCEPTION),
    ),
    EMOTIONS(
        "Gefühle & Stress", "Besser mit großen Gefühlen umgehen",
        ExerciseCategory.EMOTIONS, setOf(ImpulseTheme.SELF_COMPASSION, ImpulseTheme.PERCEPTION),
    ),
}

enum class TimePreference(val label: String, val maxMinutes: Int) {
    SHORT("Meistens kurz – etwa 2 Minuten", 5),
    LONGER("Gern etwas mehr – 5 bis 10 Minuten", 60),
}

/**
 * Antworten aus den Eingangsfragen. Alles freiwillig, jederzeit änderbar,
 * nur auf dem Gerät gespeichert. Keine Diagnose, nur Orientierung für die App.
 */
data class UserProfile(
    val onboardingDone: Boolean = false,
    val focus: Set<Focus> = emptySet(),
    /** "Dein Kompass": der eigene Wunsch in eigenen Worten (optional). */
    val wish: String? = null,
    val time: TimePreference = TimePreference.SHORT,
    /** Ab diesem Datum wird die nächste Rückblick-Einladung berechnet. */
    val reviewInviteBase: LocalDate? = null,
)
