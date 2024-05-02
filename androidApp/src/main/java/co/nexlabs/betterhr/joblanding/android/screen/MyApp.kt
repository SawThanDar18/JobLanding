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
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.CollectionCompaniesListsScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.CollectionJobsListsScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.CompanyDetailsScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.JobDetailsScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox.NotificationScreen
import co.nexlabs.betterhr.joblanding.android.screen.choose_country.ChooseCountryScreen
import co.nexlabs.betterhr.joblanding.android.screen.register.CompleteProfileScreen
import co.nexlabs.betterhr.joblanding.android.screen.register.ProfileDetailEditScreen
import co.nexlabs.betterhr.joblanding.android.screen.register.ProfileRegisterScreen
import co.nexlabs.betterhr.joblanding.android.screen.register.RegisterScreen
import co.nexlabs.betterhr.joblanding.android.screen.splash.ScreenPortal
import co.nexlabs.betterhr.joblanding.android.screen.unregister_profile.UnregisterProfileScreen
import co.nexlabs.betterhr.joblanding.network.api.SharedViewModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.BottomNavigationViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionCompaniesViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionJobsViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.JobDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CompanyDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.screen_portal.ScreenPortalViewModel
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
        NavHost(navController, startDestination = "screen-portal") {
            composable("screen-portal") {
                val viewModel: ScreenPortalViewModel = getKoin().get()
                ScreenPortal(navController, viewModel)
            }
            composable("inbox-screen") {
                UnregisterProfileScreen(navController)
            }
            composable("register-screen") {
                val viewModel: RegisterViewModel = getKoin().get()
                RegisterScreen(navController, viewModel)
            }
            composable("profile-register-screen") {
                ProfileRegisterScreen()
            }
            composable("choose-country-screen") {
                val viewModel: ChooseCountryViewModel = getKoin().get()
                ChooseCountryScreen(viewModel, navController)
            }
            composable("bottom-navigation-screen/{pageId}/{destination}") {
                val viewModel: BottomNavigationViewModel = getKoin().get()
                if (it.arguments?.getString("pageId") != "" || it.arguments?.getString("destination") != "") {
                    BottomNavigation(viewModel, navController, it.arguments?.getString("pageId") ?: "", it.arguments?.getString("destination") ?: "")
                }
            }
            composable("jobs-lists-screen/{collectionId}/{collectionName}") {
                val viewModel: CollectionJobsViewModel = getKoin().get()
                if (it.arguments?.getString("collectionId") != "" && it.arguments?.getString("collectionName") != "") {
                    CollectionJobsListsScreen(viewModel, navController, it.arguments?.getString("collectionId") ?: "", it.arguments?.getString("collectionName") ?: "")
                }
            }
            composable("companies-lists-screen/{collectionId}/{collectionName}") {
                val viewModel: CollectionCompaniesViewModel = getKoin().get()
                if (it.arguments?.getString("collectionId") != "" && it.arguments?.getString("collectionName") != "") {
                    CollectionCompaniesListsScreen(viewModel, navController, it.arguments?.getString("collectionId") ?: "", it.arguments?.getString("collectionName") ?: "")
                }
            }
            composable("job-details/{jobId}") {
                val viewModel: JobDetailViewModel = getKoin().get()
                if (it.arguments?.getString("jobId") != "") {
                    JobDetailsScreen(viewModel, navController, it.arguments?.getString("jobId") ?: "")
                }
            }
            composable("company-details/{companyId}") {
                val viewModel: CompanyDetailViewModel = getKoin().get()
                if (it.arguments?.getString("companyId") != "") {
                    CompanyDetailsScreen(viewModel, navController, it.arguments?.getString("companyId") ?: "")
                }
            }
            composable("inbox-screen") {
                NotificationScreen()
            }
            composable("profile-edit-detail-screen") {
                ProfileDetailEditScreen()
            }
            composable("complete-profile-screen") {
                CompleteProfileScreen()
            }
        }
    }
}