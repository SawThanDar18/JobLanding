package co.nexlabs.betterhr.joblanding.android.screen.choose_country

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Item
import kotlinx.coroutines.launch

@Composable
fun ChooseCountryScreen(viewModel: ChooseCountryViewModel, navController: NavController) {

    var applicationContext = LocalContext.current.applicationContext
    var items by remember { mutableStateOf(mutableListOf<Item>()) }
    var selectedItem by remember {
        mutableStateOf(
            Item(
                "ab18de52-e946-4925-83ab-46f804846034",
                "Select your country"
            )
        )
    }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {
                Log.d("state>>", "destroyed")
            }

            Lifecycle.State.INITIALIZED -> {
                Log.d("state>>", "initialized")
            }

            Lifecycle.State.CREATED -> {
                scope.launch {
                    viewModel.getCountriesList()
                }
                Log.d("state>>", "created")
            }

            Lifecycle.State.STARTED -> {
                scope.launch {
                    viewModel.getCountriesList()
                }
                Log.d("state>>", "started")
            }

            Lifecycle.State.RESUMED -> {
                scope.launch {
                    viewModel.getCountriesList()
                }
                Log.d("state>>", "resume")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 90.dp, 16.dp, 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.better_job_logo),
                contentDescription = "Better Job Logo",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier.align(Alignment.Start),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                style = TextStyle(
                    textAlign = TextAlign.Start
                ),
                text = "Select your country",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W700,
                color = Color(0xFF4A4A4A),
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .background(color = Color.Transparent)
                .height(40.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp)),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        expanded = true
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = selectedItem.countryName,
                    modifier = Modifier.padding(start = 8.dp),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 14.sp,
                )

                Image(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = "Arrow Down",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

            DropdownMenu(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(color = Color.Transparent)
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp)),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (uiState.items.isEmpty()) {
                    AnimatedVisibility(
                        uiState.items.isEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = Color(0xFF1ED292)
                        )
                    }
                } else {
                    uiState.items.forEach { item ->

                        var countryFlag by remember { mutableStateOf(R.drawable.country) }
                        when (item.countryName) {
                            "Myanmar" -> countryFlag = R.drawable.myanmar_flag
                            "Sri Lanka" -> countryFlag = R.drawable.srilanka_flag
                            "Cambodia" -> countryFlag = R.drawable.cambodia_flag
                            "Vietnam" -> countryFlag = R.drawable.vietnam_flag
                            "Thailand" -> countryFlag = R.drawable.thailand_flag
                            "Singapore" -> countryFlag = R.drawable.singapore_flag
                            else -> R.drawable.country
                        }

                        DropdownMenuItem(
                            onClick = {
                                selectedItem = item
                                expanded = false
                                scope.launch {
                                    viewModel.getDynamicPagesId(selectedItem.id)
                                }
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Image(
                                    painter = painterResource(id = countryFlag),
                                    contentDescription = item.countryName,
                                    modifier = Modifier.size(20.dp),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = item.countryName,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF757575),
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            val pageId = uiState.dynamicPageId
                            if (pageId != "") {
                                viewModel.updateCountryId(selectedItem.id)
                                viewModel.updateCountryName(selectedItem.countryName)
                                viewModel.updatePageId(pageId)
                                navController.navigate("bottom-navigation-screen/${pageId}/${"home"}")
                            } else {
                                Toast.makeText(applicationContext, "No Data!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    .height(40.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                    .background(color = Color(0xFF1ED292), shape = MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Get Started",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                )
            }
        }
    }
}