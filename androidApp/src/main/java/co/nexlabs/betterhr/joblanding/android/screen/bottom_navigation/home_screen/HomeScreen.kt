package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.AlertDialogDefaults.shape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.home.HomeViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.CollectionCompaniesUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.CollectionUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.JobsListUIModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController, pageId: String) {

    var refreshing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var bearerToken by remember { mutableStateOf("") }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            scope.launch {
                if (pageId != null && pageId != "") {
                    viewModel.getJobLandingSections(pageId)
                }
            }
            refreshing = false
        }
    }

    scope.launch {
        if (pageId != null && pageId != "") {
            viewModel.getJobLandingSections(pageId)
        }
        bearerToken = viewModel.getBearerToken()
    }

    var style by remember { mutableStateOf("style-7") }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true },
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AnimatedVisibility(
                uiState.isLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF1ED292)
                )
            }

            AnimatedVisibility(
                uiState.error != UIErrorType.Nothing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ErrorLayout(uiState.error)
            }

            AnimatedVisibility(
                uiState.jobLandingSectionsList.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp, 16.dp, 16.dp, 80.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            //modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.better_icon),
                                contentDescription = "Home Screen Header Logo",
                                modifier = Modifier
                                    .width(26.dp)
                                    .height(32.dp),
                            )

                            Text(
                                text = "Better Job",
                                modifier = Modifier.padding(start = 8.dp),
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W600,
                                color = Color(0xFF757575),
                                fontSize = 24.sp,
                            )
                        }

                        if (viewModel.getBearerToken() != "") {
                            Image(
                                painter = painterResource(id = R.drawable.qr),
                                contentDescription = "QR Logo",
                                modifier = Modifier
                                    .width(30.dp)
                                    .clickable {
                                        navController.navigate("qr-scan-login-screen")
                                    },
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    NestedLazyColumn(style, uiState.jobLandingSectionsList, navController)
                }
            }
        }
    }

    if (bearerToken == "") {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 73.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = Color(0xFF111111).copy(alpha = 0.8f))
                    .padding(horizontal = 16.dp)
                    .clickable {
                        navController.navigate("bottom-navigation-screen/${pageId}/${"profile"}")
                    },
            ) {
                Text(
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    text = "Sign up with email or phone number!",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFFFFFFFF),
                    fontSize = 12.sp,
                )

                Box(
                    modifier = Modifier
                        .width(101.dp)
                        .height(25.dp)
                        .border(1.dp, Color(0xFFE9FCF5), RoundedCornerShape(8.dp))
                        .background(color = Color(0xFFE9FCF5), shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "SIGN UP NOW",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF1ED292),
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }

}

@Composable
fun StyleSevenCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth()
            .height(80.dp)
            .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp)),
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
                    text = "Designer",
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
                    text = "Yoma Bank",
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
                        text = "MMK 400k-600k",
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
                        text = "Yangon",
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

