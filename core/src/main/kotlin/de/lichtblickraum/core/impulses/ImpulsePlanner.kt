package de.lichtblickraum.core.impulses

import java.time.DayOfWeek
import java.time.LocalDate

/**
 * Wählt den Impuls für einen Tag bzw. eine Woche.
 *
 * - Die Impulse laufen in "Runden": In jeder Runde kommt jeder Impuls genau einmal vor,
 *   jede Runde in einer neuen, gemischten Reihenfolge.
 * - Am Übergang zweier Runden wiederholt sich nichts kurz hintereinander.
 * - Am selben Tag erscheint immer derselbe Impuls (solange nicht ausgetauscht wurde).
 * - "Andere Aufgabe" erhöht `swapCount`; so werden nacheinander alle Impulse erreichbar.
 * - Kürzlich erledigte Impulse (`recentlyDone`) werden übersprungen.
 * - Es gibt keine Serien, Zähler für verpasste Tage oder Ähnliches.
 *
 * Hinweis: Kommen neue Impulse hinzu, ändert sich die Reihenfolge insgesamt – das ist gewollt.
 */
class ImpulsePlanner(
    private val daily: List<Impulse> = DailyImpulses.all,
    private val weekly: List<Impulse> = WeeklyImpulses.all,
) {
    init {
        require(daily.isNotEmpty() && weekly.isNotEmpty())
    }

    /**
     * @param preferred Themen aus den Eingangsfragen. Sie kommen an zwei von drei Tagen,
     *                  die übrigen Themen am dritten Tag – so bleibt Abwechslung.
     */
    fun dailyFor(
        date: LocalDate,
        swapCount: Int = 0,
        recentlyDone: Set<String> = emptySet(),
        preferred: Set<ImpulseTheme> = emptySet(),
    ): Impulse {
        val pref = daily.filter { it.theme in preferred }
        val other = daily.filter { it.theme !in preferred }
        val day = date.toEpochDay()
        if (pref.isEmpty() || other.isEmpty()) return pick(daily, day, swapCount, recentlyDone, SALT_DAILY)
        val q = Math.floorDiv(day, 3L)
        val r = Math.floorMod(day, 3L)
        val (pool, slot, salt) = if (r < 2) Triple(pref, q * 2 + r, SALT_PREF) else Triple(other, q, SALT_OTHER)
        val chosen = pick(pool, slot, swapCount, recentlyDone, salt)
        return if (chosen.id in recentlyDone) pick(daily, day, swapCount, recentlyDone, SALT_DAILY) else chosen
    }

    fun weeklyFor(date: LocalDate, swapCount: Int = 0, recentlyDone: Set<String> = emptySet()): Impulse =
        pick(weekly, weekKey(date), swapCount, recentlyDone, SALT_WEEKLY)

    companion object {
        private const val SALT_DAILY = 0x5EEDL
        private const val SALT_WEEKLY = 0x3EEDL
        private const val SALT_PREF = 0x7EEDL
        private const val SALT_OTHER = 0x9EEDL

        /** Allgemeine Rotation (z. B. für Texte oder Übungen): jede Runde neu gemischt. */
        fun <T> rotate(list: List<T>, slot: Long, salt: Long): T {
            require(list.isNotEmpty())
            val n = list.size
            val order = roundOrder(n, Math.floorDiv(slot, n.toLong()), salt)
            return list[order[Math.floorMod(slot, n.toLong()).toInt()]]
        }

        /** So viele Tage zählt ein erledigter Tagesimpuls als "kürzlich". */
        const val RECENT_DAYS = 21L

        /** So viele Wochen zählt ein erledigter Wochenimpuls als "kürzlich". */
        const val RECENT_WEEKS = 4L

        /** Gleicher Wert für alle Tage derselben Woche (Montag bis Sonntag). */
        fun weekKey(date: LocalDate): Long = Math.floorDiv(date.with(DayOfWeek.MONDAY).toEpochDay(), 7L)

        /** Montag der Woche – dient als Datum für Wochen-Einträge. */
        fun weekStart(date: LocalDate): LocalDate = date.with(DayOfWeek.MONDAY)

        /** Abstand am Rundenübergang, in dem sich nichts wiederholen darf. */
        internal fun guard(n: Int): Int = if (n < 4) 0 else minOf(10, n / 3)

        internal fun <T> pick(list: List<T>, slot: Long, swapCount: Int, recent: Set<String>, salt: Long): T {
            val n = list.size
            val order = roundOrder(n, Math.floorDiv(slot, n.toLong()), salt)
            val pos = Math.floorMod(slot, n.toLong()).toInt()
            val step = swapStep(n)
            // Bei kürzlich Erledigtem einfach weiterrücken – höchstens einmal rundherum.
            for (extra in 0 until n) {
                val p = Math.floorMod(pos + (swapCount.toLong() + extra) * step, n.toLong()).toInt()
                val candidate = list[order[p]]
                if (recent.isEmpty() || (candidate as? Impulse)?.id !in recent) return candidate
            }
            return list[order[Math.floorMod(pos + swapCount.toLong() * step, n.toLong()).toInt()]]
        }

        /**
         * Reihenfolge (Indizes 0..n-1) für eine Runde. Die ersten [guard] Plätze enthalten
         * nichts, was in den letzten [guard] Plätzen der Vorrunde stand.
         */
        internal fun roundOrder(n: Int, round: Long, salt: Long): IntArray {
            val order = shuffled(n, round, salt)
            val g = guard(n)
            if (g == 0) return order
            val prevTail = shuffled(n, round - 1, salt).takeLast(g).toSet()
            // Nur Kopf (0 until g) und Mitte (g until n-g) werden getauscht, das Ende bleibt
            // unverändert – so bleibt die Regel auch für die nächste Runde gültig.
            var swapWith = g
            for (i in 0 until g) {
                if (order[i] !in prevTail) continue
                while (swapWith < n - g && order[swapWith] in prevTail) swapWith++
                if (swapWith >= n - g) break
                val t = order[i]; order[i] = order[swapWith]; order[swapWith] = t
                swapWith++
            }
            return order
        }

        /** Gemischte Reihenfolge, für dieselbe Runde immer gleich (eigener Zufallsgenerator). */
        private fun shuffled(n: Int, round: Long, salt: Long): IntArray {
            val a = IntArray(n) { it }
            var state = round * -0x61c8864680b583ebL + salt
            fun next(): Long {
                state += -0x61c8864680b583ebL
                var z = state
                z = (z xor (z ushr 30)) * -0x40a7b892e31b1a47L
                z = (z xor (z ushr 27)) * -0x6b2fb644ecceee15L
                return z xor (z ushr 31)
            }
            for (i in n - 1 downTo 1) {
                val j = Math.floorMod(next(), (i + 1).toLong()).toInt()
                val t = a[i]; a[i] = a[j]; a[j] = t
            }
            return a
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
