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
        note = "Viele Übungen kannst du für dich allein machen – du musst nichts davon mit deinem " +
            "Partner oder deiner Partnerin besprechen. Übungen, die für euch zu zweit gedacht sind, " +
            "sind mit „Zu zweit“ markiert. Macht sie nur, wenn ihr beide Lust darauf habt.",
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
    CALMING(
        "Beruhigen & Halt finden",
        "Angeleitete Übungen mit ruhigen Klängen – behutsam und traumasensibel.",
        note = "Diese Übungen helfen dir, dich zu beruhigen und Halt zu finden. Du entscheidest, was du " +
            "tust: Die Augen dürfen offen bleiben, und du kannst jederzeit aufhören. Die Übungen " +
            "ersetzen keine Traumatherapie. Wenn alte Erinnerungen sehr stark werden, hol dir bitte Unterstützung.",
    ),
}

/** Ein Schritt einer Übung. Mit [inputPrompt] erscheint ein freiwilliges Textfeld. */
data class ExerciseStep(
    val title: String,
    val text: String,
    val inputPrompt: String? = null,
    /** Nur bei angeleiteten Übungen: Pause nach dem Vorlesen, in Sekunden. */
    val pauseSeconds: Int = 0,
)

data class Exercise(
    val id: String,
    val category: ExerciseCategory,
    val title: String,
    val subtitle: String,
    val durationText: String,
    /** Ungefähre Dauer in Minuten (für "Wie viel Zeit hast du?"). */
    val minutes: Int = 5,
    /** true = Übung für zwei Personen (z. B. Paare). */
    val together: Boolean = false,
    /** true = angeleitete Übung mit Vorlesestimme und Hintergrundklang. */
    val guided: Boolean = false,
    val intro: String,
    val steps: List<ExerciseStep>,
    val closing: String,
)
