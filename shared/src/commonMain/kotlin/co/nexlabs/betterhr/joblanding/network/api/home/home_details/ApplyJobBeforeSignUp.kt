package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ApplyJobBeforeSignUp() {
    var progressVisible by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    progressVisible = true
                    progress = 0f
                    coroutineScope.launch {
                        repeat(10) {
                            delay(500)
                            progress += 0.1f
                        }
                        progressVisible = false
                    }
                }
            ) {
                Text("Start Progress")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (progressVisible) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Text(text = "State-driven Progress Bar")
        }
    )
}