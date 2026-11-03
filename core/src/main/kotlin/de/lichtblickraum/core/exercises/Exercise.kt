package de.lichtblickraum.core.exercises

/** Ein Schritt einer Übung. Mit [inputPrompt] erscheint ein freiwilliges Textfeld. */
data class ExerciseStep(
    val title: String,
    val text: String,
    val inputPrompt: String? = null,
)

data class Exercise(
    val id: String,
    val title: String,
    val subtitle: String,
    val durationText: String,
    val intro: String,
    val steps: List<ExerciseStep>,
    val closing: String,
)
