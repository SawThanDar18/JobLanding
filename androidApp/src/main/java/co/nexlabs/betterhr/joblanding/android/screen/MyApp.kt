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
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.applications.ApplicationDetailScreen
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
import co.nexlabs.betterhr.joblanding.network.api.apply_job.ApplyJobViewModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.BottomNavigationViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionCompaniesViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionJobsViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.JobDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CompanyDetailViewModel
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.ApplyJobBeforeSignUp
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.ApplyJobScreens
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox.NotificationDetailScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox.OfferResponseScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox.SubmitAssignmentScreen
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.InboxDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitAssignmentViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitOfferViewModel
import co.nexlabs.betterhr.joblanding.network.api.screen_portal.ScreenPortalViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.register.CompleteProfileViewModel
import co.nexlabs.betterhr.joblanding.network.register.ProfileRegisterViewModel
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
            composable("signature") {
                //SignaturePad()
            }
            composable("screen-portal") {
                val viewModel: ScreenPortalViewModel = getKoin().get()
                ScreenPortal(navController, viewModel)
            }
            composable("register-screen") {
                val viewModel: RegisterViewModel = getKoin().get()
                RegisterScreen(navController, viewModel)
            }
            composable("profile-register-screen") {
                val viewModel: ProfileRegisterViewModel = getKoin().get()
                ProfileRegisterScreen(viewModel, navController)
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
            composable("application-details/{applicationDetailId}") {
                val viewModel: ApplicationViewModel = getKoin().get()
                if (it.arguments?.getString("applicationDetailId") != "") {
                    ApplicationDetailScreen(viewModel, navController, it.arguments?.getString("applicationDetailId") ?: "")
                }
            }
            composable("company-details/{companyId}") {
                val viewModel: CompanyDetailViewModel = getKoin().get()
                if (it.arguments?.getString("companyId") != "") {
                    CompanyDetailsScreen(viewModel, navController, it.arguments?.getString("companyId") ?: "")
                }
            }
            composable("notification-details/{notificationId}/{notiType}/{link}") {
                val viewModel: InboxDetailViewModel = getKoin().get()
                if (it.arguments?.getString("notificationId") != "" || it.arguments?.getString("notiType") != "") {
                    NotificationDetailScreen(viewModel, navController, it.arguments?.getString("notificationId") ?: "", it.arguments?.getString("notiType") ?: "")
                }
            }
            composable("profile-edit-detail-screen") {
                ProfileDetailEditScreen()
            }
            composable("complete-profile-screen") {
                val viewModel: CompleteProfileViewModel = getKoin().get()
                CompleteProfileScreen(viewModel, navController)
            }
            composable("apply-job-before-sign-up") {
                ApplyJobBeforeSignUp()
            }
            composable("apply-job") {
                val viewModel: ApplyJobViewModel = getKoin().get()
                ApplyJobScreens(viewModel, navController)
            }

            composable("submit-assignment/{notiId}/{jobId}/{referenceId}/{title}/{status}/{subDomain}/{referenceApplicationId}") {
                val viewModel: SubmitAssignmentViewModel = getKoin().get()
                if (it.arguments?.getString("notiId") != "" && it.arguments?.getString("jobId") != "" && it.arguments?.getString("referenceId") != "" &&
                    it.arguments?.getString("title") != "" && it.arguments?.getString("status") != "" &&
                    it.arguments?.getString("subDomain") != "" && it.arguments?.getString("referenceApplicationId") != "") {
                    SubmitAssignmentScreen(viewModel, navController,
                        it.arguments?.getString("notiId") ?: "",
                        it.arguments?.getString("jobId") ?: "",
                        it.arguments?.getString("referenceId") ?: "",
                        it.arguments?.getString("title") ?: "",
                        it.arguments?.getString("status") ?: "",
                        it.arguments?.getString("subDomain") ?: "",
                        it.arguments?.getString("referenceApplicationId") ?: ""
                    )
                }
            }

            composable("submit-offer/{id}/{referenceId}/{link}") {
                val viewModel: SubmitOfferViewModel = getKoin().get()
                if (it.arguments?.getString("referenceId") != "" && it.arguments?.getString("id") != "" &&
                    it.arguments?.getString("link") != "") {
                    OfferResponseScreen(viewModel, navController, it.arguments?.getString("id") ?: "", it.arguments?.getString("referenceId") ?: "", it.arguments?.getString("status") ?: "", it.arguments?.getString("subDomain") ?: "", it.arguments?.getString("link") ?: "")
                }
            }
        }
    }
}