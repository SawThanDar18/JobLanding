package co.nexlabs.betterhr.joblanding.android.screen.setting

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.setting.SavedJobsViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun SavedJobsScreen(navController: NavController, viewModel: SavedJobsViewModel) {

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var jobIds: MutableList<String> = ArrayList()

    scope.launch {
        viewModel.fetchSavedJobsIds()
    }

    LaunchedEffect(uiState.isSuccessSavedJobsIds) {
        scope.launch {
            if (uiState.savedJobIds.isNotEmpty()) {
                jobIds.clear()
                uiState.savedJobIds.map {
                    jobIds.add(it.jobId)
                }
            }

            if (jobIds.isNotEmpty()) {
                viewModel.fetchSavedJobs(jobIds)
            }
        }
    }

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
            ErrorLayout(errorType = uiState.error)
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
                    .padding(start = 16.dp, end = 16.dp),
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
                    text = "Saved jobs",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF4A4A4A),
                    fontSize = 14.sp,
                )

                Text(
                    text = "saved",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color.Transparent,
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                uiState.savedJobList.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(uiState.savedJobList.size) { index ->
                        var item = uiState.savedJobList[index]

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
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(shape = RoundedCornerShape(8.dp)),
                                        model = item.savedJobsCompanyUIModel.companyLogo,
                                        contentDescription = "Company Logo",
                                        contentScale = ContentScale.Crop,
                                    )
                                }

                                Column(modifier = Modifier.padding(start = 8.dp)) {
                                    Text(
                                        text = item.title,
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
                                        text = item.savedJobsCompanyUIModel.companyName,
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
                                            text = "${item.currencyCode} ${item.minSalary}${currencyCode}-${item.maxSalary}${currencyCode}",
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
                                            text = item.stateName,
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
                                        painter = painterResource(id = R.drawable.save_selected_green_icon),
                                        contentDescription = "Save Selected Icon",
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

            AnimatedVisibility(
                uiState.savedJobIds.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "There is no Saved Jobs yet!",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF1ED292),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}