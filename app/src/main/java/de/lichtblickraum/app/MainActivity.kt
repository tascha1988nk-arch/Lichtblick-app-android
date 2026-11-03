package de.lichtblickraum.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import de.lichtblickraum.app.navigation.LichtblickRoot
import de.lichtblickraum.app.ui.theme.LichtblickTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Helle Leisten mit dunklen Symbolen – passend zum Cremeton, auch im System-Dunkelmodus.
        val light = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        enableEdgeToEdge(statusBarStyle = light, navigationBarStyle = light)
        super.onCreate(savedInstanceState)
        val container = (application as LichtblickApplication).container
        setContent {
            LichtblickTheme {
                LichtblickRoot(container)
            }
        }
    }
}
