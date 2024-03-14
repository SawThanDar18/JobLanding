package co.nexlabs.betterhr.joblanding.android.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.nexlabs.betterhr.joblanding.android.screen.register.RegisterScreen
import co.nexlabs.betterhr.joblanding.android.screen.unregister_profile.UnregisterProfileScreen
import co.nexlabs.betterhr.joblanding.android.theme.registerScreen
import co.nexlabs.betterhr.joblanding.android.theme.unRegisterScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        NavHost(navController, startDestination = unRegisterScreen) {
            composable(unRegisterScreen) { UnregisterProfileScreen(navController) }
            composable(registerScreen) { RegisterScreen(navController) }
        }
    }
}