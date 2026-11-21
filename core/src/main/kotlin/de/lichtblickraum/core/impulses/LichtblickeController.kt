package de.lichtblickraum.core.impulses

import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.journal.Journal
import de.lichtblickraum.core.journal.JournalEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

/** Was in dieser Sitzung ausgetauscht oder ausgelassen wurde (nur im Arbeitsspeicher). */
data class ImpulseChoices(
    val dailySwaps: Map<LocalDate, Int> = emptyMap(),
    val weeklySwaps: Map<Long, Int> = emptyMap(),
    val skippedDays: Set<LocalDate> = emptySet(),
    val skippedWeeks: Set<Long> = emptySet(),
)

enum class ImpulseStatus { OPEN, DONE, SKIPPED }

data class ImpulseCard(val impulse: Impulse, val status: ImpulseStatus, val feedback: String?)

data class LichtblickeState(val today: ImpulseCard, val week: ImpulseCard)

/**
 * Steuert "Dein Lichtblick für heute" und "Dein Wochenimpuls".
 * Bewusst ohne Serien, Punkte oder Hinweise auf verpasste Tage.
 */
class LichtblickeController(
    private val planner: ImpulsePlanner,
    private val journal: Journal,
) {
    private val _choices = MutableStateFlow(ImpulseChoices())
    val choices: StateFlow<ImpulseChoices> = _choices.asStateFlow()

    fun state(
        today: LocalDate,
        choices: ImpulseChoices,
        entries: List<JournalEntry>,
        preferredThemes: Set<ImpulseTheme> = emptySet(),
    ): LichtblickeState {
        val week = ImpulsePlanner.weekKey(today)
        // Kürzlich erledigte Impulse (ohne heute bzw. diese Woche) nicht gleich wieder zeigen.
        val recentDaily = entries
            .filter { it.kind == EntryKind.DAILY && it.date < today && it.date >= today.minusDays(ImpulsePlanner.RECENT_DAYS) }
            .map { it.itemId }.toSet()
        val recentWeekly = entries
            .filter {
                it.kind == EntryKind.WEEKLY && ImpulsePlanner.weekKey(it.date).let { w -> w < week && w >= week - ImpulsePlanner.RECENT_WEEKS }
            }
            .map { it.itemId }.toSet()
        val daily = planner.dailyFor(today, choices.dailySwaps[today] ?: 0, recentDaily, preferredThemes)
        val weekly = planner.weeklyFor(today, choices.weeklySwaps[week] ?: 0, recentWeekly)

        val dailyDone = entries.any { it.kind == EntryKind.DAILY && it.date == today }
        val weeklyDone = entries.any { it.kind == EntryKind.WEEKLY && ImpulsePlanner.weekKey(it.date) == week }

        return LichtblickeState(
            today = ImpulseCard(
                daily,
                when {
                    dailyDone -> ImpulseStatus.DONE
                    today in choices.skippedDays -> ImpulseStatus.SKIPPED
                    else -> ImpulseStatus.OPEN
                },
                if (dailyDone) Feedback.forDay(today.toEpochDay()) else null,
            ),
            week = ImpulseCard(
                weekly,
                when {
                    weeklyDone -> ImpulseStatus.DONE
                    week in choices.skippedWeeks -> ImpulseStatus.SKIPPED
                    else -> ImpulseStatus.OPEN
                },
                if (weeklyDone) Feedback.forDay(week + 2) else null,
            ),
        )
    }

    fun swapDaily(today: LocalDate) = _choices.value.let { c ->
        _choices.value = c.copy(dailySwaps = c.dailySwaps + (today to (c.dailySwaps[today] ?: 0) + 1))
    }

    fun swapWeekly(today: LocalDate) = _choices.value.let { c ->
        val w = ImpulsePlanner.weekKey(today)
        _choices.value = c.copy(weeklySwaps = c.weeklySwaps + (w to (c.weeklySwaps[w] ?: 0) + 1))
    }

    fun skipDaily(today: LocalDate) {
        _choices.value = _choices.value.let { it.copy(skippedDays = it.skippedDays + today) }
    }

    fun skipWeekly(today: LocalDate) {
        _choices.value = _choices.value.let { it.copy(skippedWeeks = it.skippedWeeks + ImpulsePlanner.weekKey(today)) }
    }

    /** "Doch anschauen" nach dem Auslassen – jederzeit möglich. */
    fun reopenDaily(today: LocalDate) {
        _choices.value = _choices.value.let { it.copy(skippedDays = it.skippedDays - today) }
    }

    fun reopenWeekly(today: LocalDate) {
        _choices.value = _choices.value.let { it.copy(skippedWeeks = it.skippedWeeks - ImpulsePlanner.weekKey(today)) }
    }

    fun completeDaily(today: LocalDate, impulse: Impulse, note: String?): JournalEntry =
        journal.add(EntryKind.DAILY, impulse.id, impulse.title, today, note)

    fun completeWeekly(today: LocalDate, impulse: Impulse, note: String?): JournalEntry =
        journal.add(EntryKind.WEEKLY, impulse.id, impulse.title, today, note)
}
