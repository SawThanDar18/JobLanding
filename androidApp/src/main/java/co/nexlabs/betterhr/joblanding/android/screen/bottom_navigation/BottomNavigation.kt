package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.applications.ApplicationsScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.HomeScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox.NotificationScreen
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.interviews.InterviewsScreen
import co.nexlabs.betterhr.joblanding.android.screen.register.CompleteProfileScreen
import co.nexlabs.betterhr.joblanding.android.screen.register.ProfileRegisterScreen
import co.nexlabs.betterhr.joblanding.android.screen.unregister_profile.UnregisterProfileScreen
import co.nexlabs.betterhr.joblanding.network.api.SharedViewModel
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationViewModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.BottomNavigationViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.HomeViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.InboxViewModel
import co.nexlabs.betterhr.joblanding.network.api.interview.InterviewViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.register.CompleteProfileViewModel
import kotlinx.coroutines.launch
import org.koin.compose.getKoin

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigation(
    viewModel: BottomNavigationViewModel,
    nav: NavController,
    pageId: String,
    destination: String
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem("Home", R.drawable.home_selected_icon, R.drawable.home_icon, "home"),
        BottomNavItem(
            "Application",
            R.drawable.application_selected_icon,
            R.drawable.application_icon,
            "application"
        ),
        BottomNavItem("Inbox", R.drawable.inbox_selected_icon, R.drawable.inbox_icon, "inbox"),
        BottomNavItem(
            "Interviews",
            R.drawable.interview_selected_icon,
            R.drawable.interview_icon,
            "interviews"
        ),
        BottomNavItem(
            "Profile",
            R.drawable.profile_selected_icon,
            R.drawable.profile_icon,
            "profile"
        )
    )

    Scaffold(
        backgroundColor = Color.White,
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .height(73.dp)
                    .fillMaxWidth(),
                backgroundColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    BottomNavigationItem(
                        modifier = Modifier
                            .padding(bottom = 0.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                        icon = {
                            val image = if (currentRoute == item.route) {
                                painterResource(id = item.selectedIcon)
                            } else {
                                painterResource(id = item.unselectedIcon)
                            }
                            Image(
                                painter = image,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true,
                                maxLines = 1,
                                text = item.title,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF999999),
                                fontSize = 12.sp,
                            )
                        },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController, startDestination = destination) {

            composable("home") {
                val viewModel: HomeViewModel = getKoin().get()
                HomeScreen(viewModel, nav, pageId)
            }
            composable("application") {
                val viewModel: ApplicationViewModel = getKoin().get()
                ApplicationsScreen(viewModel, nav)
            }
            composable("inbox") {
                val viewModel: InboxViewModel = getKoin().get()
                NotificationScreen(viewModel, nav)
            }
            composable("interviews") {
                val viewModel: InterviewViewModel = getKoin().get()
                InterviewsScreen(viewModel, nav)
            }
            composable("profile") {
                if (viewModel.getBearerToken() != "") {
                    val viewModel: CompleteProfileViewModel = getKoin().get()
                    CompleteProfileScreen(viewModel, nav)
                } else {
                    UnregisterProfileScreen(nav)
                }
            }

            when(destination) {
                "home" -> composable("home") {
                    val viewModel: HomeViewModel = getKoin().get()
                    HomeScreen(viewModel, nav, pageId)
                }

                "application" -> composable("application") {
                    val viewModel: ApplicationViewModel = getKoin().get()
                    ApplicationsScreen(viewModel, nav)
                }
                "inbox" -> composable("inbox") {
                    val viewModel: InboxViewModel = getKoin().get()
                    NotificationScreen(viewModel, nav)
                }
                "interviews" -> composable("interviews") {
                    val viewModel: InterviewViewModel = getKoin().get()
                    InterviewsScreen(viewModel, nav)
                }
                "profile" -> composable("profile") {
                    if (viewModel.getBearerToken() != "") {
                        val viewModel: CompleteProfileViewModel = getKoin().get()
                        CompleteProfileScreen(viewModel, nav)
                    } else {
                        UnregisterProfileScreen(nav)
                    }
                }
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String
)