package co.nexlabs.betterhr.joblanding.android.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import co.nexlabs.betterhr.joblanding.AndroidAssetProvider
import co.nexlabs.betterhr.joblanding.AndroidFileHandler
import co.nexlabs.betterhr.joblanding.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.AssetProvider
import co.nexlabs.betterhr.joblanding.FileHandler
import co.nexlabs.betterhr.joblanding.di.initKoin
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage

class MainActivity : ComponentActivity() {

    private lateinit var localStorage: LocalStorage
    private lateinit var fileHandler: FileHandler
    private lateinit var assetProvider: AssetProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localStorage = AndroidLocalStorageImpl(this)
        fileHandler = AndroidFileHandler(this.contentResolver)
        assetProvider = AndroidAssetProvider(this)

        initKoin(localStorage, fileHandler, assetProvider)

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