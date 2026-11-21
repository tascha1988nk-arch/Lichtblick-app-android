package de.lichtblickraum.core.impulses

/** Kurze, freundliche Rückmeldungen nach einem Impuls – bewusst ohne überschwängliches Lob. */
object Feedback {

    val afterDone: List<String> = listOf(
        "Danke, dass du dir diesen Moment genommen hast.",
        "Schön, dass du bei dir vorbeigeschaut hast.",
        "Ein kleiner Moment für dich – der zählt.",
        "Vielleicht nimmst du etwas davon mit in deinen Tag.",
        "Gut, dass du dir das gegönnt hast.",
    )

    const val AFTER_SKIP: String =
        "Heute ausgelassen – das ist völlig in Ordnung. Du kannst jederzeit wieder vorbeischauen."

    const val PAUSED: String =
        "Die kleinen Lichtblicke pausieren gerade. Nimm dir die Zeit, die du brauchst. " +
            "Wenn du magst, kannst du jederzeit wieder einsteigen – ohne etwas nachholen zu müssen."

    const val WELCOME_BACK: String =
        "Schön, dass du da bist. Hier ist ein kleiner Impuls für heute – ganz ohne Verpflichtung."

    fun forDay(dayKey: Long): String =
        afterDone[Math.floorMod(dayKey, afterDone.size.toLong()).toInt()]
}
