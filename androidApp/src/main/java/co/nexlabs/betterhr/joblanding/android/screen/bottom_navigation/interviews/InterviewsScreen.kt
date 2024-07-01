package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.interviews

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.interview.InterviewViewModel
import co.nexlabs.betterhr.joblanding.network.api.interview.data.InterviewUIModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterviewsScreen(viewModel: InterviewViewModel, navController: NavController) {

    var refreshing by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var jobIds by remember { mutableStateOf<List<String>>(emptyList()) }

    val dateFormat = SimpleDateFormat("E, d MMM", Locale.getDefault())

    var todayInterviewList: MutableList<InterviewUIModel> = ArrayList()
    var upComingInterviewList: MutableList<InterviewUIModel> = ArrayList()

    LaunchedEffect(refreshing) {
        if (refreshing) {
            scope.launch {
                if (viewModel.getBearerToken() != "") {
                    viewModel.fetchInterviews()
                }
            }
            refreshing = false
        }
    }

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
                    if (viewModel.getBearerToken() != "") {
                        viewModel.fetchInterviews()
                    }
                }
                Log.d("state>>", "created")
            }
            Lifecycle.State.STARTED -> {
                scope.launch {
                    if (viewModel.getBearerToken() != "") {
                        viewModel.fetchInterviews()
                    }
                }
                Log.d("state>>", "started")
            }
            Lifecycle.State.RESUMED -> {
                scope.launch {
                    if (viewModel.getBearerToken() != "") {
                        viewModel.fetchInterviews()
                    }
                }
                Log.d("state>>", "resume")
            }
        }
    }

    if (uiState.isSuccessGetInterviewData) {
        LaunchedEffect(Unit) {
            if (uiState.interviewList.isNotEmpty()) {
                jobIds = uiState.interviewList.map {
                    it.jobId
                }
            }
        }
    }

    if (jobIds.isNotEmpty()) {
        scope.launch {
            viewModel.getCompanyInfo(jobIds)
        }
    }

    if (uiState.interviewList.isNotEmpty()) {
        uiState.interviewList.map { interviewData ->
            var status = checkDate(interviewData.interviewDate)

            when (status) {
                "Today" -> {
                    todayInterviewList.add(interviewData)
                }

                "Upcoming" -> {
                    upComingInterviewList.add(interviewData)
                }

                else -> {

                }
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true },
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            AnimatedVisibility(
                uiState.error != UIErrorType.Nothing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ErrorLayout(uiState.error)
            }
            Column(
                modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 80.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = "Interviews",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE4E7ED),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .width(29.dp)
                                .height(21.dp)
                                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(100.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (uiState.interviewList.isEmpty()) {
                                Text(
                                    text = "0",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFFAAAAAA),
                                    fontSize = 14.sp
                                )
                            } else {
                                Text(
                                    text = uiState.interviewList.size.toString(),
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFFAAAAAA),
                                    fontSize = 14.sp
                                )
                            }
                        }

                    }

                    /*Image(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filter Icon",
                        modifier = Modifier
                            .size(20.dp, 18.dp),
                        alignment = Alignment.Center
                    )*/
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.interviewList.isNotEmpty()) {
                    LazyColumn {

                        item {
                            if (todayInterviewList.isNotEmpty()) {

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Today",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFF4A4A4A),
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                FlowRow(
                                    maxItemsInEachRow = 1,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    repeat(todayInterviewList.size) { index ->
                                        var item = todayInterviewList[index]
                                        Column(
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = formatDate(item.interviewDate),
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFF6A6A6A),
                                                fontSize = 14.sp
                                            )

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(89.dp)
                                                    .border(
                                                        1.dp,
                                                        Color(0xFFE4E7ED),
                                                        RoundedCornerShape(8.dp)
                                                    )
                                                    .background(
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    )
                                                    .clickable {
                                                        navController.navigate("notification-details/${item.referenceNotificationId}/${item.interviewType}/${"link"}")
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(horizontal = 16.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Column(
                                                        verticalArrangement = Arrangement.spacedBy(
                                                            4.dp
                                                        ),
                                                        horizontalAlignment = Alignment.Start
                                                    ) {
                                                        Text(
                                                            text = "${formatTime(item.interviewStartTime)} - ${
                                                                formatTime(
                                                                    item.interviewEndTime
                                                                )
                                                            }",
                                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                            fontWeight = FontWeight.W400,
                                                            color = Color(0xFF757575),
                                                            fontSize = 12.sp
                                                        )

                                                        Text(
                                                            text = "${item.interviewRound} Interview",
                                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                            fontWeight = FontWeight.W500,
                                                            color = Color(0xFF6A6A6A),
                                                            fontSize = 14.sp
                                                        )

                                                        Box(
                                                            modifier = Modifier
                                                                .width(73.dp)
                                                                .height(29.dp)
                                                                .border(
                                                                    1.dp,
                                                                    Color(0xFFF2FDF9),
                                                                    RoundedCornerShape(8.dp)
                                                                )
                                                                .background(
                                                                    color = Color(0xFFF2FDF9),
                                                                    shape = MaterialTheme.shapes.medium
                                                                ),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Text(
                                                                text = formatString(item.interviewType),
                                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                                fontWeight = FontWeight.W600,
                                                                color = Color(0xFF1ED292),
                                                                fontSize = 12.sp
                                                            )
                                                        }
                                                    }

                                                    Spacer(modifier = Modifier.width(8.dp))

                                                    AsyncImage(
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .clip(CircleShape),
                                                        model = uiState.companyData[index].company.companyLogo,
                                                        contentDescription = "Company Logo",
                                                        contentScale = ContentScale.Fit,
                                                    )
                                                }

                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(32.dp))
                            }

                            if (upComingInterviewList.isNotEmpty()) {

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Upcoming",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFF4A4A4A),
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                FlowRow(
                                    maxItemsInEachRow = 1,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    repeat(upComingInterviewList.size) { index ->
                                        var item = upComingInterviewList[index]
                                        Column(
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                text = formatDate(item.interviewDate),
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFF6A6A6A),
                                                fontSize = 14.sp
                                            )

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(89.dp)
                                                    .border(
                                                        1.dp,
                                                        Color(0xFFE4E7ED),
                                                        RoundedCornerShape(8.dp)
                                                    )
                                                    .background(
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    )
                                                    .clickable {
                                                        navController.navigate("notification-details/${item.referenceNotificationId}/${item.interviewType}/${"link"}")
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(horizontal = 16.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Column(
                                                        verticalArrangement = Arrangement.spacedBy(
                                                            4.dp
                                                        ),
                                                        horizontalAlignment = Alignment.Start
                                                    ) {
                                                        Text(
                                                            text = "${formatTime(item.interviewStartTime)} - ${
                                                                formatTime(
                                                                    item.interviewEndTime
                                                                )
                                                            }",
                                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                            fontWeight = FontWeight.W400,
                                                            color = Color(0xFF757575),
                                                            fontSize = 12.sp
                                                        )

                                                        Text(
                                                            text = "${item.interviewRound} Interview",
                                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                            fontWeight = FontWeight.W500,
                                                            color = Color(0xFF6A6A6A),
                                                            fontSize = 14.sp
                                                        )

                                                        Box(
                                                            modifier = Modifier
                                                                .width(73.dp)
                                                                .height(29.dp)
                                                                .border(
                                                                    1.dp,
                                                                    Color(0xFFF2FDF9),
                                                                    RoundedCornerShape(8.dp)
                                                                )
                                                                .background(
                                                                    color = Color(0xFFF2FDF9),
                                                                    shape = MaterialTheme.shapes.medium
                                                                ),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Text(
                                                                text = formatString(item.interviewType),
                                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                                fontWeight = FontWeight.W600,
                                                                color = Color(0xFF1ED292),
                                                                fontSize = 12.sp
                                                            )
                                                        }
                                                    }

                                                    Spacer(modifier = Modifier.width(8.dp))

                                                    Image(
                                                        painter = painterResource(id = R.drawable.bank_logo),
                                                        contentDescription = "Company Icon",
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .clip(CircleShape)
                                                    )
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "There is no Interviews yet!",
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
}

fun checkDate(inputDate: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = Calendar.getInstance()
    val dateToCheck = Calendar.getInstance().apply {
        time = sdf.parse(inputDate)
    }

    return when {
        isSameDay(dateToCheck, currentDate) -> "Today"
        isFutureDate(dateToCheck, currentDate) -> "Upcoming"
        else -> "Past"
    }
}

fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
}

fun isFutureDate(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.after(cal2)
}

fun formatTime(inputTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = inputFormat.parse(inputTime)
    return outputFormat.format(date)
}

fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)
}

fun formatString(input: String): String {
    return input.split("_").joinToString(" ") { it.capitalize() }
}