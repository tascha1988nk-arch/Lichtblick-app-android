package de.lichtblickraum.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

object Routes {
    const val HOME = "start"
    const val SELF_TESTS = "selbsttests"
    const val EXERCISES = "uebungen"
    const val LICHTBLICKE = "lichtblicke"
    const val ABOUT = "ueber"

    const val SELF_TEST = "selbsttest/{id}"
    const val EXERCISE = "uebung/{id}"
    const val EXERCISE_CATEGORY = "uebungen/thema/{name}"
    const val REVIEW = "rueckschau"
    const val LEGAL = "rechtliches/{type}"

    fun selfTest(id: String) = "selbsttest/$id"
    fun exercise(id: String) = "uebung/$id"
    fun exerciseCategory(name: String) = "uebungen/thema/$name"
    fun legal(type: String) = "rechtliches/$type"

    const val LEGAL_IMPRINT = "impressum"
    const val LEGAL_PRIVACY = "datenschutz"
}

enum class TopLevel(val route: String, val label: String, val icon: ImageVector) {
    HOME(Routes.HOME, "Start", Icons.Filled.Home),
    SELF_TESTS(Routes.SELF_TESTS, "Selbsttests", Icons.AutoMirrored.Filled.List),
    EXERCISES(Routes.EXERCISES, "Übungen", Icons.Filled.Favorite),
    LICHTBLICKE(Routes.LICHTBLICKE, "Lichtblicke", Icons.Filled.Star),
    ABOUT(Routes.ABOUT, "Über", Icons.Filled.Person),
}
