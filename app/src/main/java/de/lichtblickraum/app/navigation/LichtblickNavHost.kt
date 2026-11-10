package de.lichtblickraum.app.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.lichtblickraum.app.AppContainer
import de.lichtblickraum.app.ui.about.AboutScreen
import de.lichtblickraum.app.ui.about.LegalScreen
import de.lichtblickraum.app.ui.exercises.ExerciseListScreen
import de.lichtblickraum.app.ui.exercises.ExerciseScreen
import de.lichtblickraum.app.ui.home.HomeScreen
import de.lichtblickraum.app.ui.lichtblicke.LichtblickeScreen
import de.lichtblickraum.app.ui.lichtblicke.ReviewScreen
import de.lichtblickraum.app.ui.selftest.SelfTestListScreen
import de.lichtblickraum.app.ui.selftest.SelfTestScreen
import de.lichtblickraum.app.ui.theme.LichtblickColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LichtblickRoot(
    container: AppContainer,
    navController: NavHostController = rememberNavController(),
    openLichtblicke: Boolean = false,
) {
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val showBottomBar = TopLevel.entries.any { it.route == currentRoute }

    fun goTopLevel(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        containerColor = LichtblickColors.Cream,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = LichtblickColors.CreamDeep) {
                    TopLevel.entries.forEach { item ->
                        val selected = backStack?.destination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = { goTopLevel(item.route) },
                            modifier = Modifier.testTag("nav_${item.route}"),
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = LichtblickColors.GoldDark,
                                selectedTextColor = LichtblickColors.Text,
                                indicatorColor = LichtblickColors.GoldSoft,
                                unselectedIconColor = LichtblickColors.TextMuted,
                                unselectedTextColor = LichtblickColors.TextMuted,
                            ),
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding).consumeWindowInsets(padding),
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onOpenSelfTests = { goTopLevel(Routes.SELF_TESTS) },
                    onOpenExercises = { goTopLevel(Routes.EXERCISES) },
                    onOpenLichtblicke = { goTopLevel(Routes.LICHTBLICKE) },
                    onOpenAbout = { goTopLevel(Routes.ABOUT) },
                )
            }
            composable(Routes.SELF_TESTS) {
                SelfTestListScreen(onStart = { navController.navigate(Routes.selfTest(it)) })
            }
            composable(Routes.SELF_TEST) { entry ->
                SelfTestScreen(
                    questionnaireId = entry.arguments?.getString("id").orEmpty(),
                    onExit = { navController.popBackStack() },
                )
            }
            composable(Routes.EXERCISES) {
                ExerciseListScreen(onStart = { navController.navigate(Routes.exercise(it)) })
            }
            composable(Routes.EXERCISE) { entry ->
                ExerciseScreen(
                    exerciseId = entry.arguments?.getString("id").orEmpty(),
                    container = container,
                    onExit = { navController.popBackStack() },
                )
            }
            composable(Routes.LICHTBLICKE) {
                LichtblickeScreen(container = container, onOpenReview = { navController.navigate(Routes.REVIEW) })
            }
            composable(Routes.REVIEW) {
                ReviewScreen(container = container, onBack = { navController.popBackStack() })
            }
            composable(Routes.ABOUT) {
                AboutScreen(onOpenLegal = { navController.navigate(Routes.legal(it)) })
            }
            composable(Routes.LEGAL) { entry ->
                LegalScreen(
                    type = entry.arguments?.getString("type").orEmpty(),
                    onBack = { navController.popBackStack() },
                )
            }
        }
        // Nach dem Aufbau der Navigation: ggf. direkt zu den kleinen Lichtblicken (aus einer Erinnerung).
        LaunchedEffect(openLichtblicke) {
            if (openLichtblicke) goTopLevel(Routes.LICHTBLICKE)
        }
    }
}
