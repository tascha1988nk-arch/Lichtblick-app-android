package de.lichtblickraum.core.profile

import de.lichtblickraum.core.exercises.Exercise
import de.lichtblickraum.core.exercises.ExerciseCatalog
import de.lichtblickraum.core.impulses.ImpulsePlanner
import de.lichtblickraum.core.impulses.ImpulseTheme
import java.time.LocalDate

/** Persönliche Auswahl – ohne Programm, ohne Fortschritt, ohne Pflicht. */
object Personalization {

    /** Etwa einmal im Monat lädt die App sanft zu einem Rückblick ein. */
    const val REVIEW_INTERVAL_DAYS = 30L

    fun preferredThemes(profile: UserProfile): Set<ImpulseTheme> =
        profile.focus.flatMap { it.impulseThemes }.toSet()

    /** Übungen zu den gewählten Themen; bei "kurz" zuerst die kurzen. Ohne Themen: alle. */
    fun suggestedExercises(profile: UserProfile): List<Exercise> {
        val base = if (profile.focus.isEmpty()) {
            ExerciseCatalog.all
        } else {
            val cats = profile.focus.map { it.exerciseCategory }.toSet()
            ExerciseCatalog.all.filter { it.category in cats }
        }
        val fitting = base.filter { it.minutes <= profile.time.maxMinutes }
        return (fitting + (base - fitting.toSet()))
    }

    /** Eine Übung für heute – wechselt täglich innerhalb der passenden Übungen. */
    fun exerciseForDay(profile: UserProfile, date: LocalDate): Exercise {
        val list = suggestedExercises(profile).let { all ->
            all.filter { it.minutes <= profile.time.maxMinutes }.ifEmpty { all }
        }
        return ImpulsePlanner.rotate(list, date.toEpochDay(), 0x0E5EL)
    }

    fun reviewInviteDue(profile: UserProfile, today: LocalDate): Boolean {
        val base = profile.reviewInviteBase ?: return false
        return profile.onboardingDone && !today.isBefore(base.plusDays(REVIEW_INTERVAL_DAYS))
    }
}
