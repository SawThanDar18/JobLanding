package co.nexlabs.betterhr.joblanding.android.screen

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import co.nexlabs.betterhr.joblanding.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.splash.ScreenPortal
import co.nexlabs.betterhr.joblanding.di.initKoin
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage

class MainActivity : ComponentActivity() {

    private lateinit var localStorage: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localStorage = AndroidLocalStorageImpl(this)

        initKoin(this.application, localStorage)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT

        setContent {
            MaterialTheme {
                MyApp()
            }
        }
    }
}