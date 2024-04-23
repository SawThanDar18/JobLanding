package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen

import android.content.Intent
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.style.TextOverflow
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailJobs
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun CompanyDetailsScreen(viewModel: CompanyDetailViewModel, navController: NavController, companyId: String) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    var isChecked by remember { mutableStateOf(true) }

    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Jobs", "About")

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    scope.launch {
        if (companyId != null && companyId != "") {
            viewModel.getCompanyDetail(companyId)
        }
    }

    val item = uiState.companyDetail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            OverlapBoxes(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.yoma_cover),
                    contentDescription = "Background Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.FillWidth
                )

                Image(
                    painter = painterResource(id = R.drawable.bank_logo),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .width(61.dp)
                        .height(61.dp)
                        .clip(CircleShape),
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Get Job Alerts",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF6A6A6A),
                fontSize = 12.sp,
            )

            ToggleButton(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp),
            text = item.name,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W600,
            color = Color(0xFF4A4A4A),
            fontSize = 16.sp,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 6.dp),
            text = "Company",
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W400,
            color = Color(0xFF4A4A4A),
            fontSize = 12.sp,
        )

        TabRow(
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Color(0xFF1ED292),
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex])
                )
            },
            contentColor = Color(0xFF1ED292),
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W500,
                            fontSize = 14.sp,
                            color = if (tabIndex == index) Color(0xFF1ED292) else Color(0xFF757575)
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> JobsScreen(uiState.companyDetail.jobs, navController)
            1 -> AboutScreen(uiState.companyDetail, uriHandler)
        }


    }

    Box {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Back Image",
            modifier = Modifier
                .padding(top = 50.dp, start = 16.dp)
                .size(24.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun JobsScreen(jobList: List<CompanyDetailJobs>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(jobList.size) { index ->
            var item = jobList[index]

            var currencyCode = ""
            if (item.currencyCode == "MMK") {
                currencyCode = "k"
            }

            Box(
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
                    .fillMaxWidth()
                    .height(80.dp)
                    .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp))
                    .clickable {
                               navController.navigate("job-details/${item.id}")
                    },
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.company_logo),
                            contentDescription = "Company Logo",
                            modifier = Modifier
                                .size(48.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = item.position,
                            maxLines = 2,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 13.sp,
                        )

                        Text(
                            modifier = Modifier.padding(top = 3.dp),
                            text = item.company.name,
                            maxLines = 2,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 12.sp,
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "${item.currencyCode} ${item.miniSalary}${currencyCode}-${item.maxiSalary}${currencyCode}",
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 12.sp,
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Image(
                                painter = painterResource(id = R.drawable.grey_line),
                                contentDescription = "Grey Space Line",
                                modifier = Modifier
                                    .size(1.dp, 8.dp),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = item.cityName,
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 12.sp,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.wrapContentHeight()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.save_unselected_icon),
                            contentDescription = "Save Unselected Icon",
                            modifier = Modifier
                                .size(11.dp, 15.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "",
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFFF8CB2E),
                            fontSize = 10.sp,
                        )

                        Text(
                            text = "2 days left",
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFFF8CB2E),
                            fontSize = 10.sp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AboutScreen(uiModel: CompanyDetailUIModel, uriHandler: UriHandler) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.mail),
                contentDescription = "Mail Icon",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = uiModel.companyMail,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF6191FC),
                fontSize = 12.sp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.globe),
                contentDescription = "Globe Icon",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = uiModel.companyLink,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF6191FC),
                fontSize = 12.sp,
                modifier = Modifier.clickable {
                    uriHandler.openUri(uiModel.companyLink)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
           /* text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,\n" +
                    "\n" +
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,\n" +
                    "\n"+
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries,\n",
            */
            text = uiModel.description,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W400,
            color = Color(0xFF757575),
            fontSize = 12.sp,
        )
    }
}


@Composable
fun OverlapBoxes(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val largeBox = measurables[0]
        val smallBox = measurables[1]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val largePlaceable = largeBox.measure(looseConstraints)
        val smallPlaceable = smallBox.measure(looseConstraints)
        layout(
            width = constraints.maxWidth,
            height = largePlaceable.height + smallPlaceable.height / 2,
        ) {
            largePlaceable.placeRelative(
                x = 0,
                y = 0,
            )
            smallPlaceable.placeRelative(
                x = 32,
                y = largePlaceable.height - smallPlaceable.height / 2
            )
        }
    }
}

@Composable
fun ToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        modifier = Modifier
            .width(35.dp)
            .height(20.dp)
            .graphicsLayer(scaleX = 0.5f, scaleY = 0.5f),
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White, // Color of the thumb when checked
            checkedTrackColor = Color(0xFF1ED292), // Color of the track when checked
            uncheckedThumbColor = Color.White, // Color of the thumb when unchecked
            uncheckedTrackColor = Color.Gray // Color of the track when unchecked
        )
    )
}