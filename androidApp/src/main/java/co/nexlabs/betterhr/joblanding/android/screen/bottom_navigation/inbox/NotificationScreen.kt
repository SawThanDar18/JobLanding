package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox

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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.register.Overlap
import co.nexlabs.betterhr.joblanding.common.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.inbox.InboxViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
@Composable
fun NotificationScreen(viewModel: InboxViewModel, navController: NavController) {

    var statusColor by remember { mutableStateOf(Color(0xFFFFFFFF)) }
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var bottomBarVisible by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        scope.launch {
            if (!viewModel.getBearerToken().isNullOrBlank()) {
                viewModel.fetchNotification(emptyList())
            }
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
            uiState.notificationList.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = "Inbox",
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
                                text = uiState.notificationList.size.toString(),
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
                            .size(20.dp, 18.dp)
                            .clickable {
                                bottomBarVisible = true
                            },
                        alignment = Alignment.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .height(45.dp)
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp))
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        ),
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    placeholder = { Text("Search", color = Color(0xFFAAAAAA)) },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color(0xFFAAAAAA)
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color(0xFFAAAAAA),
                        backgroundColor = Color.Transparent,
                        cursorColor = Color(0xFF1ED292),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    singleLine = true,
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Search Icon",
                            modifier = Modifier
                                .size(16.dp)
                        )
                    },
                )

                Spacer(modifier = Modifier.height(20.dp))

                AnimatedVisibility(
                    uiState.notificationList.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp, bottom = 32.dp)
                    ) {

                        item {
                            FlowRow(
                                maxItemsInEachRow = 1,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                repeat(uiState.notificationList.size) {index ->

                                    var item = uiState.notificationList[index]

                                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                    val timestamp = dateFormat.parse(item.updateAt)
                                    val calendarTimestamp = Calendar.getInstance()
                                    calendarTimestamp.time = timestamp
                                    val now = Calendar.getInstance()
                                    val diffMillis = now.timeInMillis - calendarTimestamp.timeInMillis
                                    val diffSeconds = diffMillis / 1000

                                    statusColor = when (item.status) {
                                        "complete" -> Color(0xFF41D888)
                                        "pending" -> Color(0xFFFCB006)
                                        "reject" -> Color(0xFFF95E08)
                                        "pin" -> Color(0xFF606060)
                                        else -> Color(0xFF606060)
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                       navController.navigate("notification-details/${item.id}/${item.notiType}/${"link"}")
                                            },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            modifier = Modifier.wrapContentSize(),
                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {

                                            Box(
                                                modifier = Modifier
                                                    .width(4.dp)
                                                    .height(64.dp)
                                                    .border(
                                                        1.dp,
                                                        statusColor,
                                                        RoundedCornerShape(100.dp)
                                                    )
                                                    .background(
                                                        color = statusColor,
                                                        shape = MaterialTheme.shapes.medium
                                                    )
                                            )

                                            Column(
                                                modifier = Modifier
                                                    .wrapContentWidth()
                                                    .height(64.dp),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                Text(
                                                    modifier = Modifier.width(234.dp),
                                                    text = item.title,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W500,
                                                    color = Color(0xFF6A6A6A),
                                                    fontSize = 14.sp,
                                                    maxLines = 2,
                                                    softWrap = true,
                                                    overflow = TextOverflow.Ellipsis
                                                )

                                                Spacer(modifier = Modifier.height(2.dp))

                                                Text(
                                                    modifier = Modifier.width(47.dp),
                                                    text = when {
                                                        diffSeconds < 60 -> "Just now"
                                                        diffSeconds < 3600 -> "${diffSeconds / 60} minutes ago"
                                                        diffSeconds < 86400 -> "${diffSeconds / 3600} hours ago"
                                                        else -> SimpleDateFormat("h:mma", Locale.getDefault()).format(timestamp)
                                                    },
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W500,
                                                    color = Color(0xFF6A6A6A),
                                                    fontSize = 12.sp,
                                                    maxLines = 1,
                                                    softWrap = true,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }

                                        }

                                        Row(
                                            modifier = Modifier.wrapContentSize(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.bank_logo),
                                                contentDescription = "Company Icon",
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .clip(CircleShape)
                                            )

                                            Image(
                                                painter = painterResource(id = R.drawable.chevron_right),
                                                contentDescription = "Arrow Icon",
                                                modifier = Modifier
                                                    .size(24.dp)
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(335.dp)
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (bottomBarVisible) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
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
                                contentDescription = "Close Icon",
                                modifier = Modifier
                                    .size(20.dp),
                                alignment = Alignment.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Status",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W700,
                                color = Color(0xFF6A6A6A),
                                fontSize = 16.sp
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.complete_filter),
                                    contentDescription = "Complete Filter Icon",
                                    modifier = Modifier
                                        .size(width = 4.dp, height = 21.dp),
                                    alignment = Alignment.Center
                                )
                                
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "Complete",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W700,
                                    color = Color(0xFF6A6A6A),
                                    fontSize = 16.sp
                                )
                            }
                        }
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
}

data class NotificationStatus(
    val name: String?,
    val color: Color,
    val title: String,
    val isChecked: Boolean
) {
    companion object {
        var PENDING = NotificationStatus("pending", Color(0xFFFCB006), "Pending", true)
        var APPROVE = NotificationStatus("approve", Color(0xFF41D888), "Complete", true)
        var REJECT = NotificationStatus("reject", Color(0xFFF95E08), "Rejected", true)
        var PIN = NotificationStatus("pin", Color(0xFF606060), "Pin", true)

        fun fromString(responseStatus: String): NotificationStatus {
            return when (responseStatus.toLowerCase()) {
                "pending" -> PENDING
                "complete" -> APPROVE
                "reject" -> REJECT
                "pin" -> PIN
                else -> PIN
            }
        }

        fun toNotiSet(notiTypeNames: Set<String>): Set<NotificationStatus> {
            return notiTypeNames.map { fromString(it) }.toSet()
        }

        fun toStringSet(notificationStatuses: Set<NotificationStatus>): Set<String> {
            return notificationStatuses.map { it.name!!.toLowerCase() }.toSet()
        }

        val allStringSet: Set<String>
            get() = setOf("pending", "complete", "reject", "pin")

        val allObjectSet: Set<NotificationStatus>
            get() = toNotiSet(allStringSet)
    }
}