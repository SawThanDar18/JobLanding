package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.applications

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
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
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationFilterViewModel
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun ApplicationsScreen(viewModel: ApplicationViewModel, navController: NavController) {

    var refreshing by remember { mutableStateOf(false) }
    var filterBottomBarVisible by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val filters by viewModel.filters.collectAsState()

    var jobIds by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            scope.launch {
                if (viewModel.getBearerToken() != "") {
                    viewModel.fetchApplication()
                }
            }
            refreshing = false
        }
    }

    scope.launch {
        if (viewModel.getBearerToken() != "") {
            viewModel.fetchApplication()
        }
    }

    if (uiState.isSuccessGetApplicationData) {
        LaunchedEffect(Unit) {
            if (uiState.application.isNotEmpty()) {
                jobIds = uiState.application.map {
                    it.referenceJobId
                }
            }
        }
    }

    if (jobIds.isNotEmpty()) {
        scope.launch {
            viewModel.getCompanyInfo(jobIds)
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
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
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
                            text = "Applications",
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
                            if (uiState.application.isEmpty()) {
                                Text(
                                    text = "0",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFFAAAAAA),
                                    fontSize = 14.sp
                                )
                            } else {
                                Text(
                                    text = uiState.application.size.toString(),
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFFAAAAAA),
                                    fontSize = 14.sp
                                )
                            }
                        }

                    }

                    Image(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filter Icon",
                        modifier = Modifier
                            .size(20.dp, 18.dp)
                            .clickable {
                                filterBottomBarVisible = true
                            },
                        alignment = Alignment.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.application.isNotEmpty() && uiState.companyData.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(bottom = 80.dp)
                    ) {
                        item {
                            FlowRow(
                                maxItemsInEachRow = 1,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                repeat(uiState.application.size) { index ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(81.dp)
                                            .border(
                                                1.dp,
                                                Color(0xFFFE4E7ED),
                                                RoundedCornerShape(8.dp)
                                            )
                                            .background(
                                                color = Color.Transparent,
                                                shape = MaterialTheme.shapes.medium
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 16.dp)
                                                .clickable {
                                                    navController.navigate("application-details/${uiState.application[index].id}/${uiState.companyData[index].company.companyName}")
                                                },
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                modifier = Modifier.weight(1f),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {

                                                Image(
                                                    painter = rememberAsyncImagePainter(uiState.companyData[index].company.companyLogo),
                                                    contentDescription = "Company Icon",
                                                    modifier = Modifier
                                                        .size(32.dp)
                                                        .clip(CircleShape)
                                                )

                                                Column(
                                                    modifier = Modifier
                                                        .wrapContentWidth()
                                                        .height(65.dp),
                                                    verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        text = DateConversion(uiState.application[index].appliedDate),
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFF757575),
                                                        fontSize = 12.sp
                                                    )

                                                    Spacer(modifier = Modifier.height(2.dp))

                                                    Text(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        text = uiState.application[index].jobTitle,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W600,
                                                        color = Color(0xFF4A4A4A),
                                                        fontSize = 14.sp
                                                    )

                                                    Spacer(modifier = Modifier.height(2.dp))

                                                    Text(
                                                        modifier = Modifier.width(120.dp),
                                                        text = uiState.companyData[index].company.companyName,
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

                                            Column(
                                                horizontalAlignment = Alignment.End,
                                                verticalArrangement = Arrangement.Bottom
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .width(62.dp)
                                                        .height(26.dp)
                                                        .border(
                                                            1.dp,
                                                            Color(0xFFEDFCF7),
                                                            RoundedCornerShape(4.dp)
                                                        )
                                                        .background(
                                                            color = Color(0xFFEDFCF7),
                                                            shape = MaterialTheme.shapes.medium
                                                        ),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        modifier = Modifier.width(46.dp),
                                                        text = uiState.application[index].status,
                                                        textAlign = TextAlign.Center,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFF1ED292),
                                                        fontSize = 12.sp,
                                                        maxLines = 1,
                                                        softWrap = true,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }

                                                if (uiState.application[index].haveAssignment && !uiState.application[index].isAssignmentSubmmitted) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.pending_assignment),
                                                        contentDescription = "Pending Assignment Icon",
                                                        modifier = Modifier
                                                            .size(124.dp, 16.dp),
                                                        alignment = Alignment.Center
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
                            text = "There is no Applications yet!",
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



    if (filterBottomBarVisible) {
        ModalBottomSheetLayout(
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 72.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Filter",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Image(
                            painter = painterResource(id = R.drawable.x),
                            contentDescription = "X Icon",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    filterBottomBarVisible = false
                                    //update localstorage
                                },
                            alignment = Alignment.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Status",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W700,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(
                            start = 8.dp
                        ).fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    filters.forEach { (key, isChecked) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = key,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W500,
                                color = Color(0xFF6A6A6A),
                                fontSize = 14.sp
                            )

                            Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { newChecked ->
                                        scope.launch {
                                            viewModel.updateFilter(key, newChecked)
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFF1ED292),
                                        uncheckedColor = Color(0xFFA7BAC5),
                                        checkmarkColor = Color.White
                                    )
                                )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        textAlign = TextAlign.Start,
                        text = "Dismiss",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF1ED292),
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { filterBottomBarVisible = false }
                    )
                }
            },
            sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Expanded
            ),
            sheetShape = RoundedCornerShape(
                bottomStart = 0.dp,
                bottomEnd = 0.dp,
                topStart = 24.dp,
                topEnd = 24.dp
            ),
            sheetElevation = 16.dp,
            sheetBackgroundColor = Color.White,
            sheetContentColor = contentColorFor(Color.White),
            modifier = Modifier.fillMaxWidth(),
            scrimColor = Color.Transparent
        ) {

        }
    }

}

@Composable
fun FilterScreen(viewModel: ApplicationFilterViewModel) {
    val filters = viewModel.filters.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Filter")

        Spacer(modifier = Modifier.height(16.dp))

        filters.value.forEach { (key, isChecked) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(key)
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { newChecked ->
                        scope.launch {
                            viewModel.updateFilter(key, newChecked)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        TextButton(onClick = { /* Dismiss or apply the filter */ }) {
            Text("Dismiss")
        }
    }
}



fun DateConversion(originalDateTimeString: String): String {
//    val originalDateTimeString = "2024-05-09 21:16:50"
    val originalDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val originalDate = originalDateFormat.parse(originalDateTimeString)
    val desiredDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    val formattedDate = desiredDateFormat.format(originalDate)
    return formattedDate
}
