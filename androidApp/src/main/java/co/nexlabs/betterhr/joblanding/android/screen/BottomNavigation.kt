package co.nexlabs.betterhr.joblanding.android.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.nexlabs.betterhr.joblanding.android.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem("Home", R.drawable.home_selected_icon, R.drawable.home_icon, "home"),
        BottomNavItem("Application", R.drawable.application_selected_icon, R.drawable.application_icon, "application"),
        BottomNavItem("Inbox", R.drawable.inbox_selected_icon, R.drawable.inbox_icon, "inbox"),
        BottomNavItem("Interviews", R.drawable.interview_selected_icon, R.drawable.interview_icon, "interviews"),
        BottomNavItem("Profile", R.drawable.profile_selected_icon, R.drawable.profile_icon, "profile")
    )

    Scaffold(
        backgroundColor = Color.White,
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    BottomNavigationItem(
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
                        label = { Text(text = item.title) },
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
        NavHost(navController, startDestination = "home") {
            composable("home") { HomeScreen() }
            composable("application") { ApplicationScreen() }
            composable("inbox") { InboxScreen() }
            composable("interviews") { InterviewsScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}

@Composable
fun HomeScreen() {
    //Text(text = "Home", modifier = Modifier.padding(16.dp))
}

@Composable
fun ApplicationScreen() {
    //Text(text = "Application", modifier = Modifier.padding(16.dp))
}

@Composable
fun InboxScreen() {
    //Text(text = "Inbox", modifier = Modifier.padding(16.dp))
}

@Composable
fun InterviewsScreen() {
    //Text(text = "Interviews", modifier = Modifier.padding(16.dp))
}

@Composable
fun ProfileScreen() {
    //Text(text = "Profile", modifier = Modifier.padding(16.dp))
}

data class BottomNavItem(val title: String, val selectedIcon: Int, val unselectedIcon: Int, val route: String)