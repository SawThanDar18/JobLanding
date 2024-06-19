package co.nexlabs.betterhr.joblanding.android.screen.setting

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Item
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CountryScreen(viewModel: ChooseCountryViewModel, navController: NavController) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var isSelected by remember { mutableStateOf(false) }
    var selectedCountryId by remember { mutableStateOf("") }
    var selectedCountryName by remember { mutableStateOf("") }

    scope.launch {
        viewModel.getCountriesList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Arrow Left",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() },
            )

            Text(
                text = "Select country",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp,
            )

            Text(
                text = "country",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color.Transparent,
                fontSize = 14.sp,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        FlowRow(
            maxItemsInEachRow = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            repeat(uiState.countries.size) { index ->
                var item = uiState.countries[index]
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedCountryId = item.id
                            selectedCountryName = item.countryName
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Image(
                            painter = painterResource(id = countryFlag),
                            contentDescription = item.countryName,
                            modifier = Modifier.size(20.dp),
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = item.countryName,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 14.sp,
                        )
                    }

                    RadioButton(
                        selected = selectedCountryId == item.id,
                        onClick = {
                            selectedCountryId = item.id
                            selectedCountryName = item.countryName
                        }
                    )
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
                        Log.d("ci>>", selectedCountryId)
                        Log.d("ci>>", selectedCountryName)
                        viewModel.updateCountryId(selectedCountryId)
                        viewModel.updateCountryName(selectedCountryName)
                        viewModel.getDynamicPagesId(selectedCountryId)
                        if (uiState.dynamicPageId != "") {
                            viewModel.updatePageId(uiState.dynamicPageId)
                        }
                        navController.popBackStack()
                    }
                }
                .height(40.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                .background(color = Color(0xFF1ED292), shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Save change",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFFFFFFFF),
                fontSize = 14.sp,
            )
        }
    }
}