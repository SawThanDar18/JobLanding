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
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.BottomNavigation
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.CollectionListsDetail
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.CompanyDetailsScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.CompanyListsScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.JobDetailsScreen
import co.nexlabs.betterhr.joblanding.android.screen.choose_country.ChooseCountryScreen
import co.nexlabs.betterhr.joblanding.android.screen.register.RegisterScreen
import co.nexlabs.betterhr.joblanding.android.screen.unregister_profile.UnregisterProfileScreen
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
import org.koin.compose.getKoin

@Composable
fun MyApp() {

    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        NavHost(navController, startDestination = "choose-country-screen") {
            composable("profile-unregister-screen") {
                UnregisterProfileScreen(navController)
            }
            composable("register-screen") {
                val viewModel: RegisterViewModel = getKoin().get()
                RegisterScreen(navController, viewModel)
            }
            composable("choose-country-screen") {
                val viewModel: ChooseCountryViewModel = getKoin().get()
                ChooseCountryScreen(viewModel, navController)
            }
            composable("bottom-navigation-screen") {
                BottomNavigation(navController)
            }
            composable("collection-lists-detail") {
                CollectionListsDetail(navController)
            }
            composable("company-lists-detail") {
                CompanyListsScreen(navController)
            }
            composable("job-details") {
                JobDetailsScreen(navController)
            }
            composable("company-details") {
                CompanyDetailsScreen(navController)
            }
        }
    }
}