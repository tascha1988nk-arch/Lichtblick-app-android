package de.lichtblickraum.app.reminders

import android.content.Context
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import de.lichtblickraum.core.reminders.ReminderScheduler
import de.lichtblickraum.core.reminders.ReminderSettings
import de.lichtblickraum.core.reminders.ReminderTiming
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

/**
 * Plant Erinnerungen mit WorkManager. Jede Erinnerung plant nach dem Auslösen die nächste,
 * so bleibt die gewählte Uhrzeit auch über Sommer-/Winterzeit hinweg erhalten.
 * WorkManager übersteht auch einen Neustart des Handys.
 */
class WorkReminderScheduler(private val context: Context) : ReminderScheduler {

    override fun apply(settings: ReminderSettings) {
        schedule(KIND_DAILY, settings)
        schedule(KIND_WEEKLY, settings)
    }

    fun schedule(kind: String, settings: ReminderSettings, now: ZonedDateTime = ZonedDateTime.now()) {
        try {
            val wm = WorkManager.getInstance(context)
            val enabled = if (kind == KIND_DAILY) settings.dailyEnabled else settings.weeklyEnabled
            if (!enabled) {
                wm.cancelUniqueWork(uniqueName(kind))
                return
            }
            val next = if (kind == KIND_DAILY) {
                ReminderTiming.nextDaily(now, settings.dailyTime)
            } else {
                ReminderTiming.nextWeekly(now, settings.weeklyDay, settings.weeklyTime)
            }
            val delay = Duration.between(now, next).toMillis().coerceAtLeast(0)
            val request = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(workDataOf(ReminderWorker.KEY_KIND to kind))
                .build()
            wm.enqueueUniqueWork(uniqueName(kind), ExistingWorkPolicy.REPLACE, request)
        } catch (e: Exception) {
            Log.w("Lichtblick", "Erinnerung konnte nicht geplant werden", e)
        }
    }

    companion object {
        const val KIND_DAILY = "taeglich"
        const val KIND_WEEKLY = "woechentlich"
        private fun uniqueName(kind: String) = "erinnerung_$kind"
    }
}
