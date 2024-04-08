package co.nexlabs.betterhr.joblanding.android.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.nexlabs.betterhr.joblanding.android.screen.choose_country.ChooseCountryScreen
import co.nexlabs.betterhr.joblanding.android.screen.register.RegisterScreen
import co.nexlabs.betterhr.joblanding.android.screen.unregister_profile.UnregisterProfileScreen
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.viewmodel.viewModel
import org.koin.compose.getKoin

@Composable
fun MyApp() {

    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        NavHost(navController, startDestination = "profile-unregister-screen") {
            composable("profile-unregister-screen") {
                //val text = it.arguments?.getString("text") ?: ""
                UnregisterProfileScreen(navController)
            }
            composable("register-screen") {
                val viewModel: RegisterViewModel = getKoin().get()
                RegisterScreen(navController, viewModel)
            }
            composable("choose-country-screen") {
                ChooseCountryScreen(navController)
            }
        }
    }
}