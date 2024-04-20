package co.nexlabs.betterhr.joblanding.android.screen.choose_country

import android.util.Log
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Button
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.data.CountriesListUIModel
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Data
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Item
import kotlinx.coroutines.launch

@Composable
fun ChooseCountryScreen(viewModel: ChooseCountryViewModel, navController: NavController) {

    var items by remember { mutableStateOf(mutableListOf<Item>()) }
    var selectedItem by remember { mutableStateOf(Item("", "Select your country")) }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    scope.launch {
        viewModel.getCountriesList()
    }

    items = uiState.items

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
                        items = uiState.items
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
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Image(
                                //painter = item.image,
                                painter = painterResource(id = R.drawable.myanmar_flag),
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

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate("bottom-navigation-screen")
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