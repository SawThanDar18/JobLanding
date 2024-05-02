package co.nexlabs.betterhr.joblanding.android.screen.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.network.api.screen_portal.ScreenPortalViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ScreenPortal(navController: NavController, viewModel: ScreenPortalViewModel) {

    val scope = rememberCoroutineScope()

    scope.launch {
        delay(1000)
        if (viewModel.getCountryId().isNotBlank() && viewModel.getPageId().isNotBlank()) {
            navController.navigate("bottom-navigation-screen/${viewModel.getPageId()}/${"home"}")
        } else {
            navController.navigate("choose-country-screen")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.better_job_logo),
            contentDescription = "Better Job Logo",
            modifier = Modifier.size(100.dp)
        )
    }
}