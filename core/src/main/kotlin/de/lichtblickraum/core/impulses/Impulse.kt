package de.lichtblickraum.core.impulses

enum class ImpulseCadence { DAILY, WEEKLY }

enum class ImpulseTheme(val label: String) {
    SELF_WORTH("Selbstwert"),
    SELF_COMPASSION("Selbstmitgefühl"),
    SELF_RELATIONSHIP("Beziehung zu dir"),
    BOUNDARIES("Grenzen"),
    NEEDS("Bedürfnisse"),
    PERCEPTION("Wahrnehmung"),
}

/**
 * Ein kleiner Impuls.
 * @param task       die eigentliche Einladung
 * @param explanation kurze Erklärung, warum das guttun kann
 * @param textPrompt wenn gesetzt, erscheint ein freiwilliges Textfeld mit diesem Hinweis
 */
data class Impulse(
    val id: String,
    val cadence: ImpulseCadence,
    val theme: ImpulseTheme,
    val title: String,
    val task: String,
    val explanation: String,
    val textPrompt: String? = null,
    val durationText: String,
)
