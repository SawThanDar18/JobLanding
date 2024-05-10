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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import co.nexlabs.betterhr.joblanding.common.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ApplicationsScreen(viewModel: ApplicationViewModel, navController: NavController) {

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    scope.launch {
        viewModel.fetchApplication()
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
            ErrorLayout(uiState.error)
        }

        AnimatedVisibility(
            (uiState.application.isNotEmpty() && uiState.companyData.isNotEmpty()),
            enter = fadeIn(),
            exit = fadeOut()
        ) {

            Column(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
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
                            Text(
                                text = uiState.application.size.toString(),
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFFAAAAAA),
                                fontSize = 14.sp
                            )
                        }

                    }

                    Image(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filter Icon",
                        modifier = Modifier
                            .size(20.dp, 18.dp),
                        alignment = Alignment.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                                        .border(1.dp, Color(0xFFFE4E7ED), RoundedCornerShape(8.dp))
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        )
                                        .clickable {
                                            navController.navigate("application-detail-screen/${uiState.application[index].id}")
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
                                        Row(
                                            modifier = Modifier.weight(1f),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Image(
                                                painter = rememberGlidePainter(request = uiState.companyData[0].company.companyLogo),
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
                                                    text = uiState.companyData[0].company.companyName,
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

            }
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
