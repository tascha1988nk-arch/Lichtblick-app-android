package de.lichtblickraum.core.exercises

/**
 * Kategorien der Übungen. Reihenfolge hier = Reihenfolge in der App.
 * @param note optionaler Hinweis, der über den Übungen der Kategorie steht
 */
enum class ExerciseCategory(val title: String, val description: String, val note: String? = null) {
    SELF_WORTH(
        "Selbstwert",
        "Dich selbst freundlicher sehen – unabhängig von Leistung.",
    ),
    BOUNDARIES(
        "Grenzen",
        "Spüren, was für dich stimmt – und was nicht.",
    ),
    COUPLE(
        "Paarthemen",
        "Für dich allein: auf deine Beziehung schauen, ohne etwas klären zu müssen.",
        note = "Diese Übungen machst du für dich allein. Du musst nichts davon mit deinem Partner " +
            "oder deiner Partnerin besprechen. Wenn du dich in deiner Beziehung nicht sicher fühlst, " +
            "sind diese Übungen gerade nicht das Richtige – dann hol dir bitte Unterstützung, zum " +
            "Beispiel beim Hilfetelefon „Gewalt gegen Frauen“ unter 116 016 (kostenfrei, rund um die Uhr).",
    ),
    ADHD(
        "ADHS im Alltag",
        "Kleine Hilfen für einen vollen Kopf – mit viel Verständnis für dich.",
        note = "Diese Übungen sind alltagspraktische Anregungen. Sie ersetzen keine Diagnostik, " +
            "Behandlung oder ärztliche Beratung.",
    ),
    EMOTIONS(
        "Gefühle & Stress",
        "Wenn es innen gerade viel ist: ankommen, benennen, begleiten.",
    ),
}

/** Ein Schritt einer Übung. Mit [inputPrompt] erscheint ein freiwilliges Textfeld. */
data class ExerciseStep(
    val title: String,
    val text: String,
    val inputPrompt: String? = null,
)

data class Exercise(
    val id: String,
    val category: ExerciseCategory,
    val title: String,
    val subtitle: String,
    val durationText: String,
    val intro: String,
    val steps: List<ExerciseStep>,
    val closing: String,
)
