package de.lichtblickraum.core.impulses

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Wählt den Impuls für einen Tag bzw. eine Woche.
 *
 * - Am selben Tag erscheint immer derselbe Impuls (solange nicht ausgetauscht wurde).
 * - "Andere Aufgabe" erhöht [swapCount]; so werden nacheinander alle Impulse erreichbar.
 * - Es gibt keine Serien, Zähler für verpasste Tage oder Ähnliches.
 */
class ImpulsePlanner(
    private val daily: List<Impulse> = DailyImpulses.all,
    private val weekly: List<Impulse> = WeeklyImpulses.all,
) {
    init {
        require(daily.isNotEmpty() && weekly.isNotEmpty())
    }

    fun dailyFor(date: LocalDate, swapCount: Int = 0): Impulse =
        pick(daily, date.toEpochDay(), swapCount)

    fun weeklyFor(date: LocalDate, swapCount: Int = 0): Impulse =
        pick(weekly, weekKey(date), swapCount)

    companion object {
        /** Gleicher Wert für alle Tage derselben Woche (Montag bis Sonntag). */
        fun weekKey(date: LocalDate): Long = date.with(DayOfWeek.MONDAY).toEpochDay() / 7

        /** Montag der Woche – dient als Datum für Wochen-Einträge. */
        fun weekStart(date: LocalDate): LocalDate = date.with(DayOfWeek.MONDAY)

        internal fun <T> pick(list: List<T>, key: Long, swapCount: Int): T {
            val n = list.size
            val base = Math.floorMod(key, n.toLong()).toInt()
            val step = swapStep(n)
            return list[Math.floorMod(base + swapCount.toLong() * step, n.toLong()).toInt()]
        }

        /** Schrittweite für "Andere Aufgabe": teilerfremd zu n, damit jeder Impuls erreichbar ist. */
        internal fun swapStep(n: Int): Int {
            if (n <= 2) return 1
            var s = maxOf(1, n / 3)
            while (gcd(s, n) != 1) s++
            return s
        }

        private tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
    }
}
