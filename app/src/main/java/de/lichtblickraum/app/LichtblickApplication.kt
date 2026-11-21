package de.lichtblickraum.app

import android.app.Application
import de.lichtblickraum.app.data.FileJournalStore
import de.lichtblickraum.app.data.ProfileStore
import de.lichtblickraum.core.impulses.ImpulseTheme
import de.lichtblickraum.core.journal.EntryKind
import de.lichtblickraum.core.profile.Personalization
import de.lichtblickraum.core.profile.UserProfile
import de.lichtblickraum.app.data.SettingsStore
import de.lichtblickraum.core.impulses.ImpulsePlanner
import de.lichtblickraum.core.impulses.LichtblickeController
import de.lichtblickraum.core.journal.Journal
import de.lichtblickraum.app.reminders.ReminderStore
import de.lichtblickraum.app.reminders.WorkReminderScheduler
import de.lichtblickraum.core.reminders.ReminderSettings
import de.lichtblickraum.core.reminders.ReminderScheduler
import java.io.File
import java.time.LocalDate

/** Hält die app-weiten Objekte, solange die App läuft (überlebt z. B. das Drehen des Geräts). */
class AppContainer(app: Application) {
    val settings = SettingsStore(app)
    val journal = Journal(
        store = FileJournalStore(File(app.filesDir, "rueckschau.json")),
        persistent = settings.saveEntries.value,
    )
    val lichtblicke = LichtblickeController(ImpulsePlanner(), journal)

    /** Freiwillige Erinnerungen (standardmäßig aus). */
    val reminderStore = ReminderStore(app)
    val reminders: ReminderScheduler = WorkReminderScheduler(app)

    fun setReminders(settings: ReminderSettings) {
        reminderStore.save(settings)
        reminders.apply(settings)
    }

    /** Austauschbar, z. B. für Tests. */
    var today: () -> LocalDate = { LocalDate.now() }

    /** Eingangsfragen / persönliche Themen. */
    val profileStore = ProfileStore(app)

    fun saveProfile(profile: UserProfile) {
        val base = profileStore.profile.value.reviewInviteBase ?: today()
        profileStore.save(profile.copy(onboardingDone = true, reviewInviteBase = profile.reviewInviteBase ?: base))
    }

    /** Rückblick-Einladung: nächste in etwa einem Monat. */
    fun postponeReview() {
        profileStore.save(profileStore.profile.value.copy(reviewInviteBase = today()))
    }

    fun preferredThemes(): Set<ImpulseTheme> = Personalization.preferredThemes(profileStore.profile.value)

    fun addMood(level: Int, tags: List<String>, note: String?) {
        journal.add(EntryKind.MOOD, "barometer", "Gefühlsbarometer", today(), note, level = level, tags = tags)
    }

    fun setSaveEntries(enabled: Boolean) {
        settings.setSaveEntries(enabled)
        if (enabled) journal.enablePersistence() else journal.disablePersistence()
    }
}

class LichtblickApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