@Composable
fun StyleSixCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
            .width(156.dp)
            .height(156.dp)
            .border(0.dp, Color.Transparent, RoundedCornerShape(8.dp)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.campaign),
            contentDescription = "Campaigns",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun StyleFiveCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
            .width(156.dp)
            .height(156.dp)
            .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bank_logo),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(42.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Mi",
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 4.dp),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF6A6A6A),
                    fontSize = 14.sp,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.save_unselected_icon),
                        contentDescription = "Save Unselected Icon",
                        modifier = Modifier
                            .size(9.dp, 12.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Graphic Designer",
                    maxLines = 2,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF6A6A6A),
                    fontSize = 14.sp,
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Full time",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )

                Text(
                    text = "MMK 300K-400K",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )

                Text(
                    text = "Yangon, Myanmar",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun CollectionLabelLayoutItem(
    collection: CollectionUIModel,
    title: String,
    count: Int,
    navController: NavController
) {

    Row(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 14.dp)
            .fillMaxWidth()
            .clickable {
                if (collection.type == "job_collection") {
                    navController.navigate("jobs-lists-screen/${collection.id}/${collection.name}")
                } else {
                    navController.navigate("companies-lists-screen/${collection.id}/${collection.name}")
                }
            },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.gradient_line),
                contentDescription = "Gradient Line",
                modifier = Modifier
                    .size(4.dp, 18.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = title,
                modifier = Modifier.padding(start = 4.dp),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF6A6A6A),
                fontSize = 14.sp,
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "See all $count jobs",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W500,
                color = Color(0xFF1ED292),
                fontSize = 13.sp,
            )
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Right Arrow",
                modifier = Modifier
                    .size(20.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NestedLazyColumn(style: String, items: List<HomeUIModel>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Box(
                modifier = Modifier
                    .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                    .height(50.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp))
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { },
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Arrow Down",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = "Search jobs, companies...",
                        modifier = Modifier.padding(start = 8.dp),
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF757575),
                        fontSize = 14.sp,
                    )
                }
            }
        }

        items(items.size) { index ->
            CollectionLabelLayoutItem(
                collection = items[index].collection,
                title = items[index].title,
                count = items[index].dataCount,
                navController
            )

            when (items[index].collectionType) {
                "job_collection" -> {
                    when (items[index].postStyle) {
                        "style_1" -> StyleThreeLazyLayout(
                            collectionId = items[index].collection.id,
                            collectionJobList = items[index].jobs ?: emptyList(),
                            navController = navController
                        )

                        "style_2" -> Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            StyleTwoLazyLayout(
                                collectionId = items[index].collection.id,
                                collectionJobList = items[index].jobs ?: emptyList(),
                                navController = navController
                            )
                        }

                        "style_3" -> StyleOneLazyLayout(
                            collectionId = items[index].collection.id,
                            collectionJobList = items[index].jobs ?: emptyList(),
                            navController = navController
                        )
                    }
                }

                "company_collection" -> Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    StyleFourLazyLayout(
                        collectionId = items[index].collection.id,
                        collectionCompanyList = items[index].collectionCompanies,
                        navController = navController
                    )
                }
            }

            /*when (items[index].postStyle) {
                "style_1" -> StyleOneLazyLayout(items[index].collection.id, items[index].collectionType, )
                "style_2" -> Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    StyleTwoLazyLayout()
                }
                "style_3" -> StyleThreeLazyLayout()
                "style_4" -> Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    StyleFourLazyLayout()
                }
                "style_5" -> StyleFiveLazyLayout()
                "style_6" -> StyleSixLazyLayout()
                "style_7" -> Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    StyleSevenLazyLayout()
                }
            }*/
        }
    }
}

