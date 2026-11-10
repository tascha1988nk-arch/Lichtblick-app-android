package de.lichtblickraum.app.reminders

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import de.lichtblickraum.app.LichtblickApplication
import de.lichtblickraum.app.MainActivity
import de.lichtblickraum.app.R
import de.lichtblickraum.core.impulses.ImpulseStatus
import de.lichtblickraum.core.reminders.ReminderTexts
import java.time.ZonedDateTime

/**
 * Zeigt eine sanfte Erinnerung – aber nur, wenn der Impuls noch offen ist und die
 * kleinen Lichtblicke nicht pausiert sind. Plant danach die nächste Erinnerung.
 */
class ReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val kind = inputData.getString(KEY_KIND) ?: return Result.success()
        val container = (applicationContext as LichtblickApplication).container
        val settings = container.reminderStore.settings.value

        val paused = container.settings.impulsesPaused.value
        val today = container.today()
        val state = container.lichtblicke.state(today, container.lichtblicke.choices.value, container.journal.entries.value)
        val card = if (kind == WorkReminderScheduler.KIND_DAILY) state.today else state.week
        if (!paused && card.status == ImpulseStatus.OPEN) {
            show(applicationContext, kind)
        }
        // Nächste Erinnerung planen (eine Minute Abstand, damit nichts doppelt auslöst).
        (container.reminders as? WorkReminderScheduler)
            ?.schedule(kind, settings, ZonedDateTime.now().plusMinutes(1))
        return Result.success()
    }

    companion object {
        const val KEY_KIND = "art"
        const val CHANNEL_ID = "lichtblicke_erinnerungen"
        const val EXTRA_TARGET = "ziel"
        const val TARGET_LICHTBLICKE = "lichtblicke"

        fun ensureChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANNEL_ID, ReminderTexts.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                channel.description = ReminderTexts.CHANNEL_DESCRIPTION
                context.getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
            }
        }

        fun canNotify(context: Context): Boolean {
            val permitted = Build.VERSION.SDK_INT < 33 ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            return permitted && NotificationManagerCompat.from(context).areNotificationsEnabled()
        }

        fun show(context: Context, kind: String) {
            if (!canNotify(context)) return
            ensureChannel(context)
            val intent = Intent(context, MainActivity::class.java)
                .putExtra(EXTRA_TARGET, TARGET_LICHTBLICKE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pending = PendingIntent.getActivity(
                context, kind.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
            val text = if (kind == WorkReminderScheduler.KIND_DAILY) ReminderTexts.DAILY else ReminderTexts.WEEKLY
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(0xFFA27330.toInt())
                .setContentTitle(ReminderTexts.TITLE)
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .setContentIntent(pending)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            try {
                NotificationManagerCompat.from(context).notify(kind.hashCode(), notification)
            } catch (e: SecurityException) {
                // Berechtigung wurde zwischenzeitlich entzogen – dann einfach nichts anzeigen.
            }
        }
    }
}
