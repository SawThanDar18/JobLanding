import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.nexlabs.betterhr.joblanding.android.RegisterScreen
import co.nexlabs.betterhr.joblanding.android.UnregisterProfileScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        NavHost(navController, startDestination = "unregister_profile_screen") {
            composable("unregister_profile_screen") { UnregisterProfileScreen(navController) }
            composable("register_screen") { RegisterScreen(navController) }
        }
    }
}