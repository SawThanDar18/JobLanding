package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.applications

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.common.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ApplicationDetailScreen(
    viewModel: ApplicationViewModel,
    navController: NavController,
    applicationId: String
) {
    var lineHeight by remember { mutableStateOf(0.dp) }
    var statusImage by remember { mutableStateOf(R.drawable.step) }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    if (applicationId != "") {
        scope.launch {
            viewModel.fetchApplicationById(applicationId)
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        AnimatedVisibility(
            uiState.error != UIErrorType.Nothing,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ErrorLayout(uiState.error)
        }


        AnimatedVisibility(
            applicationId != "",
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, 24.dp, 16.dp, 0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_left_app_history),
                        contentDescription = "Back Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        modifier = Modifier.clickable {
                            navController.navigate("job-details/${uiState.applicationById.referenceJobId}")
                        },
                        text = "View Job details",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF1082DE),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "History",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF6A6A6A),
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(70.dp),
                        text = "Company",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF757575),
                        fontSize = 14.sp
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Oway",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF4A4A4A),
                        fontSize = 14.sp,
                        maxLines = 1,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(70.dp),
                        text = "Position",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF757575),
                        fontSize = 14.sp
                    )

                    Text(
                        text = uiState.applicationById.jobTitle,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF4A4A4A),
                        fontSize = 14.sp,
                        maxLines = 1,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(70.dp),
                        text = "Status",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF757575),
                        fontSize = 14.sp
                    )

                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(23.dp)
                            .border(
                                1.dp,
                                Color(0xFFE9FCF5),
                                RoundedCornerShape(4.dp)
                            )
                            .background(
                                color = Color(0xFFE9FCF5),
                                shape = MaterialTheme.shapes.medium
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = uiState.applicationById.status,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF1ED292),
                            fontSize = 14.sp,
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color(0xFFE4E7ED))
                ) {}

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedVisibility(
                    uiState.applicationById.applicationHistories.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                    ) {
                        item {
                            if (uiState.applicationById.applicationHistories.size == 1) {
                                FlowRow(
                                    maxItemsInEachRow = 1,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    repeat(uiState.applicationById.applicationHistories.size) { index ->
                                        var item =
                                            uiState.applicationById.applicationHistories[index]

                                        statusImage = if (item.applicationStatus == "rejected") {
                                            R.drawable.application_reject
                                        } else if (item.haveAssignment && !item.isAssignmentSubmmitted) {
                                            R.drawable.application_pending
                                        } else {
                                            R.drawable.step
                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Image(
                                                painter = painterResource(id = statusImage),
                                                contentDescription = "Step Icon",
                                                modifier = Modifier
                                                    .size(20.dp)
                                            )

                                            Spacer(modifier = Modifier.width(16.dp))

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(52.dp)
                                                    .border(
                                                        1.dp,
                                                        Color(0xFFE4E7ED),
                                                        RoundedCornerShape(8.dp)
                                                    )
                                                    .background(
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(horizontal = 16.dp),
                                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        text = item.applicationStatus,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W600,
                                                        color = Color(0xFF1ED292),
                                                        fontSize = 14.sp,
                                                        maxLines = 1,
                                                        softWrap = true,
                                                        overflow = TextOverflow.Ellipsis
                                                    )

                                                    Text(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        text = DateConversionFromYMDToMDY(item.applicationDate),
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFF757575),
                                                        fontSize = 12.sp,
                                                        maxLines = 1,
                                                        softWrap = true,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                            }

                                        }
                                    }
                                }
                            } else {
                                FlowRow(
                                    maxItemsInEachRow = 1,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                ) {
                                    repeat(uiState.applicationById.applicationHistories.size) { index ->
                                        var item =
                                            uiState.applicationById.applicationHistories[index]

                                        statusImage = when (item.applicationStatus) {
                                            "rejected" -> {
                                                R.drawable.application_reject
                                            }

                                            "pending" -> {
                                                R.drawable.application_pending
                                            }

                                            else -> {
                                                R.drawable.step
                                            }
                                        }

                                        lineHeight =
                                            if (index == uiState.applicationById.applicationHistories.size - 1) {
                                                32.dp
                                            } else {
                                                60.dp
                                            }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .wrapContentWidth(),
                                                verticalArrangement = Arrangement.spacedBy(0.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Image(
                                                    painter = painterResource(id = statusImage),
                                                    contentDescription = "Step Icon",
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                )

                                                Box(
                                                    modifier = Modifier
                                                        .width(2.dp)
                                                        .height(lineHeight)
                                                        .background(color = Color(0xFFE4E7ED))
                                                ) {}
                                            }

                                            Spacer(modifier = Modifier.width(16.dp))

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(52.dp)
                                                    .border(
                                                        1.dp,
                                                        Color(0xFFE4E7ED),
                                                        RoundedCornerShape(8.dp)
                                                    )
                                                    .background(
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(horizontal = 16.dp),
                                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        text = item.applicationStatus,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W600,
                                                        color = Color(0xFF1ED292),
                                                        fontSize = 14.sp,
                                                        maxLines = 1,
                                                        softWrap = true,
                                                        overflow = TextOverflow.Ellipsis
                                                    )

                                                    Text(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        text = DateConversionFromYMDToMDY(item.applicationDate),
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFF757575),
                                                        fontSize = 12.sp,
                                                        maxLines = 1,
                                                        softWrap = true,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                            }

                                        }
                                    }
                                }
                            }

                            //Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }

            }
        }
    }
}

fun DateConversionFromYMDToMDY(originalDateString: String): String {
    //val originalDateString = "2021-05-02"
    val originalDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val originalDate = originalDateFormat.parse(originalDateString)
    val desiredDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    val formattedDate = desiredDateFormat.format(originalDate)
    return formattedDate

}