@Composable
fun StyleOneLazyLayout(
    collectionId: String,
    collectionJobList: List<JobsListUIModel>,
    navController: NavController
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(collectionJobList.size) { index ->
            var currencyCode = ""
            if (collectionJobList[index].currencyCode == "MMK") {
                currencyCode = "K"
            }

            Box(
                modifier = Modifier
                    .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                    .width(259.dp)
                    .height(119.dp)
                    .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate("job-details/${collectionJobList[index].id}")
                    },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(shape = RoundedCornerShape(8.dp)),
                            model = collectionJobList[index].company.logo,
                            contentDescription = "Company Logo",
                            contentScale = ContentScale.Crop,
                        )

                        Text(
                            text = collectionJobList[index].company.name,
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 4.dp),
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W500,
                            color = Color(0xFF6A6A6A),
                            fontSize = 12.sp,
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.save_unselected_icon),
                                contentDescription = "Save Unselected Icon",
                                modifier = Modifier
                                    .size(9.dp, 12.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 4.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = collectionJobList[index].position,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.salary_icon),
                            contentDescription = "Salary",
                            modifier = Modifier
                                .size(16.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "${collectionJobList[index].miniSalary}${currencyCode} - ${collectionJobList[index].maxiSalary}${currencyCode}",
                            modifier = Modifier.padding(start = 4.dp),
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 13.sp,
                        )

                        Text(
                            text = "${collectionJobList[index].cityName}, ${collectionJobList[index].stateName}",
                            modifier = Modifier.padding(start = 4.dp),
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StyleTwoLazyLayout(
    collectionId: String,
    collectionJobList: List<JobsListUIModel>,
    navController: NavController
) {
    /*LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {*/
    FlowRow(
        maxItemsInEachRow = 2,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(collectionJobList.size) { index ->
            Box(
                modifier = Modifier
                    .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                    .weight(1f)
                    // .width(156.dp)
                    .height(71.dp)
                    .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate("job-details/${collectionJobList[index].id}")
                    },
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        //verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            maxLines = 2,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            text = collectionJobList[index].position,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFFAAAAAA),
                            fontSize = 12.sp,
                            modifier = Modifier.width(80.dp)
                        )

                        Text(
                            maxLines = 2,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            text = collectionJobList[index].company.name,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFFAAAAAA),
                            fontSize = 12.sp,
                            modifier = Modifier.width(80.dp)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(shape = RoundedCornerShape(8.dp)),
                            model = collectionJobList[index].company.logo,
                            contentDescription = "Company Logo",
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun StyleThreeLazyLayout(
    collectionId: String,
    collectionJobList: List<JobsListUIModel>,
    navController: NavController
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(collectionJobList.size) { index ->
            var currencyCode = ""
            if (collectionJobList[index].currencyCode == "MMK") {
                currencyCode = "k"
            }

            Box(
                modifier = Modifier
                    .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                    .width(142.dp)
                    .height(173.dp)
                    .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate("job-details/${collectionJobList[index].id}")
                    },
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {

                        Text(
                            maxLines = 2,
                            text = collectionJobList[index].company.name,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp,
                            modifier = Modifier.width(60.dp)
                        )

                        AsyncImage(
                            modifier = Modifier
                                .size(32.dp).padding(start = 2.dp)
                                .clip(shape = RoundedCornerShape(8.dp)),
                            model = collectionJobList[index].company.logo,
                            contentDescription = "Company Logo",
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = collectionJobList[index].position,
                            maxLines = 2,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp,
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = collectionJobList[index].employmentType,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 12.sp,
                        )

                        Text(
                            text = "${collectionJobList[index].currencyCode} ${collectionJobList[index].miniSalary}${currencyCode} - ${collectionJobList[index].maxiSalary}${currencyCode}",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 12.sp,
                        )

                        Text(
                            text = "${collectionJobList[index].cityName}, ${collectionJobList[index].stateName}",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StyleFourLazyLayout(
    collectionId: String,
    collectionCompanyList: List<CollectionCompaniesUIModel>,
    navController: NavController
) {
    FlowRow(
        modifier = Modifier.fillMaxSize(),
        maxItemsInEachRow = 2,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        /* LazyVerticalGrid(
             modifier = Modifier.fillMaxSize(),
             columns = GridCells.Fixed(2),
             verticalArrangement = Arrangement.spacedBy(16.dp),
             horizontalArrangement = Arrangement.spacedBy(16.dp)
         ) {*/
        repeat(collectionCompanyList.size) { index ->
            Box(
                modifier = Modifier
                    .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                    .weight(1f)
                    //.width(156.dp)
                    .height(68.dp)
                    .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate("company-details/${collectionCompanyList[index].company.id}")
                    },
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(shape = RoundedCornerShape(8.dp)),
                            model = collectionCompanyList[index].company.logo,
                            contentDescription = "Company Logo",
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Column(
                        modifier = Modifier.padding(start = 2.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = collectionCompanyList[index].company.name,
                            maxLines = 2,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W500,
                            color = Color(0xFF454545),
                            fontSize = 12.sp,
                        )

                        Text(
                            text = "${collectionCompanyList[index].jobOpeningCount} opening",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W300,
                            color = Color(0xFF454545),
                            fontSize = 10.sp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StyleFiveLazyLayout() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(5) { subIndex ->
            StyleFiveCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

@Composable
fun StyleSixLazyLayout() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(10) { subIndex ->
            StyleSixCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

@Composable
fun StyleSevenLazyLayout() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(10) { subIndex ->
            StyleSevenCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}
